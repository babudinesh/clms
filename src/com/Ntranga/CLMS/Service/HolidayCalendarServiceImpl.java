package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.HolidayCalendarDao;
import com.Ntranga.CLMS.vo.HolidayCalendarHolidaysInfoVo;
import com.Ntranga.CLMS.vo.HolidayCalendarVo;
import com.Ntranga.CLMS.vo.SimpleObject;

@Service("holidayCalendarService")
public class HolidayCalendarServiceImpl implements HolidayCalendarService {

	@Autowired
	HolidayCalendarDao holidayCalendarDao;
	
	@Override
	public Integer saveHolidayCalendar(HolidayCalendarVo paramHoliday) {
		return holidayCalendarDao.saveHolidayCalendar(paramHoliday);
	}

	@Override
	public List<HolidayCalendarVo> getHolidayCalendarById(Integer holidayCalendarDetailsId) {
		return holidayCalendarDao.getHolidayCalendarById(holidayCalendarDetailsId);
	}

	@Override
	public List<HolidayCalendarVo> getHolidayCalendarListBySearch(HolidayCalendarVo paramHoliday) {
		return holidayCalendarDao.getHolidayCalendarListBySearch(paramHoliday);
	}

	@Override
	public List<HolidayCalendarHolidaysInfoVo> getHolidaysList(Integer holidayCalendarDetailsId) {
		return holidayCalendarDao.getHolidaysList(holidayCalendarDetailsId);
	}

	@Override
	public List<SimpleObject> getHolidayCalendarDropdown(Integer customerId, Integer companyId) {
		return holidayCalendarDao.getHolidayCalendarDropdown(customerId, companyId);
	}

}
