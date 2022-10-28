
package com.today.gamesdk.shabdamsdk.model.statistics;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Parcelable
{

    @SerializedName("game_user_id")
    @Expose
    private String gameUserId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("win")
    @Expose
    private String win;
    @SerializedName("loss")
    @Expose
    private String loss;
    @SerializedName("played")
    @Expose
    private String played;
    @SerializedName("current_streak")
    @Expose
    private String currentStreak;
    @SerializedName("max_streak")
    @Expose
    private String maxStreak;
    @SerializedName("last_game_status")
    @Expose
    private String lastGameStatus;
    @SerializedName("no_of_attempts")
    @Expose
    private NoOfAttempts noOfAttempts;
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
        this.gameUserId = ((String) in.readValue((Integer.class.getClassLoader())));
        this.userId = ((String) in.readValue((Integer.class.getClassLoader())));
        this.win = ((String) in.readValue((String.class.getClassLoader())));
        this.loss = ((String) in.readValue((String.class.getClassLoader())));
        this.played = ((String) in.readValue((String.class.getClassLoader())));
        this.currentStreak = ((String) in.readValue((String.class.getClassLoader())));
        this.maxStreak = ((String) in.readValue((String.class.getClassLoader())));
        this.lastGameStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.noOfAttempts = ((NoOfAttempts) in.readValue((NoOfAttempts.class.getClassLoader())));
    }

    public Data() {
    }

    public String getGameUserId() {
        return gameUserId;
    }

    public void setGameUserId(String gameUserId) {
        this.gameUserId = gameUserId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWin() {
        return win;
    }

    public void setWin(String win) {
        this.win = win;
    }

    public String getLoss() {
        return loss;
    }

    public void setLoss(String loss) {
        this.loss = loss;
    }

    public String getPlayed() {
        return played;
    }

    public void setPlayed(String played) {
        this.played = played;
    }

    public String getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(String currentStreak) {
        this.currentStreak = currentStreak;
    }

    public String getMaxStreak() {
        return maxStreak;
    }

    public void setMaxStreak(String maxStreak) {
        this.maxStreak = maxStreak;
    }

    public String getLastGameStatus() {
        return lastGameStatus;
    }

    public void setLastGameStatus(String lastGameStatus) {
        this.lastGameStatus = lastGameStatus;
    }

    public NoOfAttempts getNoOfAttempts() {
        return noOfAttempts;
    }

    public void setNoOfAttempts(NoOfAttempts noOfAttempts) {
        this.noOfAttempts = noOfAttempts;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(gameUserId);
        dest.writeValue(userId);
        dest.writeValue(win);
        dest.writeValue(loss);
        dest.writeValue(played);
        dest.writeValue(currentStreak);
        dest.writeValue(maxStreak);
        dest.writeValue(lastGameStatus);
        dest.writeValue(noOfAttempts);
    }

    public int describeContents() {
        return  0;
    }

}
