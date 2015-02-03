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
}
