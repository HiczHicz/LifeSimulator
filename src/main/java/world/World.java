package world;

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

    public void addOrganism(Organism organism) {
        organisms.add(organism);
    }

    public void turn() {

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
