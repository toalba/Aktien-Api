import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.*;


public class Apicon {

    
    private JSONObject feiertagObject(int yearString,String con) {
        String requestString;
            requestString=con+yearString;
        
        HttpURLConnection connection = null;
        try {
            URL url = new URL(requestString);
            connection =(HttpURLConnection) url.openConnection();
            if (con=="https://ferien-api.de/api/v1/holidays/BY/") {
                connection.setRequestMethod("GET");
            }
            else
            {
            connection.setRequestMethod("POST");
            }
            connection.setUseCaches(false);
            
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            if (con=="https://deutsche-feiertage-api.de/api/v1/") {
                connection.setRequestProperty("X-DFA-Token", "dfa");
            }
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

             //Send request
             
		
            try(BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                  StringBuilder response = new StringBuilder();
                  String responseLine = null;
                  while ((responseLine = br.readLine()) != null) {
                      response.append(responseLine.trim());
                  }
                  if (con=="https://ferien-api.de/api/v1/holidays/BY/") {
                    JSONArray ar = new JSONArray(response.toString());
                    JSONObject test =  ar.getJSONObject(1);
                    return test;
                    
                  }

                  return new JSONObject(response.toString());
              }            

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<JSONObject> getfeiertagObject(String maxdate,String tstart,String url)
    {
        //here start  will be 2020
        int start = Integer.parseInt(tstart);
        List<JSONObject> yearList = new ArrayList<>();
        for (int i = start; i <= Integer.parseInt(maxdate); i++) {
            yearList.add(feiertagObject(i,url));
        }
        return yearList;
    }
    public List<JSONArray> getferienArrays(String maxdate,String tstart,String url)
    {
        //here start  will be 2020
        int start = Integer.parseInt(tstart);
        List<JSONArray> yearList = new ArrayList<>();
        for (int i = start; i <= Integer.parseInt(maxdate); i++) {
            yearList.add(ferieArray(i,url));
        }
        return yearList;
    }


    private JSONArray ferieArray(int yearString,String con)
    {
        String requestString=con+yearString;
    
    HttpURLConnection connection = null;
    try {
        URL url = new URL(requestString);
        connection =(HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setUseCaches(false);
        
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);

         //Send request
    
        try(BufferedReader br = new BufferedReader(
            new InputStreamReader(connection.getInputStream(), "utf-8"))) {
              StringBuilder response = new StringBuilder();
              String responseLine = null;
              while ((responseLine = br.readLine()) != null) {
                  response.append(responseLine.trim());
              }
              return new JSONArray(response.toString());
          }            

    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
    }

}
