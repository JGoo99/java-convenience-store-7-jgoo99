package store.model;

import java.time.LocalDate;

public class Promotion {

    private final String name;
    private final int buyQuantity;
    private final int freeQuantity;
    private final int oneCycleQuantity;
    private final LocalDate start;
    private final LocalDate end;

    public Promotion(String name, int buyQuantity, int freeQuantity, LocalDate start, LocalDate end) {
        this.name = name;
        this.buyQuantity = buyQuantity;
        this.freeQuantity = freeQuantity;
        this.oneCycleQuantity = buyQuantity + freeQuantity;
        this.start = start;
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public boolean withinPeriod(LocalDate date) {
        return withinStartDate(date) && withinEndDate(date);
    }

    private boolean withinEndDate(LocalDate date) {
        return date.isBefore(end.plusDays(1));
    }

    private boolean withinStartDate(LocalDate date) {
        return date.isAfter(start.minusDays(1));
    }

    public long calcFreeQuantity(long buyQuantity) {
        return buyQuantity / oneCycleQuantity;
    }

    public long calcCurAppliedQuantity(long buyQuantity) {
        return (buyQuantity / oneCycleQuantity) * oneCycleQuantity;
    }

    public boolean promotionIfPurchaseOneMore(long unDiscountedQuantity) {
        return unDiscountedQuantity == buyQuantity;
    }

    @Override
    public String toString() {
        return name;
    }
}
