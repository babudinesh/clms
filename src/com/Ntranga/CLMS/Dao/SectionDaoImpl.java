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
import com.Ntranga.CLMS.vo.SectionDetailsInfoVo;
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
import com.Ntranga.core.CLMS.entities.SectionDetailsInfo;
import com.Ntranga.core.CLMS.entities.WorkAreaDetails;
import com.Ntranga.core.CLMS.entities.WorkAreaDetailsInfo;

import common.Logger;

@SuppressWarnings({"rawtypes","unchecked"})
@Transactional
@Repository("sectionDao")
public class SectionDaoImpl implements SectionDao {

	private static Logger log = Logger.getLogger(SectionDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	/*
	 * This method will be used to save the work area record
	 */
	@Override
	public Integer saveSection(SectionDetailsInfoVo paramWorkArea) {
		log.info("Entered in saveWorkArea()  ::   "+paramWorkArea);

		Session session = sessionFactory.getCurrentSession();
		Integer sectionInfoId = 0;
		Integer sectionId = 0;
		BigInteger workAreaSequenceId = new BigInteger("0");	
		
		
		SectionDetails workArea = new SectionDetails();
		SectionDetailsInfo workAreaDetailsInfo = new SectionDetailsInfo();
		BigInteger listSize = new BigInteger("0");
		try{
			
			if(paramWorkArea != null && paramWorkArea.getSectionDetailsId() != null && paramWorkArea.getSectionDetailsId() > 0 ){
				listSize = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(COUNT(`Section_Code`),0) FROM `section_details` parent INNER JOIN `section_details_info` child ON (parent.`Section_Id` = child.`Section_Id`)  WHERE child.`Customer_Id`="+paramWorkArea.getCustomerDetailsId()+" AND child.`Company_Id`="+paramWorkArea.getCompanyDetailsId()+" AND child.`Location_Id`="+paramWorkArea.getLocationDetailsId()+" AND child.`Plant_Id`="+paramWorkArea.getPlantDetailsId()+" AND child.`Section_Id` !="+paramWorkArea.getSectionDetailsId()+" AND lower(parent.Section_Code)= lower('"+paramWorkArea.getSectionCode()+"')").list().get(0);
			}else {
				listSize = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(COUNT(`Section_Code`),0) FROM `section_details` parent INNER JOIN `section_details_info` child ON (parent.`Section_Id` = child.`Section_Id`)  WHERE child.`Customer_Id`="+paramWorkArea.getCustomerDetailsId()+" AND child.`Company_Id`="+paramWorkArea.getCompanyDetailsId()+" AND child.`Location_Id`="+paramWorkArea.getLocationDetailsId()+" AND child.`Plant_Id`="+paramWorkArea.getPlantDetailsId()+"  AND lower(parent.Section_Code)=  lower('"+paramWorkArea.getSectionCode()+"')").list().get(0);
			}
			if(listSize.intValue() > 0){				
				sectionInfoId =-1;
				return sectionInfoId;
			}
			
			
			// Save/Update work_area_details table
			if(paramWorkArea != null && paramWorkArea.getSectionDetailsId() != null && paramWorkArea.getSectionDetailsId() > 0){
				workArea = (SectionDetails) session.load(SectionDetails.class, paramWorkArea.getSectionDetailsId());
				workArea.setCustomerDetails(new CustomerDetails(paramWorkArea.getCustomerDetailsId()));
				workArea.setCompanyDetails(new CompanyDetails(paramWorkArea.getCompanyDetailsId()));
				workArea.setSectionCode(paramWorkArea.getSectionCode());
				workArea.setModifiedBy(paramWorkArea.getModifiedBy());
				workArea.setModifiedDate(new Date());
				session.update(workArea);
				sectionId  = paramWorkArea.getSectionDetailsId();				
			}else{
				workArea = new SectionDetails();
				workArea.setCustomerDetails(new CustomerDetails(paramWorkArea.getCustomerDetailsId()));
				workArea.setCompanyDetails(new CompanyDetails(paramWorkArea.getCompanyDetailsId()));
				workArea.setSectionCode(paramWorkArea.getSectionCode());
				workArea.setCreatedBy(paramWorkArea.getCreatedBy());
				workArea.setModifiedBy(paramWorkArea.getModifiedBy());
				workArea.setCreatedDate(new Date());
				workArea.setModifiedDate(new Date());
				sectionId = (Integer) session.save(workArea);
				System.out.println("save"+sectionId);
			}
			
			
			// Save/ Update work_area_details_info table
			if(paramWorkArea != null &&  paramWorkArea.getSectionDetailsInfoId() != null &&  paramWorkArea.getSectionDetailsInfoId() > 0){
				workAreaDetailsInfo = (SectionDetailsInfo) sessionFactory.getCurrentSession().load(SectionDetailsInfo.class, paramWorkArea.getSectionDetailsInfoId());
				workAreaDetailsInfo.setSectionDetails(new SectionDetails(sectionId));
				workAreaDetailsInfo.setCustomerDetails(new CustomerDetails(paramWorkArea.getCustomerDetailsId()));
				workAreaDetailsInfo.setCompanyDetails(new CompanyDetails(paramWorkArea.getCompanyDetailsId()));
				workAreaDetailsInfo.setPlantDetails(new PlantDetails(paramWorkArea.getPlantDetailsId()));
				workAreaDetailsInfo.setCountry(paramWorkArea.getCountry());
				workAreaDetailsInfo.setLocationDetails(new LocationDetails(paramWorkArea.getLocationDetailsId()));
				workAreaDetailsInfo.setSectionName(paramWorkArea.getSectionName());		
				workAreaDetailsInfo.setDepartmentDetails(new DepartmentDetails(paramWorkArea.getDepartmentId()));
				workAreaDetailsInfo.setContactNumber(paramWorkArea.getContactNumber());			
				workAreaDetailsInfo.setSectionSupervisorName(paramWorkArea.getSectionSupervisorName());
				workAreaDetailsInfo.setStatus(paramWorkArea.getStatus());
				workAreaDetailsInfo.setTransactionDate(paramWorkArea.getTransactionDate());
				workAreaDetailsInfo.setModifiedBy(paramWorkArea.getModifiedBy());
				workAreaDetailsInfo.setModifiedDate(new Date());
				session.update(workAreaDetailsInfo);
				sectionInfoId = workAreaDetailsInfo.getSectionDetailsInfoId();
			}else{	
				workAreaDetailsInfo = new SectionDetailsInfo();
				workAreaDetailsInfo.setSectionDetails(new SectionDetails(sectionId));
				workAreaDetailsInfo.setCustomerDetails(new CustomerDetails(paramWorkArea.getCustomerDetailsId()));
				workAreaDetailsInfo.setCompanyDetails(new CompanyDetails(paramWorkArea.getCompanyDetailsId()));
				workAreaDetailsInfo.setPlantDetails(new PlantDetails(paramWorkArea.getPlantDetailsId()));
				workAreaDetailsInfo.setDepartmentDetails(new DepartmentDetails(paramWorkArea.getDepartmentId()));
				workAreaDetailsInfo.setCountry(paramWorkArea.getCountry());
				workAreaDetailsInfo.setLocationDetails(new LocationDetails(paramWorkArea.getLocationDetailsId()));
				workAreaDetailsInfo.setSectionName(paramWorkArea.getSectionName());				
				workAreaDetailsInfo.setContactNumber(paramWorkArea.getContactNumber());	
				workAreaDetailsInfo.setSectionSupervisorName(paramWorkArea.getSectionSupervisorName());
				workAreaDetailsInfo.setStatus(paramWorkArea.getStatus());
				workAreaDetailsInfo.setTransactionDate(paramWorkArea.getTransactionDate());
				workAreaDetailsInfo.setCreatedBy(paramWorkArea.getCreatedBy());
				workAreaDetailsInfo.setModifiedBy(paramWorkArea.getModifiedBy());
				workAreaDetailsInfo.setCreatedDate(new Date());
				workAreaDetailsInfo.setModifiedDate(new Date());

				workAreaSequenceId  = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Sequence_Id),0) FROM `section_details_info` info"
							+ "  WHERE  info.Section_Id = "+paramWorkArea.getSectionDetailsId()+" AND info.`Transaction_Date` = '"+DateHelper.convertDateToSQLString(paramWorkArea.getTransactionDate())+"' and info.Customer_Id='"+paramWorkArea.getCustomerDetailsId()+"' And info.Company_Id = '"+paramWorkArea.getCompanyDetailsId()+"'").list().get(0);
									
				workAreaDetailsInfo.setSequenceId(workAreaSequenceId.intValue()+1);			
				sectionInfoId = (Integer) session.save(workAreaDetailsInfo);
			}

