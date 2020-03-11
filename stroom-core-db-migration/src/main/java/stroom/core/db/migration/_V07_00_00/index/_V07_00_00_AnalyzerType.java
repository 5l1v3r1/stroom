package stroom.core.db.migration._V07_00_00.index;

import stroom.docref.HasDisplayValue;

enum _V07_00_00_AnalyzerType implements HasDisplayValue {
    KEYWORD("Keyword"),
    ALPHA("Alpha"),
    NUMERIC("Numeric"),
    ALPHA_NUMERIC("Alpha numeric"),
    WHITESPACE("Whitespace"),
    STOP("Stop words"),
    STANDARD("Standard");

    private final String displayValue;

    _V07_00_00_AnalyzerType(final String displayValue) {
        this.displayValue = displayValue;
    }

    @Override
    public String getDisplayValue() {
        return displayValue;
    }
}