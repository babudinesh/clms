package com.Ntranga.CLMS.vo;

import java.util.Date;
import java.util.List;

public class DepartmentVo {

	private int customerId ;
	private int companyId ;
	private int departmentId ;
	private int departmentInfoId;
	private String departmentName;
	private String shortName;
	private String isActive;
///	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy", timezone="IST")
	private Date transactionDate;
	private String transDate;
	private String legacyDeptId;
	private String departmentCode;
	private int costCenter;
	private boolean isCostCenter;
	private String costCenterName;
	private Integer departmentOwnerJob;
	private Integer departmentOwnerEmp;
	//private int departmentUniqueId;
	private int departmentTypeId;
	private String customerName;
	private String companyName;
	private int createdBy;
	private int modifiedBy;
	private String deptOwner;
	private int divisionId;
	
	private List<DepartmentDivisionVo> departmentDivisionList; 
	
	
	
	
	

	public int getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(int divisionId) {
		this.divisionId = divisionId;
	}

	public String getDeptOwner(){
		return deptOwner;
	}
	
	public void setDeptOwner(String deptOwner){
		this.deptOwner = deptOwner;
	}
	
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public int getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	
	
	/**
	 * @return the customerId
	 */
	public int getCustomerId() {
		return customerId;
	}
	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	/**
	 * @return the companyId
	 */
	public int getCompanyId() {
		return companyId;
	}
	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	/**
	 * @return the departmentId
	 */
	public int getDepartmentId() {
		return departmentId;
	}
	/**
	 * @param departmentId the departmentId to set
	 */
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	/**
	 * @return the departmentName
	 */
	public String getDepartmentName() {
		return departmentName;
	}
	/**
	 * @param departmentName the departmentName to set
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	/**
	 * @return the isActive
	 */
	public String getIsActive() {
		return isActive;
	}
	/**
	 * @param isActive the isActive to set
	 */
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	/**
	 * @return the transactionDate
	 */
	public Date getTransactionDate() {
		return transactionDate;
	}
	/**
	 * @param transactionDate the transactionDate to set
	 */
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	/**
	 * @return the legacyDeptId
	 */
	public String getLegacyDeptId() {
		return legacyDeptId;
	}
	/**
	 * @param legacyDeptId the legacyDeptId to set
	 */
	public void setLegacyDeptId(String legacyDeptId) {
		this.legacyDeptId = legacyDeptId;
	}
	/**
	 * @return the departmentCode
	 */
	public String getDepartmentCode() {
		return departmentCode;
	}
	/**
	 * @param departmentCode the departmentCode to set
	 */
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}
	/**
	 * @return the costCenter
	 */
	public int getCostCenter() {
		return costCenter;
	}
	/**
	 * @param costCenter the costCenter to set
	 */
	public void setCostCenter(int costCenter) {
		this.costCenter = costCenter;
	}
	/**
	 * @return the departmentInfoId
	 */
	public int getDepartmentInfoId() {
		return departmentInfoId;
	}
	/**
	 * @param departmentInfoId the departmentInfoId to set
	 */
	public void setDepartmentInfoId(int departmentInfoId) {
		this.departmentInfoId = departmentInfoId;
	}
	/**
	 * @return the departmentOwnerJob
	 */
	public Integer getDepartmentOwnerJob() {
		return departmentOwnerJob;
	}
	/**
	 * @param departmentOwnerJob the departmentOwnerJob to set
	 */
	public void setDepartmentOwnerJob(Integer departmentOwnerJob) {
		this.departmentOwnerJob = departmentOwnerJob;
	}
	/**
	 * @return the departmentOwnerEmp
	 */
	public Integer getDepartmentOwnerEmp() {
		return departmentOwnerEmp;
	}
	/**
	 * @param departmentOwnerEmp the departmentOwnerEmp to set
	 */
	public void setDepartmentOwnerEmp(Integer departmentOwnerEmp) {
		this.departmentOwnerEmp = departmentOwnerEmp;
	}
	
	/**
	 * @return the deptShortName
	 */
	public String getShortName() {
		return shortName;
	}
	/**
	 * @param deptShortName the deptShortName to set
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	/**
	 * @return the departmentTypeId
	 */
	public int getDepartmentTypeId() {
		return departmentTypeId;
	}
	/**
	 * @param departmentTypeId the departmentTypeId to set
	 */
	public void setDepartmentTypeId(int departmentTypeId) {
		this.departmentTypeId = departmentTypeId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public String getCostCenterName() {
		return costCenterName;
	}

	public void setCostCenterName(String costCenterName) {
		this.costCenterName = costCenterName;
	}

	public List<DepartmentDivisionVo> getDepartmentDivisionList() {
		return departmentDivisionList;
	}

	public void setDepartmentDivisionList(List<DepartmentDivisionVo> departmentDivisionList) {
		this.departmentDivisionList = departmentDivisionList;
	}

	public boolean getIsCostCenter() {
		return isCostCenter;
	}

	public void setIsCostCenter(boolean isCostCenter) {
		this.isCostCenter = isCostCenter;
	}

	@Override
	public String toString() {
		return "DepartmentVo [customerId=" + customerId + ", companyId="
				+ companyId + ", departmentId=" + departmentId
				+ ", departmentInfoId=" + departmentInfoId
				+ ", departmentName=" + departmentName + ", shortName="
				+ shortName + ", isActive=" + isActive + ", transactionDate="
				+ transactionDate + ", transDate=" + transDate
				+ ", legacyDeptId=" + legacyDeptId + ", departmentCode="
				+ departmentCode + ", costCenter=" + costCenter
				+ ", costCenterName=" + costCenterName
				+ ", departmentOwnerJob=" + departmentOwnerJob
				+ ", departmentOwnerEmp=" + departmentOwnerEmp
				+ ", departmentTypeId=" + departmentTypeId + ", customerName="
				+ customerName + ", companyName=" + companyName
				+ ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy
				+ ", deptOwner=" + deptOwner + ", departmentDivisionList="
				+ departmentDivisionList + "]";
	}

	
	
}
