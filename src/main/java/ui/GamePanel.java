package ui;

import organism.Organism;
import world.World;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private final int cellSize = 30;
    private World world;

    public GamePanel(World world) {
        this.world = world;
        setPreferredSize(new Dimension(world.getWidth() * cellSize, world.getHeight() * cellSize));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //set grid&organisms
        for (int y = 0; y < world.getHeight(); y++) {
            for (int x = 0; x < world.getWidth(); x++) {
                int px = x * cellSize;
                int py = y * cellSize;

                g.drawRect(px, py, cellSize, cellSize); //grid

                //get organism
                Organism organism = world.getOrganismAt(x, y);
                if (organism != null) {
                    organism.draw(g, px, py, cellSize); // drawing organism
                }
            }
        }
    }
}