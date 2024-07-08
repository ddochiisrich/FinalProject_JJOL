package com.example.project_jjol.model;

import java.sql.Timestamp;

public class Payment {
	private String payCode;
    private Timestamp payDate;
    private String payWay;
    private Integer price;
    private String userId;
    private Integer lectureId;
    
	public String getPayCode() {
		return payCode;
	}
	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}
	public Timestamp getPayDate() {
		return payDate;
	}
	public void setPayDate(Timestamp payDate) {
		this.payDate = payDate;
	}
	public String getPayWay() {
		return payWay;
	}
	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getLectureId() {
		return lectureId;
	}
	public void setLectureId(Integer lectureId) {
		this.lectureId = lectureId;
	}
	
}
