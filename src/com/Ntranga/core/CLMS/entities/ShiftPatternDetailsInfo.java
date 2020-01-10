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
@Table(name="shift_pattern_details_info")
public class ShiftPatternDetailsInfo implements Serializable {

	private CustomerDetails customerDetails;
	private CompanyDetails  companyDetails;
	private LocationDetails locationDetails;
	private PlantDetails plantDetails;
	//private MCountry countryDetails;
	private ShiftPatternDetails shiftPatternDetails;
	private Integer shiftPatternDetailsId;
	private Integer shiftPatternSequenceId;
	private String shiftPatternName;
	private String totalPatternHours;
	private String definePatternBy;
	private String hasAdditionalRules;
	private Date transactionDate;
	private String status;
	private Integer createdBy;
	private Date createdDate;
	private Integer modifiedBy;
	private Date modifiedDate;
	
	


	public ShiftPatternDetailsInfo() {
	}

	
	
	
	public ShiftPatternDetailsInfo(Integer shiftPatternDetailsId) {
		super();
		this.shiftPatternDetailsId = shiftPatternDetailsId;
	}




	public ShiftPatternDetailsInfo(CustomerDetails customerDetails,
			CompanyDetails companyDetails,
			ShiftPatternDetails shiftPatternDetails,
			Integer shiftPatternDetailsId, Integer shiftPatternSequenceId,
			String shiftPatternName, String totalPatternHours,
			String definePatternBy, String hasAdditionalRules,
			Date transactionDate, String status, Integer createdBy,
			Date createdDate, Integer modifiedBy, Date modifiedDate) {
		this.customerDetails = customerDetails;
		this.companyDetails = companyDetails;
		this.shiftPatternDetails = shiftPatternDetails;
		this.shiftPatternDetailsId = shiftPatternDetailsId;
		this.shiftPatternSequenceId = shiftPatternSequenceId;
		this.shiftPatternName = shiftPatternName;
		this.totalPatternHours = totalPatternHours;
		this.definePatternBy = definePatternBy;
		this.hasAdditionalRules = hasAdditionalRules;
		this.transactionDate = transactionDate;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}



	@Id
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="Shift_Pattern_Details_Id")
    public Integer getShiftPatternDetailsId() {
		return shiftPatternDetailsId;
	}

	public void setShiftPatternDetailsId(Integer shiftPatternDetailsId) {
		this.shiftPatternDetailsId = shiftPatternDetailsId;
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

/*@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Shift_Code")
	public ShiftsDefine getShiftCode() {
		return shiftCode;
	}

	public void setShiftCode(ShiftsDefine shiftCode) {
		this.shiftCode = shiftCode;
	}*/	
	
@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Shift_Pattern_Id")	
	public ShiftPatternDetails getShiftPatternDetails() {
		return shiftPatternDetails;
	}

	public void setShiftPatternDetails(ShiftPatternDetails shiftPatternDetails) {
		this.shiftPatternDetails = shiftPatternDetails;
	}
	
	@Column(name="Shift_Pattern_Sequence_Id")
	public Integer getShiftPatternSequenceId() {
		return shiftPatternSequenceId;
	}

	public void setShiftPatternSequenceId(Integer shiftPatternSequenceId) {
		this.shiftPatternSequenceId = shiftPatternSequenceId;
	}

	@Column(name="Shift_Pattern_Name")
	public String getShiftPatternName() {
		return shiftPatternName;
	}

	public void setShiftPatternName(String shiftPatternName) {
		this.shiftPatternName = shiftPatternName;
	}
	
	@Column(name="Total_Pattern_Hours")
	public String getTotalPatternHours() {
		return totalPatternHours;
	}

	public void setTotalPatternHours(String totalPatternHours) {
		this.totalPatternHours = totalPatternHours;
	}
	
	@Column(name="Define_Pattern_By")
	public String getDefinePatternBy() {
		return definePatternBy;
	}

	public void setDefinePatternBy(String definePatternBy) {
		this.definePatternBy = definePatternBy;
	}
	
	@Column(name="Has_Additional_Shift_Rules")
	public String getHasAdditionalRules() {
		return hasAdditionalRules;
	}

	public void setHasAdditionalRules(String hasAdditionalRules) {
		this.hasAdditionalRules = hasAdditionalRules;
	}
	
	@Column(name="Status")
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
    
    
    @Column(name="Modified_By")
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

    
    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Plant_Id")	
	public PlantDetails getPlantDetails() {
		return plantDetails;
	}

	public void setPlantDetails(PlantDetails plantDetails) {
		this.plantDetails = plantDetails;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Location_Id")
	public LocationDetails getLocationDetails() {
		return locationDetails;
	}

	public void setLocationDetails(LocationDetails locationDetails) {
		this.locationDetails = locationDetails;
	}
	@Override
	public String toString() {
		return "ShiftPatternDetailsInfo [customerDetails=" + customerDetails.getCustomerId()
				+ ", companyDetails=" + companyDetails.getCompanyId()
				+ ", shiftPatternDetails=" + shiftPatternDetails.getShiftPatternId()
				+ ", shiftPatternDetailsId=" + shiftPatternDetailsId
				+ ", shiftPatternSequenceId=" + shiftPatternSequenceId
				+ ", shiftPatternName=" + shiftPatternName
				+ ", totalPatternHours=" + totalPatternHours
				+ ", definePatternBy=" + definePatternBy
				+ ", hasAdditionalRules=" + hasAdditionalRules
				+ ", transactionDate=" + transactionDate + ", status=" + status
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", modifiedBy=" + modifiedBy + ", modifiedDate="
				+ modifiedDate + "]";
	}
    
    
}
