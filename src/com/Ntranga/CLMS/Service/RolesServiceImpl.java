package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.RolesDao;
import com.Ntranga.CLMS.vo.RolesVo;




@Service("rolesService")
public class RolesServiceImpl implements RolesService {

	@Autowired
	private  RolesDao rolesDao;

	@Override
	public List<RolesVo> getRoles(RolesVo rolesVo) {
		// TODO Auto-generated method stub
		return rolesDao.getRoles(rolesVo);
	}

	@Override
	public Integer saveOrUpdateRoleDetails(RolesVo groupVo) {
		// TODO Auto-generated method stub
		return rolesDao.saveOrUpdateRoleDetails(groupVo);
	}

	@Override
	public RolesVo getRolesByrolesId(Integer roleId) {
		// TODO Auto-generated method stub
		return rolesDao.getRolesByrolesId(roleId);
	}

	
}
