Системные требования:
JDK 17+ (рекомендуется OpenJDK/Temurin 17 LTS);
Maven 3.8.6+ (рекомендуется 3.9.x);
Интернет при первом запуске для загрузки зависимостей.

## Быстрый старт

**Для Windows (PowerShell):**
1. Откройте PowerShell в папке `app-java`
2. Запустите интерактивную игру: `.\run-utf8.ps1`
3. Для тестового режима: `.\run-jar-test.bat CAT CAT`

**Если скрипты не запускаются:**
```powershell
powershell -ExecutionPolicy Bypass -File .\run-utf8.ps1
```

# Запуск игры

## Интерактивный режим

Интерактивный режим запускается без аргументов:

Windows (PowerShell):
```powershell
# Рекомендуемый способ - используйте готовый скрипт:
.\run-utf8.ps1

# Или альтернативно через batch файл:
.\run-utf8.bat

# Если скрипты не запускаются, используйте:
powershell -ExecutionPolicy Bypass -File .\run-utf8.ps1

# Или напрямую через Maven (может не работать на некоторых системах):
mvn --% exec:java -Dexec.mainClass=academy.Application -Dfile.encoding=UTF-8
```

Linux (bash):
```bash
# В большинстве дистрибутивов терминал уже в UTF-8.
mvn exec:java -Dexec.mainClass=academy.Application -Dexec.jvmArgs="-Dfile.encoding=UTF-8"
```

### Подсказки в интерактивном режиме
- Введите "подсказка" или "?" для получения подсказки (один раз за игру).

## Неинтерактивный (тестовый) режим

Неинтерактивный режим запускается с двумя аргументами командной строки: "загаданное" слово и "угаданное" слово.

### Запуск через Maven (только для английских слов)

Windows (PowerShell):
```powershell
# Рекомендуемый способ - используйте готовый скрипт для тестового режима:
.\run-test.ps1 "CAT CAT"

# Или используйте JAR файл (рекомендуется для тестового режима):
.\run-jar-test.bat CAT CAT

# Если скрипт не запускается, используйте:
powershell -ExecutionPolicy Bypass -File .\run-test.ps1 "CAT CAT"

# Или напрямую через Maven:
mvn --% exec:java -Dexec.mainClass=academy.Application -Dfile.encoding=UTF-8 -Dexec.args="CAT CAT"
```

Linux (bash):
```bash
mvn exec:java -Dexec.mainClass=academy.Application -Dexec.jvmArgs="-Dfile.encoding=UTF-8" -Dexec.args="КОТ КОТ"
```

### Запуск через JAR (рекомендуется)

Windows:
```powershell
# Сначала соберите проект
mvn package -DskipTests

# Затем запустите JAR
java -jar target/hangman-1.0.jar КОТ КОТ
```

Linux:
```bash
mvn package -DskipTests
java -jar target/hangman-1.0.jar КОТ КОТ
```

### Формат вывода

В неинтерактивном режиме программа выводит результат в формате: `угаданные_буквы_слова;результат`, где не угаданные буквы заменены символом `*`.

Примеры:
- `.\run-jar-test.bat CAT CAT` → `cat;POS`
- `.\run-jar-test.bat CAT DOG` → `***;NEG`
- `.\run-jar-test.bat HELLO WORLD` → `*****;NEG`


