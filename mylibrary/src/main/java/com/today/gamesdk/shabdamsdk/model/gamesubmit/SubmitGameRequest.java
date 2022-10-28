
package com.today.gamesdk.shabdamsdk.model.gamesubmit;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubmitGameRequest implements Parcelable
{

    @SerializedName("game_user_id")
    @Expose
    private String gameUserId;

    @SerializedName("lang_id")
    @Expose
    private String lang_id;

    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("game_status")
    @Expose
    private String gameStatus;
    @SerializedName("app_id")
    @Expose
    private String app_id;

    @SerializedName("no_of_attempt")
    @Expose
    private Integer noOfAttempt;
    public final static Creator<SubmitGameRequest> CREATOR = new Creator<SubmitGameRequest>() {


        @SuppressWarnings({
            "unchecked"
        })
        public SubmitGameRequest createFromParcel(android.os.Parcel in) {
            return new SubmitGameRequest(in);
        }

        public SubmitGameRequest[] newArray(int size) {
            return (new SubmitGameRequest[size]);
        }

    }
    ;

    protected SubmitGameRequest(android.os.Parcel in) {
        this.gameUserId = ((String) in.readValue((String.class.getClassLoader())));
        this.lang_id = ((String) in.readValue((String.class.getClassLoader())));
        this.time = ((String) in.readValue((String.class.getClassLoader())));
        this.gameStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.noOfAttempt = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.app_id = ((String) in.readValue((Integer.class.getClassLoader())));


    }

    public SubmitGameRequest() {
    }

    public String getGameUserId() {
        return gameUserId;
    }

    public String getLanguageId() {
        return lang_id;
    }

    public void setGameUserId(String gameUserId) {
        this.gameUserId = gameUserId;
    }

    public void setLanguageId(String lang_id) {
        this.lang_id = lang_id;
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

    public Integer getNoOfAttempt() {
        return noOfAttempt;
    }

    public void setNoOfAttempt(Integer noOfAttempt) {
        this.noOfAttempt = noOfAttempt;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(gameUserId);
        dest.writeValue(lang_id);
        dest.writeValue(time);
        dest.writeValue(gameStatus);
        dest.writeValue(noOfAttempt);
        dest.writeValue(app_id);


    }

    public int describeContents() {
        return  0;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

}
