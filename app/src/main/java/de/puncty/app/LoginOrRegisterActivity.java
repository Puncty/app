package de.puncty.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.puncty.lib.Session;
import com.puncty.lib.networking.Requester;

public class LoginOrRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);

        findViewById(R.id.loginButton).setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
        });

        findViewById(R.id.registerButton).setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });

        SharedPreferences creds = getSharedPreferences("credentials", MODE_PRIVATE);
        if (creds.contains("email") && creds.contains("password")) {
            new Thread(() -> {
                String email = creds.getString("email", "");
                String pw = creds.getString("password", "");

                Requester r = new Requester(Puncty.BASE_URL);
                try {
                    Puncty.create(Session.login(r, email, pw));
                    startActivity(new Intent(this, MainActivity.class));
                } catch (Exception e) {
                    // do nothing
                }
            }).start();
        }

    }
}