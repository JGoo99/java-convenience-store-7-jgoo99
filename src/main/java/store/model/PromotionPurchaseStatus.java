package store.model;

public record PromotionPurchaseStatus(long buyQ, boolean isOverQ, long unAppliedQ, long appliedQ, long freeQ) {

    @Override
    public long buyQ() {
        return buyQ;
    }

    @Override
    public boolean isOverQ() {
        return isOverQ;
    }

    @Override
    public long unAppliedQ() {
        return unAppliedQ;
    }

    @Override
    public long appliedQ() {
        return appliedQ;
    }

    @Override
    public long freeQ() {
        return freeQ;
    }
}
