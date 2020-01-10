package com.Ntranga.CLMS.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class LocationCharacteristicsVo {

	private int locationId;
	private int customerId;
	private int companyId;
	private int companyInfoId;
	private String locationName;
	private String locationCode;
	private int characteristicId; 
	private String cstNumber ;
	private String lstNumber ; 
	private String tanNumber ;
	private String shopActLicenseNumber ;
	private boolean isProfessionalTax;
	private boolean isUnion ; 
	private String unionName ;
	private String pfAccountNumber ;
	//@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy", timezone="IST")
	private Date pfRegistrationDate;
	//@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy", timezone="IST")
	private Date pfStartDate ;
	private int pfTypeId ;
	private String pfCircle ; 
	private String esiAccountNumber ;
	//@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy", timezone="IST")
	private Date esiRegistrationDate ; 
	//@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy", timezone="IST")
	private Date esiStartDate ;
	private int countryId;
	private int createdBy;
	private int modifiedBy;
	private String customerName;
	private String companyName;
	private int locationDetailsId;
	
	
	
	public int getLocationDetailsId() {
		return locationDetailsId;
	}
	public void setLocationDetailsId(int locationDetailsId) {
		this.locationDetailsId = locationDetailsId;
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
	/**
	 * @return the companyInfoId
	 */
	public int getCompanyInfoId() {
		return companyInfoId;
	}
	/**
	 * @param companyInfoId the companyInfoId to set
	 */
	public void setCompanyInfoId(int companyInfoId) {
		this.companyInfoId = companyInfoId;
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
	public int getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
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
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public int getLocationId() {
		return locationId;
	}
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public int getCharacteristicId() {
		return characteristicId;
	}
	public void setCharacteristicId(int characteristicId) {
		this.characteristicId = characteristicId;
	}
	public String getCstNumber() {
		return cstNumber;
	}
	public void setCstNumber(String cstNumber) {
		this.cstNumber = cstNumber;
	}
	public String getLstNumber() {
		return lstNumber;
	}
	public void setLstNumber(String lstNumber) {
		this.lstNumber = lstNumber;
	}
	public String getTanNumber() {
		return tanNumber;
	}
	public void setTanNumber(String tanNumber) {
		this.tanNumber = tanNumber;
	}
	public String getShopActLicenseNumber() {
		return shopActLicenseNumber;
	}
	public void setShopActLicenseNumber(String shopActLicenseNumber) {
		this.shopActLicenseNumber = shopActLicenseNumber;
	}
	
	public boolean getIsProfessionalTax() {
		return isProfessionalTax;
	}
	public void setIsProfessionalTax(boolean isProfessionalTax) {
		this.isProfessionalTax = isProfessionalTax;
	}
	public boolean getIsUnion() {
		return isUnion;
	}
	public void setIsUnion(boolean isUnion) {
		this.isUnion = isUnion;
	}
	public String getUnionName() {
		return unionName;
	}
	public void setUnionName(String unionName) {
		this.unionName = unionName;
	}
	public String getPfAccountNumber() {
		return pfAccountNumber;
	}
	public void setPfAccountNumber(String pfAccountNumber) {
		this.pfAccountNumber = pfAccountNumber;
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
	public int getPfTypeId() {
		return pfTypeId;
	}
	public void setPfTypeId(int pfTypeId) {
		this.pfTypeId = pfTypeId;
	}
	public String getPfCircle() {
		return pfCircle;
	}
	public void setPfCircle(String pfCircle) {
		this.pfCircle = pfCircle;
	}
	public String getEsiAccountNumber() {
		return esiAccountNumber;
	}
	public void setEsiAccountNumber(String esiAccountNumber) {
		this.esiAccountNumber = esiAccountNumber;
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
	
	@Override
	public String toString() {
		return "LocationCharacteristicsVo [locationId=" + locationId
				+ ", customerId=" + customerId + ", companyId=" + companyId
				+ ", locationName=" + locationName + ", locationCode="
				+ locationCode + ", characteristicId=" + characteristicId
				+ ", cstNumber=" + cstNumber + ", lstNumber=" + lstNumber
				+ ", tanNumber=" + tanNumber + ", shopActLicenseNumber="
				+ shopActLicenseNumber + ", isProfessionalTax="
				+ isProfessionalTax + ", isUnion=" + isUnion + ", unionName="
				+ unionName + ", pfAccountNumber=" + pfAccountNumber
				+ ", pfRegistrationDate=" + pfRegistrationDate
				+ ", pfStartDate=" + pfStartDate + ", pfTypeId=" + pfTypeId
				+ ", pfCircle=" + pfCircle + ", esiAccountNumber="
				+ esiAccountNumber + ", esiRegistrationDate="
				+ esiRegistrationDate + ", esiStartDate=" + esiStartDate + "]";
	}
	
	
	
}
