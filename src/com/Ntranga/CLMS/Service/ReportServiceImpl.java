package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.ReportDao;
import com.Ntranga.CLMS.vo.AttendanceReportVo;




@Service("reportService")
public class ReportServiceImpl implements ReportService {

	@Autowired
	private  ReportDao reportDao;

	@Override
	public List<AttendanceReportVo> getAttandanceReport(String attandanceReportJsonString) {
		// TODO Auto-generated method stub
		return reportDao.getAttandanceReport(attandanceReportJsonString);
	}

	@Override
	public List<AttendanceReportVo> getActualAttandanceReport(String actualAttandanceReportJsonString) {
		// TODO Auto-generated method stub
		return reportDao.getActualAttandanceReport(actualAttandanceReportJsonString);
	}


	

}
