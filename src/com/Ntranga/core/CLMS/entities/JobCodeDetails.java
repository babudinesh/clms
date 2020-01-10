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


/**
 * The persistent class for the job_code_details database table.
 * 
 */

@Entity
@Table(name="job_code_details")
public class JobCodeDetails implements Serializable {
	

	private CompanyDetails companyDetails;
	private CustomerDetails customerDetails;
	private int jobCodeId;
	private String jobCode;
	private int createdBy;
	private Date createdDate;
	private int modifiedBy;
	private Date modifiedDate;


	
	public JobCodeDetails() {
	}

	public JobCodeDetails(int jobCodeId) {
		this.jobCodeId = jobCodeId;
	}


	public JobCodeDetails(CompanyDetails companyDetails, CustomerDetails customerDetails, int jobCodeId, String jobCode,
			int createdBy, Date createdDate, int modifiedBy, Date modifiedDate) {
		this.companyDetails = companyDetails;
		this.customerDetails = customerDetails;
		this.jobCodeId = jobCodeId;
		this.jobCode = jobCode;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Job_Code_Id", unique=true, nullable=false)
	public int getJobCodeId() {
		return this.jobCodeId;
	}

	public void setJobCodeId(int jobcodeId) {
		this.jobCodeId = jobcodeId;
	}

@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Company_Id")
	public CompanyDetails getCompanyDetails() {
		return this.companyDetails;
	}

	public void setCompanyDetails(CompanyDetails companyId) {
		this.companyDetails = companyId;
	}
	
@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Customer_Id")
	public CustomerDetails getCustomerDetails() {
		return this.customerDetails;
	}

	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}
	
	@Column(name="Job_Code")
	public String getJobCode() {
		return this.jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}
	
	@Column(name="Created_By")
	public int getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Created_Date")
	public Date getCreatedDate() {
		return this.createdDate;
	}
	
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name="Modified_By")
	public int getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Modified_Date")
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	
}