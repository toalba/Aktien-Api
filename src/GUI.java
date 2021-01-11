import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GUI extends Application{

    public void start(Stage s)
    {
        Apicon apicon = new Apicon();
       // apicon.Apihandler(apicon.Requestbuilder());
        drawLineDiagram(s,"","","Negga",apicon.DataSheetResult(apicon.WebRequest("IBM")));
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
    public void searcher(Stage s)
    {

    }


    public static void main(String args[]){
      launch(args);
   }

} 
