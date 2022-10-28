
package com.today.gamesdk.shabdamsdk.model;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SignupRequest implements Parcelable
{

    @SerializedName("device_id")
    @Expose
    private String device_id;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("is_guest")
    @Expose
    private Boolean is_guest;

    @SerializedName("language_id")
    @Expose
    private String language_id;

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("uname")
    @Expose
    private String uname;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("profileimage")
    @Expose
    private String profileimage;
    @SerializedName("app_id")
    @Expose
    private String app_id;
    public final static Creator<SignupRequest> CREATOR = new Creator<SignupRequest>() {


        @SuppressWarnings({
            "unchecked"
        })
        public SignupRequest createFromParcel(android.os.Parcel in) {
            return new SignupRequest(in);
        }

        public SignupRequest[] newArray(int size) {
            return (new SignupRequest[size]);
        }

    }
    ;
    @NotNull
    public String device_type;
    @Nullable
    public String device_token;

    protected SignupRequest(android.os.Parcel in) {
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.language_id = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.uname = ((String) in.readValue((String.class.getClassLoader())));
        this.userId = ((String) in.readValue((String.class.getClassLoader())));
        this.profileimage = ((String) in.readValue((String.class.getClassLoader())));
        this.app_id = ((String) in.readValue((String.class.getClassLoader())));
        this.device_id = ((String) in.readValue((String.class.getClassLoader())));
        this.is_guest = (Boolean) in.readValue((String.class.getClassLoader()));

    }

    public SignupRequest() {
    }

    public String getEmail() {
        return email;
    }

    public Boolean getIs_guest() {
        return is_guest;
    }

    public String getDevice_id() {
        return device_id;
    }

    public String getLanguageId() {
        return language_id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIs_guest(Boolean is_guest) {
        this.is_guest = is_guest;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public void setLanguageId(String language_id) {
        this.language_id = language_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(email);
        dest.writeValue(name);
        dest.writeValue(uname);
        dest.writeValue(userId);
        dest.writeValue(profileimage);
        dest.writeValue(app_id);
        dest.writeValue(device_id);
        dest.writeValue(is_guest);

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
