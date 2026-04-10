package organism;

public abstract class Animal extends Organism {

    private void move() {
        int xVector;
        int yVector;

        do {
            xVector = (int) Math.floor((Math.random() * 3) - 1);
            yVector = (int) Math.floor((Math.random() * 3) - 1);
        } while ((xVector == yVector && xVector == 0) || (positionX + xVector <= 0 || positionX + xVector > world.getWidth() || positionY + yVector <= 0 || positionY + yVector > world.getHeight()));

        positionX += xVector;
        positionY += yVector;
    }

    @Override
    public void action() {
        move();
    }

    @Override
    public void collision() {

    }
}
