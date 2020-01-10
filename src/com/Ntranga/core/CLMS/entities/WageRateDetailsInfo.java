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
@Table(name="wage_rate_details_info")
public class WageRateDetailsInfo implements Serializable{

	private CustomerDetails customerDetails ;
	private CompanyDetails companyDetails ;
	private VendorDetails vendorDetails;
	private Integer workOrderId;	
	private WageRateDetails wageRateDetails;
	private String wageScaleCode;
	private Integer wageRateDetailsId;
	private Integer jobCodeId;
	private Integer wageRateSequenceId;
	private String wageRateName;
	private String jobCategory;
	private String jobType;
	private MCountry MCountry;
	private String status;
	private Date transactionDate;
	private Integer baseRateCurrencyId;
	private Integer overtimeRateCurrencyId;
	private Double baseRate;
	private Double overtimeRate;
	private String baseRateFrequency;
	private String overtimeFrequency;
	
	private Double workPieceRate;
    private String workPieceCurrency;
    private String workPieceUnit;
    
	private Integer createdBy;
    private Date createdDate;
    private Integer modifiedBy;
    private Date modifiedDate;
    private Integer baseRateMargin;
    private Integer overtimeRateMargin;
    private Integer workRateMargin;
    
	public WageRateDetailsInfo() {
	}

	public WageRateDetailsInfo(Integer wageRateDetailsId) {
		this.wageRateDetailsId = wageRateDetailsId;
	}

	public WageRateDetailsInfo(CustomerDetails customerDetails, CompanyDetails companyDetails, WageRateDetails wageRateDetails, Integer jobCodeId,
									Integer wageRateDetailsId, Integer wageRateSequenceId, String wageRateName, String wageRateDescription,
									String jobCategory, String jobType, String status, Date transactionDate,Integer baseRateCurrency, 
									Integer overtimeRateCurrency,Double baseRate, Double overtimeRate, String baseRateFrequency, 
									String overtimeFrequency,Integer createdBy, Date createdDate,Integer modifiedBy, Date modifiedDate, MCountry MCountry) {
		this.customerDetails = customerDetails;
		this.companyDetails = companyDetails;
		this.wageRateDetails = wageRateDetails;
		this.wageRateDetailsId = wageRateDetailsId;
		this.jobCodeId = jobCodeId;
		this.wageRateSequenceId = wageRateSequenceId;
		this.wageRateName = wageRateName;
		this.jobCategory = jobCategory;
		this.jobType = jobType;
		this.status = status;
		this.transactionDate = transactionDate;
		this.baseRateCurrencyId = baseRateCurrency;
		this.overtimeRateCurrencyId = overtimeRateCurrency;
		this.baseRate = baseRate;
		this.overtimeRate = overtimeRate;
		this.baseRateFrequency = baseRateFrequency;
		this.overtimeFrequency = overtimeFrequency;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.MCountry = MCountry;
	}
    
    
@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Customer_Id", nullable=false)	
	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}

@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Company_Id", nullable=false)
	public CompanyDetails getCompanyDetails() {
		return companyDetails;
	}

	public void setCompanyDetails(CompanyDetails companyDetails) {
		this.companyDetails = companyDetails;
	}

	
	@Column(name="WorkOrder_id", nullable=false)
    public Integer getWorkOrderId() {
        return this.workOrderId;
    }
    
    public void setWorkOrderId(Integer workOrderId) {
        this.workOrderId = workOrderId;
    }
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Vendor_Id", nullable=false)
    public VendorDetails getVendorDetails() {
        return this.vendorDetails;
    }
    
    public void setVendorDetails(VendorDetails vendorDetails) {
        this.vendorDetails = vendorDetails;
    }
    
