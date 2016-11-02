package android.sead_systems.seads;

import android.content.Intent;
import android.os.Bundle;
import android.sead_systems.seads.graph.DemoActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/** Created by talal.abouhaiba on 10/26/2016. */

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        Button loginButton = (Button)findViewById(R.id.loginbutton);
        Button signupButton = (Button)findViewById(R.id.signupbutton);
        findViewById(R.id.loginbutton).getBackground().setAlpha(0);
        findViewById(R.id.signupbutton).getBackground().setAlpha(100);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(intent);

            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

    }

}
