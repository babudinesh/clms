package com.Ntranga.CLMS.Service;

import java.util.List;
import java.util.Map;

import com.Ntranga.CLMS.vo.DashboardChartVo;
import com.Ntranga.CLMS.vo.DashboardComplianceStatusVo;
import com.Ntranga.CLMS.vo.DashboardVo;

@SuppressWarnings("rawtypes")
public interface DashboardService {

	public Map<String, List> getDashboardDetails(String dashboardJsonString);
	
	public <V> V getWorkersHeadCount(Integer customerId, Integer companyId, Integer vendorId);
	
	public <V> V getWorkersAgeGroupCount(DashboardVo paramDashboard);
	
	public DashboardChartVo getOvertimeDetails(DashboardVo paramDashboard);
	
	public <V> V  getShiftWiseAttendanceDetails(DashboardVo paramDashboard);
	
	public <V> V getVendorWiseAttendanceDetails(DashboardVo paramDashboard);
	
	public <V> V  getSkillWiseAttendanceDetails(DashboardVo paramDashboard);
	
	public List<DashboardComplianceStatusVo>  getVendorComplianceStatus(DashboardVo paramDashboard);

	public <V> V  getDepartmentWiseAttendanceDetails(DashboardVo paramDashboard);
	
}
