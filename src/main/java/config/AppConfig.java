package config;

import controller.Controller;
import domain.inventory.Inventory;
import domain.product.Products;
import domain.product.ProductsFactory;
import domain.promotion.Promotions;
import domain.receipt.Receipt;
import java.io.FileNotFoundException;
import service.InputService;
import service.InventoryService;
import service.OutputService;
import service.PromotionService;
import service.ReceiptService;
import view.InputView;
import view.OutputView;

public class AppConfig {
    private static final String PROMOTION_FILE_PATH = "src/main/resources/promotions.md";
    private static final String PRODUCT_FILE_PATH = "src/test/resources/products.md";
    private final Promotions promotions;
    private final Products products;
    private final Inventory inventory;

    public AppConfig() {
        this.promotions = loadPromotions();
        this.products = loadProducts(promotions);
        this.inventory = Inventory.from(products);
    }

    public Controller controller() {
        return new Controller(inputService(), outputService(),
                inventoryService(), receiptService(),
                promotionService());
    }

    public InputService inputService() {
        return new InputService(new InputView());
    }

    public OutputService outputService() {
        return new OutputService(new OutputView());
    }

    public InventoryService inventoryService() {
        return new InventoryService(inventory);
    }

    public PromotionService promotionService() {
        return new PromotionService(inputService());
    }

    public ReceiptService receiptService() {
        return new ReceiptService(new Receipt());
    }

    private Promotions loadPromotions() {
        try {
            return Promotions.from(PROMOTION_FILE_PATH);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("[ERROR] Promotions 파일을 찾을 수 없습니다: " + e.getMessage(), e);
        }
    }

    private Products loadProducts(Promotions promotions) {
        try {
            return ProductsFactory.createProductsByFile(PRODUCT_FILE_PATH, promotions);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("[ERROR] Products 파일을 찾을 수 없습니다: " + e.getMessage(), e);
        }
    }
}
