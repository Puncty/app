package de.puncty.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.puncty.lib.Meetup;
import com.puncty.lib.MeetupCollection;
import com.puncty.lib.exceptions.BrokenResponse;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class CreateMeetupActivitiy extends AppCompatActivity {

    EditText locationText;
    EditText monthText;
    EditText dayText;
    EditText hourText;
    EditText minuteText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meetup_activitiy);

        locationText = findViewById(R.id.editTextTextPostalAddress2);
        monthText = findViewById(R.id.editTextMonth);
        dayText = findViewById(R.id.editTextDay);
        hourText = findViewById(R.id.editTextHour);
        minuteText = findViewById(R.id.editTextMinute);
    }

    private void onButtonClick() throws BrokenResponse {
        boolean existaAlready = false;
        MeetupCollection mc = Puncty.getInstance().getMeetupCollection();
        LocalDateTime now = LocalDateTime.now();
        int second = 0, minute = Integer.parseInt(String.valueOf(monthText.getText())), hour = Integer.parseInt(String.valueOf(hourText.getText())), day = Integer.parseInt(String.valueOf(dayText.getText())), month = Integer.parseInt(String.valueOf(monthText.getText())), year = now.getYear();
        LocalDateTime meetupTime = LocalDateTime.of(year, month, day, hour, minute, second);

        if(now.isAfter(meetupTime)){
            meetupTime = LocalDateTime.of(year+1, month, day, hour, minute, second);
        }

        Instant instant = meetupTime.atZone(ZoneId.systemDefault()).toInstant();
        Date meetupDate = Date.from(instant);

        Meetup meetup = mc.create(meetupDate.getTime(), String.valueOf(locationText.getText()));

    }
}