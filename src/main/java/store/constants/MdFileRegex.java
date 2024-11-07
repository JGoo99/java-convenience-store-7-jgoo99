package store.constants;

public enum MdFileRegex {

    PRODUCT_NAME("([가-힣]+)"),
    PROMOTION_NAME("([가-힣a-zA-Z0-9+]+)"),
    LONG("(\\d+)"),
    DATE("(\\d{4}-\\d{2}-\\d{2})");

    private final String regex;

    MdFileRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public String toString() {
        return regex;
    }
}
