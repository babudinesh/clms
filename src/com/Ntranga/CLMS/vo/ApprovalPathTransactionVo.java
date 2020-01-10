package com.Ntranga.CLMS.vo;

import java.util.Date;

public class ApprovalPathTransactionVo implements java.io.Serializable {

	private Integer approvalPathTransactionInfoId;
	private Integer approvalPathTransactionId;
	private Integer approvalPathModuleId;
	private String transactionCode;
	private String transactionName;
	private String effectiveStringDate;
	private Date effectiveDate;
	private String moduleCode;
	private String moduleName;
	private Integer customerId;
	private Integer companyId;
	private String isActive;
	private Integer createdBy;
	private String createdDate;
	private Integer modifiedBy;
	private String modifiedDate;
	
	public Integer getApprovalPathTransactionInfoId() {
		return approvalPathTransactionInfoId;
	}
	public void setApprovalPathTransactionInfoId(Integer approvalPathTransactionInfoId) {
		this.approvalPathTransactionInfoId = approvalPathTransactionInfoId;
	}
	public Integer getApprovalPathTransactionId() {
		return approvalPathTransactionId;
	}
	public void setApprovalPathTransactionId(Integer approvalPathTransactionId) {
		this.approvalPathTransactionId = approvalPathTransactionId;
	}
	public Integer getApprovalPathModuleId() {
		return approvalPathModuleId;
	}
	public void setApprovalPathModuleId(Integer approvalPathModuleId) {
		this.approvalPathModuleId = approvalPathModuleId;
	}
	public String getTransactionCode() {
		return transactionCode;
	}
	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}
	public String getTransactionName() {
		return transactionName;
	}
	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}
	public String getEffectiveStringDate() {
		return effectiveStringDate;
	}
	public void setEffectiveStringDate(String effectiveStringDate) {
		this.effectiveStringDate = effectiveStringDate;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getModuleCode() {
		return moduleCode;
	}
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
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
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	
}
