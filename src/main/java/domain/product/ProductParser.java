package domain.product;

import domain.product.Product.Builder;
import domain.promotion.Promotions;
import java.util.Arrays;
import java.util.List;

public class ProductParser {
    private static final String COMMA = ",";

    public static Product from(final String productInformation, final Promotions promotions) {
        List<String> productElements = parseElements(productInformation);
        String productName = productElements.get(0);
        int productPrice = parseNumber(productElements.get(1));
        int quantity = parseNumber(productElements.get(2));
        String promotionName = productElements.get(3);

        return new Builder(productName, Quantity.from(quantity))
                .price(productPrice)
                .promotion(promotions.findPromotionByName(promotionName))
                .build();
    }

    private static List<String> parseElements(final String productInformation) {
        List<String> productElements = Arrays.asList(productInformation.split(COMMA));
        ProductValidator.validateElementsCount(productElements);
        ProductValidator.validateElementBlankOrNull(productElements);

        return productElements;
    }

    private static int parseNumber(final String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("[ERROR] price, quantity 값들 중 유효하지 않은 값이 있습니다.");
        }
    }

}
