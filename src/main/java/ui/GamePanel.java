package ui;

import organism.Organism;
import world.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GamePanel extends JPanel {
    private World world;

    public GamePanel(World world) {
        this.world = world;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //calculating the scale (like in paintComponent)
                int cellWidth = getWidth() / world.getWidth();
                int cellHeight = getHeight() / world.getHeight();

                //deciding which cell was clicked
                int x = e.getX() / cellWidth;
                int y = e.getY() / cellHeight;

                if (x >= world.getWidth() || y >= world.getHeight()) return;

                //checking if the cell is occupied
                if (world.getOrganismAt(x, y) != null) {
                    return;
                }

                MainFrame parent = (MainFrame) SwingUtilities.getWindowAncestor(GamePanel.this);
                new AddOrganismDialog(parent, world, x, y).setVisible(true);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //calculating the size based on current panel size
        int cellWidth = getWidth() / world.getWidth();
        int cellHeight = getHeight() / world.getHeight();

        int cellSize = Math.min(cellWidth, cellHeight);

        for (int y = 0; y < world.getHeight(); y++) {
            for (int x = 0; x < world.getWidth(); x++) {

                // position calculated based on int
                int px = x * cellSize;
                int py = y * cellSize;

                // drawing grid
                g.setColor(Color.LIGHT_GRAY);
                g.drawRect(px, py, cellSize, cellSize);

                //getting and drawing orgasnisms
                Organism organism = world.getOrganismAt(x, y);
                if (organism != null) {
                    //cellsize to draw
                    organism.draw(g, px, py, cellSize);
                }
            }
        }
    }
}