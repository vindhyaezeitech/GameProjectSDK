package com.today.gamesdk.shabdamsdk.model.game_user_update_token;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("uname")
	private String uname;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("created")
	private String created;

	@SerializedName("device_token")
	private String deviceToken;

	@SerializedName("name")
	private String name;

	@SerializedName("modified")
	private String modified;

	@SerializedName("device_type")
	private String deviceType;

	@SerializedName("id")
	private int id;

	@SerializedName("email")
	private String email;

	@SerializedName("profileimage")
	private String profileimage;

	public String getUname(){
		return uname;
	}

	public int getUserId(){
		return userId;
	}

	public String getCreated(){
		return created;
	}

	public String getDeviceToken(){
		return deviceToken;
	}

	public String getName(){
		return name;
	}

	public String getModified(){
		return modified;
	}

	public String getDeviceType(){
		return deviceType;
	}

	public int getId(){
		return id;
	}

	public String getEmail(){
		return email;
	}

	public String getProfileimage(){
		return profileimage;
	}
}