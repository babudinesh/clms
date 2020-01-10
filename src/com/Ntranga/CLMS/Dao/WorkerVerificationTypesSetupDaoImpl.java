package com.Ntranga.CLMS.Dao;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.Ntranga.CLMS.vo.WorkerMedicalExaminationVo;
import com.Ntranga.CLMS.vo.WorkerVerificationTypesSetupVo;
import com.Ntranga.core.DateHelper;
import com.Ntranga.core.CLMS.entities.CompanyDetails;
import com.Ntranga.core.CLMS.entities.CustomerDetails;
import com.Ntranga.core.CLMS.entities.Users;
import com.Ntranga.core.CLMS.entities.VendorDetails;
import com.Ntranga.core.CLMS.entities.WorkerDetails;
import com.Ntranga.core.CLMS.entities.WorkerMedicalExamination;
import com.Ntranga.core.CLMS.entities.WorkerPoliceVerification;
import com.Ntranga.core.CLMS.entities.WorkerVerificationTypesSetup;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import common.Logger;

@SuppressWarnings({"rawtypes"})
@Transactional
@Repository("workerVerificationTypeSetupDao")
public class WorkerVerificationTypesSetupDaoImpl implements  WorkerVerificationTypesSetupDao {

	private static Logger log = Logger.getLogger(WorkerVerificationTypesSetupDaoImpl.class);
	
	
	@Autowired
	private SessionFactory sessionFactory;

	
	
	public List<WorkerVerificationTypesSetupVo> getWorkerVerificationTypesSetupsGridData(WorkerVerificationTypesSetupVo setupVo) {
		List<WorkerVerificationTypesSetupVo> masterInfoMap = new ArrayList<WorkerVerificationTypesSetupVo>();	
		Session session = sessionFactory.getCurrentSession();	
		
		try{
			String q = " SELECT wd.Worker_Verification_Types_Setup_Id, wd.Country_Id, wd.Is_Active, wd.Is_Mandatiory,  "+ 
						" wd.Transaction_Date, wd.Verification_Frequency, wd.Verification_Type, wd.Company_Id, wd.Customer_Id,cdi.customer_name,com.company_name	  "+ 
						" FROM worker_verification_types_setup wd"
						+" INNER JOIN `customer_details_info` cdi ON(wd.customer_id =cdi.customer_id) "
						+" 	INNER JOIN `company_details_info` com ON (com.company_id = wd.company_id) "						
						+" WHERE  CONCAT(DATE_FORMAT(cdi.transaction_date, '%Y%m%d'), LPAD(cdi.Customer_Sequence_Id, 2, '0')) =  "
						+" ( "
						+" SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.Customer_Sequence_Id, 2, '0'))) "
						+" FROM customer_details_info vdi1 "
						+" WHERE  cdi.`Customer_id` = vdi1.`Customer_id`     "
						+"  AND vdi1.transaction_date <= CURRENT_DATE()   "
						+" ) "
						+" AND  "
						+" CONCAT(DATE_FORMAT(com.transaction_date, '%Y%m%d'), LPAD(com.`Company_Sequence_Id`, 2, '0')) =  "
						+" ( "
						+" SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.`Company_Sequence_Id`, 2, '0'))) "
						+" FROM company_details_info vdi1 "
						+" WHERE com.`company_id` = vdi1.`company_id`   "
						+"  AND vdi1.transaction_date <= CURRENT_DATE() )  " ;
					
			
			if(setupVo.getCustomerId() > 0){						
				 q +=" and wd.Customer_Id = '"+setupVo.getCustomerId()+"'";				 
			 } 
			 if(setupVo.getCompanyId() > 0){						 
				 q +=" and wd.Company_Id = '"+setupVo.getCompanyId()+"' ";	
			 } 
			 if(setupVo.getVerificationType() != null && !setupVo.getVerificationType().isEmpty() ){						 
				 q +=" and wd.Verification_Type = '"+setupVo.getVerificationType()+"'";	
			 }
			 
			 if(setupVo.getIsActive() != null && !setupVo.getIsActive().isEmpty()){
				 q +=" and wd.is_active = '"+setupVo.getIsActive()+"'";
			 }
			
			List tempList = session.createSQLQuery(q).list();
		
			for(Object o : tempList){
				Object[] obj = (Object[]) o;
				WorkerVerificationTypesSetupVo workerVerificationTypesSetup = new WorkerVerificationTypesSetupVo();
				workerVerificationTypesSetup.setWorkerVerificationTypesSetupId((Integer)obj[0]);
				workerVerificationTypesSetup.setCountryId((Integer)obj[1]);
				workerVerificationTypesSetup.setIsActive((String)obj[2]);
				workerVerificationTypesSetup.setMandatiory((String)obj[3]);
				workerVerificationTypesSetup.setTransactionDate((Date)obj[4]);
				workerVerificationTypesSetup.setVerificationFrequency((String)obj[5]);	
				workerVerificationTypesSetup.setVerificationType((String)obj[6]);
				workerVerificationTypesSetup.setCompanyId((Integer)obj[7]);
				workerVerificationTypesSetup.setCustomerId((Integer)obj[8]);
				workerVerificationTypesSetup.setCustomerName((String)obj[9]);
				workerVerificationTypesSetup.setCompanyName((String)obj[10]);
				masterInfoMap.add(workerVerificationTypesSetup);
				
			}
			
			
		}catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return masterInfoMap;
	}
	
