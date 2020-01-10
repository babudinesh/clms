package com.Ntranga.CLMS.Dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Ntranga.CLMS.vo.AssignShiftsVo;
import com.Ntranga.CLMS.vo.PlantVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.WorkJobDetailsVo;
import com.Ntranga.core.DateHelper;
import com.Ntranga.core.CLMS.entities.CompanyDetails;
import com.Ntranga.core.CLMS.entities.CustomerDetails;
import com.Ntranga.core.CLMS.entities.DepartmentDetails;
import com.Ntranga.core.CLMS.entities.LocationDetails;
import com.Ntranga.core.CLMS.entities.PlantDetails;
import com.Ntranga.core.CLMS.entities.SectionDetails;
import com.Ntranga.core.CLMS.entities.VendorDetails;
import com.Ntranga.core.CLMS.entities.WageRateDetails;
import com.Ntranga.core.CLMS.entities.WorkAreaDetails;
import com.Ntranga.core.CLMS.entities.WorkJobDetails;
import com.Ntranga.core.CLMS.entities.WorkerDetails;
import com.Ntranga.core.CLMS.entities.WorkorderDetailInfo;

import common.Logger;

@SuppressWarnings({"rawtypes"})
@Transactional
@Repository("workerJobDetailsDao")
public class WorkerJobDetailsDaoImpl implements WorkerJobDetailsDao {

	private static Logger log = Logger.getLogger(WorkerJobDetailsDaoImpl.class);
	
	
	@Autowired
	private SessionFactory sessionFactory;
	

	@Override
	public List<WorkJobDetailsVo> getSearchGridData(Integer customerId, Integer companyId, Integer vendorId,
			String workerCode, String workerName, String isActive) {
		
			Session session = sessionFactory.getCurrentSession();
			List<WorkJobDetailsVo> workJobDetailsVo = new ArrayList<WorkJobDetailsVo>(); 
			try {
				String query ="SELECT  Work_Job_Details_Id,cdi.customer_name,cmpdi.`Company_Name`,vdi.`Vendor_Name` ,CONCAT(IFNULL(wdi.`First_name`,''),' ',IFNULL(wdi.`Middle_name`,''),' ',IFNULL(wdi.`Last_name`,'') ) AS wname,  DATE_FORMAT(wjd.transaction_date,'%d/%m/%Y') "
								+" FROM `work_job_details` wjd "
								+" INNER JOIN customer_details_info cdi ON cdi.customer_id=wjd.customer_id "
								+" INNER JOIN `company_details_info` cmpdi ON cmpdi.`Company_Id`=wjd.`Company_Id` "
								+" INNER JOIN `vendor_details_info` vdi ON vdi.`Vendor_Id`=wjd.`Vendor_Id` "
								+" INNER JOIN  `worker_details_info` wdi ON wdi.`Worker_id` = wjd.`Worker_Id` "
								+" INNER JOIN  `worker_details` wd ON wd.`Worker_id` = wjd.`Worker_Id` "
								+" WHERE  CONCAT(DATE_FORMAT(wjd.transaction_date, '%Y%m%d'), wjd.Sequence_Id) = "
								+" ( "
								+" SELECT MAX(CONCAT(DATE_FORMAT(wjd1.transaction_date, '%Y%m%d'), wjd1.Sequence_Id)) "
								+" FROM work_job_details wjd1 "
								+" WHERE wjd.`Worker_Id` = wjd1.`Worker_Id`  AND wjd1.`Company_Id` = wjd.`Company_Id`  AND wjd1.`Customer_Id` = wjd.customer_id "
								+"  AND wjd1.transaction_date <= CURRENT_DATE() "
										 +" ) "
								
								+" AND CONCAT(DATE_FORMAT(cdi.transaction_date, '%Y%m%d'), cdi.Customer_Sequence_Id) = "
								+" ( "
								+" SELECT MAX(CONCAT(DATE_FORMAT(cdi1.transaction_date, '%Y%m%d'), cdi1.Customer_Sequence_Id)) "
								+" FROM customer_details_info cdi1 "
								+" WHERE cdi.customer_id = cdi1.customer_id AND cdi1.transaction_date <= CURRENT_DATE() "
										+" ) "
								 
								+" AND  CONCAT(DATE_FORMAT(cmpdi.transaction_date, '%Y%m%d'), cmpdi.`Company_Sequence_Id`) = "
								+" ( "
								+" SELECT MAX(CONCAT(DATE_FORMAT(cmpdi1.transaction_date, '%Y%m%d'), cmpdi1.`Company_Sequence_Id`)) "
								+" FROM `company_details_info`  cmpdi1 "
								+" WHERE cmpdi.`Company_Id` = cmpdi1.`Company_Id` AND cmpdi1.transaction_date <= CURRENT_DATE() "
								+" ) "
								
								+" AND  CONCAT(DATE_FORMAT(vdi.transaction_date, '%Y%m%d'), vdi.`Sequence_Id`) = "
								+" ( "
										+" SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), vdi1.`Sequence_Id`)) "
								+" FROM `vendor_details_info`  vdi1 "
								+" WHERE vdi.Vendor_Id = vdi1.`Vendor_Id`  "
								+" AND vdi1.transaction_date <= CURRENT_DATE() "
								+" ) "
								
								
								+" AND  CONCAT(DATE_FORMAT(wdi.transaction_date, '%Y%m%d'), LPAD(wdi.`Sequence_Id`,2,'0')) = "
								+" ( "
								+" SELECT MAX(CONCAT(DATE_FORMAT(wdi1.transaction_date, '%Y%m%d'), LPAD(wdi1.`Sequence_Id`,2,'0'))) "
								+" FROM `worker_details_info`  wdi1 "
								+" WHERE wdi.`Worker_Id` = wdi1.`Worker_Id` AND wdi1.transaction_date <= CURRENT_DATE() "
								+" ) ";
				
				if(customerId != null && customerId > 0){
					query += " AND  wjd.`Customer_Id`= "+customerId;
				}
				if(companyId != null && companyId > 0){
					query += " AND  wjd.`Company_Id`= "+companyId;
				}
				if(vendorId != null && vendorId > 0){
					query += " AND  wjd.`Vendor_Id`= "+vendorId;
				}
				if(workerName!= null && !workerName.isEmpty() ){
					query += " AND  CONCAT(IFNULL(wdi.`First_name`,''),' ', IFNULL(wdi.`Middle_name`,''),' ', IFNULL(wdi.`Last_name`,'') ) LIKE '"+workerName+"%'";
				}
				if(workerCode != null && !workerCode.isEmpty() ){
					query += " AND  wd.Worker_Code LIKE '"+workerCode+"%'";
				}
				
				if(isActive != null && !isActive.isEmpty()){
					query += " AND  wjd.`Is_Active`= '"+isActive+"'";
				}
				query +=" Order by wname asc"	;		 
				List tempList =  session.createSQLQuery(query).list();					
				for(Object o : tempList){				
					Object[] obj =  (Object[]) o;
					WorkJobDetailsVo detailsVo = new WorkJobDetailsVo();
					detailsVo.setWorkJobDetailsId((Integer)obj[0] );
					detailsVo.setStrTransactionDate(obj[5]+"");
					//detailsVo.setIsActive( obj[5]+"" );
					detailsVo.setCustomerName( obj[1]+"" );
					detailsVo.setCompanyName( obj[2]+"" );
					detailsVo.setVendorName(obj[3]+"" );
					detailsVo.setWorkerName(obj[4]+"" );
					workJobDetailsVo.add(detailsVo);
				}
			} catch (Exception e) {
				log.error("Error Occured ",e);
			}
			return workJobDetailsVo;
		}

