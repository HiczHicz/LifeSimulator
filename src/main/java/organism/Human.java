package organism;

import logger.Logger;
import world.World;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Human extends Animal {
    private int nextDirection = -1; //stores key that was clicked on keyboard
    private int abilityDuration = 0; //special ability duration & cooldown
    private int abilityCooldown = 0;

    public Human(World world) {
        super(5, 4, 0, 0, world); //human always start on (0,0)
    }

    public int getAbilityCooldown() {
        return abilityCooldown;
    }

    public int getAbilityDuration() {
        return abilityDuration;
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

        if (abilityCooldown > 0) abilityCooldown--;

        if (abilityDuration == 0) {
            world.log(Logger.Level.SPECIAL, "Holocaust: ABILITY ENDED");
        }

        //special ability before move - so that it cleans animals nearby
        if (abilityDuration > 0) {
            specialAbilityHolocaust();
        }

        int oldX = positionX;
        int oldY = positionY;

        int nextX = positionX;
        int nextY = positionY;
        //reacting on arrows
        switch (nextDirection) {
            case KeyEvent.VK_UP -> nextY--;
            case KeyEvent.VK_DOWN -> nextY++;
            case KeyEvent.VK_LEFT -> nextX--;
            case KeyEvent.VK_RIGHT -> nextX++;
        }

        //resetting after moving
        nextDirection = -1;

        if (nextX >= 0 && nextX < world.getWidth() && nextY >= 0 && nextY < world.getHeight()) {
            Organism target = world.getOrganismAt(nextX, nextY);
            if (target != null && target != this) {
                if (abilityDuration > 0) {
                    //if ability works - animal is destroyed
                    world.log(Logger.Level.SPECIAL, "Holocaust destroys: " + target + " during Human movement");
                    world.removeOrganism(target);

                    //human moves to desired position
                    this.positionX = nextX;
                    this.positionY = nextY;
                } else {
                    //if ability doesnt work  - standard collision
                    target.collision(this);
                }
            } else if (nextX != oldX || nextY != oldY) {
                this.positionX = nextX;
                this.positionY = nextY;
                world.log(Logger.Level.INFO, "Human moved from (" + oldX + "," + oldY + ") to (" + positionX + "," + positionY + ")");
            }
        }

        //special ability AFTER moving -  cleaning new surrounding
        if (abilityDuration > 0) {
            specialAbilityHolocaust();
            abilityDuration--;
        }

    }

    @Override
    public void collision(Organism attacker) {
        //checking if special is activated
        if (this.getAbilityDuration() > 0) {
            world.log(Logger.Level.SPECIAL, "Holocaust destroys: " + attacker + " when trying to enter the field");
            world.removeOrganism(attacker);
            return;
        }

        //if ability not activated, standard collision
        super.collision(attacker);
    }

    private void specialAbilityHolocaust() {
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == y && x == 0) {
                    continue;
                }
                int targetX = positionX + x;
                int targetY = positionY + y;

                if (targetX >= 0 && targetX < world.getWidth() && targetY >= 0 && targetY < world.getHeight()) {
                    Organism victim = world.getOrganismAt(targetX, targetY);
                    if (victim != null) {
                        world.log(Logger.Level.SPECIAL, "Holocaust destroys: " + victim);
                        world.removeOrganism(victim);
                    }
                }
            }
        }
    }

    public void activateAbility() {
        if (abilityCooldown == 0) {
            abilityDuration = 5;
            abilityCooldown = 10; //duration 5 turns + 5 turns of cooldown
            world.log(Logger.Level.SPECIAL, "Holocaust activated");
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
