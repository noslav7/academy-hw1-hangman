package academy.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameSessionTest {

    private GameSession gameSession;

    @BeforeEach
    void setUp() {
        gameSession = new GameSession("тест", DifficultyLevel.MEDIUM, WordCategory.ANIMALS);
    }

    @Test
    void testInitialState() {
        assertThat(gameSession.getSecretWord()).isEqualTo("тест");
        assertThat(gameSession.getDifficulty()).isEqualTo(DifficultyLevel.MEDIUM);
        assertThat(gameSession.getCategory()).isEqualTo(WordCategory.ANIMALS);
        assertThat(gameSession.getMaxAttempts()).isEqualTo(6);
        assertThat(gameSession.getCurrentAttempts()).isEqualTo(0);
        assertThat(gameSession.getCurrentWordState()).isEqualTo("****");
        assertThat(gameSession.getResult()).isEqualTo(GameResult.IN_PROGRESS);
        assertThat(gameSession.canMakeAttempt()).isTrue();
    }

    @Test
    void testCorrectGuess() {
        boolean result = gameSession.guessLetter('т');

        assertThat(result).isTrue();
        assertThat(gameSession.getCurrentWordState()).isEqualTo("т**т");
        assertThat(gameSession.getGuessedLetters()).contains('т');
        assertThat(gameSession.getCurrentAttempts()).isEqualTo(0);
        assertThat(gameSession.getResult()).isEqualTo(GameResult.IN_PROGRESS);
    }

    @Test
    void testWrongGuess() {
        boolean result = gameSession.guessLetter('а');

        assertThat(result).isFalse();
        assertThat(gameSession.getCurrentWordState()).isEqualTo("****");
        assertThat(gameSession.getWrongLetters()).contains('а');
        assertThat(gameSession.getCurrentAttempts()).isEqualTo(1);
        assertThat(gameSession.getResult()).isEqualTo(GameResult.IN_PROGRESS);
    }

    @Test
    void testWordGuessed() {
        gameSession.guessLetter('т');
        gameSession.guessLetter('е');
        gameSession.guessLetter('с');

        assertThat(gameSession.getCurrentWordState()).isEqualTo("тест");
        assertThat(gameSession.isWordGuessed()).isTrue();
        assertThat(gameSession.getResult()).isEqualTo(GameResult.WIN);
    }

    @Test
    void testGameOverAfterMaxAttempts() {
        // Делаем 6 неправильных попыток (MEDIUM уровень дает 6 попыток)
        // Используем буквы, которых нет в слове "тест": а, б, в, г, д, ж
        char[] wrongLetters = { 'а', 'б', 'в', 'г', 'д', 'ж' };
        for (char letter : wrongLetters) {
            gameSession.guessLetter(letter);
        }

        // После 6 неправильных попыток игра должна завершиться поражением
        assertThat(gameSession.getResult()).isEqualTo(GameResult.LOSE);
        assertThat(gameSession.getCurrentAttempts()).isEqualTo(6);
    }

    @Test
    void testGameInProgressAfterSomeAttempts() {
        // Делаем 3 неправильные попытки
        char[] wrongLetters = { 'а', 'б', 'в' };
        for (char letter : wrongLetters) {
            gameSession.guessLetter(letter);
        }

        // После 3 неправильных попыток игра должна продолжаться
        assertThat(gameSession.getResult()).isEqualTo(GameResult.IN_PROGRESS);
        assertThat(gameSession.getCurrentAttempts()).isEqualTo(3);
    }

    @Test
    void testCaseInsensitiveGuess() {
        gameSession.guessLetter('Т');
        gameSession.guessLetter('Е');

        assertThat(gameSession.getCurrentWordState()).isEqualTo("те*т");
        assertThat(gameSession.getGuessedLetters()).contains('т', 'е');
    }

    @Test
    void testDuplicateGuess() {
        gameSession.guessLetter('т');
        boolean result = gameSession.guessLetter('т');

        assertThat(result).isFalse();
        assertThat(gameSession.getCurrentAttempts()).isEqualTo(0);
    }

    @Test
    void testGetAllUsedLetters() {
        gameSession.guessLetter('т');
        gameSession.guessLetter('а');

        assertThat(gameSession.getAllUsedLetters()).contains('т', 'а');
    }
}
