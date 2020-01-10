package com.Ntranga.CLMS.vo;
// Generated 20 Jul, 2016 1:01:39 PM by Hibernate Tools 3.6.0


import java.util.Date;



public class WorkerCertificationDetailsVo  implements java.io.Serializable {


     private Integer workerCertificateId;
    
     private String certificateName;
     private String issuingAuthority;
     private String validity;
     private Date validFrom;
     private Date validTo;
     private byte[] certificate;
     private String fileName;
     private String filePath;
  

    public WorkerCertificationDetailsVo() {
    }


	public Integer getWorkerCertificateId() {
		return workerCertificateId;
	}


	public void setWorkerCertificateId(Integer workerCertificateId) {
		this.workerCertificateId = workerCertificateId;
	}


	public String getCertificateName() {
		return certificateName;
	}


	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName;
	}


	public String getIssuingAuthority() {
		return issuingAuthority;
	}


	public void setIssuingAuthority(String issuingAuthority) {
		this.issuingAuthority = issuingAuthority;
	}


	public String getValidity() {
		return validity;
	}


	public void setValidity(String validity) {
		this.validity = validity;
	}


	public Date getValidFrom() {
		return validFrom;
	}


	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}


	public Date getValidTo() {
		return validTo;
	}


	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}


	public byte[] getCertificate() {
		return certificate;
	}


	public void setCertificate(byte[] certificate) {
		this.certificate = certificate;
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


