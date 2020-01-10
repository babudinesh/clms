package com.Ntranga.CLMS.vo;

import java.util.Date;
import java.util.List;

public class LocationVo {

	private String customerName;
	private String companyName;
	private int customerId ;
	private int companyId ;
	private int locDeptId;
	private int locationId ;
	private int locationDetailsId;
	private int locationTypeId;
	private String locationCode;
	private String locationName;
	private String riskIndicator;
	private String hazardousIndicator;
	private String riskDetails;
	private String status;
	//@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy", timezone="IST")
	private Date transactionDate;
	private String transDate;
	private boolean isHeadquarter;
	private String shiftId;	
	private String holidayCalendarId; 
	private boolean isShiftSystem;
	private String workWeekStartDay;
	private String workWeekEndDay;
	private Integer numberOfWorkingDays;
	private Integer businessHoursPerDay;
	private Integer standardHoursPerWeek;
	private String businessStartTime;
	private String businessEndTime;
	
	//Associated Department
	private String departmentCode;
	private String departmentName;
	private int departmentId;
	private int departmentInfoId;
	
	private List<LocationVo> departmentList;
	
	//Associated Address
	private String address1;
	private String address2;
	private String address3;
	private String cityId;
	private Integer stateId;
	private Integer countryId;
	private Integer pincode;
	
	//Associated contact
	private String contactName;
	private String emailId;
	private String phoneNumber;
	private String faxNumber;
	private int createdBy;
	private int modifiedBy;
	
	public LocationVo(){
		
	}
	
	public LocationVo(int locationId, String locationName) {	
		this.locationId = locationId;
		this.locationName = locationName;
	}

	public List<LocationVo> getDepartmentList() {
		return departmentList;
	}
	
	public void setDepartmentList(List<LocationVo> departmentList) {
		this.departmentList = departmentList;
	}
	
	/**
	 * @return the departmentInfoId
	 */
	public int getDepartmentInfoId() {
		return departmentInfoId;
	}

