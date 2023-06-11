package de.puncty.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.puncty.lib.MeetupCollection;
import com.puncty.lib.Session;

import java.util.List;

import de.puncty.app.utility.Util;

public class JoinMeetupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_meetup);

        Uri data = getIntent().getData();
        List<String> params = data.getPathSegments();
        String meetupId = params.get(0);

        if (!Puncty.exists()) {
            Util.attemptLogin(this, success -> {
                if (success) {
                    joinMeetup(meetupId);
                } else {
                    this.startActivity(new Intent(this, LoginOrRegisterActivity.class));
                }

                return 0;
            });
        } else {
            joinMeetup(meetupId);
        }

    }

    public void joinMeetup(String id) {
        new Thread(() -> {
            MeetupCollection mc = Puncty.getInstance().getMeetupCollection();
            try {
                mc.join(id);
            } catch (Exception e) {
                // do nothing
            } finally {
                startActivity(new Intent(this, ViewMeetupsActivity.class));
            }
        }).start();
    }
}