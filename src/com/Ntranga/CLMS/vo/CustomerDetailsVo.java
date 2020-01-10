package com.Ntranga.CLMS.vo;
// Generated May 18, 2016 5:45:39 PM by Hibernate Tools 3.6.0

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class CustomerDetailsVo implements java.io.Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = -3420775632203454306L;
	private Integer customerId;
	private String customerName;
	private String customerNameOtherLanguage;
	private String entityType;
	private String isMultinaional;
	private Integer numberOfCompanies;
	private String tinNumber;
	private String panNumber;
	private String pfNumber;
	private Date transactionDate;	
	private int customerCountry;
	private int industryId;
	private String IndustryName;
	private int sectorId;
	private String sectorName;
	private String mncCountries;
	private String sectorIds;
	private String industyIds;
	private String countryName;
	private String customerCode;
	private String isActive;
	private String legacyName;	
	List<CompanyVo> companyVoList = new ArrayList(); 
	private String companyBaseName;	
	private int totNumberOfCompanies;
	private int customerinfoId;
	private int index;
	private Integer createdBy;
	private Integer modifiedBy;	
	
	
	

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


	public int getIndex() {
		return index;
	}


	public void setIndex(int index) {
		this.index = index;
	}


	public int getCustomerinfoId() {
		return customerinfoId;
	}


	public void setCustomerinfoId(int customerinfoId) {
		this.customerinfoId = customerinfoId;
	}


	public String getCompanyBaseName() {
		return companyBaseName;
	}


	public void setCompanyBaseName(String companyBaseName) {
		this.companyBaseName = companyBaseName;
	}


	public int getTotNumberOfCompanies() {
		return totNumberOfCompanies;
	}


	public void setTotNumberOfCompanies(int totNumberOfCompanies) {
		this.totNumberOfCompanies = totNumberOfCompanies;
	}


	public String getLegacyName() {
		return legacyName;
	}


	public void setLegacyName(String legacyName) {
		this.legacyName = legacyName;
	}


	public String getIsActive() {
		return isActive;
	}


	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}


	public List<CompanyVo> getCompanyVoList() {
		return companyVoList;
	}


	public void setCompanyVoList(List<CompanyVo> companyVoList) {
		this.companyVoList = companyVoList;
	}
	
	

	


	public String getCustomerCode() {
		return customerCode;
	}


	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public int getCustomerCountry() {
		return customerCountry;
	}

	public void setCustomerCountry(int customerCountry) {
		this.customerCountry = customerCountry;
	}

	public String getMncCountries() {
		return mncCountries;
	}

	public void setMncCountries(String mncCountries) {
		this.mncCountries = mncCountries;
	}

	public String getSectorIds() {
		return sectorIds;
	}

	public void setSectorIds(String sectorIds) {
		this.sectorIds = sectorIds;
	}

	public String getIndustyIds() {
		return industyIds;
	}

	public void setIndustyIds(String industyIds) {
		this.industyIds = industyIds;
	}

	public int getSectorId() {
		return sectorId;
	}

	public void setSectorId(int sectorId) {
		this.sectorId = sectorId;
	}

	public String getSectorName() {
		return sectorName;
	}

	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
	}

	public int getIndustryId() {
		return industryId;
	}

	public void setIndustryId(int industryId) {
		this.industryId = industryId;
	}

	public String getIndustryName() {
		return IndustryName;
	}

	public void setIndustryName(String industryName) {
		IndustryName = industryName;
	}

	private IndustrySectorVo industrySectorVo;

	public IndustrySectorVo getIndustrySectorVo() {
		return industrySectorVo;
	}

	public void setIndustrySectorVo(IndustrySectorVo industrySectorVo) {
		this.industrySectorVo = industrySectorVo;
	}

	public CustomerDetailsVo() {
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerNameOtherLanguage() {
		return customerNameOtherLanguage;
	}

	public void setCustomerNameOtherLanguage(String customerNameOtherLanguage) {
		this.customerNameOtherLanguage = customerNameOtherLanguage;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getIsMultinaional() {
		return isMultinaional;
	}

	public void setIsMultinaional(String isMultinaional) {
		this.isMultinaional = isMultinaional;
	}

	public Integer getNumberOfCompanies() {
		return numberOfCompanies;
	}

	public void setNumberOfCompanies(Integer numberOfCompanies) {
		this.numberOfCompanies = numberOfCompanies;
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

	public String getPfNumber() {
		return pfNumber;
	}

	public void setPfNumber(String pfNumber) {
		this.pfNumber = pfNumber;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	
}
