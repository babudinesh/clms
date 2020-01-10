package com.Ntranga.CLMS.Dao;


import java.io.File;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.Ntranga.CLMS.vo.LabourTimeVo;
import com.Ntranga.CLMS.vo.ShiftsDefineVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.WorkerCertificationDetailsVo;
import com.Ntranga.CLMS.vo.WorkerDetailsVo;
import com.Ntranga.CLMS.vo.WorkerEducationAndEmploymentVo;
import com.Ntranga.CLMS.vo.WorkerEducationDetailsVo;
import com.Ntranga.CLMS.vo.WorkerEmploymentDetailsVo;
import com.Ntranga.CLMS.vo.WorkerIdentificationProofVo;
import com.Ntranga.CLMS.vo.WorkerNomineeDetailsVo;
import com.Ntranga.CLMS.vo.WorkerReferenceVo;
import com.Ntranga.CLMS.vo.WorkerVerificationStatusVo;
import com.Ntranga.core.DateHelper;
import com.Ntranga.core.CLMS.entities.CompanyDetails;
import com.Ntranga.core.CLMS.entities.CustomerDetails;
import com.Ntranga.core.CLMS.entities.VendorDetails;
import com.Ntranga.core.CLMS.entities.WorkerAddress;
import com.Ntranga.core.CLMS.entities.WorkerCertificationDetails;
import com.Ntranga.core.CLMS.entities.WorkerDetails;
import com.Ntranga.core.CLMS.entities.WorkerDetailsInfo;
import com.Ntranga.core.CLMS.entities.WorkerEducationDetails;
import com.Ntranga.core.CLMS.entities.WorkerEmploymentDetails;
import com.Ntranga.core.CLMS.entities.WorkerIdentificationProof;
import com.Ntranga.core.CLMS.entities.WorkerNomineeDetails;
import com.Ntranga.core.CLMS.entities.WorkerReference;
import com.Ntranga.core.CLMS.entities.WorkerVerificationStatus;
import com.Ntranga.core.CLMS.entities.WorkerVerificationTypesSetup;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import common.Logger;

@SuppressWarnings({"rawtypes","unchecked","unused"})
@Transactional
@Repository("workerDao")
public class WorkerDaoImpl implements WorkerDao  {

