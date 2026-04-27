package organism;

import world.World;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Human extends Animal {
    private int nextDirection = -1; //stores key that was clicked on keyboard

    public Human(World world) {
        super(5, 4, 0, 0, world); //human always start on (0,0)
    }

    @Override
    protected Animal createInstance(int x, int y) {
        return new Human(world);
    }

    @Override
    public Color getColor() {
        return Color.PINK;
    }

    public void setNextDirection(int keyCode) {
        this.nextDirection = keyCode;
    }

    @Override
    public void action() {
        int nextX = positionX;
        int nextY = positionY;
        //reacting on arrows
        switch (nextDirection) {
            case KeyEvent.VK_UP -> nextY--;
            case KeyEvent.VK_DOWN -> nextY++;
            case KeyEvent.VK_LEFT -> nextX--;
            case KeyEvent.VK_RIGHT -> nextX++;
        }

        //resetting after moviung
        nextDirection = -1;

        if (nextX >= 0 && nextX < world.getWidth() && nextY >= 0 && nextY < world.getHeight()) {
            Organism target = world.getOrganismAt(nextX, nextY);
            if (target != null && target != this) {
                target.collision(this);
            } else {
                this.positionX = nextX;
                this.positionY = nextY;
            }
        }
    }

    public void draw(Graphics g, int x, int y, int size) {
        g.setColor(this.getColor());
        g.fillRect(x, y, size, size);
        drawSymbol(g, x, y, size, "H");

        //red border
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
        //setting weight of the border
        g2d.setStroke(new BasicStroke(4));
        g2d.drawRect(x, y, size, size);
        //resetting border weight to 1
        g2d.setStroke(new BasicStroke(1));
    }
}
