package com.today.gamesdk.shabdamsdk.model.deleteAccount;

import com.google.gson.annotations.SerializedName;

public class DeleteAccountResponse{

	@SerializedName("status_message")
	private String statusMessage;

	@SerializedName("status")
	private String status;

	public String getStatusMessage(){
		return statusMessage;
	}

	public String getStatus(){
		return status;
	}
}