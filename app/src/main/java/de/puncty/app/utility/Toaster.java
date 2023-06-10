package de.puncty.app.utility;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Toaster {
    public static void print(AppCompatActivity activity, String text, int duration) {
        activity.runOnUiThread(() -> Toast.makeText(activity, text, duration).show());
    }

    public static void error(AppCompatActivity activity, String text) {
        Toaster.print(activity, text, Toast.LENGTH_SHORT);
    }
}
