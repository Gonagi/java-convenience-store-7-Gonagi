package service;

import domain.inventory.Inventory;
import domain.receipt.Receipt;
import view.OutputView;

public class OutputService {
    private final OutputView outputView;

    public OutputService(OutputView outputView) {
        this.outputView = outputView;
    }

    public void printBeforeTransaction(final Inventory inventory) {
        outputView.printStartMessage();
        outputView.printInventory(inventory);
    }

    public void printAfterTransaction(final Receipt receipt) {
        outputView.printReceipt(receipt);
    }
}
