package de.puncty.app.components;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.puncty.lib.Meetup;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import de.puncty.app.MainActivity;
import de.puncty.app.R;
import de.puncty.app.ViewMeetupActivity;
import de.puncty.app.utility.Constants;

public class MeetupCard extends RecyclerView.Adapter<MeetupCard.ViewHolder> {
    List<Meetup> meetups;
    Context context;
    public static String[] colors;

    public MeetupCard(Context context, List<Meetup> meetups) {
        this.context = context;
        this.meetups = meetups;
    }

    public MeetupCard.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_meetup_card, parent, false);
        return new MeetupCard.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MeetupCard.ViewHolder holder, int position) {
        Meetup m = meetups.get(position);
        Calendar cal = new GregorianCalendar();
        cal.setTime(m.getDatetime());
        String time = String.format("%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
        String date = String.format("%d. %s", cal.get(Calendar.DAY_OF_MONTH), Constants.month[cal.get(Calendar.MONTH)]);
        holder.locationAndTime.setText(String.format("%s / %s", m.getLocation(), time));
        holder.date.setText(date);
        int color = Color.parseColor("#" + colors[position]);
        holder.card.setBackgroundColor(color);
        holder.card.setOnClickListener(v -> {
            ViewMeetupActivity.meetup = m;
            ViewMeetupActivity.color = color;
            this.context.startActivity(new Intent(context, ViewMeetupActivity.class));
        });
    }

    @Override
    public int getItemCount() {
        return meetups.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView locationAndTime;
        TextView date;
        CardView card;

        public ViewHolder(View itemView) {
            super(itemView);

            this.locationAndTime = itemView.findViewById(R.id.meetupLocationAndTimeText);
            this.date = itemView.findViewById(R.id.meetupDateText);
            this.card = itemView.findViewById(R.id.meetupCardView);
        }
    }
}
