package de.puncty.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.puncty.lib.Meetup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ViewMeetupsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_meetups);
        List<Meetup> meetups = new ArrayList<Meetup>();

        RecyclerView meetupContainer = findViewById(R.id.MeetupContainer);
        meetupContainer.setLayoutManager(new LinearLayoutManager(this));
        meetupContainer.setAdapter(new MeetupCardAdapter(this, meetups));
    }
}

class MeetupCardAdapter extends RecyclerView.Adapter<MeetupCardAdapter.ViewHolder> {
    List<Meetup> meetups = new ArrayList<Meetup>();
    Context context;

    public MeetupCardAdapter(Context context, List<Meetup> meetups) {
        this.context = context;
        this.meetups = meetups;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_meetup_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Meetup meetup = meetups.get(position);
        Calendar cal = Calendar.getInstance();
        cal.setTime(meetup.getDatetime());
        holder.meetingPlace.setText("Ort: " + meetup.getLocation());
        holder.meetingDate.setText("Datum: " + cal.get(Calendar.DAY_OF_MONTH) + "." + cal.get(Calendar.MONTH));
        holder.meetingTime.setText("Uhrzeit: " + cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE) + "Uhr");
        holder.meetingMembers.setText("Members: " + meetup.getMembers());
        float colorSpec = (float)position/meetups.size();
        holder.cardView.setCardBackgroundColor(Color.parseColor(setColor(-222*(float)Math.pow(colorSpec, 4)+222, -200*(float)Math.pow(colorSpec-1,4)+200,19)));

    }
    public String setColor(float r, float g, int b){
        String color = "#";
        if(r>=16){
            color += Integer.toHexString((int)r);
        }else{
            color += "0" + Integer.toHexString((int)r);
        }
        if(g>=16){
            color += Integer.toHexString((int)g);
        }else{
            color+="0"+ Integer.toHexString((int)g);

        }

        color += Integer.toHexString(b);
        return color;

    }

    @Override
    public int getItemCount() {
        return meetups.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView meetingPlace;
        public TextView meetingDate;
        public TextView meetingTime;
        public TextView meetingMembers;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            meetingPlace = itemView.findViewById(R.id.meetupLocation);
            meetingDate = itemView.findViewById(R.id.meetupDate);
            meetingTime = itemView.findViewById(R.id.meetupTime);
            meetingMembers = itemView.findViewById(R.id.meetupMembers);
            cardView = itemView.findViewById(R.id.templateCardView);

        }
    }
}
