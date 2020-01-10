package com.Ntranga.CLMS.vo;

import java.io.Serializable;
import java.util.Date;

public class WorkorderManpowerDetailsVo implements Serializable {
	
	private Integer workOrderManpowerReqId;
	private Integer workrorderDetailId;
    private Integer workorderDetailInfoId;
    private String workSkillId;
    private Integer jobCodeId;
    private String jobCode;
    private Integer headCount;
    private Date fromDate;
    private Date toDate;
    private Integer duration;
    private Integer createdBy;
    private Integer modifiedBy;
    
    
	
	public Integer getWorkOrderManpowerReqId() {
		return workOrderManpowerReqId;
	}
	public void setWorkOrderManpowerReqId(Integer workOrderManpowerReqId) {
		this.workOrderManpowerReqId = workOrderManpowerReqId;
	}
	public Integer getWorkrorderDetailId() {
		return workrorderDetailId;
	}
	public void setWorkrorderDetailId(Integer workrorderDetailId) {
		this.workrorderDetailId = workrorderDetailId;
	}
	public Integer getWorkorderDetailInfoId() {
		return workorderDetailInfoId;
	}
	public void setWorkorderDetailInfoId(Integer workorderDetailInfoId) {
		this.workorderDetailInfoId = workorderDetailInfoId;
	}
	public String getWorkSkillId() {
		return workSkillId;
	}
	public void setWorkSkillId(String workSkillId) {
		this.workSkillId = workSkillId;
	}
	
	public String getJobCode() {
		return jobCode;
	}
	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}
	public Integer getJobCodeId() {
		return jobCodeId;
	}
	public void setJobCodeId(Integer jobCodeId) {
		this.jobCodeId = jobCodeId;
	}
	public Integer getHeadCount() {
		return headCount;
	}
	public void setHeadCount(Integer headCount) {
		this.headCount = headCount;
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
	
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
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

    
}
