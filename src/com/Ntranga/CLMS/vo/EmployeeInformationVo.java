package com.Ntranga.CLMS.vo;
// Generated 9 Jul, 2016 6:33:01 PM by Hibernate Tools 3.6.0


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

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * EmployeeInformation generated by hbm2java
 */
@Entity
@Table(name="employee_information"
)
public class EmployeeInformationVo  implements java.io.Serializable {


     private Integer employeeId;     
     private String employeeCode;    
     private Date transactionDate;
     private Integer sequenceId;
     private Integer uniqueId;
     private String firstName;
     private String middleName;
     private String lastName;
     private String fatherOrSpouse;
     private String fatherOrSpouseName;
     private String panNumber;     
     private Date dateOfBirth;
     private String gender;
     private String maritalStatus;
     private String bloodGroup;
     private String phoneNumber;
     private String email;
     private String address1;
     private String address2;
     private String address3;
     private int country;
     private int state;
     private String city;
     private int pincode;
     private String isActive;
     private Integer jobTitleId;
     private Integer departmentId;
     private Integer reportingMangerId;     
     private Date startDate;
     private Integer customerId;
     private Integer companyId;
     private Integer locationId;
     private Integer jobTypeId;
     private Integer jobStatusId;
     private Integer employeeCountryId;
     private byte[] photo;
     private Integer jobId;
     private String jobCode;
     private String customerName;
     private String companyName;
     private String countryName;
     private String jobType;
     private String jobStatus;
     private String employeeCountryName;
     private String stateName;
     private String locationName;
     private String departmentName;   
     private Integer sectionId;
     private Integer workAreaId;
     private Integer plantId;
    
    

	public Integer getSectionId() {
		return sectionId;
	}






	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}






	public Integer getWorkAreaId() {
		return workAreaId;
	}






	public void setWorkAreaId(Integer workAreaId) {
		this.workAreaId = workAreaId;
	}






	public Integer getPlantId() {
		return plantId;
	}






	public void setPlantId(Integer plantId) {
		this.plantId = plantId;
	}






	public String getLocationName() {
		return locationName;
	}






	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}






	public String getDepartmentName() {
		return departmentName;
	}






	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}






	public String getStateName() {
		return stateName;
	}






	public void setStateName(String stateName) {
		this.stateName = stateName;
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






	public String getCountryName() {
		return countryName;
	}






	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}








	public String getJobType() {
		return jobType;
	}






	public void setJobType(String jobType) {
		this.jobType = jobType;
	}






	public String getJobStatus() {
		return jobStatus;
	}






	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}






	public String getEmployeeCountryName() {
		return employeeCountryName;
	}






	public void setEmployeeCountryName(String employeeCountryName) {
		this.employeeCountryName = employeeCountryName;
	}






	public String getJobCode() {
		return jobCode;
	}






	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}






	public Integer getJobId() {
		return jobId;
	}






	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}






	public EmployeeInformationVo() {
    }

	

    
    
    
	public String getBloodGroup() {
		return bloodGroup;
	}






	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}






	public Integer getJobTypeId() {
		return jobTypeId;
	}






	public void setJobTypeId(Integer jobTypeId) {
		this.jobTypeId = jobTypeId;
	}






	public Integer getJobStatusId() {
		return jobStatusId;
	}






	public void setJobStatusId(Integer jobStatusId) {
		this.jobStatusId = jobStatusId;
	}






	






	public Integer getEmployeeCountryId() {
		return employeeCountryId;
	}






	public void setEmployeeCountryId(Integer employeeCountryId) {
		this.employeeCountryId = employeeCountryId;
	}






	public byte[] getPhoto() {
		return photo;
	}






	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}






	public Integer getEmployeeId() {
		return employeeId;
	}


	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}


	public String getEmployeeCode() {
		return employeeCode;
	}


	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}


	public Date getTransactionDate() {
		return transactionDate;
	}


	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}


	public Integer getSequenceId() {
		return sequenceId;
	}


	public void setSequenceId(Integer sequenceId) {
		this.sequenceId = sequenceId;
	}


	public Integer getUniqueId() {
		return uniqueId;
	}


	public void setUniqueId(Integer uniqueId) {
		this.uniqueId = uniqueId;
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


	public String getFatherOrSpouse() {
		return fatherOrSpouse;
	}


	public void setFatherOrSpouse(String fatherOrSpouse) {
		this.fatherOrSpouse = fatherOrSpouse;
	}


	public String getFatherOrSpouseName() {
		return fatherOrSpouseName;
	}


	public void setFatherOrSpouseName(String fatherOrSpouseName) {
		this.fatherOrSpouseName = fatherOrSpouseName;
	}


	public String getPanNumber() {
		return panNumber;
	}


	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}


	public Date getDateOfBirth() {
		return dateOfBirth;
	}


	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getMaritalStatus() {
		return maritalStatus;
	}


	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
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


	public String getAddress1() {
		return address1;
	}


	public void setAddress1(String address1) {
		this.address1 = address1;
	}


	public String getAddress2() {
		return address2;
	}


	public void setAddress2(String address2) {
		this.address2 = address2;
	}


	public String getAddress3() {
		return address3;
	}


	public void setAddress3(String address3) {
		this.address3 = address3;
	}


	public int getCountry() {
		return country;
	}


	public void setCountry(int country) {
		this.country = country;
	}


	public int getState() {
		return state;
	}


	public void setState(int state) {
		this.state = state;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public int getPincode() {
		return pincode;
	}


	public void setPincode(int pincode) {
		this.pincode = pincode;
	}


	public String getIsActive() {
		return isActive;
	}


	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}


	public Integer getJobTitleId() {
		return jobTitleId;
	}


	public void setJobTitleId(Integer jobTitleId) {
		this.jobTitleId = jobTitleId;
	}


	public Integer getDepartmentId() {
		return departmentId;
	}


	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}


	public Integer getReportingMangerId() {
		return reportingMangerId;
	}


	public void setReportingMangerId(Integer reportingMangerId) {
		this.reportingMangerId = reportingMangerId;
	}


	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public Integer getCustomerId() {
		return customerId;
	}


	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}


	public Integer getCompanyId() {
		return companyId;
	}


	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}


	public Integer getLocationId() {
		return locationId;
	}


	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}
   
     



}


