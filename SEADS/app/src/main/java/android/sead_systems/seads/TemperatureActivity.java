package android.sead_systems.seads;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class TemperatureActivity extends AppCompatActivity {

    private static double decimalPlaces = 10.0;
    private double currentTemp = 100.0;
    private boolean isFahrenheit = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

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
