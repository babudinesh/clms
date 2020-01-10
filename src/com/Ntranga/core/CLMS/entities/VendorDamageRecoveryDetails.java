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
@Table(name = "Vendor_Damage_Recovery_Details")
public class VendorDamageRecoveryDetails implements Serializable {
	
	private Integer damageRecoveryId;
	private VendorDamageOrLossDetails  vendorDamageDetails;
	private CustomerDetails customerDetails;
	private CompanyDetails companyDetails;
	private VendorDetails vendorDetails;
	private WorkerDetails workerDetails;
	private Date recoveredDate;
	private String recoveredAmount;
	private MCurrency currency;
	private Date createdDate;
	private Integer createdBy;
	private Date modifiedDate;
	private Integer modifiedBy;
	
	
	public VendorDamageRecoveryDetails() {
	}

	public VendorDamageRecoveryDetails( VendorDamageOrLossDetails vendorDamageDetails, CustomerDetails customerDetails,
			CompanyDetails companyDetails, VendorDetails vendorDetails, WorkerDetails workerDetails,
			Date recoveredDate, String recoveredAmount, Integer damageRecoveryId, MCurrency currency,
			Date createdDate, Integer createdBy, Date modifiedDate, Integer modifiedBy) {
		this.vendorDamageDetails = vendorDamageDetails;
		this.customerDetails = customerDetails;
		this.companyDetails = companyDetails;
		this.vendorDetails = vendorDetails;
		this.workerDetails = workerDetails;
		this.recoveredDate = recoveredDate;
		this.recoveredAmount = recoveredAmount;
		this.damageRecoveryId = damageRecoveryId;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
		this.currency = currency;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Damage_Recovery_Id", nullable = false, unique = true )
	public Integer getDamageRecoveryId() {
		return damageRecoveryId;
	}

	public void setDamageRecoveryId(Integer damageRecoveryId) {
		this.damageRecoveryId = damageRecoveryId;
	}

@ManyToOne
	@JoinColumn(name="Customer_Id", nullable = false)
	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}

@ManyToOne
	@JoinColumn(name="Company_Id", nullable = false)
	public CompanyDetails getCompanyDetails() {
		return companyDetails;
	}

	public void setCompanyDetails(CompanyDetails companyDetails) {
		this.companyDetails = companyDetails;
	}

@ManyToOne
	@JoinColumn(name="Vendor_Id", nullable = false)
	public VendorDetails getVendorDetails() {
		return vendorDetails;
	}

	public void setVendorDetails(VendorDetails vendorDetails) {
		this.vendorDetails = vendorDetails;
	}
	
@ManyToOne
	@JoinColumn(name="Worker_Id", nullable = false)
	public WorkerDetails getWorkerDetails() {
		return workerDetails;
	}

	public void setWorkerDetails(WorkerDetails workerDetails) {
		this.workerDetails = workerDetails;
	}
	
@ManyToOne
	@JoinColumn(name="Vendor_Damage_Id", nullable = false)
	public VendorDamageOrLossDetails getVendorDamageDetails() {
		return vendorDamageDetails;
	}

	public void setVendorDamageDetails(VendorDamageOrLossDetails vendorDamageDetails) {
		this.vendorDamageDetails = vendorDamageDetails;
	}

@Temporal(TemporalType.DATE)
	@Column(name="Recovered_Date", nullable = false)
	public Date getRecoveredDate() {
		return recoveredDate;
	}

	public void setRecoveredDate(Date recoveredDate) {
		this.recoveredDate = recoveredDate;
	}

	@Column(name="Recovered_Amount", nullable = false, length=15)
	public String getRecoveredAmount() {
		return recoveredAmount;
	}

	public void setRecoveredAmount(String recoveredAmount) {
		this.recoveredAmount = recoveredAmount;
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
	@Column(name="Modified_Date", nullable = false)
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name="Modified_By", nullable = false)
	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
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
