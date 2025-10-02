package academy.engine;

import academy.model.DifficultyLevel;
import academy.model.GameSession;
import academy.model.WordCategory;
import academy.model.WordWithHint;

/**
 * Запуск интерактивной игры.
 */
public class InteractiveGameRunner {

    private final HangmanGameEngine engine;

    public InteractiveGameRunner(HangmanGameEngine engine) {
        this.engine = engine;
    }

    /**
     * Запустить интерактивную игру
     */
    public void run() {
        System.out.println("Добро пожаловать в игру 'Виселица'!");

        // Выбор категории
        WordCategory category = new CategorySelector(engine.scanner).selectCategory();

        // Выбор сложности
        DifficultyLevel difficulty = new DifficultySelector(engine.scanner).selectDifficulty();

        // Создание игровой сессии
        WordWithHint wordWithHint = category.getRandomWordWithHint();
        engine.currentSession = new GameSession(wordWithHint.getWord(), wordWithHint.getHint(), difficulty, category);

        // Основной игровой цикл
        engine.playGame();
    }
}
