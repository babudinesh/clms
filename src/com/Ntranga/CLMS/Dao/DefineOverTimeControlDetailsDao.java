package com.Ntranga.CLMS.Dao;

import java.util.List;

import com.Ntranga.CLMS.vo.DefineOverTimeControlDetailsInfoVo;
import com.Ntranga.CLMS.vo.SimpleObject;

public interface DefineOverTimeControlDetailsDao {

	public List<DefineOverTimeControlDetailsInfoVo> getOverTimeDetailsForGrid(DefineOverTimeControlDetailsInfoVo overTimeVo);

	public Integer saveOrUpdateOverTime(DefineOverTimeControlDetailsInfoVo overTimeVo);

	public List<DefineOverTimeControlDetailsInfoVo> getDefineOverTimeDetailsByInfoId(DefineOverTimeControlDetailsInfoVo overTimeVo);
	
	public List<SimpleObject> getOverTimeNamesByCompanyId(Integer customerDetailsId, Integer companyDetailsId);
	
	public List<SimpleObject> getTransactionDatesListForOverTimeHistory(DefineOverTimeControlDetailsInfoVo overTimeVo);

}
