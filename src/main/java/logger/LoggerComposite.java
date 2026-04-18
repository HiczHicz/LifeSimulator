package logger;

import java.util.ArrayList;
import java.util.List;

public class LoggerComposite extends Logger {
    private final List<Logger> loggers = new ArrayList<>();

    public void addLogger(Logger logger) {
        loggers.add(logger);
    }

    @Override
    public void log(Level level, String info) {
        for (Logger l : loggers) {
            l.log(level, info);
        }
    }

    @Override
    public void flush() {
        for (Logger l : loggers) {
            l.flush();
        }
    }
}
