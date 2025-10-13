package academy.engine;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.IntFunction;
import java.util.function.Supplier;

/**
 * Utility for reading and validating simple numeric menu selections.
 * Supports:
 * - graceful EOF handling (returns defaultSupplier)
 * - "0" shortcut to random/default option
 * - 1..N range validation with customizable resolver
 */
public final class MenuSelector {

    private MenuSelector() {
    }

    /**
     * Read a menu selection from scanner and resolve to a value.
     *
     * @param scanner         input source
     * @param prompt          prompt to print before reading
     * @param size            number of selectable items (1..size)
     * @param defaultSupplier value to return on EOF or when user enters "0"
     * @param resolver        maps 1-based index to value
     * @param rangeLabel      label used in range error message (e.g. "сложности")
     * @param <T>             result type
     * @return resolved value from resolver or defaultSupplier
     */
    public static <T> T selectFromMenu(
            Scanner scanner,
            String prompt,
            int size,
            Supplier<T> defaultSupplier,
            IntFunction<T> resolver,
            String rangeLabel) {
        while (true) {
            System.out.print(prompt);
            if (!scanner.hasNextLine()) {
                return defaultSupplier.get();
            }

            final String input;
            try {
                input = scanner.nextLine().trim();
            } catch (NoSuchElementException eof) {
                return defaultSupplier.get();
            }

            if (input.equals("0")) {
                return defaultSupplier.get();
            }

            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= size) {
                    return resolver.apply(choice);
                }
                System.out.println(
                        "Пожалуйста, выберите число от 0 до " + size + (rangeLabel == null ? "" : " " + rangeLabel));
            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите число");
            }
        }
    }
}
