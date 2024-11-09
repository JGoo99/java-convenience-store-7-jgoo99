package store.constants;

public enum ReceiptFormat {

    NAME_QUANTITY_PRICE_FRAME("%-11s\t\t%-10s\t%s\n"),
    PURCHASED_ITEM("%-11s\t\t%-10d\t%-6s\n"),
    FREE_ITEM("%-11s\t\t%d\n"),
    AMOUNT("%-11s\t\t\t\t\t%s\n")
    ;

    private final String format;

    ReceiptFormat(String format) {
        this.format = format;
    }

    @Override
    public String toString() {
        return format;
    }
}
