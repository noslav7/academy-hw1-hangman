Системные требования:
JDK 17+ (рекомендуется OpenJDK/Temurin 17 LTS);
Maven 3.8.6+ (рекомендуется 3.9.x);
Интернет при первом запуске для загрузки зависимостей.

# Запуск игры

## Интерактивный режим

Интерактивный режим запускается без аргументов:

Windows (PowerShell):
```powershell
mvn --% exec:java -Dexec.mainClass=academy.Application -Dfile.encoding=UTF-8
```

Linux (bash):
```bash
# В большинстве дистрибутивов терминал уже в UTF-8.
mvn exec:java -Dexec.mainClass=academy.Application -Dexec.jvmArgs="-Dfile.encoding=UTF-8"
```

## Неинтерактивный (тестовый) режим

Неинтерактивный режим запускается с двумя аргументами командной строки: "загаданное" слово и "угаданное" слово.

### Запуск через Maven (только для английских слов)

Windows (PowerShell):
```powershell
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
- `java -jar target/hangman-1.0.jar КОТ КОТ` → `КОТ;POS`
- `java -jar target/hangman-1.0.jar КОТ ДОМ` → `***;NEG`


