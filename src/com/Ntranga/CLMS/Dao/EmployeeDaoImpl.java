package com.Ntranga.CLMS.Dao;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Ntranga.CLMS.vo.EmployeeInformationVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.core.DateHelper;
import com.Ntranga.core.CLMS.entities.CompanyDetails;
import com.Ntranga.core.CLMS.entities.CustomerDetails;
import com.Ntranga.core.CLMS.entities.DepartmentDetails;
import com.Ntranga.core.CLMS.entities.EmployeeInformation;
import com.Ntranga.core.CLMS.entities.LocationDetails;
import com.Ntranga.core.CLMS.entities.MBloodGroup;
import com.Ntranga.core.CLMS.entities.MJobStatus;
import com.Ntranga.core.CLMS.entities.MJobTitle;
import com.Ntranga.core.CLMS.entities.MJobType;
import com.Ntranga.core.CLMS.entities.PlantDetails;
import com.Ntranga.core.CLMS.entities.SectionDetails;
import com.Ntranga.core.CLMS.entities.WorkAreaDetails;

import common.Logger;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Transactional
@Repository("employeeDao")
public class EmployeeDaoImpl implements EmployeeDao {

	private static Logger log = Logger.getLogger(EmployeeDaoImpl.class);
	
	
	@Autowired
	private SessionFactory sessionFactory;

	/* Save Employee Details */

	

	
	public List<SimpleObject> getBloodGroupList() {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> SobjbloodGroupList = new ArrayList<>();
		try {
			List<MBloodGroup> bloodGroupList = session.createQuery("from MBloodGroup ORDER BY bloodGroup asc").list();						
			
			for (MBloodGroup bloodGroup  : bloodGroupList) {
				MBloodGroup obj = (MBloodGroup) bloodGroup;
				SimpleObject sobj = new SimpleObject();
				sobj.setId(obj.getBloodGroupId());
				sobj.setName(obj.getBloodGroup());
				SobjbloodGroupList.add(sobj);
			}			
		} catch (Exception e) {
			log.error("Error Occured ",e);

		}

		return SobjbloodGroupList;

	}
	
	
	public List<SimpleObject> getJobTitleList() {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> SobjJobList = new ArrayList<>();
		try {
			List<MJobTitle> jobsList = session.createQuery("from MJobTitle ORDER BY jobTitle asc ").list();						
			
			for (MJobTitle bloodGroup  : jobsList) {
				MJobTitle obj = (MJobTitle) bloodGroup;
				SimpleObject sobj = new SimpleObject();
				sobj.setId(obj.getJobId());
				sobj.setName(obj.getJobTitle());
				SobjJobList.add(sobj);
			}			
		} catch (Exception e) {
			log.error("Error Occured ",e);

		}

		return SobjJobList;

	}
	public List<SimpleObject> getJobTypeList() {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> SobjobList = new ArrayList<>();
		try {
			List<MJobType> jobs = session.createQuery("from MJobType ORDER BY jobName asc ").list();						
			
			for (MJobType mJob  : jobs) {
				MJobType obj = (MJobType) mJob;
				SimpleObject sobj = new SimpleObject();
				sobj.setId(obj.getJobId());
				sobj.setName(obj.getJobName());
				SobjobList.add(sobj);
			}			
		} catch (Exception e) {
			log.error("Error Occured ",e);

		}

		return SobjobList;

	}
	
	
	
	public List<SimpleObject> getJobStatusList() {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> SobjbloodGroupList = new ArrayList<>();
		try {
			List<MJobStatus> jobStausList = session.createQuery("from MJobStatus ORDER BY jobStatus asc").list();						
			
			for (MJobStatus bloodGroup  : jobStausList) {
				MJobStatus obj = (MJobStatus) bloodGroup;
				SimpleObject sobj = new SimpleObject();
				sobj.setId(obj.getJobStatusId());
				sobj.setName(obj.getJobStatus());
				SobjbloodGroupList.add(sobj);
			}			
		} catch (Exception e) {
			log.error("Error Occured ",e);

		}
		return SobjbloodGroupList;
	}
	
	
	
