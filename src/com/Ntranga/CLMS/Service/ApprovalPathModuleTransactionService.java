package com.Ntranga.CLMS.Service;

import java.util.List;

import com.Ntranga.CLMS.vo.ApplicationApprovalPathVo;
import com.Ntranga.CLMS.vo.ApprovalPathModuleVo;
import com.Ntranga.CLMS.vo.ApprovalPathTransactionVo;
import com.Ntranga.CLMS.vo.SimpleObject;

public interface ApprovalPathModuleTransactionService {

	// For Module start
	public List<ApprovalPathModuleVo> getApprovalPathModules(ApprovalPathModuleVo approvalPathModuleVo);

	public Integer saveOrUpdateApprovalPathModuleDetails(ApprovalPathModuleVo approvalPathModuleVo);

	// For Transaction start
	public List<ApprovalPathTransactionVo> getApprovalPathTransactions(ApprovalPathTransactionVo approvalPathTransactionVo);

	public Integer saveOrUpdateApprovalPathTransactionDetails(ApprovalPathTransactionVo approvalPathTransactionVo);

	public List<SimpleObject> getTransationHistoryDatesList(ApprovalPathTransactionVo approvalPathTransactionVo);

	public ApprovalPathTransactionVo getApprovalPathTransactionByTransactionInfoId(
			ApprovalPathTransactionVo approvalPathTransactionVo);

	// For Application Approval Path
	public List<ApplicationApprovalPathVo> getApplicationApprovalPaths(
			ApplicationApprovalPathVo applicationApprovalPathVo);
	
	public ApplicationApprovalPathVo getApplicationApprovalPathsById(
			ApplicationApprovalPathVo applicationApprovalPathVo);

	public Integer saveOrUpdateApprovalFlowDetails(ApplicationApprovalPathVo applicationApprovalPathVo);
	
	

	
}
