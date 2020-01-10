package com.Ntranga.core.CLMS.entities;

import java.io.Serializable;
import java.sql.Time;
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

@Entity
@Table(name= "location_details_info")
public class LocationDetailsInfo implements Serializable {

	private Integer locationDetailsId;
	private CustomerDetails customerDetails ;
	private CompanyDetails companyDetails ;
	private LocationDetails locationDetails;
	private Integer locationSequenceId;
	private MLocation MLocationType;
	private String locationName;
	private String contactName;
	private String phoneNUmber;
	private String emailId;
	private String faxNumber;
	private String isHeadQuarter;
	private String address1;
	private String address2;
	private String address3;
	private String mCity;
	private MState mState;
	private MCountry mCountry;
	private Integer pincode;
	private String hazardousIndicator;
	private String riskIndicator;
	private String riskDetails;
	private String status;
	private String shiftId;	//Int
	private String holidayaCalendarId; //Int
	private String isShiftSystem;
	private String workWeekStartDay;
	private String workWeekEndDay;
	private Integer numberOfWorkingDays;
	private Integer businessHoursPerDay;
	private Integer standardHoursPerWeek;
	private Time businessStartTime;
	private Time businessEndTime;
	private Date transactionDate;
	private int createdBy;
    private Date createdDate;
	private int modifiedBy;
    private Date modifiedDate;

	public LocationDetailsInfo() {
		
	}

