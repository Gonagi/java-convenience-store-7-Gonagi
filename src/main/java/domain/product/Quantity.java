package domain.product;

public class Quantity {
    private final static long NO_STOCK = 0;
    private long quantity;

    private Quantity(final long quantity) {
        validateQuantity(quantity);
        this.quantity = quantity;
    }

    public static Quantity from(final long quantity) {
        return new Quantity(quantity);
    }

    private void validateQuantity(final long quantity) {
        if (quantity < NO_STOCK) {
            throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.\n");
        }
    }

    public void reduceQuantity(final long purchaseQuantity) {
        this.quantity -= purchaseQuantity;
    }

    public long getQuantity() {
        return quantity;
    }
}
