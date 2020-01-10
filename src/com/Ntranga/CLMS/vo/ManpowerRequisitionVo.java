package com.Ntranga.CLMS.vo;

import java.util.Date;
import java.util.List;

public class ManpowerRequisitionVo {

	private Integer manpowerRequisitionId;
	private Integer customerId;
	private Integer companyId;
	private Integer countryId;
	private  Integer locationId;
	private Integer plantId;
	private Integer departmentId;
	private String requestCode;
	private Date requestDate;
	private String status;
	private Integer employeeId;
	//private Date indenterDate;
	//private Integer employeeDepartmentId;
	private String employeeContactNumber;
	private String requiredFor;
	private String requestReason;
	private String justificationForManpower;
	//private String costOfManpower;
	//private Integer currencyId;
	private Integer totalHeadCount;
	private Integer costCenterId;
	private Integer createdBy;
	private Integer modifiedBy;
	
	private String locationName;
	private String departmentName;
	private String plantName;
	private String employeeCode;
	private String employeeName;
	private String currencyName;
	private String skillType;
	private String nameOfTheIndenter;
	
	private String requestType;
	private String frequency;
	private Integer year;
	private String month;
	private Date effectiveDate;
	
	private String comments;
	private Date approvedDate;
	private Integer approvedBy;
	
	
	private List<ManpowerRequisitionSkillsVo> manpowerSkillTypesList;

	public Integer getManpowerRequisitionId() {
		return manpowerRequisitionId;
	}

	public void setManpowerRequisitionId(Integer manpowerRequisitionId) {
		this.manpowerRequisitionId = manpowerRequisitionId;
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

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public Integer getPlantId() {
		return plantId;
	}

	public void setPlantId(Integer plantId) {
		this.plantId = plantId;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public String getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(String requestCode) {
		this.requestCode = requestCode;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeContactNumber() {
		return employeeContactNumber;
	}

	public void setEmployeeContactNumber(String employeeContactNumber) {
		this.employeeContactNumber = employeeContactNumber;
	}

	public String getRequiredFor() {
		return requiredFor;
	}

	public void setRequiredFor(String requiredFor) {
		this.requiredFor = requiredFor;
	}

	public String getRequestReason() {
		return requestReason;
	}

	public void setRequestReason(String requestReason) {
		this.requestReason = requestReason;
	}

	public String getJustificationForManpower() {
		return justificationForManpower;
	}

	public void setJustificationForManpower(String justificationForManpower) {
		this.justificationForManpower = justificationForManpower;
	}

	public Integer getTotalHeadCount() {
		return totalHeadCount;
	}

	public void setTotalHeadCount(Integer totalHeadCount) {
		this.totalHeadCount = totalHeadCount;
	}

	public Integer getCostCenterId() {
		return costCenterId;
	}

	public void setCostCenterId(Integer costCenterId) {
		this.costCenterId = costCenterId;
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

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getPlantName() {
		return plantName;
	}

	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getSkillType() {
		return skillType;
	}

	public void setSkillType(String skillType) {
		this.skillType = skillType;
	}

	public String getNameOfTheIndenter() {
		return nameOfTheIndenter;
	}

	public void setNameOfTheIndenter(String nameOfTheIndenter) {
		this.nameOfTheIndenter = nameOfTheIndenter;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public List<ManpowerRequisitionSkillsVo> getManpowerSkillTypesList() {
		return manpowerSkillTypesList;
	}

	public void setManpowerSkillTypesList(
			List<ManpowerRequisitionSkillsVo> manpowerSkillTypesList) {
		this.manpowerSkillTypesList = manpowerSkillTypesList;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public Integer getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(Integer approvedBy) {
		this.approvedBy = approvedBy;
	}


	@Override
	public String toString() {
		return "ManpowerRequisitionVo [manpowerRequisitionId="
				+ manpowerRequisitionId + ", customerId=" + customerId
				+ ", companyId=" + companyId + ", countryId=" + countryId
				+ ", locationId=" + locationId + ", plantId=" + plantId
				+ ", departmentId=" + departmentId + ", requestCode="
				+ requestCode + ", requestDate=" + requestDate + ", status="
				+ status + ", employeeId=" + employeeId
				+ ", employeeContactNumber=" + employeeContactNumber
				+ ", requiredFor=" + requiredFor + ", requestReason="
				+ requestReason + ", justificationForManpower="
				+ justificationForManpower + ", totalHeadCount="
				+ totalHeadCount + ", costCenterId=" + costCenterId
				+ ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy
				+ ", locationName=" + locationName + ", departmentName="
				+ departmentName + ", plantName=" + plantName
				+ ", employeeCode=" + employeeCode + ", employeeName="
				+ employeeName + ", currencyName=" + currencyName
				+ ", skillType=" + skillType + ", nameOfTheIndenter="
				+ nameOfTheIndenter + ", requestType=" + requestType
				+ ", frequency=" + frequency + ", year=" + year + ", month="
				+ month + ", effectiveDate=" + effectiveDate + ", comments="
				+ comments + ", approvedDate=" + approvedDate + ", approvedBy="
				+ approvedBy + ", manpowerSkillTypesList="
				+ manpowerSkillTypesList + "]";
	}

	

	
}
