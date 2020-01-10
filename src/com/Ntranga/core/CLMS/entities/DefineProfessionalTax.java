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



@Entity
@Table(name="define_professional_tax")
public class DefineProfessionalTax implements Serializable {

	private CustomerDetails customerDetails;
	private CompanyDetails companyDetails;
	private MCountry countryDetails;
	private LocationDetails locationDetails;
	private Date transactionDate;
	private String status;
	private String defineByStateAndCity;
	private String defineByState;
	private String frequency;
	private Integer stateId;
	private String city;
	private Integer professionalTaxId;
	private Integer taxSequenceId;
	private Integer taxUniqueId;
	private Integer createdBy;
	private Integer modifiedBy;
	private Date createdDate;
	private Date modifiedDate;
	
	
	
	public DefineProfessionalTax() {
		
	}
	
	

	public DefineProfessionalTax(Integer professionalTaxId) {
		this.professionalTaxId = professionalTaxId;
	}



	public DefineProfessionalTax(CustomerDetails customerDetails,CompanyDetails companyDetails, MCountry countryDetails,
			LocationDetails locationDetails, Date transactionDate, String status, String defineByStateAndCity, String defineByState,
			String frequency, Integer stateId, String city, Integer professionalTaxId, Integer taxSequenceId,
			Integer taxUniqueId, Integer createdBy, Integer modifiedBy, Date createdDate, Date modifiedDate) {
		this.customerDetails = customerDetails;
		this.companyDetails = companyDetails;
		this.countryDetails = countryDetails;
		this.locationDetails = locationDetails;
		this.transactionDate = transactionDate;
		this.status = status;
		this.defineByStateAndCity = defineByStateAndCity;
		this.defineByState = defineByState;
		this.frequency = frequency;
		this.stateId = stateId;
		this.city = city;
		this.professionalTaxId = professionalTaxId;
		this.taxSequenceId = taxSequenceId;
		this.taxUniqueId = taxUniqueId;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
	}

	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	@Column(name = "Professional_Tax_Id", unique = true, nullable = false)
	public Integer getProfessionalTaxId() {
		return professionalTaxId;
	}
	
	public void setProfessionalTaxId(Integer professionalTaxId) {
		this.professionalTaxId = professionalTaxId;
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

@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="Country_Id", nullable = false)
	public MCountry getCountryDetails() {
		return countryDetails;
	}

	public void setCountryDetails(MCountry countryDetails) {
		this.countryDetails = countryDetails;
	}

@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="Location_Id", nullable = false)
	public LocationDetails getLocationDetails() {
		return locationDetails;
	}

	public void setLocationDetails(LocationDetails locationDetails) {
		this.locationDetails = locationDetails;
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

	@Column(name="Define_By_State_And_City", length = 5)
	public String getDefineByStateAndCity() {
		return defineByStateAndCity;
	}

	public void setDefineByStateAndCity(String defineByStateAndCity) {
		this.defineByStateAndCity = defineByStateAndCity;
	}

	@Column(name="Define_By_State", length = 5)
	public String getDefineByState() {
		return defineByState;
	}

	public void setDefineByState(String defineByState) {
		this.defineByState = defineByState;
	}

	@Column(name="Frequency", length = 20)
	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	@Column(name="State_Id", length = 20)
	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	@Column(name="City", length = 45)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

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
