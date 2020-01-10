package com.Ntranga.CLMS.vo;
// Generated Jul 17, 2016 4:55:33 PM by Hibernate Tools 3.6.0


import java.util.ArrayList;

import java.util.Date;
import java.util.List;


public class WorkerPoliceVerificationVo  implements java.io.Serializable {

	private Integer policeVerificationId;
     private Integer workerId;
     private Integer customerId;
     private Integer vendorId;
     private Integer companyId;
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
     private String policeStationAddress;
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
     
     private String presentAddress;
     private String permanentAddress;
     private String fileName;
     private String filePath;
     
     
     
     private List<WorkerIdentificationProofPoliceVerificationVo> identityList = new ArrayList();

    public WorkerPoliceVerificationVo() {
    }

    
    
    
    
    
    
    
	public String getFileName() {
		return fileName;
	}








	public void setFileName(String fileName) {
		this.fileName = fileName;
	}








	public String getFilePath() {
		return filePath;
	}








	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}








	public String getPresentAddress() {
		return presentAddress;
	}




	public void setPresentAddress(String presentAddress) {
		this.presentAddress = presentAddress;
	}




	public String getPermanentAddress() {
		return permanentAddress;
	}




	public void setPermanentAddress(String permanentAddress) {
		this.permanentAddress = permanentAddress;
	}




	public String getPoliceStation() {
		return policeStation;
	}




	public void setPoliceStation(String policeStation) {
		this.policeStation = policeStation;
	}




	public String getDistrict() {
		return district;
	}




	public void setDistrict(String district) {
		this.district = district;
	}




	public String getMobileNumber() {
		return mobileNumber;
	}




	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}




	public Integer getPoliceVerificationId() {
		return policeVerificationId;
	}

	public void setPoliceVerificationId(Integer policeVerificationId) {
		this.policeVerificationId = policeVerificationId;
	}

	public Integer getWorkerId() {
		return workerId;
	}

	public void setWorkerId(Integer workerId) {
		this.workerId = workerId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getPreviousEmployer() {
		return previousEmployer;
	}

	public void setPreviousEmployer(String previousEmployer) {
		this.previousEmployer = previousEmployer;
	}

	public String getTotalExperience() {
		return totalExperience;
	}

	public void setTotalExperience(String totalExperience) {
		this.totalExperience = totalExperience;
	}

	public String getVisibleIdentificationMarksOne() {
		return visibleIdentificationMarksOne;
	}

	public void setVisibleIdentificationMarksOne(String visibleIdentificationMarksOne) {
		this.visibleIdentificationMarksOne = visibleIdentificationMarksOne;
	}

	public String getVisibleIdentificationMarksTwo() {
		return visibleIdentificationMarksTwo;
	}

	public void setVisibleIdentificationMarksTwo(String visibleIdentificationMarksTwo) {
		this.visibleIdentificationMarksTwo = visibleIdentificationMarksTwo;
	}

	

	public String getRelativesOrFriendsName() {
		return relativesOrFriendsName;
	}

	public void setRelativesOrFriendsName(String relativesOrFriendsName) {
		this.relativesOrFriendsName = relativesOrFriendsName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPropertyDetails() {
		return propertyDetails;
	}

	public void setPropertyDetails(String propertyDetails) {
		this.propertyDetails = propertyDetails;
	}

	public String getHobbies() {
		return hobbies;
	}

	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}

	public String getDresses() {
		return dresses;
	}

	public void setDresses(String dresses) {
		this.dresses = dresses;
	}

	public String getHabit() {
		return habit;
	}

	public void setHabit(String habit) {
		this.habit = habit;
	}

	public String getSocialActivity() {
		return socialActivity;
	}

	public void setSocialActivity(String socialActivity) {
		this.socialActivity = socialActivity;
	}

	public String getProhibitedActivity() {
		return prohibitedActivity;
	}

	public void setProhibitedActivity(String prohibitedActivity) {
		this.prohibitedActivity = prohibitedActivity;
	}

	public String getHiddenActivity() {
		return hiddenActivity;
	}

	public void setHiddenActivity(String hiddenActivity) {
		this.hiddenActivity = hiddenActivity;
	}

	public String getSpecial() {
		return special;
	}

	public void setSpecial(String special) {
		this.special = special;
	}

	public String getLeftHandThumbPrint() {
		return leftHandThumbPrint;
	}

	public void setLeftHandThumbPrint(String leftHandThumbPrint) {
		this.leftHandThumbPrint = leftHandThumbPrint;
	}

	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public Date getDateOne() {
		return dateOne;
	}

	public void setDateOne(Date dateOne) {
		this.dateOne = dateOne;
	}

	public Date getDateTwo() {
		return dateTwo;
	}

	public void setDateTwo(Date dateTwo) {
		this.dateTwo = dateTwo;
	}

	public String getPoliceStationAddress() {
		return policeStationAddress;
	}

	public void setPoliceStationAddress(String policeStationAddress) {
		this.policeStationAddress = policeStationAddress;
	}

	public String getLetterNumber() {
		return letterNumber;
	}

	public void setLetterNumber(String letterNumber) {
		this.letterNumber = letterNumber;
	}

	public Date getDatedOn() {
		return datedOn;
	}

	public void setDatedOn(Date datedOn) {
		this.datedOn = datedOn;
	}

	public String getNameOfContract() {
		return nameOfContract;
	}

	public void setNameOfContract(String nameOfContract) {
		this.nameOfContract = nameOfContract;
	}

	public String getContractEngagedWith() {
		return contractEngagedWith;
	}

	public void setContractEngagedWith(String contractEngagedWith) {
		this.contractEngagedWith = contractEngagedWith;
	}

	public String getSignatureOfProprietor() {
		return signatureOfProprietor;
	}

	public void setSignatureOfProprietor(String signatureOfProprietor) {
		this.signatureOfProprietor = signatureOfProprietor;
	}

	public String getLetterPlace() {
		return letterPlace;
	}

	public void setLetterPlace(String letterPlace) {
		this.letterPlace = letterPlace;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}



   

	public List<WorkerIdentificationProofPoliceVerificationVo> getIdentityList() {
		return identityList;
	}




	public void setIdentityList(List<WorkerIdentificationProofPoliceVerificationVo> identityList) {
		this.identityList = identityList;
	}




	public String getThePoliceStationDetailsOne() {
		return thePoliceStationDetailsOne;
	}


	public void setThePoliceStationDetailsOne(String thePoliceStationDetailsOne) {
		this.thePoliceStationDetailsOne = thePoliceStationDetailsOne;
	}


	public String getThePoliceStationDetailsTwo() {
		return thePoliceStationDetailsTwo;
	}


	public void setThePoliceStationDetailsTwo(String thePoliceStationDetailsTwo) {
		this.thePoliceStationDetailsTwo = thePoliceStationDetailsTwo;
	}




	@Override
	public String toString() {
		return "WorkerPoliceVerificationVo [policeVerificationId=" + policeVerificationId + ", workerId=" + workerId
				+ ", customerId=" + customerId + ", vendorId=" + vendorId + ", companyId=" + companyId + ", countryId="
				+ countryId + ", fatherName=" + fatherName + ", motherName=" + motherName + ", mobileNumber="
				+ mobileNumber + ", language=" + language + ", nationality=" + nationality + ", education=" + education
				+ ", previousEmployer=" + previousEmployer + ", totalExperience=" + totalExperience
				+ ", visibleIdentificationMarksOne=" + visibleIdentificationMarksOne
				+ ", visibleIdentificationMarksTwo=" + visibleIdentificationMarksTwo + ", relativesOrFriendsName="
				+ relativesOrFriendsName + ", phoneNumber=" + phoneNumber + ", propertyDetails=" + propertyDetails
				+ ", hobbies=" + hobbies + ", dresses=" + dresses + ", habit=" + habit + ", socialActivity="
				+ socialActivity + ", prohibitedActivity=" + prohibitedActivity + ", hiddenActivity=" + hiddenActivity
				+ ", special=" + special + ", leftHandThumbPrint=" + leftHandThumbPrint + ", reviewDate=" + reviewDate
				+ ", place=" + place + ", signature=" + signature + ", dateOne=" + dateOne + ", dateTwo=" + dateTwo
				+ ", policeStationAddress=" + policeStationAddress + ", letterNumber=" + letterNumber + ", datedOn="
				+ datedOn + ", nameOfContract=" + nameOfContract + ", contractEngagedWith=" + contractEngagedWith
				+ ", signatureOfProprietor=" + signatureOfProprietor + ", letterPlace=" + letterPlace + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy + ", modifiedDate="
				+ modifiedDate + ", thePoliceStationDetailsOne=" + thePoliceStationDetailsOne
				+ ", thePoliceStationDetailsTwo=" + thePoliceStationDetailsTwo + "]";
	}
    

}


