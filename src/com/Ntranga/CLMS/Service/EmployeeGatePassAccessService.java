package com.Ntranga.CLMS.Service;

import java.util.List;

import com.Ntranga.CLMS.vo.EmployeeGatePassAccessVo;
import com.Ntranga.CLMS.vo.OTExceededWorkersVo;
import com.Ntranga.CLMS.vo.SimpleObject;

public interface EmployeeGatePassAccessService {

	public Integer saveGatePassAccessRecord(EmployeeGatePassAccessVo employeeGatePassAccessVo);
	
	public EmployeeGatePassAccessVo getGatePassAccessDetails(EmployeeGatePassAccessVo employeeGatePassAccessVo);

	public List<SimpleObject> getTransactionDatesForHistory(EmployeeGatePassAccessVo employeeGatePassAccessVo);

	public EmployeeGatePassAccessVo getGatePassAccessRecordById(EmployeeGatePassAccessVo employeeGatePassAccessVo);

	public List<OTExceededWorkersVo> getOTExceededWorkers(String employeeGatePassAccessJsonString);

}
