package android.sead_systems.seads;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/** Created by talal.abouhaiba on 11/2/2016. */

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        TextView loginButton = (TextView)findViewById(R.id.button_login);
        Button signupButton = (Button) findViewById(R.id.button_signup);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

    }

    /**
     * Removes transition when moving back to {@link LoginActivity}
     */
    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
    }

    private void signup() {
        if (!validateInput()) {
            return;
        }

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing up");
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        Thread loginThread = new Thread() {
            @Override
            public void run() {
                authenticateSignup();
            }
        };
        loginThread.start();

        progressDialog.dismiss();

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.putExtra("USERNAME", ((EditText)findViewById(R.id.input_email)).getText().toString());
        startActivity(intent);

    }

    private void authenticateSignup() {

    }

    private boolean validateInput() {
        String nameInput = ((EditText)findViewById(R.id.input_name)).getText().toString();
        String emailInput = ((EditText)findViewById(R.id.input_email)).getText().toString();
        String passwordInput = ((EditText)findViewById(R.id.input_password)).getText().toString();

        if (nameInput.isEmpty()) {
            ((EditText)findViewById(R.id.input_name)).setError("Please enter your name");
            findViewById(R.id.input_name).requestFocus();
            return false;
        } else {
            ((EditText)findViewById(R.id.input_name)).setError(null);
        }


        if (emailInput.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            ((EditText)findViewById(R.id.input_email)).setError("Please enter a valid email address");
            findViewById(R.id.input_email).requestFocus();
            return false;
        } else {
            ((EditText)findViewById(R.id.input_email)).setError(null);
        }

        if (passwordInput.isEmpty()) {
            ((EditText)findViewById(R.id.input_password)).setError("Please enter a valid password");
            findViewById(R.id.input_password).requestFocus();
            return false;
        } else {
            ((EditText)findViewById(R.id.input_password)).setError(null);
        }

        return true;

    }
}