	private static Logger log = Logger.getLogger(WorkerDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
    private HttpServletRequest request;

	private @Value("#{db['db.schemaName']}")
	String dbSchemaName;
	
	private @Value("#{system['ROOT_FOLDER_WORKER_VERIFICATION']}")
	String ROOT_FOLDER_WORKER_VERIFICATION;
	

 
 
 //private final static String filePath = "db.url";
 

	
	
/*=============================== Worker Details Start ====================================*/
	
	@Override
	public List<WorkerDetailsVo> workerGridDetails(String customerId,String companyId,String vendorId,String status,String workerCode, String workerName,String workerId) {
		Session session = sessionFactory.getCurrentSession();
		List<WorkerDetailsVo> workerDetailsList = new ArrayList<WorkerDetailsVo>();
			try {			
			 String q = " SELECT distinct cdi.customer_name, "
								+"  com.company_name, "
								+" vdi.vendor_name, "
								+" CONCAT(wdi.first_name,' ',wdi.last_name) AS Worker, "
								+" wd.worker_code, "
								+" CASE wdi.is_active WHEN 'Y' THEN 'Active' ELSE CASE wdi.is_active  WHEN 'I' THEN 'New' ELSE 'In-Active' END END AS STATUS,wdi.`Worker_info_id`,wdi.shift_name,wdi.weekly_off "							+" FROM worker_details wd  "
								+" INNER JOIN worker_details_info wdi ON(wd.worker_id = wdi.worker_id) "
								+" INNER JOIN `customer_details_info` cdi ON(wd.customer_id =cdi.customer_id) "
								+" 	INNER JOIN `company_details_info` com ON (com.company_id = wd.company_id) "
								+" INNER JOIN `vendor_details_info` vdi ON(vdi.vendor_id = wd.vendor_id) "
								+" WHERE  CONCAT(DATE_FORMAT(wdi.transaction_date, '%Y%m%d'), LPAD(wdi.Sequence_Id, 2, '0')) =  "
								+" ( "
								+" SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.Sequence_Id, 2, '0'))) "
								+" FROM worker_details_info vdi1 "
								+" WHERE  wdi.`worker_id` = vdi1.`worker_id`     "
								+"  AND vdi1.transaction_date <= CURRENT_DATE()   "
								+" ) "
								+" AND  "
								+" CONCAT(DATE_FORMAT(cdi.transaction_date, '%Y%m%d'), LPAD(cdi.`Customer_Sequence_Id`, 2, '0')) =  "
								+" ( "
								+" SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.`Customer_Sequence_Id`, 2, '0'))) "
								+" FROM customer_details_info vdi1 "
								+" WHERE cdi.`customer_id` = vdi1.`customer_id`   "
								+"  AND vdi1.transaction_date <= CURRENT_DATE()   "
								+" ) "
								+" AND  "
								+" CONCAT(DATE_FORMAT(com.transaction_date, '%Y%m%d'), LPAD(com.Company_Sequence_Id, 2, '0')) =  "
								+" ( "
								+" SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.`Company_Sequence_Id`, 2, '0'))) "
								+" FROM company_details_info vdi1 "
								+" WHERE com.`company_id` = vdi1.  `company_id` "
								+"  AND vdi1.transaction_date <= CURRENT_DATE()   "
								+" ) "								
								+" 	AND  "
								+" CONCAT(DATE_FORMAT(vdi.transaction_date, '%Y%m%d'), LPAD(vdi.Sequence_Id, 2, '0')) =  "
								+" ( "
								+" SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.Sequence_Id, 2, '0'))) "
								+" FROM vendor_details_info vdi1 "
								+" WHERE vdi.`vendor_id` = vdi1.`vendor_id` "
								+"  AND vdi1.transaction_date <= CURRENT_DATE()"
								+" ) ";
			 
			 			
			 			
					 if(customerId != null && !customerId.isEmpty() && !customerId.equalsIgnoreCase("0")){						
						 q +=" and cdi.Customer_Id = '"+customerId+"'";				 
					 } 
					 if(companyId != null && !companyId.isEmpty() && !companyId.equalsIgnoreCase("0") ){						 
						 q +=" and com.Company_Id = '"+companyId+"' ";	
					 } 
					 if(vendorId != null && !vendorId.isEmpty() ){						 
						 q +=" and vdi.Vendor_Id = '"+vendorId+"'";	
					 }
					 
					 if(status != null && !status.isEmpty()){
						 q +=" and wdi.is_active = '"+status+"'";
					 }
					 if(workerCode != null && !workerCode.isEmpty()){
						 q +=" and wd.worker_code = '"+workerCode+"'";
					 }
					 if(workerName != null && !workerName.isEmpty()){
						 q +=" and concat (wdi.first_name,' ',wdi.last_name) like '%"+workerName+"%' ";
					 }
					 
					 if(workerId != null && !workerId.isEmpty() && !workerId.equalsIgnoreCase("null") && !workerId.equalsIgnoreCase("0")){
						 q +=" and wd.worker_id = '"+workerId+"' ";
					 }
					 
					 q += " ORDER BY Worker asc";
								 
			
			 List tempList = session.createSQLQuery(q).list();
			// System.out.println(q);
			for(Object o : tempList){
				Object[] obj = (Object[]) o;
				WorkerDetailsVo workerVo = new WorkerDetailsVo();
				workerVo.setCustomerName(obj[0]+"");
				workerVo.setCompanyName(obj[1]+"");
				workerVo.setVendorName(obj[2]+"");
				workerVo.setFirstName(obj[3]+"");
				workerVo.setWorkerCode(obj[4]+"");
				workerVo.setIsActive(obj[5]+"");			
				workerVo.setWorkerInfoId((Integer)obj[6]);
				if(obj[7] != null){
					workerVo.setShiftName(obj[7]+"");
				}				
				if(obj[8] != null){
					workerVo.setWeeklyOff(obj[8]+"");
				}
				
				workerDetailsList.add(workerVo);
			}						
		} catch (Exception e) {
			log.error("Error Occured ",e);

		}
		
		
		return workerDetailsList;
	}
	

	public Integer saveOrUpdateWorkerDetails(WorkerDetailsVo workerVo) {
		Session session = sessionFactory.getCurrentSession();
		WorkerDetails workerDetails = new WorkerDetails(); 
		Integer workerId = 0;
		Integer workerInfoId = 0;
		String schema = null;
		try {
			
			if(workerVo.getSchemaName() != null && !workerVo.getSchemaName().isEmpty() && !workerVo.getSchemaName().equalsIgnoreCase("null")){
				schema = workerVo.getSchemaName();
			}else{
				schema = dbSchemaName;
			}
			if(workerVo != null && (workerVo.getWorkerId() == null || workerVo.getWorkerId() == 0)){
				workerDetails.setCustomerId(workerVo.getCustomerId());
				workerDetails.setCompanyId(workerVo.getCompanyId());
				workerDetails.setVendorId(workerVo.getVendorId());
				workerDetails.setCountryId(workerVo.getCountryId());
				workerDetails.setWorkerCode(workerVo.getWorkerCode());
				workerDetails.setCreatedBy(workerVo.getCreatedBy());
				workerDetails.setCreatedDate(new Date());
				workerDetails.setModifiedBy(workerVo.getModifiedBy());
				workerDetails.setModifiedDate(new Date());
				workerId = (Integer) session.save(workerDetails);
				
			}else if(workerVo.getWorkerId() >0){
				workerDetails = (WorkerDetails) session.load(WorkerDetails.class, workerVo.getWorkerId());
				workerDetails.setCustomerId(workerVo.getCustomerId());
				workerDetails.setCompanyId(workerVo.getCompanyId());
				workerDetails.setVendorId(workerVo.getVendorId());
				workerDetails.setCountryId(workerVo.getCountryId());
				workerDetails.setWorkerCode(workerVo.getWorkerCode());
				//workerDetails.setCreatedBy(workerVo.getCreatedBy());	
				//workerDetails.setCreatedDate(workerVo.getCreatedDate());				
				workerDetails.setModifiedBy(workerVo.getModifiedBy());
				workerDetails.setModifiedDate(new Date());
				//workerDetails.setWorkerId(workerVo.getWorkerId());
				session.update(workerDetails);
				workerId = workerDetails.getWorkerId();
				
			}
			
			WorkerDetailsInfo detailsInfo = new WorkerDetailsInfo();

			detailsInfo.setCustomerId(workerVo.getCustomerId());
			detailsInfo.setCompanyId(workerVo.getCompanyId());
			detailsInfo.setWorkerDetails(new WorkerDetails(workerId));
			detailsInfo.setTransactionDate(workerVo.getTransactionDate());
			detailsInfo.setFirstName(workerVo.getFirstName());
			detailsInfo.setLastName(workerVo.getLastName());
			detailsInfo.setMiddleName(workerVo.getMiddleName());
			detailsInfo.setFatherOrSpouse(workerVo.getFatherOrSpouse());
			detailsInfo.setFatherSpouseName(workerVo.getFatherSpouseName());
			detailsInfo.setPanNumber(workerVo.getPanNumber());
			detailsInfo.setDateOfBirth(workerVo.getDateOfBirth());
			detailsInfo.setGender(workerVo.getGender());			
			detailsInfo.setMaritalStatus(workerVo.getMaritalStatus());
			detailsInfo.setAge(workerVo.getAge());
			detailsInfo.setDateOfLeaving(workerVo.getDateOfLeaving());
			detailsInfo.setBloodGroup(workerVo.getBloodGroup());
			detailsInfo.setReligion(workerVo.getReligion());
			detailsInfo.setPhoneNumber(workerVo.getPhoneNumber());
			detailsInfo.setEmail(workerVo.getEmail());
			detailsInfo.setEmergencyContactNumber(workerVo.getEmergencyContactNumber());
			detailsInfo.setEmergencyContactPerson(workerVo.getEmergencyContactPerson());
			detailsInfo.setIsActive(workerVo.getIsActive());
			detailsInfo.setReasonForStatusChange(workerVo.getReasonForStatus());
			detailsInfo.setDateOfJoining(workerVo.getDateOfJoining());
			detailsInfo.setShiftName(workerVo.getShiftName());
			detailsInfo.setWeeklyOff(workerVo.getWeeklyOff());
			detailsInfo.setImagePath(workerVo.getImagePath());
			detailsInfo.setImageName(workerVo.getImageName());
			detailsInfo.setAccountNumber(workerVo.getAcountNumber());
			detailsInfo.setAcountHolderName(workerVo.getAcountHolderName());
			detailsInfo.setBranchName(workerVo.getBranchName());
			detailsInfo.setBankName(workerVo.getBankName());
			detailsInfo.setIfscCode(workerVo.getIfscCode());
			detailsInfo.setPlaceOfBirth(workerVo.getPlaceOfBirth());
			detailsInfo.setMotherName(workerVo.getMotherName());
			detailsInfo.setLanguage(workerVo.getLanguage());
			detailsInfo.setNationality(workerVo.getNationality());
			detailsInfo.setEducation(workerVo.getEducation());
			detailsInfo.setDomicile(workerVo.getDomicile());
			BigInteger SequenceId ;
			if(workerVo != null &&  workerVo.getWorkerInfoId() > 0){	
				detailsInfo.setSequenceId(workerVo.getSequenceId());
				detailsInfo.setWorkerInfoId(workerVo.getWorkerInfoId());
				SequenceId = BigInteger.valueOf(workerVo.getSequenceId());
				detailsInfo.setCreatedBy(workerVo.getCreatedBy());
				detailsInfo.setCreatedDate(workerVo.getCreatedDate());
				detailsInfo.setModifiedBy(workerVo.getModifiedBy());
				detailsInfo.setModifiedDate(new Date());
				session.update(detailsInfo);
				workerInfoId = workerVo.getWorkerInfoId();				
				
			}else  {
				SequenceId = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Sequence_Id),0) FROM `worker_details_info` b  WHERE  b.worker_Id = '"+workerVo.getWorkerId()+"' AND  b.`Transaction_Date` = '"+DateHelper.convertDateToSQLString(workerVo.getTransactionDate())+"'").list().get(0);
				if(SequenceId.intValue() >0){
					detailsInfo.setSequenceId(SequenceId.intValue()+1);
					SequenceId = BigInteger.valueOf(detailsInfo.getSequenceId());
				}else{
					detailsInfo.setSequenceId(1);					
					SequenceId = BigInteger.valueOf(detailsInfo.getSequenceId());
				}
				detailsInfo.setCreatedBy(workerVo.getCreatedBy());
				detailsInfo.setCreatedDate(new Date());
				detailsInfo.setModifiedBy(workerVo.getModifiedBy());
				detailsInfo.setModifiedDate(new Date());
				workerInfoId = (Integer)session.save(detailsInfo);		
			
			}
			
			if(workerVo.getWorkerInfoId() != null && workerVo.getWorkerInfoId() >0){						
				Query q = session.createSQLQuery("delete from worker_address where worker_Id = '"+workerId+"' and Sequence_Id = '"+SequenceId.intValue()+"' and Transaction_Date ='"+DateHelper.convertDateToSQLString(workerVo.getTransactionDate())+"'");
				q.executeUpdate();
			}
			
			WorkerAddress addressPermanent = new WorkerAddress();			
			addressPermanent.setCustomerId(workerVo.getCustomerId());
			addressPermanent.setCompanyId(workerVo.getCompanyId());
			addressPermanent.setAddressType("Permanent");
			addressPermanent.setAddressLine1(workerVo.getPermanentAddressLine1());
			addressPermanent.setAddressLine2(workerVo.getPermanentAddressLine2());
			addressPermanent.setAddressLine3(workerVo.getPermanentAddressLine3());
			addressPermanent.setCountryId(workerVo.getPermanentCountryId());
			addressPermanent.setStateId(workerVo.getPermanentStateId());
			addressPermanent.setCity(workerVo.getPermanentCity());
			addressPermanent.setPinCode(workerVo.getPermanentPinCode());
			addressPermanent.setWorkerDetails(new WorkerDetails(workerId));
			addressPermanent.setSequenceId(SequenceId.intValue());
			addressPermanent.setTransactionDate(workerVo.getTransactionDate());
			addressPermanent.setCreatedBy(workerVo.getCreatedBy());
			addressPermanent.setCreatedDate(new Date());
			addressPermanent.setModifiedBy(workerVo.getModifiedBy());
			addressPermanent.setModifiedDate(new Date());		
			addressPermanent.setIsSameAddress(workerVo.getIsSameAddress() == true ? "Yes" : "No");
			session.save(addressPermanent);
			
			
			WorkerAddress addressPresent = new WorkerAddress();
			addressPresent.setCustomerId(workerVo.getCustomerId());
			addressPresent.setCompanyId(workerVo.getCompanyId());
			addressPresent.setWorkerDetails(new WorkerDetails(workerId));
			addressPresent.setAddressType("Local");
			addressPresent.setAddressLine1(workerVo.getPresentAddressLine1());
			addressPresent.setAddressLine2(workerVo.getPresentAddressLine2());
			addressPresent.setAddressLine3(workerVo.getPresentAddressLine3());
			addressPresent.setCountryId(workerVo.getPresentcountryId());
			addressPresent.setStateId(workerVo.getPresentStateId());
			addressPresent.setCity(workerVo.getPresentCity());
			addressPresent.setPinCode(workerVo.getPresentPinCode());
			addressPresent.setSequenceId(SequenceId.intValue());
			addressPresent.setTransactionDate(workerVo.getTransactionDate());
			addressPresent.setCreatedBy(workerVo.getCreatedBy());
			addressPresent.setCreatedDate(new Date());
			addressPresent.setModifiedBy(workerVo.getModifiedBy());
			addressPresent.setModifiedDate(new Date());
			addressPresent.setIsSameAddress(workerVo.getIsSameAddress() == true ? "Yes" : "No");
			session.save(addressPresent);
			
			WorkerIdentificationProof  identificationProof = new WorkerIdentificationProof();
			if(workerVo.getIdentityList() != null && workerVo.getIdentityList().size() > 0 ){						
				/*Query q = session.createSQLQuery("delete from worker_identification_proof where worker_Id = '"+workerId+"' and Sequence_Id = '"+SequenceId.intValue()+"' and Transaction_Date ='"+DateHelper.convertDateToSQLString(workerVo.getTransactionDate())+"'");
				q.executeUpdate();*/
			 
			
				for(WorkerIdentificationProofVo  identification :workerVo.getIdentityList() ){
					
					log.info("********  "+identification.getIdentificationType());
					identificationProof = new WorkerIdentificationProof();	
					if(identification.getIdentificationType() != null ){
						identificationProof.setCustomerId(workerVo.getCustomerId());
						identificationProof.setCompanyId(workerVo.getCompanyId());
						identificationProof.setCountryId(identification.getCountryId());
						identificationProof.setWorkerDetails(new WorkerDetails(workerId));
						identificationProof.setSequenceId(SequenceId.intValue());
						identificationProof.setTransactionDate(workerVo.getTransactionDate());
						identificationProof.setIdentificationType(identification.getIdentificationType());
						identificationProof.setIdentificationNumber(identification.getIdentificationNumber());
						identificationProof.setFilePath(identification.getFilePath());						
						identificationProof.setCreatedBy(workerVo.getCreatedBy());
						identificationProof.setCreatedDate(new Date());
						identificationProof.setModifiedBy(workerVo.getModifiedBy());
						identificationProof.setModifiedDate(new Date());
						session.save(identificationProof);
					}
				}
			}
			
			 List labourScheduledList = session.createSQLQuery(
					"SELECT date_field AS Business_date, vd.vendor_code,worker_CODE,replace((CONCAT(RTRIM(first_name), ' ',CASE WHEN (middle_name IS NULL) THEN '' ELSE CONCAT(RTRIM(middle_name),' ') END, last_name)),'  ',' ') , "
							+ " (CASE WHEN TRIM(UPPER(Weekly_off)) = UPPER(DATE_FORMAT(date_field,'%a')) THEN 'WO' ELSE shift_name END) AS Shift, "
							+ " Weekly_Off,vdi.vendor_name,wd.customer_id,wd.company_id,wd.vendor_id,wd.worker_id,wjd.location_id,wjd.plant_id,wjd.department_id,wjd.section_id, "
							+ " wjd.work_area_id,wjd.work_skill,jcdi.job_name  "
							+ " FROM worker_details wd  "
							+ " JOIN worker_details_info wdi ON wd.worker_id = wdi.worker_id  "
							+ " JOIN vendor_details vd ON  wd.vendor_id = vd.vendor_id  "
							+ " JOIN vendor_details_info vdi ON ( vd.vendor_id =  vdi.vendor_id AND (CONCAT(DATE_FORMAT(`vdi`.`Transaction_Date`, '%Y%m%d'), `vdi`.`Sequence_Id`) = (SELECT MAX(CONCAT(DATE_FORMAT(`vdi1`.`Transaction_Date`, '%Y%m%d'), `vdi1`.`Sequence_Id`)) FROM `vendor_details_info` `vdi1` WHERE ((`vdi`.`vendor_id` = `vdi1`.`vendor_id`) AND (`vdi1`.`Transaction_Date` <= CURDATE())))) ) "
							+ " JOIN  work_job_details wjd ON (wjd.worker_id = wd.worker_id AND CONCAT(DATE_FORMAT(wjd.transaction_date, '%Y%m%d'), wjd.Sequence_Id) = ( SELECT MAX(CONCAT(DATE_FORMAT(wjd1.transaction_date, '%Y%m%d'), wjd1.Sequence_Id)) FROM work_job_details wjd1 WHERE wjd.`Worker_Id` = wjd1.`Worker_Id`  AND wjd1.`Company_Id` = wjd.`Company_Id`  AND wjd1.`Customer_Id` = wjd.customer_id  AND wjd1.transaction_date <= CURRENT_DATE())) "
							+ " JOIN job_code_details_info jcdi ON (jcdi.job_code_id = wjd.job_name AND CONCAT(DATE_FORMAT(jcdi.transaction_date, '%Y%m%d'), jcdi.`Job_Code_Sequence_Id`) = ( SELECT MAX(CONCAT(DATE_FORMAT(wjd1.transaction_date, '%Y%m%d'), wjd1.Job_Code_Sequence_Id)) FROM job_code_details_info wjd1 WHERE jcdi.`job_code_id` = wjd1.`job_code_id`  AND jcdi.transaction_date <= CURRENT_DATE() )) "
							+ " JOIN "
							+ " ( SELECT date_field FROM( SELECT MAKEDATE(YEAR(CASE WHEN  '"+DateHelper.convertDateToSQLString(workerVo.getDateOfJoining())+"' > CURRENT_DATE() THEN  '"+DateHelper.convertDateToSQLString(workerVo.getDateOfJoining())+"' ELSE CURRENT_DATE() END),1) + INTERVAL (MONTH(CASE WHEN  '"+DateHelper.convertDateToSQLString(workerVo.getDateOfJoining())+"' > CURRENT_DATE() THEN  '"+DateHelper.convertDateToSQLString(workerVo.getDateOfJoining())+"' ELSE CURRENT_DATE() END)-1) MONTH + INTERVAL daynum DAY date_field "
							+ " FROM ( SELECT t*10+u daynum FROM "
							+ "             (SELECT 0 t UNION SELECT 1 UNION SELECT 2 UNION SELECT 3) A, "
							+ "            (SELECT 0 u UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 "
							+ "             UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 "
							+ "             UNION SELECT 8 UNION SELECT 9) B " + "        ORDER BY daynum "
							+ "     ) AA ) AAA WHERE date_field BETWEEN ( CASE WHEN  '"+DateHelper.convertDateToSQLString(workerVo.getDateOfJoining())+"' > CURRENT_DATE() THEN  '"+DateHelper.convertDateToSQLString(workerVo.getDateOfJoining())+"' ELSE CURRENT_DATE() END)  AND LAST_DAY(CASE WHEN  '"+DateHelper.convertDateToSQLString(workerVo.getDateOfJoining())+"' > CURRENT_DATE() THEN  '"+DateHelper.convertDateToSQLString(workerVo.getDateOfJoining())+"' ELSE CURRENT_DATE() END)   "
							+ " ) AS Month_date WHERE  " + " shift_name IS NOT NULL AND weekly_off IS NOT NULL "
							+ " AND date_field >= wdi.date_of_joining AND CASE WHEN wdi.`Date_Of_Leaving` IS NOT NULL THEN  date_field <= +" +  "  wdi.`Date_Of_Leaving` ELSE -1='-1' END "
							+ " AND ( (select count(*) from worker_details wdd join worker_details_info wdii on (wdd.worker_id = wdii.worker_id)) =1 or  CONCAT(DATE_FORMAT(`wdi`.`Transaction_Date`, '%Y%m%d'), `wdi`.`Sequence_Id`) = (SELECT  "
							+ "                MAX(CONCAT(DATE_FORMAT(`wdi1`.`Transaction_Date`, '%Y%m%d'), `wdi1`.`Sequence_Id`)) "
							+ "             FROM " + "                `worker_details_info` `wdi1` "
							+ "             WHERE " + "                 ((`wdi`.`worker_id` = `wdi1`.`worker_id`) "
							+ "                     AND (`wdi`.`Transaction_Date` <= CURDATE())))) "
							+ " AND wd.worker_code = '"+workerVo.getWorkerCode()+"'").list();
			
			
			 boolean flag = true;
			for(Object a :labourScheduledList){
				Object[] c = (Object[])a;
				
				if(workerVo.getIsActive().equalsIgnoreCase("Y")){					
				    if(flag){
					 Query x = session.createSQLQuery("delete from labor_scheduled_shifts where business_date <   '"+DateHelper.convertDateToSQLString(workerVo.getDateOfJoining())+"'   and CODE = '"+c[1]+"' and emp_code = '"+c[2]+"'");
					 x.executeUpdate();
					 
					 VendorDetails vendorDetails = (VendorDetails) session.load(VendorDetails.class, workerVo.getVendorId());
					 
					 Query x2 = session.createSQLQuery("delete from labor_time_report where business_date < '"+DateHelper.convertDateToSQLString(workerVo.getDateOfJoining())+"'  and contractorcode = '"+vendorDetails.getVendorCode()+"' and emp = '"+workerVo.getWorkerCode()+"'");
					 x2.executeUpdate();
					 
					 Query x3 = session.createSQLQuery("delete from clms_prd_wil_comp.labor_time_report where business_date < '"+DateHelper.convertDateToSQLString(workerVo.getDateOfJoining())+"'  and contractorcode = '"+vendorDetails.getVendorCode()+"' and emp = '"+workerVo.getWorkerCode()+"'");
					 x3.executeUpdate();
					 
					 Query x4 = session.createSQLQuery("delete from clms_prd_wil_comp_b2.labor_time_report where business_date < '"+DateHelper.convertDateToSQLString(workerVo.getDateOfJoining())+"'  and contractorcode = '"+vendorDetails.getVendorCode()+"' and emp = '"+workerVo.getWorkerCode()+"'");
					 x4.executeUpdate();
					 
					 
					 flag = false;
				  }
				  Integer  plantId  =c[12] != null ? (Integer)c[12] : null;
				  Integer departmentId  =c[13] != null ? (Integer)c[13] : null;
				  Integer  sectionId  =c[14] != null ? (Integer)c[14] : null;
				  Integer  workAreaId  =c[15] != null ? (Integer)c[15] : null;
				  //String  WorkSkill = c[16] != null && c[16] != "" ? c[16]+"" : null;
				  //String designation  =c[17] != null && c[17] != "" ? c[17]+"" : null;
				    
				  
				String values = "'" + c[0] + "'" + ",'" + c[1] + "'" + ",'" + c[2] + "'" + ",'" + c[3] + "'" + ",'"
						+ c[4] + "','" + c[5] + "','" + c[6] + "','" + workerVo.getCreatedBy() + "','" + DateHelper.convertDateTimeToSQLString(new Date()) + "','" 
						+ workerVo.getModifiedBy() + "','" + DateHelper.convertDateTimeToSQLString(new Date()) + "','" + c[7]
								 + "','" + c[8] +  "','" + c[9] + "','" + c[10] + "','" + c[11] + "'," + plantId + "," + departmentId + "," + sectionId + "," + workAreaId + ",'" + c[16] + "','" + c[17] + "'";
				Query q = session.createSQLQuery(
						"replace into labor_scheduled_shifts (business_date,CODE,emp_code,emp_name,shift,wo,ContractorName,Created_By,Created_date,Modified_By,Modified_Date,customer_id,company_id,vendor_id,worker_id,location_id,plant_id,department_Id,section_Id,work_Area_id,Work_Skill,Designation) values ("
								+ values + ")");				
				q.executeUpdate();
				
				
				}
					
					
			}
			
			 if(workerVo.getIsActive().equalsIgnoreCase("N")){			
					
				VendorDetails vendorDetails = (VendorDetails) session.load(VendorDetails.class, workerVo.getVendorId());
					
					 Query x1 = session.createSQLQuery("delete from labor_scheduled_shifts where business_date > '"+DateHelper.convertDateToSQLString(workerVo.getDateOfLeaving())+"'  and CODE = '"+vendorDetails.getVendorCode()+"' and emp_code = '"+workerVo.getWorkerCode()+"'");
					 x1.executeUpdate();
					 
					 Query x2 = session.createSQLQuery("delete from labor_time_report where business_date > '"+DateHelper.convertDateToSQLString(workerVo.getDateOfLeaving())+"'  and contractorcode = '"+vendorDetails.getVendorCode()+"' and emp = '"+workerVo.getWorkerCode()+"'");
					 x2.executeUpdate();
					 
					 Query x3 = session.createSQLQuery("delete from clms_prd_wil_comp.labor_time_report where business_date > '"+DateHelper.convertDateToSQLString(workerVo.getDateOfLeaving())+"'  and contractorcode = '"+vendorDetails.getVendorCode()+"' and emp = '"+workerVo.getWorkerCode()+"'");
					 x3.executeUpdate();
					 
					 Query x4 = session.createSQLQuery("delete from clms_prd_wil_comp_b2.labor_time_report where business_date > '"+DateHelper.convertDateToSQLString(workerVo.getDateOfLeaving())+"'  and contractorcode = '"+vendorDetails.getVendorCode()+"' and emp = '"+workerVo.getWorkerCode()+"'");
					 x4.executeUpdate();
				}
			
			
		} catch (Exception e) {
			workerInfoId = 0;
			log.error("Error Occured ",e);
		}
		