	/**
	 * @param departmentInfoId the departmentInfoId to set
	 */
	public void setDepartmentInfoId(int departmentInfoId) {
		this.departmentInfoId = departmentInfoId;
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
	public int getLocDeptId() {
		return locDeptId;
	}
	public void setLocDeptId(int locDeptId) {
		this.locDeptId = locDeptId;
	}
	public int getLocationId() {
		return locationId;
	}
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
	public int getLocationDetailsId() {
		return locationDetailsId;
	}
	public void setLocationDetailsId(int locationDetailsId) {
		this.locationDetailsId = locationDetailsId;
	}
	public int getLocationTypeId() {
		return locationTypeId;
	}
	public void setLocationTypeId(int locationTypeId) {
		this.locationTypeId = locationTypeId;
	}
	public String getLocationCode(){
		return locationCode;
	}
	public void setLocationCode(String locationCode){
		this.locationCode= locationCode;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getRiskIndicator() {
		return riskIndicator;
	}
	public void setRiskIndicator(String riskIndicator) {
		this.riskIndicator = riskIndicator;
	}
	public String getHazardousIndicator() {
		return hazardousIndicator;
	}
	public void setHazardousIndicator(String hazardousIndicator) {
		this.hazardousIndicator = hazardousIndicator;
	}
	public String getRiskDetails() {
		return riskDetails;
	}
	public void setRiskDetails(String riskDetails) {
		this.riskDetails = riskDetails;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String isActive) {
		this.status = isActive;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public boolean getIsHeadquarter() {
		return isHeadquarter;
	}
	public void setIsHeadquarter(boolean isHeadquarter) {
		this.isHeadquarter = isHeadquarter;
	}
	public String getShiftId() {
		return shiftId;
	}
	public void setShiftId(String shiftId) {
		this.shiftId = shiftId;
	}
	public String getHolidayCalendarId() {
		return holidayCalendarId;
	}
	public void setHolidayCalendarId(String holidayCalendarId) {
		this.holidayCalendarId = holidayCalendarId;
	}
	public boolean getIsShiftSystem() {
		return isShiftSystem;
	}
	public void setIsShiftSystem(boolean isShiftSystem) {
		this.isShiftSystem = isShiftSystem;
	}
	public String getWorkWeekStartDay() {
		return workWeekStartDay;
	}
	public void setWorkWeekStartDay(String workWeekStartDay) {
		this.workWeekStartDay = workWeekStartDay;
	}
	public String getWorkWeekEndDay() {
		return workWeekEndDay;
	}
	public void setWorkWeekEndDay(String workWeekEndDay) {
		this.workWeekEndDay = workWeekEndDay;
	}
	public Integer getNumberOfWorkingDays() {
		return numberOfWorkingDays;
	}
	public void setNumberOfWorkingDays(Integer numberOfWorkingDays) {
		this.numberOfWorkingDays = numberOfWorkingDays;
	}
	public Integer getBusinessHoursPerDay() {
		return businessHoursPerDay;
	}
	public void setBusinessHoursPerDay(Integer businessHoursPerDay) {
		this.businessHoursPerDay = businessHoursPerDay;
	}
	public Integer getStandardHoursPerWeek() {
		return standardHoursPerWeek;
	}
	public void setStandardHoursPerWeek(Integer standardHoursPerWeek) {
		this.standardHoursPerWeek = standardHoursPerWeek;
	}
	public String getBusinessStartTime() {
		return businessStartTime;
	}
	public void setBusinessStartTime(String businessStartTime) {
		this.businessStartTime = businessStartTime;
	}
	public String getBusinessEndTime() {
		return businessEndTime;
	}
	public void setBusinessEndTime(String businessEndTime) {
		this.businessEndTime = businessEndTime;
	}
	public String getDepartmentCode() {
		return departmentCode;
	}
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getAddress3() {
		return address3;
	}
	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public Integer getStateId() {
		return stateId;
	}
	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}
	public Integer getCountryId() {
		return countryId;
	}
	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}
	public Integer getPincode() {
		return pincode;
	}
	public void setPincode(Integer pincode) {
		this.pincode = pincode;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getFaxNumber() {
		return faxNumber;
	}
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}
	
	@Override
	public String toString() {
		return "LocationVo [customerName=" + customerName + ", companyName="
				+ companyName + ", customerId=" + customerId + ", companyId="
				+ companyId + ", locationId=" + locationId
				+ ", locationDetailsId=" + locationDetailsId
				+ ", locationTypeId=" + locationTypeId + ", locationCode="
				+ locationCode + ", locationName=" + locationName
				+ ", riskIndicator=" + riskIndicator + ", hazardousIndicator="
				+ hazardousIndicator + ", riskDetails=" + riskDetails
				+ ", status=" + status + ", transactionDate=" + transactionDate
				+ ", isHeadquarter=" + isHeadquarter + ", shiftId=" + shiftId
				+ ", holidayaCalendarId=" + holidayCalendarId
				+ ", isShiftSystem=" + isShiftSystem + ", workWeekStartDay="
				+ workWeekStartDay + ", workWeekEndDay=" + workWeekEndDay
				+ ", numberOfWorkingDays=" + numberOfWorkingDays
				+ ", businessHoursperDay=" + businessHoursPerDay
				+ ", standardHoursPerWeek=" + standardHoursPerWeek
				+ ", businessStartTime=" + businessStartTime
				+ ", businessEndTime=" + businessEndTime + ", departmentCode="
				+ departmentCode + ", departmentName=" + departmentName
				+ ", departmentId=" + departmentId + ", address1=" + address1
				+ ", address2=" + address2 + ", address3=" + address3
				+ ", cityId=" + cityId + ", stateId=" + stateId
				+ ", countryId=" + countryId + ", pincode=" + pincode
				+ ", contactName=" + contactName + ", emailId=" + emailId
				+ ", phoneNumber=" + phoneNumber + ", faxNumber=" + faxNumber
				+ "]";
	}

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	

	
	
}
