package com.Ntranga.CLMS.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AssociateWorkOrderVo implements Serializable {

	private Integer customerDetailsId;
	private Integer companyDetailsId;
    private Integer vendorDetailsId;
    private Integer workrorderDetailId;       
	private String vendorCode;
    private String vendorName;    
    private String companyName;
    private String customerName;
    private String workOrderCode;
    private String workOrderName;
    private String orderPeriodFrom;
    private String orderPeriodTo;
    private String isActive;
    private String action;
    private Integer oldWorkrorderDetailId; 
    private Integer createdBy;
    private Date createdDate;
    private Integer modifiedBy;
    private Date modifiedDate;
    private List<AssociateWorkOrderVo> associateWorkOrderVo= new ArrayList<AssociateWorkOrderVo>();
    
    
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
	public Integer getVendorDetailsId() {
		return vendorDetailsId;
	}
	public void setVendorDetailsId(Integer vendorDetailsId) {
		this.vendorDetailsId = vendorDetailsId;
	}
	public Integer getWorkrorderDetailId() {
		return workrorderDetailId;
	}
	public void setWorkrorderDetailId(Integer workrorderDetailId) {
		this.workrorderDetailId = workrorderDetailId;
	}
	public String getVendorCode() {
		return vendorCode;
	}
	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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
	public String getOrderPeriodFrom() {
		return orderPeriodFrom;
	}
	public void setOrderPeriodFrom(String orderPeriodFrom) {
		this.orderPeriodFrom = orderPeriodFrom;
	}
	public String getOrderPeriodTo() {
		return orderPeriodTo;
	}
	public void setOrderPeriodTo(String orderPeriodTo) {
		this.orderPeriodTo = orderPeriodTo;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Integer getOldWorkrorderDetailId() {
		return oldWorkrorderDetailId;
	}
	public void setOldWorkrorderDetailId(Integer oldWorkrorderDetailId) {
		this.oldWorkrorderDetailId = oldWorkrorderDetailId;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public List<AssociateWorkOrderVo> getAssociateWorkOrderVo() {
		return associateWorkOrderVo;
	}
	public void setAssociateWorkOrderVo(List<AssociateWorkOrderVo> associateWorkOrderVo) {
		this.associateWorkOrderVo = associateWorkOrderVo;
	}
    
    
    
}