	public List<WorkerVerificationTypesSetupVo> getWorkerVerificationTypesSetupsByWorkerVerificationTypesSetupId(int workerVerificationTypesSetupId) {
		List<WorkerVerificationTypesSetupVo> masterInfoMap = new ArrayList<WorkerVerificationTypesSetupVo>();	
		Session session = sessionFactory.getCurrentSession();
		WorkerVerificationTypesSetup setupVo = null;
		WorkerVerificationTypesSetupVo workerVerificationTypesSetup =  new WorkerVerificationTypesSetupVo();
		try{
			setupVo = (WorkerVerificationTypesSetup) session.load(WorkerVerificationTypesSetup.class,workerVerificationTypesSetupId) ;
			workerVerificationTypesSetup.setWorkerVerificationTypesSetupId(setupVo.getWorkerVerificationTypesSetupId());
			workerVerificationTypesSetup.setCustomerId(setupVo.getCustomerDetails().getCustomerId());
			workerVerificationTypesSetup.setCompanyId(setupVo.getCompanyDetails().getCompanyId());
			workerVerificationTypesSetup.setCountryId(setupVo.getCountryId());
			workerVerificationTypesSetup.setTransactionDate(setupVo.getTransactionDate());
			workerVerificationTypesSetup.setIsActive(setupVo.getIsActive());
			workerVerificationTypesSetup.setVerificationType(setupVo.getVerificationType());
			workerVerificationTypesSetup.setMandatiory(setupVo.getIsMandatiory());
			workerVerificationTypesSetup.setVerificationFrequency(setupVo.getVerificationFrequency());	
			masterInfoMap.add(workerVerificationTypesSetup);
		}catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return masterInfoMap;
	}

	


	public Integer saveOrUpdateVerificationTypesSetups(WorkerVerificationTypesSetupVo setupVo) {
		Integer workerVerificationTypesSetupId = 0;
		Session session = null;
		WorkerVerificationTypesSetup workerVerificationTypesSetup = null;
		try{
				
			session = sessionFactory.getCurrentSession();
			
			Criteria criteria = session.createCriteria(WorkerVerificationTypesSetup.class);
			criteria.add(Restrictions.eq("verificationType", setupVo.getVerificationType()));
		
			if(setupVo != null && setupVo.getCustomerId() != null && setupVo.getCustomerId() > 0)
				criteria.add(Restrictions.eq("customerDetails.customerId", setupVo.getCustomerId()));
			if(setupVo != null && setupVo.getCompanyId() != null && setupVo.getCompanyId() > 0)
				criteria.add(Restrictions.eq("companyDetails.companyId", setupVo.getCompanyId()));
			if(setupVo != null && setupVo.getWorkerVerificationTypesSetupId() != null && setupVo.getWorkerVerificationTypesSetupId() > 0)
				criteria.add(Restrictions.ne("workerVerificationTypesSetupId", setupVo.getWorkerVerificationTypesSetupId()));
			
			List duplicateList = criteria.list();
			if(duplicateList.size() > 0){
				return -1;
			}else{
				
			if(setupVo.getWorkerVerificationTypesSetupId() != null && setupVo.getWorkerVerificationTypesSetupId() > 0){
				workerVerificationTypesSetup = (WorkerVerificationTypesSetup) session.load(WorkerVerificationTypesSetup.class,setupVo.getWorkerVerificationTypesSetupId()) ;
				workerVerificationTypesSetupId = setupVo.getWorkerVerificationTypesSetupId();
			}else{
				workerVerificationTypesSetup = new WorkerVerificationTypesSetup(); 
			}
			workerVerificationTypesSetup.setCustomerDetails(new CustomerDetails(setupVo.getCustomerId()));
			workerVerificationTypesSetup.setCompanyDetails(new CompanyDetails(setupVo.getCompanyId()));
			workerVerificationTypesSetup.setCountryId(setupVo.getCountryId());
			workerVerificationTypesSetup.setTransactionDate(setupVo.getTransactionDate());
			workerVerificationTypesSetup.setIsActive(setupVo.getIsActive());
			workerVerificationTypesSetup.setVerificationType(setupVo.getVerificationType());
			
			workerVerificationTypesSetup.setIsMandatiory(setupVo.getMandatiory());
			workerVerificationTypesSetup.setVerificationFrequency(setupVo.getVerificationFrequency());			
			workerVerificationTypesSetup.setModifiedBy(setupVo.getModifiedBy());
			workerVerificationTypesSetup.setModifiedDate(new Date());
			
			if(setupVo.getWorkerVerificationTypesSetupId() != null && setupVo.getWorkerVerificationTypesSetupId() > 0){
				session.update(workerVerificationTypesSetup);
			}else{				
				workerVerificationTypesSetup.setCreatedBy(setupVo.getCreatedBy());
				workerVerificationTypesSetup.setCreatedDate(new Date());
				workerVerificationTypesSetupId = 	(Integer) session.save(workerVerificationTypesSetup);
			}
			
			}
			
			session.flush();
			
		}catch(Exception e){		
			log.error("Error Occured ",e);
			workerVerificationTypesSetupId = 0;
		}
		return workerVerificationTypesSetupId;
	}

	
	
	
	

}
