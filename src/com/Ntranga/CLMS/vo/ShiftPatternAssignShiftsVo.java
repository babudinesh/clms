package com.Ntranga.CLMS.vo;

import java.util.List;

public class ShiftPatternAssignShiftsVo {

	private int customerId;
	private int  companyId;
	private int shiftPatternDetailsId;
	private int shiftPatternAssignId;
	private int shiftDefineId;
	private String shiftName;
	private String weekName;
    private String startTime;
    private String endTime;
    private String totalHours;
    private boolean offShift;
    private int daySequence;
    private String shiftCode;
    private List Value;
    
    
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
	public int getShiftPatternAssignId() {
		return shiftPatternAssignId;
	}
	public void setShiftPatternAssignId(int shiftPatternAssignId) {
		this.shiftPatternAssignId = shiftPatternAssignId;
	}
	public int getShiftDefineId() {
		return shiftDefineId;
	}
	public void setShiftDefineId(int shiftDefineId) {
		this.shiftDefineId = shiftDefineId;
	}
	public String getShiftName() {
		return shiftName;
	}
	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}	
	public String getWeekName() {
		return weekName;
	}
	public void setWeekName(String weekName) {
		this.weekName = weekName;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getTotalHours() {
		return totalHours;
	}
	public void setTotalHours(String scheduledHours) {
		this.totalHours = scheduledHours;
	}
	public boolean isOffShift() {
		return offShift;
	}
	public void setOffShift(boolean offShift) {
		this.offShift = offShift;
	}
	public int getDaySequence() {
		return daySequence;
	}
	public void setDaySequence(int daySequence) {
		this.daySequence = daySequence;
	}
	/**
	 * @return the shiftCode
	 */
	public String getShiftCode() {
		return shiftCode;
	}
	/**
	 * @param shiftCode the shiftCode to set
	 */
	public void setShiftCode(String shiftCode) {
		this.shiftCode = shiftCode;
	}
	@Override
	public String toString() {
		return "ShiftPatternAssignShifts [customerId=" + customerId
				+ ", companyId=" + companyId + ", shiftPatternDetailsId="
				+ shiftPatternDetailsId + ", shiftPatternAssignId="
				+ shiftPatternAssignId + ", shiftDefineId=" + shiftDefineId
				+ ", shiftName=" + shiftName + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", totalHours="
				+ totalHours + ", offShift=" + offShift
				+ ", daySequence=" + daySequence + "]";
	}
	/**
	 * @return the value
	 */
	public List getValue() {
		return Value;
	}
	/**
	 * @param obj the value to set
	 */
	public void setValue(List obj) {
		Value = obj;
	}
    
    
}
