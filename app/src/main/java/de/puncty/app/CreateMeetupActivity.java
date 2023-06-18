package de.puncty.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.puncty.lib.MeetupCollection;
import com.puncty.lib.exceptions.BrokenResponse;

import java.util.Calendar;
import java.util.GregorianCalendar;

import de.puncty.app.utility.Puncty;
import de.puncty.app.utility.Toaster;
import de.puncty.app.utility.Util;

public class CreateMeetupActivity extends AppCompatActivity {

    private EditText locationText;
    private EditText dateText;
    private EditText timeText;
    private final GregorianCalendar datetime = new GregorianCalendar();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meetup_activitiy);

        this.locationText = findViewById(R.id.editTextTextPostalAddress2);
        this.dateText = findViewById(R.id.editTextDate);

        this.dateText.setOnClickListener(v -> {
            DatePicker tmpDate = new DatePicker(this);
            new DatePickerDialog.Builder(this)
                    .setTitle("Datum des Treffens")
                    .setView(tmpDate)
                    .setPositiveButton("Setzen", (DialogInterface dialog, int which) -> {
                        this.datetime.set(Calendar.DAY_OF_MONTH, tmpDate.getDayOfMonth());
                        this.datetime.set(Calendar.MONTH, tmpDate.getMonth());
                        this.datetime.set(Calendar.YEAR, tmpDate.getYear());
                        this.dateText.setText(String.format("%02d.%02d.%02d", tmpDate.getDayOfMonth(), tmpDate.getMonth(), tmpDate.getYear()));
                    }).show();
        });

        this.timeText = findViewById(R.id.editTextHour);
        this.timeText.setOnClickListener(v -> {
            TimePicker tmpTime = new TimePicker(this);
            tmpTime.setIs24HourView(true);
            new TimePickerDialog.Builder(this)
                    .setTitle("Uhrzeit des Treffens")
                    .setView(tmpTime)
                    .setPositiveButton("Setzen", (DialogInterface dialog, int which) -> {
                        this.datetime.set(Calendar.HOUR, tmpTime.getHour());
                        this.datetime.set(Calendar.MINUTE, tmpTime.getMinute());
                        this.timeText.setText(String.format("%02d:%02d", tmpTime.getHour(), tmpTime.getMinute()));
                    })
                    .show();
        });

        findViewById(R.id.submitButton).setOnClickListener(v -> this.onButtonClick());
    }

    private void onButtonClick() {
        new Thread(() -> {
            if (this.locationText.length() == 0 || this.dateText.length() == 0 || this.timeText.length() == 0) {
                Toaster.error(this, "Bitte fülle alle Felder aus");
                return;
            }

            MeetupCollection mc = Puncty.getInstance().getMeetupCollection();
            try {
                String id = mc.create(this.datetime.getTime().getTime(), this.locationText.getText().toString());
                finish();
                startActivity(new Intent(this, MainActivity.class));
                Util.invite(this, id);
            } catch (BrokenResponse e) {
                Toaster.error(this, "Etwas ist schief gelaufen...");
            }
        }).start();
    }
}