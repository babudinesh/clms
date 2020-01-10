package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.UsersDao;
import com.Ntranga.CLMS.vo.EmployeeInformationVo;
import com.Ntranga.CLMS.vo.UsersVo;




@Service("usersService")
public class UsersServiceImpl implements UsersService {

	@Autowired
	private  UsersDao usersDao;

	@Override
	public List<UsersVo> getUsers(UsersVo usersVo) {
		// TODO Auto-generated method stub
		return usersDao.getUsers(usersVo);
	}

	@Override
	public Integer saveOrUpdateUserDetails(UsersVo usersVo) {
		// TODO Auto-generated method stub
		return usersDao.saveOrUpdateUserDetails(usersVo);
	}

	@Override
	public UsersVo getUsersByUserId(Integer userId) {
		// TODO Auto-generated method stub
		return usersDao.getUsersByUserId(userId);
	}

	@Override
	public List<EmployeeInformationVo> getEmployeeDetails(EmployeeInformationVo employeeInformationVo) {
		// TODO Auto-generated method stub
		return usersDao.getEmployeeDetails(employeeInformationVo);
	}

}
