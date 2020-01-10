package com.Ntranga.CLMS.vo;

import java.io.Serializable;

public class AttendanceReportVo implements Serializable {

	
	private String contractorName;
	private String workMenName;
	private String idNo;
	private String shift;
	private String date;
	private String inTime;
	private String outTime;
	private String manHours;
	private String overTime;
	private String status;
	
	
	
	public String getContractorName() {
		return contractorName;
	}
	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}
	public String getWorkMenName() {
		return workMenName;
	}
	public void setWorkMenName(String workMenName) {
		this.workMenName = workMenName;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getInTime() {
		return inTime;
	}
	public void setInTime(String inTime) {
		this.inTime = inTime;
	}
	public String getOutTime() {
		return outTime;
	}
	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}
	public String getManHours() {
		return manHours;
	}
	public void setManHours(String manHours) {
		this.manHours = manHours;
	}
	public String getOverTime() {
		return overTime;
	}
	public void setOverTime(String overTime) {
		this.overTime = overTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
	
	
}
