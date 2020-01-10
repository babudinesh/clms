package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.ApprovalPathModuleTransactionDao;
import com.Ntranga.CLMS.vo.ApplicationApprovalPathVo;
import com.Ntranga.CLMS.vo.ApprovalPathModuleVo;
import com.Ntranga.CLMS.vo.ApprovalPathTransactionVo;
import com.Ntranga.CLMS.vo.SimpleObject;




@Service("approvalPathModuleTransactionService")
public class ApprovalPathModuleTransactionServiceImpl implements ApprovalPathModuleTransactionService {

	@Autowired
	private  ApprovalPathModuleTransactionDao approvalPathModuleTransactionDao;

	@Override
	public List<ApprovalPathModuleVo> getApprovalPathModules(ApprovalPathModuleVo approvalPathModuleVo) {
		// TODO Auto-generated method stub
		return approvalPathModuleTransactionDao.getApprovalPathModules(approvalPathModuleVo);
	}

	@Override
	public Integer saveOrUpdateApprovalPathModuleDetails(ApprovalPathModuleVo approvalPathModuleVo) {
		// TODO Auto-generated method stub
		return approvalPathModuleTransactionDao.saveOrUpdateApprovalPathModuleDetails(approvalPathModuleVo);
	}

	@Override
	public List<ApprovalPathTransactionVo> getApprovalPathTransactions(
			ApprovalPathTransactionVo approvalPathTransactionVo) {
		// TODO Auto-generated method stub
		return approvalPathModuleTransactionDao.getApprovalPathTransactions(approvalPathTransactionVo);
	}

	@Override
	public Integer saveOrUpdateApprovalPathTransactionDetails(ApprovalPathTransactionVo approvalPathTransactionVo) {
		// TODO Auto-generated method stub
		return approvalPathModuleTransactionDao.saveOrUpdateApprovalPathTransactionDetails(approvalPathTransactionVo);
	}

	@Override
	public List<SimpleObject> getTransationHistoryDatesList(ApprovalPathTransactionVo approvalPathTransactionVo) {
		// TODO Auto-generated method stub
		return approvalPathModuleTransactionDao.getTransationHistoryDatesList(approvalPathTransactionVo);
	}

	@Override
	public ApprovalPathTransactionVo getApprovalPathTransactionByTransactionInfoId(
			ApprovalPathTransactionVo approvalPathTransactionVo) {
		// TODO Auto-generated method stub
		return approvalPathModuleTransactionDao.getApprovalPathTransactionByTransactionInfoId(approvalPathTransactionVo);
	}

	@Override
	public List<ApplicationApprovalPathVo> getApplicationApprovalPaths(
			ApplicationApprovalPathVo applicationApprovalPathVo) {
		// TODO Auto-generated method stub
		return approvalPathModuleTransactionDao.getApplicationApprovalPaths(applicationApprovalPathVo);
	}

	@Override
	public ApplicationApprovalPathVo getApplicationApprovalPathsById(
			ApplicationApprovalPathVo applicationApprovalPathVo) {
		// TODO Auto-generated method stub
		return approvalPathModuleTransactionDao.getApplicationApprovalPathsById(applicationApprovalPathVo);
	}

	@Override
	public Integer saveOrUpdateApprovalFlowDetails(ApplicationApprovalPathVo applicationApprovalPathVo) {
		// TODO Auto-generated method stub
		return approvalPathModuleTransactionDao.saveOrUpdateApprovalFlowDetails(applicationApprovalPathVo);
	}

	
}
