package domain;

public class Stock {
    private final static int NO_STOCK = 0;
    private final int quantity;

    private Stock(final int quantity) {
        validateQuantity(quantity);
        this.quantity = quantity;
    }

    public static Stock from(final int quantity) {
        return new Stock(quantity);
    }

    private void validateQuantity(final int quantity) {
        if (quantity < NO_STOCK) {
            throw new IllegalArgumentException("[ERROR] 제품의 수량은 0 이상이어야 합니다.");
        }
    }
}
