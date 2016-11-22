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

/** Created by talal.abouhaiba on 11/2/2016. */

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

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

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Signing up");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();

        authenticateSignup();
    }

    private void authenticateSignup() {
        final String email = ((EditText)findViewById(R.id.input_email)).getText().toString();
        final String password  = ((EditText)findViewById(R.id.input_password)).getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            successfulSignup();
                        } else {
                            unsuccessfulSignup();
                        }
                    }
                });
    }

    private void successfulSignup() {
        mProgressDialog.dismiss();

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.putExtra("USERNAME", ((EditText)findViewById(R.id.input_email)).getText().toString());
        startActivity(intent);
        finish();
    }

    private void unsuccessfulSignup() {
        mProgressDialog.dismiss();

        Toast.makeText(SignupActivity.this, "Error creating account", Toast.LENGTH_SHORT).show();
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

        if (passwordInput.isEmpty() || passwordInput.length() < 6) {
            ((EditText)findViewById(R.id.input_password)).setError("Please enter a valid password" +
                    " containing at least 6 characters.");
            findViewById(R.id.input_password).requestFocus();
            return false;
        } else {
            ((EditText)findViewById(R.id.input_password)).setError(null);
        }

        return true;

    }
}
