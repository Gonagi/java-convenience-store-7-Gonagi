package domain.product;

import domain.product.Product.Builder;
import domain.promotion.Promotion;
import domain.utils.Parser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ProductTest {
    @Test
    void 재고수량이_달라도_상품명과_프로모션이_같으면_같은_상품이다() {
        Product product = new Builder("사이다", Quantity.from(1)).build();
        Product sameProduct = new Builder("사이다", Quantity.from(2)).build();
        Product differentProduct = new Builder("콜라", Quantity.from(1)).build();

        Assertions.assertThat(product)
                .isEqualTo(sameProduct);

        Assertions.assertThat(product)
                .isNotEqualTo(differentProduct);
    }

    @Test
    void 프로모션이_다르면_다른_상품이다() {
        Promotion promotion1 = Promotion.of("탄산2+1", 2, 1,
                Parser.parseDate("2024-01-01"), Parser.parseDate("2024-12-31"));
        Promotion promotion2 = Promotion.of("MD추천상품", 1, 1,
                Parser.parseDate("2024-01-01"), Parser.parseDate("2024-12-31"));

        Product product = new Builder("사이다", Quantity.from(1)).promotion(promotion1).build();
        Product differentProduct = new Builder("사이다", Quantity.from(2)).promotion(promotion2).build();

        Assertions.assertThat(product)
                .isNotEqualTo(differentProduct);
    }
}
