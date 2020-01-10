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

import com.Ntranga.CLMS.vo.MWorkSkillVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.WorkAreaVo;
import com.Ntranga.core.DateHelper;
import com.Ntranga.core.CLMS.entities.CompanyDetails;
import com.Ntranga.core.CLMS.entities.CustomerDetails;
import com.Ntranga.core.CLMS.entities.DepartmentDetails;
import com.Ntranga.core.CLMS.entities.LocationDetails;
import com.Ntranga.core.CLMS.entities.MCountry;
import com.Ntranga.core.CLMS.entities.MWorkAreaSkills;
import com.Ntranga.core.CLMS.entities.PlantDetails;
import com.Ntranga.core.CLMS.entities.SectionDetails;
import com.Ntranga.core.CLMS.entities.WorkAreaDetails;
import com.Ntranga.core.CLMS.entities.WorkAreaDetailsInfo;

import common.Logger;

@SuppressWarnings({"rawtypes","unchecked"})
@Transactional
@Repository("workAreaDao")
public class WorkAreaDaoImpl implements WorkAreaDao {

	private static Logger log = Logger.getLogger(WorkAreaDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	/*
	 * This method will be used to save the work area record
	 */
	@Override
	public Integer saveWorkArea(WorkAreaVo paramWorkArea) {
		log.info("Entered in saveWorkArea()  ::   "+paramWorkArea);

		Session session = sessionFactory.getCurrentSession();
		Integer workAreaDetailsId = 0;
		Integer workAreaId = 0;
		BigInteger workAreaSequenceId = new BigInteger("0");	
		
		
		WorkAreaDetails workArea = new WorkAreaDetails();
		WorkAreaDetailsInfo workAreaDetailsInfo = new WorkAreaDetailsInfo();
		BigInteger listSize = new BigInteger("0");
		try{
			if(paramWorkArea != null && paramWorkArea.getWorkAreaId() > 0 ){
				listSize = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(COUNT(`Work_Area_Code`),0) FROM `work_area_details` parent INNER JOIN `work_area_details_info` child ON (parent.`Work_Area_Id` = child.`Work_Area_Id`)  WHERE child.`Customer_Id`="+paramWorkArea.getCustomerId()+" AND child.`Company_Id`="+paramWorkArea.getCompanyId()+" AND child.`Location_Id`="+paramWorkArea.getLocationId()+" AND child.`Plant_Id`="+paramWorkArea.getPlantId()+" AND child.`Section_Id`="+paramWorkArea.getSectionDetailsId()+" AND parent.`Work_Area_Id` !="+paramWorkArea.getWorkAreaId()+" AND parent.`Work_Area_Code`='"+paramWorkArea.getWorkAreaCode()+"'").list().get(0);
			}else {
				listSize = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(COUNT(`Work_Area_Code`),0) FROM `work_area_details` parent INNER JOIN `work_area_details_info` child ON (parent.`Work_Area_Id` = child.`Work_Area_Id`)  WHERE child.`Customer_Id`="+paramWorkArea.getCustomerId()+" AND child.`Company_Id`="+paramWorkArea.getCompanyId()+" AND child.`Location_Id`="+paramWorkArea.getLocationId()+" AND child.`Plant_Id`="+paramWorkArea.getPlantId()+" AND child.`Section_Id`="+paramWorkArea.getSectionDetailsId()+" AND parent.`Work_Area_Code`='"+paramWorkArea.getWorkAreaCode()+"'").list().get(0);
			}
			if(listSize.intValue() > 0){				
				workAreaDetailsId =2;
				return workAreaDetailsId;
			}

			// Save/Update work_area_details table
			if(paramWorkArea != null && paramWorkArea.getWorkAreaId() > 0){
				workArea = (WorkAreaDetails) session.load(WorkAreaDetails.class, paramWorkArea.getWorkAreaId());
				workArea.setCustomerDetails(new CustomerDetails(paramWorkArea.getCustomerId()));
				workArea.setCompanyDetails(new CompanyDetails(paramWorkArea.getCompanyId()));
				workArea.setWorkAreaCode(paramWorkArea.getWorkAreaCode().toUpperCase());
				workArea.setModifiedBy(paramWorkArea.getModifiedBy());
				workArea.setModifiedDate(new Date());
				session.update(workArea);
				workAreaId  = paramWorkArea.getWorkAreaId();
			}else{
				workArea = new WorkAreaDetails();
				workArea.setCustomerDetails(new CustomerDetails(paramWorkArea.getCustomerId()));
				workArea.setCompanyDetails(new CompanyDetails(paramWorkArea.getCompanyId()));
				workArea.setWorkAreaCode(paramWorkArea.getWorkAreaCode().toUpperCase());
				workArea.setCreatedBy(paramWorkArea.getCreatedBy());
				workArea.setModifiedBy(paramWorkArea.getModifiedBy());
				workArea.setCreatedDate(new Date());
				workArea.setModifiedDate(new Date());
				workAreaId = (Integer) session.save(workArea);
			}
			
			
			// Save/ Update work_area_details_info table
			if(paramWorkArea != null &&  paramWorkArea.getWorkAreaDetailsId() > 0){
				workAreaDetailsInfo = (WorkAreaDetailsInfo) sessionFactory.getCurrentSession().load(WorkAreaDetailsInfo.class, paramWorkArea.getWorkAreaDetailsId());
				workAreaDetailsInfo.setWorkAreaDetails(new WorkAreaDetails(workAreaId));
				workAreaDetailsInfo.setCustomerDetails(new CustomerDetails(paramWorkArea.getCustomerId()));
				workAreaDetailsInfo.setCompanyDetails(new CompanyDetails(paramWorkArea.getCompanyId()));
				workAreaDetailsInfo.setPlantDetails(new PlantDetails(paramWorkArea.getPlantId()));
				workAreaDetailsInfo.setDepartmentDetails(new DepartmentDetails(paramWorkArea.getDepartmentId()));
				workAreaDetailsInfo.setMCountry(new MCountry(paramWorkArea.getCountryId()));
				workAreaDetailsInfo.setLocationDetails(new LocationDetails(paramWorkArea.getLocationId()));
				workAreaDetailsInfo.setSectionDetails(new SectionDetails(paramWorkArea.getSectionDetailsId()));
				workAreaDetailsInfo.setWorkAreaName(paramWorkArea.getWorkAreaName());
				workAreaDetailsInfo.setShortName(paramWorkArea.getShortName());
				workAreaDetailsInfo.setContactNumber(paramWorkArea.getContactNumber());
				workAreaDetailsInfo.setEmailId(paramWorkArea.getEmailId());
				workAreaDetailsInfo.setWorkAreaIncharge(paramWorkArea.getWorkAreaIncharge());
				workAreaDetailsInfo.setStatus(paramWorkArea.getStatus());
				workAreaDetailsInfo.setTransactionDate(paramWorkArea.getTransactionDate());
				workArea.setModifiedBy(paramWorkArea.getModifiedBy());
				workArea.setModifiedDate(new Date());
				session.update(workAreaDetailsInfo);
				workAreaDetailsId = workAreaDetailsInfo.getWorkAreaDetailsId();
			}else{	
				workAreaDetailsInfo = new WorkAreaDetailsInfo();
				workAreaDetailsInfo.setWorkAreaDetails(new WorkAreaDetails(workAreaId));
				workAreaDetailsInfo.setCustomerDetails(new CustomerDetails(paramWorkArea.getCustomerId()));
				workAreaDetailsInfo.setCompanyDetails(new CompanyDetails(paramWorkArea.getCompanyId()));
				workAreaDetailsInfo.setPlantDetails(new PlantDetails(paramWorkArea.getPlantId()));
				workAreaDetailsInfo.setDepartmentDetails(new DepartmentDetails(paramWorkArea.getDepartmentId()));
				workAreaDetailsInfo.setMCountry(new MCountry(paramWorkArea.getCountryId()));
				workAreaDetailsInfo.setLocationDetails(new LocationDetails(paramWorkArea.getLocationId()));
				workAreaDetailsInfo.setSectionDetails(new SectionDetails(paramWorkArea.getSectionDetailsId()));
				workAreaDetailsInfo.setWorkAreaName(paramWorkArea.getWorkAreaName());
				workAreaDetailsInfo.setShortName(paramWorkArea.getShortName());
				workAreaDetailsInfo.setContactNumber(paramWorkArea.getContactNumber());
				workAreaDetailsInfo.setEmailId(paramWorkArea.getEmailId());
				workAreaDetailsInfo.setWorkAreaIncharge(paramWorkArea.getWorkAreaIncharge());
				workAreaDetailsInfo.setStatus(paramWorkArea.getStatus());
				workAreaDetailsInfo.setTransactionDate(paramWorkArea.getTransactionDate());
				workAreaDetailsInfo.setCreatedBy(paramWorkArea.getCreatedBy());
				workAreaDetailsInfo.setModifiedBy(paramWorkArea.getModifiedBy());
				workAreaDetailsInfo.setCreatedDate(new Date());
				workAreaDetailsInfo.setModifiedDate(new Date());
				
				if(workAreaId != null && workAreaId > 0){
					workAreaSequenceId  = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Work_Area_Sequence_Id),0) FROM `work_area_details_info` info"
							+ "  WHERE  info.Work_Area_Id = "+paramWorkArea.getWorkAreaId() +" AND info.`Transaction_Date` = '"+DateHelper.convertDateToSQLString(paramWorkArea.getTransactionDate())+"' and info.Customer_Id='"+paramWorkArea.getCustomerId()+"' And info.Company_Id = '"+paramWorkArea.getCompanyId()+"'").list().get(0);
					log.info("Work Area sequence Id : "+workAreaSequenceId);
				}	
				workAreaDetailsInfo.setWorkAreaSequenceId(workAreaSequenceId.intValue()+1);			
				workAreaDetailsId = (Integer) session.save(workAreaDetailsInfo);
			}
			
			
			
