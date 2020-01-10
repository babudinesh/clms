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
 * CompanyDetailsInfo generated by hbm2java
 */
@Entity
@Table(name="company_details_info"
    
)
public class CompanyDetailsInfo  implements java.io.Serializable {

	 private Integer companyInfoId;
     private CustomerDetails customerDetails;
     private CompanyDetails companyDetails;
     private String companyName;
     private int countryId;
     private int uniqueId;
     private int companySequenceId;
     private Date transactionDate;
     private String isActive;
     private Integer sectorId;
     private String legacyId;
     private Integer industryId;
     private Integer subIndustryId;
     private String corporateIdentityNumber;
     private Date registrationDate;
     private String rocRegistrationNumber;
     private Integer registrationActId;
     private String serviceTaxRegistrationNumber;
     private String tinNumber;
     private String panNumber;
     private Date panRegistrationDate;
     private String pfNumber;
     private Date pfRegistrationDate;
     private Date pfStartDate;
     private Integer pfTypeId;
     private String esiNumber;
     private Date esiRegistrationDate;
     private Date esiStartDate;
     private int createdBy;
     private Date createdDate;
     private int modifiedBy;
     private Date modifiedDate;

    public CompanyDetailsInfo() {
    }

	
    public CompanyDetailsInfo(CustomerDetails customerDetails, CompanyDetails companyDetails, String companyName, int countryId, int uniqueId, int companySequenceId, Date transactionDate, String isActive, int createdBy, Date createdDate, int modifiedBy, Date modifiedDate) {
        this.customerDetails = customerDetails;
        this.companyDetails = companyDetails;
        this.companyName = companyName;
        this.countryId = countryId;
        this.uniqueId = uniqueId;
        this.companySequenceId = companySequenceId;
        this.transactionDate = transactionDate;
        this.isActive = isActive;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.modifiedBy = modifiedBy;
        this.modifiedDate = modifiedDate;
    }
    public CompanyDetailsInfo(CustomerDetails customerDetails, CompanyDetails companyDetails, String companyName, int countryId, int uniqueId, int companySequenceId, Date transactionDate, String isActive, Integer sectorId, String legacyId, Integer industryId, Integer subIndustryId, String corporateIdentityNumber, Date registrationDate, String rocRegistrationNumber, Integer registrationActId, String serviceTaxRegistrationNumber, String tinNumber, String panNumber, Date panRegistrationDate, String pfNumber, Date pfRegistrationDate, Date pfStartDate, Integer pfTypeId, String esiNumber, Date esiRegistrationDate, Date esiStartDate, int createdBy, Date createdDate, int modifiedBy, Date modifiedDate) {
       this.customerDetails = customerDetails;
       this.companyDetails = companyDetails;
       this.companyName = companyName;
       this.countryId = countryId;
       this.uniqueId = uniqueId;
       this.companySequenceId = companySequenceId;
       this.transactionDate = transactionDate;
       this.isActive = isActive;
       this.sectorId = sectorId;
       this.legacyId = legacyId;
       this.industryId = industryId;
       this.subIndustryId = subIndustryId;
       this.corporateIdentityNumber = corporateIdentityNumber;
       this.registrationDate = registrationDate;
       this.rocRegistrationNumber = rocRegistrationNumber;
       this.registrationActId = registrationActId;
       this.serviceTaxRegistrationNumber = serviceTaxRegistrationNumber;
       this.tinNumber = tinNumber;
       this.panNumber = panNumber;
       this.panRegistrationDate = panRegistrationDate;
       this.pfNumber = pfNumber;
       this.pfRegistrationDate = pfRegistrationDate;
       this.pfStartDate = pfStartDate;
       this.pfTypeId = pfTypeId;
       this.esiNumber = esiNumber;
       this.esiRegistrationDate = esiRegistrationDate;
       this.esiStartDate = esiStartDate;
       this.createdBy = createdBy;
       this.createdDate = createdDate;
       this.modifiedBy = modifiedBy;
       this.modifiedDate = modifiedDate;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="Company_Info_Id", unique=true, nullable=false)
    public Integer getCompanyInfoId() {
        return this.companyInfoId;
    }
    
