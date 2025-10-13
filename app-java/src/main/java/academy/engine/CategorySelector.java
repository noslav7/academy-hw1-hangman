package academy.engine;

import java.util.Scanner;

import academy.model.WordCategory;

/**
 * Селектор категории слов.
 * Отвечает за выбор категории в интерактивном режиме.
 */
public class CategorySelector {

    private final Scanner scanner;

    public CategorySelector(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Выбор категории слов
     */
    public WordCategory selectCategory() {
        WordCategory[] categories = WordCategory.values();
        System.out.println("\nВыберите категорию слов:");
        for (int i = 0; i < categories.length; i++) {
            System.out.println((i + 1) + ". " + categories[i].getDisplayName());
        }
        System.out.println("0. Случайная категория");

        return MenuSelector.selectFromMenu(
                scanner,
                "Ваш выбор: ",
                categories.length,
                WordCategory::getRandom,
                idx -> categories[idx - 1],
                "");
    }
}
