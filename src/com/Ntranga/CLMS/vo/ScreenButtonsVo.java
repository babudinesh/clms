package com.Ntranga.CLMS.vo;

import java.io.Serializable;

public class ScreenButtonsVo implements Serializable {


	private static final long serialVersionUID = 1426157225873205497L;
	
	private String userName;
	private String screenName;
	private String[] screenButtonNamesArray;
	private String[] screenButtonIdsArray;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public String[] getScreenButtonNamesArray() {
		return screenButtonNamesArray;
	}
	public void setScreenButtonNamesArray(String[] screenButtonNamesArray) {
		this.screenButtonNamesArray = screenButtonNamesArray;
	}
	public String[] getScreenButtonIdsArray() {
		return screenButtonIdsArray;
	}
	public void setScreenButtonIdsArray(String[] screenButtonIdsArray) {
		this.screenButtonIdsArray = screenButtonIdsArray;
	}
	
	
	
}
