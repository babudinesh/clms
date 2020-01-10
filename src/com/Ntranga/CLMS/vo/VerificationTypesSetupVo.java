package com.Ntranga.CLMS.vo;
// Generated 23 Jan, 2017 12:03:01 PM by Hibernate Tools 3.6.0


import java.util.Date;


public class VerificationTypesSetupVo  implements java.io.Serializable {


     private Integer verificationTypesSetupId;
     private Integer customerId;
     private Integer companyId;
     private int countryId;
     private Date transactionDate;
     private char isActive;
     private String verificationType;
     private String verificationFrequency;
     private Date createdDate;
     private int createdBy;
     private int modifiedBy;
     private Date modifiedDate;
     private char isMandatory;
     
     

    public VerificationTypesSetupVo() {
    }

    
    
    
	public char getIsMandatory() {
		return isMandatory;
	}

	public void setIsMandatory(char isMandatory) {
		this.isMandatory = isMandatory;
	}




	public Integer getVerificationTypesSetupId() {
		return verificationTypesSetupId;
	}

	public void setVerificationTypesSetupId(Integer verificationTypesSetupId) {
		this.verificationTypesSetupId = verificationTypesSetupId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public char getIsActive() {
		return isActive;
	}

	public void setIsActive(char isActive) {
		this.isActive = isActive;
	}

	public String getVerificationType() {
		return verificationType;
	}

	public void setVerificationType(String verificationType) {
		this.verificationType = verificationType;
	}

	public String getVerificationFrequency() {
		return verificationFrequency;
	}

	public void setVerificationFrequency(String verificationFrequency) {
		this.verificationFrequency = verificationFrequency;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
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

    




}


