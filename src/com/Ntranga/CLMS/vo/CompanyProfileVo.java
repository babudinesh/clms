package com.Ntranga.CLMS.vo;

import java.io.Serializable;

public class CompanyProfileVo implements Serializable {

	
	private Integer companyProfileId;
    private Integer customerDetailsId;
    private Integer companyDetailsId;
    private Integer sequenceId;
    private String isActive;
    private Integer defaultCurrencyId;
    private Integer officialLanguageId;
    private Boolean isMultiLanguage;
    private Boolean isMultiCurrency;
    private Integer minAge;
    private Integer maxAge;
    private Integer retireAge;
    private Integer workWeekStartId;
    private Integer workWeekEndId;
    private Integer numberOfWorkingDays;
    private Integer bussinessHoursPerDay;
    private Integer standarHoursPerWeek;
    private String bussinessStartTime;
    private String bussinessEndTime;
    private String countryName;
    private String companyName;
    private String companyCode;
    private int[] languages;
    private int[] currencys;
    private Integer createdBy;   
    private Integer modifiedBy;
    private String defaultCurrencyName;
   
    
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
	public Integer getCompanyProfileId() {
		return companyProfileId;
	}
	public void setCompanyProfileId(Integer companyProfileId) {
		this.companyProfileId = companyProfileId;
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
	public Integer getSequenceId() {
		return sequenceId;
	}
	public void setSequenceId(Integer sequenceId) {
		this.sequenceId = sequenceId;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public Integer getDefaultCurrencyId() {
		return defaultCurrencyId;
	}
	public void setDefaultCurrencyId(Integer defaultCurrencyId) {
		this.defaultCurrencyId = defaultCurrencyId;
	}
	public Integer getOfficialLanguageId() {
		return officialLanguageId;
	}
	public void setOfficialLanguageId(Integer officialLanguageId) {
		this.officialLanguageId = officialLanguageId;
	}
	public Boolean getIsMultiLanguage() {
		return isMultiLanguage;
	}
	public void setIsMultiLanguage(Boolean isMultiLanguage) {
		this.isMultiLanguage = isMultiLanguage;
	}
	public Boolean getIsMultiCurrency() {
		return isMultiCurrency;
	}
	public void setIsMultiCurrency(Boolean isMultiCurrency) {
		this.isMultiCurrency = isMultiCurrency;
	}
	public Integer getMinAge() {
		return minAge;
	}
	public void setMinAge(Integer minAge) {
		this.minAge = minAge;
	}
	public Integer getMaxAge() {
		return maxAge;
	}
	public void setMaxAge(Integer maxAge) {
		this.maxAge = maxAge;
	}
	public Integer getRetireAge() {
		return retireAge;
	}
	public void setRetireAge(Integer retireAge) {
		this.retireAge = retireAge;
	}
	public Integer getWorkWeekStartId() {
		return workWeekStartId;
	}
	public void setWorkWeekStartId(Integer workWeekStartId) {
		this.workWeekStartId = workWeekStartId;
	}
	public Integer getWorkWeekEndId() {
		return workWeekEndId;
	}
	public void setWorkWeekEndId(Integer workWeekEndId) {
		this.workWeekEndId = workWeekEndId;
	}
	public Integer getNumberOfWorkingDays() {
		return numberOfWorkingDays;
	}
	public void setNumberOfWorkingDays(Integer numberOfWorkingDays) {
		this.numberOfWorkingDays = numberOfWorkingDays;
	}
	public Integer getBussinessHoursPerDay() {
		return bussinessHoursPerDay;
	}
	public void setBussinessHoursPerDay(Integer bussinessHoursPerDay) {
		this.bussinessHoursPerDay = bussinessHoursPerDay;
	}
	public Integer getStandarHoursPerWeek() {
		return standarHoursPerWeek;
	}
	public void setStandarHoursPerWeek(Integer standarHoursPerWeek) {
		this.standarHoursPerWeek = standarHoursPerWeek;
	}
	public String getBussinessStartTime() {
		return bussinessStartTime;
	}
	public void setBussinessStartTime(String bussinessStartTime) {
		this.bussinessStartTime = bussinessStartTime;
	}
	public String getBussinessEndTime() {
		return bussinessEndTime;
	}
	public void setBussinessEndTime(String bussinessEndTime) {
		this.bussinessEndTime = bussinessEndTime;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public int[] getLanguages() {
		return languages;
	}
	public void setLanguages(int[] languages) {
		this.languages = languages;
	}
	public int[] getCurrencys() {
		return currencys;
	}
	public void setCurrencys(int[] currencys) {
		this.currencys = currencys;
	}
	public String getDefaultCurrencyName() {
		return defaultCurrencyName;
	}
	public void setDefaultCurrencyName(String defaultCurrencyName) {
		this.defaultCurrencyName = defaultCurrencyName;
	}

	
    
    
}
