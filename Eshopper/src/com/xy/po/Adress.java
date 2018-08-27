package com.xy.po;

public class Adress {
	private int addressId;
	private String province;
	private String city;
	private String detail;
	private String zip;
	private int userId;
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	
	public Adress() {
		
	}
	public Adress(int addressId, String province, String city, String detail, String zip) {
		super();
		this.addressId = addressId;
		this.province = province;
		this.city = city;
		this.detail = detail;
		this.zip = zip;
	}
	

	
	public Adress(int addressId, String province, String city, String detail, String zip, int userId) {
		super();
		this.addressId = addressId;
		this.province = province;
		this.city = city;
		this.detail = detail;
		this.zip = zip;
		this.userId = userId;
	}
	public Adress(String province, String city, String detail, String zip) {
		super();
		this.province = province;
		this.city = city;
		this.detail = detail;
		this.zip = zip;
	}
	@Override
	public String toString() {
		return "Adress [province=" + province + ", city=" + city + ", detail=" + detail + "]";
	}
	
	

}
