package com.Ntranga.CLMS.vo;

import java.io.Serializable;


@SuppressWarnings("serial")
public class WorkerBadgeGenerationVo implements Serializable {

	private Integer workerBadgeId;
    private Integer workerDetailsId;
    private Integer customerDetailsId;
    private Integer vendorDetailsId;
    private Integer companyDetailsId;
    private Integer country;
    private String cardType;
    //private String isActive;
    private String badgeCode;
    private Boolean ppeIssued;
    private String description;
    private Integer createdBy;
    private Integer modifiedBy;
    
    private String customerName;
    private String companyName;
    private String vendorName;
    private String workerName;
    private String workerCode;
    private String[] locationId;
    
    
    
    
	
	public String[] getLocationId() {
		return locationId;
	}
	public void setLocationId(String[] locationId) {
		this.locationId = locationId;
	}
	public String getWorkerCode() {
		return workerCode;
	}
	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
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
	public String getWorkerName() {
		return workerName;
	}
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	public Integer getWorkerBadgeId() {
		return workerBadgeId;
	}
	public void setWorkerBadgeId(Integer workerBadgeId) {
		this.workerBadgeId = workerBadgeId;
	}
	public Integer getWorkerDetailsId() {
		return workerDetailsId;
	}
	public void setWorkerDetailsId(Integer workerDetailsId) {
		this.workerDetailsId = workerDetailsId;
	}
	public Integer getCustomerDetailsId() {
		return customerDetailsId;
	}
	public void setCustomerDetailsId(Integer customerDetailsId) {
		this.customerDetailsId = customerDetailsId;
	}
	public Integer getVendorDetailsId() {
		return vendorDetailsId;
	}
	public void setVendorDetailsId(Integer vendorDetailsId) {
		this.vendorDetailsId = vendorDetailsId;
	}
	public Integer getCompanyDetailsId() {
		return companyDetailsId;
	}
	public void setCompanyDetailsId(Integer companyDetailsId) {
		this.companyDetailsId = companyDetailsId;
	}
	public Integer getCountry() {
		return country;
	}
	public void setCountry(Integer country) {
		this.country = country;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	/*public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}*/
	public String getBadgeCode() {
		return badgeCode;
	}
	public void setBadgeCode(String badgeCode) {
		this.badgeCode = badgeCode;
	}
	public Boolean getPpeIssued() {
		return ppeIssued;
	}
	public void setPpeIssued(Boolean ppeIssued) {
		this.ppeIssued = ppeIssued;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Integer getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
    
    
    
}
