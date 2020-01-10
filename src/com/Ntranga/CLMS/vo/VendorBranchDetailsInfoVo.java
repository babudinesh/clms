package com.Ntranga.CLMS.vo;

import java.io.Serializable;
import java.util.Date;

public class VendorBranchDetailsInfoVo implements Serializable {
	
	private Integer vendorBranchDetailsInfoId;
    private Integer customerDetailsId;
    private Integer vendorDetailsId;
    private Integer vendorBranchesId;
    private Integer companyDetailsId;
    private String branchCode;
    private Date transactionDate;
    private Integer sequenceId;
    private String locationName;
    private String contactPerson;
    private String designation;
    private String mobile;
    private String phoneNumber;
    private String extension;
    private String faxNumber;
    private String email;
    private Integer workType;
    private String pfAccountNumber;
    private Date pfRegistrationDate;
    private Date pfStartDate;
    private Integer pfType;
    private String pfCircle;
    private String esiAccountNumber;
    private Date esiRegistrationDate;
    private Date esiStartDate;
    private String isProfessionalTaxApplicable;
    private String address1;
    private String address2;
    private String address3;
    private Integer country;
    private Integer state;
    private String city;
    private Integer pincode;
    private String isActive;
    private Integer createdBy;
    private Date createdDate;
    private Integer modifiedBy;
    private Date modifiedDate;
    
    private String vendorCode;
    private String vendorName;
    private String countryName;
    private String stateName;
    private String companyName;
    private String customerName;
    
    private String natureOfWork;
    
    
   
    
	public Integer getVendorBranchDetailsInfoId() {
		return vendorBranchDetailsInfoId;
	}
	public void setVendorBranchDetailsInfoId(Integer vendorBranchDetailsInfoId) {
		this.vendorBranchDetailsInfoId = vendorBranchDetailsInfoId;
	}
	public Integer getCustomerDetailsId() {
		return customerDetailsId;
	}
	public void setCustomerDetailsId(Integer customerDetailsId) {
		this.customerDetailsId = customerDetailsId;
	}
	public Integer getVendorDetailsId() {
		return vendorDetailsId;
	}
	public void setVendorDetailsId(Integer vendorDetailsId) {
		this.vendorDetailsId = vendorDetailsId;
	}
	public Integer getVendorBranchesId() {
		return vendorBranchesId;
	}
	public void setVendorBranchesId(Integer vendorBranchesId) {
		this.vendorBranchesId = vendorBranchesId;
	}
	public Integer getCompanyDetailsId() {
		return companyDetailsId;
	}
	public void setCompanyDetailsId(Integer companyDetailsId) {
		this.companyDetailsId = companyDetailsId;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public Integer getSequenceId() {
		return sequenceId;
	}
	public void setSequenceId(Integer sequenceId) {
		this.sequenceId = sequenceId;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public String getFaxNumber() {
		return faxNumber;
	}
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getWorkType() {
		return workType;
	}
	public void setWorkType(Integer workType) {
		this.workType = workType;
	}
	public String getPfAccountNumber() {
		return pfAccountNumber;
	}
	public void setPfAccountNumber(String pfAccountNumber) {
		this.pfAccountNumber = pfAccountNumber;
	}
	public Date getPfRegistrationDate() {
		return pfRegistrationDate;
	}
	public void setPfRegistrationDate(Date pfRegistrationDate) {
		this.pfRegistrationDate = pfRegistrationDate;
	}
	public Date getPfStartDate() {
		return pfStartDate;
	}
	public void setPfStartDate(Date pfStartDate) {
		this.pfStartDate = pfStartDate;
	}
	public Integer getPfType() {
		return pfType;
	}
	public void setPfType(Integer pfType) {
		this.pfType = pfType;
	}
	public String getPfCircle() {
		return pfCircle;
	}
	public void setPfCircle(String pfCircle) {
		this.pfCircle = pfCircle;
	}
	public String getEsiAccountNumber() {
		return esiAccountNumber;
	}
	public void setEsiAccountNumber(String esiAccountNumber) {
		this.esiAccountNumber = esiAccountNumber;
	}
	public Date getEsiRegistrationDate() {
		return esiRegistrationDate;
	}
	public void setEsiRegistrationDate(Date esiRegistrationDate) {
		this.esiRegistrationDate = esiRegistrationDate;
	}
	public Date getEsiStartDate() {
		return esiStartDate;
	}
	public void setEsiStartDate(Date esiStartDate) {
		this.esiStartDate = esiStartDate;
	}
	public String getIsProfessionalTaxApplicable() {
		return isProfessionalTaxApplicable;
	}
	public void setIsProfessionalTaxApplicable(String isProfessionalTaxApplicable) {
		this.isProfessionalTaxApplicable = isProfessionalTaxApplicable;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
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
	public String getVendorCode() {
		return vendorCode;
	}
	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getNatureOfWork() {
		return natureOfWork;
	}
	public void setNatureOfWork(String natureOfWork) {
		this.natureOfWork = natureOfWork;
	}
    
    

}