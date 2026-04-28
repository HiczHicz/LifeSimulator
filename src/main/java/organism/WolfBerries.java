package organism;

import world.World;

import java.awt.*;

public class WolfBerries extends Plant {
    public static final Color STATIC_COLOR = new Color(75, 0, 130, 120);

    public WolfBerries(int x, int y, World world) {
        // Wilcze jagody mają zazwyczaj wysoką siłę (np. 99),
        // aby podkreślić ich zabójczy charakter w silniku gry
        super(99, x, y, world);
    }

    @Override
    public void collision(Organism attacker) {
        // 1. LOGUJEMY ŚMIERĆ NAPASTNIKA
        world.log(logger.Logger.Level.DEATH, attacker + " ate WolfBerries and died instantly!");

        // 2. USUWAMY NAPASTNIKA (zwierzę ginie)
        world.removeOrganism(attacker);

        // 3. USUWAMY RÓWNIEŻ TĘ ROŚLINĘ (została zjedzona)
        world.removeOrganism(this);
    }

    @Override
    public Color getColor() {
        return STATIC_COLOR;
    }

    @Override
    public void draw(Graphics g, int x, int y, int size) {
        // 1. RYSOWANIE PRZEZROCZYSTEGO TŁA
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