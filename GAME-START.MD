# Запуск и кодировка консоли (UTF-8)

Windows: xтобы корректно отображались русские символы в выводе игры, запустите JVM и консоль в UTF‑8:
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


