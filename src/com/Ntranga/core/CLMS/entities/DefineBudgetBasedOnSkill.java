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
@Table(name="define_budget_based_on_skill")
public class DefineBudgetBasedOnSkill implements Serializable {

	 private Integer budgetSkillId;
     private CustomerDetails customerDetails;   
     private CompanyDetails companyDetails;
     private MCountry countryDetails;
     private DefineBudgetDetails defineBudgetDetails;
     private DefineBudgetDetailsInfo defineBudgetDetailsInfo;
     private Integer budgetSequenceId;
     private String skillType;
     private String amount;
     private Integer headCount;
     private Integer currencyId;
     
     private int createdBy;
     private Date createdDate;
     private int modifiedBy;
     private Date modifiedDate;
     
     
     
     
     
    public DefineBudgetBasedOnSkill() {
		
	}
    
    public DefineBudgetBasedOnSkill(Integer budgetSkillId, CustomerDetails customerDetails, CompanyDetails companyDetails,
			MCountry countryDetails, DefineBudgetDetails defineBudgetDetails,DefineBudgetDetailsInfo defineBudgetDetailsInfo, 
			String skillType, String amount, Integer headCount, Integer currencyId, Integer budgetSequenceId,
			int createdBy, Date createdDate, int modifiedBy, Date modifiedDate) {
		this.budgetSkillId = budgetSkillId;
		this.customerDetails = customerDetails;
		this.companyDetails = companyDetails;
		this.countryDetails = countryDetails;
		this.defineBudgetDetails = defineBudgetDetails;
		this.defineBudgetDetailsInfo = defineBudgetDetailsInfo;
		this.budgetSequenceId = budgetSequenceId;
		this.skillType = skillType;
		this.amount = amount;
		this.headCount = headCount;
		this.currencyId = currencyId;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}
    

	@Id 
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="Budget_Skill_Id", unique=true, nullable=false)
	public Integer getBudgetSkillId() {
		return budgetSkillId;
	}
	
	public void setBudgetSkillId(Integer budgetSkillId) {
		this.budgetSkillId = budgetSkillId;
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
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Budget_Id", nullable=false)
	public DefineBudgetDetails getDefineBudgetDetails() {
		return defineBudgetDetails;
	}

	public void setDefineBudgetDetails(DefineBudgetDetails defineBudgetDetails) {
		this.defineBudgetDetails = defineBudgetDetails;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Budget_Details_Id", nullable=false)
	public DefineBudgetDetailsInfo getDefineBudgetDetailsInfo() {
		return defineBudgetDetailsInfo;
	}
	
	public void setDefineBudgetDetailsInfo(DefineBudgetDetailsInfo defineBudgetDetailsInfo) {
		this.defineBudgetDetailsInfo = defineBudgetDetailsInfo;
	}
	
	 @Column(name="Budget_Sequence_Id", nullable=false)
	public Integer getBudgetSequenceId() {
		return budgetSequenceId;
	}

	public void setBudgetSequenceId(Integer budgetSequenceId) {
		this.budgetSequenceId = budgetSequenceId;
	}

	@Column(name="Skill_Type", nullable=false, length = 20)
	public String getSkillType() {
		return skillType;
	}
	public void setSkillType(String skillType) {
		this.skillType = skillType;
	}
	
	@Column(name="Amount", length=15)
	public String getAmount() {
		return amount;
	}
	
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	@Column(name="Head_Count")
	public Integer getHeadCount() {
		return headCount;
	}
	
	public void setHeadCount(Integer headCount) {
		this.headCount = headCount;
	}
	
	@Column(name="Currency_Id")
	public Integer getCurrencyId() {
		return currencyId;
	}
	
	public void setCurrencyId(Integer currencyId) {
		this.currencyId = currencyId;
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
