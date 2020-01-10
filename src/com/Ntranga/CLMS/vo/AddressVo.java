package com.Ntranga.CLMS.vo;

import java.util.Date;

public class AddressVo {

	 private int addressId;
	 private int customerDetailsId;
	 private int companyDetailsId;
	 private int addressTypeId;
	 private int addressUniqueId;
	 private String address1;
     private String address2;
     private String address3;
     private String isActive;
     private String country;
     private String state;
     private String city;
     private String pincode;    
     private Date transactionDate;
     private int countryId;
     private int stateId;
     private int cityId;
     private String addressType;
     
     private Integer createdBy;
 	 private Integer modifiedBy;	
 	
     
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Integer getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public int getAddressId() {
		return addressId;
	}
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
	public int getCustomerDetailsId() {
		return customerDetailsId;
	}
	public void setCustomerDetailsId(int customerDetailsId) {
		this.customerDetailsId = customerDetailsId;
	}			
	public int getCompanyDetailsId() {
		return companyDetailsId;
	}
	public void setCompanyDetailsId(int companyDetailsId) {
		this.companyDetailsId = companyDetailsId;
	}
	public int getAddressTypeId() {
		return addressTypeId;
	}
	public void setAddressTypeId(int addressTypeId) {
		this.addressTypeId = addressTypeId;
	}		
	public int getAddressUniqueId() {
		return addressUniqueId;
	}
	public void setAddressUniqueId(int addressUniqueId) {
		this.addressUniqueId = addressUniqueId;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getAddress3() {
		return address3;
	}
	public void setAddress3(String address3) {
		this.address3 = address3;
	}	
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public int getCountryId() {
		return countryId;
	}
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
	public int getStateId() {
		return stateId;
	}
	public void setStateId(int stateId) {
		this.stateId = stateId;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public String getAddressType() {
		return addressType;
	}
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}
	
     
     
     
}
