package com.Ntranga.CLMS.vo;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class JobAllocationByVendorVo {

	private Integer laborTimeReportId;
	private Integer departmentId;
	private Integer locationId;
	private Integer plantId;
	private Integer sectionId;
	private Integer workAreaId;
	private String shiftName;
	private String workSkill;
	private Date selectedDate;
	private Integer createdBy;
	private Integer modifiedBy;
	private Date createdDate;
	private String schemaName;
	private String userId;
	private String status;
	private List<JobAllocationByVendorVo> workerList = new ArrayList<JobAllocationByVendorVo>();
	
	private Integer customerId;
	private Integer companyId;
	private Integer vendorId;
	private Integer workerId;
	private String searchFor;
	
	private String vendorCode;
	private String vendorName;
	private String customerName;
	private String companyName;
	private String plantName;
	private String departmentName;
	private String locationName;
	private String sectionName;
	private String workerName;
	private String jobName;
	private String workerCode;
	private String workAreaName;
	private boolean selected;
	
	
	private String[] locationArrayId;
	
	
	public Integer getLaborTimeReportId() {
		return laborTimeReportId;
	}
	public void setLaborTimeReportId(Integer laborTimeReportId) {
		this.laborTimeReportId = laborTimeReportId;
	}
	public String[] getLocationArrayId() {
		return locationArrayId;
	}
	public void setLocationArrayId(String[] locationArrayId) {
		this.locationArrayId = locationArrayId;
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


	public Integer getDepartmentId() {
		return departmentId;
	}


	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
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


	public Integer getSectionId() {
		return sectionId;
	}


	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}


	public Integer getWorkAreaId() {
		return workAreaId;
	}


	public void setWorkAreaId(Integer workAreaId) {
		this.workAreaId = workAreaId;
	}


	public String getShiftName() {
		return shiftName;
	}


	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}


	public String getWorkSkill() {
		return workSkill;
	}


	public void setWorkSkill(String workSkill) {
		this.workSkill = workSkill;
	}


	public Date getSelectedDate() {
		return selectedDate;
	}


	public void setSelectedDate(Date selectedDate) {
		this.selectedDate = selectedDate;
	}


	public List<JobAllocationByVendorVo> getWorkerList() {
		return workerList;
	}


	public void setWorkerList(List<JobAllocationByVendorVo> workerList) {
		this.workerList = workerList;
	}


	public String getSchemaName() {
		return schemaName;
	}


	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStatus(){
		return status;
	}
	
	public void setStatus(String status){
		this.status = status;
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


	public String getCustomerName() {
		return customerName;
	}


	public String getVendorCode() {
		return vendorCode;
	}


	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
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


	public String getVendorName() {
		return vendorName;
	}


	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}


	public String getPlantName() {
		return plantName;
	}


	public void setPlantName(String plantName) {
		this.plantName = plantName;
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


	public String getSectionName() {
		return sectionName;
	}


	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}


	public String getWorkerName() {
		return workerName;
	}


	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}


	public String getJobName() {
		return jobName;
	}


	public void setJobName(String jobName) {
		this.jobName = jobName;
	}


	public String getWorkerCode() {
		return workerCode;
	}


	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
	}


	public String getWorkAreaName() {
		return workAreaName;
	}


	public void setWorkAreaName(String workAreaName) {
		this.workAreaName = workAreaName;
	}


	public boolean isSelected() {
		return selected;
	}


	public void setSelected(boolean selected) {
		this.selected = selected;
	}


	public String getSearchFor() {
		return searchFor;
	}


	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}
	
	public Integer getModifiedBy() {
		return modifiedBy;
	}


	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	public Integer getWorkerId() {
		return workerId;
	}


	public void setWorkerId(Integer workerId) {
		this.workerId = workerId;
	}
	
}
