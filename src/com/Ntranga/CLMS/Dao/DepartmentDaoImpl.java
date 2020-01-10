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
import com.Ntranga.core.DateHelper;
import com.Ntranga.core.CLMS.entities.CompanyDetails;
import com.Ntranga.core.CLMS.entities.CustomerDetails;
import com.Ntranga.core.CLMS.entities.DepartmentDetails;
import com.Ntranga.core.CLMS.entities.DepartmentDetailsInfo;
import com.Ntranga.core.CLMS.entities.DepartmentDivision;
import com.Ntranga.core.CLMS.entities.DivisionDetails;
import com.Ntranga.core.CLMS.entities.MDepartment;

import common.Logger;

@Transactional
@Repository("departmentDao")
@SuppressWarnings({"rawtypes"})
public class DepartmentDaoImpl implements DepartmentDao {

	private static Logger log = Logger.getLogger(DepartmentDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Integer saveDepartment(DepartmentVo paramDept) {
		log.info("Entered in saveDepartment()  ::   "+paramDept);

		Session session = sessionFactory.getCurrentSession();
		Integer departmentInfoId = 0;
		Integer departmentId = 0;
		BigInteger deptSequenceId = new BigInteger("0");	
		DepartmentDetails deptDetails = new DepartmentDetails();
		DepartmentDetailsInfo deptDetailsInfo = new DepartmentDetailsInfo();
		DepartmentDivision  deptDivision = new DepartmentDivision();
		
		try{									
			if(paramDept != null && paramDept.getDepartmentId() > 0){
				deptDetails = (DepartmentDetails) session.load(DepartmentDetails.class, paramDept.getDepartmentId());
				deptDetails.setCustomerDetails(new CustomerDetails(paramDept.getCustomerId()));
				deptDetails.setCompanyDetails(new CompanyDetails(paramDept.getCompanyId()));
				deptDetails.setDepartmentCode(paramDept.getDepartmentCode().toUpperCase());
				deptDetails.setModifiedBy(paramDept.getModifiedBy());
				deptDetails.setModifiedDate(new Date());
				session.update(deptDetails);
				departmentId  = paramDept.getDepartmentId();
			}else{
				deptDetails = new DepartmentDetails();
				deptDetails.setCustomerDetails(new CustomerDetails(paramDept.getCustomerId()));
				deptDetails.setCompanyDetails(new CompanyDetails(paramDept.getCompanyId()));
				deptDetails.setDepartmentCode(paramDept.getDepartmentCode().toUpperCase());
				deptDetails.setCreatedBy(paramDept.getCreatedBy());
				deptDetails.setModifiedBy(paramDept.getModifiedBy());
				deptDetails.setCreatedDate(new Date());
				deptDetails.setModifiedDate(new Date());
				departmentId = (Integer) session.save(deptDetails);
			}
			session.flush();
			
			if(paramDept != null &&  paramDept.getDepartmentInfoId() > 0){
				deptDetailsInfo = (DepartmentDetailsInfo) sessionFactory.getCurrentSession().load(DepartmentDetailsInfo.class, paramDept.getDepartmentInfoId());
				deptDetailsInfo.setDepartmentDetails(new DepartmentDetails(departmentId));
				deptDetailsInfo.setCustomerDetails(new CustomerDetails(paramDept.getCustomerId()));
				deptDetailsInfo.setCompanyDetails(new CompanyDetails(paramDept.getCompanyId()));
				deptDetailsInfo.setDepartmentName(paramDept.getDepartmentName());
				deptDetailsInfo.setCostCenter(paramDept.getCostCenter());
				//deptDetailsInfo.setDepartmentOwnerEmp(paramDept.getDepartmentOwnerEmp());
				//deptDetailsInfo.setDepartmentOwnerJob(paramDept.getDepartmentOwnerJob());
				deptDetailsInfo.setIsCostCenter(paramDept.getIsCostCenter() == true ? "Yes" : "No" );
				deptDetailsInfo.setCostCenterName(paramDept.getCostCenterName());
				deptDetailsInfo.setEmployeeId(paramDept.getDepartmentOwnerEmp());
				deptDetailsInfo.setJobCodeId(paramDept.getDepartmentOwnerJob());
				deptDetailsInfo.setShortName(paramDept.getShortName());
				deptDetailsInfo.setLegacyDepartmentId(paramDept.getLegacyDeptId());
				deptDetailsInfo.setMDepartmentType(new MDepartment(paramDept.getDepartmentTypeId()));
				deptDetailsInfo.setIsActive(paramDept.getIsActive());
				deptDetailsInfo.setTransactionDate(paramDept.getTransactionDate());
				deptDetails.setModifiedBy(paramDept.getModifiedBy());
				deptDetails.setModifiedDate(new Date());
				session.update(deptDetailsInfo);
				departmentInfoId = deptDetailsInfo.getDepartmentInfoId();
			}else{					
				deptDetailsInfo.setDepartmentDetails(deptDetails);
				deptDetailsInfo.setCustomerDetails(new CustomerDetails(paramDept.getCustomerId()));
				deptDetailsInfo.setCompanyDetails(new CompanyDetails(paramDept.getCompanyId()));
				deptDetailsInfo.setDepartmentName(paramDept.getDepartmentName());
				deptDetailsInfo.setCostCenter(paramDept.getCostCenter());
				/*deptDetailsInfo.setDepartmentOwnerEmp(paramDept.getDepartmentOwnerEmp());
				deptDetailsInfo.setDepartmentOwnerJob(paramDept.getDepartmentOwnerJob());*/
				deptDetailsInfo.setIsCostCenter(paramDept.getIsCostCenter() == true ? "Yes" : "No" );
				deptDetailsInfo.setCostCenterName(paramDept.getCostCenterName());
				deptDetailsInfo.setEmployeeId(paramDept.getDepartmentOwnerEmp());
				deptDetailsInfo.setJobCodeId(paramDept.getDepartmentOwnerJob());
				deptDetailsInfo.setShortName(paramDept.getShortName());
				deptDetailsInfo.setLegacyDepartmentId(paramDept.getLegacyDeptId());
				deptDetailsInfo.setMDepartmentType(new MDepartment(paramDept.getDepartmentTypeId()));
				deptDetailsInfo.setIsActive(paramDept.getIsActive());
				deptDetailsInfo.setTransactionDate(paramDept.getTransactionDate());
				deptDetailsInfo.setCreatedBy(paramDept.getCreatedBy());
				deptDetailsInfo.setModifiedBy(paramDept.getModifiedBy());
				deptDetailsInfo.setCreatedDate(new Date());
				deptDetailsInfo.setModifiedDate(new Date());
				if(departmentId != null && departmentId > 0){
					deptSequenceId  = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Department_Sequence_Id),0) FROM `department_details_info` dept WHERE  dept.Department_Id = "+paramDept.getDepartmentId() +" AND dept.`Transaction_Date` = '"+DateHelper.convertDateToSQLString(paramDept.getTransactionDate())+"' and Customer_Id='"+paramDept.getCustomerId()+"' And Company_Id = '"+paramDept.getCompanyId()+"'").list().get(0);
				}
				deptDetailsInfo.setDepartmentSequenceId(deptSequenceId.intValue()+1);			
				departmentInfoId = (Integer) session.save(deptDetailsInfo);
			}
			
			session.flush();
			
			if(paramDept.getDepartmentInfoId() > 0 && paramDept.getDepartmentDivisionList() != null && paramDept.getDepartmentDivisionList().size() > 0 ){
				Query q = session.createQuery("DELETE FROM DepartmentDivision WHERE Department_Info_Id= "+paramDept.getDepartmentInfoId());
				q.executeUpdate();
			}
			
			if(paramDept.getDepartmentDivisionList() != null && paramDept.getDepartmentDivisionList().size() > 0){
				
				for(DepartmentDivisionVo div : paramDept.getDepartmentDivisionList()){
					deptDivision = new DepartmentDivision();
					deptDivision.setCustomerDetails(new CustomerDetails(paramDept.getCustomerId()));
					deptDivision.setCompanyDetails(new CompanyDetails(paramDept.getCompanyId()));
					deptDivision.setDivisionDetails(new DivisionDetails(div.getDivisionId()));
					deptDivision.setDepartmentDetails(new DepartmentDetails(departmentId));
					deptDivision.setDepartmentDetailsInfo(new DepartmentDetailsInfo(departmentInfoId));
					deptDivision.setCreatedBy(paramDept.getCreatedBy());
					deptDivision.setModifiedBy(paramDept.getModifiedBy());
					deptDivision.setCreatedDate(new Date());
					deptDivision.setModifiedDate(new Date());
					
					session.save(deptDivision);
				}
			}else{
				
			}
			
		}catch (Exception e) {
			 if(!(session.getTransaction().wasRolledBack())){
				 session.getTransaction().rollback();
			 }
			 log.error("Error Occurred...   ", e);
			 log.info("Exiting from saveDepartment()  ::  departmentInfoId =  "+departmentInfoId);
		}				
		log.info("Exiting from saveDepartment()  ::  departmentInfoId =  "+departmentInfoId);
		return departmentInfoId;
	}

	/*
	 * This method will be used to get department record by department_info_id
	 */
	@Override
	public List<DepartmentVo> getDepartmentById(Integer departmentId) {
		log.info("Entered in  getDepartmentById()  ::   customerId = "+departmentId);
		Session session = sessionFactory.getCurrentSession();
		DepartmentVo returnDept = new DepartmentVo();
		DepartmentDivisionVo division = new DepartmentDivisionVo();
		List<DepartmentVo> returnDeptVo = new ArrayList<DepartmentVo>();
		List<DepartmentDivisionVo> deptDivList = new ArrayList<DepartmentDivisionVo>();
		
		String div = "SELECT  department.Department_Info_Id, department.Department_Id, "
				+ " department.Division_Id, division.Division_Name, divi.Division_Code "
				+ " FROM department_division AS department "
				+ " LEFT JOIN division_details_info division ON department.Division_Id = division.Division_Id AND department.Customer_Id = division.Customer_Id "
																+ "	AND department.Company_Id = division.Company_Id "
																+" AND CONCAT(DATE_FORMAT(division.Transaction_Date, '%Y%m%d'), LPAD(division.Division_Sequence_Id, 2, '0')) = "
																+"  ( "
																+"  SELECT MAX(CONCAT(DATE_FORMAT(division1.Transaction_Date, '%Y%m%d'), LPAD(division1.Division_Sequence_Id, 2, '0'))) "
																+"  FROM division_details_info division1 "
																+"  WHERE  division.Division_Id = division1.Division_Id     "
																+" 	AND division1.Transaction_Date <= CURRENT_DATE())   "
				+ " LEFT JOIN division_details divi ON department.Division_Id = divi.Division_Id"
				+ " LEFT JOIN company_details AS cmp  ON department.Customer_Id = cmp.Customer_Id  "
				+ "	AND department.Company_Id = cmp.Company_Id "
				+ " LEFT JOIN company_details_info AS company  ON company.Customer_Id = department.Customer_Id  "
				+ "	AND company.Company_Id = department.Company_Id "
				+ " LEFT JOIN customer_details AS cus  ON cus.Customer_Id = department.Customer_Id "
				+ " LEFT JOIN customer_details_info AS customer  ON customer.Customer_Id = department.Customer_Id "
					+ " WHERE department.Department_Info_Id = "+departmentId+" GROUP BY department.Department_Id, department.Department_Info_Id, department.Department_Division_Id";

			
	
		
		String hqlQuery = "SELECT dept.Customer_Id,  dept.Company_Id, department.Department_Info_Id, "
							+ " department.Department_Type_Id, department.Department_Name, department.Short_Name,"
							+ " department.Legacy_Department_Id, dept.Department_Code, "
							//+ "  ( CASE WHEN department.Cost_Center IS null THEN 0 ELSE department.Cost_Center END) AS Cost_Center,"
							+ " department.Is_Cost_Center, department.Transaction_Date, department.Is_Active,  "
							+ " mdepartment.Department_Type_Name , department.Department_Id, department.Job_Code_Id, "
							+ " department.Employee_Id,  department.Cost_Center_Name,department.Cost_Center "
							+ " FROM department_details_info AS department "
							+ " LEFT JOIN department_details dept ON department.Department_Id = dept.Department_Id"
							+ " LEFT JOIN m_department mdepartment ON department.Department_Type_Id = mdepartment.Department_Type_Id "
							+ " LEFT JOIN company_details AS cmp  ON department.Customer_Id = cmp.Customer_Id  "
							+ "	AND dept.Company_Id = cmp.Company_Id "
							+ " LEFT JOIN company_details_info AS company  ON company.Customer_Id = dept.Customer_Id  "
							+ "	AND company.Company_Id = cmp.Company_Id "
							+ " LEFT JOIN customer_details AS cus  ON dept.Customer_Id = cus.Customer_Id "
							+ " LEFT JOIN customer_details_info AS customer  ON customer.Customer_Id = cus.Customer_Id "
								+ " WHERE department.Department_Info_Id = "+departmentId;
		
		try {		
			SQLQuery divisionQuery = session.createSQLQuery(div);
			List divList = divisionQuery.list();

			for (Object o : divList) {
				Object[] obj = (Object[]) o;
				division = new DepartmentDivisionVo();
				
				division.setDepartmentInfoId((Integer)obj[0]);
				division.setDepartmentId((Integer)obj[1]);
				division.setDivisionId((Integer)obj[2]);
				division.setDivisionName((String)obj[3]);
				division.setDivisionCode((String)obj[4]);
				
				deptDivList.add(division);
			}	
			
			
			SQLQuery query = session.createSQLQuery(hqlQuery);
			List deptList = query.list();

			for (Object dept : deptList) {
				Object[] obj = (Object[]) dept;
				returnDept = new DepartmentVo();
				returnDept.setCustomerId((Integer)obj[0]);
				returnDept.setCompanyId((Integer)obj[1]);
				returnDept.setDepartmentInfoId((Integer)obj[2]);
				//returnDept.setDepartmentId((Integer)obj[12]);
				returnDept.setDepartmentTypeId((Integer)obj[3]);
				returnDept.setDepartmentName((String)obj[4]);
				returnDept.setShortName((String)obj[5]);
				returnDept.setLegacyDeptId((String)obj[6]);
				returnDept.setDepartmentCode((String)obj[7]);
				returnDept.setIsCostCenter(obj[8] != null ? (((String)obj[8]).equalsIgnoreCase("Yes") ? true : false) : false);
				returnDept.setTransactionDate((Date)obj[9]);
				returnDept.setIsActive(obj[10]+"");
				returnDept.setDepartmentId((Integer)obj[12]);
				returnDept.setDepartmentOwnerJob((Integer)obj[13]);
				returnDept.setDepartmentOwnerEmp((Integer)obj[14]);
				returnDept.setCostCenterName((String)obj[15] );
				returnDept.setCostCenter((Integer)obj[16] );
				returnDept.setDepartmentDivisionList(deptDivList);
				returnDeptVo.add(returnDept);
				
			}	
			
			session.clear();	
			
			
			
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from  getDepartmentById()  ::   "+returnDept);
		}
		session.flush();
		log.info("Exiting from  getDepartmentById()  ::   "+returnDept);
		return returnDeptVo;
	}

	@Override
	public List<DepartmentVo> getDepartmentsListBySearch(DepartmentVo paramDept) {
		log.info("Entered in  getDepartmentsListBySearch()  ::   DepartmentVo = "+paramDept);
		Session session = sessionFactory.getCurrentSession();
		List deptList = null;
		List<DepartmentVo> returnList = new ArrayList<DepartmentVo>();
		DepartmentVo department = new DepartmentVo();
		
		
		String hqlQuery = "SELECT dept.Customer_Id, department.Department_Id,  dept.Company_Id, "
							+ " department.Department_Type_Id, department.Department_Name, department.Short_Name,"
							+ " department.Legacy_Department_Id, dept.Department_Code,  "
							+ "  dept1.Department_Name AS Cost_Center,"
							+ "	department.Transaction_Date, department.Is_Active,  mdepartment.Department_Type_Name , "
							+ " department.Department_Info_Id,  customer.Customer_Name, company.Company_Name"
							+ " FROM department_details_info AS department "
							+ " LEFT JOIN department_division DV ON (DV.Department_Info_Id = department.Department_Info_Id)"
							+ " LEFT JOIN department_details dept ON department.Department_Id = dept.Department_Id"
							+ " LEFT JOIN department_details_info dept1 ON department.Department_Id = dept1.Cost_Center"

							+ " LEFT JOIN m_department mdepartment ON department.Department_Type_Id = mdepartment.Department_Type_Id "
						
							+ " LEFT JOIN company_details_info AS company  ON company.Customer_Id = department.Customer_Id  "
																			+ "	AND company.Company_Id = department.Company_Id "
																			+ "	AND  CONCAT(DATE_FORMAT(company.Transaction_Date, '%Y%m%d'), company.Company_Sequence_Id) = ( "
																			+"	SELECT MAX(CONCAT(DATE_FORMAT(company1.Transaction_Date, '%Y%m%d'), company1.Company_Sequence_Id)) "
																			+"	FROM company_details_info company1 "
																			+"	WHERE company.Company_Id = company1.Company_Id AND company1.Transaction_Date <= CURRENT_DATE() "
																			+ " ) "	
							+ " LEFT JOIN customer_details_info AS customer  ON customer.Customer_Id = department.Customer_Id "
																			+ " AND  CONCAT(DATE_FORMAT(customer.Transaction_Date, '%Y%m%d'), customer.Customer_Sequence_Id) =  ( "
																			+"	SELECT MAX(CONCAT(DATE_FORMAT(customer1.Transaction_Date, '%Y%m%d'), customer1.Customer_Sequence_Id)) "
																			+"	FROM customer_details_info customer1 "
																			+"	WHERE customer.Customer_Id = customer1.Customer_Id AND customer1.Transaction_Date <= CURRENT_DATE() "
																			+"	) "
															

								+ " WHERE "
								+ " customer.Is_Active = 'Y' AND company.Is_Active = 'Y' "
								+ " AND  CONCAT(DATE_FORMAT(department.Transaction_Date, '%Y%m%d'), department.Department_Sequence_Id) =  ( "
																				+"	SELECT MAX(CONCAT(DATE_FORMAT(department1.Transaction_Date, '%Y%m%d'), department1.Department_Sequence_Id)) "
																				+"	FROM department_details_info department1 "
																				+"	WHERE department.Department_Id = department1.Department_Id AND department1.Transaction_Date <= CURRENT_DATE() "
																				+"	) " ;
								
						  		
						
		
		if(paramDept.getCompanyId() > 0){
			hqlQuery += "  AND  department.Company_Id = "+paramDept.getCompanyId();
		}
		if(paramDept.getCustomerId() > 0){
			hqlQuery += "  AND  department.Customer_Id = " +paramDept.getCustomerId();
		}
		if(paramDept.getDepartmentCode() != null && !paramDept.getDepartmentCode().isEmpty()){
			hqlQuery += " AND dept.Department_Code LIKE '"+paramDept.getDepartmentCode()+"%' ";
		}
		if(paramDept.getDepartmentName() != null && !paramDept.getDepartmentName().isEmpty()){
			hqlQuery += " AND department.Department_Name LIKE '"+paramDept.getDepartmentName()+"%' ";
		}
		if(paramDept.getIsActive() != null && !paramDept.getIsActive().isEmpty()){
			hqlQuery += " AND department.Is_Active LIKE '"+paramDept.getIsActive()+"%' ";
		}else {
			hqlQuery += " AND department.Is_Active = 'Y' ";
		}
		
		if(paramDept.getDivisionId() > 0){
			hqlQuery += " AND DV.Division_Id = '"+paramDept.getDivisionId()+"' ";
		}
		
		hqlQuery += " GROUP BY department.Department_Id Order By department.Department_Name asc";
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			deptList = query.list();
			if(deptList.size() > 0){
				for (Object customer : deptList) {
					Object[] obj = (Object[]) customer;
					department = new DepartmentVo();
					
					department.setDepartmentId((Integer)obj[2]);
					department.setDepartmentInfoId((Integer)obj[12]);
					department.setDepartmentCode((String)obj[7]);
					department.setTransDate(DateHelper.convertSQLDateToString((java.sql.Date)obj[9],"dd/MM/yyyy"));
					department.setIsActive((obj[10]+"").equalsIgnoreCase("Y") ? "Active":"Inactive");
					department.setDepartmentName((String)obj[4]);
					department.setCostCenterName((String)obj[5]);
					department.setCustomerName((String)obj[13]);
					department.setCompanyName((String)obj[14]);
					returnList.add(department);
				}	
			}
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from getDepartmentsListBySearch()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from getDepartmentsListBySearch()  ::   "+returnList);
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
			List contactList = session.createSQLQuery("SELECT info.Department_Id AS id , CONCAT(Department_Name,' (',Department_Code,')') AS name FROM  department_details dept "
							+ " LEFT JOIN department_details_info info ON (dept.Department_Id = info.Department_Id) "
							+ " WHERE dept.Customer_Id = "+customerId
							+ "  AND dept.Company_Id = "+companyId
							+ " AND  CONCAT(DATE_FORMAT(info.Transaction_Date, '%Y%m%d'), info.Department_Sequence_Id) =  ( "
							+ "	SELECT MAX(CONCAT(DATE_FORMAT(info1.Transaction_Date, '%Y%m%d'), info1.Department_Sequence_Id)) "
							+ "	FROM department_details_info info1 "
							+ "	WHERE info.Department_Id = info1.Department_Id AND info1.Transaction_Date <= CURRENT_DATE() "
							+"	) " 
							+ " AND info.Is_Active = 'Y' "
							+ " ORDER BY Department_Name").list();
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
