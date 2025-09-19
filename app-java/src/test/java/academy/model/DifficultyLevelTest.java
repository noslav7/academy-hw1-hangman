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
        DifficultyLevel random = DifficultyLevel.getRandom();
        assertThat(random).isIn(DifficultyLevel.EASY, DifficultyLevel.MEDIUM, DifficultyLevel.HARD);
    }
}
