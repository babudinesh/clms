package com.Ntranga.CLMS.vo;
// Generated 16 Jul, 2016 7:27:01 PM by Hibernate Tools 3.6.0

import java.util.Date;
import java.util.List;



@SuppressWarnings("serial")
public class WorkerDetailsVo implements java.io.Serializable {

	private Integer workerInfoId;
	private Integer workerId;
	private int customerId;
	private int companyId;
	private int countryId;
	private int vendorId;
	private Date transactionDate;
	//private String status;
	private int sequenceId;
	private String isActive;
	private String reasonForStatus;
	private Date dateOfLeaving;
	private String firstName;
	private String middleName;
	private String lastName;
	private String fatherSpouseName;
	private String panNumber;
	private String age;
	private Date dateOfBirth;
	private Character gender;
	private String maritalStatus;
	private String bloodGroup;
	private String religion;
	private String phoneNumber;
	private String email;
	private String emergencyContactPerson;
	private String emergencyContactNumber;
	private String identificationType;
	private int createdBy;
	private Date createdDate;
	private int modifiedBy;
	private Date modifiedDate;
	private String workerCode;
	private String verificationStatus;
	

	private String workAddressPresent;
	private String presentAddressLine1;
	private String presentAddressLine2;
	private String presentAddressLine3;
	private Integer presentcountryId;
	private Integer presentStateId;
	private String presentCity;
	private Integer presentPinCode;
	
	private String workAddressPermanent;
	private String permanentAddressLine1;
	private String permanentAddressLine2;
	private String permanentAddressLine3;
	private Integer permanentCountryId;
	private Integer permanentStateId;
	private String permanentCity;
	private Integer permanentPinCode;
	private String fatherOrSpouse;
	private String filePath;
	
	private String customerName;
	private String companyName;
	private String vendorName;
	private String countryName;
	private Date bussinessFromDate;
	private Date bussinessEndDate;
	
	private List<WorkerIdentificationProofVo> identityList;
	private List<WorkerVerificationStatusVo> workerVerificationList;
	private List<LabourTimeVo> labourTimeDetails;
	private byte[] file;
	private Date dateOfJoining;
	private String shiftName;
	private String weeklyOff;

	private String imageName;
	private String imagePath;
	private boolean isSameAddress;

	private String year;
	private String month;

	private int verificationId;
	private int workJobDetailsId;
	private int workerBadgeId;
	
	
	private String acountHolderName;
	private String acountNumber;
	private String bankName;
	private String ifscCode;
	private String branchName;
	private String vendorCode;
	private boolean selected ;
	private String placeOfBirth;
	
	private String workSkill;
	private String jobName;
	private Integer jobCodeId;
	//private String userId;
	private String schemaName;
	
	private String motherName;
	private String language;
	private String nationality;
	private String education;
	private String domicile;
	private Integer locationId;
	private Integer departmentId;
	private Integer plantId;
	
