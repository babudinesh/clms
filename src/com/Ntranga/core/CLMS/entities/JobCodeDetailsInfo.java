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

/**
 * The persistent class for the job_code_details_info database table.
 * 
 */

@SuppressWarnings("serial")
@Entity
@Table(name="job_code_details_info")
public class JobCodeDetailsInfo implements Serializable {
	
	private int jobCodeDetailsId;
	private String allowsWorkFromHome;
	private CompanyDetails companyDetails;
	private CustomerDetails customerDetails;
	private JobCodeDetails jobCodeDetails;
	private MCountry MCountry;
	private int jobCodeSequenceId;
	private String jobCategory;
	private String jobGrade;
	private String jobLevel;
	private String jobName;
	private String jobTitle;
	private String jobType;
	private String managerLevel;
	private String maximumStandardHours;
	private String minimumStandardHours;
	private String riskDescription;
	private String riskIndicator;
	private String standardHours;
	private String status;
	private Date transactionDate;
	private String hazardousIndicator;
	private String isPPERequired;
	private String isCompensatoryJob;
	private String isCriticalJob;
	private String isEligibleForOvertime;
	private String isEligibleForPerks;
	private String isEligibleForVariableCompensation;
	private String isExServiceManJob;
	private String isGazetteOfficer;
	private String isHealthScreeningRequired;
	private String isHighlyCompensatedJob;
	private String isHotSkillCategory;
	private String isProbationRequired;
	private String isRestrictedJobs;
	private String isRoasterJob;
	private String isSkillTesting;
	private String isSportsManQuota;
	private String isTrainingRequired;
	private String isUnionJobs;
	private int createdBy;
	private Date createdDate;
	private int modifiedBy;
	private Date modifiedDate;
	private String isResidentOrdinarilyResident;
	private String isResidentButNotOrdinarilyResident;
	private String isNonResident;
	private String isPersonOfIndianOrigin;
	private String isExpatForeignNational ;

	


	public JobCodeDetailsInfo() {
	}

