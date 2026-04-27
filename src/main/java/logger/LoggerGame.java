package logger;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class LoggerGame extends Logger {
    private final JTextArea logArea;
    private List<StringBuilder> turnHistory = new ArrayList<>();
    private int currentTurn = -1;

    public LoggerGame(JTextArea logArea) {
        this.logArea = logArea;
        nextTurn();
    }

    public void nextTurn() {
        currentTurn++;
        turnHistory.add(new StringBuilder());
    }

    @Override
    public void log(Level level, String info) {
        if (currentTurn >= 0) {
            turnHistory.get(currentTurn).append("[").append(level).append("] ").append(info).append("\n");
        }
    }

    public String getTurnLog(int turnIndex) {
        if (turnIndex >= 0 && turnIndex < turnHistory.size()) {
            return turnHistory.get(turnIndex).toString();
        }
        return "";
    }

    public int getHistorySize() {
        return turnHistory.size();
    }

    public void clearHistory() {
        turnHistory.clear();
        currentTurn = -1;
    }

    public void prepareHistoryAfterLoad() {
        nextTurn();
    }
}