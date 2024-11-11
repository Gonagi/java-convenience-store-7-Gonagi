package view;

import domain.inventory.Inventory;
import domain.product.Product;
import domain.receipt.Receipt;

public class OutputView {
    public void printStartMessage() {
        System.out.println("안녕하세요. W편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.");
        System.out.println();
    }

    public void printInventory(final Inventory inventory) {
        inventory.getProducts().forEach(this::printProductInfo);
        System.out.println();
    }

    public void printReceipt(final Receipt receipt) {
        printReceiptHeader();
        receipt.getBuyingProducts().forEach(this::printProductPurchaseInfo);
        printReceiptPromotionSection(receipt);
        printReceiptSummary(receipt);
    }

    private void printProductInfo(Product product) {
        System.out.printf("- %s %,d원 %s %s\n",
                product.getName(),
                product.getPrice(),
                getQuantityInfo(product),
                getPromotionName(product));
    }

    private String getQuantityInfo(Product product) {
        if (product.getQuantity() == 0) {
            return "재고 없음";
        }
        return product.getQuantity() + "개";
    }

    private String getPromotionName(Product product) {
        if (product.isPromotion()) {
            return product.getPromotionName();
        }
        return "";
    }

    private void printReceiptHeader() {
        System.out.println("==============W 편의점================");
        System.out.println("상품명\t\t수량\t금액");
    }

    private void printProductPurchaseInfo(Product product) {
        System.out.printf("%s\t\t%d \t%,d\n",
                product.getName(),
                product.getQuantity(),
                product.getQuantity() * product.getPrice());
    }

    private void printReceiptPromotionSection(final Receipt receipt) {
        System.out.println("=============증\t정===============");
        receipt.getFreebieProducts().forEach(this::printProductFreebieInfo);
        System.out.println("====================================");
    }

    private void printProductFreebieInfo(Product product) {
        System.out.printf("%s\t\t%d\n", product.getName(), product.getQuantity());
    }

    private void printReceiptSummary(final Receipt receipt) {
        System.out.printf("총구매액\t\t%d\t%,d\n", receipt.getBuyingProducts().size(), receipt.getTotalPurchaseAmount());
        System.out.printf("행사할인\t\t\t-%,d\n", receipt.getPromotionDiscount());
        System.out.printf("멤버십할인\t\t\t-%,d\n", receipt.getMembershipDiscount());
        System.out.printf("내실돈\t\t\t %,d\n", receipt.getFinalPaymentAmount());
    }
}
