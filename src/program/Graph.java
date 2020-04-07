package program;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Graph {

    //data that will be fed into the graph
    public static ArrayList<Integer> totalInfectedStats = new ArrayList<>();
    public static ArrayList<Integer> currentInfectedStats = new ArrayList<>();
    public static ArrayList<Integer> totalDeathStats = new ArrayList<>();

    private int GRAPH_HEIGHT = 200;

    //the pane that the graph will be put in that can scroll
    private ScrollPane scrollPane;
    private Pane pane;
    private GraphicsContext gc;

    //a multiplier that prevents the data will overflow the graph
    private double scaler = GRAPH_HEIGHT / (double) Main.POPULATION_COUNT * .9;

    /**
     *
     * @param scrollPane the scrollPane that holds the graph
     */
    public Graph(ScrollPane scrollPane) {
        this.scrollPane = scrollPane;

        pane = new Pane();
        pane.setStyle("-fx-background-color: gray;");
        pane.setMinWidth(600);
        pane.setMaxWidth(600);

        Canvas c = new Canvas(Main.STAGE_WIDTH, Main.STAGE_HEIGHT);
        gc = c.getGraphicsContext2D();
        pane.getChildren().add(c);

        //putting the graph in the scrollpane
        scrollPane.setContent(pane);

        //make the scrollpane only scroll sideways
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
    }

    public void setData(int totalCases, int currentCases, int dead) {

        setGraphData(totalCases, currentCases, dead);
        clear();
        draw();
    }

    public void setGraphData(int totalCases, int currentCases, int dead) {

        totalInfectedStats.add(totalCases);
        currentInfectedStats.add(currentCases);
        totalDeathStats.add(dead);
    }

    public void clear() {
        gc.setFill(Color.GREY);
        gc.fillRect(0,0,800,250);
    }

    public void draw() {

        //draws a rectangle for every index of the arrayList to make the graph
        gc.setFill(Color.YELLOW);
        for (int i = 0; i < totalInfectedStats.size(); i++) {
            gc.fillRect(i, 240 - totalInfectedStats.get(i) * scaler, 1, 4);
        }

        gc.setFill(Color.ORANGE);
        for (int i = 0; i < currentInfectedStats.size(); i++) {
            gc.fillRect(i, 240 - currentInfectedStats.get(i) * scaler, 1, 4);
        }

        gc.setFill(Color.RED);
        for (int i = 0; i < totalDeathStats.size(); i++) {
            gc.fillRect(i, 240 - totalDeathStats.get(i) * scaler, 1, 4);
        }
    }
}
