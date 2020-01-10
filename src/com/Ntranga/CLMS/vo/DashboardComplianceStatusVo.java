package com.Ntranga.CLMS.vo;

public class DashboardComplianceStatusVo {

	private String vendorName;
	private String expiryDate;
	private String licenseNumber;
	private String complianceName;
	private String status;
	private String vendorCode;
	private Integer vendorComplianceId;
	
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getLicenseNumber() {
		return licenseNumber;
	}
	
	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}
	
	public String getComplianceName() {
		return complianceName;
	}
	
	public void setComplianceName(String complianceName) {
		this.complianceName = complianceName;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	public String getVendorCode() {
		return vendorCode;
	}
	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}
	
	public Integer getVendorComplianceId() {
		return vendorComplianceId;
	}
	public void setVendorComplianceId(Integer vendorComplianceId) {
		this.vendorComplianceId = vendorComplianceId;
	}
	
	@Override
	public String toString() {
		return "DashboardComplianceStatusVo [vendorName=" + vendorName
				+ ", expiryDate=" + expiryDate + ", licenseNumber="
				+ licenseNumber + ", complianceName=" + complianceName
				+ ", status=" + status + ", vendorCode=" + vendorCode
				+ ", vendorComplianceId=" + vendorComplianceId + "]";
	}
	
	
	
}
