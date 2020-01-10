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
@Table(name="shift_pattern_details")
public class ShiftPatternDetails implements Serializable {

	private CustomerDetails customerDetails;
	private CompanyDetails  companyDetails;
	private MCountry countryDetails;
	private Integer shiftPatternId;
	private String shiftPatternCode;	
	private Integer createdBy;
	private Date createdDate;
	private Integer modifiedBy;
	private Date modifiedDate;
	
	public ShiftPatternDetails() {
	}
	
	public ShiftPatternDetails(Integer shiftPatternId) {
		this.shiftPatternId = shiftPatternId;
	}

	public ShiftPatternDetails(CustomerDetails customerDetails, CompanyDetails companyDetails, MCountry countryDetails,
												Integer shiftPatternId, String shiftPatternCode,Integer createdBy, 
												Date createdDate, Integer modifiedBy, Date modifiedDate) {
		this.customerDetails = customerDetails;
		this.companyDetails = companyDetails;
		this.countryDetails = countryDetails;
		this.shiftPatternId = shiftPatternId;
		this.shiftPatternCode = shiftPatternCode;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY )
	@Column(name="Shift_Pattern_Id")
	public Integer getShiftPatternId() {
		return shiftPatternId;
	}

	public void setShiftPatternId(Integer shiftPatternId) {
		this.shiftPatternId = shiftPatternId;
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

@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Country_Id" )
	public MCountry getCountryDetails() {
		return countryDetails;
	}

	public void setCountryDetails(MCountry countryDetails) {
		this.countryDetails = countryDetails;
	}
	
	@Column(name="Shift_Pattern_Code")
	public String getShiftPatternCode() {
		return shiftPatternCode;
	}

	public void setShiftPatternCode(String shiftPatternCode) {
		this.shiftPatternCode = shiftPatternCode;
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

	@Override
	public String toString() {
		return "ShiftPatternDetails [customerDetails=" + customerDetails.getCustomerId()
				+ ", companyDetails=" + companyDetails.getCompanyId() + ", countryDetails="
				+ countryDetails.getCountryId() + ", shiftPatternId=" + shiftPatternId
				+ ", shiftPatternCode=" + shiftPatternCode + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + ", modifiedBy="
				+ modifiedBy + ", modifiedDate=" + modifiedDate + "]";
	}


}
