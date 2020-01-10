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


@Entity
@Table(name="m_location")
public class MLocation implements Serializable {

	private Integer locationTypeId;
    private String locationTypeName;
    private int createdBy;
    private Date createdDate;
    private int modifiedBy;
    private Date modifiedDate;

	public MLocation() {

	}
	
	public MLocation(Integer locationTypeId){
		this.locationTypeId = locationTypeId;
	}
	
	public MLocation(Integer locationTypeId, String locationTypeName, int createdBy, Date createdDate, int modifiedBy, Date modifiedDate) {
		this.locationTypeId = locationTypeId;
		this.locationTypeName = locationTypeName;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}

	@Id 
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="Location_Type_Id", unique =true, nullable = false)
	public Integer getLocationTypeId() {
		return locationTypeId;
	}
    
	public void setLocationTypeId(Integer locationTypeId) {
		this.locationTypeId = locationTypeId;
	}
	
	@Column(name="Location_Type_Name")
	public String getLocationTypeName() {
		return locationTypeName;
	}
	
	public void setLocationTypeName(String locationTypeName) {
		this.locationTypeName = locationTypeName;
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
		return "MLocation [locationTypeId=" + locationTypeId + ", locationTypeName="
				+ locationTypeName + ", createdBy=" + createdBy + ", createdDate="
				+ createdDate + ", modifiedBy=" + modifiedBy + ", modifiedDate="
				+ modifiedDate + "]";
	}
   
}
