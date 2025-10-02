package academy.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

/**
 * Категории слов для игры "Виселица"
 */
public enum WordCategory {
    ANIMALS("Животные"),
    FOOD("Еда"),
    NATURE("Природа"),
    PROFESSIONS("Профессии"),
    TRANSPORT("Транспорт");

    private static final Random RANDOM = new Random();
    private static final String WORDLISTS_RESOURCE = "/wordlists.yaml";
    private static final Map<String, Map<String, String>> CATEGORY_TO_WORDS;

    static {
        CATEGORY_TO_WORDS = loadWordlists();
    }

    private final String displayName;

    WordCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Map<String, String> getWordsWithHints() {
        Map<String, String> words = CATEGORY_TO_WORDS.getOrDefault(name(), Collections.emptyMap());
        return words;
    }

    /**
     * Обратная совместимость для тестов: вернуть список слов категории
     */
    public List<String> getWords() {
        return List.copyOf(getWordsWithHints().keySet());
    }

    /**
     * Получить случайное слово с подсказкой из категории
     */
    public WordWithHint getRandomWordWithHint() {
        List<String> words = List.copyOf(getWordsWithHints().keySet());
        String randomWord = words.get(RANDOM.nextInt(words.size()));
        String hint = getWordsWithHints().get(randomWord);
        return new WordWithHint(randomWord, hint);
    }

    /**
     * Получить случайное слово из категории (для обратной совместимости)
     */
    public String getRandomWord() {
        return getRandomWordWithHint().getWord();
    }

    /**
     * Получить подсказку для слова
     */
    public String getHintForWord(String word) {
        return getWordsWithHints().get(word.toLowerCase());
    }

    /**
     * Получить случайную категорию
     */
    public static WordCategory getRandom() {
        WordCategory[] categories = values();
        return categories[RANDOM.nextInt(categories.length)];
    }

    private static Map<String, Map<String, String>> loadWordlists() {
        // allow override via system property or env in future; for now load from
        // classpath
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory()).findAndRegisterModules();
        try (InputStream is = WordCategory.class.getResourceAsStream(WORDLISTS_RESOURCE)) {
            if (is == null) {
                return new HashMap<>();
            }
            TypeReference<HashMap<String, HashMap<String, String>>> type = new TypeReference<HashMap<String, HashMap<String, String>>>() {
            };
            HashMap<String, HashMap<String, String>> data = mapper.readValue(is, type);
            return Collections.unmodifiableMap(data);
        } catch (IOException e) {
            return new HashMap<>();
        }
    }
}
