package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.EmployeeGatePassAccessDao;
import com.Ntranga.CLMS.vo.EmployeeGatePassAccessVo;
import com.Ntranga.CLMS.vo.OTExceededWorkersVo;
import com.Ntranga.CLMS.vo.SimpleObject;

@Service("employeeGatePassAccessService")
public class EmployeeGatePassAccessServiceImpl implements EmployeeGatePassAccessService {

	@Autowired
	private EmployeeGatePassAccessDao employeeGatePassAccessDao;
	
	@Override
	public Integer saveGatePassAccessRecord(EmployeeGatePassAccessVo employeeGatePassAccessVo) {
		// TODO Auto-generated method stub
		return employeeGatePassAccessDao.saveGatePassAccessRecord(employeeGatePassAccessVo);
	}

	@Override
	public EmployeeGatePassAccessVo getGatePassAccessDetails(EmployeeGatePassAccessVo employeeGatePassAccessVo) {
		// TODO Auto-generated method stub
		return employeeGatePassAccessDao.getGatePassAccessDetails(employeeGatePassAccessVo);
	}

	@Override
	public List<SimpleObject> getTransactionDatesForHistory(EmployeeGatePassAccessVo employeeGatePassAccessVo) {
		// TODO Auto-generated method stub
		return employeeGatePassAccessDao.getTransactionDatesForHistory(employeeGatePassAccessVo);
		
	}

	@Override
	public EmployeeGatePassAccessVo getGatePassAccessRecordById(
			EmployeeGatePassAccessVo employeeGatePassAccessVo) {
		// TODO Auto-generated method stub
		return employeeGatePassAccessDao.getGatePassAccessRecordById(employeeGatePassAccessVo);
	}

	@Override
	public List<OTExceededWorkersVo> getOTExceededWorkers(String employeeGatePassAccessJsonString) {
		// TODO Auto-generated method stub
		return employeeGatePassAccessDao.getOTExceededWorkers(employeeGatePassAccessJsonString);
	}

}
