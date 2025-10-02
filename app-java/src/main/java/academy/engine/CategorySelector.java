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

        while (true) {
            try {
                System.out.print("Ваш выбор: ");
                if (!scanner.hasNextLine()) {
                    // EOF: по умолчанию выбираем случайную категорию
                    return WordCategory.getRandom();
                }
                String input;
                try {
                    input = scanner.nextLine().trim();
                } catch (java.util.NoSuchElementException eof) {
                    return WordCategory.getRandom();
                }

                if (input.equals("0")) {
                    return WordCategory.getRandom();
                }

                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= categories.length) {
                    return categories[choice - 1];
                } else {
                    System.out.println("Пожалуйста, выберите число от 0 до " + categories.length);
                }
            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите число");
            }
        }
    }
}
