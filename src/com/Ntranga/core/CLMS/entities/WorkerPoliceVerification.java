package com.Ntranga.core.CLMS.entities;
// Generated Jul 17, 2016 4:55:33 PM by Hibernate Tools 3.6.0


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
 * WorkJobDetails generated by hbm2java
 */
@SuppressWarnings("serial")
@Entity
@Table(name="worker_police_verification")
public class WorkerPoliceVerification  implements java.io.Serializable {

	private Integer policeVerificationId;
     private WorkerDetails workerDetails;
     private CustomerDetails customerDetails;
     private VendorDetails vendorDetails;
     private CompanyDetails companyDetails;
     private Integer countryId;
     private String fatherName;
     private String motherName;
     private String mobileNumber;
     private String language;
     private String nationality;
     private String education;
     private String previousEmployer;
     private String totalExperience;
     private String visibleIdentificationMarksOne;
     private String visibleIdentificationMarksTwo;     
     private String relativesOrFriendsName;
     private String phoneNumber;
     private String propertyDetails;
     private String hobbies;
     private String dresses;
     private String habit;
     private String socialActivity;
     private String prohibitedActivity;
     private String hiddenActivity;
     private String special;
     private String leftHandThumbPrint;
     private Date reviewDate;
     private String place;
     private String signature;
     private Date dateOne;
     private Date dateTwo;   
     private String letterNumber;
     private Date datedOn;
     private String nameOfContract;
     private String contractEngagedWith;
     private String signatureOfProprietor;
     private String letterPlace;   
     private Integer createdBy;
     private Date createdDate;
     private Integer modifiedBy;
     private Date modifiedDate;
     private String thePoliceStationDetailsOne;
     private String thePoliceStationDetailsTwo;
     private String policeStation;
     private String district;
     private String filePath;
     private String fileName;
     
     
     
   

    public WorkerPoliceVerification() {
    }

	
    public WorkerPoliceVerification(WorkerDetails workerDetails, CustomerDetails customerDetails, VendorDetails vendorDetails, CompanyDetails companyDetails, Date transactionDate, Integer reportingManager, Integer sequenceId, String jobType, String workSkill, Integer jobName, Integer departmentId,  Integer workOrderId, Integer createdBy, Date createdDate, Integer modifiedBy, Date modifiedDate) {
        this.workerDetails = workerDetails;
        this.customerDetails = customerDetails;
        this.vendorDetails = vendorDetails;
        this.companyDetails = companyDetails;    
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.modifiedBy = modifiedBy;
        this.modifiedDate = modifiedDate;
    }
    
   


	public WorkerPoliceVerification(Integer policeVerificationId) {
		// TODO Auto-generated constructor stub
		this.policeVerificationId = policeVerificationId;
	}


	@Id 
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="Police_Verification_Id", unique=true, nullable=false)
    public Integer getPoliceVerificationId() {
		return policeVerificationId;
	}


	public void setPoliceVerificationId(Integer policeVerificationId) {
		this.policeVerificationId = policeVerificationId;
	}
	
	


    
