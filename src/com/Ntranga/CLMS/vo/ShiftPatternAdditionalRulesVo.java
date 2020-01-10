package com.Ntranga.CLMS.vo;


public class ShiftPatternAdditionalRulesVo {
	
	private int customerId;
	private int  companyId;
	private int shiftPatternDetailsId;
	private int shiftPatternRulesId;
	private int shiftDefineId;
	private String shiftCode;
	private String shiftOccurrence;
	private String shiftWeekDay;
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
	public int getShiftPatternDetailsId() {
		return shiftPatternDetailsId;
	}
	public void setShiftPatternDetailsId(int shiftPatternDetailsId) {
		this.shiftPatternDetailsId = shiftPatternDetailsId;
	}
	public int getShiftPatternRulesId() {
		return shiftPatternRulesId;
	}
	public void setShiftPatternRulesId(int shiftPatternRulesId) {
		this.shiftPatternRulesId = shiftPatternRulesId;
	}
	public int getShiftDefineId() {
		return shiftDefineId;
	}
	public void setShiftDefineId(int shiftDefineId) {
		this.shiftDefineId = shiftDefineId;
	}
	public String getShiftCode() {
		return shiftCode;
	}
	public void setShiftCode(String shiftCode) {
		this.shiftCode = shiftCode;
	}
	public String getShiftOccurrence() {
		return shiftOccurrence;
	}
	public void setShiftOccurrence(String shiftOccurrence) {
		this.shiftOccurrence = shiftOccurrence;
	}
	public String getShiftWeekDay() {
		return shiftWeekDay;
	}
	public void setShiftWeekDay(String shiftWeekDay) {
		this.shiftWeekDay = shiftWeekDay;
	}
	@Override
	public String toString() {
		return "ShiftPatternAdditionalRulesVo [customerId=" + customerId
				+ ", companyId=" + companyId + ", shiftPatternDetailsId="
				+ shiftPatternDetailsId + ", shiftPatternRulesId="
				+ shiftPatternRulesId + ", shiftDefineId=" + shiftDefineId
				+ ", shiftCode=" + shiftCode + ", shiftOccurrence="
				+ shiftOccurrence + ", shiftWeekDay=" + shiftWeekDay + "]";
	}

	

}
