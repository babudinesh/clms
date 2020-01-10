package com.Ntranga.CLMS.vo;

import java.io.Serializable;

public class OTExceededWorkersVo implements Serializable {

	
	private String empCode;
	private String empName;
	private String contractorCode;
	private String contractorName;
	private String totalOTHours;
	
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getContractorCode() {
		return contractorCode;
	}
	public void setContractorCode(String contractorCode) {
		this.contractorCode = contractorCode;
	}
	public String getContractorName() {
		return contractorName;
	}
	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}
	public String getTotalOTHours() {
		return totalOTHours;
	}
	public void setTotalOTHours(String totalOTHours) {
		this.totalOTHours = totalOTHours;
	}
	
	
	
}
