package epiandroid.eu.epitech.epiandroid.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by debas on 28/01/15.
 */
public class InfoModel implements Serializable {
    @SerializedName("infos")
    public InfoItem infos;

    public class InfoItem implements Serializable {
        @SerializedName("login")
        public String login;

        @SerializedName("internal_email")
        public String mail;

        @SerializedName("title")
        public String title;

        @SerializedName("promo")
        public String promo;
    }
}