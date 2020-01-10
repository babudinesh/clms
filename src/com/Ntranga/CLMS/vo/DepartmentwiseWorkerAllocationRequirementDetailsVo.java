package com.Ntranga.CLMS.vo;
// Generated 22 Dec, 2016 12:44:03 PM by Hibernate Tools 3.6.0


import java.util.Date;


public class DepartmentwiseWorkerAllocationRequirementDetailsVo  implements java.io.Serializable {


     private Integer workerAllocationRequirementId;    
     private String workSkill;
     private String fromDate;
     private String toDate;
     private Integer noOfWorkers;
     private Integer rateOrDay;
     
     
	public Integer getWorkerAllocationRequirementId() {
		return workerAllocationRequirementId;
	}
	public void setWorkerAllocationRequirementId(Integer workerAllocationRequirementId) {
		this.workerAllocationRequirementId = workerAllocationRequirementId;
	}
	public String getWorkSkill() {
		return workSkill;
	}
	public void setWorkSkill(String workSkill) {
		this.workSkill = workSkill;
	}
	
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public Integer getNoOfWorkers() {
		return noOfWorkers;
	}
	public void setNoOfWorkers(Integer noOfWorkers) {
		this.noOfWorkers = noOfWorkers;
	}
	public Integer getRateOrDay() {
		return rateOrDay;
	}
	public void setRateOrDay(Integer rateOrDay) {
		this.rateOrDay = rateOrDay;
	}     




}


