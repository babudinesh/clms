package com.Ntranga.CLMS.Dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Ntranga.CLMS.vo.JobCodeVo;
import com.Ntranga.CLMS.vo.MedicalTestVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.core.DateHelper;
import com.Ntranga.core.CLMS.entities.CompanyDetails;
import com.Ntranga.core.CLMS.entities.CustomerDetails;
import com.Ntranga.core.CLMS.entities.JobCodeDetails;
import com.Ntranga.core.CLMS.entities.JobCodeDetailsInfo;
import com.Ntranga.core.CLMS.entities.MCountry;
import com.Ntranga.core.CLMS.entities.MEquipmentTypes;
import com.Ntranga.core.CLMS.entities.MMedicalTests;
import com.Ntranga.core.CLMS.entities.MSkillTests;
import com.Ntranga.core.CLMS.entities.MTrainingTypes;
import common.Logger;

@Transactional
@Repository("jobCodeDao")
@SuppressWarnings("rawtypes")
public class JobCodeDaoImpl implements JobCodeDao {

	 static Logger log = Logger.getLogger(JobCodeDaoImpl.class);
	
	@Autowired
	 SessionFactory sessionFactory;
	@Override
	public String saveJobCode(JobCodeVo paramJobCode) {
		log.info("Entered in saveJobCode()  ::   "+paramJobCode);
		
		//String ppeRequired = paramJobCode.getIsPPERequired() == true ?"Y" :"N";
		 String isPPERequired 						= paramJobCode.getIsPPERequired() 						== true ?"Y" :"N";
		 String isCompensatoryJob 					= paramJobCode.getIsCompensatoryJob() 					== true ?"Y" :"N";
		 String isCriticalJob 						= paramJobCode.getIsCriticalJob() 						== true ?"Y" :"N";
		 String isEligibleForOvertime 				= paramJobCode.getIsEligibleForOvertime() 				== true ?"Y" :"N";
		 String isEligibleForPerks 					= paramJobCode.getIsEligibleForPerks() 					== true ?"Y" :"N";
		 String isEligibleForVariableCompensation 	= paramJobCode.getIsEligibleForVariableCompensation() 	== true ?"Y" :"N";
		 String isExServiceManJob 					= paramJobCode.getIsExServiceManJob() 					== true ?"Y" :"N";
		 String isGazetteOfficer 					= paramJobCode.getIsGazetteOfficer() 					== true ?"Y" :"N";
		 String isHealthScreeningRequired			= paramJobCode.getIsHealthScreeningRequired()			== true ?"Y" :"N";
		 String isHighlyCompensatedJob				= paramJobCode.getIsHighlyCompensatedJob() 				== true ?"Y" :"N";
		 String isHotSkillCategory					= paramJobCode.getIsHotSkillCategory() 					== true ?"Y" :"N";
		 String isProbationRequired					= paramJobCode.getIsProbationRequired()					== true ?"Y" :"N";
		 String isRestrictedJob						= paramJobCode.getIsRestrictedJob() 					== true ?"Y" :"N";
		 String isRoasterJob						= paramJobCode.getIsRoasterJob() 						== true ?"Y" :"N";
		 String isSkillTesting						= paramJobCode.getIsSkillTesting() 						== true ?"Y" :"N";
		 String isSportsManQuota					= paramJobCode.getIsSportsManQuota()	 				== true ?"Y" :"N";
		 String isTrainingRequired					= paramJobCode.getIsTrainingRequired() 					== true ?"Y" :"N";
		 String isUnionJob							= paramJobCode.getIsUnionJob() 							== true ?"Y" :"N";
		 String isAllowsWorkFromHome				= paramJobCode.getIsAllowsWorkFromHome() 				== true ?"Y" :"N";
		 /*String isResidentOrdinarilyResident;
		 String isResidentButNotOrdinarilyResident;
		 String isNonResident;
		 String isPersonOfIndianOrigin;
		 String isExpatForeignNational ;*/
		
		Session session = sessionFactory.getCurrentSession();
		Integer jobCodeDetailsId = 0;
		Integer jobCodeId = 0;
		BigInteger jobSequenceId = new BigInteger("0");	
		/*Integer trainingId = 0;
		Integer skillTestId = 0;
		Integer equipmentId = 0;
		Integer medicalId = 0;*/
		
		/*Time stdHours =  paramJobCode.getStandardHours()!= null  ? DateHelper.getConvertedTime(paramJobCode.getStandardHours()+":00") : null;
		Time maxStdhrs =  paramJobCode.getMaximumStandardHours() != null  ? DateHelper.getConvertedTime(paramJobCode.getMaximumStandardHours()+":00") : null;
		Time minStdHrs   =  paramJobCode.getMinimumStandardHours()  != null    ? DateHelper.getConvertedTime(paramJobCode.getMinimumStandardHours()+":00") : null;
	*/	
		
		JobCodeDetails jobDetails = new JobCodeDetails();
		JobCodeDetailsInfo jobDetailsInfo = new JobCodeDetailsInfo();
		
		try{									
			if(paramJobCode != null && paramJobCode.getJobCodeId() > 0){
				
				jobDetails = (JobCodeDetails) session.load(JobCodeDetails.class, paramJobCode.getJobCodeId());
				jobDetails.setCustomerDetails(new CustomerDetails(paramJobCode.getCustomerId()));
				jobDetails.setCompanyDetails(new CompanyDetails(paramJobCode.getCompanyId()));
				jobDetails.setJobCode(paramJobCode.getJobCode());
				jobDetails.setModifiedBy(paramJobCode.getModifiedBy());
				jobDetails.setModifiedDate(new Date());
				session.update(jobDetails);
				jobCodeId  = paramJobCode.getJobCodeId();
			}else{
				
				jobDetails = new JobCodeDetails();
				jobDetails.setCustomerDetails(new CustomerDetails(paramJobCode.getCustomerId()));
				jobDetails.setCompanyDetails(new CompanyDetails(paramJobCode.getCompanyId()));
				jobDetails.setJobCode(paramJobCode.getJobCode());
				jobDetails.setCreatedBy(paramJobCode.getCreatedBy());
				jobDetails.setModifiedBy(paramJobCode.getModifiedBy());
				jobDetails.setCreatedDate(new Date());
				jobDetails.setModifiedDate(new Date());
				jobCodeId = (Integer) session.save(jobDetails);
			}
			
			
			session.clear();
			//Job_Code_details_info table
			if(paramJobCode != null &&  paramJobCode.getJobCodeDetailsId() > 0){
				jobDetailsInfo = (JobCodeDetailsInfo) sessionFactory.getCurrentSession().load(JobCodeDetailsInfo.class, paramJobCode.getJobCodeDetailsId());
				jobDetailsInfo.setJobCodeDetails(new JobCodeDetails(jobDetails.getJobCodeId()));
				jobDetailsInfo.setCustomerDetails(new CustomerDetails(paramJobCode.getCustomerId()));
				jobDetailsInfo.setCompanyDetails(new CompanyDetails(paramJobCode.getCompanyId()));
				jobDetailsInfo.setMCountry(new MCountry(paramJobCode.getCountryId()));
				jobDetailsInfo.setJobName(paramJobCode.getJobName());
				jobDetailsInfo.setJobTitle(paramJobCode.getJobTitle());
				jobDetailsInfo.setJobType(paramJobCode.getJobType());
				jobDetailsInfo.setJobCategory(paramJobCode.getJobCategory());
				jobDetailsInfo.setJobGrade(paramJobCode.getJobGrade());
				jobDetailsInfo.setJobLevel(paramJobCode.getJobLevel());
				jobDetailsInfo.setManagerLevel(paramJobCode.getManagerLevel());
				jobDetailsInfo.setIsTrainingRequired(isTrainingRequired);
				jobDetailsInfo.setIsPPERequired(isPPERequired);
				jobDetailsInfo.setIsSkillTesting(isSkillTesting);
				jobDetailsInfo.setIsHealthScreeningRequired(isHealthScreeningRequired);
				jobDetailsInfo.setStandardHours(paramJobCode.getStandardHours());
				jobDetailsInfo.setMinimumStandardHours(paramJobCode.getMinimumStandardHours());
				jobDetailsInfo.setMaximumStandardHours(paramJobCode.getMaximumStandardHours());
				jobDetailsInfo.setHazardousIndicator(paramJobCode.getHazardousIndicator());
				jobDetailsInfo.setRiskIndicator(paramJobCode.getRiskIndicator());
				jobDetailsInfo.setRiskDescription(paramJobCode.getRiskDescription());
				jobDetailsInfo.setIsCriticalJob(isCriticalJob);
				jobDetailsInfo.setIsGazetteOfficer(isGazetteOfficer);
				jobDetailsInfo.setIsHighlyCompensatedJob(isHighlyCompensatedJob);
				jobDetailsInfo.setIsProbationRequired(isProbationRequired);
				jobDetailsInfo.setIsHotSkillCategory(isHotSkillCategory);
				jobDetailsInfo.setIsRoasterJob(isRoasterJob);
				jobDetailsInfo.setIsCompensatoryJob(isCompensatoryJob);
				jobDetailsInfo.setIsEligibleForVariableCompensation(isEligibleForVariableCompensation);
				jobDetailsInfo.setIsUnionJobs(isUnionJob);
				jobDetailsInfo.setIsRestrictedJobs(isRestrictedJob);
				jobDetailsInfo.setIsEligibleForPerks(isEligibleForPerks);
				jobDetailsInfo.setIsEligibleForOvertime(isEligibleForOvertime);
				jobDetailsInfo.setAllowsWorkFromHome(isAllowsWorkFromHome);
				jobDetailsInfo.setIsExServiceManJob(isExServiceManJob);
				jobDetailsInfo.setIsSportsManQuota(isSportsManQuota);
				/*jobDetailsInfo.setIsResidentOrdinarilyResident(paramJobCode.getIsResidentOrdinarilyResident());
				jobDetailsInfo.setIsResidentButNotOrdinarilyResident(paramJobCode.getIsResidentButNotOrdinarilyResident());
				jobDetailsInfo.setIsNonResident(paramJobCode.getIsNonResident());
				jobDetailsInfo.setIsPersonOfIndianOrigin(paramJobCode.getIsPersonOfIndianOrigin());
				jobDetailsInfo.setIsExpatForeignNational(paramJobCode.getIsExpatForeignNational());*/
				jobDetailsInfo.setStatus(paramJobCode.getStatus());
				jobDetailsInfo.setTransactionDate(paramJobCode.getTransactionDate());
				jobDetails.setModifiedBy(paramJobCode.getModifiedBy());
				jobDetails.setModifiedDate(new Date());
				session.update(jobDetailsInfo);
				jobCodeDetailsId = jobDetailsInfo.getJobCodeDetailsId();
			}else{	
				jobDetailsInfo = new JobCodeDetailsInfo();
				jobDetailsInfo.setJobCodeDetails(new JobCodeDetails(jobDetails.getJobCodeId()));
				jobDetailsInfo.setCustomerDetails(new CustomerDetails(paramJobCode.getCustomerId()));
				jobDetailsInfo.setCompanyDetails(new CompanyDetails(paramJobCode.getCompanyId()));
				jobDetailsInfo.setMCountry(new MCountry(paramJobCode.getCountryId()));
				jobDetailsInfo.setJobName(paramJobCode.getJobName());
				jobDetailsInfo.setJobTitle(paramJobCode.getJobTitle());
				jobDetailsInfo.setJobType(paramJobCode.getJobType());
				jobDetailsInfo.setJobCategory(paramJobCode.getJobCategory());
				jobDetailsInfo.setJobGrade(paramJobCode.getJobGrade());
				jobDetailsInfo.setJobLevel(paramJobCode.getJobLevel());
				jobDetailsInfo.setManagerLevel(paramJobCode.getManagerLevel());
				jobDetailsInfo.setIsTrainingRequired(isTrainingRequired);
				jobDetailsInfo.setIsPPERequired(isPPERequired);
				jobDetailsInfo.setIsSkillTesting(isSkillTesting);
				jobDetailsInfo.setIsHealthScreeningRequired(isHealthScreeningRequired);
				jobDetailsInfo.setStandardHours(paramJobCode.getStandardHours());
				jobDetailsInfo.setMinimumStandardHours(paramJobCode.getMinimumStandardHours());
				jobDetailsInfo.setMaximumStandardHours(paramJobCode.getMaximumStandardHours());
				jobDetailsInfo.setHazardousIndicator(paramJobCode.getHazardousIndicator());
				jobDetailsInfo.setRiskIndicator(paramJobCode.getRiskIndicator());
				jobDetailsInfo.setRiskDescription(paramJobCode.getRiskDescription());
				jobDetailsInfo.setIsCriticalJob(isCriticalJob);
				jobDetailsInfo.setIsGazetteOfficer(isGazetteOfficer);
				jobDetailsInfo.setIsHighlyCompensatedJob(isHighlyCompensatedJob);
				jobDetailsInfo.setIsProbationRequired(isProbationRequired);
				jobDetailsInfo.setIsHotSkillCategory(isHotSkillCategory);
				jobDetailsInfo.setIsRoasterJob(isRoasterJob);
				jobDetailsInfo.setIsCompensatoryJob(isCompensatoryJob);
				jobDetailsInfo.setIsEligibleForVariableCompensation(isEligibleForVariableCompensation);
				jobDetailsInfo.setIsUnionJobs(isUnionJob);
				jobDetailsInfo.setIsRestrictedJobs(isRestrictedJob);
				jobDetailsInfo.setIsEligibleForPerks(isEligibleForPerks);
				jobDetailsInfo.setIsEligibleForOvertime(isEligibleForOvertime);
				jobDetailsInfo.setAllowsWorkFromHome(isAllowsWorkFromHome);
				jobDetailsInfo.setIsExServiceManJob(isExServiceManJob);
				jobDetailsInfo.setIsSportsManQuota(isSportsManQuota);
				/*jobDetailsInfo.setIsResidentOrdinarilyResident(paramJobCode.getIsResidentOrdinarilyResident());
				jobDetailsInfo.setIsResidentButNotOrdinarilyResident(paramJobCode.getIsResidentButNotOrdinarilyResident());
				jobDetailsInfo.setIsNonResident(paramJobCode.getIsNonResident());
				jobDetailsInfo.setIsPersonOfIndianOrigin(paramJobCode.getIsPersonOfIndianOrigin());
				jobDetailsInfo.setIsExpatForeignNational(paramJobCode.getIsExpatForeignNational());*/
				jobDetailsInfo.setStatus(paramJobCode.getStatus());
				jobDetailsInfo.setTransactionDate(paramJobCode.getTransactionDate());
				jobDetailsInfo.setCreatedBy(paramJobCode.getCreatedBy());
				jobDetailsInfo.setModifiedBy(paramJobCode.getModifiedBy());
				jobDetailsInfo.setCreatedDate(new Date());
				jobDetailsInfo.setModifiedDate(new Date());
				
				if(jobCodeId != null && jobCodeId > 0){
					jobSequenceId  = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Job_Code_Sequence_Id),0) FROM `job_code_details_info` job WHERE  job.Job_Code_Id = "+paramJobCode.getJobCodeId() +" AND job.`Transaction_Date` = '"+DateHelper.convertDateToSQLString(paramJobCode.getTransactionDate())+"' and Customer_Id='"+paramJobCode.getCustomerId()+"' And Company_Id = '"+paramJobCode.getCompanyId()+"'").list().get(0);
					log.info("jobCodeSqunece : "+jobSequenceId);
				}	
				
				jobDetailsInfo.setJobCodeSequenceId(jobSequenceId.intValue()+1);			
				jobCodeDetailsId = (Integer) session.save(jobDetailsInfo);
			}
			
