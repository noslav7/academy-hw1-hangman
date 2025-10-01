package academy.view;

import java.util.List;

import academy.model.GameSession;

/**
 * Класс для визуализации виселицы в игре "Виселица"
 */
public class HangmanVisualizer {

    private static final List<String> HANGMAN_STAGES = List.of(
            // 0 попыток - пустая виселица
            """
                        +---+
                        |   |
                            |
                            |
                            |
                            |
                        =========
                    """,
            // 1 попытка - голова
            """
                        +---+
                        |   |
                        O   |
                            |
                            |
                            |
                        =========
                    """,
            // 2 попытки - голова + тело
            """
                        +---+
                        |   |
                        O   |
                        |   |
                            |
                            |
                        =========
                    """,
            // 3 попытки - голова + тело + левая рука
            """
                        +---+
                        |   |
                        O   |
                       /|   |
                            |
                            |
                        =========
                    """,
            // 4 попытки - голова + тело + обе руки
            """
                        +---+
                        |   |
                        O   |
                       /|\\  |
                            |
                            |
                        =========
                    """,
            // 5 попыток - голова + тело + руки + левая нога
            """
                        +---+
                        |   |
                        O   |
                       /|\\  |
                       /    |
                            |
                        =========
                    """,
            // 6 попыток - полная фигура (поражение)
            """
                        +---+
                        |   |
                        O   |
                       /|\\  |
                       / \\  |
                            |
                        =========
                    """);

    /**
     * Получить визуализацию виселицы для текущего состояния игры
     */
    public static String getHangmanVisualization(GameSession session) {
        int stage = Math.min(session.getCurrentAttempts(), HANGMAN_STAGES.size() - 1);
        return HANGMAN_STAGES.get(stage);
    }

    /**
     * Получить полную визуализацию игрового состояния
     */
    public static String getFullGameDisplay(GameSession session) {
        StringBuilder display = new StringBuilder();

        // Очистка экрана (работает в большинстве терминалов)
        display.append("\033[2J\033[H");

        // Заголовок
        display.append("=== ИГРА 'ВИСЕЛИЦА' ===\n\n");

        // Информация о категории и сложности
        display.append("Категория: ").append(session.getCategory().getDisplayName()).append("\n");
        display.append("Сложность: ").append(session.getDifficulty().getDisplayName()).append("\n");
        display.append("Попыток: ").append(session.getCurrentAttempts())
                .append("/").append(session.getMaxAttempts()).append("\n");

        // Информация о подсказке
        if (session.getHint() != null && !session.getHint().isEmpty()) {
            if (session.isHintUsed()) {
                display.append("Подсказка: ").append(session.getHint()).append(" (использована)\n");
            } else {
                display.append("Подсказка доступна (введите 'подсказка' или '?')\n");
            }
        }
        display.append("\n");

        // Виселица
        display.append(getHangmanVisualization(session)).append("\n");

        // Текущее состояние слова
        display.append("Слово: ").append(session.getCurrentWordState()).append("\n\n");

        // Использованные буквы
        if (!session.getAllUsedLetters().isEmpty()) {
            display.append("Использованные буквы: ")
                    .append(session.getAllUsedLetters().toString()).append("\n\n");
        }

        // Результат игры
        if (session.getResult().isGameOver()) {
            display.append("=== ").append(session.getResult().getMessage()).append(" ===\n");
            if (session.getResult() == academy.model.GameResult.LOSE) {
                display.append("Загаданное слово: ").append(session.getSecretWord()).append("\n");
            }
        } else {
            display.append("Введите букву: ");
        }

        return display.toString();
    }

    /**
     * Получить простое отображение состояния игры (для тестового режима)
     */
    public static String getSimpleGameState(GameSession session) {
        String status;
        if (session.getResult() == academy.model.GameResult.WIN) {
            status = "WIN";
        } else if (session.getResult() == academy.model.GameResult.LOSE) {
            status = "LOSE";
        } else {
            status = "IN_PROGRESS";
        }
        return session.getCurrentWordState() + ";" + status;
    }

    /**
     * Получить результат тестового режима с правильным отображением угаданных букв
     */
    public static String getTestModeResult(String secretWord, String userInput, GameSession session) {
        // Маска строится по текущему состоянию слова в сессии
        String masked = session.getCurrentWordState();

        String status;
        if (session.getResult() == academy.model.GameResult.WIN) {
            status = "WIN";
        } else if (session.getResult() == academy.model.GameResult.LOSE) {
            status = "NEG";
        } else {
            status = "IN_PROGRESS";
        }

        return masked + ";" + status;
    }
}
