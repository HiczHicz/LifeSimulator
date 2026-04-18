package logger;

import javax.swing.*;

public class LoggerGame extends Logger {
    private final JTextArea logArea;

    public LoggerGame(JTextArea logArea) {
        this.logArea = logArea;
    }

    @Override
    public void flush() {
        if (logArea == null) return;

        for (String logger : logs) {
            logArea.append(logger + "\n");
        }

        //automatic scroll to the bottom
        logArea.setCaretPosition(logArea.getDocument().getLength());

        clear();
    }
}