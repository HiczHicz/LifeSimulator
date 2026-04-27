package world;

import logger.Logger;
import logger.LoggerGame;
import organism.Human;
import organism.Organism;

import java.util.ArrayList;
import java.util.List;

public class World {

    private int width;
    private int height;
    private List<Organism> organisms = new ArrayList<>();
    private Logger fileLogger;
    private Logger gameLogger;
    private int turnCounter = 0;


    public World() {
        this.width = 5;
        this.height = 5;
    }

    public World(int width, int height) {
        this.width = width;
        this.height = height;
    }

    //getter needed for turn 0 logs
    public List<Organism> getOrganisms() {
        return this.organisms;
    }

    //FileLogger
    public Logger getFileLogger() {
        return this.fileLogger;
    }

    public void setFileLogger(Logger logger) {
        this.fileLogger = logger;
    }

    //GameLogger
    public Logger getGameLogger() {
        return this.gameLogger;
    }

    public void setGameLogger(Logger gameLogger) {
        this.gameLogger = gameLogger;
    }

    public void log(Logger.Level level, String info) {
        if (fileLogger != null) fileLogger.log(level, info);
        if (gameLogger != null) gameLogger.log(level, info);
    }

    //handling organism
    public void addOrganism(Organism organism) {
        if (organism instanceof Human) {
            Organism resident = getOrganismAt(0, 0);
            if (resident != null) {
                removeOrganism(resident); // Usuń pechowca, który tam stał
            }
        }
        organisms.add(organism);
        log(Logger.Level.INFO, "Organism added: " + organism.toString());
    }

    public Organism getOrganismAt(int x, int y) {
        for (Organism o : organisms) {
            if (o.getPositionX() == x && o.getPositionY() == y) {
                return o;
            }
        }
        return null;
    }

    public void removeOrganism(Organism organism) {
        organisms.remove(organism);
    }

    public void turn() {


        if (gameLogger instanceof LoggerGame) {
            ((LoggerGame) gameLogger).nextTurn();
        }

        turnCounter += 1;
        log(Logger.Level.INFO, "---Turn " + turnCounter + "---");

        for (Organism o : organisms) {
            o.increaseAge();
        }

        //sorting by initiative, then by age (descending)
        organisms.sort((o1, o2) -> {
            if (o1.getInitiative() != o2.getInitiative()) {
                return Integer.compare(o2.getInitiative(), o1.getInitiative());
            }
            return Integer.compare(o2.getAge(), o1.getAge());
        });

        //turn
        List<Organism> currentOrganisms = new ArrayList<>(organisms);
        for (Organism o : currentOrganisms) {
            //check if the organism is still in the world (it might have been removed during another organism's action)
            if (organisms.contains(o)) {
                o.action();
            }
        }
    }


    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
