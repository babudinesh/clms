package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.RulesForPaymentDao;
import com.Ntranga.CLMS.vo.RulesForPaymentReleaseVo;
import com.Ntranga.CLMS.vo.RulesForPaymentVerificationVo;
import com.Ntranga.CLMS.vo.SimpleObject;

@Service("RulesForPaymentService")
public class RulesForPaymentServiceImpl implements RulesForPaymentService {

	@Autowired
	RulesForPaymentDao rulesForPaymentDao;
	
	
	@Override
	public Integer saveRulesForPayment(RulesForPaymentReleaseVo paramRules) {
		return rulesForPaymentDao.saveRulesForPayment(paramRules);
	}

	@Override
	public List<RulesForPaymentReleaseVo> getRulesForPaymentById(Integer customerId, Integer companyId, Integer rulesForPaymentId) {
		return rulesForPaymentDao.getRulesForPaymentById(customerId, companyId, rulesForPaymentId);
	}

	@Override
	public List<SimpleObject> getTransactionListForRulesForPayment(Integer customerId, Integer companyId, Integer uniqueId) {
		return rulesForPaymentDao.getTransactionListForRulesForPayment(customerId, companyId, uniqueId);
	}

	@Override
	public List<RulesForPaymentVerificationVo> getCompliances(Integer customerId, Integer companyId) {
		return rulesForPaymentDao.getCompliances(customerId, companyId);
	}

	@Override
	public List<SimpleObject> getPaymentRulesDocumentNames(Integer customerId, Integer companyId) {
		return rulesForPaymentDao.getPaymentRulesDocumentNames(customerId, companyId);
	}

}
