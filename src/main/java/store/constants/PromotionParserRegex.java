package store.constants;

public enum PromotionParserRegex {

    NAME("([가-힣a-zA-Z0-9+]+)"),
    QUANTITY("(\\d+)"),
    DATE("(\\d{4}-\\d{2}-\\d{2})");

    private final String regex;

    PromotionParserRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public String toString() {
        return regex;
    }
}
