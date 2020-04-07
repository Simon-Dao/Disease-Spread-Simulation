package program;

import java.util.ArrayList;
import java.util.Random;

public class Vect {

    public double mag = 0;
    public double dirAngle = 0;

    public double x;
    public double y;

    private Person person;
    private Random r = new Random();

    public Vect(Person person) {
        this.person = person;
    }

    public Vect(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void randomMovement() {

        double noise = ImprovedNoise.noise(
                randomD(-75, 50),
                randomD(-75, 50),
                randomD(-75, 50))
                * (randomD(-10, 10) * 6);
        dirAngle += noise;

        if(!person.isDead()) {
            x += Math.cos(Math.toRadians(dirAngle) * mag) * mag;
            y += Math.sin(Math.toRadians(dirAngle) * mag) * mag;

            checkCollision();
        }
    }

    private void checkCollision() {
        if (x < 10) {
            x = Main.SCENE_WIDTH - 10;
        } else if (x > Main.SCENE_WIDTH - 10) {
            x = 10;
        }

        if(y < 10) {
            y = Main.STAGE_HEIGHT - 10;
        } else if (y > Main.STAGE_HEIGHT - 10) {
            y = 10;
        }

        Person[] near = CirclesNearMe();

        for(Person v : near) {

            int random = r.nextInt(4000);

            if(person.isINFECTED() && random <= Ligma.INFECTIVITY && !person.isRecovered()) {
                v.setINFECTED(true);
            }

        }
    }

    public void moveTowards(Vect person) {
        double i = person.x + -x;
        double j = person.y + -y;

        x+= i * .01;
        y+= j * .01;

        double newAngle = Math.sqrt((x*x) + (y*y));
        dirAngle = newAngle;
    }

    public void move(double directionAngle) {
        this.dirAngle = directionAngle;
        //if direction and magnitude are automatically set to 0 unless it was set by the user

        x += Math.cos(Math.toRadians(dirAngle) * mag) * mag;
        y += Math.sin(Math.toRadians(dirAngle) * mag) * mag;
    }

    public void move() {
        //if direction and magnitude are automatically set to 0 unless it was set by the user

        x += Math.cos(Math.toRadians(dirAngle) * mag) * mag;
        y += Math.sin(Math.toRadians(dirAngle) * mag) * mag;
    }

    private Person[] CirclesNearMe() {

        Person[] near;
        ArrayList<Person> list = new ArrayList();

        for (Person c : Main.population) {
            if(c.position.x > x - Ligma.INFECTION_RANGE
                    && c.position.x < x + Ligma.INFECTION_RANGE
                    && c.position.y > y - Ligma.INFECTION_RANGE
                    && c.position.y < y + Ligma.INFECTION_RANGE
                    && !c.equals(this))
            {
                list.add(c);
            }
        }

        near = new Person[list.size()];

        for (int i = 0; i < near.length ; i++) {
            near[i] = list.get(i);
        }
        return near;
    }

    private double randomD(double rangeMin, double rangeMax) {
        return rangeMin + (rangeMax - rangeMin) * r.nextDouble();
    }
}
