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
@Table(name="m_skill_tests")
public class MSkillTests implements Serializable {

	 private Integer skillTestId;
     private String skillTestName;
     private JobCodeDetails jobCodeDetails;
     private int createdBy;
     private Date createdDate;
     private int modifiedBy;
     private Date modifiedDate;
     

	public MSkillTests() {

	}

	public MSkillTests(Integer skillTestId, String skillTestName, JobCodeDetails jobCodeDetails, int createdBy, Date createdDate, int modifiedBy, Date modifiedDate) {
		this.skillTestId = skillTestId;
		this.skillTestName = skillTestName;
		this.jobCodeDetails = jobCodeDetails;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}



	public MSkillTests(Integer skillTestId) {
		this.skillTestId = skillTestId;
	}

	@Id 
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="Skill_Test_Id", unique =true, nullable = false)
	public Integer getSkillTestId() {
		return skillTestId;
	}
     
	public void setSkillTestId(Integer trainingId) {
		this.skillTestId = trainingId;
	}
	
	@Column(name="Skill_Test_Name")
	public String getSkillTestName() {
		return skillTestName;
	}
	
	public void setSkillTestName(String skillTestName) {
		this.skillTestName = skillTestName;
	}
	
@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Job_Code_Id")
	public JobCodeDetails getJobCodeDetails() {
		return this.jobCodeDetails;
	}

	public void setJobCodeDetails(JobCodeDetails jobCodeDetails) {
		this.jobCodeDetails = jobCodeDetails;
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
		return "MSkillTests [skillTestId=" + skillTestId + ", skillTestName="
				+ skillTestName + ", jobCodeDetails=" + jobCodeDetails.getJobCodeId()
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", modifiedBy=" + modifiedBy + ", modifiedDate="
				+ modifiedDate + "]";
	}

	
     
}
