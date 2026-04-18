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
            Logger fileLogger = new LoggerFile("systemlog.txt");

            World world = new World(20, 20);

            world.setLogger(fileLogger);

            fileLogger.log(Logger.Level.INFO, "World created: " + world.getWidth() + "x" + world.getHeight());

            world.addOrganism(new Wolf(1, 2, world));
            world.addOrganism(new Sheep(3, 4, world));

            new MainFrame(world);

            fileLogger.flush();

        });
    }
}