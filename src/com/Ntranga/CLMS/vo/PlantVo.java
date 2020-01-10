package com.Ntranga.CLMS.vo;

import java.util.Date;

public class PlantVo {

	private int plantDetailsId;
	private int customerId;
	private int companyId;
	private int plantId;
	private int plantSequenceId;
	private String plantName;
	private String shortName;
	private String contactName;
	private String phoneNumber;
	private String faxNumber;
	private String emailId;
	private String address1;
	private String address2;
	private String address3;
	private Integer countryId;
	private Integer stateId;
	private String cityName;
	private Integer pincode;
	//@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy", timezone="IST")
	private Date transactionDate;
	private String status;
	private String plantCode;
	private String locationName;
	private int locationId;
	private String customerName;
	private String companyName;
	private int createdBy;
	private int modifiedBy;
	private String transDate;
	private Integer departmentId;
	
	
	public PlantVo() {
		
	}
	
	public PlantVo(int plantId, String plantName) {		
		this.plantId = plantId;
		this.plantName = plantName;
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

	public int getPlantDetailsId() {
		return plantDetailsId;
	}

	public void setPlantDetailsId(int plantDetailsId) {
		this.plantDetailsId = plantDetailsId;
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

	public int getPlantId() {
		return plantId;
	}

	public void setPlantId(int plantId) {
		this.plantId = plantId;
	}

	public int getPlantSequenceId() {
		return plantSequenceId;
	}

	public void setPlantSequenceId(int plantSequenceId) {
		this.plantSequenceId = plantSequenceId;
	}

	public String getPlantName() {
		return plantName;
	}

	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
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

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
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

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getPincode() {
		return pincode;
	}

	public void setPincode(Integer pincode) {
		this.pincode = pincode;
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

	public String getPlantCode() {
		return plantCode;
	}

	public void setPlantCode(String plantCode) {
		this.plantCode = plantCode;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}


	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
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
	
	


	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	@Override
	public String toString() {
		return "PlantVo [plantDetailsId=" + plantDetailsId + ", customerId="
				+ customerId + ", companyId=" + companyId + ", plantId="
				+ plantId + ", plantSequenceId=" + plantSequenceId
				+ ", plantName=" + plantName + ", shortName=" + shortName
				+ ", contactName=" + contactName + ", phoneNumber="
				+ phoneNumber + ", faxNumber=" + faxNumber + ", emailId="
				+ emailId + ", address1=" + address1 + ", address2=" + address2
				+ ", address3=" + address3 + ", countryId=" + countryId
				+ ", stateId=" + stateId + ", cityName=" + cityName
				+ ", pincode=" + pincode + ", transactionDate="
				+ transactionDate + ", status=" + status + ", plantCode="
				+ plantCode + ", locationName=" + locationName
				+ ", locationId=" + locationId + ", customerName="
				+ customerName + ", companyName=" + companyName
				+ ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy
				+ "]";
	}

	/**
	 * @return the transDate
	 */
	public String getTransDate() {
		return transDate;
	}

	/**
	 * @param transDate the transDate to set
	 */
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	
	
}