@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Wage_Rate_Id", nullable=false)
	public WageRateDetails getWageRateDetails() {
		return wageRateDetails;
	}

	public void setWageRateDetails(WageRateDetails wageRateDetails) {
		this.wageRateDetails = wageRateDetails;
	}
	
	@Id
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="Wage_Rate_Details_Id", nullable=false)
	public Integer getWageRateDetailsId() {
		return wageRateDetailsId;
	}

	public void setWageRateDetailsId(Integer wageRateDetailsId) {
		this.wageRateDetailsId = wageRateDetailsId;
	}

	
	@Column(name="Wage_Scale_Code", nullable=false)
	public String getWageScaleCode() {
		return wageScaleCode;
	}

	public void setWageScaleCode(String wageScaleCode) {
		this.wageScaleCode = wageScaleCode;
	}

	
	@Column(name="Job_Code_Id", nullable=false)
	public Integer getJobCodeId() {
		return jobCodeId;
	}

	public void setJobCodeId(Integer jobCodeId) {
		this.jobCodeId = jobCodeId;
	}

	@Column(name="Wage_Rate_Sequence_Id", nullable=false)
	public Integer getWageRateSequenceId() {
		return wageRateSequenceId;
	}

	public void setWageRateSequenceId(Integer wageRateSequenceId) {
		this.wageRateSequenceId = wageRateSequenceId;
	}

	@Column(name="Wage_Rate_Name", length=50, nullable=false)
	public String getWageRateName() {
		return wageRateName;
	}

	public void setWageRateName(String wageRateName) {
		this.wageRateName = wageRateName;
	}
	
	@Column(name="Job_Category", length=20, nullable=false)
	public String getJobCategory() {
		return jobCategory;
	}

	public void setJobCategory(String jobCategory) {
		this.jobCategory = jobCategory;
	}

	@Column(name="Job_Type", length=20, nullable=false)
	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Country_Id", nullable=false)	
	public MCountry getMCountry() {
		return MCountry;
	}

	public void setMCountry(MCountry MCountry) {
		this.MCountry = MCountry;
	}

    @Column(name="Base_Rate_Currency_Id")
	public Integer getBaseRateCurrencyId() {
		return baseRateCurrencyId;
	}

	public void setBaseRateCurrencyId(Integer baseRateCurrency) {
		this.baseRateCurrencyId = baseRateCurrency;
	}


    @Column(name="Overtime_Currency_Id")
	public Integer getOvertimeRateCurrencyId() {
		return overtimeRateCurrencyId;
	}

	public void setOvertimeRateCurrencyId(Integer overtimeRateCurrency) {
		this.overtimeRateCurrencyId = overtimeRateCurrency;
	}

	@Column(name="Base_Rate", length=11)
	public Double getBaseRate() {
		return baseRate;
	}

	public void setBaseRate(Double baseRate) {
		this.baseRate = baseRate;
	}
	@Column(name="Overtime_Rate",  length=11)
	public Double getOvertimeRate() {
		return overtimeRate;
	}

	public void setOvertimeRate(Double overtimeRate) {
		this.overtimeRate = overtimeRate;
	}
	
	@Column(name="Base_Rate_Frequency",  length=10)
	public String getBaseRateFrequency() {
		return baseRateFrequency;
	}


	public void setBaseRateFrequency(String baseFrequency) {
		this.baseRateFrequency = baseFrequency;
	}

	@Column(name="Overtime_Frequency",  length=10)
	public String getOvertimeFrequency() {
		return overtimeFrequency;
	}


	public void setOvertimeFrequency(String overtimeFrequency) {
		this.overtimeFrequency = overtimeFrequency;
	}

	
	@Column(name="Status", length=2, nullable=false)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Temporal(TemporalType.DATE)
    @Column(name="Transaction_Date", nullable=false, length=10)
	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
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
    
    
    @Column(name="Work_Piece_Rate",  length=25)
	public Double getWorkPieceRate() {
		return workPieceRate;
	}

	public void setWorkPieceRate(Double workPieceRate) {
		this.workPieceRate = workPieceRate;
	}

	@Column(name="Work_Piece_Currency",  length=25)
	public String getWorkPieceCurrency() {
		return workPieceCurrency;
	}

	public void setWorkPieceCurrency(String workPieceCurrency) {
		this.workPieceCurrency = workPieceCurrency;
	}

	@Column(name="Work_Piece_Unit",  length=25)
	public String getWorkPieceUnit() {
		return workPieceUnit;
	}

	public void setWorkPieceUnit(String workPieceUnit) {
		this.workPieceUnit = workPieceUnit;
	}
	
		

	@Column(name="Overtime_Rate_Margin",  length=25)
	public Integer getOvertimeRateMargin() {
		return overtimeRateMargin;
	}

	public void setOvertimeRateMargin(Integer overtimeRateMargin) {
		this.overtimeRateMargin = overtimeRateMargin;
	}
	
	
	
	@Column(name="Base_Rate_Margin",  length=25)
	public Integer getBaseRateMargin() {
		return baseRateMargin;
	}

	public void setBaseRateMargin(Integer baseRateMargin) {
		this.baseRateMargin = baseRateMargin;
	}

	@Column(name="Work_Rate_Margin",  length=25)
	public Integer getWorkRateMargin() {
		return workRateMargin;
	}

	public void setWorkRateMargin(Integer workRateMargin) {
		this.workRateMargin = workRateMargin;
	}

	@Override
	public String toString() {
		return "WageRateDetailsInfo [customerDetails=" + customerDetails + ", companyDetails=" + companyDetails
				+ ", vendorDetails=" + vendorDetails + ", workOrderId=" + workOrderId + ", wageRateDetails="
				+ wageRateDetails + ", wageScaleCode=" + wageScaleCode + ", wageRateDetailsId=" + wageRateDetailsId
				+ ", jobCodeId=" + jobCodeId + ", wageRateSequenceId=" + wageRateSequenceId
				+ ", wageRateName=" + wageRateName + ", jobCategory=" + jobCategory + ", jobType=" + jobType
				+ ", MCountry=" + MCountry + ", status=" + status + ", transactionDate=" + transactionDate
				+ ", baseRateCurrency=" + baseRateCurrencyId + ", overtimeRateCurrency=" + overtimeRateCurrencyId
				+ ", baseRate=" + baseRate + ", overtimeRate=" + overtimeRate + ", baseRateFrequency="
				+ baseRateFrequency + ", overtimeFrequency=" + overtimeFrequency + ", workPieceRate=" + workPieceRate
				+ ", workPieceCurrency=" + workPieceCurrency + ", workPieceUnit=" + workPieceUnit + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy + ", modifiedDate="
				+ modifiedDate + "]";
	}

	
    
    
}