	public List<SimpleObject> getLocationListByCustomerIdAndCompanyId(Integer customerId,Integer CompanyId) {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> SobjJobList = new ArrayList<>();
		try {
			List jobsList = session.createSQLQuery("SELECT DISTINCT locDetails.Location_Id," 
							+"  location.Location_Name "
							+" FROM location_details_info AS location  "
							+" LEFT JOIN location_details locDetails ON location.Location_Id = locDetails.Location_Id "
							+" LEFT JOIN m_location mlocation ON location.Location_Type_Id = mlocation.Location_Type_Id  "
							+" LEFT JOIN company_details AS company  ON locDetails.Customer_Id = company.Customer_Id  AND locDetails.Company_Id = company.Company_Id "  								
							+" LEFT JOIN customer_details AS customer  ON locDetails.Customer_Id = customer.Customer_Id  "
							+" LEFT JOIN customer_details_info As custDetails ON locDetails.Customer_Id = custDetails.Customer_Id "
							+" LEFT JOIN company_details_info As cmpDetails ON locDetails.Customer_Id = cmpDetails.Customer_Id AND locDetails.Company_Id = cmpDetails.Company_Id "
							//+" WHERE  location.Transaction_Date <= CURRENT_DATE()   "
						  	/*+" AND location.Transaction_Date = ( "
						  	+" SELECT MAX(info1.Transaction_Date) FROM location_details_info info1  "
			        		+" WHERE location.Location_Id = info1.Location_Id  "
			        		+" AND info1.Transaction_Date <= location.Transaction_Date)  "				  
				        	+" AND location.Location_Sequence_Id IN (  "
							+" SELECT Max(info2.Location_Sequence_Id) FROM location_details_info info2  "
							+" WHERE info2.Location_Details_Id = location.Location_Details_Id  "
							+" AND info2.Status = location.Status  "
							+" AND info2.Location_Type_Id = location.Location_Type_Id  "
							+" AND info2.Transaction_Date <= location.Transaction_Date) "*/
                            +" WHERE  CONCAT(DATE_FORMAT(location.Transaction_Date, '%Y%m%d'), location.`Location_Sequence_Id`) =  "
									+" ( "
									+" SELECT MAX(CONCAT(DATE_FORMAT(location1.Transaction_Date, '%Y%m%d'), location1.`Location_Sequence_Id`)) "
									+" FROM location_details_info location1 "
									+" WHERE location.`Location_Details_Id` = location1.`Location_Details_Id` AND location1.transaction_date <= CURRENT_DATE() "
									+" ) "
									+ " AND custDetails.customer_Id ='"+customerId+"' "
									+ " AND cmpDetails.company_Id ='"+CompanyId
									+ "' AND  location.Status = 'Y' ORDER BY location.Location_Name asc").list();						
			
			for (Object bloodGroup  : jobsList) {
				Object[] obj = (Object[]) bloodGroup;
				SimpleObject sobj = new SimpleObject();
				sobj.setId((Integer)obj[0]);
				sobj.setName(obj[1]+"");
				SobjJobList.add(sobj);
			}			
		} catch (Exception e) {
			log.error("Error Occured ",e);

		}

		return SobjJobList;

	}
	
	public List<SimpleObject> getDepartmentListByLocationId(Integer locationId) {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> SobjJobList = new ArrayList<>();
		try {
			List jobsList = session.createSQLQuery("select DISTINCT Di.Department_Id,DI.Department_Name from department_details_info di "
												+" inner join location_department ld on(di.department_id =ld.department_id) "
												+" where DI.transaction_date <= (select max(transaction_date) from department_details_info d where d.transaction_date <= current_date() "
												+" and d.Department_Id = di.department_id) "
												+" and di.Department_Sequence_Id in(select max(Department_Sequence_Id) from department_details_info c where c.transaction_date <= current_date() "
												+" and c.Department_Id = di.department_id) and ld.location_id ="+locationId+" ORDER BY DI.Department_Name asc").list();						
			
			for (Object bloodGroup  : jobsList) {
				Object[] obj = (Object[]) bloodGroup;
				SimpleObject sobj = new SimpleObject();
				sobj.setId((Integer)obj[0]);
				sobj.setName(obj[1]+"");
				SobjJobList.add(sobj);
			}			
		} catch (Exception e) {
			log.error("Error Occured ",e);

		}

		return SobjJobList;

	}
	
