package com.Ntranga.CLMS.vo;

import java.util.ArrayList;
import java.util.List;

public class PermissionsGroupVo implements java.io.Serializable {

	private static final long serialVersionUID = -4970162316624348444L;
	private Integer permissionsGroupId;
	private String permissionsGroupName;
	private String permissionsGroupDesc;
	private String isActive;
	private Integer createdBy;
	private String createdDate;
	private Integer modifiedBy;
	private String modifiedDate;
	
	private List<ScreenActionsVo> screenActionsVo;
	
	
	public PermissionsGroupVo(){
		screenActionsVo = new ArrayList<ScreenActionsVo>();
	}
	
	public List<ScreenActionsVo> getScreenActionsVo() {
		return screenActionsVo;
	}
	public void setScreenActionsVo(List<ScreenActionsVo> screenActionsVo) {
		this.screenActionsVo = screenActionsVo;
	}

	public PermissionsGroupVo(String permissionsGroupName, String isActive) {
		this.permissionsGroupName = permissionsGroupName;
		this.isActive = isActive;
	}
	
	public Integer getPermissionsGroupId() {
		return permissionsGroupId;
	}

	public void setPermissionsGroupId(Integer permissionsGroupId) {
		this.permissionsGroupId = permissionsGroupId;
	}

	public String getPermissionsGroupName() {
		return permissionsGroupName;
	}

	public void setPermissionsGroupName(String permissionsGroupName) {
		this.permissionsGroupName = permissionsGroupName;
	}

	public String getPermissionsGroupDesc() {
		return permissionsGroupDesc;
	}

	public void setPermissionsGroupDesc(String permissionsGroupDesc) {
		this.permissionsGroupDesc = permissionsGroupDesc;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
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
	
	

	

}
