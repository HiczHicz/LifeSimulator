package organism;

import world.World;

import java.awt.*;
import java.util.Random;

public abstract class Plant extends Organism {
    protected int spreadProbability = 10;

    public Plant(int strength, int positionX, int positionY, World world) {
        super(strength, 0, positionX, positionY, world);
    }


    @Override
    public void action() {
        Random rand = new Random();
        //spread probability
        if (rand.nextInt(100) < spreadProbability) {
            spread();
        }
    }

    protected void spread() {
        Point freeCell = findFreeNeighbor();
        if (freeCell != null) {
            Organism newPlant = createInstance(freeCell.x, freeCell.y);
            world.addOrganism(newPlant);
            world.log(logger.Logger.Level.INFO, this + " spreaded to (" + freeCell.x + ", " + freeCell.y + ")");
        }
    }

    @Override
    public void collision(Organism attacker) {
        //standard collision method
        if (attacker.getStrength() >= this.getStrength()) {
            world.log(logger.Logger.Level.DEATH, attacker + " ate " + this);
            world.removeOrganism(this);
            attacker.setPosition(this.positionX, this.positionY);
        } else {
            //if plant is strogner
            world.log(logger.Logger.Level.DEATH, attacker + " died eating " + this);
            world.removeOrganism(attacker);
        }
    }

    protected abstract Plant createInstance(int x, int y);

}

