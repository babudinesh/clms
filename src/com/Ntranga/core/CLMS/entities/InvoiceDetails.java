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
@Table(name="Invoice_Details")
public class InvoiceDetails implements Serializable {

	private Integer invoiceId;
	private CustomerDetails customerDetails;
	private CompanyDetails companyDetails;
	private VendorDetails vendorDetails;
	//private Integer vendorId;
	private String companyContactNumber;
	private String vendorContactNumber;
	private String vendorEmail;
	private String companyAddress;
	private String vendorAddress;
	private Integer companyAddressId;
	private Integer companyContactId;
	private Integer locationId;
	private Integer departmentId;
	
	private Integer invoiceNumber;
	private Date invoiceDate;
	private String status;
	private String poNumber;
	private String prNumber;
	private String serviceNumber;
	private String serviceTaxNumber;
	private String panNumber;
	private String remarks;
	private String subjectTo;
	private String totalAmountInWords;
	private String totalAmount;
	private String comments;
	
	private Integer createdBy;
	private Date createdDate;
	private Integer modifiedBy;
	private Date modifiedDate;
	
	
	
	public InvoiceDetails() {
		super();
	}
	
	
	public InvoiceDetails(Integer invoiceId) {
		this.invoiceId = invoiceId;
	}


	public InvoiceDetails(Integer invoiceId, CustomerDetails customerDetails,CompanyDetails companyDetails, VendorDetails vendorDetails,
			//Integer vendorAddressId, String vendorContactName, String vendorEmail, 
			Integer companyAddressId,Integer companyContactId, Integer locationId, Integer departmentId, Integer invoiceNumber, Date invoiceDate,
			String status, String poNumber, String prNumber, String serviceNumber, String serviceTaxNumber, String panNumber,
			String remarks, String subjectTo, Integer createdBy, Date createdDate, Integer modifiedBy, Date modifiedDate) {
		this.invoiceId = invoiceId;
		this.customerDetails = customerDetails;
		this.companyDetails = companyDetails;
		this.vendorDetails = vendorDetails;
		//this.vendorAddressId = vendorAddressId;
		//this.vendorContactName = vendorContactName;
		//this.vendorEmail = vendorEmail;
		this.companyAddressId = companyAddressId;
		this.companyContactId = companyContactId;
		this.locationId = locationId;
		this.departmentId = departmentId;
		this.invoiceNumber = invoiceNumber;
		this.invoiceDate = invoiceDate;
		this.status = status;
		this.poNumber = poNumber;
		this.prNumber = prNumber;
		this.serviceNumber = serviceNumber;
		this.serviceTaxNumber = serviceTaxNumber;
		this.panNumber = panNumber;
		this.remarks = remarks;
		this.subjectTo = subjectTo;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="Invoice_Id", unique = true, nullable = false)
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

	@Column(name="Company_Contact_Number", length = 15)
	public String getCompanyContactNumber() {
		return companyContactNumber;
	}

	public void setCompanyContactNumber(String companyContactNumber) {
		this.companyContactNumber = companyContactNumber;
	}

	@Column(name="Vendor_Contact_Number", length=15)
	public String getVendorContactNumber() {
		return vendorContactNumber;
	}

	public void setVendorContactNumber(String vendorContactNumber) {
		this.vendorContactNumber = vendorContactNumber;
	}

	@Column(name="Vendor_Email", length = 50)
	public String getVendorEmail() {
		return vendorEmail;
	}

	public void setVendorEmail(String vendorEmail) {
		this.vendorEmail = vendorEmail;
	}

	@Column(name="Company_Address", length = 255)
	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	@Column(name="Vendor_Address", length = 255)
	public String getVendorAddress() {
		return vendorAddress;
	}

	public void setVendorAddress(String vendorAddress) {
		this.vendorAddress = vendorAddress;
	}

	@Column(name = "Company_Address_Id")
	public Integer getCompanyAddressId() {
		return companyAddressId;
	}

	public void setCompanyAddressId(Integer companyAddressId) {
		this.companyAddressId = companyAddressId;
	}

	@Column(name = "Company_Contact_Id")
	public Integer getCompanyContactId() {
		return companyContactId;
	}

	public void setCompanyContactId(Integer companyContactId) {
		this.companyContactId = companyContactId;
	}

	@Column(name = "Location_Id")
	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	@Column(name = "Department_Id")
	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	@Column(name = "Invoice_Number", nullable = false)
	public Integer getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(Integer invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "Invoice_Date", nullable = false)
	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	@Column(name = "Status", length = 25, nullable = false)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "PO_Number", length = 50)
	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	@Column(name = "PR_Number", length = 50)
	public String getPrNumber() {
		return prNumber;
	}

	public void setPrNumber(String prNumber) {
		this.prNumber = prNumber;
	}

	@Column(name = "Service_Number", length = 50)
	public String getServiceNumber() {
		return serviceNumber;
	}

	public void setServiceNumber(String serviceNumber) {
		this.serviceNumber = serviceNumber;
	}

	@Column(name = "Service_Tax_Number", length = 50)
	public String getServiceTaxNumber() {
		return serviceTaxNumber;
	}

	public void setServiceTaxNumber(String serviceTaxNumber) {
		this.serviceTaxNumber = serviceTaxNumber;
	}

	@Column(name = "Pan_Number", length = 50)
	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	@Column(name = "Remarks", length = 255)
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "Subject_To", length = 50)
	public String getSubjectTo() {
		return subjectTo;
	}

	public void setSubjectTo(String subjectTo) {
		this.subjectTo = subjectTo;
	}

	@Column(name = "Total_Amount_In_Words", length = 200)
	public String getTotalAmountInWords() {
		return totalAmountInWords;
	}

	public void setTotalAmountInWords(String totalAmountInWords) {
		this.totalAmountInWords = totalAmountInWords;
	}

	@Column(name = "Total_Amount", length = 50)
	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Column(name = "Comments", length = 255)
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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
