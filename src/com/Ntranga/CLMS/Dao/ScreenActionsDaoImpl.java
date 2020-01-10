package com.Ntranga.CLMS.Dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Ntranga.CLMS.vo.ScreenActionsVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.core.CLMS.entities.MScreenActions;
import com.Ntranga.core.CLMS.entities.ScreenActionsMapping;
import com.Ntranga.core.CLMS.entities.ScreensList;

import common.Logger;

@Transactional
@Repository("screenActionsDao")
public class ScreenActionsDaoImpl implements ScreenActionsDao  {

	private static Logger log = Logger.getLogger(ScreenActionsDaoImpl.class);
	
	
	@Autowired
	private SessionFactory sessionFactory;


	@Override
	public List<String> getGroupNames() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<String> returnList = new ArrayList<String>();
		try{
			String query = "SELECT DISTINCT group_name FROM screens_list WHERE `is_active` = 'Y' group by screen_name Order By group_name ";
			returnList = session.createSQLQuery(query).list();
		}catch(Exception e){
			log.error("Error Occured ScreenActionsDaoImpl --> getGroupNames method ",e);
		}	
		return returnList;
	}


	@Override
	public List<ScreenActionsVo> getScreenActionsSearchList(ScreenActionsVo actionVo) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<ScreenActionsVo> screenActionsVo = new ArrayList<ScreenActionsVo>();
		try{
			String query = " SELECT sa.`screen_id`,`group_name`,`screen_name`,GROUP_CONCAT(`action_name`) as actions FROM screens_list sa INNER JOIN `screen_actions_mapping` sam ON (sa.`screen_id` = sam.`screen_id` and sam.is_active='Y') INNER JOIN m_screen_actions msa ON (msa.`master_screen_action_id`= sam.`master_screen_action_id`)  WHERE sa.`is_active` = 'Y' ";
			if(actionVo != null && actionVo.getGroupName() != null && !actionVo.getGroupName().isEmpty())
				query += " and group_name LIKE '%"+actionVo.getGroupName()+"%'";
			if(actionVo != null && actionVo.getScreenName() != null && !actionVo.getScreenName().isEmpty())
				query += " and screen_name LIKE '%"+actionVo.getScreenName()+"%'";
			
			query += "  group by screen_name Order by group_name,screen_name";
			List tempList = session.createSQLQuery(query).list();
			for(Object object : tempList){
				Object[] obj = (Object[]) object;
				ScreenActionsVo actionsVo = new ScreenActionsVo();
				actionsVo.setScreenActionId( (Integer) obj[0]);
				actionsVo.setGroupName(obj[1]+"");
				actionsVo.setScreenName(obj[2]+"");
				actionsVo.setActions(obj[3]+"");
				screenActionsVo.add(actionsVo);
			}
		}catch(Exception e){
			log.error("Error Occured ScreenActionsDaoImpl --> getScreenActionsSearchList method ",e);
		}	
		return screenActionsVo;
	}


	@Override
	public List<SimpleObject> getScreenActionsList() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> returnList = new ArrayList<SimpleObject>();
		try{
			String query = "SELECT `master_screen_action_id`,`action_name` FROM m_screen_actions WHERE `is_active` = 'Y'";
			List tempList = session.createSQLQuery(query).list();
			for(Object object : tempList){
				Object[] obj = (Object[]) object;
				returnList.add( new SimpleObject((Integer) obj[0],obj[1]+"") );
			}
		}catch(Exception e){
			log.error("Error Occured ScreenActionsDaoImpl --> getGroupNames method ",e);
		}	
		return returnList;
	}


	@Override
	public Integer saveOrUpdateScreenActionsDetails(ScreenActionsVo actionVo) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Integer id = 0;
		try{
			Criteria criteria = session.createCriteria(ScreensList.class);
			criteria.add(Restrictions.eq("screenName", actionVo.getScreenName()));
			if(actionVo != null && actionVo.getScreenActionId() != null && actionVo.getScreenActionId() > 0)
				criteria.add(Restrictions.ne("screenId", actionVo.getScreenActionId())); // ("screenName", actionVo.getScreenName()));
			List duplicateList = criteria.list();
			if(duplicateList.size() > 0){
				return -1;
			}else{
				
				ScreensList actions = new ScreensList();
				if(actionVo != null && actionVo.getScreenActionId() != null && actionVo.getScreenActionId() > 0){
					actions = (ScreensList) session.load(ScreensList.class, actionVo.getScreenActionId());
				}				
				actions.setGroupName(actionVo.getGroupName());
				actions.setScreenName(actionVo.getScreenName());
				actions.setIsActive("Y");
				actions.setCreatedBy(1);
				actions.setModifiedBy(1);
				actions.setCreatedDate(new Date());
				actions.setModifiedDate(new Date());
				id = (Integer) session.save(actions);
				
				/*for(String action : actionVo.getActionsArray()){
					String childquery = " replace into screen_actions_mapping (`screen_id`, `master_screen_action_id`, `is_active`) values ( "+id+","+action+",'Y') ";
					session.createSQLQuery(childquery).executeUpdate();
					
				}*/
				
				if(actionVo != null && actionVo.getScreenActionId() != null && actionVo.getScreenActionId() > 0){
					try{
						List<Integer> existingList = session.createSQLQuery("SELECT master_screen_action_id FROM screen_actions_mapping WHERE screen_id="+id+" AND is_active='Y'").list();
						for(String value :  actionVo.getActionsArray()){
							Boolean flag = false;
							for(Integer i : existingList){
								if( i == Integer.valueOf(value)){
									flag = true;
								}
							}
							if(!flag){
								ScreenActionsMapping mapping = new ScreenActionsMapping();
								mapping.setMScreenActions(new MScreenActions(Integer.valueOf(value)));
								mapping.setScreensList(new ScreensList(id));
								mapping.setIsActive("Y");
								session.merge(mapping);
							}
						}
						
						/*session.createSQLQuery("Update screen_actions_mapping set is_active = 'N' where screen_id="+id).executeUpdate();
						for(String action : actionVo.getActionsArray()){
							ScreenActionsMapping mapping = new ScreenActionsMapping();
							mapping.setMScreenActions(new MScreenActions(Integer.valueOf(action)));
							mapping.setScreensList(new ScreensList(id));
							mapping.setIsActive("Y");
							session.merge(mapping);
						}*/
					}catch(Exception e){
						e.printStackTrace();
						return -2;
					}
					
				}else{
					for(String action : actionVo.getActionsArray()){
						ScreenActionsMapping mapping = new ScreenActionsMapping();
						mapping.setMScreenActions(new MScreenActions(Integer.valueOf(action)));
						mapping.setScreensList(new ScreensList(id));
						mapping.setIsActive("Y");
						session.merge(mapping);
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
	public ScreenActionsVo getScreenActionsByScreenId(Integer screenActionId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		ScreenActionsVo actionsVo = new ScreenActionsVo();
		try{
			String query = " SELECT sa.`screen_id`,`group_name`,`screen_name`,GROUP_CONCAT(msa.`master_screen_action_id`) as actions FROM screens_list sa INNER JOIN `screen_actions_mapping` sam ON (sa.`screen_id` = sam.`screen_id`) INNER JOIN m_screen_actions msa ON (msa.`master_screen_action_id`= sam.`master_screen_action_id`)  WHERE sam.is_active='Y' and sa.`screen_id` ="+screenActionId+" GROUP BY  screen_name ";
			
			List tempList = session.createSQLQuery(query).list();
			for(Object object : tempList){
				Object[] obj = (Object[]) object;
				actionsVo.setScreenActionId( (Integer) obj[0]);
				actionsVo.setGroupName(obj[1]+"");
				actionsVo.setScreenName(obj[2]+"");
				if(obj[3] != null && (obj[3]+"").contains(",") )
					actionsVo.setActionsArray( (obj[3]+"").split(","));
				else if(obj[3] != null){
					actionsVo.setActionsArray( new String[]{obj[3]+""});
					System.out.println(obj[3]+" inside");
				}
				System.out.println(obj[3]+"");
			}
			
		}catch(Exception e){
			log.error("Error Occured ScreenActionsDaoImpl --> saveOrUpdateScreenActionsDetails method ",e);
		}	
		return actionsVo;
	}	
	
}
