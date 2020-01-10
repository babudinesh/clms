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

import com.Ntranga.CLMS.vo.DepartmentDivisionVo;
import com.Ntranga.CLMS.vo.DepartmentVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.VerificationTypesSetupVo;
import com.Ntranga.core.DateHelper;
import com.Ntranga.core.CLMS.entities.CompanyDetails;
import com.Ntranga.core.CLMS.entities.CustomerDetails;
import com.Ntranga.core.CLMS.entities.DepartmentDetails;
import com.Ntranga.core.CLMS.entities.DepartmentDetailsInfo;
import com.Ntranga.core.CLMS.entities.DepartmentDivision;
import com.Ntranga.core.CLMS.entities.DivisionDetails;
import com.Ntranga.core.CLMS.entities.MDepartment;
import com.Ntranga.core.CLMS.entities.VerificationTypesSetup;

import common.Logger;

@Transactional
@Repository("verificationTypesSetupDao")
@SuppressWarnings({"rawtypes"})
public class VerificationTypesSetupDaoImpl implements VerificationTypesSetupDao {

	private static Logger log = Logger.getLogger(VerificationTypesSetupDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Integer saveDepartment(VerificationTypesSetupVo verificationTypesSetupVo) {
		log.info("Entered in saveDepartment()  ::   "+verificationTypesSetupVo);

		Session session = sessionFactory.getCurrentSession();
		VerificationTypesSetup verificationTypesSetup = new VerificationTypesSetup();
		Integer verificationTypesSetupId = 0;
		try{		
		
			verificationTypesSetup.setCustomerDetails(new CustomerDetails(verificationTypesSetupVo.getCustomerId()));
			verificationTypesSetup.setCompanyDetails(new CompanyDetails(verificationTypesSetupVo.getCompanyId()));
			verificationTypesSetup.setCountryId(verificationTypesSetupVo.getCountryId());
			verificationTypesSetup.setTransactionDate(verificationTypesSetupVo.getTransactionDate());
			verificationTypesSetup.setIsActive(verificationTypesSetupVo.getIsActive());
			verificationTypesSetup.setVerificationType(verificationTypesSetupVo.getVerificationType());
			verificationTypesSetup.setVerificationFrequency(verificationTypesSetupVo.getVerificationFrequency());
			verificationTypesSetup.setIsMandatory(verificationTypesSetupVo.getIsMandatory());
			if(verificationTypesSetupVo != null && verificationTypesSetupVo.getVerificationTypesSetupId() > 0){
				verificationTypesSetup.setModifiedBy(verificationTypesSetupVo.getModifiedBy());
				verificationTypesSetup.setModifiedDate(new Date());
				verificationTypesSetup.setVerificationTypesSetupId(verificationTypesSetupVo.getVerificationTypesSetupId());
				verificationTypesSetupId = verificationTypesSetupVo.getVerificationTypesSetupId();
				session.update(verificationTypesSetup);
			}else{
				verificationTypesSetup.setCreatedBy(verificationTypesSetupVo.getCreatedBy());
				verificationTypesSetup.setModifiedBy(verificationTypesSetupVo.getModifiedBy());
				verificationTypesSetup.setCreatedDate(new Date());
				verificationTypesSetup.setModifiedDate(new Date());
				verificationTypesSetupId = (Integer) session.save(verificationTypesSetup);
			}
			
			session.flush();
			
			
			
		}catch (Exception e) {
			 if(!(session.getTransaction().wasRolledBack())){
				 session.getTransaction().rollback();
			 }
			 log.error("Error Occurred...   ", e);			
		}				
		return 0;
	}

	/*
	 * This method will be used to  getVerificationTypeSetupDetails record by verificationTypeSetupId
	 */
	@Override
	public List<VerificationTypesSetupVo> getVerificationTypeSetupDetails(Integer verificationTypeSetupId) {
		log.info("Entered in  getVerificationTypeSetupDetails()  ::   verificationTypeSetupId = "+verificationTypeSetupId);
		Session session = sessionFactory.getCurrentSession();
		List<VerificationTypesSetupVo> verificatonTypesSetupList = new ArrayList();
		String hqlQuery = "SELECT Verification_Types_Setup_Id,Customer_Id,Company_Id,Country_Id,Transaction_Date,Is_Active,Verification_Type,Verification_Frequency,Created_Date,Created_By,Modified_By,Modified_Date,Is_Mandatory FROM verification_types_setup where Verification_Types_Setup_Id = "+verificationTypeSetupId;
		
		try {		
			SQLQuery divisionQuery = session.createSQLQuery(hqlQuery);
			List tempList = divisionQuery.list();

			for (Object o : tempList) {
				Object[] obj = (Object[]) o;
				VerificationTypesSetupVo verificationTypesSetupVo = new VerificationTypesSetupVo();
				verificationTypesSetupVo.setVerificationTypesSetupId((Integer)obj[0]);
				verificationTypesSetupVo.setCustomerId((Integer)obj[0]);
				verificationTypesSetupVo.setCompanyId((Integer)obj[1]);
				verificationTypesSetupVo.setCountryId((Integer)obj[2]);
				verificationTypesSetupVo.setTransactionDate((Date)obj[3]);
				verificationTypesSetupVo.setIsActive((Character)obj[4]);
				verificationTypesSetupVo.setVerificationType(obj[5]+"");
				verificationTypesSetupVo.setVerificationFrequency(obj[6]+"");				
				verificationTypesSetupVo.setCreatedDate((Date)obj[7]);
				verificationTypesSetupVo.setCreatedBy((Integer)obj[8]);
				verificationTypesSetupVo.setModifiedBy((Integer)obj[9]);
				verificationTypesSetupVo.setCountryId((Integer)obj[10]);
				verificationTypesSetupVo.setCountryId((Integer)obj[11]);
				verificationTypesSetupVo.setCountryId((Integer)obj[12]);
				verificatonTypesSetupList.add(verificationTypesSetupVo);				
			}
		
			
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from  getDepartmentById()  ::   ");
		}
		session.flush();
		log.info("Exiting from  getDepartmentById()  ::   ");
		return verificatonTypesSetupList;
	}
	
	

	@Override
	public List<VerificationTypesSetupVo> getVerificationTypesSetupListBySearch(VerificationTypesSetupVo paramDept) {
		log.info("Entered in  getVerificationTypesSetupListBySearch()  ::   VerificationTypesSetupVo = "+paramDept);
		Session session = sessionFactory.getCurrentSession();
		List deptList = null;
		List<VerificationTypesSetupVo> returnList = new ArrayList<VerificationTypesSetupVo>();
	//	VerificationTypesSetupVo verificationTypesSetupVo = new VerificationTypesSetupVo();		
		
		String hqlQuery = "SELECT Verification_Types_Setup_Id,Customer_Id,Company_Id,Country_Id,Transaction_Date,Is_Active,Verification_Type,Verification_Frequency,Created_Date,Created_By,Modified_By,Modified_Date,Is_Mandatory FROM verification_types_setup where 1 = 1 " ;
		
		if(paramDept.getCustomerId() > 0){
			hqlQuery += "  AND  Customer_Id = " +paramDept.getCustomerId();
		}
		
		if(paramDept.getCompanyId() > 0){
			hqlQuery += "  AND  Company_Id = "+paramDept.getCompanyId();
		}
				
		if(paramDept.getCountryId() > 0){
			hqlQuery += " AND Country_id =  '"+paramDept.getCountryId()+"' ";
		}		
		
		if(paramDept.getVerificationType() != null && paramDept.getVerificationType() != ""){
			hqlQuery += " AND Verification_Type = '"+paramDept.getVerificationType()+"' ";
		}

		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			deptList = query.list();
			
			for (Object o  : deptList) {
				Object[] obj = (Object[]) o;
					VerificationTypesSetupVo verificationTypesSetupVo = new VerificationTypesSetupVo();
					verificationTypesSetupVo.setVerificationTypesSetupId((Integer)obj[0]);
					verificationTypesSetupVo.setCustomerId((Integer)obj[0]);
					verificationTypesSetupVo.setCompanyId((Integer)obj[1]);
					verificationTypesSetupVo.setCountryId((Integer)obj[2]);
					verificationTypesSetupVo.setTransactionDate((Date)obj[3]);
					verificationTypesSetupVo.setIsActive((Character)obj[4]);
					verificationTypesSetupVo.setVerificationType(obj[5]+"");
					verificationTypesSetupVo.setVerificationFrequency(obj[6]+"");				
					verificationTypesSetupVo.setCreatedDate((Date)obj[7]);
					verificationTypesSetupVo.setCreatedBy((Integer)obj[8]);
					verificationTypesSetupVo.setModifiedBy((Integer)obj[9]);
					verificationTypesSetupVo.setCountryId((Integer)obj[10]);
					verificationTypesSetupVo.setCountryId((Integer)obj[11]);
					verificationTypesSetupVo.setCountryId((Integer)obj[12]);
					returnList.add(verificationTypesSetupVo);	
				}	
			
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from getVerificationTypesSetupListBySearch()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from getVerificationTypesSetupListBySearch()  ::   "+returnList);
		return returnList;
	}

	/*
	 * This method will be used to get the drop down list for cost center 
	 */
	@Override
	public List<SimpleObject> getDepartmentNameForCostCenter(Integer customerId, Integer companyId) {
		log.info("Entered in getDepartmentNameForCostCenter()  ::   customerId = "+customerId+" , companyId = "+companyId);
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> transactionList = new ArrayList<>();
		try {
			List contactList = session.createSQLQuery("SELECT Department_Info_Id AS id , Department_Name AS name FROM  department_details dept LEFT JOIN department_details_info info ON (dept.Department_Id = info.Department_Id) LEFT JOIN m_department mdept ON (info.Department_Type_Id = mdept.Department_Type_Id)  WHERE dept.Customer_Id = "+customerId+" AND dept.Company_Id = "+companyId+ " AND mdept.department_Type_Name LIKE 'Cost%' ORDER BY Department_Name").list();
			if(contactList.size() < 0){
				transactionList = null;
			}else{
				for (Object transDates  : contactList) {
					Object[] transaction = (Object[]) transDates;
					SimpleObject object = new SimpleObject();
					object.setId((Integer)transaction[0]);
					object.setName(transaction[1]+"");
					transactionList.add(object);
				}	
			}
		} catch (Exception e) {
			log.error("Error Occurred...   ", e);
			log.info("Exiting from getDepartmentNameForCostCenter()  ::   "+transactionList);
		}
		
		log.info("Exiting from getDepartmentNameForCostCenter()  ::   "+transactionList);
		return transactionList;
	}

	@Override
	public List<SimpleObject> getTransactionListForDepartment(Integer customerId, Integer companyId, Integer deptUniqueId) {
		log.info("Entered in getTransactionListForDepartment()  ::   customerId = "+customerId+" , companyId = "+companyId+" , departmentUniqueId = "+deptUniqueId);
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> transactionList = new ArrayList<>();
		try {
			List contactList = session.createSQLQuery("SELECT Department_Info_Id AS id ,CONCAT(DATE_FORMAT(Transaction_Date,'%d/%m/%Y'),' - ',Department_Sequence_Id) AS name FROM  department_details_info dept  WHERE dept.Customer_Id = "+customerId+" AND dept.Company_Id = "+companyId+" AND dept.Department_Id = "+deptUniqueId+" ORDER BY dept.Transaction_Date, dept.Department_Info_Id asc").list();
			for (Object transDates  : contactList) {
				Object[] transaction = (Object[]) transDates;
				SimpleObject object = new SimpleObject();
				object.setId((Integer)transaction[0]);
				object.setName(transaction[1]+"");
				transactionList.add(object);
			}			
		} catch (Exception e) {
			log.error("Error Occurred...   ", e);
			log.info("Exiting from getTransactionListForDepartment()  ::   "+transactionList);
		}
		
		log.info("Exiting from getTransactionListForDepartment()  ::   "+transactionList);
		return transactionList;
	}
	
	@Override
	public List<SimpleObject> getDepartmentListDropDown(Integer customerId, Integer companyId,String status) {
		log.info("Entered in getDepartmentListDropDown()  ::   customerId = "+customerId+" , companyId = "+companyId+" , status = "+status);
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> transactionList = new ArrayList<>();
		try {
			String query = "SELECT parent.`Department_Id`,`Department_Name`,`Department_Code` FROM department_details parent "
							+" INNER JOIN  department_details_info child ON parent.Department_Id = child.Department_Id "
							+" WHERE CONCAT(DATE_FORMAT(child.transaction_date, '%Y%m%d'), child.`Department_Sequence_Id`) = " 
							+" ( " 
							+" SELECT MAX(CONCAT(DATE_FORMAT(child1.transaction_date, '%Y%m%d'), child1.Department_Sequence_Id)) "
							+" FROM department_details_info child1 "
							+" WHERE child.Department_Id = child1.Department_Id AND child1.transaction_date <= CURRENT_DATE() "
							+" ) AND child.`Customer_Id`="+customerId
							+" AND child.`Company_Id`="+companyId
							+" AND child.`Is_Active`='"+status+"'";
			List contactList = session.createSQLQuery(query).list();
			for (Object transDates  : contactList) {
				Object[] transaction = (Object[]) transDates;
				SimpleObject object = new SimpleObject();
				object.setId((Integer)transaction[0]);
				object.setName(transaction[1]+"");
				object.setDesc(transaction[2]+"");
				transactionList.add(object);
			}			
		} catch (Exception e) {
			log.error("Error Occurred...   ", e);
			log.info("Exiting from getDepartmentListDropDown()  ::   "+transactionList);
		}
		
		log.info("Exiting from getDepartmentListDropDown()  ::   "+transactionList);
		return transactionList;
	}

}
