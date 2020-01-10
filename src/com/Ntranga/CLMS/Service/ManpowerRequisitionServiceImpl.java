package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.ManpowerRequisitionDao;
import com.Ntranga.CLMS.vo.EmployeeInformationVo;
import com.Ntranga.CLMS.vo.ManpowerRequisitionVo;
import com.Ntranga.CLMS.vo.SimpleObject;

@Service("manpowerRequisitionService")
public class ManpowerRequisitionServiceImpl implements ManpowerRequisitionService {

	@Autowired
	ManpowerRequisitionDao manpowerRequisitionDao;
	
	@Override
	public Integer saveManpowerRequisition(ManpowerRequisitionVo paramManpower) {
		return manpowerRequisitionDao.saveManpowerRequisition(paramManpower);
	}

	@Override
	public List<ManpowerRequisitionVo> searchManpowerRequisitions(ManpowerRequisitionVo paramManpower) {
		return manpowerRequisitionDao.searchManpowerRequisitions(paramManpower);
	}

	@Override
	public List<ManpowerRequisitionVo> getManpowerRequisitionById(Integer manpowerRequisitionId) {
		return manpowerRequisitionDao.getManpowerRequisitionById(manpowerRequisitionId);
	}

	@Override
	public List<EmployeeInformationVo> getEmployeesDetails(ManpowerRequisitionVo paramManpower) {
		return manpowerRequisitionDao.getEmployeesDetails(paramManpower);
	}

	@Override
	public List<SimpleObject> getJobCodeListBySkillType(ManpowerRequisitionVo paramManpower) {
		return manpowerRequisitionDao.getJobCodeListBySkillType(paramManpower);
	}

	@Override
	public List<SimpleObject> getAssignedWorkersCountBySkillType(ManpowerRequisitionVo paramManpower) {
		return manpowerRequisitionDao.getAssignedWorkersCountBySkillType(paramManpower);
	}

}
