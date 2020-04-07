package program;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;

public class Person {

    private boolean INFECTED = false;
    public boolean WAS_OR_IS_INFECTED = false;
    private boolean dead = false;
    private boolean recovered = false;

    public int id;
    public static int PERSON_SIZE = 4;

    public long timeOfInfection;
    private long chanceOfDeath;

    private long start;
    private long finish;
    public long elapsedTime;

    private Pane pane;

    public Circle circle;
    public Vect positionVector = new Vect(this);

    private Random r = new Random();

    public Person(double x, double y) {
        positionVector.x = x;
        positionVector.y = y;
    }

    public Person(double x, double y, Pane pane) {
        this.pane = pane;
        positionVector.x = x;
        positionVector.y = y;

        init();
    }

    public Person(double mag, double x, double y, Pane pane) {
        positionVector.mag = mag;
        positionVector.x = x;
        positionVector.y = y;
        this.pane = pane;

        init();
    }

    public Person(double mag, double dirAngle, double x, double y, Pane pane) {

        this.pane = pane;

        init();

        positionVector.mag = mag;
        positionVector.dirAngle = dirAngle;
        positionVector.x = x;
        positionVector.y = y;
    }

    //GETTERS AND SETTERS///////////////////////////////////////////////////////////////////////////////////////////////
    public boolean isINFECTED() {
        return INFECTED;
    }

    /**
     * sets the person's infected boolean
     * and starts the time of infection timer
     *
     * @param INFECTED
     */
    public void setINFECTED(boolean INFECTED) {
        this.INFECTED = INFECTED;
        start = Main.timePassed;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean isRecovered() {
        return recovered;
    }

    public void setRecovered(boolean recovered) {
        this.recovered = recovered;
    }

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public double getMag() {
        return positionVector.mag;
    }

    public void setMag(double mag) {
        positionVector.mag = mag;
    }

    public double getX() {
        return positionVector.x;
    }

    public void setX(double x) {
        positionVector.x = x;
    }

    public double getY() {
        return positionVector.y;
    }

    public void setY(double y) {
        positionVector.y = y;
    }

    public double getDirAngle() {
        return positionVector.dirAngle;
    }

    public void setDirAngle(double dirAngle) {
        positionVector.dirAngle = dirAngle;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //code that runs when the class is initialized
    private void init() {
        positionVector = new Vect(this);
        circle = new Circle(positionVector.x, positionVector.y, PERSON_SIZE);
        circle.setFill(Color.GREEN);
        pane.getChildren().add(circle);

        setRandomValues();
    }

    //sets the time to death
    //sets the chance of death
    private void setRandomValues() {
        timeOfInfection = randomI(Ligma.MIN_INFECTION_TIME, Ligma.MAX_INFECTION_TIME) * 1000;
        chanceOfDeath = r.nextInt(100);
    }

    /**
     * @param rangeMin
     * @param rangeMax
     * @return a random integer between the min and max range
     */
    private int randomI(int rangeMin, int rangeMax) {
        if (rangeMin >= rangeMax) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((rangeMax - rangeMin) + 1) + rangeMin;
    }

    //draws and updates the person's location
    public void update() {

        draw();

        circle.setCenterX(positionVector.x);
        circle.setCenterY(positionVector.y);
    }

    public void draw() {

        if (INFECTED) {
            circle.setFill(Color.YELLOW);

            //sets the elapsed time
            finish = Main.timePassed;
            elapsedTime += finish - start;

            //boolean used to count the total cases
            WAS_OR_IS_INFECTED = true;

            /*

            after a certain period of time, the person either dies or recovers
             */

            //if the period of infection is over and their chance of death is within the mortality rate
            // the person will die
            if ((elapsedTime) >= timeOfInfection && chanceOfDeath <= Ligma.MORTALITY_RATE) {
                dead = true;
                circle.setFill(Color.RED);

              // if the period of infection is over and their chance of death is not within the mortality rate
              // the person recovers from the disease and is no longer infected
            } else if (chanceOfDeath > Ligma.MORTALITY_RATE && (elapsedTime) >= timeOfInfection) {
                INFECTED = false;
                recovered = true;
            }

          // if the person is alive and recovered set the color to purple
        } else if (!dead && recovered) {
            circle.setFill(Color.web("6E54C9"));

          // if the person is alive and not come into contact with the disease
          // set color to green
        } else {
            circle.setFill(Color.GREEN);
        }
    }

    // tells the vector to randomly change direction
    public void randomMovement() {
        positionVector.randomMovement();
    }

    public void moveTowards(Vect mouse) {
        positionVector.moveTowards(mouse);
    }

}


