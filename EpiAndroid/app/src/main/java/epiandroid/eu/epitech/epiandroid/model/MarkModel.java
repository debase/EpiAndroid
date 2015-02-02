package epiandroid.eu.epitech.epiandroid.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by linard_f on 1/28/15.
 */
public class MarkModel {

    @SerializedName("notes")
    private MarksItem[] marksItem;

    public MarksItem[] getMarksItem() {
        return marksItem;
    }

    public void setMarksItem(MarksItem[] marksItem) {
        this.marksItem = marksItem;
    }
}
