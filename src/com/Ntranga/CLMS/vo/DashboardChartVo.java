package com.Ntranga.CLMS.vo;

import java.util.List;

public class DashboardChartVo {

	List<List<Integer>> chartData;
	List<String> ticks;
	
	
	public List<List<Integer>> getChartData() {
		return chartData;
	}
	public void setChartData(List<List<Integer>> chartData) {
		this.chartData = chartData;
	}
	public List<String> getTicks() {
		return ticks;
	}
	public void setTicks(List<String> ticks) {
		this.ticks = ticks;
	}
	
	
}
