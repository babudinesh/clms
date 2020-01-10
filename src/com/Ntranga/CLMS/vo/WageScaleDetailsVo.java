package com.Ntranga.CLMS.vo;

import java.util.ArrayList;

// Generated 10 Aug, 2016 10:51:23 AM by Hibernate Tools 3.6.0


import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class WageScaleDetailsVo  implements java.io.Serializable {


     private Integer wageScaleId;     
     private String wageScaleCode;
     private String wageScaleName;
     private Date transactionDate;
     private int sequenceId;
     private int countryId;
     private int locationId;
     private String isActive;
     private int createdBy;
     private Date createdDate;
     private int modifiedBy;
     private Date modifiedDate;
     private int customerId;
     private int companyId;
     private String customerName;
     private String companyName;
     private String locationName;
     
     
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
	private List<WageScaleWorkSkillsVo> wageScaleWorkSkillList = new ArrayList(); 
     
	public Integer getWageScaleId() {
		return wageScaleId;
	}
	public void setWageScaleId(Integer wageScaleId) {
		this.wageScaleId = wageScaleId;
	}
	public String getWageScaleCode() {
		return wageScaleCode;
	}
	public void setWageScaleCode(String wageScaleCode) {
		this.wageScaleCode = wageScaleCode;
	}
	public String getWageScaleName() {
		return wageScaleName;
	}
	public void setWageScaleName(String wageScaleName) {
		this.wageScaleName = wageScaleName;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public int getSequenceId() {
		return sequenceId;
	}
	public void setSequenceId(int sequenceId) {
		this.sequenceId = sequenceId;
	}
	public int getCountryId() {
		return countryId;
	}
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
	public int getLocationId() {
		return locationId;
	}
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public int getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
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
	public List<WageScaleWorkSkillsVo> getWageScaleWorkSkillList() {
		return wageScaleWorkSkillList;
	}
	public void setWageScaleWorkSkillList(List<WageScaleWorkSkillsVo> wageScaleWorkSkillList) {
		this.wageScaleWorkSkillList = wageScaleWorkSkillList;
	}
	
	
   




}


