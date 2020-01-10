package com.Ntranga.CLMS.Dao;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.VendorBankDetailsVo;
import com.Ntranga.CLMS.vo.WorkerBadgeGenerationVo;
import com.Ntranga.core.CLMS.entities.CompanyDetails;
import com.Ntranga.core.CLMS.entities.CustomerDetails;
import com.Ntranga.core.CLMS.entities.VendorDetails;
import com.Ntranga.core.CLMS.entities.WorkerBadgeGeneration;
import com.Ntranga.core.CLMS.entities.WorkerDetails;

import common.Logger;

@SuppressWarnings("rawtypes")
@Transactional
@Repository("workerBadgeGenerationDao")
public class WorkerBadgeGenerationDaoImpl implements WorkerBadgeGenerationDao  {

	private static Logger log = Logger.getLogger(WorkerBadgeGenerationDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;


	@Override
	public List<SimpleObject> getWorkersByVendorId(WorkerBadgeGenerationVo workerBadgeVo) {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> workerlist = new ArrayList<SimpleObject>(); 
		try {
			List tempList =  session.createSQLQuery(" SELECT wdi.`Worker_id`, CONCAT(IFNULL(`First_name`,''),' ',IFNULL(`Middle_name`,''),' ',IFNULL(`Last_name`,''),' (', wd.worker_code,')') AS wname  FROM  worker_details wd "
													+" LEFT JOIN worker_details_info wdi ON(wd.worker_id = wdi.worker_id) WHERE CONCAT(DATE_FORMAT(wdi.transaction_date, '%Y%m%d'), LPAD(wdi.Sequence_Id, 2, '0')) =  "
													+" ( SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.Sequence_Id, 2, '0'))) "
													+" FROM worker_details_info vdi1 WHERE  wdi.`Worker_info_id` = vdi1.`Worker_info_id`  AND vdi1.transaction_date <= CURRENT_DATE()  ) AND wdi.`customer_id`="+workerBadgeVo.getCustomerDetailsId()+" AND wdi.`company_id` ="+workerBadgeVo.getCompanyDetailsId()+" AND  wd.`vendor_id`="+workerBadgeVo.getVendorDetailsId()+" GROUP BY wdi.Worker_Id ORDER BY wname ").list();					
			for(Object object : tempList){	
				Object[] obj = (Object[]) object;
				workerlist.add(new SimpleObject((Integer)obj[0], obj[1]+""));
			}
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return workerlist;
	}


	@Override
	public List<WorkerBadgeGenerationVo> getWorkerDetailsForGrid(WorkerBadgeGenerationVo workerBadgeVo) {
		Session session = sessionFactory.getCurrentSession();
		List<WorkerBadgeGenerationVo> badgeGenerationVos = new ArrayList<WorkerBadgeGenerationVo>(); 
		try {
			
			String q = " SELECT distinct `Worker_Badge_Id`,`Badge_Code`,cdi.customer_name,  "
					+" com.company_name, vdi.vendor_name,CONCAT(IFNULL(wdi.`First_name`,''),' ',IFNULL(wdi.`Middle_name`,''),' ',IFNULL(wdi.`Last_name`,'')) AS wname, "
					+" wd.worker_code  "
					+" FROM `worker_badge_generation` badge  "
					+" INNER JOIN worker_details wd  ON (badge.`Worker_Id` = wd.`Worker_id`) "
					+" INNER JOIN worker_details_info wdi ON(wd.worker_id = wdi.worker_id)  "
					+" INNER JOIN `customer_details_info` cdi ON(wd.customer_id =cdi.customer_id)  "
					+" INNER JOIN `company_details_info` com ON (com.company_id = wd.company_id)  "
					+" INNER JOIN `vendor_details_info` vdi ON(vdi.vendor_id = wd.vendor_id)  "
					+" WHERE  CONCAT(DATE_FORMAT(wdi.transaction_date, '%Y%m%d'), LPAD(wdi.Sequence_Id, 2, '0')) =  " 
					+" (  "
					+" SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.Sequence_Id, 2, '0'))) " 
					+" FROM worker_details_info vdi1  "
					+" WHERE  wdi.`Worker_info_id` = vdi1.`Worker_info_id`      "
					+" AND vdi1.transaction_date <= CURRENT_DATE()    "
					+" )  "
					+" AND   "
					+" CONCAT(DATE_FORMAT(cdi.transaction_date, '%Y%m%d'), LPAD(cdi.`Customer_Sequence_Id`, 2, '0')) =   "
					+" (  "
					+" SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.`Customer_Sequence_Id`, 2, '0')))  "
					+" FROM customer_details_info vdi1  "
					+" WHERE cdi.`Customer_Info_Id` = vdi1.`Customer_Info_Id`    "
					+" AND vdi1.transaction_date <= CURRENT_DATE()    "
					+" )  "
					+" AND   "
					+" CONCAT(DATE_FORMAT(com.transaction_date, '%Y%m%d'), LPAD(com.Company_Sequence_Id, 2, '0')) =   "
					+" (  "
					+" SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.`Company_Sequence_Id`, 2, '0')))  "
					+" FROM company_details_info vdi1  "
					+" WHERE com.`Company_Info_Id` = vdi1.  `Company_Info_Id`  "
					+" AND vdi1.transaction_date <= CURRENT_DATE()    "
					+" )  "
					+" AND   "
					+" CONCAT(DATE_FORMAT(com.transaction_date, '%Y%m%d'), LPAD(com.Company_Sequence_Id, 2, '0')) =   "
					+" (  "
					+" SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.`Company_Sequence_Id`, 2, '0'))) " 
					+" FROM company_details_info vdi1  "
					+" WHERE com.`Company_Info_Id` = vdi1.  `Company_Info_Id`  "
					+" AND vdi1.transaction_date <= CURRENT_DATE()    "
					+" )  "
					+" AND   "
					+" CONCAT(DATE_FORMAT(vdi.transaction_date, '%Y%m%d'), LPAD(vdi.Sequence_Id, 2, '0')) =   "
					+" (  "
					+" SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.Sequence_Id, 2, '0')))  "
					+" FROM vendor_details_info vdi1  "
					+" WHERE vdi.`vendor_details_info_id` = vdi1.`vendor_details_info_id`  "
					+" AND vdi1.transaction_date <= CURRENT_DATE()  )  " ;
					
					 if(workerBadgeVo.getCustomerName() != null && !workerBadgeVo.getCustomerName().isEmpty()){						
						 q +=" and badge.Customer_Id = '"+workerBadgeVo.getCustomerName()+"'";				 
					 } 
					 if(workerBadgeVo.getCompanyName() != null && !workerBadgeVo.getCompanyName().isEmpty() && Integer.parseInt(workerBadgeVo.getCompanyName()) > 0 ){						 
						 q +=" and badge.Company_Id = '"+workerBadgeVo.getCompanyName()+"' ";	
					 } 
					 if(workerBadgeVo.getVendorName() != null && !workerBadgeVo.getVendorName().isEmpty() && Integer.parseInt(workerBadgeVo.getVendorName()) > 0){						 
						 q +=" and badge.Vendor_Id = '"+workerBadgeVo.getVendorName()+"'";	
					 }
					 
					 /*if(workerBadgeVo.getIsActive() != null && !workerBadgeVo.getIsActive().isEmpty()){
						 q +=" and badge.is_active = '"+workerBadgeVo.getIsActive()+"'";
					 }*/
					 if(workerBadgeVo.getWorkerCode() != null && !workerBadgeVo.getWorkerCode().isEmpty()){
						 q +=" and wd.worker_code = '"+workerBadgeVo.getWorkerCode()+"'";
					 }
					 if(workerBadgeVo.getWorkerName() != null && !workerBadgeVo.getWorkerName().isEmpty()){
						 q +=" and CONCAT(wdi.first_name,IFNULL(wdi.middle_name,''),IFNULL(wdi.last_name,'')) like '%"+workerBadgeVo.getWorkerName()+"%'";
					 }
					
					q=q+"  Order By Badge_Code asc ";
			
			
			
			List tempList =  session.createSQLQuery(q).list();
		for(Object object : tempList){	
			Object[] obj = (Object[]) object;
			WorkerBadgeGenerationVo badgeGenerationVo = new WorkerBadgeGenerationVo();
			badgeGenerationVo.setWorkerBadgeId((Integer)obj[0]);
			badgeGenerationVo.setBadgeCode(obj[1]+"");
			badgeGenerationVo.setCustomerName(obj[2]+"");
			badgeGenerationVo.setCompanyName(obj[3]+"");
			badgeGenerationVo.setVendorName(obj[4]+"");
			badgeGenerationVo.setWorkerName(obj[5]+"");
			//badgeGenerationVo.setIsActive( (obj[7]+""));
			badgeGenerationVos.add(badgeGenerationVo);
		}
	} catch (Exception e) {
		log.error("Error Occured ",e);
	}
	return badgeGenerationVos;				
	}



	@Override
	public Integer saveOrUpdateWorkerBadge(WorkerBadgeGenerationVo workerBadgeVo) {
		WorkerBadgeGeneration badgeGeneration = new WorkerBadgeGeneration();
		Integer badgeId = 0;
		try{
		if(workerBadgeVo != null && workerBadgeVo.getWorkerBadgeId() != null && workerBadgeVo.getWorkerBadgeId() > 0){
			badgeGeneration = (WorkerBadgeGeneration) sessionFactory.getCurrentSession().load(WorkerBadgeGeneration.class, workerBadgeVo.getWorkerBadgeId());
			badgeId = workerBadgeVo.getWorkerBadgeId();
		}
		badgeGeneration.setCustomerDetails(new CustomerDetails(workerBadgeVo.getCustomerDetailsId()));
		badgeGeneration.setCompanyDetails(new CompanyDetails(workerBadgeVo.getCompanyDetailsId()));
		badgeGeneration.setVendorDetails(new VendorDetails(workerBadgeVo.getVendorDetailsId()));
		badgeGeneration.setWorkerDetails(new WorkerDetails(workerBadgeVo.getWorkerDetailsId()));
		badgeGeneration.setCountry(workerBadgeVo.getCountry());
		badgeGeneration.setCardType(workerBadgeVo.getCardType());
		//badgeGeneration.setIsActive(workerBadgeVo.getIsActive());
		badgeGeneration.setBadgeCode(workerBadgeVo.getBadgeCode());
		badgeGeneration.setPpeIssued(workerBadgeVo.getPpeIssued() == true ? "Y" : "N");
		badgeGeneration.setDescription(workerBadgeVo.getDescription());
		badgeGeneration.setCreatedBy(workerBadgeVo.getCreatedBy());
		badgeGeneration.setCreatedDate(new Date());
		badgeGeneration.setModifiedBy(workerBadgeVo.getModifiedBy());
		badgeGeneration.setModifiedDate(new Date());
		if(workerBadgeVo != null && workerBadgeVo.getWorkerBadgeId() != null && workerBadgeVo.getWorkerBadgeId() > 0)
			sessionFactory.getCurrentSession().update(badgeGeneration);
		else
			badgeId = (Integer)sessionFactory.getCurrentSession().save(badgeGeneration);
		
		sessionFactory.getCurrentSession().flush();
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return badgeId;
	}



	@Override
	public List<WorkerBadgeGenerationVo> getWorkerBadgeRecordByBadgeId(WorkerBadgeGenerationVo workerBadgeVo) {
		WorkerBadgeGeneration badgeGeneration = new WorkerBadgeGeneration();
		List<WorkerBadgeGenerationVo> generationVos = new ArrayList<WorkerBadgeGenerationVo>();
		WorkerBadgeGenerationVo badgeGenerationVo = new WorkerBadgeGenerationVo();
		try{
		if(workerBadgeVo != null && workerBadgeVo.getWorkerBadgeId() != null && workerBadgeVo.getWorkerBadgeId() > 0){
			badgeGeneration = (WorkerBadgeGeneration) sessionFactory.getCurrentSession().load(WorkerBadgeGeneration.class, workerBadgeVo.getWorkerBadgeId());			
		}
		badgeGenerationVo.setWorkerBadgeId(badgeGeneration.getWorkerBadgeId());
		badgeGenerationVo.setCustomerDetailsId(badgeGeneration.getCustomerDetails().getCustomerId());
		badgeGenerationVo.setCompanyDetailsId(badgeGeneration.getCompanyDetails().getCompanyId());
		badgeGenerationVo.setVendorDetailsId(badgeGeneration.getVendorDetails().getVendorId());
		badgeGenerationVo.setWorkerDetailsId(badgeGeneration.getWorkerDetails().getWorkerId());
		badgeGenerationVo.setCountry(badgeGeneration.getCountry());
		badgeGenerationVo.setCardType(badgeGeneration.getCardType());
		//badgeGenerationVo.setIsActive(badgeGeneration.getIsActive());
		badgeGenerationVo.setBadgeCode(badgeGeneration.getBadgeCode());
		if(badgeGeneration.getPpeIssued() != null)
			badgeGenerationVo.setPpeIssued(badgeGeneration.getPpeIssued().equalsIgnoreCase("Y") ? true : false);
		log.info(badgeGeneration.getPpeIssued()+" ::::: badgeGeneration.getPpeIssued()");
		badgeGenerationVo.setDescription(badgeGeneration.getDescription());
		generationVos.add(badgeGenerationVo);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return generationVos;
	}
	
	
	@Override
	public boolean validateVendorBadgeCode(WorkerBadgeGenerationVo paramVendor) {
		boolean flag = true;
		List returnList = (sessionFactory.getCurrentSession()).createQuery("FROM WorkerBadgeGeneration WHERE customerDetails = "+paramVendor.getCustomerDetailsId()+
				  " AND companyDetails = "+paramVendor.getCompanyDetailsId()+" AND vendorDetails = '"+paramVendor.getVendorDetailsId()+"' and badgeCode = '"+paramVendor.getBadgeCode()+"'").list();
		if(returnList != null && returnList.size() > 0){
			flag = false;
		}else{
			flag = true;
		}
		
		return flag;
	}

	
	
}
