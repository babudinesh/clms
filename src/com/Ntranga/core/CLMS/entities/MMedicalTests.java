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
@Table(name="m_medical_tests")
public class MMedicalTests implements Serializable{

	 private Integer medicalTestId;
     private String medicalTestName;
     private JobCodeDetails jobCodeDetails;
     private String isPeriodic;
     private String isOnBoard;
     private int createdBy;
     private Date createdDate;
     private int modifiedBy;
     private Date modifiedDate;
     

	public MMedicalTests() {

	}

	public MMedicalTests(Integer medicalTestId, String trainingName, JobCodeDetails jobCodeDetails, String isPeriodic, String isOnBoard, int createdBy, Date createdDate, int modifiedBy, Date modifiedDate) {
		this.medicalTestId = medicalTestId;
		this.medicalTestName = trainingName;
		this.jobCodeDetails = jobCodeDetails;
		this.isPeriodic = isPeriodic;
		this.isOnBoard = isOnBoard;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}

	public MMedicalTests(Integer medicalTestId) {
		this.medicalTestId = medicalTestId;
	}

	@Id 
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="Medical_Test_Id", unique =true, nullable = false)
	public Integer getMedicalTestId() {
		return medicalTestId;
	}
     
	public void setMedicalTestId(Integer trainingId) {
		this.medicalTestId = trainingId;
	}
	
	@Column(name="Medical_Test_Name")
	public String getMedicalTestName() {
		return medicalTestName;
	}
	
	public void setMedicalTestName(String medicalTestName) {
		this.medicalTestName = medicalTestName;
	}
	
@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Job_Code_Id")
	public JobCodeDetails getJobCodeDetails() {
		return this.jobCodeDetails;
	}

	public void setJobCodeDetails(JobCodeDetails jobCodeDetails) {
		this.jobCodeDetails = jobCodeDetails;
	}
	
	@Column(name="Is_Periodic")
	public String getIsPeriodic() {
		return isPeriodic;
	}

	public void setIsPeriodic(String isPeriodic) {
		this.isPeriodic = isPeriodic;
	}

	@Column(name="Is_On_Board")
	public String getIsOnBoard() {
		return isOnBoard;
	}

	public void setIsOnBoard(String isOnBoard) {
		this.isOnBoard = isOnBoard;
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
		return "MMedicalTests [medicalTestId=" + medicalTestId
				+ ", medicalTestName=" + medicalTestName + ", jobCodeDetails="
				+ jobCodeDetails.getJobCodeId() + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy
				+ ", modifiedDate=" + modifiedDate + "]";
	}
    
    
}
