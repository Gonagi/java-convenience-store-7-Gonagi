package config;

import controller.Controller;
import domain.inventory.Inventory;
import domain.product.Products;
import domain.product.ProductsFactory;
import domain.promotion.Promotions;
import java.io.FileNotFoundException;
import receipt.Receipt;
import service.InputService;
import service.InventoryService;
import service.PromotionService;
import service.ReceiptService;
import view.InputView;
import view.OutputView;

public class AppConfig {


    private Promotions promotions;
    private Products products;
    private Inventory inventory;

    public AppConfig() {
        initializeInventory();
    }

    public Controller controller() {
        return new Controller(inputView(), inputService(), outputView(), inventory, inventoryService(),
                receiptService(), promotionService());
    }

    public InputView inputView() {
        return new InputView();
    }

    public InputService inputService() {
        return new InputService(inputView());
    }

    public InventoryService inventoryService() {
        return new InventoryService(inventory); // inventory는 항상 초기화된 상태로 전달됨
    }

    public ReceiptService receiptService() {
        return new ReceiptService(new Receipt());
    }

    public PromotionService promotionService() {
        return new PromotionService(inputService());
    }

    public OutputView outputView() {
        return new OutputView();
    }

    private void initializeInventory() {
        String promotionFilePath = "src/main/resources/promotions.md";
        String productFilePath = "src/test/resources/products.md";

        try {
            promotions = Promotions.from(promotionFilePath);
            products = ProductsFactory.createProductsByFile(productFilePath, promotions);
            inventory = Inventory.from(products);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("[ERROR] 파일을 찾을 수 없습니다: " + e.getMessage(), e);
        }
    }
}
