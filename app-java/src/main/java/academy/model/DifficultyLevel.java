package academy.model;

/**
 * Уровни сложности игры "Виселица"
 */
public enum DifficultyLevel {
    EASY("Легкий", 8),
    MEDIUM("Средний", 6),
    HARD("Сложный", 4);

    private final String displayName;
    private final int maxAttempts;

    DifficultyLevel(String displayName, int maxAttempts) {
        this.displayName = displayName;
        this.maxAttempts = maxAttempts;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    /**
     * Получить случайный уровень сложности
     */
    public static DifficultyLevel getRandom() {
        DifficultyLevel[] levels = values();
        return levels[(int) (Math.random() * levels.length)];
    }
}
