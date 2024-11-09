package store.model.entity;

import java.time.LocalDate;
import store.reader.parser.Parsable;

public class Promotion implements ConvenienceEntity, Parsable {

    private final String name;
    private final int buyQuantity;
    private final int oneCycleQuantity;
    private final LocalDate start;
    private final LocalDate end;

    public Promotion(String name, int buyQuantity, int freeQuantity, LocalDate start, LocalDate end) {
        this.name = name;
        this.buyQuantity = buyQuantity;
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

    public boolean availableGetOneMoreForFree(final int unDiscountedQuantity) {
        return unDiscountedQuantity == buyQuantity;
    }

    @Override
    public String toString() {
        return name;
    }
}
