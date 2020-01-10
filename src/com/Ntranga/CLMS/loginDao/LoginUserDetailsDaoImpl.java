package com.Ntranga.CLMS.loginDao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.Ntranga.core.PasswordGenerator;
import com.Ntranga.core.UserDetails;
import com.Ntranga.core.CLMS.entities.MUsers;
import com.Ntranga.core.CLMS.entities.UserLoginInfo;

@Repository
public class LoginUserDetailsDaoImpl implements LoginUserDetailsDao {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public MUsers findByUserName(String username) {

		List<MUsers> users = new ArrayList<MUsers>();

		users = sessionFactory.getCurrentSession()
			.createQuery("from MUsers where userName=?")
			.setParameter(0, username)
			.list();

		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public UserDetails getUserDetails(String username) {
		List<MUsers> users = new ArrayList<MUsers>();

		users = sessionFactory.getCurrentSession()
			.createQuery("from MUsers where userName=?")
			.setParameter(0, username)
			.list();

		
		UserDetails userDetails = new UserDetails();
		MUsers mUsers = users.get(0);
		
		userDetails.setUserId(mUsers.getUserId());
		userDetails.setUserName(mUsers.getUserName());
		userDetails.setRoleId(mUsers.getMRoles().getRoleId());
		userDetails.setRoleName(mUsers.getMRoles().getRoleName());
		
		return userDetails;
	}
	
	
	public UserDetails validateUser(String username,String password) {
		List<MUsers> users = new ArrayList<MUsers>();
		//sessionFactory.getCurrentSession().createSQLQuery("SET @@group_concat_max_len = 4096").executeUpdate();
		List tempUserList = sessionFactory.getCurrentSession()
				.createSQLQuery("SELECT u.user_id,u.user_name,u.customer_id,u.company_id,u.employee_unique_id, GROUP_CONCAT( DISTINCT sl.screen_name) AS screenArray,GROUP_CONCAT( DISTINCT r.role_name) AS roleNamesArray,GROUP_CONCAT(DISTINCT r.role_id) AS roleIdArray, GROUP_CONCAT( DISTINCT sl.screen_id) AS screenIdsArray, GROUP_CONCAT( DISTINCT sl.group_name) AS groupNameArray,u.vendor_id,GROUP_CONCAT(DISTINCT CASE `type` WHEN 'Location' THEN type_id ELSE NULL END ORDER BY type_id) AS Location,GROUP_CONCAT(DISTINCT CASE `type` WHEN 'Plant' THEN type_id ELSE NULL END ORDER BY type_id) AS Plant,GROUP_CONCAT(DISTINCT CASE `type` WHEN 'Department' THEN type_id ELSE NULL END ORDER BY type_id) AS Department,GROUP_CONCAT(DISTINCT CASE `type` WHEN 'Section' THEN type_id ELSE NULL END ORDER BY type_id)AS Section,GROUP_CONCAT(DISTINCT CASE `type` WHEN 'WorkArea' THEN type_id ELSE NULL END ORDER BY type_id) AS WorkArea FROM `user_role_mapping` urm INNER JOIN users u ON (urm.user_id = u.user_id AND urm.is_active = 'Y') "
								+" INNER JOIN `roles` r ON (r.role_id = urm.role_id) "
								+" INNER JOIN `role_permissiongroup_mapping` rpm ON(urm.`role_id` = rpm.`role_id` AND urm.`is_active` = rpm.`is_active` AND rpm.`is_active` = 'Y') "
								+" LEFT JOIN `screen_permissions_group_mapping` spgm ON(spgm.`permissions_group_id` = rpm.`permissions_group_id` AND spgm.`is_active` = rpm.`is_active` AND spgm.`is_active` = 'Y') "
								+" LEFT JOIN `screen_actions_mapping`  sam ON(sam.`screen_action_mapping_id` = spgm.`screen_action_mapping_id` AND sam.`is_active` = spgm.`is_active` AND sam.`is_active` = 'Y') "
								+" LEFT JOIN `user_location_mapping` ulm ON (u.user_id = ulm.user_id) "
								+" LEFT JOIN `screens_list` sl ON (sl.`screen_id` = sam.`screen_id`) WHERE u.user_name='"+username+"' and binary u.password='"+PasswordGenerator.md5Encryption(password)+"'").list();
			
			UserDetails userDetails = new UserDetails();
			if(tempUserList.size() >0){
				Object[] userObj = (Object[]) tempUserList.get(0);	
				if(userObj[0] == null){
					userDetails.setUserId(0);
					return userDetails;
				}
					
				userDetails.setUserId((Integer)userObj[0]);
				userDetails.setUserName((String)userObj[1]);
				if (userObj[2] != null)
					userDetails.setCustomerId((Integer) userObj[2]);
				if (userObj[3] != null)
					userDetails.setCompanyId((Integer) userObj[3]);
				userDetails.setEmployeeUniqueId((Integer) userObj[4]);
				
				if(userObj[5] != null && (userObj[5]+"").contains(","))
					userDetails.setScreenNamesArray((userObj[5]+"").split(","));
				else if(userObj[5] != null)
					userDetails.setScreenNamesArray( new String[]{userObj[5]+""});
				
				if(userObj[8] != null && (userObj[8]+"").contains(","))
					userDetails.setScreenIdsArray((userObj[8]+"").split(","));
				else if(userObj[8] != null)
					userDetails.setScreenIdsArray( new String[]{userObj[8]+""});
				
				if(userObj[6] != null && (userObj[6]+"").contains(","))
					userDetails.setRoleNamesArray((userObj[6]+"").split(","));
				else if(userObj[6] != null)
					userDetails.setRoleNamesArray( new String[]{userObj[6]+""});
				
				if(userObj[7] != null && (userObj[7]+"").contains(","))
					userDetails.setRoleIdsArray((userObj[7]+"").split(","));
				else if(userObj[7] != null)
					userDetails.setRoleIdsArray( new String[]{userObj[7]+""});
				
				if(userObj[9] != null && (userObj[9]+"").contains(","))
					userDetails.setGroupNamesArray((userObj[9]+"").split(","));
				else if(userObj[9] != null)
					userDetails.setGroupNamesArray( new String[]{userObj[9]+""});
				if(userObj[10] != null)
					userDetails.setVendorId((Integer) userObj[10]);
				
				if(userObj[11] != null && (userObj[11]+"").contains(","))
					userDetails.setLocationArrayId((userObj[11]+"").split(","));
				else if(userObj[11] != null)
					userDetails.setLocationArrayId( new String[]{userObj[11]+""});
				
				if(userObj[12] != null && (userObj[12]+"").contains(","))
					userDetails.setPlantArrayId((userObj[12]+"").split(","));
				else if(userObj[12] != null)
					userDetails.setPlantArrayId( new String[]{userObj[12]+""});
				
				
				if(userObj[13] != null && (userObj[13]+"").contains(","))
					userDetails.setDepartmentArrayId((userObj[13]+"").split(","));
				else if(userObj[13] != null)
					userDetails.setDepartmentArrayId( new String[]{userObj[13]+""});
				
				if(userObj[14] != null && (userObj[14]+"").contains(","))
					userDetails.setSectionArrayId((userObj[14]+"").split(","));
				else if(userObj[14] != null)
					userDetails.setSectionArrayId( new String[]{userObj[14]+""});
				
				
				if(userObj[15] != null && (userObj[15]+"").contains(","))
					userDetails.setWorkAreaArrayId((userObj[15]+"").split(","));
				else if(userObj[15] != null)
					userDetails.setWorkAreaArrayId( new String[]{userObj[15]+""});
				
				UserLoginInfo info = new UserLoginInfo();
				info.setUserId((Integer)userObj[0]);
				info.setLoggedInTime( new Date() );
				sessionFactory.getCurrentSession().save(info);
				sessionFactory.getCurrentSession().flush();
				
			}else{
				userDetails.setUserId(0);
			}
			return userDetails;
		}

	@Override
	public String getSchemaNameByUserId(String userId) {
		String schema= null;
		Session session = sessionFactory.getCurrentSession();
		List<String> list = session.createSQLQuery("Select schema_name from m_user_schema where user_id = "+userId).list();
		if(list.size() > 0)
			schema = list.get(0).toString();
		return schema;
	}
	
	
}
