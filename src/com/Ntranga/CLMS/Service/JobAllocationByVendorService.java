package com.Ntranga.CLMS.Service;

import java.sql.Date;
import java.util.List;

import com.Ntranga.CLMS.vo.JobAllocationByVendorVo;
import com.Ntranga.CLMS.vo.SearchCriteriaVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.WorkerDetailsVo;

public interface JobAllocationByVendorService {
	
	public List<WorkerDetailsVo> workerGridDetails(String customerId,String companyId,String vendorId,String status,String workerCode, String workerName,String workerId);
	
	public List<SimpleObject> getDepartmentsList(Integer customerId,Integer CompanyId);

	public List<SimpleObject> getlocationsList(String customerId,String CompanyId, String locationId);

	public List<SimpleObject> getAllPlantsListByLocationId(String locationId);

	public List<SimpleObject> getAllSectionesByLocationAndPlant(String locationId,String plantId);

	public List<SimpleObject> getAllWorkAreasBySectionesAndLocationAndPlant(String customerId,String companyId,String locationId, String plantId, String sectionId, String departmentId);

	public  SimpleObject saveJobAllocationDetails(JobAllocationByVendorVo jobAllocationVO,boolean callMethod);

	public List<JobAllocationByVendorVo> searchJobAllocation(SearchCriteriaVo searchVo);

	public List<SimpleObject> getWorkersCountFromManpower(JobAllocationByVendorVo jobAllocationVo);
	
}
