package com.Ntranga.CLMS.Service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.DashboardDao;
import com.Ntranga.CLMS.vo.DashboardChartVo;
import com.Ntranga.CLMS.vo.DashboardComplianceStatusVo;
import com.Ntranga.CLMS.vo.DashboardVo;

@SuppressWarnings("rawtypes")
@Service("dashboardService")
public class DashboardServiceImpl implements DashboardService {

	@Autowired
	private  DashboardDao dashboardDao;
	
	public Map<String, List> getDashboardDetails(String dashboardJsonString){
		// TODO Auto-generated method stub
		return dashboardDao.getDashboardDetails(dashboardJsonString);
	}
	
	public <V> V getWorkersHeadCount(Integer customerId, Integer companyId, Integer vendorId){
		return dashboardDao.getWorkersHeadCount(customerId, companyId, vendorId);
	}
	
	public <V> V getWorkersAgeGroupCount(DashboardVo paramDashboard){
		return dashboardDao.getWorkersCountByAgeGroup(paramDashboard);
	}
	
	public DashboardChartVo  getOvertimeDetails(DashboardVo paramDashboard){
		return dashboardDao.getOvertimeDetails(paramDashboard);
	}
	
	public <V> V  getShiftWiseAttendanceDetails(DashboardVo paramDashboard){
		return dashboardDao.getShiftWiseAttendanceDetails(paramDashboard);
	}
	
	public <V> V getVendorWiseAttendanceDetails(DashboardVo paramDashboard){
		return dashboardDao.getVendorWiseAttendanceDetails(paramDashboard);
	}
	
	public <V> V  getSkillWiseAttendanceDetails(DashboardVo paramDashboard){
		return dashboardDao.getSkillWiseAttendanceDetails(paramDashboard);
	}
	
	public List<DashboardComplianceStatusVo>  getVendorComplianceStatus(DashboardVo paramDashboard){
		return dashboardDao.getVendorComplianceStatus(paramDashboard);
	}
	
	public <V> V  getDepartmentWiseAttendanceDetails(DashboardVo paramDashboard){
		return dashboardDao.getDepartmentWiseAttendanceDetails(paramDashboard);
	}

}
