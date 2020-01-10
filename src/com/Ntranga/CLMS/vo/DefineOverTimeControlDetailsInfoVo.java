package com.Ntranga.CLMS.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class DefineOverTimeControlDetailsInfoVo implements Serializable {

	
	
	private Integer defineOverTimeDetailsInfoId;
    private Integer defineOverTimeControlDetailsId;
    private Integer customerDetailsId;
    private Integer companyDetailsId;
    private Integer country;
    private String overTimeControlCode;
    private String overTimeControlName;
    private Date transactionDate;
    private String isActive;
    private String minimumOTHoursPerDay;
    private String overtimeDurationDay;
    private String overtimeDurationWeek;
    private String overtimeDurationQuarter;
    private String overtimeDurationMonth;
    private String considerOTHours;
    private Boolean allowOTOnWeeklyOff;
    private Boolean allowOTOnPublicHoliday;
    private String overtimeDurationYear;
    private Integer createdBy;   
    private Integer modifiedBy;
    
    private String customerName;
    private String companyName;
    
    
    private List<OtRoundOffRulesVo> otRoundOffRules = new ArrayList<OtRoundOffRulesVo>();    
    private List<WageRatesForOTVo> wageRatesForOT = new ArrayList<WageRatesForOTVo>();
    
    public DefineOverTimeControlDetailsInfoVo(){
    	otRoundOffRules = new ArrayList<OtRoundOffRulesVo>();
    	wageRatesForOT = new ArrayList<WageRatesForOTVo>();
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
	public Integer getDefineOverTimeDetailsInfoId() {
		return defineOverTimeDetailsInfoId;
	}
	public void setDefineOverTimeDetailsInfoId(Integer defineOverTimeDetailsInfoId) {
		this.defineOverTimeDetailsInfoId = defineOverTimeDetailsInfoId;
	}
	public Integer getDefineOverTimeControlDetailsId() {
		return defineOverTimeControlDetailsId;
	}
	public void setDefineOverTimeControlDetailsId(Integer defineOverTimeControlDetailsId) {
		this.defineOverTimeControlDetailsId = defineOverTimeControlDetailsId;
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
	public Integer getCountry() {
		return country;
	}
	public void setCountry(Integer country) {
		this.country = country;
	}
	
	public String getOverTimeControlCode() {
		return overTimeControlCode;
	}
	public void setOverTimeControlCode(String overTimeControlCode) {
		this.overTimeControlCode = overTimeControlCode;
	}
	public String getOverTimeControlName() {
		return overTimeControlName;
	}
	public void setOverTimeControlName(String overTimeControlName) {
		this.overTimeControlName = overTimeControlName;
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
	public String getMinimumOTHoursPerDay() {
		return minimumOTHoursPerDay;
	}
	public void setMinimumOTHoursPerDay(String minimumOTHoursPerDay) {
		this.minimumOTHoursPerDay = minimumOTHoursPerDay;
	}
	public String getOvertimeDurationDay() {
		return overtimeDurationDay;
	}
	public void setOvertimeDurationDay(String overtimeDurationDay) {
		this.overtimeDurationDay = overtimeDurationDay;
	}
	public String getOvertimeDurationWeek() {
		return overtimeDurationWeek;
	}
	public void setOvertimeDurationWeek(String overtimeDurationWeek) {
		this.overtimeDurationWeek = overtimeDurationWeek;
	}
	public String getOvertimeDurationQuarter() {
		return overtimeDurationQuarter;
	}
	public void setOvertimeDurationQuarter(String overtimeDurationQuarter) {
		this.overtimeDurationQuarter = overtimeDurationQuarter;
	}
	public String getOvertimeDurationMonth() {
		return overtimeDurationMonth;
	}
	public void setOvertimeDurationMonth(String overtimeDurationMonth) {
		this.overtimeDurationMonth = overtimeDurationMonth;
	}
	public String getConsiderOTHours() {
		return considerOTHours;
	}
	public void setConsiderOTHours(String considerOTHours) {
		this.considerOTHours = considerOTHours;
	}
	public Boolean getAllowOTOnWeeklyOff() {
		return allowOTOnWeeklyOff;
	}
	public void setAllowOTOnWeeklyOff(Boolean allowOTOnWeeklyOff) {
		this.allowOTOnWeeklyOff = allowOTOnWeeklyOff;
	}
	public Boolean getAllowOTOnPublicHoliday() {
		return allowOTOnPublicHoliday;
	}
	public void setAllowOTOnPublicHoliday(Boolean allowOTOnPublicHoliday) {
		this.allowOTOnPublicHoliday = allowOTOnPublicHoliday;
	}
	public String getOvertimeDurationYear() {
		return overtimeDurationYear;
	}
	public void setOvertimeDurationYear(String overtimeDurationYear) {
		this.overtimeDurationYear = overtimeDurationYear;
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

	public List<OtRoundOffRulesVo> getOtRoundOffRules() {
		return otRoundOffRules;
	}
	public void setOtRoundOffRules(List<OtRoundOffRulesVo> otRoundOffRules) {
		this.otRoundOffRules = otRoundOffRules;
	}
	public List<WageRatesForOTVo> getWageRatesForOT() {
		return wageRatesForOT;
	}
	public void setWageRatesForOT(List<WageRatesForOTVo> wageRatesForOT) {
		this.wageRatesForOT = wageRatesForOT;
	}
    
    
}
