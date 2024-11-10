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
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import utils.Parser;

class InventoryServiceTest {
    Promotions promotions;
    Promotion twoPlusOne;
    Products products;
    InventoryService inventoryService;
    Inventory inventory;

    private static Stream<Arguments> updateInventoryTestCases() {
        return Stream.of(
                Arguments.of(new Builder("콜라", Quantity.from(5)).build(), 5),
                Arguments.of(new Builder("사이다", Quantity.from(3)).build(), 4)
        );
    }

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

    @MethodSource("updateInventoryTestCases")
    @ParameterizedTest(name = "상품: {0}, 예상재고: {1}")
    void 인밴토리를_업데이트한다(Product product, int expectedQuantity) {
        inventoryService.updateInventory(product);

        Product findProduct = inventory.findProductByNameAndPromotion(product);

        Assertions.assertThat(findProduct.getQuantity()).isEqualTo(expectedQuantity);
    }

    @Test
    void 일반상품에서_재고_이상의_수량을_초과하여_구매할_수_없다() {
        Product purchaseProduct = new Builder("물", Quantity.from(100)).build();
        Product findProduct = inventory.findProductByName(purchaseProduct);

        org.assertj.core.api.Assertions.assertThatThrownBy(
                        () -> inventoryService.checkRegularProductStockAvailability(findProduct, purchaseProduct))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 프로모션상품에서_재고_이상의_수량을_초과하여_구매할_수_없다() {
        Product purchaseProduct = new Builder("콜라", Quantity.from(100)).promotion(twoPlusOne).build();
        Product findProduct = inventory.findProductByName(purchaseProduct);

        org.assertj.core.api.Assertions.assertThatThrownBy(
                        () -> inventoryService.checkPromotionProductStockAvailability(findProduct, purchaseProduct))
                .isInstanceOf(IllegalArgumentException.class);
    }
}