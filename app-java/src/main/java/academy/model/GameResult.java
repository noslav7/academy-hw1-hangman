package academy.model;

/**
 * Результат игры "Виселица"
 */
public enum GameResult {
    WIN("Поздравляем! Вы выиграли!"),
    LOSE("Игра окончена! Вы проиграли!"),
    IN_PROGRESS("Игра продолжается");

    private final String message;

    GameResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    /**
     * Проверить, завершена ли игра
     */
    public boolean isGameOver() {
        return this == WIN || this == LOSE;
    }
}
