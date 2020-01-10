package com.Ntranga.CLMS.Dao;

import java.util.List;

import com.Ntranga.CLMS.vo.AttendanceReportVo;

public interface ReportDao {

	
	public List<AttendanceReportVo> getAttandanceReport(String attandanceReportJsonString);

	public List<AttendanceReportVo> getActualAttandanceReport(String actualAttandanceReportJsonString);
	
}
