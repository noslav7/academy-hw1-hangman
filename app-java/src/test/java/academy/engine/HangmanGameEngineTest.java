package academy.engine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import academy.model.GameSession;

class HangmanGameEngineTest {

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
    void testTestGameWithCorrectGuess() {
        HangmanGameEngine engine = new HangmanGameEngine();

        String result = engine.startTestGame("тест", "тест");

        assertEquals("тест;WIN", result);
    }

    @Test
    void testTestGameWithPartialGuess() {
        HangmanGameEngine engine = new HangmanGameEngine();

        String result = engine.startTestGame("тест", "те");

        assertEquals("те*т;IN_PROGRESS", result);
    }

    @Test
    void testTestGameWithWrongGuess() {
        HangmanGameEngine engine = new HangmanGameEngine();

        String result = engine.startTestGame("тест", "абвгдеж");

        // Явно проверяем ожидаемый результат: угадана буква 'е'
        assertEquals("*е**;IN_PROGRESS", result);
    }

    @Test
    void testTestGameWithEmptyInput() {
        HangmanGameEngine engine = new HangmanGameEngine();

        String result = engine.startTestGame("тест", "");

        assertEquals("****;IN_PROGRESS", result);
    }

    @Test
    void testTestGameWithNullInput() {
        HangmanGameEngine engine = new HangmanGameEngine();

        String result = engine.startTestGame("тест", null);

        assertEquals("****;IN_PROGRESS", result);
    }

    @Test
    void testTestGameWithInvalidWord() {
        HangmanGameEngine engine = new HangmanGameEngine();

        assertThatThrownBy(() -> engine.startTestGame("", "тест"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Загаданное слово не может быть пустым");
    }

    @Test
    void testTestGameWithNullWord() {
        HangmanGameEngine engine = new HangmanGameEngine();

        assertThatThrownBy(() -> engine.startTestGame(null, "тест"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Загаданное слово не может быть пустым");
    }

    @Test
    void testGameSessionCreation() {
        HangmanGameEngine engine = new HangmanGameEngine();

        engine.startTestGame("тест", "т");
        GameSession session = engine.getCurrentSession();

        assertThat(session).isNotNull();
        assertThat(session.getSecretWord()).isEqualTo("тест");
        assertThat(session.getCurrentWordState()).isEqualTo("т**т");
    }

    @Test
    void testInteractiveGameInitialization() {
        String input = "1\n1\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        HangmanGameEngine engine = new HangmanGameEngine(scanner);

        // Запускаем интерактивную игру с автоматическим завершением
        Thread gameThread = new Thread(() -> {
            try {
                engine.startInteractiveGame();
            } catch (Exception e) {
                // Игнорируем исключения в тестовом потоке
            }
        });

        gameThread.start();

        try {
            gameThread.join(1000); // Ждем максимум 1 секунду
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Проверяем, что игра была инициализирована
        assertThat(engine.getCurrentSession()).isNotNull();
    }
}
