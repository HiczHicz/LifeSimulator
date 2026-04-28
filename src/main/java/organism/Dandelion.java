package organism;

import world.World;

import java.awt.*;

public class Dandelion extends Plant {
    // Statyczny kolor do legendy
    public static final Color STATIC_COLOR = new Color(255, 255, 0, 150); // Żółty z przezroczystością

    public Dandelion(int x, int y, World world) {
        super(0, x, y, world); // Mlecz ma siłę 0
    }

    @Override
    public void action() {
        // MLECZ MA 3 PRÓBY ROZPRZESTRZENIENIA SIĘ W JEDNEJ TURZE
        for (int i = 0; i < 3; i++) {
            super.action(); // Wywołujemy bazową logikę rośliny (szansa na rozsianie)
        }
    }

    @Override
    public Color getColor() {
        return STATIC_COLOR;
    }

    @Override
    public void draw(Graphics g, int x, int y, int size) {
        g.setColor(this.getColor());
        g.fillRect(x, y, size, size);
        drawSymbol(g, x, y, size, "D");
    }

    @Override
    protected Plant createInstance(int x, int y) {
        return new Dandelion(x, y, world);
    }


}