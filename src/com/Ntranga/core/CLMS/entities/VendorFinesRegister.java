package com.Ntranga.core.CLMS.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="Vendor_Fines_Register")
public class VendorFinesRegister implements Serializable {

	private CustomerDetails customerDetails;
	private CompanyDetails companyDetails;
	private VendorDetails vendorDetails;
	private WorkerDetails workerDetails;
	private MCurrency currency;
	
	private Integer finesRegisterId;
	private Date dateOfOffence;
	private String actForWhichFineIsImposed;
	private String nameOfthePerson;
	private String amountOfFineImposed;
	private String whetherWorkmanShowedCause;
	private Date dateOnWhichFineRealised;
	private String remarks;
	private String description;
	
	private Date createdDate;
	private Integer createdBy;
	private Date modifiedDate;
	private Integer modifiedBy;
	
	
	
	public VendorFinesRegister() {

	}

	public VendorFinesRegister(Integer finesRegisterId) {
		this.finesRegisterId = finesRegisterId;
	}

	public VendorFinesRegister(CustomerDetails customerDetails, CompanyDetails companyDetails, VendorDetails vendorDetails,
			WorkerDetails workerDetails, MCurrency currency, Integer finesRegisterId, Date dateOfOffence, String actForWhichFineIsImposed, 
			String nameOfthePerson, String amountOfFineImposed, String whetherWorkmanShowedCause,Date dateOnWhichFineRealised,
			String remarks, Date createdDate, Integer createdBy, Date modifiedDate, Integer modifiedBy, String description) {
		this.customerDetails = customerDetails;
		this.companyDetails = companyDetails;
		this.vendorDetails = vendorDetails;
		this.workerDetails = workerDetails;
		this.currency = currency;
		this.finesRegisterId = finesRegisterId;
		this.dateOfOffence = dateOfOffence;
		this.actForWhichFineIsImposed = actForWhichFineIsImposed;
		this.nameOfthePerson = nameOfthePerson;
		this.amountOfFineImposed = amountOfFineImposed;
		this.whetherWorkmanShowedCause = whetherWorkmanShowedCause;
		this.dateOnWhichFineRealised = dateOnWhichFineRealised;
		this.remarks = remarks;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
		this.setDescription(description);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Fines_Register_Id", nullable = false, unique = true )
	public Integer getFinesRegisterId() {
		return finesRegisterId;
	}

	public void setFinesRegisterId(Integer finesRegisterId) {
		this.finesRegisterId = finesRegisterId;
	}

@ManyToOne
	@JoinColumn(name="Customer_Id")
	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}

@ManyToOne
	@JoinColumn(name="Company_Id")
	public CompanyDetails getCompanyDetails() {
		return companyDetails;
	}

	public void setCompanyDetails(CompanyDetails companyDetails) {
		this.companyDetails = companyDetails;
	}

@ManyToOne
	@JoinColumn(name="Vendor_Id")
	public VendorDetails getVendorDetails() {
		return vendorDetails;
	}

	public void setVendorDetails(VendorDetails vendorDetails) {
		this.vendorDetails = vendorDetails;
	}
	
@ManyToOne
	@JoinColumn(name="Worker_Id")
	public WorkerDetails getWorkerDetails() {
		return workerDetails;
	}

	public void setWorkerDetails(WorkerDetails workerDetails) {
		this.workerDetails = workerDetails;
	}

@ManyToOne
	@JoinColumn(name="Currency_Id", nullable = false)
	public MCurrency getCurrency(){
		return currency;
	}
	
	public void setCurrency(MCurrency currency){
		this.currency = currency;
	}
	
@Temporal(TemporalType.DATE)
	@Column(name="Date_Of_Offence")
	public Date getDateOfOffence() {
		return dateOfOffence;
	}

	public void setDateOfOffence(Date dateOfOffence) {
		this.dateOfOffence = dateOfOffence;
	}
	
@Temporal(TemporalType.DATE)
	@Column(name="Date_On_Which_Fine_Realised")
	public Date getDateOnWhichFineRealised() {
		return dateOnWhichFineRealised;
	}

	public void setDateOnWhichFineRealised(Date dateOnWhichFineRealised) {
		this.dateOnWhichFineRealised = dateOnWhichFineRealised;
	}

	@Column(name="Act_For_Which_Fine_Is_Imposed", length = 45)
	public String getActForWhichFineIsImposed() {
		return actForWhichFineIsImposed;
	}

	public void setActForWhichFineIsImposed(String actForWhichFineIsImposed) {
		this.actForWhichFineIsImposed = actForWhichFineIsImposed;
	}

	@Column(name="Name_Of_the_Person", length = 45)
	public String getNameOfthePerson() {
		return nameOfthePerson;
	}

	public void setNameOfthePerson(String nameOfthePerson) {
		this.nameOfthePerson = nameOfthePerson;
	}


	@Column(name="Amount_Of_Fine_Imposed", length=15)
	public String getAmountOfFineImposed() {
		return amountOfFineImposed;
	}

	public void setAmountOfFineImposed(String amountOfFineImposed) {
		this.amountOfFineImposed = amountOfFineImposed;
	}

	
	@Column(name="Whether_Workman_Showed_Cause_Against_Fine", length = 5)
	public String getWhetherWorkmanShowedCause() {
		return whetherWorkmanShowedCause;
	}

	public void setWhetherWorkmanShowedCause(String whetherWorkmanShowedCause) {
		this.whetherWorkmanShowedCause = whetherWorkmanShowedCause;
	}

@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Created_Date")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name="Created_By")
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	
@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Modified_Date")
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}


	@Column(name="Modified_By")
	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name="Remarks", length = 45)
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name="Description", length = 45)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
