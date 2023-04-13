package de.puncty.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

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

    }
}