package android.sead_systems.seads.http;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;

/**
 * WebInterfacer will handle HTTP requests to the server
 * To create a request go to an URL formatted like below:
 * http://db.sead.systems:8080/466419818?start_time=1477395900&end_time=1477395910&list_format=energy&granularity=1&device=Panel3&type=P
 */
public class WebInterfacer implements HTTPGetRequestAsyncTask.HTTPResponse {
    // TODO: figure out how we're gonna get the htpp response to the instatiator
        // We can do the same thing we did and keep a reference to the instatiator. create another interface?

    /*
    TODO: Create methods to:
        Make a http request with some paramaters
        take in times, panels, granularity, type, should check for correctness

     */
    public WebInterfacer() {
        try {
            URL url = new URL("http://db.sead.systems:8080/466419818?start_time=1477395900&"+
                    "end_time=1477395910&list_format=energy&granularity=1&device=Panel3&type=P");
            // Test code to attempt to get a string from server
            new HTTPGetRequestAsyncTask(this).execute(url);


            JSONObject wholeString = new JSONObject("{ \"data\": [{ \"time\": \"2016-10-25 04:45:09\", \"energy\": \"0.000036388888888888888889\" },{ \"time\": \"2016-10-25 04:45:08\", \"energy\": \"0.000036666666666666666667\" },{ \"time\": \"2016-10-25 04:45:07\", \"energy\": \"0.000036666666666666666667\" },{ \"time\": \"2016-10-25 04:45:06\", \"energy\": \"0.000036388888888888888889\" },{ \"time\": \"2016-10-25 04:45:05\", \"energy\": \"0.000036666666666666666667\" },{ \"time\": \"2016-10-25 04:45:04\", \"energy\": \"0.000036111111111111111111\" },{ \"time\": \"2016-10-25 04:45:03\", \"energy\": \"0.000036944444444444444444\" },{ \"time\": \"2016-10-25 04:45:02\", \"energy\": \"0.000036388888888888888889\" },{ \"time\": \"2016-10-25 04:45:01\", \"energy\": \"0.000036388888888888888889\" },{ \"time\": \"2016-10-25 04:45:00\" ,\"energy\": \"0.000036388888888888888889\" }]}");
            JSONArray data = wholeString.getJSONArray("data");
            JSONObject index0 = data.getJSONObject(0);
            Log.d("WebInterfacer", "time at index 0:" + index0.getString("time"));
        } catch(Exception e) {
            Log.d("WebInterfacer", "Failure.");
        }
    }

    @Override
    public void httpResponseFinished(String result) {
        Log.d("WebInterfacer", "http result: "+result);
    }
}
