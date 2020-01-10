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

@Entity
@Table(name="Section_Details")
public class SectionDetails implements Serializable {

	private CustomerDetails customerDetails ;
	private CompanyDetails companyDetails ;
	private Integer sectionId;
	private String sectionCode;
	private Integer createdBy;
    private Date createdDate;
    private Integer modifiedBy;
    private Date modifiedDate;
    

    public SectionDetails() {
	}
	
	public SectionDetails(Integer sectionId) {
		this.sectionId = sectionId;
	}

	public SectionDetails(CustomerDetails customerDetails,CompanyDetails companyDetails, Integer sectionId, String sectionCode,
										Integer createdBy, Date createdDate, Integer modifiedBy, Date modifiedDate) {
		this.customerDetails = customerDetails;
		this.companyDetails = companyDetails;
		this.sectionId = sectionId;
		this.sectionCode = sectionCode;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}

	@Id 
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="Section_Id", unique=true, nullable=false)
	public Integer getSectionId() {
		return sectionId;
	}

	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}

	
  @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Customer_Id" )
	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}

@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Company_Id" )
	public CompanyDetails getCompanyDetails() {
		return companyDetails;
	}

	public void setCompanyDetails(CompanyDetails companyDetails) {
		this.companyDetails = companyDetails;
	}   
	
   @Column(name="Section_Code", length=45)   
   public String getSectionCode() {
	return sectionCode;
	}
	
	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}
   
   @Column(name="Created_By")
   public Integer getCreatedBy() {
       return this.createdBy;
   }


public void setCreatedBy(Integer createdBy) {
       this.createdBy = createdBy;
   }

   @Temporal(TemporalType.TIMESTAMP)
   @Column(name="Created_Date", length=19)
   public Date getCreatedDate() {
       return this.createdDate;
   }
   
   public void setCreatedDate(Date createdDate) {
       this.createdDate = createdDate;
   }

   
   @Column(name="Modified_By")
   public Integer getModifiedBy() {
       return this.modifiedBy;
   }
   
   public void setModifiedBy(Integer modifiedBy) {
       this.modifiedBy = modifiedBy;
   }

   @Temporal(TemporalType.TIMESTAMP)
   @Column(name="Modified_Date", length=19)
   public Date getModifiedDate() {
       return this.modifiedDate;
   }
   
   public void setModifiedDate(Date modifiedDate) {
       this.modifiedDate = modifiedDate;
   }
	
	
}
