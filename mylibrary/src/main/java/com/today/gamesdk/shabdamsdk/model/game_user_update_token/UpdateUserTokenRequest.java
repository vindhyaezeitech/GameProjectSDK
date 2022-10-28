package com.today.gamesdk.shabdamsdk.model.game_user_update_token;

import com.google.gson.annotations.SerializedName;

public class UpdateUserTokenRequest{

	@SerializedName("game_user_id")
	private String gameUserId;

	@SerializedName("language_id")
	private String language_id;

	@SerializedName("device_token")
	private String deviceToken;

	@SerializedName("device_type")
	private String deviceType;

	public void setGameUserId(String gameUserId){
		this.gameUserId = gameUserId;
	}

	public void setLanguageId(String language_id){
		this.language_id = language_id;
	}

	public String getGameUserId(){
		return gameUserId;
	}

	public String getLanguage_Id(){
		return language_id;
	}


	public void setDeviceToken(String deviceToken){
		this.deviceToken = deviceToken;
	}

	public String getDeviceToken(){
		return deviceToken;
	}

	public void setDeviceType(String deviceType){
		this.deviceType = deviceType;
	}

	public String getDeviceType(){
		return deviceType;
	}
}