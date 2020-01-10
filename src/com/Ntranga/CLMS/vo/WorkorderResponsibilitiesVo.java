package com.Ntranga.CLMS.vo;

import java.io.Serializable;
import java.util.Date;


public class WorkorderResponsibilitiesVo implements Serializable {

	 private Integer responsibleId;
     private Integer workrorderDetailId;
     private Integer workorderDetailInfoId;
     private String characteristics;
     private String responsibilityTakenBy;
     private Integer companyShare;
     private Integer vendorShare;
     private String additionalInfo;
     private Integer createdBy;
     private Date createdDate;
     private Integer modifiedBy;
     private Date modifiedDate;
     
     
	public Integer getResponsibleId() {
		return responsibleId;
	}
	public void setResponsibleId(Integer responsibleId) {
		this.responsibleId = responsibleId;
	}	
	public Integer getWorkrorderDetailId() {
		return workrorderDetailId;
	}
	public void setWorkrorderDetailId(Integer workrorderDetailId) {
		this.workrorderDetailId = workrorderDetailId;
	}
	public Integer getWorkorderDetailInfoId() {
		return workorderDetailInfoId;
	}
	public void setWorkorderDetailInfoId(Integer workorderDetailInfoId) {
		this.workorderDetailInfoId = workorderDetailInfoId;
	}
	public String getCharacteristics() {
		return characteristics;
	}
	public void setCharacteristics(String characteristics) {
		this.characteristics = characteristics;
	}
	public String getResponsibilityTakenBy() {
		return responsibilityTakenBy;
	}
	public void setResponsibilityTakenBy(String responsibilityTakenBy) {
		this.responsibilityTakenBy = responsibilityTakenBy;
	}
	public Integer getCompanyShare() {
		return companyShare;
	}
	public void setCompanyShare(Integer companyShare) {
		this.companyShare = companyShare;
	}
	public Integer getVendorShare() {
		return vendorShare;
	}
	public void setVendorShare(Integer vendorShare) {
		this.vendorShare = vendorShare;
	}
	public String getAdditionalInfo() {
		return additionalInfo;
	}
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
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
     
     
	
}
