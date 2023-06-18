package de.puncty.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.puncty.lib.Meetup;
import com.puncty.lib.User;
import com.puncty.lib.UserCollection;
import com.puncty.lib.exceptions.BrokenResponse;
import com.puncty.lib.exceptions.Unauthorized;

import java.util.Calendar;
import java.util.GregorianCalendar;

import de.puncty.app.utility.Constants;
import de.puncty.app.utility.Iterator;
import de.puncty.app.utility.Puncty;
import de.puncty.app.utility.Toaster;
import de.puncty.app.utility.Util;

public class ViewMeetupActivity extends AppCompatActivity {

    public static int color;
    public static Meetup meetup;

    private TextView location;
    private TextView timeView;
    private TextView dateView;
    private TextView membersView;
    private CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_meetup);

        this.location = findViewById(R.id.meetupLocationTextView);
        this.timeView = findViewById(R.id.meetupTimeTextView);
        this.dateView = findViewById(R.id.meetupDateTextView);
        this.membersView = findViewById(R.id.meetupMembersTextView);
        this.cardView = findViewById(R.id.fullsizeMeetupCardView);

        Calendar cal = new GregorianCalendar();
        cal.setTime(ViewMeetupActivity.meetup.getDatetime());
        String time = String.format("%02d:%02d", cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE));
        String date = String.format("%d. %s", cal.get(Calendar.DAY_OF_MONTH), Constants.month[cal.get(Calendar.MONTH)]);
        String members = String.join(", ", Iterator.map(ViewMeetupActivity.meetup.getMembers(), User::getName));
        this.location.setText(ViewMeetupActivity.meetup.getLocation());
        this.timeView.setText(time);
        this.dateView.setText(date);
        this.membersView.setText(members);
        this.cardView.setBackgroundColor(ViewMeetupActivity.color);

        Button inviteButton = findViewById(R.id.meetupInviteButton);
        Button editButton = findViewById(R.id.meetupEditButton);
        Button leaveButton = findViewById(R.id.meetupLeaveButton);
        Button deleteButton = findViewById(R.id.meetupDeleteButton);

        inviteButton.setOnClickListener(v -> {
            Util.invite(this, ViewMeetupActivity.meetup.getId());
        });

        editButton.setOnClickListener(v -> {
            // edit the meetup
        });

        leaveButton.setOnClickListener(v -> {
            new Thread(() -> {
                try {
                    meetup.leave();
                    this.startActivity(new Intent(this, ViewMeetupsActivity.class));
                } catch (BrokenResponse e) {
                    e.printStackTrace();
                }
            }).start();
        });

        deleteButton.setOnClickListener(v -> {
            new Thread(() -> {
                try {
                    meetup.delete();
                    this.startActivity(new Intent(this, ViewMeetupsActivity.class));
                } catch (BrokenResponse e) {
                    e.printStackTrace();
                } catch (Unauthorized e) {
                    Toaster.error(this, "Du bist nicht dazu berechtigt");
                }
            }).start();
        });

        new Thread(() -> {
            UserCollection uc = Puncty.getInstance().getUserCollection();
            boolean isAdmin = false;
            try {
                isAdmin = ViewMeetupActivity.meetup.getAdmin().equals(uc.getMe());
            } catch (BrokenResponse e) {
                // do nothing
            }
            if (!isAdmin) {
                runOnUiThread(() -> {
                    editButton.setVisibility(View.INVISIBLE);
                    deleteButton.setVisibility(View.INVISIBLE);
                });
            }
        }).start();
    }
}