package com.Ntranga.CLMS.vo;

public class TimeExceptionVo {
	private String exceptionCode;
	private String exceptionName;
	private Integer exceptionId;
	private Integer customerId;
	private Integer companyId;
	private Integer countryId;
	private Integer timeExceptionId;
	
	
	
	public String getExceptionCode() {
		return exceptionCode;
	}
	
	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
	
	public String getExceptionName() {
		return exceptionName;
	}
	
	public void setExceptionName(String exceptionName) {
		this.exceptionName = exceptionName;
	}
	
	public Integer getExceptionId() {
		return exceptionId;
	}
	
	public void setExceptionId(Integer exceptionId) {
		this.exceptionId = exceptionId;
	}
	
	public Integer getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	
	public Integer getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	
	public Integer getCountryId() {
		return countryId;
	}
	
	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}
	
	public Integer getTimeExceptionId() {
		return timeExceptionId;
	}
	
	public void setTimeExceptionId(Integer timeExceptionId) {
		this.timeExceptionId = timeExceptionId;
	}

	@Override
	public String toString() {
		return "TimeExceptionVo [exceptionCode=" + exceptionCode
				+ ", exceptionName=" + exceptionName + ", exceptionId="
				+ exceptionId + ", customerId=" + customerId + ", companyId="
				+ companyId + ", countryId=" + countryId + ", timeExceptionId="
				+ timeExceptionId + "]";
	}
	
	
	
	
}
