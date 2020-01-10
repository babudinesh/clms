package com.Ntranga.CLMS.Dao;

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

import com.Ntranga.CLMS.vo.RolesVo;
import com.Ntranga.core.DateHelper;
import com.Ntranga.core.CLMS.entities.PermissionsGroup;
import com.Ntranga.core.CLMS.entities.RolePermissiongroupMapping;
import com.Ntranga.core.CLMS.entities.Roles;

import common.Logger;

@Transactional
@Repository("rolesDao")
public class RolesDaoImpl implements RolesDao  {

	private static Logger log = Logger.getLogger(RolesDaoImpl.class);
	
	
	@Autowired
	private SessionFactory sessionFactory;


	@Override
	public List<RolesVo> getRoles(RolesVo rolesVo) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<RolesVo> returnList = new ArrayList<RolesVo>();
		try{
				String query = " SELECT role_id,role_name,role_desc,created_date,modified_date FROM roles where 1>0 ";
				if(rolesVo!= null && rolesVo.getRoleId() != null && rolesVo.getRoleId() >0 ){
					query += "  and role_id="+rolesVo.getRoleId();
				}
				if(rolesVo!= null && rolesVo.getCustomerId() != null && rolesVo.getCustomerId() >0 ){
					query += "  and customer_id="+rolesVo.getCustomerId();
				}
				if(rolesVo!= null && rolesVo.getCompanyId() != null && rolesVo.getCompanyId() >0 ){
					query += "  and company_id="+rolesVo.getCompanyId();
				}
				query += " ORDER BY role_name";
				List tempList = session.createSQLQuery(query).list();
				for(Object object : tempList){
					Object[] obj = (Object[]) object;
					RolesVo rolesVo2 = new RolesVo();
					rolesVo2.setRoleId((Integer) obj[0]);
					rolesVo2.setRoleName(obj[1]+"");
					rolesVo2.setRoleDesc(obj[2]+"");
					rolesVo2.setCreatedDate(DateHelper.convertSQLDateToString( (java.sql.Date)obj[3], "dd/MM/yyyy" ));
					rolesVo2.setModifiedDate(DateHelper.convertSQLDateToString( (java.sql.Date)obj[4], "dd/MM/yyyy" ));
					returnList.add( rolesVo2);
				}
			}catch(Exception e){
				log.error("Error Occured RolesDaoImpl --> getRoles method ",e);
			}	
		return returnList;
	}


	@Override
	public Integer saveOrUpdateRoleDetails(RolesVo rolesVo) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Integer id = 0;
		try{
			Criteria criteria = session.createCriteria(Roles.class);
			criteria.add(Restrictions.eq("roleName", rolesVo.getRoleName()));
			
			if(rolesVo != null && rolesVo.getRoleId() != null && rolesVo.getRoleId() > 0)
				criteria.add(Restrictions.ne("roleId", rolesVo.getRoleId()));
			if(rolesVo != null && rolesVo.getCustomerId() != null && rolesVo.getCustomerId() > 0)
				criteria.add(Restrictions.ne("customerId", rolesVo.getCustomerId()));
			if(rolesVo != null && rolesVo.getCompanyId() != null && rolesVo.getCompanyId() > 0)
				criteria.add(Restrictions.ne("companyId", rolesVo.getCompanyId()));
			
			List duplicateList = criteria.list();
			if(duplicateList.size() > 0){
				return -1;
			}else{
				Roles role = new Roles();
				if(rolesVo != null && rolesVo.getRoleId() != null && rolesVo.getRoleId() > 0){
					role = (Roles) session.load(Roles.class, rolesVo.getRoleId());
				}else{
					role.setCreatedBy(rolesVo.getCreatedBy());
					role.setCreatedDate(new Date());
				}
				role.setModifiedBy(rolesVo.getModifiedBy());
				role.setModifiedDate(new Date());
				role.setCustomerId(rolesVo.getCustomerId());
				role.setCompanyId(rolesVo.getCompanyId());
				role.setRoleName(rolesVo.getRoleName());
				role.setRoleDesc(rolesVo.getRoleDesc());
				role.setIsActive("Y");
				
				
				id = (Integer) session.save(role);
				
				try{
					if(rolesVo != null && rolesVo.getRoleId() != null && rolesVo.getRoleId() > 0)
					session.createSQLQuery(" Update role_permissiongroup_mapping set is_active = 'N' where role_id="+id).executeUpdate();
					for(String  groupId : rolesVo.getPermissionsGroupArray()){
						RolePermissiongroupMapping mapping = new RolePermissiongroupMapping();
						mapping.setRoles(role);
						mapping.setPermissionsGroupId(Integer.valueOf(groupId));
						mapping.setIsActive("Y");
						session.merge(mapping);					
					}
				}catch(Exception e){
					e.printStackTrace();
					return -2;
				}
			}
			session.flush();
		}catch(Exception e){
			log.error("Error Occured RolesDaoImpl --> saveOrUpdateRoleDetails method ",e);
		}	
		return id;
	}


	@Override
	public RolesVo getRolesByrolesId(Integer roleId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		RolesVo  rolesVo = new RolesVo();
		try{
			String query = " SELECT r.`role_id`, `role_name`, `role_desc`, `customer_id`, `company_id`,GROUP_CONCAT(`permissions_group_id`) FROM `roles` r INNER JOIN `role_permissiongroup_mapping` rpm ON (r.role_id = rpm.role_id) where rpm.is_active='Y' and r.role_id="+roleId;
			
			List tempList  = session.createSQLQuery(query).list();
			for(Object object : tempList){
				Object[] obj = (Object[]) object;
				rolesVo.setRoleId( (Integer)obj[0]);
				rolesVo.setRoleName(obj[1]+"");
				rolesVo.setRoleDesc(obj[2]+"");
				rolesVo.setCustomerId( (Integer)obj[3]);
				rolesVo.setCompanyId( (Integer) obj[4] );
				if(obj[5] != null &&  (obj[5]+"").contains(","))
					rolesVo.setPermissionsGroupArray((obj[5]+"").split(",") );
				else if(obj[5] != null)
					rolesVo.setPermissionsGroupArray( new String[]{obj[5]+""});
			}
		}catch(Exception e){
			log.error("Error Occured PermissionsGroupDaoImpl --> getScreenActionsList method ",e);
		}
		return rolesVo;
	}



	
}
