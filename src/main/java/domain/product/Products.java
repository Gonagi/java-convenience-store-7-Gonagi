package domain.product;

import domain.promotion.Promotions;
import domain.utils.FileUtils;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;

public class Products {
    private final List<Product> products;

    private Products(final List<Product> products) {
        this.products = products;
    }

    public static Products of(final String productFilePath, final Promotions promotions)
            throws FileNotFoundException {
        List<String> readFromProductFiles = readFromProductFiles(productFilePath);

        List<Product> products = readFromProductFiles.stream()
                .skip(1)
                .map(product -> ProductParser.createProductByParser(product, promotions))
                .toList();

        return new Products(products);
    }

    private static List<String> readFromProductFiles(final String filePath) throws FileNotFoundException {
        try {
            return FileUtils.readLinesFromFile(filePath);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("[ERROR] 상품 파일을 찾을 수 없습니다.");
        }
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }
}
