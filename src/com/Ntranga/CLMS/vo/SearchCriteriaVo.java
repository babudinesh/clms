package com.Ntranga.CLMS.vo;

import java.sql.Date;

public class SearchCriteriaVo implements java.io.Serializable {

	private Integer userId;
	private Integer customerId;
	private Integer companyId;
	private Integer vendorId;
	private Integer workerId;
	
	private String employeeName;
	private String vendorCode;
	private String vendorName;
	
	private String workerCode;
	private String workerName;
	
	
	private String isActive;
	private String status;
	
	private Date selectedDate;
	private String searchFor;

	
	private String locationId;
	private String plantId;
	private String departmentId;
	private String sectionId;
	private String workAreaId;
	private String schemaName;
	
	private String[] locationArrayId;
	private String[] plantArrayId;
	private String[] departmentArrayId;
	private String[] sectionArrayId;
	private String[] workAreaArrayId;
	private String[] roleIdsArray;
	
	
	
	public String[] getRoleIdsArray() {
		return roleIdsArray;
	}
	public void setRoleIdsArray(String[] roleIdsArray) {
		this.roleIdsArray = roleIdsArray;
	}
	public String getSchemaName() {
		return schemaName;
	}
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
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
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public Integer getWorkerId() {
		return workerId;
	}
	public void setWorkerId(Integer workerId) {
		this.workerId = workerId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getVendorCode() {
		return vendorCode;
	}
	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public Date getSelectedDate() {
		return selectedDate;
	}
	public void setSelectedDate(Date selectedDate) {
		this.selectedDate = selectedDate;
	}
	public String getSearchFor() {
		return searchFor;
	}
	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getPlantId() {
		return plantId;
	}
	public void setPlantId(String plantId) {
		this.plantId = plantId;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getSectionId() {
		return sectionId;
	}
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}
	public String getWorkAreaId() {
		return workAreaId;
	}
	public void setWorkAreaId(String workAreaId) {
		this.workAreaId = workAreaId;
	}
	public String[] getLocationArrayId() {
		return locationArrayId;
	}
	public void setLocationArrayId(String[] locationArrayId) {
		this.locationArrayId = locationArrayId;
	}
	public String[] getPlantArrayId() {
		return plantArrayId;
	}
	public void setPlantArrayId(String[] plantArrayId) {
		this.plantArrayId = plantArrayId;
	}
	public String[] getDepartmentArrayId() {
		return departmentArrayId;
	}
	public void setDepartmentArrayId(String[] departmentArrayId) {
		this.departmentArrayId = departmentArrayId;
	}
	public String[] getSectionArrayId() {
		return sectionArrayId;
	}
	public void setSectionArrayId(String[] sectionArrayId) {
		this.sectionArrayId = sectionArrayId;
	}
	public String[] getWorkAreaArrayId() {
		return workAreaArrayId;
	}
	public void setWorkAreaArrayId(String[] workAreaArrayId) {
		this.workAreaArrayId = workAreaArrayId;
	}
	public String getWorkerCode() {
		return workerCode;
	}
	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
	}
	public String getWorkerName() {
		return workerName;
	}
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	
	
}
