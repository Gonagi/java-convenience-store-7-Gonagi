package domain.inventory;

import constant.ErrorMessage;
import domain.product.Product;
import domain.product.Product.Builder;
import domain.product.Products;
import domain.product.ProductsFactory;
import domain.product.Quantity;
import domain.promotion.Promotion;
import domain.promotion.Promotions;
import java.io.FileNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Parser;

class InventoryTest {
    Promotions promotions;
    Products products;
    Inventory inventory;

    @BeforeEach
    void setUp() {
        String promotionFilePath = "src/test/resources/promotions.md";
        String productFilePath = "src/test/resources/products.md";

        try {
            promotions = Promotions.from(promotionFilePath);
            products = ProductsFactory.createProductsByFile(productFilePath, promotions);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(ErrorMessage.PROMOTION_FILE_NOT_FOUND.getMessage());
        }
        inventory = Inventory.from(products);
    }

    @Test
    void 존재하지_않는_상품은_구매할_수_없다() {
        Product purchaseProduct = new Builder("민트초코", Quantity.from(1)).build();

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> {
                    inventory.findProductByNameAndPromotion(purchaseProduct);
                })
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 프로모션이나_일반상품_중_하나만_존재해도_제품을_찾을때_예외가_발생하지_않는다() {
        Promotion mdRecommendPromotion = Promotion.of("MD추천상품", 1, 1,
                Parser.parseDate("2024-01-01"), Parser.parseDate("2024-12-31"));
        Product orangeJuice = new Builder("오렌지주스", Quantity.from(1)).promotion(mdRecommendPromotion).build();
        Product water = new Builder("물", Quantity.from(1)).build();

        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> {
            inventory.findProductByNameAndPromotion(orangeJuice);
            inventory.findProductByNameAndPromotion(water);
        });
    }
}
