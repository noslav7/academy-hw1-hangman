package academy.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Категории слов для игры "Виселица"
 */
public enum WordCategory {
    ANIMALS("Животные", new HashMap<String, String>() {
        {
            put("кот", "Домашний питомец, который мяукает");
            put("собака", "Лучший друг человека");
            put("лошадь", "Животное, на котором ездят верхом");
            put("корова", "Дает молоко");
            put("свинья", "Розовое животное на ферме");
            put("овца", "Дает шерсть");
            put("коза", "Животное с рогами, дает молоко");
            put("курица", "Несет яйца");
            put("утка", "Плавает в пруду");
            put("гусь", "Белая птица с длинной шеей");
            put("индюк", "Большая птица, символ Дня благодарения");
            put("кролик", "Маленький пушистый зверек с длинными ушами");
            put("хомяк", "Маленький грызун, живет в клетке");
            put("мышь", "Маленький серый грызун");
            put("крыса", "Большой грызун");
            put("бабушка", "Близкий родственник");
        }
    }),
    FOOD("Еда", new HashMap<String, String>() {
        {
            put("хлеб", "Выпекается из муки");
            put("молоко", "Белая жидкость от коровы");
            put("сыр", "Делается из молока");
            put("мясо", "Белок животного происхождения");
            put("рыба", "Живет в воде, плавает");
            put("яйцо", "Несет курица");
            put("масло", "Жирный продукт");
            put("сахар", "Сладкий белый порошок");
            put("соль", "Белая приправа");
            put("перец", "Острая приправа");
            put("лук", "Овощ, заставляет плакать");
            put("чеснок", "Острый овощ с запахом");
            put("морковь", "Оранжевый овощ");
            put("картофель", "Клубень, растет в земле");
            put("помидор", "Красный овощ");
            put("огурец", "Зеленый овощ");
        }
    }),
    NATURE("Природа", new HashMap<String, String>() {
        {
            put("дерево", "Высокое растение с листьями");
            put("цветок", "Красивое растение");
            put("трава", "Зеленое покрытие земли");
            put("лист", "Часть дерева");
            put("ветка", "Часть дерева");
            put("корень", "Подземная часть растения");
            put("ствол", "Основная часть дерева");
            put("крона", "Верхняя часть дерева");
            put("лес", "Много деревьев");
            put("поле", "Открытое пространство");
            put("гора", "Высокая возвышенность");
            put("река", "Водный поток");
            put("озеро", "Водоем");
            put("море", "Большой водоем");
            put("океан", "Очень большой водоем");
            put("небо", "Над головой");
        }
    }),
    PROFESSIONS("Профессии", new HashMap<String, String>() {
        {
            put("врач", "Лечит людей");
            put("учитель", "Работает в школе");
            put("инженер", "Строит и проектирует");
            put("программист", "Пишет код");
            put("дизайнер", "Создает красивое");
            put("художник", "Рисует картины");
            put("музыкант", "Играет на инструментах");
            put("повар", "Готовит еду");
            put("водитель", "Управляет автомобилем");
            put("пилот", "Управляет самолетом");
            put("моряк", "Работает на корабле");
            put("строитель", "Строит дома");
            put("пожарный", "Тушит пожары");
            put("полицейский", "Охраняет порядок");
            put("военный", "Защищает страну");
        }
    }),
    TRANSPORT("Транспорт", new HashMap<String, String>() {
        {
            put("автомобиль", "Четырехколесное средство передвижения");
            put("автобус", "Большой автомобиль для пассажиров");
            put("троллейбус", "Электрический автобус");
            put("трамвай", "Едет по рельсам");
            put("метро", "Подземный транспорт");
            put("поезд", "Длинный состав вагонов");
            put("самолет", "Летает в небе");
            put("вертолет", "Летает с винтом");
            put("корабль", "Плавает по морю");
            put("лодка", "Маленькое судно");
            put("катер", "Быстрая лодка");
            put("яхта", "Роскошная лодка");
            put("пароход", "Корабль с паровым двигателем");
            put("теплоход", "Корабль с дизельным двигателем");
            put("баржа", "Грузовое судно");
        }
    });

    private final String displayName;
    private final Map<String, String> wordsWithHints;
    private static final Random RANDOM = new Random();

    WordCategory(String displayName, Map<String, String> wordsWithHints) {
        this.displayName = displayName;
        this.wordsWithHints = wordsWithHints;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Map<String, String> getWordsWithHints() {
        return wordsWithHints;
    }

    /**
     * Обратная совместимость для тестов: вернуть список слов категории
     */
    public List<String> getWords() {
        return List.copyOf(wordsWithHints.keySet());
    }

    /**
     * Получить случайное слово с подсказкой из категории
     */
    public WordWithHint getRandomWordWithHint() {
        List<String> words = List.copyOf(wordsWithHints.keySet());
        String randomWord = words.get(RANDOM.nextInt(words.size()));
        String hint = wordsWithHints.get(randomWord);
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
        return wordsWithHints.get(word.toLowerCase());
    }

    /**
     * Получить случайную категорию
     */
    public static WordCategory getRandom() {
        WordCategory[] categories = values();
        return categories[RANDOM.nextInt(categories.length)];
    }
}
