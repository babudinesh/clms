package com.Ntranga.CLMS.Service;

import java.util.List;

import com.Ntranga.CLMS.vo.WageCalculatorSaveVo;
import com.Ntranga.CLMS.vo.WageCalculatorVo;

public interface WageCalculatorService {

	public List<WageCalculatorVo> getWageCalculatorList(String jsonSearchContent);

	public Integer saveWageCalculator(WageCalculatorSaveVo wageCalculatorSaveVo);

	

}
