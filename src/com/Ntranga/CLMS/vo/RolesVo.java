package com.Ntranga.CLMS.vo;

import java.util.Date;

public class RolesVo implements java.io.Serializable {

	private static final long serialVersionUID = 3967920950563669395L;
	private Integer roleId;
	private String roleName;
	private String roleDesc;
	private Integer customerId;
	private Integer companyId;
	private String createdDate;
	private String modifiedDate;
	private Integer createdBy;
	private Integer modifiedBy;
	private String[] permissionsGroupArray;
	
	
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
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
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
	public String[] getPermissionsGroupArray() {
		return permissionsGroupArray;
	}
	public void setPermissionsGroupArray(String[] permissionsGroupArray) {
		this.permissionsGroupArray = permissionsGroupArray;
	}
	
	
	
}
