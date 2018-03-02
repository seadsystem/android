package com.seads.seadsv2.http;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

/**
 * WebInterfacer will handle HTTP requests to the server
 * To create a request go to an URL formatted like below:
 * http://db.sead.systems:8080/466419818?start_time=1477395900&end_time=1477395910&list_format=energy&granularity=1&device=Panel3&type=P
 *
 * To use this class and act upon HTTP responses:
 * 1. Make instantiating class implement WebInterface. This is an interface that allows
 *  us to return the response
 * 2. call getJSONObject() with the appropriate paramaters
 * 3. Inside the instantiating class, override
 */


public class WebInterfacer implements HTTPGetRequestAsyncTask.HTTPResponse {

    private WebInterface instantiator;

    public WebInterfacer(WebInterface instantiator) {
        this.instantiator = instantiator;
    }


    /**
     * returns URL
     * @param startTime start time in unix time
     * @param endTime end time in unix time
     * @param listFormat "energy" or "power"
     * @param granularity every x amount of data points
     * @param device 1, 2, or 3
     * @param type "P"
     * @return URL necessary to get the json object
     */
    private URL getURL(long startTime, long endTime, String listFormat,
                                         int granularity, String device, String type) {
        try {
            Log.d("WebInterfacer", "http://db.sead.systems:8080/466419818?start_time="+startTime+"&"
                    + "end_time="+endTime+"&list_format=" + listFormat +
                    "&granularity="+granularity+"&device="+device+"&type="+type);
            return new URL("http://db.sead.systems:8080/466419818?start_time="+startTime+"&"
                    + "end_time="+endTime+"&list_format=" + listFormat +
                    "&granularity="+granularity+"&device="+device+"&type="+type);
        } catch (Exception e) {
            // Insert appropriate toast here
            return null;
        }
    }


    /**
     * retrieves the json object in httpResponseFinished
     * @param startTime start time in unix time
     * @param endTime end time in unix time
     * @param listFormat "energy" or "power"
     * @param granularity every x amount of data points
     * @param device 1, 2, or 3
     * @param type "P"
     */
    public void getJSONObject(long startTime, long endTime, String listFormat,
                              int granularity, String device, String type) {
        URL url = getURL(startTime, endTime, listFormat, granularity, device, type);
        new HTTPGetRequestAsyncTask(this).execute(url);
    }

    /**
     * Called when we receive a message from the server.
     * This function should never be manually called for the same way AsyncTask onPostExecute should
     * never be manually called
     * @param result the HTTP result as a string
     */
    @Override
    public void httpResponseFinished(String result) {
        Log.d("WebInterfacer", "http result: "+result);
        if (result == null) {
            instantiator.onJSONRetrieved(null);
        }
        try {
            JSONObject resultJSON = new JSONObject(result);
            instantiator.onJSONRetrieved(resultJSON);
        } catch(JSONException e) {
            // Print json error message
            instantiator.onJSONRetrieved(null);
        }
    }
}
