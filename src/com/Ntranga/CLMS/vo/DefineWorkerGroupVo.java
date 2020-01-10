package com.Ntranga.CLMS.vo;

import java.util.Date;

public class DefineWorkerGroupVo {
	
	private Integer customerId;
	private Integer companyId;
	private Integer countryId;
	private String groupCode;
	private Integer workerGroupId;
	private Integer sequenceId;
	private Integer uniqueId;
	private String groupName;
	//private Integer wageRateId;
	private Integer timeRuleId;
	private Integer shiftPatternId;
	private Integer holidayCalendarId;
	private Integer overTimeRuleId;
	private Date transactionDate;
	private String status;
	private Integer createdBy;
	private Integer modifiedBy;
	
	
	private String customerName;
	private String companyName;
	private String countryName;
	//private String wageRateName;
	private String timeRuleName;
	private String holidayCalendarName;
	private String shiftPatternName;
	private String overTimeRuleName;
	
	
	
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
	
	public String getGroupCode() {
		return groupCode;
	}
	
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	
	public Integer getWorkerGroupId() {
		return workerGroupId;
	}
	
	public void setWorkerGroupId(Integer workerGroupId) {
		this.workerGroupId = workerGroupId;
	}
	
	public Integer getSequenceId() {
		return sequenceId;
	}
	
	public void setSequenceId(Integer sequenceId) {
		this.sequenceId = sequenceId;
	}
	
	public Integer getUniqueId() {
		return uniqueId;
	}
	
	public void setUniqueId(Integer uniqueId) {
		this.uniqueId = uniqueId;
	}
	
	public String getGroupName() {
		return groupName;
	}
	
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	/*public Integer getWageRateId() {
		return wageRateId;
	}
	
	public void setWageRateId(Integer wageRateId) {
		this.wageRateId = wageRateId;
	}*/
	
	public Integer getTimeRuleId() {
		return timeRuleId;
	}
	
	public void setTimeRuleId(Integer timeRuleId) {
		this.timeRuleId = timeRuleId;
	}
	
	public Integer getShiftPatternId() {
		return shiftPatternId;
	}
	
	public void setShiftPatternId(Integer shiftPatternId) {
		this.shiftPatternId = shiftPatternId;
	}
	
	public Integer getHolidayCalendarId() {
		return holidayCalendarId;
	}
	
	public void setHolidayCalendarId(Integer holidayCalendarId) {
		this.holidayCalendarId = holidayCalendarId;
	}
	
	public Integer getOverTimeRuleId() {
		return overTimeRuleId;
	}
	
	public void setOverTimeRuleId(Integer overTimeRuleId) {
		this.overTimeRuleId = overTimeRuleId;
	}
	
	public Date getTransactionDate() {
		return transactionDate;
	}
	
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
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
	
	public String getCountryName() {
		return countryName;
	}
	
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	/*public String getWageRateName() {
		return wageRateName;
	}
	
	public void setWageRateName(String wageRateName) {
		this.wageRateName = wageRateName;
	}*/
	
	public String getTimeRuleName() {
		return timeRuleName;
	}
	
	public void setTimeRuleName(String timeRuleName) {
		this.timeRuleName = timeRuleName;
	}
	
	public String getHolidayCalendarName() {
		return holidayCalendarName;
	}
	
	public void setHolidayCalendarName(String holidayCalendarName) {
		this.holidayCalendarName = holidayCalendarName;
	}
	
	public String getShiftPatternName() {
		return shiftPatternName;
	}
	
	public void setShiftPatternName(String shiftPatternName) {
		this.shiftPatternName = shiftPatternName;
	}
	
	public String getOverTimeRuleName() {
		return overTimeRuleName;
	}
	
	public void setOverTimeRuleName(String overTimeRuleName) {
		this.overTimeRuleName = overTimeRuleName;
	}
	
	@Override
	public String toString() {
		return "DefineWorkerGroupVo [customerId=" + customerId + ", companyId="
				+ companyId + ", countryId=" + countryId + ", groupCode="
				+ groupCode + ", workerGroupId=" + workerGroupId
				+ ", sequenceId=" + sequenceId + ", uniqueId=" + uniqueId
				+ ", groupName=" + groupName 
				//+ ", wageRateId=" + wageRateId
				+ ", timeRuleId=" + timeRuleId + ", shiftPatternId="
				+ shiftPatternId + ", holidayCalendarId=" + holidayCalendarId
				+ ", overTimeRuleId=" + overTimeRuleId + ", transactionDate="
				+ transactionDate + ", status=" + status + ", createdBy="
				+ createdBy + ", modifiedBy=" + modifiedBy + ", customerName="
				+ customerName + ", companyName=" + companyName
				+ ", countryName=" + countryName 
				//+ ", wageRateName="+ wageRateName 
				+ ", timeRuleName=" + timeRuleName
				+ ", holidayCalendarName=" + holidayCalendarName
				+ ", shiftPatternName=" + shiftPatternName
				+ ", overTimeRuleName=" + overTimeRuleName + "]";
	}
	
	
	
	

}
