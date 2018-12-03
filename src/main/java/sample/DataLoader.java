package sample;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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
    public HashMap<String, HashMap<Integer, Boolean>> loadHttpDate(String webPage) throws IOException, JSONException {
        HashMap<String, HashMap<Integer, Boolean>> result = new HashMap<String, HashMap<Integer, Boolean>>();
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
            result.putAll(readJson(response));
        }

    return result;
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

    /**
     * Odczytuje wartości z pobranego JSONA
     * @param buffer
     * @throws JSONException
     */
    private HashMap<String, HashMap<Integer, Boolean>> readJson(StringBuffer buffer) throws JSONException {
        HashMap<String, HashMap<Integer, Boolean>> result = new HashMap<String, HashMap<Integer, Boolean>>();
        JSONObject myRespone = new JSONObject(buffer.toString());
        String component = myRespone.getString("component");
        myRespone = new JSONObject(component);
        JSONArray jsonArray = myRespone.getJSONArray("measures");
        for(int i= 0; i<jsonArray.length(); i++){
            JSONObject singleJson = jsonArray.getJSONObject(i);
            result.putAll(getSingleMesure(singleJson));

        }
        return result;

    }

    /**
     * Zapisywanie pojedyńczych obiektów JSON do hashmap
     * @param jsonObject
     */
    private HashMap<String, HashMap<Integer, Boolean>> getSingleMesure(JSONObject jsonObject) throws JSONException {
        HashMap<Integer, Boolean> valuesMap = new HashMap<Integer, Boolean>();
        boolean bestValue = false;
        String metric = jsonObject.getString("metric");
        int value = jsonObject.getInt("value");
        if (jsonObject.toString().contains("bestValue"))
            bestValue = jsonObject.getBoolean("bestValue");
        valuesMap.put(value,bestValue);
        mesuresResult.put(metric,valuesMap);
        return mesuresResult;
    }
}
