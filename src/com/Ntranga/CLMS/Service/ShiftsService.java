package com.Ntranga.CLMS.Service;

import java.util.List;

import com.Ntranga.CLMS.vo.ShiftsVo;
import com.Ntranga.CLMS.vo.SimpleObject;

public interface ShiftsService {

	public Integer saveShiftData(ShiftsVo shiftsVo);

	public ShiftsVo getShiftRecord(ShiftsVo shiftsVo);
	
	public ShiftsVo getShiftRecordByShiftId(Integer shiftId);

	public List<SimpleObject> getTransactionDatesForShiftsHistory(ShiftsVo shiftsVo);

}
