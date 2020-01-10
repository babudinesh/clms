package com.Ntranga.CLMS.Service;

import java.util.List;

import com.Ntranga.CLMS.vo.RolesVo;

public interface RolesService {

	public List<RolesVo> getRoles(RolesVo  rolesVo);

	public Integer saveOrUpdateRoleDetails(RolesVo groupVo);

	public RolesVo getRolesByrolesId(Integer roleId);

	
}
