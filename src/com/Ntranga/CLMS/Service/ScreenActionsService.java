package com.Ntranga.CLMS.Service;

import java.util.List;

import com.Ntranga.CLMS.vo.ScreenActionsVo;
import com.Ntranga.CLMS.vo.SimpleObject;

public interface ScreenActionsService {

	public List<String> getGroupNames();

	public List<ScreenActionsVo> getScreenActionsSearchList(ScreenActionsVo actionVo);

	public List<SimpleObject> getScreenActionsList();

	public Integer saveOrUpdateScreenActionsDetails(ScreenActionsVo actionVo);

	public ScreenActionsVo getScreenActionsByScreenId(Integer screenActionId);

	
}