	public List<SimpleObject> getJobCodeListforCUstomerAndCompany(Integer customerId,Integer CompanyId) {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> SobjJobList = new ArrayList<>();
		try {
			List jobsList = session.createSQLQuery("SELECT DISTINCT parent.Job_Code_Id,CONCAT(parent.Job_Code,' ',child.`Job_Name`) FROM job_code_details parent INNER JOIN `job_code_details_info` child ON (parent.`Job_Code_Id` = child.`Job_Code_Id`) WHERE CONCAT(DATE_FORMAT(child.transaction_date, '%Y%m%d'), child.`Job_Code_Sequence_Id`) =   ( SELECT MAX(CONCAT(DATE_FORMAT(child.transaction_date, '%Y%m%d'), vdi1.`Job_Code_Sequence_Id`))   FROM job_code_details_info vdi1  WHERE vdi1.`Job_Code_Id` = child.`Job_Code_Id` AND vdi1.transaction_date <= CURRENT_DATE())  AND  parent.customer_id ='"+customerId+"' and parent.company_id ='"+CompanyId+"' AND child.status='Y'  ORDER BY Job_Code asc").list();						
			
			for (Object bloodGroup  : jobsList) {
				Object[] obj = (Object[]) bloodGroup;
				SimpleObject sobj = new SimpleObject();
				sobj.setId((Integer)obj[0]);
				sobj.setName(obj[1]+"");
				SobjJobList.add(sobj);
			}			
		} catch (Exception e) {
			log.error("Error Occured ",e);

		}

		return SobjJobList;

	}
	
	
	public List<SimpleObject> getReportingMangerList(Integer customerId,Integer CompanyId) {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> SobjJobList = new ArrayList<>();
		try {
			List jobsList = session.createSQLQuery("select employee_id,first_name from employee_information ei where ei.customer_id ='"+customerId+"' and ei.company_id ='"+CompanyId+
					"' AND  CONCAT(DATE_FORMAT(ei.Transaction_Date, '%Y%m%d'), ei.Sequence_Id) = ( SELECT MAX(CONCAT(DATE_FORMAT(ei1.Transaction_Date, '%Y%m%d'), ei1.Sequence_Id)) FROM employee_information ei1 WHERE ei.unique_Id = ei1.unique_Id AND ei1.Transaction_Date <= CURRENT_DATE() ) ORDER BY First_Name asc").list();						
			
			for (Object bloodGroup  : jobsList) {
				Object[] obj = (Object[]) bloodGroup;
				SimpleObject sobj = new SimpleObject();
				sobj.setId((Integer)obj[0]);
				sobj.setName(obj[1]+"");
				SobjJobList.add(sobj);
			}			
		} catch (Exception e) {
			log.error("Error Occured ",e);

		}

		return SobjJobList;

	}
	
