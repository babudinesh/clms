package com.Ntranga.CLMS.vo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class WorkAreaVo {

	private int workAreaDetailsId;
	private int customerId;
	private int companyId;
	private String customerName;
	private String companyName;
	private String locationName;
	//private int countryName;
	private int countryId;
	private String plantName;
	private int plantId;
	private int workAreaId;
	private Integer workAreaSequenceId;
	private String workAreaName;
	private Integer sectionDetailsId;
	private String shortName;
	private String workAreaIncharge;
	private int locationId;
	private String contactNumber;
	private String emailId;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy", timezone="IST")
	private Date transactionDate;
	private String status;
	private String workAreaCode;
	private List<MWorkSkillVo> skillList;
	private int createdBy;
	private int modifiedBy;
	private String sectionName ;
	private String transDate;
	private Integer departmentId;
	private String departmentName;
	
	private String[] workAreaArrayId;
	
	
	
	
	
	
	public String[] getWorkAreaArrayId() {
		return workAreaArrayId;
	}
	public void setWorkAreaArrayId(String[] workAreaArrayId) {
		this.workAreaArrayId = workAreaArrayId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	public Integer getSectionDetailsId() {
		return sectionDetailsId;
	}
	public void setSectionDetailsId(Integer sectionDetailsId) {
		this.sectionDetailsId = sectionDetailsId;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
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


	public List<MWorkSkillVo> getSkillList() {
		return skillList;
	}

	public void setSkillList(List<MWorkSkillVo> skillList) {
		this.skillList = skillList;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
	public int getWorkAreaDetailsId() {
		return workAreaDetailsId;
	}
	public void setWorkAreaDetailsId(int workAreaDetailsId) {
		this.workAreaDetailsId = workAreaDetailsId;
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
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getPlantName() {
		return plantName;
	}
	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}
	public int getPlantId() {
		return plantId;
	}
	public void setPlantId(int plantId) {
		this.plantId = plantId;
	}
	public int getWorkAreaId() {
		return workAreaId;
	}
	public void setWorkAreaId(int workAreaId) {
		this.workAreaId = workAreaId;
	}
	public Integer getWorkAreaSequenceId() {
		return workAreaSequenceId;
	}
	public void setWorkAreaSequenceId(Integer workAreaSequenceId) {
		this.workAreaSequenceId = workAreaSequenceId;
	}
	public String getWorkAreaName() {
		return workAreaName;
	}
	public void setWorkAreaName(String workAreaName) {
		this.workAreaName = workAreaName;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getWorkAreaIncharge() {
		return workAreaIncharge;
	}
	public void setWorkAreaIncharge(String workAreaIncharge) {
		this.workAreaIncharge = workAreaIncharge;
	}
	public int getLocationId() {
		return locationId;
	}
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
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
	public String getWorkAreaCode() {
		return workAreaCode;
	}
	public void setWorkAreaCode(String workAreaCode) {
		this.workAreaCode = workAreaCode;
	}
	
	
	
	
	
	
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	@Override
	public String toString() {
		return "WorkAreaVo [workAreaDetailsId=" + workAreaDetailsId
				+ ", customerId=" + customerId + ", companyId=" + companyId
				+ ", customerName=" + customerName + ", companyName="
				+ companyName + ", locationName=" + locationName
				+ ", countryId=" + countryId + ", plantName=" + plantName
				+ ", plantId=" + plantId + ", workAreaId=" + workAreaId
				+ ", workAreaSequenceId=" + workAreaSequenceId
				+ ", workAreaName=" + workAreaName + ", shortName=" + shortName
				+ ", workAreaIncharge=" + workAreaIncharge + ", locationId="
				+ locationId + ", contactNumber=" + contactNumber
				+ ", emailId=" + emailId + ", transactionDate="
				+ transactionDate + ", status=" + status + ", workAreaCode="
				+ workAreaCode + ", skillList=" + skillList
				+ "]";
	}
	
	
}
