package com.Ntranga.CLMS.vo;

import java.util.ArrayList;
import java.util.List;

public class UploadStatusVo {
	
	private int totalRecords;
	private int totalRecordsInserted;
	private List<String> errors = new ArrayList();
	private int flag;
	private String description;
	
	
	/*private String missingWorkerCode ;
	private String missingVendorCode;
	private String missingWorkerName;
	private String missingVendorName;
	private String missingShift;
	private String missingBusinessDate;
	private String missingAttendanceStatus;
	private int totalNoOfRecordsInsered = 0;*/

	
	
	private String workerCode;
	private String vendorCode;
	private String vendorName;
	private String workerName;
	private String businessDate;
	private String errorDescription;
	private String attendanceStatus;
	private String shift;
	private String weeklyOff;
	private String monthYear;
	private int lineNumber;
	
	

	
	
	
	public int getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	public String getAttendanceStatus() {
		return attendanceStatus;
	}
	public void setAttendanceStatus(String attendanceStatus) {
		this.attendanceStatus = attendanceStatus;
	}
	public String getWorkerCode() {
		return workerCode;
	}
	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
	}
	public String getVendorCode() {
		return vendorCode;
	}
	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getWorkerName() {
		return workerName;
	}
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	public String getBusinessDate() {
		return businessDate;
	}
	public void setBusinessDate(String businessDate) {
		this.businessDate = businessDate;
	}
	/*public int getTotalNoOfRecordsInsered() {
		return totalNoOfRecordsInsered;
	}
	public void setTotalNoOfRecordsInsered(int totalNoOfRecordsInsered) {
		this.totalNoOfRecordsInsered = totalNoOfRecordsInsered;
	}
	public int getNoOfRecordsHavingErrors() {
		return noOfRecordsHavingErrors;
	}
	public void setNoOfRecordsHavingErrors(int noOfRecordsHavingErrors) {
		this.noOfRecordsHavingErrors = noOfRecordsHavingErrors;
	}
	public String getMissingWorkerCode() {
		return missingWorkerCode;
	}
	public void setMissingWorkerCode(String missingWorkerCode) {
		this.missingWorkerCode = missingWorkerCode;
	}
	public String getMissingVendorCode() {
		return missingVendorCode;
	}
	public void setMissingVendorCode(String missingVendorCode) {
		this.missingVendorCode = missingVendorCode;
	}
	public String getMissingWorkerName() {
		return missingWorkerName;
	}
	public void setMissingWorkerName(String missingWorkerName) {
		this.missingWorkerName = missingWorkerName;
	}
	public String getMissingVendorName() {
		return missingVendorName;
	}
	public void setMissingVendorName(String missingVendorName) {
		this.missingVendorName = missingVendorName;
	}
	public String getMissingShift() {
		return missingShift;
	}
	public void setMissingShift(String missingShift) {
		this.missingShift = missingShift;
	}
	public String getMissingBusinessDate() {
		return missingBusinessDate;
	}
	public void setMissingBusinessDate(String missingBusinessDate) {
		this.missingBusinessDate = missingBusinessDate;
	}
	public String getMissingAttendanceStatus() {
		return missingAttendanceStatus;
	}
	public void setMissingAttendanceStatus(String missingAttendanceStatus) {
		this.missingAttendanceStatus = missingAttendanceStatus;
	}*/
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getTotalRecordsInserted() {
		return totalRecordsInserted;
	}
	public void setTotalRecordsInserted(int totalRecordsInserted) {
		this.totalRecordsInserted = totalRecordsInserted;
	}
	
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	public String getWeeklyOff() {
		return weeklyOff;
	}
	public void setWeeklyOff(String weeklyOff) {
		this.weeklyOff = weeklyOff;
	}
	public String getMonthYear() {
		return monthYear;
	}
	public void setMonthYear(String monthYear) {
		this.monthYear = monthYear;
	}
	
	
	
	
		
	
	
	 
	 
}
