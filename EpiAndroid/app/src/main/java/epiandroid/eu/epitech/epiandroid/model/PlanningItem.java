package epiandroid.eu.epitech.epiandroid.model;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by remihillairet on 28/01/15.
 */
public class PlanningItem {
    private String title = "";
    private String schedule = "";

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSchedule(String start, String end) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;

        ParsePosition pos = new ParsePosition(0);

        date = dateFormat.parse(start, pos);
        String startStr = timeFormat.format(date);

        pos.setIndex(0);
        date = dateFormat.parse(end, pos);
        String endStr = timeFormat.format(date);
        this.schedule = startStr + " - " + endStr;
    }

    public String getTitle() {
        return this.title;
    }

    public String getSchedule() {
        return this.schedule;
    }
}
