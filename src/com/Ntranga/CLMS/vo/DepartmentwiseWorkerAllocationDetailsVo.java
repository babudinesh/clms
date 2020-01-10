package com.Ntranga.CLMS.vo;
// Generated 22 Dec, 2016 12:44:03 PM by Hibernate Tools 3.6.0


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DepartmentwiseWorkerAllocationDetailsVo  implements java.io.Serializable {


     private Integer workerAllocationInfoId;
     private Integer plantId ;
     private Integer departmentId;
     private Integer customerId;
     private Integer workAreaId;
     private Integer workerAllocationId;
     private Integer sectionId;
     private Integer companyId;
     private Integer locationId;
     private int countryId;
     private String plannedOrAdhoc;
     private Date fromDate;
     private Date toDate;
     private Date transactionDate;
     private Integer createdBy;
     private Date createdDate;
     private Integer modifiedBy;
     private Date modifiedDate;
     private Integer transactionId;
     private String requestStatus;
     private String comments;
     
     
     private String departmentName;
     private String locationName;
     
     
     
     public String getComments() {
 		return comments;
 	}


 	public void setComments(String comments) {
 		this.comments = comments;
 	}

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

private List<DepartmentwiseWorkerAllocationRequirementDetailsVo> workerAllocationRequirementList = new ArrayList<DepartmentwiseWorkerAllocationRequirementDetailsVo>();

public Integer getWorkerAllocationInfoId() {
	return workerAllocationInfoId;
}

public void setWorkerAllocationInfoId(Integer workerAllocationInfoId) {
	this.workerAllocationInfoId = workerAllocationInfoId;
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

public Integer getCustomerId() {
	return customerId;
}

public void setCustomerId(Integer customerId) {
	this.customerId = customerId;
}

public Integer getWorkAreaId() {
	return workAreaId;
}

public void setWorkAreaId(Integer workAreaId) {
	this.workAreaId = workAreaId;
}

public Integer getWorkerAllocationId() {
	return workerAllocationId;
}

public void setWorkerAllocationId(Integer workerAllocationId) {
	this.workerAllocationId = workerAllocationId;
}

public Integer getSectionId() {
	return sectionId;
}

public void setSectionId(Integer sectionId) {
	this.sectionId = sectionId;
}

public Integer getCompanyId() {
	return companyId;
}

public void setCompanyId(Integer companyId) {
	this.companyId = companyId;
}

public Integer getLocationId() {
	return locationId;
}

public void setLocationId(Integer locationId) {
	this.locationId = locationId;
}

public int getCountryId() {
	return countryId;
}

public void setCountryId(int countryId) {
	this.countryId = countryId;
}

public String getPlannedOrAdhoc() {
	return plannedOrAdhoc;
}

public void setPlannedOrAdhoc(String plannedOrAdhoc) {
	this.plannedOrAdhoc = plannedOrAdhoc;
}

public Date getFromDate() {
	return fromDate;
}

public void setFromDate(Date fromDate) {
	this.fromDate = fromDate;
}

public Date getToDate() {
	return toDate;
}

public void setToDate(Date toDate) {
	this.toDate = toDate;
}

public Date getTransactionDate() {
	return transactionDate;
}

public void setTransactionDate(Date transactionDate) {
	this.transactionDate = transactionDate;
}

public Integer getCreatedBy() {
	return createdBy;
}

public void setCreatedBy(Integer createdBy) {
	this.createdBy = createdBy;
}

public Date getCreatedDate() {
	return createdDate;
}

public void setCreatedDate(Date createdDate) {
	this.createdDate = createdDate;
}

public Integer getModifiedBy() {
	return modifiedBy;
}

public void setModifiedBy(Integer modifiedBy) {
	this.modifiedBy = modifiedBy;
}

public Date getModifiedDate() {
	return modifiedDate;
}

public void setModifiedDate(Date modifiedDate) {
	this.modifiedDate = modifiedDate;
}

public Integer getTransactionId() {
	return transactionId;
}

public void setTransactionId(Integer transactionId) {
	this.transactionId = transactionId;
}

public List<DepartmentwiseWorkerAllocationRequirementDetailsVo> getWorkerAllocationRequirementList() {
	return workerAllocationRequirementList;
}

public void setWorkerAllocationRequirementList(
		List<DepartmentwiseWorkerAllocationRequirementDetailsVo> workerAllocationRequirementList) {
	this.workerAllocationRequirementList = workerAllocationRequirementList;
}



}


