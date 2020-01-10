package com.Ntranga.CLMS.Service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.AssignShiftsDao;
import com.Ntranga.CLMS.vo.AssignShiftPatternDetailsVo;
import com.Ntranga.CLMS.vo.AssignShiftPatternUpdateVo;
import com.Ntranga.CLMS.vo.AssignShiftsVo;
import com.Ntranga.CLMS.vo.PlantVo;
import com.Ntranga.CLMS.vo.SimpleObject;




@Service("assignShiftservice")
public class AssignShiftsServiceImpl implements AssignShiftsService {

	@Autowired
	private  AssignShiftsDao assignShiftsDao;

	@Override
	public List<AssignShiftsVo> getWorkerDetailsToGridView(String jsonString) {
		// TODO Auto-generated method stub
		return assignShiftsDao.getWorkerDetailsToGridView(jsonString);
	}

	@Override
	public List<AssignShiftsVo> getDeptLocPlantDtls(String jsonString) {
		// TODO Auto-generated method stub
		return assignShiftsDao.getDeptLocPlantDtls(jsonString);
	}

	@Override
	public Integer saveShiftPatternDetails(AssignShiftPatternDetailsVo assignShiftPatternDetailsVo) {
		// TODO Auto-generated method stub
		return assignShiftsDao.saveShiftPatternDetails(assignShiftPatternDetailsVo);
	}

	@Override
	public Map<String,List<Object>> previewDetails(AssignShiftPatternDetailsVo assignShiftPatternDetailsVo) {
		// TODO Auto-generated method stub
		return assignShiftsDao.previewDetails(assignShiftPatternDetailsVo);
	}

	@Override
	public List<AssignShiftPatternUpdateVo> getAssignShiftPatternUpdateMasterData(Integer workerId,Integer year,Integer month) {
		// TODO Auto-generated method stub
		return assignShiftsDao.getAssignShiftPatternUpdateMasterData(workerId, year, month);
	}

	@Override
	public Integer saveAssignShiftPatternRecord(AssignShiftPatternUpdateVo assignShiftPatternDetailsVo) {
		// TODO Auto-generated method stub
		return assignShiftsDao.saveAssignShiftPatternRecord(assignShiftPatternDetailsVo);
	}

	@Override
	public List<SimpleObject> getPatternCodesbyPlantId(PlantVo plantVo) {
		// TODO Auto-generated method stub
		return assignShiftsDao.getPatternCodesbyPlantId(plantVo);
	}
	

}
