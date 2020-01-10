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
@Table(name="Invoice_Particulars")
public class InvoiceParticulars implements Serializable {

	private Integer invoiceParticularId;
	private Integer invoiceId;
	private CustomerDetails customerDetails;
	private CompanyDetails companyDetails;
	private VendorDetails vendorDetails;
	
	private String particulars;
	private Date fromDate;
	private Date toDate;
	private String rate;
	private String frequency;
	private String amount;
	private Integer quantity;
	private Integer currencyId;
	
	private Integer createdBy;
	private Date createdDate;
	private Integer modifiedBy;
	private Date modifiedDate;
	
	
	
	public InvoiceParticulars(){
		
	}
	
	public InvoiceParticulars(Integer invoiceParticularId){
		this.invoiceParticularId = invoiceParticularId;
	}
	
	public InvoiceParticulars(Integer invoiceParticularId, Integer invoiceId,CustomerDetails customerDetails, CompanyDetails companyDetails,
			VendorDetails vendorDetails, String particulars, Date fromDate, Date toDate, String rate, String frequency, String amount,
			Integer quantity, Integer currencyId, Integer createdBy, Date createdDate, Integer modifiedby, Date modifiedDate) {
		this.invoiceParticularId = invoiceParticularId;
		this.invoiceId = invoiceId;
		this.customerDetails = customerDetails;
		this.companyDetails = companyDetails;
		this.vendorDetails = vendorDetails;
		this.particulars = particulars;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.rate = rate;
		this.frequency = frequency;
		this.amount = amount;
		this.quantity = quantity;
		this.currencyId = currencyId;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedby;
		this.modifiedDate = modifiedDate;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="Invoice_Particular_Id", unique = true, nullable = false)
	public Integer getInvoiceParticularId() {
		return invoiceParticularId;
	}
	
	public void setInvoiceParticularId(Integer invoiceParticularId) {
		this.invoiceParticularId = invoiceParticularId;
	}
	
	@Column(name = "Invoice_Id" , nullable = false)
	public Integer getInvoiceId() {
		return invoiceId;
	}
	
	public void setInvoiceId(Integer invoiceId) {
		this.invoiceId = invoiceId;
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
	@JoinColumn(name="Vendor_Id", nullable = false)
	public VendorDetails getVendorDetails() {
		return vendorDetails;
	}
	
	public void setVendorDetails(VendorDetails vendorDetails) {
		this.vendorDetails = vendorDetails;
	}
	
	@Column(name = "Particulars", length = 50)
	public String getParticulars() {
		return particulars;
	}
	
	public void setParticulars(String particulars) {
		this.particulars = particulars;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="From_Date")
	public Date getFromDate() {
		return fromDate;
	}
	
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="To_Date")
	public Date getToDate() {
		return toDate;
	}
	
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
	@Column(name="Rate", length = 45)
	public String getRate() {
		return rate;
	}
	
	public void setRate(String rate) {
		this.rate = rate;
	}
	
	@Column(name="Frequency", length = 20)
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	
	@Column(name="Amount", length = 45)
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	@Column(name="Quantity", length = 45)
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	@Column(name="Currency_Id")
	public Integer getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Integer currencyId) {
		this.currencyId = currencyId;
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
