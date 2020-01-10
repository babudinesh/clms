package com.Ntranga.CLMS.vo;

import java.util.Date;
import java.util.List;

public class LWFRulesVo {

	private Integer customerId;
	private Integer companyId;
	private Integer countryId;
	private Integer stateId;
	private Date transactionDate;
	private String status;
	private String deductionFrequency;
	private Integer lwfRulesId;
	private Integer lwfSequenceId;
	private Integer lwfUniqueId;
	private Integer lwfRulesInfoId;
	private Integer createdBy;
	private Integer modifiedBy;
	
	private String customerName;
	private String companyName;
	private String countryName;
	private String stateName;
	
	private List<LWFRulesInfoVo> lwfRulesList;

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

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDeductionFrequency() {
		return deductionFrequency;
	}

	public void setDeductionFrequency(String deductionFrequency) {
		this.deductionFrequency = deductionFrequency;
	}

	public Integer getLwfRulesId() {
		return lwfRulesId;
	}

	public void setLwfRulesId(Integer lwfRulesId) {
		this.lwfRulesId = lwfRulesId;
	}

	public Integer getLwfSequenceId() {
		return lwfSequenceId;
	}

	public void setLwfSequenceId(Integer lwfSequenceId) {
		this.lwfSequenceId = lwfSequenceId;
	}

	public Integer getLwfUniqueId() {
		return lwfUniqueId;
	}

	public void setLwfUniqueId(Integer lwfUniqueId) {
		this.lwfUniqueId = lwfUniqueId;
	}

	public Integer getLwfRulesInfoId() {
		return lwfRulesInfoId;
	}

	public void setLwfRulesInfoId(Integer lwfRulesInfoId) {
		this.lwfRulesInfoId = lwfRulesInfoId;
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

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public List<LWFRulesInfoVo> getLwfRulesList() {
		return lwfRulesList;
	}

	public void setLwfRulesList(List<LWFRulesInfoVo> lwfRulesList) {
		this.lwfRulesList = lwfRulesList;
	}
	
	
	
	
}
