package com.Ntranga.core.CLMS.entities;

import static javax.persistence.GenerationType.IDENTITY;

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

@Entity
@Table(name="Worker_Verification_Types_Setup")
public class WorkerVerificationTypesSetup implements java.io.Serializable {

	private CustomerDetails customerDetails ;
	private CompanyDetails companyDetails ;
	private Integer workerVerificationTypesSetupId ;
	private Date transactionDate;
	private String isActive;
	private String verificationType;
	private String isMandatiory;
	private String verificationFrequency;
	private Integer countryId;	
	private int createdBy;
    private Date createdDate;
	private int modifiedBy;
    private Date modifiedDate;
    
    public WorkerVerificationTypesSetup(){
    	
    }

	public WorkerVerificationTypesSetup(CustomerDetails customerDetails,CompanyDetails companyDetails, 
												Integer createdBy, Date createdDate, Integer modifiedBy, Date modifiedDate) {
		this.customerDetails = customerDetails;
		this.companyDetails = companyDetails;		
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}
	
	
	
	@Id 
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="Worker_Verification_Types_Setup_Id", unique=true, nullable=false)
	public Integer getWorkerVerificationTypesSetupId() {
		return workerVerificationTypesSetupId;
	}

	public void setWorkerVerificationTypesSetupId(Integer workerVerificationTypesSetupId) {
		this.workerVerificationTypesSetupId = workerVerificationTypesSetupId;
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

	
	@Column(name="Created_By", nullable=false)
    public int getCreatedBy() {
        return this.createdBy;
    }
    
    public void setCreatedBy(int createdBy) {
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
    public int getModifiedBy() {
        return this.modifiedBy;
    }
    
    public void setModifiedBy(int modifiedBy) {
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

    @Column(name="Transaction_Date", nullable=false)
	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	@Column(name="Is_Active", nullable=false)
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	@Column(name="Verification_Type", nullable=false)
	public String getVerificationType() {
		return verificationType;
	}

	public void setVerificationType(String verificationType) {
		this.verificationType = verificationType;
	}

	@Column(name="Is_Mandatiory", nullable=false)
	public String getIsMandatiory() {
		return isMandatiory;
	}

	public void setIsMandatiory(String isMandatiory) {
		this.isMandatiory = isMandatiory;
	}

	@Column(name="Verification_Frequency", nullable=false)
	public String getVerificationFrequency() {
		return verificationFrequency;
	}

	public void setVerificationFrequency(String verificationFrequency) {
		this.verificationFrequency = verificationFrequency;
	}

	@Column(name="Country_Id", nullable=false)
	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	@Override
	public String toString() {
		return "WorkerVerificationTypesSetup [customerDetails=" + customerDetails + ", companyDetails=" + companyDetails
				+ ", workerVerificationTypesSetupId=" + workerVerificationTypesSetupId + ", transactionDate="
				+ transactionDate + ", isActive=" + isActive + ", verificationType=" + verificationType
				+ ", isMandatiory=" + isMandatiory + ", verificationFrequency=" + verificationFrequency + ", countryId="
				+ countryId + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy
				+ ", modifiedDate=" + modifiedDate + "]";
	}
    
    
	
    
	
    
}
