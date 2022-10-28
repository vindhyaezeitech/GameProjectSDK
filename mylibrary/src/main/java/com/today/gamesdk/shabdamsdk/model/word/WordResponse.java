
package com.today.gamesdk.shabdamsdk.model.word;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WordResponse implements Parcelable
{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("status_message")
    @Expose
    private String statusMessage;
    @SerializedName("data")
    @Expose
    private Data data;
    public final static Creator<WordResponse> CREATOR = new Creator<WordResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public WordResponse createFromParcel(android.os.Parcel in) {
            return new WordResponse(in);
        }

        public WordResponse[] newArray(int size) {
            return (new WordResponse[size]);
        }

    }
    ;

    protected WordResponse(android.os.Parcel in) {
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.statusMessage = ((String) in.readValue((String.class.getClassLoader())));
        this.data = ((Data) in.readValue((Data.class.getClassLoader())));
    }

    public WordResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(statusMessage);
        dest.writeValue(data);
    }

    public int describeContents() {
        return  0;
    }

}
