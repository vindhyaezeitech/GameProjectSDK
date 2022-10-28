
package com.today.gamesdk.shabdamsdk.model.dictionary;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckWordDicResponse implements Parcelable
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
    public final static Creator<CheckWordDicResponse> CREATOR = new Creator<CheckWordDicResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public CheckWordDicResponse createFromParcel(android.os.Parcel in) {
            return new CheckWordDicResponse(in);
        }

        public CheckWordDicResponse[] newArray(int size) {
            return (new CheckWordDicResponse[size]);
        }

    }
    ;

    protected CheckWordDicResponse(android.os.Parcel in) {
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.statusMessage = ((String) in.readValue((String.class.getClassLoader())));
        this.data = ((Data) in.readValue((Data.class.getClassLoader())));
    }

    public CheckWordDicResponse() {
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
