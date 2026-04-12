package organism;

import world.World;

import java.awt.*;

public abstract class Organism {
    protected int strength;
    protected int initiative;
    protected int positionX;
    protected int positionY;
    protected World world;

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void action() {

    }

    public void collision() {

    }

    public void draw(Graphics g, int x, int y, int size) {

    }

}
