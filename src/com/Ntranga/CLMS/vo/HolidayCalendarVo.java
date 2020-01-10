package com.Ntranga.CLMS.vo;

import java.util.List;


public class HolidayCalendarVo {

	private int customerId;
	private String customerName;
	private String companyName;
	private int companyId;
	private int holidayCalendarDetailsId;
	private int holidayCalendarId;
	private int locationId;
	private String locationName;
	private int countryId;
	private String countryName;
	private String status;
	private String calendarName;
	private String holidayCalendarCode;
	private Integer year;
    private Integer createdBy;
    private Integer modifiedBy;
    private List<HolidayCalendarHolidaysInfoVo> holidayList ;
    
    
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
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
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public int getHolidayCalendarDetailsId() {
		return holidayCalendarDetailsId;
	}
	public void setHolidayCalendarDetailsId(int holidayCalendarDetailsId) {
		this.holidayCalendarDetailsId = holidayCalendarDetailsId;
	}
	public int getHolidayCalendarId() {
		return holidayCalendarId;
	}
	public void setHolidayCalendarId(int holidayCalendarId) {
		this.holidayCalendarId = holidayCalendarId;
	}
	public int getLocationId() {
		return locationId;
	}
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public int getCountryId() {
		return countryId;
	}
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCalendarName() {
		return calendarName;
	}
	public void setCalendarName(String calendarName) {
		this.calendarName = calendarName;
	}
	public String getHolidayCalendarCode() {
		return holidayCalendarCode;
	}
	public void setHolidayCalendarCode(String holidayCalendarCode) {
		this.holidayCalendarCode = holidayCalendarCode;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
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

	public List<HolidayCalendarHolidaysInfoVo> getHolidayList() {
		return holidayList;
	}
	
	public void setHolidayList(List<HolidayCalendarHolidaysInfoVo> holidayList) {
		this.holidayList = holidayList;
	}
	@Override
	public String toString() {
		return "HolidayCalendarVo [customerId=" + customerId
				+ ", customerName=" + customerName + ", companyName="
				+ companyName + ", companyId=" + companyId
				+ ", holidayCalendarDetailsId=" + holidayCalendarDetailsId
				+ ", holidayCalendarId=" + holidayCalendarId + ", locationId="
				+ locationId + ", locationName=" + locationName
				+ ", countryId=" + countryId + ", countryName=" + countryName
				+ ", status=" + status + ", calendarName=" + calendarName
				+ ", holidayCalendarCode=" + holidayCalendarCode + ", year="
				+ year + ", createdBy=" + createdBy + ", modifiedBy="
				+ modifiedBy + ", holidayList=" + holidayList + "]";
	}
    
    
    
}
