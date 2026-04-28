package organism;

import logger.Logger;
import world.World;

import java.awt.*;

public class Antilope extends Animal {
    public static final Color STATIC_COLOR = Color.ORANGE.darker();

    public Antilope(int positionX, int positionY, World world) {
        super(4, 4, positionX, positionY, world);
    }

    @Override
    protected Point getNewPositionToMove() {
        int xVector;
        int yVector;
        int nextX, nextY;

        do {
            xVector = (int) (Math.random() * 4) - 2;
            yVector = (int) (Math.random() * 4) - 2;

            nextX = positionX + xVector;
            nextY = positionY + yVector;

            //loop repeats when we get (0,0) (no move) or nextX/Y is out of bounds
        } while ((xVector == 0 && yVector == 0) ||
                (nextX < 0 || nextX >= world.getWidth() ||
                        nextY < 0 || nextY >= world.getHeight()));

        return new Point(nextX, nextY);
    }

    @Override
    public void collision(Organism attacker) {
        if (attacker instanceof Human h && h.getAbilityDuration() > 0) {
            world.log(Logger.Level.SPECIAL, "Holocaust is too fast! Antelope cannot escape.");
            world.removeOrganism(this);
            return;
        }
        if (this.getClass().equals(attacker.getClass())) {

            //breeding
            this.breed();
        } else {
            //fight
            world.log(Logger.Level.ATTACK, attacker + " attacks " + this);

            //antilope dodges in 50% of cases
            if (Math.random() * 101 > 50) {
                //attacker changes its position to previous antilope position
                attacker.setPosition(this.positionX, this.positionY);

                //looking for free cell for antilope to move
                Point cellToMove = this.findFreeNeighbor();
                this.setPosition(cellToMove.x, cellToMove.y);

                world.log(Logger.Level.SPECIAL, this + " dodged " + attacker + " and moved to: (" + cellToMove.x + ", " + cellToMove.y + ")");


            } else if (attacker.getStrength() >= this.getStrength()) {
                //attacker wins
                world.log(Logger.Level.DEATH, this + " didn't dodge and was killed by " + attacker);
                world.removeOrganism(this);
                attacker.setPosition(this.positionX, this.positionY);
            } else {
                //attacker loses
                world.log(Logger.Level.DEATH, attacker + " killed by " + this);
                world.removeOrganism(attacker);
            }
        }
    }


    @Override
    protected Animal createInstance(int x, int y) {
        return new Antilope(x, y, world);
    }

    @Override
    public void draw(Graphics g, int x, int y, int size) {
        g.setColor(this.getColor());
        g.fillRect(x, y, size, size);
        drawSymbol(g, x, y, size, "A");
    }


    @Override
    public Color getColor() {
        return STATIC_COLOR;
    }

}
