package com.Ntranga.CLMS.Controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Ntranga.CLMS.Service.CommonService;
import com.Ntranga.CLMS.Service.CompanyService;
import com.Ntranga.CLMS.Service.CustomerService;
import com.Ntranga.CLMS.Service.EmployeeService;
import com.Ntranga.CLMS.Service.VendorService;
import com.Ntranga.CLMS.vo.AddressVo;
import com.Ntranga.CLMS.vo.CompanyDetailsInfoVo;
import com.Ntranga.CLMS.vo.CompanyDetailsVo;
import com.Ntranga.CLMS.vo.CompanyProfileVo;
import com.Ntranga.CLMS.vo.ContactVo;
import com.Ntranga.CLMS.vo.CountryVo;
import com.Ntranga.CLMS.vo.CustomerVo;
import com.Ntranga.CLMS.vo.DivisionVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.WorkorderDetailInfoVo;
import com.Ntranga.core.StringHelper;
import com.Ntranga.core.CLMS.entities.CompanyAddress;
import com.Ntranga.core.CLMS.entities.CompanyContact;
import com.Ntranga.core.CLMS.entities.CompanyDetails;
import com.Ntranga.core.CLMS.entities.CustomerDetails;
import com.Ntranga.core.CLMS.entities.MAddressType;
import com.Ntranga.core.CLMS.entities.MContactType;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



@RestController
@RequestMapping(value = "CompanyController")
public class CompanyController {

	private static Logger log = Logger.getLogger(CompanyController.class);
	
	@Autowired
	private CompanyService companyService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CommonService commonService;
	
	@Autowired
	private VendorService vendorService;
	
	@Autowired
	private EmployeeService employeeService;
	 /**********************  Company Address  start  **************************/
	
