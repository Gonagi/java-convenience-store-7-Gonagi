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

    public void reduceProductStock(final Product product, final int purchaseQuantity) {
        product.reduceProductStock(purchaseQuantity);
    }
}
