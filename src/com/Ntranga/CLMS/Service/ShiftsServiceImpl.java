package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.ShiftsDao;
import com.Ntranga.CLMS.vo.ShiftsVo;
import com.Ntranga.CLMS.vo.SimpleObject;

@Service("shiftsService")
public class ShiftsServiceImpl implements ShiftsService {

	@Autowired
	private ShiftsDao shiftsDao;
	
	@Override
	public Integer saveShiftData(ShiftsVo shiftsVo) {
		// TODO Auto-generated method stub
		return shiftsDao.saveShiftData(shiftsVo);
	}

	@Override
	public ShiftsVo getShiftRecord(ShiftsVo shiftsVo) {
		// TODO Auto-generated method stub
		return shiftsDao.getShiftRecord(shiftsVo);
	}

	@Override
	public List<SimpleObject> getTransactionDatesForShiftsHistory(ShiftsVo shiftsVo) {
		// TODO Auto-generated method stub
		return shiftsDao.getTransactionDatesForShiftsHistory(shiftsVo);
	}

	@Override
	public ShiftsVo getShiftRecordByShiftId(Integer shiftId) {
		// TODO Auto-generated method stub
		return shiftsDao.getShiftRecordByShiftId(shiftId);
	}
	

}
