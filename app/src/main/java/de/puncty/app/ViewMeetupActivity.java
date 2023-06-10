package de.puncty.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.puncty.lib.Meetup;

import java.util.Calendar;
import java.util.GregorianCalendar;

import de.puncty.app.utility.Constants;

public class ViewMeetupActivity extends AppCompatActivity {

    public static int color;
    public static Meetup meetup;

    private TextView location;
    private TextView timeView;
    private TextView dateView;
    private CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_meetup);

        this.location = findViewById(R.id.meetupLocationTextView);
        this.timeView = findViewById(R.id.meetupTimeTextView);
        this.dateView = findViewById(R.id.meetupDateTextView);
        this.cardView = findViewById(R.id.fullsizeMeetupCardView);

        Calendar cal = new GregorianCalendar();
        cal.setTime(ViewMeetupActivity.meetup.getDatetime());
        String time = String.format("%02d:%02d", cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE));
        String date = String.format("%d. %s", cal.get(Calendar.DAY_OF_MONTH), Constants.month[cal.get(Calendar.MONTH)]);
        this.location.setText(this.meetup.getLocation());
        this.timeView.setText(time);
        this.dateView.setText(date);
        this.cardView.setBackgroundColor(this.color);
    }
}