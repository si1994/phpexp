package com.quirktastic.instagram.model.instagram;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class DataItem {

	@SerializedName("created_time")
	private String createdTime;

	@SerializedName("images")
	private Images images;

	@SerializedName("comments")
	private Comments comments;

	@SerializedName("users_in_photo")
	private List<UsersInPhotoItem> usersInPhoto;

	@SerializedName("user_has_liked")
	private boolean userHasLiked;

	@SerializedName("link")
	private String link;

	@SerializedName("caption")
	private Caption caption;

	@SerializedName("type")
	private String type;

	@SerializedName("tags")
	private List<String> tags;

	@SerializedName("filter")
	private String filter;

	@SerializedName("attribution")
	private Object attribution;

	@SerializedName("location")
	private Object location;

	@SerializedName("id")
	private String id;

	@SerializedName("user")
	private User user;

	@SerializedName("likes")
	private Likes likes;

	@SerializedName("videos")
	private Videos videos;

	@SerializedName("carousel_media")
	private List<CarouselMediaItem> carouselMedia;

	public void setCreatedTime(String createdTime){
		this.createdTime = createdTime;
	}

	public String getCreatedTime(){
		return createdTime;
	}

	public void setImages(Images images){
		this.images = images;
	}

	public Images getImages(){
		return images;
	}

	public void setComments(Comments comments){
		this.comments = comments;
	}

	public Comments getComments(){
		return comments;
	}

	public void setUsersInPhoto(List<UsersInPhotoItem> usersInPhoto){
		this.usersInPhoto = usersInPhoto;
	}

	public List<UsersInPhotoItem> getUsersInPhoto(){
		return usersInPhoto;
	}

	public void setUserHasLiked(boolean userHasLiked){
		this.userHasLiked = userHasLiked;
	}

	public boolean isUserHasLiked(){
		return userHasLiked;
	}

	public void setLink(String link){
		this.link = link;
	}

	public String getLink(){
		return link;
	}

	public void setCaption(Caption caption){
		this.caption = caption;
	}

	public Caption getCaption(){
		return caption;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setTags(List<String> tags){
		this.tags = tags;
	}

	public List<String> getTags(){
		return tags;
	}

	public void setFilter(String filter){
		this.filter = filter;
	}

	public String getFilter(){
		return filter;
	}

	public void setAttribution(Object attribution){
		this.attribution = attribution;
	}

	public Object getAttribution(){
		return attribution;
	}

	public void setLocation(Object location){
		this.location = location;
	}

	public Object getLocation(){
		return location;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setUser(User user){
		this.user = user;
	}

	public User getUser(){
		return user;
	}

	public void setLikes(Likes likes){
		this.likes = likes;
	}

	public Likes getLikes(){
		return likes;
	}

	public void setVideos(Videos videos){
		this.videos = videos;
	}

	public Videos getVideos(){
		return videos;
	}

	public void setCarouselMedia(List<CarouselMediaItem> carouselMedia){
		this.carouselMedia = carouselMedia;
	}

	public List<CarouselMediaItem> getCarouselMedia(){
		return carouselMedia;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"created_time = '" + createdTime + '\'' + 
			",images = '" + images + '\'' + 
			",comments = '" + comments + '\'' + 
			",users_in_photo = '" + usersInPhoto + '\'' + 
			",user_has_liked = '" + userHasLiked + '\'' + 
			",link = '" + link + '\'' + 
			",caption = '" + caption + '\'' + 
			",type = '" + type + '\'' + 
			",tags = '" + tags + '\'' + 
			",filter = '" + filter + '\'' + 
			",attribution = '" + attribution + '\'' + 
			",location = '" + location + '\'' + 
			",id = '" + id + '\'' + 
			",user = '" + user + '\'' + 
			",likes = '" + likes + '\'' + 
			",videos = '" + videos + '\'' + 
			",carousel_media = '" + carouselMedia + '\'' + 
			"}";
		}
}