package com.Ntranga.CLMS.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class DefineComplianceTypesVo implements Serializable {

	
	private Integer defineComplianceTypeId;
    private Integer customerDetailsId;   
    private Integer companyDetailsId;
    private String complianceCode;
    private Integer country;
    private Integer sequenceId;
    private Date transactionDate;
    private String complianceAct;
    private String doccumentName;
    private String doccumentDescription;
    private String applicableTo;
    private String frequency;     
    private String isActive;
    private int createdBy;
    private Date createdDate;
    private int modifiedBy;
    private Date modifiedDate;
    private Integer complianceUniqueId;
    private boolean isMandatory;
    private List<DefineComplianceTypesVo> complianceList = new ArrayList<DefineComplianceTypesVo>();
    private Integer locationId;
    
    
	
	public List<DefineComplianceTypesVo> getComplianceList() {
		return complianceList;
	}
	public void setComplianceList(List<DefineComplianceTypesVo> complianceList) {
		this.complianceList = complianceList;
	}
	public Integer getDefineComplianceTypeId() {
		return defineComplianceTypeId;
	}
	public void setDefineComplianceTypeId(Integer defineComplianceTypeId) {
		this.defineComplianceTypeId = defineComplianceTypeId;
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
	public String getComplianceCode() {
		return complianceCode;
	}
	public void setComplianceCode(String complianceCode) {
		this.complianceCode = complianceCode;
	}	
	public Integer getCountry() {
		return country;
	}
	public void setCountry(Integer country) {
		this.country = country;
	}
	public Integer getSequenceId() {
		return sequenceId;
	}
	public void setSequenceId(Integer sequenceId) {
		this.sequenceId = sequenceId;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getComplianceAct() {
		return complianceAct;
	}
	public void setComplianceAct(String complianceAct) {
		this.complianceAct = complianceAct;
	}
	public String getDoccumentName() {
		return doccumentName;
	}
	public void setDoccumentName(String doccumentName) {
		this.doccumentName = doccumentName;
	}
	public String getDoccumentDescription() {
		return doccumentDescription;
	}
	public void setDoccumentDescription(String doccumentDescription) {
		this.doccumentDescription = doccumentDescription;
	}
	public String getApplicableTo() {
		return applicableTo;
	}
	public void setApplicableTo(String applicableTo) {
		this.applicableTo = applicableTo;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
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
	public Integer getComplianceUniqueId() {
		return complianceUniqueId;
	}
	public void setComplianceUniqueId(Integer complianceUniqueId) {
		this.complianceUniqueId = complianceUniqueId;
	}
	public boolean getIsMandatory() {
		return isMandatory;
	}
	public void setIsMandatory(boolean isMandatory) {
		this.isMandatory = isMandatory;
	}
	public Integer getLocationId() {
		return locationId;
	}
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}
    
	
	
    
}
