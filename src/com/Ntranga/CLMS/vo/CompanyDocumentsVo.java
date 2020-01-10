package com.Ntranga.CLMS.vo;

import java.util.Date;


public class CompanyDocumentsVo  {

	private Integer companyDocumentId;
	private String documentName;
	private String fileName;
	private String filePath;
	//private File fileData;
	private Integer challanNumber;
	private Date documentDate;
	private String status;
	private String amount;
	private Integer locationId;
	private Integer currencyId;
	private String currencyName;
	private Integer headCount;
	private String documentFrequency;
	private Integer createdBy;
	private Integer modifiedBy;
	private String comments;
	private String locationName;
	
	private Integer customerId;
	private Integer companyId;
	private String companyCode;
	private String companyName;
	private Date fromDate;
	private Date toDate;
	private Integer month;
	private Integer year;
	
	
	
	public Integer getCompanyDocumentId() {
		return companyDocumentId;
	}
	
	public void setCompanyDocumentId(Integer companyDocumentId) {
		this.companyDocumentId = companyDocumentId;
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
	
	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
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

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Override
	public String toString() {
		return "CompanyDocumentsVo [companyDocumentId=" + companyDocumentId
				+ ", documentName=" + documentName + ", fileName=" + fileName
				+ ", filePath=" + filePath + ", challanNumber=" + challanNumber
				+ ", documentDate=" + documentDate + ", status=" + status
				+ ", amount=" + amount + ", locationId=" + locationId
				+ ", currencyId=" + currencyId + ", currencyName="
				+ currencyName + ", headCount=" + headCount
				+ ", documentFrequency=" + documentFrequency + ", createdBy="
				+ createdBy + ", modifiedBy=" + modifiedBy + ", comments="
				+ comments + ", locationName=" + locationName + ", customerId="
				+ customerId + ", companyId=" + companyId + ", companyCode="
				+ companyCode + ", companyName=" + companyName + ", fromDate="
				+ fromDate + ", toDate=" + toDate + ", month=" + month
				+ ", year=" + year + "]";
	}

	
	
}
