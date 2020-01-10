package com.Ntranga.CLMS.vo;
// Generated 22 Jun, 2016 4:22:24 PM by Hibernate Tools 3.6.0


import java.util.Date;

import com.Ntranga.core.DateHelper;

public class VendorDetailsVo  implements java.io.Serializable {


     /**
	 * 
	 */
	private static final long serialVersionUID = -5361134436007652857L;
	private Integer vendorDetailsInfoId;	
     private Date transactionDate;
     private Integer sequenceId;
     private String vendorName;
     private Integer vendorTypeId;
     private String telephoneNumber;
     private String faxNumber;
     private String email;
     private String website;
     private String natureOfWork;
     private String directorOwnerProprietorName;
     private Integer modeOfPaymentId;
     private Integer paymentFrequencyId;
     private Integer companyTypeId;
     private Integer registeredUnderId;
     private String registrationNumber;     
     private Date registrationDate;
     private String panNumber;
     private String serviceRegistrationNumber;
     private String vatNumber;
     private String cstNumber;
     private String tanNumber;
     private String lstNumber;
     private Integer employeeStrength;
     private Double lastYearSalesTurnover;
     private String currencyIn;
     private String securityDepositDetails;
     private String discounts;
     private String address1;
     private String address2;
     private String address3;
     private Integer country;
     private Integer state;
     private String city;
     private Integer pincode;
     private String isActive; 
     private int vendorId;
     private String vendorCode;
     private int companyId;
     private int venodrId;
     private String companyType;
     private String registrationAct;
     private String modeOfPayment;
     private String paymentFrequency;
     private String countryName;
     private String stateName;
     private String cityName;
     private String industyIds;
     private String subIndustryIds;
     private int customerId;
     private String vendorType;
     private String vendorStatus;
     private Date statusDate;
     private String reasonForChange;
     private Date vendorRegistrationDate;
     
     private String address;
     
