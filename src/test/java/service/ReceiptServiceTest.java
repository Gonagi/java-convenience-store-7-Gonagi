package service;

import domain.product.Product;
import domain.product.Product.Builder;
import domain.product.Quantity;
import domain.promotion.Promotion;
import domain.receipt.Receipt;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Parser;

class ReceiptServiceTest {
    int requestQuantity = 3;
    Receipt receipt;
    ReceiptService receiptService;
    Product coke;
    Product cider;

    @BeforeEach
    void setUp() {
        receipt = new Receipt();
        receiptService = new ReceiptService(receipt);
        Promotion twoPlusOne = Promotion.of("탄산2+1", 2, 1,
                Parser.parseDate("2024-01-01"), Parser.parseDate("2024-12-31"));

        coke = new Builder("콜라", Quantity.from(10))
                .price(1000).build();
        cider = new Builder("사이다", Quantity.from(3))
                .price(2000).promotion(twoPlusOne).build();
    }

    @Test
    void 입력한_수량만큼_상품을_구매한다() {
        int expectedTotalPurchaseAmount = 3000;

        receiptService.addBuyingProducts(coke, requestQuantity);

        Assertions.assertThat(receipt.getTotalPurchaseAmount())
                .isEqualTo(expectedTotalPurchaseAmount);
    }

    @Test
    void 입력한_수량만큼_상품을_받는다() {
        int expectedPromotionDiscount = 6000;

        receiptService.addFreebieProduct(cider, requestQuantity);

        Assertions.assertThat(receipt.getPromotionDiscount())
                .isEqualTo(expectedPromotionDiscount);
    }

    @Test
    void 멤버십_할인을_적용한다() {
        receiptService.addBuyingProducts(coke, requestQuantity);
        receiptService.updateMembershipDiscount();

        int expectedMembershipDiscount = 900;

        Assertions.assertThat(receipt.getMembershipDiscount())
                .isEqualTo(expectedMembershipDiscount);
    }

    @Test
    void 최종_결제_금액을_확인한다() {
        receiptService.addBuyingProducts(coke, requestQuantity);
        receiptService.updateMembershipDiscount();
        receiptService.updateFinalPaymentAmount();
        int expectedFinalPaymentAmount = 2100;

        Assertions.assertThat(receipt.getFinalPaymentAmount())
                .isEqualTo(expectedFinalPaymentAmount);
    }
}
