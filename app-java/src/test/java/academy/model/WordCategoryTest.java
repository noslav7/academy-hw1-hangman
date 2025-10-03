package academy.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class WordCategoryTest {

    @Test
    void testGetDisplayName() {
        assertThat(WordCategory.ANIMALS.getDisplayName()).isEqualTo("Животные");
        assertThat(WordCategory.FOOD.getDisplayName()).isEqualTo("Еда");
        assertThat(WordCategory.NATURE.getDisplayName()).isEqualTo("Природа");
        assertThat(WordCategory.PROFESSIONS.getDisplayName()).isEqualTo("Профессии");
        assertThat(WordCategory.TRANSPORT.getDisplayName()).isEqualTo("Транспорт");
    }

    @Test
    void testGetWords() {
        for (WordCategory category : WordCategory.values()) {
            assertThat(category.getWords()).isNotEmpty();
        }
    }

    @Test
    void testGetRandomWord() {
        String randomWord = WordCategory.ANIMALS.getRandomWord();
        assertThat(randomWord).isIn(WordCategory.ANIMALS.getWords());
    }

    @Test
    void testGetRandom() {
        // Проверяем, что метод getRandom() возвращает валидное значение
        WordCategory random = WordCategory.getRandom();
        assertThat(random).isNotNull();
        assertThat(random).isInstanceOf(WordCategory.class);

        // Проверяем, что возвращаемое значение имеет корректные свойства
        assertThat(random.getDisplayName()).isNotBlank();
        assertThat(random.getWords()).isNotEmpty();
    }
}
