package com.Ntranga.CLMS.vo;

import java.io.Serializable;

public class ApplicationTrackerInfoVo implements Serializable {

	private Integer applicationTrackerInfoId;
	private Integer applicationTrackerId;
	private Integer fromRoleId;
	private Integer fromUserId;
	private Integer toRoleId;
	private Integer toUserId;
	private String applicationStatus;
	private String applicationType;
	private String remarks;
	
	
	private String fromRoles;
	
	public String getFromRoles() {
		return fromRoles;
	}
	public void setFromRoles(String fromRoles) {
		this.fromRoles = fromRoles;
	}
	public Integer getApplicationTrackerInfoId() {
		return applicationTrackerInfoId;
	}
	public void setApplicationTrackerInfoId(Integer applicationTrackerInfoId) {
		this.applicationTrackerInfoId = applicationTrackerInfoId;
	}
	public Integer getApplicationTrackerId() {
		return applicationTrackerId;
	}
	public void setApplicationTrackerId(Integer applicationTrackerId) {
		this.applicationTrackerId = applicationTrackerId;
	}
	public Integer getFromRoleId() {
		return fromRoleId;
	}
	public void setFromRoleId(Integer fromRoleId) {
		this.fromRoleId = fromRoleId;
	}
	public Integer getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(Integer fromUserId) {
		this.fromUserId = fromUserId;
	}
	public Integer getToRoleId() {
		return toRoleId;
	}
	public void setToRoleId(Integer toRoleId) {
		this.toRoleId = toRoleId;
	}
	public Integer getToUserId() {
		return toUserId;
	}
	public void setToUserId(Integer toUserId) {
		this.toUserId = toUserId;
	}
	public String getApplicationStatus() {
		return applicationStatus;
	}
	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
	public String getApplicationType() {
		return applicationType;
	}
	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	
}
