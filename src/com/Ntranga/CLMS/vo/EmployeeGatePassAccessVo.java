package com.Ntranga.CLMS.vo;

import java.io.Serializable;
import java.util.Date;

public class EmployeeGatePassAccessVo implements Serializable {

	private Integer employeeGatePassAccessId;
	private Integer customerId;
	private Integer companyId;	
    private Integer locationId;
    private Integer countryId;
    private Date transactionDate;
    private Integer cuttOfHoursORMonth;
    private Integer createdBy;
    private Date createdDate;
    private Integer modifiedBy;
    private Date modifiedDate;
    
    
	public Integer getEmployeeGatePassAccessId() {
		return employeeGatePassAccessId;
	}
	public void setEmployeeGatePassAccessId(Integer employeeGatePassAccessId) {
		this.employeeGatePassAccessId = employeeGatePassAccessId;
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
	public Integer getLocationId() {
		return locationId;
	}
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}
	public Integer getCountryId() {
		return countryId;
	}
	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public Integer getCuttOfHoursORMonth() {
		return cuttOfHoursORMonth;
	}
	public void setCuttOfHoursORMonth(Integer cuttOfHoursORMonth) {
		this.cuttOfHoursORMonth = cuttOfHoursORMonth;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
    
    
}
