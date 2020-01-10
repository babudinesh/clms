package com.Ntranga.CLMS.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Ntranga.CLMS.Service.CommonService;
import com.Ntranga.CLMS.Service.CustomerService;
import com.Ntranga.CLMS.Service.VendorService;
import com.Ntranga.CLMS.Service.WorkerService;
import com.Ntranga.CLMS.vo.CustomerVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.VendorBankDetailsVo;
import com.Ntranga.CLMS.vo.VendorBranchDetailsInfoVo;
import com.Ntranga.CLMS.vo.VendorDetailsVo;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@RestController
@RequestMapping(value = "vendorController")
@SuppressWarnings({"rawtypes","unchecked"})
public class VendorController {
	
	private static Logger log = Logger.getLogger(VendorController.class);
	@Autowired
	private VendorService vendorService;

	@Autowired
	private CommonService commonService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private WorkerService workerService;
	
	
	/*@RequestMapping(value="/VendorDetails", method = RequestMethod.GET)
	public ModelAndView  vendorDetails() throws Exception {			
		ModelAndView mav = new ModelAndView("vendor/VendorDetailsSearch");			
			List<SimpleObject> customerList = vendorService.getCustomerNamesAsDropDown();
			mav.addObject("customerList",customerList);			
			List<SimpleObject> vendorTypeList = vendorService.getVendorTypesList();
			List<SimpleObject> modeOfPaymentList = vendorService.getPaymentModesList();
			List<SimpleObject> paymentFrequencyList = vendorService.getPaymentFrequencyList();
			List<SimpleObject> companyTypeList = commonService.getCompanySectors();
			List<SimpleObject> registrationActsList  = commonService.getRegistrationActs();
			List<IndustryVo> industyList = commonService.getIndustriesList();	
			mav.addObject("industyList",industyList);
			mav.addObject("companyTypeList",companyTypeList);
			mav.addObject("registrationActsList",registrationActsList);
			mav.addObject("vendorTypeList",vendorTypeList);
			mav.addObject("modeOfPaymentList",modeOfPaymentList);
			mav.addObject("paymentFrequencyList",paymentFrequencyList);
			mav.addObject("customerList",customerList);
		return mav;
	}*/
	
	
	
	/*@RequestMapping(value = "/getMasterInfoForVendorDetails.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getMAsterInfoForVendorLocation(@RequestBody String CustomerIdCompanyIdVendorIdJson) {
		Map<String,List> masterInfoMap = new HashMap<String,List>();		
		List<SimpleObject> vendorTypeList = vendorService.getVendorTypesList();
		List<SimpleObject> modeOfPaymentList = vendorService.getPaymentModesList();
		List<SimpleObject> paymentFrequencyList = vendorService.getPaymentFrequencyList();
		List<SimpleObject> companyTypeList = commonService.getCompanySectors();
		List<SimpleObject> registrationActsList  = commonService.getRegistrationActs();
		List<IndustryVo> industyList = commonService.getIndustriesList();		
		masterInfoMap.put("vendorTypeList", vendorTypeList);
		masterInfoMap.put("modeOfPaymentList", modeOfPaymentList);
		masterInfoMap.put("paymentFrequencyList", paymentFrequencyList);
		masterInfoMap.put("companyTypeList", companyTypeList);
		masterInfoMap.put("registrationActsList", registrationActsList);
		masterInfoMap.put("industyList", industyList);				
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
	}*/
	

