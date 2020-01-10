package com.Ntranga.CLMS.vo;

import java.util.Date;

public class DivisionVo {

	private Integer customerId;
	private Integer companyId;
	private Integer countryId;
	private int divisionId;
	private int divisionDetailsId;
	private int divisionSequenceId;
	//private Integer divisionUniqueId;
	private String divisionName;
	private String shortName;
	private String status;
	private Date transactionDate;
	private String divisionCode;
	private String CustomerName;
	private String companyName;
	private String countryName;
	private Integer createdBy;
	private Date createdDate;
	private Integer modifiedBy;
	private Date modifiedDate;
	
	
	
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
	public int getDivisionId() {
		return divisionId;
	}
	public void setDivisionId(int divisionId) {
		this.divisionId = divisionId;
	}
	public int getDivisionDetailsId() {
		return divisionDetailsId;
	}
	public void setDivisionDetailsId(int divisionDetailsId) {
		this.divisionDetailsId = divisionDetailsId;
	}
	public int getDivisionSequenceId() {
		return divisionSequenceId;
	}
	public void setDivisionSequenceId(int divisionSequenceId) {
		this.divisionSequenceId = divisionSequenceId;
	}
	/*public Integer getDivisionUniqueId() {
		return divisionUniqueId;
	}
	public void setDivisionUniqueId(Integer divisionUniqueId) {
		this.divisionUniqueId = divisionUniqueId;
	}*/
	public String getDivisionName() {
		return divisionName;
	}
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getDivisionCode() {
		return divisionCode;
	}
	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}
	public String getCustomerName() {
		return CustomerName;
	}
	public void setCustomerName(String customerName) {
		CustomerName = customerName;
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
	
	
	@Override
	public String toString() {
		return "DivisionDetailsVo [customerId=" + customerId + ", companyId="
				+ companyId + ", countryId=" + countryId + ", divisionId="
				+ divisionId + ", divisionDetailsId=" + divisionDetailsId
				+ ", divisionSequenceId=" + divisionSequenceId
				+ ", divisionName="
				+ divisionName + ", shortName=" + shortName + ", status="
				+ status + ", transactionDate=" + transactionDate
				+ ", divisionCode=" + divisionCode + ", CustomerName="
				+ CustomerName + ", companyName=" + companyName
				+ ", countryName=" + countryName + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy
				+ ", modifiedDate=" + modifiedDate + "]";
	}
	
	
}
