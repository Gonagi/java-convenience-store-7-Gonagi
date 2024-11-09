package domain.promotion;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import utils.FileUtils;

public class Promotions {
    private final List<Promotion> promotions;

    private Promotions(final List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public static Promotions from(final String filePath) throws FileNotFoundException {
        List<String> readFromPromotionFiles = readFromPromotionFiles(filePath);

        List<Promotion> promotions = readFromPromotionFiles.stream()
                .skip(1)
                .map(PromotionParser::createPromotionByParser)
                .toList();

        return new Promotions(promotions);
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
                .orElse(null);
    }

    public List<Promotion> getPromotions() {
        return Collections.unmodifiableList(promotions);
    }
}
