package com.Ntranga.core;

import java.io.Serializable;

//@XmlRootElement
public class UserDetails implements Serializable {

	private static final long serialVersionUID = 8012088699460337011L;

	public static final String SESSION_KEY = UserDetails.class.getName();
	private int userId;
	private String userName;
	private int roleId;
	private String roleName;
	private int customerId;
	private int companyId;
	private int vendorId;
	private String screenName;
	private String readStatistics;
	private String screenUrl;
	private String parentNode;
	private int childOrder;
	private int locationId;
	private int departmentId;
	private int plantId;
	private int sectionId;
	private int workAreaId;
	private int screenId;
	private int divisionId;
	
	
	private Integer employeeUniqueId;
	private String[] roleNamesArray;
	private String[] roleIdsArray;
	private String[] screenNamesArray;
	private String[] screenIdsArray;
	private String[] groupNamesArray;
	
	private String[] locationArrayId;
	private String[] plantArrayId;
	private String[] departmentArrayId;
	private String[] sectionArrayId;
	private String[] workAreaArrayId;
	
	public String[] getScreenIdsArray() {
		return screenIdsArray;
	}

	public void setScreenIdsArray(String[] screenIdsArray) {
		this.screenIdsArray = screenIdsArray;
	}

	public String[] getRoleNamesArray() {
		return roleNamesArray;
	}

	public void setRoleNamesArray(String[] roleNamesArray) {
		this.roleNamesArray = roleNamesArray;
	}

	public String[] getRoleIdsArray() {
		return roleIdsArray;
	}

	public void setRoleIdsArray(String[] roleIdsArray) {
		this.roleIdsArray = roleIdsArray;
	}

	public String[] getScreenNamesArray() {
		return screenNamesArray;
	}
	
	public void setScreenNamesArray(String[] screenNamesArray) {
		this.screenNamesArray = screenNamesArray;
	}
	
	public Integer getEmployeeUniqueId() {
		return employeeUniqueId;
	}

	public void setEmployeeUniqueId(Integer employeeUniqueId) {
		this.employeeUniqueId = employeeUniqueId;
	}

	public UserDetails() {

	}

	public int getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(int divisionId) {
		this.divisionId = divisionId;
	}

	public int getScreenId() {
		return screenId;
	}

	public void setScreenId(int screenId) {
		this.screenId = screenId;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}




	public int getPlantId() {
		return plantId;
	}




	public void setPlantId(int plantId) {
		this.plantId = plantId;
	}




	public int getSectionId() {
		return sectionId;
	}




	public void setSectionId(int sectionId) {
		this.sectionId = sectionId;
	}




	public int getWorkAreaId() {
		return workAreaId;
	}




	public void setWorkAreaId(int workAreaId) {
		this.workAreaId = workAreaId;
	}




	public String getScreenUrl() {
		return screenUrl;
	}




	public void setScreenUrl(String screenUrl) {
		this.screenUrl = screenUrl;
	}




	public String getParentNode() {
		return parentNode;
	}




	public void setParentNode(String parentNode) {
		this.parentNode = parentNode;
	}




	public int getChildOrder() {
		return childOrder;
	}




	public void setChildOrder(int childOrder) {
		this.childOrder = childOrder;
	}




	public String getScreenName() {
		return screenName;
	}




	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}




	public String getReadStatistics() {
		return readStatistics;
	}




	public void setReadStatistics(String readStatistics) {
		this.readStatistics = readStatistics;
	}




	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public int getVendorId() {
		return vendorId;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	public String[] getGroupNamesArray() {
		return groupNamesArray;
	}

	public void setGroupNamesArray(String[] groupNamesArray) {
		this.groupNamesArray = groupNamesArray;
	}

	public String[] getLocationArrayId() {
		return locationArrayId;
	}

	public void setLocationArrayId(String[] locationArrayId) {
		this.locationArrayId = locationArrayId;
	}

	public String[] getPlantArrayId() {
		return plantArrayId;
	}

	public void setPlantArrayId(String[] plantArrayId) {
		this.plantArrayId = plantArrayId;
	}

	public String[] getDepartmentArrayId() {
		return departmentArrayId;
	}

	public void setDepartmentArrayId(String[] departmentArrayId) {
		this.departmentArrayId = departmentArrayId;
	}

	public String[] getSectionArrayId() {
		return sectionArrayId;
	}

	public void setSectionArrayId(String[] sectionArrayId) {
		this.sectionArrayId = sectionArrayId;
	}

	public String[] getWorkAreaArrayId() {
		return workAreaArrayId;
	}

	public void setWorkAreaArrayId(String[] workAreaArrayId) {
		this.workAreaArrayId = workAreaArrayId;
	}
	
	
}
