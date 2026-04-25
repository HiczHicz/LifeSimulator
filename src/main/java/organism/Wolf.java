package organism;

import world.World;

import java.awt.*;

public class Wolf extends Animal {

    public Wolf(int positionX, int positionY, World world) {
        // losowanie punktów X, Y - aby ukryć logikę przed graczem, do zrobienia w przyszłości
        super(9, 5, positionX, positionY, world);
    }

    @Override
    protected Animal createInstance(int x, int y) {
        return new Wolf(x, y, world);
    }

    @Override
    public void draw(Graphics g, int x, int y, int size) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(x, y, size, size);
        g.setColor(Color.WHITE);
        g.drawString("W", x + size / 3, y + 2 * size / 3); // Symbol ASCII
        g.setColor(Color.BLACK);
    }
}
