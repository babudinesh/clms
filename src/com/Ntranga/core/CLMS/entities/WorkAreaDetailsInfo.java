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
@Table(name="work_area_details_info")
public class WorkAreaDetailsInfo implements Serializable{

	private Integer workAreaDetailsId;
	private CustomerDetails customerDetails;
	private CompanyDetails companyDetails;
	private SectionDetails sectionDetails;
	private MCountry MCountry;
	private LocationDetails locationDetails;
	private PlantDetails plantDetails;
	private WorkAreaDetails workAreaDetails;
	private Integer workAreaSequenceId;
	private String workAreaName;
	private String shortName;
	private String workAreaIncharge;
	private String contactNumber;
	private String emailId;
	private Date transactionDate;
	private String status;
	private Integer createdBy;
    private Date createdDate;
	private Integer modifiedBy;
    private Date modifiedDate;
    private DepartmentDetails departmentDetails;
    
    


	public WorkAreaDetailsInfo() {
	}
	
	
	public WorkAreaDetailsInfo(Integer plantDetailsId, CustomerDetails customerDetails, CompanyDetails companyDetails, WorkAreaDetails workAreaDetails,
			PlantDetails plantDetails, Integer plantSequenceId, String plantName, String shortName, String workAreaIncharge,
			LocationDetails locationDetails, String contactNumber, String emailId, Date transactionDate, String status,
			Integer createdBy, Date createdDate, Integer modifiedBy, Date modifiedDate, MCountry MCountry ) {
		this.workAreaDetailsId = plantDetailsId;
		this.customerDetails = customerDetails;
		this.companyDetails = companyDetails;
		this.plantDetails = plantDetails;
		this.workAreaDetails = workAreaDetails;
		this.workAreaSequenceId = plantSequenceId;
		this.workAreaName = plantName;
		this.shortName = shortName;
		this.workAreaIncharge = workAreaIncharge;
		this.locationDetails = locationDetails;
		this.contactNumber = contactNumber;
		this.emailId = emailId;
		this.transactionDate = transactionDate;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.MCountry = MCountry;
	}



	@Id
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="Work_Area_Details_Id", nullable=false)
    public Integer getWorkAreaDetailsId() {
		return workAreaDetailsId;
	}

	public void setWorkAreaDetailsId(Integer plantDetailsId) {
		this.workAreaDetailsId = plantDetailsId;
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
	@JoinColumn(name="Work_Area_Id")	
	public WorkAreaDetails getWorkAreaDetails() {
		return workAreaDetails;
	}

	public void setWorkAreaDetails(WorkAreaDetails workAreaDetails) {
		this.workAreaDetails = workAreaDetails;
	}
	
	@Column(name="Work_Area_Sequence_Id")
	public Integer getWorkAreaSequenceId() {
		return workAreaSequenceId;
	}

	public void setWorkAreaSequenceId(Integer workAreaSequenceId) {
		this.workAreaSequenceId = workAreaSequenceId;
	}

	@Column(name="Work_Area_Name")
	public String getWorkAreaName() {
		return workAreaName;
	}

	public void setWorkAreaName(String plantName) {
		this.workAreaName = plantName;
	}

	@Column(name="Short_Name")
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	@Column(name="Work_Area_Incharge")
	public String getWorkAreaIncharge() {
		return workAreaIncharge;
	}

	public void setWorkAreaIncharge(String workAreaIncharge) {
		this.workAreaIncharge = workAreaIncharge;
	}

@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Location_Id")
	public LocationDetails getLocationDetails() {
		return locationDetails;
	}

	public void setLocationDetails(LocationDetails locationDetails) {
		this.locationDetails = locationDetails;
	}

@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Country_Id")
	public MCountry getMCountry() {
		return MCountry;
	}

	public void setMCountry(MCountry MCountry) {
		this.MCountry = MCountry;
	}

	@Column(name="Contact_Number")
	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	@Column(name="Email_Id")
	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
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

    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Section_Id")	
	public SectionDetails getSectionDetails() {
		return sectionDetails;
	}


	public void setSectionDetails(SectionDetails sectionDetails) {
		this.sectionDetails = sectionDetails;
	}

    
}

