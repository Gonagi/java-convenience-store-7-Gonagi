package domain.product;

import domain.promotion.Promotions;
import java.util.List;
import utils.Parser;

public class ProductParser {
    public static Product createProductByParser(final String productInformation, final Promotions promotions) {
        List<String> productElements = parseProductElements(productInformation);
        return createProductByElements(productElements, promotions);
    }

    private static Product createProductByElements(final List<String> productElements, final Promotions promotions) {
        String productName = productElements.get(0);
        int productPrice = parseProductNumber(productElements.get(1));
        int quantity = parseProductNumber(productElements.get(2));
        String promotionName = productElements.get(3);
        Product.Builder productBuilder = new Product.Builder(productName, Quantity.from(quantity))
                .price(productPrice);

        if (!promotionName.isBlank() && !"null".equalsIgnoreCase(promotionName)) {
            productBuilder.promotion(promotions.findPromotionByName(promotionName));
        }

        return productBuilder.build();
//        return new Builder(productName, Quantity.from(quantity))
//                .price(productPrice)
//                .promotion(promotions.findPromotionByName(promotionName))
//                .build();
    }

    private static List<String> parseProductElements(final String productInformation) {
        List<String> productElements = Parser.parseElements(productInformation);
        ProductValidator.validateElementsCount(productElements);
        ProductValidator.validateElementBlankOrNull(productElements);

        return productElements;
    }

    private static int parseProductNumber(final String number) {
        try {
            return Parser.parseNumber(number);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("[ERROR] price, quantity 값들 중 유효하지 않은 값이 있습니다.");
        }
    }
}
