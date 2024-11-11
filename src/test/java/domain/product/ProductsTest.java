package domain.product;

import constant.ErrorMessage;
import domain.product.Product.Builder;
import domain.promotion.Promotion;
import domain.promotion.Promotions;
import java.io.FileNotFoundException;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Parser;

class ProductsTest {
    Promotions promotions;

    @BeforeEach
    void setUp() {
        String filePath = "src/test/resources/promotions.md";
        try {
            promotions = Promotions.from(filePath);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(ErrorMessage.PROMOTION_FILE_NOT_FOUND.getMessage());
        }
    }

    @Test
    void 같은_상품이_존재해서는_안된다() {
        Product product = new Builder("사이다", Quantity.from(1)).build();
        Product sameProduct = new Builder("사이다", Quantity.from(2)).build();

        org.assertj.core.api.Assertions.assertThatThrownBy(
                        () -> ProductsFactory.createProductsByProducts(Set.of(product, sameProduct)))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    void 잘못된_파일_경로가_주어지면_예외가_발생해야_한다() {
        String wrongFilePath = "wrongFilePath";

        Assertions.assertThatThrownBy(() -> ProductsFactory.createProductsByFile(wrongFilePath, promotions))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 파일경로로_상품_파일을_읽어온다() {
        String productFilePath = "src/test/resources/products.md";
        Product product1 = new Builder("콜라", Quantity.from(10))
                .price(1000)
                .promotion(Promotion.of("탄산2+1", 2, 1, Parser.parseDate("2024-01-01"), Parser.parseDate("2024-12-31")))
                .build();
        Product product2 = new Builder("콜라", Quantity.from(10)).price(1000).build();
        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> {
            Products products = ProductsFactory.createProductsByFile(productFilePath, promotions);
            Assertions.assertThat(products.getProducts()).contains(product1, product2);
        });
    }
}
