package com.Ntranga.CLMS.Service;

import java.util.List;

import com.Ntranga.CLMS.vo.SectionDetailsInfoVo;
import com.Ntranga.CLMS.vo.SimpleObject;

public interface SectionService {

	public Integer saveSection(SectionDetailsInfoVo paramArea);	
	
	public SectionDetailsInfoVo  getSectionRecordById(Integer sectionId);
	
	public List<SectionDetailsInfoVo> getSectionListBySearch(SectionDetailsInfoVo paramWorkArea);
	
	public List<SimpleObject> getTransactionDatesListOfHistory(Integer customerId, Integer companyId, Integer sectionId);

}

