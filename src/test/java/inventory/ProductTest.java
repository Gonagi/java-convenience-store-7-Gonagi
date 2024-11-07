package inventory;

import domain.Product;
import domain.Stock;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ProductTest {
    @Test
    void 재고수량이_달라도_상품명이_같으면_같은_상품이다() {
        Product product = Product.of("사이다", Stock.from(1));
        Product sameProduct = Product.of("사이다", Stock.from(2));
        Product differentProduct = Product.of("뉴사이다", Stock.from(1));

        Assertions.assertThat(product)
                .isEqualTo(sameProduct);
        
        Assertions.assertThat(product)
                .isNotEqualTo(differentProduct);
    }

}
