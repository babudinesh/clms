package com.Ntranga.core.CLMS.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name="Define_Budget_Details_Info")
public class DefineBudgetDetailsInfo  implements Serializable {

	 private Integer budgetDetailsId;
     private CustomerDetails customerDetails;   
     private CompanyDetails companyDetails;
     private MCountry countryDetails;
     private Integer budgetSequenceId;
     private DefineBudgetDetails budgetId;
     private Date transactionDate;
     private String budgetStatus;
     private String approvalStatus;
     private String budgetName;
     private Integer locationId;
     private Integer departmentId;
     private String hasCalendarYear;
     private String hasFinancialYear;
     private String budgetYear;  
     private String budgetType;   
     private String budgetFrequency;   
     private String hasBonus;
     private String hasRegularWages;
     private String hasOvertimeWages;
     private String hasLoans;
     private String hasAdditionalAllowances;
     private String hasOthers;
     
     private int createdBy;
     private Date createdDate;
     private int modifiedBy;
     private Date modifiedDate;
      
     
	public DefineBudgetDetailsInfo() {
	}

	public DefineBudgetDetailsInfo(Integer budgetDetailsId) {
		this.budgetDetailsId = budgetDetailsId;
	}

	public DefineBudgetDetailsInfo(Integer budgetDetailsId,
			CustomerDetails customerDetails, CompanyDetails companyDetails,
			MCountry countryDetails, Integer budgetSequenceId,
			DefineBudgetDetails budgetId, Date transactionDate,
			String budgetStatus, String approvalStatus, String budgetName,
			Integer locationId, Integer departmentId, String hasCalendarYear,
			String hasFinancialYear, String budgetYear, String budgetType,
			String budgetFrequency, String hasBonus, String hasRegularWages,
			String hasOvertimeWages, String hasLoans,
			String hasAdditionalAllowances, String hasOthers, int createdBy,
			Date createdDate, int modifiedBy, Date modifiedDate) {
		this.budgetDetailsId = budgetDetailsId;
		this.customerDetails = customerDetails;
		this.companyDetails = companyDetails;
		this.countryDetails = countryDetails;
		this.budgetSequenceId = budgetSequenceId;
		this.budgetId = budgetId;
		this.transactionDate = transactionDate;
		this.budgetStatus = budgetStatus;
		this.approvalStatus = approvalStatus;
		this.budgetName = budgetName;
		this.locationId = locationId;
		this.departmentId = departmentId;
		this.hasCalendarYear = hasCalendarYear;
		this.hasFinancialYear = hasFinancialYear;
		this.budgetYear = budgetYear;
		this.budgetType = budgetType;
		this.budgetFrequency = budgetFrequency;
		this.hasBonus = hasBonus;
		this.hasRegularWages = hasRegularWages;
		this.hasOvertimeWages = hasOvertimeWages;
		this.hasLoans = hasLoans;
		this.hasAdditionalAllowances = hasAdditionalAllowances;
		this.hasOthers = hasOthers;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}

