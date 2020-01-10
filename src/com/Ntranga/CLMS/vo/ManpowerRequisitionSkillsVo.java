package com.Ntranga.CLMS.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ManpowerRequisitionSkillsVo {

	private Integer manpowerSkillId;
	private String skillType;
	private Integer jobCodeId;
	private String jobName;
	//private Integer assignedWorkers;
	private Integer requiredWorkers;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy", timezone="IST")
	private Date fromDate;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy", timezone="IST")
	private Date toDate;
	private Integer totalDays;
	//private Integer totalCount;
	private Integer manpowerRequisitionId;
	
	
	public Integer getManpowerSkillId() {
		return manpowerSkillId;
	}
	
	public void setManpowerSkillId(Integer manpowerSkillId) {
		this.manpowerSkillId = manpowerSkillId;
	}
	
	public String getSkillType() {
		return skillType;
	}
	
	public void setSkillType(String skillType) {
		this.skillType = skillType;
	}
	
	public Integer getJobCodeId() {
		return jobCodeId;
	}
	
	public void setJobCodeId(Integer jobCodeId) {
		this.jobCodeId = jobCodeId;
	}
	
	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	/*public Integer getAssignedWorkers() {
		return assignedWorkers;
	}
	
	public void setAssignedWorkers(Integer assignedWorkers) {
		this.assignedWorkers = assignedWorkers;
	}*/
	
	public Integer getRequiredWorkers() {
		return requiredWorkers;
	}
	
	public void setRequiredWorkers(Integer requiredWorkers) {
		this.requiredWorkers = requiredWorkers;
	}
	
	public Date getFromDate() {
		return fromDate;
	}
	
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	
	public Date getToDate() {
		return toDate;
	}
	
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
	public Integer getTotalDays() {
		return totalDays;
	}
	
	public void setTotalDays(Integer totalDays) {
		this.totalDays = totalDays;
	}
	
	/*public Integer getTotalCount() {
		return totalCount;
	}
	
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}*/
	
	public Integer getManpowerRequisitionId() {
		return manpowerRequisitionId;
	}

	public void setManpowerRequisitionId(Integer manpowerRequisitionId) {
		this.manpowerRequisitionId = manpowerRequisitionId;
	}

	@Override
	public String toString() {
		return "ManpowerRequisitionSkillsVo [manpowerSkillId="
				+ manpowerSkillId + ", skillType=" + skillType + ", jobCodeId="
				+ jobCodeId + ", jobName=" + jobName + ", requiredWorkers=" + requiredWorkers
				+ ", fromDate=" + fromDate + ", toDate=" + toDate
				+ ", totalDays=" + totalDays 
				+ ", manpowerRequisitionId=" + manpowerRequisitionId + "]";
	}

	
	
}
