package academy.engine;

import java.util.Scanner;

import academy.model.DifficultyLevel;
import academy.model.GameSession;
import academy.model.WordCategory;
import academy.model.WordWithHint;

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

        // Выбор категории (через отдельный селектор)
        WordCategory category = new CategorySelector(scanner).selectCategory();

        // Выбор сложности (через отдельный селектор)
        DifficultyLevel difficulty = new DifficultySelector(scanner).selectDifficulty();

        // Создание игровой сессии
        WordWithHint wordWithHint = category.getRandomWordWithHint();
        currentSession = new GameSession(wordWithHint.getWord(), wordWithHint.getHint(), difficulty, category);

        // Основной игровой цикл
        playGame();
    }

    /**
     * Запустить игру в тестовом режиме
     *
     * @param secretWord загаданное слово
     * @param userInput  проверочное слово (результат угадывания)
     * @return результат в формате "угаданные_буквы;результат"
     */
    public String startTestGame(String secretWord, String userInput) {
        if (secretWord == null || secretWord.trim().isEmpty()) {
            throw new IllegalArgumentException("Загаданное слово не может быть пустым");
        }

        if (userInput == null) {
            userInput = "";
        }

        // Создание игровой сессии со случайными параметрами
        WordCategory category = WordCategory.getRandom();
        DifficultyLevel difficulty = DifficultyLevel.getRandom();
        currentSession = new GameSession(secretWord, difficulty, category);

        // Учитываем каждую букву из ввода пользователя (без учета регистра)
        String userLower = userInput.toLowerCase();
        for (int i = 0; i < userLower.length(); i++) {
            char c = userLower.charAt(i);
            if (Character.isLetter(c)) {
                currentSession.guessLetter(c);
            }
        }

        // Определяем результат игры: победа, если угаданы все буквы, иначе игра
        // продолжается
        if (currentSession.isWordGuessed()) {
            currentSession.setResult(academy.model.GameResult.WIN);
        } else {
            currentSession.setResult(academy.model.GameResult.IN_PROGRESS);
        }

        // Возврат результата
        return academy.view.HangmanVisualizer.getTestModeResult(secretWord, userInput, currentSession);
    }

    /**
     * Выбор категории слов
     */

    /**
     * Основной игровой цикл
     */
    private void playGame() {
        while (currentSession.canMakeAttempt()) {
            // Отображение текущего состояния
            System.out.println(academy.view.HangmanVisualizer.getFullGameDisplay(currentSession));

            // Получение ввода от пользователя
            if (!scanner.hasNextLine()) {
                // EOF: выходим из игрового цикла без ошибки
                break;
            }
            String input;
            try {
                input = scanner.nextLine().trim();
            } catch (java.util.NoSuchElementException eof) {
                break;
            }

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
            System.out.println("Пожалуйста, введите букву или команду");
            return false;
        }

        // Обработка команды подсказки
        if (input.equalsIgnoreCase("подсказка") || input.equalsIgnoreCase("hint") || input.equals("?")) {
            if (!currentSession.isHintUsed()) {
                System.out.println("Подсказка: " + currentSession.getHint());
                currentSession.useHint();
                return true;
            } else {
                System.out.println("Подсказка уже была использована!");
                return false;
            }
        }

        if (input.length() > 1) {
            System.out.println("Пожалуйста, введите только одну букву или команду 'подсказка'");
            return false;
        }

        char letter = input.charAt(0);
        if (!Character.isLetter(letter)) {
            System.out.println("Пожалуйста, введите букву или команду 'подсказка'");
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
