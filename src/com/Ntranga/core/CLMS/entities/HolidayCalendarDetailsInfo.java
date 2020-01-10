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
@Table(name="holiday_calendar_details_info")
public class HolidayCalendarDetailsInfo implements Serializable{

	private CustomerDetails customerDetails;
	private CompanyDetails companyDetails;
	private HolidayCalendarDetails holidayCalendarDetails;
	private MCountry MCountry;
	private LocationDetails locationDetails;
	private Integer holidayCalendarDetailsId;
	private String status;
	private String calendarName;
	private Integer year;
    private Integer createdBy;
    private Date createdDate;
    private Integer modifiedBy;
    private Date modifiedDate;
    
    public HolidayCalendarDetailsInfo(){
    	
    }
    
    
    
    public HolidayCalendarDetailsInfo(CustomerDetails customerDetails,
			CompanyDetails companyDetails,
			HolidayCalendarDetails holidayCalendarDetails,
			com.Ntranga.core.CLMS.entities.MCountry mCountry,
			LocationDetails locationDetails, Integer holidayCalendarDetailsId, String status,
			String calendarName, Integer year, Integer createdBy,
			Date createdDate, Integer modifiedBy, Date modifiedDate) {
		this.customerDetails = customerDetails;
		this.companyDetails = companyDetails;
		this.holidayCalendarDetails = holidayCalendarDetails;
		this.MCountry = mCountry;
		this.locationDetails = locationDetails;
		this.holidayCalendarDetailsId = holidayCalendarDetailsId;
		this.status = status;
		this.calendarName = calendarName;
		this.year = year;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}



	public HolidayCalendarDetailsInfo(Integer holidayCalendarDetailsId) {
		this.holidayCalendarDetailsId = holidayCalendarDetailsId;
	}



	@Id 
	@GeneratedValue(strategy=IDENTITY)
    @Column(name="Holiday_Calendar_Details_Id", unique=true, nullable=false)
    public Integer getHolidayCalendarDetailsId() {
        return holidayCalendarDetailsId;
    }
    
    public void setHolidayCalendarDetailsId(Integer holidayCalendarDetailsId) {
        this.holidayCalendarDetailsId = holidayCalendarDetailsId;
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
	@JoinColumn(name="Holiday_Calendar_Id")
	public HolidayCalendarDetails getHolidayCalendarDetails() {
		return holidayCalendarDetails;
	}

	public void setHolidayCalendarDetails(HolidayCalendarDetails holidayCalendarDetails) {
		this.holidayCalendarDetails = holidayCalendarDetails;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Country_Id")
	public MCountry getMCountry() {
		return MCountry;
	}

	public void setMCountry(MCountry MCountry) {
		this.MCountry = MCountry;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Location_Id")
	public LocationDetails getLocationDetails() {
		return locationDetails;
	}

	public void setLocationDetails(LocationDetails locationDetails) {
		this.locationDetails = locationDetails;
	}
	
	 @Column(name="Calendar_Name", nullable=false)
	 public String getCalendarName() {
        return calendarName;
     }
    
     public void setCalendarName(String calendarName) {
        this.calendarName = calendarName;
     }
     
     
     @Column(name="year", nullable=false)
	 public Integer getYear() {
        return year;
     }
    
     public void setYear(Integer year) {
        this.year = year;
     }
    
     @Column(name="status", nullable=false)
	 public String getStatus() {
        return status;
     }
    
     public void setStatus(String status) {
        this.status = status;
     }
     
	@Column(name="Created_By", nullable=false)
    public Integer getCreatedBy() {
        return createdBy;
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

    
    @Column(name="Modified_By")
    public Integer getModifiedBy() {
        return modifiedBy;
    }
    
    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="Modified_Date", nullable=false, length=19)
    public Date getModifiedDate() {
        return modifiedDate;
    }
    
    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
