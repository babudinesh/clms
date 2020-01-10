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

@SuppressWarnings("serial")
@Entity
@Table(name="section_details_info")
public class SectionDetailsInfo implements Serializable{

	private Integer sectionDetailsInfoId;
	private CustomerDetails customerDetails;
	private CompanyDetails companyDetails;
	private Integer country;
	private LocationDetails locationDetails;
	private PlantDetails plantDetails;
	private SectionDetails sectionDetails;
	private Integer sequenceId;
	private String sectionName;
	private String sectionSupervisorName;
	private String contactNumber;
	private Date transactionDate;
	private String status;
	private Integer createdBy;
    private Date createdDate;
	private Integer modifiedBy;
    private Date modifiedDate;
    private DepartmentDetails departmentDetails;


	public SectionDetailsInfo() {
	}
	
	
	public SectionDetailsInfo(Integer sectionDetailsInfoId, CustomerDetails customerDetails, CompanyDetails companyDetails, SectionDetails sectionDetails,
			PlantDetails plantDetails, Integer sequenceId, String sectionName,
			LocationDetails locationDetails, String contactNumber, String emailId, Date transactionDate, String status,
			Integer createdBy, Date createdDate, Integer modifiedBy, Date modifiedDate, Integer country ) {
		this.sectionDetailsInfoId = sectionDetailsInfoId;
		this.customerDetails = customerDetails;
		this.companyDetails = companyDetails;
		this.plantDetails = plantDetails;
		this.sectionDetails = sectionDetails;
		this.sequenceId = sequenceId;
		this.sectionName = sectionName;				
		this.locationDetails = locationDetails;
		this.contactNumber = contactNumber;	
		this.transactionDate = transactionDate;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.country = country;
	}



	@Id
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="Section_Details_Info_Id", nullable=false)
	public Integer getSectionDetailsInfoId() {
		return sectionDetailsInfoId;
	}


	public void setSectionDetailsInfoId(Integer sectionDetailsInfoId) {
		this.sectionDetailsInfoId = sectionDetailsInfoId;
	}
	

@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Customer_Id")	
	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}

	


	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}

@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Company_Id")
	public CompanyDetails getCompanyDetails() {
		return companyDetails;
	}

	public void setCompanyDetails(CompanyDetails companyDetails) {
		this.companyDetails = companyDetails;
	}

@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Plant_Id")	
	public PlantDetails getPlantDetails() {
		return plantDetails;
	}

	public void setPlantDetails(PlantDetails plantDetails) {
		this.plantDetails = plantDetails;
	}
	
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Department_Id")	
	public DepartmentDetails getDepartmentDetails() {
		return departmentDetails;
	}


	public void setDepartmentDetails(DepartmentDetails departmentDetails) {
		this.departmentDetails = departmentDetails;
	}


	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Section_Id")	
	public SectionDetails getSectionDetails() {
		return sectionDetails;
	}
	
	
	public void setSectionDetails(SectionDetails sectionDetails) {
		this.sectionDetails = sectionDetails;
	}
	
	@Column(name="Sequence_Id")
	public Integer getSequenceId() {
		return sequenceId;
	}


	public void setSequenceId(Integer sequenceId) {
		this.sequenceId = sequenceId;
	}

	@Column(name="country")
	public Integer getCountry() {
		return country;
	}


	public void setCountry(Integer country) {
		this.country = country;
	}

	@Column(name="Section_Name")
	public String getSectionName() {
		return sectionName;
	}


	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	@Column(name="Section_Supervisor_Name")
	public String getSectionSupervisorName() {
		return sectionSupervisorName;
	}


	public void setSectionSupervisorName(String sectionSupervisorName) {
		this.sectionSupervisorName = sectionSupervisorName;
	}


@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Location_Id")
	public LocationDetails getLocationDetails() {
		return locationDetails;
	}

	public void setLocationDetails(LocationDetails locationDetails) {
		this.locationDetails = locationDetails;
	}


	@Column(name="Contact_Number")
	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	@Column(name="Status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

@Temporal(TemporalType.DATE)
    @Column(name="Transaction_Date", nullable=false, length=10)
	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
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

