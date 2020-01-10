package com.Ntranga.CLMS.Dao;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Ntranga.CLMS.vo.JobAllocationByVendorVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.WorkerDetailsVo;
import com.Ntranga.core.DateHelper;
import common.Logger;

@SuppressWarnings({"rawtypes","unused"})
@Transactional
@Repository("jobAllocationByVendorDao")
public class JobAllocatioByVendorDaoImpl implements JobAllocationByVendorDao  {

	private static Logger log = Logger.getLogger(JobAllocatioByVendorDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
    private HttpServletRequest request;

	private @Value("#{db['db.schemaName']}")
	String dbSchemaName;
	

	@Override
	public List<WorkerDetailsVo> workerGridDetails(String customerId,String companyId,String vendorId,String status,String workerCode, String workerName,String workerId) {
		Session session = sessionFactory.getCurrentSession();
		List<WorkerDetailsVo> workerDetailsList = new ArrayList<WorkerDetailsVo>();
			try {			
			 String q = " SELECT distinct cdi.customer_name, "
								+"  com.company_name, "
								+" vdi.vendor_name, "
								+" replace((CONCAT(RTRIM(wdi.first_name), ' ',CASE WHEN (wdi.middle_name IS NULL) THEN '' ELSE CONCAT(RTRIM(wdi.middle_name),' ') END, wdi.last_name)),'  ',' ') AS Worker, "
								+" wd.worker_code, "
								+" CASE wdi.is_active WHEN 'Y' THEN 'Active' ELSE CASE wdi.is_active  WHEN 'I' THEN 'New' ELSE 'In-Active' END END AS STATUS,wdi.`Worker_info_id`,wdi.shift_name,wdi.weekly_off,"
								+" vd.vendor_code, wjd.Work_Skill, CASE WHEN wjd.Job_Name = 0 THEN 'ALL' ELSE job.Job_Name END "
								+" FROM worker_details wd  "
								+" INNER JOIN worker_details_info wdi ON(wd.worker_id = wdi.worker_id) "
								+" INNER JOIN work_job_details wjd ON (wd.worker_id = wjd.worker_id)"
								+" INNER JOIN `customer_details_info` cdi ON(wd.customer_id =cdi.customer_id) "
								+" INNER JOIN `company_details_info` com ON (com.company_id = wd.company_id) "
								+" INNER JOIN `vendor_details` vd ON(vd.vendor_id = wd.vendor_id) "
								+" INNER JOIN `vendor_details_info` vdi ON(vdi.vendor_id = wd.vendor_id) "
								+" INNER JOIN job_code_details_info job ON job.Job_Code_Id = wjd.Job_Name "
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
								+" ) "
								+" AND  CONCAT(DATE_FORMAT(wjd.transaction_date, '%Y%m%d'), wjd.Sequence_Id) = "
								+" ( "
								+" SELECT MAX(CONCAT(DATE_FORMAT(wjd1.transaction_date, '%Y%m%d'), wjd1.Sequence_Id)) "
								+" FROM work_job_details wjd1 "
								+" WHERE wjd.`Worker_Id` = wjd1.`Worker_Id`  AND wjd1.`Company_Id` = wjd.`Company_Id`  AND wjd1.`Customer_Id` = wjd.customer_id "
								+"  AND wjd1.transaction_date <= CURRENT_DATE() "
								+" ) "
								+" AND CONCAT(DATE_FORMAT(job.transaction_date, '%Y%m%d'), LPAD(job.job_code_Sequence_Id, 2, '0')) =   "
								+" (  "   
							    +" SELECT MAX(CONCAT(DATE_FORMAT(job1.transaction_date, '%Y%m%d'), LPAD(job1.job_code_Sequence_Id, 2, '0')))  "  
								+" FROM job_code_details_info job1  " 
								+" WHERE  job.Job_Code_Id = job1.Job_Code_Id  " 
								+" AND job1.transaction_date <= CURRENT_DATE() )  ";
				
			 			
			 			
					 if(customerId != null && !customerId.isEmpty()){						
						 q +=" and cdi.Customer_Id = '"+customerId+"'";				 
					 } 
					 if(companyId != null && !companyId.isEmpty()  ){						 
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
						 q +=" and replace((CONCAT(RTRIM(wdi.first_name), ' ',CASE WHEN (wdi.middle_name IS NULL) THEN '' ELSE CONCAT(RTRIM(wdi.middle_name),' ') END, wdi.last_name)),'  ',' ') like '%"+workerName+"%' ";
					 }
					 
					 if(workerId != null && !workerId.isEmpty() && !workerId.equalsIgnoreCase("null")){
						 q +=" and wd.worker_id = '"+workerId+"' ";
					 }
					 
					 q += " ORDER BY Worker asc";
								 
			
			 List tempList = session.createSQLQuery(q).list();
			
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
				workerVo.setVendorCode(obj[9]+"");
				workerVo.setWorkSkill(obj[10]+"");
				workerVo.setJobName(obj[11]+"");
				workerDetailsList.add(workerVo);
			}						
		} catch (Exception e) {
			log.error("Error Occured ",e);

		}
		
		
		return workerDetailsList;
	}
	

	
	@Override
	public List<JobAllocationByVendorVo> searchJobAllocation(String customerId,String companyId,String vendorId,String status,String workerCode, String workerName,String workerId, java.sql.Date selectedDate, String searchFor, String schemaName) {
		log.info("Entered in searchJobAllocationForApproval ::::  Customer Id : "+customerId+", CompanyId : "+companyId+", VendorId : "+vendorId+" ,Status : "+status+", Worker Code : "+workerCode+",  WorkerName : "+workerName+", WorkerId : "+ workerId+", selectedDate = "+selectedDate);
		Session session = sessionFactory.getCurrentSession();
		List<JobAllocationByVendorVo> workerDetailsList = new ArrayList<JobAllocationByVendorVo>();
		String query = null;
			try {		
				if(schemaName == null || schemaName.isEmpty()){
					schemaName = dbSchemaName;
				}
				if(searchFor.equalsIgnoreCase("JobApproval")){
					 query = " SELECT ContractorCode, ltr.ContractorName, Emp, ltr.Emp_Name, ltr.Shift,  ltr.Work_Skill,  "
							+ " ltr.Designation, pdi.Plant_Name, ldi.Location_Name, wadi.Work_Area_Name, sdi.Section_Name, ddi.Department_Name "
							+ " FROM "+schemaName+".labor_time_report ltr"
							//+ " INNER JOIN labor_scheduled_shifts lss ON ltr.Emp = lss.Emp_Code "
							+ " INNER JOIN v_worker_details vw ON ltr.Emp = vw.Worker_Code "
							+ " LEFT JOIN plant_details_info pdi ON pdi.Plant_Id = ltr.Plant_Id "
													+" AND  CONCAT(DATE_FORMAT(pdi.Transaction_date, '%Y%m%d'), LPAD(pdi.Plant_Sequence_Id, 2, '0')) =  "
													+" ( "
													+" SELECT MAX(CONCAT(DATE_FORMAT(pdi1.Transaction_date, '%Y%m%d'), LPAD(pdi1.Plant_Sequence_Id, 2, '0'))) "
													+" FROM plant_details_info pdi1 "
													+" WHERE pdi.`Plant_Id` = pdi1.`Plant_Id` "
													+"  AND pdi1.transaction_date <= CURRENT_DATE()"
													+" ) "
							+ " LEFT JOIN location_details_info ldi ON ldi.Location_Id = ltr.Location_Id "
													+" AND  CONCAT(DATE_FORMAT(ldi.Transaction_date, '%Y%m%d'), LPAD(ldi.Location_Sequence_Id, 2, '0')) =  "
													+" ( "
													+" SELECT MAX(CONCAT(DATE_FORMAT(ldi1.Transaction_date, '%Y%m%d'), LPAD(ldi1.Location_Sequence_Id, 2, '0'))) "
													+" FROM location_details_info ldi1 "
													+" WHERE ldi.`Location_Id` = ldi1.`Location_Id` "
													+"  AND ldi1.transaction_date <= CURRENT_DATE()"
													+" ) "
							+ " LEFT JOIN department_details_info ddi ON ddi.Department_Id = ltr.Department_Id "
													+" AND  CONCAT(DATE_FORMAT(ddi.Transaction_date, '%Y%m%d'), LPAD(ddi.Department_Sequence_Id, 2, '0')) =  "
													+" ( "
													+" SELECT MAX(CONCAT(DATE_FORMAT(ddi1.Transaction_date, '%Y%m%d'), LPAD(ddi1.Department_Sequence_Id, 2, '0'))) "
													+" FROM department_details_info ddi1 "
													+" WHERE ddi.`Department_Id` = ddi1.`Department_Id` "
													+"  AND ddi1.transaction_date <= CURRENT_DATE()"
													+" ) "		
							+ " LEFT JOIN section_details_info sdi ON sdi.Section_Id = ltr.Section_Id "
													+" AND  CONCAT(DATE_FORMAT(sdi.Transaction_date, '%Y%m%d'), LPAD(sdi.Sequence_Id, 2, '0')) =  "
													+" ( "
													+" SELECT MAX(CONCAT(DATE_FORMAT(sdi1.Transaction_date, '%Y%m%d'), LPAD(sdi1.Sequence_Id, 2, '0'))) "
													+" FROM section_details_info sdi1 "
													+" WHERE sdi.`Section_Id` = sdi1.`Section_Id` "
													+"  AND sdi1.transaction_date <= CURRENT_DATE()"
													+" ) "
							
							+ " LEFT JOIN work_area_details_info wadi ON wadi.Work_Area_Id = ltr.Work_Area_Id "
													+" AND  CONCAT(DATE_FORMAT(wadi.Transaction_date, '%Y%m%d'), LPAD(wadi.Work_Area_Sequence_Id, 2, '0')) =  "
													+" ( "
													+" SELECT MAX(CONCAT(DATE_FORMAT(wadi1.Transaction_date, '%Y%m%d'), LPAD(wadi1.Work_Area_Sequence_Id, 2, '0'))) "
													+" FROM work_area_details_info wadi1 "
													+" WHERE wadi.`Work_Area_Id` = wadi1.`Work_Area_Id` "
													+"  AND wadi1.transaction_date <= CURRENT_DATE()"
													+" ) ";
					}else{
						query = " SELECT ContractorCode, ContractorName, Emp, Emp_Name, Shift,  Work_Skill,  "
								+ " ltr.Designation "
								+ " FROM  "+schemaName+".labor_time_report ltr"
								+ " INNER JOIN v_worker_details vw ON ltr.Emp = vw.Worker_Code ";
					}
						query += " WHERE 1 = 1  AND ltr.Intime IS NOT NULL ";
			 			
			 			
					 if(customerId != null && !customerId.isEmpty()){						
						 query +=" AND vw.Customer_Id = '"+customerId+"'";				 
					 } 
					 if(companyId != null && !companyId.isEmpty()  ){						 
						 query +=" AND vw.Company_Id = '"+companyId+"' ";	
					 } 
					 if(vendorId != null && !vendorId.isEmpty() ){						 
						 query +=" AND vw.Vendor_Id = '"+vendorId+"'";	
					 }
					 
					 if(workerId != null && !workerId.isEmpty() && !workerId.equalsIgnoreCase("null") && !workerId.equalsIgnoreCase("0") ){
						 query +=" AND vw.Worker_Id = '"+workerCode+"' ";
					 }
					 
					 if(status != null && !status.isEmpty() && !status.equalsIgnoreCase("Unassigned")){
						 query += " AND ltr.Allocation_Status =  '"+status+"' ";
					 }else{
						 query += " AND ( ltr.Allocation_Status = 'Unassigned'  OR ltr.Allocation_Status IS NULL ) ";
					 }
					 
					 if(workerCode != null && !workerCode.isEmpty()){
						 query +=" AND ltr.Emp LIKE '"+workerCode+"%' ";
					 }
					 if(workerName != null && !workerName.isEmpty()){
						 query +=" AND ltr.Emp_Name LIKE '%"+workerName+"%' ";
					 }
					 
					 if(selectedDate != null ){
						 query +=" AND ltr.Business_Date = '"+selectedDate+"' ";
					 }
					 
					 query += "GROUP BY ltr.Emp, ltr.Business_Date ORDER BY ltr.Emp_Name asc";
								 
			
			 List tempList = session.createSQLQuery(query).list();
			
			for(Object o : tempList){
				Object[] obj = (Object[]) o;
				JobAllocationByVendorVo workerVo = new JobAllocationByVendorVo();
				workerVo.setVendorCode(obj[0]+"");
				workerVo.setVendorName((String)obj[1]);
				workerVo.setWorkerCode(obj[2]+"");
				workerVo.setWorkerName((String)obj[3]);
				if(obj[4] != null){
					workerVo.setShiftName((String)obj[4]);
				}				
				workerVo.setWorkSkill((String)obj[5]);
				workerVo.setJobName((String)obj[6]);
				if(searchFor.equalsIgnoreCase("JobApproval")){
					workerVo.setPlantName((String)obj[7]);
					workerVo.setLocationName((String)obj[8]);
					workerVo.setWorkAreaName((String)obj[9]);
					workerVo.setSectionName((String)obj[10]);
					workerVo.setDepartmentName((String)obj[11]);
				}
				workerDetailsList.add(workerVo);
			}						
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return workerDetailsList;
	}
	
	
	@Override
	public List<SimpleObject> getDepartmentsList(Integer customerId,Integer CompanyId) {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> departmentList = new ArrayList<>();
		try {
			List workerTempList = session.createSQLQuery(" SELECT ddi.department_id, ddi.department_name FROM department_details dd "+
															" INNER JOIN `department_details_info` ddi ON(dd.department_id = ddi.department_id) "+
															" WHERE "+
															" CONCAT(DATE_FORMAT(ddi.transaction_date, '%Y%m%d'), LPAD(ddi.department_Sequence_Id, 2, '0')) =   ( "+
															"   SELECT "+
															"  MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, "+
															"    '%Y%m%d'), "+
															"     LPAD(vdi1.department_Sequence_Id, "+
															"       2, "+
															"        '0')))   "+
															"   FROM "+
															"    department_details_info vdi1   "+
															"   WHERE "+
															"        ddi.department_id = vdi1.department_id  "+
															"        AND vdi1.transaction_date <= CURRENT_DATE()  "+
															"  )  "+
															"   AND  "+
															"    ddi.customer_id = '"+customerId+"' AND ddi.company_id = '"+CompanyId+"' and ddi.is_active= 'Y'").list();
			for (Object customerObject  : workerTempList) {
				Object[] obj = (Object[]) customerObject;
				SimpleObject object = new SimpleObject();
				object.setId((Integer)obj[0]);
				object.setName(obj[1]+"");
				departmentList.add(object);
			}			
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return departmentList;

	}
	
	@Override
	public List<SimpleObject> getlocationsList(String customerId,String CompanyId) {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> locationList = new ArrayList<>();
		try {
			List workerTempList = session.createSQLQuery(" SELECT ddi.location_id, ddi.location_name FROM location_details dd "+
														" 	INNER JOIN `location_details_info` ddi ON(dd.location_id = ddi.location_id) "+
														" 	WHERE "+
														" CONCAT(DATE_FORMAT(ddi.transaction_date, '%Y%m%d'), LPAD(ddi.location_Sequence_Id, 2, '0')) =   ( "+
														" SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date,'%Y%m%d'),LPAD(vdi1.location_Sequence_Id,2,'0')))   "+
														" FROM location_details_info vdi1  WHERE ddi.location_id = vdi1.location_id AND vdi1.transaction_date <= CURRENT_DATE())  "+
														" AND ddi.customer_id = '"+customerId+"' AND ddi.company_id = '"+CompanyId+"' and ddi.status='Y'").list();
			for (Object customerObject  : workerTempList) {
				Object[] obj = (Object[]) customerObject;
				SimpleObject object = new SimpleObject();
				object.setId((Integer)obj[0]);
				object.setName(obj[1]+"");
				locationList.add(object);
			}			
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return locationList;

	}
	
	
	@Override
	public List<SimpleObject> getAllPlantsListByLocationId(String locationId) {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> plantsList = new ArrayList<>();
		try {
			List workerTempList = session.createSQLQuery(" SELECT ddi.plant_id, ddi.plant_name FROM plant_details dd "+
															" INNER JOIN `plant_details_info` ddi ON(dd.plant_id = ddi.plant_id) "+
															" WHERE "+
															" CONCAT(DATE_FORMAT(ddi.transaction_date, '%Y%m%d'), LPAD(ddi.plant_Sequence_Id, 2, '0')) =   ( "+
															" SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date,'%Y%m%d'),LPAD(vdi1.plant_Sequence_Id,2,'0')))   "+
															" FROM plant_details_info vdi1  WHERE ddi.plant_id = vdi1.plant_id AND vdi1.transaction_date <= CURRENT_DATE())  "+
															" AND ddi.location_id in ("+locationId +") and ddi.status= 'Y'").list();
			for (Object customerObject  : workerTempList) {
				Object[] obj = (Object[]) customerObject;
				SimpleObject object = new SimpleObject();
				object.setId((Integer)obj[0]);
				object.setName(obj[1]+"");
				plantsList.add(object);
			}			
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return plantsList;
	}


	@Override
	public List<SimpleObject> getAllSectionesByLocationAndPlant(String locationId,String plantId) {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> sectionList = new ArrayList<>();
		try {
			List workerTempList = session
					.createSQLQuery(" SELECT ddi.section_id,ddi.section_name FROM section_details dd "
							+ " INNER JOIN section_details_info ddi ON(dd.section_id = ddi.section_id) " + " WHERE "
							+ " CONCAT(DATE_FORMAT(ddi.transaction_date, '%Y%m%d'), LPAD(ddi.Sequence_Id, 2, '0')) =   (  "
							+ " SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date,'%Y%m%d'),LPAD(vdi1.Sequence_Id,2,'0')))   "
							+ " FROM section_details_info vdi1  WHERE ddi.section_id = vdi1.section_id AND vdi1.transaction_date <= CURRENT_DATE()) "
							+ " AND ddi.location_id in ("+locationId+") AND ddi.plant_id in("+plantId+") AND ddi.status = 'Y' ")
					.list();
			
			for (Object customerObject  : workerTempList) {
				Object[] obj = (Object[]) customerObject;
				SimpleObject object = new SimpleObject();
				object.setId((Integer)obj[0]);
				object.setName(obj[1]+"");
				sectionList.add(object);
			}			
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return sectionList;
	}

	
	
	@Override
	public List<SimpleObject> getAllWorkAreasBySectionesAndLocationAndPlant(String customerId,String companyId,String locationId, String plantId, String sectionId, String departmentId){
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> workAreaList = new ArrayList<>();
		try {
			List workerTempList = session
					.createSQLQuery(" SELECT ddi.work_area_id,ddi.work_area_name FROM work_area_details dd "+
									" INNER JOIN work_area_details_info ddi ON(dd.work_area_id = ddi.work_area_id) "+
									" WHERE "+
									" CONCAT(DATE_FORMAT(ddi.transaction_date, '%Y%m%d'), LPAD(ddi.work_area_Sequence_Id, 2, '0')) =   ( "+
									" SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date,'%Y%m%d'),LPAD(vdi1.work_area_Sequence_Id,2,'0')))   "+
									" FROM work_area_details_info vdi1  WHERE ddi.work_area_id = vdi1.work_area_id AND vdi1.transaction_date <= CURRENT_DATE()) "+
									" AND ddi.Customer_Id = "+customerId+" AND ddi.Company_Id = "+companyId+" AND ddi.Department_Id IN( "+departmentId+ 
									") AND ddi.location_id in ("+locationId+") AND ddi.plant_id in ("+plantId+")  AND ddi.section_id in ("+sectionId+") AND ddi.status = 'Y' ")
									.list();			
			
			for (Object customerObject  : workerTempList) {
				Object[] obj = (Object[]) customerObject;
				SimpleObject object = new SimpleObject();
				object.setId((Integer)obj[0]);
				object.setName(obj[1]+"");
				workAreaList.add(object);
			}			
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return workAreaList;
	}
	
	
	
	
	public int returnCountOfJobSkillAndDesignation(String jobSkill,String Designation,List<JobAllocationByVendorVo> workersList){		
		int i = 0;
		for(JobAllocationByVendorVo  workerVo :workersList){
			if(workerVo.isSelected()){					
				if(jobSkill.equalsIgnoreCase(workerVo.getJobName()) && Designation.equalsIgnoreCase(workerVo.getWorkSkill())){
					i++;
				}
			}
		
		}
		
		
		
		return i;
	}

	@Override
	public SimpleObject saveJobAllocationDetails(JobAllocationByVendorVo jobAllocationVo,boolean callMethod) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		JobAllocationByVendorVo workerDetails = new JobAllocationByVendorVo(); 	
		List<SimpleObject> simpleObj = new ArrayList<SimpleObject>();
		boolean newFlag = true;
		SimpleObject errorDisplay = new SimpleObject();
		try {
			
			List<SimpleObject> manPowerCountCheckList = this.getWorkersCountFromManpower(jobAllocationVo);			
			if(callMethod){
				if(manPowerCountCheckList != null && manPowerCountCheckList.size() > 0){
						for(JobAllocationByVendorVo  workerVo :jobAllocationVo.getWorkerList()){
							
							if(workerVo.isSelected()){	
								int i =0;
								boolean flag = false;
								for(SimpleObject ss :simpleObj){
									if(ss.getName().equalsIgnoreCase(workerVo.getJobName()) && ss.getDesc().equalsIgnoreCase(workerVo.getWorkSkill()) ){
										i = ss.getId();
										ss.setId(ss.getId()+1);
										flag = true;
									}
								}
								if(flag){
									
								}else
									simpleObj.add(new SimpleObject(i+1, workerVo.getJobName(), workerVo.getWorkSkill()));
							}
						}
						
						for(SimpleObject ss :simpleObj){
							for(SimpleObject  so :manPowerCountCheckList){
								 newFlag = true;
								if(ss.getDesc().equalsIgnoreCase(so.getName()) && so.getId() >= ss.getId() ){
									newFlag = false;
								}
							}
							
							if(newFlag){								
								errorDisplay.setId(1);	
								errorDisplay.setDesc("Should not able to allocate as the allocation workers exceeds manpower requisition workers Limit");
								return errorDisplay;
							}
						}
						
						if(jobAllocationVo.getWorkerList() != null && jobAllocationVo.getWorkerList().size() > 0 && jobAllocationVo.getStatus() != null && jobAllocationVo.getStatus().equalsIgnoreCase("Unassigned") ){			
							for(JobAllocationByVendorVo  workerVo :jobAllocationVo.getWorkerList()){
								if(workerVo.isSelected()){			
									log.info("======="+workerVo.getWorkerCode()+":::===="+jobAllocationVo.getSelectedDate()+"  ::::  Shift :"+workerVo.getShiftName()+ "   :::::   "+jobAllocationVo.getSchemaName()+" ::::: "+workerVo.getWorkSkill());
									//Query q11 = session.createSQLQuery("REPLACE INTO labor_scheduled_shifts (Business_Date,CODE,ContractorName, Emp_Code,Emp_Name,Shift,WO,department_id,Location_Id,Created_By,Modified_By,Created_date,Modified_Date,Plant_Id,Section_Id,Work_Area_Id)	VALUES	('"+DateHelper.convertDateToSQLString(jobAllocationVo.getSeletedDate())+"','"+workerVo.getVendorCode()+"','"+workerVo.getVendorName()+"','"+workerVo.getWorkerCode()+"','"+workerVo.getFirstName()+"','"+workerVo.getShiftName()+"','"+workerVo.getWeeklyOff()+"','"+jobAllocationVo.getDepartmentId()+"','"+jobAllocationVo.getLocationId()+"','"+jobAllocationVo.getCreatedBy()+"','"+jobAllocationVo.getCreatedBy()+"','"+DateHelper.convertDateTimeToSQLString(new Date())+"','"+DateHelper.convertDateTimeToSQLString(new Date())+"',"+jobAllocationVo.getPlantId()+","+jobAllocationVo.getSectionId()+","+jobAllocationVo.getWorkAreaId()+")");
									if(workerVo.getShiftName() != null && !workerVo.getShiftName().equalsIgnoreCase("null") && !workerVo.getShiftName().isEmpty() && !workerVo.getShiftName().equalsIgnoreCase("") &&
											workerVo.getJobName() != null && !workerVo.getJobName().equalsIgnoreCase("null") && !workerVo.getJobName().isEmpty() && !workerVo.getJobName().equalsIgnoreCase("") 	&&
											workerVo.getWorkSkill() != null && !workerVo.getWorkSkill().equalsIgnoreCase("null") && !workerVo.getWorkSkill().isEmpty() && !workerVo.getWorkSkill().equalsIgnoreCase("")){
										Query scheduledUpdate = session.createSQLQuery("UPDATE labor_scheduled_shifts lss SET Shift = '"+workerVo.getShiftName()+"' ,Department_Id = "+jobAllocationVo.getDepartmentId()+" ,Location_Id = "+jobAllocationVo.getLocationId()+",Modified_By = "+jobAllocationVo.getCreatedBy()+" ,Modified_Date = '"+DateHelper.convertDateTimeToSQLString(new Date())+"' ,Plant_Id = "+jobAllocationVo.getPlantId()+" ,Section_Id = "+jobAllocationVo.getSectionId()+" ,Work_Area_Id = "+jobAllocationVo.getWorkAreaId()+", Designation = '"+workerVo.getJobName()+"', Work_Skill = '"+workerVo.getWorkSkill()+"' WHERE Business_Date = '"+DateHelper.convertDateToSQLString(jobAllocationVo.getSelectedDate())+"' AND lss.Emp_Code = '"+workerVo.getWorkerCode()+"'");	
										scheduledUpdate.executeUpdate();
										errorDisplay.setId(0);	
										errorDisplay.setDesc("Success");
										
										Query timeReportUpdate = session.createSQLQuery("UPDATE "+jobAllocationVo.getSchemaName()+".labor_time_report ltr SET Shift = '"+workerVo.getShiftName()+"' , Work_Skill = '"+workerVo.getWorkSkill()+"' , Designation = '"+workerVo.getJobName()
													+"' ,Department_Id = "+jobAllocationVo.getDepartmentId()+" ,Department_Name = '"+jobAllocationVo.getDepartmentName()
													+"' ,Location_Id = "+jobAllocationVo.getLocationId()+" ,Location_Name = '"+jobAllocationVo.getLocationName()
													+"' ,Plant_Id = "+jobAllocationVo.getPlantId()+" ,Plant_Name = '"+jobAllocationVo.getPlantName()
													+"' ,Section_Id = "+jobAllocationVo.getSectionId()+" ,Section_Name = '"+jobAllocationVo.getSectionName()
													+"' ,Work_Area_Id = "+jobAllocationVo.getWorkAreaId()+" ,Work_Area_Name = '"+jobAllocationVo.getWorkAreaName()
													+"' , Allocation_Status = 'Assigned' ,Assigned_By = "+jobAllocationVo.getCreatedBy()+" ,Assigned_Date = '"+DateHelper.convertDateTimeToSQLString(new Date())
												+"' WHERE Business_Date = '"+DateHelper.convertDateToSQLString(jobAllocationVo.getSelectedDate())+"' AND ltr.Emp = '"+workerVo.getWorkerCode()+"'");	
										timeReportUpdate.executeUpdate();
										errorDisplay.setId(0);	
										errorDisplay.setDesc("Success");
									}else{
										log.info("======="+workerVo.getWorkerCode()+":::===="+jobAllocationVo.getSelectedDate()+"  ::::  Shift :"+workerVo.getShiftName()+ "   :::::  SchemaName ::  "+jobAllocationVo.getSchemaName()+" ::::: Skill ::  "+workerVo.getWorkSkill()+" ::::: Designation ::  "+workerVo.getJobName());
									}
											
									
								}					
							}
						}else if(jobAllocationVo.getWorkerList() != null && jobAllocationVo.getWorkerList() .size() > 0 && jobAllocationVo.getStatus() != null && jobAllocationVo.getStatus().equalsIgnoreCase("Assigned")){
							for(JobAllocationByVendorVo  workerVo :jobAllocationVo.getWorkerList()){
								if(workerVo.isSelected()){			
									log.info("======="+workerVo.getWorkerCode()+":::===="+jobAllocationVo.getSelectedDate()+"  ::::  Shift :"+workerVo.getShiftName()+ "   :::::   "+jobAllocationVo.getSchemaName()+" ::::: "+workerVo.getWorkSkill());
									//Query q11 = session.createSQLQuery("REPLACE INTO labor_scheduled_shifts (Business_Date,CODE,ContractorName, Emp_Code,Emp_Name,Shift,WO,department_id,Location_Id,Created_By,Modified_By,Created_date,Modified_Date,Plant_Id,Section_Id,Work_Area_Id)	VALUES	('"+DateHelper.convertDateToSQLString(jobAllocationVo.getSeletedDate())+"','"+workerVo.getVendorCode()+"','"+workerVo.getVendorName()+"','"+workerVo.getWorkerCode()+"','"+workerVo.getFirstName()+"','"+workerVo.getShiftName()+"','"+workerVo.getWeeklyOff()+"','"+jobAllocationVo.getDepartmentId()+"','"+jobAllocationVo.getLocationId()+"','"+jobAllocationVo.getCreatedBy()+"','"+jobAllocationVo.getCreatedBy()+"','"+DateHelper.convertDateTimeToSQLString(new Date())+"','"+DateHelper.convertDateTimeToSQLString(new Date())+"',"+jobAllocationVo.getPlantId()+","+jobAllocationVo.getSectionId()+","+jobAllocationVo.getWorkAreaId()+")");
									
									Query timeReportUpdate = session.createSQLQuery("UPDATE "+jobAllocationVo.getSchemaName()+".labor_time_report ltr SET  Allocation_Status = 'Unassigned' ,Modified_By = "+jobAllocationVo.getCreatedBy()+" ,Modified_Date = '"+DateHelper.convertDateTimeToSQLString(new Date())+"' WHERE Business_Date = '"+DateHelper.convertDateToSQLString(jobAllocationVo.getSelectedDate())+"' AND ltr.Emp = '"+workerVo.getWorkerCode()+"'");	
									timeReportUpdate.executeUpdate();
									errorDisplay.setId(0);	
									errorDisplay.setDesc("Success");
								}					
							}
						}else if(jobAllocationVo.getWorkerList() != null && jobAllocationVo.getWorkerList() .size() > 0 && jobAllocationVo.getStatus() != null){
							for(JobAllocationByVendorVo  workerVo :jobAllocationVo.getWorkerList()){
								if(workerVo.isSelected()){			
									log.info("======="+workerVo.getWorkerCode()+":::===="+jobAllocationVo.getSelectedDate()+"  ::::  Shift :"+workerVo.getShiftName()+ "   :::::   "+jobAllocationVo.getSchemaName()+" ::::: "+workerVo.getWorkSkill());
									
									Query timeReportUpdate = session.createSQLQuery("UPDATE "+jobAllocationVo.getSchemaName()+".labor_time_report ltr SET  Allocation_Status = '"+jobAllocationVo.getStatus()+"' ,Approved_By = "+jobAllocationVo.getCreatedBy()+" ,Approved_Date = '"+DateHelper.convertDateTimeToSQLString(new Date())+"' WHERE Business_Date = '"+DateHelper.convertDateToSQLString(jobAllocationVo.getSelectedDate())+"' AND ltr.Emp = '"+workerVo.getWorkerCode()+"'");	
									timeReportUpdate.executeUpdate();
									errorDisplay.setId(0);	
									errorDisplay.setDesc("Success");
								}					
							}
						}
				
				}else{					
					errorDisplay.setId(1);	
					errorDisplay.setDesc("Should not able to allocate as the allocation of manpower requisition workers is not aviailable");
					return errorDisplay;
				}	
			
			}else{
				
				if(jobAllocationVo.getWorkerList() != null && jobAllocationVo.getWorkerList().size() > 0 && jobAllocationVo.getStatus() != null && jobAllocationVo.getStatus().equalsIgnoreCase("Unassigned") ){			
					for(JobAllocationByVendorVo  workerVo :jobAllocationVo.getWorkerList()){
						if(workerVo.isSelected()){			
							log.info("======="+workerVo.getWorkerCode()+":::===="+jobAllocationVo.getSelectedDate()+"  ::::  Shift :"+workerVo.getShiftName()+ "   :::::   "+jobAllocationVo.getSchemaName()+" ::::: "+workerVo.getWorkSkill());
							//Query q11 = session.createSQLQuery("REPLACE INTO labor_scheduled_shifts (Business_Date,CODE,ContractorName, Emp_Code,Emp_Name,Shift,WO,department_id,Location_Id,Created_By,Modified_By,Created_date,Modified_Date,Plant_Id,Section_Id,Work_Area_Id)	VALUES	('"+DateHelper.convertDateToSQLString(jobAllocationVo.getSeletedDate())+"','"+workerVo.getVendorCode()+"','"+workerVo.getVendorName()+"','"+workerVo.getWorkerCode()+"','"+workerVo.getFirstName()+"','"+workerVo.getShiftName()+"','"+workerVo.getWeeklyOff()+"','"+jobAllocationVo.getDepartmentId()+"','"+jobAllocationVo.getLocationId()+"','"+jobAllocationVo.getCreatedBy()+"','"+jobAllocationVo.getCreatedBy()+"','"+DateHelper.convertDateTimeToSQLString(new Date())+"','"+DateHelper.convertDateTimeToSQLString(new Date())+"',"+jobAllocationVo.getPlantId()+","+jobAllocationVo.getSectionId()+","+jobAllocationVo.getWorkAreaId()+")");
							if(workerVo.getShiftName() != null && !workerVo.getShiftName().equalsIgnoreCase("null") && !workerVo.getShiftName().isEmpty() && !workerVo.getShiftName().equalsIgnoreCase("") &&
									workerVo.getJobName() != null && !workerVo.getJobName().equalsIgnoreCase("null") && !workerVo.getJobName().isEmpty() && !workerVo.getJobName().equalsIgnoreCase("") 	&&
									workerVo.getWorkSkill() != null && !workerVo.getWorkSkill().equalsIgnoreCase("null") && !workerVo.getWorkSkill().isEmpty() && !workerVo.getWorkSkill().equalsIgnoreCase("")){
								Query scheduledUpdate = session.createSQLQuery("UPDATE labor_scheduled_shifts lss SET Shift = '"+workerVo.getShiftName()+"' ,Department_Id = "+jobAllocationVo.getDepartmentId()+" ,Location_Id = "+jobAllocationVo.getLocationId()+",Modified_By = "+jobAllocationVo.getCreatedBy()+" ,Modified_Date = '"+DateHelper.convertDateTimeToSQLString(new Date())+"' ,Plant_Id = "+jobAllocationVo.getPlantId()+" ,Section_Id = "+jobAllocationVo.getSectionId()+" ,Work_Area_Id = "+jobAllocationVo.getWorkAreaId()+", Designation = '"+workerVo.getJobName()+"', Work_Skill = '"+workerVo.getWorkSkill()+"' WHERE Business_Date = '"+DateHelper.convertDateToSQLString(jobAllocationVo.getSelectedDate())+"' AND lss.Emp_Code = '"+workerVo.getWorkerCode()+"'");	
								scheduledUpdate.executeUpdate();
								errorDisplay.setId(0);	
								errorDisplay.setDesc("Success");
								
								Query timeReportUpdate = session.createSQLQuery("UPDATE "+jobAllocationVo.getSchemaName()+".labor_time_report ltr SET Shift = '"+workerVo.getShiftName()+"' , Work_Skill = '"+workerVo.getWorkSkill()+"' , Designation = '"+workerVo.getJobName()
											+"' ,Department_Id = "+jobAllocationVo.getDepartmentId()+" ,Department_Name = '"+jobAllocationVo.getDepartmentName()
											+"' ,Location_Id = "+jobAllocationVo.getLocationId()+" ,Location_Name = '"+jobAllocationVo.getLocationName()
											+"' ,Plant_Id = "+jobAllocationVo.getPlantId()+" ,Plant_Name = '"+jobAllocationVo.getPlantName()
											+"' ,Section_Id = "+jobAllocationVo.getSectionId()+" ,Section_Name = '"+jobAllocationVo.getSectionName()
											+"' ,Work_Area_Id = "+jobAllocationVo.getWorkAreaId()+" ,Work_Area_Name = '"+jobAllocationVo.getWorkAreaName()
											+"' , Allocation_Status = 'Assigned' ,Assigned_By = "+jobAllocationVo.getCreatedBy()+" ,Assigned_Date = '"+DateHelper.convertDateTimeToSQLString(new Date())
										+"' WHERE Business_Date = '"+DateHelper.convertDateToSQLString(jobAllocationVo.getSelectedDate())+"' AND ltr.Emp = '"+workerVo.getWorkerCode()+"'");	
								timeReportUpdate.executeUpdate();
								errorDisplay.setId(0);	
								errorDisplay.setDesc("Success");
							}else{
								log.info("======="+workerVo.getWorkerCode()+":::===="+jobAllocationVo.getSelectedDate()+"  ::::  Shift :"+workerVo.getShiftName()+ "   :::::  SchemaName ::  "+jobAllocationVo.getSchemaName()+" ::::: Skill ::  "+workerVo.getWorkSkill()+" ::::: Designation ::  "+workerVo.getJobName());
							}
									
							
						}					
					}
				}else if(jobAllocationVo.getWorkerList() != null && jobAllocationVo.getWorkerList() .size() > 0 && jobAllocationVo.getStatus() != null && jobAllocationVo.getStatus().equalsIgnoreCase("Assigned")){
					for(JobAllocationByVendorVo  workerVo :jobAllocationVo.getWorkerList()){
						if(workerVo.isSelected()){			
							log.info("======="+workerVo.getWorkerCode()+":::===="+jobAllocationVo.getSelectedDate()+"  ::::  Shift :"+workerVo.getShiftName()+ "   :::::   "+jobAllocationVo.getSchemaName()+" ::::: "+workerVo.getWorkSkill());
							//Query q11 = session.createSQLQuery("REPLACE INTO labor_scheduled_shifts (Business_Date,CODE,ContractorName, Emp_Code,Emp_Name,Shift,WO,department_id,Location_Id,Created_By,Modified_By,Created_date,Modified_Date,Plant_Id,Section_Id,Work_Area_Id)	VALUES	('"+DateHelper.convertDateToSQLString(jobAllocationVo.getSeletedDate())+"','"+workerVo.getVendorCode()+"','"+workerVo.getVendorName()+"','"+workerVo.getWorkerCode()+"','"+workerVo.getFirstName()+"','"+workerVo.getShiftName()+"','"+workerVo.getWeeklyOff()+"','"+jobAllocationVo.getDepartmentId()+"','"+jobAllocationVo.getLocationId()+"','"+jobAllocationVo.getCreatedBy()+"','"+jobAllocationVo.getCreatedBy()+"','"+DateHelper.convertDateTimeToSQLString(new Date())+"','"+DateHelper.convertDateTimeToSQLString(new Date())+"',"+jobAllocationVo.getPlantId()+","+jobAllocationVo.getSectionId()+","+jobAllocationVo.getWorkAreaId()+")");
							
							Query timeReportUpdate = session.createSQLQuery("UPDATE "+jobAllocationVo.getSchemaName()+".labor_time_report ltr SET  Allocation_Status = 'Unassigned' ,Modified_By = "+jobAllocationVo.getCreatedBy()+" ,Modified_Date = '"+DateHelper.convertDateTimeToSQLString(new Date())+"' WHERE Business_Date = '"+DateHelper.convertDateToSQLString(jobAllocationVo.getSelectedDate())+"' AND ltr.Emp = '"+workerVo.getWorkerCode()+"'");	
							timeReportUpdate.executeUpdate();
							errorDisplay.setId(0);	
							errorDisplay.setDesc("Success");
						}					
					}
				}else if(jobAllocationVo.getWorkerList() != null && jobAllocationVo.getWorkerList() .size() > 0 && jobAllocationVo.getStatus() != null){
					for(JobAllocationByVendorVo  workerVo :jobAllocationVo.getWorkerList()){
						if(workerVo.isSelected()){			
							log.info("======="+workerVo.getWorkerCode()+":::===="+jobAllocationVo.getSelectedDate()+"  ::::  Shift :"+workerVo.getShiftName()+ "   :::::   "+jobAllocationVo.getSchemaName()+" ::::: "+workerVo.getWorkSkill());
							
							Query timeReportUpdate = session.createSQLQuery("UPDATE "+jobAllocationVo.getSchemaName()+".labor_time_report ltr SET  Allocation_Status = '"+jobAllocationVo.getStatus()+"' ,Approved_By = "+jobAllocationVo.getCreatedBy()+" ,Approved_Date = '"+DateHelper.convertDateTimeToSQLString(new Date())+"' WHERE Business_Date = '"+DateHelper.convertDateToSQLString(jobAllocationVo.getSelectedDate())+"' AND ltr.Emp = '"+workerVo.getWorkerCode()+"'");	
							timeReportUpdate.executeUpdate();
							errorDisplay.setId(0);	
							errorDisplay.setDesc("Success");
						}					
					}
				}
				
				
			}
			
						
			
			
			
			
		} catch (Exception e) {	
			errorDisplay.setId(-1);	
			errorDisplay.setDesc("Error");
			log.error("Error Occured ",e);
		}
		return errorDisplay;
	}



	@Override
	public List<SimpleObject> getWorkersCountFromManpower(JobAllocationByVendorVo jobAllocationVo) {
		Session session = sessionFactory.getCurrentSession();
		Integer workersCount = 0;
		List<SimpleObject> returnList = new ArrayList<>();
		SimpleObject simple = new SimpleObject();
		jobAllocationVo.setCustomerId(1);
		jobAllocationVo.setCompanyId(1);
		try {
			List workerTempList = session.createSQLQuery(" SELECT cast((Required_Workers- (SUM(CASE  WHEN Allocation_Status = 'Assigned' THEN 1 ELSE 0 END ))) as char)AS workers, Job_Skill, "
					+ " Designation, SUM(CASE  WHEN Allocation_Status = 'Assigned' THEN 1 ELSE 0 END ) AS AssignedWorkers"
					+ " FROM manpower_requisition_daywise manpower "
					+ " left JOIN "+jobAllocationVo.getSchemaName()+".labor_time_report ltr ON ltr.Customer_Id = manpower.Customer_Id AND ltr.Company_Id = manpower.Company_Id AND ltr.location_Id = manpower.Location_Id "
					+ " AND ltr.Plant_Id = manpower.Plant_Id AND ltr.Department_Id = manpower.Department_Id AND ltr.Work_Skill = manpower.Job_Skill AND ltr.business_date = manpower.business_date"
									+ " WHERE manpower.Customer_Id = " +jobAllocationVo.getCustomerId()
											+" AND manpower.Company_Id = "+jobAllocationVo.getCompanyId()
											+" AND manpower.Location_Id = "+jobAllocationVo.getLocationId()
											+" AND manpower.Plant_Id = "+jobAllocationVo.getPlantId()
											+" AND manpower.Department_Id = "+jobAllocationVo.getDepartmentId()
											+" AND manpower.Business_Date = '"+jobAllocationVo.getSelectedDate()+"' "											
											+" GROUP BY Job_Skill, manpower.Business_Date,Designation").list();
											//+" AND ( Required_For <> 'Reduction' OR ( Required_For = 'Reduction' AND Effective_Date > '"+jobAllocationVo.getSelectedDate()+"' ) ) ").list();
											
			if(workerTempList != null && workerTempList.size() > 0){
				for (Object customerObject  : workerTempList) {
					Object[] obj = (Object[]) customerObject;
					simple = new SimpleObject();
					if(obj != null){
						simple.setId(Integer.parseInt((String)obj[0]));
						simple.setName((String)obj[1]);
						simple.setDesc((String)obj[2]);
						returnList.add(simple);
					}
				}	
			}
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		System.out.println(workersCount);
		return returnList;
	}
	
	
	
	
}