	public JobCodeDetailsInfo(int jobCodeDetailsId, String allowsWorkFromHome,CompanyDetails companyDetails, CustomerDetails customerDetails,
			JobCodeDetails jobCodeDetails, int jobCodeSequenceId,String jobCategory, String jobGrade, String jobLevel,
			String jobName, String jobTitle, String jobType,String managerLevel, String maximiumStandardHours,
			String minimumStandardHours, String riskDescription, String riskIndicator, String standardHours, String status,
			Date transactionDate, String hazardousIndicator, String isPPERequired, String isCompensatoryJob,
			String isCriticalJob, String isEligibleForOvertime, String isEligibleForPerks, String isEligibleForVariableCompensation, String isExServiceManJob,
			String isGazetteOfficer, String isHealthScreeningRequired,String isHighlyCompensatedJob, String isHotSkillCategory,
			String isProbationRequired, String isRestrictedJobs, String isRoasterJob, String isSkillTesting,
			String isSportsManQuota, String isTrainingRequired, String isUnionJobs, int createdBy, Date createdDate, 
			int modifiedBy, Date modifiedDate,String isResidentOrdinarilyResident, String isResidentButNotOrdinarilyResident, String isNonResident,
			String isPersonOfIndianOrigin, String isExpatForeignNational, MCountry MCountry) {
		this.jobCodeDetailsId = jobCodeDetailsId;
		this.allowsWorkFromHome = allowsWorkFromHome;
		this.companyDetails = companyDetails;
		this.customerDetails = customerDetails;
		this.jobCodeDetails = jobCodeDetails;
		this.jobCodeSequenceId = jobCodeSequenceId;
		this.jobCategory = jobCategory;
		this.jobGrade = jobGrade;
		this.jobLevel = jobLevel;
		this.jobName = jobName;
		this.jobTitle = jobTitle;
		this.jobType = jobType;
		this.managerLevel = managerLevel;
		this.maximumStandardHours = maximiumStandardHours;
		this.minimumStandardHours = minimumStandardHours;
		this.riskDescription = riskDescription;
		this.riskIndicator = riskIndicator;
		this.standardHours = standardHours;
		this.status = status;
		this.transactionDate = transactionDate;
		this.hazardousIndicator = hazardousIndicator;
		this.isPPERequired = isPPERequired;
		this.isCompensatoryJob = isCompensatoryJob;
		this.isCriticalJob = isCriticalJob;
		this.isEligibleForOvertime = isEligibleForOvertime;
		this.isEligibleForPerks = isEligibleForPerks;
		this.isEligibleForVariableCompensation = isEligibleForVariableCompensation;
		this.isExServiceManJob = isExServiceManJob;
		this.isGazetteOfficer = isGazetteOfficer;
		this.isHealthScreeningRequired = isHealthScreeningRequired;
		this.isHighlyCompensatedJob = isHighlyCompensatedJob;
		this.isHotSkillCategory = isHotSkillCategory;
		this.isProbationRequired = isProbationRequired;
		this.isRestrictedJobs = isRestrictedJobs;
		this.isRoasterJob = isRoasterJob;
		this.isSkillTesting = isSkillTesting;
		this.isSportsManQuota = isSportsManQuota;
		this.isTrainingRequired = isTrainingRequired;
		this.isUnionJobs = isUnionJobs;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.isResidentOrdinarilyResident = isResidentOrdinarilyResident;
		this.isResidentButNotOrdinarilyResident =isResidentButNotOrdinarilyResident;
		this.isNonResident = isNonResident;
		this.isPersonOfIndianOrigin = isPersonOfIndianOrigin;
		this.isExpatForeignNational = isExpatForeignNational;
		this.MCountry = MCountry;
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Job_Code_Details_Id", unique=true, nullable=false)
	public int getJobCodeDetailsId() {
		return this.jobCodeDetailsId;
	}

	public void setJobCodeDetailsId(int jobCodeDetailsId) {
		this.jobCodeDetailsId = jobCodeDetailsId;
	}

@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Company_Id")
	public CompanyDetails getCompanyDetails() {
		return this.companyDetails;
	}

	public void setCompanyDetails(CompanyDetails companyId) {
		this.companyDetails = companyId;
	}
	
@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Customer_Id")
	public CustomerDetails getCustomerDetails() {
		return this.customerDetails;
	}

	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}

@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Job_Code_Id")
	public JobCodeDetails getJobCodeDetails() {
		return this.jobCodeDetails;
	}

	public void setJobCodeDetails(JobCodeDetails jobCodeDetails) {
		this.jobCodeDetails = jobCodeDetails;
	}
	
@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Country_Id")
	public MCountry getMCountry() {
		return this.MCountry;
	}

	public void setMCountry(MCountry MCountry) {
		this.MCountry = MCountry;
	}

	@Column(name="Job_Code_Sequence_Id")
	public int getJobCodeSequenceId() {
		return this.jobCodeSequenceId;
	}

	public void setJobCodeSequenceId(int jobCodeSequenceId) {
		this.jobCodeSequenceId = jobCodeSequenceId;
	}
	
	@Column(name="Hazardous_Indicator")
	public String getHazardousIndicator() {
		return this.hazardousIndicator;
	}

	public void setHazardousIndicator(String hazardousIndicator) {
		this.hazardousIndicator = hazardousIndicator;
	}

	@Column(name="Job_Category")
	public String getJobCategory() {
		return this.jobCategory;
	}

	public void setJobCategory(String jobCategory) {
		this.jobCategory = jobCategory;
	}

	@Column(name="Job_Grade")
	public String getJobGrade() {
		return this.jobGrade;
	}

	public void setJobGrade(String jobGrade) {
		this.jobGrade = jobGrade;
	}

