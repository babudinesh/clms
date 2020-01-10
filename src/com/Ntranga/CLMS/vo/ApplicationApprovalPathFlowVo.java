package com.Ntranga.CLMS.vo;

import java.io.Serializable;

import com.Ntranga.core.CLMS.entities.ApplicationApprovalPath;

public class ApplicationApprovalPathFlowVo implements Serializable {

	private Integer applicationApprovalPathFlowId;
	private Integer fromRoleId;
	private String fromRoleName;
	private Integer fromUserId;
	private String fromUserName;
	private Integer toRoleId;
	private String toRoleName;
	private Integer toUserId;
	private String toUserName;
	private String approverStatus;
	
	public Integer getApplicationApprovalPathFlowId() {
		return applicationApprovalPathFlowId;
	}
	public void setApplicationApprovalPathFlowId(Integer applicationApprovalPathFlowId) {
		this.applicationApprovalPathFlowId = applicationApprovalPathFlowId;
	}
	public Integer getFromRoleId() {
		return fromRoleId;
	}
	public void setFromRoleId(Integer fromRoleId) {
		this.fromRoleId = fromRoleId;
	}
	public String getFromRoleName() {
		return fromRoleName;
	}
	public void setFromRoleName(String fromRoleName) {
		this.fromRoleName = fromRoleName;
	}
	public Integer getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(Integer fromUserId) {
		this.fromUserId = fromUserId;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public Integer getToRoleId() {
		return toRoleId;
	}
	public void setToRoleId(Integer toRoleId) {
		this.toRoleId = toRoleId;
	}
	public String getToRoleName() {
		return toRoleName;
	}
	public void setToRoleName(String toRoleName) {
		this.toRoleName = toRoleName;
	}
	public Integer getToUserId() {
		return toUserId;
	}
	public void setToUserId(Integer toUserId) {
		this.toUserId = toUserId;
	}
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	public String getApproverStatus() {
		return approverStatus;
	}
	public void setApproverStatus(String approverStatus) {
		this.approverStatus = approverStatus;
	}
	
	
	
	
}
