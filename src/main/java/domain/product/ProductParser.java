package domain.product;

import constant.ErrorMessage;
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
        long productPrice = parseProductNumber(productElements.get(1));
        long quantity = parseProductNumber(productElements.get(2));
        String promotionName = productElements.get(3);

        Product.Builder productBuilder = new Product.Builder(productName, Quantity.from(quantity)).price(productPrice);
        addPromotionToBuilder(productBuilder, promotionName, promotions);
        return productBuilder.build();
    }

    private static List<String> parseProductElements(final String productInformation) {
        List<String> productElements = Parser.parseElements(productInformation);
        ProductValidator.validateElementsCount(productElements);
        ProductValidator.validateElementBlankOrNull(productElements);

        return productElements;
    }

    private static long parseProductNumber(final String number) {
        try {
            return Parser.parseNumber(number);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(ErrorMessage.INVALID_PRICE_OR_QUANTITY.getMessage());
        }
    }

    private static void addPromotionToBuilder(final Product.Builder productBuilder, final String promotionName,
                                              final Promotions promotions) {
        if (!promotionName.isBlank() && !"null".equalsIgnoreCase(promotionName)) {
            productBuilder.promotion(promotions.findPromotionByName(promotionName));
        }
    }
}
