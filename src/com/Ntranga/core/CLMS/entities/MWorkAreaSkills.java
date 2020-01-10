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
@Table(name="m_work_area_skills")
public class MWorkAreaSkills implements Serializable {
	
	 private Integer workAreaSkillId;
     private String workAreaSkillName;
     private WorkAreaDetails workAreaDetails;
     private String isWorkAreaSkill;
     private int createdBy;
     private Date createdDate;
     private int modifiedBy;
     private Date modifiedDate;
     

	public MWorkAreaSkills() {

	}

	public MWorkAreaSkills(Integer departmentTypeId, String departmentName, int createdBy, Date createdDate, int modifiedBy, Date modifiedDate) {
		this.workAreaSkillId = departmentTypeId;
		this.workAreaSkillName = departmentName;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}

	public MWorkAreaSkills(Integer departmentTypeId) {
		this.workAreaSkillId = departmentTypeId;
	}

	@Id 
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="Work_Area_Skill_Id", unique =true, nullable = false)
	public Integer getWorkAreaSkillId() {
		return workAreaSkillId;
	}
     
	public void setWorkAreaSkillId(Integer wokAreaSkillId) {
		this.workAreaSkillId = wokAreaSkillId;
	}
	
	@Column(name="Work_Area_Skill_Name")
	public String getWorkAreaSkillName() {
		return workAreaSkillName;
	}
	
	public void setWorkAreaSkillName(String workAreaSkillName) {
		this.workAreaSkillName = workAreaSkillName;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Work_Area_Id")	
	public WorkAreaDetails getWorkAreaDetails() {
		return workAreaDetails;
	}

	public void setWorkAreaDetails(WorkAreaDetails workAreaDetails) {
		this.workAreaDetails = workAreaDetails;
	}
	

	@Column(name="Is_Work_Area_Skill")	
	public String getIsWorkAreaSkill() {
		return isWorkAreaSkill;
	}

	public void setIsWorkAreaSkill(String isWorkAreaSkill) {
		this.isWorkAreaSkill = isWorkAreaSkill;
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

	@Override
	public String toString() {
		return "MWorkAreaSkills [workAreaSkillId=" + workAreaSkillId
				+ ", workAreaSkillName=" + workAreaSkillName
				+ ", workAreaDetails=" + workAreaDetails.getWorkAreaId() + ", isWorkAreaSkill="
				+ isWorkAreaSkill + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy
				+ ", modifiedDate=" + modifiedDate + "]";
	}
     
	
}
