package com.Ntranga.CLMS.vo;
// Generated 25 Jul, 2016 10:18:35 AM by Hibernate Tools 3.6.0


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimeRulesDetailsVo  implements java.io.Serializable {


     private Integer timeRulesInfoId;
     private int timeRulesId;
     private int customerId;
     private int companyId;
     private int countryId;
     private Date transactionDate;
     private int sequenceId;
     private String isActive;
     private String timeRulesDescription;
     private String weekStartDay;
     private String weekEndDay;
     private String followFirstInLastOut;
     private Integer hoursDay;
     private Integer hoursWeek;
     private String minimumDays;
     private String maximumDays;
     private String showWo;
     private String showPh;
     private String showLeaves;
     private String showOd;
     private int createdBy;
     private Date createdDate;
     private int modifiedBy;
     private Date modifiedDate;
     private String timeRuleCode;
     
     private String customerName;
     private String companyName;
     private String countryName;
     
     private List<TimeRuleWorkDayStatusVo> workDayStatusList = new ArrayList<TimeRuleWorkDayStatusVo>();
     private List<TimeRuleBufferTimeVo> bufferTimeLsit = new ArrayList<TimeRuleBufferTimeVo>();

    public TimeRulesDetailsVo() {
    }

    
    
 



	public List<TimeRuleWorkDayStatusVo> getWorkDayStatusList() {
		return workDayStatusList;
	}







	public void setWorkDayStatusList(List<TimeRuleWorkDayStatusVo> workDayStatusList) {
		this.workDayStatusList = workDayStatusList;
	}







	public List<TimeRuleBufferTimeVo> getBufferTimeLsit() {
		return bufferTimeLsit;
	}







	public void setBufferTimeLsit(List<TimeRuleBufferTimeVo> bufferTimeLsit) {
		this.bufferTimeLsit = bufferTimeLsit;
	}







	public String getTimeRuleCode() {
		return timeRuleCode;
	}




	public void setTimeRuleCode(String timeRuleCode) {
		this.timeRuleCode = timeRuleCode;
	}




	public Integer getTimeRulesInfoId() {
		return timeRulesInfoId;
	}

	public void setTimeRulesInfoId(Integer timeRulesInfoId) {
		this.timeRulesInfoId = timeRulesInfoId;
	}

	public int getTimeRulesId() {
		return timeRulesId;
	}

	public void setTimeRulesId(int timeRulesId) {
		this.timeRulesId = timeRulesId;
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

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
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

	public String getTimeRulesDescription() {
		return timeRulesDescription;
	}

	public void setTimeRulesDescription(String timeRulesDescription) {
		this.timeRulesDescription = timeRulesDescription;
	}

	public String getWeekStartDay() {
		return weekStartDay;
	}

	public void setWeekStartDay(String weekStartDay) {
		this.weekStartDay = weekStartDay;
	}

	public String getWeekEndDay() {
		return weekEndDay;
	}

	public void setWeekEndDay(String weekEndDay) {
		this.weekEndDay = weekEndDay;
	}

	public String getFollowFirstInLastOut() {
		return followFirstInLastOut;
	}

	public void setFollowFirstInLastOut(String followFirstInLastOut) {
		this.followFirstInLastOut = followFirstInLastOut;
	}

	public Integer getHoursDay() {
		return hoursDay;
	}

	public void setHoursDay(Integer hoursDay) {
		this.hoursDay = hoursDay;
	}

	public Integer getHoursWeek() {
		return hoursWeek;
	}
	
	public void setHoursWeek(Integer hoursWeek) {
		this.hoursWeek = hoursWeek;
	}

	public String getMinimumDays() {
		return minimumDays;
	}

	public void setMinimumDays(String minimumDays) {
		this.minimumDays = minimumDays;
	}

	public String getMaximumDays() {
		return maximumDays;
	}

	public void setMaximumDays(String maximumDays) {
		this.maximumDays = maximumDays;
	}

	public String getShowWo() {
		return showWo;
	}

	public void setShowWo(String showWo) {
		this.showWo = showWo;
	}

	public String getShowPh() {
		return showPh;
	}

	public void setShowPh(String showPh) {
		this.showPh = showPh;
	}

	public String getShowLeaves() {
		return showLeaves;
	}

	public void setShowLeaves(String showLeaves) {
		this.showLeaves = showLeaves;
	}

	public String getShowOd() {
		return showOd;
	}

	public void setShowOd(String showOd) {
		this.showOd = showOd;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public int getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(int modifiedBy) {
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







	public String getCountryName() {
		return countryName;
	}







	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	



}


