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
@Table(name="shift_pattern_assign_shifts")
public class ShiftPatternAssignShifts implements Serializable {

	private CustomerDetails customerDetails;
	private CompanyDetails  companyDetails;
	private ShiftPatternDetailsInfo shiftPatternDetailsInfo;
	private Integer shiftPatternAssignId;
	private String shiftCode;
	private ShiftsDefine shiftDefine;
	private Integer daySequence;
	private String weekName;
	private Integer createdBy;
	private Date createdDate;
	private Integer modifiedBy;
	private Date modifiedDate;
	

	
	public ShiftPatternAssignShifts() {
	}

	public ShiftPatternAssignShifts(CustomerDetails customerDetails, CompanyDetails companyDetails, ShiftPatternDetailsInfo shiftPatternDetailsInfo,
									Integer shiftPatternAssignId, String shiftCode, Integer daySequence, ShiftsDefine shiftDefine,
									Integer createdBy, Date createdDate, Integer modifiedBy, Date modifiedDate) {
		this.customerDetails = customerDetails;
		this.companyDetails = companyDetails;
		this.shiftPatternDetailsInfo = shiftPatternDetailsInfo;
		this.shiftPatternAssignId = shiftPatternAssignId;
		this.shiftCode = shiftCode;
		this.daySequence = daySequence;
		this.shiftDefine = shiftDefine;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}
	
	@Id
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="Shift_Pattern_Assign_Id")
    public Integer getShiftPatternAssignId() {
		return shiftPatternAssignId;
	}

	public void setShiftPatternAssignId(Integer shiftPatternAssignId) {
		this.shiftPatternAssignId= shiftPatternAssignId;
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
	@JoinColumn(name="Define_Shift_Id")	
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
	
	@Column(name="Day_Sequence")
	public Integer getDaySequence() {
		return daySequence;
	}

	public void setDaySequence(Integer daySequence) {
		this.daySequence = daySequence;
	}
	
	@Column(name="Week_Name", length=20)
	public String getWeekName() {
		return weekName;
	}

	public void setWeekName(String weekName) {
		this.weekName = weekName;
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
		return "ShiftPatternAssignShifts [customerDetails=" + customerDetails.getCustomerId()
				+ ", companyDetails=" + companyDetails.getCompanyId()
				+ ", shiftPatternDetailsInfo=" + shiftPatternDetailsInfo.getShiftPatternDetailsId()
				+ ", shiftDefine=" + shiftDefine.getShiftDefineId()
				+ ", shiftPatternAssignId=" + shiftPatternAssignId
				+ ", shiftCode=" + shiftCode + ", daySequence=" + daySequence
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", modifiedBy=" + modifiedBy + ", modifiedDate="
				+ modifiedDate + "]";
	}
    
    
}
