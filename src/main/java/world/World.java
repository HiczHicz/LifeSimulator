package world;

import logger.Logger;
import logger.LoggerGame;
import organism.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    public void saveToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new File(filename))) {
            writer.println(width + " " + height + " " + turnCounter);

            //all info about organism in one line
            for (Organism o : organisms) {
                writer.print(o.getClass().getSimpleName() + " " + o.getPositionX() + " " +
                        o.getPositionY() + " " + o.getStrength() + " " + o.getAge());

                if (o instanceof Human h) {
                    writer.print(" " + h.getAbilityCooldown() + " " + h.getAbilityDuration());
                }

                writer.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            if (!scanner.hasNextInt()) return; //if file empty

            organisms.clear();
            this.width = scanner.nextInt();
            this.height = scanner.nextInt();
            this.turnCounter = scanner.nextInt();

            if (gameLogger instanceof LoggerGame lg) {
                lg.clearHistory(); //cleaning old history

                lg.prepareHistoryAfterLoad();

                // logging turn counter and opening log
                log(Logger.Level.INFO, "--- Turn " + turnCounter + " ---");
                log(Logger.Level.INFO, "GAME LOADED FROM FILE: " + filename);
            }

            //we read the file until there is new data - ignores empty lines
            while (scanner.hasNext()) {
                String type = scanner.next(); //reads name
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                int strength = scanner.nextInt();
                int age = scanner.nextInt();

                Organism o = createOrganismByType(type, x, y);
                if (o != null) {
                    o.setStrength(strength);
                    o.setAge(age);

                    //human has two additional variables
                    if (o instanceof Human h) {
                        int cooldown = scanner.nextInt();
                        int duration = scanner.nextInt();
                        h.setAbilityParams(cooldown, duration);
                    }
                    organisms.add(o);

                    //logging all organism added while loading
                    log(Logger.Level.INFO, "Organism added: " + o.toString());
                }
            }
        } catch (IOException e) {
            System.out.println("BŁĄD PODCZAS WCZYTYWANIA: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //auxilary method to create object by type
    private Organism createOrganismByType(String type, int x, int y) {
        return switch (type) {
            case "Wolf" -> new Wolf(x, y, this);
            case "Sheep" -> new Sheep(x, y, this);
            case "Turtle" -> new Turtle(x, y, this);
            case "Fox" -> new Fox(x, y, this);
            case "Antilope" -> new Antilope(x, y, this);
            case "Human" -> new Human(this);

            default -> null;
        };
    }

    public int getTurnCounter() {
        return this.turnCounter;
    }
}
