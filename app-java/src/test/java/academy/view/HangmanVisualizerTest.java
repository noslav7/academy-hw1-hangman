package academy.view;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import academy.model.DifficultyLevel;
import academy.model.GameSession;
import academy.model.WordCategory;

class HangmanVisualizerTest {

    private GameSession gameSession;

    @BeforeEach
    void setUp() {
        gameSession = new GameSession("тест", DifficultyLevel.MEDIUM, WordCategory.ANIMALS);
    }

    @Test
    void testHangmanVisualizationInitialState() {
        String visualization = HangmanVisualizer.getHangmanVisualization(gameSession);

        assertThat(visualization).contains("+---+");
        assertThat(visualization).contains("|   |");
        assertThat(visualization).doesNotContain("O");
    }

    @Test
    void testHangmanVisualizationAfterWrongGuess() {
        gameSession.guessLetter('а');

        String visualization = HangmanVisualizer.getHangmanVisualization(gameSession);

        assertThat(visualization).contains("O");
    }

    @Test
    void testHangmanVisualizationAfterMultipleGuesses() {
        gameSession.guessLetter('а');
        gameSession.guessLetter('б');
        gameSession.guessLetter('в');

        String visualization = HangmanVisualizer.getHangmanVisualization(gameSession);

        assertThat(visualization).contains("O");
        assertThat(visualization).contains("|");
    }

    @Test
    void testFullGameDisplay() {
        String display = HangmanVisualizer.getFullGameDisplay(gameSession);

        assertThat(display).contains("=== ИГРА 'ВИСЕЛИЦА' ===");
        assertThat(display).contains("Категория: Животные");
        assertThat(display).contains("Сложность: Средний");
        assertThat(display).contains("Попыток: 0/6");
        assertThat(display).contains("Слово: ****");
        assertThat(display).contains("Введите букву:");
    }

    @Test
    void testFullGameDisplayWithGuessedLetters() {
        gameSession.guessLetter('т');
        gameSession.guessLetter('е');

        String display = HangmanVisualizer.getFullGameDisplay(gameSession);

        assertThat(display).contains("Слово: те*т");
        assertThat(display).contains("Использованные буквы: [т, е]");
    }

    @Test
    void testFullGameDisplayWin() {
        gameSession.guessLetter('т');
        gameSession.guessLetter('е');
        gameSession.guessLetter('с');

        String display = HangmanVisualizer.getFullGameDisplay(gameSession);

        assertThat(display).contains("=== Поздравляем! Вы выиграли! ===");
    }

    @Test
    void testFullGameDisplayLose() {
        // Делаем 6 неправильных попыток (MEDIUM уровень дает 6 попыток)
        // Используем буквы, которых нет в слове "тест": а, б, в, г, д, ж
        char[] wrongLetters = { 'а', 'б', 'в', 'г', 'д', 'ж' };
        for (char letter : wrongLetters) {
            gameSession.guessLetter(letter);
        }

        String display = HangmanVisualizer.getFullGameDisplay(gameSession);

        // После 6 неправильных попыток игра должна завершиться поражением
        assertThat(display).contains("Игра окончена! Вы проиграли!");
        assertThat(display).contains("Загаданное слово: тест");
    }

    @Test
    void testSimpleGameState() {
        String state = HangmanVisualizer.getSimpleGameState(gameSession);

        assertThat(state).isEqualTo("****;IN_PROGRESS");
    }

    @Test
    void testSimpleGameStateWin() {
        gameSession.guessLetter('т');
        gameSession.guessLetter('е');
        gameSession.guessLetter('с');

        String state = HangmanVisualizer.getSimpleGameState(gameSession);

        assertThat(state).isEqualTo("тест;WIN");
    }

    @Test
    void testSimpleGameStateLose() {
        // Делаем 6 неправильных попыток (MEDIUM уровень дает 6 попыток)
        // Используем буквы, которых нет в слове "тест": а, б, в, г, д, ж
        char[] wrongLetters = { 'а', 'б', 'в', 'г', 'д', 'ж' };
        for (char letter : wrongLetters) {
            gameSession.guessLetter(letter);
        }

        String state = HangmanVisualizer.getSimpleGameState(gameSession);

        // После 6 неправильных попыток игра должна завершиться поражением
        assertThat(state).isEqualTo("****;LOSE");
    }
}
