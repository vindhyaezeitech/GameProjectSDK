
package com.today.gamesdk.shabdamsdk.model;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetWordRequest implements Parcelable
{

    @SerializedName("game_user_id")
    @Expose
    private String userId;

    @SerializedName("lang_id")
    @Expose
    private String lang_id;

    @SerializedName("word_id")
    @Expose
    private List<String> wordId = null;
    @SerializedName("app_id")
    @Expose
    private String app_id;
    public final static Creator<GetWordRequest> CREATOR = new Creator<GetWordRequest>() {


        @SuppressWarnings({
            "unchecked"
        })
        public GetWordRequest createFromParcel(android.os.Parcel in) {
            return new GetWordRequest(in);
        }

        public GetWordRequest[] newArray(int size) {
            return (new GetWordRequest[size]);
        }

    }
    ;

    protected GetWordRequest(android.os.Parcel in) {
        this.userId = ((String) in.readValue((String.class.getClassLoader())));
        this.lang_id = ((String) in.readValue((String.class.getClassLoader())));
        this.app_id = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.wordId, (String.class.getClassLoader()));
    }

    public GetWordRequest() {
    }

    public String getUserId() {
        return userId;
    }

    public String getLanguageId() {
        return lang_id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setLanguageId(String lang_id) {
        this.lang_id = lang_id;
    }

    public List<String> getWordId() {
        return wordId;
    }

    public void setWordId(List<String> wordId) {
        this.wordId = wordId;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(userId);
        dest.writeValue(lang_id);
        dest.writeList(wordId);
        dest.writeValue(app_id);


    }

    public int describeContents() {
        return  0;
    }

}
