import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.*;


public class Apicon {

    // api key 1AD6CE6LV8OFT02F
    private final String key="&apikey=1AD6CE6LV8OFT02F";
    private String requestString ="https://www.alphavantage.co/query";
    private String function = "?function=";
    private String symbol = "&symbol=";

    public JSONObject WebRequest(String urlstring) {
        HttpURLConnection connection;
        try {
            URL url = new URL("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=IBM&apikey=1AD6CE6LV8OFT02F");
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
                  return new JSONObject(response.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<Webrequestresult> Apihandler(JSONObject webresult)
    {
        ArrayList<Webrequestresult> wresult =  new ArrayList<Webrequestresult>();
        try {
            JSONObject data = webresult.getJSONObject("Time Series (Daily)");
            System.out.println(data.get("2020-08-07"));
            for (int i = 0; i < data.length(); i++) {

            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return new ArrayList<Webrequestresult>();
    }


    public JSONObject Requestbuilder()
    {
        String url=requestString+function+"TIME_SERIES_DAILY"+symbol+"TSLA"+key;
        return WebRequest(url);
    }



    public JSONObject Webrequestsucher(String keyword) {
        HttpURLConnection connection;
        String urlstring="https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords="+keyword+key;
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

                return new JSONObject(response.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<Websearchresult> Searchresults(String keyword) {
        ArrayList<Websearchresult> array = new ArrayList<>();
        JSONObject researchresult = Webrequestsucher(keyword);
        try {
            JSONArray researchresarray = researchresult.getJSONArray("bestMatches");
            for (int i = 0; i < researchresarray.length(); i++) {
                JSONObject res = researchresarray.getJSONObject(i);
                array.add(new Websearchresult(res.getString("2. name"),res.getString("1. symbol"),res.getString("4. region")));
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return array;
    }
    public ArrayList<Datasheet> DataSheetResult(JSONObject json)
    {
        ArrayList<Datasheet> arrayList = new ArrayList<>();
        try
        {
            JSONObject result = json.getJSONObject("Time Series (Daily)");
            for(int i = 0; i < result.length(); i++)
            {
                String datum = result.names().get(i).toString();
                JSONObject wert = (JSONObject) result.get(datum);
                arrayList.add(new Datasheet(datum,Double.parseDouble(wert.get("4. close").toString())));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return arrayList;
    }


}
