package com.Ntranga.CLMS.Service;

import java.util.List;

import com.Ntranga.CLMS.vo.CompanyDocumentsVo;
import com.Ntranga.CLMS.vo.VendorDocumentsVo;


public interface DocumentService {

	public List<VendorDocumentsVo> getDocumentsListBySearch(VendorDocumentsVo paramDocuments);
	
	public Integer saveVendorDocument(VendorDocumentsVo paramDocuments);

	public List<VendorDocumentsVo> getVendorDocumentById(Integer vendorDocumentId);
	
	public List<CompanyDocumentsVo> getCompanyDocumentsListBySearch(CompanyDocumentsVo paramDocuments);

	public Integer saveCompanyDocument(CompanyDocumentsVo paramDocuments);

	public List<CompanyDocumentsVo> getCompanyDocumentById(Integer companyDocumentId);
}
