package epiandroid.eu.epitech.epiandroid.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by debas on 28/01/15.
 */
public class InfoModel implements Serializable {
    @SerializedName("infos")
    public InfoItem infos;

    @SerializedName("current")
    public InfoCurrentItem infoCurrent;

    @SerializedName("history")
    public HistoryItem[] history;

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

    public class InfoCurrentItem implements Serializable {
        @SerializedName("active_log")
        public String active_log;

        @SerializedName("achieved")
        public String current_credit;

        @SerializedName("credits_obj")
        public String objectif_credit;

        @SerializedName("semester_num")
        public String user_semester;

    }
}