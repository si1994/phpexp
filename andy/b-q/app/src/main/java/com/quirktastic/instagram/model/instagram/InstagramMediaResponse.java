package com.quirktastic.instagram.model.instagram;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class InstagramMediaResponse{

	@SerializedName("pagination")
	private Pagination pagination;

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("meta")
	private Meta meta;

	public void setPagination(Pagination pagination){
		this.pagination = pagination;
	}

	public Pagination getPagination(){
		return pagination;
	}

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	public void setMeta(Meta meta){
		this.meta = meta;
	}

	public Meta getMeta(){
		return meta;
	}

	@Override
 	public String toString(){
		return 
			"InstagramMediaResponse{" + 
			"pagination = '" + pagination + '\'' + 
			",data = '" + data + '\'' + 
			",meta = '" + meta + '\'' + 
			"}";
		}
}