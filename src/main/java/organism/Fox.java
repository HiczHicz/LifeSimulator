package organism;

import logger.Logger;
import world.World;

import java.awt.*;

public class Fox extends Animal {
    public static final Color STATIC_COLOR = Color.ORANGE;

    public Fox(int positionX, int positionY, World world) {
        super(3, 7, positionX, positionY, world);
    }


    @Override
    protected Point getNewPositionToMove() {
        int xVector;
        int yVector;
        int nextX, nextY;

        do {
            xVector = (int) (Math.random() * 3) - 1;
            yVector = (int) (Math.random() * 3) - 1;

            nextX = positionX + xVector;
            nextY = positionY + yVector;

            if (world.getOrganismAt(nextX, nextY) != null && world.getOrganismAt(nextX, nextY).getStrength() > this.getStrength()) {
                world.log(Logger.Level.SPECIAL, this + " dodged " + world.getOrganismAt(nextX, nextY));
            }

            //loop repeats when we get (0,0) (no move) or nextX/Y is out of bounds
        } while ((xVector == 0 && yVector == 0) ||
                (nextX < 0 || nextX >= world.getWidth() ||
                        nextY < 0 || nextY >= world.getHeight()) ||
                (world.getOrganismAt(nextX, nextY) != null && world.getOrganismAt(nextX, nextY).getStrength() > this.getStrength()));

        return new Point(nextX, nextY);
    }


    @Override
    protected Animal createInstance(int x, int y) {
        return new Fox(x, y, world);
    }

    @Override
    public void draw(Graphics g, int x, int y, int size) {
        g.setColor(this.getColor());
        g.fillRect(x, y, size, size);
        drawSymbol(g, x, y, size, "F");
    }


    @Override
    public Color getColor() {
        return STATIC_COLOR;
    }
}
