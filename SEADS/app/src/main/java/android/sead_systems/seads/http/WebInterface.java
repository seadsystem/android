package android.sead_systems.seads.http;

import org.json.JSONObject;

/**
 * WebInterface
 * This interface should be implemented when using the WebInterfacer.
 * This interface provides a method to act when we receive a response from the server
 */
public interface WebInterface {
    /**
     * called from WebInterfacer when an HTTP response has been received
     * @param result the result of the HTTP response
     */
    void onJSONRetrieved(JSONObject result);
}
