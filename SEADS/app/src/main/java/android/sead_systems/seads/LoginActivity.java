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

/** Created by talal.abouhaiba on 10/26/2016. */

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        Button loginButton = (Button)findViewById(R.id.button_login);
        TextView signupButton = (TextView) findViewById(R.id.button_signup);

        if (getIntent().getStringExtra("USERNAME") != null) {
            ((EditText)findViewById(R.id.input_email)).setText(getIntent().getStringExtra("USERNAME"));
            findViewById(R.id.input_password).requestFocus();
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        ServerHandler serverHandler = new ServerHandler(LoginActivity.this);
        serverHandler.serverCall();

    }

    private void login() {

        if (!validateInput()) {
            return;
        }

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in");
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        Thread loginThread = new Thread() {
            @Override
            public void run() {
                authenticateLogin();
            }
        };
        loginThread.start();

        progressDialog.dismiss();

        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
        startActivity(intent);
    }

    private void authenticateLogin() {

        /** Server call to be made here. **/

    }

    /**
     * Validates the given email and password
     * @return true if the email and password are correctly formed
     */
    private boolean validateInput() {
        String emailInput = ((EditText)findViewById(R.id.input_email)).getText().toString();
        String passwordInput = ((EditText)findViewById(R.id.input_password)).getText().toString();

        if (emailInput.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            ((EditText)findViewById(R.id.input_email)).setError("Please enter a valid email address");
            findViewById(R.id.input_email).requestFocus();
            return false;
        } else {
            ((EditText)findViewById(R.id.input_email)).setError(null);
        }

        if (passwordInput.isEmpty()) {
            ((EditText)findViewById(R.id.input_password)).setError("Please enter your password");
            findViewById(R.id.input_password).requestFocus();
            return false;
        } else {
            ((EditText)findViewById(R.id.input_password)).setError(null);
        }

        return true;
    }
}
