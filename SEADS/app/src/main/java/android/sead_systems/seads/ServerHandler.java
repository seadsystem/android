package android.sead_systems.seads;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Deprecated server handling class. Currently not in use as of December 1, 2016.
 * @author Chris Persons
 */

public class ServerHandler {
    // http://db.sead.systems:8080/466419818?start_time=1478462587&end_time=1478548987&subset=10&type=P
    private String baseUrl = "http://db.sead.systems:8080/";
    // Current live device ID
    private String deviceId = "466419818";
    // start & end time are unix epoch time stamps
    private String startTime = "1478462587";
    private String endTime = "1478548987";
    // I = current, T = temperature, W = power, V = voltage
    private String type = "";
    // Selects a subset of data within the given filters that are evenly spaced within range
    private String subset = "";
    // Retrieve only first x rows
    private String limit = "";
    // Reverse the result from the API
    private boolean reverse = false;
    private String url = "http://api.eia.gov/series/?api_key=YOUR_ID_HERE&" +
            "series_id=ELEC.PRICE.CA-RES.M&start=201601&end=201608";

            //"http://db.sead.systems:8080/466419818?start_time=1478462587&" +
            //"end_time=1478548987&subset=10&type=P";
    private Context context;

    ServerHandler (Context context) {
        this.context = context;
    }

    Context getContext () {
        return this.context;
    }

    void setContext(Context context) {
        this.context = context;
    }

    void serverCall() {
        new JSONParseObject().execute();
    }
    //new JSONParse().execute();


    private class JSONParseArray extends AsyncTask<String, String, JSONArray> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONArray doInBackground(String... args) {
            JSONArray jsonArray = new JSONArray();
            try{
                jsonArray = getJSONArrayFromURL(url);
                return jsonArray;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonArray;
        }
        @Override
        protected void onPostExecute(JSONArray json) {
            pDialog.dismiss();
        }
    }


    public static JSONArray getJSONArrayFromURL(String urlString) throws IOException, JSONException {
        System.out.println("CostActivity::getJSONObjectFromURL");
        HttpURLConnection urlConnection = null;

        URL url = new URL(urlString);

        urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000 /* milliseconds */);
        urlConnection.setConnectTimeout(15000 /* milliseconds */);

        urlConnection.setDoOutput(true);

        urlConnection.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

        String jsonString = new String();

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line+"\n");
        }
        br.close();

        jsonString = sb.toString();

        System.out.println("JSON: " + jsonString);

        return new JSONArray(jsonString);
    } // end getJSONObjectFromURL

    private class JSONParseObject extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONObject jsonObject = new JSONObject();
            try{
                jsonObject = getJSONObjectFromURL(url);
                // Gets pricePerKWHour in cents
//                JSONArray jArray = jsonObject.getJSONArray("series");
//                JSONArray ja, costJsonObject;
//                for(int i = 0; i < jArray.length(); i++) {
//                    costJsonObject = jArray.getJSONObject(i).getJSONArray("data");
//                    ja = costJsonObject.getJSONArray(0);
//                    CostActivity.pricePerKWHour = ja.getString(1);
//                }
                return jsonObject;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonObject;
        }
        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
        }
    }


    public static JSONObject getJSONObjectFromURL(String urlString) throws IOException, JSONException {
        HttpURLConnection urlConnection = null;

        URL url = new URL(urlString);

        urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000 /* milliseconds */);
        urlConnection.setConnectTimeout(15000 /* milliseconds */);

        urlConnection.setDoOutput(true);

        urlConnection.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

        String jsonString = new String();

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line+"\n");
        }
        br.close();

        jsonString = sb.toString();

        System.out.println("JSON: " + jsonString);

        return new JSONObject(jsonString);
    } // end getJSONObjectFromURL
}
