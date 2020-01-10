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

@Entity
@Table(name="m_equipment_types")
public class MEquipmentTypes implements Serializable{

	 private Integer equipmentTypeId;
     private String equipmentTypeName;
     private JobCodeDetails jobCodeDetails;
     private int createdBy;
     private Date createdDate;
     private int modifiedBy;
     private Date modifiedDate;
     

	public MEquipmentTypes() {

	}

	public MEquipmentTypes(Integer equipmentTypeId, String equipmentTypeName, JobCodeDetails jobCodeDetails, int createdBy, Date createdDate, int modifiedBy, Date modifiedDate) {
		this.equipmentTypeId = equipmentTypeId;
		this.equipmentTypeName = equipmentTypeName;
		this.jobCodeDetails = jobCodeDetails;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}

	public MEquipmentTypes(Integer equipmentTypeId) {
		this.equipmentTypeId = equipmentTypeId;
	}

	@Id 
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="Equipment_Id", unique =true, nullable = false)
	public Integer getEquipmentTypeId() {
		return equipmentTypeId;
	}
     
	public void setEquipmentTypeId(Integer equipmentTypeId) {
		this.equipmentTypeId = equipmentTypeId;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Job_Code_Id")
	public JobCodeDetails getJobCodeDetails() {
		return this.jobCodeDetails;
	}

	public void setJobCodeDetails(JobCodeDetails jobCodeDetails) {
		this.jobCodeDetails = jobCodeDetails;
	}

	
	@Column(name="Equipment_Name")
	public String getEquipmentTypeName() {
		return equipmentTypeName;
	}
	
	public void setEquipmentTypeName(String equipmentTypeName) {
		this.equipmentTypeName = equipmentTypeName;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MEquipmentTypes [equipmentTypeId=" + equipmentTypeId
				+ ", equipmentTypeName=" + equipmentTypeName
				+ ", jobCodeDetails=" + jobCodeDetails.getJobCodeId() + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + ", modifiedBy="
				+ modifiedBy + ", modifiedDate=" + modifiedDate + "]";
	}
    
    
}
