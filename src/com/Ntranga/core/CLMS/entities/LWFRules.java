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
@Table(name="lwf_rules")
public class LWFRules implements Serializable{

	private CustomerDetails customerDetails;
	private CompanyDetails companyDetails;
	private MCountry countryDetails;
	private MState stateDetails;
	private Date transactionDate;
	private String status;
	private String deductionFrequency;
	/*private String lwfMonth;
	private Double  salaryFrom;
	private Double salaryTo;
	private Float employeeShare;
	private Float employerShare;*/
	
	private Integer lwfRulesId;
	private Integer lwfSequenceId;
	private Integer lwfUniqueId;
	private Integer createdBy;
	private Date createdDate;
	private Integer modifiedBy;
	private Date modifiedDate;
	
	
	
	/* no-arg constructor */
	public LWFRules() {

	}

	/* single-arg Constructor*/
	public LWFRules(Integer lwfRulesId) {
		this.lwfRulesId = lwfRulesId;
	}

	public LWFRules(CustomerDetails customerDetails, CompanyDetails companyDetails, MCountry countryDetails, MState stateDetails, 
					Date transactionDate, String status, String deductionFrequency, Integer lwfRulesId, Integer lwfSequenceId,
					Integer lwfUniqueId, Integer createdBy, Date createdDate, Integer modifiedBy, Date modifiedDate) {
		this.customerDetails = customerDetails;
		this.companyDetails = companyDetails;
		this.countryDetails = countryDetails;
		this.stateDetails = stateDetails;
		this.transactionDate = transactionDate;
		this.status = status;
		this.deductionFrequency = deductionFrequency;
		this.lwfRulesId = lwfRulesId;
		this.lwfSequenceId = lwfSequenceId;
		this.lwfUniqueId = lwfUniqueId;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}

	
	/** Getters and Setters **/
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	@Column(name = "LWF_Rules_Id", nullable = false)
	public Integer getLwfRulesId() {
		return lwfRulesId;
	}

	public void setLwfRulesId(Integer lwfRulesId) {
		this.lwfRulesId = lwfRulesId;
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="Country_Id", nullable = false)
	public MCountry getCountryDetails() {
		return countryDetails;
	}
	
	public void setCountryDetails(MCountry countryDetails) {
		this.countryDetails = countryDetails;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="State_Id", nullable = false)
	public MState getStateDetails() {
		return stateDetails;
	}
	
	public void setStateDetails(MState stateDetails) {
		this.stateDetails = stateDetails;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="Transaction_Date", nullable = false)
	public Date getTransactionDate() {
		return transactionDate;
	}
	
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	
	@Column(name="Status", length = 2, nullable = false)
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
	}
	
/*	@Column(name = "LWF_Month", length=10 )
	public String getLwfMonth() {
		return lwfMonth;
	}
	
	public void setLwfMonth(String lwfMonth) {
		this.lwfMonth = lwfMonth;
	}
	
	@Column(name = "Salary_From")
	public Double getSalaryFrom() {
		return salaryFrom;
	}
	
	public void setSalaryFrom(Double salaryFrom) {
		this.salaryFrom = salaryFrom;
	}
	
	@Column(name = "Salary_To")
	public Double getSalaryTo() {
		return salaryTo;
	}
	
	public void setSalaryTo(Double salaryTo) {
		this.salaryTo = salaryTo;
	}
	
	@Column(name = "Employee_Share")
	public Float getEmployeeShare() {
		return employeeShare;
	}
	
	public void setEmployeeShare(Float employeeShare) {
		this.employeeShare = employeeShare;
	}
	
	@Column(name = "Employer_Share")
	public Float getEmployerShare() {
		return employerShare;
	}
	
	public void setEmployerShare(Float employerShare) {
		this.employerShare = employerShare;
	}*/
	
	@Column(name = "Created_By")
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Created_Date")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "Modified_By")
	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Modified_Date")
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

}
