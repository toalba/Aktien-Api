import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.*;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GUI extends Application{

    public void start(Stage s)
    {
        Apicon apicon = new Apicon();
        DB.CreateSTM();
        // apicon.Apihandler(apicon.Requestbuilder());
        apicon.DataSheetResult(apicon.Requestbuilder("TSLA"));
        DB.SelectStatement("TSLA");
        drawLineDiagram(s,"x","z","Dia",DB._Lastdataselect);
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
        //XYChart.Series<String, Number> zwohundert = new XYChart.Series<>();
        Scene scene = new Scene(lineChart, 1080, 720);
        s.setScene(scene);
        for (Datasheet d :daten)
        {
            series.getData().add(new XYChart.Data<>(d.Datum,d.Wert));
        }
        //lineChart.getData().add(zwohundert);
        lineChart.getData().add(series);
        saveAsPng(lineChart,"/home/toalba/Java2/Aktien-Api/img/chart.png");
        s.show();
    }
    public void drawsearcher(Stage s)
    {

    }

    public void searcher(Stage s)
    {

    }
    public void saveAsPng(LineChart lineChart, String path) {
        WritableImage image = lineChart.snapshot(new SnapshotParameters(), null);
        File file = new File(path);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String args[]){
      launch(args);
   }

} 
