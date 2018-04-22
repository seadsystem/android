package com.seads.seadsv3;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Allows the user to sign up or return to the log in page.
 * @author Talal Abou Haiba
 */

public class SignupActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private ProgressDialog mProgressDialog;

    private EditText mNameField;
    private EditText mEmailField;
    private EditText mPasswordField;

    private TextView mLoginButton;
    private Button mSignupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

//        mNameField = (EditText) findViewById(R.id.input_name);
//        mEmailField = (EditText) findViewById(R.id.input_email);
//        mPasswordField = (EditText) findViewById(R.id.input_password);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

//        mLoginButton = (TextView) findViewById(R.id.button_login);
//        mSignupButton = (Button) findViewById(R.id.button_signup);
//
//        mLoginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                startActivity(intent);
//            }
//        });

//        mSignupButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                signup();
//            }
//        });

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
        final String email = mEmailField.getText().toString();
        final String password  = mPasswordField.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            successfulSignup();
                            writeNewUser(email, task.getResult().getUser().getUid());
                        } else {
                            unsuccessfulSignup();
                        }
                    }
                });
    }

    private void successfulSignup() {
        mProgressDialog.dismiss();

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.putExtra("USERNAME", mEmailField.getText().toString());
        startActivity(intent);
        finish();
    }

    private void unsuccessfulSignup() {
        mProgressDialog.dismiss();

        Toast.makeText(SignupActivity.this, "Error creating account", Toast.LENGTH_SHORT).show();
    }

    private void writeNewUser(String email, String uuid) {
        System.out.println("Writing user: " + email + " " + uuid);
        mDatabase.child("users").child(uuid).child("name").setValue(mNameField.getText().toString());
    }

    private boolean validateInput() {
        String nameInput = mNameField.getText().toString();
        String emailInput = mEmailField.getText().toString();
        String passwordInput = mPasswordField.getText().toString();

        if (nameInput.isEmpty()) {
            mNameField.setError("Please enter your name");
            mNameField.requestFocus();
            return false;
        } else {
            mNameField.setError(null);
        }


        if (emailInput.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            mEmailField.setError("Please enter a valid email address");
            mEmailField.requestFocus();
            return false;
        } else {
            mEmailField.setError(null);
        }

        if (passwordInput.isEmpty() || passwordInput.length() < 6) {
            mPasswordField.setError("Please enter a valid password" +
                    " containing at least 6 characters.");
            mPasswordField.requestFocus();
            return false;
        } else {
            mPasswordField.setError(null);
        }

        return true;

    }
}
