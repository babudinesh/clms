package com.Ntranga.CLMS.Dao;

import java.util.List;

import com.Ntranga.CLMS.vo.EmployeeInformationVo;
import com.Ntranga.CLMS.vo.UsersVo;

public interface UsersDao {

	public List<UsersVo> getUsers(UsersVo usersVo);

	public Integer saveOrUpdateUserDetails(UsersVo usersVo);

	public UsersVo getUsersByUserId(Integer userId);
	
	public List<EmployeeInformationVo> getEmployeeDetails(EmployeeInformationVo employeeInformationVo);
}
