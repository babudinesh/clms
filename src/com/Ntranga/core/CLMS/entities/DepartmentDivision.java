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
@Table(name="Department_Division")
public class DepartmentDivision implements Serializable {

	private CustomerDetails customerDetails;
	private CompanyDetails companyDetails;
	private DepartmentDetails departmentDetails;
	private DivisionDetails divisionDetails;
	private DepartmentDetailsInfo departmentDetailsInfo;
	
	private Integer departmentDivisionId;
	private Integer createdBy;
	private Integer modifiedBy;
	private Date createdDate;
	private Date modifiedDate;
	
	
	
	
	
	public DepartmentDivision() {
		
	}
	
	public DepartmentDivision(CustomerDetails customerDetails, CompanyDetails companyDetails, DepartmentDetails departmentDetails,
			DivisionDetails divisionDeatils, DepartmentDetailsInfo departmentDetailsInfo,
			Integer departmentDivisionId, Integer createdBy, Integer modifiedBy, Date createdDate, Date modifiedDate) {
		this.customerDetails = customerDetails;
		this.companyDetails = companyDetails;
		this.departmentDetails = departmentDetails;
		this.divisionDetails = divisionDeatils;
		this.departmentDetailsInfo = departmentDetailsInfo;
		this.departmentDivisionId = departmentDivisionId;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
	}
	
	
@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name="Customer_Id", nullable= false)
	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}
	
@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name="Company_Id", nullable= false)
	public CompanyDetails getCompanyDetails() {
		return companyDetails;
	}

	public void setCompanyDetails(CompanyDetails companyDetails) {
		this.companyDetails = companyDetails;
	}
	
@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name="Department_Id", nullable= false)	
	public DepartmentDetails getDepartmentDetails() {
		return departmentDetails;
	}

	public void setDepartmentDetails(DepartmentDetails departmentDetails) {
		this.departmentDetails = departmentDetails;
	}

@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name="Division_Id", nullable= false)
	public DivisionDetails getDivisionDetails() {
		return divisionDetails;
	}

	public void setDivisionDetails(DivisionDetails divisionDeatils) {
		this.divisionDetails = divisionDeatils;
	}
	
@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name="Department_Info_Id", nullable= false)	
	public DepartmentDetailsInfo getDepartmentDetailsInfo() {
		return departmentDetailsInfo;
	}

	public void setDepartmentDetailsInfo(DepartmentDetailsInfo departmentDetailsInfo) {
		this.departmentDetailsInfo = departmentDetailsInfo;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="Department_Division_Id", nullable=false)
	public Integer getDepartmentDivisionId() {
		return departmentDivisionId;
	}
	
	public void setDepartmentDivisionId(Integer departmentDivisionId) {
		this.departmentDivisionId = departmentDivisionId;
	}
	
	@Column(name="Created_By")
	public Integer getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	
	@Column(name="Modified_By")
	public Integer getModifiedBy() {
		return modifiedBy;
	}
	
	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Created_Date")
	public Date getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Modified_Date")
	public Date getModifiedDate() {
		return modifiedDate;
	}
	
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	
	
}
