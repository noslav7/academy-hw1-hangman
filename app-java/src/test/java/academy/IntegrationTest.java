package academy;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        assertEquals("кот;WIN", result);
    }

    @Test
    void testCompleteLoseScenario() {
        HangmanGameEngine engine = new HangmanGameEngine();

        String result = engine.startTestGame("кот", "абвгде");

        // Явно проверяем ожидаемый результат
        assertEquals("***;IN_PROGRESS", result);
    }

    @Test
    void testPartialWinScenario() {
        HangmanGameEngine engine = new HangmanGameEngine();

        String result = engine.startTestGame("кот", "ко");

        assertEquals("ко*;IN_PROGRESS", result);
    }

    @Test
    void testCaseInsensitiveScenario() {
        HangmanGameEngine engine = new HangmanGameEngine();

        String result = engine.startTestGame("Кот", "КоТ");

        assertEquals("кот;WIN", result);
    }

    @Test
    void testMixedCorrectAndWrongGuesses() {
        HangmanGameEngine engine = new HangmanGameEngine();

        String result = engine.startTestGame("кот", "каот");

        assertEquals("кот;WIN", result);
    }

    @Test
    void testLongWordScenario() {
        HangmanGameEngine engine = new HangmanGameEngine();

        String result = engine.startTestGame("программист", "программист");

        assertEquals("программист;WIN", result);
    }

    @Test
    void testSingleLetterWord() {
        HangmanGameEngine engine = new HangmanGameEngine();

        String result = engine.startTestGame("а", "а");

        assertEquals("а;WIN", result);
    }

    @Test
    void testEmptyUserInput() {
        HangmanGameEngine engine = new HangmanGameEngine();

        String result = engine.startTestGame("тест", "");

        assertEquals("****;IN_PROGRESS", result);
    }

    @Test
    void testSpecialCharactersInInput() {
        HangmanGameEngine engine = new HangmanGameEngine();

        String result = engine.startTestGame("тест", "123!@#");

        assertEquals("****;IN_PROGRESS", result);
    }

    @Test
    void testDuplicateLettersInWord() {
        HangmanGameEngine engine = new HangmanGameEngine();

        String result = engine.startTestGame("мама", "ма");

        assertEquals("мама;WIN", result);
    }
}
