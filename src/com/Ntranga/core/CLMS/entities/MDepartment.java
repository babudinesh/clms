package com.Ntranga.core.CLMS.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="m_department")
public class MDepartment {
	
	 private Integer departmentTypeId;
     private String departmentTypeName;
     private int createdBy;
     private Date createdDate;
     private int modifiedBy;
     private Date modifiedDate;
     

	public MDepartment() {

	}

	public MDepartment(Integer departmentTypeId, String departmentName, int createdBy, Date createdDate, int modifiedBy, Date modifiedDate) {
		this.departmentTypeId = departmentTypeId;
		this.departmentTypeName = departmentName;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}

	public MDepartment(Integer departmentTypeId) {
		this.departmentTypeId = departmentTypeId;
	}

	@Id 
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="Department_Type_Id", unique =true, nullable = false)
	public Integer getDepartmentTypeId() {
		return departmentTypeId;
	}
     
	public void setDepartmentTypeId(Integer departmentTypeId) {
		this.departmentTypeId = departmentTypeId;
	}
	
	@Column(name="Department_Type_Name")
	public String getDepartmentTypeName() {
		return departmentTypeName;
	}
	
	public void setDepartmentTypeName(String departmentTypeName) {
		this.departmentTypeName = departmentTypeName;
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
		return "MDepartment [departmentId=" + departmentTypeId
				+ ", departmentName=" + departmentTypeName + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + ", modifiedBy="
				+ modifiedBy + ", modifiedDate=" + modifiedDate + "]";
	}
     
	
}
