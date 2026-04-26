package logger;

import java.util.ArrayList;

public abstract class Logger {
    protected ArrayList<String> logs = new ArrayList<>();

    // logs structure
    public void log(Level level, String info) {
        logs.add(level + ": " + info);
    }

    protected void clear() { //clears the log
        logs.clear();
    }

    public void flush() {

    }

    public enum Level {
        INFO,
        MOVE,
        ATTACK,
        DEATH,
        BREED,
        ERROR,
        SPECIAL
    }


}

