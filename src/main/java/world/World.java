package world;

import logger.Logger;
import organism.Organism;

import java.util.ArrayList;
import java.util.List;

public class World {

    private int width;
    private int height;
    private List<Organism> organisms = new ArrayList<>();


    public World(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void addOrganism(Organism organism, Logger logger) {
        organisms.add(organism);
        logger.log(Logger.Level.INFO, "Organism succesfully added: " + organism.toString());

    }

    public Organism getOrganismAt(int x, int y) {
        for (Organism o : organisms) {
            if (o.getPositionX() == x && o.getPositionY() == y) {
                return o;
            }
        }
        return null;
    }

    public void turn() {
        for (Organism o : organisms) {
            o.action();
        }
    }

    public void drawWorld() {

    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
