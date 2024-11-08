package domain.product;

import domain.product.Product.Builder;
import domain.promotion.Promotions;
import domain.utils.Parser;
import java.util.List;

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

        return new Builder(productName, Quantity.from(quantity))
                .price(productPrice)
                .promotion(promotions.findPromotionByName(promotionName))
                .build();
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
