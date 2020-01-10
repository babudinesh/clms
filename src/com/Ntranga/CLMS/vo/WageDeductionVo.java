package com.Ntranga.CLMS.vo;


// Generated 12 Aug, 2016 11:37:16 AM by Hibernate Tools 3.6.0


import java.util.Date;


public class WageDeductionVo  implements java.io.Serializable {


     private Integer deductionId;
     private int customerId;
     private int companyId;
     private int countryId;
     private int locationId;
     private String deductionCode;
     private Date transactionDate;
     private String deductionName;
     private String fixedAmountOrPercentage;
     private String employerAmount;
     private int employerAmountType;
     private String employeeAmount;
     private int employeeAmountType;
     private String employerPercentage;
     private String employerPercentageType;
     private String employeePercentage;
     private String employeePercentageType;
     private int sequenceId;
     private String isActive;
     private Date createdDate;
     private int createdBy;
     private Date modifiedDate;
     private int modifiedBy;
     
     private String customerName;
     private String companyName;
     private String locationName;
     

    public WageDeductionVo() {
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




	public String getLocationName() {
		return locationName;
	}




	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}




	public Integer getDeductionId() {
		return deductionId;
	}

	public void setDeductionId(Integer deductionId) {
		this.deductionId = deductionId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public String getDeductionCode() {
		return deductionCode;
	}

	public void setDeductionCode(String deductionCode) {
		this.deductionCode = deductionCode;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getDeductionName() {
		return deductionName;
	}

	public void setDeductionName(String deductionName) {
		this.deductionName = deductionName;
	}

	public String getFixedAmountOrPercentage() {
		return fixedAmountOrPercentage;
	}

	public void setFixedAmountOrPercentage(String fixedAmountOrPercentage) {
		this.fixedAmountOrPercentage = fixedAmountOrPercentage;
	}

	public String getEmployerAmount() {
		return employerAmount;
	}

	public void setEmployerAmount(String employerAmount) {
		this.employerAmount = employerAmount;
	}

	
	public String getEmployeeAmount() {
		return employeeAmount;
	}

	public void setEmployeeAmount(String employeeAmount) {
		this.employeeAmount = employeeAmount;
	}

	

	public String getEmployerPercentage() {
		return employerPercentage;
	}

	public void setEmployerPercentage(String employerPercentage) {
		this.employerPercentage = employerPercentage;
	}

	public String getEmployerPercentageType() {
		return employerPercentageType;
	}

	public void setEmployerPercentageType(String employerPercentageType) {
		this.employerPercentageType = employerPercentageType;
	}

	public String getEmployeePercentage() {
		return employeePercentage;
	}

	public void setEmployeePercentage(String employeePercentage) {
		this.employeePercentage = employeePercentage;
	}

	public String getEmployeePercentageType() {
		return employeePercentageType;
	}

	public void setEmployeePercentageType(String employeePercentageType) {
		this.employeePercentageType = employeePercentageType;
	}

	public int getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(int sequenceId) {
		this.sequenceId = sequenceId;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
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

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public int getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}




	public int getEmployerAmountType() {
		return employerAmountType;
	}




	public void setEmployerAmountType(int employerAmountType) {
		this.employerAmountType = employerAmountType;
	}




	public int getEmployeeAmountType() {
		return employeeAmountType;
	}




	public void setEmployeeAmountType(int employeeAmountType) {
		this.employeeAmountType = employeeAmountType;
	}

	


}


