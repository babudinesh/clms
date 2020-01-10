package com.Ntranga.CLMS.Dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Ntranga.CLMS.vo.PermissionsGroupVo;
import com.Ntranga.CLMS.vo.ScreenActionsVo;
import com.Ntranga.core.DateHelper;
import com.Ntranga.core.CLMS.entities.PermissionsGroup;
import com.Ntranga.core.CLMS.entities.ScreenActionsMapping;
import com.Ntranga.core.CLMS.entities.ScreenPermissionsGroupMapping;

import common.Logger;

@Transactional
@Repository("permissionsGroupDao")
public class PermissionsGroupDaoImpl implements PermissionsGroupDao  {

	private static Logger log = Logger.getLogger(PermissionsGroupDaoImpl.class);
	
	
	@Autowired
	private SessionFactory sessionFactory;


	@Override
	public List<PermissionsGroupVo> getPermissionsGroupName(PermissionsGroupVo groupVo) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<PermissionsGroupVo> returnList = new ArrayList<PermissionsGroupVo>();
		try{
				String query = " SELECT permissions_group_id,permissions_group_name,permissions_group_desc,created_date,modified_date FROM permissions_group ";
				if(groupVo!= null && groupVo.getPermissionsGroupId() != null && groupVo.getPermissionsGroupId() >0 ){
					query += "  where permissions_group_id="+groupVo.getPermissionsGroupId();
				}
				query += " ORDER BY permissions_group_name ";
				List tempList = session.createSQLQuery(query).list();
				for(Object object : tempList){
					Object[] obj = (Object[]) object;
					PermissionsGroupVo groupVo2 = new PermissionsGroupVo();
					groupVo2.setPermissionsGroupId((Integer) obj[0]);
					groupVo2.setPermissionsGroupName(obj[1]+"");
					groupVo2.setPermissionsGroupDesc(obj[2]+"");
					groupVo2.setCreatedDate(DateHelper.convertSQLDateToString( (java.sql.Date)obj[3], "dd/MM/yyyy" ));
					groupVo2.setModifiedDate(DateHelper.convertSQLDateToString( (java.sql.Date)obj[4], "dd/MM/yyyy" ));
					returnList.add( groupVo2);
				}
			}catch(Exception e){
				log.error("Error Occured PermissionsGroupDaoImpl --> getPermissionsGroupName method ",e);
			}	
		return returnList;
	}


	@Override
	public String[] getScreenActionsList() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		String[] actionList = new String[100];
		try{
			List tempList  = session.createSQLQuery(" SELECT  GROUP_CONCAT(action_name) FROM  `m_screen_actions` ").list();
			if(tempList != null && tempList.size() > 0){
				System.out.println(tempList.get(0)+"");
				System.out.println((tempList.get(0)+"").split(",") );
				actionList = ( (tempList.get(0)+"").split(",") );
				System.out.println("actionList :: "+actionList.toString());
			}
			/*for(Object object : tempList){
				Object[] obj = (Object[]) object;
				actionList = ( (obj[0]+"").split(",") );
			}*/
		}catch(Exception e){
			log.error("Error Occured PermissionsGroupDaoImpl --> getScreenActionsList method ",e);
		}	
		return actionList;
	}


	@Override
	public Map<String, List> getScreenActionsMapList(Integer permissionsGroupId) {
		// TODO Auto-generated method stub
		Map<String, List> screenList = new HashMap<String, List>();	
		Session session = sessionFactory.getCurrentSession();
		try{
			
			String query = " ";
			
			if(permissionsGroupId != null && permissionsGroupId > 0){
				query = " SELECT sa.screen_id,group_name,screen_name,GROUP_CONCAT(msa.action_name) AS actions, (SELECT GROUP_CONCAT(msa1.action_name) AS actions1 FROM screen_actions_mapping sam1 "
						+ " LEFT JOIN screen_permissions_group_mapping spgm ON (spgm.screen_action_mapping_id = sam1.screen_action_mapping_id and spgm.is_active = 'Y') LEFT JOIN permissions_group pg ON (pg.permissions_group_id = spgm.permissions_group_id )  "
						+ " LEFT JOIN  m_screen_actions msa1 ON (msa1.master_screen_action_id= sam1.master_screen_action_id)  WHERE pg.`permissions_group_id` ="+permissionsGroupId+" AND  sam.screen_id = sam1.screen_id and sam1.is_active='Y'  "
						+ " GROUP BY  sam.screen_id ) AS selectedactions FROM screens_list sa INNER JOIN screen_actions_mapping sam ON (sa.screen_id = sam.screen_id)  "
						+ " INNER JOIN m_screen_actions msa ON (msa.master_screen_action_id= sam.master_screen_action_id) WHERE sa.is_active = 'Y' and sam.is_active='Y' GROUP BY screen_id ORDER BY group_name,screen_name  ";
			}else{
				 query = " SELECT sa.`screen_id`,`group_name`,`screen_name`,GROUP_CONCAT(`action_name`) as actions FROM screens_list sa INNER JOIN `screen_actions_mapping` sam ON (sa.`screen_id` = sam.`screen_id`) INNER JOIN m_screen_actions msa ON (msa.`master_screen_action_id`= sam.`master_screen_action_id`)  WHERE sa.`is_active` = 'Y' and sam.is_active='Y'  group by screen_id Order by group_name,screen_name ";
			}
			List tempList  = session.createSQLQuery(query).list();
			for(Object object : tempList){
				Object[] obj = (Object[]) object;
				ScreenActionsVo actionVo = new ScreenActionsVo();
				actionVo.setScreenActionId( (Integer) obj[0]);
				actionVo.setGroupName(obj[1]+"");
				actionVo.setScreenName(obj[2]+"");
				actionVo.setActions(obj[3]+"");
				if(permissionsGroupId != null && permissionsGroupId > 0){
					if(obj[4] != null && (obj[4]+"").contains(","))
						actionVo.setActionsArray((obj[4]+"").split(","));
					else if(obj[4] != null)
						actionVo.setActionsArray( new String[]{obj[4]+""});
				} 
				
				if( screenList != null &&  screenList.containsKey(obj[1]+"") ){
					screenList.get(obj[1]+"").add(actionVo);
				}else {
					ArrayList<ScreenActionsVo> arrayList1 = new ArrayList<ScreenActionsVo>();
					arrayList1.add(actionVo);
					screenList.put(obj[1]+"",arrayList1);
				}
				
			}
		}catch(Exception e){
			log.error("Error Occured PermissionsGroupDaoImpl --> getScreenActionsMapList method ",e);
		}	
		return screenList;
	}


	@Override
	public Integer saveOrUpdatePermissionsGroupDetails(PermissionsGroupVo groupVo) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Integer id = 0;
		try{
			Criteria criteria = session.createCriteria(PermissionsGroup.class);
			criteria.add(Restrictions.eq("permissionsGroupName", groupVo.getPermissionsGroupName()));
			
			if(groupVo != null && groupVo.getPermissionsGroupId() != null && groupVo.getPermissionsGroupId() > 0)
				criteria.add(Restrictions.ne("permissionsGroupId", groupVo.getPermissionsGroupId())); 
			
			List duplicateList = criteria.list();
			if(duplicateList.size() > 0){
				return -1;
			}else{
				PermissionsGroup actions = new PermissionsGroup();
				if(groupVo != null && groupVo.getPermissionsGroupId() != null && groupVo.getPermissionsGroupId() > 0){
					actions = (PermissionsGroup) session.load(PermissionsGroup.class, groupVo.getPermissionsGroupId());
				}				
				actions.setPermissionsGroupName(groupVo.getPermissionsGroupName());
				actions.setPermissionsGroupDesc(groupVo.getPermissionsGroupDesc());
				actions.setIsActive("Y");
				actions.setCreatedBy(1);
				actions.setModifiedBy(1);
				actions.setCreatedDate(new Date());
				actions.setModifiedDate(new Date());
				id = (Integer) session.save(actions);
				
				if(groupVo != null && groupVo.getPermissionsGroupId() != null && groupVo.getPermissionsGroupId() > 0){
					try{
						session.createSQLQuery("update screen_permissions_group_mapping  set is_active='N' where permissions_group_id="+id).executeUpdate();
						for(ScreenActionsVo  actionVo : groupVo.getScreenActionsVo()){
							if(actionVo.getActionsArray() != null){
								for(String action : actionVo.getActionsArray()){
									Integer mapId = (Integer)session.createSQLQuery("SELECT `screen_action_mapping_id` FROM `screen_actions_mapping` sam INNER JOIN `m_screen_actions` msa ON (sam.`master_screen_action_id`=msa.`master_screen_action_id`) WHERE `screen_id`='"+actionVo.getScreenActionId()+"' AND `action_name` = '"+action+"' and  sam.is_active='Y' ").list().get(0);
									// List<ScreenActionsMapping> actionsMapping = session.createQuery(" from ScreenActionsMapping sam inner join MScreenActions msa on (sam.MScreenActions = msa.masterScreenActionId) where sam.screensList='"+actionVo.getScreenActionId()+"' AND msa.actionName= '"+action+"'").list();
									ScreenPermissionsGroupMapping mapping = new ScreenPermissionsGroupMapping();
									mapping.setScreenActionsMapping( new ScreenActionsMapping(mapId));
									mapping.setPermissionsGroup(new PermissionsGroup(id));
									mapping.setIsActive("Y");
									session.merge(mapping);
								}
							}
						}
					}catch(Exception e){
						e.printStackTrace();
						return -2;
					}
					
				}else{
					for(ScreenActionsVo  actionVo : groupVo.getScreenActionsVo()){ 
						if(actionVo.getActionsArray() != null){
							for(String action : actionVo.getActionsArray()){
								Integer mapId = (Integer)session.createSQLQuery("SELECT `screen_action_mapping_id` FROM `screen_actions_mapping` sam INNER JOIN `m_screen_actions` msa ON (sam.`master_screen_action_id`=msa.`master_screen_action_id`) WHERE `screen_id`='"+actionVo.getScreenActionId()+"' AND `action_name` = '"+action+"' sam.is_active='Y'").list().get(0);
								// List<ScreenActionsMapping> actionsMapping = session.createQuery(" from ScreenActionsMapping sam inner join MScreenActions msa on (sam.MScreenActions = msa.masterScreenActionId) where sam.screensList='"+actionVo.getScreenActionId()+"' AND msa.actionName= '"+action+"'").list();
								ScreenPermissionsGroupMapping mapping = new ScreenPermissionsGroupMapping();
								mapping.setScreenActionsMapping( new ScreenActionsMapping(mapId));
								mapping.setPermissionsGroup(new PermissionsGroup(id));
								mapping.setIsActive("Y");
								session.merge(mapping);
							}
						}
					}
				}
				
			}
			session.flush();
		}catch(Exception e){
			log.error("Error Occured ScreenActionsDaoImpl --> saveOrUpdateScreenActionsDetails method ",e);
		}	
		return id;
	}


	@Override
	public PermissionsGroupVo getPermissionGroupByPermissionGroupId(Integer permissionsGroupId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		PermissionsGroupVo  groupVo = new PermissionsGroupVo();
		try{
			PermissionsGroup group  = (PermissionsGroup) session.get(PermissionsGroup.class, permissionsGroupId);
			if(group != null ){
				groupVo.setPermissionsGroupId(group.getPermissionsGroupId());
				groupVo.setPermissionsGroupName(group.getPermissionsGroupName());
				groupVo.setPermissionsGroupDesc(group.getPermissionsGroupDesc());
			}
			/*for(Object object : tempList){
				Object[] obj = (Object[]) object;
				actionList = ( (obj[0]+"").split(",") );
			}*/
		}catch(Exception e){
			log.error("Error Occured PermissionsGroupDaoImpl --> getScreenActionsList method ",e);
		}	
		return groupVo;
	}


	
}
