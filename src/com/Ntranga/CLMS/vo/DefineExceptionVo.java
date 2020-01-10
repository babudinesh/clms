package com.Ntranga.CLMS.vo;

import java.util.Date;

public class DefineExceptionVo {

	private Integer customerId;
	private Integer companyId;
	private Integer countryId;
	private Integer exceptionId;
	private Integer exceptionUniqueId;
	private Integer exceptionSequenceId;
	private String exceptionCode;
	private String exceptionName;
	private String effectiveStatus;
	private Date transactionDate;
	private int createdBy;
	private int modifiedBy;
	
	private String customerName;
	private String companyName;
	private String countryName;
	
	
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
	
	public Integer getCountryId() {
		return countryId;
	}
	
	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}
	
	public Integer getExceptionId() {
		return exceptionId;
	}
	
	public void setExceptionId(Integer exceptionId) {
		this.exceptionId = exceptionId;
	}
	
	public Integer getExceptionUniqueId() {
		return exceptionUniqueId;
	}
	
	public void setExceptionUniqueId(Integer exceptionUniqueId) {
		this.exceptionUniqueId = exceptionUniqueId;
	}
	
	public Integer getExceptionSequenceId() {
		return exceptionSequenceId;
	}
	
	public void setExceptionSequenceId(Integer exceptionSequenceId) {
		this.exceptionSequenceId = exceptionSequenceId;
	}
	
	public String getExceptionCode() {
		return exceptionCode;
	}
	
	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
	
	public String getExceptionName() {
		return exceptionName;
	}
	
	public void setExceptionName(String exceptionName) {
		this.exceptionName = exceptionName;
	}
	
	public String getEffectiveStatus() {
		return effectiveStatus;
	}
	
	public void setEffectiveStatus(String effectiveStatus) {
		this.effectiveStatus = effectiveStatus;
	}
	
	public Date getTransactionDate() {
		return transactionDate;
	}
	
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
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

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	@Override
	public String toString() {
		return "DefineExceptionVo [customerId=" + customerId + ", companyId="
				+ companyId + ", countryId=" + countryId + ", exceptionId="
				+ exceptionId + ", exceptionUniqueId=" + exceptionUniqueId
				+ ", exceptionSequenceId=" + exceptionSequenceId
				+ ", exceptionCode=" + exceptionCode + ", exceptionName="
				+ exceptionName + ", effectiveStatus=" + effectiveStatus
				+ ", transactionDate=" + transactionDate + ", createdBy="
				+ createdBy + ", modifiedBy=" + modifiedBy + ", customerName="
				+ customerName + ", companyName=" + companyName
				+ ", countryName=" + countryName + "]";
	}

	
	
	
}
