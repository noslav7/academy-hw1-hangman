package academy.engine;

import java.util.Scanner;

import academy.model.DifficultyLevel;

/**
 * Селектор уровня сложности.
 * Отвечает за выбор сложности в интерактивном режиме.
 */
public class DifficultySelector {

    private final Scanner scanner;

    public DifficultySelector(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Выбор уровня сложности
     */
    public DifficultyLevel selectDifficulty() {
        DifficultyLevel[] difficulties = DifficultyLevel.values();
        System.out.println("\nВыберите уровень сложности:");
        for (int i = 0; i < difficulties.length; i++) {
            System.out.println((i + 1) + ". " + difficulties[i].getDisplayName() +
                    " (" + difficulties[i].getMaxAttempts() + " попыток)");
        }
        System.out.println("0. Случайная сложность");

        while (true) {
            try {
                System.out.print("Ваш выбор: ");
                if (!scanner.hasNextLine()) {
                    // EOF: по умолчанию выбираем случайную сложность
                    return DifficultyLevel.getRandom();
                }
                String input;
                try {
                    input = scanner.nextLine().trim();
                } catch (java.util.NoSuchElementException eof) {
                    return DifficultyLevel.getRandom();
                }

                if (input.equals("0")) {
                    return DifficultyLevel.getRandom();
                }

                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= difficulties.length) {
                    return difficulties[choice - 1];
                } else {
                    System.out.println("Пожалуйста, выберите число от 0 до " + difficulties.length);
                }
            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите число");
            }
        }
    }
}
