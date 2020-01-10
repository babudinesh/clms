package com.Ntranga.CLMS.vo;

import java.io.Serializable;
import java.util.Date;


public class SectionDetailsInfoVo implements Serializable {

	
	private Integer sectionDetailsInfoId;
	private Integer customerDetailsId;
	private Integer companyDetailsId;
	private Integer country;
	private Integer locationDetailsId;
	private Integer plantDetailsId;
	private Integer sectionDetailsId;
	private Integer sequenceId;
	private String sectionName;
	private String sectionCode;
	private String sectionSupervisorName;
	private String contactNumber;
	private Date transactionDate;
	private Integer createdBy;
    private Date createdDate;
	private Integer modifiedBy;
	
	private Integer customerId;
	private Integer companyId;
	private Integer locationId;
	private Integer plantId;
	private Integer departmentId;
	private String status;
	
	private String customerName;
	private String companyName;
	private String locationName;
	private String plantName;
	private String departmentName;
	private String[] sectionArrayId;
	
	
	
	
	public String[] getSectionArrayId() {
		return sectionArrayId;
	}
	public void setSectionArrayId(String[] sectionArrayId) {
		this.sectionArrayId = sectionArrayId;
	}
	
	
	public SectionDetailsInfoVo(Integer sectionDetailsId,String sectionName) {		
		this.sectionDetailsId = sectionDetailsId;
		this.sectionName = sectionName;
	}
	public SectionDetailsInfoVo() {		
	}
	
	
	
	
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	public String getSectionCode() {
		return sectionCode;
	}
	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
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
	
	public Integer getSectionDetailsInfoId() {
		return sectionDetailsInfoId;
	}
	public void setSectionDetailsInfoId(Integer sectionDetailsInfoId) {
		this.sectionDetailsInfoId = sectionDetailsInfoId;
	}
	public Integer getCustomerDetailsId() {
		return customerDetailsId;
	}
	public void setCustomerDetailsId(Integer customerDetailsId) {
		this.customerDetailsId = customerDetailsId;
	}
	public Integer getCompanyDetailsId() {
		return companyDetailsId;
	}
	public void setCompanyDetailsId(Integer companyDetailsId) {
		this.companyDetailsId = companyDetailsId;
	}
	public Integer getCountry() {
		return country;
	}
	public void setCountry(Integer country) {
		this.country = country;
	}
	public Integer getLocationDetailsId() {
		return locationDetailsId;
	}
	public void setLocationDetailsId(Integer locationDetailsId) {
		this.locationDetailsId = locationDetailsId;
	}
	public Integer getPlantDetailsId() {
		return plantDetailsId;
	}
	public void setPlantDetailsId(Integer plantDetailsId) {
		this.plantDetailsId = plantDetailsId;
	}
	public Integer getSectionDetailsId() {
		return sectionDetailsId;
	}
	public void setSectionDetailsId(Integer sectionDetailsId) {
		this.sectionDetailsId = sectionDetailsId;
	}
	public Integer getSequenceId() {
		return sequenceId;
	}
	public void setSequenceId(Integer sequenceId) {
		this.sequenceId = sequenceId;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public String getSectionSupervisorName() {
		return sectionSupervisorName;
	}
	public void setSectionSupervisorName(String sectionSupervisorName) {
		this.sectionSupervisorName = sectionSupervisorName;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
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
	public Integer getLocationId() {
		return locationId;
	}
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getPlantId() {
		return plantId;
	}
	public void setPlantId(Integer plantId) {
		this.plantId = plantId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
	
	
	
	
}
