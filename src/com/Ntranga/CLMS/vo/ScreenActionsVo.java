package com.Ntranga.CLMS.vo;

import java.io.Serializable;

public class ScreenActionsVo implements Serializable {


	private static final long serialVersionUID = 6426157225873205497L;
	
	private Integer screenActionId;
	private String groupName;
	private String newGroupName;
	private String screenName;
	private String actions;
	private String[] actionsArray;
	private boolean viewScreenActions; 
	
	public String getNewGroupName() {
		return newGroupName;
	}
	public void setNewGroupName(String newGroupName) {
		this.newGroupName = newGroupName;
	}
	public String[] getActionsArray() {
		return actionsArray;
	}
	public void setActionsArray(String[] actionsArray) {
		this.actionsArray = actionsArray;
	}
	public Integer getScreenActionId() {
		return screenActionId;
	}
	public void setScreenActionId(Integer screenActionId) {
		this.screenActionId = screenActionId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public String getActions() {
		return actions;
	}
	public void setActions(String actions) {
		this.actions = actions;
	}
	public boolean isViewScreenActions() {
		return viewScreenActions;
	}
	public void setViewScreenActions(boolean viewScreenActions) {
		this.viewScreenActions = viewScreenActions;
	}

	
	
}
