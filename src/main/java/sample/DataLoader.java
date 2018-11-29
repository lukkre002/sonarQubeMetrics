package sample;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class DataLoader {
    HashMap<String, HashMap<Integer, Boolean>> mesuresResult = new HashMap<String, HashMap<Integer, Boolean>>();
    public DataLoader() {
    }

    /**
     * Pobranie JSONA z http
     * @param webPage
     * @throws IOException
     * @throws JSONException
     */
    public void loadHttpDate(String webPage) throws IOException, JSONException {
        URL url = new URL(webPage);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        connection.connect();
        int responseCode = connection.getResponseCode();
        if(checkConnection(responseCode)){
            BufferedReader in = new BufferedReader( new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine())!=null){
                response.append(inputLine);
            }
            in.close();
            readJson(response);
        }


    }

    /**
     * Metoda sprawdzająca czy udało się poprawnie nawiązać połącznie
     * @param responseCode - kod połączenia
     * @return
     */
    private boolean checkConnection(int responseCode){
        if (responseCode != 200){
            // Bład- brak polaczenia
            throw new RuntimeException("HttpResponse Code: " + responseCode);
        } else
        {
            //Udało się nawiązać połączenie
            return true;
        }
    }

    private void readJson(StringBuffer buffer) throws JSONException {
        JSONObject myRespone = new JSONObject(buffer.toString());
        String component = myRespone.getString("component");
        myRespone = new JSONObject(component);
        JSONArray jsonArray = myRespone.getJSONArray("measures");
        for(int i= 0; i<jsonArray.length(); i++){
            JSONObject singleJson = jsonArray.getJSONObject(i);
            getSingleMesure(singleJson);

        }

    }

    /**
     * Zapisywanie pojedyńczych obiektów JSON do hashmap
     * @param jsonObject
     */
    private HashMap<String, HashMap<Integer, Boolean>> getSingleMesure(JSONObject jsonObject) throws JSONException {
        HashMap<Integer, Boolean> valuesMap = new HashMap<Integer, Boolean>();
        String metric = jsonObject.getString("metric");
        int value = jsonObject.getInt("value");
        boolean bestValue = jsonObject.getBoolean("bestValue");
        valuesMap.put(value,bestValue);
        mesuresResult.put(metric,valuesMap);
        return mesuresResult;
    }
}
