package com.Ntranga.CLMS.vo;

import java.util.Date;
import java.util.List;

public class DefineProfessionalTaxVo {
	private Integer customerId;
	private Integer companyId;
	private Integer stateId;
	private Integer locationId;
	private Integer countryId;
	private String city;
	private Date transactionDate;
	private String status;
	private boolean defineByStateAndCity;
	private boolean defineByState;
	private String frequency;
	private Integer professionalTaxInfoId;
	private Integer professionalTaxId;
	private Integer taxSequenceId;
	private Integer taxUniqueId;
	private Integer createdBy;
	private Integer modifiedBy;
	private Date createdDate;
	private Date modifiedDate;
	
	private String customerName;
	private String companyName;
	private String countryName;
	private String locationName;
	private String stateName;
	
	private List<DefineProfessionalTaxInfoVo> taxRulesList;

	
	
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

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public boolean getDefineByStateAndCity() {
		return defineByStateAndCity;
	}

	public void setDefineByStateAndCity(boolean defineByStateAndCity) {
		this.defineByStateAndCity = defineByStateAndCity;
	}

	public boolean getDefineByState() {
		return defineByState;
	}

	public void setDefineByState(boolean defineByState) {
		this.defineByState = defineByState;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	

	public Integer getProfessionalTaxInfoId() {
		return professionalTaxInfoId;
	}

	public void setProfessionalTaxInfoId(Integer professionalTaxInfoId) {
		this.professionalTaxInfoId = professionalTaxInfoId;
	}

	public Integer getProfessionalTaxId() {
		return professionalTaxId;
	}

	public void setProfessionalTaxId(Integer professionalTaxId) {
		this.professionalTaxId = professionalTaxId;
	}

	public Integer getTaxSequenceId() {
		return taxSequenceId;
	}

	public void setTaxSequenceId(Integer taxSequenceId) {
		this.taxSequenceId = taxSequenceId;
	}

	public Integer getTaxUniqueId() {
		return taxUniqueId;
	}

	public void setTaxUniqueId(Integer taxUniqueId) {
		this.taxUniqueId = taxUniqueId;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
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

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public List<DefineProfessionalTaxInfoVo> getTaxRulesList() {
		return taxRulesList;
	}

	public void setTaxRulesList(List<DefineProfessionalTaxInfoVo> taxRulesList) {
		this.taxRulesList = taxRulesList;
	}

	@Override
	public String toString() {
		return "DefineProfessionalTaxVo [customerId=" + customerId
				+ ", companyId=" + companyId + ", stateId=" + stateId
				+ ", locationId=" + locationId + ", countryId=" + countryId
				+ ", city=" + city + ", transactionDate=" + transactionDate
				+ ", status=" + status + ", defineByStateAndCity="
				+ defineByStateAndCity + ", defineByState=" + defineByState
				+ ", frequency=" + frequency + ", professionalTaxInfoId="
				+ professionalTaxInfoId + ", professionalTaxId="
				+ professionalTaxId + ", taxSequenceId=" + taxSequenceId
				+ ", taxUniqueId=" + taxUniqueId + ", createdBy=" + createdBy
				+ ", modifiedBy=" + modifiedBy + ", createdDate=" + createdDate
				+ ", modifiedDate=" + modifiedDate + ", customerName="
				+ customerName + ", companyName=" + companyName
				+ ", countryName=" + countryName + ", locationName="
				+ locationName + ", stateName=" + stateName + ", taxRulesList="
				+ taxRulesList + "]";
	}
	
	
	
}
