package logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LoggerFile extends Logger {
    private final static String LOGS_PATH = "logs/"; //default location of logs
    private String fileName;

    public LoggerFile(String fileName) {
        this.fileName = fileName;

        //reseting the file with application starting
        try (FileWriter fw = new FileWriter(LOGS_PATH + fileName, false)) {
        } catch (IOException e) {
            System.err.println("Can't reset log file: " + e.getMessage());
        }
    }

    public void flush() {
        //append true, so the file doesnt clear after turn()
        try (FileWriter fw = new FileWriter(LOGS_PATH + fileName, true);
             PrintWriter out = new PrintWriter(fw)) {

            for (String log : logs) {
                out.println(log);
            }
            clear();

        } catch (IOException e) {
            System.err.println("Can’t flush the log. Error: " + e.getMessage());
        }
    }
}
