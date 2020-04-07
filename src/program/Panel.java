package program;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Slider;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Random;

public class Panel {

    private Pane layout;

    //marks the statistics
    public Text infectedText;
    public Text healthyText;
    public Text deadText;
    public Text recoveredText;

    //displays the statistics
    public Text infectedCount;
    public Text healthyCount;
    public Text deadCount;
    public Text recoveredCount;

    public Button resetButton;

    public Slider speedMeter;

    private Random r = new Random();

    //TODO add a mortality, infectious range and infectivity and min and max infection times

    /**
     *
     * @param layout the pane that holds the panel
     */
    public Panel(Pane layout) {
        this.layout = layout;

        //prevents the pane from expanding randomly
        layout.setMaxHeight(450);
        layout.setMinHeight(450);

        loadGui();
    }

    //sets the data for the panel
    public void setInfectedCount(int count) {
        infectedCount.setText(String.valueOf(count));
    }
    public void setHealthyCount(int count){healthyCount.setText(String.valueOf(count));}
    public void setRecoveredCount(int count){recoveredCount.setText(String.valueOf(count));}
    public void setDeadCount(int count){deadCount.setText(String.valueOf(count));}

    private void loadGui() {

        infectedText = new Text("Current Infected");
        infectedText.setLayoutX(10);
        infectedText.setLayoutY(90);
        infectedText.setFill(Color.BLACK);
        infectedText.setFont(new Font("consolas",15));
        infectedText.setFill(Color.ORANGE);

        infectedCount = new Text();
        infectedCount.setLayoutX(10);
        infectedCount.setLayoutY(120);
        infectedCount.setFill(Color.BLACK);
        infectedCount.setFont(new Font("consolas",25));
        infectedCount.setFill(Color.ORANGE);

        deadText   = new Text(String.valueOf("Total Deaths"));
        deadText.setLayoutX(10);
        deadText.setLayoutY(140);
        deadText.setFont(new Font("consolas",15));
        deadText.setFill(Color.RED);

        deadCount   = new Text();
        deadCount.setLayoutX(10);
        deadCount.setLayoutY(170);
        deadCount.setFont(new Font("consolas",25));
        deadCount.setFill(Color.RED);

        healthyText = new Text("Uneffected People");
        healthyText.setLayoutX(10);
        healthyText.setLayoutY(190);
        healthyText.setFont(new Font("consolas",15));
        healthyText.setFill(Color.GREEN);

        healthyCount = new Text();
        healthyCount.setLayoutX(10);
        healthyCount.setLayoutY(220);
        healthyCount.setFont(new Font("consolas",25));
        healthyCount.setFill(Color.GREEN);

        recoveredText = new Text("Recovered");
        recoveredText.setLayoutX(10);
        recoveredText.setLayoutY(240);
        recoveredText.setFont(new Font("consolas",15));
        recoveredText.setFill(Color.web("6E54C9"));

        recoveredCount = new Text();
        recoveredCount.setLayoutX(10);
        recoveredCount.setLayoutY(270);
        recoveredCount.setFont(new Font("consolas",25));
        recoveredCount.setFill(Color.web("6E54C9"));

        resetButton = new Button("reset");
        resetButton.setLayoutX(50);
        resetButton.setLayoutY(290);
        resetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                //reset all of the people
                for (int i = 0; i < Main.population.length ; i++) {
                    Main.population[i].setX(r.nextInt(Main.SCENE_WIDTH));
                    Main.population[i].setY(r.nextInt(Main.STAGE_HEIGHT));
                    Main.population[i].setDead(false);
                    Main.population[i].setRecovered(false);
                    Main.population[i].setINFECTED(false);
                    Main.population[i].WAS_OR_IS_INFECTED = false;
                    Main.population[i].elapsedTime = 0;

                    //reseting the graph
                    Graph.totalInfectedStats.clear();
                    Graph.totalDeathStats.clear();
                    Graph.currentInfectedStats.clear();
                }

                //set the 1st person in the list as infected
                Main.population[0].setINFECTED(true);
                Main.population[0].setRecovered(false);
                Main.population[0].setDead(false);
            }
        });

        speedMeter = new Slider();
        speedMeter.setShowTickMarks(true);
        speedMeter.setShowTickLabels(true);
        speedMeter.adjustValue(1);
        speedMeter.setMajorTickUnit(.5);
        speedMeter.setLayoutX(30);
        speedMeter.setLayoutY(340);
        speedMeter.setMin(0);
        speedMeter.setMax(2.5);

        //add all the nodes into the pane
        layout.getChildren().add(infectedText);
        layout.getChildren().add(infectedCount);
        layout.getChildren().add(healthyText);
        layout.getChildren().add(healthyCount);
        layout.getChildren().add(deadText);
        layout.getChildren().add(deadCount);
        layout.getChildren().add(recoveredText);
        layout.getChildren().add(recoveredCount);
        layout.getChildren().add(resetButton);
        layout.getChildren().add(speedMeter);
    }
}
