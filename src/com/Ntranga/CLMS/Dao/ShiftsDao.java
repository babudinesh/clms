package com.Ntranga.CLMS.Dao;

import java.util.List;

import com.Ntranga.CLMS.vo.ShiftsVo;
import com.Ntranga.CLMS.vo.SimpleObject;

public interface ShiftsDao {

	public Integer saveShiftData(ShiftsVo shiftsVo);

	public ShiftsVo getShiftRecord(ShiftsVo shiftsVo);
	
	public ShiftsVo getShiftRecordByShiftId(Integer shiftId);
	
	public List<SimpleObject> getTransactionDatesForShiftsHistory(ShiftsVo shiftsVo);
}
