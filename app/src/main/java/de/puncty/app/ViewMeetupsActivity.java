package de.puncty.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.puncty.lib.Meetup;
import com.puncty.lib.MeetupCollection;
import com.puncty.lib.exceptions.BrokenResponse;
import com.puncty.lib.exceptions.NotFound;
import com.puncty.lib.exceptions.Unauthorized;

import java.util.ArrayList;
import java.util.List;

import de.puncty.app.components.MeetupCard;
import de.puncty.app.utility.Interpolator;

public class ViewMeetupsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_meetups);

        // HTTPS Requests CAN NOT run in the main-Thread
        new Thread(this::loadCards).start();
    }

    public void loadCards() {
        MeetupCollection mc = new MeetupCollection(Puncty.getInstance().getSession());
        List<Meetup> meetups = new ArrayList<>();
        try {
            List<String> ids = mc.joined();
            for (String id : ids) {
                meetups.add(mc.get(id));
            }
        } catch (BrokenResponse e){
            // do nothing
        } catch (NotFound e) {
            // do nothing
        } catch (Unauthorized e) {
            // do nothing
        }

        runOnUiThread(() -> {
            MeetupCard.colors = Interpolator.interpolate(0xef221f, 0x1fef76, meetups.size() + 1);
            RecyclerView meetupContainer = findViewById(R.id.MeetupContainer);
            meetupContainer.setLayoutManager(new LinearLayoutManager(this));
            meetupContainer.setAdapter(new MeetupCard(this, meetups));
        });
    }
}

