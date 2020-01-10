package com.Ntranga.CLMS.vo;

public class RulesForPaymentVerificationVo {

	private Integer complianceUniqueId;
	private boolean considerForVerification;
	private String doccumentName;
	private Integer paymentVerificationId;
	
	
	public Integer getComplianceUniqueId() {
		return complianceUniqueId;
	}
	
	public void setComplianceUniqueId(Integer complianceUniqueId) {
		this.complianceUniqueId = complianceUniqueId;
	}
	
	public boolean getConsiderForVerification() {
		return considerForVerification;
	}
	
	public void setConsiderForVerification(boolean considerForVerification) {
		this.considerForVerification = considerForVerification;
	}
	
	public String getDoccumentName() {
		return doccumentName;
	}
	
	public void setDoccumentName(String doccumentName) {
		this.doccumentName = doccumentName;
	}

	public Integer getPaymentVerificationId() {
		return paymentVerificationId;
	}

	public void setPaymentVerificationId(Integer paymentVerificationId) {
		this.paymentVerificationId = paymentVerificationId;
	}

	@Override
	public String toString() {
		return "RulesForPaymentVerificationVo [complianceUniqueId="
				+ complianceUniqueId + ", considerForVerification="
				+ considerForVerification + ", doccumentName=" + doccumentName
				+ ", paymentVerificationId=" + paymentVerificationId + "]";
	}

	
	
	
	
}
