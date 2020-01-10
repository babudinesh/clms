package com.Ntranga.CLMS.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AssignShiftsVo implements Serializable {
	
	private Integer workerId;
	private String workerCode;
    private String workerName;
    private String vendorName;
    private String workOrderName;
    private String workAreaName;
    private String departmentName;
    private String locationName;
    private String validityDate;
    private Boolean selected;  
    private Integer departmentId;
    private Integer locationId;
    private Integer sectionId;
    private List<SimpleObject> plantList = new ArrayList<SimpleObject>();
           
    
	public Integer getSectionId() {
		return sectionId;
	}
	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}
	public List<SimpleObject> getPlantList() {
		return plantList;
	}
	public void setPlantList(List<SimpleObject> plantList) {
		this.plantList = plantList;
	}
	public Boolean getSelected() {
		return selected;
	}
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
	public Integer getWorkerId() {
		return workerId;
	}
	public void setWorkerId(Integer workerId) {
		this.workerId = workerId;
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
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getWorkOrderName() {
		return workOrderName;
	}
	public void setWorkOrderName(String workOrderName) {
		this.workOrderName = workOrderName;
	}
	public String getWorkAreaName() {
		return workAreaName;
	}
	public void setWorkAreaName(String workAreaName) {
		this.workAreaName = workAreaName;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getValidityDate() {
		return validityDate;
	}
	public void setValidityDate(String validityDate) {
		this.validityDate = validityDate;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
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
	@Override
	public String toString() {
		return "AssignShiftsVo [workerId=" + workerId + ", workerCode=" + workerCode + ", workerName=" + workerName
				+ ", vendorName=" + vendorName + ", workOrderName=" + workOrderName + ", workAreaName=" + workAreaName
				+ ", departmentName=" + departmentName + ", locationName=" + locationName + ", validityDate="
				+ validityDate + ", selected=" + selected + ", departmentId=" + departmentId + ", locationId="
				+ locationId + ", plantList=" + plantList + "]";
	}
    
	
}
