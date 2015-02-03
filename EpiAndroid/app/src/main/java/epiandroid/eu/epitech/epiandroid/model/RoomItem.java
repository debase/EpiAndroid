package epiandroid.eu.epitech.epiandroid.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by remihillairet on 03/02/15.
 */
public class RoomItem {

    @SerializedName("type")
    private String type;

    @SerializedName("seats")
    private String seats;

    @SerializedName("code")
    private String code;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getSeats() {
        return this.seats;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
