package com.Ntranga.CLMS.vo;

import java.io.Serializable;
import java.util.Date;

public class BonusRulesVo implements Serializable {

	
	private Integer bonusRulesId;
    private Integer customerDetailsId;
    private Integer companyDetailsId;
    private Integer countryId;
    private Date transactionDate;
    private Integer sequenceId;
    private String isActive;
    
    private Integer salaryLimit;
    private Integer bonusPercentage;    
    private Integer maxCalculationLimit;
    
    private Integer createdBy;    
    private Integer modifiedBy;
    
	public Integer getBonusRulesId() {
		return bonusRulesId;
	}
	public void setBonusRulesId(Integer bonusRulesId) {
		this.bonusRulesId = bonusRulesId;
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
	public Integer getCountryId() {
		return countryId;
	}
	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
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
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public Integer getSalaryLimit() {
		return salaryLimit;
	}
	public void setSalaryLimit(Integer salaryLimit) {
		this.salaryLimit = salaryLimit;
	}
	public Integer getBonusPercentage() {
		return bonusPercentage;
	}
	public void setBonusPercentage(Integer bonusPercentage) {
		this.bonusPercentage = bonusPercentage;
	}
	public Integer getMaxCalculationLimit() {
		return maxCalculationLimit;
	}
	public void setMaxCalculationLimit(Integer maxCalculationLimit) {
		this.maxCalculationLimit = maxCalculationLimit;
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
    
    
    
}
