import logger.Logger;
import logger.LoggerFile;
import organism.Sheep;
import organism.Wolf;
import ui.MainFrame;
import world.World;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        //handling problems with threads
        SwingUtilities.invokeLater(() -> {
            World world = new World(15, 15);

            LoggerFile fileLogger = new LoggerFile("system_log.txt");
            world.setFileLogger(fileLogger);

            world.log(Logger.Level.INFO, "World created successfully, size: " + world.getWidth() + "x" + world.getHeight());

            world.addOrganism(new Wolf(1, 2, world));
            world.addOrganism(new Wolf(5, 6, world));
            world.addOrganism(new Sheep(3, 4, world));
            world.addOrganism(new Sheep(7, 8, world));
            world.addOrganism(new Sheep(2, 2, world));
            world.addOrganism(new Sheep(6, 6, world));

            new MainFrame(world);

            fileLogger.flush();
        });
    }
}