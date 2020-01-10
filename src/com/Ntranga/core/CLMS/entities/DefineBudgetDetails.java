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
@Table(name="Define_Budget_Details")
public class DefineBudgetDetails implements Serializable{

	 private Integer budgetId;
     private CustomerDetails customerDetails;   
     private CompanyDetails companyDetails;
     private MCountry countryDetails;
     private String budgetCode;
     
     private int createdBy;
     private Date createdDate;
     private int modifiedBy;
     private Date modifiedDate;
     
     
     public DefineBudgetDetails() {
	 }
     
	public DefineBudgetDetails(Integer budgetId) {
		this.budgetId = budgetId;
	}

	public DefineBudgetDetails(Integer budgetId, CustomerDetails customerDetails, CompanyDetails companyDetails,
			MCountry countryDetails, String budgetCode, int createdBy,Date createdDate, int modifiedBy, Date modifiedDate) {
		this.budgetId = budgetId;
		this.customerDetails = customerDetails;
		this.companyDetails = companyDetails;
		this.countryDetails = countryDetails;
		this.budgetCode = budgetCode;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}



	@Id 
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="Budget_Id", unique=true, nullable=false)
	public Integer getBudgetId() {
		return budgetId;
	}
	
	public void setBudgetId(Integer budgetId) {
		this.budgetId = budgetId;
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
	
	@Column(name="Budget_Code", length=20 , nullable=false)
	public String getBudgetCode() {
		return budgetCode;
	}
	
	public void setBudgetCode(String budgetCode) {
		this.budgetCode = budgetCode;
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
