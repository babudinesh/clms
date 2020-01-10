package com.Ntranga.CLMS.Service;

import java.util.List;

import com.Ntranga.CLMS.vo.HolidayCalendarHolidaysInfoVo;
import com.Ntranga.CLMS.vo.HolidayCalendarVo;
import com.Ntranga.CLMS.vo.SimpleObject;

public interface HolidayCalendarService {

	public Integer saveHolidayCalendar(HolidayCalendarVo paramHoliday);	
	
	public List<HolidayCalendarVo>  getHolidayCalendarById(Integer holidayCalendarDetailsId);
	
	public List<HolidayCalendarVo> getHolidayCalendarListBySearch(HolidayCalendarVo paramHoliday);
	
	public List<HolidayCalendarHolidaysInfoVo> getHolidaysList(Integer holidayCalendarDetailsId);
	
	public List<SimpleObject> getHolidayCalendarDropdown(Integer customerId, Integer companyId);
	
}
