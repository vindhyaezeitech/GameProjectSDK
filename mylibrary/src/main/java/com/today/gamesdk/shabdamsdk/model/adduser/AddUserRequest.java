package com.today.gamesdk.shabdamsdk.model.adduser;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddUserRequest implements Parcelable
{

    @SerializedName("user_id")
    @Expose
    private String userId;

    @SerializedName("lang_id")
    @Expose
    private String lang_id;

    @SerializedName("name")
    @Expose
    private String name = " ";
    @SerializedName("uname")
    @Expose
    private String uname = " ";
    @SerializedName("email")
    @Expose
    private String email = " ";
    @SerializedName("profileimage")
    @Expose
    private String profileimage = " ";
    @SerializedName("app_id")
    @Expose
    private String app_id;
    public final static Creator<AddUserRequest> CREATOR = new Creator<AddUserRequest>() {


        @SuppressWarnings({
            "unchecked"
        })
        public AddUserRequest createFromParcel(android.os.Parcel in) {
            return new AddUserRequest(in);
        }

        public AddUserRequest[] newArray(int size) {
            return (new AddUserRequest[size]);
        }

    }
    ;

    protected AddUserRequest(android.os.Parcel in) {
        this.userId = ((String) in.readValue((String.class.getClassLoader())));
        this.lang_id = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.uname = ((String) in.readValue((String.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.profileimage = ((String) in.readValue((String.class.getClassLoader())));
        this.app_id = ((String) in.readValue((String.class.getClassLoader())));

    }

    public AddUserRequest() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(userId);
        dest.writeValue(lang_id);
        dest.writeValue(name);
        dest.writeValue(uname);
        dest.writeValue(email);
        dest.writeValue(profileimage);
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
