package de.puncty.app.utility;

import com.puncty.lib.Meetup;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MeetupSorter {
    public static void meetupSorter(List<Meetup> meetups){
        meetups.sort(Comparator.comparing(Meetup::getDatetime));
    }
}
