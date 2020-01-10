package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.WageCalculatorDao;
import com.Ntranga.CLMS.vo.WageCalculatorSaveVo;
import com.Ntranga.CLMS.vo.WageCalculatorVo;




@Service("wageCalculatorService")
public class WageCalculatorServiceImpl implements WageCalculatorService {

	@Autowired
	private  WageCalculatorDao wageCalculatorDao;

	@Override
	public List<WageCalculatorVo> getWageCalculatorList(String jsonSearchContent) {
		// TODO Auto-generated method stub
		return wageCalculatorDao.getWageCalculatorList(jsonSearchContent);
	}

	@Override
	public Integer saveWageCalculator(WageCalculatorSaveVo wageCalculatorSaveVo) {
		// TODO Auto-generated method stub
		return wageCalculatorDao.saveWageCalculator(wageCalculatorSaveVo);
	}

}
