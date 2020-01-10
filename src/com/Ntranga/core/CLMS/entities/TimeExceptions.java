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
@Table(name="Time_Exceptions")
public class TimeExceptions implements Serializable {

	private Integer timeExceptionId;
	private TimeExceptionRules timeExceptionRules;
	private CustomerDetails customerDetails;
	private CompanyDetails companyDetails;
	private MCountry countryDetails;
	private Integer exceptionId;
	private Integer createdBy;
	private Date createdDate;
	private Integer modifiedBy;
	private Date modifiedDate;

	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "Time_Exception_Id", nullable = false)
	public Integer getTimeExceptionId() {
		return timeExceptionId;
	}

	public void setTimeExceptionId(Integer timeExceptionId) {
		this.timeExceptionId = timeExceptionId;
	}

@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Exception_Rules_Id" )
	public TimeExceptionRules getTimeExceptionRules() {
		return timeExceptionRules;
	}

	public void setTimeExceptionRules(TimeExceptionRules timeExceptionRules) {
		this.timeExceptionRules = timeExceptionRules;
	}

@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Customer_Id" )
	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}
	
@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Company_Id" )	
	public CompanyDetails getCompanyDetails() {
		return companyDetails;
	}
	
	public void setCompanyDetails(CompanyDetails companyDetails) {
		this.companyDetails = companyDetails;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Country_Id" )	
	public MCountry getCountryDetails() {
		return countryDetails;
	}

	public void setCountryDetails(MCountry countryDetails) {
		this.countryDetails = countryDetails;
	}

	@Column(name="Exception_Id", nullable = false )
	public Integer getExceptionId() {
		return exceptionId;
	}
	
	public void setExceptionId(Integer exceptionId) {
		this.exceptionId = exceptionId;
	}

	@Column(name="Created_By", nullable=false)
    public Integer getCreatedBy() {
        return this.createdBy;
    }
    
    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="Created_Date", nullable=false, length=19)
    public Date getCreatedDate() {
        return this.createdDate;
    }
    
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    
    @Column(name="Modified_By", nullable=false)
    public Integer getModifiedBy() {
        return this.modifiedBy;
    }
    
    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="Modified_Date", nullable=false, length=19)
    public Date getModifiedDate() {
        return this.modifiedDate;
    }
    
    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
	
}
