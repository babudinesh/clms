package com.Ntranga.core.CLMS.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "Manpower_Requisition")
public class ManpowerRequisition implements Serializable {
	
	private Integer manpowerRequisitionId;
	private CustomerDetails customerDetails;
	private CompanyDetails companyDetails;
	private MCountry countryDetails;
	private  Integer locationId;
	private Integer plantId;
	private Integer departmentId;
	private String requestCode;
	
	private Date requestDate;
	private String status;
	private String requestType;
	//private String frequency;
	//private Integer year;
	//private String month;
	private Integer employeeId;
	private String employeeName;
	//private Date effectiveDate;
	private String employeeContactNumber;
	private String requestReason;
	private String requiredFor;
	private String justificationForManpower;
	
	//private String costOfManpower;
	//private Integer currencyId;
	private Integer totalHeadCount;
	
	private Integer costCenterId;

	private Integer createdBy;
    private Date createdDate;
	private Integer modifiedBy;
    private Date modifiedDate;
	
	private Integer approvedBy;
	private Date approvedDate;
	private String comments;
	
	
	public ManpowerRequisition() {
	
	}

	public ManpowerRequisition(Integer manpowerRequisitionId) {
		this.manpowerRequisitionId = manpowerRequisitionId;
	}

	

	

	public ManpowerRequisition(Integer manpowerRequisitionId,CustomerDetails customerDetails, CompanyDetails companyDetails,
			MCountry countryDetails, Integer locationId, Integer plantId, Integer departmentId, String requestCode, Date requestDate,
			String status, String requestType, String frequency, Integer year, String month, Integer employeeId, String employeeName,
			Date effectiveDate, String employeeContactNumber, String requestReason, String requiredFor,String justificationForManpower, 
			Integer totalHeadCount,Integer costCenterId, Integer approvedBy, Date approvedDate, String comments,
			Integer createdBy, Date createdDate,Integer modifiedBy, Date modifiedDate) {
		this.manpowerRequisitionId = manpowerRequisitionId;
		this.customerDetails = customerDetails;
		this.companyDetails = companyDetails;
		this.countryDetails = countryDetails;
		this.locationId = locationId;
		this.plantId = plantId;
		this.departmentId = departmentId;
		this.requestCode = requestCode;
		this.requestDate = requestDate;
		this.status = status;
		this.requestType = requestType;
		//this.frequency = frequency;
		//this.year = year;
		//this.month = month;
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		//this.effectiveDate = effectiveDate;
		this.employeeContactNumber = employeeContactNumber;
		this.requestReason = requestReason;
		this.requiredFor= requiredFor;
		this.justificationForManpower = justificationForManpower;
		this.totalHeadCount = totalHeadCount;
		this.costCenterId = costCenterId;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.comments = comments;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Manpower_Requisition_Id", unique = true, nullable = false)
	public Integer getManpowerRequisitionId() {
		return manpowerRequisitionId;
	}

	public void setManpowerRequisitionId(Integer manpowerRequisitionId) {
		this.manpowerRequisitionId = manpowerRequisitionId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Customer_Id", nullable = false)
	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Company_Id", nullable = false)
	public CompanyDetails getCompanyDetails() {
		return companyDetails;
	}

	public void setCompanyDetails(CompanyDetails companyDetails) {
		this.companyDetails = companyDetails;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Country_Id", nullable = false)
	public MCountry getCountryDetails() {
		return countryDetails;
	}

	public void setCountryDetails(MCountry countryDetails) {
		this.countryDetails = countryDetails;
	}

	@Column(name="Location_Id", nullable = false)
	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	@Column(name="Plant_Id", nullable = false)
	public Integer getPlantId() {
		return plantId;
	}

	public void setPlantId(Integer plantId) {
		this.plantId = plantId;
	}

	@Column(name="Department_Id", nullable = false)
	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	@Column(name="Request_Code", nullable = false, length=20)
	public String getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(String requestCode) {
		this.requestCode = requestCode;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="Request_Date", nullable = false)
	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	@Column(name="Status", nullable = false, length = 20)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="Request_Type", length = 20)
	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	/*@Column(name="Frequency", length = 20)
	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	@Column(name="Year",  length = 20)
	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	@Column(name="Month", length = 20)
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}*/

	@Column(name="Employee_Id", nullable = false)
	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	/*@Temporal(TemporalType.DATE)
	@Column(name="Indenter_Date", nullable = false)
	public Date getIndenterDate() {
		return indenterDate;
	}

	public void setIndenterDate(Date indenterDate) {
		this.indenterDate = indenterDate;
	}*/

	
	/*@Column(name="Employee_Department_Id")
	public Integer getEmployeeDepartmentId() {
		return employeeDepartmentId;
	}

	public void setEmployeeDepartmentId(Integer employeeDepartmentId) {
		this.employeeDepartmentId = employeeDepartmentId;
	}*/

	@Column(name="Employee_Name", length = 50)
	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	/*@Temporal(TemporalType.DATE)
	@Column(name="Effective_Date")
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
*/
	@Column(name="Employee_Contact_Number", length=15)
	public String getEmployeeContactNumber() {
		return employeeContactNumber;
	}

	public void setEmployeeContactNumber(String employeeContactNumber) {
		this.employeeContactNumber = employeeContactNumber;
	}

	@Column(name="Required_For", length=20, nullable = false)
	public String getRequiredFor() {
		return requiredFor;
	}

	public void setRequiredFor(String requiredFor) {
		this.requiredFor = requiredFor;
	}

	@Column(name="Justification_For_Manpower", length=50)
	public String getJustificationForManpower() {
		return justificationForManpower;
	}

	public void setJustificationForManpower(String justificationForManpower) {
		this.justificationForManpower = justificationForManpower;
	}

	/*@Column(name="Cost_Of_Manpower", length=15)
	public String getCostOfManpower() {
		return costOfManpower;
	}

	public void setCostOfManpower(String costOfManpower) {
		this.costOfManpower = costOfManpower;
	}

	@Column(name="Currency_Id", length=20)
	public Integer getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Integer currencyId) {
		this.currencyId = currencyId;
	}*/

	@Column(name="Total_Head_Count")
	public Integer getTotalHeadCount() {
		return totalHeadCount;
	}

	public void setTotalHeadCount(Integer totalHeadCount) {
		this.totalHeadCount = totalHeadCount;
	}

	@Column(name="Cost_Center_Id", length=20)
	public Integer getCostCenterId() {
		return costCenterId;
	}

	public void setCostCenterId(Integer costCenterId) {
		this.costCenterId = costCenterId;
	}
	
	
	@Column(name="Created_By", nullable=false)
    public Integer getCreatedBy() {
        return this.createdBy;
    }
    
    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="Created_Date", nullable=false)
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
    @Column(name="Modified_Date", nullable=false)
    public Date getModifiedDate() {
        return this.modifiedDate;
    }
    
    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Column(name="Request_Reason", length = 50)
	public String getRequestReason() {
		return requestReason;
	}

	public void setRequestReason(String requestReason) {
		this.requestReason = requestReason;
	}

	@Column(name="Approved_By")
	public Integer getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(Integer approvedBy) {
		this.approvedBy = approvedBy;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="Approved_Date")
	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	 @Column(name="Comments", length = 255)
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}
