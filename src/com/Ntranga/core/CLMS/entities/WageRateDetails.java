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
@Table(name="wage_rate_details")
public class WageRateDetails implements Serializable {

	private CustomerDetails customerDetails ;
	private CompanyDetails companyDetails ;
	private Integer wageRateId;
	private String wageRateCode;
	private Integer createdBy;
    private Date createdDate;
    private Integer modifiedBy;
    private Date modifiedDate;
    
    
	public WageRateDetails() {
	}
	
	public WageRateDetails(Integer wageRateId) {
		this.wageRateId = wageRateId;
	}

	
	public WageRateDetails(CustomerDetails customerDetails, CompanyDetails companyDetails, Integer wageRateId,
									String wageRateCode, Integer createdBy, Date createdDate, Integer modifiedBy, Date modifiedDate) {
		this.customerDetails = customerDetails;
		this.companyDetails = companyDetails;
		this.wageRateId = wageRateId;
		this.wageRateCode = wageRateCode;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}

	@Id 
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="Wage_Rate_Id", unique=true, nullable=false)
   public Integer getWageRateId() {
       return this.wageRateId;
   }
   
   public void setWageRateId(Integer wageRateId) {
       this.wageRateId = wageRateId;
   }
   
 @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Customer_Id", nullable=false )
	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}

@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Company_Id", nullable=false )
	public CompanyDetails getCompanyDetails() {
		return companyDetails;
	}

	public void setCompanyDetails(CompanyDetails companyDetails) {
		this.companyDetails = companyDetails;
	}
   
   @Column(name="Wage_Rate_Code", length=20, nullable=false)
   public String getWageRateCode() {
       return this.wageRateCode;
   }
   
   public void setWageRateCode(String wageRateCode) {
       this.wageRateCode = wageRateCode;
   }
   
   @Column(name="Created_By", nullable=false)
   public Integer getCreatedBy() {
       return this.createdBy;
   }
   
   public void setCreatedBy(Integer createdBy) {
       this.createdBy = createdBy;
   }

   @Temporal(TemporalType.TIMESTAMP)
   @Column(name="Created_Date", length=19, nullable=false)
   public Date getCreatedDate() {
       return this.createdDate;
   }
   
   public void setCreatedDate(Date createdDate) {
       this.createdDate = createdDate;
   }

   
   @Column(name="Modified_By", nullable=false)
   public Integer getModifiedBy() {
       return this.modifiedBy;
   }
   
   public void setModifiedBy(Integer modifiedBy) {
       this.modifiedBy = modifiedBy;
   }

   @Temporal(TemporalType.TIMESTAMP)
   @Column(name="Modified_Date", length=19,nullable=false)
   public Date getModifiedDate() {
       return this.modifiedDate;
   }
   
   public void setModifiedDate(Date modifiedDate) {
       this.modifiedDate = modifiedDate;
   }

@Override
public String toString() {
	return "WageRateDetails [customerDetails=" + customerDetails.getCustomerId()
			+ ", companyDetails=" + companyDetails.getCompanyId() + ", wageRateId="
			+ wageRateId + ", wageRateCode=" + wageRateCode + ", createdBy="
			+ createdBy + ", createdDate=" + createdDate + ", modifiedBy="
			+ modifiedBy + ", modifiedDate=" + modifiedDate + "]";
}
   
   
}
