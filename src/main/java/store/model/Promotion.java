package store.model;

import java.time.LocalDate;

public class Promotion {

    private final String name;
    private final int buyQuantity;
    private final int getQuantity;
    private final LocalDate start;
    private final LocalDate end;

    public Promotion(String name, int buyQuantity, int getQuantity, LocalDate start, LocalDate end) {
        this.name = name;
        this.buyQuantity = buyQuantity;
        this.getQuantity = getQuantity;
        this.start = start;
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public boolean withinPeriod(LocalDate date) {
        return date.isAfter(start.minusDays(1)) && date.isBefore(end.plusDays(1));
    }

    public long calcQuantity(long quantity) {
        int unit = buyQuantity + getQuantity;
        return (quantity / unit) * unit;
    }

    public long calcFreeQuantity(long quantity) {
        return quantity / (buyQuantity + getQuantity);
    }

    public boolean needOneMoreQuantity(long quantity) {
        return quantity % (buyQuantity + getQuantity) == buyQuantity;
    }

    public boolean lessThanBuyCnt(long quantity) {
        return buyQuantity > quantity;
    }

    @Override
    public String toString() {
        return name;
    }
}
