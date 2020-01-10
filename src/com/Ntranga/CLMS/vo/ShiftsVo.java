package com.Ntranga.CLMS.vo;

import java.util.Date;

public class ShiftsVo {

	private Integer shiftId;
    private Integer customerDetailsId;
    private Integer companyDetailsId;
    private int countryId;
    private Date transactionDate;
    private int sequnceId;
    private String isActive;
    private int weekStartDay;
    private int weekEndDay;
    private String timeZone;
    private String timeSetFormat;
    private String defaultPatternType;
    private int currencyId;
    private Integer createdBy;
    private Integer modifiedBy;
	public Integer getShiftId() {		
		return shiftId;
	}
	public void setShiftId(Integer shiftId) {
		this.shiftId = shiftId;
	}
	public Integer getCustomerDetailsId() {
		return customerDetailsId;
	}
	public void setCustomerDetailsId(Integer customerDetailsId) {
		this.customerDetailsId = customerDetailsId;
	}
	public Integer getCompanyDetailsId() {
		return companyDetailsId;
	}
	public void setCompanyDetailsId(Integer companyDetailsId) {
		this.companyDetailsId = companyDetailsId;
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
	public int getSequnceId() {
		return sequnceId;
	}
	public void setSequnceId(int sequnceId) {
		this.sequnceId = sequnceId;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public int getWeekStartDay() {
		return weekStartDay;
	}
	public void setWeekStartDay(int weekStartDay) {
		this.weekStartDay = weekStartDay;
	}
	public int getWeekEndDay() {
		return weekEndDay;
	}
	public void setWeekEndDay(int weekEndDay) {
		this.weekEndDay = weekEndDay;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	
	public String getTimeSetFormat() {
		return timeSetFormat;
	}
	public void setTimeSetFormat(String timeSetFormat) {
		this.timeSetFormat = timeSetFormat;
	}	
	public String getDefaultPatternType() {
		return defaultPatternType;
	}
	public void setDefaultPatternType(String defaultPatternType) {
		this.defaultPatternType = defaultPatternType;
	}
	public int getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(int currencyId) {
		this.currencyId = currencyId;
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
    
    
}
