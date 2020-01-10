package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.DocumentDao;
import com.Ntranga.CLMS.vo.CompanyDocumentsVo;
import com.Ntranga.CLMS.vo.VendorDocumentsVo;

@Service("documentService")
public class DocumentServiceImpl implements DocumentService {

	@Autowired
	DocumentDao documentDao;
	
	@Override
	public List<VendorDocumentsVo> getDocumentsListBySearch(VendorDocumentsVo paramDocuments) {
		return documentDao.getDocumentsListBySearch( paramDocuments);
	}

	@Override
	public Integer saveVendorDocument(VendorDocumentsVo paramDocuments) {
		return documentDao.saveVendorDocument(paramDocuments);
	}

	@Override
	public List<VendorDocumentsVo> getVendorDocumentById(Integer vendorDocumentId) {
		return documentDao.getVendorDocumentById(vendorDocumentId);
	}

	@Override
	public List<CompanyDocumentsVo> getCompanyDocumentsListBySearch(CompanyDocumentsVo paramDocuments) {
		return documentDao.getCompanyDocumentsListBySearch(paramDocuments);
	}

	@Override
	public Integer saveCompanyDocument(CompanyDocumentsVo paramDocuments) {
		return documentDao.saveCompanyDocument(paramDocuments);
	}

	@Override
	public List<CompanyDocumentsVo> getCompanyDocumentById(Integer companyDocumentId) {
		return documentDao.getCompanyDocumentById(companyDocumentId);
	}
}
