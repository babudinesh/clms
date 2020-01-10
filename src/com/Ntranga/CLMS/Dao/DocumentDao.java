package com.Ntranga.CLMS.Dao;

import java.util.List;

import com.Ntranga.CLMS.vo.CompanyDocumentsVo;
import com.Ntranga.CLMS.vo.VendorDocumentsVo;

public interface DocumentDao {

	public List<VendorDocumentsVo> getDocumentsListBySearch(VendorDocumentsVo paramDocuments);

	public Integer saveVendorDocument(VendorDocumentsVo paramDocuments);

	public List<VendorDocumentsVo> getVendorDocumentById(Integer vendorDocumentId);
	
	public List<CompanyDocumentsVo> getCompanyDocumentsListBySearch(CompanyDocumentsVo paramDocuments);

	public Integer saveCompanyDocument(CompanyDocumentsVo paramDocuments);

	public List<CompanyDocumentsVo> getCompanyDocumentById(Integer companyDocumentId);
}
