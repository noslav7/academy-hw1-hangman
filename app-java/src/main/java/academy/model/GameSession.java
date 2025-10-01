package academy.model;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Игровая сессия для хранения состояния игры "Виселица"
 */
public class GameSession {
    private final String secretWord;
    private final String hint;
    private final DifficultyLevel difficulty;
    private final WordCategory category;
    private final int maxAttempts;
    private int currentAttempts;
    private final Set<Character> guessedLetters;
    private final Set<Character> wrongLetters;
    private GameResult result;
    private boolean hintUsed;

    public GameSession(String secretWord, DifficultyLevel difficulty, WordCategory category) {
        this.secretWord = secretWord.toLowerCase();
        this.hint = category.getHintForWord(secretWord);
        this.difficulty = difficulty;
        this.category = category;
        this.maxAttempts = difficulty.getMaxAttempts();
        this.currentAttempts = 0;
        this.guessedLetters = new LinkedHashSet<>();
        this.wrongLetters = new LinkedHashSet<>();
        this.result = GameResult.IN_PROGRESS;
        this.hintUsed = false;
    }

    public GameSession(String secretWord, String hint, DifficultyLevel difficulty, WordCategory category) {
        this.secretWord = secretWord.toLowerCase();
        this.hint = hint;
        this.difficulty = difficulty;
        this.category = category;
        this.maxAttempts = difficulty.getMaxAttempts();
        this.currentAttempts = 0;
        this.guessedLetters = new LinkedHashSet<>();
        this.wrongLetters = new LinkedHashSet<>();
        this.result = GameResult.IN_PROGRESS;
        this.hintUsed = false;
    }

    public String getSecretWord() {
        return secretWord;
    }

    public String getHint() {
        return hint;
    }

    public boolean isHintUsed() {
        return hintUsed;
    }

    public void useHint() {
        this.hintUsed = true;
    }

    public DifficultyLevel getDifficulty() {
        return difficulty;
    }

    public WordCategory getCategory() {
        return category;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public int getCurrentAttempts() {
        return currentAttempts;
    }

    public int getRemainingAttempts() {
        return maxAttempts - currentAttempts;
    }

    public Set<Character> getGuessedLetters() {
        return guessedLetters;
    }

    public Set<Character> getWrongLetters() {
        return wrongLetters;
    }

    public GameResult getResult() {
        return result;
    }

    public void setResult(GameResult result) {
        this.result = result;
    }

    /**
     * Получить текущее состояние угадываемого слова
     * Неугаданные буквы заменяются символом '*'
     */
    public String getCurrentWordState() {
        StringBuilder result = new StringBuilder();
        for (char c : secretWord.toCharArray()) {
            if (guessedLetters.contains(c)) {
                result.append(c);
            } else {
                result.append('*');
            }
        }
        return result.toString();
    }

    /**
     * Проверить, угадано ли слово полностью
     */
    public boolean isWordGuessed() {
        for (char c : secretWord.toCharArray()) {
            if (!guessedLetters.contains(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Сделать попытку угадать букву
     *
     * @param letter буква для угадывания
     * @return true если буква есть в слове, false если нет
     */
    public boolean guessLetter(char letter) {
        char lowerLetter = Character.toLowerCase(letter);

        if (guessedLetters.contains(lowerLetter) || wrongLetters.contains(lowerLetter)) {
            return false; // буква уже была угадана
        }

        if (secretWord.contains(String.valueOf(lowerLetter))) {
            guessedLetters.add(lowerLetter);
            if (isWordGuessed()) {
                result = GameResult.WIN;
            }
            return true;
        } else {
            wrongLetters.add(lowerLetter);
            currentAttempts++;
            if (currentAttempts >= maxAttempts) {
                result = GameResult.LOSE;
            }
            return false;
        }
    }

    /**
     * Проверить, можно ли сделать еще попытку
     */
    public boolean canMakeAttempt() {
        return result == GameResult.IN_PROGRESS && currentAttempts < maxAttempts;
    }

    /**
     * Получить все использованные буквы (правильные и неправильные)
     */
    public Set<Character> getAllUsedLetters() {
        Set<Character> allUsed = new LinkedHashSet<>();
        // Preserve insertion order: guessed letters first, then wrong letters
        allUsed.addAll(guessedLetters);
        allUsed.addAll(wrongLetters);
        return allUsed;
    }
}
