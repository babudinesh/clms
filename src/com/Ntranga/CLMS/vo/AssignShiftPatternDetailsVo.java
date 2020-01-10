package com.Ntranga.CLMS.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class AssignShiftPatternDetailsVo implements Serializable {

	private Integer customerId;
	private Integer companyId;
	private Integer vendorId;
	private Integer workOrderId;
	private Integer departmentId;
    private Integer locationId;
    private Integer plantId;
    private Integer sectionId;
    private String shiftCode;
    private Date patternDate;
    private Integer workAreaId;
    private Integer createdBy;
    private Date createdDate;
    private Integer modifiedBy;
    private Date modifiedDate;
    private String status;
    private String monthYear;
    
    private List<AssignShiftsVo> assignShiftsVo;
    
           
    
	public String getMonthYear() {
		return monthYear;
	}
	public void setMonthYear(String monthYear) {
		this.monthYear = monthYear;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getSectionId() {
		return sectionId;
	}
	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
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
	public Integer getWorkOrderId() {
		return workOrderId;
	}
	public void setWorkOrderId(Integer workOrderId) {
		this.workOrderId = workOrderId;
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
	public String getShiftCode() {
		return shiftCode;
	}
	public void setShiftCode(String shiftCode) {
		this.shiftCode = shiftCode;
	}
	public Date getPatternDate() {
		return patternDate;
	}
	public void setPatternDate(Date patternDate) {
		this.patternDate = patternDate;
	}
	public Integer getWorkAreaId() {
		return workAreaId;
	}
	public void setWorkAreaId(Integer workAreaId) {
		this.workAreaId = workAreaId;
	}
	public List<AssignShiftsVo> getAssignShiftsVo() {
		return assignShiftsVo;
	}
	public void setAssignShiftsVo(List<AssignShiftsVo> assignShiftsVo) {
		this.assignShiftsVo = assignShiftsVo;
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
	@Override
	public String toString() {
		return "AssignShiftPatternDetailsVo [customerId=" + customerId + ", companyId=" + companyId + ", vendorId="
				+ vendorId + ", workOrderId=" + workOrderId + ", departmentId=" + departmentId + ", locationId="
				+ locationId + ", plantId=" + plantId + ", shiftCode=" + shiftCode + ", patternDate=" + patternDate
				+ ", workAreaId=" + workAreaId + ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", modifiedBy=" + modifiedBy + ", modifiedDate=" + modifiedDate + ", assignShiftsVo=" + assignShiftsVo
				+ "]";
	}
	
	
    
    
    
	
}
