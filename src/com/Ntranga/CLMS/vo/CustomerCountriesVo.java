package com.Ntranga.CLMS.vo;
// Generated 13 Jun, 2016 1:21:47 PM by Hibernate Tools 3.6.0


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


public class CustomerCountriesVo  implements java.io.Serializable {

  
     private Integer countryId;     
     private Date transactionDate;
     private String isActive;   
     private Integer languageId;
     private Integer currencyId;
     private String baseCompanyName;
     private String noOfCompanies;
     private int customerCountriesId;
     private String languageName;
     private String currencyName;
     private String countryName;
     private int index;
     
     
     
     
     
     
     

    public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public CustomerCountriesVo() {
    }

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
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

	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}

	public Integer getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Integer currencyId) {
		this.currencyId = currencyId;
	}

	public String getBaseCompanyName() {
		return baseCompanyName;
	}

	public void setBaseCompanyName(String baseCompanyName) {
		this.baseCompanyName = baseCompanyName;
	}

	public String getNoOfCompanies() {
		return noOfCompanies;
	}

	public void setNoOfCompanies(String noOfCompanies) {
		this.noOfCompanies = noOfCompanies;
	}

	public int getCustomerCountriesId() {
		return customerCountriesId;
	}

	public void setCustomerCountriesId(int customerCountriesId) {
		this.customerCountriesId = customerCountriesId;
	}

	



}