     public Integer getLocationId() {
		return locationId;
	}


	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}


	public String getLocationName() {
		return locationName;
	}


	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}


	private Integer countryId;
     private Integer locationId;
     private String locationName;
     
     
     
     
     
     
     
     
     
     

    public Integer getCountryId() {
		return countryId;
	}


	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}


	public String getVendorType() {
		return vendorType;
	}


	public void setVendorType(String vendorType) {
		this.vendorType = vendorType;
	}


	public int getCustomerId() {
		return customerId;
	}


	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}


	public VendorDetailsVo() {
    }


    public VendorDetailsVo(Date transactionDate, Integer sequenceId, String vendorName, Integer vendorTypeId, String telephoneNumber, String faxNumber, String email, String website, Integer modeOfPaymentId, Integer paymentFrequencyId, Integer companyTypeId, Integer registeredUnderId, String registrationNumber, Date registrationDate, String panNumber, String serviceRegistrationNumber, String vatNumber, String cstNumber, String tanNumber, String lstNumber, Integer employeeStrength, Double lastYearSalesTurnover, String currencyIn, String securityDepositDetails, String discounts, String address1, String address2, String address3, Integer country, Integer state, String city, Integer pincode, String isActive) {
       
       this.transactionDate = transactionDate;
       this.sequenceId = sequenceId;
       this.vendorName = vendorName;
       this.vendorTypeId = vendorTypeId;
       this.telephoneNumber = telephoneNumber;
       this.faxNumber = faxNumber;
       this.email = email;
       this.website = website;
       this.modeOfPaymentId = modeOfPaymentId;
       this.paymentFrequencyId = paymentFrequencyId;
       this.companyTypeId = companyTypeId;
       this.registeredUnderId = registeredUnderId;
       this.registrationNumber = registrationNumber;
       this.registrationDate = registrationDate;
       this.panNumber = panNumber;
       this.serviceRegistrationNumber = serviceRegistrationNumber;
       this.vatNumber = vatNumber;
       this.cstNumber = cstNumber;
       this.tanNumber = tanNumber;
       this.lstNumber = lstNumber;
       this.employeeStrength = employeeStrength;
       this.lastYearSalesTurnover = lastYearSalesTurnover;
       this.currencyIn = currencyIn;
       this.securityDepositDetails = securityDepositDetails;
       this.discounts = discounts;
       this.address1 = address1;
       this.address2 = address2;
       this.address3 = address3;
       this.country = country;
       this.state = state;
       this.city = city;
       this.pincode = pincode;
       this.isActive = isActive;
     
    }


	public int getCompanyId() {
		return companyId;
	}


	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}


	public int getVenodrId() {
		return venodrId;
	}


	public void setVenodrId(int venodrId) {
		this.venodrId = venodrId;
	}


	public String getCompanyType() {
		return companyType;
	}


	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}


	public String getRegistrationAct() {
		return registrationAct;
	}


	public void setRegistrationAct(String registrationAct) {
		this.registrationAct = registrationAct;
	}


	public String getModeOfPayment() {
		return modeOfPayment;
	}


	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}


	public String getPaymentFrequency() {
		return paymentFrequency;
	}


	public void setPaymentFrequency(String paymentFrequency) {
		this.paymentFrequency = paymentFrequency;
	}


	public String getCountryName() {
		return countryName;
	}


	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}


	public String getStateName() {
		return stateName;
	}


	public void setStateName(String stateName) {
		this.stateName = stateName;
	}


	public String getCityName() {
		return cityName;
	}


	public void setCityName(String cityName) {
		this.cityName = cityName;
	}


	public String getIndustyIds() {
		return industyIds;
	}


	public void setIndustyIds(String industyIds) {
		this.industyIds = industyIds;
	}


	public String getSubIndustryIds() {
		return subIndustryIds;
	}


	public void setSubIndustryIds(String subIndustryIds) {
		this.subIndustryIds = subIndustryIds;
	}


	public int getVendorId() {
		return vendorId;
	}


	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}


	public String getVendorCode() {
		return vendorCode;
	}


	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}


	public Integer getVendorDetailsInfoId() {
		return vendorDetailsInfoId;
	}


	public void setVendorDetailsInfoId(Integer vendorDetailsInfoId) {
		this.vendorDetailsInfoId = vendorDetailsInfoId;
	}


	public Date getTransactionDate() {
		return transactionDate;
	}


	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}


	public Integer getSequenceId() {
		return sequenceId;
	}


	public void setSequenceId(Integer sequenceId) {
		this.sequenceId = sequenceId;
	}


	public String getVendorName() {
		return vendorName;
	}


	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}


	public Integer getVendorTypeId() {
		return vendorTypeId;
	}


	public void setVendorTypeId(Integer vendorTypeId) {
		this.vendorTypeId = vendorTypeId;
	}


	public String getTelephoneNumber() {
		return telephoneNumber;
	}


	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}


	public String getFaxNumber() {
		return faxNumber;
	}


	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getWebsite() {
		return website;
	}


	public void setWebsite(String website) {
		this.website = website;
	}


	public Integer getModeOfPaymentId() {
		return modeOfPaymentId;
	}


	public void setModeOfPaymentId(Integer modeOfPaymentId) {
		this.modeOfPaymentId = modeOfPaymentId;
	}


	public Integer getPaymentFrequencyId() {
		return paymentFrequencyId;
	}


	public void setPaymentFrequencyId(Integer paymentFrequencyId) {
		this.paymentFrequencyId = paymentFrequencyId;
	}


	public Integer getCompanyTypeId() {
		return companyTypeId;
	}


	public void setCompanyTypeId(Integer companyTypeId) {
		this.companyTypeId = companyTypeId;
	}


	public Integer getRegisteredUnderId() {
		return registeredUnderId;
	}


	public void setRegisteredUnderId(Integer registeredUnderId) {
		this.registeredUnderId = registeredUnderId;
	}


	public String getNatureOfWork() {
		return natureOfWork;
	}


	public void setNatureOfWork(String natureOfWork) {
		this.natureOfWork = natureOfWork;
	}


	public String getDirectorOwnerProprietorName() {
		return directorOwnerProprietorName;
	}


	public void setDirectorOwnerProprietorName(String directorOwnerProprietorName) {
		this.directorOwnerProprietorName = directorOwnerProprietorName;
	}


	public String getRegistrationNumber() {
		return registrationNumber;
	}


	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}


	public Date getRegistrationDate() {
		return registrationDate;
	}


	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}


	public String getPanNumber() {
		return panNumber;
	}


	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}


	public String getServiceRegistrationNumber() {
		return serviceRegistrationNumber;
	}


	public void setServiceRegistrationNumber(String serviceRegistrationNumber) {
		this.serviceRegistrationNumber = serviceRegistrationNumber;
	}


	public String getVatNumber() {
		return vatNumber;
	}


	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}


	public String getCstNumber() {
		return cstNumber;
	}


	public void setCstNumber(String cstNumber) {
		this.cstNumber = cstNumber;
	}


	public String getTanNumber() {
		return tanNumber;
	}


	public void setTanNumber(String tanNumber) {
		this.tanNumber = tanNumber;
	}


	public String getLstNumber() {
		return lstNumber;
	}


	public void setLstNumber(String lstNumber) {
		this.lstNumber = lstNumber;
	}


	public Integer getEmployeeStrength() {
		return employeeStrength;
	}


	public void setEmployeeStrength(Integer employeeStrength) {
		this.employeeStrength = employeeStrength;
	}


	public Double getLastYearSalesTurnover() {
		return lastYearSalesTurnover;
	}


	public void setLastYearSalesTurnover(Double lastYearSalesTurnover) {
		this.lastYearSalesTurnover = lastYearSalesTurnover;
	}


	public String getCurrencyIn() {
		return currencyIn;
	}


	public void setCurrencyIn(String currencyIn) {
		this.currencyIn = currencyIn;
	}


	public String getSecurityDepositDetails() {
		return securityDepositDetails;
	}


	public void setSecurityDepositDetails(String securityDepositDetails) {
		this.securityDepositDetails = securityDepositDetails;
	}


	public String getDiscounts() {
		return discounts;
	}


	public void setDiscounts(String discounts) {
		this.discounts = discounts;
	}


	public String getAddress1() {
		return address1;
	}


	public void setAddress1(String address1) {
		this.address1 = address1;
	}


	public String getAddress2() {
		return address2;
	}


	public void setAddress2(String address2) {
		this.address2 = address2;
	}


	public String getAddress3() {
		return address3;
	}


	public void setAddress3(String address3) {
		this.address3 = address3;
	}


	public Integer getCountry() {
		return country;
	}


	public void setCountry(Integer country) {
		this.country = country;
	}


	public Integer getState() {
		return state;
	}


	public void setState(Integer state) {
		this.state = state;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public Integer getPincode() {
		return pincode;
	}


	public void setPincode(Integer pincode) {
		this.pincode = pincode;
	}


	public String getIsActive() {
		return isActive;
	}


	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}


	public String getVendorStatus() {
		return vendorStatus;
	}


	public void setVendorStatus(String vendorStatus) {
		this.vendorStatus = vendorStatus;
	}


	public String getReasonForChange() {
		return reasonForChange;
	}


	public void setReasonForChange(String reasonForStatusChange) {
		this.reasonForChange = reasonForStatusChange;
	}


	public Date getStatusDate() {
		return statusDate;
	}


	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}


	public Date getVendorRegistrationDate() {
		return vendorRegistrationDate;
	}


	public void setVendorRegistrationDate(Date vendorRegistrationDate) {
		this.vendorRegistrationDate = vendorRegistrationDate;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}
  


}


