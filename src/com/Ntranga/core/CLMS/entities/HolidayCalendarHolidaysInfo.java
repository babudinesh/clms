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
@Table(name="holiday_calendar_holidays_info")
public class HolidayCalendarHolidaysInfo implements Serializable{

	private CustomerDetails customerDetails;
	private CompanyDetails companyDetails;
	private HolidayCalendarDetailsInfo holidayCalendarDetailsInfo;
	private Integer holidayId;
	private Date fromDate;
	private Date toDate;
	private String holidayName;
	private MHolidayType MHoliday;
    private Integer createdBy;
    private Date createdDate;
    private Integer modifiedBy;
    private Date modifiedDate;
    
    public HolidayCalendarHolidaysInfo(){
    	
    }
    
    
    public HolidayCalendarHolidaysInfo(CustomerDetails customerDetails,
			CompanyDetails companyDetails,
			HolidayCalendarDetailsInfo holidayCalendarDetailsInfo,
			Integer holidayId, Date fromDate, Date toDate, String holidayName,
			MHolidayType mHoliday, Integer createdBy, Date createdDate,
			Integer modifiedBy, Date modifiedDate) {
		this.customerDetails = customerDetails;
		this.companyDetails = companyDetails;
		this.holidayCalendarDetailsInfo = holidayCalendarDetailsInfo;
		this.holidayId = holidayId;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.holidayName = holidayName;
		this.MHoliday = mHoliday;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}


    
	public HolidayCalendarHolidaysInfo(Integer holidayId) {
		this.holidayId = holidayId;
	}


	@Id 
	@GeneratedValue(strategy=IDENTITY)
    @Column(name="Holiday_Id", unique=true, nullable=false)
    public Integer getHolidayId() {
        return holidayId;
    }
    
    public void setHolidayId(Integer holidayId) {
        this.holidayId = holidayId;
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
	@JoinColumn(name="Holiday_Calendar_Details_Id")
	public HolidayCalendarDetailsInfo getHolidayCalendarDetailsInfo() {
		return holidayCalendarDetailsInfo;
	}

	public void setHolidayCalendarDetailsInfo(HolidayCalendarDetailsInfo holidayCalendarDetailsInfo) {
		this.holidayCalendarDetailsInfo = holidayCalendarDetailsInfo;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Holiday_Type_Id")
	public MHolidayType getMHoliday() {
		return MHoliday;
	}

	public void setMHoliday(MHolidayType MHoliday) {
		this.MHoliday = MHoliday;
	}
    
	@Column(name="Holiday_Name")
	public String getHolidayName() {
		return holidayName;
	}

	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="From_Date")
	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="To_Date")
	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
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

    
    @Column(name="Modified_By", nullable=false)
    public Integer getModifiedBy() {
        return modifiedBy;
    }
    
    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="Modified_Date", nullable=false, length=19)
    public Date getModifiedDate() {
        return  modifiedDate;
    }
    
    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
