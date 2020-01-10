package com.Ntranga.CLMS.vo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class JobCodeVo {

	private int jobCodeDetailsId;
	private boolean isAllowsWorkFromHome;
	private int companyId;
	private int countryId;
	private String jobCode;
	private String customerName;
	private String companyName;
	private int customerId;
	private int jobCodeId;
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
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy", timezone="IST")
	private Date transactionDate;
	private String hazardousIndicator;
	private boolean isPPERequired;
	private boolean isCompensatoryJob;
	private boolean isCriticalJob;
	private boolean isEligibleForOvertime;
	private boolean isEligibleForPerks;
	private boolean isEligibleForVariableCompensation;
	private boolean isExServiceManJob;
	private boolean isGazetteOfficer;
	private boolean isHealthScreeningRequired;
	private boolean isHighlyCompensatedJob;
	private boolean isHotSkillCategory;
	private boolean isProbationRequired;
	private boolean isRestrictedJob;
	private boolean isRoasterJob;
	private boolean isSkillTesting;
	private boolean isSportsManQuota;
	private boolean isTrainingRequired;
	private boolean isUnionJob;
	private boolean isResidentOrdinarilyResident;
	private boolean isResidentButNotOrdinarilyResident;
	private boolean isNonResident;
	private boolean isPersonOfIndianOrigin;
	private boolean isExpatForeignNational ;
	/*private int trainingId;
	private String trainingName;
	private int skillTestId;
	private String  skillTestName;
	private int medicalId;
	private String medicalName;*/
	/*private String isPeriodic;
	private String isOnBoard;*/
	private int equipmentId;
	private String equipmentName;
	private List<SimpleObject> trainingList;
	private List<MedicalTestVo> medicalList;
	private List<SimpleObject> equipmentList;
	private List<SimpleObject> skillList;
	private Integer createdBy;
	private Integer modifiedBy;
	private String transDate;
	
	public JobCodeVo(){		
	}
	
	public JobCodeVo(int jobCodeId,String jobName){
		this.jobCodeId=jobCodeId;
		this.jobName=jobName;
	}
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Integer getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	
	/**
	 * @return the jobCodeDetailsId
	 */
	public int getJobCodeDetailsId() {
		return jobCodeDetailsId;
	}
	/**
	 * @param jobCodeDetailsId the jobCodeDetailsId to set
	 */
	public void setJobCodeDetailsId(int jobCodeDetailsId) {
		this.jobCodeDetailsId = jobCodeDetailsId;
	}
	/**
	 * @return the allowsWorkFromHome
	 */
	public boolean getIsAllowsWorkFromHome() {
		return isAllowsWorkFromHome;
	}
	/**
	 * @param allowsWorkFromHome the allowsWorkFromHome to set
	 */
	public void setIsAllowsWorkFromHome(boolean isAllowsWorkFromHome) {
		this.isAllowsWorkFromHome = isAllowsWorkFromHome;
	}
	/**
	 * @return the companyId
	 */
	public int getCompanyId() {
		return companyId;
	}
	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	/**
	 * @return the countryId
	 */
	public int getCountryId() {
		return countryId;
	}
	/**
	 * @param countryId the countryId to set
	 */
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
	/**
	 * @return the customerId
	 */
	public int getCustomerId() {
		return customerId;
	}
	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	/**
	 * @return the jobCode
	 */
	public String getJobCode() {
		return jobCode;
	}
	/**
	 * @param jobCode the jobCode to set
	 */
	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}
	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}
	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}
	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	/**
	 * @return the jobCodeId
	 */
	public int getJobCodeId() {
		return jobCodeId;
	}
	/**
	 * @param jobCodeId the jobCodeId to set
	 */
	public void setJobCodeId(int jobCodeId) {
		this.jobCodeId = jobCodeId;
	}
	/**
	 * @return the jobCodeSequenceId
	 */
	public int getJobCodeSequenceId() {
		return jobCodeSequenceId;
	}
	/**
	 * @param jobCodeSequenceId the jobCodeSequenceId to set
	 */
	public void setJobCodeSequenceId(int jobCodeSequenceId) {
		this.jobCodeSequenceId = jobCodeSequenceId;
	}
	/**
	 * @return the jobCategory
	 */
	public String getJobCategory() {
		return jobCategory;
	}
	/**
	 * @param jobCategory the jobCategory to set
	 */
	public void setJobCategory(String jobCategory) {
		this.jobCategory = jobCategory;
	}
	/**
	 * @return the jobGrade
	 */
	public String getJobGrade() {
		return jobGrade;
	}
	/**
	 * @param jobGrade the jobGrade to set
	 */
	public void setJobGrade(String jobGrade) {
		this.jobGrade = jobGrade;
	}
	/**
	 * @return the jobLevel
	 */
	public String getJobLevel() {
		return jobLevel;
	}
	/**
	 * @param jobLevel the jobLevel to set
	 */
	public void setJobLevel(String jobLevel) {
		this.jobLevel = jobLevel;
	}
	/**
	 * @return the jobName
	 */
	public String getJobName() {
		return jobName;
	}
	/**
	 * @param jobName the jobName to set
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	/**
	 * @return the jobTitle
	 */
	public String getJobTitle() {
		return jobTitle;
	}
	/**
	 * @param jobTitle the jobTitle to set
	 */
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	/**
	 * @return the jobType
	 */
	public String getJobType() {
		return jobType;
	}
	/**
	 * @param jobType the jobType to set
	 */
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	/**
	 * @return the managerLevel
	 */
	public String getManagerLevel() {
		return managerLevel;
	}
	/**
	 * @param managerLevel the managerLevel to set
	 */
	public void setManagerLevel(String managerLevel) {
		this.managerLevel = managerLevel;
	}
	/**
	 * @return the maximiumStandardHours
	 */
	public String getMaximumStandardHours() {
		return maximumStandardHours;
	}
	/**
	 * @param maximiumStandardHours the maximiumStandardHours to set
	 */
	public void setMaximumStandardHours(String maximumStandardHours) {
		this.maximumStandardHours = maximumStandardHours;
	}
	/**
	 * @return the minimumStandardHours
	 */
	public String getMinimumStandardHours() {
		return minimumStandardHours;
	}
	/**
	 * @param minimumStandardHours the minimumStandardHours to set
	 */
	public void setMinimumStandardHours(String minimumStandardHours) {
		this.minimumStandardHours = minimumStandardHours;
	}
	/**
	 * @return the riskDescription
	 */
	public String getRiskDescription() {
		return riskDescription;
	}
	/**
	 * @param riskDescription the riskDescription to set
	 */
	public void setRiskDescription(String riskDescription) {
		this.riskDescription = riskDescription;
	}
	/**
	 * @return the riskIndicator
	 */
	public String getRiskIndicator() {
		return riskIndicator;
	}
	/**
	 * @param riskIndicator the riskIndicator to set
	 */
	public void setRiskIndicator(String riskIndicator) {
		this.riskIndicator = riskIndicator;
	}
	/**
	 * @return the standardHours
	 */
	public String getStandardHours() {
		return standardHours;
	}
	/**
	 * @param standardHours the standardHours to set
	 */
	public void setStandardHours(String standardHours) {
		this.standardHours = standardHours;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the transactionDate
	 */
	public Date getTransactionDate() {
		return transactionDate;
	}
	/**
	 * @param transactionDate the transactionDate to set
	 */
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	/**
	 * @return the hazardousIndicator
	 */
	public String getHazardousIndicator() {
		return hazardousIndicator;
	}
	/**
	 * @param hazardousIndicator the hazardousIndicator to set
	 */
	public void setHazardousIndicator(String hazardousIndicator) {
		this.hazardousIndicator = hazardousIndicator;
	}
	/**
	 * @return the isPPERequired
	 */
	public boolean getIsPPERequired() {
		return isPPERequired;
	}
	/**
	 * @param isPPERequired the isPPERequired to set
	 */
	public void setIsPPERequired(boolean isPPERequired) {
		this.isPPERequired = isPPERequired;
	}
	/**
	 * @return the isCompensatoryJob
	 */
	public boolean getIsCompensatoryJob() {
		return isCompensatoryJob;
	}
	/**
	 * @param isCompensatoryJob the isCompensatoryJob to set
	 */
	public void setIsCompensatoryJob(boolean isCompensatoryJob) {
		this.isCompensatoryJob = isCompensatoryJob;
	}
	/**
	 * @return the isCriticalJob
	 */
	public boolean getIsCriticalJob() {
		return isCriticalJob;
	}
	/**
	 * @param isCriticalJob the isCriticalJob to set
	 */
	public void setIsCriticalJob(boolean isCriticalJob) {
		this.isCriticalJob = isCriticalJob;
	}
	/**
	 * @return the isEligibleForOvertime
	 */
	public boolean getIsEligibleForOvertime() {
		return isEligibleForOvertime;
	}
	/**
	 * @param isEligibleForOvertime the isEligibleForOvertime to set
	 */
	public void setIsEligibleForOvertime(boolean isEligibleForOvertime) {
		this.isEligibleForOvertime = isEligibleForOvertime;
	}
	/**
	 * @return the isEligibleForPerks
	 */
	public boolean getIsEligibleForPerks() {
		return isEligibleForPerks;
	}
	/**
	 * @param isEligibleForPerks the isEligibleForPerks to set
	 */
	public void setIsEligibleForPerks(boolean isEligibleForPerks) {
		this.isEligibleForPerks = isEligibleForPerks;
	}
	/**
	 * @return the isEligibleForVariableCompensation
	 */
	public boolean getIsEligibleForVariableCompensation() {
		return isEligibleForVariableCompensation;
	}
	/**
	 * @param isEligibleForVariableCompensation the isEligibleForVariableCompensation to set
	 */
	public void setIsEligibleForVariableCompensation(boolean isEligibleForVariableCompensation) {
		this.isEligibleForVariableCompensation = isEligibleForVariableCompensation;
	}
	/**
	 * @return the isExServiceManJob
	 */
	public boolean getIsExServiceManJob() {
		return isExServiceManJob;
	}
	/**
	 * @param isExServiceManJob the isExServiceManJob to set
	 */
	public void setIsExServiceManJob(boolean isExServiceManJob) {
		this.isExServiceManJob = isExServiceManJob;
	}
	/**
	 * @return the isGazetteOfficer
	 */
	public boolean getIsGazetteOfficer() {
		return isGazetteOfficer;
	}
	/**
	 * @param isGazetteOfficer the isGazetteOfficer to set
	 */
	public void setIsGazetteOfficer(boolean isGazetteOfficer) {
		this.isGazetteOfficer = isGazetteOfficer;
	}
	/**
	 * @return the isHealthScreeningRequired
	 */
	public boolean getIsHealthScreeningRequired() {
		return isHealthScreeningRequired;
	}
	/**
	 * @param isHealthScreeningRequired the isHealthScreeningRequired to set
	 */
	public void setIsHealthScreeningRequired(boolean isHealthScreeningRequired) {
		this.isHealthScreeningRequired = isHealthScreeningRequired;
	}
	/**
	 * @return the isHighlyCompensatedJob
	 */
	public boolean getIsHighlyCompensatedJob() {
		return isHighlyCompensatedJob;
	}
	/**
	 * @param isHighlyCompensatedJob the isHighlyCompensatedJob to set
	 */
	public void setIsHighlyCompensatedJob(boolean isHighlyCompensatedJob) {
		this.isHighlyCompensatedJob = isHighlyCompensatedJob;
	}
	/**
	 * @return the isHotSkillCategory
	 */
	public boolean getIsHotSkillCategory() {
		return isHotSkillCategory;
	}
	/**
	 * @param isHotSkillCategory the isHotSkillCategory to set
	 */
	public void setIsHotSkillCategory(boolean isHotSkillCategory) {
		this.isHotSkillCategory = isHotSkillCategory;
	}
	/**
	 * @return the isProbationRequired
	 */
	public boolean getIsProbationRequired() {
		return isProbationRequired;
	}
	/**
	 * @param isProbationRequired the isProbationRequired to set
	 */
	public void setIsProbationRequired(boolean isProbationRequired) {
		this.isProbationRequired = isProbationRequired;
	}
	/**
	 * @return the isRestrictedJobs
	 */
	public boolean getIsRestrictedJob() {
		return isRestrictedJob;
	}
	/**
	 * @param isRestrictedJobs the isRestrictedJobs to set
	 */
	public void setIsRestrictedJob(boolean isRestrictedJobs) {
		this.isRestrictedJob = isRestrictedJobs;
	}
	/**
	 * @return the isRoasterJob
	 */
	public boolean getIsRoasterJob() {
		return isRoasterJob;
	}
	/**
	 * @param isRoasterJob the isRoasterJob to set
	 */
	public void setIsRoasterJob(boolean isRoasterJob) {
		this.isRoasterJob = isRoasterJob;
	}
	/**
	 * @return the isSkillTesting
	 */
	public boolean getIsSkillTesting() {
		return isSkillTesting;
	}
	/**
	 * @param isSkillTesting the isSkillTesting to set
	 */
	public void setIsSkillTesting(boolean isSkillTesting) {
		this.isSkillTesting = isSkillTesting;
	}
	/**
	 * @return the isSportsManQuota
	 */
	public boolean getIsSportsManQuota() {
		return isSportsManQuota;
	}
	/**
	 * @param isSportsManQuota the isSportsManQuota to set
	 */
	public void setIsSportsManQuota(boolean isSportsManQuota) {
		this.isSportsManQuota = isSportsManQuota;
	}
	/**
	 * @return the isTrainingRequired
	 */
	public boolean getIsTrainingRequired() {
		return isTrainingRequired;
	}
	/**
	 * @param isTrainingRequired the isTrainingRequired to set
	 */
	public void setIsTrainingRequired(boolean isTrainingRequired) {
		this.isTrainingRequired = isTrainingRequired;
	}
	/**
	 * @return the isUnionJob
	 */
	public boolean getIsUnionJob() {
		return isUnionJob;
	}
	/**
	 * @param isUnionJob the isUnionJob to set
	 */
	public void setIsUnionJob(boolean isUnionJob) {
		this.isUnionJob = isUnionJob;
	}
	
	public boolean getIsResidentOrdinarilyResident() {
		return isResidentOrdinarilyResident;
	}
	public void setIsResidentOrdinarilyResident(boolean isResidentOrdinarilyResident) {
		this.isResidentOrdinarilyResident = isResidentOrdinarilyResident;
	}
	public boolean getIsResidentButNotOrdinarilyResident() {
		return isResidentButNotOrdinarilyResident;
	}
	public void setIsResidentButNotOrdinarilyResident(boolean isResidentButNotOrdinarilyResident) {
		this.isResidentButNotOrdinarilyResident = isResidentButNotOrdinarilyResident;
	}
	public boolean getIsNonResident() {
		return isNonResident;
	}
	public void setIsNonResident(boolean isNonResident) {
		this.isNonResident = isNonResident;
	}
	public boolean getIsPersonOfIndianOrigin() {
		return isPersonOfIndianOrigin;
	}
	public void setIsPersonOfIndianOrigin(boolean isPersonOfIndianOrigin) {
		this.isPersonOfIndianOrigin = isPersonOfIndianOrigin;
	}
	public boolean getIsExpatForeignNational() {
		return isExpatForeignNational;
	}
	public void setIsExpatForeignNational(boolean isExpatForeignNational) {
		this.isExpatForeignNational = isExpatForeignNational;
	}
	/*public int getTrainingId() {
		return trainingId;
	}
	public void setTrainingId(int trainingId) {
		this.trainingId = trainingId;
	}
	public String getTrainingName() {
		return trainingName;
	}
	public void setTrainingName(String trainingName) {
		this.trainingName = trainingName;
	}
	public int getSkillTestId() {
		return skillTestId;
	}
	public void setSkillTestId(int skillTestId) {
		this.skillTestId = skillTestId;
	}
	public String getSkillTestName() {
		return skillTestName;
	}
	public void setSkillTestName(String skillTestName) {
		this.skillTestName = skillTestName;
	}
	public int getMedicalId() {
		return medicalId;
	}
	public void setMedicalId(int medicalId) {
		this.medicalId = medicalId;
	}
	public String getMedicalName() {
		return medicalName;
	}
	public void setMedicalName(String medicalName) {
		this.medicalName = medicalName;
	}
	public String getIsPeriodic() {
		return isPeriodic;
	}
	public void setIsPeriodic(String isPeriodic) {
		this.isPeriodic = isPeriodic;
	}
	public String getIsOnBoard() {
		return isOnBoard;
	}
	public void setIsOnBoard(String isOnBoard) {
		this.isOnBoard = isOnBoard;
	}*/
	public int getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(int equipmentId) {
		this.equipmentId = equipmentId;
	}
	public String getEquipmentName() {
		return equipmentName;
	}
	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}
	public List<SimpleObject> getTrainingList() {
		return trainingList;
	}
	public void setTrainingList(List<SimpleObject> trainingList) {
		this.trainingList = trainingList;
	}
	public List<MedicalTestVo> getMedicalList() {
		return medicalList;
	}
	public void setMedicalList(List<MedicalTestVo> medicalList) {
		this.medicalList = medicalList;
	}
	public List<SimpleObject> getEquipmentList() {
		return equipmentList;
	}
	public void setEquipmentList(List<SimpleObject> equipmentList) {
		this.equipmentList = equipmentList;
	}
	public List<SimpleObject> getSkillList() {
		return skillList;
	}
	public void setSkillList(List<SimpleObject> skillList) {
		this.skillList = skillList;
	}
	@Override
	public String toString() {
		return "JobCodeVo [jobCodeDetailsId=" + jobCodeDetailsId
				+ ", isAllowsWorkFromHome=" + isAllowsWorkFromHome
				+ ", companyId=" + companyId + ", countryId=" + countryId
				+ ", jobCode=" + jobCode + ", customerName=" + customerName
				+ ", companyName=" + companyName + ", customerId=" + customerId
				+ ", jobCodeId=" + jobCodeId + ", jobCodeSequenceId="
				+ jobCodeSequenceId + ", jobCategory=" + jobCategory
				+ ", jobGrade=" + jobGrade + ", jobLevel=" + jobLevel
				+ ", jobName=" + jobName + ", jobTitle=" + jobTitle
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
				+ ", isRestrictedJob=" + isRestrictedJob + ", isRoasterJob="
				+ isRoasterJob + ", isSkillTesting=" + isSkillTesting
				+ ", isSportsManQuota=" + isSportsManQuota
				+ ", isTrainingRequired=" + isTrainingRequired
				+ ", isUnionJob=" + isUnionJob
				+ ", isResidentOrdinarilyResident="
				+ isResidentOrdinarilyResident
				+ ", isResidentButNotOrdinarilyResident="
				+ isResidentButNotOrdinarilyResident + ", isNonResident="
				+ isNonResident + ", isPersonOfIndianOrigin="
				+ isPersonOfIndianOrigin + ", isExpatForeignNational="
				+ isExpatForeignNational + ", equipmentId=" + equipmentId
				+ ", equipmentName=" + equipmentName + ", trainingList="
				+ trainingList + ", medicalList=" + medicalList
				+ ", equipmentList=" + equipmentList + ", skillList="
				+ skillList + ", createdBy=" + createdBy + ", modifiedBy="
				+ modifiedBy + "]";
	}
	
	
	
	
}
