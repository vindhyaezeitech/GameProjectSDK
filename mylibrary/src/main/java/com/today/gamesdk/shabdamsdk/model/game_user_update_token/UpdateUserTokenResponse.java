package com.today.gamesdk.shabdamsdk.model.game_user_update_token;

import com.google.gson.annotations.SerializedName;

public class UpdateUserTokenResponse{

	@SerializedName("status_message")
	private String statusMessage;

	@SerializedName("data")
	private Data data;

	@SerializedName("status")
	private String status;

	public String getStatusMessage(){
		return statusMessage;
	}

	public Data getData(){
		return data;
	}

	public String getStatus(){
		return status;
	}
}