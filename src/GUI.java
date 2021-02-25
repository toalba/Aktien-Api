import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.*;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
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
        //apicon.DataSheetResult(apicon.Requestbuilder("TSLA"));
        //DB.SelectStatement("TSLA");
        DB.SelectAVGStatement("TSLA");
        //drawLineDiagram(s,"x","z","Dia",DB._Lastdataselect);
    }

    public void drawLineDiagram(Stage s,String xlabel,String ylabel,String title,ArrayList<Datasheet> daten)
    {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel(xlabel);
        yAxis.setLabel(ylabel);
        //final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        XYChart.Series<String, Number> zwohundert = new XYChart.Series<>();
        for (Datasheet d :daten)
        {
            series.getData().add(new XYChart.Data<>(d.Datum,d.Wert));
        }
        series.setName("Aktienverlauf");
        zwohundert.setName("200erLinie");
        //creating the chart
        final LineChart<String, Number> lineChart =
                new LineChart<String, Number>(xAxis, yAxis, FXCollections.observableArrayList(series)) {
                    @Override
                    protected void layoutPlotChildren() {
                        super.layoutPlotChildren();
                        Series series =  (Series) getData().get(0);
                        ObservableList<Data<String,Number>> listOfData = series.getData();
                        ObservableList<Data<String,Number>> listOfDatazwo = zwohundert.getData();
                        for(int i = 0; i < listOfData.size()-1; i++) {
                            // Check Green
                                double x1 = getXAxis().getDisplayPosition(listOfData.get(i).getXValue());
                                double y1 = getYAxis().getDisplayPosition(0);
                                double x2 = getXAxis().getDisplayPosition(listOfData.get((i + 1)).getXValue());
                                double y2 = getYAxis().getDisplayPosition(0);
                                Polygon polygon = new Polygon();
                                LinearGradient linearGrad;
                                if(listOfData.get(i).getYValue().doubleValue() >= listOfDatazwo.get(i).getYValue().doubleValue()) {
                                    linearGrad = new LinearGradient( 0, 0, 0, 1,
                                        true, // proportional
                                        CycleMethod.NO_CYCLE, // cycle colors
                                        new Stop(0.1f, Color.rgb(0, 255, 0, .3)));
                                }
                                else
                                {
                                    linearGrad = new LinearGradient( 0, 0, 0, 1,
                                            true, // proportional
                                            CycleMethod.NO_CYCLE, // cycle colors
                                            new Stop(0.1f, Color.rgb(255, 0, 0, .3)));
                                }
                                polygon.getPoints().addAll(new Double[]{
                                        x1,y1,
                                        x1, getYAxis().getDisplayPosition(listOfData.get(i).getYValue()),
                                        x2,getYAxis().getDisplayPosition(listOfData.get((i+1)).getYValue()),
                                        x2,y2
                                });
                                getPlotChildren().add(polygon);
                                polygon.toFront();
                                polygon.setFill(linearGrad);
                        }
                    }
                };
        Scene scene = new Scene(lineChart, 1080, 720);
        s.setScene(scene);
        lineChart.setTitle(title);
        lineChart.setCreateSymbols(false);
        lineChart.getData().add(zwohundert);
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
