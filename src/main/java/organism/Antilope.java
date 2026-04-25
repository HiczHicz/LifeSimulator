package organism;

import logger.Logger;
import world.World;

import java.awt.*;

public class Antilope extends Animal {
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

//    @Override
//    public void collision(Organism attacker) {
//        if (this.getClass().equals(attacker.getClass())) {
//            //breeding
//            this.breed();
//        } else {
//            //fight
//            world.log(Logger.Level.ATTACK, attacker + " attacks " + this);
//
//            if (attacker.getStrength() >= this.getStrength()) {
//                //attacker wins
//                world.log(Logger.Level.DEATH, this + " killed by " + attacker);
//                world.removeOrganism(this);
//                attacker.setPosition(this.positionX, this.positionY);
//            } else {
//                //attacker loses
//                world.log(Logger.Level.DEATH, attacker + " killed by " + this);
//                world.removeOrganism(attacker);
//            }
//        }
//    }


    @Override
    protected void breed() {
        Point p = findFreeNeighbor();
        if (p != null) {
            world.addOrganism(new Antilope(p.x, p.y, world));
            world.log(Logger.Level.BREED, "New Antilope born at (" + p.x + "," + p.y + ")");
        }
    }

    @Override
    public void draw(Graphics g, int x, int y, int size) {
        g.setColor(Color.ORANGE.darker());
        g.fillRect(x, y, size, size);
        g.setColor(Color.WHITE);
        g.drawString("A", x + size / 3, y + 2 * size / 3); // Symbol ASCII
        g.setColor(Color.BLACK);
    }

}
