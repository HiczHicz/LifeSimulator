package organism;

import world.World;

import java.awt.*;

public class Grass extends Plant {
    public static final Color STATIC_COLOR = new Color(0, 100, 0, 150);

    public Grass(int x, int y, World world) {
        super(0, x, y, world);
    }

    @Override
    public Color getColor() {
        return STATIC_COLOR;
    }

    public void draw(Graphics g, int x, int y, int size) {
        //transparent background
        g.setColor(this.getColor());
        g.fillRect(x, y, size, size);

        //G symbol
        drawSymbol(g, x, y, size, "Gr");
    }

    @Override
    protected Plant createInstance(int x, int y) {
        return new Grass(x, y, world);
    }

}