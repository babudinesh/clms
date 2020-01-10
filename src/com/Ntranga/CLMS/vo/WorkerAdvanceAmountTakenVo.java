package com.Ntranga.CLMS.vo;
// Generated Aug 18, 2016 1:10:14 PM by Hibernate Tools 3.6.0

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkerAdvanceAmountTakenVo implements java.io.Serializable {

	private Integer workerAdvanceAmountTakenId;
	private Integer customerId;
	private Integer companyId;
	private Integer workerId;
	private Integer vendorId;
	private String isActive;
	private Integer createdBy;
	private Date createdDate;
	private Integer modifiedBy;
	private Date modifiedDate;
	private String amountTakenDate;
	private String advanceAmountTaken;
	private String currency;
	private String purpose;
	private Integer noOfInstallmentsAllowed;
	private String remarks;
	private String registerType;
	private String workerName;
	private String workerCode;
	private String vendorName;
	private String vendorCode;
	private Integer currencyId;
	private String currencyName;
	private Integer vendorDamageId;
 	
	private List<WorkerAdvanceInstallmentDetailsVo> advanceInstallmentsList = new ArrayList();
	
	

	public List<WorkerAdvanceInstallmentDetailsVo> getAdvanceInstallmentsList() {
		return advanceInstallmentsList;
	}
	public void setAdvanceInstallmentsList(List<WorkerAdvanceInstallmentDetailsVo> advanceInstallmentsList) {
		this.advanceInstallmentsList = advanceInstallmentsList;
	}
	public Integer getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(Integer currencyId) {
		this.currencyId = currencyId;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	private List<WorkerAdvanceInstallmentDetailsVo> advanceInstallments = new ArrayList();
	
	
	
	public List<WorkerAdvanceInstallmentDetailsVo> getAdvanceInstallments() {
		return advanceInstallments;
	}
	public void setAdvanceInstallments(List<WorkerAdvanceInstallmentDetailsVo> advanceInstallments) {
		this.advanceInstallments = advanceInstallments;
	}
	public Integer getWorkerAdvanceAmountTakenId() {
		return workerAdvanceAmountTakenId;
	}
	public void setWorkerAdvanceAmountTakenId(Integer workerAdvanceAmountTakenId) {
		this.workerAdvanceAmountTakenId = workerAdvanceAmountTakenId;
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
	public Integer getWorkerId() {
		return workerId;
	}
	public void setWorkerId(Integer workerId) {
		this.workerId = workerId;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
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
	public String getAmountTakenDate() {
		return amountTakenDate;
	}
	public void setAmountTakenDate(String obj) {
		this.amountTakenDate = obj;
	}
	public String getAdvanceAmountTaken() {
		return advanceAmountTaken;
	}
	public void setAdvanceAmountTaken(String advanceAmountTaken) {
		this.advanceAmountTaken = advanceAmountTaken;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public Integer getNoOfInstallmentsAllowed() {
		return noOfInstallmentsAllowed;
	}
	public void setNoOfInstallmentsAllowed(Integer noOfInstallmentsAllowed) {
		this.noOfInstallmentsAllowed = noOfInstallmentsAllowed;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRegisterType() {
		return registerType;
	}
	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}
	public String getWorkerName() {
		return workerName;
	}
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	public String getWorkerCode() {
		return workerCode;
	}
	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getVendorCode() {
		return vendorCode;
	}
	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}
	public Integer getVendorDamageId() {
		return vendorDamageId;
	}
	public void setVendorDamageId(Integer vendorDamageId) {
		this.vendorDamageId = vendorDamageId;
	}
	
	
	

	

}
