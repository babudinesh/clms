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
@Table(name="Time_Exception_Rules")
public class TimeExceptionRules implements Serializable{

	private Integer exceptionRulesId;
	private Integer exceptionSequenceId;
	private Integer exceptionUniqueId;
	private CustomerDetails customerDetails;
	private CompanyDetails companyDetails;
	private MCountry countryDetails;
	private TimeRulesDetails timeRulesDetails;
	private TimeRulesDetailsInfo timeRulesDetailsInfo;
	private Date transactionDate;
	private Integer limitForExceptionCorrection;
	private String periodType;
	private Integer numberOfExceptions;
	//private DefineExceptions defineExceptions;
	private Integer createdBy;
	private Date createdDate;
	private Integer modifiedBy;
	private Date modifiedDate;
	
	

	
	public TimeExceptionRules() {

	}

	
	public TimeExceptionRules(Integer exceptionRulesId) {
		this.exceptionRulesId = exceptionRulesId;
	}


	public TimeExceptionRules(Integer exceptionRulesId,Integer exceptionSequenceId, Integer exceptionUniqueId,
			CustomerDetails customerDetails, CompanyDetails companyDetails, MCountry countryDetails, TimeRulesDetails timeRulesDetails,
			TimeRulesDetailsInfo timeRulesDetailsInfo, Date transactionDate, Integer limitForExceptionCorrection, String periodType,
			Integer numberOfExceptions,Integer createdBy, Date createdDate, Integer modifiedBy,Date modifiedDate) {
		this.exceptionRulesId = exceptionRulesId;
		this.exceptionSequenceId = exceptionSequenceId;
		this.exceptionUniqueId = exceptionUniqueId;
		this.customerDetails = customerDetails;
		this.companyDetails = companyDetails;
		this.countryDetails = countryDetails;
		this.timeRulesDetails = timeRulesDetails;
		this.timeRulesDetailsInfo = timeRulesDetailsInfo;
		this.transactionDate = transactionDate;
		this.limitForExceptionCorrection = limitForExceptionCorrection;
		this.periodType = periodType;
		this.numberOfExceptions = numberOfExceptions;
	//	this.defineExceptions = defineExceptions;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "Exception_Rules_Id", nullable = false)
	public Integer getExceptionRulesId() {
		return exceptionRulesId;
	}

	public void setExceptionRulesId(Integer exceptionRulesId) {
		this.exceptionRulesId = exceptionRulesId;
	}

	@Column(name = "Exception_Sequence_Id", nullable = false)
	public Integer getExceptionSequenceId() {
		return exceptionSequenceId;
	}

	public void setExceptionSequenceId(Integer exceptionSequenceId) {
		this.exceptionSequenceId = exceptionSequenceId;
	}

	@Column(name = "Exception_Unique_Id", nullable = false)
	public Integer getExceptionUniqueId() {
		return exceptionUniqueId;
	}

	public void setExceptionUniqueId(Integer exceptionUniqueId) {
		this.exceptionUniqueId = exceptionUniqueId;
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
	
@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Time_Rule_Id" )
	public TimeRulesDetails getTimeRulesDetails() {
		return timeRulesDetails;
	}
	
	public void setTimeRulesDetails(TimeRulesDetails timeRulesDetails) {
		this.timeRulesDetails = timeRulesDetails;
	}

@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Time_Rules_Info_Id" )
	public TimeRulesDetailsInfo getTimeRulesDetailsInfo() {
		return timeRulesDetailsInfo;
	}
	
	public void setTimeRulesDetailsInfo(TimeRulesDetailsInfo timeRulesDetailsInfo) {
		this.timeRulesDetailsInfo = timeRulesDetailsInfo;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "Transaction_Date", nullable = false)
	public Date getTransactionDate() {
		return transactionDate;
	}
	
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	
	@Column(name = "Limit_For_Exception_Correction", nullable = false)
	public Integer getLimitForExceptionCorrection() {
		return limitForExceptionCorrection;
	}
	
	public void setLimitForExceptionCorrection(Integer limitForExceptionCorrection) {
		this.limitForExceptionCorrection = limitForExceptionCorrection;
	}
	
	@Column(name = "Period_Type", nullable = false, length=20)
	public String getPeriodType() {
		return periodType;
	}
	
	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}
	
	@Column(name = "Number_Of_Exceptions", nullable = false)
	public Integer getNumberOfExceptions() {
		return numberOfExceptions;
	}
	
	public void setNumberOfExceptions(Integer numberOfExceptions) {
		this.numberOfExceptions = numberOfExceptions;
	}

/*@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Exception_Id" )
	public DefineExceptions getDefineExceptions() {
		return defineExceptions;
	}
	
	public void setDefineExceptions(DefineExceptions defineExceptions) {
		this.defineExceptions = defineExceptions;
	}*/

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