    public void setCompanyInfoId(Integer companyInfoId) {
        this.companyInfoId = companyInfoId;
    }

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

    
    @Column(name="Company_Name", nullable=false, length=45)
    public String getCompanyName() {
        return this.companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    
    @Column(name="Country_Id", nullable=false)
    public int getCountryId() {
        return this.countryId;
    }
    
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    
    @Column(name="Unique_Id", nullable=false)
    public int getUniqueId() {
        return this.uniqueId;
    }
    
    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    
    @Column(name="Company_Sequence_Id", nullable=false)
    public int getCompanySequenceId() {
        return this.companySequenceId;
    }
    
    public void setCompanySequenceId(int companySequenceId) {
        this.companySequenceId = companySequenceId;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="Transaction_Date", nullable=false, length=10)
    public Date getTransactionDate() {
        return this.transactionDate;
    }
    
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    
    @Column(name="Is_Active", nullable=false, length=2)
    public String getIsActive() {
        return this.isActive;
    }
    
    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    
    @Column(name="Sector_Id")
    public Integer getSectorId() {
        return this.sectorId;
    }
    
    public void setSectorId(Integer sectorId) {
        this.sectorId = sectorId;
    }

    
    @Column(name="Legacy_Id", length=50)
    public String getLegacyId() {
        return this.legacyId;
    }
    
    public void setLegacyId(String legacyId) {
        this.legacyId = legacyId;
    }

    
    @Column(name="Industry_Id")
    public Integer getIndustryId() {
        return this.industryId;
    }
    
    public void setIndustryId(Integer industryId) {
        this.industryId = industryId;
    }

    
    @Column(name="Sub_Industry_Id")
    public Integer getSubIndustryId() {
        return this.subIndustryId;
    }
    
    public void setSubIndustryId(Integer subIndustryId) {
        this.subIndustryId = subIndustryId;
    }

    
    @Column(name="Corporate_Identity_Number", length=50)
    public String getCorporateIdentityNumber() {
        return this.corporateIdentityNumber;
    }
    
    public void setCorporateIdentityNumber(String corporateIdentityNumber) {
        this.corporateIdentityNumber = corporateIdentityNumber;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="Registration_Date", length=10)
    public Date getRegistrationDate() {
        return this.registrationDate;
    }
    
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    
    @Column(name="ROC_Registration_Number", length=25)
    public String getRocRegistrationNumber() {
        return this.rocRegistrationNumber;
    }
    
    public void setRocRegistrationNumber(String rocRegistrationNumber) {
        this.rocRegistrationNumber = rocRegistrationNumber;
    }

    
    @Column(name="Registration_Act_Id")
    public Integer getRegistrationActId() {
        return this.registrationActId;
    }
    
    public void setRegistrationActId(Integer registrationActId) {
        this.registrationActId = registrationActId;
    }

    
    @Column(name="Service_Tax_Registration_Number", length=45)
    public String getServiceTaxRegistrationNumber() {
        return this.serviceTaxRegistrationNumber;
    }
    
    public void setServiceTaxRegistrationNumber(String serviceTaxRegistrationNumber) {
        this.serviceTaxRegistrationNumber = serviceTaxRegistrationNumber;
    }

    
    @Column(name="TIN_Number", length=45)
    public String getTinNumber() {
        return this.tinNumber;
    }
    
    public void setTinNumber(String tinNumber) {
        this.tinNumber = tinNumber;
    }

    
    @Column(name="PAN_Number", length=25)
    public String getPanNumber() {
        return this.panNumber;
    }
    
    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="PAN_Registration_Date", length=10)
    public Date getPanRegistrationDate() {
        return this.panRegistrationDate;
    }
    
    public void setPanRegistrationDate(Date panRegistrationDate) {
        this.panRegistrationDate = panRegistrationDate;
    }

    
    @Column(name="PF_Number", length=25)
    public String getPfNumber() {
        return this.pfNumber;
    }
    
    public void setPfNumber(String pfNumber) {
        this.pfNumber = pfNumber;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="PF_Registration_Date", length=10)
    public Date getPfRegistrationDate() {
        return this.pfRegistrationDate;
    }
    
    public void setPfRegistrationDate(Date pfRegistrationDate) {
        this.pfRegistrationDate = pfRegistrationDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="PF_Start_date", length=10)
    public Date getPfStartDate() {
        return this.pfStartDate;
    }
    
    public void setPfStartDate(Date pfStartDate) {
        this.pfStartDate = pfStartDate;
    }

    
    @Column(name="PF_Type_Id")
    public Integer getPfTypeId() {
        return this.pfTypeId;
    }
    
    public void setPfTypeId(Integer pfTypeId) {
        this.pfTypeId = pfTypeId;
    }

    
    @Column(name="ESI_Number")
    public String getEsiNumber() {
        return this.esiNumber;
    }
    
    public void setEsiNumber(String esiNumber) {
        this.esiNumber = esiNumber;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="ESI_Registration_Date", length=10)
    public Date getEsiRegistrationDate() {
        return this.esiRegistrationDate;
    }
    
    public void setEsiRegistrationDate(Date esiRegistrationDate) {
        this.esiRegistrationDate = esiRegistrationDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="ESI_Start_Date", length=10)
    public Date getEsiStartDate() {
        return this.esiStartDate;
    }
    
    public void setEsiStartDate(Date esiStartDate) {
        this.esiStartDate = esiStartDate;
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

}


