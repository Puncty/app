package de.puncty.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import de.puncty.app.utility.Puncty;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        if (!Puncty.exists()) {
            startActivity(new Intent(this, LoginOrRegisterActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        Button createMeetupButton = findViewById(R.id.createMeetupButton);
        createMeetupButton.setOnClickListener(v -> {
            startActivity(new Intent(this, CreateMeetupActivity.class));
        });
        Button viewMeetupsButton = findViewById(R.id.viewMeetupsButton);
        viewMeetupsButton.setOnClickListener(v -> {
            startActivity(new Intent(this, ViewMeetupsActivity.class));
        });
    }
}
