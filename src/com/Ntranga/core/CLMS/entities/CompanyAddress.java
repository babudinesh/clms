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
 * CompanyAddress generated by hbm2java
 */
@Entity
@Table(name="company_address"
    
)
public class CompanyAddress  implements java.io.Serializable {


     private Integer companyAddressId;
     private MAddressType MAddressType;
     private CustomerDetails customerDetails;
     private CompanyDetails companyDetails;
     private int companyAddressSequenceId;
     private Date transactionDate;
     private String address1;
     private String address2;
     private String address3;
     private Integer country;
     private Integer state;
     private Integer city;
     private Integer pincode;
     private String isActive;
     private int createdBy;
     private Date createdDate;
     private int modifiedBy;
     private Date modifiedDate;
     private int addressId;

    // private Set<CompanyContact> companyContacts = new HashSet<CompanyContact>(0);

    public CompanyAddress() {
    }

	
    public CompanyAddress(CustomerDetails customerDetails, CompanyDetails companyDetails, int companyAddressSequenceId, Date transactionDate, String isActive, int createdBy, Date createdDate, int modifiedBy, Date modifiedDate, int addressId) {
        this.customerDetails = customerDetails;
        this.companyDetails = companyDetails;
        this.companyAddressSequenceId = companyAddressSequenceId;
        this.transactionDate = transactionDate;
        this.isActive = isActive;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.modifiedBy = modifiedBy;
        this.modifiedDate = modifiedDate;
        this.addressId = addressId;
    }
  /*  public CompanyAddress(MAddressType MAddressType, CustomerDetails customerDetails, CompanyDetails companyDetails, int companyAddressSequenceId, Date transactionDate, String address1, String address2, String address3, Integer country, Integer state, Integer city, Integer pincode, String isActive, int createdBy, Date createdDate, int modifiedBy, Date modifiedDate, int addressId, Integer cityId, Integer countryId, Integer stateId, Set<CompanyContact> companyContacts) {
       this.MAddressType = MAddressType;
       this.customerDetails = customerDetails;
       this.companyDetails = companyDetails;
       this.companyAddressSequenceId = companyAddressSequenceId;
       this.transactionDate = transactionDate;
       this.address1 = address1;
       this.address2 = address2;
       this.address3 = address3;
       this.country = country;
       this.state = state;
       this.city = city;
       this.pincode = pincode;
       this.isActive = isActive;
       this.createdBy = createdBy;
       this.createdDate = createdDate;
       this.modifiedBy = modifiedBy;
       this.modifiedDate = modifiedDate;
       this.addressId = addressId;
       this.cityId = cityId;
       this.countryId = countryId;
       this.stateId = stateId;
      // this.companyContacts = companyContacts;
    }*/
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="Company_Address_Id", unique=true, nullable=false)
    public Integer getCompanyAddressId() {
        return this.companyAddressId;
    }
    
    public void setCompanyAddressId(Integer companyAddressId) {
        this.companyAddressId = companyAddressId;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Address_Type_Id")
    public MAddressType getMAddressType() {
        return this.MAddressType;
    }
    
    public void setMAddressType(MAddressType MAddressType) {
        this.MAddressType = MAddressType;
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

    
    @Column(name="Company_Address_Sequence_Id", nullable=false)
    public int getCompanyAddressSequenceId() {
        return this.companyAddressSequenceId;
    }
    
    public void setCompanyAddressSequenceId(int companyAddressSequenceId) {
        this.companyAddressSequenceId = companyAddressSequenceId;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="Transaction_Date", nullable=false, length=10)
    public Date getTransactionDate() {
        return this.transactionDate;
    }
    
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    
    @Column(name="Address_1", length=45)
    public String getAddress1() {
        return this.address1;
    }
    
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    
    @Column(name="Address_2", length=45)
    public String getAddress2() {
        return this.address2;
    }
    
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    
    @Column(name="Address_3", length=45)
    public String getAddress3() {
        return this.address3;
    }
    
    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    
    @Column(name="Country")
    public Integer getCountry() {
        return this.country;
    }
    
    public void setCountry(Integer country) {
        this.country = country;
    }

    
    @Column(name="State")
    public Integer getState() {
        return this.state;
    }
    
    public void setState(Integer state) {
        this.state = state;
    }

    
    @Column(name="City")
    public Integer getCity() {
        return this.city;
    }
    
    public void setCity(Integer city) {
        this.city = city;
    }

    
    @Column(name="Pincode")
    public Integer getPincode() {
        return this.pincode;
    }
    
    public void setPincode(Integer pincode) {
        this.pincode = pincode;
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

    
    @Column(name="Address_Id", nullable=false)
    public int getAddressId() {
        return this.addressId;
    }
    
    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    
   
/*
@OneToMany(fetch=FetchType.LAZY, mappedBy="companyAddress")
    public Set<CompanyContact> getCompanyContacts() {
        return this.companyContacts;
    }
    
    public void setCompanyContacts(Set<CompanyContact> companyContacts) {
        this.companyContacts = companyContacts;
    }*/






}


