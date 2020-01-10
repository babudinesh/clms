package com.Ntranga.core.CLMS.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name="m_holiday_type")
public class MHolidayType implements Serializable {

	private Integer holidayTypeId;
    private String holidayTypeName;
    private Integer createdBy;
    private Date createdDate;
    private Integer modifiedBy;
    private Date modifiedDate;
    
    
	public MHolidayType() {
	}
	
	

	public MHolidayType(Integer holidayTypeId) {
		this.holidayTypeId = holidayTypeId;
	}



	public MHolidayType(Integer holidayTypeId, String holidayTypeName, Integer createdBy, Date createdDate, Integer modifiedBy, Date modifiedDate) {
		this.holidayTypeId = holidayTypeId;
		this.holidayTypeName = holidayTypeName;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}



	@Id 
	@GeneratedValue(strategy=IDENTITY)
    @Column(name="Holiday_Type_Id", unique=true, nullable=false)
    public Integer getHolidayTypeId() {
        return holidayTypeId;
    }
    
    public void setHolidayTypeId(Integer holidayTypeId) {
        this.holidayTypeId = holidayTypeId;
    }

    
    @Column(name="Holiday_Type_Name", length=45)
    public String getHolidayTypeName() {
        return holidayTypeName;
    }
    
    public void setHolidayTypeName(String holidayTypeName) {
        this.holidayTypeName = holidayTypeName;
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

    
    @Column(name="Modified_By", nullable=false)
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


}
