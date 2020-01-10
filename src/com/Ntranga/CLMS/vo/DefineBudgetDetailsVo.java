package com.Ntranga.CLMS.vo;

import java.util.Date;
import java.util.List;

public class DefineBudgetDetailsVo {

	 private Integer budgetDetailsId;
     private Integer customerId;   
     private Integer companyId;
     private Integer countryId;
     private Integer budgetSequenceId;
     private Integer budgetId;
     private String budgetCode;
     private Date transactionDate;
     private String budgetStatus;
     private String approvalStatus;
     private String budgetName;
     private Integer locationId;
     private Integer departmentId;
     private boolean hasCalendarYear;
     private boolean hasFinancialYear;
     private String considerYear;
     private String budgetYear;  
     private String budgetType;   
     private String budgetFrequency;   
     private boolean hasBonus;
     private boolean hasRegularWages;
     private boolean hasOvertimeWages;
     private boolean hasLoans;
     private boolean hasAdditionalAllowance;
     private boolean hasOther;
     
     
     private String customerName;
     private String companyName;
     private String countryName;
     private String locationName;
     private String departmentName;
     private int createdBy;
     private int modifiedBy;
     
     private List<DefineBudgetBasedOnSkillVo> budgetSkillsList;
     
	public Integer getBudgetDetailsId() {
		return budgetDetailsId;
	}
	public void setBudgetDetailsId(Integer budgetDetailsId) {
		this.budgetDetailsId = budgetDetailsId;
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
	
	public Integer getCountryId() {
		return countryId;
	}
	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}
	
	public Integer getBudgetSequenceId() {
		return budgetSequenceId;
	}
	public void setBudgetSequenceId(Integer budgetSequenceId) {
		this.budgetSequenceId = budgetSequenceId;
	}
	
	public Integer getBudgetId() {
		return budgetId;
	}
	public void setBudgetId(Integer budgetId) {
		this.budgetId = budgetId;
	}
	
	public String getBudgetCode() {
		return budgetCode;
	}
	public void setBudgetCode(String budgetCode) {
		this.budgetCode = budgetCode;
	}
	
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	
	public String getBudgetStatus() {
		return budgetStatus;
	}
	public void setBudgetStatus(String budgetStatus) {
		this.budgetStatus = budgetStatus;
	}
	
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	
	public String getBudgetName() {
		return budgetName;
	}
	public void setBudgetName(String budgetName) {
		this.budgetName = budgetName;
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
	
	public boolean getHasCalendarYear() {
		return hasCalendarYear;
	}
	public void setHasCalendarYear(boolean hasCalendarYear) {
		this.hasCalendarYear = hasCalendarYear;
	}
	
	public boolean getHasFinancialYear() {
		return hasFinancialYear;
	}
	public void setHasFinancialYear(boolean hasFinancialYear) {
		this.hasFinancialYear = hasFinancialYear;
	}
	
	public String getConsiderYear() {
		return considerYear;
	}
	public void setConsiderYear(String considerYear) {
		this.considerYear = considerYear;
	}
	public String getBudgetYear() {
		return budgetYear;
	}
	public void setBudgetYear(String budgetYear) {
		this.budgetYear = budgetYear;
	}
	
	public String getBudgetType() {
		return budgetType;
	}
	public void setBudgetType(String budgetType) {
		this.budgetType = budgetType;
	}
	
	public String getBudgetFrequency() {
		return budgetFrequency;
	}
	public void setBudgetFrequency(String budgetFrequency) {
		this.budgetFrequency = budgetFrequency;
	}
	
	public boolean getHasBonus() {
		return hasBonus;
	}
	public void setHasBonus(boolean hasBonus) {
		this.hasBonus = hasBonus;
	}
	
	public boolean getHasRegularWages() {
		return hasRegularWages;
	}
	public void setHasRegularWages(boolean hasRegularWages) {
		this.hasRegularWages = hasRegularWages;
	}
	
	public boolean getHasOvertimeWages() {
		return hasOvertimeWages;
	}
	public void setHasOvertimeWages(boolean hasOvertimeWages) {
		this.hasOvertimeWages = hasOvertimeWages;
	}
	
	public boolean getHasLoans() {
		return hasLoans;
	}
	public void setHasLoans(boolean hasLoans) {
		this.hasLoans = hasLoans;
	}
	
	public boolean getHasAdditionalAllowance() {
		return hasAdditionalAllowance;
	}
	public void setHasAdditionalAllowance(boolean hasAdditionalAllowance) {
		this.hasAdditionalAllowance = hasAdditionalAllowance;
	}
	
	public boolean getHasOther() {
		return hasOther;
	}
	public void setHasOther(boolean hasOther) {
		this.hasOther = hasOther;
	}
	
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	
	public int getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	public List<DefineBudgetBasedOnSkillVo> getBudgetSkillsList() {
		return budgetSkillsList;
	}
	
	public void setBudgetSkillsList(List<DefineBudgetBasedOnSkillVo> budgetSkillsList) {
		this.budgetSkillsList = budgetSkillsList;
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
	
	public String getDepartmentName() {
		return departmentName;
	}
	
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
	
	@Override
	public String toString() {
		return "DefineBudgetDetailsVo [budgetDetailsId=" + budgetDetailsId
				+ ", customerId=" + customerId + ", companyId=" + companyId
				+ ", countryId=" + countryId + ", budgetSequenceId="
				+ budgetSequenceId + ", budgetId=" + budgetId + ", budgetCode="
				+ budgetCode + ", transactionDate=" + transactionDate
				+ ", budgetStatus=" + budgetStatus + ", approvalStatus="
				+ approvalStatus + ", budgetName=" + budgetName
				+ ", locationId=" + locationId + ", departmentId="
				+ departmentId + ", hasCalendarYear=" + hasCalendarYear
				+ ", hasFinancialYear=" + hasFinancialYear + ", considerYear="
				+ considerYear + ", budgetYear=" + budgetYear + ", budgetType="
				+ budgetType + ", budgetFrequency=" + budgetFrequency
				+ ", hasBonus=" + hasBonus + ", hasRegularWages="
				+ hasRegularWages + ", hasOvertimeWages=" + hasOvertimeWages
				+ ", hasLoans=" + hasLoans + ", hasAdditionalAllowance="
				+ hasAdditionalAllowance + ", hasOther=" + hasOther
				+ ", customerName=" + customerName + ", companyName="
				+ companyName + ", countryName=" + countryName
				+ ", locationName=" + locationName + ", departmentName="
				+ departmentName + ", createdBy=" + createdBy + ", modifiedBy="
				+ modifiedBy + ", budgetSkillsList=" + budgetSkillsList + "]";
	}
	
	
     
}
