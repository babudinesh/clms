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
@Table(name = "Rules_For_Payment_Verification")
public class RulesForPaymentVerification implements Serializable {
	
	private Integer paymentVerificationId;
	private CustomerDetails customerDetails;
	private CompanyDetails companyDetails;
	private MCountry countryDetails;
	private RulesForPaymentRelease RulesForPaymentDetails;
	private Integer complianceId;
	private String considerForVerification;
	private Integer createdBy;
    private Date createdDate;
    private Integer modifiedBy;
    private Date modifiedDate;

	
	
	
	public RulesForPaymentVerification() {
	}
	

	public RulesForPaymentVerification(Integer paymentVerificationId,CustomerDetails customerDetails, 
			CompanyDetails companyDetails,MCountry countryDetails, RulesForPaymentRelease rulesForPaymentDetails,
			Integer complianceId, String considerForVerification, Integer createdBy, Date cretatedDate, Integer modifiedBy, Date modifiedDate) {
		this.paymentVerificationId = paymentVerificationId;
		this.customerDetails = customerDetails;
		this.companyDetails = companyDetails;
		this.countryDetails = countryDetails;
		this.RulesForPaymentDetails = rulesForPaymentDetails;
		this.complianceId = complianceId;
		this.considerForVerification = considerForVerification;
		this.createdBy = createdBy;
		this.createdDate = cretatedDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Payment_Verification_Id", unique = true, nullable = false)
	public Integer getPaymentVerificationId() {
		return paymentVerificationId;
	}

	public void setPaymentVerificationId(Integer paymentVerificationId) {
		this.paymentVerificationId = paymentVerificationId;
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

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Rules_For_Payment_Id", nullable = false)
	public RulesForPaymentRelease getRulesForPaymentDetails() {
		return RulesForPaymentDetails;
	}

	public void setRulesForPaymentDetails(
			RulesForPaymentRelease rulesForPaymentDetails) {
		RulesForPaymentDetails = rulesForPaymentDetails;
	}

	@Column(name = "Compliance_Id")
	public Integer getComplianceId() {
		return complianceId;
	}

	public void setComplianceId(Integer complianceId) {
		this.complianceId = complianceId;
	}

	@Column(name = "Consider_For_Verification", length = 2)
	public String getConsiderForVerification() {
		return considerForVerification;
	}

	public void setConsiderForVerification(String considerForVerification) {
		this.considerForVerification = considerForVerification;
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
