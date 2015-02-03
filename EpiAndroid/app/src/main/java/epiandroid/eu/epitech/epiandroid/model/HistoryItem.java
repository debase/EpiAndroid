package epiandroid.eu.epitech.epiandroid.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by debas on 03/02/15.
 */
public class HistoryItem implements Serializable {
    @SerializedName("title")
    public String title;

    @SerializedName("content")
    public String content;

    @SerializedName("date")
    public String historyDate;

    @SerializedName("user")
    public UserItem user;
}