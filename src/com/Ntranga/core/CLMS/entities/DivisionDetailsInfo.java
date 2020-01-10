package com.Ntranga.core.CLMS.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name="division_details_info")
public class DivisionDetailsInfo implements Serializable {

	private CustomerDetails customerDetails;
	private CompanyDetails companyDetails;
	private MCountry country;
	private DivisionDetails divisionDetails;
	private Integer divisionDetailsId;
	private Integer divisionSequenceId;
	private String divisionName;
	private String shortName;
	private String status;
	private Date transactionDate;
	private Integer createdBy;
	private Date createdDate;
	private Integer modifiedBy;
	private Date modifiedDate;
	
	
	
	public DivisionDetailsInfo() {
		
	}



	public DivisionDetailsInfo(CustomerDetails customerDetails, CompanyDetails companyDetails, MCountry country,
			DivisionDetails divisionDetails, Integer divisionDetailsId, Integer divisionSequenceId, Integer divisionId,
			String divisionName, String shortName, String status, Date transactionDate, Integer createdBy, Date createdDate,
			Integer modifiedBy, Date modifiedDate) {
		this.customerDetails = customerDetails;
		this.companyDetails = companyDetails;
		this.country = country;
		this.divisionDetails = divisionDetails;
		this.divisionDetailsId = divisionDetailsId;
		this.divisionSequenceId = divisionSequenceId;
		this.divisionName = divisionName;
		this.shortName = shortName;
		this.status = status;
		this.transactionDate = transactionDate;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}

	
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="Division_Details_Id", nullable=false)
	public Integer getDivisionDetailsId() {
		return divisionDetailsId;
	}

	public void setDivisionDetailsId(Integer divisionDetailsId) {
		this.divisionDetailsId = divisionDetailsId;
	}
	
@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Customer_Id", nullable=false)
	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}

@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Company_Id", nullable=false)
	public CompanyDetails getCompanyDetails() {
		return companyDetails;
	}

	public void setCompanyDetails(CompanyDetails companyDetails) {
		this.companyDetails = companyDetails;
	}

@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Country_Id", nullable=false)
	public MCountry getCountry() {
		return country;
	}

	public void setCountry(MCountry country) {
		this.country = country;
	}

@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Division_Id", nullable=false)
	public DivisionDetails getDivisionDetails() {
		return divisionDetails;
	}

	public void setDivisionDetails(DivisionDetails divisionDetails) {
		this.divisionDetails = divisionDetails;
	}

	@Column(name="Division_Sequence_Id", nullable=false )
	public Integer getDivisionSequenceId() {
		return divisionSequenceId;
	}

	public void setDivisionSequenceId(Integer divisionSequenceId) {
		this.divisionSequenceId = divisionSequenceId;
	}

	/*@Column(name="Division_Id", nullable=false )
	public Integer getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(Integer divisionId) {
		this.divisionId = divisionId;
	}*/

	@Column(name="Division_Name", length=45 )
	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	@Column(name="Short_Name", length=45 )
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Column(name="Status", length=2 )
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
	
}
