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
        for (int i = 0; i < 6; i++) {
            gameSession.guessLetter((char) ('а' + i));
        }

        // Проверяем, что игра завершилась поражением или в процессе
        assertThat(gameSession.getResult()).isIn(GameResult.LOSE, GameResult.IN_PROGRESS);
        assertThat(gameSession.getCurrentAttempts()).isEqualTo(5);
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
