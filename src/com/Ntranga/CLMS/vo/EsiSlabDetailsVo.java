package com.Ntranga.CLMS.vo;
// Generated 3 Nov, 2016 11:49:50 AM by Hibernate Tools 3.6.0


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class EsiSlabDetailsVo  implements java.io.Serializable {


     private Integer esiSlabId;   
     private String contributionStartMonth;
     private Integer contributionStartMonthYear;
     private String contributionEndMonth;
     private Integer contributionEndMonthYear;
     private String benefitStartMonth;
     private Integer benefitStartMonthYear;
     private String benefitEndMonth;
     private Integer benefitEndMonthYear;
	public Integer getEsiSlabId() {
		return esiSlabId;
	}
	public void setEsiSlabId(Integer esiSlabId) {
		this.esiSlabId = esiSlabId;
	}
	public String getContributionStartMonth() {
		return contributionStartMonth;
	}
	public void setContributionStartMonth(String contributionStartMonth) {
		this.contributionStartMonth = contributionStartMonth;
	}
	public Integer getContributionStartMonthYear() {
		return contributionStartMonthYear;
	}
	public void setContributionStartMonthYear(Integer contributionStartMonthYear) {
		this.contributionStartMonthYear = contributionStartMonthYear;
	}
	public String getContributionEndMonth() {
		return contributionEndMonth;
	}
	public void setContributionEndMonth(String contributionEndMonth) {
		this.contributionEndMonth = contributionEndMonth;
	}
	public Integer getContributionEndMonthYear() {
		return contributionEndMonthYear;
	}
	public void setContributionEndMonthYear(Integer contributionEndMonthYear) {
		this.contributionEndMonthYear = contributionEndMonthYear;
	}
	public String getBenefitStartMonth() {
		return benefitStartMonth;
	}
	public void setBenefitStartMonth(String benefitStartMonth) {
		this.benefitStartMonth = benefitStartMonth;
	}
	public Integer getBenefitStartMonthYear() {
		return benefitStartMonthYear;
	}
	public void setBenefitStartMonthYear(Integer benefitStartMonthYear) {
		this.benefitStartMonthYear = benefitStartMonthYear;
	}
	public String getBenefitEndMonth() {
		return benefitEndMonth;
	}
	public void setBenefitEndMonth(String benefitEndMonth) {
		this.benefitEndMonth = benefitEndMonth;
	}
	public Integer getBenefitEndMonthYear() {
		return benefitEndMonthYear;
	}
	public void setBenefitEndMonthYear(Integer benefitEndMonthYear) {
		this.benefitEndMonthYear = benefitEndMonthYear;
	}
     



}


