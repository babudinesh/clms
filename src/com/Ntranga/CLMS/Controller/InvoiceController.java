package com.Ntranga.CLMS.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Ntranga.CLMS.Service.CompanyService;
import com.Ntranga.CLMS.Service.EmployeeService;
import com.Ntranga.CLMS.Service.InvoiceService;
import com.Ntranga.CLMS.Service.LocationService;
import com.Ntranga.CLMS.Service.VendorService;
import com.Ntranga.CLMS.Service.WorkerService;
import com.Ntranga.CLMS.vo.CompanyProfileVo;
import com.Ntranga.CLMS.vo.CustomerVo;
import com.Ntranga.CLMS.vo.InvoiceDetailsVo;
import com.Ntranga.CLMS.vo.LocationVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.VendorDetailsVo;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import common.Logger;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping(value="invoiceController")
public class InvoiceController {

	
	private static final Logger log = Logger.getLogger(InvoiceController.class);
	
	@Autowired
	InvoiceService invoiceService;
	
	@Autowired
	VendorService vendorService;
	
	@Autowired 
	CompanyService companyService;
	
	@Autowired
	WorkerService workerService;
	
	@Autowired
	LocationService locationService;
	
	@Autowired
	EmployeeService employeeService;
	

	/*
	 * This method will be used to save or update the invoice record 
	*/ 
	@RequestMapping(value = "/saveInvoice.json", method = RequestMethod.POST)
	public ResponseEntity<SimpleObject> saveInvoiceWithoutFiles(@RequestBody InvoiceDetailsVo  paramInvoice) {
		log.info("Entered in Invoice Controller saveInvoice() ::  "+paramInvoice);
		
		Integer invoiceId = null;
		SimpleObject object = new SimpleObject();
		
		try{
		
			if(paramInvoice != null){
				invoiceId = invoiceService.saveInvoice(paramInvoice);
				log.debug("Invoice ID: "+invoiceId);
			}
			
			if (invoiceId != null) {
				object.setId(invoiceId);
				object.setName("Success");
			} else {
				object.setId(0);
				object.setName("Fail");
			}
		
		}catch(Exception e){
			object.setId(-1);
			log.error("Error Occured ",e);
		}
		log.info("Exiting from Invoice Controller saveInvoice() ::  "+object.toString());
		return new ResponseEntity<SimpleObject>(object, HttpStatus.OK);
	}
	

	/*
	 * This method will be used to get the invoice record based on given invoice number, from date and to date
	 * 																					
	 */
	@RequestMapping(value = "/getInvoicesListBySearch.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getInvoicesListBySearch(@RequestBody InvoiceDetailsVo paramInvoice) throws JSONException {
		log.info("Entered in Invoice Controller getInvoicesListBySearch()   ::   "+paramInvoice);
		Map<String,List> returnData = new HashMap<String,List>();
		try{
		List<InvoiceDetailsVo> invoiceList = invoiceService.getInvoicesListBySearch(paramInvoice);
		log.debug("List of Invoices : "+invoiceList);
		
		returnData.put("invoiceList",invoiceList);
		
		log.info("Exiting from Invoice Controller getInvoicesListBySearch() ::  "+returnData.toString());
		
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(returnData,HttpStatus.OK);
	}
	
	/*
	 * This method will be used to get the invoice record based on invoice Id
	 */	
	@RequestMapping(value = "/getInvoiceById.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getInvoiceById(@RequestBody String paramInvoice) throws JSONException {
		log.info("Entered in Invoice Controller getInvoiceById()  ::   "+paramInvoice);
		Map<String,List> returnList = new HashMap<String,List>();
		try{
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(paramInvoice);
			log.debug("JSON Object:  "+jo);
			String locationId = null;
			String invoiceId = null, customerId = null, vendorId= null, companyId = null;
			if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null){
				customerId = jo.get("customerId").getAsString(); 
				log.debug("Customer Id : "+customerId);
			}
				
				
			if(jo.get("invoiceId") != null && jo.get("invoiceId").getAsString() != null){
				invoiceId = jo.get("invoiceId").getAsString();  
				log.debug("Invoice Id : "+invoiceId);
			}
							
			if(jo.get("vendorId") != null && jo.get("vendorId").getAsString() != null){
				vendorId = jo.get("vendorId").getAsString(); 
				log.debug("Vendor Id : "+vendorId);
			}
				
			if(jo.get("companyId") != null && jo.get("companyId").getAsString() != null){
				companyId = jo.get("companyId").getAsString(); 
				log.debug("Company Id : "+companyId);
			}	 
			if(jo.has("locationId") && jo.get("locationId") != null &&  !jo.get("locationId").isJsonNull() && jo.get("locationId").getAsJsonArray().size() > 0 ){
				JsonArray locId = jo.get("locationId").getAsJsonArray();
				locationId = locId.toString().replace("]", "").replace("[", "");
			}
		    List<InvoiceDetailsVo> invoiceVo= invoiceService.getInvoiceById(Integer.valueOf(invoiceId.trim()));	
		    List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(customerId);
		    List<SimpleObject> companyList  = vendorService.getComapanyNamesAsDropDown(customerId, companyId);		
		    List<SimpleObject> vendorList  = workerService.getVendorNamesAsDropDown(customerId,companyId, vendorId, "Validated",locationId);
		    
		    CompanyProfileVo profileVo = companyService.getCompanyProfileByCompanyId(companyId, customerId);
		    List<CompanyProfileVo> profileList = new ArrayList<>();
		    profileList.add(profileVo);
		    
		    LocationVo paramLocation = new LocationVo();
	    	paramLocation.setCustomerId(Integer.parseInt(customerId.trim()));
	    	paramLocation.setCompanyId(Integer.parseInt(companyId.trim()));
	    	
		    List<LocationVo> locationsList = locationService.getLocationsListBySearch(paramLocation);
		    List<SimpleObject> departmentsList = employeeService.getDepartmentListByCompanyId(Integer.parseInt(customerId.trim()), Integer.parseInt(companyId.trim()) );
		    
		    //company Address and vendor Address
		    List<SimpleObject> companyAddress = invoiceService.getCompanyAddressByCompanyId(Integer.parseInt(customerId.trim()), Integer.parseInt(companyId.trim()));
		    if(companyAddress != null && companyAddress.size() > 0){
		    	List<SimpleObject> companyContact = invoiceService.getCompanyContactByAddressId(companyAddress.get(0).getId());
		    	returnList.put("companyContact", companyContact);
		    }
		    
		    if(vendorId != null && Integer.parseInt(vendorId) > 0){
			    List<VendorDetailsVo> vendorAddressContact = invoiceService.getVendorAddressContactByVendorId(Integer.parseInt(customerId.trim()), Integer.parseInt(companyId.trim()), Integer.parseInt(vendorId.trim()));
			    returnList.put("vendorAddressContact", vendorAddressContact);
		    }
		    
			returnList.put("invoiceVo",invoiceVo);
			returnList.put("customerList", customerList);
			returnList.put("companyList", companyList);
			returnList.put("vendorList", vendorList);
			returnList.put("locationsList", locationsList);
			returnList.put("departmentsList", departmentsList);
			returnList.put("defaultCurrency", profileList);
			returnList.put("companyAddress", companyAddress);
			
			
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		
		log.info("Exiting from Invoice Controller getInvoiceById() ::  "+returnList);
		return new ResponseEntity<Map<String,List>>(returnList, HttpStatus.OK);
	}

}
