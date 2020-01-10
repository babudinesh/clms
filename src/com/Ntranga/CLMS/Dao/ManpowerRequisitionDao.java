package com.Ntranga.CLMS.Dao;

import java.util.List;

import com.Ntranga.CLMS.vo.EmployeeInformationVo;
import com.Ntranga.CLMS.vo.ManpowerRequisitionVo;
import com.Ntranga.CLMS.vo.SimpleObject;

public interface ManpowerRequisitionDao {
	
	public Integer saveManpowerRequisition(ManpowerRequisitionVo paramManpower);
	
	public List<ManpowerRequisitionVo> searchManpowerRequisitions(ManpowerRequisitionVo paramManpower);
	
	public List<ManpowerRequisitionVo> getManpowerRequisitionById(Integer manpowerRequisitionId);

	public List<EmployeeInformationVo> getEmployeesDetails(ManpowerRequisitionVo paramManpower);

	public List<SimpleObject> getJobCodeListBySkillType(ManpowerRequisitionVo paramManpower);
	
	public List<SimpleObject> getAssignedWorkersCountBySkillType(ManpowerRequisitionVo paramManpower);
}
