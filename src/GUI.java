import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class GUI extends Application{

    public void start(Stage s)
    {

    }

    public void drawLineDiagram(Stage s,String xlabel,String ylabel,String title,ArrayList<Datasheet> daten)
    {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel(xlabel);
        yAxis.setLabel(ylabel);
        final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle(title);
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        Scene scene = new Scene(lineChart, 1080, 720);
        s.setScene(scene);
        for (Datasheet d :daten)
        {
            series.getData().add(new XYChart.Data<>(d.Datum,d.Wert));
        }
        lineChart.getData().add(series);
        s.show();
    }



    public static void main(String args[]){
      launch(args);
   }

} 
