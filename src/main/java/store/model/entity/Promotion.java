package store.model.entity;

import java.time.LocalDate;

public class Promotion implements ConvenienceEntity {

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

    public int calcFreeQuantity(final int purchaseQuantity) {
        return purchaseQuantity / oneCycleQuantity;
    }

    public int calcCurAppliedQuantity(final int purchaseQuantity) {
        return (purchaseQuantity / oneCycleQuantity) * oneCycleQuantity;
    }

    public boolean promotionIfPurchaseOneMore(final int unDiscountedQuantity) {
        return unDiscountedQuantity == buyQuantity;
    }

    @Override
    public String toString() {
        return name;
    }
}
