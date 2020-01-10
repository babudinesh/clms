package com.Ntranga.CLMS.vo;
// Generated Jul 17, 2016 4:55:33 PM by Hibernate Tools 3.6.0


import java.io.File;
import java.util.Date;


public class WorkerMedicalExaminationVo  implements java.io.Serializable {

	private Integer medicalExaminationId;
     private Integer workerId ;
     private Integer customerId;
     private Integer VendorId;
     private Integer CompanyId;
     private String height;
     private String weight;
     private String pulse;
     private String bloodPressure;
     private String hearing;
     private String colorBlindness;
     private String eyeVision;
     private String r;
    private String bloodGroup;
    private String respiratorySystem;
    
    private String ansAutonomousNervesSystem;
    private String liverOrSpleen;
    private String pastHistory;
    private String anyOperationDoneInPast;
    private String oeema;
    private String cunosis;
    private String jaundice;
    private String medicalOfficerComment;
    private String anyOtherPhysicalDisability;
    private String medicallyFit;
    private Date reviewDate;
    private String signatureOfDoctor;
    private String filePath;
    
     private Integer createdBy;
     private Date createdDate;
     private Integer modifiedBy;
     private Date modifiedDate;
     private Integer countryId;
     private File fileData;
     private String fileName;
     private String weightType;
     private String heightType;
     
     
    public String getWeightType() {
		return weightType;
	}

	public void setWeightType(String weightType) {
		this.weightType = weightType;
	}

	public String getHeightType() {
		return heightType;
	}

	public void setHeightType(String heightType) {
		this.heightType = heightType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public File getFileData() {
		return fileData;
	}

	public void setFileData(File fileData) {
		this.fileData = fileData;
	}

	public WorkerMedicalExaminationVo() {
    }

	public Integer getMedicalExaminationId() {
		return medicalExaminationId;
	}

	public void setMedicalExaminationId(Integer medicalExaminationId) {
		this.medicalExaminationId = medicalExaminationId;
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
		return VendorId;
	}

	public void setVendorId(Integer vendorId) {
		VendorId = vendorId;
	}

	public Integer getCompanyId() {
		return CompanyId;
	}

	public void setCompanyId(Integer companyId) {
		CompanyId = companyId;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getPulse() {
		return pulse;
	}

	public void setPulse(String pulse) {
		this.pulse = pulse;
	}

	public String getBloodPressure() {
		return bloodPressure;
	}

	public void setBloodPressure(String bloodPressure) {
		this.bloodPressure = bloodPressure;
	}

	public String getHearing() {
		return hearing;
	}

	public void setHearing(String hearing) {
		this.hearing = hearing;
	}

	public String getColorBlindness() {
		return colorBlindness;
	}

	public void setColorBlindness(String colorBlindness) {
		this.colorBlindness = colorBlindness;
	}

	public String getEyeVision() {
		return eyeVision;
	}

	public void setEyeVision(String eyeVision) {
		this.eyeVision = eyeVision;
	}

	public String getR() {
		return r;
	}

	public void setR(String r) {
		this.r = r;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getRespiratorySystem() {
		return respiratorySystem;
	}

	public void setRespiratorySystem(String respiratorySystem) {
		this.respiratorySystem = respiratorySystem;
	}

	public String getAnsAutonomousNervesSystem() {
		return ansAutonomousNervesSystem;
	}

	public void setAnsAutonomousNervesSystem(String ansAutonomousNervesSystem) {
		this.ansAutonomousNervesSystem = ansAutonomousNervesSystem;
	}

	public String getLiverOrSpleen() {
		return liverOrSpleen;
	}

	public void setLiverOrSpleen(String liverOrSpleen) {
		this.liverOrSpleen = liverOrSpleen;
	}

	public String getPastHistory() {
		return pastHistory;
	}

	public void setPastHistory(String pastHistory) {
		this.pastHistory = pastHistory;
	}

	public String getAnyOperationDoneInPast() {
		return anyOperationDoneInPast;
	}

	public void setAnyOperationDoneInPast(String anyOperationDoneInPast) {
		this.anyOperationDoneInPast = anyOperationDoneInPast;
	}

	public String getOeema() {
		return oeema;
	}

	public void setOeema(String oeema) {
		this.oeema = oeema;
	}

	public String getCunosis() {
		return cunosis;
	}

	public void setCunosis(String cunosis) {
		this.cunosis = cunosis;
	}

	public String getJaundice() {
		return jaundice;
	}

	public void setJaundice(String jaundice) {
		this.jaundice = jaundice;
	}

	public String getMedicalOfficerComment() {
		return medicalOfficerComment;
	}

	public void setMedicalOfficerComment(String medicalOfficerComment) {
		this.medicalOfficerComment = medicalOfficerComment;
	}

	public String getAnyOtherPhysicalDisability() {
		return anyOtherPhysicalDisability;
	}

	public void setAnyOtherPhysicalDisability(String anyOtherPhysicalDisability) {
		this.anyOtherPhysicalDisability = anyOtherPhysicalDisability;
	}

	public String getMedicallyFit() {
		return medicallyFit;
	}

	public void setMedicallyFit(String medicallyFit) {
		this.medicallyFit = medicallyFit;
	}

	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	public String getSignatureOfDoctor() {
		return signatureOfDoctor;
	}

	public void setSignatureOfDoctor(String signatureOfDoctor) {
		this.signatureOfDoctor = signatureOfDoctor;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
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

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	
    
    
    

}


