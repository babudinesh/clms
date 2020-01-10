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
@Table(name = "Vendor_Damage_Or_Loss_Details")
public class VendorDamageOrLossDetails implements Serializable {

	private Integer  vendorDamageId;
	private CustomerDetails customerDetails;
	private CompanyDetails companyDetails;
	private VendorDetails vendorDetails;
	private WorkerDetails workerDetails;
	private MCurrency currency;
	private Date dateOfDamage;
	private String particularsOfDamage;
	private String nameOfthePerson;
	private Integer numberOfInstallmentsAllowed;
	private String amountOfDeductionImposed;
	private String whetherWorkmanShowedCause;
	private String description;
	private String remarks;
	private Date createdDate;
	private Integer createdBy;
	private Date modifiedDate;
	private Integer modifiedBy;
	
	public VendorDamageOrLossDetails() {
	}

	public VendorDamageOrLossDetails(Integer vendorDamageId) {
		this.vendorDamageId = vendorDamageId;
	}

	public VendorDamageOrLossDetails(Integer vendorDamageId,CustomerDetails customerDetails, CompanyDetails companyDetails,
			VendorDetails vendorDetails, WorkerDetails workerDetails, Date dateOfDamage, String particularsOfDamage, MCurrency currency, 
			String nameOfthePerson, Integer numberOfInstallmentsAllowed, String amountOfDeductionImposed, String whetherWorkmanShowedCause,
			Date createdDate, Integer createdBy, Date modifiedDate, Integer modifiedBy, String description, String remarks) {
		this.vendorDamageId = vendorDamageId;
		this.customerDetails = customerDetails;
		this.companyDetails = companyDetails;
		this.vendorDetails = vendorDetails;
		this.workerDetails = workerDetails;
		this.dateOfDamage = dateOfDamage;
		this.particularsOfDamage = particularsOfDamage;
		this.nameOfthePerson = nameOfthePerson;
		this.numberOfInstallmentsAllowed = numberOfInstallmentsAllowed;
		this.amountOfDeductionImposed = amountOfDeductionImposed;
		this.whetherWorkmanShowedCause = whetherWorkmanShowedCause;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
		this.description = description;
		this.remarks = remarks;
		this.currency = currency;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Vendor_Damage_Id", nullable = false, unique = true )
	public Integer getVendorDamageId() {
		return vendorDamageId;
	}

	public void setVendorDamageId(Integer vendorDamageId) {
		this.vendorDamageId = vendorDamageId;
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

@Temporal(TemporalType.DATE)
	@Column(name="Date_Of_Damage")
	public Date getDateOfDamage() {
		return dateOfDamage;
	}

	public void setDateOfDamage(Date dateOfDamage) {
		this.dateOfDamage = dateOfDamage;
	}

	@Column(name="Particulars_Of_Damage", length = 45)
	public String getParticularsOfDamage() {
		return particularsOfDamage;
	}

	public void setParticularsOfDamage(String particularsOfDamage) {
		this.particularsOfDamage = particularsOfDamage;
	}

	@Column(name="Name_Of_the_Person", length = 45)
	public String getNameOfthePerson() {
		return nameOfthePerson;
	}

	public void setNameOfthePerson(String nameOfthePerson) {
		this.nameOfthePerson = nameOfthePerson;
	}

	@Column(name="No_Of_Installments_Allowed")
	public Integer getNumberOfInstallmentsAllowed() {
		return numberOfInstallmentsAllowed;
	}

	public void setNumberOfInstallmentsAllowed(Integer numberOfInstallmentsAllowed) {
		this.numberOfInstallmentsAllowed = numberOfInstallmentsAllowed;
	}

	@Column(name="Amount_Of_Deduction_Imposed", length=15)
	public String getAmountOfDeductionImposed() {
		return amountOfDeductionImposed;
	}

	public void setAmountOfDeductionImposed(String amountOfDeductionImposed) {
		this.amountOfDeductionImposed = amountOfDeductionImposed;
	}

	
	@Column(name="Whether_Workman_Showed_Cause_Against_Deduction", length=5)
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

	@Column(name="Description_For_Cause", length = 45)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="Remarks", length = 45)
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	@ManyToOne
	@JoinColumn(name="Currency_Id", nullable = false)
	public MCurrency getCurrency(){
		return currency;
	}
	
	public void setCurrency(MCurrency currency){
		this.currency = currency;
	}
	
	
	
}
