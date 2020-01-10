package com.Ntranga.CLMS.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkorderDetailInfoVo {

	
	     private Integer workOrderInfoId;
	     private Integer workrorderDetailId;
	     private Integer customerDetailsId;
	     private Integer companyDetailsId;
	     private Integer country;
	     private Date transactionDate;
	     private Integer sequenceId;
	     private String isActive;
	     private String workOrderCode;
	     private String workOrderName;
	     private Integer location;
	     private Integer department;
	     private Integer totalHeadCount;
	     private Date workPeriodFrom;
	     private Date workPeriodTo;
	     private String natureOfWork;
	     private Integer createdBy;	 
	     private Integer modifiedBy;
	     private String customerName;
	     private String companyName;
	     private String locationName;
	     private String departmentName;
	     private List<WorkorderResponsibilitiesVo>  workorderResponsibilitiesVo;
	     private List<WorkorderManpowerDetailsVo>  workorderManpowerDetailsVo;
	     private Integer plantId;
	     
	     public WorkorderDetailInfoVo() {
	    	 workorderResponsibilitiesVo = new ArrayList<WorkorderResponsibilitiesVo>();
	         workorderManpowerDetailsVo = new  ArrayList<WorkorderManpowerDetailsVo>();
	 	}
	     
		public List<WorkorderResponsibilitiesVo> getWorkorderResponsibilitiesVo() {
			return workorderResponsibilitiesVo;
		}
		public void setWorkorderResponsibilitiesVo(List<WorkorderResponsibilitiesVo> workorderResponsibilitiesVo) {
			this.workorderResponsibilitiesVo = workorderResponsibilitiesVo;
		}
		public List<WorkorderManpowerDetailsVo> getWorkorderManpowerDetailsVo() {
			return workorderManpowerDetailsVo;
		}
		public void setWorkorderManpowerDetailsVo(List<WorkorderManpowerDetailsVo> workorderManpowerDetailsVo) {
			this.workorderManpowerDetailsVo = workorderManpowerDetailsVo;
		}
		public Integer getWorkOrderInfoId() {
			return workOrderInfoId;
		}
		public void setWorkOrderInfoId(Integer workOrderInfoId) {
			this.workOrderInfoId = workOrderInfoId;
		}
		public Integer getWorkrorderDetailId() {
			return workrorderDetailId;
		}
		public void setWorkrorderDetailId(Integer workrorderDetailId) {
			this.workrorderDetailId = workrorderDetailId;
		}
		public Integer getCustomerDetailsId() {
			return customerDetailsId;
		}
		public void setCustomerDetailsId(Integer customerDetailsId) {
			this.customerDetailsId = customerDetailsId;
		}
		public Integer getCompanyDetailsId() {
			return companyDetailsId;
		}
		public void setCompanyDetailsId(Integer companyDetailsId) {
			this.companyDetailsId = companyDetailsId;
		}
		public Integer getCountry() {
			return country;
		}
		public void setCountry(Integer country) {
			this.country = country;
		}
		public Date getTransactionDate() {
			return transactionDate;
		}
		public void setTransactionDate(Date transactionDate) {
			this.transactionDate = transactionDate;
		}
		public Integer getSequenceId() {
			return sequenceId;
		}
		public void setSequenceId(Integer sequenceId) {
			this.sequenceId = sequenceId;
		}
		public String getIsActive() {
			return isActive;
		}
		public void setIsActive(String isActive) {
			this.isActive = isActive;
		}
		public String getWorkOrderCode() {
			return workOrderCode;
		}
		public void setWorkOrderCode(String workOrderCode) {
			this.workOrderCode = workOrderCode;
		}
		public String getWorkOrderName() {
			return workOrderName;
		}
		public void setWorkOrderName(String workOrderName) {
			this.workOrderName = workOrderName;
		}
		public Integer getLocation() {
			return location;
		}
		public void setLocation(Integer location) {
			this.location = location;
		}
		public Integer getDepartment() {
			return department;
		}
		public void setDepartment(Integer department) {
			this.department = department;
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
		public String getLocationName() {
			return locationName;
		}
		public void setLocationName(String locationName) {
			this.locationName = locationName;
		}
		public String getDepartmentName() {
			return departmentName;
		}
		public void setDepartmentName(String departmentName) {
			this.departmentName = departmentName;
		}

		public Integer getTotalHeadCount() {
			return totalHeadCount;
		}

		public void setTotalHeadCount(Integer totalHeadCount) {
			this.totalHeadCount = totalHeadCount;
		}

		public Date getWorkPeriodFrom() {
			return workPeriodFrom;
		}

		public void setWorkPeriodFrom(Date workPeriodFrom) {
			this.workPeriodFrom = workPeriodFrom;
		}

		public Date getWorkPeriodTo() {
			return workPeriodTo;
		}

		public void setWorkPeriodTo(Date workPeriodTo) {
			this.workPeriodTo = workPeriodTo;
		}

		public String getNatureOfWork() {
			return natureOfWork;
		}

		public void setNatureOfWork(String natureOfWork) {
			this.natureOfWork = natureOfWork;
		}

		public Integer getPlantId() {
			return plantId;
		}

		public void setPlantId(Integer plantId) {
			this.plantId = plantId;
		}
	     
	     
}
