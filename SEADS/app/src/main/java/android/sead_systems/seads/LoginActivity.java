package android.sead_systems.seads;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/** Created by talal.abouhaiba on 10/26/2016. */

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

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

//        ServerHandler serverHandler = new ServerHandler(LoginActivity.this);
//        serverHandler.serverCall();

    }

    private void login() {

        if (!validateInput()) {
            return;
        }

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Logging in");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();

        authenticateLogin();

    }

    private void successfulLogin() {
        mProgressDialog.dismiss();

        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    private void unsuccessfulLogin() {
        mProgressDialog.dismiss();
        Toast.makeText(LoginActivity.this, "Invalid login", Toast.LENGTH_SHORT).show();
    }

    private void authenticateLogin() {
        String emailInput = ((EditText)findViewById(R.id.input_email)).getText().toString();
        String passwordInput = ((EditText)findViewById(R.id.input_password)).getText().toString();


        mAuth.signInWithEmailAndPassword(emailInput, passwordInput)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            successfulLogin();
                        } else {
                            unsuccessfulLogin();
                        }
                    }
                });
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
