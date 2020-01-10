package com.Ntranga.core.CLMS.entities;
// Generated 25 Jan, 2017 6:00:55 PM by Hibernate Tools 3.6.0


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * AssociatingDepartmentToLocationPlant generated by hbm2java
 */
@Entity
@Table(name="associating_department_to_location_plant"   
)
public class AssociatingDepartmentToLocationPlant  implements java.io.Serializable {


     private Integer associationDepartmentId;
     private PlantDetails plantDetails;
     private DepartmentDetails departmentDetails;
     private CustomerDetails customerDetails;
     private CompanyDetails companyDetails;
     private LocationDetails locationDetails;
     private int createdBy;
     private Date createdDate;
     private int modifiedBy;
     private Date modifiedDate;

    public AssociatingDepartmentToLocationPlant() {
    }

    public AssociatingDepartmentToLocationPlant(PlantDetails plantDetails, DepartmentDetails departmentDetails, CustomerDetails customerDetails, CompanyDetails companyDetails, LocationDetails locationDetails, int createdBy, Date createdDate, int modifiedBy, Date modifiedDate) {
       this.plantDetails = plantDetails;
       this.departmentDetails = departmentDetails;
       this.customerDetails = customerDetails;
       this.companyDetails = companyDetails;
       this.locationDetails = locationDetails;
       this.createdBy = createdBy;
       this.createdDate = createdDate;
       this.modifiedBy = modifiedBy;
       this.modifiedDate = modifiedDate;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="Association_Department_Id", unique=true, nullable=false)
    public Integer getAssociationDepartmentId() {
        return this.associationDepartmentId;
    }
    
    public void setAssociationDepartmentId(Integer associationDepartmentId) {
        this.associationDepartmentId = associationDepartmentId;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Plant_Id", nullable=false)
    public PlantDetails getPlantDetails() {
        return this.plantDetails;
    }
    
    public void setPlantDetails(PlantDetails plantDetails) {
        this.plantDetails = plantDetails;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Department_Id", nullable=false)
    public DepartmentDetails getDepartmentDetails() {
        return this.departmentDetails;
    }
    
    public void setDepartmentDetails(DepartmentDetails departmentDetails) {
        this.departmentDetails = departmentDetails;
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

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Location_Id", nullable=false)
    public LocationDetails getLocationDetails() {
        return this.locationDetails;
    }
    
    public void setLocationDetails(LocationDetails locationDetails) {
        this.locationDetails = locationDetails;
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




}


