package com.Ntranga.CLMS.Service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.DepartmentWiseWorkerAllocationDao;
import com.Ntranga.CLMS.vo.DepartmentwiseWorkerAllocationDetailsVo;

@Service("departmentWiseWorkerAllocationService")
public class DepartmentWiseWorkerAllocationServiceImpl implements DepartmentWiseWorkerAllocationService {

	@Autowired
	private DepartmentWiseWorkerAllocationDao departmentWiseWorkerAllocationDao;

	@Override
	public List<DepartmentwiseWorkerAllocationDetailsVo> WorkerAllocationDetailsId(Integer workerAllocationId) {
		// TODO Auto-generated method stub
		return departmentWiseWorkerAllocationDao.WorkerAllocationDetailsId(workerAllocationId);
	}

	@Override
	public Integer saveOrUpdateWorkerAllocationDetails(DepartmentwiseWorkerAllocationDetailsVo workerVo) {
		// TODO Auto-generated method stub
		return departmentWiseWorkerAllocationDao.saveOrUpdateWorkerAllocationDetails(workerVo);
	}

	@Override
	public List<DepartmentwiseWorkerAllocationDetailsVo> DepartmentWiseWorkerAllocationGridDetails(String customerId,
			String companyId, String countryId, String departmentId, String locationId, String plannedOrAdhoc,
			String fromDate, String toDate) {
		// TODO Auto-generated method stub
		return departmentWiseWorkerAllocationDao.DepartmentWiseWorkerAllocationGridDetails(customerId, companyId,
				countryId, departmentId, locationId, plannedOrAdhoc, fromDate, toDate);
	}

}
