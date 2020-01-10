package com.Ntranga.CLMS.vo;

import java.util.List;

public class VendorDamageOrLossDetailsVo {

	private Integer  vendorDamageId;
	private Integer customerId;
	private Integer companyId;
	private Integer vendorId;
	private Integer workerId;
	private String dateOfDamage;
	private String remarks;
	private String description;
	private String particularsOfDamage;
	private String nameOfThePerson;
	private Integer numberOfInstallments;
	private String amountOfDeduction;
	private String whetherWorkmanShowedCause;
	private Integer createdBy;
	private Integer modifiedBy;
	private Integer currencyId;
	private List<VendorDamageRecoveryVo> recoveryList;
	
	private String vendorName;
	private String workerName;
	//private String customerName;
	//private String companyName;
	//private String currencyName;
	private String workerCode;
	private String vendorCode;
	
	
	public Integer getVendorDamageId() {
		return vendorDamageId;
	}
	
	public void setVendorDamageId(Integer vendorDamageId) {
		this.vendorDamageId = vendorDamageId;
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
	
	public Integer getVendorId() {
		return vendorId;
	}
	
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	
	public Integer getWorkerId() {
		return workerId;
	}
	
	public void setWorkerId(Integer workerId) {
		this.workerId = workerId;
	}
	
	public String getDateOfDamage() {
		return dateOfDamage;
	}
	
	public void setDateOfDamage(String dateOfDamage) {
		this.dateOfDamage = dateOfDamage;
	}
	
	public String getParticularsOfDamage() {
		return particularsOfDamage;
	}
	
	public void setParticularsOfDamage(String particularsOfDamage) {
		this.particularsOfDamage = particularsOfDamage;
	}
	
	public String getNameOfThePerson() {
		return nameOfThePerson;
	}
	
	public void setNameOfThePerson(String nameOfThePerson) {
		this.nameOfThePerson = nameOfThePerson;
	}
	
	public Integer getNumberOfInstallments() {
		return numberOfInstallments;
	}
	
	public void setNumberOfInstallments(Integer numberOfInstallments) {
		this.numberOfInstallments = numberOfInstallments;
	}
	
	public String getAmountOfDeduction() {
		return amountOfDeduction;
	}
	
	public void setAmountOfDeduction(String amountOfDeduction) {
		this.amountOfDeduction = amountOfDeduction;
	}
	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getWhetherWorkmanShowedCause() {
		return whetherWorkmanShowedCause;
	}

	public void setWhetherWorkmanShowedCause(String whetherWorkmanShowedCause) {
		this.whetherWorkmanShowedCause = whetherWorkmanShowedCause;
	}

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
	
	public List<VendorDamageRecoveryVo> getRecoveryList() {
		return recoveryList;
	}
	
	public void setRecoveryList(List<VendorDamageRecoveryVo> recoveryList) {
		this.recoveryList = recoveryList;
	}
	
	public String getVendorName() {
		return vendorName;
	}
	
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	
	public String getWorkerName() {
		return workerName;
	}
	
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	
	/*public String getCustomerName() {
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
	}*/

	public String getWorkerCode() {
		return workerCode;
	}

	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public Integer getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Integer currencyId) {
		this.currencyId = currencyId;
	}

	/*public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}*/

	@Override
	public String toString() {
		return "VendorDamageOrLossDetailsVo [vendorDamageId=" + vendorDamageId
				+ ", customerId=" + customerId + ", companyId=" + companyId
				+ ", vendorId=" + vendorId + ", workerId=" + workerId
				+ ", dateOfDamage=" + dateOfDamage + ", remarks=" + remarks
				+ ", description=" + description + ", particularsOfDamage="
				+ particularsOfDamage + ", nameOfThePerson=" + nameOfThePerson
				+ ", numberOfInstallments=" + numberOfInstallments
				+ ", amountOfDeduction=" + amountOfDeduction
				+ ", whetherWorkmanShowedCause=" + whetherWorkmanShowedCause
				+ ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy
				+ ", currencyId=" + currencyId + ", recoveryList="+ recoveryList 
				+ ", vendorName=" + vendorName 
				+ ", workerName="+ workerName 
				//+ ", customerName=" + customerName
				//+ ", companyName=" + companyName + ", currencyName="+ currencyName 
				+ ", workerCode=" + workerCode + ", vendorCode="
				+ vendorCode + "]";
	}
	
	
	
	
	
	
	
}
