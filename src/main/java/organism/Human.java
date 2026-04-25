package organism;

import world.World;

public class Human extends Animal {
    public Human(int positionX, int positionY, World world) {
        super(5, 4, positionX, positionY, world);
    }

    @Override
    protected Animal createInstance(int x, int y) {
        return new Human(x, y, world);
    }
}