	@Override
	public List<WorkJobDetailsVo> getWorkJobDetailsById(int workerJobId) {
		List<WorkJobDetailsVo> masterInfoMap = new ArrayList<WorkJobDetailsVo>();
		WorkJobDetailsVo workJobDetailsVo = new WorkJobDetailsVo();
		try{
			WorkJobDetails details = (WorkJobDetails) sessionFactory.getCurrentSession().get(WorkJobDetails.class, workerJobId);
			workJobDetailsVo.setWorkJobDetailsId(details.getWorkJobDetailsId());
			workJobDetailsVo.setTransactionDate(details.getTransactionDate());
			//workJobDetailsVo.setIsActive(details.getIsActive());
			//workJobDetailsVo.setReasonForChange(details.getReasonForChange());
			workJobDetailsVo.setJobType(details.getJobType());
			workJobDetailsVo.setJobName(details.getJobName());
			workJobDetailsVo.setWorkSkill(details.getWorkSkill());
			//workJobDetailsVo.setWorkStartDate(details.getWorkStartDate());

			workJobDetailsVo.setLocationId(details.getLocationDetails().getLocationId() != null ? details.getLocationDetails().getLocationId() : 0);
			workJobDetailsVo.setPlantId(details.getPlantDetails());
			workJobDetailsVo.setDepartmentId(details.getDepartmentId() != null ? details.getDepartmentId() : 0);
			workJobDetailsVo.setWorkAreaId(details.getWorkAreaDetails());
			workJobDetailsVo.setWorkOrderId(details.getWorkOrderId() != null ? details.getWorkOrderId() : 0);
			workJobDetailsVo.setSectionId(details.getSectionDetails());
			workJobDetailsVo.setWageRateId(details.getWageRateDetails());

			/*workJobDetailsVo.setLocationName(details.getLocationName());
			workJobDetailsVo.setPlantName(details.getPlantName());			
			workJobDetailsVo.setDepartmentId(details.getDepartmentId());
			workJobDetailsVo.setLocationId(details.getLocationId());
			workJobDetailsVo.setDepartment(details.getDepartment());*/

			//workJobDetailsVo.setPlantId(details.getPlantId());
			workJobDetailsVo.setReportingManager(details.getReportingManager());
			//workJobDetailsVo.setCardType(details.getCardType());
			workJobDetailsVo.setVendorSupervisorName(details.getVendorSupervisorName());
			workJobDetailsVo.setContactNo(details.getContactNo());
			workJobDetailsVo.setPfNumber(details.getPfNumber());
			workJobDetailsVo.setEsiNumber(details.getEsiNumber());	
			workJobDetailsVo.setCustomerDetailsId(details.getCustomerDetails().getCustomerId());
			workJobDetailsVo.setCompanyDetailsId(details.getCompanyDetails().getCompanyId());
			workJobDetailsVo.setVendorDetailsId(details.getVendorDetails().getVendorId());
			workJobDetailsVo.setWorkerDetailsId(details.getWorkerDetails().getWorkerId());
			workJobDetailsVo.setSequenceId(details.getSequenceId());
			masterInfoMap.add(workJobDetailsVo);
		}catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return masterInfoMap;
	}

