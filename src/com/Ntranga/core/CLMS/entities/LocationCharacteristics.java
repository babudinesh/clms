package com.Ntranga.core.CLMS.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="location_characteristics")
public class LocationCharacteristics implements Serializable {

	private LocationDetails locationDetails;
	private CustomerDetails customerDetails ;
	private CompanyDetails companyDetails ;
	private Integer locationCharacteristicId;
	private String CSTNumber ;
	private String LSTNumber ; 
	private String TANNumber ;
	private String shopActLicenseNumber ;
	private String isProfessionalTaxApplicable;
	private String isUnion ; 
	private String unionName ;
	private String pfAccountNumber ;
	private Date pfRegistrationDate;
	private Date pfStartDate ;
	private Integer pfType ;
	private String pfCircle ; 
	private String ESIAccountNumber ;
	private Date ESIRegistrationDate ; 
	private Date ESIStartDate ;
	private Integer createdBy;
    private Date createdDate;
	private Integer modifiedBy;
    private Date modifiedDate;
    private MCountry countryDetails;

	public LocationCharacteristics() {
		
	}

     
    public LocationCharacteristics(LocationDetails locationDetails, Integer locationCharacteristicId, MCountry countryDetails,
								String CSTNumber, String LSTNumber,String TANNumber, String shopActLicenseNumber, String isProfessionalTaxApplicable,
								String isUnion,String unionName, String pfAccountNumber,Date pfRegistrationDate, Date pfStartDate,
								Integer pfType, String pfCircle, String ESIAccountNumber, Date ESIRegistrationDate,
								Date eSIStartDate, Integer createdBy, Date createdDate, Integer modifiedBy,Date modifiedDate,
								CustomerDetails customerDetails, CompanyDetails companyDetails) {
		this.locationDetails = locationDetails;
		this.customerDetails = customerDetails;
		this.companyDetails = companyDetails;
		this.locationCharacteristicId = locationCharacteristicId;
		this.countryDetails = countryDetails;
		this.CSTNumber = CSTNumber;
		this.LSTNumber = LSTNumber;
		this.TANNumber = TANNumber;
		this.shopActLicenseNumber = shopActLicenseNumber;
		this.isUnion = isUnion;
		this.isProfessionalTaxApplicable = isProfessionalTaxApplicable;
		this.unionName = unionName;
		this.pfAccountNumber = pfAccountNumber;
		this.pfRegistrationDate = pfRegistrationDate;
		this.pfStartDate = pfStartDate;
		this.pfType = pfType;
		this.pfCircle = pfCircle;
		this.ESIAccountNumber = ESIAccountNumber;
		this.ESIRegistrationDate = ESIRegistrationDate;
		this.ESIStartDate = eSIStartDate;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Location_Characteristic_Id")
	public Integer getLocationCharacteristicId() {
		return locationCharacteristicId;
	}

	public void setLocationCharacteristicId(Integer locationCharacteristicId) {
		this.locationCharacteristicId = locationCharacteristicId;
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

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Country_Id")
    public MCountry getCountryDetails() {
		return countryDetails;
	}

	public void setCountryDetails(MCountry countryDetails) {
		this.countryDetails = countryDetails;
	}
	
	@Column(name="CST_NUmber", length = 45)
	public String getCSTNumber() {
		return CSTNumber;
	}

	public void setCSTNumber(String CSTNumber) {
		this.CSTNumber = CSTNumber;
	}

	@Column(name="LST_NUmber", length = 45)
	public String getLSTNumber() {
		return LSTNumber;
	}

	public void setLSTNumber(String LSTNumber) {
		this.LSTNumber = LSTNumber;
	}

	@Column(name="TAN_NUmber", length = 45)
	public String getTANNumber() {
		return TANNumber;
	}

	public void setTANNumber(String TANNumber) {
		this.TANNumber = TANNumber;
	}

	@Column(name="Shop_Act_License_Number", length = 45)
	public String getShopActLicenseNumber() {
		return shopActLicenseNumber;
	}

	public void setShopActLicenseNumber(String shopActLicenseNumber) {
		this.shopActLicenseNumber = shopActLicenseNumber;
	}
	
	@Column(name="Is_Professional_Tax_Applicable", length = 2)
	public String getIsProfessionalTaxApplicable() {
		return isProfessionalTaxApplicable;
	}

	public void setIsProfessionalTaxApplicable(
			String isProfessionalTaxApplicable) {
		this.isProfessionalTaxApplicable = isProfessionalTaxApplicable;
	}

	@Column(name="Is_Union", length = 2)
	public String getIsUnion() {
		return isUnion;
	}
    
	public void setIsUnion(String isUnion) {
		this.isUnion = isUnion;
	}
	@Column(name="Union_Name", length = 45 )
	public String getUnionName() {
		return unionName;
	}

	public void setUnionName(String unionName) {
		this.unionName = unionName;
	}

	@Column(name="PF_Account_Number", length = 20)
	public String getPfAccountNumber() {
		return pfAccountNumber;
	}

	public void setPfAccountNumber(String pfAccountNumber) {
		this.pfAccountNumber = pfAccountNumber;
	}

	@Column(name="PF_Registration_Date")
	public Date getPfRegistrationDate() {
		return pfRegistrationDate;
	}

	public void setPfRegistrationDate(Date date) {
		this.pfRegistrationDate = date;
	}

	@Column(name="PF_Start_Date")
	public Date getPfStartDate() {
		return pfStartDate;
	}

	public void setPfStartDate(Date pfStartDate) {
		this.pfStartDate = pfStartDate;
	}
	
	@Column(name="PF_Type_Id")
	public Integer getPfType() {
		return pfType;
	}

	public void setPfType(Integer pfType) {
		this.pfType = pfType;
	}

	@Column(name="PF_Circle", length = 45)
	public String getPfCircle() {
		return pfCircle;
	}

	public void setPfCircle(String pfCircle) {
		this.pfCircle = pfCircle;
	}

	@Column(name="ESI_Account_Number", length = 20)
	public String getESIAccountNumber() {
		return ESIAccountNumber;
	}

	public void setESIAccountNumber(String ESIAccountNumber) {
		this.ESIAccountNumber = ESIAccountNumber;
	}

	@Column(name="ESI_Registration_Date")
	public Date getESIRegistrationDate() {
		return ESIRegistrationDate;
	}

	public void setESIRegistrationDate(Date ESIRegistrationDate) {
		this.ESIRegistrationDate = ESIRegistrationDate;
	}

	@Column(name="ESI_Start_Date")
	public Date getESIStartDate() {
		return ESIStartDate;
	}

	public void setESIStartDate(Date ESIStartDate) {
		this.ESIStartDate = ESIStartDate;
	}

	@Column(name="Created_By", nullable=false)
	public Integer getCreatedBy() {
        return this.createdBy;
    }
    
    public void setCreatedBy(Integer createdBy) {
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
