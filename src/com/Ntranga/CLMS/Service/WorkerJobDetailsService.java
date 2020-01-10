package com.Ntranga.CLMS.Service;

import java.util.List;

import com.Ntranga.CLMS.vo.AssignShiftsVo;
import com.Ntranga.CLMS.vo.PlantVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.WorkJobDetailsVo;

public interface WorkerJobDetailsService {

	public List<WorkJobDetailsVo> getSearchGridData(Integer customerId, Integer companyId, Integer vendorId, String workerCode, String workerName, String isActive );

	public  List<WorkJobDetailsVo>  getWorkJobDetailsById(Integer workerJobId);

	public List<SimpleObject> getTransactionDatesOfHistory(Integer customerId, Integer companyId, Integer vendorId, Integer workerId);

	public Integer saveOrUpdateWorkJobRecord(WorkJobDetailsVo workJobDetailsVo);

	public List<SimpleObject> getWorkerNamesAsDropDown(Integer customerId, Integer companyId, Integer vendorId);

	public List<SimpleObject> getReportingManagerDropDown(String customerId, String companyId);

	public List<SimpleObject> getJobCodesDropDown(String customerId, String companyId);

	public List<SimpleObject> getCardTypesDropDown(String customerId, String companyId);

	public List<SimpleObject> getWorkOrdersDropDown(String customerId, String companyId);

	public AssignShiftsVo getDeptAntPlantDropDown(Integer customerId, Integer companyId, Integer workOrderId);
	
	public List<SimpleObject> getWageRateDropDown(Integer customerId, Integer companyId, Integer vendorId, Integer jobCode, String jobCategory, String jobType);
	
	//public List<SimpleObject> getLocationsDropdown(Integer customerId, Integer companyId, Integer workOrderId);
	
	//public List<SimpleObject> getDepartmentsDropdown(Integer customerId, Integer companyId, Integer workOrderId);
	
	public List<SimpleObject> getPlantDropDownByLocationId(Integer locationId,String plantArray);
	
	public List<SimpleObject> getAllWorkerNamesAsDropDown(Integer customerId, Integer companyId, Integer vendorId);

}
