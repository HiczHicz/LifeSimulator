package organism;


import world.World;

import java.awt.*;

public class Guarana extends Plant {
    public static final Color STATIC_COLOR = new Color(255, 0, 0, 120);

    public Guarana(int x, int y, World world) {
        super(0, x, y, world);
    }

    @Override
    public void collision(Organism attacker) {
        //increasing strength of attacker by 3
        int oldStrength = attacker.getStrength();
        attacker.setStrength(oldStrength + 3);

        //log special effect
        world.log(logger.Logger.Level.SPECIAL, attacker + " ate Guarana! Strength increased from " +
                oldStrength + " to " + attacker.getStrength());

        //standard plant remove
        world.removeOrganism(this);
        attacker.setPosition(this.positionX, this.positionY);
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


        drawSymbol(g, x, y, size, "Gu"); // 'u' jak gUarana (G jest już dla trawy)
    }

    @Override
    protected Plant createInstance(int x, int y) {
        return new Guarana(x, y, world);
    }

}