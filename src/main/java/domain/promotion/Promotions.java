package domain.promotion;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Promotions {
    private final List<Promotion> promotions;

    private Promotions(final List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public static List<Promotion> from(final String filePath) throws FileNotFoundException {
        List<String> promotions = readFromPromotionFiles(filePath);

        return promotions.stream()
                .skip(1)
                .map(PromotionParser::from)
                .toList();
    }

    private static List<String> readFromPromotionFiles(final String filePath) throws FileNotFoundException {
        List<String> promotions = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                promotions.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("[ERROR] 프로모션 파일을 찾을 수 없습니다.");
        }
        return promotions;
    }

    public Promotion findPromotionByName(final String name) {
        return promotions.stream()
                .filter(promotion -> name.equals(promotion.getName()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("[ERROR] 일치하는 프로모션이 없습니다."));
    }
}
