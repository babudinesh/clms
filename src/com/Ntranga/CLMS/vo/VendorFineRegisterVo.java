package com.Ntranga.CLMS.vo;


public class VendorFineRegisterVo {

	private Integer customerId;
	private Integer companyId;
	private Integer vendorId;
	private Integer workerId;
	private Integer currencyId;
	
	private Integer finesRegisterId;
	private String dateOfOffence;
	private String actForWhichFineIsImposed;
	private String nameOfthePerson;
	private String amountOfFineImposed;
	private String whetherWorkmanShowedCause;
	private String dateOnWhichFineRealised;
	private String remarks;
	private String description;	
	
	private Integer createdBy;
	private Integer modifiedBy;
	
	
	private String customerName;
	private String companyName;
	private String vendorName;
	private String workerName;
	private String vendorCode;
	private String workerCode;
	
	
	
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
	
	public Integer getCurrencyId() {
		return currencyId;
	}
	
	public void setCurrencyId(Integer currencyId) {
		this.currencyId = currencyId;
	}
	
	public Integer getFinesRegisterId() {
		return finesRegisterId;
	}
	
	public void setFinesRegisterId(Integer finesRegisterId) {
		this.finesRegisterId = finesRegisterId;
	}
	
	public String getDateOfOffence() {
		return dateOfOffence;
	}
	
	public void setDateOfOffence(String dateOfOffence) {
		this.dateOfOffence = dateOfOffence;
	}
	
	public String getActForWhichFineIsImposed() {
		return actForWhichFineIsImposed;
	}
	
	public void setActForWhichFineIsImposed(String actForWhichFineIsImposed) {
		this.actForWhichFineIsImposed = actForWhichFineIsImposed;
	}
	
	public String getNameOfThePerson() {
		return nameOfthePerson;
	}
	
	public void setNameOfThePerson(String nameOfthePerson) {
		this.nameOfthePerson = nameOfthePerson;
	}
	
	public String getAmountOfFineImposed() {
		return amountOfFineImposed;
	}
	
	public void setAmountOfFineImposed(String amountOfFineImposed) {
		this.amountOfFineImposed = amountOfFineImposed;
	}
	
	public String getWhetherWorkmanShowedCause() {
		return whetherWorkmanShowedCause;
	}
	
	public void setWhetherWorkmanShowedCause(String whetherWorkmanShowedCause) {
		this.whetherWorkmanShowedCause = whetherWorkmanShowedCause;
	}
	
	public String getDateOnWhichFineRealised() {
		return dateOnWhichFineRealised;
	}
	
	public void setDateOnWhichFineRealised(String dateOnWhichFineRealised) {
		this.dateOnWhichFineRealised = dateOnWhichFineRealised;
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

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public String getWorkerCode() {
		return workerCode;
	}

	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
	}

	@Override
	public String toString() {
		return "VendorFineRegisterVo [customerId=" + customerId
				+ ", companyId=" + companyId + ", vendorId=" + vendorId
				+ ", workerId=" + workerId + ", currencyId=" + currencyId
				+ ", finesRegisterId=" + finesRegisterId + ", dateOfOffence="
				+ dateOfOffence + ", actForWhichFineIsImposed="
				+ actForWhichFineIsImposed + ", nameOfthePerson="
				+ nameOfthePerson + ", amountOfFineImposed="
				+ amountOfFineImposed + ", whetherWorkmanShowedCause="
				+ whetherWorkmanShowedCause + ", dateOnWhichFineRealised="
				+ dateOnWhichFineRealised + ", remarks=" + remarks
				+ ", description=" + description + ", createdBy=" + createdBy
				+ ", modifiedBy=" + modifiedBy + ", customerName="
				+ customerName + ", companyName=" + companyName
				+ ", vendorName=" + vendorName + ", workerName=" + workerName
				+ ", vendorCode=" + vendorCode + ", workerCode=" + workerCode
				+ "]";
	}


	
}
