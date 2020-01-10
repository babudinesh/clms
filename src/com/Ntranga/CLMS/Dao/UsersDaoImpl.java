package com.Ntranga.CLMS.Dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Ntranga.CLMS.vo.EmployeeInformationVo;
import com.Ntranga.CLMS.vo.UsersVo;
import com.Ntranga.core.DateHelper;
import com.Ntranga.core.PasswordGenerator;
import com.Ntranga.core.CLMS.entities.UserLocationMapping;
import com.Ntranga.core.CLMS.entities.UserRoleMapping;
import com.Ntranga.core.CLMS.entities.Users;
import com.Ntranga.core.configuration.EmailNotification;

import common.Logger;

@Transactional
@Repository("usersDao")
public class UsersDaoImpl implements UsersDao  {

	private static Logger log = Logger.getLogger(UsersDaoImpl.class);
	
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private EmailNotification emailNotification;

	@Override
	public List<UsersVo> getUsers(UsersVo usersVo) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<UsersVo> returnList = new ArrayList<UsersVo>();
		try{
				String query = " SELECT user_id,user_name,u.created_date,u.modified_date,COALESCE(CONCAT(ei.first_name,' ',ei.last_name),vdi.vendor_name) AS NAME, user_type " // ,u.location_id,u.plant_id,u.department_id,u.section_id,u.work_area_id "
						+ " FROM `users`u LEFT JOIN vendor_details vd  ON (vd.vendor_id = u.vendor_id)  LEFT JOIN vendor_details_info vdi ON (vd.vendor_id = vdi.vendor_id AND CONCAT(DATE_FORMAT(vdi.transaction_date, '%Y%m%d'), LPAD(vdi.Sequence_Id,2,'0')) = (SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date,'%Y%m%d'),LPAD(vdi1.Sequence_Id,2,'0'))) FROM `vendor_details_info` vdi1 WHERE vdi.`vendor_id` = vdi1.`vendor_id` AND vdi1.transaction_date <= CURRENT_DATE())) LEFT JOIN employee_information ei ON (u.employee_unique_id = ei.Unique_Id AND CONCAT(DATE_FORMAT(ei.transaction_date, '%Y%m%d'), LPAD(ei.Sequence_Id,2,'0')) = (SELECT MAX(CONCAT(DATE_FORMAT(ei1.transaction_date,'%Y%m%d'),LPAD(ei1.Sequence_Id,2,'0'))) FROM `employee_information` ei1 WHERE ei.`Unique_Id` = ei1.`Unique_Id` AND ei1.transaction_date <= CURRENT_DATE() )) where 1>0  ";
				if(usersVo!= null && usersVo.getUserId() != null && usersVo.getUserId() >0 ){
					query += "  and user_id="+usersVo.getUserId();
				}
				if(usersVo!= null && usersVo.getCustomerId() != null && usersVo.getCustomerId() >0 ){
					query += "  and u.customer_id="+usersVo.getCustomerId();
				}
				if(usersVo!= null && usersVo.getCompanyId() != null && usersVo.getCompanyId() >0 ){
					query += "  and u.company_id="+usersVo.getCompanyId();
				}
				if(usersVo!= null && usersVo.getVendorId() != null && usersVo.getVendorId() >0){
					query += "  and u.vendor_Id > 0 ";
				}
				
				query += "  ORDER BY user_name";
				List tempList = session.createSQLQuery(query).list();
				for(Object object : tempList){
					Object[] obj = (Object[]) object;
					UsersVo usersVo2 = new UsersVo();
					usersVo2.setUserId((Integer) obj[0]);
					usersVo2.setUserName(obj[1]+"");
					usersVo2.setCreatedDate(DateHelper.convertSQLDateToString( (java.sql.Date)obj[2], "dd/MM/yyyy" ));
					usersVo2.setModifiedDate(DateHelper.convertSQLDateToString( (java.sql.Date)obj[3], "dd/MM/yyyy" ));
					usersVo2.setEmployeeName(obj[4]+"");
					usersVo2.setUserType(obj[5]+"");
					
					/*if(obj[6] != null && (obj[6]+"").contains(","))
						usersVo2.setLocationArrayId((obj[6]+"").split(","));
					else if(obj[6] != null)
						usersVo2.setLocationArrayId( new String[]{obj[6]+""});
					
					if(obj[7] != null && (obj[7]+"").contains(","))
						usersVo2.setPlantArrayId((obj[7]+"").split(","));
					else if(obj[7] != null)
						usersVo2.setPlantArrayId( new String[]{obj[7]+""});
					
					
					if(obj[8] != null && (obj[8]+"").contains(","))
						usersVo2.setDepartmentArrayId((obj[8]+"").split(","));
					else if(obj[8] != null)
						usersVo2.setDepartmentArrayId( new String[]{obj[8]+""});
					
					if(obj[9] != null && (obj[9]+"").contains(","))
						usersVo2.setSectionArrayId((obj[9]+"").split(","));
					else if(obj[9] != null)
						usersVo2.setSectionArrayId( new String[]{obj[9]+""});
					
					
					if(obj[10] != null && (obj[10]+"").contains(","))
						usersVo2.setWorkAreaArrayId((obj[10]+"").split(","));
					else if(obj[10] != null)
						usersVo2.setWorkAreaArrayId( new String[]{obj[10]+""});*/
					returnList.add( usersVo2);
				}
			}catch(Exception e){
				log.error("Error Occured UsersDaoImpl --> getUsers method ",e);
			}	
		return returnList;
	}


	@Override
	public Integer saveOrUpdateUserDetails(UsersVo usersVo) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Integer id = 0;
		try{
			Criteria criteria = session.createCriteria(Users.class);
			criteria.add(Restrictions.eq("userName", usersVo.getUserName()));
			
			if(usersVo != null && usersVo.getUserId() != null && usersVo.getUserId() > 0)
				criteria.add(Restrictions.ne("userId", usersVo.getUserId()));
			if(usersVo != null && usersVo.getCustomerId() != null && usersVo.getCustomerId() > 0)
				criteria.add(Restrictions.ne("customerId", usersVo.getCustomerId()));
			if(usersVo != null && usersVo.getCompanyId() != null && usersVo.getCompanyId() > 0)
				criteria.add(Restrictions.ne("companyId", usersVo.getCompanyId()));
			
			List duplicateList = criteria.list();
			if(duplicateList.size() > 0){
				return -1;
			}else{
				Users user = new Users();
				if(usersVo != null && usersVo.getUserId() != null && usersVo.getUserId() > 0){
					user = (Users) session.load(Users.class, usersVo.getUserId());
				}else{
					user.setCreatedBy(usersVo.getCreatedBy());
					user.setCreatedDate(new Date());
				}
				if(usersVo.isPasswordChanged())
					user.setPassword(PasswordGenerator.md5Encryption(usersVo.getPassword()));
				
				user.setModifiedBy(usersVo.getModifiedBy());
				user.setModifiedDate(new Date());
				user.setCustomerId(usersVo.getCustomerId());
				user.setCompanyId(usersVo.getCompanyId());
				user.setEmployeeUniqueId(usersVo.getEmployeeUniqueId());
				user.setVendorId(usersVo.getVendorId());
				user.setUserType(usersVo.getUserType());
				user.setUserName(usersVo.getUserName());
				// user.setLocationId(Arrays.toString(usersVo.getLocationArrayId()).replace("[", "").replace("]", ""));
				// user.setPlantId(Arrays.toString(usersVo.getPlantArrayId()).replace("[", "").replace("]", ""));
				// user.setDepartmentId(Arrays.toString(usersVo.getDepartmentArrayId()).replace("[", "").replace("]", ""));
				// user.setSectionId(Arrays.toString(usersVo.getSectionArrayId()).replace("[", "").replace("]", ""));
				// user.setWorkAreaId(Arrays.toString(usersVo.getWorkAreaArrayId()).replace("[", "").replace("]", ""));
				user.setIsActive("Y");
				
				
				id = (Integer) session.save(user);
				
				session.createSQLQuery("delete from user_location_mapping where user_id="+id).executeUpdate();
				this.mergeDataToSession(usersVo.getLocationArrayId(),session,id,"Location");
				this.mergeDataToSession(usersVo.getPlantArrayId(),session,id,"Plant");
				this.mergeDataToSession(usersVo.getDepartmentArrayId(),session,id,"Department");
				this.mergeDataToSession(usersVo.getSectionArrayId(),session,id,"Section");
				this.mergeDataToSession(usersVo.getWorkAreaArrayId(),session,id,"WorkArea");
				
				
				try{
					if(usersVo != null && usersVo.getUserId() != null && usersVo.getUserId() > 0)
						session.createSQLQuery(" Update user_role_mapping set is_active = 'N' where user_id="+id).executeUpdate();
					for(String  roleId : usersVo.getUserRoles()){
						UserRoleMapping mapping = new UserRoleMapping();
						mapping.setUsers(user);
						mapping.setRoleId(Integer.valueOf(roleId));
						mapping.setIsActive("Y");
						session.merge(mapping);					
					}
				}catch(Exception e){
					e.printStackTrace();
					return -2;
				}
				List tempList = new ArrayList();
				if(usersVo != null && usersVo.getUserType() != null && usersVo.getUserType().equalsIgnoreCase("Employee") ){
					tempList = session.createSQLQuery("SELECT email, first_name, last_name FROM employee_information ei WHERE CONCAT(DATE_FORMAT(ei.transaction_date, '%Y%m%d'), ei.Sequence_Id) = ( SELECT MAX(CONCAT(DATE_FORMAT(ei1.transaction_date, '%Y%m%d'), ei1.Sequence_Id)) FROM employee_information ei1 WHERE ei.Unique_Id = ei1.Unique_Id AND ei1.transaction_date <= CURRENT_DATE() ) AND ei.Unique_Id="+usersVo.getEmployeeUniqueId()).list();
				}else{
					tempList = session.createSQLQuery("SELECT email,vendor_name,pan_number FROM vendor_details vd INNER JOIN vendor_details_info vdi ON (vd.vendor_id = vdi.vendor_id AND CONCAT(DATE_FORMAT(vdi.transaction_date, '%Y%m%d'), LPAD(vdi.Sequence_Id,2,'0')) = (SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date,'%Y%m%d'),LPAD(vdi1.Sequence_Id,2,'0'))) FROM `vendor_details_info` vdi1 WHERE vdi.`vendor_id` = vdi1.`vendor_id` AND vdi1.transaction_date <= CURRENT_DATE())) where vd.vendor_id="+usersVo.getVendorId()).list();
				}
				log.info(usersVo.getUserId() +" :: userId:: "+usersVo.getUserType()+ " :: User Type  list size::"+tempList.size());
				if(tempList.size() >0){
					Object object = tempList.get(0);
					Object[] obj = (Object[]) object;
					log.info(obj[0]+"  :: email");
					if(usersVo != null && usersVo.getUserId() != null && usersVo.getUserId() > 0 && obj[0] != null){
						log.info("if email sending to "+obj[0]);
						emailNotification.sendMail(obj[0]+"", " Login Details Mdified ", " Hi"+obj[1]+" "+obj[2]+", You have just Modified Login with following credentials,  Username:"+usersVo.getUserName()+" Thanks, CLMS Support.");
						log.info("if email sent to "+obj[0]);
					}else if(obj[0] != null){
						log.info("else email sending to "+obj[0]);
						emailNotification.sendMail(obj[0]+"", " Login Created ", " Hi"+obj[1]+" "+obj[2]+", You have just created Login with following credentials,  Username:"+usersVo.getUserName()+"  Thanks, CLMS Support.");
						log.info("else email sent to "+obj[0]);
					}
				}
			}
			session.flush();
		}catch(Exception e){
			log.error("Error Occured UsersDaoImpl --> saveOrUpdateUserDetails method ",e);
		}	
		return id;
	}

	public void mergeDataToSession(String[] arrayData,Session session,Integer id,String type){
		for(String location : arrayData){
			UserLocationMapping userLocationMapping = new UserLocationMapping();
			userLocationMapping.setType(type);
			userLocationMapping.setUsers(new Users(id));
			userLocationMapping.setTypeId(Integer.valueOf(location));
			session.merge(userLocationMapping);
		}
	}
	
	@Override
	public UsersVo getUsersByUserId(Integer userId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		UsersVo  usersVo = new UsersVo();
		try{
			String query = "SELECT u.user_id, user_name, u.`customer_id`, u.`company_id`, GROUP_CONCAT(DISTINCT `role_id`), u.is_active, PASSWORD, employee_unique_id, CONCAT(ei.first_name,' ',ei.last_name) AS ename, u.vendor_id, u.user_type, CONCAT(vdi.vendor_name,' (',vd.vendor_code,')') AS vname,GROUP_CONCAT(CASE `type` WHEN 'Location' THEN type_id ELSE NULL END ORDER BY type_id) AS Location,GROUP_CONCAT(CASE `type` WHEN 'Plant' THEN type_id ELSE NULL END ORDER BY type_id) AS Plant,GROUP_CONCAT(CASE `type` WHEN 'Department' THEN type_id ELSE NULL END ORDER BY type_id) AS Department,GROUP_CONCAT(CASE `type` WHEN 'Section' THEN type_id ELSE NULL END ORDER BY type_id)AS Section,GROUP_CONCAT(CASE `type` WHEN 'WorkArea' THEN type_id ELSE NULL END ORDER BY type_id) AS WorkArea FROM `users`u LEFT JOIN vendor_details vd  ON (vd.vendor_id = u.vendor_id)  LEFT JOIN vendor_details_info vdi ON (vd.vendor_id = vdi.vendor_id AND CONCAT(DATE_FORMAT(vdi.transaction_date, '%Y%m%d'), LPAD(vdi.Sequence_Id,2,'0')) = (SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date,'%Y%m%d'),LPAD(vdi1.Sequence_Id,2,'0'))) FROM `vendor_details_info` vdi1 WHERE vdi.`vendor_id` = vdi1.`vendor_id` AND vdi1.transaction_date <= CURRENT_DATE())) LEFT JOIN employee_information ei ON (u.employee_unique_id = ei.Unique_Id AND CONCAT(DATE_FORMAT(ei.transaction_date, '%Y%m%d'), LPAD(ei.Sequence_Id,2,'0')) = (SELECT MAX(CONCAT(DATE_FORMAT(ei1.transaction_date,'%Y%m%d'),LPAD(ei1.Sequence_Id,2,'0'))) FROM `employee_information` ei1 WHERE ei.`Unique_Id` = ei1.`Unique_Id` AND ei1.transaction_date <= CURRENT_DATE() )) LEFT JOIN `user_location_mapping` ulm ON (u.user_id = ulm.user_id)  INNER JOIN `user_role_mapping` urm ON (u.user_id = urm.user_id) WHERE urm.is_active='Y' and u.user_id="+userId;
			
			List tempList  = session.createSQLQuery(query).list();
			for(Object object : tempList){
				Object[] obj = (Object[]) object;
				usersVo.setUserId( (Integer)obj[0]);
				usersVo.setUserName(obj[1]+"");
				usersVo.setCustomerId( (Integer)obj[2]);
				usersVo.setCompanyId( (Integer) obj[3] );
				if(obj[4] != null &&  (obj[4]+"").contains(","))
					usersVo.setUserRoles((obj[4]+"").split(",") );
				else if(obj[4] != null)
					usersVo.setUserRoles( new String[]{obj[4]+""});
				usersVo.setIsActive(obj[5]+"");
				usersVo.setPassword(obj[6]+"");
				usersVo.setConfirmPassword(obj[6]+"");
				if(obj[7] != null)
					usersVo.setEmployeeUniqueId((Integer)obj[7]);
				usersVo.setEmployeeName(obj[8]+"");
				usersVo.setVendorId( (Integer)obj[9]);
				usersVo.setUserType(obj[10]+"");
				usersVo.setVendorName(obj[11]+"");
				
				
				if(obj[12] != null && (obj[12]+"").contains(","))
					usersVo.setLocationArrayId((obj[12]+"").split(","));
				else if(obj[12] != null)
					usersVo.setLocationArrayId( new String[]{obj[12]+""});
				
				if(obj[13] != null && (obj[13]+"").contains(","))
					usersVo.setPlantArrayId((obj[13]+"").split(","));
				else if(obj[13] != null)
					usersVo.setPlantArrayId( new String[]{obj[13]+""});
				
				
				if(obj[14] != null && (obj[14]+"").contains(","))
					usersVo.setDepartmentArrayId((obj[14]+"").split(","));
				else if(obj[14] != null)
					usersVo.setDepartmentArrayId( new String[]{obj[14]+""});
				
				if(obj[15] != null && (obj[15]+"").contains(","))
					usersVo.setSectionArrayId((obj[15]+"").split(","));
				else if(obj[15] != null)
					usersVo.setSectionArrayId( new String[]{obj[15]+""});
				
				
				if(obj[16] != null && (obj[16]+"").contains(","))
					usersVo.setWorkAreaArrayId((obj[16]+"").split(","));
				else if(obj[16] != null)
					usersVo.setWorkAreaArrayId( new String[]{obj[16]+""});
			}
		}catch(Exception e){
			log.error("Error Occured UsersDaoImpl --> getUsersByUserId method ",e);
		}
		return usersVo;
	}


	@Override
	public List<EmployeeInformationVo> getEmployeeDetails(EmployeeInformationVo employeeInformationVo) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<EmployeeInformationVo> SobjempList = new ArrayList<>();
		try {
			String  q = " SELECT distinct ei.`unique_id`,`Employee_Code`,CONCAT(`First_Name`,' ',last_name) as FirstName, COALESCE(ldi.`Location_Name`,'') AS loc ,CASE ei.is_active WHEN 'Y' THEN 'Active' ELSE 'In-Active' END AS STATUS,"
					+ " jdi.Job_Title, ddi.Department_Name"
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
				
					if(employeeInformationVo != null && employeeInformationVo.getCustomerId() != null && employeeInformationVo.getCustomerId() > 0){
						 q= q+" and ei.customer_id ='"+employeeInformationVo.getCustomerId()+"'";
					 }
					if(employeeInformationVo != null && employeeInformationVo.getCompanyId() != null && employeeInformationVo.getCompanyId() > 0){
						 q= q+" and ei.company_id ='"+employeeInformationVo.getCompanyId()+"'";
					 }
					 if(employeeInformationVo != null && employeeInformationVo.getEmployeeCode() != null && !employeeInformationVo.getEmployeeCode().isEmpty()){
						 q= q+" and ei.Employee_Code Like'%"+employeeInformationVo.getEmployeeCode()+"%'";
					 }
					 if(employeeInformationVo != null && employeeInformationVo.getFirstName() != null && !employeeInformationVo.getFirstName().isEmpty()){
						 q= q+" and ei.first_name Like'%"+employeeInformationVo.getFirstName()+"%'";
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
	



	
}
