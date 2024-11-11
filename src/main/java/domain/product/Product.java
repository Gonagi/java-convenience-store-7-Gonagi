package domain.product;

import camp.nextstep.edu.missionutils.DateTimes;
import domain.promotion.Promotion;
import java.time.LocalDate;
import java.util.Objects;

public class Product {
    private final String name;
    private final int price;
    private final Quantity quantity;
    private final Promotion promotion;

    private Product(final Builder builder) {
        this.name = builder.name;
        this.price = builder.price;
        this.quantity = builder.quantity;
        this.promotion = builder.promotion;
    }

    public void reduceProductStock(final int purchaseQuantity) {
        quantity.reduceQuantity(purchaseQuantity);
    }

    public boolean isSameName(final String productName) {
        return name.equals(productName);
    }

    public boolean isPromotion() {
        return promotion != null;
    }

    public boolean checkPromotionDate() {
        LocalDate promotionStartDate = promotion.getStartDate();
        LocalDate promotionEndDate = promotion.getEndDate();
        LocalDate now = DateTimes.now().toLocalDate();

        return isWithinPromotionPeriod(now, promotionStartDate, promotionEndDate);
    }

    private boolean isWithinPromotionPeriod(final LocalDate date, final LocalDate startDate, final LocalDate endDate) {
        return isDateRangeValid(startDate, endDate)
                && (date.isEqual(startDate) || date.isEqual(endDate)
                || (date.isAfter(startDate) && date.isBefore(endDate)));
    }

    private boolean isDateRangeValid(final LocalDate startDate, final LocalDate endDate) {
        return startDate != null && endDate != null && !startDate.isAfter(endDate);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getPromotionName() {
        return promotion.getName();
    }

    public int getQuantity() {
        return quantity.getQuantity();
    }

    public int getPromotionBuy() {
        return promotion.getBuy();
    }

    public int getPromotionGet() {
        return promotion.getGet();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Product otherProduct = (Product) obj;
        return Objects.equals(name, otherProduct.name) &&
                Objects.equals(promotion, otherProduct.promotion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, promotion);
    }

    public static class Builder {
        String name;
        int price;
        Quantity quantity;
        Promotion promotion;

        public Builder(final String name, final Quantity quantity) {
            this.name = name;
            this.quantity = quantity;
        }

        public Builder price(final int price) {
            this.price = price;
            return this;
        }

        public Builder promotion(final Promotion promotion) {
            this.promotion = promotion;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