	/*
	 * This method will be used to load the contact page 
	 */
	/*
	@InitBinder
	public void initBinder(WebDataBinder binder) {	
		System.out.println("Hi....");
		// binder.registerCustomEditor(Active.class, new EnumEditor(Active.class));
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}*/
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getAddressMasterInfo.json", method = RequestMethod.GET)
	public ResponseEntity<Map<String, List>> getAddressMasterInfo(HttpServletRequest request) {
		Map<String, List> map = new HashMap<String, List>();	
		try {
			List<SimpleObject> addressTypes = commonService.getAddressTypeList();
			log.debug("List of Address Types:  "+addressTypes);			
			
			List<SimpleObject> countryList = commonService.getCountriesList();
			log.debug("List of countries : "+countryList);
			
			map.put("addressTypes", addressTypes);
			map.put("countryList", countryList );
		} catch (Exception e) {
			log.error("Error Occured. ",e);			
		}
		
		return new ResponseEntity<Map<String, List>>(map, HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getContactMasterInfo.json", method = RequestMethod.GET)
	public ResponseEntity<Map<String, List>> getCompanyMasterInfo(HttpServletRequest request) {
		Map<String, List> map = new HashMap<String, List>(); 				
		try {
			List<SimpleObject> contactTypes = commonService.getContactTypeList();
			log.debug("Type of Contacts : "+contactTypes);
			
			List<SimpleObject> countryList = commonService.getCountriesList();
			log.debug("List of countries : "+countryList);
			
			map.put("contactTypes", contactTypes);			
			map.put("countryList", countryList );
		} catch (Exception e) {
			log.error("Error Occured. ",e);			
		}
		
		return new ResponseEntity<Map<String, List>>(map, HttpStatus.OK);
	}
	
	 
	/*
	 * This method will be used to save or update the company address details
	 */
	@RequestMapping(value = "/saveCompanyAddress.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Integer>>  saveCompanyAddress(@RequestBody AddressVo companyAddressVo) {
		log.info("Entered in Company Controller saveCompanyAddress()  ::  "+companyAddressVo);
		Map<String, Integer> map = new HashMap<String, Integer>();	
		try{
		CompanyAddress companyAddress = new CompanyAddress();
		companyAddress.setCompanyAddressId(companyAddressVo.getAddressId());
		companyAddress.setAddress1(companyAddressVo.getAddress1());
		companyAddress.setAddress2(companyAddressVo.getAddress2());
		companyAddress.setAddress3(companyAddressVo.getAddress3());
		companyAddress.setIsActive(companyAddressVo.getIsActive()+"");
		companyAddress.setCountry(companyAddressVo.getCountryId());
		companyAddress.setState(companyAddressVo.getStateId());
		companyAddress.setCity(companyAddressVo.getCityId());
		companyAddress.setPincode(Integer.valueOf(companyAddressVo.getPincode()));
		companyAddress.setMAddressType(new MAddressType(companyAddressVo.getAddressTypeId()));
		companyAddress.setCustomerDetails(new CustomerDetails(companyAddressVo.getCustomerDetailsId()));
		companyAddress.setCompanyDetails(new CompanyDetails(companyAddressVo.getCompanyDetailsId()));
		companyAddress.setCreatedBy(companyAddressVo.getCreatedBy());
		companyAddress.setModifiedBy(companyAddressVo.getModifiedBy());
		companyAddress.setCreatedDate(new Date());
		companyAddress.setModifiedDate(new Date());
		companyAddress.setAddressId(companyAddressVo.getAddressUniqueId());
		log.debug("Transaction Date : "+companyAddressVo.getTransactionDate());
		if(companyAddressVo.getTransactionDate() != null){
			companyAddress.setTransactionDate(companyAddressVo.getTransactionDate());
		}else{
			companyAddress.setTransactionDate(new Date());
		}
		Integer companyAddressId = null;
		if (companyAddress != null){
			companyAddressId = companyService.saveCompanyAddress(companyAddress);
			log.debug("Company Address Id: "+companyAddressId);
		}		
		map.put("companyAddressId", companyAddressId);	
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
						
		return new ResponseEntity<Map<String, Integer>>(map, HttpStatus.OK);
	}
	
	/*
	 * This method will be used to get company address record by given companyAddressId
	 */
	@RequestMapping(value = "/getCompanyAddressRecordById.json", method = RequestMethod.POST)
	public ResponseEntity<AddressVo> getCompanyAddressRecordById(@RequestBody String companyAddressId) throws JSONException {
		log.info("Entered in Company Controller getCompanyAddressRecordById() ::  "+companyAddressId);
		 AddressVo addressVo = new AddressVo();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(companyAddressId);
		log.debug("JSON Object : "+jo);
		
		String addressId = null;
		if(jo.get("companyAddressId") != null && jo.get("companyAddressId").getAsString() != null){
			addressId = jo.get("companyAddressId").getAsString();
			log.debug("Company Address Id : "+addressId);
		}
		
	    addressVo= companyService.getCompanyAddressRecordById(Integer.valueOf(addressId.trim()));
		log.info("Exiting from Company Controller getCompanyAddressRecordById() ::  "+addressVo);	
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<AddressVo>(addressVo, HttpStatus.OK);
	}
	
	/*
	 * This method will be used to get company address record list based on given customerId and companyId
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getCompanyContactAndAddressGridList.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getCompanyAddressJsonList(@RequestBody String companyAddress) throws JSONException {
		log.info("Entered in Company Controller getCompanyAddressJsonList() ::  "+companyAddress);
		Map<String,List> map = new HashMap<String,List>();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(companyAddress);
		log.debug("JSON Object "+jo);
		
		String customerId = "0", companyId = "0";
		if(jo.get("customerId") != null && !jo.get("customerId").toString().equalsIgnoreCase("null")){
			customerId = jo.get("customerId").getAsString(); 
			log.debug("Customer Id : "+customerId);
		}
		
		if(jo.get("companyId") != null && !jo.get("companyId").toString().equalsIgnoreCase("null")){
			companyId = jo.get("companyId").getAsString(); 
			log.debug("Company Id : "+companyId);
		}
		
		List<AddressVo> addressList = companyService.getCompanyAddressList(Integer.valueOf(customerId), Integer.valueOf(companyId));
		map.put("addressList", addressList);
		List<ContactVo> contactList = companyService.getCompanyContactsList(Integer.valueOf(customerId.trim()),Integer.valueOf(companyId.trim()) );
		map.put("contactList", contactList);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return  new ResponseEntity<Map<String,List>>(map, HttpStatus.OK);
	}
	
	
	/*
	 * This method will be used to get transaction dates list of address records based 
	 * 																	on given customerId, companyId and addressUniqueId
	 */
	@RequestMapping(value = "/getTransactionListForCompanyAddress.json", method = RequestMethod.POST)
	public  ResponseEntity<List<SimpleObject>> getTransactionListForAddress(@RequestBody String customerAddress) throws JSONException {
		log.info("Entered in Company Controller getTransactionListForAddress() ::  "+customerAddress);
		List<SimpleObject> simpleObjects = new ArrayList();
	try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(customerAddress);
		log.debug("JSON Object "+jo);
		
		String customerId = null , addressUniqueId = null, companyId = null;
		if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null 
				&& jo.get("companyId") != null && jo.get("companyId").getAsString() != null
				&& jo.get("addressUniqueId") != null && jo.get("addressUniqueId").getAsString() != null ){
			customerId = jo.get("customerId").getAsString(); 
			addressUniqueId = jo.get("addressUniqueId").getAsString(); 
			companyId = jo.get("companyId").getAsString();
			
			log.debug("Customer Id: "+customerId+ "\t Company Id:  "+companyId+ "\t Address Unique Id : "+addressUniqueId);
		}
		 simpleObjects = companyService.getTransactionListForAddress(Integer.valueOf(customerId), Integer.valueOf(companyId), Integer.valueOf(addressUniqueId));	
		log.info("Exiting from Company Controller getTransactionListForAddress() ::  "+simpleObjects.size());
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<SimpleObject>>(simpleObjects, HttpStatus.OK);
	}
	
	  /**********************  Company Address  End  **************************/
	
	
	

	 /**********************  Company Contact  start  **************************/
	/*
	 * This method will be used to save or update the company contact details
	 */
	@RequestMapping(value = "/saveCompanyContact.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Integer>> saveCompanyContact(@RequestBody ContactVo contact) {
		log.info("Entered in Company Controller saveCompanyContact() ::  "+contact);
		Map<String, Integer> map = new HashMap<String, Integer>();	
		try{
		CompanyContact company = new CompanyContact();
		company.setCustomerDetails(new CustomerDetails(contact.getCustomerId()));
		company.setCompanyDetails(new CompanyDetails(contact.getCompanyId()));
    	//company.setCompanyAddress(new CompanyAddress(contact.getAddressId()));

    	company.setMContactType(new MContactType(contact.getContactTypeId()));
    	company.setCompanyAddressId(contact.getAddressId());
    	company.setCompanyContactId(contact.getContactId());
    	company.setCompanyContactUniqueId(contact.getContactUniqueId() == null ? 0 : contact.getContactUniqueId());
    	company.setContactName(contact.getContactName());
    	company.setContactNameOtherLanguage(contact.getContactNameOtherLanguage());
    	company.setTransactionDate(contact.getTransactionDate());
    	company.setEmailId(contact.getEmailId());
    	company.setBusinessPhoneNumber(contact.getBusinessPhoneNumber());
    	company.setExtensionNumber(contact.getExtensionNumber());
    	company.setIsActive(contact.getIsActive()+"");
    	company.setMobileNumber(contact.getMobileNumber());
    	company.setCreatedBy(contact.getCreatedBy());
    	company.setModifiedBy(contact.getModifiedBy());
    	company.setCreatedDate(new Date());
    	company.setModifiedDate(new Date());
		log.debug("Transaction Date : "+contact.getTransactionDate());
		if(contact.getTransactionDate() != null){
			company.setTransactionDate(contact.getTransactionDate());
		}else{
			company.setTransactionDate(new Date());
		}
		Integer contactId = companyService.saveCompanyContact(company);					
		
		map.put("contactId", contactId);					
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String, Integer>>(map, HttpStatus.OK);		
	}

	/*
	 * To get the company contacts list on page load
	 */
	/*@RequestMapping(value = "/companyContactList", method = RequestMethod.GET)
	public ModelAndView CustomerContactList(HttpServletRequest request) {
		log.info("Entered in Company Controller CompanyContactList() ");
		ModelAndView mav = new ModelAndView("company/CompanyContact"); 
				
		try {
			List<MContactType> contactTypes = customerService.getContactTypeList();
			log.debug("Type of Contacts : "+contactTypes);

			mav.addObject("contactTypes", contactTypes);
		} catch (Exception e) {
			log.error("Error Occured. ",e);
			log.info("Exiting from Company Controller CompanyContactList() ::  "+mav);
		}
		
		log.info("Exiting from Company Controller CompanyContactList() ::  "+mav);
		return mav;
	}*/

	
	
	
	

	
	/*
	 * This method will be used to get the company contacts based on given customer Id and companyId
	 */
	@RequestMapping(value = "/getCmpContactsByCmpId.json", method = RequestMethod.POST)
	public String getCompanyContactsByCmpId(@RequestBody String companyContact) throws JSONException {
		log.info("Entered in Company Controller getCompanyContactsByCmpId()  ::   "+companyContact);
		String s = "";
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(companyContact);
		log.debug("JSON Object:  "+companyContact);
		
		String customerId = null, companyId = null;
		
		if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null){
			customerId = jo.get("customerId").getAsString();  
			log.debug("Customer Id : "+customerId);
		}
		
		if(jo.get("companyId") != null && jo.get("companyId").getAsString() != null){
			companyId = jo.get("companyId").getAsString();  
			log.debug("Company Id : "+companyId);
		}
		
		List<ContactVo> companyContactList = companyService.getCompanyContactsList(Integer.valueOf(customerId.trim()),Integer.valueOf(companyId.trim()) );
		log.debug("List of Company contacts : "+companyContactList);
		
		StringBuilder br = new StringBuilder();
		br.append("[");
		for(ContactVo contactVo : companyContactList){
			br.append("[\""+contactVo.getContactTypeName()+"\",\""+contactVo.getContactName()/*+"\",\""+contactVo.getMobileNumber()*/+"\",\""+contactVo.getBusinessPhoneNumber()+/*"\",\""+contactVo.getExtensionNumber()+*/"\",\""+contactVo.getEmailId()+"\",\""+contactVo.getAddress()+"\",\""+contactVo.getContactId()+"\"],");
		}
		 s = br.substring(0, br.length()-1);
		s+="]";
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		log.info("Exiting from Company Controller getCompanyContactsByCmpId() ::  "+JSONObject.valueToString(s).toString());
		return  JSONObject.valueToString(s).toString();
	}
  
  
	/*
	 * This method will be used to get the customer contacts list based on contact Id
	 */	
	@RequestMapping(value = "/getCmpContactByContactId.json", method = RequestMethod.POST)
	public ResponseEntity<ContactVo> getCompanyContactByContactId(@RequestBody String comapnyContact) throws JSONException {
		log.info("Entered in Company Controller getCmpContactByContactId()  ::   "+comapnyContact);
		ContactVo contactVo = new ContactVo();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(comapnyContact);
		log.debug("JSON Object:  "+comapnyContact);
		
		String contactId = null;
		if(jo.get("contactId") != null && jo.get("contactId").getAsString() != null){
			contactId = jo.get("contactId").getAsString();  
			log.debug("Contact Id : "+contactId);
		}
	    contactVo= companyService.getCompanyContactRecordById(Integer.valueOf(contactId.trim()));	   
		log.info("Exiting from Company Controller getCmpContactByContactId() ::  "+contactVo);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<ContactVo>(contactVo, HttpStatus.OK);
	}

	/*
	 * 	This method will be used to get current address list  based on given customerId and companyId
	 */
	@RequestMapping(value = "/getCurrentAddressByCmpId.json", method = RequestMethod.POST)
	public String getCurrentAddressListByCmpId(@RequestBody String companyContact) throws JSONException {
		log.info("Entered in Company Controller getCurrentAddressByCmpId() ::  "+companyContact);
		
		List<SimpleObject> object = new ArrayList<SimpleObject>();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(companyContact);
		log.debug("JSON Object : "+jo);
		
		String customerId = null;
		String companyId = null;
		
		if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null){
			customerId = jo.get("customerId").getAsString();  
			log.debug("Customer Id : "+customerId);
		}
		
		if(jo.get("companyId") != null && jo.get("companyId").getAsString() != null){
			companyId = jo.get("companyId").getAsString();  
			log.debug("Company Id : "+companyId);
		}
		object = companyService.getCompanyCurrentAddressRecordByCompanyId(Integer.valueOf(customerId.trim()), Integer.valueOf(companyId.trim()) );
		log.info("Exiting from Company Controller getCurrentAddressByCmpId() ::  "+JSONObject.valueToString(object).toString());
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return  JSONObject.valueToString(object).toString();
	}
	
	/*
	 * This method will be used to get transaction dates list for contact based on given customerId, companyId and contactUniqueId
	 */
	@RequestMapping(value = "/getTransactionDatesList.json", method = RequestMethod.POST)
	public ResponseEntity<List<SimpleObject>> getTransactionDatesListForContact(@RequestBody String companyContact) throws JSONException {
		log.info("Entered in Company Controller getTransactionDatesListForContact() ::  "+companyContact);
		List<SimpleObject> simpleObjects = new ArrayList();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(companyContact);
		log.debug("JSON Object "+jo);
		
		String customerId = null ,companyId = null, contactUniqueId = null;
		if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null
								&& jo.get("contactUniqueId") != null && jo.get("contactUniqueId").getAsString() != null
								&& jo.get("companyId") != null && jo.get("companyId").getAsString() != null){
			customerId = jo.get("customerId").getAsString(); 
			companyId = jo.get("companyId").getAsString();			
			contactUniqueId = jo.get("contactUniqueId").getAsString(); 
			log.debug("Customer Id:  "+customerId+"\t Company Id :   "+companyId+"\t Contact Unique Id : "+contactUniqueId);
		}
		 simpleObjects = companyService.getTransactionListForContact(Integer.valueOf(customerId), Integer.valueOf(companyId),  Integer.valueOf(contactUniqueId));	
		log.info("Exiting from Company Controller getTransactionDatesListForContact() ::  "+simpleObjects);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<SimpleObject>>(simpleObjects, HttpStatus.OK);
	}
	
	
	  /**********************  Company Contact  End  **************************/
	
	
/* Company Details Mahendra  Start */
		
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getMasterInfoForCompanyGrid.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getMasterInfoForCompanyGrid(@RequestBody String CustomerJsonString) {
		Map<String,List> masterInfoMap = new HashMap<String,List>();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(CustomerJsonString);
		String customerId=null;
		if(jo.get("customerId") != null && !jo.get("customerId").toString().equalsIgnoreCase("null")){
			customerId = jo.get("customerId").getAsString();
		}
		List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(customerId);						
		masterInfoMap.put("customerList", customerList);	
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
	}
	
