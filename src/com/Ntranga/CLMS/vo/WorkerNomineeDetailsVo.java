package com.Ntranga.CLMS.vo;
// Generated 16 Jul, 2016 7:27:01 PM by Hibernate Tools 3.6.0


import java.util.Date;



public class WorkerNomineeDetailsVo  implements java.io.Serializable {


     private Integer workerNomineeId;
     private Integer workerId;
     private int customerId;
     private int companyId;
     private String memberName;
     private String relationship;
     private Date dateOfBirth;
     private Character gender;
     private String address;    
     private String phoneNumber;
     private String selectAsNominee;
     private String isMinor;
     private String guardianDetails;
     private Integer percentageShareInPF;    
     private int createdBy;
     private Date createdDate;
     private int modifiedBy;
     private Date modifiedDate;
     private Integer ageYears;
     private Integer ageMonths;
     
     
     
     
    




	public Integer getAgeYears() {
		return ageYears;
	}




	public void setAgeYears(Integer ageYears) {
		this.ageYears = ageYears;
	}




	public Integer getAgeMonths() {
		return ageMonths;
	}




	public void setAgeMonths(Integer ageMonths) {
		this.ageMonths = ageMonths;
	}




	public WorkerNomineeDetailsVo() {
    }

    
    
    
	public String getGuardianDetails() {
		return guardianDetails;
	}




	public void setGuardianDetails(String guardianDetails) {
		this.guardianDetails = guardianDetails;
	}




	public Integer getPercentageShareInPF() {
		return percentageShareInPF;
	}




	public void setPercentageShareInPF(Integer percentageShareInPF) {
		this.percentageShareInPF = percentageShareInPF;
	}




	public Integer getWorkerNomineeId() {
		return workerNomineeId;
	}

	public void setWorkerNomineeId(Integer workerNomineeId) {
		this.workerNomineeId = workerNomineeId;
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

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSelectAsNominee() {
		return selectAsNominee;
	}

	public void setSelectAsNominee(String selectAsNominee) {
		this.selectAsNominee = selectAsNominee;
	}

	public String getIsMinor() {
		return isMinor;
	}

	public void setIsMinor(String isMinor) {
		this.isMinor = isMinor;
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

	
   



}


