package organism;

import world.World;

import java.awt.*;

public class Sheep extends Animal {
    public Sheep(int positionX, int positionY, World world) {
        super(4, 4, positionX, positionY, world);
    }

    @Override
    public void draw(Graphics g, int x, int y, int size) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x, y, size, size);
        g.setColor(Color.BLACK);
        g.drawString("O", x + size / 3, y + 2 * size / 3); // Symbol ASCII
        //g.setColor(Color.BLACK);
    }

    @Override
    public String toString() {
        return "Sheep, position (X,Y): (" + positionX + ", " + positionY + ")";
    }
}
