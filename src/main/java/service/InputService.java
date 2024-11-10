package service;

import domain.product.Product;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import utils.Parser;
import view.InputView;

public class InputService {
    private static final Pattern PATTERN = Pattern.compile("\\[([^\\[\\]-]+)-(\\d+)\\]");
    private static final String ERROR_MESSAGE = "[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.";

    private InputView inputView;

    public InputService(final InputView inputView) {
        this.inputView = inputView;
    }

    public String[] inputOrder() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        String input = inputView.input();
        return validateAndParseInput(input);
    }

    public String inputPromotionApplied(final Product product) {
        System.out.printf("현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)%n", product.getName());
        return inputYesNo();
    }

    public String inputRegularPricePaymentOption(final Product product) {
        System.out.printf("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)%n",
                product.getName(), product.getQuantity());
        return inputYesNo();
    }

    public String inputMembershipDiscountApplied() {
        System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
        return inputYesNo();
    }

    public String inputAdditionalPurchaseOption() {
        System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
        return inputYesNo();
    }

    private String[] validateAndParseInput(final String input) {
        String[] items = Parser.splitInputByComma(input);
        for (String item : items) {
            if (!isValidItem(item.trim())) {
                throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
            }
        }
        return items;
    }

    private boolean isValidItem(String item) {
        Matcher matcher = PATTERN.matcher(item);
        return matcher.matches();
    }

    private String inputYesNo() {
        String input = inputView.input();
        if ("Y".equals(input) || "N".equals(input)) {
            return input;
        }
        throw new IllegalArgumentException(ERROR_MESSAGE);
    }
}
