package com.Ntranga.CLMS.Dao;

import java.util.List;
import java.util.Map;

import com.Ntranga.CLMS.vo.AssignShiftPatternDetailsVo;
import com.Ntranga.CLMS.vo.AssignShiftPatternUpdateVo;
import com.Ntranga.CLMS.vo.AssignShiftsVo;
import com.Ntranga.CLMS.vo.PlantVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.WageCalculatorSaveVo;
import com.Ntranga.CLMS.vo.WageCalculatorVo;

public interface WageCalculatorDao {

	public List<WageCalculatorVo> getWageCalculatorList(String jsonSearchContent);
	
	public Integer saveWageCalculator(WageCalculatorSaveVo wageCalculatorSaveVo);
	
}
