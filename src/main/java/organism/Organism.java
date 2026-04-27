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

    //for file saving and loading
    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getInitiative() {
        return initiative;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public abstract Color getColor();

    public void setPosition(int x, int y) {
        this.positionX = x;
        this.positionY = y;
    }

    public abstract void action();

    public abstract void collision(Organism attacker);

    public int increaseAge() {
        return ++age;
    }

    public abstract void draw(Graphics g, int x, int y, int size);

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ", position (X,Y): (" + this.positionX + ", " + this.positionY + ")";
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

    protected void drawSymbol(Graphics g, int x, int y, int size, String symbol) {
        g.setColor(Color.BLACK);

        // font scalling - 70% of cellsize
        int fontSize = (int) (size * 0.7);
        g.setFont(new Font("Arial", Font.BOLD, fontSize));

        FontMetrics fm = g.getFontMetrics();

        //centering the text
        int textX = x + (size - fm.stringWidth(symbol)) / 2;
        int textY = y + ((size - fm.getHeight()) / 2) + fm.getAscent();

        g.drawString(symbol, textX, textY);
    }
}
