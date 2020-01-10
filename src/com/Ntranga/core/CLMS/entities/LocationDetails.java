package com.Ntranga.core.CLMS.entities;

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

import static javax.persistence.GenerationType.IDENTITY;
@Entity
@Table(name="location_details")
public class LocationDetails implements Serializable {

	private CustomerDetails customerDetails ;
	private CompanyDetails companyDetails ;
	private Integer locationId;
	private String locationCode;
	private int createdBy;
    private Date createdDate;
	private int modifiedBy;
    private Date modifiedDate;
    
    public LocationDetails(){
    	
    }
    
    public LocationDetails(Integer locationID){
    	this.locationId= locationID;
    }

	public LocationDetails(CustomerDetails customerDetails, CompanyDetails companyDetails, Integer locationId,String locationCode, 
												int createdBy, Date createdDate,int modifiedBy, Date modifiedDate) {
		this.customerDetails = customerDetails;
		this.companyDetails = companyDetails;
		this.locationId = locationId;
		this.locationCode = locationCode;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}

	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="Location_Id" ,unique=true , nullable=false)
	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
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

	@Column(name="Location_Code")
	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
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
		return "LocationDetails [customerDetails=" + customerDetails.getCustomerId()
				+ ", companyDetails=" + companyDetails.getCompanyId() + ", locationId="
				+ locationId + ", locationCode=" + locationCode
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", modifiedBy=" + modifiedBy + ", modifiedDate="
				+ modifiedDate + "]";
	}


    
    
}
