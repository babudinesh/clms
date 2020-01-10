package com.Ntranga.CLMS.Dao;

import java.util.List;
import java.util.Map;

import com.Ntranga.CLMS.vo.AssignShiftPatternDetailsVo;
import com.Ntranga.CLMS.vo.AssignShiftPatternUpdateVo;
import com.Ntranga.CLMS.vo.AssignShiftsVo;
import com.Ntranga.CLMS.vo.PlantVo;
import com.Ntranga.CLMS.vo.SimpleObject;

public interface AssignShiftsDao {

	public List<AssignShiftsVo> getWorkerDetailsToGridView(String jsonString);
	
	public List<AssignShiftsVo> getDeptLocPlantDtls(String jsonString);	
	
	public Integer saveShiftPatternDetails(AssignShiftPatternDetailsVo assignShiftPatternDetailsVo);
	
	public Map<String,List<Object>> previewDetails(AssignShiftPatternDetailsVo assignShiftPatternDetailsVo);
	
	public List<AssignShiftPatternUpdateVo> getAssignShiftPatternUpdateMasterData(Integer workerId,Integer year,Integer month);
	
	public Integer saveAssignShiftPatternRecord(AssignShiftPatternUpdateVo assignShiftPatternDetailsVo);
	
	public List<SimpleObject> getPatternCodesbyPlantId(PlantVo plantVo);
}
