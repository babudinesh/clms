package com.Ntranga.core.CLMS.entities;
// Generated Jun 17, 2016 10:42:43 AM by Hibernate Tools 3.6.0


import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * CompanyDetails generated by hbm2java
 */
@Entity
@Table(name="company_details"
    
)
public class CompanyDetails  implements java.io.Serializable {


     private Integer companyId;
     private CustomerDetails customerDetails;
     private String companyCode;
     private int createdBy;
     private Date createdDate;
     private int modifiedBy;
     private Date modifiedDate;
     private String isActive;
     private Set<CompanyProfile> companyProfiles = new HashSet<CompanyProfile>(0);
     private Set<CompanyAddress> companyAddresses = new HashSet<CompanyAddress>(0);
     private Set<CompanyDetailsInfo> companyDetailsInfos = new HashSet<CompanyDetailsInfo>(0);
     private Set<CompanyContact> companyContacts = new HashSet<CompanyContact>(0);
     private Set<Shifts> shiftses = new HashSet<Shifts>(0);
     private Set<ShiftsDefine> shiftsDefines = new HashSet<ShiftsDefine>(0);
     private String isDivision;
     private Integer divisionCompanyId;
     
     @Column(name="IsDivision", nullable=true)
    public String getIsDivision() {
		return isDivision;
	}

	public void setIsDivision(String isDivision) {
		this.isDivision = isDivision;
	}
	@Column(name="Division_Company_Id", nullable=true)
	public Integer getDivisionCompanyId() {
		return divisionCompanyId;
	}

	public void setDivisionCompanyId(Integer divisionCompanyId) {
		this.divisionCompanyId = divisionCompanyId;
	}

	public CompanyDetails() {
    }

    public CompanyDetails(Integer companyId) {
        this.companyId = companyId;
    }

    public CompanyDetails(CustomerDetails customerDetails, int createdBy, Date createdDate, int modifiedBy, Date modifiedDate, String isActive) {
        this.customerDetails = customerDetails;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.modifiedBy = modifiedBy;
        this.modifiedDate = modifiedDate;
        this.isActive = isActive;
    }
    public CompanyDetails(CustomerDetails customerDetails, String companyCode, int createdBy, Date createdDate, int modifiedBy, Date modifiedDate, String isActive, Set<CompanyProfile> companyProfiles, Set<CompanyAddress> companyAddresses, Set<CompanyDetailsInfo> companyDetailsInfos, Set<CompanyContact> companyContacts) {
       this.customerDetails = customerDetails;
       this.companyCode = companyCode;
       this.createdBy = createdBy;
       this.createdDate = createdDate;
       this.modifiedBy = modifiedBy;
       this.modifiedDate = modifiedDate;
       this.isActive = isActive;
       this.companyProfiles = companyProfiles;
       this.companyAddresses = companyAddresses;
       this.companyDetailsInfos = companyDetailsInfos;
       this.companyContacts = companyContacts;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="Company_Id", unique=true, nullable=false)
    public Integer getCompanyId() {
        return this.companyId;
    }
    
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Customer_Id", nullable=false)
    public CustomerDetails getCustomerDetails() {
        return this.customerDetails;
    }
    
    public void setCustomerDetails(CustomerDetails customerDetails) {
        this.customerDetails = customerDetails;
    }

    
    @Column(name="Company_Code", length=45)
    public String getCompanyCode() {
        return this.companyCode;
    }
    
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
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

    
    @Column(name="Is_Active", nullable=false, length=2)
    public String getIsActive() {
        return this.isActive;
    }
    
    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="companyDetails")
    public Set<CompanyProfile> getCompanyProfiles() {
        return this.companyProfiles;
    }
    
    public void setCompanyProfiles(Set<CompanyProfile> companyProfiles) {
        this.companyProfiles = companyProfiles;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="companyDetails")
    public Set<CompanyAddress> getCompanyAddresses() {
        return this.companyAddresses;
    }
    
    public void setCompanyAddresses(Set<CompanyAddress> companyAddresses) {
        this.companyAddresses = companyAddresses;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="companyDetails")
    public Set<CompanyDetailsInfo> getCompanyDetailsInfos() {
        return this.companyDetailsInfos;
    }
    
    public void setCompanyDetailsInfos(Set<CompanyDetailsInfo> companyDetailsInfos) {
        this.companyDetailsInfos = companyDetailsInfos;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="companyDetails")
    public Set<CompanyContact> getCompanyContacts() {
        return this.companyContacts;
    }
    
    public void setCompanyContacts(Set<CompanyContact> companyContacts) {
        this.companyContacts = companyContacts;
    }


@OneToMany(fetch=FetchType.LAZY, mappedBy="companyDetails")
    public Set<Shifts> getShiftses() {
        return this.shiftses;
    }
    
    public void setShiftses(Set<Shifts> shiftses) {
        this.shiftses = shiftses;
    }
@OneToMany(fetch=FetchType.LAZY, mappedBy="companyDetails")
    public Set<ShiftsDefine> getShiftsDefines() {
        return this.shiftsDefines;
    }
    
    public void setShiftsDefines(Set<ShiftsDefine> shiftsDefines) {
        this.shiftsDefines = shiftsDefines;
    }

}