			session.flush();
			
			if(jobCodeId > 0 && paramJobCode.getTrainingList() != null && paramJobCode.getTrainingList().size() > 0){
				Query q = session.createQuery("DELETE FROM MTrainingTypes WHERE Job_Code_Id= "+jobCodeId);
				q.executeUpdate();
			}
			
			if(paramJobCode.getTrainingList() != null){
				for(SimpleObject object : paramJobCode.getTrainingList()){
					MTrainingTypes training  = new MTrainingTypes();
					training.setJobCodeDetails(new JobCodeDetails(jobCodeId));
					training.setTrainingName(object.getName());
					training.setCreatedBy(paramJobCode.getCreatedBy());
					training.setModifiedBy(paramJobCode.getModifiedBy());
					training.setCreatedDate(new Date());
					training.setModifiedDate(new Date());
					
					session.save(training);	
				}
			}
			
			
			if(jobCodeId > 0 && paramJobCode.getMedicalList() != null && paramJobCode.getMedicalList().size() > 0){
				Query q = session.createQuery("DELETE FROM  MMedicalTests WHERE Job_Code_Id= "+jobCodeId);
				q.executeUpdate();
				for(MedicalTestVo object : paramJobCode.getMedicalList()){
					MMedicalTests medicalTests = new MMedicalTests();
					
					medicalTests.setJobCodeDetails(new JobCodeDetails(jobCodeId));
					medicalTests.setMedicalTestName(object.getName());
					medicalTests.setIsOnBoard((object.getIsOnBoard() != null && object.getIsOnBoard()) == true ? "Y" : "N");
					medicalTests.setIsPeriodic((object.getIsPeriodic() != null && object.getIsPeriodic()) == true ? "Y" : "N");
					medicalTests.setCreatedBy(paramJobCode.getCreatedBy());
					medicalTests.setModifiedBy(paramJobCode.getModifiedBy());
					medicalTests.setCreatedDate(new Date());
					medicalTests.setModifiedDate(new Date());
					session.save(medicalTests);	
				}
			}
			if(jobCodeId > 0 && paramJobCode.getSkillList() != null && paramJobCode.getSkillList().size() > 0){
				Query q = session.createQuery("DELETE FROM MSkillTests WHERE Job_Code_Id= " +jobCodeId);
				q.executeUpdate();
				for(SimpleObject object : paramJobCode.getSkillList()){
					MSkillTests skill = new MSkillTests();
					
					skill.setJobCodeDetails(new JobCodeDetails(jobCodeId));
					skill.setSkillTestName(object.getName());
					skill.setCreatedBy(paramJobCode.getCreatedBy());
					skill.setModifiedBy(paramJobCode.getModifiedBy());
					skill.setCreatedDate(new Date());
					skill.setModifiedDate(new Date());
					session.save(skill);
				}
			}
			
