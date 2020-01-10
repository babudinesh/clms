package com.Ntranga.CLMS.Dao;

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

import com.Ntranga.CLMS.vo.EmployeeInformationVo;
import com.Ntranga.CLMS.vo.ManpowerRequisitionSkillsVo;
import com.Ntranga.CLMS.vo.ManpowerRequisitionVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.core.DateHelper;
import com.Ntranga.core.CLMS.entities.CompanyDetails;
import com.Ntranga.core.CLMS.entities.CustomerDetails;
import com.Ntranga.core.CLMS.entities.MCountry;
import com.Ntranga.core.CLMS.entities.ManpowerRequisition;
import com.Ntranga.core.CLMS.entities.ManpowerRequisitionDaywise;
import com.Ntranga.core.CLMS.entities.ManpowerRequisitionSkills;
import common.Logger;

@SuppressWarnings("rawtypes")
@Transactional
@Repository("manpowerRequisitionDao")
public class ManpowerRequisitionDaoImpl implements ManpowerRequisitionDao{

	private static Logger log = Logger.getLogger(ManpowerRequisitionDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	/*
	 * This method will be used to save or update the  Manpower Requisition details
	 */
	@Override
	public Integer saveManpowerRequisition(ManpowerRequisitionVo paramManpower) {
		log.info("Entered in saveManpowerRequisition()  ::   "+paramManpower);

		Session session = sessionFactory.getCurrentSession();
		Integer manpowerRequistionId = 0;
		//Integer holidayCalendarId = 0;
		//BigInteger plantSequenceId = new BigInteger("0");	
		
		
		ManpowerRequisition requisition = new ManpowerRequisition();
		ManpowerRequisitionSkills skills = new ManpowerRequisitionSkills();
		
		try{	
			if(paramManpower != null && paramManpower.getManpowerRequisitionId() != null &&  paramManpower.getManpowerRequisitionId() > 0){
				requisition = (ManpowerRequisition) session.load(ManpowerRequisition.class, paramManpower.getManpowerRequisitionId());
			}
			requisition.setCustomerDetails(new CustomerDetails(paramManpower.getCustomerId()));
			requisition.setCompanyDetails(new CompanyDetails(paramManpower.getCompanyId()));
			requisition.setCountryDetails(new MCountry(paramManpower.getCountryId()));
			requisition.setRequestCode(paramManpower.getRequestCode().toUpperCase());
			requisition.setLocationId(paramManpower.getLocationId());
			requisition.setDepartmentId(paramManpower.getDepartmentId());
			requisition.setPlantId(paramManpower.getPlantId());
			requisition.setRequestDate(paramManpower.getRequestDate());
			//System.out.println(paramManpower.getStatus());
			requisition.setStatus(paramManpower.getStatus());
			requisition.setEmployeeId(paramManpower.getEmployeeId());
			//requisition.setIndenterDate(paramManpower.getIndenterDate());
			requisition.setEmployeeContactNumber(paramManpower.getEmployeeContactNumber());
			requisition.setEmployeeName(paramManpower.getNameOfTheIndenter());
			requisition.setJustificationForManpower(paramManpower.getJustificationForManpower());
			requisition.setRequiredFor(paramManpower.getRequiredFor());
			requisition.setRequestReason(paramManpower.getRequestReason());
			requisition.setTotalHeadCount(paramManpower.getTotalHeadCount());
			requisition.setCostCenterId(paramManpower.getCostCenterId());
			requisition.setRequestType(paramManpower.getRequestType());
			//requisition.setFrequency(paramManpower.getFrequency());
			//requisition.setYear(paramManpower.getYear());
			//requisition.setMonth(paramManpower.getMonth());
			//requisition.setEffectiveDate(paramManpower.getEffectiveDate());
			requisition.setComments(paramManpower.getComments());
			requisition.setApprovedBy(paramManpower.getApprovedBy());
			requisition.setApprovedDate(paramManpower.getApprovedDate());
			requisition.setModifiedBy(paramManpower.getModifiedBy());
			requisition.setModifiedDate(new Date());
			
			if(paramManpower != null && paramManpower.getManpowerRequisitionId() != null &&  paramManpower.getManpowerRequisitionId() > 0){
				System.out.println(requisition.getStatus());
				System.out.println(requisition.toString());
				session.update(requisition);
				manpowerRequistionId  = paramManpower.getManpowerRequisitionId();
			}else{
				requisition.setCreatedBy(paramManpower.getCreatedBy());
				requisition.setCreatedDate(new Date());
				manpowerRequistionId = (Integer) session.save(requisition);
			}
			
			session.flush();
			
			
			if(manpowerRequistionId > 0  && paramManpower.getManpowerSkillTypesList() != null && paramManpower.getManpowerSkillTypesList().size() > 0){
				Query q = session.createQuery("DELETE FROM ManpowerRequisitionSkills  WHERE manpowerRequisitionDetails = "+manpowerRequistionId);
				q.executeUpdate();
				
				if(paramManpower.getManpowerSkillTypesList() != null ){
					for(ManpowerRequisitionSkillsVo skillVo : paramManpower.getManpowerSkillTypesList()){
						skills = new ManpowerRequisitionSkills();
						skills.setManpowerRequisitionDetails(new ManpowerRequisition(manpowerRequistionId));
						skills.setCustomerDetails(new CustomerDetails(paramManpower.getCustomerId()));
						skills.setCompanyDetails(new CompanyDetails(paramManpower.getCompanyId()));
						skills.setCountryDetails(new MCountry(paramManpower.getCountryId()));
						skills.setLocationId(paramManpower.getLocationId());
						skills.setPlantId(paramManpower.getPlantId());
						skills.setDepartmentId(paramManpower.getDepartmentId());
						skills.setSkillType(skillVo.getSkillType());
						skills.setJobCodeId(skillVo.getJobCodeId());
						//skills.setAssignedWorkers(skillVo.getAssignedWorkers());
						skills.setRequiredWorkers(skillVo.getRequiredWorkers());
						skills.setFromDate(skillVo.getFromDate());
						skills.setToDate(skillVo.getToDate());
						skills.setTotalDays(skillVo.getTotalDays());
						//skills.setTotalCount(skillVo.getTotalCount());
						skills.setCreatedBy(paramManpower.getCreatedBy());
						skills.setCreatedDate(new Date());
						skills.setModifiedBy(paramManpower.getCreatedBy());
						skills.setModifiedDate(new Date());
						session.save(skills);	
						
						session.flush();
						
						Integer[] count = new Integer[367];
						Integer[] id = new Integer[400];
						
						if(paramManpower.getStatus().equalsIgnoreCase("Approved")){
							List countList = session.createSQLQuery("Select Required_Workers, Day_Wise_Id, Business_Date FROM manpower_requisition_daywise "
									+ " WHERE Job_Skill = '"+skillVo.getSkillType()+"' "
									+ " AND job_code_id = "+skillVo.getJobCodeId()
									+ " AND Customer_Id = "+paramManpower.getCustomerId()
									+ " AND Company_Id = "+paramManpower.getCompanyId()
									+ " AND Location_Id = "+paramManpower.getLocationId()
									+ " AND Plant_Id = "+paramManpower.getPlantId()
									+ " AND Department_Id = "+paramManpower.getDepartmentId()
									+ " AND Business_Date between '"+DateHelper.convertDateToSQLDate(skills.getFromDate())+"' AND '"+DateHelper.convertDateToSQLDate(skills.getToDate())+"' "
									+ " GROUP BY Job_Skill,Business_Date ORDER BY Business_Date").list();
							
							List<Date> datesList = DateHelper.getDatesBetweenDates(skills.getFromDate(), skills.getToDate());
							
							List<Date> dtList = new ArrayList<>();
							dtList.addAll(datesList);
							if(datesList != null && datesList.size() > 0){
								System.out.println("Dates List : "+datesList.size());
								int datecount =0;
								for(Date d : datesList){
									
									if(datesList != null && countList != null && countList.size() > 0){
										for(Object object : countList){
											//System.out.println("datecount122::"+datecount+":::dtList:"+dtList.size());
											int i = 0;
											Object[] obj = (Object[]) object;
											//System.out.println(obj);
											if(obj != null){
												count[i] = (Integer) obj[0];
												id[i] = (Integer) obj[1];
												//System.out.println("Compare : "+d+ "@@@@@@@@@   "+d.compareTo((Date)obj[2])+" ::  "+i);
												if(obj[2] != null && d.compareTo((Date)obj[2]) == 0){
													//System.out.println("datecount1ManpowerRequisitionDaywise::"+datecount+":::dtList:"+dtList.size());
													ManpowerRequisitionDaywise day = new ManpowerRequisitionDaywise();
													if(paramManpower.getRequiredFor().equalsIgnoreCase("Addition")){
														day.setRequiredWorkers(skills.getRequiredWorkers()+count[i]);
													}else if(paramManpower.getRequiredFor().equalsIgnoreCase("Reduction")){
														day.setRequiredWorkers(count[i] - skills.getRequiredWorkers());
													}
													Query q4 = session.createSQLQuery("REPLACE INTO manpower_requisition_daywise(Customer_Id, Company_Id, Location_Id, Plant_Id, Department_Id, Job_Skill, Business_Date, Required_Workers, Created_By, Created_Date, Modified_By, Modified_Date,job_code_id ) "
													+ "     VALUES("+paramManpower.getCustomerId()+", "+paramManpower.getCompanyId()+", "+paramManpower.getLocationId()+", "+paramManpower.getPlantId()+", "+paramManpower.getDepartmentId()+",'"+skills.getSkillType()+"', '"+DateHelper.convertDateToSQLDate(d)+"', "+day.getRequiredWorkers()+", "
													+paramManpower.getCreatedBy()+", '"+DateHelper.convertDateTimeToSQLString(new Date())+"', "+paramManpower.getModifiedBy()+", '"+DateHelper.convertDateTimeToSQLString(new Date())+"','"+skills.getJobCodeId()+"') ");
													q4.executeUpdate();
													//System.out.println("datecount::"+datecount+":::dtList:"+dtList.size());
													if(dtList.size() > datecount)
														dtList.remove(datecount);
												}//date comparision if
												
											}//if
											i++;
										}//for(countList for-loop)
									}//if(count list if)
									datecount++;
								}//for(datesList- for loop)
								if(dtList != null && dtList.size() > 0 && countList!= null && countList.size() == 0){
									for(Date d : dtList){
										ManpowerRequisitionDaywise day = new ManpowerRequisitionDaywise();
										if(paramManpower.getRequiredFor().equalsIgnoreCase("Addition")){
											day.setRequiredWorkers(skills.getRequiredWorkers()+0);
										}else if(paramManpower.getRequiredFor().equalsIgnoreCase("Reduction")){
											day.setRequiredWorkers(0 - skills.getRequiredWorkers());
										}
										Query q4 = session.createSQLQuery("REPLACE INTO manpower_requisition_daywise(Customer_Id, Company_Id, Location_Id, Plant_Id, Department_Id, Job_Skill, Business_Date, Required_Workers, Created_By, Created_Date, Modified_By, Modified_Date,Job_Code_Id ) "
										+ "     VALUES("+paramManpower.getCustomerId()+", "+paramManpower.getCompanyId()+", "+paramManpower.getLocationId()+", "+paramManpower.getPlantId()+", "+paramManpower.getDepartmentId()+",'"+skills.getSkillType()+"', '"+DateHelper.convertDateToSQLDate(d)+"', "+day.getRequiredWorkers()+", "
										+paramManpower.getCreatedBy()+", '"+DateHelper.convertDateTimeToSQLString(new Date())+"', "+paramManpower.getModifiedBy()+", '"+DateHelper.convertDateTimeToSQLString(new Date())+"','"+skills.getJobCodeId()+"') ");
										q4.executeUpdate();
									}
								}
								
							}//if
							
						}//if status-Approved
					}//for skills-for loop
				}//if
			}else if(manpowerRequistionId > 0 && paramManpower.getManpowerSkillTypesList() == null ||(paramManpower.getManpowerSkillTypesList() != null && paramManpower.getManpowerSkillTypesList().size() == 0)){
				Query q = session.createQuery("DELETE FROM ManpowerRequisitionSkills  WHERE manpowerRequisitionDetails = "+manpowerRequistionId);
				q.executeUpdate();
			}
			session.flush();
			
			
			
		}catch (Exception e) {
			 if(!(session.getTransaction().wasRolledBack())){
				 session.getTransaction().rollback();
			 }
			 log.error("Error Occurred...   ", e);
			 log.info("Exiting from saveManpowerRequisition()  ::  manpowerRequistionId =  "+manpowerRequistionId);
		}				
		log.info("Exiting from saveManpowerRequisition()  ::  manpowerRequistionId =  "+manpowerRequistionId);
		return manpowerRequistionId;
	}

	/*
	 * This method will be used to search the Manpower Requisition list based on customer Id, companyId, countryId, location Id, 
	 * 																	request code and status
	 */
	@Override
	public List<ManpowerRequisitionVo> searchManpowerRequisitions(ManpowerRequisitionVo paramManpower) {
		log.info("Entered in  searchManpowerRequisitions()  ::   ManpowerRequisitionVo  = "+paramManpower);
		
		Session session = sessionFactory.getCurrentSession();
		List queryList = null;
		List<ManpowerRequisitionVo> returnList = new ArrayList<ManpowerRequisitionVo>();
		ManpowerRequisitionVo manpower = new ManpowerRequisitionVo();
		
		String hqlQuery = "SELECT  info.Request_Code, info.Status, info.Manpower_Requisition_Id, "
							+ " location.Location_Name, dept.Department_Name, info.Total_Head_Count, emp.First_Name, info.Employee_Name, plant.Plant_Name "
							+ " FROM manpower_requisition AS info "
							+ " LEFT JOIN company_details AS company  ON company.Customer_Id = info.Customer_Id  AND company.Company_Id = info.Company_Id "
							+ " LEFT JOIN customer_details AS customer  ON customer.Customer_Id = info.Customer_Id "
							+ " LEFT JOIN location_details_info location ON  location.Location_Id = info.Location_Id "
																	+ " AND  CONCAT(DATE_FORMAT(location.Transaction_Date, '%Y%m%d'), location.Location_Sequence_Id) =  ( "
																	+"	SELECT MAX(CONCAT(DATE_FORMAT(location1.Transaction_Date, '%Y%m%d'), location1.Location_Sequence_Id)) "
																	+"	FROM location_details_info location1 "
																	+"	WHERE location.Location_Id = location1.Location_Id AND location1.Transaction_Date <= CURRENT_DATE() "
																	+"	) "
							+ " LEFT JOIN plant_details_info plant ON  plant.Plant_Id = info.Plant_Id "
																	+ " AND  CONCAT(DATE_FORMAT(plant.Transaction_Date, '%Y%m%d'), plant.plant_Sequence_Id) =  ( "
																	+"	SELECT MAX(CONCAT(DATE_FORMAT(plant1.Transaction_Date, '%Y%m%d'), plant1.plant_Sequence_Id)) "
																	+"	FROM plant_details_info plant1 "
																	+"	WHERE plant.Plant_Id = plant1.Plant_Id AND plant1.Transaction_Date <= CURRENT_DATE() "
																	+"	) "
							+ " LEFT JOIN department_details_info dept ON  dept.Department_Id = info.Department_Id "
																	+ " AND  CONCAT(DATE_FORMAT(dept.Transaction_Date, '%Y%m%d'), dept.Department_Sequence_Id) =  ( "
																	+"	SELECT MAX(CONCAT(DATE_FORMAT(dept1.Transaction_Date, '%Y%m%d'), dept1.Department_Sequence_Id)) "
																	+"	FROM department_details_info dept1 "
																	+"	WHERE dept.Department_Id = dept1.Department_Id AND dept1.Transaction_Date <= CURRENT_DATE() "
																	+"	) "
							+ " LEFT JOIN employee_information emp ON  emp.Unique_Id = info.Employee_Id "
																	+ " AND  CONCAT(DATE_FORMAT(emp.Transaction_Date, '%Y%m%d'), emp.Sequence_Id) =  ( "
																	+"	SELECT MAX(CONCAT(DATE_FORMAT(emp1.Transaction_Date, '%Y%m%d'), emp1.Sequence_Id)) "
																	+"	FROM employee_information emp1 "
																	+"	WHERE emp.Unique_Id = emp1.Unique_Id AND emp1.Transaction_Date <= CURRENT_DATE() "
																	+"	) "

							//+ " LEFT JOIN m_country country ON  country.Country_Id = info.Country_Id "
							+ " WHERE 1= 1  AND emp.Is_Active = 'Y' ";
		
		
		if(paramManpower.getCustomerId() != null && paramManpower.getCustomerId() > 0){
			hqlQuery += "  AND  info.Customer_Id = "+paramManpower.getCustomerId();
		}
		
		if(paramManpower.getCompanyId() != null && paramManpower.getCompanyId() > 0){
			hqlQuery += "  AND  info.Company_Id = "+paramManpower.getCompanyId();
		}
		if(paramManpower.getLocationId() != null && paramManpower.getLocationId() > 0){
			hqlQuery += "  AND  info.Location_Id = "+paramManpower.getLocationId();
		}
		
		if(paramManpower.getDepartmentId() != null && paramManpower.getDepartmentId() > 0){
			hqlQuery += " AND info.Department_Id = "+paramManpower.getDepartmentId();
		}
		
		if(paramManpower.getRequestCode() != null && !paramManpower.getRequestCode().isEmpty()){
			hqlQuery += " AND info.Request_Code LIKE '"+paramManpower.getRequestCode()+"%' ";
		}
		
		if(paramManpower.getStatus() != null && !paramManpower.getStatus().isEmpty()){
			hqlQuery += " AND info.Status = '"+paramManpower.getStatus()+"' ";
		}
		
		
		
		hqlQuery += " GROUP BY info.Manpower_Requisition_Id Order By info.Request_Code asc";
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			queryList = query.list();
			if(queryList.size() > 0){
				for (Object manpowerVo : queryList) {
					Object[] obj = (Object[]) manpowerVo;
					manpower = new ManpowerRequisitionVo();

					manpower.setRequestCode((String)obj[0]);
					manpower.setStatus((String)obj[1]);
					manpower.setManpowerRequisitionId((Integer)obj[2]);
					manpower.setLocationName((String)obj[3]);
					manpower.setDepartmentName((String)obj[4]);
					manpower.setTotalHeadCount((Integer)obj[5]);
					manpower.setEmployeeName((String)obj[6]);
					manpower.setNameOfTheIndenter((String)obj[7]);
					manpower.setPlantName((String)obj[8]);
					
					returnList.add(manpower);
				}	
			}
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from searchManpowerRequisitions()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from searchManpowerRequisitions()  ::   "+returnList);
		return returnList;
	}

	/*
	 *This method will be used to get the manpower details by given manpower requisition id
	 */
	@Override
	public List<ManpowerRequisitionVo> getManpowerRequisitionById(Integer manpowerRequisitionId) {
		log.info("Entered in  getManpowerRequisitionById()  ::   manpowerRequisitionId = "+manpowerRequisitionId);
		
		Session session = sessionFactory.getCurrentSession();

		List<ManpowerRequisitionVo> returnList = new ArrayList<ManpowerRequisitionVo>();
		List<ManpowerRequisitionSkillsVo> manpowerSkillsList = new ArrayList<ManpowerRequisitionSkillsVo>();
		
		String  manpowerQuery = " SELECT info.Customer_Id, info.Company_Id, info.Country_Id, info.Request_Code, "
							+ "	info.Location_Id, info.Department_Id, info.Status, info.Employee_Id, info.Request_Date, "
							+ " info.Employee_Contact_Number,  info.Justification_For_Manpower, "
							+ " info.Required_For, info.Request_Reason, "
							+ " info.Cost_Center_Id, info.Total_Head_Count, info.Plant_Id, info.Request_Type, "
							+ "  info.Employee_Name,'', "
							+ " info.Comments, info.Approved_By, info.Approved_Date "
							+ " FROM manpower_requisition AS info "
							+ " WHERE info.Manpower_Requisition_Id = "+manpowerRequisitionId;
		
		try {				
			SQLQuery manpower = session.createSQLQuery(manpowerQuery);
			List queryList = manpower.list();

			for (Object object : queryList) {
				Object[] obj = (Object[]) object;
				ManpowerRequisitionVo manpowerVo = new ManpowerRequisitionVo();
				
				manpowerVo.setManpowerRequisitionId(manpowerRequisitionId);
				manpowerVo.setCustomerId((Integer)obj[0]);
				manpowerVo.setCompanyId((Integer)obj[1]);
				manpowerVo.setCountryId((Integer)obj[2]);
				manpowerVo.setRequestCode((String)obj[3]);
				manpowerVo.setLocationId((Integer)obj[4]);
				manpowerVo.setDepartmentId((Integer)obj[5]);
				manpowerVo.setStatus((String)obj[6]);
				manpowerVo.setEmployeeId((Integer)obj[7]);
				manpowerVo.setRequestDate((Date)obj[8]);
				manpowerVo.setEmployeeContactNumber((String)obj[9]);
				manpowerVo.setJustificationForManpower((String)obj[10]);
				manpowerVo.setRequiredFor((String)obj[11]);
				manpowerVo.setRequestReason((String)obj[12]);
				manpowerVo.setCostCenterId((Integer)obj[13]);
				manpowerVo.setTotalHeadCount((Integer)obj[14]);
				manpowerVo.setPlantId((Integer)obj[15]);
				manpowerVo.setRequestType((String)obj[16]);
				//manpowerVo.setFrequency((String)obj[17]);
				//manpowerVo.setYear((Integer)obj[18]);
				//manpowerVo.setMonth((String)obj[19]);
				manpowerVo.setNameOfTheIndenter((String)obj[17]);
				//manpowerVo.setEffectiveDate((Date)obj[18]);
				manpowerVo.setComments((String)obj[19]);
				manpowerVo.setApprovedBy((Integer)obj[20]);
				manpowerVo.setApprovedDate((Date)obj[21]);
				
				String  skillsQuery = " SELECT  skills.Skill_Type, job.Job_Name, skills.Job_Code_Id, "
						+ " skills.From_Date, skills.To_Date, skills.Total_Days,skills.Manpower_Skill_Id, "
						+ "  skills.Required_Workers"
						+ " FROM manpower_requisition_skills AS skills "
						+ " LEFT JOIN job_code_details_info job ON job.Job_Code_Id = skills.Job_Code_Id "
															+" AND CONCAT(DATE_FORMAT(job.transaction_date, '%Y%m%d'), LPAD(job.job_code_Sequence_Id, 2, '0')) =   "
															+" (  "   
														    +" SELECT MAX(CONCAT(DATE_FORMAT(job1.transaction_date, '%Y%m%d'), LPAD(job1.job_code_Sequence_Id, 2, '0')))  "  
															+" FROM job_code_details_info job1  " 
															+" WHERE  job.Job_Code_Id = job1.Job_Code_Id  " 
															+" AND job1.transaction_date <= CURRENT_DATE() )  "
						+ " WHERE skills.Manpower_Requisition_Id = "+manpowerRequisitionId;
				List skillsList = session.createSQLQuery(skillsQuery).list();

				for (Object skill : skillsList) {
					Object[] obj1 = (Object[]) skill;
					ManpowerRequisitionSkillsVo skillsVo = new ManpowerRequisitionSkillsVo();
					
					skillsVo.setManpowerRequisitionId(manpowerRequisitionId);
					skillsVo.setSkillType((String)obj1[0]);
					skillsVo.setJobName((String)obj1[1]);
					skillsVo.setJobCodeId((Integer)obj1[2]);
					skillsVo.setFromDate((DateHelper.convertDate((Date)obj1[3])));
					skillsVo.setToDate((DateHelper.convertDate((Date)obj1[4])));
					skillsVo.setTotalDays((Integer)obj1[5]);
					skillsVo.setManpowerSkillId((Integer)obj1[6]);
					//skillsVo.setAssignedWorkers((Integer)obj1[7]);
					skillsVo.setRequiredWorkers((Integer)obj1[7]);
					//skillsVo.setTotalCount((Integer)obj1[9]);
					
					manpowerSkillsList.add(skillsVo);
				}
				manpowerVo.setManpowerSkillTypesList(manpowerSkillsList);
				
				returnList.add(manpowerVo);
			}	
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from  getManpowerRequisitionById()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from  getManpowerRequisitionById()  ::   "+returnList);
		return returnList;
	}
	
	/*
	 * This method will be used to get the employees list based on given customer, company, location and department
	 */
	@Override
	public List<EmployeeInformationVo> getEmployeesDetails(ManpowerRequisitionVo paramManpower){
		log.info("Entered in  getEmployeesDetails()  ::   paramManpower = "+paramManpower);
		Session session = sessionFactory.getCurrentSession();
		List<EmployeeInformationVo> employeeList = new ArrayList<>();
		try {
			
			String  q = " SELECT DISTINCT emp.`unique_id`,`Employee_Code`,CONCAT(`First_Name`,' ',last_name) as FirstName,"
						+ " COALESCE(ldi.`Location_Name`,'') AS loc ,CASE emp.is_active WHEN 'Y' THEN 'Active' ELSE 'In-Active' END AS STATUS,"
						+ "  ddi.Department_Name, emp.Phone_Number, emp.Department_Id "
						+" FROM employee_information emp 	  "
							+" LEFT JOIN location_details_info ldi ON(ldi.Location_Id = emp.Location_Id AND   "
									+" CONCAT(DATE_FORMAT(ldi.Transaction_Date, '%Y%m%d'), ldi.`Location_Sequence_Id`) =  "
									+" ( "
									+" SELECT MAX(CONCAT(DATE_FORMAT(ldi1.Transaction_Date, '%Y%m%d'), ldi1.`Location_Sequence_Id`)) "
									+" FROM location_details_info ldi1 "
									+" WHERE ldi.`Location_Details_Id` = ldi1.`Location_Details_Id` AND ldi1.Transaction_Date <= CURRENT_DATE() "
									+" ) ) "
							+" LEFT JOIN department_details_info ddi ON(ddi.Department_Id = emp.Department_Id AND   "
									+" CONCAT(DATE_FORMAT(ddi.Transaction_Date, '%Y%m%d'), ddi.`Department_Sequence_Id`) =  "
									+" ( "
									+" SELECT MAX(CONCAT(DATE_FORMAT(ddi1.Transaction_Date, '%Y%m%d'), ddi1.`Department_Sequence_Id`)) "
									+" FROM department_details_info ddi1 "
									+" WHERE ddi.`Department_Id` = ddi1.`Department_Id` AND ddi1.Transaction_Date <= CURRENT_DATE() "
									+" ) ) "
						+" WHERE 1 = 1 ";
			if(paramManpower != null && paramManpower.getEmployeeId() != null && paramManpower.getEmployeeId() > 0){
				q += " AND emp.Unique_Id = "+paramManpower.getEmployeeId();
			}else{
						
				q += " AND  "
				+"  CONCAT(DATE_FORMAT(emp.transaction_date, '%Y%m%d'), emp.Sequence_Id) =  "
				+" ( SELECT MAX(CONCAT(DATE_FORMAT(emp1.transaction_date, '%Y%m%d'), emp1.Sequence_Id)) "
				+" FROM employee_information emp1 "
				+" WHERE emp.Unique_Id = emp1.Unique_Id AND emp1.transaction_date <= CURRENT_DATE() "
				+" 	)  ";
			}	
			
			if(paramManpower != null && paramManpower.getCustomerId() != null && paramManpower.getCustomerId() > 0){
				 q= q+" AND emp.Customer_Id ='"+paramManpower.getCustomerId()+"'";
			}
			
			if(paramManpower != null  && paramManpower.getCompanyId() != null && paramManpower.getCompanyId() > 0){
				 q= q+" AND emp.Company_Id ='"+paramManpower.getCompanyId()+"'";
			}
			
			if(paramManpower != null && paramManpower.getLocationId() != null && paramManpower.getLocationId() > 0){
				 q= q+" AND emp.Location_Id ='"+paramManpower.getLocationId()+"'";
			}
			
			if(paramManpower != null && paramManpower.getDepartmentId() != null && paramManpower.getDepartmentId() > 0){
				 q= q+" AND emp.Department_Id ='"+paramManpower.getDepartmentId()+"'";
			}
			 
			q=q+" ORDER BY Employee_Code ASC ";
			
			Query q1 = (Query) session.createSQLQuery(q);
			
			for (Object bloodGroup  : q1.list()) {
				Object[] obj = (Object[]) bloodGroup;
				EmployeeInformationVo employee = new EmployeeInformationVo();
				employee.setEmployeeId((Integer)obj[0]);
				employee.setEmployeeCode((String)obj[1]);
				employee.setFirstName((String)obj[2]);
				employee.setLocationName((String)obj[3]);
				employee.setIsActive((String)obj[4]);
				employee.setDepartmentName((String)obj[5]);
				employee.setPhoneNumber((String)obj[6]);
				employee.setDepartmentId((Integer)obj[7]);
				employeeList.add(employee);
			}			
		} catch (Exception e) {
			log.error("Error Occured ",e);
			log.info("Exiting from  getEmployeesDetails()  ::   "+employeeList);

		}
		log.info("Exiting from  getEmployeesDetails()  ::   "+employeeList);
		return employeeList;

	}
	
	@Override
	public List<SimpleObject> getJobCodeListBySkillType(ManpowerRequisitionVo paramManpower){
		log.info("Entered in  getJobCodeListBySkillType()  ::   paramManpower = "+paramManpower);
		Session session = sessionFactory.getCurrentSession();
		
		List<SimpleObject> jobnames = new ArrayList<SimpleObject>(); 
		try {
		
			String query =  "SELECT jobcode.`Job_Code_Id`, jobcode.`Job_Name`  FROM job_code_details_info jobcode  "
							+ " INNER JOIN job_code_details job ON job.Job_Code_Id = jobcode.Job_Code_Id "
							+ " WHERE CONCAT(DATE_FORMAT(jobcode.transaction_date, '%Y%m%d'), LPAD(jobcode.job_code_Sequence_Id, 2, '0')) =   "
							+ " (  "   
						    + " SELECT MAX(CONCAT(DATE_FORMAT(job1.transaction_date, '%Y%m%d'), LPAD(job1.job_code_Sequence_Id, 2, '0')))  "  
							+ " FROM job_code_details_info job1  " 
							+ " WHERE  job.Job_Code_Id = job1.Job_Code_Id  " 
							+ " AND job1.transaction_date <= CURRENT_DATE() )  "							
							+ " AND  jobcode.Customer_Id = "+paramManpower.getCustomerId()
							+ " AND  jobcode.Company_Id = "+paramManpower.getCompanyId();
			
			if(paramManpower.getSkillType() != null && paramManpower.getSkillType() != "" && !paramManpower.getSkillType().isEmpty()){
				query  += " AND  jobcode.Job_Category = '"+paramManpower.getSkillType()+"' ";
			}
			query += " ORDER BY jobcode.`Job_Name` asc" ;
			
			List tempList =  session.createSQLQuery(query).list();					
			for(Object o : tempList){				
				Object[] obj =  (Object[]) o;				
				jobnames.add(new SimpleObject((Integer)obj[0], (String)obj[1]));
			}
		} catch (Exception e) {
			log.error("Error Occured ",e);
			log.info("Exiting from  getJobCodeListBySkillType()  ::   "+jobnames);
		}
		log.info("Exiting from  getJobCodeListBySkillType()  ::   "+jobnames);
		return jobnames;
	}

	@Override
	public List<SimpleObject> getAssignedWorkersCountBySkillType(ManpowerRequisitionVo paramManpower) {
		log.info("Entered in  getAssignedWorkersCountBySkillType()  ::   paramManpower = "+paramManpower);
		Session session = sessionFactory.getCurrentSession();
		
		List<SimpleObject> assignedList = new ArrayList<SimpleObject>(); 
		/*try {
			
			String  skillsQuery = " SELECT  SUM(day.Required_Workers), Job_Skill "
					+ " FROM manpower_requisition_daywise AS day"
					+ " WHERE 1 = 1 ";
			
			if(paramManpower.getCustomerId() != null && paramManpower.getCustomerId() > 0){
				skillsQuery  += " AND  day.Customer_Id = "+paramManpower.getCustomerId();
			}
			if(paramManpower.getCompanyId() != null && paramManpower.getCompanyId() > 0){
				skillsQuery  += " AND  day.Company_Id = "+paramManpower.getCompanyId();
			}
			if(paramManpower.getLocationId() != null && paramManpower.getLocationId() > 0){
				skillsQuery  += " AND  day.Location_Id = "+paramManpower.getLocationId();
			}
			if(paramManpower.getPlantId() != null && paramManpower.getPlantId() > 0){
				skillsQuery  += " AND  day.Plant_Id = "+paramManpower.getPlantId();
			}
			if(paramManpower.getDepartmentId() != null && paramManpower.getDepartmentId() > 0){
				skillsQuery  += " AND  day.Department_Id = "+paramManpower.getDepartmentId();
			}
			
			if(paramManpower.getSkillType() != null && paramManpower.getSkillType() != "" && !paramManpower.getSkillType().isEmpty()){
				skillsQuery  += " AND  day.Skill_Type = '"+paramManpower.getSkillType()+"' ";
			}
			if(paramManpower.getFrequency() != null && paramManpower.getFrequency() != "" && !paramManpower.getFrequency().isEmpty()){
				if(paramManpower.getFrequency().equalsIgnoreCase("Annual")){
					//skillsQuery  += " AND  manpower.Frequency IN( '"+paramManpower.getFrequency()+"' ";
				}else{
					skillsQuery  += " AND  manpower.Frequency = '"+paramManpower.getFrequency()+"' ";
				}
			}
			
			if(paramManpower.getYear() != null && paramManpower.getYear() > 0 && (paramManpower.getMonth() == null || paramManpower.getMonth() == "" || paramManpower.getMonth().isEmpty())){
				skillsQuery  += " AND  manpower.Year = "+paramManpower.getYear();
			}
			
			if(paramManpower.getYear() != null && paramManpower.getYear() > 0 && paramManpower.getMonth() != null && paramManpower.getMonth() != "" && paramManpower.getMonth().isEmpty()){
				skillsQuery  += "AND  manpower.Year = "+paramManpower.getYear()+" AND  manpower.Month = '"+paramManpower.getMonth()+"' ";
			}
			
			skillsQuery  += " AND ( manpower.Required_For <> 'Reduction' OR  (manpower.Required_For = 'Reduction' AND manpower.Effective_Date > 'CURRENT_DATE()' )) ";
			
			//skillsQuery +=
			//skillsQuery += " ORDER BY jobcode.`Job_Name` asc" ;
			
			List tempList =  session.createSQLQuery(skillsQuery).list();					
			for(Object o : tempList){				
				Object[] obj =  (Object[]) o;	
				SimpleObject object = new SimpleObject();
				if(obj != null && obj[0] != null){
					object.setId((Integer)((BigDecimal)obj[0]).intValue());
				}else{
					object.setId((Integer)0);
				}
				assignedList.add(object);
			}
		} catch (Exception e) {
			log.error("Error Occured ",e);
			log.info("Exiting from  getAssignedWorkersCountBySkillType()  ::   "+assignedList);
		}
		log.info("Exiting from  getAssignedWorkersCountBySkillType()  ::   "+assignedList);*/
		return assignedList;
	}

}
