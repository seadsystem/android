package android.sead_systems.seads;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class TemperatureActivity extends AppCompatActivity {

    private static double decimalPlaces = 10.0;
    private double currentTemp = 100.0;
    private boolean isFahrenheit = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        System.out.println("TESTING " + System.currentTimeMillis());

        final Button tempButton = (Button) findViewById(R.id.temperature_button);
        tempButton.setText(buildTemperatureString());
        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertTemperature();
                tempButton.setText(buildTemperatureString());
            }
        });


    }

    /**
     * @return String of temperature in Fahrenheit or Celsius
     */
    private String buildTemperatureString() {
        String temperatureString = roundTemperature();
        if (isFahrenheit) {
            temperatureString += " F°";
        } else {
            temperatureString += " C°";
        }
        return temperatureString;
    }

    /**
     * @return String of currentTemp rounded to 1 decimal place
     */
    private String roundTemperature() {
        String rounded;
        double toBeRounded = Math.round(currentTemp * decimalPlaces);
        toBeRounded /= decimalPlaces;
        rounded = Double.toString(toBeRounded);
        return rounded;
    }

    /**
     * Converts the currentTemp from either Celsius -> Fahrenheit or Fahrenheit -> Celsius
     */
    private void convertTemperature() {
        if (isFahrenheit) {
            // convert to Celsius
            currentTemp = (currentTemp - 32) * 5 / 9;
            isFahrenheit = false;
        } else {
            // convert to Fahrenheit
            currentTemp =  currentTemp * 1.8 + 32.0;
            isFahrenheit = true;
        }
    }
}

/**
 Usage: http://db.sead.systems:8080/(device id)['?' + '&'.join(.[[start_time=(start time as UTC unix timestamp)],
 [end_time=(end time as UTC unix timestamp)], [type=(Sensor type code),[device=(seadplug for SEAD plug,
 egauge or channel name for eGauge), granularity=(interval between data points in seconds of an energy list
 query, must also include list_format=energy and type=P), [diff=(1 get the data differences instead of the data),
 event=(threshold of event detection, must also include device and type=P and list_format=event)]]]],
 [subset=(subsample result down to this many rows)], [list_format=(string representing what the json list entries
 will look like)], [limit=(truncate result to this many rows)], [json=(1 get the result in pseudo JSON format)]]

 http://db.sead.systems:8080/0000001?type=W&subset=5 returns Temperature for device 0000001
 */