			if(jobCodeId > 0 && paramJobCode.getEquipmentList() != null && paramJobCode.getEquipmentList().size() > 0){
				Query q = session.createQuery("DELETE FROM  MEquipmentTypes WHERE Job_Code_Id= "+jobCodeId);
				q.executeUpdate();
				for(SimpleObject object : paramJobCode.getEquipmentList()){
					MEquipmentTypes equipments = new MEquipmentTypes();
					
					equipments.setJobCodeDetails(new JobCodeDetails(jobCodeId));
					equipments.setEquipmentTypeName(object.getName());
					equipments.setCreatedBy(paramJobCode.getCreatedBy());
					equipments.setModifiedBy(paramJobCode.getModifiedBy());
					equipments.setCreatedDate(new Date());
					equipments.setModifiedDate(new Date());
					session.save(equipments);	
					//}
				}
			}
				session.flush();
			
		}catch (Exception e) {
			 if(!(session.getTransaction().wasRolledBack())){
				 session.getTransaction().rollback();
			 }
			 log.error("Error Occurred...   ", e);
			 log.info("Exiting from saveJobCode()  ::  jobCodeDetailsId =  "+jobCodeDetailsId);
		}				
		log.info("Exiting from saveJobCode()  ::  jobCodeDetailsId =  "+jobCodeDetailsId);
		return jobCodeDetailsId+"/"+jobCodeId;
	}

	@Override
	public List<JobCodeVo> getJobCodeById(Integer jobCodeDetailsId) {
		log.info("Entered in  getJobCodeById()  ::   customerId = "+jobCodeDetailsId);
		Session session = sessionFactory.getCurrentSession();
		JobCodeVo returnJob = new JobCodeVo();
		List<JobCodeVo> returnList = new ArrayList<JobCodeVo>();
		String  hqlQuery = "SELECT  jobcode.Customer_Id,  jobcode.Company_Id, jobcode.Job_Code_Details_Id, jobcode.Job_Code_Id, "
						   + " details.Job_Code ,jobcode.Job_Title, jobcode.Job_Name, jobcode.Job_Category,  "
						   + " jobcode.Job_Type, jobcode.Job_Grade, jobcode.Job_Level, jobcode.Transaction_Date, "
						   + " jobcode.Status, jobcode.Is_Training_Required, jobcode.Is_PPE_Required, jobcode.Is_Skill_Testing, "
						   + " jobcode.Is_Health_Screening_Required, jobcode.Standard_Hours, jobcode.Minimum_Standard_Hours, "
						   + " jobcode.Maximum_Standard_Hours, jobcode.Hazardous_Indicator, jobcode.Risk_Indicator, "
						   + " jobcode.Risk_Description, jobcode.Is_Critical_Job, jobcode.Is_Gazette_Officer, "
						   + " jobcode.Is_Highly_Compensated_Job, jobcode.Is_Probation_Required, jobcode.Is_Hot_Skill_Category, "
						   + " jobcode.Is_Roaster_Job, jobcode.Is_Compensatory_Job, jobcode.Is_Eligible_For_Variable_Compensation, "
						   + " jobcode.Is_Union_Job, jobcode.Is_Restricted_Job, jobcode.Is_Eligible_For_Perks, "
						   + " jobcode.Is_Eligible_For_Overtime, jobcode.Allows_Work_From_Home,jobcode.Is_Ex_Service_Man_Job, "
						   + " jobcode.Is_Sports_Man_Quota, jobcode.Is_Resident_Ordinarily_Resident,"
						   + " jobcode.Is_Resident_But_Not_Ordinarily_Resident, jobcode.Is_Non_Resident,"
						   + " jobcode.Is_Person_Of_Indian_Origin, jobcode.Is_Expat_Foreign_National, jobcode.Manager_Level, jobcode.country_Id "
							+ " FROM job_code_details_info AS jobcode "
							+ " LEFT JOIN job_code_details details ON jobcode.Job_Code_Id = details.Job_Code_Id"
							+ " LEFT JOIN M_Medical_Tests medical ON details.Job_Code_Id = medical.Job_Code_Id"
							+ " LEFT JOIN M_Skill_Tests skill ON details.Job_Code_Id = skill.Job_Code_Id"
							+ " LEFT JOIN M_Training_Types training ON details.Job_Code_Id = training.Job_Code_Id"
							+ " LEFT JOIN M_Equipment_Types equipment ON details.Job_Code_Id = equipment.Job_Code_Id"
							+ " WHERE jobcode.job_code_Details_Id = "+jobCodeDetailsId;
		
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			List deptList = query.list();

			for (Object dept : deptList) {
				Object[] obj = (Object[]) dept;
				returnJob = new JobCodeVo();
				returnJob.setCustomerId((Integer)obj[0]);
				returnJob.setCompanyId((Integer)obj[1]);
				returnJob.setJobCodeDetailsId((Integer)obj[2]);
				returnJob.setJobCodeId((Integer)obj[3]);
				returnJob.setJobCode((String)obj[4]);
				returnJob.setJobTitle(obj[5]+"");
				returnJob.setJobName(obj[6]+"");
				returnJob.setJobCategory(obj[7]+"");
				returnJob.setJobType(obj[8]+"");
				returnJob.setJobGrade(obj[9]+"");
				returnJob.setJobLevel(obj[10]+"");
				returnJob.setTransactionDate(DateHelper.convertSQLDateToDate((java.sql.Date)obj[11]));
				returnJob.setStatus(obj[12]+"");
				returnJob.setIsTrainingRequired((obj[13]+"").equalsIgnoreCase("Y")? true : false);
				returnJob.setIsPPERequired((obj[14]+"").equalsIgnoreCase("Y")? true : false);
				returnJob.setIsSkillTesting((obj[15]+"").equalsIgnoreCase("Y")? true : false);
				returnJob.setIsHealthScreeningRequired((obj[16]+"").equalsIgnoreCase("Y")? true : false);
				returnJob.setStandardHours((String)obj[17]);
				returnJob.setMinimumStandardHours((String)(obj[18]));
				returnJob.setMaximumStandardHours((String)(obj[19]));
				returnJob.setHazardousIndicator((String)obj[20]);
				returnJob.setRiskIndicator((String)obj[21]);
				returnJob.setRiskDescription((String)obj[22]);
				returnJob.setIsCriticalJob((obj[23]+"").equalsIgnoreCase("Y")? true : false);
				returnJob.setIsGazetteOfficer((obj[24]+"").equalsIgnoreCase("Y")? true : false);
				returnJob.setIsHighlyCompensatedJob((obj[25]+"").equalsIgnoreCase("Y")? true : false);
				returnJob.setIsProbationRequired((obj[26]+"").equalsIgnoreCase("Y")? true : false);
				returnJob.setIsHotSkillCategory((obj[27]+"").equalsIgnoreCase("Y")? true : false);
				returnJob.setIsRoasterJob((obj[28]+"").equalsIgnoreCase("Y")? true : false);
				returnJob.setIsCompensatoryJob((obj[29]+"").equalsIgnoreCase("Y")? true : false);
				returnJob.setIsEligibleForVariableCompensation((obj[30]+"").equalsIgnoreCase("Y")? true : false);
				returnJob.setIsUnionJob((obj[31]+"").equalsIgnoreCase("Y")? true : false);
				returnJob.setIsRestrictedJob((obj[32]+"").equalsIgnoreCase("Y")? true : false);
				returnJob.setIsEligibleForPerks((obj[33]+"").equalsIgnoreCase("Y")? true : false);
				returnJob.setIsEligibleForOvertime((obj[34]+"").equalsIgnoreCase("Y")? true : false);
				returnJob.setIsAllowsWorkFromHome((obj[35]+"").equalsIgnoreCase("Y")? true : false);
				returnJob.setIsExServiceManJob((obj[36]+"").equalsIgnoreCase("Y")? true : false);
				returnJob.setIsSportsManQuota((obj[37]+"").equalsIgnoreCase("Y")? true : false);
				/*returnJob.setIsResidentOrdinarilyResident(obj[38]+"");
				returnJob.setIsResidentButNotOrdinarilyResident(obj[39]+"");
				returnJob.setIsNonResident(obj[40]+"");
				returnJob.setIsPersonOfIndianOrigin(obj[41]+"");
				returnJob.setIsExpatForeignNational(obj[42]+"");*/
				returnJob.setManagerLevel(obj[43]+"");
				returnJob.setCountryId((Integer)obj[44]);
				
				returnList.add(returnJob);
			}	
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Entered in  getJobCodeById()  ::   "+returnList);
		}
		session.flush();
		log.info("Entered in  getJobCodeById()  ::   "+returnList);
		return returnList;
	}

	@Override
	public List<JobCodeVo> getJobCodesListBySearch(JobCodeVo paramJobCode) {
		log.info("Entered in  getJobCodesListBySearch()  ::   JobCodeVo = "+paramJobCode);
		
		Session session = sessionFactory.getCurrentSession();
		List queryList = null;
		List<JobCodeVo> returnList = new ArrayList<JobCodeVo>();
		JobCodeVo jobcode = new JobCodeVo();
		
		
		String hqlQuery = "SELECT jobcode.Customer_Id, jobCode.Company_Id, company.Company_Name, jobcode.Job_Code_Id, "
							+ " jobcode.Job_Type, jobcode.Job_Category, job.Job_Code, "
							+ "	jobcode.Transaction_Date, jobcode.Status, jobcode.Job_Code_Details_Id, jobcode.Job_Name"
							+ " FROM job_code_details_info AS jobcode "
							+ " INNER JOIN job_code_details job ON job.Job_Code_Id = jobcode.Job_Code_Id"
							+ " INNER JOIN company_details AS cmp  ON jobcode.Customer_Id = cmp.Customer_Id  "
																			+ "	AND jobcode.Company_Id = cmp.Company_Id "
							+ " INNER JOIN company_details_info AS company  ON company.Customer_Id = jobcode.Customer_Id  "
																			+ "	AND company.Company_Id = jobcode.Company_Id "
							+ " INNER JOIN customer_details AS cus  ON jobcode.Customer_Id = cus.Customer_Id "
							+ " INNER JOIN customer_details_info AS customer  ON customer.Customer_Id = jobcode.Customer_Id "

							+ " WHERE CONCAT(DATE_FORMAT(jobcode.transaction_date, '%Y%m%d'), LPAD(jobcode.job_code_Sequence_Id, 2, '0')) =   "
							+ " (  "   
						    + " SELECT MAX(CONCAT(DATE_FORMAT(job1.transaction_date, '%Y%m%d'), LPAD(job1.job_code_Sequence_Id, 2, '0')))  "  
							+ " FROM job_code_details_info job1  " 
							+ " WHERE  job.Job_Code_Id = job1.Job_Code_Id  " 
							+ " AND job1.transaction_date <= CURRENT_DATE() )  "
						 
							+ " AND CONCAT(DATE_FORMAT(company.transaction_date, '%Y%m%d'), LPAD(company.company_Sequence_Id, 2, '0')) =  "   
							+ " (  "   
							+ " SELECT MAX(CONCAT(DATE_FORMAT(company1.transaction_date, '%Y%m%d'), LPAD(company1.company_Sequence_Id, 2, '0')))  "  
							+ " FROM company_details_info company1  " 
							+ " WHERE  company.Company_Id = company1.Company_Id  " 
							+ " AND company1.transaction_date <= CURRENT_DATE() )  "
							   
							+ " AND CONCAT(DATE_FORMAT(customer.transaction_date, '%Y%m%d'), LPAD(customer.customer_Sequence_Id, 2, '0')) =  "   
							+ " (  "   
							+ " SELECT MAX(CONCAT(DATE_FORMAT(customer1.transaction_date, '%Y%m%d'), LPAD(customer1.customer_Sequence_Id, 2, '0')))  "  
							+ " FROM customer_details_info customer1  " 
							+ " WHERE customer.Customer_Id = customer1.Customer_Id  " 
							+ " AND customer1.transaction_date <= CURRENT_DATE() ) ";
			
		if(paramJobCode.getCustomerId() > 0){
			hqlQuery += "  AND  jobcode.Customer_Id = "+paramJobCode.getCustomerId();
		}
		
		if(paramJobCode.getCompanyId() > 0){
			hqlQuery += "  AND  jobcode.Company_Id = "+paramJobCode.getCompanyId();
		}
		
		if(paramJobCode.getJobCode() != null && !paramJobCode.getJobCode().isEmpty()){
			hqlQuery += " AND job.Job_Code LIKE '"+paramJobCode.getJobCode()+"%' ";
		}
		
		if(paramJobCode.getStatus() != null && !paramJobCode.getStatus().isEmpty()){
			hqlQuery += " AND jobcode.Status LIKE '"+paramJobCode.getStatus()+"%' ";
		}
		
		if(paramJobCode.getJobCategory() != null && !paramJobCode.getJobCategory().isEmpty()){
			hqlQuery += " AND jobcode.Job_Category = '"+paramJobCode.getJobCategory()+"' ";
		}
		
		if(paramJobCode.getCountryId() > 0){
			hqlQuery += "  AND  jobcode.Country_Id = "+paramJobCode.getCountryId();
		}
		
		hqlQuery += " GROUP BY jobcode.Job_Code_Id Order By job.Job_Code asc";
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			queryList = query.list();
			if(queryList.size() > 0){
				for (Object customer : queryList) {
					Object[] obj = (Object[]) customer;
					jobcode = new JobCodeVo();
					
					jobcode.setCustomerId((Integer)obj[0]);
					jobcode.setCompanyId((Integer)obj[1]);
					jobcode.setCompanyName((String)obj[2]);
					jobcode.setJobCodeId((Integer)obj[3]);
					jobcode.setJobType(obj[4] == null || (obj[4]+"").equalsIgnoreCase("null") ? "" : obj[4] +"");
					jobcode.setJobCategory(obj[5] == null ? "" : obj[5] +"");
					jobcode.setJobCode((String)obj[6]);
					jobcode.setTransactionDate((Date)obj[7]);
					jobcode.setTransDate(DateHelper.convertSQLDateToString((java.sql.Date)obj[7],"dd/MM/yyyy"));
					jobcode.setStatus((obj[8]+"").equalsIgnoreCase("Y") ? "Active":"Inactive");
					jobcode.setJobCodeDetailsId((Integer)obj[9]);
					jobcode.setJobName(obj[10]+"");

					returnList.add(jobcode);
				}	
			}
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from getJobCodesListBySearch()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from getJobCodesListBySearch()  ::   "+returnList);
		return returnList;
	}

	@Override
	public List<SimpleObject> getTransactionListForJobCode(Integer customerId, Integer companyId, Integer jobCodeId) {
		log.info("Entered in getTransactionListForJobCode()  ::   customerId = "+customerId+" , companyId = "+companyId+" , jobCodeDetailsId = "+jobCodeId);
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> transactionList = new ArrayList<>();
		try {
			List jobCodeList = session.createSQLQuery("SELECT Job_Code_Details_Id AS id ,CONCAT(DATE_FORMAT(Transaction_Date,'%d/%m/%Y'),' - ',Job_Code_Sequence_Id) AS name FROM  job_code_details_info job  WHERE job.Customer_Id = "+customerId+" AND job.Company_Id = "+companyId+" AND job.Job_Code_Id = "+jobCodeId+" ORDER BY job.Transaction_Date, job.Job_Code_Details_Id").list();
			for (Object transDates  : jobCodeList) {
				Object[] transaction = (Object[]) transDates;
				SimpleObject object = new SimpleObject();
				object.setId((Integer)transaction[0]);
				object.setName(transaction[1]+"");
				transactionList.add(object);
			}			
		} catch (Exception e) {
			log.error("Error Occurred...   ", e);
			log.info("Exiting from getTransactionListForJobCode()  ::   "+transactionList);
		}
		
		log.info("Exiting from getTransactionListForJobCode()  ::   "+transactionList);
		return transactionList;
	}
	
	
	
	
	@Override
	public int validateJobcodeAssociationWithWorker(JobCodeVo paramJobCode) {
		log.info("Entered in  validateJobcodeAssociationWithWorker()  ::   JobCodeVo = "+paramJobCode);
		
		Session session = sessionFactory.getCurrentSession();
		List queryList = null;
		int code = 0;
		try {	
		
		String hqlQuery = " SELECT jcd.Job_Code FROM job_code_details jcd "+
							" INNER JOIN work_job_details wjd ON(jcd.job_code_id = wjd.job_name) "+
							" INNER JOIN worker_details wd ON (wd.worker_id = wjd.worker_id)  "+
							" INNER JOIN worker_details_info wdi ON (wdi.worker_id = wd.worker_id) "+
							" WHERE  "+
							"  CONCAT(DATE_FORMAT(wdi.transaction_date, '%Y%m%d'), LPAD(wdi.Sequence_Id, 2, '0')) =    "+  
							"   (      "+
							"   SELECT MAX(CONCAT(DATE_FORMAT(wdi1.transaction_date, '%Y%m%d'), LPAD(wdi1.Sequence_Id, 2, '0')))     "+
							"   FROM worker_details_info wdi1   "+
							"   WHERE  wdi1.worker_id = wdi.worker_id     AND wdi1.transaction_date <= CURRENT_DATE() "+
							"  )";
							
			
		if(paramJobCode.getCustomerId() > 0){
			hqlQuery += "  AND  jcd.Customer_Id = "+paramJobCode.getCustomerId();
		}
		
		if(paramJobCode.getCompanyId() > 0){
			hqlQuery += "  AND  jcd.Company_Id = "+paramJobCode.getCompanyId();
		}
		
		if(paramJobCode.getJobCode() != null && !paramJobCode.getJobCode().isEmpty()){
			hqlQuery += " AND jcd.Job_Code LIKE '"+paramJobCode.getJobCode()+"%' ";
		}
		
		
					
			SQLQuery query = session.createSQLQuery(hqlQuery);
			queryList = query.list();
			if(queryList.size() > 0){			
				code = 1;
				}
			
				
		} catch (Exception e) {
			code = 2;
			log.error("Error occured ... ",e);
			log.info("Exiting from validateJobcodeAssociationWithWorker()  ::   "+code);
		}
		
		log.info("Exiting from validateJobcodeAssociationWithWorker()  ::   "+code);
		return code;
	}
	

}
