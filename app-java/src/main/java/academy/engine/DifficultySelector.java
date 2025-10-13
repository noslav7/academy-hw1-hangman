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

        return MenuSelector.selectFromMenu(
                scanner,
                "Ваш выбор: ",
                difficulties.length,
                DifficultyLevel::getRandom,
                idx -> difficulties[idx - 1],
                "");
    }
}