	@RequestMapping(value = "/getCustomerNamesAsDropDown.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getCustomerNamesAsDropDown(@RequestBody String customerIdJson) {
		Map<String,List> masterInfoMap = new HashMap<String,List>();
		try{
			String customerId = "";
			System.out.println(customerIdJson);
			JsonParser jsonParser = new JsonParser();
			 JsonObject jo = (JsonObject)jsonParser.parse(customerIdJson);  
			if(customerIdJson != null && jo.get("customerId") != null) {
		     
		     customerId =  jo.get("customerId").getAsString();
			}
			List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(customerId);					
			masterInfoMap.put("customerList", customerList);
			
			List<SimpleObject> industryList = commonService.getIndustriesList();
			masterInfoMap.put("industryList", industryList);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
	}
	
	
	
	
	@RequestMapping(value="/VendorGridDetails.json", method = RequestMethod.POST)
	public ResponseEntity<List<VendorDetailsVo>>  vendorGridDetails(@RequestBody String CustomerIdCompanyIdVendorIdJson) throws Exception {		
		System.out.println("CustomerIdCompanyIdVendorIdJson::"+CustomerIdCompanyIdVendorIdJson);
		String customerId = "";
	    String companyId =  "";
	    String vendorId ="";
	    String vendorCode = null;
	    String vendorName = null;
	    Integer industryId = 0;
	    String status = "";
	    List<VendorDetailsVo> vendorGridList = new ArrayList();
	    try{
	    	JsonParser jsonParser = new JsonParser();
	    	JsonObject jo = (JsonObject)jsonParser.parse(CustomerIdCompanyIdVendorIdJson);   
			if(CustomerIdCompanyIdVendorIdJson != null) {
				if(jo.get("customerId") != null ){
					customerId =  jo.get("customerId").getAsString();
				}
				if(jo.get("companyId") != null){
					companyId =  jo.get("companyId").getAsString();
				}
				if(jo.get("vendorCode") != null){
					vendorCode =  jo.get("vendorCode").getAsString();
				}
				if(jo.get("vendorName") != null){
					vendorName =  jo.get("vendorName").getAsString();
				}
				
				if(jo.get("vendorId") != null){
					vendorId =  jo.get("vendorId").getAsString();
				}
				
				
				if(jo.get("status") != null && !jo.get("status").isJsonNull() && !jo.get("status").getAsString().trim().equalsIgnoreCase("null")){
					status =  jo.get("status").getAsString();
				}
			}			
			
			
			vendorGridList = vendorService.getVendorDetailsList(customerId, companyId, vendorCode, vendorName, vendorId, industryId, status);	
	    }catch(Exception e){
			log.error("Error Occured ",e);
		}
	    /*try{
	    	JsonParser jsonParser = new JsonParser();
	    	JsonObject jo = (JsonObject)jsonParser.parse(CustomerIdCompanyIdVendorIdJson);   
			if(CustomerIdCompanyIdVendorIdJson != null && jo.get("customerId") != null  && jo.get("companyId") != null) {
		   
		     customerId =  jo.get("customerId").getAsString();
		     companyId =  jo.get("companyId").getAsString();
		     vendorId =  jo.get("vendorId").getAsString();
			}			
			if(jo.get("industryId") != null && jo.get("industryId").getAsInt()>0){
				industryId =  jo.get("industryId").getAsInt();
			}
			if(jo.get("status") != null && !jo.get("status").isJsonNull() && !jo.get("status").getAsString().trim().equalsIgnoreCase("null")){
				status =  jo.get("status").getAsString();
			}
			vendorGridList = vendorService.getVendorDetailsList(customerId,companyId, vendorId,industryId, status);	
	    }catch(Exception e){
			log.error("Error Occured ",e);
		}*/
		return new ResponseEntity<List<VendorDetailsVo>>(vendorGridList,HttpStatus.OK);
	}
	

	
	@RequestMapping(value = "/getCompanyNamesAsDropDown.json", method = RequestMethod.POST)
	public  ResponseEntity<List<SimpleObject>> getCompanyNamesAsDropDown(@RequestBody String customerandCompanyJsonString) {
		List<SimpleObject> companyNames = new ArrayList();
		try{
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(customerandCompanyJsonString);
			
			String customerId = "";
			String companyId= "";
			if(jo.get("customerId")!= null && !jo.get("customerId").toString().isEmpty()){
				customerId = jo.get("customerId").getAsString();
			}
			if(jo.get("companyId")!= null && !jo.get("companyId").toString().isEmpty()){
				companyId = jo.get("companyId").getAsString();
			}
			
			companyNames  = vendorService.getComapanyNamesAsDropDown(customerId,companyId);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		
		return new ResponseEntity<List<SimpleObject>>(companyNames,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getVendorNamesAsDropDown.json", method = RequestMethod.POST)
	public  ResponseEntity<List<SimpleObject>> getVendorNamesAsDropDown(@RequestBody String customerCompanyVendorJsonString) {
		List<SimpleObject> vendorNames = new ArrayList();
		try{
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(customerCompanyVendorJsonString);		
			
			String customerId = "";
			String companyId= "";
			String vendorId= "";
			if(jo.get("customerId")!= null && !jo.get("customerId").toString().equalsIgnoreCase("null")){
				customerId = jo.get("customerId").getAsString();
			}
			if(jo.get("companyId")!= null && !jo.get("companyId").toString().equalsIgnoreCase("null")){
				companyId = jo.get("companyId").getAsString();
			}
			if(jo.get("vendorId")!= null && !jo.get("vendorId").toString().equalsIgnoreCase("null")){
				vendorId = jo.get("vendorId").getAsString();
			}
			
			 vendorNames  = vendorService.getVendorNamesAsDropDown(customerId, companyId,vendorId);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<SimpleObject>>(vendorNames,HttpStatus.OK);
	}
	
	
	
	/*=============================== Vendor Branch Details START  ====================================*/

	
	@RequestMapping(value = "/getMasterInfoForvendorBranchGrid.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getMasterInfoForCompanyGrid(@RequestBody String customerIdJson) {
		Map<String,List> masterInfoMap = new HashMap<String,List>();
		try{
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(customerIdJson);	
			String customerId = "";
			
			if(jo.get("customerId")!= null && !jo.get("customerId").toString().equalsIgnoreCase("null")){
				customerId = jo.get("customerId").getAsString();
			}
			
			List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(customerId);						
			masterInfoMap.put("customerList", customerList);
			List<SimpleObject> industryList = commonService.getIndustriesList();
			masterInfoMap.put("industryList", industryList);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getvendorBranchGridData.json", method = RequestMethod.POST)
	public  ResponseEntity<List<VendorBranchDetailsInfoVo>> getvendorBranchGridData(@RequestBody String vendorGridData) {
		List<VendorBranchDetailsInfoVo> gridDataList = new ArrayList();
		Integer customerId = 0;
		Integer companyId= 0;
		Integer vendorId = 0;
		String vendorCode= "";
		String vendorName = "";
		String branchCode = "";
		
		try{
			log.info(vendorGridData+"vendorGridDatal");
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(vendorGridData);	
			//log.info(jo.get("industryId").getAsJsonArray().toString()+"  jo.get(industryId).getAsString() ");
			if(jo.get("customerId")!= null && !jo.get("customerId").toString().equalsIgnoreCase("null")){
				customerId = jo.get("customerId").getAsInt();
			}
			
			if(jo.get("companyId")!= null && !jo.get("companyId").toString().equalsIgnoreCase("null")){
				companyId = jo.get("companyId").getAsInt();
			}
			
			if(jo.get("vendorCode")!= null && !jo.get("vendorCode").toString().equalsIgnoreCase("null")){
				vendorCode = jo.get("vendorCode").getAsString();
			}
			
			if(jo.get("vendorName")!= null && !jo.get("vendorName").toString().equalsIgnoreCase("null")){
				vendorName = jo.get("vendorName").getAsString();
			}
			
			if(jo.get("branchCode")!= null && !jo.get("branchCode").toString().equalsIgnoreCase("null")){
				branchCode = jo.get("branchCode").getAsString();
			}
			
			if(jo.get("vendorId")!= null && !jo.get("vendorId").toString().equalsIgnoreCase("null")){
				vendorId = jo.get("vendorId").getAsInt();
			}
			
			
			 gridDataList  = vendorService.getVendorBranchGridList(customerId, companyId,vendorId, vendorCode,vendorName,branchCode);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<VendorBranchDetailsInfoVo>>(gridDataList,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getTransactionHistoryDatesListForVendorBranchDetails.json", method = RequestMethod.POST)
	public  ResponseEntity<List<SimpleObject>> getTransactionHistoryDatesListForVendorBranchDetails(@RequestBody String vendorBranchId) {
		List<SimpleObject> transactionDatesList = new ArrayList();
		try{
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(vendorBranchId);				
			transactionDatesList  = vendorService.getTransactionHistoryDatesListForVendorBranchDetails(jo.get("vendorBranchId").getAsInt());
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<SimpleObject>>(transactionDatesList,HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value = "/getsubIndustryList.json", method = RequestMethod.POST)
	public  ResponseEntity<String> getsubIndustryList(@RequestBody String inudstryIdJson) {
		String subInustryList = "";
		try{
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(inudstryIdJson);
			JsonArray array = jo.get("industryId").getAsJsonArray();
			
			String array1  = array.toString().replaceAll("\\[|\\]", "");
			
			subInustryList  = customerService.getInustrySectorsList(array1);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<String>(subInustryList,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/saveVendorDetails.json", method = RequestMethod.POST)
	public  ResponseEntity<SimpleObject> saveVendorDetails(@RequestBody String vendorJsonJsonString) throws Exception {
		log.info("Entered in Vendor Controller saveVendorDetails() ::  "+vendorJsonJsonString);
		
		SimpleObject object = new SimpleObject();
		try{
			Integer vendorId = null;
			if (vendorJsonJsonString != null)
				vendorId = vendorService.saveVendorDetails(vendorJsonJsonString);
		
			if (vendorId != null) {				
				object.setId(vendorId);		
				object.setName("success");
			} else {
				object.setId(0);		
				object.setName("Error in saving vendor details");
			}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		log.info("Exiting from Customer Controller saveCustomer() ::  "+object);
		return new ResponseEntity<SimpleObject>(object, HttpStatus.OK);
	}
	
	
	
	
	
	@RequestMapping(value = "/getVednorDetailsById.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getVendorDetailsbyId(@RequestBody String vendorInfoIdJson) {
					
		Map<String,List> masterInfoMap = new HashMap<String,List>();	
		
		try{
			List<SimpleObject> vendorTypeList = vendorService.getVendorTypesList();
			List<SimpleObject> modeOfPaymentList = vendorService.getPaymentModesList();
			List<SimpleObject> paymentFrequencyList = vendorService.getPaymentFrequencyList();
			List<SimpleObject> companyTypeList = commonService.getCompanySectors();
			List<SimpleObject> registrationActsList  = commonService.getRegistrationActs();
			List<SimpleObject> industyList = commonService.getIndustriesList();	
			List<SimpleObject> countryList = commonService.getCountriesList();
						
			
			masterInfoMap.put("vendorTypeList", vendorTypeList);
			masterInfoMap.put("modeOfPaymentList", modeOfPaymentList);
			masterInfoMap.put("paymentFrequencyList", paymentFrequencyList);
			masterInfoMap.put("companyTypeList", companyTypeList);
			masterInfoMap.put("registrationActsList", registrationActsList);
			masterInfoMap.put("industyList", industyList);
			masterInfoMap.put("countryList", countryList);
			
			List<VendorDetailsVo> vendorDetailsVoList = new ArrayList<>();
			List<CustomerVo> customerList = new ArrayList();
			JsonParser jsonParser = new JsonParser();
			System.out.println("vendorInfoIdJson::"+vendorInfoIdJson);
			JsonObject jo = (JsonObject) jsonParser.parse(vendorInfoIdJson);
			if(jo.get("vendorInfoId").getAsString() != null && !jo.get("vendorInfoId").getAsString().isEmpty() ){
				vendorDetailsVoList  = vendorService.getVendorDetailsbyId(jo.get("vendorInfoId").getAsString());			
			}						
			if(jo.get("customerId")!= null){
			 customerList = vendorService.getCustomerNamesAsDropDown(jo.get("customerId").getAsString());
			}
			masterInfoMap.put("customerList", customerList);
			masterInfoMap.put("vendorDetailsVoList",  vendorDetailsVoList);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
		
	}
	
	
	@RequestMapping(value = "/getTransactionDatesListForEditingVendorDetails.json", method = RequestMethod.POST)
	public  ResponseEntity<List<SimpleObject>> getTransactionDatesListForEditingVendorDetails(@RequestBody String vendorIdJson) {
		List<SimpleObject> transactionDatesList = new ArrayList();
		try{
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(vendorIdJson);				
			transactionDatesList  = vendorService.getTransactionDatesListForEditingVendorDetails(jo.get("vendorId").getAsInt());
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<SimpleObject>>(transactionDatesList,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getcountryListByCompanyId.json", method = RequestMethod.POST)
	public  ResponseEntity<List<SimpleObject>> getcountryListByCompanyId(@RequestBody String companyId) {
		List<SimpleObject> countriesList = new ArrayList();
		try{
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(companyId);				
			countriesList = commonService.getCompanyCountries(jo.get("companyId").getAsInt());	
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<SimpleObject>>(countriesList,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getVendorBranchDetails.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String, List>> getVendorBranchDetails(@RequestBody String customerCompanyVendorJsonString) {
		Map<String,List> masterInfoMap = new HashMap<String,List>();
		try{
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(customerCompanyVendorJsonString);	
			
			List<SimpleObject> countryList = commonService.getCountriesList();
			masterInfoMap.put("countryList", countryList );
			
			List<VendorBranchDetailsInfoVo> detailsInfoVos = new ArrayList<VendorBranchDetailsInfoVo>();
			VendorBranchDetailsInfoVo branchDetailsInfoVo = new VendorBranchDetailsInfoVo();
			if(jo.get("vendorBranchId").getAsInt()> 0){
				branchDetailsInfoVo  = vendorService.getVendorBranchDetails(jo.get("vendorBranchId").getAsString());
				detailsInfoVos.add(branchDetailsInfoVo);			
			}
			String customerId = "";		
			if(jo.get("customerId")!= null && !jo.get("customerId").toString().equalsIgnoreCase("null")){
				customerId = jo.get("customerId").getAsString();
			}
			List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(customerId);						
			masterInfoMap.put("customerList", customerList);
			
			if(branchDetailsInfoVo != null && branchDetailsInfoVo.getCustomerDetailsId() != null && branchDetailsInfoVo.getCustomerDetailsId() > 0){
				List<SimpleObject> companyList = new ArrayList();			
				companyList  = vendorService.getComapanyNamesAsDropDown(branchDetailsInfoVo.getCustomerDetailsId()+"",branchDetailsInfoVo.getCompanyDetailsId()+"");
				masterInfoMap.put("companyList", companyList);
			}
			if(branchDetailsInfoVo != null && branchDetailsInfoVo.getCompanyDetailsId() != null && branchDetailsInfoVo.getCompanyDetailsId() > 0){
				List<SimpleObject> vendorList = new ArrayList();			
				vendorList  = vendorService.getVendorNamesAsDropDown(branchDetailsInfoVo.getCustomerDetailsId()+"", branchDetailsInfoVo.getCompanyDetailsId()+"",branchDetailsInfoVo.getVendorDetailsId()+"");
				masterInfoMap.put("vendorList", vendorList);
			}
			if(branchDetailsInfoVo != null && branchDetailsInfoVo.getCountry() != null && branchDetailsInfoVo.getCountry() > 0){
				List<SimpleObject> statesList = new ArrayList<SimpleObject>();
				statesList = commonService.getStatesList(branchDetailsInfoVo.getCountry());
				masterInfoMap.put("statesList", statesList);
			}
			
			masterInfoMap.put("vendorBranchDetails", detailsInfoVos );
			List<SimpleObject> skills = commonService.getSkills();
			masterInfoMap.put("skills", skills );
			List<SimpleObject> pfTypes = commonService.getPFTypes();
			masterInfoMap.put("pfTypes", pfTypes );
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		
		return new ResponseEntity<Map<String, List>>(masterInfoMap,HttpStatus.OK);
	}
	
	
	/*=============================== Vendor Bank Details ====================================*/

	@RequestMapping(value="/VendorBankGridDetails.json", method = RequestMethod.POST)
	public ResponseEntity<List<VendorBankDetailsVo>>  vendorBankGridDetails(@RequestBody String CustomerIdCompanyIdVendorIdLocationIdJson) throws Exception {		
		System.out.println("CustomerIdCompanyIdVendorIdJson::"+CustomerIdCompanyIdVendorIdLocationIdJson);
		String customerId = "";
	    String companyId =  "";
	    String vendorCode ="";
	    String vendorName ="";
	    String vendorId = "";
	    //String locationId = "";
	    List<VendorBankDetailsVo> vendorBankGridList = new ArrayList();
	    try{
	    	JsonParser jsonParser = new JsonParser();
	    	JsonObject jo = (JsonObject)jsonParser.parse(CustomerIdCompanyIdVendorIdLocationIdJson);   
	    	if(CustomerIdCompanyIdVendorIdLocationIdJson != null) {
	   
				if(jo.get("customerId") != null){
					 customerId =  jo.get("customerId").getAsString();
				}
				
				if(jo.get("companyId") != null){
					companyId =  jo.get("companyId").getAsString();
				}
				
				if(jo.get("vendorCode") != null){
					vendorCode =  jo.get("vendorCode").getAsString();
				}
				
				if(jo.get("vendorName") != null){
					vendorName =  jo.get("vendorName").getAsString();
				}
				
				if(jo.get("vendorId") != null){
					vendorId =  jo.get("vendorId").getAsString();
				}
			
			}			
			vendorBankGridList = vendorService.VendorBankGridDetails(customerId,companyId, vendorCode, vendorName,vendorId);	
	    }catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<VendorBankDetailsVo>>(vendorBankGridList,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/saveOrUpdateVendorBankDetails.json", method = RequestMethod.POST)
	public  ResponseEntity<Integer> saveOrUpdateVendorBankDetails(@RequestBody VendorBankDetailsVo vendorBankDetails) {	
		Integer branchId = 0;
		try{
			branchId = vendorService.saveOrUpdateVendorBankDetails(vendorBankDetails);		
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Integer>(branchId,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getTransactionDatesListForEditingVendorBankDetails.json", method = RequestMethod.POST)
	public  ResponseEntity<List<SimpleObject>> getTransactionDatesListForEditingVendorBankDetails(@RequestBody String uniqueId) {
		List<SimpleObject> transactionDatesList = new ArrayList();
		try{
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(uniqueId);				
			transactionDatesList  = vendorService.getTransactionDatesListForEditingVendorBankDetails(jo.get("uniqueId").getAsInt());
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<SimpleObject>>(transactionDatesList,HttpStatus.OK);
	}
	
	
	
	
	@RequestMapping(value = "/getVendorBankDetailsbyId.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getVendorBankDetailsbyId(@RequestBody String vendorBankIdJson) {
					
		Map<String,List> masterInfoMap = new HashMap<String,List>();					
		
		try{
			List<VendorBankDetailsVo> vendorDetailsBank = new ArrayList<>();
			List<CustomerVo> customerList = new ArrayList();
			JsonParser jsonParser = new JsonParser();
			System.out.println("vendorInfoIdJson::"+vendorBankIdJson);
			JsonObject jo = (JsonObject) jsonParser.parse(vendorBankIdJson);
			if( jo.get("vendorBankId")!= null && jo.get("vendorBankId").toString() != null && !jo.get("vendorBankId").getAsString().isEmpty() ){
				vendorDetailsBank  = vendorService.getVendorBankDetailsbyId(jo.get("vendorBankId").getAsString());			
			}						
			if(jo.get("customerId")!= null){
			 customerList = vendorService.getCustomerNamesAsDropDown(jo.get("customerId").getAsString());
			}
			List<SimpleObject> countryList =   commonService.getCountriesList();
			masterInfoMap.put("countryList", countryList);
			masterInfoMap.put("customerList", customerList);
			masterInfoMap.put("vendorDetailsBank",  vendorDetailsBank);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
				
		//return new ResponseEntity<VendorDetailsVo>(vendorDetailsVo,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/validateVendorBankCode.json", method = RequestMethod.POST)
	public  boolean validateVendorBankCode(@RequestBody VendorBankDetailsVo paramVendor) {		
		boolean flag = vendorService.validateVendorBankCode(paramVendor);
		return flag;
	}
	
	@RequestMapping(value = "/getVendorAndLocationNamesAsDropDowns.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getLocationNamesAsDropDown(@RequestBody String customerCompanyJsonString) {		
		Map<String,List> masterInfoMap = new HashMap<String,List>();
		try{
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(customerCompanyJsonString);
			System.out.println(customerCompanyJsonString);	
			Integer customerId = 0;
			Integer companyId = 0;
			String vendorId = "";
			String locationId = null;
			if (jo.get("customerId") != null && !jo.get("customerId").toString().equalsIgnoreCase("null")) {
				customerId = jo.get("customerId").getAsInt();
			}
	
			if (jo.get("companyId") != null && !jo.get("companyId").toString().equalsIgnoreCase("null")) {
				companyId = jo.get("companyId").getAsInt();
			}
	
			if (jo.get("vendorId") != null && !jo.get("vendorId").toString().equalsIgnoreCase("null") && !jo.get("vendorId").toString().isEmpty()) {
				vendorId = jo.get("vendorId").getAsString();
			}
			if(jo.has("locationId") && jo.get("locationId") != null &&  !jo.get("locationId").isJsonNull() && 			  	 	jo.get("locationId").getAsJsonArray().size() > 0 ){
				JsonArray locId = jo.get("locationId").getAsJsonArray();
				locationId = locId.toString().replace("]", "").replace("[", "");
			}
			
			List<SimpleObject> vendorNames  = workerService.getVendorNamesAsDropDown(customerId+"", companyId+"",vendorId,"Validated",locationId);
			List<SimpleObject> locationNames  = vendorService.getLocationNamesAsDropDown(customerId+"", companyId+"");
			List<SimpleObject> countriesList = commonService.getCompanyCountries(companyId);
			masterInfoMap.put("countriesList", countriesList);
			masterInfoMap.put("vendorList", vendorNames);
			masterInfoMap.put("locationList", locationNames);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/validateVendorCode.json", method = RequestMethod.POST)
	public  boolean validateVendorCode(@RequestBody VendorDetailsVo paramVendor) {		
		boolean flag = vendorService.validateVendorCode(paramVendor);
		return flag;
	}
	
	/*=============================== Vendor Branch Details Start  ====================================*/
	@RequestMapping(value = "/saveOrUpdateVendorBranchDetails.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,Integer>> saveOrUpdateVendorBranchDetails(@RequestBody VendorBranchDetailsInfoVo vendorBranchDetailsInfoVo) {				
		Integer branchId = vendorService.saveOrUpdateVendorBranchDetails(vendorBranchDetailsInfoVo);
		Map<String,Integer> masterInfoMap = new HashMap<String,Integer>();
		masterInfoMap.put("branchId", branchId);
		return new ResponseEntity<Map<String,Integer>>(masterInfoMap,HttpStatus.OK);
	}
	
	/*=============================== Vendor Branch Details END ====================================*/
}
