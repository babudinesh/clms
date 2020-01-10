package com.Ntranga.CLMS.Service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.PermissionsGroupDao;
import com.Ntranga.CLMS.vo.PermissionsGroupVo;




@Service("permissionsGroupService")
public class PermissionsGroupServiceImpl implements PermissionsGroupService {

	@Autowired
	private  PermissionsGroupDao permissionsGroupDao;

	@Override
	public List<PermissionsGroupVo> getPermissionsGroupName(PermissionsGroupVo  groupVo) {
		// TODO Auto-generated method stub
		return permissionsGroupDao.getPermissionsGroupName(groupVo);
	}

	@Override
	public String[] getScreenActionsList() {
		// TODO Auto-generated method stub
		return permissionsGroupDao.getScreenActionsList();
	}

	@Override
	public Map<String, List> getScreenActionsMapList(Integer permissionsGroupId) {
		// TODO Auto-generated method stub
		return permissionsGroupDao.getScreenActionsMapList(permissionsGroupId);
	}

	@Override
	public Integer saveOrUpdatePermissionsGroupDetails(PermissionsGroupVo groupVo) {
		// TODO Auto-generated method stub
		return permissionsGroupDao.saveOrUpdatePermissionsGroupDetails(groupVo);
	}

	@Override
	public PermissionsGroupVo getPermissionGroupByPermissionGroupId(Integer permissionsGroupId) {
		// TODO Auto-generated method stub
		return permissionsGroupDao.getPermissionGroupByPermissionGroupId(permissionsGroupId);
	}

}
