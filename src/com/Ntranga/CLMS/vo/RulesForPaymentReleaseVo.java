package com.Ntranga.CLMS.vo;

import java.util.Date;
import java.util.List;

public class RulesForPaymentReleaseVo {

	private Integer rulesForPaymentId;
    private Integer sequenceId;
    private Integer uniqueId;
	private Integer customerId;
    private Integer companyId;
    private Integer countryId;
    private  Date transactionDate;
    private Integer liabilitiesPaid;
    private Integer period;
    private String periodType;
    private Integer beforeVerification;
    private Integer afterVerification;
    private String paymentMethod;
    private Integer createdBy;
    private Integer modifiedBy;
    private List<RulesForPaymentVerificationVo> verificationList;
    
    private String customerName;
    private String companyName;
    private String countryName;
    
    
    
	public Integer getRulesForPaymentId() {
		return rulesForPaymentId;
	}
	
	public void setRulesForPaymentId(Integer rulesForPaymentId) {
		this.rulesForPaymentId = rulesForPaymentId;
	}
	
	public Integer getSequenceId() {
		return sequenceId;
	}
	
	public void setSequenceId(Integer sequenceId) {
		this.sequenceId = sequenceId;
	}
	
	public Integer getUniqueId() {
		return uniqueId;
	}
	
	public void setUniqueId(Integer uniqueId) {
		this.uniqueId = uniqueId;
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
	
	public Date getTransactionDate() {
		return transactionDate;
	}
	
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	
	public Integer getLiabilitiesPaid() {
		return liabilitiesPaid;
	}
	
	public void setLiabilitiesPaid(Integer liabilitiesPaid) {
		this.liabilitiesPaid = liabilitiesPaid;
	}
	
	public Integer getPeriod() {
		return period;
	}
	public void setPeriod(Integer period) {
		this.period = period;
	}
	
	public String getPeriodType() {
		return periodType;
	}
	
	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}
	
	public Integer getBeforeVerification() {
		return beforeVerification;
	}
	
	public void setBeforeVerification(Integer beforeVerification) {
		this.beforeVerification = beforeVerification;
	}
	
	public Integer getAfterVerification() {
		return afterVerification;
	}
	
	public void setAfterVerification(Integer afterVerification) {
		this.afterVerification = afterVerification;
	}
	
	public String getPaymentMethod() {
		return paymentMethod;
	}
	
	public void setPaymentMethod(String obj) {
		this.paymentMethod = obj;
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
	
	public List<RulesForPaymentVerificationVo> getVerificationList() {
		return verificationList;
	}
	
	public void setVerificationList(
			List<RulesForPaymentVerificationVo> verificationList) {
		this.verificationList = verificationList;
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
	
	public String getCountryName() {
		return countryName;
	}
	
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	@Override
	public String toString() {
		return "RulesForPaymentReleaseVo [rulesForPaymentId="
				+ rulesForPaymentId + ", sequenceId=" + sequenceId
				+ ", uniqueId=" + uniqueId + ", customerId=" + customerId
				+ ", companyId=" + companyId + ", countryId=" + countryId
				+ ", transactionDate=" + transactionDate + ", liabilitiesPaid="
				+ liabilitiesPaid + ", period=" + period + ", periodType="
				+ periodType + ", beforeVerification=" + beforeVerification
				+ ", afterVerification=" + afterVerification
				+ ", paymentMethod=" + paymentMethod + ", createdBy="
				+ createdBy + ", modifiedBy=" + modifiedBy
				+ ", verificationList=" + verificationList + ", customerName="
				+ customerName + ", companyName=" + companyName
				+ ", countryName=" + countryName + "]";
	}
    
    
    
    
    
}
