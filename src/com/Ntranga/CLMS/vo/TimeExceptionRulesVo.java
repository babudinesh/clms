package com.Ntranga.CLMS.vo;

import java.util.Date;
import java.util.List;

public class TimeExceptionRulesVo {

	private Integer exceptionRulesId;
	private Integer exceptionSequenceId;
	private Integer exceptionUniqueId;
	private Integer customerId;
	private Integer companyId;
	private Integer countryId;
	private Integer timeRuleId;
	private Integer timeRulesInfoId;
	private Date transactionDate;
	private Integer limitForExceptionCorrection;
	private String periodType;
	private Integer numberOfExceptions;
	private Integer exceptionId;
	private Integer createdBy;
	private Date createdDate;
	private Integer modifiedBy;
	private Date modifiedDate;
	
	private String customerName;
	private String companyName;
	private String timeRuleCode;
	private String countryName;
	
	private List<TimeExceptionVo> exceptionsList;
	
	public Integer getExceptionRulesId() {
		return exceptionRulesId;
	}
	
	public void setExceptionRulesId(Integer exceptionRulesId) {
		this.exceptionRulesId = exceptionRulesId;
	}
	
	public Integer getExceptionSequenceId() {
		return exceptionSequenceId;
	}
	
	public void setExceptionSequenceId(Integer exceptionSequenceId) {
		this.exceptionSequenceId = exceptionSequenceId;
	}
	
	public Integer getExceptionUniqueId() {
		return exceptionUniqueId;
	}
	
	public void setExceptionUniqueId(Integer exceptionUniqueId) {
		this.exceptionUniqueId = exceptionUniqueId;
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
	
	public Integer getCountryId() {
		return countryId;
	}
	
	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}
	
	public Integer getTimeRuleId() {
		return timeRuleId;
	}
	
	public void setTimeRuleId(Integer timeRuleId) {
		this.timeRuleId = timeRuleId;
	}
	
	public Integer getTimeRulesInfoId() {
		return timeRulesInfoId;
	}
	
	public void setTimeRulesInfoId(Integer timeRulesInfoId) {
		this.timeRulesInfoId = timeRulesInfoId;
	}
	
	public Date getTransactionDate() {
		return transactionDate;
	}
	
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	
	public Integer getLimitForExceptionCorrection() {
		return limitForExceptionCorrection;
	}
	
	public void setLimitForExceptionCorrection(Integer limitForExceptionCorrection) {
		this.limitForExceptionCorrection = limitForExceptionCorrection;
	}
	
	public String getPeriodType() {
		return periodType;
	}
	
	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}
	
	public Integer getNumberOfExceptions() {
		return numberOfExceptions;
	}
	
	public void setNumberOfExceptions(Integer numberOfExceptions) {
		this.numberOfExceptions = numberOfExceptions;
	}
	
	public Integer getExceptionId() {
		return exceptionId;
	}
	
	public void setExceptionId(Integer exceptionId) {
		this.exceptionId = exceptionId;
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
	
	/*public String getExceptionCode() {
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
	}*/
	
	public String getTimeRuleCode() {
		return timeRuleCode;
	}
	
	public void setTimeRuleCode(String timeRuleCode) {
		this.timeRuleCode = timeRuleCode;
	}
	
	public String getCountryName() {
		return countryName;
	}
	
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	
	public List<TimeExceptionVo> getExceptionsList() {
		return exceptionsList;
	}

	public void setExceptionsList(List<TimeExceptionVo> exceptionsList) {
		this.exceptionsList = exceptionsList;
	}
	

	@Override
	public String toString() {
		return "TimeExceptionRulesVo [exceptionRulesId=" + exceptionRulesId
				+ ", exceptionSequenceId=" + exceptionSequenceId
				+ ", exceptionUniqueId=" + exceptionUniqueId + ", customerId="
				+ customerId + ", companyId=" + companyId + ", countryId="
				+ countryId + ", timeRuleId=" + timeRuleId
				+ ", timeRulesInfoId=" + timeRulesInfoId + ", transactionDate="
				+ transactionDate + ", limitForExceptionCorrection="
				+ limitForExceptionCorrection + ", periodType=" + periodType
				+ ", numberOfExceptions=" + numberOfExceptions
				+ ", exceptionId=" + exceptionId + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy
				+ ", modifiedDate=" + modifiedDate + ", customerName="
				+ customerName + ", companyName=" + companyName
				//+ ", exceptionCode=" + exceptionCode + ", exceptionName="+ exceptionName 
				+ ", timeRuleCode=" + timeRuleCode
				+ ", countryName=" + countryName + ", exceptionsList="
				+ exceptionsList + "]";
	}
	
	
	
}
