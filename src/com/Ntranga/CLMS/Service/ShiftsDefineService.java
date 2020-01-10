package com.Ntranga.CLMS.Service;

import java.util.List;
import java.util.Map;

import com.Ntranga.CLMS.vo.ShiftsDefineVo;

@SuppressWarnings("rawtypes")
public interface ShiftsDefineService {

	public Integer saveShiftDefineData(ShiftsDefineVo shiftsDefineVo);
	
	public ShiftsDefineVo getShiftRecordByShiftId(Integer shiftDefineId);

	public Map<String,List> getTransactionDatesForShiftsHistory(Integer customerId, Integer companyId,String shiftCode);	

	public List<ShiftsDefineVo> getShiftDefineGridData(Integer customerId, Integer companyId, String status, String shiftCode,
			String shiftName);

}
