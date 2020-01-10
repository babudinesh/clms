package com.Ntranga.core.CLMS.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name="Define_Exceptions")
public class DefineExceptions implements Serializable {

	private CustomerDetails customerDetails;
	private CompanyDetails companyDetails;
	private MCountry countryDetails;
	private Integer exceptionUniqueId;
	private Integer exceptionSequenceId;
	private Integer exceptionId;
	private Date transactionDate;
	private  String exceptionCode;
	private String exceptionName;
	private String effectiveStatus;
	
	private int createdBy;
	private Date createdDate;
	private int modifiedBy;
	private Date modifiedDate;
	
	
	public DefineExceptions() {
		
	}

	public DefineExceptions(Integer exceptionUniqueId) {
		this.exceptionUniqueId = exceptionUniqueId;
	}
	
	public DefineExceptions(CustomerDetails customerDetails,CompanyDetails companyDetails, MCountry countryDetails,
			Integer exceptionUniqueId, Integer exceptionSequenceId,Integer exceptionId, Date transactionDate, 
			String exceptionCode, String exceptionName, String effectiveStatus, int createdBy,
			Date createdDate, int modifiedBy, Date modifiedDate) {
		this.customerDetails = customerDetails;
		this.companyDetails = companyDetails;
		this.countryDetails = countryDetails;
		this.exceptionUniqueId = exceptionUniqueId;
		this.exceptionSequenceId = exceptionSequenceId;
		this.exceptionId = exceptionId;
		this.transactionDate = transactionDate;
		this.exceptionCode = exceptionCode;
		this.exceptionName = exceptionName;
		this.effectiveStatus = effectiveStatus;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}

	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="Exception_Id", nullable = false)
	public Integer getExceptionId() {
		return exceptionId;
	}

	public void setExceptionId(Integer exceptionId) {
		this.exceptionId = exceptionId;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Customer_Id", nullable=false)
	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}
	
	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Company_Id", nullable=false)
	public CompanyDetails getCompanyDetails() {
		return companyDetails;
	}
	
	public void setCompanyDetails(CompanyDetails companyDetails) {
		this.companyDetails = companyDetails;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Country_Id", nullable=false)
	public MCountry getCountryDetails() {
		return countryDetails;
	}
	
	public void setCountryDetails(MCountry countryDetails) {
		this.countryDetails = countryDetails;
	}

	@Column(name="Exception_Unique_Id", nullable = false)
	public Integer getExceptionUniqueId() {
		return exceptionUniqueId;
	}

	public void setExceptionUniqueId(Integer exceptionUniqueId) {
		this.exceptionUniqueId = exceptionUniqueId;
	}

	@Column(name="Exception_Sequence_Id", nullable = false)
	public Integer getExceptionSequenceId() {
		return exceptionSequenceId;
	}

	public void setExceptionSequenceId(Integer exceptionSequenceId) {
		this.exceptionSequenceId = exceptionSequenceId;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="Transaction_Date", nullable = false)
	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	@Column(name="Exception_Code", nullable = false, length = 20)
	public String getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	@Column(name="Exception_Name", nullable = false, length = 45)
	public String getExceptionName() {
		return exceptionName;
	}

	public void setExceptionName(String exceptionName) {
		this.exceptionName = exceptionName;
	}

	@Column(name="Effective_Status", nullable = false, length = 2)
	public String getEffectiveStatus() {
		return effectiveStatus;
	}

	public void setEffectiveStatus(String effectiveStatus) {
		this.effectiveStatus = effectiveStatus;
	}

	@Column(name="Created_By", nullable=false)
    public int getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="Created_Date", nullable=false, length=19)
    public Date getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    
    @Column(name="Modified_By", nullable=false)
    public int getModifiedBy() {
        return modifiedBy;
    }
    
    public void setModifiedBy(int modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="Modified_Date", nullable=false, length=19)
    public Date getModifiedDate() {
        return modifiedDate;
    }
	
    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
	
	
	
}
