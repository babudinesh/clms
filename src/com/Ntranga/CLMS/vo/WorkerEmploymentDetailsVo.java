package com.Ntranga.CLMS.vo;
// Generated 20 Jul, 2016 1:01:39 PM by Hibernate Tools 3.6.0


import java.util.Date;



public class WorkerEmploymentDetailsVo  implements java.io.Serializable {


     private Integer employmentDetailsId;    
   
     private String organizationName;
     private String contactNumber;
     private String designation;
     private boolean current;
     private Date startDate;
     private Date endDate;
     private String fileName;
     private String filePath;
     private Integer totalYears;
     

    public WorkerEmploymentDetailsVo() {
    }


	public Integer getEmploymentDetailsId() {
		return employmentDetailsId;
	}


	public void setEmploymentDetailsId(Integer employmentDetailsId) {
		this.employmentDetailsId = employmentDetailsId;
	}


	public String getOrganizationName() {
		return organizationName;
	}


	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}


	public String getContactNumber() {
		return contactNumber;
	}


	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}


	public String getDesignation() {
		return designation;
	}


	public void setDesignation(String designation) {
		this.designation = designation;
	}


	public boolean getCurrent() {
		return current;
	}


	public void setCurrent(boolean current) {
		this.current = current;
	}


	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public Integer getTotalYears() {
		return totalYears;
	}


	public void setTotalYears(Integer integer) {
		this.totalYears = integer;
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

	
   


}


