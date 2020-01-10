package com.Ntranga.CLMS.vo;

import java.util.Date;

public class DashboardVo {

	private Integer customerId;
	private Integer companyId;
	private Integer vendorId;
	private Integer workerId;
	private Date fromDate;
	private Date toDate;
	private String userId;
	private String schemaName;
	
	
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
	
	public Integer getWorkerId() {
		return workerId;
	}
	
	public void setWorkerId(Integer workerId) {
		this.workerId = workerId;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	@Override
	public String toString() {
		return "DashboardVo [customerId=" + customerId + ", companyId="
				+ companyId + ", vendorId=" + vendorId + ", workerId="
				+ workerId + ", fromDate=" + fromDate + ", toDate=" + toDate
				+ ", userId=" + userId + ", schemaName=" + schemaName + "]";
	}

	
	
	
	
}
