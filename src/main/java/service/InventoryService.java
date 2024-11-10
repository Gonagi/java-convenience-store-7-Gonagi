package service;

import domain.inventory.Inventory;
import domain.product.Product;

public class InventoryService {
    private final Inventory inventory;

    public InventoryService(final Inventory inventory) {
        this.inventory = inventory;
    }

    public void updateInventory(final Product purchaseProduct) {
        Product findProduct = inventory.findProductByNameAndPromotion(purchaseProduct);
        reduceProductStock(findProduct, purchaseProduct.getQuantity());
    }

    public void checkRegularProductStockAvailability(final Product findProduct, final Product purchaseProduct) {
        if (findProduct.getQuantity() < purchaseProduct.getQuantity()) {
            throw new IllegalArgumentException("[ERROR] 일반 상품 재고가 부족합니다.");
        }
    }

    public void checkPromotionProductStockAvailability(final Product findProduct, final Product purchaseProduct) {
        if (findProduct.getQuantity() < purchaseProduct.getQuantity()) {
            throw new IllegalArgumentException("[ERROR] 프로모션 상품 재고가 부족합니다.");
        }
    }

    public void reduceProductStock(final Product product, final int purchaseQuantity) {
        product.reduceProductStock(purchaseQuantity);
    }
}
