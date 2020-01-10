package com.Ntranga.CLMS.vo;
// Generated 4 Jul, 2016 3:41:29 PM by Hibernate Tools 3.6.0


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class VendorBankDetailsVo  implements java.io.Serializable {


     private Integer vendorBankId;    
     private String bankCode;
     private Date transactionDate;
     private int sequenceId;
     private String bankName;
     private String accountHolderName;
     private String accountNumber;
     private String branch;
     private String ifscCode;
     private String micrCode;
     private String phoneNumber;
     private String addressLine1;
     private String addressLine2;
     private String addressLine3;
     private Integer country;
     private Integer state;
     private String city;
     private Integer pincode;
     private int createdBy;
     private Date createdDate;
     private int modifiedBy;
     private Date modifiedDate;
     private String isActive;
     private int uniqueId;
     private Integer customerId;
     private Integer companyid;
     private Integer vendorId;
     private Integer locationId;
     private Integer countryid;
     private String bankCity;
     
     private String vendorName;
     private String companyName;
     private String customerNmae;
     private String strTransactionDate;
     
     
     
     

    public String getStrTransactionDate() {
		return strTransactionDate;
	}




	public void setStrTransactionDate(String strTransactionDate) {
		this.strTransactionDate = strTransactionDate;
	}




	public VendorBankDetailsVo() {
    }




	public String getVendorName() {
		return vendorName;
	}




	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}




	public String getCompanyName() {
		return companyName;
	}




	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}




	public String getCustomerNmae() {
		return customerNmae;
	}




	public void setCustomerNmae(String customerNmae) {
		this.customerNmae = customerNmae;
	}




	public String getBankCity() {
		return bankCity;
	}




	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}




	public Integer getVendorBankId() {
		return vendorBankId;
	}




	public void setVendorBankId(Integer vendorBankId) {
		this.vendorBankId = vendorBankId;
	}




	public String getBankCode() {
		return bankCode;
	}




	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}




	public Date getTransactionDate() {
		return transactionDate;
	}




	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}




	public int getSequenceId() {
		return sequenceId;
	}




	public void setSequenceId(int sequenceId) {
		this.sequenceId = sequenceId;
	}




	public String getBankName() {
		return bankName;
	}




	public void setBankName(String bankName) {
		this.bankName = bankName;
	}




	public String getAccountHolderName() {
		return accountHolderName;
	}




	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}




	public String getAccountNumber() {
		return accountNumber;
	}




	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}




	public String getBranch() {
		return branch;
	}




	public void setBranch(String branch) {
		this.branch = branch;
	}




	public String getIfscCode() {
		return ifscCode;
	}




	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}




	public String getMicrCode() {
		return micrCode;
	}




	public void setMicrCode(String micrCode) {
		this.micrCode = micrCode;
	}




	public String getPhoneNumber() {
		return phoneNumber;
	}




	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}




	public String getAddressLine1() {
		return addressLine1;
	}




	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}




	public String getAddressLine2() {
		return addressLine2;
	}




	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}




	public String getAddressLine3() {
		return addressLine3;
	}




	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}




	public Integer getCountry() {
		return country;
	}




	public void setCountry(Integer country) {
		this.country = country;
	}




	public Integer getState() {
		return state;
	}




	public void setState(Integer state) {
		this.state = state;
	}




	public String getCity() {
		return city;
	}




	public void setCity(String city) {
		this.city = city;
	}




	public Integer getPincode() {
		return pincode;
	}




	public void setPincode(Integer pincode) {
		this.pincode = pincode;
	}




	public int getCreatedBy() {
		return createdBy;
	}




	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}




	public Date getCreatedDate() {
		return createdDate;
	}




	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}




	public int getModifiedBy() {
		return modifiedBy;
	}




	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}




	public Date getModifiedDate() {
		return modifiedDate;
	}




	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}




	public String getIsActive() {
		return isActive;
	}




	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}




	public int getUniqueId() {
		return uniqueId;
	}




	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
	}




	public Integer getCustomerId() {
		return customerId;
	}




	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}




	public Integer getCompanyid() {
		return companyid;
	}




	public void setCompanyid(Integer companyid) {
		this.companyid = companyid;
	}




	public Integer getVendorId() {
		return vendorId;
	}




	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}




	public Integer getLocationId() {
		return locationId;
	}




	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}




	public Integer getCountryid() {
		return countryid;
	}




	public void setCountryid(Integer countryid) {
		this.countryid = countryid;
	}

	
   
    



}


