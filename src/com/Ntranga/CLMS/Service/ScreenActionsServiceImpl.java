package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.ScreenActionsDao;
import com.Ntranga.CLMS.vo.ScreenActionsVo;
import com.Ntranga.CLMS.vo.SimpleObject;




@Service("screenActionsService")
public class ScreenActionsServiceImpl implements ScreenActionsService {

	@Autowired
	private  ScreenActionsDao screenActionsDao;

	@Override
	public List<String> getGroupNames() {
		// TODO Auto-generated method stub
		return screenActionsDao.getGroupNames();
	}

	@Override
	public List<ScreenActionsVo> getScreenActionsSearchList(ScreenActionsVo actionVo) {
		// TODO Auto-generated method stub
		return screenActionsDao.getScreenActionsSearchList(actionVo);
	}

	@Override
	public List<SimpleObject> getScreenActionsList() {
		// TODO Auto-generated method stub
		return screenActionsDao.getScreenActionsList();
	}

	@Override
	public Integer saveOrUpdateScreenActionsDetails(ScreenActionsVo actionVo) {
		// TODO Auto-generated method stub
		return screenActionsDao.saveOrUpdateScreenActionsDetails(actionVo);
	}

	@Override
	public ScreenActionsVo getScreenActionsByScreenId(Integer screenActionId) {
		// TODO Auto-generated method stub
		return screenActionsDao.getScreenActionsByScreenId(screenActionId);
	}

}