			//Save /update m_work_area_skills table
			if(workAreaId > 0 && paramWorkArea.getSkillList() != null && paramWorkArea.getSkillList().size() > 0){
				Query q = session.createQuery("DELETE FROM  MWorkAreaSkills WHERE Work_Area_Id= "+workAreaId);
				q.executeUpdate();
				
				for(MWorkSkillVo skills : paramWorkArea.getSkillList()){
					MWorkAreaSkills workAreaSkill = new MWorkAreaSkills();
					
					workAreaSkill.setWorkAreaDetails(new WorkAreaDetails(workAreaId));
					workAreaSkill.setWorkAreaSkillName(skills.getWorkAreaSkillName());
					workAreaSkill.setIsWorkAreaSkill(skills.getIsWorkAreaSkill() == true ? "Y" : "N");
					workAreaSkill.setCreatedBy(paramWorkArea.getCreatedBy());
					workAreaSkill.setModifiedBy(paramWorkArea.getModifiedBy());
					workAreaSkill.setCreatedDate(new Date());
					workAreaSkill.setModifiedDate(new Date());
					session.save(workAreaSkill);	
				}
			}
			
			session.flush();
		}catch (Exception e) {
			 if(!(session.getTransaction().wasRolledBack())){
				 session.getTransaction().rollback();
			 }
			 log.error("Error Occurred...   ", e);
			 log.info("Exiting from saveWorkArea()  ::  workAreaDetailsId =  "+workAreaDetailsId);
		}				
		log.info("Exiting from saveWorkArea()  ::  workAreaDetailsId =  "+workAreaDetailsId);
		return workAreaDetailsId;
	}
	
	/*
	 * This method will be used to get a work area record based on given work area id
	 */

	@Override
	public List<WorkAreaVo> getWorkAreaById(Integer workAreaDetailsId) {
		log.info("Entered in  getWorkAreaById()  ::   workAreaDetailsId = "+workAreaDetailsId);
		Session session = sessionFactory.getCurrentSession();
		WorkAreaVo work = new WorkAreaVo();
		List<WorkAreaVo> returnList = new ArrayList<WorkAreaVo>();
		String  hqlQuery = " SELECT info.Customer_Id, info.Company_Id, info.Work_Area_Details_Id, info.Work_Area_Id, work.Work_Area_Code, "
							+ "	info.Work_Area_Name, info.Short_Name, info.Contact_Number, info.Email_Id, info.Work_Area_Incharge, "
							+ " info.Location_Id, info.Transaction_Date, info.Status, info.Plant_Id, info.Country_Id, info.section_Id,info.Department_Id "
							+ " FROM work_area_details_info AS info "
							+ " LEFT JOIN work_area_details AS work ON info.Work_Area_Id = work.Work_Area_Id " 							
							+ " WHERE info.Work_Area_Details_Id = "+workAreaDetailsId;
		
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			List plantList = query.list();

			for (Object object : plantList) {
				Object[] obj = (Object[]) object;
				work = new WorkAreaVo();
				work.setCustomerId((Integer)obj[0]);
				work.setCompanyId((Integer)obj[1]);
				work.setWorkAreaDetailsId((Integer)obj[2]);
				work.setWorkAreaId((Integer)obj[3]);
				work.setWorkAreaCode(obj[4]+"");
				work.setWorkAreaName(obj[5]+"");
				if(obj[6] != null)
				work.setShortName(obj[6]+"");
				if(obj[15] != null)
				work.setSectionDetailsId((Integer)obj[15]);
				if(obj[7] != null)
				work.setContactNumber(obj[7]+"");
				if(obj[8] != null)
				work.setEmailId(obj[8]+"");
				if(obj[9] != null)
				work.setWorkAreaIncharge(obj[9]+"");
				work.setLocationId((Integer)obj[10]);
				work.setTransactionDate(DateHelper.convertSQLDateToDate((java.sql.Date)obj[11]));
				work.setStatus(obj[12]+"");
				work.setPlantId((Integer)obj[13]);
				work.setCountryId((Integer)obj[14]);
				work.setDepartmentId((Integer)obj[16]);
				returnList.add(work);
			}	
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from  getWorkAreaById()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from  getWorkAreaById()  ::   "+returnList);
		return returnList;
	}

	/*
	 * This method will be used to get list of work area records based on given customerId and companyId
	 */
	
	@Override
	public List<WorkAreaVo> getWorkAreaListBySearch(WorkAreaVo paramWorkArea) {
	log.info("Entered in  getWorkAreaListBySearch()  ::   workAreaVo  = "+paramWorkArea);
		
		Session session = sessionFactory.getCurrentSession();
		List queryList = null;
		List<WorkAreaVo> returnList = new ArrayList<WorkAreaVo>();
		WorkAreaVo work = new WorkAreaVo();
		
		String hqlQuery = " SELECT sdi.Customer_Id,sdi.Company_Id, "
				+ " cdi.Customer_Name,codi.Company_Name,ldi.Location_Name,pdi.Plant_name,ddi.department_name,sdi.`Section_Name`, "
				+ " wadi.work_area_name,  wad.`Work_Area_Id`, sdi.Transaction_Date,  sdi.`Status`, "
				+ " wadi.Work_Area_Details_Id,  wad.`Work_Area_Code` "

				+ " FROM " + " work_area_details  wad "
				+ "  INNER JOIN work_area_details_info wadi ON wadi.`Work_Area_Id` = wad. Work_Area_Id "
				+ "  AND CONCAT(DATE_FORMAT(wadi.transaction_date, '%Y%m%d'), LPAD(wadi.Work_Area_Sequence_Id, 2, '0')) =   ( "
				+ " SELECT  MAX(CONCAT(DATE_FORMAT(wadi1.transaction_date,  '%Y%m%d'), "
				+ " LPAD(wadi1.Work_Area_Sequence_Id, 2,  '0')))  FROM "
				+ " work_area_details_info wadi1 WHERE  wadi.Work_Area_Id = wadi1.Work_Area_Id "
				+ " AND wadi.transaction_date <= CURRENT_DATE()  ) "

				+ " INNER JOIN section_Details_info sdi  ON wadi.section_id = sdi.section_id "
				+ " AND CONCAT(DATE_FORMAT(sdi.transaction_date, '%Y%m%d'), LPAD(sdi.Sequence_Id, 2, '0')) =   ( "
				+ " SELECT  MAX(CONCAT(DATE_FORMAT(sdi1.transaction_date,  '%Y%m%d'), "
				+ " LPAD(sdi1.Sequence_Id,  2,  '0'))) FROM  section_details_info sdi1 "
				+ " WHERE sdi.Section_Id = sdi1.Section_Id  AND sdi.transaction_date <= CURRENT_DATE() "
				+ " )  INNER JOIN department_details_info ddi ON  sdi.department_id = ddi.department_id "
				+ " AND CONCAT(DATE_FORMAT(ddi.transaction_date, '%Y%m%d'), LPAD(ddi.department_Sequence_Id, 2, '0')) =   ( "
				+ " SELECT  MAX(CONCAT(DATE_FORMAT(ddi1.transaction_date,  '%Y%m%d'), "
				+ " LPAD(ddi1.department_Sequence_Id,  2, '0')))  	FROM "
				+ " 	department_details_info ddi1  	WHERE  ddi.department_Id = ddi1.department_Id "
				+ " 	AND ddi.transaction_date <= CURRENT_DATE() 	) "
				+ " 	INNER JOIN  associating_department_to_location_plant  dlp "
				+ " 	ON dlp.department_Id = ddi.department_Id  INNER JOIN  plant_details_info  pdi "
				+ " ON pdi.plant_id = dlp.plant_id  AND sdi.plant_id= dlp.plant_id "
				+ " 	AND CONCAT(DATE_FORMAT(pdi.transaction_date, '%Y%m%d'), LPAD(pdi.plant_Sequence_Id, 2, '0')) =   ( "
				+ " SELECT " + " MAX(CONCAT(DATE_FORMAT(pdi1.transaction_date,  '%Y%m%d'), "
				+ " 	LPAD(pdi1.plant_Sequence_Id, 	2, 	'0')))  	FROM "
				+ " 	plant_details_info pdi1  	WHERE 	pdi.plant_id = pdi1.plant_id "
				+ " 	AND pdi.transaction_date <= CURRENT_DATE()  ) "

				+ " 	INNER JOIN  location_details_info  ldi  ON ldi.location_id = dlp.location_id "
				+ " AND sdi.location_id = dlp.location_id "
				+ " AND CONCAT(DATE_FORMAT(ldi.transaction_date, '%Y%m%d'), LPAD(ldi.location_Sequence_Id, 2, '0')) =   ( "
				+ " 	SELECT  	MAX(CONCAT(DATE_FORMAT(ldi1.transaction_date, 	'%Y%m%d'), "
				+ " 	LPAD(ldi1.location_Sequence_Id,  	2,  	'0')))  	FROM "
				+ " 	location_details_info ldi1  	WHERE  	ldi.location_id = ldi1.location_id "
				+ " 	AND ldi.transaction_date <= CURRENT_DATE() ) "

				+ " INNER JOIN  company_details_info  codi  	ON codi.company_id = dlp.company_id "
				+ " 	AND sdi.company_id = dlp.company_id "
				+ " AND CONCAT(DATE_FORMAT(codi.transaction_date, '%Y%m%d'), LPAD(codi.company_Sequence_Id, 2, '0')) =   ( "
				+ " 	SELECT MAX(CONCAT(DATE_FORMAT(codi1.transaction_date,  	'%Y%m%d'), "
				+ " 	LPAD(codi1.company_Sequence_Id,  	2,  	'0')))  	FROM "
				+ " 	company_details_info codi1  	WHERE  	codi.company_id = codi1.company_id "
				+ " 	AND codi.transaction_date <= CURRENT_DATE()  	) "

				+ " 	INNER JOIN  customer_details_info  cdi  	ON cdi.customer_id = dlp.customer_id "
				+ " 	AND sdi.customer_id = dlp.customer_id "
				+ " 	AND CONCAT(DATE_FORMAT(cdi.transaction_date, '%Y%m%d'), LPAD(cdi.customer_Sequence_Id, 2, '0')) =   ( "
				+ " SELECT 	MAX(CONCAT(DATE_FORMAT(cdi1.transaction_date,  	'%Y%m%d'), "
				+ " LPAD(cdi1.customer_Sequence_Id,  	2,  	'0')))  	FROM "
				+ " 	customer_details_info cdi1  	WHERE  cdi.customer_id = cdi1.customer_id "
				+ " 	AND cdi.transaction_date <= CURRENT_DATE() )";
			
		if(paramWorkArea.getCustomerId() > 0){
			hqlQuery += "  AND  wadi.Customer_Id = "+paramWorkArea.getCustomerId();
		}
		if(paramWorkArea.getCompanyId() > 0){
			hqlQuery += "  AND  wadi.Company_Id = "+paramWorkArea.getCompanyId();
		}
		if(paramWorkArea.getLocationId() > 0){
			hqlQuery += "  AND  wadi.Location_Id = "+paramWorkArea.getLocationId();
		}
		if(paramWorkArea.getPlantId() > 0){
			hqlQuery += "  AND  wadi.Plant_Id = "+paramWorkArea.getPlantId();
		}
		
		if(paramWorkArea.getSectionDetailsId() != null && paramWorkArea.getSectionDetailsId() > 0){
			hqlQuery += "  AND  wadi.section_Id = "+paramWorkArea.getSectionDetailsId();
		}
		
		if(paramWorkArea.getWorkAreaName() != null && !paramWorkArea.getWorkAreaName().isEmpty()){
			hqlQuery += " AND wadi.Work_Area_Name LIKE '%"+paramWorkArea.getWorkAreaName()+"%' ";
		}
		
		if(paramWorkArea.getStatus() != null && !paramWorkArea.getStatus().isEmpty()){
			hqlQuery += " AND wadi.Status = '"+paramWorkArea.getStatus()+"' ";
		}
		
		if(paramWorkArea.getDepartmentId() != null && paramWorkArea.getDepartmentId() >0 ){
			hqlQuery += "  AND  wadi.Department_Id = "+paramWorkArea.getDepartmentId();
		}
		
		hqlQuery += " Order By wad.Work_Area_Code asc";
		
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			queryList = query.list();
			if(queryList.size() > 0){
				for (Object customer : queryList) {
					Object[] obj = (Object[]) customer;
					work = new WorkAreaVo();
					work.setCustomerId((Integer)obj[0]);
					work.setCompanyId((Integer)obj[1]);
					work.setCustomerName((String)obj[2]);
					work.setCompanyName((String)obj[3]);	
					work.setLocationName((String)obj[4]);
					work.setPlantName(obj[5]+"");
					work.setDepartmentName(obj[6]+"");
					work.setSectionName(obj[7]+"");
					work.setWorkAreaName((String)obj[8]);					
					work.setWorkAreaId((Integer)obj[9]);
					work.setTransactionDate((Date)obj[10]);
					if(obj[11] != null)
						work.setStatus((obj[11]+"").equalsIgnoreCase("Y") ? "Active" : "InActive");
					else
						work.setStatus("InActive");
					work.setWorkAreaDetailsId((Integer)obj[12]);
					work.setWorkAreaCode(obj[13]+"");
					
					returnList.add(work);
				}	
			}
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from getWorkAreaListBySearch()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from getWorkAreaListBySearch()  ::   "+returnList);
		return returnList;
	}

	@Override
	public List<SimpleObject> getTransactionListForWorkArea(Integer customerId, Integer companyId, Integer workAreaId) {
		log.info("Entered in getTransactionListForWorkArea()  ::   customerId = "+customerId+" , companyId = "+companyId+" , workAreaId = "+workAreaId);
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> transactionList = new ArrayList<>();
		try {
			List workAreaList = session.createSQLQuery("SELECT Work_Area_Details_Id AS id ,CONCAT(DATE_FORMAT(Transaction_Date,'%d/%m/%Y'),' - ',Work_Area_Sequence_Id) AS name FROM  work_area_details_info info  WHERE info.Customer_Id = "+customerId+" AND info.Company_Id = "+companyId+" AND info.Work_Area_Id = "+workAreaId+" ORDER BY info.Transaction_Date, info.Work_Area_Details_Id asc").list();
			for (Object transDates  : workAreaList) {
				Object[] transaction = (Object[]) transDates;
				SimpleObject object = new SimpleObject();
				object.setId((Integer)transaction[0]);
				object.setName(transaction[1]+"");
				transactionList.add(object);
			}			
		} catch (Exception e) {
			log.error("Error Occurred...   ", e);
			log.info("Exiting from getTransactionListForWorkArea()  ::   "+transactionList);
		}
		
		log.info("Exiting from getTransactionListForWorkArea()  ::   "+transactionList);
		return transactionList;	
	}

	/*
	 * This method will be used to get Work Area skills List for given customerId, companyId and workAreaId
	 */
	@Override
	public List<MWorkSkillVo> getWorkAreaSkillsList(Integer workAreaId) {
		log.info("Entered in  getWorkAreaSkillsList()  ::   workAreaId = "+workAreaId);
		
		Session session = sessionFactory.getCurrentSession();
		List<MWorkAreaSkills> queryList = null;
		List<MWorkSkillVo> returnList = new ArrayList<MWorkSkillVo>();
		MWorkSkillVo work = new MWorkSkillVo();
		
		try {				
			queryList = session.createQuery(" FROM MWorkAreaSkills WHERE Work_Area_Id = "+workAreaId).list();
			if(queryList.size() > 0){
				for (MWorkAreaSkills skill : queryList) {
					work = new MWorkSkillVo();
					
					work.setWorkAreaSkillId(skill.getWorkAreaSkillId());
					work.setWorkAreaSkillName(skill.getWorkAreaSkillName());
					work.setIsWorkAreaSkill((skill.getIsWorkAreaSkill()).equalsIgnoreCase("Y") ? true : false);
					returnList.add(work);
				}	
			}
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from getWorkAreaListBySearch()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from getWorkAreaListBySearch()  ::   "+returnList);
		return returnList;
	}

	@Override
	public List<SimpleObject> getWorkAreaListForDropDown(Integer customerId, Integer companyId, String status) {
		log.info("Entered in getWorkAreaListForDropDown()  ::   customerId = "+customerId+" , companyId = "+companyId+" , status = "+status);
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> transactionList = new ArrayList<>();
		try {
			String query = "SELECT parent.`Work_Area_Id`,`Work_Area_Name`,`Work_Area_Code` FROM  `work_area_details` parent "
							+" INNER JOIN `work_area_details_info` child ON (parent.`Work_Area_Id` = child.`Work_Area_Id`) "
							+" WHERE CONCAT(DATE_FORMAT(child.transaction_date, '%Y%m%d'), child.`Work_Area_Sequence_Id`) = " 
							+" ( " 
							+"  SELECT MAX(CONCAT(DATE_FORMAT(child1.transaction_date, '%Y%m%d'), child1.Work_Area_Sequence_Id)) "
							+" FROM work_area_details_info child1 "
							+" WHERE child.Work_Area_Id = child1.Work_Area_Id AND child1.transaction_date <= CURRENT_DATE() "
							+" ) AND child.`Customer_Id`="+customerId
							+" AND child.`Company_Id`="+companyId
							+" AND child.`Status`='"+status+"'";
			List workAreaList = session.createSQLQuery(query).list();
			for (Object transDates  : workAreaList) {
				Object[] transaction = (Object[]) transDates;
				SimpleObject object = new SimpleObject();
				object.setId((Integer)transaction[0]);
				object.setName(transaction[1]+"");
				transactionList.add(object);
			}			
		} catch (Exception e) {
			log.error("Error Occurred...   ", e);
			log.info("Exiting from getWorkAreaListForDropDown()  ::   "+transactionList);
		}
		
		log.info("Exiting from getWorkAreaListForDropDown()  ::   "+transactionList);
		return transactionList;	
	}
	
}
