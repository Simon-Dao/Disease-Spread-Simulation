package program;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;

public class Person {

    private boolean INFECTED = false;
    public boolean WAS_OR_IS_INFECTED = false;
    private boolean dead = false;
    private boolean recovered = false;

    public int id;

    public long timeToDeath;
    private long chanceOfDeath;

    private long start;
    private long finish;
    public long elapsedTime;

    private Pane pane;

    public Circle circle;
    public Vect position = new Vect(this);

    private Random r = new Random();

    public Person(double x, double y) {
        position.x = x;
        position.y = y;
    }

    public Person(double x, double y, Pane pane) {
        this.pane = pane;
        position.x = x;
        position.y = y;

        init();
    }

    public Person(double mag, double x, double y, Pane pane) {
        position.mag = mag;
        position.x = x;
        position.y = y;
        this.pane = pane;

        init();
    }

    public Person(double mag, double dirAngle, double x, double y, Pane pane) {

        this.pane = pane;

        init();

        position.mag = mag;
        position.dirAngle = dirAngle;
        position.x = x;
        position.y = y;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean isINFECTED() {
        return INFECTED;
    }

    public void setINFECTED(boolean INFECTED) {
        this.INFECTED = INFECTED;

        start = Main.timePassed;
    }

    public boolean isRecovered() {
        return recovered;
    }

    public void setRecovered(boolean recovered) {
        this.recovered = recovered;
    }

    //getters and setters
    public Pane getPane() {
        return pane;
    }
    public void setPane(Pane pane) {
        this.pane = pane;
    }
    public double getMag() {
        return position.mag;
    }
    public void setMag(double mag) {
        position.mag = mag;
    }
    public double getX() {
        return position.x;
    }
    public void setX(double x) {
        position.x = x;
    }
    public double getY() {
        return position.y;
    }
    public void setY(double y) {
        position.y = y;
    }
    public double getDirAngle() {
        return position.dirAngle;
    }
    public void setDirAngle(double dirAngle) {
        position.dirAngle = dirAngle;
    }

    //code that runs when the class is initialized
    private void init() {
        position = new Vect(this);
        circle = new Circle(position.x, position.y, 10);
        circle.setFill(Color.GREEN);
        pane.getChildren().add(circle);

        timeToDeath = randomI(Ligma.MIN_INFECTION_TIME,Ligma.MAX_INFECTION_TIME) * 1000;
        chanceOfDeath = r.nextInt(100);
    }

    private int randomI(int rangeMin, int rangeMax) {
        if (rangeMin >= rangeMax) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((rangeMax - rangeMin) + 1) + rangeMin;
    }

    //draws the person
    public void draw() {

        if (INFECTED) {
            circle.setFill(Color.YELLOW);
            finish = Main.timePassed;

            elapsedTime += finish - start;

            WAS_OR_IS_INFECTED = true;

            //after a certain amount of time either the person recovers or dies
            if((elapsedTime) >= timeToDeath && chanceOfDeath <= Ligma.MORTALITY_RATE) {
                die();
            } else if(chanceOfDeath > Ligma.MORTALITY_RATE && (elapsedTime) >= timeToDeath ) {

                INFECTED = false;
                recovered = true;
            }
            //System.out.println(System.currentTimeMillis() - startingTime);
        } else if(!dead && recovered){
            circle.setFill(Color.web("6E54C9"));
        } else {
            circle.setFill(Color.GREEN);
        }

        circle.setCenterX(position.x);
        circle.setCenterY(position.y);
    }

    public void randomMovement() {
        position.randomMovement();
    }

    public void moveTowards(Vect mouse) {
        position.moveTowards(mouse);
    }

    public void die() {
        dead = true;
        circle.setFill(Color.RED);
    }

}


