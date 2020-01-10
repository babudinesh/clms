package com.Ntranga.CLMS.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CompanyDetailsInfoVo implements Serializable {

	private Integer companyInfoId;
    private Integer customerDetailsId;
    private Integer companyDetailsId;
    private String companyName;
    private String companyCode;
    private Integer countryId;
    private Integer uniqueId;
    private Integer companySequenceId;
    
    private Date transactionDate;
    private String isActive;
    private Integer sectorId;
    private String legacyId;
    private Integer industryId;
    private Integer subIndustryId;
    private String corporateIdentityNumber;
    
    private Date registrationDate;
    private String rocRegistrationNumber;
    private Integer registrationActId;
    private String serviceTaxRegistrationNumber;
    private String tinNumber;
    private String panNumber;
    
    private Date panRegistrationDate;
    private String pfNumber;
    
    private Date pfRegistrationDate;
    
    private Date pfStartDate;
    private Integer pfTypeId;
    private String esiNumber;
    
    private Date esiRegistrationDate;
    
    private Date esiStartDate;
    private Integer createdBy;
    @JsonIgnore
    private Date createdDate;
    private Integer modifiedBy;
    @JsonIgnore
    private Date modifiedDate;
    
    
	public CompanyDetailsInfoVo() {		
	}
	
	public Integer getCompanyInfoId() {
		return companyInfoId;
	}
	public void setCompanyInfoId(Integer companyInfoId) {
		this.companyInfoId = companyInfoId;
	}
	public Integer getCustomerDetailsId() {
		return customerDetailsId;
	}
	public void setCustomerDetailsId(Integer customerDetailsId) {
		this.customerDetailsId = customerDetailsId;
	}
	public Integer getCompanyDetailsId() {
		return companyDetailsId;
	}
	public void setCompanyDetailsId(Integer companyDetailsId) {
		this.companyDetailsId = companyDetailsId;
	}	
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Integer getCountryId() {
		return countryId;
	}
	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}
	public Integer getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(Integer uniqueId) {
		this.uniqueId = uniqueId;
	}
	public Integer getCompanySequenceId() {
		return companySequenceId;
	}
	public void setCompanySequenceId(Integer companySequenceId) {
		this.companySequenceId = companySequenceId;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public Integer getSectorId() {
		return sectorId;
	}
	public void setSectorId(Integer sectorId) {
		this.sectorId = sectorId;
	}
	public String getLegacyId() {
		return legacyId;
	}
	public void setLegacyId(String legacyId) {
		this.legacyId = legacyId;
	}
	public Integer getIndustryId() {
		return industryId;
	}
	public void setIndustryId(Integer industryId) {
		this.industryId = industryId;
	}
	public Integer getSubIndustryId() {
		return subIndustryId;
	}
	public void setSubIndustryId(Integer subIndustryId) {
		this.subIndustryId = subIndustryId;
	}
	public String getCorporateIdentityNumber() {
		return corporateIdentityNumber;
	}
	public void setCorporateIdentityNumber(String corporateIdentityNumber) {
		this.corporateIdentityNumber = corporateIdentityNumber;
	}
	public Date getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	public String getRocRegistrationNumber() {
		return rocRegistrationNumber;
	}
	public void setRocRegistrationNumber(String rocRegistrationNumber) {
		this.rocRegistrationNumber = rocRegistrationNumber;
	}
	public Integer getRegistrationActId() {
		return registrationActId;
	}
	public void setRegistrationActId(Integer registrationActId) {
		this.registrationActId = registrationActId;
	}
	public String getServiceTaxRegistrationNumber() {
		return serviceTaxRegistrationNumber;
	}
	public void setServiceTaxRegistrationNumber(String serviceTaxRegistrationNumber) {
		this.serviceTaxRegistrationNumber = serviceTaxRegistrationNumber;
	}
	public String getTinNumber() {
		return tinNumber;
	}
	public void setTinNumber(String tinNumber) {
		this.tinNumber = tinNumber;
	}
	public String getPanNumber() {
		return panNumber;
	}
	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}
	public Date getPanRegistrationDate() {
		return panRegistrationDate;
	}
	public void setPanRegistrationDate(Date panRegistrationDate) {
		this.panRegistrationDate = panRegistrationDate;
	}
	public String getPfNumber() {
		return pfNumber;
	}
	public void setPfNumber(String pfNumber) {
		this.pfNumber = pfNumber;
	}
	public Date getPfRegistrationDate() {
		return pfRegistrationDate;
	}
	public void setPfRegistrationDate(Date pfRegistrationDate) {
		this.pfRegistrationDate = pfRegistrationDate;
	}
	public Date getPfStartDate() {
		return pfStartDate;
	}
	public void setPfStartDate(Date pfStartDate) {
		this.pfStartDate = pfStartDate;
	}
	public Integer getPfTypeId() {
		return pfTypeId;
	}
	public void setPfTypeId(Integer pfTypeId) {
		this.pfTypeId = pfTypeId;
	}
	public String getEsiNumber() {
		return esiNumber;
	}
	public void setEsiNumber(String esiNumber) {
		this.esiNumber = esiNumber;
	}
	public Date getEsiRegistrationDate() {
		return esiRegistrationDate;
	}
	public void setEsiRegistrationDate(Date esiRegistrationDate) {
		this.esiRegistrationDate = esiRegistrationDate;
	}
	public Date getEsiStartDate() {
		return esiStartDate;
	}
	public void setEsiStartDate(Date esiStartDate) {
		this.esiStartDate = esiStartDate;
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
