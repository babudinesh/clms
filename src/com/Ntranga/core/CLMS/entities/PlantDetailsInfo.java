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
@Table(name="plant_details_info")
public class PlantDetailsInfo implements Serializable{

	private Integer plantDetailsId;
	private CustomerDetails customerDetails;
	private CompanyDetails companyDetails;
	private PlantDetails plantDetails;
	private Integer plantSequenceId;
	private String plantName;
	private String shortName;
	private LocationDetails locationDetails;
	private String contactName;
	private String phoneNumber;
	private String faxNumber;
	private String emailId;
	private String address1;
	private String address2;
	private String address3;
	private MCountry MCountry;
	private MState MState;
	private String cityName;
	private Integer pincode;
	private Date transactionDate;
	private String status;
	private int createdBy;
    private Date createdDate;
	private int modifiedBy;
    private Date modifiedDate;


	public PlantDetailsInfo() {
	}
	
	
	public PlantDetailsInfo(Integer plantDetailsId,
			CustomerDetails customerDetails, CompanyDetails companyDetails,
			PlantDetails plantDetails, Integer plantSequenceId,
			String plantName, String shortName, LocationDetails locationDetails, String contactName,
			String phoneNumber, String faxNumber, String emailId,
			String address1, String address2, String address3,
			com.Ntranga.core.CLMS.entities.MCountry mCountry,
			com.Ntranga.core.CLMS.entities.MState mState, String cityName,
			Integer pincode, Date transactionDate, String status,
			int createdBy, Date createdDate, int modifiedBy, Date modifiedDate) {
		this.plantDetailsId = plantDetailsId;
		this.customerDetails = customerDetails;
		this.companyDetails = companyDetails;
		this.plantDetails = plantDetails;
		this.plantSequenceId = plantSequenceId;
		this.plantName = plantName;
		this.shortName = shortName;
		this.locationDetails = locationDetails;
		this.contactName = contactName;
		this.phoneNumber = phoneNumber;
		this.faxNumber = faxNumber;
		this.emailId = emailId;
		this.address1 = address1;
		this.address2 = address2;
		this.address3 = address3;
		this.MCountry = mCountry;
		this.MState = mState;
		this.cityName = cityName;
		this.pincode = pincode;
		this.transactionDate = transactionDate;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}



	@Id
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="Plant_Details_Id")
    public Integer getPlantDetailsId() {
		return plantDetailsId;
	}

	public void setPlantDetailsId(Integer plantDetailsId) {
		this.plantDetailsId = plantDetailsId;
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
	@JoinColumn(name="Plant_Id")	
	public PlantDetails getPlantDetails() {
		return plantDetails;
	}

	public void setPlantDetails(PlantDetails privateDetails) {
		this.plantDetails = privateDetails;
	}
	
	@Column(name="Plant_Sequence_Id")
	public Integer getPlantSequenceId() {
		return plantSequenceId;
	}

	public void setPlantSequenceId(Integer plantSequenceId) {
		this.plantSequenceId = plantSequenceId;
	}

	@Column(name="Plant_Name")
	public String getPlantName() {
		return plantName;
	}

	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}

	@Column(name="Short_Name")
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Location_Id")
	public LocationDetails getLocationDetails() {
		return locationDetails;
	}

	public void setLocationDetails(LocationDetails locationDetails) {
		this.locationDetails = locationDetails;
	}


	@Column(name="Contact_Name")
	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	@Column(name="Phone_Number")
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Column(name="Fax_Number")
	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	@Column(name="Email_Id")
	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
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
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Country_Id")	
	public MCountry getMCountry() {
		return MCountry;
	}

	public void setMCountry(MCountry mCountry) {
		MCountry = mCountry;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="State_Id")	
	public MState getMState() {
		return MState;
	}

	public void setMState(MState mState) {
		MState = mState;
	}


	@Column(name="City_Name")
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}


	@Column(name="Pincode")
	public Integer getPincode() {
		return pincode;
	}

	public void setPincode(Integer pincode) {
		this.pincode = pincode;
	}


	@Column(name="Status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

@Temporal(TemporalType.DATE)
    @Column(name="Transaction_Date", nullable=false, length=10)
	public Date getTransactionDate() {
		return transactionDate;
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


	@Override
	public String toString() {
		return "PlantDetailsInfo [plantDetailsId=" + plantDetailsId
				+ ", customerDetails=" + customerDetails.getCustomerId() + ", companyDetails="
				+ companyDetails.getCompanyId() + ", plantDetails=" + plantDetails.getPlantId()
				+ ", plantSequenceId=" + plantSequenceId + ", plantName="
				+ plantName + ", shortName=" + shortName + ", locationDetails = "+locationDetails.getLocationId()+
				", contactName= "+ contactName + ", phoneNumber=" + phoneNumber + ", faxNumber="
				+ faxNumber + ", emailId=" + emailId + ", address1=" + address1
				+ ", address2=" + address2 + ", address3=" + address3
				+ ", MCountry=" + MCountry.getCountryId() + ", MState=" + MState.getStateId()
				+ ", cityName=" + cityName + ", pincode=" + pincode
				+ ", transactionDate=" + transactionDate + ", status=" + status
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", modifiedBy=" + modifiedBy + ", modifiedDate="
				+ modifiedDate + "]";
	}
    
    
    
}
