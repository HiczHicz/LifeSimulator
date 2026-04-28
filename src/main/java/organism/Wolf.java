package organism;

import world.World;

import java.awt.*;

public class Wolf extends Animal {
    public static final Color STATIC_COLOR = Color.DARK_GRAY;

    public Wolf(int positionX, int positionY, World world) {
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
        return STATIC_COLOR;
    }
}
