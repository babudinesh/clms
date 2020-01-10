package com.Ntranga.core.CLMS.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "Define_Worker_Group")
public class DefineWorkerGroup implements Serializable {

	private CustomerDetails customerDetails;
	private CompanyDetails companyDetails;
	private MCountry countryDetails;
	private Date transactionDate;
	private String status;
	private String groupCode;
	private String groupName;
	private Integer workerGroupId;
	private Integer uniqueId;
	private Integer sequenceId;
	//private Integer wageRateId;
	private Integer timeRuleId;
	private Integer holidayCalendarId;
	private Integer overTimeRuleId;
	private Integer shiftPatternId;
	private Integer createdBy;
	private Date createdDate;
	private Integer modifiedBy;
	private Date modifiedDate;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "Worker_Group_Id", unique = true, nullable = false)
	public Integer getWorkerGroupId() {
		return workerGroupId;
	}
	
	public void setWorkerGroupId(Integer workerGroupId) {
		this.workerGroupId = workerGroupId;
	}
	
@ManyToOne
	@JoinColumn(name="Customer_Id", nullable = false)
	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}
	
	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}
	
@ManyToOne
	@JoinColumn(name="Company_Id", nullable = false)	
	public CompanyDetails getCompanyDetails() {
		return companyDetails;
	}
	
	public void setCompanyDetails(CompanyDetails companyDetails) {
		this.companyDetails = companyDetails;
	}

@ManyToOne
	@JoinColumn(name="Country_Id", nullable = false)
	public MCountry getCountryDetails() {
		return countryDetails;
	}
	
	public void setCountryDetails(MCountry countryDetails) {
		this.countryDetails = countryDetails;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="Transaction_Date", nullable = false)
	public Date getTransactionDate() {
		return transactionDate;
	}
	
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	
	@Column(name="Status", nullable = false, length = 2)
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name="Group_Code", nullable = false, length = 20)
	public String getGroupCode() {
		return groupCode;
	}
	
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	
	@Column(name="Group_Name", nullable = false, length = 45)
	public String getGroupName() {
		return groupName;
	}
	
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	@Column(name="Unique_Id", nullable = false)
	public Integer getUniqueId() {
		return uniqueId;
	}
	
	public void setUniqueId(Integer uniqueId) {
		this.uniqueId = uniqueId;
	}
	
	@Column(name="Sequence_Id", nullable = false)
	public Integer getSequenceId() {
		return sequenceId;
	}
	
	public void setSequenceId(Integer sequenceId) {
		this.sequenceId = sequenceId;
	}
	
	/*@Column(name="Wage_Rate_Id", nullable = false)
	public Integer getWageRateId() {
		return wageRateId;
	}
	
	public void setWageRateId(Integer wageRateId) {
		this.wageRateId = wageRateId;
	}*/
	
	@Column(name="Time_Rule_Id", nullable = false)
	public Integer getTimeRuleId() {
		return timeRuleId;
	}
	
	public void setTimeRuleId(Integer timeRuleId) {
		this.timeRuleId = timeRuleId;
	}
	
	@Column(name="Holiday_Calendar_Id", nullable = false)
	public Integer getHolidayCalendarId() {
		return holidayCalendarId;
	}
	
	public void setHolidayCalendarId(Integer holidayCalendarId) {
		this.holidayCalendarId = holidayCalendarId;
	}
	
	@Column(name="Over_Time_Rule_Id", nullable = false)
	public Integer getOverTimeRuleId() {
		return overTimeRuleId;
	}
	
	public void setOverTimeRuleId(Integer overTimeRuleId) {
		this.overTimeRuleId = overTimeRuleId;
	}
	
	@Column(name="Shift_Pattern_Id", nullable = false)
	public Integer getShiftPatternId() {
		return shiftPatternId;
	}
	
	public void setShiftPatternId(Integer shiftPatternId) {
		this.shiftPatternId = shiftPatternId;
	}
	
	@Column(name="Created_By", nullable = false)
	public Integer getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Created_Date", nullable = false)
	public Date getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name="Modified_By", nullable = false)
	public Integer getModifiedBy() {
		return modifiedBy;
	}
	
	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Modified_Date", nullable = false)
	public Date getModifiedDate() {
		return modifiedDate;
	}
	
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	
}
