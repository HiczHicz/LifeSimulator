package organism;

import world.World;

import java.awt.*;


public abstract class Organism {
    protected int strength;
    protected int initiative;
    protected int positionX;
    protected int positionY;
    protected int age;
    protected World world;

    public Organism(int strength, int initiative, int positionX, int positionY, World world) {
        this.strength = strength;
        this.initiative = initiative;
        this.positionX = positionX;
        this.positionY = positionY;
        this.world = world;
    }


    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public int getStrength() {
        return strength;
    }

    public int getInitiative() {
        return initiative;
    }

    public int getAge() {
        return age;
    }

    public void setPosition(int x, int y) {
        this.positionX = x;
        this.positionY = y;
    }

    public void action() {

    }

    public void collision(Organism attacker) {

    }

    public int increaseAge() {
        return ++age;
    }

    public void draw(Graphics g, int x, int y, int size) {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ", position (X,Y): (" + this.positionX + ", " + this.positionY + ")";
    }

}
