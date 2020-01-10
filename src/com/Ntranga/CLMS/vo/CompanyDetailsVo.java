package com.Ntranga.CLMS.vo;
// Generated 6 Jun, 2016 10:39:27 AM by Hibernate Tools 3.6.0

public class CompanyDetailsVo  implements java.io.Serializable {


	private int companyId;
	private String companyName;
	private String companyCode;
	private String countryName;	
    private Integer customerId;
    private String  isActive;
    private int cmpId;
    private String customerCode;
    private String customerName;
    
    
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	/**
	 * @return the cmpId
	 */
	public int getCmpId() {
		return cmpId;
	}
	/**
	 * @param cmpId the cmpId to set
	 */
	public void setCmpId(int cmpId) {
		this.cmpId = cmpId;
	}
	
     
    
}


