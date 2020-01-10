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
@Entity
@Table(name="Location_Department")
public class LocationDepartment implements Serializable {

	private CustomerDetails customerDetails;
	private CompanyDetails companyDetails;
	private Integer locDepId;
	private LocationDetails location;
	private DepartmentDetails department;
	private int createdBy;
    private Date createdDate;
	private int modifiedBy;
    private Date modifiedDate;
    
	public LocationDepartment() {
		super();
	}

	public LocationDepartment(Integer locDepId) {
		this.locDepId = locDepId;
	}

	public LocationDepartment(CustomerDetails customerDetails,CompanyDetails companyDetails, Integer locDepId, LocationDetails location, DepartmentDetails department, int createdBy, Date createdDate,
												int modifiedBy, Date modifiedDate) {
		this.customerDetails = customerDetails;
		this.companyDetails = companyDetails;
		this.locDepId = locDepId;
		this.location = location;
		this.department = department;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}
    
	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="Loc_Dep_Id", unique=true, nullable=false)
	public Integer getLocDepId() {
		return locDepId;
	}

	public void setLocDepId(Integer locDepId) {
		this.locDepId = locDepId;
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
	@JoinColumn(name="Company_Id")
	public CompanyDetails getCompanyDetails() {
		return companyDetails;
	}

	public void setCompanyDetails(CompanyDetails companyDetails) {
		this.companyDetails = companyDetails;
	}
	
@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Location_Id")
	public LocationDetails getLocation() {
		return location;
	}

	public void setLocation(LocationDetails location) {
		this.location = location;
	}

@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Department_Id")
	public DepartmentDetails getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentDetails department) {
		this.department = department;
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

	@Override
	public String toString() {
		return "LocationDepartment [customerDetails=" + customerDetails.getCustomerId()
				+ ", companyDetails=" + companyDetails.getCompanyId() + ", locDepId="
				+ locDepId + ", location=" + location.getLocationId() + ", department="
				+ department.getDepartmentId() + ", createdBy=" + createdBy + ", createdDate="
				+ createdDate + ", modifiedBy=" + modifiedBy
				+ ", modifiedDate=" + modifiedDate + "]";
	}


    
}