	@Override
	public List<SimpleObject> getTransactionDatesOfHistory(Integer customerId, Integer companyId, Integer vendorId,
			Integer workerId) {
		
		List<SimpleObject> objects = new ArrayList<SimpleObject>(); 
		try {
			String query ="SELECT `Work_Job_Details_Id`, CONCAT(DATE_FORMAT(Transaction_Date,'%d/%m/%Y'),' - ', Sequence_Id) AS tname FROM `work_job_details` jobDetails WHERE jobDetails.`Customer_Id`="+customerId+"  AND jobDetails.`Company_Id`="+companyId+"  AND jobDetails.`Vendor_Id`="+vendorId+" AND  jobDetails.`Worker_Id`="+workerId ;							
						 
			List tempList =  sessionFactory.getCurrentSession().createSQLQuery(query).list();					
			for(Object o : tempList){				
				Object[] obj =  (Object[]) o;
				
				objects.add(new SimpleObject((Integer)obj[0], obj[1]+""));
			}
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return objects;
	}

	@Override
	public Integer saveOrUpdateWorkJobRecord(WorkJobDetailsVo workJobDetailsVo) {
		Integer workJobId = 0;
		Session session = null;
		WorkJobDetails details = null;
		try{
			
			session = sessionFactory.getCurrentSession();
			
			if(workJobDetailsVo.getWorkJobDetailsId() != null && workJobDetailsVo.getWorkJobDetailsId() > 0){
				details = (WorkJobDetails) session.load(WorkJobDetails.class, workJobDetailsVo.getWorkJobDetailsId()) ;
				workJobId = workJobDetailsVo.getWorkJobDetailsId() ;
			}else{
				details = new WorkJobDetails(); 
			}
			
			details.setTransactionDate(workJobDetailsVo.getTransactionDate());
			//details.setIsActive(workJobDetailsVo.getIsActive());
			//details.setReasonForChange(workJobDetailsVo.getReasonForChange());
			details.setJobType(workJobDetailsVo.getJobType());
			details.setJobName(workJobDetailsVo.getJobName());
			details.setWorkSkill(workJobDetailsVo.getWorkSkill());
			//details.setWorkStartDate(workJobDetailsVo.getWorkStartDate());

			details.setLocationDetails(new LocationDetails(workJobDetailsVo.getLocationId()));
			details.setPlantDetails(workJobDetailsVo.getPlantId());
			details.setDepartmentId(workJobDetailsVo.getDepartmentId() );
			details.setWorkAreaDetails(workJobDetailsVo.getWorkAreaId());
			details.setWorkOrderId(workJobDetailsVo.getWorkOrderId());
			details.setSectionDetails(workJobDetailsVo.getSectionId());
			details.setWageRateDetails(workJobDetailsVo.getWageRateId());;

			/*details.setLocationName(workJobDetailsVo.getLocationName());
			details.setLocationId(workJobDetailsVo.getLocationId());
			details.setDepartmentId(workJobDetailsVo.getDepartmentId());
			details.setPlantName(workJobDetailsVo.getPlantName());
			details.setPlantId(workJobDetailsVo.getPlantName());
			details.setDepartment(workJobDetailsVo.getDepartment());
*/
			details.setReportingManager(workJobDetailsVo.getReportingManager());
			//details.setCardType(workJobDetailsVo.getCardType());
			details.setVendorSupervisorName(workJobDetailsVo.getVendorSupervisorName());
			details.setContactNo(workJobDetailsVo.getContactNo());
			details.setPfNumber(workJobDetailsVo.getPfNumber());
			details.setEsiNumber(workJobDetailsVo.getEsiNumber());			
			details.setModifiedBy(workJobDetailsVo.getModifiedBy());
			details.setModifiedDate(new Date());
			
			if(workJobDetailsVo.getWorkJobDetailsId() != null && workJobDetailsVo.getWorkJobDetailsId() > 0){
				session.update(details);
			}else{
				details.setCustomerDetails(new CustomerDetails(workJobDetailsVo.getCustomerDetailsId()));
				details.setCompanyDetails(new CompanyDetails(workJobDetailsVo.getCompanyDetailsId()));
				details.setVendorDetails(new VendorDetails(workJobDetailsVo.getVendorDetailsId()));
				details.setWorkerDetails(new WorkerDetails(workJobDetailsVo.getWorkerDetailsId()));
				BigInteger sequenceId = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Sequence_Id),0) FROM  `work_job_details` jobDetails WHERE jobDetails.`Customer_Id`="+workJobDetailsVo.getCustomerDetailsId()+"  AND jobDetails.`Company_Id`="+workJobDetailsVo.getCompanyDetailsId()+"  AND jobDetails.`Vendor_Id`="+workJobDetailsVo.getVendorDetailsId()+" AND  jobDetails.`Worker_Id`="+workJobDetailsVo.getWorkerDetailsId()+" and  `Transaction_Date`='"+DateHelper.convertDateToSQLString(workJobDetailsVo.getTransactionDate())+"'").list().get(0);
				details.setSequenceId(sequenceId.intValue()+1);
				details.setCreatedBy(workJobDetailsVo.getCreatedBy());
				details.setCreatedDate(new Date());
				workJobId = (Integer) session.save(details);
			}
						
			

		}catch(Exception e){
			
			log.error("Error Occured ",e);
		}
		return workJobId;
	}

	@Override
	public List<SimpleObject> getWorkerNamesAsDropDown(Integer customerId, Integer companyId, Integer vendorId) {
		List<SimpleObject> objects = new ArrayList<SimpleObject>(); 
		try {
			String query =" SELECT wd.`Worker_id`, CONCAT(COALESCE(wdi.`First_name`,''),' ',COALESCE(wdi.`Middle_name`,''),' ',COALESCE(wdi.`Last_name`,''),' (',wd.worker_code,')' ) AS wname FROM worker_details wd  INNER JOIN worker_details_info wdi ON (wd.`Worker_id` = wdi.`Worker_id`) WHERE  CONCAT(DATE_FORMAT(wdi.transaction_date, '%Y%m%d'), LPAD(wdi.Sequence_Id,2,'0')) =  ( SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.Sequence_Id,2,'0'))) FROM worker_details_info vdi1 WHERE wdi.`Worker_id` = vdi1.`Worker_id` AND vdi1.transaction_date <= CURRENT_DATE() ) " ;							
			query += "AND wdi.is_active='Y' and wd.`Customer_Id`= "+customerId+"  AND  wd.`Company_Id`= "+companyId+"  AND  wd.`Vendor_Id`= "+vendorId+" ORDER BY wname asc" ;
			
			List tempList =  sessionFactory.getCurrentSession().createSQLQuery(query).list();					
			for(Object o : tempList){				
				Object[] obj =  (Object[]) o;
				
				objects.add(new SimpleObject((Integer)obj[0], obj[1]+""));
			}
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return objects;
	}
	
	
	@Override
	public List<SimpleObject> getAllWorkerNamesAsDropDown(Integer customerId, Integer companyId, Integer vendorId) {
		List<SimpleObject> objects = new ArrayList<SimpleObject>(); 
		try {
			String query =" SELECT wd.`Worker_id`, CONCAT(COALESCE(wdi.`First_name`,''),' ',COALESCE(wdi.`Middle_name`,''),' ',COALESCE(wdi.`Last_name`,''),' (',wd.worker_code,')' ) AS wname FROM worker_details wd  INNER JOIN worker_details_info wdi ON (wd.`Worker_id` = wdi.`Worker_id`) WHERE  CONCAT(DATE_FORMAT(wdi.transaction_date, '%Y%m%d'), LPAD(wdi.Sequence_Id,2,'0')) =  ( SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.Sequence_Id,2,'0'))) FROM worker_details_info vdi1 WHERE wdi.`Worker_id` = vdi1.`Worker_id` AND vdi1.transaction_date <= CURRENT_DATE() ) " ;							
			query += "AND wdi.is_active in ('Y','I','N') and wd.`Customer_Id`= "+customerId+"  AND  wd.`Company_Id`= "+companyId+"  AND  wd.`Vendor_Id`= "+vendorId+" ORDER BY wname asc" ;
			
			List tempList =  sessionFactory.getCurrentSession().createSQLQuery(query).list();					
			for(Object o : tempList){				
				Object[] obj =  (Object[]) o;
				
				objects.add(new SimpleObject((Integer)obj[0], obj[1]+""));
			}
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return objects;
	}
	
	
	

	@Override
	public List<SimpleObject> getReportingManagerDropDown(String customerId, String companyId) {
		List<SimpleObject> managers = new ArrayList<SimpleObject>(); 
		try {
			String query =" SELECT employee_id,CONCAT(COALESCE(emp.`First_Name`,''),' ',COALESCE(`Middle_Name`,''),' ',COALESCE(`Last_Name`,'')) As employeeName  FROM `employee_information` emp  WHERE  CONCAT(DATE_FORMAT(emp.transaction_date, '%Y%m%d'), emp.Sequence_Id) = ( SELECT MAX(CONCAT(DATE_FORMAT(emp1.transaction_date, '%Y%m%d'), emp1.Sequence_Id)) FROM employee_information emp1 WHERE emp.unique_id = emp1.unique_id AND emp1.transaction_date <= CURRENT_DATE() )  " ;							
			query += "  AND  emp.Customer_Id = "+customerId+"  AND  emp.Company_Id = "+companyId+" order by employeeName" ;
			
			List tempList =  sessionFactory.getCurrentSession().createSQLQuery(query).list();					
			for(Object o : tempList){				
				Object[] obj =  (Object[]) o;				
				managers.add(new SimpleObject((Integer)obj[0], obj[1]+""));
			}
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return managers;
	}

	@Override
	public List<SimpleObject> getJobCodesDropDown(String customerId, String companyId) {
		List<SimpleObject> jobnames = new ArrayList<SimpleObject>(); 
		try {
/*			String query =  "SELECT jobcode.`Job_Code_Details_Id`, CONCAT(job.`Job_Code`,' - ', jobcode.`Job_Name`,Manager_Level) AS jname FROM job_code_details_info jobcode  INNER JOIN job_code_details job ON job.Job_Code_Id = jobcode.Job_Code_Id WHERE jobcode.`Transaction_Date` <= CURRENT_DATE() AND jobcode.Transaction_Date = ( SELECT MAX(info1.Transaction_Date) FROM job_code_details_info info1  WHERE jobcode.Job_Code_Id = info1.Job_Code_Id AND info1.Transaction_Date <= jobcode.Transaction_Date) AND Job_Code_Sequence_Id IN ( SELECT MAX(info2.Job_Code_Sequence_Id) FROM job_code_details_info info2 WHERE info2.Job_Code_Id = jobcode.Job_Code_Id  AND info2.Status = jobcode.Status AND info2.Transaction_Date <= jobcode.Transaction_Date ) ";							
			query += " AND  jobcode.Customer_Id = "+customerId+"  AND  jobcode.Company_Id = "+companyId+"  Order by jname asc" ;
*/			
			String query =  "SELECT jobcode.`Job_Code_Id`, CONCAT(job.`Job_Code`,' - ', jobcode.`Job_Name`) AS jname FROM job_code_details_info jobcode  "
							+ " INNER JOIN job_code_details job ON job.Job_Code_Id = jobcode.Job_Code_Id "
							+ " WHERE CONCAT(DATE_FORMAT(jobcode.transaction_date, '%Y%m%d'), LPAD(jobcode.job_code_Sequence_Id, 2, '0')) =   "
							+ " (  "   
						    + " SELECT MAX(CONCAT(DATE_FORMAT(job1.transaction_date, '%Y%m%d'), LPAD(job1.job_code_Sequence_Id, 2, '0')))  "  
							+ " FROM job_code_details_info job1  " 
							+ " WHERE  job.Job_Code_Id = job1.Job_Code_Id  " 
							+ " AND job1.transaction_date <= CURRENT_DATE() )  "							
							+ " AND  jobcode.Customer_Id = "+customerId+"  AND  jobcode.Company_Id = "+companyId+"  Order by jname asc" ;
			
			List tempList =  sessionFactory.getCurrentSession().createSQLQuery(query).list();					
			for(Object o : tempList){				
				Object[] obj =  (Object[]) o;				
				jobnames.add(new SimpleObject((Integer)obj[0], obj[1]+""));
			}
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return jobnames;
	}

	@Override
	public List<SimpleObject> getCardTypesDropDown(String customerId, String companyId) {
		List<SimpleObject> cardTypes = new ArrayList<SimpleObject>(); 
		try {
			String query =  " SELECT 	`card_Type_Id`, `Card_Type` FROM `customer_company_cardtypes`  ";							
			query += " WHERE  Customer_Id = "+customerId+"  AND  Company_Id = "+companyId+" ORDER BY Card_Type asc" ;
			
			List tempList =  sessionFactory.getCurrentSession().createSQLQuery(query).list();					
			for(Object o : tempList){				
				Object[] obj =  (Object[]) o;				
				cardTypes.add(new SimpleObject((Integer)obj[0], obj[1]+""));
			}
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return cardTypes;
	}

	@Override
	public List<SimpleObject> getWorkOrdersDropDown(String customerId, String companyId) {
		List<SimpleObject> workOrder = new ArrayList<SimpleObject>(); 
		try {
			String query =  "SELECT parent.`WorkOrder_id`,info.`Work_Order_Name` FROM `workrorder_detail`  parent INNER JOIN `workorder_detail_info` info ON (parent.`WorkOrder_id` = info.`WorkOrder_id`)  ";							
			query += "  WHERE  CONCAT(DATE_FORMAT(info.transaction_date, '%Y%m%d'), LPAD(info.Sequence_Id, 2, '0')) =  ("
							+ " SELECT MAX(CONCAT(DATE_FORMAT(info1.transaction_date, '%Y%m%d'), LPAD(info1.Sequence_Id, 2, '0')))  FROM workorder_detail_info info1 "
							+ " WHERE info.WorkOrder_id = info1.WorkOrder_id  AND info1.transaction_date <= CURRENT_DATE()  ) "
						+ " AND info.is_active = 'Y' and parent.`Customer_Id`= "+customerId+"  AND  parent.`Company_Id` = "+companyId+" "
						//+ " AND info.Job_Type = 'ALL' AND info.Job_Category='Skilled' AND info.Job_Code_Id = 0"
						+ " ORDER BY  info.Work_Order_Name" ;
			
			List tempList =  sessionFactory.getCurrentSession().createSQLQuery(query).list();					
			for(Object o : tempList){				
				Object[] obj =  (Object[]) o;				
				workOrder.add(new SimpleObject((Integer)obj[0], obj[1]+""));
			}
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return workOrder;	
	}

	@Override
	public AssignShiftsVo getDeptAntPlantDropDown(Integer customerId, Integer companyId, Integer workOrderId){
		Session session = sessionFactory.getCurrentSession();
		AssignShiftsVo shiftsVo = new AssignShiftsVo();
		try{
			/*JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(jsonString);*/
			
			String query = " SELECT  wdi.department,wdi.location,location.Location_Name,CASE wdi.department WHEN 0 THEN 'All' ELSE dept.Department_Name END AS dept   FROM  `workrorder_detail` wd  INNER JOIN workorder_detail_info wdi ON (wd.`WorkOrder_id` = wdi.`WorkOrder_id`)  INNER JOIN location_details_info  location  ON( location.Location_Id = wdi.location)   LEFT JOIN department_details_info dept ON (dept.Department_Id = wdi.department AND CONCAT(DATE_FORMAT(dept.transaction_date, '%Y%m%d'), dept.`Department_Sequence_Id`) =  ( SELECT MAX(CONCAT(DATE_FORMAT(dept1.transaction_date,'%Y%m%d'), dept1.`Department_Sequence_Id`)) FROM department_details_info dept1 WHERE  dept.`Department_Id` = dept1.`Department_Id` AND dept1.transaction_date <= CURRENT_DATE()  ))  WHERE    CONCAT(DATE_FORMAT(wdi.transaction_date, '%Y%m%d'), wdi.`Sequence_Id`) =     ( SELECT MAX(CONCAT(DATE_FORMAT(wdi1.transaction_date, '%Y%m%d'), wdi1.`Sequence_Id`))    FROM workorder_detail_info wdi1 WHERE wdi.`WorkOrder_id` = wdi1.`WorkOrder_id`     AND wdi1.transaction_date <= CURRENT_DATE() )   AND CONCAT(DATE_FORMAT(location.transaction_date, '%Y%m%d'), location.`Location_Sequence_Id`) =  ( SELECT MAX(CONCAT(DATE_FORMAT(location1.transaction_date, '%Y%m%d'), location1.`Location_Sequence_Id`))  FROM location_details_info location1  WHERE   location.`Location_Id` = location1.`Location_Id` AND location1.transaction_date <= CURRENT_DATE()  ) ";
			
			query += " AND  wd.customer_id="+customerId+"  AND  wd.company_id="+companyId+" AND  wd.`WorkOrder_id`="+workOrderId;
			
			/*if(!(jo.get("customerId").getAsString().equalsIgnoreCase("null")) && !(jo.get("customerId").getAsString().equalsIgnoreCase("null")) && jo.get("customerId").getAsInt() > 0){
				query += " AND  wd.customer_id="+jo.get("customerId").getAsInt();
            }
			if(!(jo.get("companyId").getAsString().equalsIgnoreCase("null")) && !(jo.get("companyId").getAsString().equalsIgnoreCase("null")) && jo.get("companyId").getAsInt() > 0){
				query += " AND  wd.company_id="+jo.get("companyId").getAsInt();
			}			
			if(!(jo.get("workOrderId").getAsString().equalsIgnoreCase("null")) && !(jo.get("workOrderId").getAsString().equalsIgnoreCase("null")) && jo.get("workOrderId").getAsInt() > 0){
				query += " AND  wd.`WorkOrder_id`="+jo.get("workOrderId").getAsString();
            }*/
											
			List tempList = session.createSQLQuery(query).list();			
			for(Object o : tempList){
				Object[] obj = (Object[]) o;	
				shiftsVo.setDepartmentId((Integer)obj[0]);
				shiftsVo.setLocationId((Integer)obj[1]);
				shiftsVo.setDepartmentName((String)obj[3]);
				shiftsVo.setLocationName((String)obj[2]);			
			}	
		
			String plantquery = "SELECT plant.`Plant_Id`,info.`Plant_Name` FROM plant_details_info info INNER JOIN plant_details plant ON plant.Plant_Id = info.Plant_Id WHERE CONCAT(DATE_FORMAT(info.transaction_date, '%Y%m%d'), LPAD(info.`Plant_Sequence_Id`, 2, '0')) =  (  SELECT MAX(CONCAT(DATE_FORMAT(info1.transaction_date, '%Y%m%d'), LPAD(info1.`Plant_Sequence_Id`, 2, '0'))) FROM plant_details_info info1 "
					+ " WHERE info.Plant_Id = info1.Plant_Id  AND info1.transaction_date <= CURRENT_DATE()  ) "		
					+ " AND location_id ="+shiftsVo.getLocationId();
			
			List planttempList = session.createSQLQuery(plantquery).list();
			
			for(Object o : planttempList){
				Object[] obj = (Object[]) o;			
				shiftsVo.getPlantList().add(new SimpleObject((Integer)obj[0], obj[1]+""));		
			}			
			
		}catch(Exception e){
			log.error("Error Occured ",e);
		}	
		return shiftsVo;
	}
	
	
	
	
	
	@Override
	public List<SimpleObject> getPlantDropDownByLocationId(Integer locationId){
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> plantVoList = new ArrayList<SimpleObject>();
		try{
					
			String plantquery = "SELECT plant.`Plant_Id`,info.`Plant_Name` FROM plant_details_info info INNER JOIN plant_details plant ON plant.Plant_Id = info.Plant_Id WHERE CONCAT(DATE_FORMAT(info.transaction_date, '%Y%m%d'), LPAD(info.`Plant_Sequence_Id`, 2, '0')) =  (  SELECT MAX(CONCAT(DATE_FORMAT(info1.transaction_date, '%Y%m%d'), LPAD(info1.`Plant_Sequence_Id`, 2, '0'))) FROM plant_details_info info1 "
					+ " WHERE info.Plant_Id = info1.Plant_Id  AND info1.transaction_date <= CURRENT_DATE()  ) "		
					+ " AND location_id ="+locationId;
			
			List planttempList = session.createSQLQuery(plantquery).list();
			
			for(Object o : planttempList){
				Object[] obj = (Object[]) o;			
				SimpleObject vo = new SimpleObject();
				vo.setId((Integer)obj[0]);
				vo.setName(obj[1]+"");
				plantVoList.add(vo);
			}			
			
		}catch(Exception e){
			log.error("Error Occured ",e);
		}	
		return plantVoList;
	}
	
	
	
	
	
	/*
	 * This method will be used to get the wage rates  list based on given customerId, companyId and vendorId
	 */
	@Override
	public List<SimpleObject> getWageRateDropDown(Integer customerId, Integer companyId, Integer vendorId, Integer jobCode, String jobCategory, String jobType) {
		System.out.println("JobCode : "+jobCode+", job category : "+jobCategory+", JobType: "+jobType);
		List<SimpleObject> workOrder = new ArrayList<SimpleObject>(); 
		try {
			String query = "SELECT wage.Wage_Rate_Id, CONCAT(wage.Wage_Rate_Code,' - ',info.Wage_Rate_Name) FROM wage_rate_details_info info "
							+ " LEFT JOIN wage_rate_details wage ON wage.Wage_Rate_Id = info.Wage_Rate_Id "
							+ " WHERE  CONCAT(DATE_FORMAT(info.Transaction_Date, '%Y%m%d'), LPAD(info.Wage_Rate_Sequence_Id, 2, '0')) =  ("
									+ " SELECT MAX(CONCAT(DATE_FORMAT(info1.Transaction_Date, '%Y%m%d'), LPAD(info1.Wage_Rate_Sequence_Id, 2, '0')))  FROM wage_rate_details_info info1 "
									+ " WHERE info1.Wage_Rate_Id = info.Wage_Rate_Id   AND info1.Transaction_Date <= CURRENT_DATE()  ) "
							+ " AND info.Customer_Id = "+customerId
							+ " AND info.Company_Id = "+companyId
							+ " AND info.Vendor_Id = "+vendorId
							+ " AND info.Status = 'Y' ";
			
			/*if(jobCode != null && jobCode == 0 && jobCategory != null && !jobCategory.isEmpty() && jobCategory.equalsIgnoreCase("Skilled") && jobType != null && !jobType.isEmpty() && jobType.equalsIgnoreCase("ALL") ){
				query += " AND info.Job_Category = '"+jobCategory+"' ";
			}else{*/
			
				if(jobCode != null && jobCode > 0){
					query += " AND info.Job_Code_Id = "+jobCode;
				}
				
				if(jobCategory != null && !jobCategory.isEmpty()){
					query += " AND info.Job_Category = '"+jobCategory+"' ";
				}
								
				if(jobType != null && !jobType.isEmpty() && !jobType.equalsIgnoreCase("ALL")){
					query += " AND info.Job_Type = '"+jobType+"' ";
				}
			//}
			
			List tempList =  sessionFactory.getCurrentSession().createSQLQuery(query).list();					
			for(Object o : tempList){				
				Object[] obj =  (Object[]) o;				
				workOrder.add(new SimpleObject((Integer)obj[0], obj[1]+""));
			}
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return workOrder;	
	}
	
	/*
	 * This method will be used to get the location  list based on given customerId, companyId and workOrderId
	 */
	/*@Override
	public List<SimpleObject> getLocationsDropdown(Integer customerId, Integer companyId, Integer workOrderId) {
		List<SimpleObject> location = new ArrayList<SimpleObject>(); 
		try {
			String query = " SELECT work.Location, CONCAT(location.Location_Code,' - ',info.Location_Name) FROM workorder_details_info work "
							+ " LEFT JOIN location_details_info  info ON info.Location_Id = work.Location AND "
									+ " CONCAT(DATE_FORMAT(info.Transaction_Date, '%Y%m%d'), LPAD(info.Location_Sequence_Id, 2, '0')) =  ("
									+ " SELECT MAX(CONCAT(DATE_FORMAT(info1.Transaction_Date, '%Y%m%d'), LPAD(info1.Location_Sequence_Id, 2, '0')))  FROM location_details_info info1 "
									+ " WHERE info1.Location_Id = info.Location_Id   AND info1.Transaction_Date <= CURRENT_DATE()  ) "
							+ " LEFT JOIN location_details location ON location.Location_Id = work.Location "
							+ " WHERE  CONCAT(DATE_FORMAT(work.Transaction_Date, '%Y%m%d'), LPAD(work.Sequence_Id, 2, '0')) =  ("
									+ " SELECT MAX(CONCAT(DATE_FORMAT(work1.Transaction_Date, '%Y%m%d'), LPAD(work1.Sequence_Id, 2, '0')))  FROM workorder_details_info work1 "
									+ " WHERE work1.WorkOrder_id = work.WorkOrder_id   AND work1.Transaction_Date <= CURRENT_DATE()  ) "
							+ " AND work.Customer_Id = "+customerId
							+ " AND work.Company_Id = "+companyId
							+ " AND work.WorkOrder_id = "+workOrderId
							+ " AND work.Is_Active = 'Y' "
							+ " AND info.Status = 'Y' ";
			
			List tempList =  sessionFactory.getCurrentSession().createSQLQuery(query).list();					
			for(Object o : tempList){				
				Object[] obj =  (Object[]) o;				
				location.add(new SimpleObject((Integer)obj[0], obj[1]+""));
			}
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return location;	
	}*/

	/*
	 * This method will be used to get the department  list based on given customerId, companyId and workOrderId
	 */
	/*@Override
	public List<SimpleObject> getDepartmentsDropdown(Integer customerId, Integer companyId, Integer workOrderId) {
		List<SimpleObject> location = new ArrayList<SimpleObject>(); 
		try {
			String query = " SELECT work.Department, CONCAT(department.Department_Code,' - ',info.Department_Name) FROM workorder_details_info work "
							+ " LEFT JOIN department_details_info  info ON info.Department_Id = work.Department AND "
									+ " CONCAT(DATE_FORMAT(info.Transaction_Date, '%Y%m%d'), LPAD(info.Department_Sequence_Id, 2, '0')) =  ("
									+ " SELECT MAX(CONCAT(DATE_FORMAT(info1.Transaction_Date, '%Y%m%d'), LPAD(info1.Department_Sequence_Id, 2, '0')))  FROM department_details_info info1 "
									+ " WHERE info1.Department_Id = info.Department_Id   AND info1.Transaction_Date <= CURRENT_DATE()  ) "
							+ " LEFT JOIN department_details department ON department.Department = work.Department "
							+ " WHERE  CONCAT(DATE_FORMAT(work.Transaction_Date, '%Y%m%d'), LPAD(work.Sequence_Id, 2, '0')) =  ("
									+ " SELECT MAX(CONCAT(DATE_FORMAT(work1.Transaction_Date, '%Y%m%d'), LPAD(work1.Sequence_Id, 2, '0')))  FROM workorder_details_info work1 "
									+ " WHERE work1.WorkOrder_id = work1.WorkOrder_id   AND work1.Transaction_Date <= CURRENT_DATE()  ) "
							+ " AND work.Customer_Id = "+customerId
							+ " AND work.Company_Id = "+companyId
							+ " AND work.WorkOrder_id = "+workOrderId
							+ " AND work.Is_Active = 'Y' "
							+ " AND info.Is_Active = 'Y' ";
			
			List tempList =  sessionFactory.getCurrentSession().createSQLQuery(query).list();					
			for(Object o : tempList){				
				Object[] obj =  (Object[]) o;				
				location.add(new SimpleObject((Integer)obj[0], obj[1]+""));
			}
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return location;	
	}*/
}
