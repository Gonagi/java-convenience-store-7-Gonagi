package domain.receipt;

public class Amount {
    private long totalPurchaseAmount;
    private long promotionDiscount;
    private long membershipDiscount;
    private long finalPaymentAmount;

    public Amount() {
        this.totalPurchaseAmount = 0;
        this.promotionDiscount = 0;
        this.membershipDiscount = 0;
        this.finalPaymentAmount = 0;
    }

    public void addTotalPurchaseAmount(final long amount) {
        this.totalPurchaseAmount += amount;
    }

    public void addPromotionDiscount(final long discount) {
        this.promotionDiscount += discount;
    }

    public void calculateMembershipDiscount() {
        long nonPromotionAmount = totalPurchaseAmount - promotionDiscount;
        long calculatedDiscount = (long) (nonPromotionAmount * 0.3);

        if (calculatedDiscount > 8000) {
            this.membershipDiscount = 8000;
            return;
        }
        this.membershipDiscount = calculatedDiscount;
    }

    public void calculateFinalPaymentAmount() {
        this.finalPaymentAmount = totalPurchaseAmount - promotionDiscount - membershipDiscount;
    }

    public long getTotalPurchaseAmount() {
        return totalPurchaseAmount;
    }

    public long getPromotionDiscount() {
        return promotionDiscount;
    }

    public long getMembershipDiscount() {
        return membershipDiscount;
    }

    public long getFinalPaymentAmount() {
        return finalPaymentAmount;
    }
}
