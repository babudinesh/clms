package com.Ntranga.core.CLMS.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name="lwf_rules_info")
public class LWFRulesInfo implements Serializable {

	private Integer lwfRulesInfoId;
	private Integer lwfSequenceId;
	private Integer lwfUniqueId;
	private LWFRules lwfRules;
	private CustomerDetails customerDetails;
	private CompanyDetails companyDetails;
	private String lwfMonth;
	private String  salaryFrom;
	private String salaryTo;
	private String employeeShare;
	private String employerShare;
	private Date transactionDate;
	//private String status;
	//private String deductionFrequency;
	private Integer createdBy;
	private Date createdDate;
	private Integer modifiedBy;
	private Date modifiedDate;
	
	
	
	
	public LWFRulesInfo() {
	
	}

	

	
	
	public LWFRulesInfo(Integer lwfRulesInfoId, Integer lwfSequenceId, Integer lwfUniqueId, LWFRules lwfRules, String lwfMonth,
			CustomerDetails customerDetails, CompanyDetails companyDetails, String salaryFrom, String salaryTo,
			String employeeShare, String employerShare, Date transactionDate, String status, String deductionFrequency, 
			Integer createdBy, Date createdDate, Integer modifiedBy, Date modifiedDate) {
		this.lwfRulesInfoId = lwfRulesInfoId;
		this.lwfSequenceId = lwfSequenceId;
		this.lwfUniqueId = lwfUniqueId;
		this.lwfRules = lwfRules;
		this.customerDetails = customerDetails;
		this.companyDetails = companyDetails;
		this.lwfMonth = lwfMonth;
		this.salaryFrom = salaryFrom;
		this.salaryTo = salaryTo;
		this.employeeShare = employeeShare;
		this.employerShare = employerShare;
		this.transactionDate = transactionDate;
		//this.status = status;
		//this.deductionFrequency = deductionFrequency;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}





	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	@Column(name = "LWF_Rules_Info_Id", nullable = false)
	public Integer getLwfRulesInfoId() {
		return lwfRulesInfoId;
	}

	public void setLwfRulesInfoId(Integer lwfRulesInfoId) {
		this.lwfRulesInfoId = lwfRulesInfoId;
	}

	@Column(name = "LWF_Sequence_Id", nullable = false)
	public Integer getLwfSequenceId() {
		return lwfSequenceId;
	}

	public void setLwfSequenceId(Integer lwfSequenceId) {
		this.lwfSequenceId = lwfSequenceId;
	}

	@Column(name = "LWF_Unique_Id", nullable = false)
	public Integer getLwfUniqueId() {
		return lwfUniqueId;
	}

	public void setLwfUniqueId(Integer lwfUniqueId) {
		this.lwfUniqueId = lwfUniqueId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="LWF_Rules_Id", nullable = false)
	public LWFRules getLwfRules() {
		return lwfRules;
	}
	
	public void setLwfRules(LWFRules lwfRules) {
		this.lwfRules = lwfRules;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="Customer_Id", nullable = false)
	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}
	
	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="Company_Id", nullable = false)
	public CompanyDetails getCompanyDetails() {
		return companyDetails;
	}
	
	public void setCompanyDetails(CompanyDetails companyDetails) {
		this.companyDetails = companyDetails;
	}
	
	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="Country_Id", nullable = false)
	public MCountry getCountryDetails() {
		return countryDetails;
	}
	
	public void setCountryDetails(MCountry countryDetails) {
		this.countryDetails = countryDetails;
	}*/
	@Column(name = "LWF_Month", length=10, nullable = false )
	public String getLwfMonth() {
		return lwfMonth;
	}
	
	public void setLwfMonth(String lwfMonth) {
		this.lwfMonth = lwfMonth;
	}
	
	@Column(name = "Salary_From", length =15, nullable = false)
	public String getSalaryFrom() {
		return salaryFrom;
	}
	
	public void setSalaryFrom(String salaryFrom) {
		this.salaryFrom = salaryFrom;
	}
	
	@Column(name = "Salary_To", length =15, nullable = false)
	public String getSalaryTo() {
		return salaryTo;
	}
	
	public void setSalaryTo(String salaryTo) {
		this.salaryTo = salaryTo;
	}
	
	@Column(name = "Employee_Contribution_Share", length =15, nullable = false)
	public String getEmployeeShare() {
		return employeeShare;
	}
	
	public void setEmployeeShare(String employeeShare) {
		this.employeeShare = employeeShare;
	}
	
	@Column(name = "Employer_Contribution_Share", length =15, nullable = false)
	public String getEmployerShare() {
		return employerShare;
	}
	
	public void setEmployerShare(String employerShare) {
		this.employerShare = employerShare;
	}
	
	@Column(name = "Created_By", nullable = false)
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Created_Date", nullable = false)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "Modified_By", nullable = false)
	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Modified_Date", nullable = false)
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="Transaction_Date", nullable = false)
	public Date getTransactionDate() {
		return transactionDate;
	}
	
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	
/*	@Column(name="Status", length = 2, nullable = false)
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "Deduction_Frequency",  length = 20)
	public String getDeductionFrequency() {
		return deductionFrequency;
	}
	
	public void setDeductionFrequency(String deductionFrequency) {
		this.deductionFrequency = deductionFrequency;
	}*/
}
