package com.Ntranga.CLMS.Dao;

import java.util.List;

import com.Ntranga.CLMS.vo.InvoiceDetailsVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.VendorDetailsVo;

public interface InvoiceDao {

	public Integer saveInvoice(InvoiceDetailsVo paramInvoice);

	public List<InvoiceDetailsVo> getInvoicesListBySearch(InvoiceDetailsVo paramInvoice);

	public List<InvoiceDetailsVo> getInvoiceById(Integer invoiceId);

	public List<SimpleObject> getCompanyAddressByCompanyId(Integer customerId,Integer companyId);

	public List<SimpleObject> getCompanyContactByAddressId(Integer addressId);
	
	public List<VendorDetailsVo> getVendorAddressContactByVendorId(Integer customerId,Integer companyId, Integer vendorId);
	
}