	public LocationDetailsInfo(Integer locationDetailsId, LocationDetails locationDetails, Integer locationSequenceId,
			MLocation mLocationType, String contactName, String phoneNUmber,String emailId, String faxNumber, String isHeadQuarter,
			String address1, String address2, String address3, String cityId, MState stateId, MCountry countryId, Integer pincode, 
			String status, String shiftId, String holidayaCalendarId, String isShiftSystem,
			String workWeekStartDay, String workWeekEndDay, Integer numberOfWorkingDays, Integer businessHoursPerDay,
			Integer standardHoursPerWeek, Time businessStartTime, Time businessEndTime, Date transactionDate, 
			int createdBy,Date createdDate, int modifiedBy, Date modifiedDate) {
		this.locationDetailsId = locationDetailsId;
		this.locationDetails = locationDetails;
		this.locationSequenceId = locationSequenceId;
		this.MLocationType = mLocationType;
		this.contactName = contactName;
		this.phoneNUmber = phoneNUmber;
		this.emailId = emailId;
		this.faxNumber = faxNumber;
		this.isHeadQuarter = isHeadQuarter;
		this.address1 = address1;
		this.address2 = address2;
		this.address3 = address3;
		this.mCity = cityId;
		this.mState = stateId;
		this.mCountry = countryId;
		this.pincode = pincode;
		this.status = status;
		this.shiftId = shiftId;
		this.holidayaCalendarId = holidayaCalendarId;
		this.isShiftSystem = isShiftSystem;
		this.workWeekStartDay = workWeekStartDay;
		this.workWeekEndDay = workWeekEndDay;
		this.numberOfWorkingDays = numberOfWorkingDays;
		this.businessHoursPerDay = businessHoursPerDay;
		this.standardHoursPerWeek = standardHoursPerWeek;
		this.businessStartTime = businessStartTime;
		this.businessEndTime = businessEndTime;
		this.transactionDate = transactionDate;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}

	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="Location_Details_Id", unique = true, nullable=false)
	public Integer getLocationDetailsId() {
		return locationDetailsId;
	}

	public void setLocationDetailsId(Integer locationDetailsId) {
		this.locationDetailsId = locationDetailsId;
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

	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name="Location_Id")
	public LocationDetails getLocationDetails() {
		return locationDetails;
	}

	public void setLocationDetails(LocationDetails locationDetails) {
		this.locationDetails = locationDetails;
	}
	
	@Column(name="Location_Sequence_Id")
	public Integer getLocationSequenceId() {
		return locationSequenceId;
	}

	public void setLocationSequenceId(Integer locationSequenceId) {
		this.locationSequenceId = locationSequenceId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Location_Type_Id")
	public MLocation getMLocationType() {
		return MLocationType;
	}

	public void setMLocationType(MLocation mLocationType) {
		MLocationType = mLocationType;
	}

	@Column(name="Location_Name")
	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	@Column(name="Contact_Name")
	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	@Column(name="Phone_Number")
	public String getPhoneNUmber() {
		return phoneNUmber;
	}

	public void setPhoneNUmber(String phoneNUmber) {
		this.phoneNUmber = phoneNUmber;
	}

	@Column(name="Email_Id")
	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	@Column(name="Fax_Number")
	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	@Column(name="Is_Headquarter")
	public String getIsHeadQuarter() {
		return isHeadQuarter;
	}

	public void setIsHeadQuarter(String isHeadQuarter) {
		this.isHeadQuarter = isHeadQuarter;
	}

	@Column(name="Address_1")
	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	@Column(name="Address_2")
	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	@Column(name="Address_3")
	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	@Column(name="City")
	public String getmCity() {
		return mCity;
	}

	public void setmCity(String mCity) {
		this.mCity = mCity;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="State_Id")
	public MState getmState() {
		return mState;
	}

	public void setmState(MState mState) {
		this.mState = mState;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Country_Id")
	public MCountry getmCountry() {
		return mCountry;
	}

	public void setmCountry(MCountry mCountry) {
		this.mCountry = mCountry;
	}

	@Column(name="Pincode")
	public Integer getPincode() {
		return pincode;
	}

	public void setPincode(Integer pincode) {
		this.pincode = pincode;
	}

	@Column(name="Hazardous_Indicator")
	public String getHazardousIndicator() {
		return hazardousIndicator;
	}

	public void setHazardousIndicator(String hazardousIndicator) {
		this.hazardousIndicator = hazardousIndicator;
	}

	@Column(name="Risk_Indicator")
	public String getRiskIndicator() {
		return riskIndicator;
	}

	public void setRiskIndicator(String riskIndicator) {
		this.riskIndicator = riskIndicator;
	}

	@Column(name="Risk_Details")
	public String getRiskDetails() {
		return riskDetails;
	}

	public void setRiskDetails(String riskDetails) {
		this.riskDetails = riskDetails;
	}

	@Column(name="Status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="Shift_Id")
	public String getShiftId() {
		return shiftId;
	}

	public void setShiftId(String shiftId) {
		this.shiftId = shiftId;
	}

	@Column(name="Holiday_Calendar_Id")
	public String getHolidayaCalendarId() {
		return holidayaCalendarId;
	}

	public void setHolidayaCalendarId(String holidayaCalendarId) {
		this.holidayaCalendarId = holidayaCalendarId;
	}

	@Column(name="Is_Shift_System")
	public String getIsShiftSystem() {
		return isShiftSystem;
	}

	public void setIsShiftSystem(String isShiftSystem) {
		this.isShiftSystem = isShiftSystem;
	}

	@Column(name="Work_Week_Start_Day")
	public String getWorkWeekStartDay() {
		return workWeekStartDay;
	}

	public void setWorkWeekStartDay(String workWeekStartDay) {
		this.workWeekStartDay = workWeekStartDay;
	}

	@Column(name="Work_Week_End_Day")
	public String getWorkWeekEndDay() {
		return workWeekEndDay;
	}

	public void setWorkWeekEndDay(String workWeekEndDay) {
		this.workWeekEndDay = workWeekEndDay;
	}

	@Column(name="Number_Of_Working_Days")
	public Integer getNumberOfWorkingDays() {
		return numberOfWorkingDays;
	}

	public void setNumberOfWorkingDays(Integer numberOfWorkingDays) {
		this.numberOfWorkingDays = numberOfWorkingDays;
	}

	@Column(name="Business_Hours_Per_Day")
	public Integer getBusinessHoursPerDay() {
		return businessHoursPerDay;
	}

	public void setBusinessHoursPerDay(Integer businessHoursPerDay) {
		this.businessHoursPerDay = businessHoursPerDay;
	}

	@Column(name="Standard_Hours_Per_Week")
	public Integer getStandardHoursPerWeek() {
		return standardHoursPerWeek;
	}

	public void setStandardHoursPerWeek(Integer standardHoursPerWeek) {
		this.standardHoursPerWeek = standardHoursPerWeek;
	}

	@Column(name="Business_Start_Time")
	public Time getBusinessStartTime() {
		return businessStartTime;
	}

	public void setBusinessStartTime(Time businessStartTime) {
		this.businessStartTime = businessStartTime;
	}

	@Column(name="Business_End_Time")
	public Time getBusinessEndTime() {
		return businessEndTime;
	}

	public void setBusinessEndTime(Time businessEndTime) {
		this.businessEndTime = businessEndTime;
	}

	@Temporal(TemporalType.DATE)
    @Column(name="Transaction_Date", length=10)
    public Date getTransactionDate() {
        return this.transactionDate;
    }
    
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
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
		return "LocationDetailsInfo [locationDetailsId=" + locationDetailsId
				+ ", locationDetails=" + locationDetails.getLocationId()
				+ ", locationSequenceId=" + locationSequenceId
				+ ", MLocationType=" + MLocationType.getLocationTypeId() + ", locationName="
				+ locationName + ", contactName=" + contactName
				+ ", phoneNUmber=" + phoneNUmber + ", emailId=" + emailId
				+ ", faxNumber=" + faxNumber + ", isHeadQuarter="
				+ isHeadQuarter + ", address1=" + address1 + ", address2="
				+ address2 + ", address3=" + address3 + ", mCity=" + mCity
				+ ", mState=" + mState.getStateId() + ", mcountry=" + mCountry.getCountryId()
				+ ", pincode=" + pincode + ", status=" + status + ", shiftId="
				+ shiftId + ", holidayaCalendarId=" + holidayaCalendarId
				+ ", isShiftSystem=" + isShiftSystem + ", workWeekStartDay="
				+ workWeekStartDay + ", workWeekEndDay=" + workWeekEndDay
				+ ", numberOfWorkingDays=" + numberOfWorkingDays
				+ ", businessHoursperDay=" + businessHoursPerDay
				+ ", standardHoursPerWeek=" + standardHoursPerWeek
				+ ", businessStartTime=" + businessStartTime
				+ ", businessEndTime=" + businessEndTime + ", transactionDate="
				+ transactionDate + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy
				+ ", modifiedDate=" + modifiedDate + "]";
	}

    
}
