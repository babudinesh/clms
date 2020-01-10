package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.SectionDao;
import com.Ntranga.CLMS.vo.SectionDetailsInfoVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.WorkAreaVo;

@Service("sectionService")
public class SectionServiceImpl implements SectionService {

	@Autowired
	private  SectionDao sectionDao;
	
	@Override
	public Integer saveSection(SectionDetailsInfoVo paramArea) {
		return sectionDao.saveSection(paramArea);
	}

	@Override
	public SectionDetailsInfoVo getSectionRecordById(Integer sectionId) {
		return sectionDao.getSectionRecordById(sectionId);
	}

	@Override
	public List<SectionDetailsInfoVo> getSectionListBySearch(SectionDetailsInfoVo paramWorkArea) {
		return sectionDao.getSectionListBySearch(paramWorkArea);
	}

	@Override
	public List<SimpleObject> getTransactionDatesListOfHistory(Integer customerId, Integer companyId, Integer sectionId) {
		return sectionDao.getTransactionDatesListOfHistory(customerId, companyId, sectionId);
	}

}
