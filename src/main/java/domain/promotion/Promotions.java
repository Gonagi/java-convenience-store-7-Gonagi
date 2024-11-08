package domain.promotion;

import domain.utils.FileUtils;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;

public class Promotions {
    private final List<Promotion> promotions;

    private Promotions(final List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public static List<Promotion> from(final String filePath) throws FileNotFoundException {
        List<String> promotions = readFromPromotionFiles(filePath);

        return promotions.stream()
                .skip(1)
                .map(PromotionParser::createPromotionByFile)
                .toList();
    }

    private static List<String> readFromPromotionFiles(final String filePath) throws FileNotFoundException {
        try {
            return FileUtils.readLinesFromFile(filePath);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("[ERROR] 프로모션 파일을 찾을 수 없습니다.");
        }
    }

    public Promotion findPromotionByName(final String name) {
        return promotions.stream()
                .filter(promotion -> name.equals(promotion.getName()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("[ERROR] 일치하는 프로모션이 없습니다."));
    }
}
