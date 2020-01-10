package com.Ntranga.CLMS.vo;
// Generated 16 Jul, 2016 7:27:01 PM by Hibernate Tools 3.6.0


import java.util.Date;
import java.io.File;

public class WorkerIdentificationProofVo  implements java.io.Serializable {


     private Integer workerIdentificationId;
     private Integer workerId ;
     private int countryId;
     private int customerId;
     private int companyId;
     private Date transactionDate;
     private int sequenceId;
    // private Integer identificationTypeId;
     private String identificationType;
     //private byte[] fileData;
     private String fileName;
     private String filePath;
     private File fileData;
     
     private String countryName;
     private String identificationNumber;
     
     
     
     
     
	public String getIdentificationNumber() {
		return identificationNumber;
	}
	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}
	public Integer getWorkerIdentificationId() {
		return workerIdentificationId;
	}
	public void setWorkerIdentificationId(Integer workerIdentificationId) {
		this.workerIdentificationId = workerIdentificationId;
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
	/*public Integer getIdentificationTypeId() {
		return identificationTypeId;
	}
	public void setIdentificationTypeId(Integer identificationTypeId) {
		this.identificationTypeId = identificationTypeId;
	}*/
	public String getIdentificationType() {
		return identificationType;
	}
	public void setIdentificationType(String identificationType) {
		this.identificationType = identificationType;
	}
	/*public byte[] getFileData() {
		return fileData;
	}
	public void setFileData(byte[] identificationProofDocument) {
		this.fileData = identificationProofDocument;
	}*/
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getCountryId() {
		return countryId;
	}
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public File getFileData() {
		return fileData;
	}
	public void setFileData(File fileData) {
		this.fileData = fileData;
	}
   
     
}


