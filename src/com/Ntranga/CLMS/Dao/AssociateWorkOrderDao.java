package com.Ntranga.CLMS.Dao;

import java.util.List;
import java.util.Map;

import com.Ntranga.CLMS.vo.AssociateWorkOrderVo;
import com.Ntranga.CLMS.vo.SimpleObject;

public interface AssociateWorkOrderDao {

	public List<SimpleObject> getVendorAssociatedWorkOrder(AssociateWorkOrderVo associateWorkOrderVo) ;
	
	public List<AssociateWorkOrderVo> getAssociateWorkOrderSearch(AssociateWorkOrderVo associateWorkOrderVo);

	public AssociateWorkOrderVo getWorkOrderAssociatedList(AssociateWorkOrderVo associateWorkOrderVo);
	
	public Integer saveAssociateWorkOrder(AssociateWorkOrderVo associateWorkOrderVo);
	
	public List<AssociateWorkOrderVo> getWorkOrderList(AssociateWorkOrderVo associateWorkOrderVo);
	
	public Map<String, String> workorderChangeListener(Integer workrorderDetailId);
}
