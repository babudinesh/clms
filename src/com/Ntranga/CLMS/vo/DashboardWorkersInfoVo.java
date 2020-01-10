package com.Ntranga.CLMS.vo;

import java.util.List;

public class DashboardWorkersInfoVo {

	private List<DashboardWorkersTypeVo> types;
	private String dateRange ;
	
	
	public List<DashboardWorkersTypeVo> getTypes() {
		return types;
	}
	
	public void setTypes(List<DashboardWorkersTypeVo> types) {
		this.types = types;
	}
	
	public String getDateRange() {
		return dateRange;
	}
	
	public void setDateRange(String dateRange) {
		this.dateRange = dateRange;
	}
	
	@Override
	public String toString() {
		return "DashboardWorkersInfoVo [dateRange=" + dateRange + "]";
	}
	
	
}
