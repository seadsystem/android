package android.sead_systems.seads.http;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * An AsyncTask that is used to make http get requests to a supplied URL
 * To use:
 * 1. Make calling class implement HTTPGetRequestAsyncTask.HTTPResponse. This is an interface that allows
 *  us to return the response
 * 2. Create an URL object associated with the proper URL we want to submit a get request to
 * 3. Inside the instantiating class, the code should look like:
 *  new HTTPGetRequestAsyncTask(this).execute(url);
 */
public class HTTPGetRequestAsyncTask extends AsyncTask<URL, Void, String> {
    /**
     * takes in the appropriate url generated in the WebInterfacer
     */
    public interface HTTPResponse {
        void httpResponseFinished(String result);
    }

    /**
     * Reference to the class that instantiates this HTTPGetRequestAsyncTask
     */
    private HTTPResponse instantiator;

    /**
     * Constructor for the HTTPGetRequestAsyncTask Asynctask
     * @param instantiator we will need a reference to the instantiating class so we can give it the
     *                     http response as a String
     */
    public HTTPGetRequestAsyncTask(HTTPResponse instantiator) {
        this.instantiator = instantiator;
    }

    /**
     * the asynctasks's bg execution. Creates a connection then reads the response
     * @param urls takes in a single URL. It won't use more than the first url given
     * @return the get request result
     */
    @Override
    protected String doInBackground(URL... urls) {
        String result;
        try {
            // Create connection
            HttpURLConnection connection = setupConnection(urls[0]);

            // Read from the connection
            result = readConnection(connection);
        } catch(Exception e){
            Log.d("HTTP", "Exception:" + e.toString());

            result = null;
        }
        return result;
    }

    /**
     * sets the connection up to do an appropriate GET request.
     * @param url
     * @return HttpURLConnection that has reasonable timeout times and is setup for GET requests
     */
    private HttpURLConnection setupConnection(URL url) {
        if(url==null) {
            return null;
        }
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.connect();
            return connection;
        } catch (IOException e) {
            // TODO: replace with toast later
            Log.d("HTTP", "setting up connection failed!");
            return null;
        }
    }

    /**
     * gets the HTTP get response in string format
     * @param connection an HtppURLConnection to where we want to pull data from
     * @return the response to the url
     */
    private String readConnection(HttpURLConnection connection) {
        if(connection == null) {
            return null;
        }
        try {
            String curLine;
            InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder sb = new StringBuilder();
            while ((curLine = reader.readLine()) != null) {
                sb.append(curLine);
            }
            reader.close();
            streamReader.close();
            connection.disconnect();
            return sb.toString();
        } catch (IOException e) {
            Log.d("HTTP", "IOException:" + e.toString());
            // If we catch a timeout error Toast to show connection didn't work
            return null;
        }
    }

    /**
     * End of AsyncTask. Will call the instatiator's httpResponseFinished method with the response
     * @param result this will be the http response
     */
    protected void onPostExecute(String result){
//        Log.d("HTTP", result);
        instantiator.httpResponseFinished(result);
    }
}