package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.WorkerJobDetailsDao;
import com.Ntranga.CLMS.vo.AssignShiftsVo;
import com.Ntranga.CLMS.vo.PlantVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.WorkJobDetailsVo;

@Service("workerJobDetailsService")
public  class WorkerJobDetailsServiceImpl implements WorkerJobDetailsService {

	@Autowired
	private WorkerJobDetailsDao workerJobDetailsDao;

	@Override
	public List<WorkJobDetailsVo> getSearchGridData(Integer customerId, Integer companyId, Integer vendorId,
			String workerId, String workerName, String isActive) {
		// TODO Auto-generated method stub
		return workerJobDetailsDao.getSearchGridData(customerId, companyId, vendorId, workerId, workerName, isActive);
	}

	@Override
	public  List<WorkJobDetailsVo>  getWorkJobDetailsById(Integer workerJobId) {
		// TODO Auto-generated method stub
		return workerJobDetailsDao.getWorkJobDetailsById(workerJobId);
	}

	@Override
	public List<SimpleObject> getTransactionDatesOfHistory(Integer customerId, Integer companyId, Integer vendorId,
			Integer workerId) {
		// TODO Auto-generated method stub
		return workerJobDetailsDao.getTransactionDatesOfHistory(customerId, companyId, vendorId, workerId);
	}

	@Override
	public Integer saveOrUpdateWorkJobRecord(WorkJobDetailsVo workJobDetailsVo) {
		// TODO Auto-generated method stub
		return workerJobDetailsDao.saveOrUpdateWorkJobRecord(workJobDetailsVo);
	}

	@Override
	public List<SimpleObject> getWorkerNamesAsDropDown(Integer customerId, Integer companyId, Integer vendorId) {
		// TODO Auto-generated method stub
		return workerJobDetailsDao.getWorkerNamesAsDropDown(customerId, companyId, vendorId);
	}
	
	@Override
	public List<SimpleObject> getAllWorkerNamesAsDropDown(Integer customerId, Integer companyId, Integer vendorId) {
		// TODO Auto-generated method stub
		return workerJobDetailsDao.getAllWorkerNamesAsDropDown(customerId, companyId, vendorId);
	}

	@Override
	public List<SimpleObject> getReportingManagerDropDown(String customerId, String companyId) {
		// TODO Auto-generated method stub
		return workerJobDetailsDao.getReportingManagerDropDown(customerId, companyId);
	}

	@Override
	public List<SimpleObject> getJobCodesDropDown(String customerId, String companyId) {
		// TODO Auto-generated method stub
		return workerJobDetailsDao.getJobCodesDropDown(customerId, companyId);
	}

	@Override
	public List<SimpleObject> getCardTypesDropDown(String customerId, String companyId) {
		// TODO Auto-generated method stub
		return workerJobDetailsDao.getCardTypesDropDown(customerId, companyId);
	}

	@Override
	public List<SimpleObject> getWorkOrdersDropDown(String customerId, String companyId) {
		// TODO Auto-generated method stub
		return workerJobDetailsDao.getWorkOrdersDropDown(customerId, companyId);
	}

	@Override
	public AssignShiftsVo getDeptAntPlantDropDown(Integer customerId, Integer companyId, Integer workOrderId){
		// TODO Auto-generated method stub
		return workerJobDetailsDao.getDeptAntPlantDropDown(customerId, companyId, workOrderId);
	}
	
	@Override
	public List<SimpleObject> getWageRateDropDown(Integer customerId,Integer companyId, Integer vendorId,Integer jobCode, String jobCategory, String jobType) {
		return workerJobDetailsDao.getWageRateDropDown(customerId, companyId, vendorId, jobCode, jobCategory, jobType);
	}

	@Override
	public List<SimpleObject> getPlantDropDownByLocationId(Integer locationId,String plantArray) {
		// TODO Auto-generated method stub
		//return workerJobDetailsDao.getPlantDropDownByLocationId(locationId,plantArray);
		return null;
	}
	
	/*@Override
	public List<SimpleObject> getLocationsDropdown(Integer customerId, Integer companyId, Integer workOrderId){
		return workerJobDetailsDao.getLocationsDropdown(customerId, companyId, workOrderId);
	}
	
	@Override
	public List<SimpleObject> getDepartmentsDropdown(Integer customerId, Integer companyId, Integer workOrderId){
		return workerJobDetailsDao.getDepartmentsDropdown(customerId, companyId, workOrderId);
	}*/
}
