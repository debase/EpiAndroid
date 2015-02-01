package epiandroid.eu.epitech.epiandroid.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by linard_f on 1/22/15.
 */
public class MarksItem {

    @SerializedName("title")
    private String name;

    @SerializedName("final_note")
    private String mark;

    @SerializedName("correcteur")
    private String rater;

    public MarksItem(String name, String mark) {
        this.name = name;
        this.mark = mark;
    }

    public MarksItem() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getRater() {
        return rater;
    }

    public void setRater(String rater) {
        this.rater = rater;
    }
}
