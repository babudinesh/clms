package com.Ntranga.CLMS.vo;



public class UsersVo implements java.io.Serializable {

	private static final long serialVersionUID = -6958109466742837298L;
	private Integer userId;
	private String userName;
	private Integer customerId;
	private Integer companyId;
	private String password;
	private String confirmPassword;
	private String userType;
	private Integer vendorId;
	private Integer employeeUniqueId;
	private String employeeName;
	private String vendorName;
	private String isActive;
	private Integer createdBy;
	private String createdDate;
	private Integer modifiedBy;
	private String modifiedDate;
	
	private String locationId;
	private String plantId;
	private String departmentId;
	private String sectionId;
	private String workAreaId;
	
	private String[] locationArrayId;
	private String[] plantArrayId;
	private String[] departmentArrayId;
	private String[] sectionArrayId;
	private String[] workAreaArrayId;
	
	
	private boolean passwordChanged;
	
	private String[] userRoles;
	
	
	
	
	public boolean isPasswordChanged() {
		return passwordChanged;
	}
	public void setPasswordChanged(boolean passwordChanged) {
		this.passwordChanged = passwordChanged;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public Integer getEmployeeUniqueId() {
		return employeeUniqueId;
	}
	public void setEmployeeUniqueId(Integer employeeUniqueId) {
		this.employeeUniqueId = employeeUniqueId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
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
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String[] getUserRoles() {
		return userRoles;
	}
	public void setUserRoles(String[] userRoles) {
		this.userRoles = userRoles;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getPlantId() {
		return plantId;
	}
	public void setPlantId(String plantId) {
		this.plantId = plantId;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getSectionId() {
		return sectionId;
	}
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}
	public String getWorkAreaId() {
		return workAreaId;
	}
	public void setWorkAreaId(String workAreaId) {
		this.workAreaId = workAreaId;
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
