package com.Ntranga.CLMS.vo;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class WorkJobDetailsVo implements Serializable {

	private Integer workJobDetailsId;
    private Integer workerDetailsId;
    private Integer customerDetailsId;
    private Integer vendorDetailsId;
    private Integer companyDetailsId;
    private Date transactionDate;
    private Integer sequenceId;
    private String jobType;
    private String workSkill;
    private Integer jobName;
    //private Date workStartDate;

    //private Integer plantName;

    private Integer reportingManager;
    private String vendorSupervisorName;
    private String contactNo;
    private String pfNumber;
    private String esiNumber;
    private Integer createdBy;    
    private Integer modifiedBy;
    
    private String customerName;
    private String companyName;
    private String vendorName;
    private String workerName;
    private String locationName;
    private String departmentName;
    
    private Integer wageRateId;
    private Integer locationId;
    private Integer departmentId;
    private Integer plantId;
    private Integer sectionId;
    private Integer workOrderId;
    private Integer workAreaId;
  
   private String strTransactionDate;
    
     
	public String getStrTransactionDate() {
		return strTransactionDate;
	}
	public void setStrTransactionDate(String strTransactionDate) {
		this.strTransactionDate = strTransactionDate;
	}
	public Integer getWorkJobDetailsId() {
		return workJobDetailsId;
	}
	public void setWorkJobDetailsId(Integer workJobDetailsId) {
		this.workJobDetailsId = workJobDetailsId;
	}
	public Integer getWorkerDetailsId() {
		return workerDetailsId;
	}
	public void setWorkerDetailsId(Integer workerDetailsId) {
		this.workerDetailsId = workerDetailsId;
	}
	public Integer getCustomerDetailsId() {
		return customerDetailsId;
	}
	public void setCustomerDetailsId(Integer customerDetailsId) {
		this.customerDetailsId = customerDetailsId;
	}
	public Integer getVendorDetailsId() {
		return vendorDetailsId;
	}
	public void setVendorDetailsId(Integer vendorDetailsId) {
		this.vendorDetailsId = vendorDetailsId;
	}
	public Integer getCompanyDetailsId() {
		return companyDetailsId;
	}
	public void setCompanyDetailsId(Integer companyDetailsId) {
		this.companyDetailsId = companyDetailsId;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public Integer getSequenceId() {
		return sequenceId;
	}
	public void setSequenceId(Integer sequenceId) {
		this.sequenceId = sequenceId;
	}
	/*public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getReasonForChange() {
		return reasonForChange;
	}
	public void setReasonForChange(String reasonForChange) {
		this.reasonForChange = reasonForChange;
	}*/
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	public String getWorkSkill() {
		return workSkill;
	}
	public void setWorkSkill(String workSkill) {
		this.workSkill = workSkill;
	}
	public Integer getJobName() {
		return jobName;
	}
	public void setJobName(Integer jobName) {
		this.jobName = jobName;
	}
	/*public Date getWorkStartDate() {
		return workStartDate;
	}
	public void setWorkStartDate(Date workStartDate) {
		this.workStartDate = workStartDate;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public Integer getPlantName() {
		return plantName;
	}
	public void setPlantName(Integer plantName) {
		this.plantName = plantName;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}*/
	public Integer getReportingManager() {
		return reportingManager;
	}
	public void setReportingManager(Integer reportingManager) {
		this.reportingManager = reportingManager;
	}
	/*public Integer getWorkOrder() {
		return workOrder;
	}
	public void setWorkOrder(Integer workOrder) {
		this.workOrder = workOrder;
	}*/
	/*public Integer getCardType() {
		return cardType;
	}
	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}*/
	
	public String getVendorSupervisorName() {
		return vendorSupervisorName;
	}
	public void setVendorSupervisorName(String vendorSupervisorName) {
		this.vendorSupervisorName = vendorSupervisorName;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getPfNumber() {
		return pfNumber;
	}
	public void setPfNumber(String pfNumber) {
		this.pfNumber = pfNumber;
	}
	public String getEsiNumber() {
		return esiNumber;
	}
	public void setEsiNumber(String esiNumber) {
		this.esiNumber = esiNumber;
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
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getWorkerName() {
		return workerName;
	}
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
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
	public Integer getWageRateId() {
		return wageRateId;
	}
	public void setWageRateId(Integer wageRateId) {
		this.wageRateId = wageRateId;
	}
	public Integer getLocationId() {
		return locationId;
	}
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
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
	public Integer getWorkOrderId() {
		return workOrderId;
	}
	public void setWorkOrderId(Integer workOrderId) {
		this.workOrderId = workOrderId;
	}
	public Integer getWorkAreaId() {
		return workAreaId;
	}
	public void setWorkAreaId(Integer workAreaId) {
		this.workAreaId = workAreaId;
	}


    
    
    
}
