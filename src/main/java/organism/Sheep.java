package organism;

import world.World;

import java.awt.*;

public class Sheep extends Animal {
    public Sheep(int positionX, int positionY, World world) {
        super(4, 4, positionX, positionY, world);
    }

    @Override
    protected Animal createInstance(int x, int y) {
        return new Sheep(x, y, world);
    }

    @Override
    public void draw(Graphics g, int x, int y, int size) {
        g.setColor(this.getColor());
        g.fillRect(x, y, size, size);
        drawSymbol(g, x, y, size, "S");
    }


    @Override
    public Color getColor() {
        return Color.LIGHT_GRAY;
    }
}
