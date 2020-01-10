package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.DefineComplianceTypesDao;
import com.Ntranga.CLMS.vo.DefineComplianceTypesVo;
import com.Ntranga.CLMS.vo.SimpleObject;



@Service("defineComplianceTypesService")
public class DefineComplianceTypesServiceImpl implements DefineComplianceTypesService {

	@Autowired
	private  DefineComplianceTypesDao defineComplianceTypesDao;

	@Override
	public List<DefineComplianceTypesVo> getComplianceList(Integer customerId, Integer companyId,Integer locationId) {
		// TODO Auto-generated method stub
		return defineComplianceTypesDao.getComplianceList(customerId, companyId, locationId);
	}

	@Override
	public Integer saveCompliance(DefineComplianceTypesVo defineComplianceTypesVo) {
		// TODO Auto-generated method stub
		return defineComplianceTypesDao.saveCompliance(defineComplianceTypesVo);
	}

	@Override
	public List<SimpleObject> getTransactionDatesofHistory(Integer customerId, Integer companyId) {
		// TODO Auto-generated method stub
		return defineComplianceTypesDao.getTransactionDatesofHistory(customerId, companyId);
	}

	@Override
	public List<DefineComplianceTypesVo> getComplianceRecordById(Integer defineComplianceTypeId) {
		// TODO Auto-generated method stub
		return defineComplianceTypesDao.getComplianceRecordById(defineComplianceTypeId);
	}

	@Override
	public List<DefineComplianceTypesVo> getComplianceListByApplicable(Integer customerId, Integer companyId, String applicableTo) {
		return defineComplianceTypesDao.getComplianceListByApplicable(customerId, companyId, applicableTo);
	}
	
	

}
