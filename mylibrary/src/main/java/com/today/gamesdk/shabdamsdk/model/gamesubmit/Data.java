
package com.today.gamesdk.shabdamsdk.model.gamesubmit;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("game_user_id")
    @Expose
    private String gameUserId;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("game_status")
    @Expose
    private String gameStatus;
    public final static Creator<Data> CREATOR = new Creator<Data>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Data createFromParcel(android.os.Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return (new Data[size]);
        }

    }
    ;

    protected Data(android.os.Parcel in) {
        this.id = ((String) in.readValue((Integer.class.getClassLoader())));
        this.gameUserId = ((String) in.readValue((Integer.class.getClassLoader())));
        this.time = ((String) in.readValue((String.class.getClassLoader())));
        this.gameStatus = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Data() {
    }

    public String  getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGameUserId() {
        return gameUserId;
    }

    public void setGameUserId(String gameUserId) {
        this.gameUserId = gameUserId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(gameUserId);
        dest.writeValue(time);
        dest.writeValue(gameStatus);
    }

    public int describeContents() {
        return  0;
    }

}