	@Id 
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="Budget_Details_Id", unique=true, nullable=false)
	public Integer getBudgetDetailsId() {
		return budgetDetailsId;
	}

	public void setBudgetDetailsId(Integer budgetDetailsId) {
		this.budgetDetailsId = budgetDetailsId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Customer_Id", nullable=false)
	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}
	
	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Company_Id", nullable=false)
	public CompanyDetails getCompanyDetails() {
		return companyDetails;
	}
	
	public void setCompanyDetails(CompanyDetails companyDetails) {
		this.companyDetails = companyDetails;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Country_Id", nullable=false)
	public MCountry getCountryDetails() {
		return countryDetails;
	}
	
	public void setCountryDetails(MCountry countryDetails) {
		this.countryDetails = countryDetails;
	}
	
	@Column(name="Budget_Sequence_Id", nullable=false, length=50)
	public Integer getBudgetSequenceId() {
		return budgetSequenceId;
	}

	public void setBudgetSequenceId(Integer budgetSequenceId) {
		this.budgetSequenceId = budgetSequenceId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Budget_Id", nullable=false)
	public DefineBudgetDetails getBudgetId() {
		return budgetId;
	}

	public void setBudgetId(DefineBudgetDetails budgetId) {
		this.budgetId = budgetId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="Transaction_Date",  nullable= false)
	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	@Column(name="Budget_Status", nullable=false, length=2)
	public String getBudgetStatus() {
		return budgetStatus;
	}

	public void setBudgetStatus(String budgetStatus) {
		this.budgetStatus = budgetStatus;
	}

	@Column(name="Approval_Status", nullable=false,  length=20)
	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	@Column(name="Budget_Name", nullable=false, length=50)
	public String getBudgetName() {
		return budgetName;
	}

	public void setBudgetName(String budgetName) {
		this.budgetName = budgetName;
	}

	@Column(name="Location_Id")
	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}
	
	@Column(name="Department_Id")
	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	@Column(name="Has_Calendar_Year", nullable=false, length=2)
	public String getHasCalendarYear() {
		return hasCalendarYear;
	}

	public void setHasCalendarYear(String hasCalendarYear) {
		this.hasCalendarYear = hasCalendarYear;
	}

	@Column(name="Has_Financial_Year", nullable=false, length=2)
	public String getHasFinancialYear() {
		return hasFinancialYear;
	}

	public void setHasFinancialYear(String hasFinancialYear) {
		this.hasFinancialYear = hasFinancialYear;
	}

	@Column(name="Budget_Year", nullable=false,  length=5)
	public String getBudgetYear() {
		return budgetYear;
	}

	public void setBudgetYear(String budgetYear) {
		this.budgetYear = budgetYear;
	}

	@Column(name="Budget_Type", nullable=false, length=20)
	public String getBudgetType() {
		return budgetType;
	}

	public void setBudgetType(String budgetType) {
		this.budgetType = budgetType;
	}

	@Column(name="Budget_Frequency", nullable=false, length=20)
	public String getBudgetFrequency() {
		return budgetFrequency;
	}

	public void setBudgetFrequency(String budgetFrequency) {
		this.budgetFrequency = budgetFrequency;
	}

	@Column(name="Has_Bonus", nullable=false, length=2)
	public String getHasBonus() {
		return hasBonus;
	}

	public void setHasBonus(String hasBonus) {
		this.hasBonus = hasBonus;
	}

	@Column(name="Has_Regular_Wages", nullable=false, length=2)
	public String getHasRegularWages() {
		return hasRegularWages;
	}

	public void setHasRegularWages(String hasRegularWages) {
		this.hasRegularWages = hasRegularWages;
	}

	@Column(name="Has_Overtime_Wages", nullable=false, length=2)
	public String getHasOvertimeWages() {
		return hasOvertimeWages;
	}

	public void setHasOvertimeWages(String hasOvertimeWages) {
		this.hasOvertimeWages = hasOvertimeWages;
	}

	@Column(name="Has_Loans", nullable=false, length=2)
	public String getHasLoans() {
		return hasLoans;
	}

	public void setHasLoans(String hasLoans) {
		this.hasLoans = hasLoans;
	}

	@Column(name="Has_Additional_Allowances", nullable=false, length=2)
	public String getHasAdditionalAllowances() {
		return hasAdditionalAllowances;
	}

	public void setHasAdditionalAllowances(String hasAdditionalAllowances) {
		this.hasAdditionalAllowances = hasAdditionalAllowances;
	}

	@Column(name="Has_Others", nullable=false, length=2)
	public String getHasOthers() {
		return hasOthers;
	}

	public void setHasOthers(String hasOthers) {
		this.hasOthers = hasOthers;
	}


	@Column(name="Created_By", nullable=false)
    public int getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="Created_Date", nullable=false, length=19)
    public Date getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name="Modified_By", nullable=false)
    public int getModifiedBy() {
        return modifiedBy;
    }
    
    public void setModifiedBy(int modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="Modified_Date", nullable=false, length=19)
    public Date getModifiedDate() {
        return modifiedDate;
    }
    
    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
 
     
}
