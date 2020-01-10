package com.Ntranga.CLMS.vo;

import java.io.File;
import java.util.Date;

public class VendorComplianceVo {

	private Date fromDate;
	private Date toDate;
	private int vendorComplianceId;
	private int vendorComplianceSequenceId;
	private int vendorComplianceUniqueId;
	private int customerId;
	private String customerName;
	private int companyId;
	private String companyName;
	private int vendorId;
	private String vendorName;
	private Integer defineComplianceTypeId;
	private String complianceName;
	private int countryId;
	private String countryName;
	private int registeredState;
	private String licensePolicyNumber;
	private Date issueDate;
	private Date expiryDate;
	private Date renewalDate;
	private String dueDate;
	private Date transactionDate;
	private String status;
	//private int validityPeriod;
	//private String periodType;
	private double transactionAmount;
	private int amountType;
	private int numberOfWorkersCovered;
	private String remarks;
	private int createdBy;
    private int modifiedBy;
    private String issueDt;
    private String expiryDt;
    private boolean mandatory;
    private boolean validated;
    private boolean saved;
    private String reason;
    private String documentPath;
    private String path;
    private String vendorStatus;
    private File file;
    private Integer locationId;
    
    
    public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getVendorStatus() {
		return vendorStatus;
	}

	public void setVendorStatus(String vendorStatus) {
		this.vendorStatus = vendorStatus;
	}

	public String getPath(){
    	return path;
    }
    
    public void setPath(String path){
    	this.path = path;
    }
    
    public String getDocumentPath(){
    	return documentPath;
    }
    
    public void setDocumentPath(String documentPath){
    	this.documentPath = documentPath;
    }
    
    public String getReason(){
    	return reason;
    }
    
    public void setReason(String reason){
    	this.reason = reason;
    }
    
    public boolean getValidated() {
		return validated;
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
	}
	
	public boolean getSaved() {
		return saved;
	}

	public void setSaved(boolean saved) {
		this.saved = saved;
	}

	public boolean getMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	public String getIssueDt() {
		return issueDt;
	}

	public void setIssueDt(String issueDt) {
		this.issueDt = issueDt;
	}

	public String getExpiryDt() {
		return expiryDt;
	}

	public void setExpiryDt(String expiryDt) {
		this.expiryDt = expiryDt;
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

	public int getVendorComplianceId() {
		return vendorComplianceId;
	}
	public void setVendorComplianceId(int vendorComplianceId) {
		this.vendorComplianceId = vendorComplianceId;
	}
	public int getVendorComplianceSequenceId() {
		return vendorComplianceSequenceId;
	}
	public void setVendorComplianceSequenceId(int vendorComplianceSequenceId) {
		this.vendorComplianceSequenceId = vendorComplianceSequenceId;
	}
	public int getVendorComplianceUniqueId() {
		return vendorComplianceUniqueId;
	}
	public void setVendorComplianceUniqueId(int vendorComplianceUniqueId) {
		this.vendorComplianceUniqueId = vendorComplianceUniqueId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public int getVendorId() {
		return vendorId;
	}
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public Integer getDefineComplianceTypeId() {
		return defineComplianceTypeId;
	}
	public void setDefineComplianceTypeId(Integer defineComplianceTypeId) {
		this.defineComplianceTypeId = defineComplianceTypeId;
	}
	public String getComplianceName() {
		return complianceName;
	}
	public void setComplianceName(String complianceName) {
		this.complianceName = complianceName;
	}
	public int getCountryId() {
		return countryId;
	}
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public int getRegisteredState() {
		return registeredState;
	}
	public void setRegisteredState(int registeredState) {
		this.registeredState = registeredState;
	}
	public String getLicensePolicyNumber() {
		return licensePolicyNumber;
	}
	public void setLicensePolicyNumber(String licensePolicyNumber) {
		this.licensePolicyNumber = licensePolicyNumber;
	}
	public Date getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public Date getRenewalDate() {
		return renewalDate;
	}
	public void setRenewalDate(Date renewalDate) {
		this.renewalDate = renewalDate;
	}
	/**
	 * @return the dueDate
	 */
	public String getDueDate() {
		return dueDate;
	}
	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	/*public int getValidityPeriod() {
		return validityPeriod;
	}
	public void setValidityPeriod(int validityPeriod) {
		this.validityPeriod = validityPeriod;
	}
	public String getPeriodType() {
		return periodType;
	}
	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}*/
	public double getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public int getAmountType() {
		return amountType;
	}
	public void setAmountType(int amountType) {
		this.amountType = amountType;
	}
	public int getNumberOfWorkersCovered() {
		return numberOfWorkersCovered;
	}
	public void setNumberOfWorkersCovered(int numberOfWorkersCovered) {
		this.numberOfWorkersCovered = numberOfWorkersCovered;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public int getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	
	
	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	@Override
	public String toString() {
		return "VendorComplianceVo [fromDate=" + fromDate + ", toDate="
				+ toDate + ", vendorComplianceId=" + vendorComplianceId
				+ ", vendorComplianceSequenceId=" + vendorComplianceSequenceId
				+ ", vendorComplianceUniqueId=" + vendorComplianceUniqueId
				+ ", customerId=" + customerId + ", customerName="
				+ customerName + ", companyId=" + companyId + ", companyName="
				+ companyName + ", vendorId=" + vendorId + ", vendorName="
				+ vendorName + ", defineComplianceTypeId="
				+ defineComplianceTypeId + ", complianceName=" + complianceName
				+ ", countryId=" + countryId + ", countryName=" + countryName
				+ ", registeredState=" + registeredState
				+ ", licensePolicyNumber=" + licensePolicyNumber
				+ ", issueDate=" + issueDate + ", expiryDate=" + expiryDate
				+ ", renewalDate=" + renewalDate + ", dueDate=" + dueDate
				+ ", transactionDate=" + transactionDate + ", status=" + status
				+ ", transactionAmount=" + transactionAmount + ", amountType="
				+ amountType + ", numberOfWorkersCovered="
				+ numberOfWorkersCovered + ", remarks=" + remarks
				+ ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy
				+ ", issueDt=" + issueDt + ", expiryDt=" + expiryDt
				+ ", mandatory=" + mandatory + ", validated=" + validated
				+ ", reason=" + reason + ", documentPath=" + documentPath + "]";
	}
    
    
}
