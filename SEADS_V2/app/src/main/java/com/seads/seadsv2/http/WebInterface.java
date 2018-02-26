package com.seads.seadsv2.http;

import org.json.JSONObject;

/**
 * WebInterface
 * This interface should be implemented when using the WebInterfacer.
 * This interface provides a method to act when we receive a response from the server
 */
public interface WebInterface {
    /**
     * called from WebInterfacer when an HTTP response has been received
     * The JSONObject result can be used in the following manner:
     * JSONArray data = result.getJSONArray("data");
     * JSONObject index0 = data.getJSONObject(0);
     * Log.d("WebInterfacer", "time at index 0:" + index0.getString("time"));
     * @param result the result of the HTTP response
     */
    void onJSONRetrieved(JSONObject result);
}
