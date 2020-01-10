package com.Ntranga.CLMS.vo;
// Generated 16 Jul, 2016 7:27:01 PM by Hibernate Tools 3.6.0

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import com.Ntranga.core.CLMS.entities.WorkerDetailsInfo;



public class WorkerEducationAndEmploymentVo implements java.io.Serializable {

	private Integer customerId;
	private Integer companyId;
	private Integer vendorId;
	private Integer workerId;
	private String workSkill;
	private int createdBy;
	private Date createdDate;
	private int modifiedBy;
	private Date modifiedDate;
	private String workerCode;
	private String workerName;
	private String countryName;
	private String customerName;
	private String companyName;
	private String vendorName;
	private String filePath;
	
		
	private List<WorkerEducationDetailsVo> educationalList = new ArrayList() ;
	private List<WorkerCertificationDetailsVo> certificationList = new ArrayList();
	private List<WorkerEmploymentDetailsVo> employmentList = new ArrayList();
	private List<WorkerReferenceVo> referenceList = new ArrayList();
	

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

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public int getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public List<WorkerEducationDetailsVo> getEducationalList() {
		return educationalList;
	}

	public void setEducationalList(List<WorkerEducationDetailsVo> educationalList) {
		this.educationalList = educationalList;
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

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
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

	public List<WorkerCertificationDetailsVo> getCertificationList() {
		return certificationList;
	}

	public void setCertificationList(List<WorkerCertificationDetailsVo> certificationList) {
		this.certificationList = certificationList;
	}

	public List<WorkerEmploymentDetailsVo> getEmploymentList() {
		return employmentList;
	}

	public void setEmploymentList(List<WorkerEmploymentDetailsVo> employmentList) {
		this.employmentList = employmentList;
	}

	public List<WorkerReferenceVo> getReferenceList() {
		return referenceList;
	}

	public void setReferenceList(List<WorkerReferenceVo> referenceList) {
		this.referenceList = referenceList;
	}
	
	@Column(name="File_Path", length=255)
    public String getFilePath() {
        return this.filePath;
    }
    
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

	public String getWorkSkill() {
		return workSkill;
	}

	public void setWorkSkill(String workSkill) {
		this.workSkill = workSkill;
	}
	
	

}
