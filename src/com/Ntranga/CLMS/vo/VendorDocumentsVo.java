package com.Ntranga.CLMS.vo;

import java.util.Date;


public class VendorDocumentsVo  {

	private Integer vendorDocumentId;
	private String documentName;
	private String fileName;
	private String filePath;
	//private File fileData;
	private Integer challanNumber;
	private Date documentDate;
	private String status;
	private String amount;
	private Integer currencyId;
	private String currencyName;
	private Integer headCount;
	private String documentFrequency;
	private String vendorCode;
	private String vendorName;
	private Integer createdBy;
	private Integer modifiedBy;
	private String comments;
	
	private Integer customerId;
	private Integer companyId;
	private Integer vendorId;
	private Date fromDate;
	private Date toDate;
	private Integer month;
	private Integer year;
	
	
	
	public Integer getVendorDocumentId() {
		return vendorDocumentId;
	}
	
	public void setVendorDocumentId(Integer vendorDocumentId) {
		this.vendorDocumentId = vendorDocumentId;
	}
	
	public String getDocumentName() {
		return documentName;
	}
	
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public Integer getChallanNumber() {
		return challanNumber;
	}
	
	public void setChallanNumber(Integer challanNumber) {
		this.challanNumber = challanNumber;
	}
	
	public Date getDocumentDate() {
		return documentDate;
	}
	
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getAmount() {
		return amount;
	}
	
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public Integer getCurrencyId() {
		return currencyId;
	}
	
	public void setCurrencyId(Integer currencyId) {
		this.currencyId = currencyId;
	}
	
	public String getCurrencyName() {
		return currencyName;
	}
	
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	
	public Integer getHeadCount() {
		return headCount;
	}
	
	public void setHeadCount(Integer headCount) {
		this.headCount = headCount;
	}
	
	public String getDocumentFrequency() {
		return documentFrequency;
	}
	
	public void setDocumentFrequency(String documentFrequency) {
		this.documentFrequency = documentFrequency;
	}
	
	public String getVendorCode() {
		return vendorCode;
	}
	
	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}
	
	public String getVendorName() {
		return vendorName;
	}
	
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "VendorDocumentsVo [vendorDocumentId=" + vendorDocumentId
				+ ", documentName=" + documentName + ", fileName=" + fileName
				+ ", filePath=" + filePath + ", challanNumber=" + challanNumber
				+ ", documentDate=" + documentDate + ", status=" + status
				+ ", amount=" + amount + ", currencyId=" + currencyId
				+ ", currencyName=" + currencyName + ", headCount=" + headCount
				+ ", documentFrequency=" + documentFrequency + ", vendorCode="
				+ vendorCode + ", vendorName=" + vendorName + ", createdBy="
				+ createdBy + ", modifiedBy=" + modifiedBy + ", comments="
				+ comments + ", customerId=" + customerId + ", companyId="
				+ companyId + ", vendorId=" + vendorId + ", fromDate="
				+ fromDate + ", toDate=" + toDate + ", month=" + month
				+ ", year=" + year + "]";
	}
	
}
