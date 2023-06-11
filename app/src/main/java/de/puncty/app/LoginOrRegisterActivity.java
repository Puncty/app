package de.puncty.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.puncty.lib.Session;
import com.puncty.lib.networking.Requester;

import de.puncty.app.utility.Util;

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

        Util.attemptLogin(this, success -> {
            if (success) {
                this.startActivity(new Intent(this, MainActivity.class));
            }

            return 0;
        });

    }
}