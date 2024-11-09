package domain.product;

import domain.promotion.Promotions;
import java.io.FileNotFoundException;
import java.util.List;
import utils.FileUtils;

public class ProductsFactory {
    public static Products createProductsByFile(final String productFilePath, final Promotions promotions)
            throws FileNotFoundException {
        List<String> readFromProductFiles = readFromProductFiles(productFilePath);

        List<Product> products = readFromProductFiles.stream()
                .skip(1)
                .map(product -> ProductParser.createProductByParser(product, promotions))
                .toList();

        return new Products(products);
    }

    public static Products createProductsByProducts(final List<Product> products) {
        return new Products(products);
    }

    private static List<String> readFromProductFiles(final String filePath) throws FileNotFoundException {
        try {
            return FileUtils.readLinesFromFile(filePath);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("[ERROR] 상품 파일을 찾을 수 없습니다.");
        }
    }
}
