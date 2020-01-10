package com.Ntranga.core.CLMS.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name="manpower_requisition_daywise")
public class ManpowerRequisitionDaywise implements Serializable{

	private Integer dayWiseId;
	private Integer customerId;
	private Integer companyId;
	private  Integer locationId;
	private Integer plantId;
	private Integer departmentId;
	private String jobSkill;
	private Integer requiredWorkers;
	private Date businessDate;
	
	private Integer createdBy;
    private Date createdDate;
	private Integer modifiedBy;
    private Date modifiedDate;
    private Integer jobCodeId;
    
    
    
    public ManpowerRequisitionDaywise() {
	}

    
	public ManpowerRequisitionDaywise(Integer dayWiseId, Integer customerId,Integer companyId, Integer locationId, Integer plantId,
			Integer departmentId, String jobSkill, Integer createdBy,Date createdDate, Integer modifiedBy, Date modifiedDate, 
			Integer requiredWorkers, Date businessDate) {
		this.dayWiseId = dayWiseId;
		this.customerId = customerId;
		this.companyId = companyId;
		this.locationId = locationId;
		this.plantId = plantId;
		this.departmentId = departmentId;
		this.jobSkill = jobSkill;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.requiredWorkers = requiredWorkers;
		this.businessDate = businessDate;
	}
	
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Day_Wise_Id", unique = true, nullable = false)
	public Integer getDayWiseId() {
		return dayWiseId;
	}

	public void setDayWiseId(Integer dayWiseId) {
		this.dayWiseId = dayWiseId;
	}

	@Column(name = "Customer_Id", nullable = false)
	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	@Column(name = "Company_Id", nullable = false)
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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
	
	@Column(name ="Job_Skill", nullable = false, length=20)
	public String getJobSkill() {
		return jobSkill;
	}
	public void setJobSkill(String jobSkill) {
		this.jobSkill = jobSkill;
	}
	
	@Column(name ="Required_Workers", nullable = false)
	public Integer getRequiredWorkers() {
		return requiredWorkers;
	}

	public void setRequiredWorkers(Integer requiredWorkers) {
		this.requiredWorkers = requiredWorkers;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="Business_Date", nullable = false)
	public Date getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
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


    @Column(name="Job_Code_Id")
	public Integer getJobCodeId() {
		return jobCodeId;
	}


	public void setJobCodeId(Integer jobCodeId) {
		this.jobCodeId = jobCodeId;
	}
    
    
    
    
    
    
    
}
