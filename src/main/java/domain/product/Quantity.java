package domain.product;

import constant.ErrorMessage;

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
            throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT.getMessage());
        }
    }

    public void reduceQuantity(final long purchaseQuantity) {
        this.quantity -= purchaseQuantity;
    }

    public long getQuantity() {
        return quantity;
    }
}
