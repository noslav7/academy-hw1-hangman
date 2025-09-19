package academy.engine;

import java.util.Scanner;

import academy.model.DifficultyLevel;
import academy.model.GameSession;
import academy.model.WordCategory;

/**
 * Игровой движок для игры "Виселица"
 */
public class HangmanGameEngine {
    private final Scanner scanner;
    protected GameSession currentSession;

    public HangmanGameEngine() {
        this.scanner = new Scanner(System.in);
    }

    public HangmanGameEngine(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Получить текущую игровую сессию
     */
    public GameSession getCurrentSession() {
        return currentSession;
    }

    /**
     * Запустить интерактивную игру
     */
    public void startInteractiveGame() {
        System.out.println("Добро пожаловать в игру 'Виселица'!");

        // Выбор категории
        WordCategory category = selectCategory();

        // Выбор сложности
        DifficultyLevel difficulty = selectDifficulty();

        // Создание игровой сессии
        String secretWord = category.getRandomWord();
        currentSession = new GameSession(secretWord, difficulty, category);

        // Основной игровой цикл
        playGame();
    }

    /**
     * Запустить игру в тестовом режиме
     *
     * @param secretWord загаданное слово
     * @param userInput  ввод пользователя (буквы)
     * @return результат в формате "угаданные_буквы;результат"
     */
    public String startTestGame(String secretWord, String userInput) {
        if (secretWord == null || secretWord.trim().isEmpty()) {
            throw new IllegalArgumentException("Загаданное слово не может быть пустым");
        }

        if (userInput == null) {
            userInput = "";
        }

        // Создание игровой сессии с случайными параметрами
        WordCategory category = WordCategory.getRandom();
        DifficultyLevel difficulty = DifficultyLevel.getRandom();
        currentSession = new GameSession(secretWord, difficulty, category);

        // Обработка ввода пользователя посимвольно
        for (char letter : userInput.toCharArray()) {
            if (Character.isLetter(letter)) {
                currentSession.guessLetter(letter);
            }
        }

        // Возврат результата
        return academy.view.HangmanVisualizer.getSimpleGameState(currentSession);
    }

    /**
     * Выбор категории слов
     */
    private WordCategory selectCategory() {
        WordCategory[] categories = WordCategory.values();
        System.out.println("\nВыберите категорию слов:");
        for (int i = 0; i < categories.length; i++) {
            System.out.println((i + 1) + ". " + categories[i].getDisplayName());
        }
        System.out.println("0. Случайная категория");

        while (true) {
            try {
                System.out.print("Ваш выбор: ");
                String input = scanner.nextLine().trim();

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

    /**
     * Выбор уровня сложности
     */
    private DifficultyLevel selectDifficulty() {
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
                String input = scanner.nextLine().trim();

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

    /**
     * Основной игровой цикл
     */
    private void playGame() {
        while (currentSession.canMakeAttempt()) {
            // Отображение текущего состояния
            System.out.println(academy.view.HangmanVisualizer.getFullGameDisplay(currentSession));

            // Получение ввода от пользователя
            String input = scanner.nextLine().trim();

            // Обработка ввода
            if (!processUserInput(input)) {
                continue; // Повторный запрос ввода
            }

            // Проверка окончания игры
            if (currentSession.getResult().isGameOver()) {
                break;
            }
        }

        // Финальное отображение
        System.out.println(academy.view.HangmanVisualizer.getFullGameDisplay(currentSession));
    }

    /**
     * Обработка ввода пользователя
     *
     * @param input ввод пользователя
     * @return true если ввод был обработан успешно, false если нужен повторный ввод
     */
    private boolean processUserInput(String input) {
        if (input.isEmpty()) {
            System.out.println("Пожалуйста, введите букву");
            return false;
        }

        if (input.length() > 1) {
            System.out.println("Пожалуйста, введите только одну букву");
            return false;
        }

        char letter = input.charAt(0);
        if (!Character.isLetter(letter)) {
            System.out.println("Пожалуйста, введите букву");
            return false;
        }

        // Проверка, не была ли буква уже использована
        if (currentSession.getAllUsedLetters().contains(Character.toLowerCase(letter))) {
            System.out.println("Эта буква уже была использована. Попробуйте другую.");
            return false;
        }

        // Попытка угадать букву
        boolean isCorrect = currentSession.guessLetter(letter);

        if (isCorrect) {
            System.out.println("Правильно! Буква '" + letter + "' есть в слове.");
        } else {
            System.out.println("Неправильно! Буквы '" + letter + "' нет в слове.");
        }

        return true;
    }

    /**
     * Закрыть ресурсы
     */
    public void close() {
        if (scanner != null) {
            scanner.close();
        }
    }
}
