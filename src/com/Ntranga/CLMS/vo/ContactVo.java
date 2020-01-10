package com.Ntranga.CLMS.vo;

import java.util.Date;


public class ContactVo {
	
	 private Integer contactSequenceId;
	 private Integer contactId;
	 private Integer contactUniqueId;
     private Integer customerId;
     private Integer companyId;
     private Integer addressId;
	 private Integer contactTypeId;
	 private String contactTypeName;
	 private String contactName;
     private String contactNameOtherLanguage;
     private String mobileNumber;
     private String businessPhoneNumber;
     private String extensionNumber;
     private String emailId;   
     private Date transactionDate;
     private String isActive;
	 private String address;
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

	/**
	 * @return the contactSequenceId
	 */
	public Integer getContactSequenceId() {
		return contactSequenceId;
	}
	
	/**
	 * @param contactSequenceId the contactSequenceId to set
	 */
	public void setContactSequenceId(Integer contactSequenceId) {
		this.contactSequenceId = contactSequenceId;
	}
	
	public Integer getContactId() {
		return contactId;
	}
	
	/**
	 * @param contactId the contactId to set
	 */
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}
	
	 /**
	 * @return the contactUniqueId
	 */
	public Integer getContactUniqueId() {
		return contactUniqueId;
	}

	/**
	 * @param contactUniqueId the contactUniqueId to set
	 */
	public void setContactUniqueId(Integer contactUniqueId) {
		this.contactUniqueId = contactUniqueId;
	}

	/**
	 * @return the contactTypeName
	 */
	public String getContactTypeName() {
		return contactTypeName;
	}
	
	/**
	 * @param contactTypeName the contactTypeName to set
	 */
	public void setContactTypeName(String contactTypeName) {
		this.contactTypeName = contactTypeName;
	}
	
	/**
	 * @return the customerId
	 */
	public Integer getCustomerId() {
		return customerId;
	}
	
	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	
	/**
	 * @return the companyId
	 */
	public Integer getCompanyId() {
		return companyId;
	}
	
	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	
	/**
	 * @return the addressId
	 */
	public Integer getAddressId() {
		return addressId;
	}
	
	/**
	 * @param addressId the addressId to set
	 */
	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}
	

     /**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * @return the contactTypeId
	 */
	public Integer getContactTypeId() {
		return contactTypeId;
	}
	
	/**
	 * @param contactTypeId the contactTypeId to set
	 */
	public void setContactTypeId(Integer contactTypeId) {
		this.contactTypeId = contactTypeId;
	}
	
	/**
	 * @return the contactName
	 */
	public String getContactName() {
		return contactName;
	}
	
	/**
	 * @param contactName the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	
	/**
	 * @return the contactNameOtherLanguage
	 */
	public String getContactNameOtherLanguage() {
		return contactNameOtherLanguage;
	}
	
	/**
	 * @param contactNameOtherLanguage the contactNameOtherLanguage to set
	 */
	public void setContactNameOtherLanguage(String contactNameOtherLanguage) {
		this.contactNameOtherLanguage = contactNameOtherLanguage;
	}
	
	/**
	 * @return the mobileNumber
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}
	
	/**
	 * @param mobileNumber the mobileNumber to set
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	
	/**
	 * @return the businessPhoneNumber
	 */
	public String getBusinessPhoneNumber() {
		return businessPhoneNumber;
	}
	
	/**
	 * @param businessPhoneNumber the businessPhoneNumber to set
	 */
	public void setBusinessPhoneNumber(String businessPhoneNumber) {
		this.businessPhoneNumber = businessPhoneNumber;
	}
	
	/**
	 * @return the extensionNumber
	 */
	public String getExtensionNumber() {
		return extensionNumber;
	}
	
	/**
	 * @param extensionNumber the extensionNumber to set
	 */
	public void setExtensionNumber(String extensionNumber) {
		this.extensionNumber = extensionNumber;
	}
	
	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}
	
	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	/**
	 * @return the transactionDate
	 */
	public Date getTransactionDate() {
		return transactionDate;
	}
	
	/**
	 * @param transactionDate the transactionDate to set
	 */
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	
	/**
	 * @return the isActive
	 */
	public String getIsActive() {
		return isActive;
	}
	
	/**
	 * @param isActive the isActive to set
	 */
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ContactVo [contactSequenceId=" + contactSequenceId
				+ ", contactId=" + contactId + ", contactUniqueId="
				+ contactUniqueId + ", customerId=" + customerId
				+ ", companyId=" + companyId + ", addressId=" + addressId
				+ ", contactTypeId=" + contactTypeId + ", contactTypeName="
				+ contactTypeName + ", contactName=" + contactName
				+ ", contactNameOtherLanguage=" + contactNameOtherLanguage
				+ ", mobileNumber=" + mobileNumber + ", businessPhoneNumber="
				+ businessPhoneNumber + ", extensionNumber=" + extensionNumber
				+ ", emailId=" + emailId + ", transactionDate="
				+ transactionDate + ", isActive=" + isActive + ", address="
				+ address + "]";
	}
    
	
}
