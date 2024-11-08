package domain;

import domain.Product.Builder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ProductTest {
    @Test
    void 재고수량이_달라도_상품명이_같으면_같은_상품이다() {
        Product product = new Builder("사이다", Quantity.from(1)).build();
        Product sameProduct = new Builder("사이다", Quantity.from(2)).build();
        Product differentProduct = new Builder("콜라", Quantity.from(1)).build();

        Assertions.assertThat(product)
                .isEqualTo(sameProduct);

        Assertions.assertThat(product)
                .isNotEqualTo(differentProduct);
    }
}
