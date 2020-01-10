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
@Table(name="Manpower_Requisition_Skills")
public class ManpowerRequisitionSkills implements Serializable{
	
	private Integer manpowerSkillId;
	private CustomerDetails customerDetails;
	private CompanyDetails companyDetails;
	private MCountry countryDetails;
	private Integer locationId;
	private Integer plantId;
	private Integer departmentId;
	private ManpowerRequisition manpowerRequisitionDetails;
	private String skillType;
	private Integer jobCodeId;
	//private Integer assignedWorkers;
	private Integer requiredWorkers;
	private Date fromDate;
	private Date toDate;
	private Integer totalDays;
	//private Integer totalCount;
	
	private Integer createdBy;
    private Date createdDate;
	private Integer modifiedBy;
    private Date modifiedDate;
	
    
    
    
    
    public ManpowerRequisitionSkills() {

    }
    
	public ManpowerRequisitionSkills(Integer manpowerSkillId) {
		this.manpowerSkillId = manpowerSkillId;
	}

	

	public ManpowerRequisitionSkills(Integer manpowerSkillId, CustomerDetails customerDetails, CompanyDetails companyDetails, MCountry countryDetails,
			Integer locationId, Integer plantId, Integer departmentId,
			ManpowerRequisition manpowerRequisitionDetails, String skillType, Integer jobCodeId, Integer assignedWorkers,
			Integer requiredWorkers, Date fromDate, Date toDate, Integer totalDays, Integer totalCount,
			Integer createdBy, Date createdDate, Integer modifiedBy, Date modifiedDate) {
		this.manpowerSkillId = manpowerSkillId;
		this.customerDetails = customerDetails;
		this.companyDetails = companyDetails;
		this.countryDetails = countryDetails;
		this.locationId = locationId;
		this.plantId = plantId;
		this.departmentId = departmentId;
		this.manpowerRequisitionDetails = manpowerRequisitionDetails;
		this.skillType = skillType;
		this.jobCodeId = jobCodeId;
		//this.assignedWorkers = assignedWorkers;
		this.requiredWorkers = requiredWorkers;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.totalDays = totalDays;
		//this.totalCount = totalCount;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Manpower_Skill_Id", unique = true, nullable = false)
    public Integer getManpowerSkillId() {
		return manpowerSkillId;
	}
    
	public void setManpowerSkillId(Integer manpowerSkillId) {
		this.manpowerSkillId = manpowerSkillId;
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Manpower_Requisition_Id", nullable = false)
	public ManpowerRequisition getManpowerRequisitionDetails() {
		return manpowerRequisitionDetails;
	}
	public void setManpowerRequisitionDetails(ManpowerRequisition manpowerRequisitionDetails) {
		this.manpowerRequisitionDetails = manpowerRequisitionDetails;
	}
	
	@Column(name ="Skill_Type", nullable = false, length=20)
	public String getSkillType() {
		return skillType;
	}
	public void setSkillType(String skillType) {
		this.skillType = skillType;
	}
	
	@Column(name ="Job_Code_Id", nullable = false)
	public Integer getJobCodeId() {
		return jobCodeId;
	}
	public void setJobCodeId(Integer jobCodeId) {
		this.jobCodeId = jobCodeId;
	}
	
	/*@Column(name ="Assigned_Workers")
	public Integer getAssignedWorkers() {
		return assignedWorkers;
	}
	public void setAssignedWorkers(Integer assignedWorkers) {
		this.assignedWorkers = assignedWorkers;
	}*/
	
	@Column(name ="Required_Workers")
	public Integer getRequiredWorkers() {
		return requiredWorkers;
	}

	public void setRequiredWorkers(Integer requiredWorkers) {
		this.requiredWorkers = requiredWorkers;
	}

	@Temporal(TemporalType.DATE)
	@Column(name ="From_Date")
	public Date getFromDate() {
		return fromDate;
	}
	
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name ="To_Date")
	public Date getToDate() {
		return toDate;
	}
	
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
	@Column(name ="Total_Days")
	public Integer getTotalDays() {
		return totalDays;
	}
	
	public void setTotalDays(Integer totalDays) {
		this.totalDays = totalDays;
	}
	
	/*@Column(name ="Total_Count")
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}*/
	
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
    
    
    
	
	
}
