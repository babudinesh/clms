package com.Ntranga.CLMS.vo;


public class VendorDamageRecoveryVo {

	private Integer customerId;
	private Integer companyId;
	private Integer vendorDamageId;
	private Integer damageRecoveryId;
	private String recoveredDate;
	private String recoveredAmount;
	private String remarks;
	//private String currencyName;
	private Integer currencyId;
	
	
	/*public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}*/

	public Integer getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Integer currencyId) {
		this.currencyId = currencyId;
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
	
	public Integer getVendorDamageId() {
		return vendorDamageId;
	}
	
	public void setVendorDamageId(Integer vendorDamageId) {
		this.vendorDamageId = vendorDamageId;
	}
	
	public Integer getDamageRecoveryId() {
		return damageRecoveryId;
	}
	
	public void setDamageRecoveryId(Integer damageRecoveryId) {
		this.damageRecoveryId = damageRecoveryId;
	}
	
	public String getRecoveredDate() {
		return recoveredDate;
	}
	
	public void setRecoveredDate(String recoveredDate) {
		this.recoveredDate = recoveredDate;
	}
	
	public String getRecoveredAmount() {
		return recoveredAmount;
	}
	
	public void setRecoveredAmount(String recoveryAmount) {
		this.recoveredAmount = recoveryAmount;
	}
	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "VendorDamageRecoveryVo [customerId=" + customerId
				+ ", companyId=" + companyId + ", vendorDamageId="
				+ vendorDamageId + ", damageRecoveryId=" + damageRecoveryId
				+ ", recoveredDate=" + recoveredDate + ", recoveredAmount="
				+ recoveredAmount + ", remarks=" + remarks 
				//+ ", currencyName="+ currencyName 
				+ ", currencyId=" + currencyId + "]";
	}
	
	
}
