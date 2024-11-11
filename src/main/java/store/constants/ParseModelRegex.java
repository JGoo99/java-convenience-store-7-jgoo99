package store.constants;

public enum ParseModelRegex {

    NAME("([가-힣a-zA-Z0-9+]+)"),
    NUMBER("(\\d+)"),
    DATE("(\\d{4}-\\d{2}-\\d{2})"),

    OPENED_SQUARE_BRACKET("\\["),
    CLOSED_SQUARE_BRACKET("\\]"),
    DASH("-"),
    COMMA(",");

    private final String regex;

    ParseModelRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public String toString() {
        return regex;
    }
}
