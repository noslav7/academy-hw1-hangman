package academy.model;

/**
 * Состояние игры "Виселица"
 */
public enum GameState {
    WIN("Поздравляем! Вы выиграли!"),
    LOSE("Игра окончена! Вы проиграли!"),
    IN_PROGRESS("Игра продолжается");

    private final String message;

    GameState(String message) {
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
