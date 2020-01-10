package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.InvoiceDao;
import com.Ntranga.CLMS.vo.InvoiceDetailsVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.VendorDetailsVo;

@Service("invoiceService")
public class InvoiceServiceImpl implements InvoiceService {

	@Autowired
	InvoiceDao invoiceDao;
	
	
	@Override
	public Integer saveInvoice(InvoiceDetailsVo paramInvoice) {
		return invoiceDao.saveInvoice(paramInvoice);
	}

	@Override
	public List<InvoiceDetailsVo> getInvoicesListBySearch(InvoiceDetailsVo paramInvoice) {
		return invoiceDao.getInvoicesListBySearch(paramInvoice);
	}

	@Override
	public List<InvoiceDetailsVo> getInvoiceById(Integer invoiceId) {
		return invoiceDao.getInvoiceById(invoiceId);
	}

	@Override
	public List<SimpleObject> getCompanyAddressByCompanyId(Integer customerId, Integer companyId) {
		return invoiceDao.getCompanyAddressByCompanyId(customerId, companyId);
	}

	@Override
	public List<SimpleObject> getCompanyContactByAddressId(Integer addressId) {
		return invoiceDao.getCompanyContactByAddressId(addressId);
	}
	
	@Override
	public List<VendorDetailsVo> getVendorAddressContactByVendorId(Integer customerId, Integer companyId, Integer vendorId) {
		return invoiceDao.getVendorAddressContactByVendorId(customerId, companyId, vendorId);
	}

}
