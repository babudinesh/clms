package com.Ntranga.CLMS.vo;

import java.io.Serializable;
import java.util.Date;

public class WageCalculatorVo implements Serializable {
	
	private Integer customerDetailsId;    
    private Integer companyDetailsId;
    private Integer vendorDetailsId;
    private Integer workerDetailsId;
    private Date transactionDate;
    private Integer sequenceId;
    private Integer atYear;
    private Integer atMonth;
    private Integer payableDays;
    private Double  overTimeHours;
    private Double  dailyRatePerDay;
    private Double  baseRatePerDay;
    private Double  overTimeRatePerHour;
    private Double  additionalAllowance;
    private Double  oneTimeEarnings;
    private Double  totalEarnings;
    private Double  pf;
    private Double  esi;
    private Double  lwf;
    private Double  pt;
    private Double  advance;
    private Double  damageOrLoss;
    private Double  fines;
    private Double  totalDeductions;
    private Double  grossSalaray;
    private Double  netSalary;

    private String workerCode;
    private String workerName;
    private String workerSkill;
    private String jobCode;
	public Integer getCustomerDetailsId() {
		return customerDetailsId;
	}
	public void setCustomerDetailsId(Integer customerDetailsId) {
		this.customerDetailsId = customerDetailsId;
	}
	public Integer getCompanyDetailsId() {
		return companyDetailsId;
	}
	public void setCompanyDetailsId(Integer companyDetailsId) {
		this.companyDetailsId = companyDetailsId;
	}
	public Integer getVendorDetailsId() {
		return vendorDetailsId;
	}
	public void setVendorDetailsId(Integer vendorDetailsId) {
		this.vendorDetailsId = vendorDetailsId;
	}
	public Integer getWorkerDetailsId() {
		return workerDetailsId;
	}
	public void setWorkerDetailsId(Integer workerDetailsId) {
		this.workerDetailsId = workerDetailsId;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public Integer getSequenceId() {
		return sequenceId;
	}
	public void setSequenceId(Integer sequenceId) {
		this.sequenceId = sequenceId;
	}
	public Integer getAtYear() {
		return atYear;
	}
	public void setAtYear(Integer atYear) {
		this.atYear = atYear;
	}
	public Integer getAtMonth() {
		return atMonth;
	}
	public void setAtMonth(Integer atMonth) {
		this.atMonth = atMonth;
	}
	public Integer getPayableDays() {
		return payableDays;
	}
	public void setPayableDays(Integer payableDays) {
		this.payableDays = payableDays;
	}
	public Double getOverTimeHours() {
		return overTimeHours;
	}
	public void setOverTimeHours(Double overTimeHours) {
		this.overTimeHours = overTimeHours;
	}
	public Double getDailyRatePerDay() {
		return dailyRatePerDay;
	}
	public void setDailyRatePerDay(Double dailyRatePerDay) {
		this.dailyRatePerDay = dailyRatePerDay;
	}
	public Double getBaseRatePerDay() {
		return baseRatePerDay;
	}
	public void setBaseRatePerDay(Double baseRatePerDay) {
		this.baseRatePerDay = baseRatePerDay;
	}
	public Double getOverTimeRatePerHour() {
		return overTimeRatePerHour;
	}
	public void setOverTimeRatePerHour(Double overTimeRatePerHour) {
		this.overTimeRatePerHour = overTimeRatePerHour;
	}
	public Double getAdditionalAllowance() {
		return additionalAllowance;
	}
	public void setAdditionalAllowance(Double additionalAllowance) {
		this.additionalAllowance = additionalAllowance;
	}
	public Double getOneTimeEarnings() {
		return oneTimeEarnings;
	}
	public void setOneTimeEarnings(Double oneTimeEarnings) {
		this.oneTimeEarnings = oneTimeEarnings;
	}
	public Double getTotalEarnings() {
		return totalEarnings;
	}
	public void setTotalEarnings(Double totalEarnings) {
		this.totalEarnings = totalEarnings;
	}
	public Double getPf() {
		return pf;
	}
	public void setPf(Double pf) {
		this.pf = pf;
	}
	public Double getEsi() {
		return esi;
	}
	public void setEsi(Double esi) {
		this.esi = esi;
	}
	public Double getLwf() {
		return lwf;
	}
	public void setLwf(Double lwf) {
		this.lwf = lwf;
	}
	public Double getPt() {
		return pt;
	}
	public void setPt(Double pt) {
		this.pt = pt;
	}
	public Double getAdvance() {
		return advance;
	}
	public void setAdvance(Double advance) {
		this.advance = advance;
	}
	public Double getDamageOrLoss() {
		return damageOrLoss;
	}
	public void setDamageOrLoss(Double damageOrLoss) {
		this.damageOrLoss = damageOrLoss;
	}
	public Double getFines() {
		return fines;
	}
	public void setFines(Double fines) {
		this.fines = fines;
	}
	public Double getTotalDeductions() {
		return totalDeductions;
	}
	public void setTotalDeductions(Double totalDeductions) {
		this.totalDeductions = totalDeductions;
	}
	public Double getGrossSalaray() {
		return grossSalaray;
	}
	public void setGrossSalaray(Double grossSalaray) {
		this.grossSalaray = grossSalaray;
	}
	public Double getNetSalary() {
		return netSalary;
	}
	public void setNetSalary(Double netSalary) {
		this.netSalary = netSalary;
	}
	public String getWorkerCode() {
		return workerCode;
	}
	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
	}
	public String getWorkerName() {
		return workerName;
	}
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	public String getWorkerSkill() {
		return workerSkill;
	}
	public void setWorkerSkill(String workerSkill) {
		this.workerSkill = workerSkill;
	}
	public String getJobCode() {
		return jobCode;
	}
	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}
	@Override
	public String toString() {
		return "WageCalculatorVo [customerDetailsId=" + customerDetailsId + ", companyDetailsId=" + companyDetailsId
				+ ", vendorDetailsId=" + vendorDetailsId + ", workerDetailsId=" + workerDetailsId + ", transactionDate="
				+ transactionDate + ", sequenceId=" + sequenceId + ", atYear=" + atYear + ", atMonth=" + atMonth
				+ ", payableDays=" + payableDays + ", overTimeHours=" + overTimeHours + ", dailyRatePerDay="
				+ dailyRatePerDay + ", baseRatePerDay=" + baseRatePerDay + ", overTimeRatePerHour="
				+ overTimeRatePerHour + ", additionalAllowance=" + additionalAllowance + ", oneTimeEarnings="
				+ oneTimeEarnings + ", totalEarnings=" + totalEarnings + ", pf=" + pf + ", esi=" + esi + ", lwf=" + lwf
				+ ", pt=" + pt + ", advance=" + advance + ", damageOrLoss=" + damageOrLoss + ", fines=" + fines
				+ ", totalDeductions=" + totalDeductions + ", grossSalaray=" + grossSalaray + ", netSalary=" + netSalary
				+ ", workerCode=" + workerCode + ", workerName=" + workerName + ", workerSkill=" + workerSkill
				+ ", jobCode=" + jobCode + "]";
	}
    
    
    
    
}
