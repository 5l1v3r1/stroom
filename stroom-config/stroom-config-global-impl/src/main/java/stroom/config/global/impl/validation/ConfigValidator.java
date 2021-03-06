package stroom.config.global.impl.validation;

import stroom.security.impl.ValidationSeverity;
import stroom.util.config.PropertyUtil;
import stroom.util.logging.LambdaLogger;
import stroom.util.logging.LambdaLoggerFactory;
import stroom.util.logging.LogUtil;
import stroom.util.shared.AbstractConfig;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ConfigValidator {

    private static final LambdaLogger LOGGER = LambdaLoggerFactory.getLogger(ConfigValidator.class);


    private final Validator validator;

    @Inject
    public ConfigValidator(final Validator validator) {
        this.validator = validator;
    }

    /**
     * Validates a single config property value
     */
    public Result validateValue(final Class<? extends AbstractConfig> configClass,
                                final String propertyName,
                                final Object value) {

        final Set<? extends ConstraintViolation<? extends AbstractConfig>> constraintViolations =
                validator.validateValue(configClass, propertyName, value);

        return Result.of(constraintViolations);
    }

    /**
     * Default validation that logs errors/warnings to the logger as well as returning the results
     */
    public Result validate(final AbstractConfig config) {
        final Set<ConstraintViolation<AbstractConfig>> constraintViolations = validator.validate(config);
        return Result.of(constraintViolations);
    }

    /**
     * Walks the config object tree validating each branch
     */
    public Result validateRecursively(final AbstractConfig config) {
        final List<Result> resultList = new ArrayList<>();

        // Validate the top level AppConfig object
        final ConfigValidator.Result rootResult = validate(config);
        if (rootResult.hasErrorsOrWarnings()) {
            resultList.add(rootResult);
        }

        // Now validate each of the branches recursively
        // We could do this by setting @Valid on each config getter but that is liable to
        // be forgotten on new config objects
        PropertyUtil.walkObjectTree(
            config,
            prop ->
                // Only want to validate config objects
                AbstractConfig.class.isAssignableFrom(prop.getValueClass())
                    && prop.getValueFromConfigObject() != null,
            prop -> {
                final AbstractConfig configObject = (AbstractConfig) prop.getValueFromConfigObject();
                final ConfigValidator.Result result = validate(configObject);
                if (result.hasErrorsOrWarnings()) {
                    resultList.add(result);
                }
            });

        return ConfigValidator.Result.of(resultList);
    }

    public static void logConstraintViolation(
        final ConstraintViolation<? extends AbstractConfig> constraintViolation,
        final ValidationSeverity severity) {

        final Consumer<String> logFunc;
        final String severityStr;
        if (severity.equals(ValidationSeverity.WARNING)) {
            logFunc = LOGGER::warn;
            severityStr = "warning";
        } else {
            logFunc = LOGGER::error;
            severityStr = "error";
        }

        String propName = null;
        for (javax.validation.Path.Node node : constraintViolation.getPropertyPath()) {
            propName = node.getName();
        }
        final AbstractConfig config = (AbstractConfig) constraintViolation.getLeafBean();

        final String path = config.getFullPath(propName);

        logFunc.accept(LogUtil.message("  Validation {} for {} [{}] - {}",
            severityStr,
            path,
            constraintViolation.getInvalidValue(),
            constraintViolation.getMessage()));
    }

    public static class Result {
        private static final Result EMPTY = new Result(Collections.emptySet());

        private final int errorCount;
        private final int warningCount;
        private final Set<? extends ConstraintViolation<? extends AbstractConfig>> constraintViolations;

        private Result(final Set<? extends ConstraintViolation<? extends AbstractConfig>> constraintViolations) {
            if (constraintViolations == null || constraintViolations.isEmpty()) {
                this.errorCount = 0;
                this.warningCount = 0;
                this.constraintViolations = Collections.emptySet();
            } else {
                int errorCount = 0;
                int warningCount = 0;

                for (final ConstraintViolation<? extends AbstractConfig> constraintViolation : constraintViolations) {
                    LOGGER.debug(() -> LogUtil.message("constraintViolation - prop: {}, value: [{}], object: {}",
                            constraintViolation.getPropertyPath().toString(),
                            constraintViolation.getInvalidValue(),
                            constraintViolation.getLeafBean() != null
                                ? constraintViolation.getLeafBean().getClass().getCanonicalName()
                                : "NULL"));

                    final ValidationSeverity severity = ValidationSeverity.fromPayloads(
                            constraintViolation.getConstraintDescriptor().getPayload());

                    if (severity.equals(ValidationSeverity.WARNING)) {
                        warningCount++;
                    } else if (severity.equals(ValidationSeverity.ERROR)) {
                        errorCount++;
                    }
                }
                this.errorCount = errorCount;
                this.warningCount = warningCount;
                this.constraintViolations = constraintViolations;
            }
        }

        public static Result of(final Set<? extends ConstraintViolation<? extends AbstractConfig>> constraintViolations) {
            if (constraintViolations == null || constraintViolations.isEmpty()) {
                return empty();
            } else {
                return new Result(constraintViolations);
            }
        }

        public static Result of(final Collection<Result> results) {
            final Set<ConstraintViolation<? extends AbstractConfig>> constraintViolations = new HashSet<>();

            results.forEach(result -> {
                if (result.hasErrorsOrWarnings()) {
                    constraintViolations.addAll(result.constraintViolations);
                }
            });

            return Result.of(constraintViolations);
        }

        public static Result merge(final Result result1, final Result result2) {
            final Set<ConstraintViolation<? extends AbstractConfig>> constraintViolations = new HashSet<>();
            constraintViolations.addAll(result1.constraintViolations);
            constraintViolations.addAll(result2.constraintViolations);
            return Result.of(constraintViolations);
        }


        public static Result empty() {
            return EMPTY;
        }

        /**
         * The passed constraintViolationConsumer is called for each violation in the result set. If there are no
         * errors or warnings the consumer will not be called.
         */
        public void handleViolations(
                final BiConsumer<ConstraintViolation<? extends AbstractConfig>, ValidationSeverity> constraintViolationConsumer) {

            for (final ConstraintViolation<? extends AbstractConfig> constraintViolation : constraintViolations) {
                final ValidationSeverity severity = ValidationSeverity.fromPayloads(
                        constraintViolation.getConstraintDescriptor().getPayload());

                constraintViolationConsumer.accept(constraintViolation, severity);
            }
        }

        /**
         * The passed errorConsumer is called for each ERROR violation in the result set. If there are no
         * errors the consumer will not be called.
         */
        public void handleErrors(
                final Consumer<ConstraintViolation<? extends AbstractConfig>> errorConsumer) {

            handleViolations(buildFilteringConsumer(ValidationSeverity.ERROR, errorConsumer));
        }

        /**
         * The passed errorConsumer is called for each WARNING violation in the result set. If there are no
         * errors the consumer will not be called.
         */
        public void handleWarnings(
                final Consumer<ConstraintViolation<? extends AbstractConfig>> warningConsumer) {

            handleViolations(buildFilteringConsumer(ValidationSeverity.WARNING, warningConsumer));
        }

        private BiConsumer<ConstraintViolation<? extends AbstractConfig>, ValidationSeverity> buildFilteringConsumer(
                final ValidationSeverity requiredSeverity,
                final Consumer<ConstraintViolation<? extends AbstractConfig>> errorConsumer) {
            Objects.requireNonNull(requiredSeverity);

            return (constraintViolation, severity) -> {
                if (requiredSeverity.equals(severity)) {
                    errorConsumer.accept(constraintViolation);
                }
            };
        }

        public int getErrorCount() {
            return errorCount;
        }

        public int getWarningCount() {
            return warningCount;
        }

        public boolean hasErrors() {
            return errorCount > 0;
        }

        public boolean hasWarnings() {
            return warningCount > 0;
        }

        public boolean hasErrorsOrWarnings() {
            return errorCount > 0 || warningCount > 0;
        }

        @Override
        public String toString() {
            return "Result{" +
                "errorCount=" + errorCount +
                ", warningCount=" + warningCount +
                '}';
        }
    }
}
