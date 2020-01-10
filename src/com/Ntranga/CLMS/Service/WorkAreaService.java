package com.Ntranga.CLMS.Service;

import java.util.List;

import com.Ntranga.CLMS.vo.MWorkSkillVo;
import com.Ntranga.CLMS.vo.WorkAreaVo;
import com.Ntranga.CLMS.vo.SimpleObject;

public interface WorkAreaService {

	public Integer saveWorkArea(WorkAreaVo paramArea);	
	
	public List<WorkAreaVo>  getWorkAreaById(Integer workAreaDetailsId);
	
	public List<WorkAreaVo> getWorkAreaListBySearch(WorkAreaVo paramWorkArea);
	
	public List<SimpleObject> getTransactionListForWorkArea(Integer customerId, Integer companyId, Integer workAreaId);

	public List<MWorkSkillVo> getWorkAreaSkillsList(Integer workAreaId);
	
	public List<SimpleObject> getWorkAreaListForDropDown(Integer customerId, Integer companyId, String status);
}

