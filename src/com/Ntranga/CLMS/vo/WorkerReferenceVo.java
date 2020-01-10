package com.Ntranga.CLMS.vo;
// Generated 20 Jul, 2016 1:01:39 PM by Hibernate Tools 3.6.0





public class WorkerReferenceVo  implements java.io.Serializable {


     private Integer referenceId;
     
     private String name;
     private String emailId;
     private String relationship;
     private String designation;
     private String contactNumber;
     

    public WorkerReferenceVo() {
    }


	public Integer getReferenceId() {
		return referenceId;
	}


	public void setReferenceId(Integer referenceId) {
		this.referenceId = referenceId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getRelationship() {
		return relationship;
	}


	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}


	public String getDesignation() {
		return designation;
	}


	public void setDesignation(String designation) {
		this.designation = designation;
	}


	public String getContactNumber() {
		return contactNumber;
	}


	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}


	public String getEmailId() {
		return emailId;
	}


	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	
    


}


