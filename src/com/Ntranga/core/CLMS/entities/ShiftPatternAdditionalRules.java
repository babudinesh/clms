package com.Ntranga.core.CLMS.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name="shift_pattern_additional_rules")
public class ShiftPatternAdditionalRules implements Serializable {

	private CustomerDetails customerDetails;
	private CompanyDetails  companyDetails;
	private ShiftPatternDetailsInfo shiftPatternDetailsInfo;
	private Integer shiftPatternRulesId;
	private ShiftsDefine shiftDefine;
	private String shiftCode;
	private String shiftOccurrence;
	private String shiftWeekDay;
	private Integer createdBy;
	private Date createdDate;
	private Integer modifiedBy;
	private Date modifiedDate;
	
	
	
	
	public ShiftPatternAdditionalRules() {
	}

	public ShiftPatternAdditionalRules(CustomerDetails customerDetails,CompanyDetails companyDetails, ShiftPatternDetailsInfo shiftPatternDetailsInfo,
										Integer shiftPatternRulesId, String shiftCode, String shiftOccurrence, String shiftWeekDay, 
										Integer createdBy, Date createdDate, Integer modifiedBy, Date modifiedDate, ShiftsDefine shiftDefine) {
		this.customerDetails = customerDetails;
		this.companyDetails = companyDetails;
		this.shiftPatternDetailsInfo = shiftPatternDetailsInfo;
		this.shiftPatternRulesId = shiftPatternRulesId;
		this.shiftCode = shiftCode;
		this.shiftOccurrence = shiftOccurrence;
		this.shiftDefine = shiftDefine;
		this.shiftWeekDay = shiftWeekDay;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}

	@Id
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="Shift_Pattern_Rules_Id")
    public Integer getShiftPatternRulesId() {
		return shiftPatternRulesId;
	}

	public void setShiftPatternRulesId(Integer shiftPatternRulesId) {
		this.shiftPatternRulesId = shiftPatternRulesId;
	}

@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Customer_Id")	
	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}

@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Company_Id")
	public CompanyDetails getCompanyDetails() {
		return companyDetails;
	}

	public void setCompanyDetails(CompanyDetails companyDetails) {
		this.companyDetails = companyDetails;
	}

@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Shift_Pattern_Details_Id")	
	public ShiftPatternDetailsInfo getShiftPatternDetailsInfo() {
		return shiftPatternDetailsInfo;
	}

	public void setShiftPatternDetailsInfo(ShiftPatternDetailsInfo shiftPatternDetailsInfo) {
		this.shiftPatternDetailsInfo = shiftPatternDetailsInfo;
	}
	
@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Shift_Define_Id")	
	public ShiftsDefine getShiftDefine() {
		return shiftDefine;
	}

	public void setShiftDefine(ShiftsDefine shiftDefine) {
		this.shiftDefine = shiftDefine;
	}
	
	@Column(name="Shift_Code")
	public String getShiftCode() {
		return shiftCode;
	}

	public void setShiftCode(String shiftCode) {
		this.shiftCode = shiftCode;
	}
	
	@Column(name="Shift_Occurrence")
	public String getShiftOccurrence() {
		return shiftOccurrence;
	}

	public void setShiftOccurrence(String shiftOccurrence) {
		this.shiftOccurrence = shiftOccurrence;
	}
	
	@Column(name="Shift_Week_Day")
	public String getShiftWeekDay() {
		return shiftWeekDay;
	}

	public void setShiftWeekDay(String shiftWeekDay) {
		this.shiftWeekDay = shiftWeekDay;
	}
	
	@Column(name="Created_By", nullable=false)
    public Integer getCreatedBy() {
        return this.createdBy;
    }
    
    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="Created_Date", nullable=false, length=19)
    public Date getCreatedDate() {
        return this.createdDate;
    }
    
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    
    @Column(name="Modified_By", nullable=false)
    public Integer getModifiedBy() {
        return this.modifiedBy;
    }
    
    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="Modified_Date", nullable=false, length=19)
    public Date getModifiedDate() {
        return this.modifiedDate;
    }
    
    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

	@Override
	public String toString() {
		return "ShiftPatternAdditionalRules [customerDetails="
				+ customerDetails.getCustomerId() + ", companyDetails=" + companyDetails.getCompanyId()
				+ ", shiftPatternDetailsInfo=" + shiftPatternDetailsInfo.getShiftPatternDetailsId()
				+ ", shiftPatternRulesId=" + shiftPatternRulesId
				+ ", shiftCode=" + shiftCode + ", shiftOccurrence="
				+ shiftOccurrence + ", shiftWeekDay=" + shiftWeekDay
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", modifiedBy=" + modifiedBy + ", modifiedDate="
				+ modifiedDate + "]";
	}
	
    
}
