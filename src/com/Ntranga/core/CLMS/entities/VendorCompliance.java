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
@Table(name="vendor_compliance")
public class VendorCompliance implements Serializable {

	private Integer vendorComplianceId;
	private Integer vendorComplianceSequenceId;
	private Integer vendorComplianceUniqueId;
	private CustomerDetails customerDetails;
	private CompanyDetails companyDetails;
	private VendorDetails vendorDetails;
	private LocationDetails locationDetails;
	//private DefineComplianceTypes defineCompliance;
	private MCountry countryDetails;
	private MState registeredState;
	private String licensePolicyNumber;
	private Date issueDate;
	private Date expiryDate;
	private Date renewalDate;
	private Date transactionDate;
	private String status;
	//private Integer validityPeriod;
	//private String periodType;
	private double transactionAmount;
	private MCurrency amountType;
	private Integer numberOfWorkersCovered;
	private String remarks;
	private Integer createdBy;
    private Date createdDate;
    private Integer modifiedBy;
    private Date modifiedDate;
	//private String mandatory;
	//private String validated;
	private String documentPath;
	private String reason;
	private Integer complianceUniqueId;
	
	
	public VendorCompliance() {
	}


	

	public VendorCompliance(Integer vendorComplianceId, Integer vendorComplianceSequenceId,Integer vendorComplianceUniqueId, 
			CustomerDetails customerDetails, CompanyDetails companyDetails, VendorDetails vendorDetails,
			 MCountry countryDetails,MState registeredState, String licensePolicyNumber, Integer complianceUniqueId,
			Date issueDate, Date expiryDate, Date renewalDate, Date transactionDate,String status, 
			double transactionAmount, MCurrency amountType, Integer numberOfWorkersCovered, String remarks, Integer createdBy,
			Date createdDate, Integer modifiedBy, Date modifiedDate, String mandatory, String documentPath, String reason ) {
		this.vendorComplianceId = vendorComplianceId;
		this.vendorComplianceSequenceId = vendorComplianceSequenceId;
		this.vendorComplianceUniqueId = vendorComplianceUniqueId;
		this.customerDetails = customerDetails;
		this.companyDetails = companyDetails;
		this.vendorDetails = vendorDetails;
		//this.defineCompliance = defineCompliance;
		this.countryDetails = countryDetails;
		this.registeredState = registeredState;
		this.licensePolicyNumber = licensePolicyNumber;
		this.issueDate = issueDate;
		this.expiryDate = expiryDate;
		this.renewalDate = renewalDate;
		this.transactionDate = transactionDate;
		this.status = status;
		this.transactionAmount = transactionAmount;
		this.amountType = amountType;
		this.numberOfWorkersCovered = numberOfWorkersCovered;
		this.remarks = remarks;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.documentPath = documentPath;
		this.reason = reason;
	}
	
	@Column(name="Reason")
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	/*@Column(name="Validated")
	public String getValidated() {
		return validated;
	}

	public void setValidated(String validated) {
		this.validated = validated;
	}*/

	/*@Column(name="Mandatory")
	public String getMandatory() {
		return mandatory;
	}

	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
	}*/
	
	@Column(name="Document_Path", length=255)
	public String getDocumentPath() {
		return documentPath;
	}

	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}

	@Id
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="Vendor_Compliance_Id")
	public Integer getVendorComplianceId() {
		return vendorComplianceId;
	}

	public void setVendorComplianceId(Integer vendorComplianceId) {
		this.vendorComplianceId = vendorComplianceId;
	}

	@Column(name="Vendor_Compliance_Sequence_Id")
	public Integer getVendorComplianceSequenceId() {
		return vendorComplianceSequenceId;
	}

	public void setVendorComplianceSequenceId(Integer vendorComplianceSequenceId) {
		this.vendorComplianceSequenceId = vendorComplianceSequenceId;
	}

	@Column(name="Vendor_Compliance_Unique_Id")
	public Integer getVendorComplianceUniqueId() {
		return vendorComplianceUniqueId;
	}

	public void setVendorComplianceUniqueId(Integer vendorComplianceUniqueId) {
		this.vendorComplianceUniqueId = vendorComplianceUniqueId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Customer_Id")	
	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Location_Id")	
	public LocationDetails getLocationDetails() {
		return locationDetails;
	}


	public void setLocationDetails(LocationDetails locationDetails) {
		this.locationDetails = locationDetails;
	}




	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Company_Id")	
	public CompanyDetails getCompanyDetails() {
		return companyDetails;
	}

	public void setCompanyDetails(CompanyDetails companyDetails) {
		this.companyDetails = companyDetails;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Vendor_Id")	
	public VendorDetails getVendorDetails() {
		return vendorDetails;
	}

	public void setVendorDetails(VendorDetails vendorDetails) {
		this.vendorDetails = vendorDetails;
	}

	/*@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Define_Compliance_Type_Id")	
	public DefineComplianceTypes getDefineCompliance() {
		return defineCompliance;
	}

	public void setDefineCompliance(DefineComplianceTypes defineCompliance) {
		this.defineCompliance = defineCompliance;
	}*/
	

	@Column(name="Compliance_Unique_Id")	
	public Integer getComplianceUniqueId() {
		return complianceUniqueId;
	}

	public void setComplianceUniqueId(Integer complianceUniqueId) {
		this.complianceUniqueId = complianceUniqueId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Country_Id")	
	public MCountry getCountryDetails() {
		return countryDetails;
	}

	public void setCountryDetails(MCountry countryDetails) {
		this.countryDetails = countryDetails;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Registered_State")
	public MState getRegisteredState() {
		return registeredState;
	}

	public void setRegisteredState(MState registeredState) {
		this.registeredState = registeredState;
	}

	@Column(name="License_Policy_Number", length=45)
	public String getLicensePolicyNumber() {
		return licensePolicyNumber;
	}

	public void setLicensePolicyNumber(String licensePolicyNumber) {
		this.licensePolicyNumber = licensePolicyNumber;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="Issue_Date")
	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="Expiry_Date")
	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="Renewal_Date")
	public Date getRenewalDate() {
		return renewalDate;
	}

	public void setRenewalDate(Date renewalDate) {
		this.renewalDate = renewalDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="Transaction_Date")
	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	@Column(name="Status", length=10)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/*@Column(name="Validity_Period")
	public Integer getValidityPeriod() {
		return validityPeriod;
	}

	public void setValidityPeriod(Integer validityPeriod) {
		this.validityPeriod = validityPeriod;
	}

	@Column(name="Period_Type")
	public String getPeriodType() {
		return periodType;
	}

	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}*/

	@Column(name="Transaction_Amount")
	public double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	@ManyToOne(fetch=FetchType.LAZY)	
	@JoinColumn(name="Amount_Type")
	public MCurrency getAmountType() {
		return amountType;
	}

	public void setAmountType(MCurrency amountType) {
		this.amountType = amountType;
	}

	@Column(name="Number_Of_Workers_Covered", length=6)
	public Integer getNumberOfWorkersCovered() {
		return numberOfWorkersCovered;
	}

	public void setNumberOfWorkersCovered(Integer numberOfWorkersCovered) {
		this.numberOfWorkersCovered = numberOfWorkersCovered;
	}

	@Column(name="Remarks", length=255)
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
    @Column(name="Created_By", nullable=false)
    public Integer getCreatedBy() {
        return this.createdBy;
    }
    
    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="Created_date", nullable=false, length=19)
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
