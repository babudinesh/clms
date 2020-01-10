package com.Ntranga.CLMS.Dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Ntranga.CLMS.vo.ApplicationApprovalPathFlowVo;
import com.Ntranga.CLMS.vo.ApplicationApprovalPathVo;
import com.Ntranga.CLMS.vo.ApprovalPathModuleVo;
import com.Ntranga.CLMS.vo.ApprovalPathTransactionVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.core.DateHelper;
import com.Ntranga.core.CLMS.entities.ApplicationApprovalPath;
import com.Ntranga.core.CLMS.entities.ApplicationApprovalPathFlow;
import com.Ntranga.core.CLMS.entities.ApprovalPathModule;
import com.Ntranga.core.CLMS.entities.ApprovalPathTransaction;
import com.Ntranga.core.CLMS.entities.ApprovalPathTransactionInfo;

import common.Logger;

@Transactional
@Repository("approvalPathModuleTransactionDao")
public class ApprovalPathModuleTransactionDaoImpl implements ApprovalPathModuleTransactionDao  {

	private static Logger log = Logger.getLogger(ApprovalPathModuleTransactionDaoImpl.class);
	
	
	@Autowired
	private SessionFactory sessionFactory;

	
	@Override
	public List<ApprovalPathModuleVo> getApprovalPathModules(ApprovalPathModuleVo approvalPathModuleVo) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<ApprovalPathModuleVo> returnList = new ArrayList<ApprovalPathModuleVo>();
		try{
				String query = " SELECT 	`approval_path_module_id`, `module_code`, `module_name`, `customer_id`, `company_id`, `is_active`, `created_date`, `modified_date` FROM `approval_path_module` apm where 1>0   ";
				if(approvalPathModuleVo!= null && approvalPathModuleVo.getApprovalPathModuleId() != null && approvalPathModuleVo.getApprovalPathModuleId() >0 ){
					query += "  and apm.approval_path_module_id="+approvalPathModuleVo.getApprovalPathModuleId();
				}
				if(approvalPathModuleVo!= null && approvalPathModuleVo.getCustomerId() != null && approvalPathModuleVo.getCustomerId() >0 ){
					query += "  and apm.customer_id="+approvalPathModuleVo.getCustomerId();
				}
				if(approvalPathModuleVo!= null && approvalPathModuleVo.getCompanyId() != null && approvalPathModuleVo.getCompanyId() >0 ){
					query += "  and apm.company_id="+approvalPathModuleVo.getCompanyId();
				}
				if(approvalPathModuleVo!= null && approvalPathModuleVo.getModuleCode() != null && !approvalPathModuleVo.getModuleCode().isEmpty() ){
					query += "  and apm.module_code='"+approvalPathModuleVo.getModuleCode()+"'";
				}
				if(approvalPathModuleVo!= null && approvalPathModuleVo.getModuleName() != null && !approvalPathModuleVo.getModuleName().isEmpty() ){
					query += "  and apm.module_name='"+approvalPathModuleVo.getModuleName()+"'";
				}
				if(approvalPathModuleVo!= null && approvalPathModuleVo.getIsActive() != null && !approvalPathModuleVo.getIsActive().isEmpty() ){
					query += "  and apm.is_active='"+approvalPathModuleVo.getIsActive()+"'";
				}
				query += "  ORDER BY module_code";
				List tempList = session.createSQLQuery(query).list();
				for(Object object : tempList){
					Object[] obj = (Object[]) object;
					ApprovalPathModuleVo moduleVo = new ApprovalPathModuleVo();
					moduleVo.setApprovalPathModuleId((Integer) obj[0]);
					moduleVo.setModuleCode(obj[1]+"");
					moduleVo.setModuleName(obj[2]+"");
					moduleVo.setCustomerId((Integer) obj[3]);
					moduleVo.setCompanyId((Integer) obj[4]);
					moduleVo.setIsActive(obj[5]+"");
					moduleVo.setCreatedDate(DateHelper.convertSQLDateToString( (java.sql.Date)obj[6], "dd/MM/yyyy" ));
					moduleVo.setModifiedDate(DateHelper.convertSQLDateToString( (java.sql.Date)obj[7], "dd/MM/yyyy" ));
					returnList.add( moduleVo);
				}
			}catch(Exception e){
				log.error("Error Occured ApprovalPathModuleTransactionDaoImpl --> getApprovalPathModules method ",e);
			}	
		return returnList;
	}


	@Override
	public Integer saveOrUpdateApprovalPathModuleDetails(ApprovalPathModuleVo approvalPathModuleVo) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Integer id = 0;
		try{
			if(approvalPathModuleVo!= null && approvalPathModuleVo.getApprovalPathModuleId() != null && approvalPathModuleVo.getApprovalPathModuleId() >0 ){
				Criteria criteria = session.createCriteria(ApprovalPathModule.class);
				criteria.add(Restrictions.eq("moduleCode", approvalPathModuleVo.getModuleCode()));
				if(approvalPathModuleVo!= null && approvalPathModuleVo.getApprovalPathModuleId() != null && approvalPathModuleVo.getApprovalPathModuleId() >0 ){
					criteria.add(Restrictions.ne("approvalPathModuleId", approvalPathModuleVo.getApprovalPathModuleId()));
				}
				
				/*if(approvalPathModuleVo!= null && approvalPathModuleVo.getApprovalPathModuleId() != null && approvalPathModuleVo.getApprovalPathModuleId() >0 ){
					criteria.add(Restrictions.ne("approvalPathModuleId", approvalPathModuleVo.getApprovalPathModuleId()));
				}
				if(approvalPathModuleVo!= null && approvalPathModuleVo.getCustomerId() != null && approvalPathModuleVo.getCustomerId() >0 ){
					criteria.add(Restrictions.ne("customerId", approvalPathModuleVo.getCustomerId()));
				}
				if(approvalPathModuleVo!= null && approvalPathModuleVo.getCompanyId() != null && approvalPathModuleVo.getCompanyId() >0 ){
					criteria.add(Restrictions.ne("companyId", approvalPathModuleVo.getCompanyId()));
				}*/
				List duplicateList = criteria.list();
				if(duplicateList.size() > 0){
					return -1;
				}else{
					ApprovalPathModule pathModule = (ApprovalPathModule) session.load(ApprovalPathModule.class, approvalPathModuleVo.getApprovalPathModuleId());
					pathModule.setModifiedBy(approvalPathModuleVo.getModifiedBy());
					pathModule.setModifiedDate(new Date());
					pathModule.setCustomerId(approvalPathModuleVo.getCustomerId());
					pathModule.setCompanyId(approvalPathModuleVo.getCompanyId());
					pathModule.setModuleCode(approvalPathModuleVo.getModuleCode());
					pathModule.setModuleName(approvalPathModuleVo.getModuleName());
					pathModule.setIsActive(approvalPathModuleVo.getIsActive());								
					id = (Integer) session.save(pathModule);
				}
			} else{
				ApprovalPathModule pathModule = new ApprovalPathModule();
				pathModule.setCreatedBy(approvalPathModuleVo.getCreatedBy());
				pathModule.setCreatedDate(new Date());
				pathModule.setModifiedBy(approvalPathModuleVo.getModifiedBy());
				pathModule.setModifiedDate(new Date());
				pathModule.setCustomerId(approvalPathModuleVo.getCustomerId());
				pathModule.setCompanyId(approvalPathModuleVo.getCompanyId());
				pathModule.setModuleCode(approvalPathModuleVo.getModuleCode());
				pathModule.setModuleName(approvalPathModuleVo.getModuleName());
				pathModule.setIsActive(approvalPathModuleVo.getIsActive());								
				id = (Integer) session.save(pathModule);
			}
			session.flush();
		}catch(Exception e){
			log.error("Error Occured ApprovalPathModuleTransactionDaoImpl --> saveOrUpdateApprovalPathModuleDetails method ",e);
		}	
		return id;
	}


	@Override
	public List<ApprovalPathTransactionVo> getApprovalPathTransactions(
			ApprovalPathTransactionVo approvalPathTransactionVo) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<ApprovalPathTransactionVo> returnList = new ArrayList<ApprovalPathTransactionVo>();
		try{
				String query = " SELECT apm.`approval_path_module_id`, apm.`module_code`, apm.`module_name`,apt.`approval_path_transaction_id`,apt.`transaction_code`,apt.`is_active`,apti.`transaction_name`,apti.`effective_date`,apt.`customer_id`,apt.`company_id`,apti.`created_date`,apti.`modified_date`,approval_path_transaction_info_id "
								+" FROM `approval_path_transaction_info` apti INNER JOIN `approval_path_transaction` apt ON (apti.`approval_path_transaction_id` = apt.`approval_path_transaction_id`) "
								+" INNER JOIN `approval_path_module` apm ON (apt.`approval_path_module_id` = apm.`approval_path_module_id`) "
								+" WHERE CONCAT(DATE_FORMAT(apti.effective_date, '%Y%m%d'), LPAD(apti.Sequence_Id, 2, '0')) =    "
								+" ( SELECT MAX(CONCAT(DATE_FORMAT(apti1.effective_date, '%Y%m%d'), LPAD(apti1.Sequence_Id, 2, '0'))) "  
								+" FROM approval_path_transaction_info apti1   "
								+" WHERE  apti.approval_path_transaction_id = apti1.approval_path_transaction_id " 
								+" AND apti1.effective_date <= CURRENT_DATE() )   ";
				if(approvalPathTransactionVo!= null && approvalPathTransactionVo.getApprovalPathTransactionInfoId() != null && approvalPathTransactionVo.getApprovalPathTransactionInfoId() >0 ){
					query += "  and apti.approval_path_transaction_info_id="+approvalPathTransactionVo.getApprovalPathTransactionInfoId();
				}
				if(approvalPathTransactionVo!= null && approvalPathTransactionVo.getCustomerId() != null && approvalPathTransactionVo.getCustomerId() >0 ){
					query += "  and apt.customer_id="+approvalPathTransactionVo.getCustomerId();
				}
				if(approvalPathTransactionVo!= null && approvalPathTransactionVo.getCompanyId() != null && approvalPathTransactionVo.getCompanyId() >0 ){
					query += "  and apt.company_id="+approvalPathTransactionVo.getCompanyId();
				}
				if(approvalPathTransactionVo!= null && approvalPathTransactionVo.getApprovalPathModuleId()!= null && approvalPathTransactionVo.getApprovalPathModuleId() >0 ){
					query += "  and apt.approval_path_module_id="+approvalPathTransactionVo.getApprovalPathModuleId();
				}
				if(approvalPathTransactionVo!= null && approvalPathTransactionVo.getTransactionCode() != null && !approvalPathTransactionVo.getTransactionCode().isEmpty() ){
					query += "  and apt.transaction_code='"+approvalPathTransactionVo.getTransactionCode()+"'";
				}
				if(approvalPathTransactionVo!= null && approvalPathTransactionVo.getTransactionName() != null && !approvalPathTransactionVo.getTransactionName().isEmpty() ){
					query += "  and apti.transaction_name='"+approvalPathTransactionVo.getTransactionName()+"'";
				}
				if(approvalPathTransactionVo!= null && approvalPathTransactionVo.getIsActive() != null && !approvalPathTransactionVo.getIsActive().isEmpty() ){
					query += "  and apt.is_active='"+approvalPathTransactionVo.getIsActive()+"'";
				}
				query += "  ORDER BY transaction_code";
				List tempList = session.createSQLQuery(query).list();
				for(Object object : tempList){
					Object[] obj = (Object[]) object;
					ApprovalPathTransactionVo transactionVo = new ApprovalPathTransactionVo();
					transactionVo.setApprovalPathModuleId((Integer) obj[0]);
					transactionVo.setModuleCode(obj[1]+"");
					transactionVo.setModuleName(obj[2]+"");
					transactionVo.setApprovalPathTransactionId((Integer) obj[3]);
					transactionVo.setTransactionCode(obj[4]+"");
					transactionVo.setIsActive(obj[5]+"");
					transactionVo.setTransactionName(obj[6]+"");
					transactionVo.setEffectiveStringDate(DateHelper.convertSQLDateToString( (java.sql.Date)obj[7], "dd/MM/yyyy" ));
					transactionVo.setEffectiveDate( (Date)obj[7]);
					transactionVo.setCustomerId((Integer) obj[8]);
					transactionVo.setCompanyId((Integer) obj[9]);
					transactionVo.setCreatedDate(DateHelper.convertSQLDateToString( (java.sql.Date)obj[10], "dd/MM/yyyy" ));
					transactionVo.setModifiedDate(DateHelper.convertSQLDateToString( (java.sql.Date)obj[11], "dd/MM/yyyy" ));
					transactionVo.setApprovalPathTransactionInfoId((Integer) obj[12]);
					returnList.add(transactionVo);
				}
			}catch(Exception e){
				log.error("Error Occured ApprovalPathModuleTransactionDaoImpl --> getApprovalPathTransactions method ",e);
			}	
		return returnList;
	}


	@Override
	public Integer saveOrUpdateApprovalPathTransactionDetails(ApprovalPathTransactionVo approvalPathTransactionVo) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Integer id = 0, tid = 0;
		try{
			Criteria criteria = session.createCriteria(ApprovalPathTransaction.class);
			criteria.add(Restrictions.eq("transactionCode", approvalPathTransactionVo.getTransactionCode()));
			if(approvalPathTransactionVo!= null && approvalPathTransactionVo.getApprovalPathTransactionId() != null && approvalPathTransactionVo.getApprovalPathTransactionId() >0 ){
				criteria.add(Restrictions.ne("approvalPathTransactionId", approvalPathTransactionVo.getApprovalPathTransactionId()));
			}
			
			List duplicateList = criteria.list();
			if(duplicateList.size() > 0){
				return -1;
			}
			
			ApprovalPathTransaction approvalPathTransaction = new ApprovalPathTransaction();
			if(approvalPathTransactionVo !=null && approvalPathTransactionVo.getApprovalPathTransactionId() != null && approvalPathTransactionVo.getApprovalPathTransactionId() > 0){
				approvalPathTransaction = (ApprovalPathTransaction) session.load(ApprovalPathTransaction.class, approvalPathTransactionVo.getApprovalPathTransactionId());
			}else{
				approvalPathTransaction.setCreatedBy(approvalPathTransactionVo.getCreatedBy());
				approvalPathTransaction.setCreatedDate(new Date());
			}
			approvalPathTransaction.setModifiedBy(approvalPathTransactionVo.getModifiedBy());
			approvalPathTransaction.setModifiedDate(new Date());
			approvalPathTransaction.setApprovalPathModule(new ApprovalPathModule(approvalPathTransactionVo.getApprovalPathModuleId()));
			approvalPathTransaction.setCustomerId(approvalPathTransactionVo.getCustomerId());
			approvalPathTransaction.setCompanyId(approvalPathTransactionVo.getCompanyId());
			approvalPathTransaction.setIsActive(approvalPathTransactionVo.getIsActive());
			approvalPathTransaction.setTransactionCode(approvalPathTransactionVo.getTransactionCode());
			tid = (Integer) session.save(approvalPathTransaction);
			
			ApprovalPathTransactionInfo approvalPathTransactionInfo = new ApprovalPathTransactionInfo();
			if(approvalPathTransactionVo !=null && approvalPathTransactionVo.getApprovalPathTransactionInfoId() != null && approvalPathTransactionVo.getApprovalPathTransactionInfoId() > 0){
				approvalPathTransactionInfo = (ApprovalPathTransactionInfo) session.load(ApprovalPathTransactionInfo.class, approvalPathTransactionVo.getApprovalPathTransactionInfoId());
			}else{
				approvalPathTransactionInfo.setCreatedBy(approvalPathTransactionVo.getCreatedBy());
				approvalPathTransactionInfo.setCreatedDate(new Date());
				BigInteger seq = (BigInteger) session.createSQLQuery("SELECT COALESCE(COUNT(*),0) FROM `approval_path_transaction_info` WHERE `approval_path_transaction_id` = "+tid).list().get(0);
				approvalPathTransactionInfo.setSequenceId(seq.intValue()+1);
			}
			approvalPathTransactionInfo.setApprovalPathTransaction(approvalPathTransaction);
			approvalPathTransactionInfo.setTransactionName(approvalPathTransactionVo.getTransactionName());
			approvalPathTransactionInfo.setEffectiveDate(approvalPathTransactionVo.getEffectiveDate());
			approvalPathTransactionInfo.setModifiedBy(approvalPathTransactionVo.getModifiedBy());
			approvalPathTransactionInfo.setModifiedDate(new Date());
			id = (Integer) session.save(approvalPathTransactionInfo);
			
			session.flush();
		}catch(Exception e){
			log.error("Error Occured ApprovalPathModuleTransactionDaoImpl --> saveOrUpdateApprovalPathTransactionDetails method ",e);
		}	
		return id;
	}


	@Override
	public List<SimpleObject> getTransationHistoryDatesList(ApprovalPathTransactionVo approvalPathTransactionVo) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> transactionList = new ArrayList<>();
		try {
			List contactList = session.createSQLQuery("SELECT `approval_path_transaction_info_id` AS id ,CONCAT(DATE_FORMAT(effective_Date,'%d/%m/%Y'),' - ',`sequence_id`) AS cname FROM `approval_path_transaction_info` apti WHERE `approval_path_transaction_id` ="+approvalPathTransactionVo.getApprovalPathTransactionId()+" ORDER BY `effective_date`,`sequence_id`").list();
			for (Object transDates  : contactList) {
				Object[] transaction = (Object[]) transDates;
				SimpleObject object = new SimpleObject();
				object.setId((Integer)transaction[0]);
				object.setName(transaction[1]+"");
				transactionList.add(object);
			}			
		} catch (Exception e) {
			log.error("Error Occurred...   ", e);
			log.info("Exiting from getTransactionListForContact()  ::   "+transactionList);
		}
		
		log.info("Exiting from getTransactionListForContact()  ::   "+transactionList);
		return transactionList;

	}


	@Override
	public ApprovalPathTransactionVo getApprovalPathTransactionByTransactionInfoId(
			ApprovalPathTransactionVo approvalPathTransactionVo) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		ApprovalPathTransactionVo transactionVo = new ApprovalPathTransactionVo();
		try{
				String query = " SELECT apm.`approval_path_module_id`, apm.`module_code`, apm.`module_name`,apt.`approval_path_transaction_id`,apt.`transaction_code`,apt.`is_active`,apti.`transaction_name`,apti.`effective_date`,apt.`customer_id`,apt.`company_id`,apti.`created_date`,apti.`modified_date`,approval_path_transaction_info_id "
								+" FROM `approval_path_transaction_info` apti INNER JOIN `approval_path_transaction` apt ON (apti.`approval_path_transaction_id` = apt.`approval_path_transaction_id`) "
								+" INNER JOIN `approval_path_module` apm ON (apt.`approval_path_module_id` = apm.`approval_path_module_id`) "
								+" WHERE  1 = 1 ";
				if(approvalPathTransactionVo!= null && approvalPathTransactionVo.getApprovalPathTransactionInfoId() != null && approvalPathTransactionVo.getApprovalPathTransactionInfoId() >0 ){
					query += "  and apti.approval_path_transaction_info_id="+approvalPathTransactionVo.getApprovalPathTransactionInfoId();
				}
				if(approvalPathTransactionVo!= null && approvalPathTransactionVo.getCustomerId() != null && approvalPathTransactionVo.getCustomerId() >0 ){
					query += "  and apt.customer_id="+approvalPathTransactionVo.getCustomerId();
				}
				if(approvalPathTransactionVo!= null && approvalPathTransactionVo.getCompanyId() != null && approvalPathTransactionVo.getCompanyId() >0 ){
					query += "  and apt.company_id="+approvalPathTransactionVo.getCompanyId();
				}
				if(approvalPathTransactionVo!= null && approvalPathTransactionVo.getApprovalPathModuleId()!= null && approvalPathTransactionVo.getApprovalPathModuleId() >0 ){
					query += "  and apt.approval_path_module_id="+approvalPathTransactionVo.getApprovalPathModuleId();
				}
				if(approvalPathTransactionVo!= null && approvalPathTransactionVo.getTransactionCode() != null && !approvalPathTransactionVo.getTransactionCode().isEmpty() ){
					query += "  and apt.transaction_code='"+approvalPathTransactionVo.getTransactionCode()+"'";
				}
				if(approvalPathTransactionVo!= null && approvalPathTransactionVo.getTransactionName() != null && !approvalPathTransactionVo.getTransactionName().isEmpty() ){
					query += "  and apti.transaction_name='"+approvalPathTransactionVo.getTransactionName()+"'";
				}
				if(approvalPathTransactionVo!= null && approvalPathTransactionVo.getIsActive() != null && !approvalPathTransactionVo.getIsActive().isEmpty() ){
					query += "  and apt.is_active='"+approvalPathTransactionVo.getIsActive()+"'";
				}
				query += "  ORDER BY transaction_code";
				List tempList = session.createSQLQuery(query).list();
				for(Object object : tempList){
					Object[] obj = (Object[]) object;
					transactionVo.setApprovalPathModuleId((Integer) obj[0]);
					transactionVo.setModuleCode(obj[1]+"");
					transactionVo.setModuleName(obj[2]+"");
					transactionVo.setApprovalPathTransactionId((Integer) obj[3]);
					transactionVo.setTransactionCode(obj[4]+"");
					transactionVo.setIsActive(obj[5]+"");
					transactionVo.setTransactionName(obj[6]+"");
					transactionVo.setEffectiveStringDate(DateHelper.convertSQLDateToString( (java.sql.Date)obj[7], "dd/MM/yyyy" ));
					transactionVo.setEffectiveDate( (Date)obj[7]);
					transactionVo.setCustomerId((Integer) obj[8]);
					transactionVo.setCompanyId((Integer) obj[9]);
					transactionVo.setCreatedDate(DateHelper.convertSQLDateToString( (java.sql.Date)obj[10], "dd/MM/yyyy" ));
					transactionVo.setModifiedDate(DateHelper.convertSQLDateToString( (java.sql.Date)obj[11], "dd/MM/yyyy" ));
					transactionVo.setApprovalPathTransactionInfoId((Integer) obj[12]);
				}
			}catch(Exception e){
				log.error("Error Occured ApprovalPathModuleTransactionDaoImpl --> getApprovalPathTransactions method ",e);
			}	
		return transactionVo;
	}


	@Override
	public List<ApplicationApprovalPathVo> getApplicationApprovalPaths(
			ApplicationApprovalPathVo applicationApprovalPathVo) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<ApplicationApprovalPathVo> returnList = new ArrayList<ApplicationApprovalPathVo>();
		try{
				String query = " SELECT `application_approval_path_id`,`path_code`,`path_name`,apm.`approval_path_module_id`, apm.`module_code`, apm.`module_name`,apt.`approval_path_transaction_id`, apt.`transaction_code`, apti.`transaction_name`,app.is_active, app.`customer_id`,app.`company_id`,app.`created_date`,app.`modified_date`  "
								+" FROM `application_approval_path` app INNER JOIN `approval_path_transaction_info` apti ON (app.`approval_path_transaction_id`=apti.`approval_path_transaction_id`) INNER JOIN `approval_path_transaction` apt ON (apti.`approval_path_transaction_id` = apt.`approval_path_transaction_id`) "
								+" INNER JOIN `approval_path_module` apm ON (apt.`approval_path_module_id` = apm.`approval_path_module_id`) "
								+" WHERE CONCAT(DATE_FORMAT(apti.effective_date, '%Y%m%d'), LPAD(apti.Sequence_Id, 2, '0')) = ( SELECT MAX(CONCAT(DATE_FORMAT(apti1.effective_date, '%Y%m%d'), LPAD(apti1.Sequence_Id, 2, '0'))) "  
								+" FROM approval_path_transaction_info apti1 WHERE  apti.approval_path_transaction_id = apti1.approval_path_transaction_id  AND apti1.effective_date <= CURRENT_DATE() ) ";
				if(applicationApprovalPathVo!= null && applicationApprovalPathVo.getApplicationApprovalPathId() != null && applicationApprovalPathVo.getApplicationApprovalPathId() >0 ){
					query += "  and application_approval_path_id="+applicationApprovalPathVo.getApplicationApprovalPathId();
				}
				if(applicationApprovalPathVo!= null && applicationApprovalPathVo.getCustomerId() != null && applicationApprovalPathVo.getCustomerId() >0 ){
					query += "  and app.customer_id="+applicationApprovalPathVo.getCustomerId();
				}
				if(applicationApprovalPathVo!= null && applicationApprovalPathVo.getCompanyId() != null && applicationApprovalPathVo.getCompanyId() >0 ){
					query += "  and app.company_id="+applicationApprovalPathVo.getCompanyId();
				}
				if(applicationApprovalPathVo!= null && applicationApprovalPathVo.getApprovalPathModuleId()!= null && applicationApprovalPathVo.getApprovalPathModuleId() >0 ){
					query += "  and app.approval_path_module_id="+applicationApprovalPathVo.getApprovalPathModuleId();
				}
				if(applicationApprovalPathVo!= null && applicationApprovalPathVo.getApprovalPathTransactionId() != null && applicationApprovalPathVo.getApprovalPathTransactionId() >0 ){
					query += "  and app.approval_path_transaction_id="+applicationApprovalPathVo.getApprovalPathTransactionId();
				}
				if(applicationApprovalPathVo!= null && applicationApprovalPathVo.getPathCode() != null && !applicationApprovalPathVo.getPathCode().isEmpty() ){
					query += "  and app.path_code Like '%"+applicationApprovalPathVo.getPathCode()+"%'";
				}
				if(applicationApprovalPathVo!= null && applicationApprovalPathVo.getPathName() != null && !applicationApprovalPathVo.getPathName().isEmpty() ){
					query += "  and app.path_name Like '%"+applicationApprovalPathVo.getPathName()+"%'";
				}
				if(applicationApprovalPathVo!= null && applicationApprovalPathVo.getIsActive() != null && !applicationApprovalPathVo.getIsActive().isEmpty() ){
					query += "  and app.is_active='"+applicationApprovalPathVo.getIsActive()+"'";
				}
				query += "  ORDER BY path_code";
				
				List tempList = session.createSQLQuery(query).list();
				for(Object object : tempList){
					Object[] obj = (Object[]) object;
					ApplicationApprovalPathVo transactionVo = new ApplicationApprovalPathVo();
					transactionVo.setApplicationApprovalPathId((Integer) obj[0]);
					transactionVo.setPathCode(obj[1]+"");
					transactionVo.setPathName(obj[2]+"");
					transactionVo.setApprovalPathModuleId((Integer) obj[3]);
					transactionVo.setModuleCode(obj[4]+"");
					transactionVo.setModuleName(obj[5]+"");
					transactionVo.setApprovalPathTransactionId((Integer) obj[6]);
					transactionVo.setTransactionCode(obj[7]+"");					
					transactionVo.setTransactionName(obj[8]+"");
					transactionVo.setIsActive(obj[9]+"");
					transactionVo.setCustomerId((Integer) obj[10]);
					transactionVo.setCompanyId((Integer) obj[11]);
					transactionVo.setCreatedDate(DateHelper.convertSQLDateToString( (java.sql.Date)obj[12], "dd/MM/yyyy" ));
					transactionVo.setModifiedDate(DateHelper.convertSQLDateToString( (java.sql.Date)obj[13], "dd/MM/yyyy" ));
					returnList.add(transactionVo);
				}
			}catch(Exception e){
				log.error("Error Occured ApprovalPathModuleTransactionDaoImpl --> getApprovalPathTransactions method ",e);
			}	
		return returnList;
	}


	@Override
	public ApplicationApprovalPathVo getApplicationApprovalPathsById(
			ApplicationApprovalPathVo applicationApprovalPathVo) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		ApplicationApprovalPathVo transactionVo = new ApplicationApprovalPathVo();
		try{
			String query = " SELECT `application_approval_path_id`,`path_code`,`path_name`,apm.`approval_path_module_id`, apm.`module_code`, apm.`module_name`,apt.`approval_path_transaction_id`, apt.`transaction_code`, apti.`transaction_name`,app.is_active, app.`customer_id`,app.`company_id`,app.`created_date`,app.`modified_date`,`no_approval_required`,`no_of_approval_levels`   "
					+" FROM `application_approval_path` app INNER JOIN `approval_path_transaction_info` apti ON (app.`approval_path_transaction_id`=apti.`approval_path_transaction_id`) INNER JOIN `approval_path_transaction` apt ON (apti.`approval_path_transaction_id` = apt.`approval_path_transaction_id`) "
					+" INNER JOIN `approval_path_module` apm ON (apt.`approval_path_module_id` = apm.`approval_path_module_id`) "
					+" WHERE CONCAT(DATE_FORMAT(apti.effective_date, '%Y%m%d'), LPAD(apti.Sequence_Id, 2, '0')) = ( SELECT MAX(CONCAT(DATE_FORMAT(apti1.effective_date, '%Y%m%d'), LPAD(apti1.Sequence_Id, 2, '0'))) "  
					+" FROM approval_path_transaction_info apti1 WHERE  apti.approval_path_transaction_id = apti1.approval_path_transaction_id  AND apti1.effective_date <= CURRENT_DATE() ) ";
			
			if(applicationApprovalPathVo!= null && applicationApprovalPathVo.getApplicationApprovalPathId() != null && applicationApprovalPathVo.getApplicationApprovalPathId() >0 ){
				query += "  and application_approval_path_id="+applicationApprovalPathVo.getApplicationApprovalPathId();
			}
	
			List tempList = session.createSQLQuery(query).list();
			for(Object object : tempList){
				Object[] obj = (Object[]) object;
				transactionVo.setApplicationApprovalPathId((Integer) obj[0]);
				transactionVo.setPathCode(obj[1]+"");
				transactionVo.setPathName(obj[2]+"");
				transactionVo.setApprovalPathModuleId((Integer) obj[3]);
				transactionVo.setModuleCode(obj[4]+"");
				transactionVo.setModuleName(obj[5]+"");
				transactionVo.setApprovalPathTransactionId((Integer) obj[6]);
				transactionVo.setTransactionCode(obj[7]+"");					
				transactionVo.setTransactionName(obj[8]+"");
				transactionVo.setIsActive(obj[9]+"");
				transactionVo.setCustomerId((Integer) obj[10]);
				transactionVo.setCompanyId((Integer) obj[11]);
				transactionVo.setCreatedDate(DateHelper.convertSQLDateToString( (java.sql.Date)obj[12], "dd/MM/yyyy" ));
				transactionVo.setModifiedDate(DateHelper.convertSQLDateToString( (java.sql.Date)obj[13], "dd/MM/yyyy" ));
				if(obj[14] != null)
					transactionVo.setNoApprovalRequired(obj[14]+"");
				if(obj[15] != null)
					transactionVo.setNoOfApprovalLevels((Integer) obj[15]);
				
				if(obj[14] == null || (obj[14]+"").equalsIgnoreCase("N")){
					String childQuery = "SELECT `application_approval_path_id`, appf.`from_role_id`, appf.`from_user_id`,r.role_name, u.user_name ,appf.`to_role_id`, appf.`to_user_id`,torole.role_name as rname, toUser.user_name as uname, appf.approver_status FROM `application_approval_path_flow` appf INNER JOIN roles r ON (appf.from_role_id = r.role_id) LEFT JOIN users u ON (appf.from_user_id =u.user_id) LEFT JOIN roles torole ON (appf.to_role_id = torole.role_id) LEFT JOIN users toUser ON (appf.to_user_id =toUser.user_id)  "
							+ "  WHERE `application_approval_path_id` = "+obj[0]+" AND appf.`is_active`='Y'";
					List childList = session.createSQLQuery(childQuery).list();
					for(Object childObject : childList){
						Object[] childobj = (Object[]) childObject;
						ApplicationApprovalPathFlowVo appfvo = new ApplicationApprovalPathFlowVo();
						if(childobj[1] != null)
							appfvo.setFromRoleId((Integer) childobj[1]);
						if(childobj[2] != null)
							appfvo.setFromUserId((Integer) childobj[2]);
						if(childobj[3] != null)
							appfvo.setFromRoleName(childobj[3]+"");
						if(childobj[4] != null)
							appfvo.setFromUserName(childobj[4]+"");
						
						if(childobj[5] != null)
							appfvo.setToRoleId((Integer) childobj[5]);
						if(childobj[6] != null)
							appfvo.setToUserId((Integer) childobj[6]);
						if(childobj[7] != null)
							appfvo.setToRoleName(childobj[7]+"");
						if(childobj[8] != null)
							appfvo.setToUserName(childobj[8]+"");
						
						appfvo.setApproverStatus(childobj[9]+"");
						transactionVo.getFlowList().add(appfvo);
					}
				}
			}
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return transactionVo;
	}


	@Override
	public Integer saveOrUpdateApprovalFlowDetails(ApplicationApprovalPathVo applicationApprovalPathVo) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Integer id = 0;
		try{
				Criteria criteria = session.createCriteria(ApplicationApprovalPath.class);
				criteria.add(Restrictions.eq("pathCode", applicationApprovalPathVo.getPathCode()));
				if(applicationApprovalPathVo!= null && applicationApprovalPathVo.getApplicationApprovalPathId()!= null && applicationApprovalPathVo.getApplicationApprovalPathId() >0 ){
					criteria.add(Restrictions.ne("applicationApprovalPathId", applicationApprovalPathVo.getApplicationApprovalPathId()));
				}
				
				List duplicateList = criteria.list();
				if(duplicateList.size() > 0){
					return -1;
				}
				
				ApplicationApprovalPath pathModule = new ApplicationApprovalPath();
				if(applicationApprovalPathVo!= null && applicationApprovalPathVo.getApplicationApprovalPathId() != null && applicationApprovalPathVo.getApplicationApprovalPathId() >0 ){
					pathModule = (ApplicationApprovalPath) session.load(ApplicationApprovalPath.class, applicationApprovalPathVo.getApplicationApprovalPathId());
					pathModule.setModifiedBy(applicationApprovalPathVo.getModifiedBy());
					pathModule.setModifiedDate(new Date());
					id = (Integer) session.save(pathModule);
					session.createSQLQuery("UPDATE `application_approval_path_flow` SET `is_active` = 'N' WHERE `application_approval_path_id` = "+id).executeUpdate();
				} else{
					pathModule.setCreatedBy(applicationApprovalPathVo.getCreatedBy());
					pathModule.setCreatedDate(new Date());
					pathModule.setModifiedBy(applicationApprovalPathVo.getModifiedBy());
					pathModule.setModifiedDate(new Date());
				}
				pathModule.setCustomerId(applicationApprovalPathVo.getCustomerId());
				pathModule.setCompanyId(applicationApprovalPathVo.getCompanyId());
				pathModule.setPathCode(applicationApprovalPathVo.getPathCode());
				pathModule.setPathName(applicationApprovalPathVo.getPathName());
				pathModule.setIsActive(applicationApprovalPathVo.getIsActive());
				pathModule.setApprovalPathModuleId(applicationApprovalPathVo.getApprovalPathModuleId());
				pathModule.setApprovalPathTransactionId(applicationApprovalPathVo.getApprovalPathTransactionId());
				pathModule.setNoApprovalRequired(applicationApprovalPathVo.getNoApprovalRequired());
				pathModule.setNoOfApprovalLevels(applicationApprovalPathVo.getNoOfApprovalLevels());
				id = (Integer) session.save(pathModule);
				
				for(ApplicationApprovalPathFlowVo flowVo :applicationApprovalPathVo.getFlowList()){
					ApplicationApprovalPathFlow flow = new ApplicationApprovalPathFlow();
					flow.setApplicationApprovalPath(pathModule);
					flow.setFromRoleId(flowVo.getFromRoleId());					
					if(flowVo.getFromUserId() != null)
						flow.setFromUserId(flowVo.getFromUserId());
					if(flowVo.getToRoleId() != null)
						flow.setToRoleId(flowVo.getToRoleId());
					if(flowVo.getToUserId() != null)
						flow.setToUserId(flowVo.getToUserId());
					flow.setApproverStatus(flowVo.getApproverStatus());
					flow.setIsActive("Y");
					flow.setCreatedDate(new Date());
					flow.setModifiedDate(new Date());
					flow.setCreatedBy(applicationApprovalPathVo.getCreatedBy());
					flow.setModifiedBy( applicationApprovalPathVo.getModifiedBy());
					session.merge(flow);
				}
				session.flush();
		}catch(Exception e){
			log.error("Error Occured ApprovalPathModuleTransactionDaoImpl --> saveOrUpdateApprovalFlowDetails method ",e);
		}	
		return id;
	}



	
}
