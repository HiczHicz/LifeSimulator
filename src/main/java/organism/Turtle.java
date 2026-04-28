package organism;

import logger.Logger;
import world.World;

import java.awt.*;

public class Turtle extends Animal {

    public static final Color STATIC_COLOR = Color.GREEN;

    public Turtle(int positionX, int positionY, World world) {
        super(2, 1, positionX, positionY, world);
    }

    @Override
    protected Point getNewPositionToMove() {
        int xVector;
        int yVector;
        int nextX, nextY;

        do {
            //turtle doesn't change its position in 75% of cases
            if (Math.random() * 101 < 75) {
                //world.log(Logger.Level.SPECIAL, this + " did not move");
                return new Point(positionX, positionY);
            }

            xVector = (int) (Math.random() * 3) - 1;
            yVector = (int) (Math.random() * 3) - 1;

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
        if (this.getClass().equals(attacker.getClass())) {
            //breeding
            this.breed();
        } else {
            //fight
            world.log(Logger.Level.ATTACK, attacker + " attacks " + this);

            if (attacker.getStrength() >= this.getStrength() && attacker.getStrength() >= 5) {
                //attacker wins
                world.log(Logger.Level.DEATH, this + " killed by " + attacker);
                world.removeOrganism(this);
                attacker.setPosition(this.positionX, this.positionY);
            } else if (attacker.getStrength() < 5) {
                //turtle dodges attacks from animal with strength >5
                world.log(Logger.Level.SPECIAL, this + " repelled " + attacker);
                //the attacker has to move to its previous cell - we dont set attacker position

            } else {
                //attacker loses
                world.log(Logger.Level.DEATH, attacker + " killed by " + this);
                world.removeOrganism(attacker);
            }
        }
    }

    @Override
    protected Animal createInstance(int x, int y) {
        return new Turtle(x, y, world);
    }

    @Override
    public void draw(Graphics g, int x, int y, int size) {
        g.setColor(this.getColor());
        g.fillRect(x, y, size, size);
        drawSymbol(g, x, y, size, "T");
    }

    @Override
    public Color getColor() {
        return STATIC_COLOR;
    }

}