			session.flush();
		}catch (Exception e) {
			 if(!(session.getTransaction().wasRolledBack())){
				 session.getTransaction().rollback();
			 }
			 log.error("Error Occurred...   ", e);
			 log.info("Exiting from saveSection()  ::  sectionInfoId =  "+sectionInfoId);
		}				
		log.info("Exiting from saveSection()  ::  sectionInfoId =  "+sectionInfoId);
		return sectionInfoId;
	}
	
	/*
	 * This method will be used to get a work area record based on given work area id
	 */

	@Override
	public SectionDetailsInfoVo getSectionRecordById(Integer sectionId) {
		log.info("Entered in  getSectionRecordById()  ::   sectionId = "+sectionId);
		Session session = sessionFactory.getCurrentSession();			
		SectionDetailsInfoVo infoVo = new SectionDetailsInfoVo();
		try {				
			SectionDetailsInfo sectionDetailsInfo = (SectionDetailsInfo) session.load(SectionDetailsInfo.class, sectionId);
			infoVo.setCustomerDetailsId(sectionDetailsInfo.getCustomerDetails().getCustomerId());
			infoVo.setCompanyDetailsId(sectionDetailsInfo.getCompanyDetails().getCompanyId());
			infoVo.setSectionDetailsInfoId(sectionDetailsInfo.getSectionDetailsInfoId());
			infoVo.setSectionDetailsId(sectionDetailsInfo.getSectionDetails().getSectionId());
			infoVo.setSectionCode(sectionDetailsInfo.getSectionDetails().getSectionCode());
			infoVo.setPlantDetailsId(sectionDetailsInfo.getPlantDetails().getPlantId());
			infoVo.setLocationDetailsId(sectionDetailsInfo.getLocationDetails().getLocationId());
			infoVo.setSectionName(sectionDetailsInfo.getSectionName());				
			infoVo.setContactNumber(sectionDetailsInfo.getContactNumber());				
			infoVo.setSectionSupervisorName(sectionDetailsInfo.getSectionSupervisorName());
			infoVo.setLocationDetailsId(sectionDetailsInfo.getLocationDetails().getLocationId());
			infoVo.setTransactionDate(sectionDetailsInfo.getTransactionDate());
			infoVo.setStatus(sectionDetailsInfo.getStatus());			
			infoVo.setCountry(sectionDetailsInfo.getCountry());						
			infoVo.setDepartmentId(sectionDetailsInfo.getDepartmentDetails().getDepartmentId());
		} catch (Exception e) {
			log.error("Error occured ... ",e);		
		}		
		return infoVo;
	}

	/*
	 * This method will be used to get list of work area records based on given customerId and companyId
	 */
	
	@Override
	public List<SectionDetailsInfoVo> getSectionListBySearch(SectionDetailsInfoVo paramWorkArea) {
	log.info("Entered in  getWorkAreaListBySearch()  ::   workAreaVo  = "+paramWorkArea);
		
		Session session = sessionFactory.getCurrentSession();
		List queryList = null;
		List<SectionDetailsInfoVo> returnList = new ArrayList<SectionDetailsInfoVo>();
		SectionDetailsInfoVo work = new SectionDetailsInfoVo();
		
		String hqlQuery = " SELECT sdi.Customer_Id, " + " sdi.Company_Id, "
				+ " cdi.Customer_Name, "
				+ " 		codi.Company_Name, 	ldi.Location_Name,  		pdi.Plant_name, "
				+ " 		ddi.department_name, 		sdi.`Section_Name`, 		sdi.`Section_Id`, "
				+ " 		sdi.Transaction_Date, 		sdi.`Status`, "
				+ " 		sdi.Section_Details_Info_Id, 		sd.section_code FROM " 

				+ " 		section_details sd  INNER JOIN section_Details_info sdi "
				+ " 		ON sd.section_id = sdi.section_id "
				+ " 		AND CONCAT(DATE_FORMAT(sdi.transaction_date, '%Y%m%d'), LPAD(sdi.Sequence_Id, 2, '0')) =   ( "
				+ " 		SELECT " + " 		MAX(CONCAT(DATE_FORMAT(sdi1.transaction_date, "
				+ " 			'%Y%m%d'),  		LPAD(sdi1.Sequence_Id,  		2,  		'0'))) "
				+ " 		FROM 		section_details_info sdi1  		WHERE "
				+ " 		sdi.Section_Id = sdi1.Section_Id  		AND sdi.transaction_date <= CURRENT_DATE() "
				+ " 		)  		INNER JOIN department_details_info ddi ON "
				+ " 		sdi.department_id = ddi.department_id "
				+ " 		AND CONCAT(DATE_FORMAT(ddi.transaction_date, '%Y%m%d'), LPAD(ddi.department_Sequence_Id, 2, '0')) =   ( "
				+ " 		SELECT 	MAX(CONCAT(DATE_FORMAT(ddi1.transaction_date, " + " 		'%Y%m%d'), "
				+ " 		LPAD(ddi1.department_Sequence_Id,  		2,  		'0')))  		FROM "
				+ " 		department_details_info ddi1  		WHERE "
				+ " 		ddi.department_Id = ddi1.department_Id "
				+ " 		AND ddi.transaction_date <= CURRENT_DATE()  		) "
				+ " 		INNER JOIN  associating_department_to_location_plant  dlp "
				+ " 		ON dlp.department_Id = ddi.department_Id  	INNER JOIN  plant_details_info  pdi "
				+ " 		ON pdi.plant_id = dlp.plant_id 	AND sdi.plant_id= dlp.plant_id "
				+ " 		AND CONCAT(DATE_FORMAT(pdi.transaction_date, '%Y%m%d'), LPAD(pdi.plant_Sequence_Id, 2, '0')) =   ( "
				+ " 		SELECT 	MAX(CONCAT(DATE_FORMAT(pdi1.transaction_date, '%Y%m%d'), "
				+ " 		LPAD(pdi1.plant_Sequence_Id, 2, '0'))) 	FROM "
				+ " 		plant_details_info pdi1 WHERE  	pdi.plant_id = pdi1.plant_id "
				+ " 		AND pdi.transaction_date <= CURRENT_DATE() 	) " +

				" 			INNER JOIN  location_details_info  ldi "
				+ " 						ON ldi.location_id = dlp.location_id "
				+ " 						AND sdi.location_id = dlp.location_id "
				+ " 						AND CONCAT(DATE_FORMAT(ldi.transaction_date, '%Y%m%d'), LPAD(ldi.location_Sequence_Id, 2, '0')) =   ( "
				+ " 						SELECT "
				+ " 					MAX(CONCAT(DATE_FORMAT(ldi1.transaction_date, "
				+ " 						'%Y%m%d'), 	LPAD(ldi1.location_Sequence_Id, "
				+ " 				2, 	'0'))) FROM "
				+ " 				location_details_info ldi1 	WHERE "
				+ " 					ldi.location_id = ldi1.location_id "
				+ " 				AND ldi.transaction_date <= CURRENT_DATE() ) " +

				" 				INNER JOIN  company_details_info  codi "
				+ " 						ON codi.company_id = dlp.company_id "
				+ " 						AND sdi.company_id = dlp.company_id "
				+ " 						AND CONCAT(DATE_FORMAT(codi.transaction_date, '%Y%m%d'), LPAD(codi.company_Sequence_Id, 2, '0')) =   ( "
				+ " 						SELECT "
				+ " 						MAX(CONCAT(DATE_FORMAT(codi1.transaction_date, "
				+ " 						'%Y%m%d'), 	LPAD(codi1.company_Sequence_Id, "
				+ " 						2, 	'0'))) 	FROM "
				+ " 						company_details_info codi1 	WHERE "
				+ " 						codi.company_id = codi1.company_id "
				+ " 						AND codi.transaction_date <= CURRENT_DATE() ) "
				+ " 						INNER JOIN  customer_details_info  cdi "
				+ " 						ON cdi.customer_id = dlp.customer_id "
				+ " 						AND sdi.customer_id = dlp.customer_id "
				+ " 						AND CONCAT(DATE_FORMAT(cdi.transaction_date, '%Y%m%d'), LPAD(cdi.customer_Sequence_Id, 2, '0')) =   ( "
				+ " 						SELECT "
				+ " 						MAX(CONCAT(DATE_FORMAT(cdi1.transaction_date, "
				+ " 						'%Y%m%d'), 	LPAD(cdi1.customer_Sequence_Id, "
				+ " 						2, '0'))) 	FROM "
				+ " 						customer_details_info cdi1 	WHERE "
				+ " 						cdi.customer_id = cdi1.customer_id "
				+ " 						AND cdi.transaction_date <= CURRENT_DATE() 	) ";
			
		if(paramWorkArea.getCustomerId() != null && paramWorkArea.getCustomerId() > 0){
			hqlQuery += "  AND  sdi.Customer_Id = "+paramWorkArea.getCustomerId();
		}
		if(paramWorkArea.getCompanyId() != null && paramWorkArea.getCompanyId() > 0){
			hqlQuery += "  AND  sdi.Company_Id = "+paramWorkArea.getCompanyId();
		}
		if(paramWorkArea.getLocationId() != null && paramWorkArea.getLocationId() > 0){
			hqlQuery += "  AND  sdi.Location_Id = "+paramWorkArea.getLocationId();
		}
		if(paramWorkArea.getPlantDetailsId()  !=null && paramWorkArea.getPlantDetailsId() > 0){
			hqlQuery += "  AND  sdi.Plant_Id = "+paramWorkArea.getPlantDetailsId();
		}
		
		if(paramWorkArea.getSectionName() != null && !paramWorkArea.getSectionName().isEmpty()){
			hqlQuery += " AND sdi.section_Name LIKE '"+paramWorkArea.getSectionName()+"%' ";
		}
		
		if(paramWorkArea.getStatus() != null && !paramWorkArea.getStatus().isEmpty()){
			hqlQuery += " AND sdi.Status = '"+paramWorkArea.getStatus()+"' ";
		}
		
		if(paramWorkArea.getDepartmentId() != null && paramWorkArea.getDepartmentId() >0 ){
			hqlQuery += "  AND  sdi.Department_Id = "+paramWorkArea.getDepartmentId();
		}
		
		hqlQuery += " Order By sd.Section_Code asc";
		
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			queryList = query.list();
			if(queryList.size() > 0){
				for (Object customer : queryList) {
					Object[] obj = (Object[]) customer;
					work = new SectionDetailsInfoVo();					
					work.setCustomerId((Integer)obj[0]);
					work.setCompanyId((Integer)obj[1]);
					work.setCustomerName((String)obj[2]);
					work.setCompanyName((String)obj[3]);	
					work.setLocationName((String)obj[4]);
					work.setPlantName(obj[5]+"");
					work.setDepartmentName(obj[6]+"");
					work.setSectionName(obj[7]+"");
					work.setSectionDetailsId((Integer)obj[8]);
					work.setTransactionDate((Date)obj[9]);
					work.setStatus((obj[10]+"").equalsIgnoreCase("Y") ? "Active" : "InActive");									
					work.setSectionDetailsInfoId((Integer)obj[11]);
					work.setSectionCode(obj[12]+"");	
					
					returnList.add(work);
				}	
			}
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from getWorkAreaListBySearch()  ::   "+returnList);
		}
		session.flush();	
		return returnList;
	}

	@Override
	public List<SimpleObject> getTransactionDatesListOfHistory(Integer customerId, Integer companyId, Integer sectionId) {
		log.info("Entered in getTransactionListForWorkArea()  ::   customerId = "+customerId+" , companyId = "+companyId+" , workAreaId = "+sectionId);
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> transactionList = new ArrayList<>();
		try {
			List workAreaList = session.createSQLQuery("SELECT `Section_Details_Info_Id` AS id ,CONCAT(DATE_FORMAT(Transaction_Date,'%d/%m/%Y'),' - ',Sequence_Id) AS eNAME FROM  `section_details_info` info  WHERE info.Customer_Id = "+customerId+" AND info.Company_Id = "+companyId+" AND info.Section_Id = "+sectionId+" ORDER BY info.Transaction_Date, info.Section_Details_Info_Id ").list();
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


}
