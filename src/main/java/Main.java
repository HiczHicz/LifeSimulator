import organism.Wolf;
import ui.MainFrame;
import world.World;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        //handling problems with threads
        SwingUtilities.invokeLater(() -> {
            World world = new World(20, 20);

            world.addOrganism(new Wolf(1, 2, world));

            new MainFrame(world);

        });
    }
}