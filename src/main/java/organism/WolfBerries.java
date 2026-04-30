package organism;

import world.World;

import java.awt.*;

public class WolfBerries extends Plant {
    public static final Color STATIC_COLOR = new Color(75, 0, 130, 120);

    public WolfBerries(int x, int y, World world) {
        super(99, x, y, world);
    }

    @Override
    public void collision(Organism attacker) {
        world.log(logger.Logger.Level.DEATH, attacker + " ate WolfBerries and died instantly!");
        world.removeOrganism(attacker);
        world.removeOrganism(this);
    }

    @Override
    public Color getColor() {
        return STATIC_COLOR;
    }

    @Override
    public void draw(Graphics g, int x, int y, int size) {
        g.setColor(this.getColor());
        g.fillRect(x, y, size, size);

        drawSymbol(g, x, y, size, "Wb"); // 'J' jak Jagody
    }

    @Override
    protected Plant createInstance(int x, int y) {
        return new WolfBerries(x, y, world);
    }

    @Override
    public String toString() {
        return "WolfBerries";
    }
}