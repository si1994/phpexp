package com.quirktastic.chat.chatmodel;


import com.google.gson.annotations.SerializedName;

public class USERDETAILSItem{

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("id")
	private String id;

	@SerializedName("created_date")
	private String createdDate;

	@SerializedName("massage")
	private String massage;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("from_user_id")
	private String fromUserId;

	@SerializedName("senderProfilePic")
	private String senderProfilePic;

	@SerializedName("senderFullName")
	private String senderFullName;

	@SerializedName("senderAlphabetCapital")
	private String senderAlphabetCapital;


	@SerializedName("recvProfilePic")
	private String RecvProfilePic;

	@SerializedName("recvFullName")
	private String recvFullName;

	@SerializedName("recvAlphabetCapital")
	private String recvAlphabetCapital;

	@SerializedName("message_url")
	private String messageUrl;


	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setCreatedDate(String createdDate){
		this.createdDate = createdDate;
	}

	public String getCreatedDate(){
		return createdDate;
	}

	public void setMassage(String massage){
		this.massage = massage;
	}

	public String getMassage(){
		return massage;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setFromUserId(String fromUserId){
		this.fromUserId = fromUserId;
	}

	public String getFromUserId(){
		return fromUserId;
	}





	public String getSenderFullName() {
		return senderFullName;
	}

	public void setSenderFullName(String senderFullName) {
		this.senderFullName = senderFullName;
	}

	public String getSenderAlphabetCapital() {
		return senderAlphabetCapital;
	}

	public void setSenderAlphabetCapital(String senderAlphabetCapital) {
		this.senderAlphabetCapital = senderAlphabetCapital;
	}

	public String getSenderProfilePic() {
		return senderProfilePic;
	}

	public void setSenderProfilePic(String senderProfilePic) {
		this.senderProfilePic = senderProfilePic;
	}

	public String getRecvProfilePic() {
		return RecvProfilePic;
	}

	public void setRecvProfilePic(String recvProfilePic) {
		RecvProfilePic = recvProfilePic;
	}

	public String getRecvFullName() {
		return recvFullName;
	}

	public void setRecvFullName(String recvFullName) {
		this.recvFullName = recvFullName;
	}

	public String getRecvAlphabetCapital() {
		return recvAlphabetCapital;
	}

	public void setRecvAlphabetCapital(String recvAlphabetCapital) {
		this.recvAlphabetCapital = recvAlphabetCapital;
	}

	public String getMessageUrl() {
		return messageUrl;
	}

	public void setMessageUrl(String messageUrl) {
		this.messageUrl = messageUrl;
	}
}