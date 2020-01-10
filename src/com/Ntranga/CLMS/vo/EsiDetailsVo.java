package com.Ntranga.CLMS.vo;
// Generated 16 Jul, 2016 7:27:01 PM by Hibernate Tools 3.6.0

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



@SuppressWarnings("serial")
public class EsiDetailsVo implements java.io.Serializable {

	 private Integer esiId;
     private int customerId;
     private int companyId;
     private int countryId;
     private Date transactionDate;
     private int sequenceId;
     private int uniqueId;
     private String isActive;
     private String earningSalaryLimit;
     private String employeeContribution;
     private String employerContribution;
     private Integer createdBy;
     private Date createdDate;
     private Integer modifiedBy;
     private Date modifiedDate;
     private List<EsiSlabDetailsVo> esiSlabDetailsList =  new ArrayList();
     
     
	public Integer getEsiId() {
		return esiId;
	}
	public void setEsiId(Integer esiId) {
		this.esiId = esiId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public int getCountryId() {
		return countryId;
	}
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public int getSequenceId() {
		return sequenceId;
	}
	public void setSequenceId(int sequenceId) {
		this.sequenceId = sequenceId;
	}
	public int getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getEarningSalaryLimit() {
		return earningSalaryLimit;
	}
	public void setEarningSalaryLimit(String earningSalaryLimit) {
		this.earningSalaryLimit = earningSalaryLimit;
	}
	public String getEmployeeContribution() {
		return employeeContribution;
	}
	public void setEmployeeContribution(String employeeContribution) {
		this.employeeContribution = employeeContribution;
	}
	public String getEmployerContribution() {
		return employerContribution;
	}
	public void setEmployerContribution(String employerContribution) {
		this.employerContribution = employerContribution;
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
	public List<EsiSlabDetailsVo> getEsiSlabDetailsList() {
		return esiSlabDetailsList;
	}
	public void setEsiSlabDetailsList(List<EsiSlabDetailsVo> esiSlabDetailsList) {
		this.esiSlabDetailsList = esiSlabDetailsList;
	}
     
     
     



}
