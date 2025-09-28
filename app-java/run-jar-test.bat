@echo off
chcp 65001 >nul
set JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF-8 -Dconsole.encoding=UTF-8
java -Dfile.encoding=UTF-8 -jar target/hangman-1.0.jar %1 %2
