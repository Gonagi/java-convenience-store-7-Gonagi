package service;

import domain.inventory.Inventory;
import domain.product.Product;

public class InventoryService {
    private final Inventory inventory;

    public InventoryService(Inventory inventory) {
        this.inventory = inventory;
    }

    public void updateInventoryForRegular(final Product purchaseProduct) {
        Product findProduct = inventory.findProductByNameAndPromotion(purchaseProduct);
        checkRegularProductStockAvailability(findProduct, purchaseProduct);
        reduceProductStock(findProduct, purchaseProduct);
    }

    public void updateInventoryForPromotion(final Product purchaseProduct) {
        Product findProduct = inventory.findProductByNameAndPromotion(purchaseProduct);
        checkPromotionProductStockAvailability(findProduct, purchaseProduct);
        reduceProductStock(findProduct, purchaseProduct);
    }

    private void checkRegularProductStockAvailability(final Product findProduct, final Product purchaseProduct) {
        if (findProduct.getQuantity() < purchaseProduct.getQuantity()) {
            throw new IllegalArgumentException("[ERROR] 일반 상품 재고가 부족합니다.");
        }
    }

    private void checkPromotionProductStockAvailability(final Product findProduct, final Product purchaseProduct) {
        if (findProduct.getQuantity() < purchaseProduct.getQuantity()) {
            throw new IllegalArgumentException("[ERROR] 프로모션 상품 재고가 부족합니다.");
        }
    }

    private void reduceProductStock(final Product product, final Product purchaseProduct) {
        product.reduceProductStock(purchaseProduct.getQuantity());
    }
}
