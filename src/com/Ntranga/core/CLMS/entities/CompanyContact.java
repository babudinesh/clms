package com.Ntranga.core.CLMS.entities;
// Generated Jun 17, 2016 10:42:43 AM by Hibernate Tools 3.6.0


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

/**
 * CompanyContact generated by hbm2java
 */
@Entity
@Table(name="company_contact")
public class CompanyContact  implements java.io.Serializable {


     private Integer companyContactId;
     private CustomerDetails customerDetails;
     private CompanyDetails companyDetails;
     private int companyContactSequenceId;
     private int companyContactUniqueId;
     private int companyAddressId;
     private MContactType MContactType;
     private String contactName;
     private String contactNameOtherLanguage;
     private String mobileNumber;
     private String businessPhoneNumber;
     private String extensionNumber;
     private String emailId;
     private Date transactionDate;
     private String isActive;
     private int createdBy;
     private Date createdDate;
     private int modifiedBy;
     private Date modifiedDate;
    
    

    public CompanyContact() {
    }

	
    public CompanyContact(CustomerDetails customerDetails, CompanyDetails companyDetails, int companyContactSequenceId, String isActive, int createdBy, Date createdDate, int modifiedBy, Date modifiedDate, int companyContactUniqueId) {
        this.customerDetails = customerDetails;
        this.companyDetails = companyDetails;
        this.companyContactSequenceId = companyContactSequenceId;
        this.isActive = isActive;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.modifiedBy = modifiedBy;
        this.modifiedDate = modifiedDate;
        this.companyContactUniqueId = companyContactUniqueId;
    }
    public CompanyContact(int companyAddressId, CustomerDetails customerDetails, CompanyDetails companyDetails, MContactType MContactType, int companyContactSequenceId, Date transactionDate, String contactName, String mobileNumber, String businessPhoneNumber, String extensionNumber, String emailId, String isActive, int createdBy, Date createdDate, int modifiedBy, Date modifiedDate, byte[] companyDetails_1, String contactNameOtherLanguage, int companyContactUniqueId) {
       this.customerDetails = customerDetails;
       this.companyDetails = companyDetails;
       this.MContactType = MContactType;
       this.companyContactSequenceId = companyContactSequenceId;
       this.companyContactUniqueId = companyContactUniqueId;
       this.companyAddressId = companyAddressId;
       this.contactName = contactName;
       this.contactNameOtherLanguage = contactNameOtherLanguage;
       this.mobileNumber = mobileNumber;
       this.businessPhoneNumber = businessPhoneNumber;
       this.extensionNumber = extensionNumber;
       this.emailId = emailId;
       this.transactionDate = transactionDate;
       this.isActive = isActive;
       this.createdBy = createdBy;
       this.createdDate = createdDate;
       this.modifiedBy = modifiedBy;
       this.modifiedDate = modifiedDate;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="Company_Contact_Id", unique=true, nullable=false)
    public Integer getCompanyContactId() {
        return this.companyContactId;
    }
    
    public void setCompanyContactId(Integer companyContactId) {
        this.companyContactId = companyContactId;
    }

/*@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Company_Address_Id")
    public CompanyAddress getCompanyAddress() {
        return this.companyAddress;
    }
    
    public void setCompanyAddress(CompanyAddress companyAddress) {
        this.companyAddress = companyAddress;
    }*/

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Customer_Id", nullable=false)
    public CustomerDetails getCustomerDetails() {
        return this.customerDetails;
    }
    
    public void setCustomerDetails(CustomerDetails customerDetails) {
        this.customerDetails = customerDetails;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Company_Id", nullable=false)
    public CompanyDetails getCompanyDetails() {
        return this.companyDetails;
    }
    
    public void setCompanyDetails(CompanyDetails companyDetails) {
        this.companyDetails = companyDetails;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Contact_Type_Id")
    public MContactType getMContactType() {
        return this.MContactType;
    }
    
    public void setMContactType(MContactType MContactType) {
        this.MContactType = MContactType;
    }

    
    @Column(name="Company_Contact_Sequence_Id", nullable=false)
    public int getCompanyContactSequenceId() {
        return this.companyContactSequenceId;
    }
    
    public void setCompanyContactSequenceId(int companyContactSequenceId) {
        this.companyContactSequenceId = companyContactSequenceId;
    }

    @Column(name="Company_Contact_Unique_Id", nullable=false)
	public int getCompanyContactUniqueId() {
		return companyContactUniqueId;
	}


	public void setCompanyContactUniqueId(int companyContactUniqueId) {
		this.companyContactUniqueId = companyContactUniqueId;
	}


	@Column(name="Company_Address_Id", nullable=false)
	public int getCompanyAddressId() {
		return companyAddressId;
	}

	public void setCompanyAddressId(int companyAddressId) {
		this.companyAddressId = companyAddressId;
	}


	@Temporal(TemporalType.DATE)
    @Column(name="Transaction_Date", length=10)
    public Date getTransactionDate() {
        return this.transactionDate;
    }
    
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    
    @Column(name="Contact_Name", length=45)
    public String getContactName() {
        return this.contactName;
    }
    
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    
    @Column(name="Mobile_Number", length=45)
    public String getMobileNumber() {
        return this.mobileNumber;
    }
    
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    
    @Column(name="Business_Phone_Number", length=45)
    public String getBusinessPhoneNumber() {
        return this.businessPhoneNumber;
    }
    
    public void setBusinessPhoneNumber(String businessPhoneNumber) {
        this.businessPhoneNumber = businessPhoneNumber;
    }

    
    @Column(name="Extension_Number", length=45)
    public String getExtensionNumber() {
        return this.extensionNumber;
    }
    
    public void setExtensionNumber(String extensionNumber) {
        this.extensionNumber = extensionNumber;
    }

    
    @Column(name="Email_Id", length=45)
    public String getEmailId() {
        return this.emailId;
    }
    
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    
    @Column(name="Is_Active", nullable=false, length=2)
    public String getIsActive() {
        return this.isActive;
    }
    
    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    
    @Column(name="Created_By", nullable=false)
    public int getCreatedBy() {
        return this.createdBy;
    }
    
    public void setCreatedBy(int createdBy) {
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

    
    @Column(name="Contact_Name_Other_Language", length=45)
    public String getContactNameOtherLanguage() {
        return this.contactNameOtherLanguage;
    }
    
    public void setContactNameOtherLanguage(String contactNameOtherLanguage) {
        this.contactNameOtherLanguage = contactNameOtherLanguage;
    }

    
   


	



}


