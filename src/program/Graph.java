package program;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Graph {

    public static ArrayList<Integer> totalInfectedStats = new ArrayList<>();
    public static ArrayList<Integer> currentInfectedStats = new ArrayList<>();
    public static ArrayList<Integer> totalDeathStats = new ArrayList<>();
    private ScrollPane scrollPane;
    private Pane pane;
    private GraphicsContext gc;
    private Main main;

    private double scaler = 200 / (double) Main.POPULATION_COUNT * .9;

    public Graph(Main main, ScrollPane scrollPane) {
        this.main = main;
        this.scrollPane = scrollPane;

        pane = new Pane();
        pane.setStyle("-fx-background-color: gray;");
        pane.setMinWidth(600);
        pane.setMaxWidth(600);

        Canvas c = new Canvas(Main.STAGE_WIDTH, Main.STAGE_HEIGHT);
        gc = c.getGraphicsContext2D();
        pane.getChildren().add(c);

        scrollPane.setContent(pane);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
    }

    public void setData(int totalCases, int currentCases, int dead) {

        checkForOverflow(totalCases, currentCases, dead);

        //if (totalInfectedStats.size() <= 200) {
            clear();
            draw();
       // }
    }

    //if the graph is about to overfill the scene then remove the first
    public void checkForOverflow(int totalCases, int currentCases, int dead) {

        //add the previous amount to get total number of cases
        totalInfectedStats.add(totalCases);
        currentInfectedStats.add(currentCases);
        totalDeathStats.add(dead);
    }

    public void clear() {
        gc.setFill(Color.GREY);
        gc.fillRect(0, 0, 200, 250);
    }

    public void draw() {
        gc.setFill(Color.YELLOW);

        //System.out.println(totalInfectedStats.size());
        for (int i = 0; i < totalInfectedStats.size(); i++) {
            gc.fillRect(i, 230 - totalInfectedStats.get(i) * scaler, 1, 4);
        }

        gc.setFill(Color.ORANGE);
        for (int i = 0; i < currentInfectedStats.size(); i++) {
            gc.fillRect(i, 230 - currentInfectedStats.get(i) * scaler, 1, 4);
        }

        gc.setFill(Color.RED);
        for (int i = 0; i < totalDeathStats.size(); i++) {
            gc.fillRect(i, 230 - totalDeathStats.get(i) * scaler, 1, 4);
        }
    }
}
