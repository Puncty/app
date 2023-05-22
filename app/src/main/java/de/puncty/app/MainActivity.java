package de.puncty.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        if (!Puncty.exists()) {
            startActivity(new Intent(this, LoginOrRegisterActivity.class));
            return;
        }
        if (Puncty.getInstance() == null) {
          startActivity(new Intent(this, LoginOrRegisterActivity.class));
        return;
        }

        setContentView(R.layout.activity_main);

        Button createMeetupButton = findViewById(R.id.createMeetupButton);
        createMeetupButton.setOnClickListener(v -> {
            startActivity(new Intent(this, CreateMeetupActivitiy.class));
        });
        Button viewMeetupsButton = findViewById(R.id.viewMeetupsButton);
        viewMeetupsButton.setOnClickListener(v -> {
            startActivity(new Intent(this, ViewMeetupsActivity.class));
        });
    }
}
