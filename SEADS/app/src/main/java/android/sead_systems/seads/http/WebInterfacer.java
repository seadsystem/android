package android.sead_systems.seads.http;

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
    // TODO: figure out how we're gonna get the htpp response to the instantiator
        // We can do the same thing we did and keep a reference to the instantiator. create another interface?

    /*
    TODO: Create methods to:
        Make a http request with some paramaters
        take in times, panels, granularity, type, should check for correctness

     */
    private WebInterface instantiator;

    public WebInterfacer(WebInterface instantiator) {
        this.instantiator = instantiator;
    }

//    public WebInterfacer() {
//        try {
//            URL url = getURL(1477395900, 1477395910, "energy", 1, 3, "P");
////            URL url = new URL("http://db.sead.systems:8080/466419818?start_time=1477395900&"+
////                    "end_time=1477395910&list_format=energy&granularity=1&device=Panel3&type=P");
//            // Test code to attempt to get a string from server
//            new HTTPGetRequestAsyncTask(this).execute(url);
//
//
//            JSONObject wholeString = new JSONObject("{ \"data\": [{ \"time\": \"2016-10-25 04:45:09\", \"energy\": \"0.000036388888888888888889\" },{ \"time\": \"2016-10-25 04:45:08\", \"energy\": \"0.000036666666666666666667\" },{ \"time\": \"2016-10-25 04:45:07\", \"energy\": \"0.000036666666666666666667\" },{ \"time\": \"2016-10-25 04:45:06\", \"energy\": \"0.000036388888888888888889\" },{ \"time\": \"2016-10-25 04:45:05\", \"energy\": \"0.000036666666666666666667\" },{ \"time\": \"2016-10-25 04:45:04\", \"energy\": \"0.000036111111111111111111\" },{ \"time\": \"2016-10-25 04:45:03\", \"energy\": \"0.000036944444444444444444\" },{ \"time\": \"2016-10-25 04:45:02\", \"energy\": \"0.000036388888888888888889\" },{ \"time\": \"2016-10-25 04:45:01\", \"energy\": \"0.000036388888888888888889\" },{ \"time\": \"2016-10-25 04:45:00\" ,\"energy\": \"0.000036388888888888888889\" }]}");
//            JSONArray data = wholeString.getJSONArray("data");
//            JSONObject index0 = data.getJSONObject(0);
//            Log.d("WebInterfacer", "time at index 0:" + index0.getString("time"));
//        } catch(Exception e) {
//            Log.d("WebInterfacer", "Failure.");
//        }
//    }

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
