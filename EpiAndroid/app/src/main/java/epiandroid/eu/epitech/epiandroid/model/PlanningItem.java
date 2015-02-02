package epiandroid.eu.epitech.epiandroid.model;

import com.google.gson.annotations.SerializedName;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by remihillairet on 28/01/15.
 */
public class PlanningItem {

    @SerializedName("acti_title")
    private String title;

    @SerializedName("start")
    private String start;

    @SerializedName("end")
    private String end;

    @SerializedName("event_registered")
    private String registered;

    @SerializedName("scolaryear")
    private String scolaryear;

    @SerializedName("codemodule")
    private String codemodule;

    @SerializedName("codeinstance")
    private String codeinstance;

    @SerializedName("codeacti")
    private String codeacti;

    @SerializedName("codeevent")
    private String codeevent;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getStart() {
        return this.start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getEnd() {
        return this.end;
    }

    public void setRegistered(String registered) {
        this.registered = registered;
    }

    public String getRegistered() {
        return this.registered;
    }

    public void setScolaryear(String scolaryear) {
        this.scolaryear = scolaryear;
    }

    public String getScolaryear() {
        return this.scolaryear;
    }

    public void setCodemodule(String codemodule) {
        this.codemodule = codemodule;
    }

    public String getCodemodule() {
        return this.codemodule;
    }

    public void setCodeinstance(String codeinstance) {
        this.codeinstance = codeinstance;
    }

    public String getCodeinstance() {
        return this.codeinstance;
    }

    public void setCodeacti(String codeacti) {
        this.codeacti = codeacti;
    }

    public String getCodeacti() {
        return this.codeacti;
    }

    public void setCodeevent(String codeevent) {
        this.codeevent = codeevent;
    }

    public String getCodeevent() {
        return this.codeevent;
    }

    public String getSchedule() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;

        ParsePosition pos = new ParsePosition(0);

        date = dateFormat.parse(start, pos);
        String startStr = timeFormat.format(date);

        pos.setIndex(0);
        date = dateFormat.parse(end, pos);
        String endStr = timeFormat.format(date);
        return startStr + " - " + endStr;
    }
}
