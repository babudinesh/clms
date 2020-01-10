package com.Ntranga.CLMS.Dao;

import java.util.List;

import com.Ntranga.CLMS.vo.RolesVo;

public interface RolesDao {

	public List<RolesVo> getRoles(RolesVo  rolesVo);

	public Integer saveOrUpdateRoleDetails(RolesVo groupVo);

	public RolesVo getRolesByrolesId(Integer roleId);
}
