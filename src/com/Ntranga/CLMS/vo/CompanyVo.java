package com.Ntranga.CLMS.vo;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;

public class CompanyVo {
	
	private String companyName;
	private String regNumber;
	private String panNumber;
	private String tinNumber;
	private Date incorporationDate;
	private String serviceTxNumber;
	private int totalEmployees;
	private String pfNumber;
	private String contactPrsonType;
	private String personName;
	private String emailId;
	private String officeNumber;
	private String mobileNumber;
	private String faxNumber;
	private int companyId;
	private int companyInfoId;
	private String isHoldingCompany;
	private int customerId;
	
	
	
	
	
	
	
	public int getCustomerId() {
		return customerId;
	}



	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}



	public int getCompanyInfoId() {
		return companyInfoId;
	}



	public void setCompanyInfoId(int companyInfoId) {
		this.companyInfoId = companyInfoId;
	}



	public String getIsHoldingCompany() {
		return isHoldingCompany;
	}



	public void setIsHoldingCompany(String isHoldingCompany) {
		this.isHoldingCompany = isHoldingCompany;
	}



	public CompanyVo(String companyName,int totalEmployees,int companyInfoId,String isHoldingCompany){
		this.companyName=companyName;
		this.totalEmployees=totalEmployees;
		this.companyInfoId=companyInfoId;
		this.isHoldingCompany = isHoldingCompany;
		
	}
	
	
	
	public CompanyVo() {
		// TODO Auto-generated constructor stub
	}



	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getContactPrsonType() {
		return contactPrsonType;
	}
	public void setContactPrsonType(String contactPrsonType) {
		this.contactPrsonType = contactPrsonType;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getOfficeNumber() {
		return officeNumber;
	}
	public void setOfficeNumber(String officeNumber) {
		this.officeNumber = officeNumber;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getFaxNumber() {
		return faxNumber;
	}
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}
	private List<ContactDetailsVo> contactDetailsList = new ArrayList<ContactDetailsVo>();
	
	
	 
	 
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getRegNumber() {
		return regNumber;
	}
	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}
	public String getPanNumber() {
		return panNumber;
	}
	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}
	public String getTinNumber() {
		return tinNumber;
	}
	public void setTinNumber(String tinNumber) {
		this.tinNumber = tinNumber;
	}
	public Date getIncorporationDate() {
		return incorporationDate;
	}
	public void setIncorporationDate(Date incorporationDate) {
		this.incorporationDate = incorporationDate;
	}
	public String getServiceTxNumber() {
		return serviceTxNumber;
	}
	public void setServiceTxNumber(String serviceTxNumber) {
		this.serviceTxNumber = serviceTxNumber;
	}
	public int getTotalEmployees() {
		return totalEmployees;
	}
	public void setTotalEmployees(int totalEmployees) {
		this.totalEmployees = totalEmployees;
	}
	public String getPfNumber() {
		return pfNumber;
	}
	public void setPfNumber(String pfNumber) {
		this.pfNumber = pfNumber;
	}
	public List<ContactDetailsVo> getContactDetailsList() {
		return contactDetailsList;
	}
	public void setContactDetailsList(List<ContactDetailsVo> contactDetailsList) {
		this.contactDetailsList = contactDetailsList;
	}
	 
	 
}