	public List<EmployeeInformationVo> getEmployeeGridData(Integer customerId,Integer CompanyId,Integer jobTypeId,Integer jobStatusId,Integer employeeCountryId,String employeeCode) {
		Session session = sessionFactory.getCurrentSession();
		List<EmployeeInformationVo> SobjempList = new ArrayList<>();
		try {
			String  q = " SELECT distinct ei.`Employee_Id`,`Employee_Code`,`First_Name`,COALESCE(ldi.`Location_Name`,'') AS loc ,CASE ei.is_active WHEN 'Y' THEN 'Active' ELSE 'In-Active' END AS STATUS,"
													+ " jdi.Job_Name, ddi.Department_Name"
													+" FROM employee_information ei 	  "
													+" LEFT JOIN location_details_info ldi ON(ldi.Location_Id = ei.Location_Id AND   "
															+" CONCAT(DATE_FORMAT(ldi.Transaction_Date, '%Y%m%d'), ldi.`Location_Sequence_Id`) =  "
															+" ( "
															+" SELECT MAX(CONCAT(DATE_FORMAT(ldi1.Transaction_Date, '%Y%m%d'), ldi1.`Location_Sequence_Id`)) "
															+" FROM location_details_info ldi1 "
															+" WHERE ldi.`Location_Details_Id` = ldi1.`Location_Details_Id` AND ldi1.Transaction_Date <= CURRENT_DATE() "
															+" ) ) "
													+" LEFT JOIN department_details_info ddi ON(ddi.Department_Id = ei.Department_Id AND   "
															+" CONCAT(DATE_FORMAT(ddi.Transaction_Date, '%Y%m%d'), ddi.`Department_Sequence_Id`) =  "
															+" ( "
															+" SELECT MAX(CONCAT(DATE_FORMAT(ddi1.Transaction_Date, '%Y%m%d'), ddi1.`Department_Sequence_Id`)) "
															+" FROM department_details_info ddi1 "
															+" WHERE ddi.`Department_Id` = ddi1.`Department_Id` AND ddi1.Transaction_Date <= CURRENT_DATE() "
															+" ) ) "
													+" LEFT JOIN job_code_details_info jdi ON (jdi.Job_Code_Id = ei.Job_Id AND   "
															+" CONCAT(DATE_FORMAT(jdi.Transaction_Date, '%Y%m%d'), jdi.`Job_Code_Sequence_Id`) =  "
															+" ( "
															+" SELECT MAX(CONCAT(DATE_FORMAT(jdi1.Transaction_Date, '%Y%m%d'), jdi1.`Job_Code_Sequence_Id`)) "
															+" FROM job_code_details_info jdi1 "
															+" WHERE jdi.`Job_Code_Id` = jdi1.`Job_Code_Id` AND jdi1.Transaction_Date <= CURRENT_DATE() "
															+" ) ) "
													+" WHERE  "
													+"  CONCAT(DATE_FORMAT(ei.transaction_date, '%Y%m%d'), ei.Sequence_Id) =  "
													+" ( "
													+" SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), vdi1.Sequence_Id)) "
													+" FROM employee_information vdi1 "
													+" WHERE ei.Unique_Id = vdi1.Unique_Id AND vdi1.transaction_date <= CURRENT_DATE() "
													+" 	)  ";
												
													 if(customerId != null && customerId >0){
														 q= q+" and ei.customer_id ='"+customerId+"'";
													 }
													 if(CompanyId != null && CompanyId >0){
														 q= q+" and ei.company_id ='"+CompanyId+"'";
													 }
													 if(employeeCountryId != null && employeeCountryId >0){
														 q= q+" and ei.Employee_Country ='"+employeeCountryId+"'";
													 }
													 if(jobTypeId != null && jobTypeId >0){
														 q= q+"and ei.Job_Type_Id ='"+jobTypeId+"'";
													 }
													 if(jobStatusId != null && jobStatusId >0){
														 q= q+" and ei.Job_Status_Id ='"+jobStatusId+"'";
													 }
													 if(employeeCode != null && !employeeCode.isEmpty()){
														 q= q+" and ei.Employee_Code ='"+employeeCode+"'";
													 }	
													 	 q=q+" order by employee_code asc ";
			
			Query q1 = (Query) session.createSQLQuery(q);
			
			for (Object bloodGroup  : q1.list()) {
				Object[] obj = (Object[]) bloodGroup;
				EmployeeInformationVo sobj = new EmployeeInformationVo();
				sobj.setEmployeeId((Integer)obj[0]);
				sobj.setEmployeeCode(obj[1]+"");
				sobj.setFirstName(obj[2]+"");
				sobj.setLocationName(obj[3]+"");
				sobj.setIsActive(obj[4]+"");
				sobj.setDepartmentName((String)obj[6]);
				sobj.setJobCode((String)obj[5]);
				SobjempList.add(sobj);
			}			
		} catch (Exception e) {
			log.error("Error Occured ",e);

		}

		return SobjempList;

	}
	
	
	
	
	public Integer saveEmployeeDetails(EmployeeInformationVo employeeVo) {	
		Integer employeeId = 0;
		Session session = this.sessionFactory.openSession();
		BigInteger adressSequenceId = new BigInteger("0");
		try {	
			session.beginTransaction();	
			EmployeeInformation employeeInformation = new EmployeeInformation();			
			employeeInformation.setEmployeeCode(employeeVo.getEmployeeCode());
			employeeInformation.setCompanyDetails(new CompanyDetails(employeeVo.getCompanyId()));
			employeeInformation.setCustomerDetails(new CustomerDetails(employeeVo.getCustomerId()));
			employeeInformation.setEmployeeCountry(employeeVo.getEmployeeCountryId());
			employeeInformation.setJobTypeId(employeeVo.getJobTypeId());
			employeeInformation.setJobStatusId(employeeVo.getJobStatusId());
			
			employeeInformation.setTransactionDate(employeeVo.getTransactionDate());
			employeeInformation.setIsActive(employeeVo.getIsActive());
			employeeInformation.setFirstName(employeeVo.getFirstName());
			employeeInformation.setMiddleName(employeeVo.getMiddleName());
			employeeInformation.setLastName(employeeVo.getLastName());
			employeeInformation.setFatherOrSpouse(employeeVo.getFatherOrSpouse());
			employeeInformation.setFatherOrSpouseName(employeeVo.getFatherOrSpouseName());
			employeeInformation.setPanNumber(employeeVo.getPanNumber());
			employeeInformation.setDateOfBirth(employeeVo.getDateOfBirth());
			employeeInformation.setGender(employeeVo.getGender());
			employeeInformation.setMaritalStatus(employeeVo.getMaritalStatus());
			employeeInformation.setBloodGroup(employeeVo.getBloodGroup());
			employeeInformation.setPanNumber(employeeVo.getPanNumber());
			employeeInformation.setGender(employeeVo.getGender());
			employeeInformation.setPhoneNumber(employeeVo.getPhoneNumber());
			employeeInformation.setEmail(employeeVo.getEmail());
			employeeInformation.setJobId(employeeVo.getJobId());
			employeeInformation.setStartDate(employeeVo.getStartDate());
			
			if(employeeVo.getLocationId() != null && employeeVo.getLocationId() > 0)
			employeeInformation.setLocationDetails(new LocationDetails(employeeVo.getLocationId()));
			if(employeeVo.getPlantId() != null && employeeVo.getPlantId() > 0)
				employeeInformation.setPlantDetails(new PlantDetails(employeeVo.getPlantId()));
			if(employeeVo.getDepartmentId() != null && employeeVo.getDepartmentId() > 0)
				employeeInformation.setDepartmentDetails(new DepartmentDetails(employeeVo.getDepartmentId()));
			if(employeeVo.getSectionId() != null && employeeVo.getSectionId() > 0)
				employeeInformation.setSectionDetails(new SectionDetails(employeeVo.getSectionId()));
			if(employeeVo.getWorkAreaId() != null && employeeVo.getWorkAreaId() > 0)
				employeeInformation.setWorkAreaDetails(new WorkAreaDetails(employeeVo.getWorkAreaId()));
			
			//employeeInformation.setDepartmentId(employeeVo.getDepartmentId());
			employeeInformation.setReportingMangerId(employeeVo.getReportingMangerId());
			employeeInformation.setAddress1(employeeVo.getAddress1());
			employeeInformation.setAddress2(employeeVo.getAddress2());
			employeeInformation.setAddress3(employeeVo.getAddress3());
			employeeInformation.setCountry(employeeVo.getCountry());
			employeeInformation.setState(employeeVo.getState());
			employeeInformation.setCity(employeeVo.getCity());
			employeeInformation.setPincode(employeeVo.getPincode());							
			employeeInformation.setCreatedBy(1);			
			employeeInformation.setCreatedDate(new Date());
			employeeInformation.setModifiedBy(1);
			employeeInformation.setModifiedDate(new Date());
			
				
			if(employeeVo != null && employeeVo.getEmployeeId() != null && employeeVo.getEmployeeId() > 0){
				employeeInformation.setEmployeeId(employeeVo.getEmployeeId());	
				 employeeInformation.setUniqueId(employeeVo.getUniqueId());
				 employeeInformation.setSequenceId(employeeVo.getSequenceId());
				 session.update(employeeInformation);					
				employeeId = employeeInformation.getEmployeeId();
			}else{	
				if(employeeVo != null && employeeVo.getUniqueId() > 0){	
					adressSequenceId  = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Sequence_Id),0) FROM `employee_information` e WHERE  e.`Transaction_Date` = '"+DateHelper.convertDateToSQLString(employeeVo.getTransactionDate())+"' and customer_id='"+employeeVo.getCustomerId()+"' and e.Unique_Id = "+employeeVo.getUniqueId() +" ").list().get(0);
					employeeInformation.setUniqueId(employeeVo.getUniqueId());	
				}else {
					BigInteger uniqueId = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(unique_id),0) as id FROM employee_information").list().get(0);
					employeeInformation.setUniqueId(uniqueId.intValue()+1);					
				}
				employeeInformation.setSequenceId(adressSequenceId.intValue()+1);
				
