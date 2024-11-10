package store.constants;

public enum ReceiptFormat {

    ITEM_FRAME_LINE("%-11s\t\t%-10s\t%s\n"),
    ITEM_LINE("%-11s\t\t%-10d\t%-6s\n"),
    FREE_ITEM_LINE("%-11s\t\t%d\n"),
    AMOUNT_LINE("%-11s\t\t\t\t\t%s\n");

    private final String format;

    ReceiptFormat(String format) {
        this.format = format;
    }

    @Override
    public String toString() {
        return format;
    }
}
