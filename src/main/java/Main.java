import logger.Logger;
import logger.LoggerFile;
import organism.Antilope;
import organism.Human;
import organism.Wolf;
import ui.MainFrame;
import world.World;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        //handling problems with threads
        SwingUtilities.invokeLater(() -> {
            World world = new World(20, 20);

            LoggerFile fileLogger = new LoggerFile("system_log.txt");
            world.setFileLogger(fileLogger);

            world.log(Logger.Level.INFO, "World created successfully, size: " + world.getWidth() + "x" + world.getHeight());

            world.addOrganism(new Human(world));
            world.addOrganism(new Antilope(1, 2, world));
            world.addOrganism(new Wolf(5, 6, world));
            world.addOrganism(new Wolf(3, 4, world));
            world.addOrganism(new Antilope(7, 8, world));
            world.addOrganism(new Antilope(2, 2, world));
            world.addOrganism(new Wolf(4, 6, world));
            world.addOrganism(new Antilope(5, 2, world));


            new MainFrame(world);

            fileLogger.flush();
        });
    }
}