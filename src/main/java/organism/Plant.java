package organism;

import world.World;

public abstract class Plant extends Organism {

    public Plant(int strength, int positionX, int positionY, World world) {
        super(strength, 0, positionX, positionY, world);
    }
}