	/*@RequestMapping(value = "/getCompanyNamesByCustomerId.json", method = RequestMethod.POST)
	public  ResponseEntity<List<CompanyVo>> getCompanyNamesByCustomerId(@RequestBody CustomerVo customerVo) {			
		List<CompanyVo> companyNames  = vendorService.getComapanyNamesAsDropDown(customerVo.getCustomerId()+"");
		return new ResponseEntity<List<CompanyVo>>(companyNames,HttpStatus.OK);
	}*/
	
	 @SuppressWarnings("rawtypes")
	 @RequestMapping(value="/getCompanyDetailsForGridView.json", method = RequestMethod.POST)
	 public ResponseEntity<Map<String,List>>  CustomerView(@RequestBody String companyJsonString) throws Exception {
			Map<String,List> map = new HashMap<String,List>();	
			log.debug(companyJsonString);
			System.out.println(companyJsonString+":::companyJsonString");
			try{
			 	JsonParser jsonParser = new JsonParser();
				JsonObject jo = (JsonObject) jsonParser.parse(companyJsonString);
			 													
				List<CompanyDetailsVo> detailsVos = new ArrayList<CompanyDetailsVo>();
				detailsVos = companyService.getCompanyDetailsListByCustomerId(jo.get("customerId").getAsInt(),jo.get("countryName").getAsString(),StringHelper.createNonNullString(jo.get("companyName").getAsString()),StringHelper.createNonNullString(jo.get("status").getAsString()),jo.get("companyId").getAsInt());
				map.put("companyDetails",detailsVos);
			}catch(Exception e){
				log.error("Error Occured ",e);
			}
			return new ResponseEntity<Map<String,List>>(map,HttpStatus.OK);
	}		
	 
