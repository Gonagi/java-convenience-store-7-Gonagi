package receipt;

public class Amount {
    private int totalPurchaseAmount;
    private int promotionDiscount;
    private int membershipDiscount;
    private int finalPaymentAmount;

    public Amount() {
        this.totalPurchaseAmount = 0;
        this.promotionDiscount = 0;
        this.membershipDiscount = 0;
        this.finalPaymentAmount = 0;
    }

    public void addTotalPurchaseAmount(final int amount) {
        this.totalPurchaseAmount += amount;
    }

    public void addPromotionDiscount(final int discount) {
        this.promotionDiscount += discount;
    }

    public void calculateMembershipDiscount() {
        int nonPromotionAmount = totalPurchaseAmount - promotionDiscount;
        int calculatedDiscount = (int) (nonPromotionAmount * 0.3);

        if (calculatedDiscount > 8000) {
            this.membershipDiscount = 8000;
            return;
        }
        this.membershipDiscount = calculatedDiscount;
    }

    public void calculateFinalPaymentAmount() {
        this.finalPaymentAmount = totalPurchaseAmount - promotionDiscount - membershipDiscount;
    }

    public int getTotalPurchaseAmount() {
        return totalPurchaseAmount;
    }

    public int getPromotionDiscount() {
        return promotionDiscount;
    }

    public int getMembershipDiscount() {
        return membershipDiscount;
    }

    public int getFinalPaymentAmount() {
        return finalPaymentAmount;
    }
}
