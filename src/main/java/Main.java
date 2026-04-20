import logger.Logger;
import logger.LoggerFile;
import organism.Wolf;
import ui.MainFrame;
import world.World;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        //handling problems with threads
        SwingUtilities.invokeLater(() -> {
            World world = new World(10, 10);

            LoggerFile fileLogger = new LoggerFile("system_log.txt");
            world.setFileLogger(fileLogger);

            world.log(Logger.Level.INFO, "World created successfully, size: " + world.getWidth() + "x" + world.getHeight());

            world.addOrganism(new Wolf(1, 2, world));
            world.addOrganism(new Wolf(5, 6, world));
            world.addOrganism(new Wolf(3, 4, world));

            new MainFrame(world);

            fileLogger.flush();
        });
    }
}