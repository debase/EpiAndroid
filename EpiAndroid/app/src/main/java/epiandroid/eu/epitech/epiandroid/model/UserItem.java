package epiandroid.eu.epitech.epiandroid.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by debas on 30/01/15.
 */
public class UserItem {
    @SerializedName("picture")
    public String picture;

    @SerializedName("title")
    public String title;

    @SerializedName("url")
    public String url;

}
