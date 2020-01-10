package com.Ntranga.CLMS.Service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.ShiftsDefineDao;
import com.Ntranga.CLMS.vo.ShiftsDefineVo;

@SuppressWarnings("rawtypes")
@Service("shiftsDefineService")
public class ShiftsDefineServiceImpl implements ShiftsDefineService {

	@Autowired
	private ShiftsDefineDao shiftsDefineDao;
	
	@Override
	public Integer saveShiftDefineData(ShiftsDefineVo shiftsDefineVo) {
		// TODO Auto-generated method stub
		return shiftsDefineDao.saveShiftDefineData(shiftsDefineVo);
	}

	@Override
	public Map<String,List> getTransactionDatesForShiftsHistory(Integer customerId, Integer companyId,String shiftCode) {
		// TODO Auto-generated method stub
		return shiftsDefineDao.getTransactionDatesForShiftsHistory(customerId, companyId,shiftCode);
	}

	@Override
	public ShiftsDefineVo getShiftRecordByShiftId(Integer shiftDefineId) {
		// TODO Auto-generated method stub
		return shiftsDefineDao.getShiftRecordByShiftId(shiftDefineId);
	}

	@Override
	public List<ShiftsDefineVo> getShiftDefineGridData(Integer customerId, Integer companyId, String status, String shiftCode,
			String shiftName) {
		// TODO Auto-generated method stub
		return shiftsDefineDao.getShiftDefineGridData(customerId, companyId, status, shiftCode, shiftName);
	}
	

}
