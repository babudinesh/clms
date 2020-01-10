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
@Table(name="Rules_For_Payment_Release")
public class RulesForPaymentRelease implements Serializable {

	private Integer rulesForPaymentId;
    private Integer sequenceId;
    private Integer uniqueId;
	private CustomerDetails customerDetails;
    private CompanyDetails companyDetails;
    private MCountry countryDetails;
    private  Date transactionDate;
    private Integer liabilitiesPaid;
    private Integer period;
    private String periodType;
    private Integer beforeVerification;
    private Integer afterVerification;
    private String paymentMethod;
    private Integer createdBy;
    private Date createdDate;
    private Integer modifiedBy;
    private Date modifiedDate;
    
    
    public RulesForPaymentRelease() {
    	
	}

	public RulesForPaymentRelease(Integer rulesForPaymentId) {
		this.rulesForPaymentId = rulesForPaymentId;
	}

	
	
	public RulesForPaymentRelease(Integer rulesForPaymentId,Integer sequenceId, Integer uniqueId,
			CustomerDetails customerDetails, CompanyDetails companyDetails,MCountry countryDetails, Date transactionDate,
			Integer liabilitiesPaid, Integer period, String periodType, Integer beforeVerification, Integer afterVerification,
			String paymentMethod, Integer createdBy, Date cretatedDate, Integer modifiedBy, Date modifiedDate) {
		this.rulesForPaymentId = rulesForPaymentId;
		this.sequenceId = sequenceId;
		this.uniqueId = uniqueId;
		this.customerDetails = customerDetails;
		this.companyDetails = companyDetails;
		this.countryDetails = countryDetails;
		this.transactionDate = transactionDate;
		this.liabilitiesPaid = liabilitiesPaid;
		this.period = period;
		this.periodType = periodType;
		this.beforeVerification = beforeVerification;
		this.afterVerification = afterVerification;
		this.paymentMethod = paymentMethod;
		this.createdBy = createdBy;
		this.createdDate = cretatedDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="Rules_For_Payment_Id", unique = true, nullable = false)
	public Integer getRulesForPaymentId() {
		return rulesForPaymentId;
	}

	public void setRulesForPaymentId(Integer rulesForPaymentId) {
		this.rulesForPaymentId = rulesForPaymentId;
	}

	@Column(name="Sequence_Id",  nullable = false)
	public Integer getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(Integer sequenceId) {
		this.sequenceId = sequenceId;
	}

	@Column(name="Unique_Id",  nullable = false)
	public Integer getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(Integer uniqueId) {
		this.uniqueId = uniqueId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Customer_Id", nullable = false)
	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Company_Id", nullable = false)
	public CompanyDetails getCompanyDetails() {
		return companyDetails;
	}

	public void setCompanyDetails(CompanyDetails companyDetails) {
		this.companyDetails = companyDetails;
	}

	@ManyToOne(fetch=FetchType.LAZY)
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

	@Column(name="Liabilities_Paid")
	public Integer getLiabilitiesPaid() {
		return liabilitiesPaid;
	}

	public void setLiabilitiesPaid(Integer liabilitiesPaid) {
		this.liabilitiesPaid = liabilitiesPaid;
	}

	@Column(name="Period")
	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	@Column(name="Period_Type")
	public String getPeriodType() {
		return periodType;
	}

	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}

	@Column(name="Before_Verification")
	public Integer getBeforeVerification() {
		return beforeVerification;
	}

	public void setBeforeVerification(Integer beforeVerification) {
		this.beforeVerification = beforeVerification;
	}

	@Column(name="After_Verification")
	public Integer getAfterVerification() {
		return afterVerification;
	}

	public void setAfterVerification(Integer afterVerification) {
		this.afterVerification = afterVerification;
	}

	@Column(name="Payment_Method", length = 20)
	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
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
