package academy;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import academy.engine.HangmanGameEngine;

class IntegrationTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testCompleteWinScenario() {
        HangmanGameEngine engine = new HangmanGameEngine();

        String result = engine.startTestGame("кот", "кот");

        assertThat(result).isEqualTo("кот;WIN");
    }

    @Test
    void testCompleteLoseScenario() {
        HangmanGameEngine engine = new HangmanGameEngine();

        String result = engine.startTestGame("кот", "абвгде");

        // Проверяем, что игра не завершилась победой
        assertThat(result).doesNotContain("WIN");
    }

    @Test
    void testPartialWinScenario() {
        HangmanGameEngine engine = new HangmanGameEngine();

        String result = engine.startTestGame("кот", "ко");

        assertThat(result).isEqualTo("ко*;IN_PROGRESS");
    }

    @Test
    void testCaseInsensitiveScenario() {
        HangmanGameEngine engine = new HangmanGameEngine();

        String result = engine.startTestGame("Кот", "КоТ");

        assertThat(result).isEqualTo("кот;WIN");
    }

    @Test
    void testMixedCorrectAndWrongGuesses() {
        HangmanGameEngine engine = new HangmanGameEngine();

        String result = engine.startTestGame("кот", "каот");

        assertThat(result).isEqualTo("кот;WIN");
    }

    @Test
    void testLongWordScenario() {
        HangmanGameEngine engine = new HangmanGameEngine();

        String result = engine.startTestGame("программист", "программист");

        assertThat(result).isEqualTo("программист;WIN");
    }

    @Test
    void testSingleLetterWord() {
        HangmanGameEngine engine = new HangmanGameEngine();

        String result = engine.startTestGame("а", "а");

        assertThat(result).isEqualTo("а;WIN");
    }

    @Test
    void testEmptyUserInput() {
        HangmanGameEngine engine = new HangmanGameEngine();

        String result = engine.startTestGame("тест", "");

        assertThat(result).isEqualTo("****;IN_PROGRESS");
    }

    @Test
    void testSpecialCharactersInInput() {
        HangmanGameEngine engine = new HangmanGameEngine();

        String result = engine.startTestGame("тест", "123!@#");

        assertThat(result).isEqualTo("****;IN_PROGRESS");
    }

    @Test
    void testDuplicateLettersInWord() {
        HangmanGameEngine engine = new HangmanGameEngine();

        String result = engine.startTestGame("мама", "ма");

        assertThat(result).isEqualTo("мама;WIN");
    }
}
