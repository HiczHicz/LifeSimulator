package organism;

import logger.Logger;
import world.World;

import java.awt.*;

public abstract class Animal extends Organism {

    public Animal(int strength, int initiative, int positionX, int positionY, World world) {
        super(strength, initiative, positionX, positionY, world);
    }

    private void move() {
        int oldX = positionX;
        int oldY = positionY;

        //new position
        Point newPos = getNewPositionToMove();

        Organism target = world.getOrganismAt(newPos.x, newPos.y);

        if (target != null && target != this) {
            //if occupied, collide
            target.collision(this);
        } else {
            //if free, move
            this.positionX = newPos.x;
            this.positionY = newPos.y;
            world.log(Logger.Level.MOVE, this + " moved from (" + oldX + "," + oldY + ") to (" + this.positionX + "," + this.positionY + ")");
        }
    }


    protected Point getNewPositionToMove() {
        int xVector;
        int yVector;
        int nextX, nextY;

        do {
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

    protected void breed() {

    }

    protected Point findFreeNeighbor() {
        int currentX = getPositionX();
        int currentY = getPositionY();

        for (int offsetX = -1; offsetX <= 1; offsetX++) {
            for (int offsetY = -1; offsetY <= 1; offsetY++) {
                //skip the current cell; we only want adjacent positions.
                if (offsetX == 0 && offsetY == 0) {
                    continue;
                }

                int candidateX = currentX + offsetX;
                int candidateY = currentY + offsetY;

                if (!isInsideWorld(candidateX, candidateY)) {
                    continue;
                }

                if (world.getOrganismAt(candidateX, candidateY) == null) {
                    return new Point(candidateX, candidateY);
                }
            }
        }
        return null;
    }

    private boolean isInsideWorld(int x, int y) {
        return x >= 0 && x < world.getWidth() && y >= 0 && y < world.getHeight();
    }


    @Override
    public void action() {
        move();
    }

    @Override
    public void collision(Organism attacker) {
        if (this.getClass().equals(attacker.getClass())) {
            //breeding
            this.breed();
        } else {
            //fight
            world.log(Logger.Level.ATTACK, attacker + " attacks " + this);

            if (attacker.getStrength() >= this.getStrength()) {
                //attacker wins
                world.log(Logger.Level.DEATH, this + " killed by " + attacker);
                world.removeOrganism(this);
                attacker.setPosition(this.positionX, this.positionY);
            } else {
                //attacker loses
                world.log(Logger.Level.DEATH, attacker + " killed by " + this);
                world.removeOrganism(attacker);
            }
        }
    }
}

