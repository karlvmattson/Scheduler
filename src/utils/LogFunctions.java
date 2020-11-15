package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LogFunctions {

    /**
     * Adds a log entry to a given .txt file.
     * @param filename The name of the log file. Stores the log in the src/logs/ folder.
     * @param message The message to log.
     */
    public static void logEntry(String filename, String message) {
        try {
            FileWriter fileWriter = new FileWriter("src/logs/" + filename, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(message);
            printWriter.close();
        } catch (IOException ioException) {
            System.out.println("Error writing to log file.");
            System.out.println(ioException.getMessage());
        }
    }
}
