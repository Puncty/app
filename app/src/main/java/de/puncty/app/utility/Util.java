package de.puncty.app.utility;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.puncty.lib.Session;
import com.puncty.lib.networking.Requester;

import de.puncty.app.MainActivity;
import de.puncty.app.Puncty;
import de.puncty.app.ViewMeetupActivity;

public class Util {
    public static void attemptLogin(AppCompatActivity ctx, Predicate<Boolean, Integer> pred) {
        SharedPreferences creds = ctx.getSharedPreferences("credentials", Context.MODE_PRIVATE);
        if (creds.contains("email") && creds.contains("password")) {
            new Thread(() -> {
                String email = creds.getString("email", "");
                String pw = creds.getString("password", "");

                Requester r = new Requester(Puncty.BASE_URL);
                boolean successful = false;
                try {
                    Puncty.create(Session.login(r, email, pw));
                    successful = true;
                } catch (Exception e) {
                    // do nothing
                } finally {
                    pred.fn(successful);
                }
            }).start();
        }
    }

    public static void invite(AppCompatActivity ctx, String meetupId) {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Puncty");
            String msg = String.format("%s/meetup/%s/join", Puncty.BASE_URL, meetupId);
            shareIntent.putExtra(Intent.EXTRA_TEXT, msg);
            ctx.startActivity(Intent.createChooser(shareIntent, "Wen willst du einladen?"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
