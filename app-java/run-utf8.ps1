# Set console encoding to UTF-8
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
[Console]::InputEncoding = [System.Text.Encoding]::UTF8

# Set environment variables for UTF-8
$env:JAVA_TOOL_OPTIONS = "-Dfile.encoding=UTF-8 -Dconsole.encoding=UTF-8"

# Run the application
& mvn exec:java "-Dexec.mainClass=academy.Application" "-Dfile.encoding=UTF-8"
