package com.Ntranga.CLMS.Dao;

import java.util.List;

import com.Ntranga.CLMS.vo.ShiftPatternAdditionalRulesVo;
import com.Ntranga.CLMS.vo.ShiftPatternAssignShiftsVo;
import com.Ntranga.CLMS.vo.ShiftPatternVo;
import com.Ntranga.CLMS.vo.SimpleObject;


public interface ShiftPatternDao {

	public Integer saveShiftPattern(ShiftPatternVo paramShift);	
	
	public List<ShiftPatternVo>  getShiftPatternById(Integer shiftPatternDetailsId);
	
	public List<ShiftPatternVo> getShiftPatternListBySearch(ShiftPatternVo paramShift);
	
	public List<SimpleObject> getTransactionListForShiftPattern(Integer customerId, Integer companyId, Integer shiftPatternId);
	
    public List<ShiftPatternAdditionalRulesVo> getShiftAdditionalRules(Integer customerId, Integer companyId, Integer shiftPatternDetailsId);

    public List<ShiftPatternAssignShiftsVo> getShiftPatternAssignShifts(Integer customerId, Integer companyId, Integer shiftPatternDetailsId);
    
    public List<SimpleObject> getShiftPatternDropdown(Integer customerId, Integer companyId);

}
