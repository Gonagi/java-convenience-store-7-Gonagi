package receipt;

import domain.product.Product;
import domain.product.Product.Builder;
import domain.product.Quantity;
import domain.receipt.Receipt;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReceiptTest {
    Receipt receipt;
    Product coke;
    Product cider;

    @BeforeEach
    void setUp() {
        receipt = new Receipt();
        coke = new Builder("콜라", Quantity.from(10))
                .price(1000).build();
        cider = new Builder("사이다", Quantity.from(3))
                .price(2000).build();
    }

    @Test
    void 상품을_추가하고_총_구매_금액을_확인한다() {
        int expectedTotal = 16000;

        receipt.addBuyingProducts(coke);
        receipt.addBuyingProducts(cider);

        Assertions.assertThat(receipt.getTotalPurchaseAmount())
                .isEqualTo(expectedTotal);
    }

    @Test
    void 프로모션_상품을_추가하고_프로모션_할인_금액을_계산한다() {
        int expectedPromotionDiscount = 6000;

        receipt.addFreebieProducts(cider);

        Assertions.assertThat(receipt.getPromotionDiscount())
                .isEqualTo(expectedPromotionDiscount);
    }

    @Test
    void 멤버십_할인의_최대한도를_넘지않는_할인_금액을_계산합니다() {
        int expectedMembershipDiscount = 1200;

        receipt.addBuyingProducts(coke);
        receipt.addFreebieProducts(cider);
        receipt.calculateMembershipDiscount();

        Assertions.assertThat(receipt.getMembershipDiscount())
                .isEqualTo(expectedMembershipDiscount);
    }

    @Test
    void 멤버십_할인의_최대한도를_넘는_할인_금액을_계산합니다() {
        Product expensiveCoke = new Builder("콜라", Quantity.from(10))
                .price(10000).build();
        int expectedMembershipDiscount = 8000;

        receipt.addBuyingProducts(expensiveCoke);
        receipt.addFreebieProducts(cider);
        receipt.calculateMembershipDiscount();

        Assertions.assertThat(receipt.getMembershipDiscount())
                .isEqualTo(expectedMembershipDiscount);
    }

    @Test
    void 최종_결제_금액을_확인합니다() {
        int expectedMembershipDiscount = 4000;

        receipt.addBuyingProducts(coke);
        receipt.addFreebieProducts(cider);
        receipt.calculateFinalPaymentAmount();

        Assertions.assertThat(receipt.getFinalPaymentAmount())
                .isEqualTo(expectedMembershipDiscount);
    }
}
