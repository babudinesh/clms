package com.Ntranga.CLMS.vo;

import java.io.Serializable;
import java.util.Date;

public class AssignShiftPatternUpdateVo implements Serializable {

	private Integer customerId;
	private Integer companyId;	
	private Integer departmentId;
	private String shiftDate;
	private String shiftCode;
	private Integer workerId;
	private String workerCodeName;
	private String departmentName;
	private Integer edit;
		
	
	
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public Integer getEdit() {
		return edit;
	}
	public void setEdit(Integer edit) {
		this.edit = edit;
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
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	public String getShiftDate() {
		return shiftDate;
	}
	public void setShiftDate(String shiftDate) {
		this.shiftDate = shiftDate;
	}
	public String getShiftCode() {
		return shiftCode;
	}
	public void setShiftCode(String shiftCode) {
		this.shiftCode = shiftCode;
	}
	public Integer getWorkerId() {
		return workerId;
	}
	public void setWorkerId(Integer workerId) {
		this.workerId = workerId;
	}
	public String getWorkerCodeName() {
		return workerCodeName;
	}
	public void setWorkerCodeName(String workerCodeName) {
		this.workerCodeName = workerCodeName;
	}

	
	
}
