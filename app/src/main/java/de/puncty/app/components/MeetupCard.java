package de.puncty.app.components;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.puncty.lib.Meetup;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import de.puncty.app.R;

public class MeetupCard extends RecyclerView.Adapter<MeetupCard.ViewHolder> {
    List<Meetup> meetups;
    Context context;
    final String[] month = {
            "Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember"
    };

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
        String time = String.format("%02d:%02d", cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE));
        String date = String.format("%d. %s", cal.get(Calendar.DAY_OF_MONTH), month[cal.get(Calendar.MONTH)]);
        holder.locationAndTime.setText(String.format("%s / %s", m.getLocation(), time));
        holder.date.setText(date);
    }

    public String setColor(float r, float g, int b) {
        String color = "#";
        if (r >= 16) {
            color += Integer.toHexString((int) r);
        } else {
            color += "0" + Integer.toHexString((int) r);
        }
        if (g >= 16) {
            color += Integer.toHexString((int) g);
        } else {
            color += "0" + Integer.toHexString((int) g);
        }

        color += Integer.toHexString(b);
        return color;
    }

    @Override
    public int getItemCount() {
        return meetups.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView locationAndTime;
        TextView date;

        public ViewHolder(View itemView) {
            super(itemView);

            this.locationAndTime = itemView.findViewById(R.id.meetupLocationAndTimeText);
            this.date = itemView.findViewById(R.id.meetupDateText);
        }
    }
}
