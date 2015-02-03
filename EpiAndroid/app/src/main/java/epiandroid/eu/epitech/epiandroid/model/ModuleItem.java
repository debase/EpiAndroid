package epiandroid.eu.epitech.epiandroid.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by remihillairet on 03/02/15.
 */
public class ModuleItem {

    @SerializedName("title")
    private String title;

    @SerializedName("grade")
    private String grade;

    @SerializedName("scolaryear")
    private String scolaryear;

    @SerializedName("date_ins")
    private String dateSubscribed;

    @SerializedName("credits")
    private String credits;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getGrade() {
        return this.grade;
    }

    public void setScolaryear(String scolaryear) {
        this.scolaryear = scolaryear;
    }

    public String getScolaryear() {
        return this.scolaryear;
    }

    public void setDateSubscribed(String dateSubscribed) {
        this.dateSubscribed = dateSubscribed;
    }

    public String getDateSubscribed() {
        return this.dateSubscribed;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getCredits() {
        return this.credits;
    }
}