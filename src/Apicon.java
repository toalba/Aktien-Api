import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.*;


public class Apicon {

    // api key 1AD6CE6LV8OFT02F
    private final String key="&apikey=1AD6CE6LV8OFT02F";
    private String requestString ="https://www.alphavantage.co/query";
    private String function = "?function=";
    private String symbol = "&symbol=";

    private JSONObject WebRequest(String urlstring) {
        HttpURLConnection connection;
        try {
            URL url = new URL(urlstring);
            connection =(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            //Send request
            try(BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                  StringBuilder response = new StringBuilder();
                  String responseLine;
                  while ((responseLine = br.readLine()) != null) {
                      response.append(responseLine.trim());
                  }
                  System.out.println(response.toString());
                  return new JSONObject(response.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public JSONObject Requestbuilder(String function)
    {

        HashMap<String,String> blblb = new HashMap<String,String>();

        return new JSONObject();
    }



}
