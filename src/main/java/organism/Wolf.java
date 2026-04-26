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
        g.setColor(this.getColor());
        g.fillRect(x, y, size, size);
        drawSymbol(g, x, y, size, "W");
    }


    @Override
    public Color getColor() {
        return Color.DARK_GRAY;
    }
}