		return workerInfoId;
	}
	
	
	@Override
	public List<WorkerDetailsVo> getWorkerDetailsbyId(String workerInfoId){
		
		List<WorkerDetailsVo> workerList = new ArrayList<WorkerDetailsVo>();
		Session session = sessionFactory.getCurrentSession();
		try{
			
			
			String q =  " SELECT  DISTINCT wd.`Worker_code`,wd.`Customer_id`,wd.`Company_id`,wd.`Country_id`,wd.`vendor_id` ,wd.`worker_id` ,	wdi.Worker_info_id,	wdi.Transaction_Date, wdi.Sequence_id,wdi.Is_Active, wdi.First_name,wdi.Middle_name,wdi.Last_name,wdi.Father_Spouse_name,wdi.PAN_number, "
							+" wdi.Date_of_birth,wdi.Gender,wdi.Marital_Status,wdi.Blood_group, wdi.Religion, wdi.Phone_number, wdi.Email, wdi.Emergency_contact_person, wdi.Emergency_contact_number, wdi.Created_Date,wdi.Modified_Date, wdi.Father_Or_Spouse,  "
							+" ci.customer_name,cdi.company_name,vdi.vendor_name,mc.`Country_Name`, wdi.Age, wdi.Date_Of_Leaving, "
							+ "  wdi.Date_Of_Joining,wdi.shift_Name,wdi.weekly_Off, wdi.Image_Path, wdi.Image_Name, wdi.Reason_For_Status_Change, wdi.Acount_Holder_Name,wdi.Acount_Number,"
							+ " wdi.Bank_Name,wdi.Branch_Name,wdi.Ifsc_Code,wdi.place_of_birth,wdi.mother_name,wdi.language,wdi.education,wdi.nationality,wdi.domicile "
							+" FROM worker_details wd   "
							+" INNER JOIN worker_details_info wdi ON (wd.`Worker_id` = wdi.`Worker_id`) "
							+" INNER JOIN customer_details_info ci ON (ci.customer_id = wd.customer_id)  "
							+" INNER JOIN company_details_info cdi ON(cdi.company_id = wd.company_id) "
							+" INNER JOIN `vendor_details_info` vdi ON(vdi.vendor_id = wd.vendor_id) "
							+" INNER JOIN m_country mc ON(mc.`Country_Id` = wd.country_id) "
							+" WHERE 1= 1 "
							/*+ " CONCAT(DATE_FORMAT(wdi.transaction_date, '%Y%m%d'), LPAD(wdi.Sequence_Id, 2, '0'))  =		 "	 			 
							+" 	(  "
							+" SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(wdi.Sequence_Id, 2, '0')))   "
							+" FROM worker_details_info vdi1  "
							+" WHERE wdi.`worker_id` = vdi1.`worker_id` AND vdi1.transaction_date <= CURRENT_DATE()  "
							+" ) "*/
							
						+" AND  CONCAT(DATE_FORMAT(ci.transaction_date, '%Y%m%d'), LPAD(ci.`Customer_Sequence_Id`, 2, '0'))  =		 "	 			 
						+" (  "
						+" SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.`Customer_Sequence_Id`, 2, '0')))   "
						+" FROM customer_details_info vdi1  "
						+" WHERE ci.`customer_id` = vdi1.`customer_id` AND vdi1.transaction_date <= CURRENT_DATE()  "
						+" )  "
												
						+" AND CONCAT(DATE_FORMAT(cdi.transaction_date, '%Y%m%d'), LPAD(cdi.`Company_Sequence_Id`, 2, '0'))  =		 "	 			 
						+" (  " 
						+" SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.`Company_Sequence_Id`, 2, '0')))   "
						+" FROM company_details_info vdi1  "
						+" WHERE cdi.`company_id` = vdi1.`company_id` AND vdi1.transaction_date <= CURRENT_DATE()  "
						+" 	)  "
												
						+" AND CONCAT(DATE_FORMAT(vdi.transaction_date, '%Y%m%d'), LPAD(vdi.`Sequence_Id`, 2, '0'))  =			  "			 
						+" 	(  "
						+" SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.`Sequence_Id`, 2, '0')))   "
						+" FROM vendor_details_info vdi1  "
						+" WHERE vdi.`vendor_id` = vdi1.`vendor_id` AND vdi1.transaction_date <= CURRENT_DATE()  "
						+" ) AND wdi.`Worker_info_id` =" +workerInfoId;
		 					
							//System.out.println("query"+q);					 
		
				List tempList = session.createSQLQuery(q).list();
				for(Object o:tempList){
					Object[] obj = (Object[])o;
					WorkerDetailsVo workerDetailsVo = new WorkerDetailsVo();
					workerDetailsVo.setWorkerCode(obj[0]+"");
					workerDetailsVo.setCustomerId((Integer)obj[1]);
					workerDetailsVo.setCompanyId((Integer)obj[2]);
					workerDetailsVo.setCountryId((Integer)obj[3]);
					workerDetailsVo.setVendorId((Integer)obj[4]);
					workerDetailsVo.setWorkerId((Integer)obj[5]);					
					workerDetailsVo.setWorkerInfoId((Integer)obj[6]);
					workerDetailsVo.setTransactionDate((Date)obj[7]);
					workerDetailsVo.setSequenceId((Integer)obj[8]);
					workerDetailsVo.setIsActive(obj[9]+"");
					workerDetailsVo.setFirstName(obj[10]+"");
					if(obj[11] != null){
					workerDetailsVo.setMiddleName(obj[11]+"");
					}
					workerDetailsVo.setLastName(obj[12]+"");
					if(obj[13] != null){
					workerDetailsVo.setFatherSpouseName(obj[13]+"");
					}
					if(obj[14] != null){
					workerDetailsVo.setPanNumber(obj[14]+"");
					}
					workerDetailsVo.setDateOfBirth((Date)obj[15]);
					if(obj[16] != null){
					workerDetailsVo.setGender((Character)obj[16]);
					}
					if(obj[17] != null){
					workerDetailsVo.setMaritalStatus(obj[17]+"");
					}
					if(obj[18] != null){
					workerDetailsVo.setBloodGroup(obj[18]+"");
					}
					if(obj[19] != null){
					workerDetailsVo.setReligion(obj[19]+"");
					}
					if(obj[20] != null){
					workerDetailsVo.setPhoneNumber(obj[20]+"");
					}
					if(obj[21] != null){
					workerDetailsVo.setEmail(obj[21]+"");
					}
					if(obj[22] != null){
					workerDetailsVo.setEmergencyContactPerson(obj[22]+"");
					}
					if(obj[23] != null){
					workerDetailsVo.setEmergencyContactNumber(obj[23]+"");
					}
					if(obj[24] != null){
					workerDetailsVo.setCreatedDate((Date)obj[24]);
					}
					if(obj[25] != null){
					workerDetailsVo.setModifiedDate((Date)obj[25]);
					}
					if(obj[26] != null){  
					workerDetailsVo.setFatherOrSpouse((String)obj[26]);
					}
					
					workerDetailsVo.setCustomerName(obj[27]+"");
					workerDetailsVo.setCompanyName(obj[28]+"");
					workerDetailsVo.setVendorName(obj[29]+"");
					workerDetailsVo.setCountryName(obj[30]+"");
					workerDetailsVo.setAge((String)obj[31]);
					workerDetailsVo.setDateOfLeaving(obj[32] != null ? (Date)obj[32] : null);
					workerDetailsVo.setDateOfJoining(obj[33] != null ? (Date)obj[33] : null);
					if(obj[34] != null){
					workerDetailsVo.setShiftName(obj[34]+"");
					}if(obj[35] != null){
					workerDetailsVo.setWeeklyOff(obj[35]+"");
					}
					if(obj[36] != null){
						workerDetailsVo.setImagePath((String)obj[36]);
					}
					if(obj[37] != null){
						workerDetailsVo.setImageName((String)obj[37]);
					}
					if(obj[38] != null){
						workerDetailsVo.setReasonForStatus((String)obj[38]);
					}
					if(obj[39] != null){
						workerDetailsVo.setAcountHolderName((String)obj[39]);
					}
					if(obj[40] != null){
						workerDetailsVo.setAcountNumber((String)obj[40]);
					}
					if(obj[41] != null){
						workerDetailsVo.setBankName((String)obj[41]);
					}
					if(obj[42] != null){
						workerDetailsVo.setBranchName((String)obj[42]);
					}
					if(obj[43] != null){
						workerDetailsVo.setIfscCode((String)obj[43]);
					}
					if(obj[44] != null){
						workerDetailsVo.setPlaceOfBirth((String)obj[44]);
					}
					if(obj[45] != null){
						workerDetailsVo.setMotherName((String)obj[45]);
					}
					if(obj[46] != null){
						workerDetailsVo.setLanguage((String)obj[46]);
					}
					if(obj[47] != null){
						workerDetailsVo.setEducation((String)obj[47]);
					}
					if(obj[48] != null){
						workerDetailsVo.setNationality((String)obj[48]);
					}
					if(obj[49] != null){
						workerDetailsVo.setDomicile((String)obj[49]);
					}
					
					if((String)obj[36] != null){
						try{
							java.io.File file = new java.io.File((String)obj[36]);
							byte[] b = new byte[(int)file.length()];
							b = FileUtils.readFileToByteArray(file);
							System.out.println(b);
							workerDetailsVo.setFile(b);
						}catch(Exception e){
							log.error("Error Occured ",e);
						}
					}
					
					String q1 = "  SELECT Address_Type,Address_line1, Address_line2, Address_line3, Country_id, State_id, City, Pin_code,"
							  + "  Is_Same_Address FROM worker_details_info wdi  "
							  +"  LEFT JOIN worker_address wa ON(wdi.worker_id = wa.worker_id AND wdi.sequence_id = wa.sequence_id "
							  +" AND wdi.transaction_date = wa.transaction_date) "
							  +" WHERE  1 = 1 "
							 /* + " AND CONCAT(DATE_FORMAT(wa.transaction_date, '%Y%m%d'), LPAD(wa.Sequence_Id,2,'0')) =  "
							  +" (  "
							  +" SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.Sequence_Id,2,'0'))) "
							  +" FROM worker_address vdi1 "
							  +" WHERE wa.`Worker_Address_id` = vdi1.`Worker_Address_id`  AND wa.`Address_Type` = vdi1.`Address_Type` "
							  +"  AND vdi1.transaction_date <= CURRENT_DATE()   "
							  +" ) "*/
							  + " AND wdi.`Worker_info_id` = "+workerInfoId;
				 					
						System.out.println("query"+q1);					 
		
						List addressList = session.createSQLQuery(q1).list();
						for (Object o1 : addressList) {
							Object[] obj1 = (Object[]) o1;
							if (obj1[0] != null && ((String) obj1[0]).equalsIgnoreCase("Permanent")) {
								workerDetailsVo.setPermanentAddressLine1((String)obj1[1]);
								workerDetailsVo.setPermanentAddressLine2((String)obj1[2]);
								workerDetailsVo.setPermanentAddressLine3((String)obj1[3]);
								workerDetailsVo.setPermanentCountryId((Integer) obj1[4]);
								workerDetailsVo.setPermanentStateId((Integer) obj1[5]);
								workerDetailsVo.setPermanentCity((String)obj1[6]);
								workerDetailsVo.setPermanentPinCode((Integer) obj1[7]);
								if(obj1[8] != null){
									System.out.println(obj1[8]+"");
									workerDetailsVo.setIsSameAddress((obj1[8]+"").equalsIgnoreCase("Yes") ? true : false);
								}

							} else if (obj1[0] != null) {
								
								workerDetailsVo.setPresentAddressLine1((String)obj1[1]);
								workerDetailsVo.setPresentAddressLine2((String)obj1[2]);
								workerDetailsVo.setPresentAddressLine3((String)obj1[3]);
								workerDetailsVo.setPresentcountryId((Integer) obj1[4]);
								workerDetailsVo.setPresentStateId((Integer) obj1[5]);
								workerDetailsVo.setPresentCity((String)obj1[6]);
								workerDetailsVo.setPresentPinCode((Integer) obj1[7]);
								if(obj1[8] != null){
									System.out.println(obj1[8]+"");
									workerDetailsVo.setIsSameAddress((obj1[8]+"").equalsIgnoreCase("Yes") ? true : false);
								}
							}
		
						}
								
						String idQuery = "  SELECT wi.Country_Id, m.Country_Name, wi.Identification_Type, wi.File_Path,wi.Identification_Number FROM worker_details_info wdi  "
								  +  "  LEFT JOIN worker_identification_proof wi ON(wdi.worker_id = wi.worker_id AND wdi.sequence_id = wi.sequence_id "
								  							+ " AND wdi.transaction_date = wi.transaction_date) "
								  + " LEFT JOIN m_country m ON wi.Country_Id = m.Country_Id "
								  + " WHERE  1 = 1 "
								 /* + " AND CONCAT(DATE_FORMAT(wi.transaction_date, '%Y%m%d'), LPAD(wi.Sequence_Id,2,'0')) =  "
								  +	" (  "
								  +	" SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.Sequence_Id,2,'0'))) "
								  +	" FROM worker_identification_proof vdi1 "
								  +	" WHERE wi.`worker_identification_id` = vdi1.`worker_identification_id`  "
								  +	" AND wi.`Identification_Type` = vdi1.`Identification_Type` "
								  +	"  AND vdi1.transaction_date <= CURRENT_DATE()   "
								  +	" ) "*/
								  + " AND wi.`Worker_id` = "+workerDetailsVo.getWorkerId()
								  + " AND wi.`Transaction_Date` = '"+workerDetailsVo.getTransactionDate()+"' "
								  + " AND wi.`Sequence_id` = "+workerDetailsVo.getSequenceId();
					 					
					    System.out.println("query: "+idQuery);	
					    
					    List identityList = session.createSQLQuery(idQuery).list();
					    
					    WorkerIdentificationProofVo identity = new WorkerIdentificationProofVo();
					    List<WorkerIdentificationProofVo> idList = new ArrayList<WorkerIdentificationProofVo>();
					    log.info("Size of identity List  "+identityList.size());
					    
					    if(identityList != null && identityList.size() > 0){
							for (Object o1 : identityList) {
								Object[] obj1 = (Object[]) o1;
								identity = new WorkerIdentificationProofVo();
								identity.setCountryId(obj1[0] != null ? (Integer)obj1[0] : 0);
								identity.setCountryName(obj[1] != null ? (String)obj1[1] : null);
								identity.setIdentificationType(obj[2] != null ? (String)obj1[2] : null);
								identity.setFilePath(obj1[3] != null ? (String)obj1[3] : null);
								identity.setIdentificationNumber(obj1[4] != null ? (String)obj1[4] : null);
								
								String[] count = {} ;
								
								if((String)obj1[3] != null){
									count =obj1[3] != null ? (String[])(obj1[3]+"").split(Pattern.quote(System.getProperty("file.separator"))) : null;
									java.io.File file = new java.io.File((String)obj1[3]);
									identity.setFileData(file);
									byte[] b = new byte[(int)file.length()];
									String[] extension = count[count.length-1].split("\\.(?=[^\\.]+$)");
									identity.setFileName(count.length > 0 ? count[count.length-1].substring(0, extension[0].length()-20).concat(".").concat(extension[1]) : null);
									
									try{
										b = FileUtils.readFileToByteArray(file);
									}catch(Exception e){
										log.error("Error Occured ",e);
									}
								}
								idList.add(identity);
							 }
					    }
						workerDetailsVo.setIdentityList(idList);		
						workerList.add(workerDetailsVo);
						
				}
			
		
		}catch(Exception e){
			log.error("Error Occured ",e);
			
		}
		
		return workerList;
	}
	
	
	@Override
	public List<SimpleObject> getTransactionDatesListForEditingWorkerDetails(Integer workerId) {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> transactionList = new ArrayList<>();
		try {
			List workerTempList = session.createSQLQuery("select worker_Info_Id,concat(DATE_FORMAT(Transaction_Date,'%d/%m/%Y'),'-',Sequence_Id) from worker_details_info  where worker_id = '"+workerId+"'   order by Transaction_Date ,Sequence_Id " ).list();
			for (Object customerObject  : workerTempList) {
				Object[] obj = (Object[]) customerObject;
				SimpleObject object = new SimpleObject();
				object.setId((Integer)obj[0]);
				object.setName(obj[1]+"");
				transactionList.add(object);
			}			
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}

		return transactionList;

	}


	@Override
	public Integer saveNomineeDetails(WorkerNomineeDetailsVo workerNomineeVo) {
		Session session = sessionFactory.getCurrentSession();
		WorkerNomineeDetails nomineeDetails = new WorkerNomineeDetails();
		Integer workerNomineeId = 0;
		try{
			if(workerNomineeVo != null){
				
				nomineeDetails.setWorkerDetails(new WorkerDetails(workerNomineeVo.getWorkerId()));
				nomineeDetails.setCustomerId(workerNomineeVo.getCustomerId());
				nomineeDetails.setCompanyId(workerNomineeVo.getCompanyId());
				nomineeDetails.setMemberName(workerNomineeVo.getMemberName());
				nomineeDetails.setRelationship(workerNomineeVo.getRelationship());
				nomineeDetails.setDateOfBirth(workerNomineeVo.getDateOfBirth());
				nomineeDetails.setPhoneNumber(workerNomineeVo.getPhoneNumber());
				nomineeDetails.setPercentageShareInPF(workerNomineeVo.getPercentageShareInPF());
				nomineeDetails.setGender(workerNomineeVo.getGender());
				nomineeDetails.setAddress(workerNomineeVo.getAddress());
				nomineeDetails.setGuardianDetails(workerNomineeVo.getGuardianDetails());
				nomineeDetails.setModifiedBy(workerNomineeVo.getModifiedBy());
				if(workerNomineeVo.getAgeYears() != null)
				nomineeDetails.setAgeYears(workerNomineeVo.getAgeYears());
				if(workerNomineeVo.getAgeMonths() != null)
				nomineeDetails.setAgeMonths(workerNomineeVo.getAgeMonths());
				nomineeDetails.setModifiedDate(new Date());
				
				if(workerNomineeVo.getSelectAsNominee() != null && (workerNomineeVo.getSelectAsNominee().equalsIgnoreCase("true") || workerNomineeVo.getSelectAsNominee().equalsIgnoreCase("Y"))){
				nomineeDetails.setSelectAsNominee("Y");
				}else{					
					nomineeDetails.setSelectAsNominee("N");
				}
				if(workerNomineeVo.getIsMinor() != null && (workerNomineeVo.getIsMinor().equalsIgnoreCase("true") || workerNomineeVo.getIsMinor().equalsIgnoreCase("Y")) ){
					nomineeDetails.setIsMinor("Y");
				}else{
					nomineeDetails.setIsMinor("N");
					
				}
				
				if(workerNomineeVo.getWorkerNomineeId() != null && workerNomineeVo.getWorkerNomineeId() >0){
					nomineeDetails.setWorkerNomineeId(workerNomineeVo.getWorkerNomineeId());
					nomineeDetails.setCreatedBy(workerNomineeVo.getCreatedBy());
					nomineeDetails.setCreatedDate(workerNomineeVo.getCreatedDate());
					session.update(nomineeDetails);	
					workerNomineeId = workerNomineeVo.getWorkerNomineeId();
				}else{
					nomineeDetails.setCreatedBy(workerNomineeVo.getCreatedBy());
					nomineeDetails.setCreatedDate(new Date());					
					workerNomineeId = (Integer) session.save(nomineeDetails);
				}
				
								
			}
			
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return workerNomineeId;
	}
	
	
	
	public Integer deleteNomineeDetails(Integer workerNomineeId) {
		Session session = sessionFactory.getCurrentSession();
		Integer flag = 0; 
		
		try {		
			if(workerNomineeId != null && workerNomineeId >0){						
				Query q = session.createSQLQuery("delete from worker_nominee_details where Worker_nominee_id = '"+workerNomineeId+"'");
				q.executeUpdate();
			}
		} catch (Exception e) {
			log.error("Error Occured ",e);
			flag = 1;
		}
		
		return flag;
	}
	
	
	
	
	
	public List<WorkerNomineeDetailsVo> getNomineeGridData(Integer customerId,Integer companyId,Integer workerId) {

		List<WorkerNomineeDetailsVo> nomineeList = new ArrayList<WorkerNomineeDetailsVo>();
		Session session = sessionFactory.getCurrentSession();
		try {

			
			List tempList = session.createSQLQuery("SELECT Worker_nominee_id,Worker_id,customer_id,company_id,Member_name,Relationship,Date_of_birth,Gender,Address,Phone_number,Select_As_Nominee,IsMinor,Created_By,Created_Date,Modified_By,	Modified_Date,Guardian_Details,Percentage_Share_In_PF,ageMonths,ageYears FROM  worker_nominee_details WHERE worker_id = '"+workerId+"' AND customer_id ='"+customerId+"' AND company_id = '"+companyId+"' ORDER BY Member_name asc").list();
			for (Object o : tempList) {
				Object[] obj = (Object[]) o;
				WorkerNomineeDetailsVo workerDetailsVo = new WorkerNomineeDetailsVo();
				workerDetailsVo.setWorkerNomineeId((Integer)obj[0]);
				workerDetailsVo.setWorkerId((Integer)obj[1]);
				workerDetailsVo.setCustomerId((Integer)obj[2]);
				workerDetailsVo.setCompanyId((Integer)obj[3]);
				workerDetailsVo.setMemberName((String)obj[4]);
				workerDetailsVo.setRelationship((String)obj[5]);
				workerDetailsVo.setDateOfBirth((Date)obj[6]);
				workerDetailsVo.setGender((Character)obj[7]);
				workerDetailsVo.setAddress((String)obj[8]);
				workerDetailsVo.setPhoneNumber((String)obj[9]);
				workerDetailsVo.setSelectAsNominee(obj[10]+"");
				workerDetailsVo.setIsMinor(obj[11]+"");
				workerDetailsVo.setCreatedBy((Integer)obj[12]);
				workerDetailsVo.setCreatedDate((Date)obj[13]);
				workerDetailsVo.setModifiedBy((Integer)obj[14]);
				workerDetailsVo.setModifiedDate((Date)obj[15]);	
				workerDetailsVo.setGuardianDetails((String)obj[16]);
				workerDetailsVo.setPercentageShareInPF((Integer)obj[17]);
				workerDetailsVo.setAgeMonths((Integer)obj[18]);
				workerDetailsVo.setAgeYears((Integer)obj[19]);
				nomineeList.add(workerDetailsVo);
			}

		} catch (Exception e) {
			log.error("Error Occured ",e);

		}

		return nomineeList;
	}


	@Override
	public Integer saveEducationAndEmploymentDetails(WorkerEducationAndEmploymentVo educationAndEmployment) {
		Session session = sessionFactory.getCurrentSession();
		System.out.println(educationAndEmployment.getEducationalList().size());
		Integer flag= 0;
		try{

			/***** Save Education *****/
				
			if(educationAndEmployment.getEducationalList() != null && educationAndEmployment.getEducationalList().size() > 0){
				Query q = session.createSQLQuery("DELETE FROM worker_education_details WHERE Worker_id = '"+educationAndEmployment.getWorkerId()+"'");
				q.executeUpdate();
			
				for(WorkerEducationDetailsVo eduVo :educationAndEmployment.getEducationalList()){
					WorkerEducationDetails education = new WorkerEducationDetails();
					
					education.setCustomerDetails(new CustomerDetails(educationAndEmployment.getCustomerId()));
					education.setCompanyDetails(new CompanyDetails(educationAndEmployment.getCompanyId()));
					education.setVendorDetails(new VendorDetails(educationAndEmployment.getVendorId()));
					education.setWorkerDetails(new WorkerDetails(educationAndEmployment.getWorkerId()));
				//	education.setSkillType("Skilled");
					education.setFilepath(education.getFilepath());
					education.setEducationalLevel(eduVo.getEducationLevel());
					education.setDegreeName(eduVo.getDegreeName());
					education.setModeOfEducation(eduVo.getModeOfEducation());
					education.setInstitutionName(eduVo.getInstitutionName());
					education.setYearOfPassing(eduVo.getYearOfPassing());
					education.setCreatedBy(educationAndEmployment.getCreatedBy());
					education.setCreatedDate(new Date());
					education.setModifiedBy(educationAndEmployment.getModifiedBy());
					education.setModifiedDate(new Date());
					
					session.save(education);
				}
			}
			
			 /***** Save Certification Start *****/ 
			
			if(educationAndEmployment.getCertificationList() != null && educationAndEmployment.getCertificationList().size() > 0){

				Query q1 = session.createSQLQuery("DELETE FROM worker_certification_details WHERE Worker_id = '"+educationAndEmployment.getWorkerId()+"'");
				q1.executeUpdate();
			
				for(WorkerCertificationDetailsVo workerCertVo :educationAndEmployment.getCertificationList()){
					WorkerCertificationDetails certification = new WorkerCertificationDetails();
					
					certification.setCustomerDetails(new CustomerDetails(educationAndEmployment.getCustomerId()));
					certification.setCompanyDetails(new CompanyDetails(educationAndEmployment.getCompanyId()));
					certification.setVendorDetails(new VendorDetails(educationAndEmployment.getVendorId()));
					certification.setWorkerDetails(new WorkerDetails(educationAndEmployment.getWorkerId()));
					certification.setCertificateName(workerCertVo.getCertificateName());
					certification.setIssuingAuthority(workerCertVo.getIssuingAuthority());
					certification.setValidity(workerCertVo.getValidity());
					certification.setValidFrom(workerCertVo.getValidFrom());
					certification.setValidTo(workerCertVo.getValidTo());
					certification.setFilePath(workerCertVo.getFilePath());
					certification.setCreatedBy(educationAndEmployment.getCreatedBy());
					certification.setCreatedDate(new Date());
					certification.setModifiedBy(educationAndEmployment.getModifiedBy());
					certification.setModifiedDate(new Date());
					
					session.save(certification);
				}
			
			}
			
			 /***** Save Employment Start *****/
			
			if(educationAndEmployment.getEmploymentList() != null && educationAndEmployment.getEmploymentList().size() > 0){
				Query q2 = session.createSQLQuery("DELETE FROM worker_employment_details WHERE Worker_id = '"+educationAndEmployment.getWorkerId()+"'");
				q2.executeUpdate();
			
				for(WorkerEmploymentDetailsVo workeremployVo :educationAndEmployment.getEmploymentList()){
					WorkerEmploymentDetails employment = new WorkerEmploymentDetails();
					
					employment.setCustomerDetails(new CustomerDetails(educationAndEmployment.getCustomerId()));
					employment.setCompanyDetails(new CompanyDetails(educationAndEmployment.getCompanyId()));
					employment.setVendorId(new VendorDetails(educationAndEmployment.getVendorId()));
					employment.setWorkerId(new WorkerDetails(educationAndEmployment.getWorkerId()));
					employment.setOrganizationName(workeremployVo.getOrganizationName());
					employment.setContactNumber(workeremployVo.getContactNumber());
					employment.setCurrent(workeremployVo.getCurrent() == true ? "Y" : "N");
					employment.setDesignation(workeremployVo.getDesignation());
					employment.setFilePath(workeremployVo.getFilePath());
					employment.setCreatedBy(educationAndEmployment.getCreatedBy());
					employment.setCreatedDate(new Date());
					employment.setModifiedBy(educationAndEmployment.getModifiedBy());
					employment.setModifiedDate(new Date());
					
					session.save(employment);
				}
			}
			
			/***** Save Reference Start *****/
			
			if(educationAndEmployment.getReferenceList() != null && educationAndEmployment.getReferenceList().size() > 0){
				Query q2 = session.createSQLQuery("DELETE FROM worker_reference WHERE Worker_id = '"+educationAndEmployment.getWorkerId()+"'");
				q2.executeUpdate();
			
				for(WorkerReferenceVo workeremployVo :educationAndEmployment.getReferenceList()){
					WorkerReference reference = new WorkerReference();
					
					reference.setCustomerId(new CustomerDetails(educationAndEmployment.getCustomerId()));
					reference.setCompanyId(new CompanyDetails(educationAndEmployment.getCompanyId()));
					reference.setVendorId(new VendorDetails(educationAndEmployment.getVendorId()));
					reference.setWorkerId(new WorkerDetails(educationAndEmployment.getWorkerId()));
					reference.setName(workeremployVo.getName());
					reference.setContactNumber(workeremployVo.getContactNumber());
					reference.setDesignation(workeremployVo.getDesignation());
					reference.setEmail(workeremployVo.getEmailId());
					reference.setCreatedBy(educationAndEmployment.getCreatedBy());
					reference.setCreatedDate(new Date());
					reference.setModifiedBy(educationAndEmployment.getModifiedBy());
					reference.setModifiedDate(new Date());
					
					session.save(reference);
				}
			}
		}catch(Exception e){
			flag= 1;
			log.error("Error Occured ",e);
			
		}
		return flag;
	}
	
	@Override
	public WorkerEducationAndEmploymentVo getEducationAndEmploymentList(Integer customerId,Integer companyId,Integer workerId){
		Session session = sessionFactory.getCurrentSession();
		
		WorkerEducationDetailsVo educationVo = new WorkerEducationDetailsVo();
		WorkerCertificationDetailsVo certificateVo = new WorkerCertificationDetailsVo();
		WorkerEmploymentDetailsVo employmentVo = new WorkerEmploymentDetailsVo();
		WorkerReferenceVo referenceVo = new WorkerReferenceVo();
		
		List<WorkerEducationDetailsVo> educationList = new ArrayList<WorkerEducationDetailsVo>();
		List<WorkerCertificationDetailsVo> certificateList = new ArrayList<WorkerCertificationDetailsVo>();
		List<WorkerEmploymentDetailsVo> employmentList = new ArrayList<WorkerEmploymentDetailsVo>();
		List<WorkerReferenceVo> referenceList = new ArrayList<WorkerReferenceVo>();
		
		WorkerEducationAndEmploymentVo returnList = new WorkerEducationAndEmploymentVo();
		
		
		String q = "SELECT Educational_Level, Degree_Name, Institution_Name, Mode_Of_Education, Year_Of_Passing, "
					+ " Percentage_Or_Grade, File_Path FROM worker_education_details "
					+ " WHERE Worker_Id = "+workerId;
		try{
		List queryList = session.createSQLQuery(q).list();
		for (Object o1 : queryList) {
			Object[] obj1 = (Object[]) o1;
			educationVo.setEducationLevel((String)obj1[0]);
			educationVo.setDegreeName((String)obj1[1]);
			educationVo.setInstitutionName((String)obj1[2]);
			educationVo.setModeOfEducation((String) obj1[3]);
			educationVo.setYearOfPassing((Integer) obj1[4]);
			educationVo.setPercentageOrGrade((String)obj1[5]);
			educationVo.setFilePath((String) obj1[6]);
			String[] count = obj1[6] != null ? (String[])(obj1[6]+"").split(Pattern.quote(System.getProperty("file.separator"))) : null;
			educationVo.setFileName(count.length > 0 ? count[count.length-1] : null);
			
			educationList.add(educationVo);
		}
		
		String q1 = " SELECT Certificate_Name, Issuing_Authority, Validity, Valid_From, Valid_To,File_Path "
					+ " FROM worker_certification_details "
					+ " WHERE Worker_Id = "+workerId;
		
		List queryList1 = session.createSQLQuery(q1).list();
		for (Object o1 : queryList1) {
			Object[] obj1 = (Object[]) o1;
			certificateVo.setCertificateName((String)obj1[0]);
			certificateVo.setIssuingAuthority((String)obj1[1]);
			certificateVo.setValidity((String)obj1[2]);
			certificateVo.setValidFrom((Date) obj1[3]);
			certificateVo.setValidTo((Date) obj1[4]);
			certificateVo.setFilePath((String) obj1[5]);
			String[] count = obj1[5] != null ? (String[])(obj1[5]+"").split(Pattern.quote(System.getProperty("file.separator"))) : null;
			certificateVo.setFileName(count.length > 0 ? count[count.length-1] : null);
				
			certificateList.add(certificateVo);
		}
		
		String q2 = " SELECT Organization_Name, Designation, Contact_Number, Start_Date, End_Date, Current, Total_Days,"
					+ " File_Path FROM worker_employment_details "
					+ " WHERE Worker_Id = "+workerId;
		
		List queryList2 = session.createSQLQuery(q2).list();
		for (Object o1 : queryList2) {
			Object[] obj1 = (Object[]) o1;
			employmentVo.setOrganizationName((String)obj1[0]);
			employmentVo.setDesignation((String)obj1[1]);
			employmentVo.setContactNumber((String)obj1[2]);
			employmentVo.setStartDate((Date) obj1[3]);
			employmentVo.setEndDate((Date) obj1[4]);
			employmentVo.setTotalYears((Integer) obj1[5]);
			employmentVo.setFilePath((String) obj1[6]);
			String[] count = obj1[6] != null ? (String[])(obj1[6]+"").split(Pattern.quote(System.getProperty("file.separator"))) : null;
			employmentVo.setFileName(count.length > 0 ? count[count.length-1] : null);
				
			employmentList.add(employmentVo);
		}
		
		String q3 = " SELECT Name, Designation, Contact_Number, Email, Relationship "
					+ " FROM worker_reference "
					+ " WHERE Worker_Id = "+workerId;
		
		List queryList3 = session.createSQLQuery(q3).list();
		for (Object o1 : queryList3) {
			Object[] obj1 = (Object[]) o1;
			referenceVo.setName((String)obj1[0]);
			referenceVo.setDesignation((String)obj1[1]);
			referenceVo.setContactNumber((String)obj1[2]);
			referenceVo.setEmailId((String) obj1[3]);
			referenceVo.setRelationship((String) obj1[4]);
			
			referenceList.add(referenceVo);
		}
		
		returnList.setCertificationList(certificateList);
		returnList.setEducationalList(educationList);
		returnList.setEmploymentList(employmentList);
		returnList.setReferenceList(referenceList);
		//Query q = 
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return returnList;
		
	}
	
	
	public SimpleObject validateWorkerCode(String workerCode,Integer customerID,Integer companyId,Integer vendorId,Integer workerId){
		Session session = sessionFactory.getCurrentSession();
		List<WorkerDetails> q = null;
		//String message = null;
		SimpleObject object = new SimpleObject();
		try{
			if(workerCode != null && !workerCode.isEmpty() && workerId > 0 ){
				q =  (List<WorkerDetails>) session.createQuery(" from WorkerDetails where workerCode='"+workerCode.trim()+"' and workerId NOT IN ('"+workerId+"')").list();
			}else if(workerCode != null && !workerCode.isEmpty() && workerId == 0 ){
				q =  (List<WorkerDetails>) session.createQuery(" from WorkerDetails where workerCode='"+workerCode.trim()+"'").list();
			}
			
			if (q.size() >0) {				
				WorkerDetails  w= q.get(0);
				object.setId(w.getWorkerId());
				object.setName("Customer ID already existed");		
			}else{
				object.setId(0);
				object.setName("Success");
			}		
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
			//simpleObjectList.add(object);
		return object;
	}
	
	
	public SimpleObject validateDuplicateWorker(WorkerDetailsVo detailsVo){
		Session session = sessionFactory.getCurrentSession();
		List<WorkerDetailsInfo> q = null;
		//String message = null;
		SimpleObject object = new SimpleObject();
		try{				
			
			String query =  " SELECT worker_id,is_Active,reason_for_status_change FROM worker_details_info wdi WHERE "+
							" CONCAT(DATE_FORMAT(wdi.Transaction_Date,'%Y%m%d'),LPAD(wdi.Sequence_id,2,'0')) = (SELECT "+
							" MAX(CONCAT(DATE_FORMAT(vdi1.Transaction_Date,'%Y%m%d'),LPAD(vdi1.Sequence_id,2,'0'))) "+
							" FROM worker_details_info vdi1 "+
							" WHERE ((wdi.Worker_id = vdi1.Worker_id) "+
							" AND (vdi1.Transaction_Date <= CURDATE()))) and first_Name='"+detailsVo.getFirstName().trim()+"' and last_Name  = '"+detailsVo.getLastName().trim()+"' and date_Of_Birth = '"+DateHelper.convertDateToSQLString(detailsVo.getDateOfBirth())+"'";
			
			
			List queryList = session.createSQLQuery(query).list();
			if(queryList.size() >0) {
				for (Object o1 : queryList) {
					Object[] obj1 = (Object[]) o1;					
						object.setId((Integer)obj1[0]);	
						String s = obj1[1]+"";
						String status = obj1[2]+"";
						if(obj1[2] != null && obj1[2].toString().equalsIgnoreCase("Debarred")){	
							object.setName("Y");	
						}else{
							if(s.equalsIgnoreCase("Y")){
								object.setName("Y");	
							}else if(s.equalsIgnoreCase("N")){
								object.setName("N");
							}else if(s.equalsIgnoreCase("I")){
								object.setName("I");
							}
						}
				}
			}else{
				object.setId(0);
				object.setName("Success");
			}
			
					
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
			//simpleObjectList.add(object);
		return object;
	}
	

	public Integer getWorkerInfoId(Integer workerId,Date transactionDate){
		Session session = sessionFactory.getCurrentSession();
		List q = null;
		Integer  workerinfoid=0;
		try{
			if(workerId != null && workerId >0){
			q =  (List) session.createSQLQuery("SELECT `Worker_info_id` FROM worker_details_info WHERE worker_id ='"+workerId+"' AND transaction_date = '"+DateHelper.convertDateToSQLString(transactionDate)+"'  AND `Sequence_id` = 1").list();
			if(q.size() > 0){
			Integer obj = (Integer) q.get(0);
			workerinfoid = obj;
			}
			}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}	
			
			
		
			
			//simpleObjectList.add(object);
		return workerinfoid;
	}

	/*=============================== Worker Details END ====================================*/	
	
	
	@Override
	public List<WorkerDetailsVo> workerVerificationDetailsByWorkerInfoId(String workerInfoId) {
		Session session = sessionFactory.getCurrentSession();
		List<WorkerDetailsVo> workerDetailsList = new ArrayList<WorkerDetailsVo>();		
		List<WorkerVerificationStatusVo> verificationList = new ArrayList<WorkerVerificationStatusVo>();		
		try {
			
			 String q = " SELECT DISTINCT vdi.customer_id,cdi.customer_name,vdi.company_id,com.company_name,wd.vendor_id,ven.vendor_name, "+
						" 	wd.country_id,mc.country_Name,vdi.worker_id, "+
						" 	CONCAT(vdi.first_name,' ',vdi.last_name) AS workerName,wd.worker_code "+
						" FROM worker_details_info vdi "+
						" INNER JOIN customer_details_info cdi ON(cdi.customer_id = vdi.customer_id) "+
						" INNER JOIN company_details_info com ON(com.company_id = vdi.company_id) "+
						" INNER JOIN worker_details wd ON(wd.worker_id = vdi.worker_id) "+
						" INNER JOIN vendor_details_info ven ON (wd.vendor_id =  ven.vendor_id) "+
						" LEFT JOIN m_country mc ON(mc.country_id = wd.country_id) "+
						" WHERE  "+
						" CONCAT(DATE_FORMAT(cdi.transaction_date, '%Y%m%d'), LPAD(cdi.Customer_Sequence_Id, 2, '0')) =   "+
						"  (  "+
						"  SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.Customer_Sequence_Id, 2, '0')))  "+
						"  FROM customer_details_info vdi1  "+
						"  WHERE cdi.customer_id = vdi1.customer_id    "+
						"   AND vdi1.transaction_date <= CURRENT_DATE()    "+
						"  )  "+
						"  AND   "+
						"  CONCAT(DATE_FORMAT(com.transaction_date, '%Y%m%d'), LPAD(com.Company_Sequence_Id, 2, '0')) =   "+
						"  (  "+
						"  SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.Company_Sequence_Id, 2, '0')))  "+
						"  FROM company_details_info vdi1  "+
						" 	 WHERE com.company_id = vdi1.  company_id  "+
						"   AND vdi1.transaction_date <= CURRENT_DATE()    "+
						"  )  "+
						"  AND   "+
						"  CONCAT(DATE_FORMAT(ven.transaction_date, '%Y%m%d'), LPAD(ven.Sequence_Id, 2, '0')) =   "+
						"  (  "+
						"  SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.Sequence_Id, 2, '0')))  "+
						"  FROM vendor_details_info vdi1  "+
						"  WHERE ven.vendor_id = vdi1.vendor_id  "+
						"   AND vdi1.transaction_date <= CURRENT_DATE() "+
						" 	 ) and vdi.worker_info_id =  "+workerInfoId;
			
			 List tempList = session.createSQLQuery(q).list();
			
			for(Object o : tempList){
				Object[] obj = (Object[]) o;
				WorkerDetailsVo workerVo = new WorkerDetailsVo();
				workerVo.setCustomerId((Integer)obj[0]);
				workerVo.setCustomerName(obj[1]+"");
				workerVo.setCompanyId((Integer)obj[2]);
				workerVo.setCompanyName(obj[3]+"");
				workerVo.setVendorId((Integer)obj[4]);
				workerVo.setVendorName(obj[5]+"");
				workerVo.setCountryId((Integer)obj[6]);
				workerVo.setCountryName(obj[7]+"");
				workerVo.setWorkerId((Integer)obj[8]);
				workerVo.setFirstName(obj[9]+"");
				workerVo.setWorkerCode(obj[10]+"");	

				workerDetailsList.add(workerVo);
			}			
			
			List workerVerificatinStatus = session.createSQLQuery("SELECT Verification_Type,IsCleared,Comments,Verification_Status,File_Name,Location,Verification_id, status  FROM worker_verification_status WHERE Worker_Id =" + workerDetailsList.get(0).getWorkerId()).list();

			for (Object o1 : workerVerificatinStatus) {
				Object[] obj1 = (Object[]) o1;
				WorkerVerificationStatusVo workerVerificationStatusVo = new WorkerVerificationStatusVo();
				workerVerificationStatusVo.setVerificationType(obj1[0].toString());
				if(obj1[1] != null){
					workerVerificationStatusVo.setIsCleared((String)obj1[1]);
				}else{
					workerVerificationStatusVo.setIsCleared("No");
				}
				workerVerificationStatusVo.setComments(obj1[2].toString());			
				if(obj1[3] != null){
					workerVerificationStatusVo.setVerificationStatus(obj1[3].toString());
				}
				/*if(obj1[3] != null){
					workerVerificationStatusVo.setIsValidated(obj1[3].toString());
				}
				if(obj1[4] != null){
				workerVerificationStatusVo.setBadgeGenerationStatus(obj1[4].toString());
				}*/
				if(obj1[4] != null){
				workerVerificationStatusVo.setFileName(obj1[4].toString());
				}
				if(obj1[5] != null){
				workerVerificationStatusVo.setLocation(obj1[5].toString());
				}
				
				workerVerificationStatusVo.setVerificationId((Integer)obj1[6]);
				
				if(obj1[7] != null){
					workerVerificationStatusVo.setStatus(obj1[7].toString());
				}
				
				//if(workerVo.getWorkerVerificationList() != null)
				//workerVo.getWorkerVerificationList().add(workerVerificationStatusVo);
				verificationList.add(workerVerificationStatusVo);
				workerDetailsList.get(0).setWorkerVerificationList(verificationList);
			}
		} catch (Exception e) {
			log.error("Error Occured ",e);

		}
		
		
		return workerDetailsList;
	}


	@Override
	public Integer saveVerificationStatus(String formData, MultipartFile[] files, String fileNames) {
		Session session = sessionFactory.getCurrentSession();
		System.out.println(formData);
		log.info("Input data:  "+formData+"  :::::::   "+files.length);
		Integer flag= 0;
		Integer verificationId = 0;
		Integer workerInfoId = 0;
		try{
			
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(formData);
			
			JsonArray array = jo.get("workerVerificationList").getAsJsonArray();	
					
					
			Query q2 = session.createSQLQuery("DELETE FROM worker_verification_Status WHERE Worker_id = '"+jo.get("workerId").getAsInt()+"'");
			q2.executeUpdate();
				//flag3= false;
			WorkerVerificationStatus verificationSttus = new WorkerVerificationStatus(); 
			WorkerDetailsInfo workerDetailsVo = new WorkerDetailsInfo();
			List<WorkerDetailsVo> workerList = new ArrayList<WorkerDetailsVo>();
			
			for (Object o : array) {				
				JsonObject jobj = (JsonObject) o;	
				verificationSttus = new WorkerVerificationStatus();
				verificationSttus.setCustomerId(jo.get("customerId").getAsInt());
				verificationSttus.setCompanyId(jo.get("companyId").getAsInt());
				verificationSttus.setVendorId(jo.get("vendorId").getAsInt());
				verificationSttus.setCountryId(jo.get("countryId").getAsInt());
				verificationSttus.setWorkerDetails(new WorkerDetails(jo.get("workerId").getAsInt()));
				verificationSttus.setVerificationStatus(jo.get("verificationStatus").getAsString());
				if(jobj.get("verificationType") != null && !jobj.get("verificationType").toString().equalsIgnoreCase("null")){
					verificationSttus.setVerificationType(jobj.get("verificationType").getAsString());
				}
				/*if(jobj.get("isCleared") != null && !jobj.get("isCleared").toString().equalsIgnoreCase("null")){
					verificationSttus.setIsCleared(jobj.get("isCleared").getAsString());
				}*/
				if(jobj.get("status") != null && !jobj.get("status").toString().equalsIgnoreCase("null")){
					verificationSttus.setStatus(jobj.get("status").getAsString());
				}
				if(jobj.get("comments") != null && !jobj.get("comments").toString().equalsIgnoreCase("null")){
					verificationSttus.setComments(jobj.get("comments").getAsString());
				}
				verificationSttus.setCreatedBy(jo.get("createdBy").getAsInt());
				verificationSttus.setCreatedDate(new Date());
				verificationSttus.setModifiedBy(jo.get("modifiedBy").getAsInt());
				verificationSttus.setModifiedDate(new Date());
				
				String realPathtoUploads =  null;
				
						
						
				boolean flag3 = true;
				List workerVerificatinStatusList =  session.createSQLQuery("Select * FROM worker_verification_Status WHERE Worker_id = '"+jo.get("workerId").getAsInt()+"'").list();
				
				/*if(workerVerificatinStatusList.size() >0 && flag3){
					Query q2 = session.createSQLQuery("DELETE FROM worker_verification_Status WHERE Worker_id = '"+jo.get("workerId").getAsInt()+"'");
					q2.executeUpdate();
					/*flag3= false;
					if(new File(realPathtoUploads).exists())
	                {			                 
	                	new File(realPathtoUploads).delete();			                	
	                }
				}*/
				
				//System.out.println(jobj.has("fileName"));
				//System.out.println(jobj.get("fileName").toString().equalsIgnoreCase("null"));
				//System.out.println(jobj.get("fileName").getAsString().equalsIgnoreCase(multipartFile.getOriginalFilename()));
				System.out.println(jobj.has("location") && !jobj.get("location").toString().equalsIgnoreCase("null") && !jobj.get("location").toString().equalsIgnoreCase("") &&  jobj.get("location") != null && !jobj.get("location").getAsString().isEmpty() );
				System.out.println( jobj.has("fileName") && !jobj.get("fileName").toString().equalsIgnoreCase("null") && jobj.get("fileName") != null && !jobj.get("fileName").getAsString().isEmpty());
				if(files.length > 0  ){
					 for(MultipartFile multipartFile :files ){
						 System.out.println(jobj.has("fileName")  && !jobj.get("fileName").toString().equalsIgnoreCase("null") && jobj.get("fileName") != null &&  jobj.get("fileName").getAsString().equalsIgnoreCase(multipartFile.getOriginalFilename()));
						 if(jobj.has("fileName")  && !jobj.get("fileName").toString().equalsIgnoreCase("null") && jobj.get("fileName") != null &&  jobj.get("fileName").getAsString().equalsIgnoreCase(multipartFile.getOriginalFilename())){
							 realPathtoUploads =  ROOT_FOLDER_WORKER_VERIFICATION+jo.get("workerId").getAsInt()+'/';
							 
							 if(! new File(realPathtoUploads).exists()){
								 new File(realPathtoUploads).mkdirs();				                  
				             }
							 
							 verificationSttus.setFileName(multipartFile.getOriginalFilename());
							 File destinationPath = new File(realPathtoUploads+multipartFile.getOriginalFilename()); 
	                		 multipartFile.transferTo(destinationPath);	
	                		 verificationSttus.setLocation(realPathtoUploads+multipartFile.getOriginalFilename());
							 
						 }else if(jobj.has("location") && !jobj.get("location").toString().equalsIgnoreCase("null") &&  jobj.get("location") != null && !jobj.get("location").getAsString().isEmpty()  
									&& jobj.has("fileName") && !jobj.get("fileName").toString().equalsIgnoreCase("null")  && jobj.get("fileName") != null && !jobj.get("fileName").getAsString().isEmpty()){
								verificationSttus.setFileName(jobj.get("fileName").getAsString());
								verificationSttus.setLocation(jobj.get("location").getAsString());
						}
					 }
				}else if(jobj.has("location") && !jobj.get("location").toString().equalsIgnoreCase("null") &&  jobj.get("location") != null && !jobj.get("location").getAsString().isEmpty()  
						&& jobj.has("fileName") && !jobj.get("fileName").toString().equalsIgnoreCase("null")  && jobj.get("fileName") != null && !jobj.get("fileName").getAsString().isEmpty()){
				verificationSttus.setFileName(jobj.get("fileName").getAsString());
				verificationSttus.setLocation(jobj.get("location").getAsString());
			}
				
				verificationId = (Integer) session.save(verificationSttus);
				System.out.println(verificationId);
				
			}
					

			
		}catch(Exception e){
			log.error("Error Occured ",e);
			flag = 1;
		}
		return flag;
	}
	
	
	
	 
	 
	public List<LabourTimeVo> viewWorkerTimeDetailsForEditing(WorkerDetailsVo detailsVo){
		Session session = sessionFactory.getCurrentSession();
		List<LabourTimeVo> workerDetailsList = new ArrayList<LabourTimeVo>();	
		String dates = "";
		
		String schema = null;
		try {
			
			if(detailsVo.getSchemaName() != null && !detailsVo.getSchemaName().isEmpty() && !detailsVo.getSchemaName().equalsIgnoreCase("null"))
				schema = detailsVo.getSchemaName();
			else
				schema = dbSchemaName;
			System.out.println(detailsVo.getYear()+"year");
			if(Integer.parseInt(detailsVo.getYear()) > 0 && Integer.parseInt(detailsVo.getMonth()) >0){		    	
		    	 dates = detailsVo.getYear().concat("-").concat(detailsVo.getMonth()).concat("-").concat("01");		    	
		    }
			
			String q = "SELECT DISTINCT vd.vendor_id,vdi.vendor_name ,replace((CONCAT(RTRIM(wdi.first_name), ' ',CASE WHEN (wdi.middle_name IS NULL) THEN '' ELSE CONCAT(RTRIM(wdi.middle_name),' ') END, wdi.last_name)),'  ',' ')  AS workerName,wd.worker_code,ltr.shift, "
					+ " DATE_FORMAT(date_field,'%d/%m/%Y') AS `Date`, TIME(IFNULL(InTime,'')) AS inTime,TIME(IFNULL(OutTime,'')) AS outTime, "
					+ " IFNULL(ManHours,0),IFNULL(OTHours,0) AS OT,Attendance,vd.vendor_code,ltr.Designation,ltr.created_by,ltr.created_date,ltr.modified_by  "
					+ " FROM worker_details wd JOIN worker_details_info wdi ON wd.worker_id = wdi.worker_id "
					+ " JOIN vendor_details vd ON vd.vendor_id = wd.vendor_id "
					+ " JOIN vendor_details_info vdi ON vdi.vendor_id = wd.vendor_id " + " JOIN ( "
					+ " SELECT date_field " + " FROM " + " ( " + "   SELECT " ;
			
					if (dates != null && dates != "") {
						q= q+ "  MAKEDATE(YEAR('"+dates+"'),1) + " + " INTERVAL (MONTH('"+dates+"')-1) MONTH + ";
					}else if(detailsVo.getBussinessFromDate() != null && detailsVo.getBussinessFromDate() + "" != ""
							&& detailsVo.getBussinessEndDate() != null && detailsVo.getBussinessEndDate() + "" != ""){
						q= q+ "  MAKEDATE(YEAR('"+DateHelper.convertDateToSQLString(detailsVo.getBussinessFromDate())+"'),1) + " + " INTERVAL (MONTH('"+DateHelper.convertDateToSQLString(detailsVo.getBussinessFromDate())+"')-1) MONTH + ";
					}
					
					q= q +  "         INTERVAL daynum DAY date_field " + "     FROM " + "     ( "
							+ "         SELECT t*10+u daynum " + "         FROM "
							+ "             (SELECT 0 t UNION SELECT 1 UNION SELECT 2 UNION SELECT 3) A, "
							+ "             (SELECT 0 u UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 "
							+ "            UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 "
							+ "             UNION SELECT 8 UNION SELECT 9) B " + "         ORDER BY daynum " + "     ) AA "
							+ "  ) AAA ";
			 
					if (dates != null && dates != "") {
						q = q + " WHERE MONTH(date_field) = MONTH('" + dates + "') and YEAR(date_field) = '" + dates + "') d  ";
					} else if (detailsVo.getBussinessFromDate() != null && detailsVo.getBussinessFromDate() + "" != ""
							&& detailsVo.getBussinessEndDate() != null && detailsVo.getBussinessEndDate() + "" != "") {
						q = q + " WHERE date_field BETWEEN '" + DateHelper.convertDateToSQLString(detailsVo.getBussinessFromDate()) + "' and '"
								+ DateHelper.convertDateToSQLString(detailsVo.getBussinessEndDate()) + "') d   ";
					}  
						   
					q = q + " LEFT JOIN "+schema+".labor_time_report ltr ON ltr.emp = wd.worker_code "
							+ " AND ltr.business_date = d.date_field " + " WHERE date_field >= wdi.`Date_Of_Joining` AND "
							+ "   CONCAT(DATE_FORMAT(wdi.transaction_date, '%Y%m%d'), LPAD(wdi.Sequence_Id, 2, '0')) =   "
							+ "  (  "
							+ "  SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.Sequence_Id, 2, '0')))  "
							+ "  FROM worker_details_info vdi1  " + "  WHERE  wdi.`worker_id` = vdi1.`worker_id`      "
							+ "   AND vdi1.transaction_date <= CURRENT_DATE()    " + "  )     AND  "
							+ "  CONCAT(DATE_FORMAT(vdi.transaction_date, '%Y%m%d'), LPAD(vdi.Sequence_Id, 2, '0')) =   "
							+ "  (  "
							+ "  SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.Sequence_Id, 2, '0')))  "
							+ "  FROM vendor_details_info vdi1  " + "  WHERE  vdi.`vendor_id` = vdi1.`vendor_id`  "
							+ "   AND vdi1.transaction_date <= CURRENT_DATE() ) AND wd.customer_id='"+detailsVo.getCustomerId()+"' AND  wd.company_id= '"+detailsVo.getCompanyId()+"' AND  vd.vendor_id='"+detailsVo.getVendorId()+"' ";
						    
							if(detailsVo.getWorkerCode() != null && detailsVo.getWorkerCode() != "" && Integer.parseInt(detailsVo.getWorkerCode()) > 0){
								q= q+" AND wd.worker_code = '"+detailsVo.getWorkerCode()+"'";
							}
							
							if(detailsVo.getFirstName() != null && detailsVo.getFirstName()!= ""){
								q= q+" AND replace((CONCAT(RTRIM(wdi.first_name), ' ',CASE WHEN (wdi.middle_name IS NULL) THEN '' ELSE CONCAT(RTRIM(wdi.middle_name),' ') END, wdi.last_name)),'  ',' ')  = '"+detailsVo.getFirstName()+"'";
							}
					
						    q = q+ "  ORDER BY d.date_field ";
			 
			 List tempList = session.createSQLQuery(q).list();
			String contractName = "";
			String empName = "";
			String empCode = "";
			String ContractCode = "";
			String designation = "";
			
			
			for(Object o : tempList){
				Object[] obj = (Object[]) o;
				 LabourTimeVo labourVo = new LabourTimeVo();
				if(obj[1] != null){
					labourVo.setContractorName(obj[1]+"");
					labourVo.setEmpName(obj[2]+"");
					labourVo.setEmployeeCode(obj[3]+"");
					labourVo.setContractorCode(obj[11]+"");
					labourVo.setDesignation(obj[12]+"");
					 contractName = obj[1]+"";
					 empName = obj[2]+"";
					 empCode = obj[3]+"";
					 ContractCode = obj[11]+"";
					 designation = obj[12]+"";					
				}else{
					labourVo.setContractorName(contractName);
					labourVo.setEmpName(empName);
					labourVo.setEmployeeCode(empCode);
					labourVo.setContractorCode(ContractCode);
					labourVo.setDesignation(designation);					
				}
				
				if(obj[4] != null && ( obj[4]+"" == "WO" || obj[4]+"" == "AB" || obj[4]+"" == "PH" )){
					labourVo.setShiftId(obj[4]+"");		
					labourVo.setInTime("0");
					labourVo.setOutTime("0");
					labourVo.setStatus(obj[4]+"" == "WO" ? "WO":obj[4]+"" == "PH" ? "Public Holiday" : obj[4]+"" == "AB" ? "Absent" : "");
				}else if(obj[4] != null){
					
					labourVo.setShiftId(obj[4]+"");
					if(obj[6] != null){
					labourVo.setInTime(obj[6]+"");
					}
					if(obj[7] != null){
					labourVo.setOutTime(obj[7]+"");
					}
					if(obj[10] != null){
						labourVo.setStatus(obj[10]+"");
					}
				}
				
				labourVo.setBusinessDate(obj[5]+"");				
				labourVo.setManHours(obj[8]+"");
				labourVo.setOtHours(obj[9]+"");				
				System.out.println("obj 11"+obj[11]);
				if(obj[13] != null && !obj[13].toString().equalsIgnoreCase("null")){
				labourVo.setCreatedBy((Integer)obj[13]);
				}
				
				DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Date transactionDate = null;
				System.out.println(obj[14]);
			/*	try {
					if(obj[14] != null && obj[14] != "" && !obj[14].toString().isEmpty() ){
					transactionDate = (Date) formatter.parse(obj[14]+"");
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(transactionDate != null){
				
				}*/
				if(obj[14] != null){
				labourVo.setCreatedDate((Date)obj[14]);
				}
				if(obj[15] != null){
				labourVo.setModifiedBy((Integer)obj[15]);
				}
							
				workerDetailsList.add(labourVo);
			}						
		} catch (Exception e) {
			log.error("Error Occured ",e);

		}
		return workerDetailsList;
	}
	
	
	
	public Integer saveModifiedLaborTimeDetails(WorkerDetailsVo detailsVo){
		Session session = sessionFactory.getCurrentSession();
		String schema = null;
		Integer flag= 1;
		Date newBusinessdate = null;
		try{
			if(detailsVo.getSchemaName() != null && !detailsVo.getSchemaName().isEmpty() && !detailsVo.getSchemaName().equalsIgnoreCase("null"))
				schema = detailsVo.getSchemaName();
			else
				schema = dbSchemaName;
			
			System.out.println(schema+" ::::::::::::::::::::::::::::::::::::::::::schema:::"+schema);
				for(LabourTimeVo labourTimeVo: detailsVo.getLabourTimeDetails()){				
					
					DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");		
					Date businesDate = (Date) formatter.parse(labourTimeVo.getBusinessDate());
					
					if(labourTimeVo.getInTime() != null && labourTimeVo.getInTime() != "" && labourTimeVo.getOutTime() != null && labourTimeVo.getOutTime() != ""){
						String arryaInTime[] = labourTimeVo.getInTime().split(":");
						String arryaOutTime[] = labourTimeVo.getOutTime().split(":");
						System.out.println(arryaInTime[0]+":::"+arryaOutTime[0]);
						if(Integer.parseInt(arryaInTime[0]) > Integer.parseInt(arryaOutTime[0])){
							newBusinessdate =  (Date) formatter.parse(labourTimeVo.getBusinessDate());					
							Calendar c = Calendar.getInstance(); 
							c.setTime(newBusinessdate); 
							c.add(Calendar.DATE, 1);
							newBusinessdate = c.getTime();
							System.out.println(newBusinessdate+":::newBusinessdate");
						}
						
					}
					
					
					String sq = " SELECT wd.worker_id,wd.vendor_id,wjd.location_id,wjd.plant_id,wjd.department_id,wjd.section_id,wjd.work_area_id,wjd.work_skill, "
							+ " jcdi.job_name "
							+ " FROM worker_details wd  "
							+ " left JOIN work_job_details wjd ON (wjd.worker_id = wd.worker_id  and "							
							+ " CONCAT(DATE_FORMAT(wjd.transaction_date, '%Y%m%d'), wjd.Sequence_Id) =  "
							+ " (  "
							+ " SELECT MAX(CONCAT(DATE_FORMAT(wjd1.transaction_date, '%Y%m%d'), wjd1.Sequence_Id))  "
							+ " FROM work_job_details wjd1  "
							+ " WHERE wjd.`Worker_Id` = wjd1.`Worker_Id`  AND wjd1.`Company_Id` = wjd.`Company_Id`  AND wjd1.`Customer_Id` = wjd.customer_id  "
							+ "  AND wjd1.transaction_date <= CURRENT_DATE()  "
							+ " ) ) "	
							+ " left JOIN job_code_details_info jcdi ON (jcdi.job_code_id = wjd.job_name and "																
							+ " CONCAT(DATE_FORMAT(jcdi.transaction_date, '%Y%m%d'), jcdi.`Job_Code_Sequence_Id`) =  "
							+ " (  "
							+ " SELECT MAX(CONCAT(DATE_FORMAT(wjd1.transaction_date, '%Y%m%d'), wjd1.Job_Code_Sequence_Id))  "
							+ " FROM job_code_details_info wjd1  "
							+ " WHERE jcdi.`job_code_id` = wjd1.`job_code_id`  "
							+ "  AND jcdi.transaction_date <= CURRENT_DATE()  "
							+ " ) )  where wd.worker_code ='"+labourTimeVo.getEmployeeCode()+"'";
				
				
				List tempList =  session.createSQLQuery(sq).list();
				Integer workerId = 0;
				Integer vendorId = 0;
				//Integer locationId = 0;
				//Integer plantId = 0;
				//Integer departmentId = 0;
				//Integer sectionId = 0;
				//Integer workAreaId = 0;
				String workSkill = null;
				//String designation = null;
				for(Object obj:tempList){
					Object[] ob = (Object[])obj;
					workerId = (Integer) ob[0];
					vendorId = (Integer) ob[1];	
				//locationId = (Integer) ob[1];
					//plantId = (Integer) ob[1];	
					///departmentId = (Integer) ob[1];	
				//	sectionId = (Integer) ob[1];	
				//	workAreaId = (Integer) ob[1];	
					workSkill =  ob[7] != null ? (String) ob[7] :null;	
					//designation = (String) ob[8];									
				}
					
					if(workerId>0){
					List checkAvilabilityOfDate = session.createSQLQuery("select * from  "+schema+".Labor_Time_report  WHERE Emp ='"+labourTimeVo.getEmployeeCode()+"' AND Business_Date ='"+DateHelper.convertDateToSQLString(businesDate)+"'").list();
					if(checkAvilabilityOfDate.size() >0){
						String inTime = labourTimeVo.getInTime() != null && labourTimeVo.getInTime() != "" ? labourTimeVo.getInTime() : null;
						String outTime = labourTimeVo.getOutTime() != null && labourTimeVo.getOutTime() != "" ? labourTimeVo.getOutTime() : null;
						String otHours = labourTimeVo.getOtHours() != "" ? labourTimeVo.getOtHours() : "0";
						String manHours = labourTimeVo.getManHours() != "" ? labourTimeVo.getManHours() : null;
						String designation = labourTimeVo.getDesignation() != null && !labourTimeVo.getDesignation().toString().equalsIgnoreCase("null") ? labourTimeVo.getDesignation() : "";
						String businessDate;
						Query q2 = session.createSQLQuery("UPDATE  "+schema+".Labor_Time_report SET work_skill = '"+workSkill+"' ,ContractorCode ='"+labourTimeVo.getContractorCode()+"' ,ContractorName  = '"+labourTimeVo.getContractorName()+"',Emp='"+labourTimeVo.getEmployeeCode()+"',Emp_Name='"+labourTimeVo.getEmpName()+"',Designation='"+designation+"',Shift='"+labourTimeVo.getShiftId()+"' ,Intime= "+(inTime != null ? "'"+DateHelper.convertDateToSQLString(businesDate)+' '+inTime+"'":inTime)+",OutTime= "+(outTime != null ?  newBusinessdate != null ? "'"+DateHelper.convertDateToSQLString(newBusinessdate)+' '+outTime+"'" : "'"+DateHelper.convertDateToSQLString(businesDate)+' '+outTime+"'":outTime)+",Attendance= '"+labourTimeVo.getStatus()+"',ManHours= "+(manHours != null ? "'"+manHours+"'":manHours)+ ",OTHours= "+otHours+",Business_Date= '"+DateHelper.convertDateToSQLString(businesDate)+"', modified_by = '"+detailsVo.getModifiedBy()+"' , modified_Date ='"+DateHelper.convertDateTimeToSQLString(new Date())+"', customer_id = '"+detailsVo.getCustomerId()+"',company_Id = '"+detailsVo.getCustomerId()+"',vendor_id = '"+detailsVo.getVendorId()+"',worker_Id = '"+workerId+"'  WHERE Emp ='"+labourTimeVo.getEmployeeCode()+"' AND Business_Date ='"+DateHelper.convertDateToSQLString(businesDate)+"' AND Location_Id = '"+detailsVo.getLocationId()+"' AND Plant_Id = '"+detailsVo.getPlantId()+"' AND Department_Id = '"+detailsVo.getDepartmentId()+"'");
						q2.executeUpdate();	
					}else{
						String designation = labourTimeVo.getDesignation() != null && !labourTimeVo.getDesignation().toString().equalsIgnoreCase("null") ? labourTimeVo.getDesignation() : "";
						String inTime = labourTimeVo.getInTime() != null && labourTimeVo.getInTime() != "" ? labourTimeVo.getInTime() : null;
						String outTime = labourTimeVo.getOutTime() != null && labourTimeVo.getOutTime() != "" ? labourTimeVo.getOutTime() : null;
						String otHours = labourTimeVo.getOtHours() != "" ? labourTimeVo.getOtHours() : "0";
						String manHours = labourTimeVo.getManHours() != "" ? labourTimeVo.getManHours() : null;
						
						String ss = "INSERT INTO  "+schema+".Labor_Time_report (work_skill,ContractorCode,ContractorName,Emp,Emp_Name,Designation,Shift,Intime,OutTime,Attendance,ManHours,OTHours,Business_Date,created_by,created_date,modified_by,modified_date,customer_id,company_id,vendor_id,worker_id,Location_id,Department_Id,Plant_Id) "+
								" VALUES ('"+workSkill+"','"+labourTimeVo.getContractorCode()+"','"+labourTimeVo.getContractorName()+"',  "+
							   	" '"+labourTimeVo.getEmployeeCode()+"','"+labourTimeVo.getEmpName()+"','"+designation+"','"+labourTimeVo.getShiftId()+"' , "+
							   	(inTime != null ? "'"+DateHelper.convertDateToSQLString(businesDate)+' '+inTime+"'":inTime)+  ", "+(outTime != null ? newBusinessdate != null ?  "'"+DateHelper.convertDateToSQLString(newBusinessdate)+' '+outTime+"'" :  "'"+DateHelper.convertDateToSQLString(businesDate)+' '+outTime+"'":outTime)+", "+
								" '"+labourTimeVo.getStatus()+"', "+(manHours != null ? "'"+manHours+"'":manHours)+", "+otHours+", '"+DateHelper.convertDateToSQLString(businesDate)+"','"+detailsVo.getCreatedBy()+"','"+DateHelper.convertDateTimeToSQLString(new Date())+"','"+detailsVo.getModifiedBy()+"','"+DateHelper.convertDateTimeToSQLString(new Date())+ "','"+detailsVo.getCustomerId() +"','"+detailsVo.getCompanyId()+"','"+detailsVo.getVendorId() +"','"+workerId+"','"+detailsVo.getLocationId()+"','"+detailsVo.getDepartmentId()+"','"+detailsVo.getPlantId()+"')" ;
						System.out.println("Query:::"+ss);
						Query q2 = session.createSQLQuery(ss);
															
						q2.executeUpdate();
					}
					
					
				
						Query laborSchedule = session.createSQLQuery("UPDATE labor_scheduled_shifts SET Shift = '"+labourTimeVo.getShiftId()+"' ,customer_id = '"+detailsVo.getCustomerId()+"',company_Id = '"+detailsVo.getCompanyId()+"',vendor_id = '"+detailsVo.getVendorId()+"',worker_id='"+workerId+"', Modified_By='"+detailsVo.getModifiedBy()+"' , Modified_Date='"+DateHelper.convertDateTimeToSQLString(new Date())+"' WHERE Emp_Code = '"+labourTimeVo.getEmployeeCode()+"' AND Business_Date ='"+DateHelper.convertDateToSQLString(businesDate)+"'");
						laborSchedule.executeUpdate();
					}
					
					
					flag =0;
				}
			
		}catch(Exception e){
			flag= 1;
			log.error("Error Occured ",e);
			
		}
		return flag;
	}
	
	
	
	public List<ShiftsDefineVo>  getAvailableShifts(ShiftsDefineVo shiftsDefineVo){
		Session session = sessionFactory.getCurrentSession();
		List q = null;		
		List<ShiftsDefineVo> shifsList = new ArrayList();
		try{
			String query = " SELECT shift_Code,TIME_FORMAT(End_Time,'%r') FROM shifts_define sd "+
					   " WHERE  "+
					   " CONCAT(DATE_FORMAT(sd.Transaction_Date,'%Y%m%d'),LPAD(sd.Sequence_id,2,'0')) = (SELECT "+
					   " MAX(CONCAT(DATE_FORMAT(vdi1.Transaction_Date,'%Y%m%d'),LPAD(vdi1.Sequence_id,2,'0'))) "+
					   " FROM shifts_define vdi1 "+
					   " WHERE ((sd.shift_code = vdi1.shift_code) "+
					   " AND (vdi1.Transaction_Date <= CURDATE()))) ";
			
				if(shiftsDefineVo.getCustomerDetailsId() != null && shiftsDefineVo.getCustomerDetailsId() >0 ){
				query += " AND sd.`Customer_Id`= "+shiftsDefineVo.getCustomerDetailsId();
				}
				if(shiftsDefineVo.getCompanyDetailsId() != null && shiftsDefineVo.getCompanyDetailsId() >0 ){
				query += " AND sd.`Company_Id`= "+shiftsDefineVo.getCompanyDetailsId();		
				}
				
				if(shiftsDefineVo.getLocationDetailsId() != null && shiftsDefineVo.getLocationDetailsId() >0 ){
				query += " AND sd.`Location_Id`= "+shiftsDefineVo.getLocationDetailsId();		
				}
				if(shiftsDefineVo.getPlantDetailsId() != null && shiftsDefineVo.getPlantDetailsId() >0 ){
				query += " AND sd.`Plant_Id`= "+shiftsDefineVo.getPlantDetailsId();		
				}
				
				query += " AND sd.`Is_Active`= 'Y'";
		/*q =  (List) session.createSQLQuery(" SELECT shift_Code,TIME_FORMAT(End_Time,'%r') FROM shifts_define sd "+
										   " WHERE  "+
										   " CONCAT(DATE_FORMAT(sd.Transaction_Date,'%Y%m%d'),LPAD(sd.Sequence_id,2,'0')) = (SELECT "+
										   " MAX(CONCAT(DATE_FORMAT(vdi1.Transaction_Date,'%Y%m%d'),LPAD(vdi1.Sequence_id,2,'0'))) "+
										   " FROM shifts_define vdi1 "+
										   " WHERE ((sd.shift_code = vdi1.shift_code) "+
										   " AND (vdi1.Transaction_Date <= CURDATE())))").list();	*/	
				q =  (List) session.createSQLQuery(query).list();	
			for(Object o :q){
				Object[] obj = (Object[]) o;
				ShiftsDefineVo defineVo = new ShiftsDefineVo();
				defineVo.setShiftName(obj[0]+"");
				defineVo.setEndTime(obj[1]+"");
				shifsList.add(defineVo);
			}		
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return shifsList;
	}
	
	 public List<WorkerDetailsVo>  getWorkerChildScreensData(Integer workerId){
		Session session = sessionFactory.getCurrentSession();
		List q = null;		
		List<WorkerDetailsVo> workerChildIdsList = new ArrayList();
		try{
		q =  (List) session.createSQLQuery("SELECT vwd.workerId,vwd.Worker_info_id,IFNULL(wvs.verification_id,0) AS verificationId, "+
											" IFNULL(wjd.Work_Job_Details_Id ,0) AS Work_Job_Details_Id, IFNULL(wbg.`Worker_Badge_Id`,0) AS Worker_Badge_Id "+
											" FROM v_worker_details vwd "+
											" LEFT JOIN worker_verification_status wvs ON (vwd.workerId = wvs.worker_id) "+
											" LEFT JOIN `worker_badge_generation` wbg  ON (vwd.workerId = wbg.worker_id) "+
											" LEFT JOIN work_job_details wjd ON (vwd.workerId = wjd.worker_id AND"+	
											
											"  CONCAT(DATE_FORMAT(`wjd`.`Transaction_Date`,'%Y%m%d'),LPAD(`wjd`.`Sequence_id`,2,'0')) = (SELECT "+
											" MAX(CONCAT(DATE_FORMAT(`vdi1`.`Transaction_Date`,'%Y%m%d'),LPAD(`vdi1`.`Sequence_id`,2,'0'))) "+
											" FROM `work_job_details` `vdi1` "+
											" WHERE ((`wjd`.`Worker_id` = `vdi1`.`Worker_id`) "+
											" AND (`wjd`.`Transaction_Date` <= CURRENT_DATE())))) "+											
											"  WHERE vwd.workerid ="+workerId).list();		
			for(Object o :q){
				Object[] obj = (Object[]) o;
				WorkerDetailsVo workerVo = new WorkerDetailsVo();
				workerVo.setWorkerId((Integer)obj[0]);
				workerVo.setWorkerInfoId((Integer)obj[1]);
				workerVo.setVerificationId(new BigInteger(obj[2]+"").intValue());
				workerVo.setWorkJobDetailsId(new BigInteger(obj[3]+"").intValue());
				workerVo.setWorkerBadgeId(new BigInteger(obj[4]+"").intValue());
				workerChildIdsList.add(workerVo);
			}		
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return workerChildIdsList;
	} 
	
	
	@Override
	public List<SimpleObject> getVendorNamesAsDropDown(String customerId,String companyId,String vendorId, String vendorStatus) { 
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> simpleObjects = new ArrayList<SimpleObject>(); 
		System.out.println("Vendor Status : "+vendorStatus);
		try {
			String query = "SELECT a.vendor_id,concat(a.vendor_Name,' (',b.vendor_code,') ') as vname, b.vendor_code FROM vendor_details_info  a "
						+" inner join vendor_details b on a.vendor_id = b.vendor_id "
						+" WHERE CONCAT(DATE_FORMAT(a.transaction_date, '%Y%m%d'), LPAD(a.Sequence_Id,2,'0')) =  "
						+" ( SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.Sequence_Id,2,'0'))) "
						+" FROM vendor_details_info vdi1  "
						+" 	WHERE a.vendor_id = vdi1.vendor_id AND a.company_id= vdi1.company_id AND a.customer_id = vdi1.customer_id "
						+" AND vdi1.transaction_date <= CURRENT_DATE())   ";

			if(customerId!= null && !customerId.isEmpty() && Integer.parseInt(customerId) > 0){		
				query +=" and a.Customer_Id= '"+customerId+"' ";
			}
			if(companyId!= null && !companyId.isEmpty() && Integer.parseInt(companyId) > 0){		
				query +=" and a.company_id= '"+companyId+"' ";
			}
			if(vendorId!= null && !vendorId.isEmpty() && Integer.parseInt(vendorId) > 0){		
				query +=" and a.vendor_id= '"+vendorId+"' ";
			}
			if(vendorStatus!= null && !vendorStatus.isEmpty() ){		
				query +=" and a.Vendor_Status= '"+vendorStatus+"' ";
			}
			
			query+=" order by a.vendor_name ";
			List tempList =  session.createSQLQuery(query).list();
			
			
																	
			for(Object  o: tempList){
				Object[] obj = (Object[]) o;
				SimpleObject object = new SimpleObject();
				object.setId((Integer)obj[0]);
				object.setName(obj[1]+"");
				object.setDesc(obj[2]+"");
				simpleObjects.add(object);
			}
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return simpleObjects;
	}
	
	
	@Override
	public String getWorkerAddressByWorkerId(Integer workerId) { 
		Session session = sessionFactory.getCurrentSession();
		String address = null;
		System.out.println("workerId : "+workerId);
		try {
			String query = " SELECT  "+
							" REPLACE(REPLACE(CONCAT(Address_line1, "+
							" 	CASE WHEN (Address_line2 IS NULL) THEN ', ' ELSE CONCAT(Address_line2,', ') END, "+
							" 	CASE WHEN (Address_line3 IS NULL) THEN ', ' ELSE CONCAT(Address_line3,', ') END, "+
							" 	CASE WHEN (City IS NULL) THEN ', ' ELSE CONCAT(City,', ') END, "+
							" 	CASE WHEN (wd.State_id IS NULL) THEN ', ' ELSE CONCAT(ms.State_Name,', ') END, "+
							" 	CASE WHEN (wd.country_id IS NULL) THEN ', ' ELSE CONCAT(mc.Country_Name,', ') END, "+
							" 	CASE WHEN (Pin_code IS NULL) THEN '.' ELSE CONCAT(Pin_code,'.') END) ,' ,',''),', .','.') AS address "+
							" 	FROM worker_address wd "+
							" 	LEFT JOIN m_country mc ON (mc.Country_Id=wd.country_id) "+
							" 	LEFT JOIN m_state ms ON (ms.State_Id = wd.State_id) "+
							" 	WHERE CONCAT(DATE_FORMAT(wd.transaction_date, '%Y%m%d'), LPAD(wd.Sequence_Id, 2, '0')) =   "+
							" 	 (  "+
							" 	 SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.Sequence_Id, 2, '0')))  "+
							" 	 FROM worker_address vdi1  "+
							" 	 WHERE wd.worker_id = vdi1.worker_id    "+
							" 	  AND vdi1.transaction_date <= CURRENT_DATE()    "+
							" 	 ) "+							 
							" 	  AND Address_type = 'Permanent' "+
							" 	 AND wd.worker_id = '"+workerId+"' ";

			List tempList =  session.createSQLQuery(query).list();
			
			
																	
			for(Object  o: tempList){
				address = (String) o;				
			}
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return address;
	}
	
	
	
	@Override
	public Integer getWorkerId(int workerCode) { 
		Session session = sessionFactory.getCurrentSession();
		Integer address = null;
		System.out.println("workerId : "+workerCode);
		try {
			String query = " SELECT worker_id from worker_details where worker_code = '"+workerCode+"' ";

			List tempList =  session.createSQLQuery(query).list();		
			
																	
			for(Object  o: tempList){
				address = (Integer) o;				
			}
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return address;
	}
	
	
	@Override
	public boolean checkWorkerStatus(WorkerDetailsVo workervo){
		Session session = sessionFactory.getCurrentSession();		
		boolean falgNew = false;
		System.out.println("workerInfoId : "+workervo.getWorkerInfoId());
		try {
			List tempList =  session.createSQLQuery("SELECT *  FROM v_worker_details WHERE is_active = 'I'  AND Worker_info_id = '"+workervo.getWorkerInfoId()+"'").list();	
			
			if(tempList != null && tempList.size() >0){
				falgNew = true;
			}			
			
		}catch(Exception e){
			falgNew = false;
			e.printStackTrace();
		}
		return falgNew;
	}	
	
	
	
	
	@Override
	public boolean validateWorkerAndSchedulingShifts(WorkerDetailsVo workervo){
		Session session = sessionFactory.getCurrentSession();
		String workerCode = null;
		Date dateOfJoining = null;
		boolean falgNew = false;
		System.out.println("workerInfoId : "+workervo.getWorkerInfoId());
		try {
			List tempList =  session.createSQLQuery("SELECT CASE WHEN  Date_Of_Joining > CURRENT_DATE() THEN  Date_Of_Joining ELSE CURRENT_DATE() END AS DOJ,Worker_code  FROM v_worker_details WHERE is_active = 'I'  AND Worker_info_id = '"+workervo.getWorkerInfoId()+"'").list();	
			
			for(Object  o: tempList){
				Object[] obj = (Object[]) o;		
				dateOfJoining = (Date)obj[0];
				workerCode = (String)obj[1];				
			}			
			
			
			
			List labourScheduledList = session.createSQLQuery(
					"SELECT date_field AS Business_date, vd.vendor_code,worker_CODE,replace((CONCAT(RTRIM(first_name), ' ',CASE WHEN (middle_name IS NULL) THEN '' ELSE CONCAT(RTRIM(middle_name),' ') END, last_name)),'  ',' ') , "
							+ " (CASE WHEN TRIM(UPPER(Weekly_off)) = UPPER(DATE_FORMAT(date_field,'%a')) THEN 'WO' ELSE shift_name END) AS Shift, "
							+ " Weekly_Off,vdi.vendor_name ,wd.customer_id,wd.company_id,wd.vendor_id,wd.worker_id,wjd.location_id,wjd.plant_id,wjd.department_id,wjd.section_id, "
							+ " wjd.work_area_id,wjd.work_skill,jcdi.job_name  "
							+ " FROM worker_details wd  "
							+ " JOIN worker_details_info wdi ON wd.worker_id = wdi.worker_id  "
							+ " JOIN vendor_details vd ON  wd.vendor_id = vd.vendor_id  "
							+ " JOIN vendor_details_info vdi ON ( vd.vendor_id =  vdi.vendor_id AND (CONCAT(DATE_FORMAT(`vdi`.`Transaction_Date`, '%Y%m%d'), `vdi`.`Sequence_Id`) = (SELECT MAX(CONCAT(DATE_FORMAT(`vdi1`.`Transaction_Date`, '%Y%m%d'), `vdi1`.`Sequence_Id`)) FROM `vendor_details_info` `vdi1` WHERE ((`vdi`.`vendor_id` = `vdi1`.`vendor_id`) AND (`vdi1`.`Transaction_Date` <= CURDATE())))) ) "
							+ " JOIN  work_job_details wjd ON (wjd.worker_id = wd.worker_id AND CONCAT(DATE_FORMAT(wjd.transaction_date, '%Y%m%d'), wjd.Sequence_Id) = ( SELECT MAX(CONCAT(DATE_FORMAT(wjd1.transaction_date, '%Y%m%d'), wjd1.Sequence_Id)) FROM work_job_details wjd1 WHERE wjd.`Worker_Id` = wjd1.`Worker_Id`  AND wjd1.`Company_Id` = wjd.`Company_Id`  AND wjd1.`Customer_Id` = wjd.customer_id  AND wjd1.transaction_date <= CURRENT_DATE())) "
							+ " JOIN job_code_details_info jcdi ON (jcdi.job_code_id = wjd.job_name AND CONCAT(DATE_FORMAT(jcdi.transaction_date, '%Y%m%d'), jcdi.`Job_Code_Sequence_Id`) = ( SELECT MAX(CONCAT(DATE_FORMAT(wjd1.transaction_date, '%Y%m%d'), wjd1.Job_Code_Sequence_Id)) FROM job_code_details_info wjd1 WHERE jcdi.`job_code_id` = wjd1.`job_code_id`  AND jcdi.transaction_date <= CURRENT_DATE() )) "
							+ " JOIN "
							+ " ( SELECT date_field FROM( SELECT MAKEDATE(YEAR('"+dateOfJoining+"'),1) + INTERVAL (MONTH('"+dateOfJoining+"')-1) MONTH + INTERVAL daynum DAY date_field "
							+ " FROM ( SELECT t*10+u daynum FROM "
							+ "             (SELECT 0 t UNION SELECT 1 UNION SELECT 2 UNION SELECT 3) A, "
							+ "            (SELECT 0 u UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 "
							+ "             UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 "
							+ "             UNION SELECT 8 UNION SELECT 9) B " + "        ORDER BY daynum "
							+ "     ) AA ) AAA WHERE date_field BETWEEN ('"+dateOfJoining+"')  AND LAST_DAY('"+dateOfJoining+"')   "
							+ " ) AS Month_date WHERE  " + " shift_name IS NOT NULL AND weekly_off IS NOT NULL "
							+ " AND date_field >= wdi.date_of_joining AND CASE WHEN wdi.`Date_Of_Leaving` IS NOT NULL THEN  date_field <= +" +  "  wdi.`Date_Of_Leaving` ELSE -1='-1' END "
							+ " AND ( (select count(*) from worker_details wdd join worker_details_info wdii on (wdd.worker_id = wdii.worker_id)) =1 or  CONCAT(DATE_FORMAT(`wdi`.`Transaction_Date`, '%Y%m%d'), `wdi`.`Sequence_Id`) = (SELECT  "
							+ "                MAX(CONCAT(DATE_FORMAT(`wdi1`.`Transaction_Date`, '%Y%m%d'), `wdi1`.`Sequence_Id`)) "
							+ "             FROM " + "                `worker_details_info` `wdi1` "
							+ "             WHERE " + "                 ((`wdi`.`worker_id` = `wdi1`.`worker_id`) "
							+ "                     AND (`wdi`.`Transaction_Date` <= CURDATE())))) "
							+ " AND wd.worker_code = '"+workerCode+"'").list();
			
			boolean flag = true;
			for(Object a :labourScheduledList){
				Object[] c = (Object[])a;	
				    if(flag){	
				    	
				    	// Activating Worker
				    	WorkerDetailsInfo	workerDetails = (WorkerDetailsInfo) session.load(WorkerDetailsInfo.class, workervo.getWorkerInfoId());
				    	workerDetails.setIsActive("Y");
				    	session.update(workerDetails);				    	
				    	
				    	// Deleting Shifts
						 Query x = session.createSQLQuery("delete from labor_scheduled_shifts where business_date >=  '"+dateOfJoining+"'  and CODE = '"+c[1]+"' and emp_code = '"+c[2]+"'");
						 x.executeUpdate();
						 flag = false;
				  }				  
				    
				    Integer  plantId  =c[12] != null ? (Integer)c[12] : null;
					  Integer departmentId  =c[13] != null ? (Integer)c[13] : null;
					  Integer  sectionId  =c[14] != null ? (Integer)c[14] : null;
					  Integer  workAreaId  =c[15] != null ? (Integer)c[15] : null;
					  //String  WorkSkill = c[16] != null && c[16] != "" ? c[16]+"" : null;
					  //String designation  =c[17] != null && c[17] != "" ? c[17]+"" : null;
				    
				    
				    
				    // Assigning New Shifts
					String values = "'" + c[0] + "'" + ",'" + c[1] + "'" + ",'" + c[2] + "'" + ",'" + c[3] + "'" + ",'"
							+ c[4] + "','" + c[5] + "','" + c[6] + "','" + workervo.getCreatedBy() + "','"
							+ DateHelper.convertDateTimeToSQLString(new Date()) + "','" + workervo.getModifiedBy() + "','"
							+ DateHelper.convertDateTimeToSQLString(new Date()) +  "','" + c[7] + "','" + c[8] +  "','" 
							+ c[9] + "','" + c[10] + "','" + c[11] + "'," + plantId + "," + departmentId + "," + sectionId + "," + workAreaId + ",'" + c[16] + "','" + c[17] + "'";
					Query q = session.createSQLQuery(
				"insert into labor_scheduled_shifts (business_date,CODE,emp_code,emp_name,shift,wo,ContractorName,Created_By,Created_date," 
				+ " Modified_By,Modified_Date,Customer_Id,Company_Id,Vendor_Id,worker_Id,location_id,plant_id,department_Id,section_Id,work_Area_id,Work_Skill,Designation) values ("
									+ values + ")");
					
					q.executeUpdate();	
					falgNew = true;
			}
			
			
		} catch (Exception e) {
			falgNew = false;
			log.error("Error Occured ",e);
		}
		return falgNew;
		
	}


	@Override
	public SimpleObject getMaximumWorkerCode(WorkerDetailsVo paramDetails) {
		Session session = sessionFactory.getCurrentSession();
		List q = null;
		//String message = null;
		List<SimpleObject> returnList = new ArrayList();
		SimpleObject object = new SimpleObject();
		try{
			if(paramDetails != null &&  paramDetails.getCustomerId() > 0 && paramDetails.getCompanyId() > 0 && paramDetails.getVendorId() > 0){
				q =  (List) session.createSQLQuery(" SELECT MAX(worker_Code)+1, Vendor_Code from worker_details wd  JOIN vendor_details vd ON wd.Vendor_Id = vd.Vendor_Id where wd.customer_Id  IN ('"+paramDetails.getCustomerId()+"') and wd.company_Id  in ('"+paramDetails.getCompanyId()+"') and wd.vendor_Id  in ('"+paramDetails.getVendorId()+"')").list();
			}
			
			if (q.size() >0) {	
				if(((Double)((Object[])q.get(0))[0]) != null){
				//object.setId(Integer.parseInt((String)((Object[])q.get(0))[0]));
					object.setName(((Double)((Object[])q.get(0))[0]).intValue()+"");
				}else{
					object.setId(0);
					object.setName((String)((Object[])q.get(0))[1]+"0001");
				}
			}
		}catch(Exception e){
			log.error("Error Occured ",e);
			object.setId(-1);
		}
		//returnList.add(object);
			//simpleObjectList.add(object);
		return object;
	}
	
	
	@Override
	public Integer changeWorkerCode(WorkerDetailsVo workerVo) {
		Session session = sessionFactory.getCurrentSession();
		WorkerDetails workerDetails = new WorkerDetails(); 
		Integer workerId = 0;
		Integer workerInfoId = 0;
		String schema = null;
		try {
			Criteria criteria = session.createCriteria(WorkerDetails.class);
			criteria.add(Restrictions.ne("workerId", workerVo.getWorkerId()));
		
			if(workerVo != null && workerVo.getCustomerId() > 0){
				criteria.add(Restrictions.eq("customerId", workerVo.getCustomerId()));
			}
			if(workerVo != null && workerVo.getCompanyId() > 0){
				criteria.add(Restrictions.eq("companyId", workerVo.getCompanyId()));
			}
			if(workerVo != null && workerVo.getVendorId() > 0){
				criteria.add(Restrictions.eq("vendorId", workerVo.getVendorId()));
			}
				criteria.add(Restrictions.eq("workerCode", workerVo.getWorkerCode()));
			
			List duplicateList = criteria.list();
			if(duplicateList.size() > 0){
				return -1;
			}else{
				if(workerVo.getWorkerId() >0 && workerVo.getWorkerCode() != null && !workerVo.getWorkerCode().isEmpty()){
					workerDetails = (WorkerDetails) session.load(WorkerDetails.class, workerVo.getWorkerId());				
					workerDetails.setWorkerCode(workerVo.getWorkerCode());							
					workerDetails.setModifiedBy(workerVo.getModifiedBy());
					workerDetails.setModifiedDate(new Date());				
					session.update(workerDetails);
					workerId = workerDetails.getWorkerId();
					
				}
			}
			
		} catch (Exception e) {
			workerId = 0;
			log.error("Error Occured ",e);
		}
		
		return workerId;
	}
	
	
}
