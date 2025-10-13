package academy.engine;

import academy.model.DifficultyLevel;
import academy.model.GameSession;
import academy.model.WordCategory;

/**
 * Запуск игры в тестовом (неинтерактивном) режиме.
 */
public class TestGameRunner {

    private final HangmanGameEngine engine;

    public TestGameRunner(HangmanGameEngine engine) {
        this.engine = engine;
    }

    /**
     * Запустить игру в тестовом режиме
     *
     * @param secretWord загаданное слово
     * @param userInput  проверочное слово (результат угадывания)
     * @return результат в формате "угаданные_буквы;результат"
     */
    public String run(String secretWord, String userInput) {
        if (secretWord == null || secretWord.trim().isEmpty()) {
            throw new IllegalArgumentException("Загаданное слово не может быть пустым");
        }

        if (userInput == null) {
            userInput = "";
        }

        // Создание игровой сессии со случайными параметрами
        WordCategory category = WordCategory.getRandom();
        DifficultyLevel difficulty = DifficultyLevel.getRandom();
        engine.currentSession = new GameSession(secretWord, difficulty, category);

        // Учитываем каждую букву из ввода пользователя (без учета регистра)
        String userLower = userInput.toLowerCase();
        for (int i = 0; i < userLower.length(); i++) {
            char c = userLower.charAt(i);
            if (Character.isLetter(c)) {
                engine.currentSession.guessLetter(c);
            }
        }

        // Определяем результат игры: победа, если угаданы все буквы, иначе игра
        // продолжается
        if (engine.currentSession.isWordGuessed()) {
            engine.currentSession.setState(academy.model.GameState.WIN);
        } else {
            engine.currentSession.setState(academy.model.GameState.IN_PROGRESS);
        }

        // Возврат результата
        return academy.view.HangmanVisualizer.getTestModeResult(secretWord, userInput, engine.currentSession);
    }
}
