/*
 * Copyright 2017 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package stroom.config.global.impl.db;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.google.common.base.Splitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import stroom.config.app.AppConfig;
import stroom.config.global.api.ConfigProperty;
import stroom.config.global.impl.db.BeanUtil.Prop;
import stroom.docref.DocRef;
import stroom.util.config.annotations.Password;
import stroom.util.config.annotations.ReadOnly;
import stroom.util.config.annotations.RequiresRestart;
import stroom.util.logging.LambdaLogger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Singleton
class ConfigMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigMapper.class);

    private static final List<String> DELIMITERS = List.of(
            "|", ":", ";", ",", "!", "/", "\\", "#", "@", "~", "-", "_", "=", "+", "?");
    private static final String ROOT_PROPERTY_PATH = "stroom";
    private static final String DOCREF_PREFIX = "docRef(";

    private final List<ConfigProperty> globalProperties = new ArrayList<>();
    private final Map<String, Prop> propertyMap = new HashMap<>();

    @Inject
    ConfigMapper(final AppConfig appConfig) {

        // The values in the passed AppConfig will have been set from the yaml by DropWizard on
        // app boot.  We want to know the default values as defined by the compile-time initial values of
        // the instance variables in the AppConfig tree.  Therefore create our own AppConfig tree and
        // walk it to build a map of these default values.
        final Map<String, Prop> propertyDefaultsMap = new HashMap<>();
        addConfigObjectMethods(new AppConfig(), ROOT_PROPERTY_PATH, propertyDefaultsMap);

        final BiConsumer<String, Prop> propConsumer = createConfigPropertyCreationConsumer(propertyDefaultsMap);
        // walk the AppConfig object model creating ConfigProperty objects for each property
        addConfigObjectMethods(appConfig, ROOT_PROPERTY_PATH, propertyMap, propConsumer);

        globalProperties.sort(Comparator.naturalOrder());
    }

    List<ConfigProperty> getGlobalProperties() {
        return globalProperties;
    }


    private void addConfigObjectMethods(final Object object,
                                        final String path,
                                        final Map<String, Prop> propertyMap) {
        addConfigObjectMethods(object, path, propertyMap, (fullPath, prop) -> {
            // Do nothing
        });

    }

    private void addConfigObjectMethods(final Object object,
                                        final String path,
                                        final Map<String, Prop> propertyMap,
                                        final BiConsumer<String, Prop> propConsumer) {
        LOGGER.trace("addConfigObjectMethods({}, {}, .....)", object, path);

        final Map<String, Prop> properties = BeanUtil.getProperties(object);
        properties.forEach((k, prop) -> {
            LOGGER.trace("prop: {}", prop);
            Method getter = prop.getGetter();
            String specifiedName = getNameFromAnnotation(getter);
            String name = prop.getName();
            if (specifiedName != null) {
                name = specifiedName;
            }

            final String fullPath = path + "." + name;

            try {
                final Class<?> valueType = prop.getValueClass();

                final Object value = prop.getValueFromConfigObject();
                if (value != null) {
                    if (isSupportedPropertyType(valueType)) {
                        // This is a leaf, i.e. a property so add it to our map
                        propertyMap.put(fullPath, prop);

                        // Now let the consumer do something to it
                        propConsumer.accept(fullPath, prop);
                    } else {
                        // This must be a branch, i.e. config object so recurse into that
                        addConfigObjectMethods(value, fullPath, propertyMap, propConsumer);
                    }
                }

            } catch (final InvocationTargetException | IllegalAccessException e) {
                LOGGER.error(e.getMessage(), e);
            }
        });
    }

    private BiConsumer<String, Prop> createConfigPropertyCreationConsumer(final Map<String, Prop> propertyDefaults) {

        return (fullPath, prop) -> {
            // Create global property.
            final Prop propDefault = propertyDefaults.get(fullPath);
            final String defaultValueAsStr = getDefaultValue(propDefault);
            final String valueAsStr = getStringValue(prop);

            // build a new ConfigProperty object from our Prop and our defaults
            final ConfigProperty configProperty = new ConfigProperty();
            configProperty.setSource("Code");
            configProperty.setName(fullPath);
            configProperty.setValue(valueAsStr);
            configProperty.setDefaultValue(defaultValueAsStr);

            updatePropertyFromConfigAnnotations(configProperty, prop);

            // Default the value if we don't have a value and have a default
            if (configProperty.getValue() == null && configProperty.getDefaultValue() != null) {
                configProperty.setValue(configProperty.getDefaultValue());
            }

            globalProperties.add(configProperty);
        };
    }

    private boolean isSupportedPropertyType(final Class<?> type) {
        boolean isSupported = type.equals(String.class) ||
                type.equals(Byte.class) ||
                type.equals(byte.class) ||
                type.equals(Integer.class) ||
                type.equals(int.class) ||
                type.equals(Long.class) ||
                type.equals(long.class) ||
                type.equals(Short.class) ||
                type.equals(short.class) ||
                type.equals(Float.class) ||
                type.equals(float.class) ||
                type.equals(Double.class) ||
                type.equals(double.class) ||
                type.equals(Boolean.class) ||
                type.equals(boolean.class) ||
                type.equals(Character.class) ||
                type.equals(char.class) ||
                List.class.isAssignableFrom(type) ||
                Map.class.isAssignableFrom(type) ||
                DocRef.class.isAssignableFrom(type);

        LOGGER.trace("isSupportedPropertyType({}), returning: {}", type, isSupported);
        return isSupported;
    }

    private void updatePropertyFromConfigAnnotations(final ConfigProperty configProperty, final Prop prop) {
        // Editable by default unless found otherwise below
        configProperty.setEditable(true);

        for (final Annotation declaredAnnotation : prop.getGetter().getDeclaredAnnotations()) {
            Class<? extends Annotation> annotationType = declaredAnnotation.annotationType();

            if (annotationType.equals(JsonPropertyDescription.class)) {
                configProperty.setDescription(((JsonPropertyDescription) declaredAnnotation).value());
            } else if (annotationType.equals(ReadOnly.class)) {
                configProperty.setEditable(false);
            } else if (annotationType.equals(Password.class)) {
                configProperty.setPassword(true);
            } else if (annotationType.equals(RequiresRestart.class)) {
                RequiresRestart.RestartScope scope = ((RequiresRestart) declaredAnnotation).value();
                switch (scope) {
                    case SYSTEM:
                        configProperty.setRequireRestart(true);
                        break;
                    case UI:
                        configProperty.setRequireUiRestart(true);
                        break;
                    default:
                        throw new RuntimeException("Should never get here");
                }
                configProperty.setEditable(false);
            }
        }
    }

    private String getNameFromAnnotation(final Method method) {
        for (final Annotation declaredAnnotation : method.getDeclaredAnnotations()) {
            if (declaredAnnotation.annotationType().equals(JsonProperty.class)) {
                final JsonProperty jsonProperty = (JsonProperty) declaredAnnotation;
                return jsonProperty.value();
            }
        }
        return null;
    }


    static String convertToString(final Object value) {
        List<String> availableDelimiters = new ArrayList<>(DELIMITERS);
        return convertToString(value, availableDelimiters);
    }

    private static String convertToString(final Object value, final List<String> availableDelimiters) {
        if (value != null) {
            if (value instanceof List) {
                return listToString((List<?>) value, availableDelimiters);
            } else if (value instanceof Map) {
                return mapToString((Map<?, ?>) value, availableDelimiters);
            } else if (value instanceof DocRef) {
                return docRefToString((DocRef) value, availableDelimiters);
            } else {
                return value.toString();
            }
        } else {
            return null;
        }
    }

    private String getStringValue(final Prop prop) {
        try {
            final Object value = prop.getValueFromConfigObject();
            if (value != null) {
                return convertToString(value);
            }
        } catch (final IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
            LOGGER.debug(e.getMessage(), e);
        }
        return null;
    }

    private String getDefaultValue(final Prop prop) {
        if (prop != null) {
            try {
                final Object value = prop.getValueFromConfigObject();
                if (value != null) {
                    return convertToString(value);
                }
            } catch (final IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                LOGGER.debug(e.getMessage(), e);
            }
        }
        return null;
    }

    Object updateConfigObject(final String key, final String value) {
        try {
            final Prop prop = propertyMap.get(key);
            if (prop != null) {
                final Type genericType = prop.getValueType();
                final Object typedValue = convertToObject(value, genericType);
                prop.setValueOnConfigObject(typedValue);
                return typedValue;
            }
        } catch (final IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
            // TODO why swallow these exceptions
            LOGGER.debug(e.getMessage(), e);
        }
        return null;
    }

    private static Object convertToObject(final String value, final Type genericType) {
        if (value == null) {
            return null;
        }

        Class<?> type = getDataType(genericType);

        if (type.equals(String.class)) {
            return value;
        } else if (type.equals(Byte.class) || type.equals(byte.class)) {
            return Byte.valueOf(value);
        } else if (type.equals(Integer.class) || type.equals(int.class)) {
            return Integer.valueOf(value);
        } else if (type.equals(Long.class) || type.equals(long.class)) {
            return Long.valueOf(value);
        } else if (type.equals(Short.class) || type.equals(short.class)) {
            return Short.valueOf(value);
        } else if (type.equals(Float.class) || type.equals(float.class)) {
            return Float.valueOf(value);
        } else if (type.equals(Double.class) || type.equals(double.class)) {
            return Double.valueOf(value);
        } else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
            return Boolean.valueOf(value);
        } else if ((type.equals(Character.class) || type.equals(char.class)) && value.length() > 0) {
            return value.charAt(0);
        } else if (type.isAssignableFrom(List.class)) {
            // determine the type of the list items
            Class<?> itemType = getDataType(getGenericTypes(genericType).get(0));
            return stringToList(value, itemType);
        } else if (type.isAssignableFrom(Map.class)) {
            // determine the types of the keys and values
            Class<?> keyType = getDataType(getGenericTypes(genericType).get(0));
            Class<?> valueType = getDataType(getGenericTypes(genericType).get(1));
            return stringToMap(value, keyType, valueType);
        } else if (type.equals(DocRef.class)) {
            return stringToDocRef(value);
        }

        return null;
    }


    private static String listToString(final List<?> list, final List<String> availableDelimiters) {

        if (list.isEmpty()) {
            return "";
        }
        List<String> strList = list.stream()
                .map(ConfigMapper::convertToString)
                .collect(Collectors.toList());

        String allText = String.join("", strList);

        String delimiter = getDelimiter(allText, availableDelimiters);

        // prefix the delimited form with the delimiter so when we deserialise
        // we know what the delimiter is
        return delimiter + String.join(delimiter, strList);
    }

    private static String mapToString(final Map<?, ?> map, final List<String> availableDelimiters) {
        if (map.isEmpty()) {
            return "";
        }
        // convert keys/values to strings
        final List<Map.Entry<String, String>> strEntries = map.entrySet().stream()
                .map(entry -> {
                    String key = ConfigMapper.convertToString(entry.getKey());
                    String value = ConfigMapper.convertToString(entry.getValue());
                    return Map.entry(key, value);
                })
                .collect(Collectors.toList());

        // join all strings into one fat string
        final String allText = strEntries.stream()
                .map(entry -> entry.getKey() + entry.getValue())
                .collect(Collectors.joining());

        final String keyValueDelimiter = getDelimiter(allText, availableDelimiters);
        final String entryDelimiter = getDelimiter(allText, availableDelimiters);

        // prefix the delimited form with the delimiters so when we deserialise
        // we know what the delimiters are
        return entryDelimiter + keyValueDelimiter + strEntries.stream()
                .map(entry ->
                        entry.getKey() + keyValueDelimiter + entry.getValue())
                .collect(Collectors.joining(entryDelimiter));
    }

    private static String docRefToString(final DocRef docRef, final List<String> availableDelimiters) {
        String allText = String.join("", docRef.getType(), docRef.getUuid(), docRef.getName());
        String delimiter = getDelimiter(allText, availableDelimiters);

        // prefix the delimited form with the delimiter so when we deserialise
        // we know what the delimiter is
        return delimiter
                + "docRef("
                + String.join(delimiter, docRef.getType(), docRef.getUuid(), docRef.getName())
                + ")";
    }

    private static <T> List<T> stringToList(final String serialisedForm, final Class<T> type) {
        if (serialisedForm == null || serialisedForm.isEmpty()) {
            return Collections.emptyList();
        }

        String delimiter = String.valueOf(serialisedForm.charAt(0));
        String delimitedValue = serialisedForm.substring(1);

        return StreamSupport.stream(Splitter.on(delimiter).split(delimitedValue).spliterator(), false)
                .map(str -> convertToObject(str, type))
                .map(type::cast)
                .collect(Collectors.toList());
    }

    private static <K, V> Map<K, V> stringToMap(
            final String serialisedForm,
            final Class<K> keyType,
            final Class<V> valueType) {

        final String entryDelimiter = String.valueOf(serialisedForm.charAt(0));
        final String keyValueDelimiter = String.valueOf(serialisedForm.charAt(1));

        // now remove the delimiters from our value
        final String delimitedValue = serialisedForm.substring(2);

        return StreamSupport.stream(Splitter.on(entryDelimiter).split(delimitedValue).spliterator(), false)
                .map(keyValueStr -> {
                    final List<String> parts = Splitter.on(keyValueDelimiter).splitToList(keyValueStr);

                    if (parts.size() < 1 || parts.size() > 2) {
                        throw new RuntimeException(LambdaLogger.buildMessage("Too many parts [{}] in value [{}], whole value [{}]",
                                parts.size(), keyValueStr, serialisedForm));
                    }

                    String keyStr = parts.get(0);
                    String valueStr = parts.size() == 1 ? null : parts.get(1);

                    K key = keyType.cast(convertToObject(keyStr, keyType));
                    V value = valueStr != null ? valueType.cast(convertToObject(valueStr, valueType)) : null;

                    return Map.entry(key, value);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static DocRef stringToDocRef(final String serialisedForm) {

        final String delimiter = String.valueOf(serialisedForm.charAt(0));
        String delimitedValue = serialisedForm.substring(1);

        delimitedValue = delimitedValue.replace(DOCREF_PREFIX, "");
        delimitedValue = delimitedValue.replace(")", "");

        final List<String> parts = Splitter.on(delimiter).splitToList(delimitedValue);

        return new DocRef.Builder()
                .type(parts.get(0))
                .uuid(parts.get(1))
                .name(parts.get(2))
                .build();
    }

    private static Class getDataType(Class clazz) {
        if (clazz.isPrimitive()) {
            return clazz;
        }

        if (clazz.isArray()) {
            return getDataType(clazz.getComponentType());
        }

        return clazz;
    }

    private static Class getDataType(Type type) {
        if (type instanceof Class) {
            return getDataType((Class) type);
        } else if (type instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) type;
            return getDataType(pt.getRawType());
        } else {
            throw new RuntimeException(LambdaLogger.buildMessage("Unexpected type of type {}",
                    type.getClass().getName()));
        }
    }

    private static List<Type> getGenericTypes(Type type) {
        if (type instanceof Class) {
            return Collections.emptyList();
        } else if (type instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) type;
            Type[] specificTypes = pt.getActualTypeArguments();

            return Arrays.asList(specificTypes);
        } else {
            throw new RuntimeException(LambdaLogger.buildMessage("Unexpected type of type {}",
                    type.getClass().getName()));
        }
    }

    private static String getDelimiter(final String allText, final List<String> availableDelimiters) {
        // find the first delimiter that does not appear in the text
        String chosenDelimiter = availableDelimiters.stream()
                .filter(delimiter -> !allText.contains(delimiter))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Exhausted all delimiters"));
        // remove the chosen delimiter so it doesn't get re-used for another purpose
        availableDelimiters.remove(chosenDelimiter);
        return chosenDelimiter;
    }

}
