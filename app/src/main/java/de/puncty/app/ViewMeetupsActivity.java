package de.puncty.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.puncty.lib.Meetup;
import com.puncty.lib.MeetupCollection;

import java.util.ArrayList;
import java.util.List;

import de.puncty.app.components.MeetupCard;
import de.puncty.app.utility.ColorGenerator;
import de.puncty.app.utility.MeetupSorter;
import de.puncty.app.utility.Puncty;
import de.puncty.app.utility.Toaster;

public class ViewMeetupsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_meetups);

        // HTTPS Requests CAN NOT run in the main-Thread
        new Thread(this::loadCards).start();
    }

    public void loadCards() {
        MeetupCollection mc = Puncty.getInstance().getMeetupCollection();
        List<Meetup> meetups = new ArrayList<>();
        try {
            List<String> ids = mc.joined();
            for (String id : ids) {
                meetups.add(mc.get(id));
            }
        } catch (Exception e){
            Toaster.error(this, "Etwas ist schief gelaufen...");
            e.printStackTrace();
        }

        runOnUiThread(() -> {
            MeetupSorter.meetupSorter(meetups);
            MeetupCard.colors = ColorGenerator.generate(meetups.size());
            RecyclerView meetupContainer = findViewById(R.id.MeetupContainer);
            meetupContainer.setLayoutManager(new LinearLayoutManager(this));
            meetupContainer.setAdapter(new MeetupCard(this, meetups));
        });
    }
}

