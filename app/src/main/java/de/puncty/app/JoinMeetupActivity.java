package de.puncty.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.puncty.lib.MeetupCollection;
import com.puncty.lib.Session;

import java.util.List;

public class JoinMeetupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_meetup);

        Uri data = getIntent().getData();
        List<String> params = data.getPathSegments();
        String meetupId = params.get(0);

        if (!Puncty.exists()) {
            startActivity(new Intent(this, LoginOrRegisterActivity.class));
            return;
        }

        new Thread(() -> {
            Session s = Puncty.getInstance().getSession();
            MeetupCollection mc = new MeetupCollection(s);
            try {
                mc.join(meetupId);
            } catch (Exception e) {
                // do nothing
            } finally {
                startActivity(new Intent(this, ViewMeetupsActivity.class));
            }
        }).start();
    }
}