package domain.product;

public class Quantity {
    private final static int NO_STOCK = 0;
    private int quantity;

    private Quantity(final int quantity) {
        validateQuantity(quantity);
        this.quantity = quantity;
    }

    public static Quantity from(final int quantity) {
        return new Quantity(quantity);
    }

    private void validateQuantity(final int quantity) {
        if (quantity < NO_STOCK) {
            throw new IllegalArgumentException("[ERROR] 제품의 수량은 0 이상이어야 합니다.");
        }
    }

    public void reduceQuantity(final int purchaseQuantity) {
        this.quantity -= purchaseQuantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
