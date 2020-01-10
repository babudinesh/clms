package com.Ntranga.CLMS.Service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.AssociateWorkOrderDao;
import com.Ntranga.CLMS.vo.AssociateWorkOrderVo;
import com.Ntranga.CLMS.vo.SimpleObject;




@Service("associateWorkOrderService")
public class AssociateWorkOrderServiceImpl implements AssociateWorkOrderService {

	@Autowired
	private  AssociateWorkOrderDao associateWorkOrderDao;

	public List<SimpleObject> getVendorAssociatedWorkOrder(AssociateWorkOrderVo associateWorkOrderVo){
		// TODO Auto-generated method stub
		return associateWorkOrderDao.getVendorAssociatedWorkOrder(associateWorkOrderVo);
	}
	
	@Override
	public List<AssociateWorkOrderVo> getAssociateWorkOrderSearch(AssociateWorkOrderVo associateWorkOrderVo) {
		// TODO Auto-generated method stub
		return associateWorkOrderDao.getAssociateWorkOrderSearch(associateWorkOrderVo);
	}

	@Override
	public AssociateWorkOrderVo getWorkOrderAssociatedList(AssociateWorkOrderVo associateWorkOrderVo) {
		// TODO Auto-generated method stub
		return associateWorkOrderDao.getWorkOrderAssociatedList(associateWorkOrderVo);
	}

	@Override
	public Integer saveAssociateWorkOrder(AssociateWorkOrderVo associateWorkOrderVo) {
		// TODO Auto-generated method stub
		return associateWorkOrderDao.saveAssociateWorkOrder(associateWorkOrderVo);
	}

	@Override
	public List<AssociateWorkOrderVo> getWorkOrderList(AssociateWorkOrderVo associateWorkOrderVo) {
		// TODO Auto-generated method stub
		return associateWorkOrderDao.getWorkOrderList(associateWorkOrderVo);
	}

	@Override
	public Map<String, String> workorderChangeListener(Integer workrorderDetailId) {
		// TODO Auto-generated method stub
		return associateWorkOrderDao.workorderChangeListener(workrorderDetailId);
	}


	
	

}
