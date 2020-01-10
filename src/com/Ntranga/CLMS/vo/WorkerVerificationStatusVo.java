package com.Ntranga.CLMS.vo;

import java.io.File;
// Generated 18 Aug, 2016 7:24:50 PM by Hibernate Tools 3.6.0
import java.util.Date;

public class WorkerVerificationStatusVo  implements java.io.Serializable {

	private Integer verificationId;     
    private int customerId;
    private int companyId;
    private int vendorId;
    private int workerId;
    private String verificationType;
    private String isCleared;
    private String comments;  
   // private String isValidated;
    //private String badgeGenerationStatus;
    private String verificationStatus;
    private int countryId;
    private int createdBy;
    private Date createdDate;
    private int modifiedBy;
    private Date modifiedDate;
    private String fileName;
    private String location;
    private File attachment;
    private String attachmentName;
    private String status;

   public WorkerVerificationStatusVo() {
   }
   
   
   

	public File getAttachment() {
	return attachment;
}




public void setAttachment(File attachment) {
	this.attachment = attachment;
}




public String getAttachmentName() {
	return attachmentName;
}




public void setAttachmentName(String attachmentName) {
	this.attachmentName = attachmentName;
}




	public String getFileName() {
	return fileName;
}




public void setFileName(String fileName) {
	this.fileName = fileName;
}




public String getLocation() {
	return location;
}




public void setLocation(String location) {
	this.location = location;
}




	public Integer getVerificationId() {
		return verificationId;
	}

	public void setVerificationId(Integer verificationId) {
		this.verificationId = verificationId;
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

	public int getVendorId() {
		return vendorId;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	public int getWorkerId() {
		return workerId;
	}

	public void setWorkerId(int workerId) {
		this.workerId = workerId;
	}

	public String getVerificationType() {
		return verificationType;
	}

	public void setVerificationType(String verificationType) {
		this.verificationType = verificationType;
	}

	public String getIsCleared() {
		return isCleared;
	}

	public void setIsCleared(String isCleared) {
		this.isCleared = isCleared;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	

	/*public String getIsValidated() {
		return isValidated;
	}

	public void setIsValidated(String isValidated) {
		this.isValidated = isValidated;
	}

	public String getBadgeGenerationStatus() {
		return badgeGenerationStatus;
	}

	public void setBadgeGenerationStatus(String badgeGenerationStatus) {
		this.badgeGenerationStatus = badgeGenerationStatus;
	}*/
	
	public String getVerificationStatus() {
		return verificationStatus;
	}

	public void setVerificationStatus(String verificationStatus) {
		this.verificationStatus = verificationStatus;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    


}


