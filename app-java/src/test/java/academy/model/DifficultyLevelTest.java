package academy.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class DifficultyLevelTest {

    @Test
    void testGetDisplayName() {
        assertThat(DifficultyLevel.EASY.getDisplayName()).isEqualTo("Легкий");
        assertThat(DifficultyLevel.MEDIUM.getDisplayName()).isEqualTo("Средний");
        assertThat(DifficultyLevel.HARD.getDisplayName()).isEqualTo("Сложный");
    }

    @Test
    void testGetMaxAttempts() {
        assertThat(DifficultyLevel.EASY.getMaxAttempts()).isEqualTo(8);
        assertThat(DifficultyLevel.MEDIUM.getMaxAttempts()).isEqualTo(6);
        assertThat(DifficultyLevel.HARD.getMaxAttempts()).isEqualTo(4);
    }

    @Test
    void testGetRandom() {
        // Проверяем, что метод getRandom() возвращает валидное значение
        DifficultyLevel random = DifficultyLevel.getRandom();
        assertThat(random).isNotNull();
        assertThat(random).isInstanceOf(DifficultyLevel.class);

        // Проверяем, что возвращаемое значение имеет корректные свойства
        assertThat(random.getDisplayName()).isNotBlank();
        assertThat(random.getMaxAttempts()).isPositive();
    }
}
