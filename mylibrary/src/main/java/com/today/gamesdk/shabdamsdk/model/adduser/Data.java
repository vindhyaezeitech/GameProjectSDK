package com.today.gamesdk.shabdamsdk.model.adduser;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("device_id")
    @Expose
    private String device_id;

    @SerializedName("is_guest")
    @Expose
    private Boolean is_guest;

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("uname")
    @Expose
    private String uname;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("profileimage")
    @Expose
    private String profileimage;

    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("modified")

    @Expose
    private String modified;
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
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.uname = ((String) in.readValue((String.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.profileimage = ((String) in.readValue((String.class.getClassLoader())));
        this.created = ((String) in.readValue((String.class.getClassLoader())));
        this.modified = ((String) in.readValue((String.class.getClassLoader())));
        this.device_id = ((String) in.readValue((String.class.getClassLoader())));
        this.is_guest = ((Boolean) in.readValue((String.class.getClassLoader())));
    }

    public Data() {
    }

    public String getId() {
        return id.toString();
    }

    public Boolean getIs_guest() {
        return is_guest;
    }

    public String getDevice_id() {
        return device_id.toString();
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIs_guest(Boolean is_guest) {
        this.is_guest = is_guest;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(uname);
        dest.writeValue(email);
        dest.writeValue(profileimage);
        dest.writeValue(created);
        dest.writeValue(modified);
        dest.writeValue(device_id);
        dest.writeValue(is_guest);
    }

    public int describeContents() {
        return  0;
    }

}
