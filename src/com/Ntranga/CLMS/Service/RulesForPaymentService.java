package com.Ntranga.CLMS.Service;

import java.util.List;

import com.Ntranga.CLMS.vo.RulesForPaymentReleaseVo;
import com.Ntranga.CLMS.vo.RulesForPaymentVerificationVo;
import com.Ntranga.CLMS.vo.SimpleObject;

public interface RulesForPaymentService {

	public Integer saveRulesForPayment(RulesForPaymentReleaseVo paramRules);	
	
	public List<RulesForPaymentReleaseVo>  getRulesForPaymentById(Integer customerId, Integer companyId, Integer rulesForPaymentId);
	
	public List<SimpleObject> getTransactionListForRulesForPayment(Integer customerId, Integer companyId, Integer uniqueId);
	
	public List<RulesForPaymentVerificationVo> getCompliances(Integer customerId, Integer companyId);

	public List<SimpleObject> getPaymentRulesDocumentNames(Integer customerId,Integer companyId);
}
