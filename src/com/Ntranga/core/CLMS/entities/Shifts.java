package com.Ntranga.core.CLMS.entities;
// Generated Jul 7, 2016 12:18:53 PM by Hibernate Tools 3.6.0


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

/**
 * Shifts generated by hbm2java
 */
@Entity
@Table(name="shifts"   
)
public class Shifts  implements java.io.Serializable {


     private Integer shiftId;
     private CustomerDetails customerDetails;
     private CompanyDetails companyDetails;
     private int countryId;
     private Date transactionDate;
     private int sequnceId;
     private String isActive;
     private int weekStartDay;
     private int weekEndDay;
     private String timeZone;
     private String timeSetFormat;
     private String defaultPatternType;
     private int currencyId;
     private Integer createdBy;
     private Date cretatedDate;
     private Integer modifiedBy;
     private Date modifiedDate;

    public Shifts() {
    }

	
    public Shifts(CustomerDetails customerDetails, CompanyDetails companyDetails, int countryId, Date transactionDate, int sequnceId, int weekStartDay, int weekEndDay, String timeZone, String timeSetFormat, int currencyId) {
        this.customerDetails = customerDetails;
        this.companyDetails = companyDetails;
        this.countryId = countryId;
        this.transactionDate = transactionDate;
        this.sequnceId = sequnceId;
        this.weekStartDay = weekStartDay;
        this.weekEndDay = weekEndDay;
        this.timeZone = timeZone;
        this.timeSetFormat = timeSetFormat;
        this.currencyId = currencyId;
    }
    public Shifts(CustomerDetails customerDetails, CompanyDetails companyDetails, int countryId, Date transactionDate, int sequnceId, String isActive, int weekStartDay, int weekEndDay, String timeZone, String timeSetFormat, int currencyId, Integer createdBy, Date cretatedDate, Integer modifiedBy, Date modifiedDate) {
       this.customerDetails = customerDetails;
       this.companyDetails = companyDetails;
       this.countryId = countryId;
       this.transactionDate = transactionDate;
       this.sequnceId = sequnceId;
       this.isActive = isActive;
       this.weekStartDay = weekStartDay;
       this.weekEndDay = weekEndDay;
       this.timeZone = timeZone;
       this.timeSetFormat = timeSetFormat;
       this.currencyId = currencyId;
       this.createdBy = createdBy;
       this.cretatedDate = cretatedDate;
       this.modifiedBy = modifiedBy;
       this.modifiedDate = modifiedDate;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="Shift_id", unique=true, nullable=false)
    public Integer getShiftId() {
        return this.shiftId;
    }
    
    public void setShiftId(Integer shiftId) {
        this.shiftId = shiftId;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Customer_Id", nullable=false)
    public CustomerDetails getCustomerDetails() {
        return this.customerDetails;
    }
    
    public void setCustomerDetails(CustomerDetails customerDetails) {
        this.customerDetails = customerDetails;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Company_id", nullable=false)
    public CompanyDetails getCompanyDetails() {
        return this.companyDetails;
    }
    
    public void setCompanyDetails(CompanyDetails companyDetails) {
        this.companyDetails = companyDetails;
    }

    
    @Column(name="Country_Id", nullable=false)
    public int getCountryId() {
        return this.countryId;
    }
    
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="Transaction_Date", nullable=false, length=10)
    public Date getTransactionDate() {
        return this.transactionDate;
    }
    
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Column(name="Sequence_Id", nullable=false)
    public int getSequnceId() {
		return sequnceId;
	}


	public void setSequnceId(int sequnceId) {
		this.sequnceId = sequnceId;
	}


	@Column(name="Is_Active", length=2)
    public String getIsActive() {
        return this.isActive;
    }
    
    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    
    @Column(name="Week_Start_Day", nullable=false)
    public int getWeekStartDay() {
        return this.weekStartDay;
    }
    
    public void setWeekStartDay(int weekStartDay) {
        this.weekStartDay = weekStartDay;
    }

    
    @Column(name="Week_End_Day", nullable=false)
    public int getWeekEndDay() {
        return this.weekEndDay;
    }
    
    public void setWeekEndDay(int weekEndDay) {
        this.weekEndDay = weekEndDay;
    }

    
    @Column(name="Time_Zone", nullable=false, length=25)
    public String getTimeZone() {
        return this.timeZone;
    }
    
    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    
    @Column(name="Time_Set_Format", nullable=false, length=25)
    public String getTimeSetFormat() {
        return this.timeSetFormat;
    }
    
    public void setTimeSetFormat(String timeSetFormat) {
        this.timeSetFormat = timeSetFormat;
    }



    
    @Column(name="Currency_Id", nullable=false)
    public int getCurrencyId() {
        return this.currencyId;
    }
    
    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    @Column(name="Default_Pattern_Type", nullable=false, length=25)
    public String getDefaultPatternType() {
		return defaultPatternType;
	}


	public void setDefaultPatternType(String defaultPatternType) {
		this.defaultPatternType = defaultPatternType;
	}


	@Column(name="Created_By")
    public Integer getCreatedBy() {
        return this.createdBy;
    }
    
    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="Cretated_Date", length=10)
    public Date getCretatedDate() {
        return this.cretatedDate;
    }
    
    public void setCretatedDate(Date cretatedDate) {
        this.cretatedDate = cretatedDate;
    }

    
    @Column(name="Modified_By")
    public Integer getModifiedBy() {
        return this.modifiedBy;
    }
    
    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="Modified_Date", length=10)
    public Date getModifiedDate() {
        return this.modifiedDate;
    }
    
    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }




}


