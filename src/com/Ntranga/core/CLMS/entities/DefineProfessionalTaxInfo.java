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
@Table(name="define_professional_tax_info")
public class DefineProfessionalTaxInfo implements Serializable {

	private CustomerDetails customerDetails;
	private CompanyDetails companyDetails;
	private String slabFrom;
	private String slabTo;
	private String amount;
	private String PTAmount;
	private Integer professionalTaxInfoId;
	private DefineProfessionalTax taxDetails;
	private Integer taxSequenceId;
	private Integer taxUniqueId;
	private String PTMonthBasedOnFrequency;
	private Integer createdBy;
	private Integer modifiedBy;
	private Date createdDate;
	private Date modifiedDate;
	
	
	
	public DefineProfessionalTaxInfo() {
		
	}

	

	public DefineProfessionalTaxInfo(CustomerDetails customerDetails,
			CompanyDetails companyDetails, String slabFrom, String slabTo,
			String amount, String pTAmount, Integer professionalTaxInfoId,
			Integer taxSequenceId, Integer taxUniqueId,
			String PTMonthBasedOnFrequency, Integer createdBy,
			Integer modifiedBy, Date createdDate, Date modifiedDate, DefineProfessionalTax taxDetails) {
		this.customerDetails = customerDetails;
		this.companyDetails = companyDetails;
		this.slabFrom = slabFrom;
		this.slabTo = slabTo;
		this.amount = amount;
		this.PTAmount = pTAmount;
		this.professionalTaxInfoId = professionalTaxInfoId;
		this.taxSequenceId = taxSequenceId;
		this.taxUniqueId = taxUniqueId;
		this.PTMonthBasedOnFrequency = PTMonthBasedOnFrequency;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.taxDetails = taxDetails;
	}

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	@Column(name = "Professional_Tax_Info_Id", nullable = false, unique = true)
	public Integer getProfessionalTaxInfoId() {
		return professionalTaxInfoId;
	}

	public void setProfessionalTaxInfoId(Integer professionalTaxInfoId) {
		this.professionalTaxInfoId = professionalTaxInfoId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="Professional_Tax_Id", nullable = false)
	public DefineProfessionalTax getTaxDetails() {
		return taxDetails;
	}

	public void setTaxDetails(DefineProfessionalTax taxDetails) {
		this.taxDetails = taxDetails;
	}
	
	@Column(name = "Tax_Sequence_Id", nullable = false)
	public Integer getTaxSequenceId() {
		return taxSequenceId;
	}

	public void setTaxSequenceId(Integer taxSequenceId) {
		this.taxSequenceId = taxSequenceId;
	}
	
	@Column(name = "Tax_Unique_Id", nullable = false)
	public Integer getTaxUniqueId() {
		return taxUniqueId;
	}

	public void setTaxUniqueId(Integer taxUniqueId) {
		this.taxUniqueId = taxUniqueId;
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

	@Column(name = "Slab_From", length=15)
	public String getSlabFrom() {
		return slabFrom;
	}

	public void setSlabFrom(String slabFrom) {
		this.slabFrom = slabFrom;
	}

	@Column(name = "Slab_To", length =15)
	public String getSlabTo() {
		return slabTo;
	}

	public void setSlabTo(String slabTo) {
		this.slabTo = slabTo;
	}

	@Column(name = "Amount", length =15)
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	@Column(name = "PT_Amount", length =15)
	public String getPTAmount() {
		return PTAmount;
	}

	public void setPTAmount(String pTAmount) {
		this.PTAmount = pTAmount;
	}
	
	@Column(name = "PT_Month_Based_On_Frequency", length =15)
	public String getPTMonthBasedOnFrequency() {
		return PTMonthBasedOnFrequency;
	}

	public void setPTMonthBasedOnFrequency(String pTMonthBasedOnFrequency) {
		this.PTMonthBasedOnFrequency = pTMonthBasedOnFrequency;
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
	
	
	
	
}
