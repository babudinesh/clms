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
@Table(name="holiday_calendar_details")
public class HolidayCalendarDetails implements Serializable {

	private CustomerDetails customerDetails;
	private CompanyDetails companyDetails;
	private LocationDetails locationDetails;
	private Integer holidayCalendarId;
	private String holidayCalendarCode;
    private Integer createdBy;
    private Date createdDate;
    private Integer modifiedBy;
    private Date modifiedDate;
    
    
    public HolidayCalendarDetails(){
    	
    }
    
    public HolidayCalendarDetails(Integer holidayCalendarId) {
		this.holidayCalendarId = holidayCalendarId;
	}



	public HolidayCalendarDetails(CustomerDetails customerDetails,
			CompanyDetails companyDetails, LocationDetails locationDetails,
			Integer holidayCalendarId, String holidayCalendarCode,
			Integer createdBy, Date createdDate, Integer modifiedBy,
			Date modifiedDate) {
		this.customerDetails = customerDetails;
		this.companyDetails = companyDetails;
		this.locationDetails = locationDetails;
		this.holidayCalendarId = holidayCalendarId;
		this.holidayCalendarCode = holidayCalendarCode;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}

	@Id 
	@GeneratedValue(strategy=IDENTITY)
    @Column(name="Holiday_Calendar_Id", unique=true, nullable=false)
    public Integer getHolidayCalendarId() {
        return holidayCalendarId;
    }
    
    public void setHolidayCalendarId(Integer holidayCalendarId) {
        this.holidayCalendarId = holidayCalendarId;
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
	public LocationDetails getLocationDetails() {
		return locationDetails;
	}

	public void setLocationDetails(LocationDetails locationDetails) {
		this.locationDetails = locationDetails;
	}
	
	@Column(name="Holiday_Calendar_Code", nullable=false)
	 public String getHolidayCalendarCode() {
       return holidayCalendarCode;
    }
   
    public void setHolidayCalendarCode(String holidayCalendarCode) {
       this.holidayCalendarCode = holidayCalendarCode;
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
        return createdDate;
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
