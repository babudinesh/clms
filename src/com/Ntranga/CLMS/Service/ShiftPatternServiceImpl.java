package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.ShiftPatternDao;
import com.Ntranga.CLMS.vo.ShiftPatternAdditionalRulesVo;
import com.Ntranga.CLMS.vo.ShiftPatternAssignShiftsVo;
import com.Ntranga.CLMS.vo.ShiftPatternVo;
import com.Ntranga.CLMS.vo.SimpleObject;

@Service("shiftPatternService")
public class ShiftPatternServiceImpl implements ShiftPatternService {

	@Autowired
	private ShiftPatternDao shiftPatternDao;
	
	@Override
	public Integer saveShiftPattern(ShiftPatternVo paramShift) {
		return shiftPatternDao.saveShiftPattern(paramShift);
	}

	@Override
	public List<ShiftPatternVo> getShiftPatternById(Integer shiftPatternDetailsId) {
		return shiftPatternDao.getShiftPatternById(shiftPatternDetailsId);
	}

	@Override
	public List<ShiftPatternVo> getShiftPatternListBySearch(ShiftPatternVo paramShift) {
		return shiftPatternDao.getShiftPatternListBySearch(paramShift);
	}

	@Override
	public List<SimpleObject> getTransactionListForShiftPattern(Integer customerId, Integer companyId, Integer shiftPatternId) {
		return shiftPatternDao.getTransactionListForShiftPattern(customerId, companyId, shiftPatternId);
	}

	@Override
	public List<ShiftPatternAdditionalRulesVo> getShiftAdditionalRules(Integer customerId, Integer companyId, Integer shiftPatternDetailsId) {
		return shiftPatternDao.getShiftAdditionalRules(customerId, companyId, shiftPatternDetailsId);
	}

	@Override
	public List<ShiftPatternAssignShiftsVo> getShiftPatternAssignShifts(Integer customerId, Integer companyId, Integer shiftPatternDetailsId) {
		return shiftPatternDao.getShiftPatternAssignShifts(customerId, companyId, shiftPatternDetailsId);
	}

	@Override
	public List<SimpleObject> getShiftPatternDropdown(Integer customerId, Integer companyId) {
		return shiftPatternDao.getShiftPatternDropdown(customerId, companyId);
	}

}
