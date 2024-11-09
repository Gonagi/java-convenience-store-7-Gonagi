package service;

import domain.inventory.Inventory;
import domain.product.Product;
import domain.product.Product.Builder;
import domain.product.Products;
import domain.product.ProductsFactory;
import domain.product.Quantity;
import domain.promotion.Promotion;
import domain.promotion.Promotions;
import java.io.FileNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Parser;

class InventoryServiceTest {
    Promotions promotions;
    Promotion twoPlusOne;
    Products products;
    InventoryService inventoryService;
    Inventory inventory;

    @BeforeEach
    void setUp() {
        String promotionFilePath = "src/test/resources/promotions.md";
        String productFilePath = "src/test/resources/products.md";

        try {
            promotions = Promotions.from(promotionFilePath);
            products = ProductsFactory.createProductsByFile(productFilePath, promotions);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        inventory = Inventory.from(products);
        inventoryService = new InventoryService(inventory);

        twoPlusOne = Promotion.of("탄산2+1", 2, 1,
                Parser.parseDate("2024-01-01"), Parser.parseDate("2024-12-31"));
    }

    @Test
    void 일반상품의_인밴토리를_업데이트한다() {
        Product regularCoke = new Builder("콜라", Quantity.from(5)).build();

        inventoryService.updateInventoryForRegular(regularCoke);

        Product findProduct = inventory.findProductByNameAndPromotion(regularCoke);
        Assertions.assertThat(findProduct.getQuantity()).isEqualTo(5);
    }

    @Test
    void 프로모션상품의_인밴토리를_업데이트한다() {
        Product promotionCocke = new Builder("콜라", Quantity.from(5)).promotion(twoPlusOne).build();

        inventoryService.updateInventoryForRegular(promotionCocke);

        Product findProduct = inventory.findProductByNameAndPromotion(promotionCocke);
        Assertions.assertThat(findProduct.getQuantity()).isEqualTo(5);

    }

    @Test
    void 일반상품에서_재고_이상의_수량을_초과하여_구매할_수_없다() {
        Product purchaseProduct = new Builder("물", Quantity.from(100)).build();

        org.assertj.core.api.Assertions.assertThatThrownBy(
                        () -> inventoryService.updateInventoryForPromotion(purchaseProduct))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 프로모션상품에서_재고_이상의_수량을_초과하여_구매할_수_없다() {
        Product purchaseProduct = new Builder("콜라", Quantity.from(100)).promotion(twoPlusOne).build();

        org.assertj.core.api.Assertions.assertThatThrownBy(
                        () -> inventoryService.updateInventoryForPromotion(purchaseProduct))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
