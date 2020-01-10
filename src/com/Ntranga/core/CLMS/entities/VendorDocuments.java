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
@Table(name="vendor_documents")
public class VendorDocuments implements Serializable {

	private Integer vendorDocumentId;
	private CustomerDetails customerDetails;
	private CompanyDetails companyDetails;
	private VendorDetails vendorDetails;
	
	private String documentName;
	private String filePath;
	private String fileName;
	private Integer challanNumber;
	private String amount;
	private Date documentDate;
	private String documentFrequency;
	private Integer headCount;
	private Integer currencyId;
	private String status;
	private String comments;
	
	private Integer createdBy;
	private Date createdDate;
	private Integer modifiedBy;
	private Date modifiedDate;
	
	
	public VendorDocuments() {

	}

	public VendorDocuments(Integer vendorDocumentId) {
		this.vendorDocumentId = vendorDocumentId;
	}	
	
	public VendorDocuments(Integer vendorDocumentId, CustomerDetails customerDetails, CompanyDetails companyDetails,
			VendorDetails vendorDetails, String documentName, String filePath, String fileName, Integer challanNumber, 
			String amount, Date documentDate, String documentFrequency, Integer headCount, Integer currencyId, 
			String status, Integer createdBy, Date createdDate, Integer modifiedBy, Date modifiedDate, String comments) {
		this.vendorDocumentId = vendorDocumentId;
		this.customerDetails = customerDetails;
		this.companyDetails = companyDetails;
		this.vendorDetails = vendorDetails;
		this.documentName = documentName;
		this.filePath = filePath;
		this.fileName = fileName;
		this.challanNumber = challanNumber;
		this.amount = amount;
		this.documentDate = documentDate;
		this.documentFrequency = documentFrequency;
		this.headCount = headCount;
		this.currencyId = currencyId;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.comments = comments;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Vendor_Document_Id", unique = true, nullable = false)
	public Integer getVendorDocumentId() {
		return vendorDocumentId;
	}

	public void setVendorDocumentId(Integer vendorDocumentId) {
		this.vendorDocumentId = vendorDocumentId;
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

	@Column(name="Document_Name", length = 50)
	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	@Column(name="File_Path", length = 200)
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Column(name="File_Name", length = 100)
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name="Challan_Number")
	public Integer getChallanNumber() {
		return challanNumber;
	}

	public void setChallanNumber(Integer challanNumber) {
		this.challanNumber = challanNumber;
	}

	@Column(name="Amount", length=15)
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="Document_Date")
	public Date getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	@Column(name="Currency_Id")
	public Integer getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Integer currencyId) {
		this.currencyId = currencyId;
	}

	@Column(name="Document_Frequency", length=15)
	public String getDocumentFrequency() {
		return documentFrequency;
	}

	public void setDocumentFrequency(String documentFrequency) {
		this.documentFrequency = documentFrequency;
	}
	
	@Column(name="Head_Count")
	public Integer getHeadCount() {
		return headCount;
	}

	public void setHeadCount(Integer headCount) {
		this.headCount = headCount;
	}
	
	@Column(name="Status", length=20)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name="Comments", length=255)
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
