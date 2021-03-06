/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package program;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Random;

/**
 * @author Simon Dao
 */
public class Main extends Application {

    private Random r = new Random();

    public Pane simulation;
    private Pane controlPanel;
    private Panel controls;

    public static long timePassed;

    public static int SCENE_WIDTH = 600;

    public static int STAGE_WIDTH = 800;
    public static int STAGE_HEIGHT = 700;

    //population recommended to be less than 3000
    public static int POPULATION_COUNT = 1000;

    public static Person[] population = new Person[POPULATION_COUNT];

    private Stage stage;
    private Scene scene;

    public int dead;
    public int infected;
    public int recovered;
    public int healthy;
    public int totalCases;

    private Graph graph;

    private boolean SIMULATION_RUNNING = true;

    private String title = "Disease Spread simulation";

    public void start(Stage primaryStage) {
        this.stage = primaryStage;

        stage.setResizable(false);

        //holds the simulation and the control panel
        loadGui();

        initializeSimulation();

        startTicking();

        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void loadGui() {
        HBox window = new HBox();

        simulation = new Pane();
        simulation.setMaxWidth(600);
        simulation.setMinWidth(600);
        simulation.setStyle("-fx-background-color: black;");

        //holds the control panel and the graph
        VBox rightSide = new VBox();

        //control panel
        controlPanel = new Pane();
        controls = new Panel(controlPanel);

        //graph
        ScrollPane graphPane = new ScrollPane();
        graph = new Graph(graphPane);

        rightSide.getChildren().add(controlPanel);
        rightSide.getChildren().add(graphPane);

        window.getChildren().add(simulation);
        window.getChildren().add(rightSide);

        scene = new Scene(window, STAGE_WIDTH, STAGE_HEIGHT);
    }

    public void initializeSimulation() {

        //creates new people and adds them to the population
        for (int i = 0; i < population.length; i++) {

            if (i != 0) {
                Person person = new Person(1, 1, r.nextInt(SCENE_WIDTH), r.nextInt(STAGE_HEIGHT), simulation);
                person.id = i;
                population[i] = person;
            } else if (i == 0) {
                Person infected = new Person(1, 1, r.nextInt(SCENE_WIDTH), r.nextInt(STAGE_HEIGHT), simulation);
                infected.id = i;
                infected.setINFECTED(true);
                infected.circle.setFill(Color.WHITE);
                population[i] = infected;
            }
        }
    }

    private void startTicking() {

        new AnimationTimer() {
            long lastTick = 0;

            //TIMER
            public void handle(long now) {
                if (SIMULATION_RUNNING == true) {
                    stage.setScene(scene);

                    if (lastTick == 0) {
                        lastTick = now;
                        tick();

                        return;
                    }

                    if (now - lastTick > 1000000000 / 60) {
                        lastTick = now;
                        tick();
                    }
                }
            }
        }.start();

        //mouse event
        simulation.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                double mx = event.getX();
                double my = event.getY();

                Vect mouse = new Vect(mx, my);

                for (Person p : population) {
                    if (!p.isDead())
                        p.moveTowards(mouse);
                }
            }
        });
    }

    private void tick() {

        if(controls.speedMeter.getValue() == 0) {
            timePassed += (long) Math.rint(controls.speedMeter.getValue());
        } else
            timePassed += 1;

        int alivec = 0;
        int deadc = 0;
        int recoveredc = 0;
        int infectedc = 0;
        int totalCasesc = 0;

        for (int i = 0; i < population.length; i++) {

            population[i].setMag(controls.speedMeter.getValue());
            population[i].update();
            population[i].randomMovement();
            if (population[i].isINFECTED()) {
                infectedc++;
            }
            if (!population[i].isDead() && !population[i].isINFECTED()) {
                alivec++;
            }
            if (population[i].isDead()) {
                deadc++;
                if (infectedc > 0)
                    infectedc--;
            }
            if (population[i].isRecovered()) {
                recoveredc++;
                alivec--;
                if (infectedc > 0)
                    infected--;
            }
            if (population[i].WAS_OR_IS_INFECTED) {
                totalCasesc++;
            }
        }

        dead = deadc;
        healthy = alivec;
        recovered = recoveredc;
        infected = infectedc;
        totalCases = totalCasesc;

        controls.setInfectedCount(infected);
        controls.setHealthyCount(healthy);
        controls.setRecoveredCount(recovered);
        controls.setDeadCount(dead);

        if (timePassed % 6 == 0) {

            graph.setData(totalCases, infected, dead);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