	 @SuppressWarnings("rawtypes")
		@RequestMapping(value = "/getCompanyDetailsMasterInfoByCustomerd.json", method = RequestMethod.POST)
		public ResponseEntity<Map<String,List>> masterInfoOfCompanyDetailsByCompanyInfoId(@RequestBody String companyJsonString) {
			Map<String,List> map = new HashMap<String,List>();
			try{
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(companyJsonString);					
			List<SimpleObject> companySectors = commonService.getCompanySectors();
			map.put("companySectors",companySectors);			
			List<SimpleObject> pfTypes = commonService.getPFTypes();
			map.put("pfTypes",pfTypes);	
			List<SimpleObject> regTypes = commonService.getRegistrationActs();
			map.put("regTypes",regTypes);	
			if(jo.get("customerId").getAsInt() > 0){
				List<SimpleObject> industries = companyService.getCusomerIndustries(jo.get("customerId").getAsInt());
				map.put("industries",industries);
				List<CountryVo> countries = customerService.getCountriesListByCustomerId(jo.get("customerId").getAsString());
				map.put("countries",countries);
			}
			}catch(Exception e){
				log.error("Error Occured ",e);
			}
			return new ResponseEntity<Map<String,List>>(map,HttpStatus.OK);
		}
	 
	@RequestMapping(value = "/getCompanyDetailsByCompanyInfoId.json", method = RequestMethod.POST)
	public ResponseEntity<CompanyDetailsInfoVo> CompanyDetailsByCompanyInfoId(@RequestBody String companyJsonString) {
		
		CompanyDetailsInfoVo detailsInfoVo = new CompanyDetailsInfoVo();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(companyJsonString);		
		String companyInfoId = jo.get("companyInfoId").getAsString();
		 detailsInfoVo = companyService.getCompanyDetailsListByCompanyId(Integer.parseInt(companyInfoId));
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<CompanyDetailsInfoVo>(detailsInfoVo,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getCompanyTransactionDates.json", method = RequestMethod.POST)
	public ResponseEntity<List<SimpleObject>> getCompanyTransactionDates(@RequestBody String companyJsonString) {
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(companyJsonString);			
		List<SimpleObject> objects  = companyService.getCompanyTransactionDates(jo.get("customerId").getAsString(), jo.get("companyId").getAsString());
		return new ResponseEntity<List<SimpleObject>>(objects,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getSubIndustriesByCustomerId.json", method = RequestMethod.POST)
	public  ResponseEntity<List<SimpleObject>> getSubIndustriesByCustomerId(@RequestBody String companyJsonString) {
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(companyJsonString);
		//System.out.println(jo.get("customerId").getAsString()+":customerId - companyId:" + jo.get("companyId").getAsString()+" :: "+jo.get("companyId").getAsString()+" industryId : uniqueId: "+jo.get("uniqueId").getAsString());		
		List<SimpleObject> objects  = companyService.getCustomerSectors(Integer.valueOf(jo.get("customerId").getAsString()),  Integer.valueOf(jo.get("industryId").getAsString()));
		return new ResponseEntity<List<SimpleObject>>(objects,HttpStatus.OK);
	}
	 
	@RequestMapping(value = "/saveCompanyDetails.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Integer>> saveCompanyDetails(@RequestBody CompanyDetailsInfoVo companyDetailsInfoVo) {	
		Map<String, Integer> map = new HashMap<String, Integer>();
		Integer companyId = 0;
		if (companyDetailsInfoVo != null)
			companyId = companyService.saveCompanyDetails(companyDetailsInfoVo);
		map.put("companyId", companyId);
		map.put("customerId", companyDetailsInfoVo.getCustomerDetailsId());
		return new ResponseEntity<Map<String, Integer>>(map, HttpStatus.OK);
	}
	 	
	/* Company Details Mahendra End */
	
	
	/* Company Profile Mahendra  Start */
	
	@RequestMapping(value="/getMasterInfoForComapanyProfile.json", method = RequestMethod.GET)
	public ResponseEntity<Map<String,List<SimpleObject>>>  CompanyProfileView() throws Exception {			
		Map<String, List<SimpleObject>> map = new HashMap<String,List<SimpleObject>>();									
		List<SimpleObject> languages = commonService.getLanguageList();
		map.put("languages",languages);
		List<SimpleObject> currency = commonService.getCurrencyList();
		map.put("currency",currency);
		return new ResponseEntity<Map<String,List<SimpleObject>>>(map,HttpStatus.OK);
	}
	

	@RequestMapping(value = "/getCompanyProfileByCompanyId.json", method = RequestMethod.POST)
	public ResponseEntity<CompanyProfileVo> CompanyProfileByCompanyId(@RequestBody String companyIdJSON) {		
		JsonParser jsonParser = new JsonParser();  
		JsonObject jo = (JsonObject) jsonParser.parse(companyIdJSON);
		CompanyProfileVo profileVo = new CompanyProfileVo();
		if(jo.get("companyId") != null && !jo.get("companyId").toString().equalsIgnoreCase("null")){
			profileVo = companyService.getCompanyProfileByCompanyId(jo.get("companyId").getAsString(),jo.get("customerId").getAsString());  
			log.debug("profileVo : "+profileVo.getBussinessStartTime());
		}		
		return new ResponseEntity<CompanyProfileVo>(profileVo,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/saveCompanyProfile.json", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> saveCompanyProfile(@RequestBody CompanyProfileVo profileVo)  {		
		Integer profileId = companyService.saveCompanyProfile(profileVo);	
		if(profileId > 0)
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		else
			return new ResponseEntity<HttpStatus>(HttpStatus.CONFLICT);
	}
	
	/* Company Profile Mahendra End */

	
	
	/* Company Work Order  Mahendra Start  */
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getLocationsByCompanyId.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String, List<SimpleObject>>> getLocationsByCompanyId(@RequestBody String companyJsonString) {
		Map<String, List<SimpleObject>> map = new HashMap<String, List<SimpleObject>>();	
		JsonParser jsonParser = new JsonParser();  
		JsonObject jo = (JsonObject) jsonParser.parse(companyJsonString);
		try {	
			String locationParam = null;
			if(jo.get("locationId") != null &&  !jo.get("locationId").isJsonNull() && jo.get("locationId").getAsJsonArray().size() > 0 ){
				JsonArray locId = jo.get("locationId").getAsJsonArray();
				//System.out.println(locId);
				locationParam = locId.toString().replace("]", "").replace("[", "");
			}
			List<SimpleObject>  locationList =   employeeService.getLocationListByCustomerIdAndCompanyId(jo.get("customerId").getAsInt(), jo.get("companyId").getAsInt(),  locationParam );
			List<SimpleObject>  departmentList = employeeService.getDepartmentListByCompanyId(jo.get("customerId").getAsInt(), jo.get("companyId").getAsInt());
			departmentList.add(0,new SimpleObject(0,"ALL"));
			List<SimpleObject> countriesList = commonService.getCompanyCountries(jo.get("companyId").getAsInt());
			List<SimpleObject> activeJobCodes =  commonService.getActiveJobCodes(jo.get("customerId").getAsInt(), jo.get("companyId").getAsInt());			
			map.put("activeJobCodes", activeJobCodes);
			map.put("countriesList", countriesList);
			map.put("departmentList", departmentList);				
			map.put("locationList", locationList);			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error Occured. ",e);			
		}
		
		return new ResponseEntity<Map<String, List<SimpleObject>>>(map, HttpStatus.OK);
	}
	
	/*@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getDepartmentsByLocationId.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String, List<SimpleObject>>> getDepartmentsByLocationId(@RequestBody String companyJsonString) {
		Map<String, List<SimpleObject>> map = new HashMap<String, List<SimpleObject>>();	
		JsonParser jsonParser = new JsonParser();  
		JsonObject jo = (JsonObject) jsonParser.parse(companyJsonString);
		try {			
			List<SimpleObject>  departmentList = employeeService.getDepartmentListByLocationId(jo.get("locationId").getAsInt());									
			map.put("departmentList", departmentList);			
		} catch (Exception e) {
			log.error("Error Occured. ",e);			
		}
		
		return new ResponseEntity<Map<String, List<SimpleObject>>>(map, HttpStatus.OK);
	}*/
	
	@RequestMapping(value = "/getCompanyWorkOrderGridData.json", method = RequestMethod.POST)
	public ResponseEntity<List<WorkorderDetailInfoVo>> getCompanyWorkOrderGridData(@RequestBody String companyJsonString) {
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(companyJsonString);			
		List<WorkorderDetailInfoVo> objects  = companyService.getCompanyWorkOrderGridData(jo.get("customerId").getAsString(), jo.get("companyId").getAsString(),jo.get("locationName").getAsString(),jo.get("departmentName").getAsString(),jo.get("workOrderCode").getAsString(),jo.get("workOrderName").getAsString(),jo.get("status").getAsString());
		return new ResponseEntity<List<WorkorderDetailInfoVo>>(objects,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/saveCompanyWorkOrder.json", method = RequestMethod.POST)
	public ResponseEntity<Integer> saveCompanyWorkOrder(@RequestBody WorkorderDetailInfoVo workorderDetailInfoVo)  {		
		Integer workOrderId = companyService.saveCompanyWorkOrder(workorderDetailInfoVo);	
		if(workOrderId > 0)
			return new ResponseEntity<Integer>(workOrderId,HttpStatus.OK);
		else
			return new ResponseEntity<Integer>(0,HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getMasterInfoForcompanyWorkOrder.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getMasterInfoForCompanyWorkOrderGrid(@RequestBody String workOrderId) {
		Map<String,List> masterInfoMap = new HashMap<String,List>();
		Integer customerId =0;
		
		try{
			
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(workOrderId);
			if(jo.get("customerId") != null && !jo.get("customerId").toString().equalsIgnoreCase("null")){
				customerId= jo.get("customerId").getAsInt();
			}
			
			List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(customerId+"");						
			masterInfoMap.put("customerList", customerList);
			System.out.println(jo.get("workOrderId").getAsString());
			if(!jo.get("workOrderId").getAsString().toString().equalsIgnoreCase("null")  &&  jo.get("workOrderId").getAsInt() > 0){
				List<WorkorderDetailInfoVo> detailInfoVos = new ArrayList<WorkorderDetailInfoVo>(); 
				WorkorderDetailInfoVo detailsVos = companyService.getWorkorderDetailInfoByWorkOrderId(jo.get("workOrderId").getAsString());
				List<SimpleObject>  locationList =   employeeService.getLocationListByCustomerIdAndCompanyId(detailsVos.getCustomerDetailsId(), detailsVos.getCompanyDetailsId(), null);
				List<SimpleObject>  departmentList = employeeService.getDepartmentListByCompanyId(detailsVos.getCustomerDetailsId(), detailsVos.getCompanyDetailsId());
				departmentList.add(0,new SimpleObject(0,"ALL"));
				
				List<SimpleObject> countriesList = commonService.getCompanyCountries(detailsVos.getCompanyDetailsId());
				List<SimpleObject> companyNames  = vendorService.getComapanyNamesAsDropDown(detailsVos.getCustomerDetailsId()+"",detailsVos.getCompanyDetailsId()+"");
				List<SimpleObject> activeJobCodes =  commonService.getActiveJobCodes(detailsVos.getCustomerDetailsId(), detailsVos.getCompanyDetailsId());
				detailInfoVos.add(detailsVos);
				masterInfoMap.put("activeJobCodes", activeJobCodes);
				masterInfoMap.put("WorkorderDetails", detailInfoVos);
				masterInfoMap.put("companyList", companyNames);
				masterInfoMap.put("countriesList", countriesList);
				masterInfoMap.put("departmentList", departmentList);				
				masterInfoMap.put("locationList", locationList);	
			}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
	}
			
	@RequestMapping(value = "/getTransactionDatesOfWorkOrderHistory.json", method = RequestMethod.POST)
	public  ResponseEntity<List<SimpleObject>> getTransactionDatesOfWorkOrderHistory(@RequestBody String workOrderJsonString) throws JSONException {
		log.info("Entered in Company Controller getTransactionDatesOfWorkOrderHistory() ::  "+workOrderJsonString);
	
		JsonParser jsonParser = new JsonParser(); //{"customerId":16,"companyId":3,"workerOrderId":2}
		JsonObject jo = (JsonObject) jsonParser.parse(workOrderJsonString);
		List<SimpleObject> simpleObjects = companyService.getTransactionDatesOfWorkOrderHistory(jo.get("customerId").getAsInt(),
				jo.get("companyId").getAsInt(), jo.get("workerOrderId").getAsInt() );	
	
		return new ResponseEntity<List<SimpleObject>>(simpleObjects, HttpStatus.OK);
	}
	
	
	/* Company Work Order  Mahendra End */
	
	
	/************************ Company Division Start ******************************/
	/*
	 * This method will be used to save or update the division details
	*/ 
	@RequestMapping(value = "/saveDivision.json", method = RequestMethod.POST)
	public ResponseEntity<SimpleObject> saveDivision(@RequestBody DivisionVo paramDivision) {
		log.info("Entered in Company Controller saveDivision() ::  "+paramDivision);
		
		Integer divisionId = null;
		int companyDetailsId = 0;
		int companyId = paramDivision.getCompanyId();
		int customerId = paramDivision.getCustomerId();
		
		SimpleObject object = new SimpleObject();
		try{
			List<SimpleObject> obj = companyService.getCompanyTransactionDates(customerId+"", companyId+"");
			
			if(obj!=null && obj.size() > 0) {
				companyDetailsId= (obj.get(0).getId());
			}
			
			log.info("Date "+companyDetailsId);
			java.util.Date companyDate = (companyService.getCompanyDetailsListByCompanyId(companyDetailsId).getTransactionDate());
			java.util.Date deptDate = (paramDivision.getTransactionDate());
			log.info("Diff "+companyDate.compareTo(deptDate));
			
			if(companyDate.compareTo(deptDate) <= 0){
				if(paramDivision != null ){
					divisionId = companyService.saveDivision(paramDivision);
					log.debug("divisionId: "+divisionId);
				}
				
				if (divisionId != null && divisionId > 0) {
					object.setId(divisionId);
					object.setName("Success");
				} else {
					object.setId(0);
					object.setName("Fail");
				}
			}else{
				object.setId(-1);
				object.setName("Transaction date should not be less than Company Transaction Date");
			}
		
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		log.info("Exiting from Company Controller saveDivision() ::  "+object.toString());
		return new ResponseEntity<SimpleObject>(object, HttpStatus.OK);
	}

	
	/*
	 * This method will be used to get the division records based on given customerId, company Id and Division Name
	 * 																					
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getDivisionsListBySearch.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getDivisionsListBySearch(@RequestBody DivisionVo paramDivision) throws JSONException {
		log.info("Entered in Company Controller getDivisionsListBySearch()   ::   "+paramDivision);
		Map<String,List> returnData = new HashMap<String,List>();
		try{
		//List<SimpleObject> deptTypelist = commonService.getDepartmentTypes();
		List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(String.valueOf(paramDivision.getCustomerId()));
		List<DivisionVo> divisionList = companyService.getDivisionsListBySearch(paramDivision);
		log.debug("List of Divisions : "+divisionList);
		
		
		//returnData.put("deptTypeList", deptTypelist);
		returnData.put("customerList",customerList);
		returnData.put("divisionList",divisionList);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		log.info("Exiting from Company Controller getDivisionsListBySearch() ::  "+JSONObject.valueToString(null).toString());
		//return  JSONObject.valueToString(s).toString();
		return new ResponseEntity<Map<String,List>>(returnData,HttpStatus.OK);
	}
	
	/*
	 * This method will be used to get the plant record  based on divisionDetailsId
	 */	
	
	@RequestMapping(value = "/getDivisionById.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getDivisionById(@RequestBody String paramDivision) throws JSONException {
		log.info("Entered in Company Controller getDivisionById()  ::   "+paramDivision);
		
		Map<String,List> returnList = new HashMap<String,List>();
		try{
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(paramDivision);
			log.debug("JSON Object:  "+jo);
			
			String divisionDetailsId = null, customerId = null;
			if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null
							|| jo.get("divisionDetailsId") != null && jo.get("divisionDetailsId").getAsString() != null){
				customerId = jo.get("customerId").getAsString(); 
				divisionDetailsId = jo.get("divisionDetailsId").getAsString();  
				log.debug("customer d : "+customerId+" \t Division Details Id : "+divisionDetailsId);
			}
	
		    List<DivisionVo> divisionVo= companyService.getDivisionById(Integer.valueOf(divisionDetailsId.trim()));	
		    List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(customerId);
		    List<SimpleObject> countryList = commonService.getCountriesList();
		    List<SimpleObject> companyList = new ArrayList<SimpleObject>();
		     
		    if(divisionVo != null && divisionVo.size() > 0){
		    	companyList = vendorService.getComapanyNamesAsDropDown((divisionVo.get(0).getCustomerId())+"",(divisionVo.get(0).getCompanyId())+"");
		    }
		    
			returnList.put("divisionVo",divisionVo);
			returnList.put("customerList", customerList);
			returnList.put("countryList",countryList);
			returnList.put("companyList",companyList);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		log.info("Exiting from Company Controller getDivisionById() ::  "+returnList);
		return new ResponseEntity<Map<String,List>>(returnList, HttpStatus.OK);
	}

	/*
	 * This method will be used to get transaction dates list for division based on given customerId, companyId and divisionId
	 */
	@RequestMapping(value = "/getDivisionTransactionDatesList.json", method = RequestMethod.POST)
	public ResponseEntity<List<SimpleObject>> getTransactionDatesListForDivision(@RequestBody String paramDivision) throws JSONException {
		log.info("Entered in Company Controller getTransactionDatesListForDivision() ::  "+paramDivision);
		List<SimpleObject> simpleObjects = new ArrayList();
		try{
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(paramDivision);
			log.debug("JSON Object "+jo);
			
			String customerId = null ,companyId = null, divisionId = null;
			if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null
									&& jo.get("divisionId") != null && jo.get("divisionId").getAsString() != null
									&& jo.get("companyId") != null && jo.get("companyId").getAsString() != null){
				customerId = jo.get("customerId").getAsString(); 
				companyId = jo.get("companyId").getAsString();			
				divisionId = jo.get("divisionId").getAsString(); 
				log.debug("Customer Id:  "+customerId+"\t Company Id :   "+companyId+"\t Division Id : "+divisionId);
			}
			
			simpleObjects = companyService.getTransactionListForDivision(Integer.valueOf(customerId), Integer.valueOf(companyId),  Integer.valueOf(divisionId));	
			log.info("Exiting from Company Controller getTransactionDatesListForDivision() ::  "+simpleObjects);
		}catch(Exception e){
			log.error("Error Occured ",e);
			log.info("Exiting from Company Controller getTransactionDatesListForDivision() ::  "+simpleObjects);

		}
		return new ResponseEntity<List<SimpleObject>>(simpleObjects, HttpStatus.OK);
	}

	
	/*
	 * This service is used to validate the department that already exists are not
	 */
	@RequestMapping(value = "/validateDivisionCode.json", method = RequestMethod.POST)
	public Integer validateDivisionCode(@RequestBody DivisionVo paramDivision) throws JSONException {
		log.info("Entered in Company Controller validateDivisionCode() ::  "+paramDivision);
		Integer returnString = null;
		try{
			List<DivisionVo> divList = companyService.getDivisionsListBySearch(paramDivision);
			Integer companyCode = companyService.validateCompanyCode(paramDivision.getCustomerId()+"", paramDivision.getDivisionCode());
			
			log.info("List of Divisions : "+divList);
			log.info("CompanyCode:  "+companyCode);
			System.out.println(companyCode);
			System.out.println(divList.size());
			//List<CompanyDetailsInfoVo>
			if((divList != null && divList.size() > 0) || (companyCode != null && companyCode == 0)){
				returnString = 0;
			}else{
				returnString = 1;
			}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		System.out.println(returnString);
			log.info("Exiting from Company Controller validateDivisionCode() ::  "+returnString);
			return returnString;
	}
	
	
	 /********************* Company Division End ***************************/
}
