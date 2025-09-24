package academy;

import static java.util.Objects.nonNull;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import academy.engine.HangmanGameEngine;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "Application Example", version = "Example 1.0", mixinStandardHelpOptions = true)
public class Application implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
    private static final ObjectReader YAML_READER = new ObjectMapper(new YAMLFactory()).findAndRegisterModules()
            .reader();
    private static final Predicate<String[]> IS_TESTING_MODE = words -> nonNull(words) && words.length == 2;

    @Option(names = { "-s", "--font-size" }, description = "Font size")
    int fontSize;

    @Parameters(paramLabel = "<word>", description = "Words pair for testing mode")
    private String[] words;

    @Option(names = { "-c", "--config" }, description = "Path to YAML config file")
    private File configPath;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Application()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        AppConfig config = loadConfig();
        LOGGER.atInfo().addKeyValue("config", config).log("Config content");

        HangmanGameEngine engine = new HangmanGameEngine();
        try {
            if (IS_TESTING_MODE.test(config.words())) {
                LOGGER.atInfo().log("Non-interactive testing mode enabled");
                var word = config.words()[0];
                var userInput = config.words()[1];
                var result = engine.startTestGame(word, userInput);
                System.out.println(result);
            } else {
                LOGGER.atInfo().log("Interactive mode enabled");
                engine.startInteractiveGame();
            }
        } finally {
            engine.close();
        }
    }

    private AppConfig loadConfig() {
        // fill with cli options
        if (configPath == null)
            return new AppConfig(fontSize, words);

        // use config file if provided
        try {
            return YAML_READER.readValue(configPath, AppConfig.class);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
