package com.Ntranga.CLMS.vo;

import java.util.Date;
import java.util.List;

public class InvoiceDetailsVo {

	private Integer invoiceId;
	private Integer customerId;
	private Integer companyId;
	private Integer vendorId;
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
	private Integer modifiedBy;
	private String companyAddress;
	private String vendorAddress;
	private String companyContactNumber;
	private String vendorContactNumber;
	private String vendorEmail;
	
	
	private String customerName;
	private String companyName;
	private String vendorName;
	private String locationName;
	private String departmentName;
	
	private Integer month;
	private Integer year;
	private Date fromDate;
	private Date toDate;
	
	private List<InvoiceParticularsVo> particularsList;
	
	
	
	public Integer getInvoiceId() {
		return invoiceId;
	}
	
	public void setInvoiceId(Integer invoiceId) {
		this.invoiceId = invoiceId;
	}
	
	public Integer getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	
	public Integer getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	
	public Integer getVendorId() {
		return vendorId;
	}
	
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	
	/*public Integer getVendorAddressId() {
		return vendorAddressId;
	}
	
	public void setVendorAddressId(Integer vendorAddressId) {
		this.vendorAddressId = vendorAddressId;
	}
	
	public String getVendorContactName() {
		return vendorContactName;
	}
	
	public void setVendorContactName(String vendorContactName) {
		this.vendorContactName = vendorContactName;
	}
	
	public String getVendorEmail() {
		return vendorEmail;
	}
	
	public void setVendorEmail(String vendorEmail) {
		this.vendorEmail = vendorEmail;
	}*/
	
	public Integer getCompanyAddressId() {
		return companyAddressId;
	}
	
	public void setCompanyAddressId(Integer companyAddressId) {
		this.companyAddressId = companyAddressId;
	}
	
	public Integer getCompanyContactId() {
		return companyContactId;
	}
	
	public void setCompanyContactId(Integer companyContactId) {
		this.companyContactId = companyContactId;
	}
	
	public Integer getLocationId() {
		return locationId;
	}
	
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}
	
	public Integer getDepartmentId() {
		return departmentId;
	}
	
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	
	public Integer getInvoiceNumber() {
		return invoiceNumber;
	}
	
	public void setInvoiceNumber(Integer invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getPoNumber() {
		return poNumber;
	}
	
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	
	public String getPrNumber() {
		return prNumber;
	}
	
	public void setPrNumber(String prNumber) {
		this.prNumber = prNumber;
	}
	
	public String getServiceNumber() {
		return serviceNumber;
	}
	
	public void setServiceNumber(String serviceNumber) {
		this.serviceNumber = serviceNumber;
	}
	
	public String getServiceTaxNumber() {
		return serviceTaxNumber;
	}
	
	public void setServiceTaxNumber(String serviceTaxNumber) {
		this.serviceTaxNumber = serviceTaxNumber;
	}
	
	public String getPanNumber() {
		return panNumber;
	}
	
	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}
	
	public String getRemarks() {
		return remarks;
	}
	
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getSubjectTo() {
		return subjectTo;
	}
	
	public void setSubjectTo(String subjectTo) {
		this.subjectTo = subjectTo;
	}
	
	public String getTotalAmountInWords() {
		return totalAmountInWords;
	}
	
	public void setTotalAmountInWords(String totalAmountInWords) {
		this.totalAmountInWords = totalAmountInWords;
	}
	
	public String getTotalAmount() {
		return totalAmount;
	}
	
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public String getComments() {
		return comments;
	}
	
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public Integer getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	
	public Integer getModifiedBy() {
		return modifiedBy;
	}
	
	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getVendorAddress() {
		return vendorAddress;
	}

	public void setVendorAddress(String vendorAddress) {
		this.vendorAddress = vendorAddress;
	}

	public String getCompanyContactNumber() {
		return companyContactNumber;
	}

	public void setCompanyContactNumber(String companyContactNumber) {
		this.companyContactNumber = companyContactNumber;
	}

	public String getVendorContactNumber() {
		return vendorContactNumber;
	}

	public void setVendorContactNumber(String vendorContactNumber) {
		this.vendorContactNumber = vendorContactNumber;
	}

	public String getVendorEmail() {
		return vendorEmail;
	}

	public void setVendorEmail(String vendorEmail) {
		this.vendorEmail = vendorEmail;
	}

	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getVendorName() {
		return vendorName;
	}
	
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	
	public String getLocationName() {
		return locationName;
	}
	
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
	public String getDepartmentName() {
		return departmentName;
	}
	
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public List<InvoiceParticularsVo> getParticularsList() {
		return particularsList;
	}
	
	public void setParticularsList(List<InvoiceParticularsVo> particularsList) {
		this.particularsList = particularsList;
	}
	
	@Override
	public String toString() {
		return "InvoiceDetailsVo [invoiceId=" + invoiceId + ", customerId="
				+ customerId + ", companyId=" + companyId + ", vendorId="
				+ vendorId + ", companyAddressId=" + companyAddressId
				+ ", companyContactId=" + companyContactId + ", locationId="
				+ locationId + ", departmentId=" + departmentId
				+ ", invoiceNumber=" + invoiceNumber + ", invoiceDate="
				+ invoiceDate + ", status=" + status + ", poNumber=" + poNumber
				+ ", prNumber=" + prNumber + ", serviceNumber=" + serviceNumber
				+ ", serviceTaxNumber=" + serviceTaxNumber + ", panNumber="
				+ panNumber + ", remarks=" + remarks + ", subjectTo="
				+ subjectTo + ", totalAmountInWords=" + totalAmountInWords
				+ ", totalAmount=" + totalAmount + ", comments=" + comments
				+ ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy
				+ ", companyAddress=" + companyAddress + ", vendorAddress="
				+ vendorAddress + ", companyContactNumber="
				+ companyContactNumber + ", vendorContactNumber="
				+ vendorContactNumber + ", vendorEmail=" + vendorEmail
				+ ", customerName=" + customerName + ", companyName="
				+ companyName + ", vendorName=" + vendorName
				+ ", locationName=" + locationName + ", departmentName="
				+ departmentName + ", month=" + month + ", year=" + year
				+ ", fromDate=" + fromDate + ", toDate=" + toDate
				+ ", particularsList=" + particularsList + "]";
	}

	

	
	
}