@ManyToOne(fetch=FetchType.LAZY)
@JoinColumn(name="Worker_Id", nullable=false)
public WorkerDetails getWorkerDetails() {
    return this.workerDetails;
}


	public void setWorkerDetails(WorkerDetails workerDetails) {
        this.workerDetails = workerDetails;
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
    @JoinColumn(name="Vendor_Id", nullable=false)
    public VendorDetails getVendorDetails() {
        return this.vendorDetails;
    }
    
    public void setVendorDetails(VendorDetails vendorDetails) {
        this.vendorDetails = vendorDetails;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Company_Id", nullable=false)
    public CompanyDetails getCompanyDetails() {
        return this.companyDetails;
    }
    
    public void setCompanyDetails(CompanyDetails companyDetails) {
        this.companyDetails = companyDetails;
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

   

	 @Column(name="Country_Id")
	public Integer getCountryId() {
		return countryId;
	}


	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	@Column(name="Father_Name")
	public String getFatherName() {
		return fatherName;
	}


	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	@Column(name="Mother_Name")
	public String getMotherName() {
		return motherName;
	}


	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	@Column(name="Language")
	public String getLanguage() {
		return language;
	}


	public void setLanguage(String language) {
		this.language = language;
	}

	@Column(name="Nationality")
	public String getNationality() {
		return nationality;
	}


	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	@Column(name="Education")
	public String getEducation() {
		return education;
	}


	public void setEducation(String education) {
		this.education = education;
	}

	@Column(name="Previous_Employer")
	public String getPreviousEmployer() {
		return previousEmployer;
	}


	public void setPreviousEmployer(String previousEmployer) {
		this.previousEmployer = previousEmployer;
	}

	@Column(name="Total_Experience")
	public String getTotalExperience() {
		return totalExperience;
	}


	public void setTotalExperience(String totalExperience) {
		this.totalExperience = totalExperience;
	}

	@Column(name="Visible_Identification_Marks_One")
	public String getVisibleIdentificationMarksOne() {
		return visibleIdentificationMarksOne;
	}


	public void setVisibleIdentificationMarksOne(String visibleIdentificationMarksOne) {
		this.visibleIdentificationMarksOne = visibleIdentificationMarksOne;
	}

	@Column(name="Visible_Identification_Marks_Two")
	public String getVisibleIdentificationMarksTwo() {
		return visibleIdentificationMarksTwo;
	}


	public void setVisibleIdentificationMarksTwo(String visibleIdentificationMarksTwo) {
		this.visibleIdentificationMarksTwo = visibleIdentificationMarksTwo;
	}

	

	@Column(name="Relatives_Or_Friends_Name")
	public String getRelativesOrFriendsName() {
		return relativesOrFriendsName;
	}


	public void setRelativesOrFriendsName(String relativesOrFriendsName) {
		this.relativesOrFriendsName = relativesOrFriendsName;
	}

	@Column(name="Phone_Number")
	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Column(name="Property_Details")
	public String getPropertyDetails() {
		return propertyDetails;
	}


	public void setPropertyDetails(String propertyDetails) {
		this.propertyDetails = propertyDetails;
	}

	@Column(name="Hobbies")
	public String getHobbies() {
		return hobbies;
	}


	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}

	@Column(name="Dresses")
	public String getDresses() {
		return dresses;
	}


	public void setDresses(String dresses) {
		this.dresses = dresses;
	}

	@Column(name="Habit")
	public String getHabit() {
		return habit;
	}


	public void setHabit(String habit) {
		this.habit = habit;
	}

	@Column(name="Social_Activity")
	public String getSocialActivity() {
		return socialActivity;
	}


	public void setSocialActivity(String socialActivity) {
		this.socialActivity = socialActivity;
	}

	@Column(name="Prohibited_Activity")
	public String getProhibitedActivity() {
		return prohibitedActivity;
	}


	public void setProhibitedActivity(String prohibitedActivity) {
		this.prohibitedActivity = prohibitedActivity;
	}

	@Column(name="Hidden_Activity")
	public String getHiddenActivity() {
		return hiddenActivity;
	}


	public void setHiddenActivity(String hiddenActivity) {
		this.hiddenActivity = hiddenActivity;
	}

	@Column(name="Special")
	public String getSpecial() {
		return special;
	}


	public void setSpecial(String special) {
		this.special = special;
	}

	@Column(name="Left_Hand_Thumb_Print")
	public String getLeftHandThumbPrint() {
		return leftHandThumbPrint;
	}


	public void setLeftHandThumbPrint(String leftHandThumbPrint) {
		this.leftHandThumbPrint = leftHandThumbPrint;
	}

	@Column(name="Review_Date")
	public Date getReviewDate() {
		return reviewDate;
	}


	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	@Column(name="place")
	public String getPlace() {
		return place;
	}


	public void setPlace(String place) {
		this.place = place;
	}

	@Column(name="Signature")
	public String getSignature() {
		return signature;
	}


	public void setSignature(String signature) {
		this.signature = signature;
	}

	@Column(name="Date_One")
	public Date getDateOne() {
		return dateOne;
	}


	public void setDateOne(Date dateOne) {
		this.dateOne = dateOne;
	}

	@Column(name="Date_Two")
	public Date getDateTwo() {
		return dateTwo;
	}


	public void setDateTwo(Date dateTwo) {
		this.dateTwo = dateTwo;
	}

	

	@Column(name="Letter_Number")
	public String getLetterNumber() {
		return letterNumber;
	}


	public void setLetterNumber(String letterNumber) {
		this.letterNumber = letterNumber;
	}

	@Column(name="Dated_On")
	public Date getDatedOn() {
		return datedOn;
	}


	public void setDatedOn(Date datedOn) {
		this.datedOn = datedOn;
	}

	@Column(name="Name_Of_Contract")
	public String getNameOfContract() {
		return nameOfContract;
	}


	public void setNameOfContract(String nameOfContract) {
		this.nameOfContract = nameOfContract;
	}

	@Column(name="Contract_Engaged_With")
	public String getContractEngagedWith() {
		return contractEngagedWith;
	}


	public void setContractEngagedWith(String contractEngagedWith) {
		this.contractEngagedWith = contractEngagedWith;
	}

	@Column(name="Signature_Of_Proprietor")
	public String getSignatureOfProprietor() {
		return signatureOfProprietor;
	}


	public void setSignatureOfProprietor(String signatureOfProprietor) {
		this.signatureOfProprietor = signatureOfProprietor;
	}

	@Column(name="Letter_Place")
	public String getLetterPlace() {
		return letterPlace;
	}


	public void setLetterPlace(String letterPlace) {
		this.letterPlace = letterPlace;
	}

	@Column(name="Mobile_Number")
	public String getMobileNumber() {
		return mobileNumber;
	}


	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	@Column(name="The_Police_Station_Details_One")
	public String getThePoliceStationDetailsOne() {
		return thePoliceStationDetailsOne;
	}


	public void setThePoliceStationDetailsOne(String thePoliceStationDetailsOne) {
		this.thePoliceStationDetailsOne = thePoliceStationDetailsOne;
	}

	@Column(name="The_Police_Station_Details_Two")
	public String getThePoliceStationDetailsTwo() {
		return thePoliceStationDetailsTwo;
	}


	public void setThePoliceStationDetailsTwo(String thePoliceStationDetailsTwo) {
		this.thePoliceStationDetailsTwo = thePoliceStationDetailsTwo;
	}

	@Column(name="Police_Station")
	public String getPoliceStation() {
		return policeStation;
	}


	public void setPoliceStation(String policeStation) {
		this.policeStation = policeStation;
	}

	@Column(name="District")
	public String getDistrict() {
		return district;
	}


	public void setDistrict(String district) {
		this.district = district;
	}

	@Column(name="File_Path")
	public String getFilePath() {
		return filePath;
	}


	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Column(name="File_Name")
	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
    
    
    
    
    

}

