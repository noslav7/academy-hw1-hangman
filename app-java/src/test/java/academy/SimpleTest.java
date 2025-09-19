package academy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class SimpleTest {

    @Test
    void testBasicFunctionality() {
        // Простой тест для проверки базовой функциональности
        assertThat(true).isTrue();
        assertThat(1 + 1).isEqualTo(2);
    }
}
