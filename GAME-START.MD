Системные требования:
JDK 17+ (рекомендуется OpenJDK/Temurin 17 LTS);
Maven 3.8.6+ (рекомендуется 3.9.x);
Интернет при первом запуске для загрузки зависимостей.

# Запуск и кодировка консоли (UTF-8)

Windows: чтобы корректно отображались русские символы в выводе игры, запустите JVM и консоль в UTF‑8:
```powershell
chcp 65001; [Console]::OutputEncoding = [Text.UTF8Encoding]::UTF8; [Console]::InputEncoding = [Text.UTF8Encoding]::UTF8
```

Интерактивный режим запускается без аргументов:
```powershell
mvn --% exec:java -Dexec.mainClass=academy.Application -Dfile.encoding=UTF-8
```


Linux (bash):
```bash
# В большинстве дистрибутивов терминал уже в UTF-8.
mvn exec:java -Dexec.mainClass=academy.Application -Dexec.jvmArgs="-Dfile.encoding=UTF-8"
```


