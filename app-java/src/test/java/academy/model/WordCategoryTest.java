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
        WordCategory random = WordCategory.getRandom();
        assertThat(random).isIn(WordCategory.ANIMALS, WordCategory.FOOD, WordCategory.NATURE,
                WordCategory.PROFESSIONS, WordCategory.TRANSPORT);
    }
}
