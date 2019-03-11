package com.quirktastic.instagram.model.instagram;


import com.google.gson.annotations.SerializedName;


public class Videos{

	@SerializedName("low_resolution")
	private LowResolution lowResolution;

	@SerializedName("low_bandwidth")
	private LowBandwidth lowBandwidth;

	@SerializedName("standard_resolution")
	private StandardResolution standardResolution;

	public void setLowResolution(LowResolution lowResolution){
		this.lowResolution = lowResolution;
	}

	public LowResolution getLowResolution(){
		return lowResolution;
	}

	public void setLowBandwidth(LowBandwidth lowBandwidth){
		this.lowBandwidth = lowBandwidth;
	}

	public LowBandwidth getLowBandwidth(){
		return lowBandwidth;
	}

	public void setStandardResolution(StandardResolution standardResolution){
		this.standardResolution = standardResolution;
	}

	public StandardResolution getStandardResolution(){
		return standardResolution;
	}

	@Override
 	public String toString(){
		return 
			"Videos{" + 
			"low_resolution = '" + lowResolution + '\'' + 
			",low_bandwidth = '" + lowBandwidth + '\'' + 
			",standard_resolution = '" + standardResolution + '\'' + 
			"}";
		}
}