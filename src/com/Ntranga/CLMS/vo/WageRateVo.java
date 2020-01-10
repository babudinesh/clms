package com.Ntranga.CLMS.vo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class WageRateVo {

	private Integer customerId ;
	private Integer companyId ;
	private Integer vendorId;
	private Integer workOrderId;
	private String wageScaleId;
	private Integer wageRateId;
	private Integer wageRateDetailsId;
	private Integer jobCodeId;
	private Integer wageRateSequenceId;
	private String wageRateName;
	private String wageRateCode;
	private String wageRateDescription;
	private String jobCategory;
	private String jobType;
	private String status;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy", timezone="IST")
	private Date transactionDate;
	private Integer wageRateCurrencyId;
	private Integer baseRateCurrencyId;
	private String baseRateCurrency;
	private Integer overtimeCurrencyId;
	private String overtimeCurrency;
	private Double baseRate;
	private Double overtimeRate;
	private String baseRateFrequency;
	private String overtimeFrequency;
	private String customerName;
	private String companyName;
	private String vendorName;
	private String workOrderName;
	private String wageScaleName;
	private Integer createdBy;
    private Integer modifiedBy;
    private Integer countryId;
    private List<WageRateVo> currencyList;
    private String transDate;
    
    private Double workPieceRate;
    private String workPieceCurrency;
    private String workPieceCurrencyName;
    private String workPieceUnit;
    
    private Integer baseRateMargin;
    private Integer overtimeRateMargin;
    private Integer workRateMargin;
    
    
    
    
    
    
    
    
	public Integer getBaseRateMargin() {
		return baseRateMargin;
	}

	public void setBaseRateMargin(Integer baseRateMargin) {
		this.baseRateMargin = baseRateMargin;
	}

	public Integer getOvertimeRateMargin() {
		return overtimeRateMargin;
	}

	public void setOvertimeRateMargin(Integer overtimeRateMargin) {
		this.overtimeRateMargin = overtimeRateMargin;
	}

	public Integer getWorkRateMargin() {
		return workRateMargin;
	}

	public void setWorkRateMargin(Integer workRateMargin) {
		this.workRateMargin = workRateMargin;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getWorkOrderName() {
		return workOrderName;
	}

	public void setWorkOrderName(String workOrderName) {
		this.workOrderName = workOrderName;
	}

	public String getWageScaleName() {
		return wageScaleName;
	}

	public void setWageScaleName(String wageScaleName) {
		this.wageScaleName = wageScaleName;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public Integer getWorkOrderId() {
		return workOrderId;
	}

	public void setWorkOrderId(Integer workOrderId) {
		this.workOrderId = workOrderId;
	}

	public String getWageScaleId() {
		return wageScaleId;
	}

	public void setWageScaleId(String wageScaleId) {
		this.wageScaleId = wageScaleId;
	}

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
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
	
	public Integer getWageRateId() {
		return wageRateId;
	}
	
	public void setWageRateId(Integer wageRatId) {
		this.wageRateId = wageRatId;
	}
	
	public Integer getWageRateDetailsId() {
		return wageRateDetailsId;
	}
	
	public void setWageRateDetailsId(Integer wageRateDetailsId) {
		this.wageRateDetailsId = wageRateDetailsId;
	}
	
	public Integer getJobCodeId() {
		return jobCodeId;
	}
	
	public void setJobCodeId(Integer jobCodeDetailsId) {
		this.jobCodeId = jobCodeDetailsId;
	}
	
	public Integer getWageRateSequenceId() {
		return wageRateSequenceId;
	}
	
	public void setWageRateSequenceId(Integer wageRateSequenceId) {
		this.wageRateSequenceId = wageRateSequenceId;
	}
	
	public String getWageRateName() {
		return wageRateName;
	}
	
	public void setWageRateName(String wageRateName) {
		this.wageRateName = wageRateName;
	}
	
	public String getWageRateCode() {
		return wageRateCode;
	}
	
	public void setWageRateCode(String wageRateCode) {
		this.wageRateCode = wageRateCode;
	}
	
	public String getWageRateDescription() {
		return wageRateDescription;
	}
	
	public void setWageRateDescription(String wageRateDescription) {
		this.wageRateDescription = wageRateDescription;
	}
	
	public String getJobCategory() {
		return jobCategory;
	}
	
	public void setJobCategory(String jobCategory) {
		this.jobCategory = jobCategory;
	}
	
	public String getJobType() {
		return jobType;
	}
	
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Date getTransactionDate() {
		return transactionDate;
	}
	
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	
	public Integer getWageRateCurrencyId() {
		return wageRateCurrencyId;
	}
	
	public void setWageRateCurrencyId(Integer wageRateCurrencyId) {
		this.wageRateCurrencyId = wageRateCurrencyId;
	}
	
	public Integer getBaseRateCurrencyId() {
		return baseRateCurrencyId;
	}
	
	public void setBaseRateCurrencyId(Integer baseRateCurrencyId) {
		this.baseRateCurrencyId = baseRateCurrencyId;
	}
	
	public String getBaseRateCurrency() {
		return baseRateCurrency;
	}
	
	public void setBaseRateCurrency(String baseRateCurrency) {
		this.baseRateCurrency = baseRateCurrency;
	}
	
	public Integer getOvertimeCurrencyId() {
		return overtimeCurrencyId;
	}
	
	public void setOvertimeCurrencyId(Integer overtimeCurrencyId) {
		this.overtimeCurrencyId = overtimeCurrencyId;
	}
	
	public String getOvertimeCurrency() {
		return overtimeCurrency;
	}
	
	public void setOvertimeCurrency(String overtimeCurrency) {
		this.overtimeCurrency = overtimeCurrency;
	}
	
	public Double getBaseRate() {
		return baseRate;
	}
	
	public void setBaseRate(Double baseRate) {
		this.baseRate = baseRate;
	}
	
	public Double getOvertimeRate() {
		return overtimeRate;
	}
	
	public void setOvertimeRate(Double overtimeRate) {
		this.overtimeRate = overtimeRate;
	}
	
	public String getBaseRateFrequency() {
		return baseRateFrequency;
	}
	
	public void setBaseRateFrequency(String baseRateFrequency) {
		this.baseRateFrequency = baseRateFrequency;
	}
	
	public String getOvertimeFrequency() {
		return overtimeFrequency;
	}
	
	public void setOvertimeFrequency(String overtimeFrequency) {
		this.overtimeFrequency = overtimeFrequency;
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

	/**
	 * @return the countryId
	 */
	public Integer getCountryId() {
		return countryId;
	}

	/**
	 * @param countryId the countryId to set
	 */
	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public Double getWorkPieceRate() {
		return workPieceRate;
	}

	public void setWorkPieceRate(Double workPieceRate) {
		this.workPieceRate = workPieceRate;
	}

	public String getWorkPieceCurrency() {
		return workPieceCurrency;
	}

	public void setWorkPieceCurrency(String workPieceCurrency) {
		this.workPieceCurrency = workPieceCurrency;
	}

	public String getWorkPieceUnit() {
		return workPieceUnit;
	}

	public void setWorkPieceUnit(String workPieceUnit) {
		this.workPieceUnit = workPieceUnit;
	}

	

	public String getWorkPieceCurrencyName() {
		return workPieceCurrencyName;
	}

	public void setWorkPieceCurrencyName(String workPieceCurrencyName) {
		this.workPieceCurrencyName = workPieceCurrencyName;
	}

	@Override
	public String toString() {
		return "WageRateVo [customerId=" + customerId + ", companyId=" + companyId + ", vendorId=" + vendorId
				+ ", workOrderId=" + workOrderId + ", wageScaleId=" + wageScaleId + ", wageRateId=" + wageRateId
				+ ", wageRateDetailsId=" + wageRateDetailsId + ", jobCodeId=" + jobCodeId + ", wageRateSequenceId="
				+ wageRateSequenceId + ", wageRateName=" + wageRateName + ", wageRateCode=" + wageRateCode
				+ ", wageRateDescription=" + wageRateDescription + ", jobCategory=" + jobCategory + ", jobType="
				+ jobType + ", status=" + status + ", transactionDate=" + transactionDate + ", wageRateCurrencyId="
				+ wageRateCurrencyId + ", baseRateCurrencyId=" + baseRateCurrencyId + ", baseRateCurrency="
				+ baseRateCurrency + ", overtimeCurrencyId=" + overtimeCurrencyId + ", overtimeCurrency="
				+ overtimeCurrency + ", baseRate=" + baseRate + ", overtimeRate=" + overtimeRate
				+ ", baseRateFrequency=" + baseRateFrequency + ", overtimeFrequency=" + overtimeFrequency
				+ ", customerName=" + customerName + ", companyName=" + companyName + ", vendorName=" + vendorName
				+ ", workOrderName=" + workOrderName + ", wageScaleName=" + wageScaleName + ", createdBy=" + createdBy
				+ ", modifiedBy=" + modifiedBy + ", countryId=" + countryId + ", currencyList=" + currencyList
				+ ", transDate=" + transDate + ", workPieceRate=" + workPieceRate + ", workPieceCurrency="
				+ workPieceCurrency + ", workPieceUnit=" + workPieceUnit + "]";
	}

	/**
	 * @return the currencyList
	 */
	public List<WageRateVo> getCurrencyList() {
		return currencyList;
	}

	/**
	 * @param currencyList the currencyList to set
	 */
	public void setCurrencyList(List<WageRateVo> currencyList) {
		this.currencyList = currencyList;
	}
    
    
}
