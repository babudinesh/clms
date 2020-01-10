package com.Ntranga.CLMS.Service;

import java.util.List;

import com.Ntranga.CLMS.vo.AttendanceReportVo;

public interface ReportService {

	public List<AttendanceReportVo> getAttandanceReport(String attandanceReportJsonString);

	public List<AttendanceReportVo> getActualAttandanceReport(String actualAttandanceReportJsonString);


}
