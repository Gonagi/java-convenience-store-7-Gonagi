package domain.product;

import constant.ErrorMessage;
import domain.promotion.Promotions;
import java.io.FileNotFoundException;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import utils.FileUtils;
import utils.Parser;

public class ProductsFactory {
    public static Products createProductsByFile(final String productFilePath, final Promotions promotions)
            throws FileNotFoundException {
        List<String> readFromProductFiles = readFromProductFiles(productFilePath);
        List<Product> productsWithPromotions = parseProductsWithPromotions(readFromProductFiles, promotions);
        Set<Product> allProducts = addMissingNonPromotionProducts(productsWithPromotions);

        return new Products(allProducts);
    }

    public static Products createProductsByProducts(final Set<Product> products) {
        return new Products(products);
    }

    public static Products createProductsByInput(final String[] inputOrders) {
        Set<Product> products = new LinkedHashSet<>();
        for (String inputOrder : inputOrders) {
            products.add(createProduct(inputOrder));
        }
        return new Products(products);
    }

    private static List<String> readFromProductFiles(final String filePath) {
        try {
            return FileUtils.readLinesFromFile(filePath);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(ErrorMessage.PRODUCT_FILE_NOT_FOUND.getMessage());
        }
    }

    private static List<Product> parseProductsWithPromotions(final List<String> productFileLines,
                                                             final Promotions promotions) {
        return productFileLines.stream()
                .skip(1)
                .map(line -> ProductParser.createProductByParser(line, promotions))
                .toList();
    }

    private static Set<Product> addMissingNonPromotionProducts(final List<Product> productsWithPromotions) {
        List<Product> result = new LinkedList<>();
        for (Product product : productsWithPromotions) {
            result.add(product);
            if (product.isPromotion()) {
                Product regularProduct = createRegularProductIfNeeded(product, productsWithPromotions);
                addRegularProductIfAbsent(result, regularProduct);
            }
        }
        return new LinkedHashSet<>(result);
    }

    private static Product createRegularProductIfNeeded(final Product product,
                                                        final List<Product> productsWithPromotions) {
        Product regularProduct = new Product.Builder(product.getName(), Quantity.from(0))
                .price(product.getPrice()).build();
        return (productsWithPromotions.contains(regularProduct)) ? null : regularProduct;
    }

    private static void addRegularProductIfAbsent(final List<Product> result, final Product regularProduct) {
        if (regularProduct != null) {
            result.add(regularProduct);
        }
    }

    private static Product createProduct(String inputOrder) {
        String[] split = inputOrder.split("-");
        String name = Parser.removeFrontBracket(split[0]);
        long quantity = Parser.parseNumber(Parser.removeBackBracket(split[1]));
        return new Product.Builder(name, Quantity.from(quantity)).build();
    }
}