				employeeId = (Integer) session.save(employeeInformation);					
			}
			
			
			
			 }catch (Exception e) {
				 log.error("Error Occured ",e);
				 if(!(session.getTransaction().wasRolledBack())){
					 session.getTransaction().rollback();
				 }
			 }

		if(!(session.getTransaction().wasCommitted())){
			 session.getTransaction().commit();
			 session.flush();
			 session.clear();
		 }
		return employeeId;

	}
	
	
	
	public List<EmployeeInformationVo> employeeDetailsByemployeeId(Integer employeeId) {
				Session session = sessionFactory.getCurrentSession();
				CustomerDetails customersList = null;
				List employeeList = new ArrayList<>();
				
				try {				
					List empList  =session.createSQLQuery(" select ei.Employee_Id,ei.Customer_Id, ei.Company_Id, ei.Employee_Code, ei.Transaction_Date, ei.Sequence_Id, ei.Unique_Id,  "+
															" ei.First_Name, ei.Middle_Name, ei.Last_Name, ei.Father_Or_Spouse, ei.Father_Or_Spouse_Name, ei.PAN_Number, ei.Date_Of_Birth,   "+
															" ei.Gender, ei.Marital_Status, ei.Blood_Group, ei.Phone_Number, ei.Email, Address_1, ei.Address_2, ei.Address_3,ei.Country, ei.State,   "+
															" ei.City, ei.Pincode, ei.Is_Active, ei.Job_Id, ei.Location_Id, ei.Department_Id, ei.Reporting_Manger_id, ei.Start_Date, ei.Created_By,   "+
															" ei.Created_date, ei.Modified_By, ei.Modified_Date, ei.Photo, ei.Employee_Country, ei.Job_Type_Id, ei.Job_Status_Id,ci.Customer_Name,  "+
															" cp.Company_Name,mc.Country_name as c1,jt.Job_name,js.Job_Status,jcd.Job_Code,mc1.Country_name as c2,ms.state_name,ei.plant_id,ei.section_id,ei.work_area_id "+
															"  from employee_information ei "
															+" left join customer_details_info ci on (ci.customer_id = ei.customer_id) "
															+" left join company_details_info cp on(cp.company_id = ei.company_id) "
															+" left join m_country mc on(mc.Country_Id = ei.Employee_Country) "
															+" left join m_job_type jt on (jt.JobId = ei.Job_Type_Id) "
															+" left join m_job_status js on  (js.Job_Status_Id =ei.Job_Status_Id) "
															+" left join m_country mc1 on(mc1.Country_Id = ei.country) "
															+" left join m_state ms on(ms.state_id = ei.state) "
															+" left join job_code_details jcd on(jcd.Job_Code_Id = ei.job_id) "
															+" where "
															+" ei.transaction_date <= (select max(d.transaction_date) from employee_information d where ei.transaction_date <= current_date() " 
															+"  and d.Employee_Id = ei.Employee_Id)  "
															+"  and ei.Sequence_Id in(select max(Sequence_Id)  "
															+"  from employee_information c where c.transaction_date <= current_date()  "
															+"  and c.Employee_Id = ei.Employee_Id) and  "
															
															+" ci.transaction_date <= (select max(d.transaction_date) from customer_details_info d where ci.transaction_date <= current_date()  "
															+" and d.Customer_Id = ci.Customer_Id)  "
															+" and ci.Customer_Sequence_Id in(select max(Customer_Sequence_Id)  "
															+" from customer_details_info c where c.transaction_date <= current_date()  "
															+"  and c.Customer_Id = ci.Customer_Id)   and "
															                                                 
															+" 	cp.transaction_date <= (select max(d.transaction_date) from company_details_info d where cp.transaction_date <= current_date()  "
															+" and d.Company_Id = cp.Company_Id)  "
															+" and cp.Company_Sequence_Id in(select max(Company_Sequence_Id)  "
															+"  from company_details_info c where c.transaction_date <= current_date()  "
															+"  and c.Company_Id = cp.Company_Id) and ei.employee_id ="+employeeId).list();		
					for(int i=0; i<empList.size();i++){
						Object[] obj = (Object[]) empList.get(0);
						EmployeeInformationVo employeeInformation = new EmployeeInformationVo();
						employeeInformation.setEmployeeId((Integer)obj[0]);
						employeeInformation.setCustomerId((Integer)obj[1]);
						employeeInformation.setCompanyId((Integer)obj[2]);
						employeeInformation.setEmployeeCode(obj[3]+"");
						employeeInformation.setTransactionDate(DateHelper.convertSQLDateToDate((java.sql.Date)obj[4]));
						employeeInformation.setSequenceId((Integer)obj[5]);
						employeeInformation.setUniqueId((Integer)obj[6]);						
						employeeInformation.setFirstName(obj[7]+"");
						if(obj[8] != null)
						employeeInformation.setMiddleName(obj[8]+"");
						employeeInformation.setLastName(obj[9]+"");
						if(obj[10] != null)
						employeeInformation.setFatherOrSpouse(obj[10]+"");
						if(obj[11] != null)
						employeeInformation.setFatherOrSpouseName(obj[11]+"");
						if(obj[12] != null)
						employeeInformation.setPanNumber(obj[12]+"");
						employeeInformation.setDateOfBirth(DateHelper.convertSQLDateToDate((java.sql.Date)obj[13]));
						employeeInformation.setGender(obj[14]+"");
						employeeInformation.setMaritalStatus(obj[15]+"");
						employeeInformation.setBloodGroup(obj[16]+"");
						if(obj[17] != null)
						employeeInformation.setPhoneNumber(obj[17]+"");
						if(obj[18] != null)
						employeeInformation.setEmail(obj[18]+"");
						if(obj[19] != null)
						employeeInformation.setAddress1(obj[19]+"");
						if(obj[20] != null)
						employeeInformation.setAddress2(obj[20]+"");
						if(obj[21] != null)
						employeeInformation.setAddress3(obj[21]+"");
						if(obj[22] != null)
						employeeInformation.setCountry((Integer)obj[22]);
						if(obj[23] != null)
						employeeInformation.setState((Integer)obj[23]);
						if(obj[24] != null)
						employeeInformation.setCity(obj[24]+"");
						if(obj[25] != null)
						employeeInformation.setPincode((Integer)obj[25]);
						employeeInformation.setIsActive(obj[26]+"");
						if(obj[27] != null)
						employeeInformation.setJobId((Integer)obj[27]);
						if(obj[28] != null)
						employeeInformation.setLocationId((Integer)obj[28]);
						if(obj[29] != null)
						employeeInformation.setDepartmentId((Integer)obj[29]);
						if(obj[30] != null)
						employeeInformation.setReportingMangerId((Integer)obj[30]);
						if(obj[31] != null)
						employeeInformation.setStartDate(DateHelper.convertSQLDateToDate((java.sql.Date)obj[31]));
						//employeeInformation.setPhoto(Byte[]z); 36
						if(obj[37] != null)
						employeeInformation.setEmployeeCountryId((Integer)obj[37]);
						if(obj[38] != null)
						employeeInformation.setJobTypeId((Integer)obj[38]);
						if(obj[39] != null)
						employeeInformation.setJobStatusId((Integer)obj[39]);
						employeeInformation.setCustomerName(obj[40]+"");
						employeeInformation.setCompanyName(obj[41]+"");
						employeeInformation.setEmployeeCountryName(obj[42]+"");
						employeeInformation.setJobType(obj[43]+"");
						employeeInformation.setJobStatus(obj[44]+"");
						employeeInformation.setJobCode(obj[45]+"");
						if(obj[46] != null)
						employeeInformation.setCountryName(obj[46]+"");
						if(obj[47] != null)
						employeeInformation.setStateName(obj[47]+"");
						if(obj[48] != null)
							employeeInformation.setPlantId((Integer)obj[48]);
						if(obj[49] != null)
							employeeInformation.setSectionId((Integer)obj[49]);
						if(obj[50] != null)
							employeeInformation.setWorkAreaId((Integer)obj[50]);
						employeeList.add(employeeInformation);						
					}					
										
				} catch (Exception e) {
					log.error("Error Occured ",e);					
				}
				
				return employeeList;
				
			}
	
	
	public List<SimpleObject> getTransactionListForEmployeeInfo(Integer uniqueId) {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> transactionList = new ArrayList<>();
		try {
			List datesList = session.createSQLQuery("SELECT `Employee_Id` AS id ,CONCAT(DATE_FORMAT(`Transaction_Date`,'%d/%m/%Y'),' - ',`Sequence_Id`) AS cname  FROM `employee_information` e  WHERE  Unique_Id="+uniqueId+"").list();
			for (Object countryObject  : datesList) {
				Object[] obj = (Object[]) countryObject;
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
	public List<SimpleObject> getDepartmentListByCompanyId(Integer customerId, Integer CompanyId) {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> departmentList = new ArrayList<>();
		try {
			List datesList = session.createSQLQuery(" SELECT  `Department_Id`,`Department_Name` FROM department_details_info  info WHERE CONCAT(DATE_FORMAT(info.transaction_date, '%Y%m%d'), info.`Department_Sequence_Id`) =  ( SELECT MAX(CONCAT(DATE_FORMAT(info1.transaction_date, '%Y%m%d'), info1.`Department_Sequence_Id`)) FROM department_details_info info1 WHERE info.`Department_Id` = info1.`Department_Id`  AND info1.transaction_date <= CURRENT_DATE() ) AND info.Customer_Id="+customerId+"   AND  info.`Company_Id` ="+CompanyId+" AND info.Is_Active = 'Y' ORDER BY Department_Name asc").list();
			for (Object countryObject  : datesList) {
				Object[] obj = (Object[]) countryObject;
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
	public List<SimpleObject> getEmployeesList(Integer customerId,Integer companyId){
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> employeeList = new ArrayList<>();
		try {
			List datesList = session.createSQLQuery(" SELECT  Unique_Id,Employee_Code, replace((CONCAT(RTRIM(First_Name), ' ',CASE WHEN (Middle_Name IS NULL) THEN '' ELSE CONCAT(RTRIM(Middle_Name),' ') END, Last_Name)),'  ',' ') AS Employee_Name FROM employee_information  info WHERE CONCAT(DATE_FORMAT(info.Transaction_Date, '%Y%m%d'), info.`Sequence_Id`) =  ( SELECT MAX(CONCAT(DATE_FORMAT(info1.Transaction_Date, '%Y%m%d'), info1.`Sequence_Id`)) FROM employee_information info1 WHERE info.`Unique_Id` = info1.`Unique_Id`  AND info1.Transaction_Date <= CURRENT_DATE() ) AND info.Customer_Id="+customerId+"   AND  info.`Company_Id` ="+companyId+" AND info.Is_Active = 'Y' ORDER BY First_Name asc").list();
			for (Object countryObject  : datesList) {
				Object[] obj = (Object[]) countryObject;
				SimpleObject object = new SimpleObject();
				object.setId((Integer)obj[0]);
				object.setName(obj[1]+"");
				object.setDesc((String)obj[2]);
				
				employeeList.add(object);
			}			
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}

		return employeeList;
	}
	
	

}
