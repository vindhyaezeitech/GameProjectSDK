package com.today.gamesdk.shabdamsdk.model.deleteAccount;

import com.google.gson.annotations.SerializedName;

public class DeleteAccountRequest{

	@SerializedName("game_user_id")
	private String gameUserId;

	@SerializedName("language_id")
	private String language_id;

	public void setGameUserId(String gameUserId){
		this.gameUserId = gameUserId;
	}

	public void setLanguageId(String language_id){
		this.language_id = language_id;
	}

	public String getGameUserId(){
		return gameUserId;
	}

	public String getLanguageId(){
		return language_id;
	}
}