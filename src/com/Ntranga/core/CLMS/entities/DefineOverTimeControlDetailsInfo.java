package com.Ntranga.core.CLMS.entities;
// Generated 22 Jun, 2016 4:22:24 PM by Hibernate Tools 3.6.0


import static javax.persistence.GenerationType.IDENTITY;

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

/**
 * DefineOverTimeControlDetailsInfo generated by hbm2java
 */
@Entity
@Table(name="Define_OverTime_Control_Details_info"
    
)
public class DefineOverTimeControlDetailsInfo  implements java.io.Serializable {


     private Integer defineOverTimeDetailsInfoId;
     private DefineOverTimeControlDetails defineOverTimeControlDetails;
     private CustomerDetails customerDetails;
     private CompanyDetails companyDetails;
     private Integer country;
     private String overTimeControlName;
     private Date transactionDate;
     private Integer sequenceId;
     private String isActive;
     private String minimumOTHoursPerDay;
     private String overtimeDurationDay;
     private String overtimeDurationWeek;
     private String overtimeDurationQuarter;
     private String overtimeDurationMonth;
     private String considerOTHours;
     private String allowOTOnWeeklyOff;
     private String allowOTOnPublicHoliday;
     private String overtimeDurationYear;
     private Integer createdBy;
     private Date createdDate;
     private int modifiedBy;
     private Date modifiedDate;
     

    public DefineOverTimeControlDetailsInfo() {
    }

    public DefineOverTimeControlDetailsInfo(Integer defineOverTimeDetailsInfoId) {
    	this.defineOverTimeDetailsInfoId = defineOverTimeDetailsInfoId;
    }
	
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="Define_OverTime_Details_InfoId", unique=true, nullable=false)
 	public Integer getDefineOverTimeDetailsInfoId() {
 		return defineOverTimeDetailsInfoId;
 	}

 	public void setDefineOverTimeDetailsInfoId(Integer defineOverTimeDetailsInfoId) {
 		this.defineOverTimeDetailsInfoId = defineOverTimeDetailsInfoId;
 	}
     
     
 	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Customer_Id")
    public CustomerDetails getCustomerDetails() {
        return this.customerDetails;
    }

	public void setCustomerDetails(CustomerDetails customerDetails) {
        this.customerDetails = customerDetails;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Company_Id")
    public CompanyDetails getCompanyDetails() {
        return this.companyDetails;
    }
    
    public void setCompanyDetails(CompanyDetails companyDetails) {
        this.companyDetails = companyDetails;
    }
    
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Define_OverTime_Details_Id")
    public DefineOverTimeControlDetails getDefineOverTimeControlDetails() {
		return defineOverTimeControlDetails;
	}

	public void setDefineOverTimeControlDetails(DefineOverTimeControlDetails defineOverTimeControlDetails) {
		this.defineOverTimeControlDetails = defineOverTimeControlDetails;
	}

	@Column(name="OverTime_Control_Name", length=45)
    public String getOverTimeControlName() {
		return overTimeControlName;
	}

	public void setOverTimeControlName(String overTimeControlName) {
		this.overTimeControlName = overTimeControlName;
	}
	
	@Column(name="Sequence_Id", length=11)
	public Integer getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(Integer sequenceId) {
		this.sequenceId = sequenceId;
	}

	@Column(name="Minimum_OT_Hours_Per_Day", length=45)
    public String getMinimumOTHoursPerDay() {
		return minimumOTHoursPerDay;
	}

	public void setMinimumOTHoursPerDay(String minimumOTHoursPerDay) {
		this.minimumOTHoursPerDay = minimumOTHoursPerDay;
	}

	@Column(name="OverTime_Duration_Day", length=45)
	public String getOvertimeDurationDay() {
		return overtimeDurationDay;
	}

	public void setOvertimeDurationDay(String overtimeDurationDay) {
		this.overtimeDurationDay = overtimeDurationDay;
	}
	
	@Column(name="OverTime_Duration_Week", length=45)
	public String getOvertimeDurationWeek() {
		return overtimeDurationWeek;
	}

	public void setOvertimeDurationWeek(String overtimeDurationWeek) {
		this.overtimeDurationWeek = overtimeDurationWeek;
	}

	@Column(name="OverTime_Duration_Quarter", length=45)
	public String getOvertimeDurationQuarter() {
		return overtimeDurationQuarter;
	}

	public void setOvertimeDurationQuarter(String overtimeDurationQuarter) {
		this.overtimeDurationQuarter = overtimeDurationQuarter;
	}

	@Column(name="OverTime_Duration_Month", length=45)
	public String getOvertimeDurationMonth() {
		return overtimeDurationMonth;
	}

	public void setOvertimeDurationMonth(String overtimeDurationMonth) {
		this.overtimeDurationMonth = overtimeDurationMonth;
	}

	@Column(name="OverTime_Duration_Year", length=45)
	public String getOvertimeDurationYear() {
		return overtimeDurationYear;
	}

	public void setOvertimeDurationYear(String overtimeDurationYear) {
		this.overtimeDurationYear = overtimeDurationYear;
	}
	
	@Column(name="Consider_OT_Hours", length=45)
	public String getConsiderOTHours() {
		return considerOTHours;
	}

	public void setConsiderOTHours(String considerOTHours) {
		this.considerOTHours = considerOTHours;
	}

	@Column(name="Allow_OT_On_Weekly_Off", length=45)
	public String getAllowOTOnWeeklyOff() {
		return allowOTOnWeeklyOff;
	}

	public void setAllowOTOnWeeklyOff(String allowOTOnWeeklyOff) {
		this.allowOTOnWeeklyOff = allowOTOnWeeklyOff;
	}

	@Column(name="Allow_OT_On_Public_Holiday", length=45)
	public String getAllowOTOnPublicHoliday() {
		return allowOTOnPublicHoliday;
	}

	public void setAllowOTOnPublicHoliday(String allowOTOnPublicHoliday) {
		this.allowOTOnPublicHoliday = allowOTOnPublicHoliday;
	}

	@Temporal(TemporalType.DATE)
    @Column(name="Transaction_Date", nullable=false, length=10)
    public Date getTransactionDate() {
        return this.transactionDate;
    }
    
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
    
    
    @Column(name="Country", nullable=false)
    public Integer getCountry() {
        return this.country;
    }
    
    public void setCountry(Integer country) {
		this.country = country;
	}

	@Column(name="Is_Active", nullable=false, length=2)
    public String getIsActive() {
        return this.isActive;
    }

	public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    
    @Column(name="Created_By", nullable=false)
    public Integer getCreatedBy() {
        return this.createdBy;
    }
    
    public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
    @Column(name="Created_date", nullable=false, length=19)
    public Date getCreatedDate() {
        return this.createdDate;
    }
    
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    
    @Column(name="Modified_By", nullable=false)
    public int getModifiedBy() {
        return this.modifiedBy;
    }
    
    public void setModifiedBy(int modifiedBy) {
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


}

