package com.Ntranga.CLMS.vo;

import java.util.Date;


public class WorkerVerificationTypesSetupVo implements java.io.Serializable {

	private Integer customerId;
	private Integer companyId ;
	private Integer workerVerificationTypesSetupId ;
	private Date transactionDate;
	private String isActive;
	private String verificationType;
	private String mandatiory;
	private String verificationFrequency;
	private Integer countryId;	
	private int createdBy;
    private Date createdDate;
	private int modifiedBy;
    private Date modifiedDate;
    private String customerName;
    private String companyName;
    
    public WorkerVerificationTypesSetupVo(){
    	
    }
    
    

	public String getCustomerName() {
		return customerName;
	}



	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}



	public String getCompanyName() {
		return companyName;
	}



	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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

	public Integer getWorkerVerificationTypesSetupId() {
		return workerVerificationTypesSetupId;
	}

	public void setWorkerVerificationTypesSetupId(Integer workerVerificationTypesSetupId) {
		this.workerVerificationTypesSetupId = workerVerificationTypesSetupId;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getVerificationType() {
		return verificationType;
	}

	public void setVerificationType(String verificationType) {
		this.verificationType = verificationType;
	}

	

	public String getMandatiory() {
		return mandatiory;
	}



	public void setMandatiory(String mandatiory) {
		this.mandatiory = mandatiory;
	}



	public String getVerificationFrequency() {
		return verificationFrequency;
	}

	public void setVerificationFrequency(String verificationFrequency) {
		this.verificationFrequency = verificationFrequency;
	}

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
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

	
    
	
    
}
