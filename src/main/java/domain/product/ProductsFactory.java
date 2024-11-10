package domain.product;

import domain.promotion.Promotions;
import java.io.FileNotFoundException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import utils.FileUtils;
import utils.Parser;

public class ProductsFactory {
    public static Products createProductsByFile(final String productFilePath, final Promotions promotions)
            throws FileNotFoundException {
        List<String> readFromProductFiles = readFromProductFiles(productFilePath);

        Set<Product> products = readFromProductFiles.stream()
                .skip(1)
                .map(product -> ProductParser.createProductByParser(product, promotions))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        return new Products(products);
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

    private static List<String> readFromProductFiles(final String filePath) throws FileNotFoundException {
        try {
            return FileUtils.readLinesFromFile(filePath);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("[ERROR] 상품 파일을 찾을 수 없습니다.");
        }
    }

    private static Product createProduct(String inputOrder) {
        String[] split = inputOrder.split("-");
        String name = Parser.removeFrontBracket(split[0]);
        int quantity = Parser.parseNumber(Parser.removeBackBracket(split[1]));
        return new Product.Builder(name, Quantity.from(quantity)).build();
    }
}
