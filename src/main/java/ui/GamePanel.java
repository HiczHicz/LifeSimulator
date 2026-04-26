package ui;

import organism.Organism;
import world.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GamePanel extends JPanel {
    private final int cellSize = 30;
    private World world;

    public GamePanel(World world) {
        this.world = world;
        setPreferredSize(new Dimension(world.getWidth() * cellSize, world.getHeight() * cellSize));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Obliczamy skalę (tak samo jak w paintComponent)
                double cellWidth = (double) getWidth() / world.getWidth();
                double cellHeight = (double) getHeight() / world.getHeight();

                // Wyznaczamy, w którą komórkę kliknięto
                int x = (int) (e.getX() / cellWidth);
                int y = (int) (e.getY() / cellHeight);

                // Sprawdzamy zajętość pola
                if (world.getOrganismAt(x, y) != null) {
                    return;
                }

                // Otwieramy nowe, sprytne okienko
                // Zakładam, że masz dostęp do MainFrame (jako rodzica)
                MainFrame parent = (MainFrame) SwingUtilities.getWindowAncestor(GamePanel.this);
                new AddOrganismDialog(parent, world, x, y).setVisible(true);
            }
        });
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