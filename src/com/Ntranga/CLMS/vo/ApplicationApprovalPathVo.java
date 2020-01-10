package com.Ntranga.CLMS.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ApplicationApprovalPathVo implements Serializable {

	private Integer applicationApprovalPathId;
	private String transactionCode;
	private String transactionName;
	private String moduleCode;
	private String moduleName;
	private String pathCode;
	private String pathName;
	private Integer customerId;
	private Integer companyId;
	private Integer approvalPathModuleId;
	private Integer approvalPathTransactionId;
	private String noApprovalRequired;
	private Integer noOfApprovalLevels;
	private String isActive;
	private Integer createdBy;
	private String createdDate;
	private Integer modifiedBy;
	private String modifiedDate;
	private List<ApplicationApprovalPathFlowVo> flowList;
	
	
	public ApplicationApprovalPathVo() {
		flowList = new ArrayList<ApplicationApprovalPathFlowVo>();
	}
	public Integer getApplicationApprovalPathId() {
		return applicationApprovalPathId;
	}
	public void setApplicationApprovalPathId(Integer applicationApprovalPathId) {
		this.applicationApprovalPathId = applicationApprovalPathId;
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
	public String getPathCode() {
		return pathCode;
	}
	public void setPathCode(String pathCode) {
		this.pathCode = pathCode;
	}
	public String getPathName() {
		return pathName;
	}
	public void setPathName(String pathName) {
		this.pathName = pathName;
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
	public Integer getApprovalPathModuleId() {
		return approvalPathModuleId;
	}
	public void setApprovalPathModuleId(Integer approvalPathModuleId) {
		this.approvalPathModuleId = approvalPathModuleId;
	}
	public Integer getApprovalPathTransactionId() {
		return approvalPathTransactionId;
	}
	public void setApprovalPathTransactionId(Integer approvalPathTransactionId) {
		this.approvalPathTransactionId = approvalPathTransactionId;
	}
	public String getNoApprovalRequired() {
		return noApprovalRequired;
	}
	public void setNoApprovalRequired(String noApprovalRequired) {
		this.noApprovalRequired = noApprovalRequired;
	}
	public Integer getNoOfApprovalLevels() {
		return noOfApprovalLevels;
	}
	public void setNoOfApprovalLevels(Integer noOfApprovalLevels) {
		this.noOfApprovalLevels = noOfApprovalLevels;
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
	public List<ApplicationApprovalPathFlowVo> getFlowList() {
		return flowList;
	}
	public void setFlowList(List<ApplicationApprovalPathFlowVo> flowList) {
		this.flowList = flowList;
	}
	
	
}
