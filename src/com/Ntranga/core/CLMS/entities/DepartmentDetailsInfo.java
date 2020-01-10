package com.Ntranga.core.CLMS.entities;

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

@SuppressWarnings("serial")
@Entity
@Table(name="department_details_info")
  
public class DepartmentDetailsInfo implements java.io.Serializable {

	private CustomerDetails customerDetails ;
	private CompanyDetails companyDetails ;
	private DepartmentDetails departmentDetails ;
	private Integer departmentSequenceId;
	//private Integer departmentUniqueId;
	private MDepartment MDepartmentType;
	private String departmentName;
	private String shortName;
	private String isActive;
	private Date transactionDate;
	private String legacyDepartmentId;
	//private String departmentCode;
	private Integer costCenter;
	//private String departmentOwnerEmp;
	//private String departmentOwnerJob;
	private Integer employeeId;
	private Integer jobCodeId;
	private String isCostCenter;
	private String costCenterName;
	private Integer departmentInfoId;
	private int createdBy;
    private Date createdDate;
	private int modifiedBy;
    private Date modifiedDate;
    
    
    
    public DepartmentDetailsInfo(){
    	
    }
    
    
    public DepartmentDetailsInfo(Integer departmentInfoId){
    	this.departmentInfoId = departmentInfoId;
    }

	public DepartmentDetailsInfo(CustomerDetails customerDetails,CompanyDetails companyDetails, DepartmentDetails departmentId, 
						Integer departmentSequenceId, MDepartment MDepartmentType, String departmentName, String shortName, 
						String isActive,Date transactionDate, String legacyDepartmentId, String costCenterName, String isCostCenter, 
						Integer employeeId, Integer jobCodeId, Integer departmentInfoId, Integer createdBy, Date createdDate, Integer modifiedBy, Date modifiedDate) {
		this.customerDetails = customerDetails;
		this.companyDetails = companyDetails;
		this.departmentDetails = departmentId;
		this.departmentSequenceId = departmentSequenceId;
		this.MDepartmentType = MDepartmentType;
		this.departmentName = departmentName;
		this.shortName = shortName;
		this.isActive = isActive;
		this.transactionDate = transactionDate;
		this.legacyDepartmentId = legacyDepartmentId;
		//this.departmentCode = departmentCode;
		//this.costCenter = costCenter;
		this.employeeId = employeeId;
		this.jobCodeId = jobCodeId;
		this.departmentInfoId = departmentInfoId;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.isCostCenter = isCostCenter;
		this.costCenterName = costCenterName;
	}

	@Id 
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="Department_Info_Id" )
	public Integer getDepartmentInfoId() {
		return departmentInfoId;
	}

	public void setDepartmentInfoId(Integer departmentInfoId) {
		this.departmentInfoId = departmentInfoId;
	}
	
@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Customer_Id" )
	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}

@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Company_Id" )
	public CompanyDetails getCompanyDetails() {
		return companyDetails;
	}

	public void setCompanyDetails(CompanyDetails companyDetails) {
		this.companyDetails = companyDetails;
	}
	
@ManyToOne(fetch=FetchType.LAZY)
	 @JoinColumn(name="Department_Id", nullable=false)
	public DepartmentDetails getDepartmentDetails() {
		return departmentDetails;
	}

	public void setDepartmentDetails(DepartmentDetails departmentId) {
		this.departmentDetails = departmentId;
	}

	@Column(name="Department_Sequence_Id" )
	public Integer getDepartmentSequenceId() {
		return departmentSequenceId;
	}

	public void setDepartmentSequenceId(Integer departmentSequenceId) {
		this.departmentSequenceId = departmentSequenceId;
	}
	
	/*@Column(name="Department_Unique_Id" )
	public Integer getDepartmentUniqueId() {
		return departmentUniqueId;
	}

	public void setDepartmentUniqueId(Integer departmentUniqueId) {
		this.departmentUniqueId = departmentUniqueId;
	}*/

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Department_Type_Id" )
	public MDepartment getMDepartmentType() {
		return MDepartmentType;
	}

	public void setMDepartmentType(MDepartment MDepartmentType) {
		this.MDepartmentType = MDepartmentType;
	}

	@Column(name="Department_Name")
	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	@Column(name="Short_Name" )
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Column(name="Is_Active" )
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Temporal(TemporalType.DATE)
    @Column(name="Transaction_Date", nullable=false, length=10)
	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	@Column(name="Legacy_Department_Id" )
	public String getLegacyDepartmentId() {
		return legacyDepartmentId;
	}

	public void setLegacyDepartmentId(String legacyDepartmentId) {
		this.legacyDepartmentId = legacyDepartmentId;
	}

	/*@Column(name="Department_Code" )
	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}*/

	@Column(name="Cost_Center" )
	public Integer getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(Integer costCenter) {
		this.costCenter = costCenter;
	}

	@Column(name="Employee_Id")
	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	@Column(name="Job_Code_Id" )
	public Integer getJobCodeId() {
		return jobCodeId;
	}

	public void setJobCodeId(Integer jobCodeId) {
		this.jobCodeId = jobCodeId;
	}
	
	@Column(name="Is_Cost_Center", length = 5 )
	public String getIsCostCenter() {
		return isCostCenter;
	}

	public void setIsCostCenter(String isCostCenter) {
		this.isCostCenter = isCostCenter;
	}

	@Column(name="Cost_Center_Name", length = 45 )
	public String getCostCenterName() {
		return costCenterName;
	}

	public void setCostCenterName(String costCenterName) {
		this.costCenterName = costCenterName;
	}


	@Column(name="Created_By", nullable=false)
    public int getCreatedBy() {
        return this.createdBy;
    }
    
    public void setCreatedBy(int createdBy) {
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


	/*@Override
	public String toString() {
		return "DepartmentDetailsInfo [customerDetails=" + customerDetails
				+ ", companyDetails=" + companyDetails + ", departmentDetails="
				+ departmentDetails + ", departmentSequenceId="
				+ departmentSequenceId + ", MDepartmentType=" + MDepartmentType
				+ ", departmentName=" + departmentName + ", shortName="
				+ shortName + ", isActive=" + isActive + ", transactionDate="
				+ transactionDate + ", legacyDepartmentId="
				+ legacyDepartmentId + ", departmentOwnerEmp="
				+ departmentOwnerEmp + ", departmentOwnerJob="
				+ departmentOwnerJob + ", isCostCenter=" + isCostCenter
				+ ", costCenterName=" + costCenterName + ", departmentInfoId="
				+ departmentInfoId + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy
				+ ", modifiedDate=" + modifiedDate + "]";
	}*/
    
    
	
	
    
	
    
}
