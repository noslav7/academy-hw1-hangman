@echo off
chcp 65001 >nul
set JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF-8 -Dconsole.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8
mvn exec:java -Dexec.mainClass=academy.Application -Dfile.encoding=UTF-8