	private java.sql.Date selectedDate;
	
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public String getVendorCode() {
		return vendorCode;
	}
	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}
	
	public String getAcountHolderName() {
		return acountHolderName;
	}
	public void setAcountHolderName(String acountHolderName) {
		this.acountHolderName = acountHolderName;
	}
	public String getAcountNumber() {
		return acountNumber;
	}
	public void setAcountNumber(String acountNumber) {
		this.acountNumber = acountNumber;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getIfscCode() {
		return ifscCode;
	}
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public void setSameAddress(boolean isSameAddress) {
		this.isSameAddress = isSameAddress;
	}
	public int getVerificationId() {
		return verificationId;
	}
	public void setVerificationId(int verificationId) {
		this.verificationId = verificationId;
	}
	public int getWorkJobDetailsId() {
		return workJobDetailsId;
	}
	public void setWorkJobDetailsId(int workJobDetailsId) {
		this.workJobDetailsId = workJobDetailsId;
	}
	public int getWorkerBadgeId() {
		return workerBadgeId;
	}
	public void setWorkerBadgeId(int workerBadgeId) {
		this.workerBadgeId = workerBadgeId;
	}
	public String getSchemaName() {
		return schemaName;
	}
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	
	
	
	public String getShiftName() {
		return shiftName;
	}
	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}
	public String getWeeklyOff() {
		return weeklyOff;
	}
	public void setWeeklyOff(String weeklyOff) {
		this.weeklyOff = weeklyOff;
	}
	public Integer getWorkerInfoId() {
		return workerInfoId;
	}
	public void setWorkerInfoId(Integer workerInfoId) {
		this.workerInfoId = workerInfoId;
	}
	public Integer getWorkerId() {
		return workerId;
	}
	public void setWorkerId(Integer workerId) {
		this.workerId = workerId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public int getCountryId() {
		return countryId;
	}
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
	public int getVendorId() {
		return vendorId;
	}
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public int getSequenceId() {
		return sequenceId;
	}
	public void setSequenceId(int sequenceId) {
		this.sequenceId = sequenceId;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getReasonForStatus() {
		return reasonForStatus;
	}
	public void setReasonForStatus(String reasonForStatus) {
		this.reasonForStatus = reasonForStatus;
	}
	public Date getDateOfLeaving() {
		return dateOfLeaving;
	}
	public void setDateOfLeaving(Date dateOfLeaving) {
		this.dateOfLeaving = dateOfLeaving;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFatherSpouseName() {
		return fatherSpouseName;
	}
	public void setFatherSpouseName(String fatherSpouseName) {
		this.fatherSpouseName = fatherSpouseName;
	}
	public String getPanNumber() {
		return panNumber;
	}
	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public Character getGender() {
		return gender;
	}
	public void setGender(Character gender) {
		this.gender = gender;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getBloodGroup() {
		return bloodGroup;
	}
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	public String getReligion() {
		return religion;
	}
	public void setReligion(String religion) {
		this.religion = religion;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmergencyContactPerson() {
		return emergencyContactPerson;
	}
	public void setEmergencyContactPerson(String emergencyContactPerson) {
		this.emergencyContactPerson = emergencyContactPerson;
	}
	public String getEmergencyContactNumber() {
		return emergencyContactNumber;
	}
	public void setEmergencyContactNumber(String emergencyContactNumber) {
		this.emergencyContactNumber = emergencyContactNumber;
	}
	public String getIdentificationType() {
		return identificationType;
	}
	public void setIdentificationType(String identificationType) {
		this.identificationType = identificationType;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public int getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getWorkerCode() {
		return workerCode;
	}
	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
	}
	public String getWorkAddressPresent() {
		return workAddressPresent;
	}
	public void setWorkAddressPresent(String workAddressPresent) {
		this.workAddressPresent = workAddressPresent;
	}
	public String getPresentAddressLine1() {
		return presentAddressLine1;
	}
	public void setPresentAddressLine1(String presentAddressLine1) {
		this.presentAddressLine1 = presentAddressLine1;
	}
	public String getPresentAddressLine2() {
		return presentAddressLine2;
	}
	public void setPresentAddressLine2(String presentAddressLine2) {
		this.presentAddressLine2 = presentAddressLine2;
	}
	public String getPresentAddressLine3() {
		return presentAddressLine3;
	}
	public void setPresentAddressLine3(String presentAddressLine3) {
		this.presentAddressLine3 = presentAddressLine3;
	}
	public Integer getPresentcountryId() {
		return presentcountryId;
	}
	public void setPresentcountryId(Integer presentcountryId) {
		this.presentcountryId = presentcountryId;
	}
	public Integer getPresentStateId() {
		return presentStateId;
	}
	public void setPresentStateId(Integer presentStateId) {
		this.presentStateId = presentStateId;
	}
	public String getPresentCity() {
		return presentCity;
	}
	public void setPresentCity(String presentCity) {
		this.presentCity = presentCity;
	}
	public Integer getPresentPinCode() {
		return presentPinCode;
	}
	public void setPresentPinCode(Integer presentPinCode) {
		this.presentPinCode = presentPinCode;
	}
	public String getWorkAddressPermanent() {
		return workAddressPermanent;
	}
	public void setWorkAddressPermanent(String workAddressPermanent) {
		this.workAddressPermanent = workAddressPermanent;
	}
	public String getPermanentAddressLine1() {
		return permanentAddressLine1;
	}
	public void setPermanentAddressLine1(String permanentAddressLine1) {
		this.permanentAddressLine1 = permanentAddressLine1;
	}
	public String getPermanentAddressLine2() {
		return permanentAddressLine2;
	}
	public void setPermanentAddressLine2(String permanentAddressLine2) {
		this.permanentAddressLine2 = permanentAddressLine2;
	}
	public String getPermanentAddressLine3() {
		return permanentAddressLine3;
	}
	public void setPermanentAddressLine3(String permanentAddressLine3) {
		this.permanentAddressLine3 = permanentAddressLine3;
	}
	public Integer getPermanentCountryId() {
		return permanentCountryId;
	}
	public void setPermanentCountryId(Integer permanentCountryId) {
		this.permanentCountryId = permanentCountryId;
	}
	public Integer getPermanentStateId() {
		return permanentStateId;
	}
	public void setPermanentStateId(Integer permanentStateId) {
		this.permanentStateId = permanentStateId;
	}
	public String getPermanentCity() {
		return permanentCity;
	}
	public void setPermanentCity(String permanentCity) {
		this.permanentCity = permanentCity;
	}
	public Integer getPermanentPinCode() {
		return permanentPinCode;
	}
	public void setPermanentPinCode(Integer permanentPinCode) {
		this.permanentPinCode = permanentPinCode;
	}
	public String getFatherOrSpouse() {
		return fatherOrSpouse;
	}
	public void setFatherOrSpouse(String fatherOrSpouse) {
		this.fatherOrSpouse = fatherOrSpouse;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public Date getBussinessFromDate() {
		return bussinessFromDate;
	}
	public void setBussinessFromDate(Date bussinessFromDate) {
		this.bussinessFromDate = bussinessFromDate;
	}
	public Date getBussinessEndDate() {
		return bussinessEndDate;
	}
	public void setBussinessEndDate(Date bussinessEndDate) {
		this.bussinessEndDate = bussinessEndDate;
	}
	public List<WorkerIdentificationProofVo> getIdentityList() {
		return identityList;
	}
	public void setIdentityList(List<WorkerIdentificationProofVo> identityList) {
		this.identityList = identityList;
	}
	public List<WorkerVerificationStatusVo> getWorkerVerificationList() {
		return workerVerificationList;
	}
	public void setWorkerVerificationList(
			List<WorkerVerificationStatusVo> workerVerificationList) {
		this.workerVerificationList = workerVerificationList;
	}
	public List<LabourTimeVo> getLabourTimeDetails() {
		return labourTimeDetails;
	}
	public void setLabourTimeDetails(List<LabourTimeVo> labourTimeDetails) {
		this.labourTimeDetails = labourTimeDetails;
	}
	public byte[] getFile() {
		return file;
	}
	public void setFile(byte[] file) {
		this.file = file;
	}
	public Date getDateOfJoining() {
		return dateOfJoining;
	}
	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}
	public String getVerificationStatus() {
		return verificationStatus;
	}
	public void setVerificationStatus(String verificationStatus) {
		this.verificationStatus = verificationStatus;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public boolean getIsSameAddress() {
		return isSameAddress;
	}
	public void setIsSameAddress(boolean isSameAddress) {
		this.isSameAddress = isSameAddress;
	}

	public String getWorkSkill() {
		return workSkill;
	}
	public void setWorkSkill(String workSkill) {
		this.workSkill = workSkill;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public java.sql.Date getSelectedDate() {
		return selectedDate;
	}
	public void setSelectedDate(java.sql.Date selectedDate) {
		this.selectedDate = selectedDate;
	}
	public Integer getJobCodeId() {
		return jobCodeId;
	}
	public void setJobCodeId(Integer jobCodeId) {
		this.jobCodeId = jobCodeId;
	}
	/*public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}*/

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}
	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
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
	public String getDomicile() {
		return domicile;
	}
	public void setDomicile(String domicile) {
		this.domicile = domicile;
	}
	public Integer getLocationId() {
		return locationId;
	}
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	public Integer getPlantId() {
		return plantId;
	}
	public void setPlantId(Integer plantId) {
		this.plantId = plantId;
	}

	

	
	



}
