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
}
