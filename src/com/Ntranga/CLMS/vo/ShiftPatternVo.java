package com.Ntranga.CLMS.vo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ShiftPatternVo {

	// common fields
	private int customerId;
	private String customerName;
	private int companyId;
	private String companyName;
	private int countryId;
	private String countryName;
	
	private Integer locationDetailsId;
    private Integer plantDetailsId;
    private String locationName;
    private String plantName;
    
	private Integer createdBy;
	private int modifiedBy;
	
	private int shiftPatternId;
	private String shiftPatternCode;	

	private int shiftPatternDetailsId;
	private String shiftPatternName;
	private String totalPatternHours;
	private boolean hasAdditionalRules;
	private String definePatternBy;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy", timezone="IST")
	private Date transactionDate;
	private String status;
	
	private List<ShiftPatternAssignShiftsVo> patternAssignList;
	private List<ShiftPatternAdditionalRulesVo> patternAdditionalList;
	private String transDate;
	
	
	/**
	 * @return the transDate
	 */
	public String getTransDate() {
		return transDate;
	}
	/**
	 * @param transDate the transDate to set
	 */
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
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
	public int getCountryId() {
		return countryId;
	}
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public int getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public int getShiftPatternId() {
		return shiftPatternId;
	}
	public void setShiftPatternId(int shiftPatternId) {
		this.shiftPatternId = shiftPatternId;
	}
	public String getShiftPatternCode() {
		return shiftPatternCode;
	}
	public void setShiftPatternCode(String shiftPatternCode) {
		this.shiftPatternCode = shiftPatternCode;
	}
	public int getShiftPatternDetailsId() {
		return shiftPatternDetailsId;
	}
	public void setShiftPatternDetailsId(int shiftPatternDetailsId) {
		this.shiftPatternDetailsId = shiftPatternDetailsId;
	}
	public String getShiftPatternName() {
		return shiftPatternName;
	}
	public void setShiftPatternName(String shiftPatternName) {
		this.shiftPatternName = shiftPatternName;
	}
	public String getTotalPatternHours() {
		return totalPatternHours;
	}
	public void setTotalPatternHours(String totalPatternHours) {
		this.totalPatternHours = totalPatternHours;
	}
	public boolean isHasAdditionalRules() {
		return hasAdditionalRules;
	}
	public void setHasAdditionalRules(boolean hasAdditionalRules) {
		this.hasAdditionalRules = hasAdditionalRules;
	}
	public String getDefinePatternBy() {
		return definePatternBy;
	}
	public void setDefinePatternBy(String definePatternBy) {
		this.definePatternBy = definePatternBy;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<ShiftPatternAssignShiftsVo> getPatternAssignList() {
		return patternAssignList;
	}
	public void setPatternAssignList(
			List<ShiftPatternAssignShiftsVo> patternAssignList) {
		this.patternAssignList = patternAssignList;
	}
	public List<ShiftPatternAdditionalRulesVo> getPatternAdditionalList() {
		return patternAdditionalList;
	}
	public void setPatternAdditionalList(List<ShiftPatternAdditionalRulesVo> patternAdditionalList) {
		this.patternAdditionalList = patternAdditionalList;
	}		
	public Integer getLocationDetailsId() {
		return locationDetailsId;
	}
	public void setLocationDetailsId(Integer locationDetailsId) {
		this.locationDetailsId = locationDetailsId;
	}
	public Integer getPlantDetailsId() {
		return plantDetailsId;
	}
	public void setPlantDetailsId(Integer plantDetailsId) {
		this.plantDetailsId = plantDetailsId;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getPlantName() {
		return plantName;
	}
	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}
	
	
	
	@Override
	public String toString() {
		return "ShiftPatternVo [customerId=" + customerId + ", customerName="
				+ customerName + ", companyId=" + companyId + ", companyName="
				+ companyName + ", countryId=" + countryId + ", countryName="
				+ countryName + ", createdBy=" + createdBy + ", modifiedBy="
				+ modifiedBy + ", shiftPatternId=" + shiftPatternId
				+ ", shiftPatternCode=" + shiftPatternCode
				+ ", shiftPatternDetailsId=" + shiftPatternDetailsId
				+ ", shiftPatternName=" + shiftPatternName
				+ ", totalPatternHours=" + totalPatternHours
				+ ", hasAdditionalRules=" + hasAdditionalRules
				+ ", definePatternBy=" + definePatternBy + ", transactionDate="
				+ transactionDate + ", status=" + status
				+ ", patternAssignList=" + patternAssignList
				+ ", patternAdditionalList=" + patternAdditionalList + "]";
	}
	
	
	

}
