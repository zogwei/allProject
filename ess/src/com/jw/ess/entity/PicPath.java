package com.jw.ess.entity;

public class PicPath {
	private int id;
	
	private String picPath; //图片地址
	
	private int floorId; //地板id
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	public int getFloorId() {
		return floorId;
	}
	public void setFloorId(int floorId) {
		this.floorId = floorId;
	}
	@Override
	public String toString() {
		return "PicPath [floorId=" + floorId + ", id=" + id + ", picPath="
				+ picPath + "]";
	}
}
