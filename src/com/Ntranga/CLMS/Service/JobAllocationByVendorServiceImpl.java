package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.JobAllocationByVendorDao;
import com.Ntranga.CLMS.vo.JobAllocationByVendorVo;
import com.Ntranga.CLMS.vo.SearchCriteriaVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.WorkerDetailsVo;




@Service("jobAllocationByVendorService")
public class JobAllocationByVendorServiceImpl implements JobAllocationByVendorService {

	@Autowired
	private  JobAllocationByVendorDao jobAllocationByVendorDao;

	@Override
	public List<SimpleObject> getDepartmentsList(Integer customerId, Integer CompanyId) {
		// TODO Auto-generated method stub
		return jobAllocationByVendorDao.getDepartmentsList(customerId, CompanyId);
	}

	@Override
	public List<SimpleObject> getlocationsList(String customerId, String CompanyId, String locationId) {
		// TODO Auto-generated method stub
		return jobAllocationByVendorDao.getlocationsList(customerId, CompanyId);
		//return null;
	}

	@Override
	public List<SimpleObject> getAllPlantsListByLocationId(String locationId) {
		// TODO Auto-generated method stub
		return jobAllocationByVendorDao.getAllPlantsListByLocationId(locationId);
	}

	@Override
	public List<SimpleObject> getAllSectionesByLocationAndPlant(String locationId, String plantId) {
		// TODO Auto-generated method stub
		return jobAllocationByVendorDao.getAllSectionesByLocationAndPlant(locationId, plantId);
	}

	@Override
	public List<SimpleObject> getAllWorkAreasBySectionesAndLocationAndPlant(String customerId, String companyId, String locationId, String plantId, String SectionId, String departmentId) {
		// TODO Auto-generated method stub
		return jobAllocationByVendorDao.getAllWorkAreasBySectionesAndLocationAndPlant(customerId, companyId, locationId, plantId, SectionId, departmentId);
	}
	
	
	@Override
	public  SimpleObject saveJobAllocationDetails(JobAllocationByVendorVo jobAllocationVO,boolean callMethod){
		return jobAllocationByVendorDao.saveJobAllocationDetails(jobAllocationVO, callMethod);
	}

	@Override
	public List<WorkerDetailsVo> workerGridDetails(String customerId, String companyId, String vendorId, String status,
			String workerCode, String workerName, String workerId) {
		// TODO Auto-generated method stub
		return jobAllocationByVendorDao.workerGridDetails(customerId, companyId, vendorId, status, workerCode, workerName, workerId);
	}

	@Override
	public List<JobAllocationByVendorVo> searchJobAllocation(SearchCriteriaVo searchVo) {
		return jobAllocationByVendorDao.searchJobAllocation(String.valueOf(searchVo.getCustomerId()),String.valueOf(searchVo.getCompanyId()),
		        String.valueOf(searchVo.getVendorId()),String.valueOf(searchVo.getStatus()),String.valueOf(searchVo.getWorkerCode()),
		        String.valueOf(searchVo.getWorkerName()),String.valueOf(searchVo.getWorkerId()),
		                searchVo.getSelectedDate(),searchVo.getSearchFor(),searchVo.getSchemaName());
		//return null;
	}

	@Override
	public List<SimpleObject> getWorkersCountFromManpower(JobAllocationByVendorVo jobAllocationVo) {
		return jobAllocationByVendorDao.getWorkersCountFromManpower(jobAllocationVo);
	}
	
}