	@Column(name="Job_Level")
	public String getJobLevel() {
		return this.jobLevel;
	}

	public void setJobLevel(String jobLevel) {
		this.jobLevel = jobLevel;
	}

	@Column(name="Job_Name")
	public String getJobName() {
		return this.jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	@Column(name="Job_Title")
	public String getJobTitle() {
		return this.jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	@Column(name="Job_Type")
	public String getJobType() {
		return this.jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	@Column(name="Manager_Level")
	public String getManagerLevel() {
		return this.managerLevel;
	}

	public void setManagerLevel(String managerLevel) {
		this.managerLevel = managerLevel;
	}
	
	@Column(name="Allows_Work_From_Home")
	public String getAllowsWorkFromHome() {
		return this.allowsWorkFromHome;
	}

	public void setAllowsWorkFromHome(String allowsWorkFromHome) {
		this.allowsWorkFromHome = allowsWorkFromHome;
	}
	
	@Column(name="Is_Compensatory_Job")
	public String getIsCompensatoryJob() {
		return this.isCompensatoryJob;
	}

	public void setIsCompensatoryJob(String isCompensatoryJob) {
		this.isCompensatoryJob = isCompensatoryJob;
	}

	@Column(name="Is_Critical_Job")
	public String getIsCriticalJob() {
		return this.isCriticalJob;
	}

	public void setIsCriticalJob(String isCriticalJob) {
		this.isCriticalJob = isCriticalJob;
	}

	@Column(name="Is_Eligible_For_Overtime")
	public String getIsEligibleForOvertime() {
		return this.isEligibleForOvertime;
	}

	public void setIsEligibleForOvertime(String isEligibleForOvertime) {
		this.isEligibleForOvertime = isEligibleForOvertime;
	}

	@Column(name="Is_Eligible_For_Perks")
	public String getIsEligibleForPerks() {
		return this.isEligibleForPerks;
	}

	public void setIsEligibleForPerks(String isEligibleForPerks) {
		this.isEligibleForPerks = isEligibleForPerks;
	}

	@Column(name="Is_Eligible_For_Variable_Compensation")
	public String getIsEligibleForVariableCompensation() {
		return this.isEligibleForVariableCompensation;
	}

	public void setIsEligibleForVariableCompensation(String isEligibleForVariableCompensation) {
		this.isEligibleForVariableCompensation = isEligibleForVariableCompensation;
	}

	@Column(name="Is_Ex_Service_Man_Job")
	public String getIsExServiceManJob() {
		return this.isExServiceManJob;
	}

	public void setIsExServiceManJob(String isExServiceManJob) {
		this.isExServiceManJob = isExServiceManJob;
	}

	@Column(name="Is_Gazette_Officer")
	public String getIsGazetteOfficer() {
		return this.isGazetteOfficer;
	}

	public void setIsGazetteOfficer(String isGazetteOfficer) {
		this.isGazetteOfficer = isGazetteOfficer;
	}

	@Column(name="Is_Health_Screening_Required")
	public String getIsHealthScreeningRequired() {
		return this.isHealthScreeningRequired;
	}

	public void setIsHealthScreeningRequired(String isHealthScreeningRequired) {
		this.isHealthScreeningRequired = isHealthScreeningRequired;
	}

	@Column(name="Is_Highly_Compensated_Job")
	public String getIsHighlyCompensatedJob() {
		return this.isHighlyCompensatedJob;
	}

	public void setIsHighlyCompensatedJob(String isHighlyCompensatedJob) {
		this.isHighlyCompensatedJob = isHighlyCompensatedJob;
	}

	@Column(name="Is_Hot_Skill_Category")
	public String getIsHotSkillCategory() {
		return this.isHotSkillCategory;
	}

	public void setIsHotSkillCategory(String isHotSkillCategory) {
		this.isHotSkillCategory = isHotSkillCategory;
	}

	@Column(name="Is_Probation_Required")
	public String getIsProbationRequired() {
		return this.isProbationRequired;
	}

	public void setIsProbationRequired(String isProbationRequired) {
		this.isProbationRequired = isProbationRequired;
	}

	@Column(name="Is_Restricted_Job")
	public String getIsRestrictedJobs() {
		return this.isRestrictedJobs;
	}

	public void setIsRestrictedJobs(String isRestrictedJobs) {
		this.isRestrictedJobs = isRestrictedJobs;
	}

	@Column(name="Is_Roaster_Job")
	public String getIsRoasterJob() {
		return this.isRoasterJob;
	}

	public void setIsRoasterJob(String isRoasterJob) {
		this.isRoasterJob = isRoasterJob;
	}

	@Column(name="Is_Skill_Testing")
	public String getIsSkillTesting() {
		return this.isSkillTesting;
	}

	public void setIsSkillTesting(String isSkillTesting) {
		this.isSkillTesting = isSkillTesting;
	}

	@Column(name="Is_Sports_Man_Quota")
	public String getIsSportsManQuota() {
		return this.isSportsManQuota;
	}

	public void setIsSportsManQuota(String isSportsManQuota) {
		this.isSportsManQuota = isSportsManQuota;
	}

	@Column(name="Is_Training_Required")
	public String getIsTrainingRequired() {
		return this.isTrainingRequired;
	}

	public void setIsTrainingRequired(String isTrainingRequired) {
		this.isTrainingRequired = isTrainingRequired;
	}

	@Column(name="Is_Union_Job")
	public String getIsUnionJobs() {
		return this.isUnionJobs;
	}

	public void setIsUnionJobs(String isUnionJobs) {
		this.isUnionJobs = isUnionJobs;
	}

	@Column(name="Is_PPE_Required")
	public String getIsPPERequired() {
		return this.isPPERequired;
	}

	public void setIsPPERequired(String isPPERequired) {
		this.isPPERequired = isPPERequired;
	}
	
	@Column(name="Risk_Description")
	public String getRiskDescription() {
		return this.riskDescription;
	}

	public void setRiskDescription(String riskDescription) {
		this.riskDescription = riskDescription;
	}
	
	@Column(name="Risk_Indicator")
	public String getRiskIndicator() {
		return this.riskIndicator;
	}

	public void setRiskIndicator(String riskIndicator) {
		this.riskIndicator = riskIndicator;
	}

	@Column(name="Standard_Hours")
	public String getStandardHours() {
		return this.standardHours;
	}

	public void setStandardHours(String standardHours) {
		this.standardHours = standardHours;
	}
	
	@Column(name="Maximum_Standard_Hours")
	public String getMaximumStandardHours() {
		return this.maximumStandardHours;
	}

	public void setMaximumStandardHours(String maximumStandardHours) {
		this.maximumStandardHours = maximumStandardHours;
	}
	
	@Column(name="Minimum_Standard_Hours")
	public String getMinimumStandardHours() {
		return this.minimumStandardHours;
	}

	public void setMinimumStandardHours(String minimumStandardHours) {
		this.minimumStandardHours = minimumStandardHours;
	}

	@Column(name="Status")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="Transaction_Date")
	public Date getTransactionDate() {
		return this.transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	
	@Column(name="Created_By")
	public int getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Created_Date")
	public Date getCreatedDate() {
		return this.createdDate;
	}
	
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name="Modified_By")
	public int getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Modified_Date")
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name="Is_Resident_Ordinarily_Resident")
	public String getIsResidentOrdinarilyResident() {
		return isResidentOrdinarilyResident;
	}

	public void setIsResidentOrdinarilyResident(String isResidentOrdinarilyResident) {
		this.isResidentOrdinarilyResident = isResidentOrdinarilyResident;
	}

	@Column(name="Is_Resident_But_Not_Ordinarily_Resident")
	public String getIsResidentButNotOrdinarilyResident() {
		return isResidentButNotOrdinarilyResident;
	}

	public void setIsResidentButNotOrdinarilyResident(
			String isResidentButNotOrdinarilyResident) {
		this.isResidentButNotOrdinarilyResident = isResidentButNotOrdinarilyResident;
	}

	@Column(name="Is_Non_Resident")
	public String getIsNonResident() {
		return isNonResident;
	}

	public void setIsNonResident(String isNonResident) {
		this.isNonResident = isNonResident;
	}

	@Column(name="Is_Person_Of_Indian_Origin")
	public String getIsPersonOfIndianOrigin() {
		return isPersonOfIndianOrigin;
	}

	public void setIsPersonOfIndianOrigin(String isPersonOfIndianOrigin) {
		this.isPersonOfIndianOrigin = isPersonOfIndianOrigin;
	}

	@Column(name="Is_Expat_Foreign_National")
	public String getIsExpatForeignNational() {
		return isExpatForeignNational;
	}

	public void setIsExpatForeignNational(String isExpatForeignNational) {
		this.isExpatForeignNational = isExpatForeignNational;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "JobCodeDetailsInfo [jobCodeDetailsId=" + jobCodeDetailsId
				+ ", allowsWorkFromHome=" + allowsWorkFromHome
				+ ", companyDetails=" + companyDetails.getCompanyId() + ", customerDetails="
				+ customerDetails.getCustomerId() + ", jobCodeDetails=" + jobCodeDetails.getJobCodeId()
				+ ", MCountry= " + MCountry.getCountryId()
				+ ", jobCodeSequenceId=" + jobCodeSequenceId + ", jobCategory="
				+ jobCategory + ", jobGrade=" + jobGrade + ", jobLevel="
				+ jobLevel + ", jobName=" + jobName + ", jobTitle=" + jobTitle
				+ ", jobType=" + jobType + ", managerLevel=" + managerLevel
				+ ", maximumStandardHours=" + maximumStandardHours
				+ ", minimumStandardHours=" + minimumStandardHours
				+ ", riskDescription=" + riskDescription + ", riskIndicator="
				+ riskIndicator + ", standardHours=" + standardHours
				+ ", status=" + status + ", transactionDate=" + transactionDate
				+ ", hazardousIndicator=" + hazardousIndicator
				+ ", isPPERequired=" + isPPERequired + ", isCompensatoryJob="
				+ isCompensatoryJob + ", isCriticalJob=" + isCriticalJob
				+ ", isEligibleForOvertime=" + isEligibleForOvertime
				+ ", isEligibleForPerks=" + isEligibleForPerks
				+ ", isEligibleForVariableCompensation="
				+ isEligibleForVariableCompensation + ", isExServiceManJob="
				+ isExServiceManJob + ", isGazetteOfficer=" + isGazetteOfficer
				+ ", isHealthScreeningRequired=" + isHealthScreeningRequired
				+ ", isHighlyCompensatedJob=" + isHighlyCompensatedJob
				+ ", isHotSkillCategory=" + isHotSkillCategory
				+ ", isProbationRequired=" + isProbationRequired
				+ ", isRestrictedJobs=" + isRestrictedJobs + ", isRoasterJob="
				+ isRoasterJob + ", isSkillTesting=" + isSkillTesting
				+ ", isSportsManQuota=" + isSportsManQuota
				+ ", isTrainingRequired=" + isTrainingRequired
				+ ", isUnionJobs=" + isUnionJobs + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy
				+ ", modifiedDate=" + modifiedDate
				+ ", isResidentOrdinarilyResident="
				+ isResidentOrdinarilyResident
				+ ", isResidentButNotOrdinarilyResident="
				+ isResidentButNotOrdinarilyResident + ", isNonResident="
				+ isNonResident + ", isPersonOfIndianOrigin="
				+ isPersonOfIndianOrigin + ", isExpatForeignNational="
				+ isExpatForeignNational + "]";
	}

	
}