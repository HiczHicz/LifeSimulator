import logger.Logger;
import logger.LoggerComposite;
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

            LoggerComposite multiLogger = new LoggerComposite();

            //file logger
            multiLogger.addLogger(new LoggerFile("system_log.txt"));

            //making logger composite
            World world = new World(20, 20);
            world.setLogger(multiLogger);

            //
            multiLogger.log(Logger.Level.INFO, "World created successfully");

            world.addOrganism(new Wolf(1, 2, world));
            world.addOrganism(new Sheep(3, 4, world));

            new MainFrame(world);

            multiLogger.flush();
        });
    }
}