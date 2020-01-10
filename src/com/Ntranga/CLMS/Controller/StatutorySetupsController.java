package com.Ntranga.CLMS.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Ntranga.CLMS.Service.CommonService;
import com.Ntranga.CLMS.Service.CompanyService;
import com.Ntranga.CLMS.Service.DefineOverTimeControlDetailsService;
import com.Ntranga.CLMS.Service.DepartmentService;
import com.Ntranga.CLMS.Service.EmployeeService;
import com.Ntranga.CLMS.Service.HolidayCalendarService;
import com.Ntranga.CLMS.Service.LocationService;
import com.Ntranga.CLMS.Service.ShiftPatternService;
import com.Ntranga.CLMS.Service.StatutorySetupsService;
import com.Ntranga.CLMS.Service.TimeRulesService;
import com.Ntranga.CLMS.Service.VendorService;
import com.Ntranga.CLMS.vo.BonusRulesVo;
import com.Ntranga.CLMS.vo.CompanyProfileVo;
import com.Ntranga.CLMS.vo.CustomerVo;
import com.Ntranga.CLMS.vo.DefineBudgetDetailsVo;
import com.Ntranga.CLMS.vo.DefineProfessionalTaxInfoVo;
import com.Ntranga.CLMS.vo.DefineProfessionalTaxVo;
import com.Ntranga.CLMS.vo.DefineWorkerGroupVo;
import com.Ntranga.CLMS.vo.LWFRulesInfoVo;
import com.Ntranga.CLMS.vo.LWFRulesVo;
import com.Ntranga.CLMS.vo.LocationVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@SuppressWarnings({"rawtypes","unused"})
@RestController
@RequestMapping(value = "statutorySetupsController")
public class StatutorySetupsController {

	private static Logger log = Logger.getLogger(StatutorySetupsController.class);
	
	@Autowired
	private StatutorySetupsService statutorySetupsService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private VendorService vendorService;
	
	@Autowired 
	private LocationService locationService;
	
	@Autowired 
	private EmployeeService employeeService;
	
	@Autowired 
	private CompanyService companyService;
	
	@Autowired 
	private ShiftPatternService shiftPatternService;
	
	@Autowired 
	private HolidayCalendarService holidayCalendarService;
	
	@Autowired 
	private TimeRulesService timeRulesService;
	
	@Autowired 
	private DefineOverTimeControlDetailsService overTimeService;
	
	
	@RequestMapping(value = "/saveBonusRulesDetails.json", method = RequestMethod.POST)
	public  ResponseEntity<Integer> saveBonusRulesDetails(@RequestBody BonusRulesVo bonusRulesVo) {				
		Integer id = statutorySetupsService.saveBonusRulesDetails(bonusRulesVo);
		return new ResponseEntity<Integer>(id,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getCountryNamesAsDropDown.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getCountryNamesAsDropDown(@RequestBody BonusRulesVo bonusRulesVo) {		
		Map<String,List> masterInfoMap = new HashMap<String,List>();  
		try{
		List<SimpleObject> countriesList = commonService.getCompanyCountries(bonusRulesVo.getCompanyDetailsId());
		masterInfoMap.put("countriesList", countriesList);
		
		if(countriesList != null && countriesList.size() == 1){
			SimpleObject object  = countriesList.get(0);
			BonusRulesVo vo = statutorySetupsService.getBonusRulesDetails(bonusRulesVo);
			List<BonusRulesVo> bonusRulesVos = new ArrayList<BonusRulesVo>();
			bonusRulesVos.add(vo);
			masterInfoMap.put("bonusRulesVo", bonusRulesVos);
		}	
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getBonusRulesDetails.json", method = RequestMethod.POST)
	public  ResponseEntity<BonusRulesVo> getBonusRulesDetails(@RequestBody BonusRulesVo bonusRulesVo) {				
		BonusRulesVo rulesVo = statutorySetupsService.getBonusRulesDetails(bonusRulesVo);
		return new ResponseEntity<BonusRulesVo>(rulesVo,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getTransactionDatesForBonusHistory.json", method = RequestMethod.POST)
	public  ResponseEntity<List<SimpleObject>> getTransactionDatesForBonusHistory(@RequestBody BonusRulesVo bonusRulesVo) {	
		List<SimpleObject>  list = new ArrayList<SimpleObject>();
		try{		
			list = statutorySetupsService.getTransactionDatesForBonusHistory(bonusRulesVo);		
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<SimpleObject>>(list, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getBonusRecordByBonusRulesId.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List> > getBonusRecordByBonusRulesId(@RequestBody String bonusRulesId) {
		Map<String,List> masterInfoMap = new HashMap<String,List>();
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(bonusRulesId);
		try{		
			BonusRulesVo bonusRulesVo  = statutorySetupsService.getBonusRecordByBonusRulesId(jo.get("bonusRulesId").getAsString());
			List<BonusRulesVo> bonusRulesVos = new ArrayList<BonusRulesVo>();
			bonusRulesVos.add(bonusRulesVo);
			masterInfoMap.put("bonusRulesVo", bonusRulesVos);
			List<SimpleObject> countriesList = commonService.getCompanyCountries(bonusRulesVo.getCompanyDetailsId());
			masterInfoMap.put("countriesList", countriesList);
			List<SimpleObject> list = vendorService.getComapanyNamesAsDropDown(bonusRulesVo.getCustomerDetailsId()+"",bonusRulesVo.getCompanyDetailsId()+"");
			masterInfoMap.put("companyList", list);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap, HttpStatus.OK);
	}
	
	
	/*
	 * This method will be used to save or update the LWF Rules 
	 */
	@RequestMapping(value = "/saveLWFRules.json", method = RequestMethod.POST)
	public ResponseEntity<SimpleObject> saveLWFRules(@RequestBody LWFRulesVo lwfRules) {
		log.info("Entered in StatutorySetupsController saveLWFRules() ::  "+lwfRules);
		
		SimpleObject object = new SimpleObject();
		Integer rulesId = null;
		try{
		
			if (lwfRules != null){
				rulesId = statutorySetupsService.saveLWFRules(lwfRules);
				log.debug("LWF Rules ID: "+rulesId);
			}
		
			if (rulesId != null) {
				object.setId(rulesId);
				object.setName("Success");
			} else {
				object.setId(0);
				object.setName("Fail");
			}
			
		}catch(Exception e){
			object.setId(0);
			object.setName("Exception");
			log.error("Error Occured ",e);
			log.info("Exiting from StatutorySetupsController saveLWFRules() with exception ::  "+object+"  : "+e.getMessage());
		}
		log.info("Exiting from StatutorySetupsController saveLWFRules() ::  "+object);
		return new ResponseEntity<SimpleObject>(object, HttpStatus.OK);
	}

	/*
	 * This method will be used to get the LWF rules list based on given customer/company/country/state name/s
	 */
	@RequestMapping(value = "/getLWFRulesListBySearch.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getLWFRulesListBySearch(@RequestBody LWFRulesVo lwfRules) throws JSONException {
		log.info("Entered in StatutorySetupsController getLWFRulesBySearch()  ::   "+lwfRules);
		Map<String,List> returnData = new HashMap<String,List>();
		try{
			List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(String.valueOf(lwfRules.getCustomerId()));
			List<LWFRulesVo> rulesList = statutorySetupsService.getLWFRulesBySearch(lwfRules);
			log.debug("LWF Rules List : "+rulesList);
			
			returnData.put("customerList",customerList);
			returnData.put("lwfRulesList",rulesList);
			
			log.info("Exiting from StatutorySetupsController getLWFRulesListBySearch() ::  "+returnData.toString());
		}catch(Exception e){
			log.error("Error Occured ",e);
			log.info("Exiting from StatutorySetupsController getLWFRulesListBySearch() ::  "+returnData.toString());
		}
		log.info("Exiting from StatutorySetupsController getLWFRulesListBySearch() ::  "+returnData.toString());
		return new ResponseEntity<Map<String,List>>(returnData,HttpStatus.OK);
	}
	
	/*
	 * This method will be used to get the LWF Rules , customer, company, country and states List based on given Id
	 */	
	@RequestMapping(value = "/getLWFRuleById.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getLWFRuleByRuleId(@RequestBody String lwfRule) throws JSONException {
		log.info("Entered in StatutorySetupsController  getLWFRuleByRuleId()  ::   "+lwfRule);
		Map<String,List> returnRules = new HashMap<String,List>();
		
		try{
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(lwfRule);
			log.debug("JSON Object:  "+jo);
			
			String lwfRuleId = null, customerId = null, companyId = null;
			if(jo.get("lwfRuleId") != null && jo.get("lwfRuleId").getAsString() != null){
				lwfRuleId = jo.get("lwfRuleId").getAsString();  
				log.debug("LWF Rule Id : "+lwfRuleId);
			}
			
			if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null){
				customerId = jo.get("customerId").getAsString();  
				log.debug("customer Id : "+customerId);
			}
			
			if(jo.get("companyId") != null && jo.get("companyId").getAsString() != null){
				companyId = jo.get("companyId").getAsString();  
				log.debug("Company Id : "+companyId);
			}
			
		    List<LWFRulesVo> rulesVo = statutorySetupsService.getLWFRuleByRuleId(Integer.valueOf(lwfRuleId.trim()));	
		    List<LWFRulesInfoVo> rulesList  = statutorySetupsService.getLWFRulesListByRuleId(Integer.valueOf(lwfRuleId.trim()));
		    List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(customerId);
		    List<SimpleObject> companyList = new ArrayList<SimpleObject>();
		    List<SimpleObject> countryList = new ArrayList<SimpleObject>();
		    List<SimpleObject> statesList = new ArrayList<SimpleObject>();
		    
		    if(rulesVo != null && rulesVo.size() > 0 ){
			    companyList = vendorService.getComapanyNamesAsDropDown((rulesVo.get(0).getCustomerId())+"",rulesVo.get(0).getCompanyId()+"");
				countryList = commonService.getCompanyCountries(rulesVo.get(0).getCompanyId());
				statesList = commonService.getStatesList(rulesVo.get(0).getCountryId());
			    
		    }
			
			returnRules.put("lwfRulesList",rulesVo);
			returnRules.put("rulesList",rulesList);
			returnRules.put("customerList", customerList);
			returnRules.put("companyList", companyList);
			returnRules.put("countryList",countryList);
			returnRules.put("statesList", statesList);
			
		
		}catch(Exception e){
			log.error("Error Occured ",e);
			log.info("Exiting from StatutorySetupsController getLWFRuleByRuleId() ::  "+returnRules);
		}
		log.info("Exiting from StatutorySetupsController getLWFRuleByRuleId() ::  "+returnRules);
		return new ResponseEntity<Map<String,List>>(returnRules, HttpStatus.OK);
	}

	
	/*
	 * This method will be used to get transaction dates list for LWF Rules based on given customerId, companyId and lwfUniqueId
	 */
	@RequestMapping(value = "/getTransactionDatesListForLWFRules.json", method = RequestMethod.POST)
	public ResponseEntity<List<SimpleObject>> getTransactionDatesListForLWFRules(@RequestBody String lwfRules) throws JSONException {
		log.info("Entered in StatutorySetupsController getTransactionDatesListForLWFRules() ::  "+lwfRules);
		
		List<SimpleObject> simpleObjects =  new ArrayList<SimpleObject>();
		try{
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(lwfRules);
			log.debug("JSON Object "+jo);
			
			String customerId = null ,companyId = null, lwfUniqueId = null;
			if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null
									&& jo.get("lwfUniqueId") != null && jo.get("lwfUniqueId").getAsString() != null
									&& jo.get("companyId") != null && jo.get("companyId").getAsString() != null){
				customerId = jo.get("customerId").getAsString(); 
				companyId = jo.get("companyId").getAsString();			
				lwfUniqueId = jo.get("lwfUniqueId").getAsString(); 
				log.debug("Customer Id:  "+customerId+"\t Company Id :   "+companyId+"\t LWF Unique Id : "+lwfUniqueId);
			}
			 simpleObjects = statutorySetupsService.getTransactionListForLWFRules(Integer.valueOf(customerId), Integer.valueOf(companyId),  Integer.valueOf(lwfUniqueId));	
			log.info("Exiting from StatutorySetupsController getTransactionDatesListForLWFRules() ::  "+simpleObjects);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<SimpleObject>>(simpleObjects, HttpStatus.OK);
	}
	
	/*
	 * This method will be used to save or update the ProfessionalTax
	 */
	@RequestMapping(value = "/saveProfessionalTax.json", method = RequestMethod.POST)
	public ResponseEntity<SimpleObject> saveProfessionalTax(@RequestBody DefineProfessionalTaxVo paramRules) {
		log.info("Entered in StatutorySetupsController saveProfessionalTax() ::  "+paramRules);
		
		SimpleObject object = new SimpleObject();
		Integer rulesId = null;
		try{
		
			if (paramRules != null){
				rulesId = statutorySetupsService.saveProfessionalTax(paramRules);
				log.debug("Professional Tax Id: "+rulesId);
			}
		
			if (rulesId != null && rulesId > 0) {
				object.setId(rulesId);
				object.setName("Success");
			} else {
				object.setId(0);
				object.setName("Fail");
			}
			
		}catch(Exception e){
			object.setId(0);
			object.setName("Exception");
			log.error("Error Occured ",e);
			log.info("Exiting from StatutorySetupsController saveProfessionalTax() with exception ::  "+object+"  : "+e.getMessage());
		}
		log.info("Exiting from StatutorySetupsController saveProfessionalTax() ::  "+object);
		return new ResponseEntity<SimpleObject>(object, HttpStatus.OK);
	}

	/*
	 * This method will be used to get the professional tax records based on given customer Id and/or company Id
	 * 																					and/or country Id and/or location name
	 */
	@RequestMapping(value = "/getProfessionalTaxListBySearch.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getProfessionalTaxListBySearch(@RequestBody DefineProfessionalTaxVo paramRules) throws JSONException {
		log.info("Entered in StatutorySetupsController getProfessionalTaxListBySearch()  ::   "+paramRules);
		Map<String,List> returnData = new HashMap<String,List>();
		try{
			List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(String.valueOf(paramRules.getCustomerId()));
			List<DefineProfessionalTaxVo> taxList = statutorySetupsService.getProfessionalTaxListBySearch(paramRules);
			log.debug("Professional Tax List : "+taxList);
			
			returnData.put("customerList",customerList);
			returnData.put("taxList",taxList);
			
			log.info("Exiting from StatutorySetupsController getProfessionalTaxListBySearch() ::  "+returnData.toString());
		}catch(Exception e){
			log.error("Error Occured ",e);
			log.info("Exiting from StatutorySetupsController getProfessionalTaxListBySearch() ::  "+returnData.toString());
		}
		log.info("Exiting from StatutorySetupsController getProfessionalTaxListBySearch() ::  "+returnData.toString());
		return new ResponseEntity<Map<String,List>>(returnData,HttpStatus.OK);
	}
	
	/*
	 * This method will be used to get the professional Tax details, customer, company, country and states list based on given Id
	 */	
	@RequestMapping(value = "/getProfessionalTaxById.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getProfessionalTaxById(@RequestBody String paramRule) throws JSONException {
		log.info("Entered in StatutorySetupsController  getProfessionalTaxById()  ::   "+paramRule);
		Map<String,List> returnRules = new HashMap<String,List>();
		
		try{
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(paramRule);
			log.debug("JSON Object:  "+jo);
			
			String professionalTaxId = null, customerId = null, companyId = null;
			if(jo.get("professionalTaxId") != null && jo.get("professionalTaxId").getAsString() != null){
				professionalTaxId = jo.get("professionalTaxId").getAsString();  
				log.debug("Professional Tax Id : "+professionalTaxId);
			}
			
			if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null){
				customerId = jo.get("customerId").getAsString();  
				log.debug("customer Id : "+customerId);
			}
			
			if(jo.get("companyId") != null && jo.get("companyId").getAsString() != null){
				companyId = jo.get("companyId").getAsString();  
				log.debug("Company Id : "+companyId);
			}
			
		    List<DefineProfessionalTaxVo> taxVo = statutorySetupsService.getProfessionalTaxByProfessionalTaxId(Integer.valueOf(professionalTaxId.trim()));	
		    List<DefineProfessionalTaxInfoVo> taxRulesList  = statutorySetupsService.getPTRulesListByProfessionalTaxId(Integer.valueOf(professionalTaxId.trim()));
		    List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(customerId);
		    List<SimpleObject> companyList = new ArrayList<SimpleObject>();
		    List<SimpleObject> countryList = new ArrayList<SimpleObject>();
		    List<SimpleObject> statesList = new ArrayList<SimpleObject>();
		    List locationsList = new ArrayList<>();
		    
		    if(taxVo != null && taxVo.size() > 0 ){
		    	LocationVo paramLocation = new LocationVo();
		    	paramLocation.setCustomerId(taxVo.get(0).getCustomerId());
		    	paramLocation.setCompanyId(taxVo.get(0).getCompanyId());
		    	paramLocation.setCountryId(taxVo.get(0).getCountryId());
		    	
			    companyList = vendorService.getComapanyNamesAsDropDown((taxVo.get(0).getCustomerId())+"",taxVo.get(0).getCompanyId()+"");
				countryList = commonService.getCompanyCountries(taxVo.get(0).getCompanyId());
				statesList = commonService.getStatesList(taxVo.get(0).getCountryId());
			    locationsList   = locationService.getLocationsListBySearch(paramLocation);
		    }
			
			returnRules.put("professionalTaxList",taxVo);
			returnRules.put("taxRulesList",taxRulesList);
			returnRules.put("customerList", customerList);
			returnRules.put("companyList", companyList);
			returnRules.put("countryList",countryList);
			returnRules.put("statesList", statesList);
			returnRules.put("locationsList", locationsList);
		
		}catch(Exception e){
			log.error("Error Occured ",e);
			log.info("Exiting from StatutorySetupsController getProfessionalTaxById() ::  "+returnRules);
		}
		log.info("Exiting from StatutorySetupsController getProfessionalTaxById() ::  "+returnRules);
		return new ResponseEntity<Map<String,List>>(returnRules, HttpStatus.OK);
	}

	
	/*
	 * This method will be used to get transaction dates list for professional tax based on given customerId, companyId and taxUniqueId
	 */
	@RequestMapping(value = "/getTransactionDatesListForProfessionalTax.json", method = RequestMethod.POST)
	public ResponseEntity<List<SimpleObject>> getTransactionDatesListForProfessionalTax(@RequestBody String paramRules) throws JSONException {
		log.info("Entered in StatutorySetupsController getTransactionDatesListForProfessionalTax() ::  "+paramRules);
		
		List<SimpleObject> simpleObjects =  new ArrayList<SimpleObject>();
		try{
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(paramRules);
			log.debug("JSON Object "+jo);
			
			String customerId = null ,companyId = null, taxUniqueId = null;
			if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null
									&& jo.get("taxUniqueId") != null && jo.get("taxUniqueId").getAsString() != null
									&& jo.get("companyId") != null && jo.get("companyId").getAsString() != null){
				customerId = jo.get("customerId").getAsString(); 
				companyId = jo.get("companyId").getAsString();			
				taxUniqueId = jo.get("taxUniqueId").getAsString(); 
				log.debug("Customer Id:  "+customerId+"\t Company Id :   "+companyId+"\t Tax Unique Id : "+taxUniqueId);
			}
			 simpleObjects = statutorySetupsService.getTransactionListForProfessionalTax(Integer.valueOf(customerId), Integer.valueOf(companyId),  Integer.valueOf(taxUniqueId));	
			log.info("Exiting from StatutorySetupsController getTransactionDatesListForProfessionalTax() ::  "+simpleObjects);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<SimpleObject>>(simpleObjects, HttpStatus.OK);
	}
	
	/*
	 * This method will be used to get the budget records based on given customer Id and/or company Id and/or
	 * 																					and/or budget Id/name and/or budgetYear and/or approvalStatus
	 */
	@RequestMapping(value = "/getBudgetListBySearch.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getBudgetListBySearch(@RequestBody DefineBudgetDetailsVo paramBudget) throws JSONException {
		log.info("Entered in StatutorySetupsController getBudgetListBySearch()  ::   "+paramBudget);
		Map<String,List> returnData = new HashMap<String,List>();
		try{
			List<DefineBudgetDetailsVo> budgetList = statutorySetupsService.getBudgetListBySearch(paramBudget);
			log.debug("Budget List : "+budgetList);
			
			returnData.put("budgetList",budgetList);
			
		}catch(Exception e){
			log.error("Error Occured ",e);
			log.info("Exiting from StatutorySetupsController getBudgetListBySearch() ::  "+returnData.toString());
		}
		log.info("Exiting from StatutorySetupsController getBudgetListBySearch() ::  "+returnData.toString());
		return new ResponseEntity<Map<String,List>>(returnData,HttpStatus.OK);
	}
	
	/*
	 * This method will be used to get the budget details based on budgetDetailsId and also to get
	 * 											locations list, departments list and default currency Id
	 */	
	@RequestMapping(value = "/getBudgetDetailsById.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getBudgetDetailsById(@RequestBody String jsonString) throws JSONException {
		log.info("Entered in StatutorySetupsController  getBudgetDetails()  ::   "+jsonString);
		Map<String,List> returnRules = new HashMap<String,List>();
		
		try{
			
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(jsonString);
			log.debug("JSON Object:  "+jo);
			
			String budgetDetailsId = null, customerId = null;
			if(jo.get("budgetDetailsId") != null && jo.get("budgetDetailsId").getAsString() != null){
				budgetDetailsId = jo.get("budgetDetailsId").getAsString();  
				log.debug("Budget Details Id : "+budgetDetailsId);
			}
			
			if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null){
				customerId = jo.get("customerId").getAsString();  
				log.debug("customer Id : "+customerId);
			}
		
		    List<DefineBudgetDetailsVo> budgetList = statutorySetupsService.getBudgetDetailsByBudgetDetailsId(Integer.parseInt(budgetDetailsId));

		    List<CompanyProfileVo> profileList = new ArrayList<>();
		    List locationsList = new ArrayList<>();
		    List departmentsList = new ArrayList<>();
		    List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(customerId);
		    List<SimpleObject> companyList = new ArrayList<SimpleObject>();
		    List<SimpleObject> countryList = new ArrayList<SimpleObject>();
		    
		    if(budgetList != null && budgetList.size() > 0){
		    	companyList = vendorService.getComapanyNamesAsDropDown((budgetList.get(0).getCustomerId())+"",budgetList.get(0).getCompanyId()+"");
				countryList = commonService.getCompanyCountries(budgetList.get(0).getCompanyId());
				
		    	CompanyProfileVo profileVo = companyService.getCompanyProfileByCompanyId(budgetList.get(0).getCompanyId()+"", budgetList.get(0).getCustomerId()+"");
			    profileList.add(profileVo);
			    
			    LocationVo paramLocation = new LocationVo();
		    	paramLocation.setCustomerId(budgetList.get(0).getCustomerId());
		    	paramLocation.setCompanyId(budgetList.get(0).getCompanyId());
		    	paramLocation.setCountryId(budgetList.get(0).getCountryId());
			    	
				locationsList   = locationService.getLocationsListBySearch(paramLocation);
				departmentsList =  employeeService.getDepartmentListByCompanyId(budgetList.get(0).getCustomerId(), budgetList.get(0).getCompanyId());
		    }
			returnRules.put("budgetVo",budgetList);
			returnRules.put("defaultCurrency",profileList);
			returnRules.put("departmentsList", departmentsList);
			returnRules.put("locationsList", locationsList);
			returnRules.put("customerList", customerList);
			returnRules.put("companyList", companyList);
			returnRules.put("countryList",countryList);
		
		}catch(Exception e){
			log.error("Error Occured ",e);
			log.info("Exiting from StatutorySetupsController getBudgetDetails() ::  "+returnRules);
		}
		log.info("Exiting from StatutorySetupsController getBudgetDetails() ::  "+returnRules);
		return new ResponseEntity<Map<String,List>>(returnRules, HttpStatus.OK);
	}
	
	/*
	 * This method will be used to save or update the budget
	 */
	@RequestMapping(value = "/saveBudget.json", method = RequestMethod.POST)
	public ResponseEntity<SimpleObject> saveBudget(@RequestBody DefineBudgetDetailsVo paramBudget) {
		log.info("Entered in StatutorySetupsController saveBudget() ::  "+paramBudget);
		
		SimpleObject object = new SimpleObject();
		Integer budgetId = null;
		try{
			if (paramBudget != null){
				budgetId = statutorySetupsService.saveBudget(paramBudget);
				log.debug("Professional Tax Id: "+budgetId);
			}
		
			if (budgetId != null && budgetId > 0) {
				object.setId(budgetId);
				object.setName("Success");
			} else {
				object.setId(0);
				object.setName("Fail");
			}
			
		}catch(Exception e){
			object.setId(0);
			object.setName("Exception");
			log.error("Error Occured ",e);
			log.info("Exiting from StatutorySetupsController saveBudget() with exception ::  "+object+"  : "+e.getMessage());
		}
		log.info("Exiting from StatutorySetupsController saveBudget() ::  "+object);
		return new ResponseEntity<SimpleObject>(object, HttpStatus.OK);
	}
	
	/*
	 * This method will be used to get transaction dates list for budget based on given customerId, companyId and budgetId
	 */
	@RequestMapping(value = "/getTransactionDatesListForBudget.json", method = RequestMethod.POST)
	public ResponseEntity<List<SimpleObject>> getTransactionDatesListForBudget(@RequestBody DefineBudgetDetailsVo paramBudget) throws JSONException {
		log.info("Entered in StatutorySetupsController getTransactionDatesListForBudget() ::  "+paramBudget);
		
		List<SimpleObject> simpleObjects =  new ArrayList<SimpleObject>();
		try{
			simpleObjects = statutorySetupsService.getTransactionListForBudget(paramBudget);	
			
		}catch(Exception e){
			log.error("Error Occured ",e);
			log.info("Exiting from StatutorySetupsController getTransactionDatesListForBudget() ::  "+simpleObjects);
		}
		log.info("Exiting from StatutorySetupsController getTransactionDatesListForBudget() ::  "+simpleObjects);
		return new ResponseEntity<List<SimpleObject>>(simpleObjects, HttpStatus.OK);
	}
	
	/*
	 * This method will be used to validate budget code and budget Year
	 */
	@RequestMapping(value = "/validateBudgetCodeAndYear.json", method = RequestMethod.POST)
	public Integer validateBudgetCodeAndYear(@RequestBody DefineBudgetDetailsVo paramBudget) throws JSONException{
		log.info("Entered in  StatutorySetupsController validateBudgetCodeAndYear() ::  "+paramBudget);
		Integer isValid = null;
		
		try{
			//List<DefineExceptionVo> exceptionList = timeRulesService.getExceptionsListBySearch(paramException);
			if(paramBudget.getBudgetYear() == null ||  paramBudget.getBudgetYear().isEmpty()){
				isValid = statutorySetupsService.validateBudgetCodeandYear(paramBudget.getCustomerId(), paramBudget.getCompanyId(), paramBudget.getBudgetCode(), null);
			}else{
				isValid = statutorySetupsService.validateBudgetCodeandYear(paramBudget.getCustomerId(), paramBudget.getCompanyId(), null, paramBudget.getBudgetYear());
			}
			
		}catch(Exception e){
			log.error("Error Occured ",e);
			isValid = -1;
		}
		System.out.println(isValid);
			log.info("Exiting from  StatutorySetupsController validateBudgetCodeAndYear() ::  "+isValid);
			return isValid;
	}
	
	
	/*
	 * This method will be used to get the countries list,locations list, departments list and default currency Id
	 */	
	@RequestMapping(value = "/getCountryLocationAndDepartmentDropdown.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getCountryLocationAndDepartmentDropdown(@RequestBody String paramString) throws JSONException {
		log.info("Entered in StatutorySetupsController  getCountryLocationAndDepartmentDropdown()  :: paramString =   "+paramString);
		Map<String,List> returnRules = new HashMap<String,List>();
		
		try{
			
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(paramString);
			log.debug("JSON Object:  "+jo);
			
			String companyId = null, customerId = null;
			if(jo.get("companyId") != null && jo.get("companyId").getAsString() != null){
				companyId = jo.get("companyId").getAsString();  
				log.debug("Company Id : "+companyId);
			}
			
			if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null){
				customerId = jo.get("customerId").getAsString();  
				log.debug("customer Id : "+customerId);
			}
		
			CompanyProfileVo profileVo = companyService.getCompanyProfileByCompanyId(companyId, customerId);
		    List<CompanyProfileVo> profileList = new ArrayList<>();
		    profileList.add(profileVo);
		    
		    List locationsList = new ArrayList<>();
		    List departmentsList = new ArrayList<>();
		    List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(customerId);
		    List<SimpleObject> companyList = vendorService.getComapanyNamesAsDropDown(customerId, companyId);
		    List<SimpleObject> countryList = commonService.getCompanyCountries(Integer.parseInt(companyId));		    
			    
		    LocationVo paramLocation = new LocationVo();
	    	paramLocation.setCustomerId(Integer.parseInt(customerId));
	    	paramLocation.setCompanyId(Integer.parseInt(companyId));
	    	//paramLocation.setCountryId(Integer.parseInt(countryId));
		    	
			locationsList   = locationService.getLocationsListBySearch(paramLocation);
			departmentsList =  employeeService.getDepartmentListByCompanyId(Integer.parseInt(customerId), Integer.parseInt(companyId) );
			List<SimpleObject> costCenterList = new ArrayList<SimpleObject>();
			costCenterList = departmentService.getDepartmentNameForCostCenter(Integer.valueOf(customerId.trim()), Integer.valueOf(companyId.trim()) );
			
			returnRules.put("defaultCurrency",profileList);
			returnRules.put("departmentsList", departmentsList);
			returnRules.put("locationsList", locationsList);
			returnRules.put("countryList",countryList);
			returnRules.put("costCenterList",costCenterList);
			
		}catch(Exception e){
			log.error("Error Occured ",e);
			log.info("Exiting from StatutorySetupsController getCountryLocationAndDepartmentDropdown() ::  "+returnRules);
		}
		log.info("Exiting from StatutorySetupsController getCountryLocationAndDepartmentDropdown() ::  "+returnRules);
		return new ResponseEntity<Map<String,List>>(returnRules, HttpStatus.OK);
	}
	
	
	/*
	 * This method will be used to get the Worker Group records based on given customer Id and/or company Id and/or
	 * 																					and/or Group Id/name 
	 */
	@RequestMapping(value = "/getWorkGroupBySearch.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getWorkGroupBySearch(@RequestBody DefineWorkerGroupVo paramWorkerGroup) throws JSONException {
		log.info("Entered in StatutorySetupsController getWorkGroupBySearch()  ::   "+paramWorkerGroup);
		Map<String,List> returnData = new HashMap<String,List>();
		try{
			List<DefineWorkerGroupVo> workerGroupList = statutorySetupsService.getWorkGroupBySearch(paramWorkerGroup);
			log.debug("Worker Group List : "+workerGroupList);
			
			returnData.put("workerGroupList",workerGroupList);
			
		}catch(Exception e){
			log.error("Error Occured ",e);
			log.info("Exiting from StatutorySetupsController getWorkGroupBySearch() ::  "+returnData.toString());
		}
		log.info("Exiting from StatutorySetupsController getWorkGroupBySearch() ::  "+returnData.toString());
		return new ResponseEntity<Map<String,List>>(returnData,HttpStatus.OK);
	}
	
	/*
	 * This method will be used to get the Worker Group details based on WorkerGroupId 
	 */	
	@RequestMapping(value = "/getWorkerGroupById.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getWorkerGroupById(@RequestBody String jsonString) throws JSONException {
		log.info("Entered in StatutorySetupsController  getWorkerGroupById()  ::   "+jsonString);
		Map<String,List> returnRules = new HashMap<String,List>();
		
		try{
			
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(jsonString);
			log.debug("JSON Object:  "+jo);
			
			String workerGroupId = null, customerId = null;
			if(jo.get("workerGroupId") != null && jo.get("workerGroupId").getAsString() != null){
				workerGroupId = jo.get("workerGroupId").getAsString();  
				log.debug("Worker Group Id : "+workerGroupId);
			}
			
			if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null){
				customerId = jo.get("customerId").getAsString();  
				log.debug("customer Id : "+customerId);
			}
		
		    List<DefineWorkerGroupVo> workerGroupList = statutorySetupsService.getWorkerGroupById(Integer.parseInt(workerGroupId));

		    List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(customerId);
		    List<SimpleObject> companyList = new ArrayList<SimpleObject>();
		    List<SimpleObject> countryList = new ArrayList<SimpleObject>();
		    //List<SimpleObject> wageRateList = new ArrayList<SimpleObject>();
		    List<SimpleObject> shiftPatternList = new ArrayList<SimpleObject>();
		    List<SimpleObject> holidayCalendarList = new ArrayList<SimpleObject>();
		    List<SimpleObject> timeRuleList = new ArrayList<SimpleObject>();
		    List<SimpleObject> overTimeRuleList = new ArrayList<SimpleObject>();
		    
		    if(workerGroupList != null && workerGroupList.size() > 0){
		    	companyList = vendorService.getComapanyNamesAsDropDown((workerGroupList.get(0).getCustomerId())+"",workerGroupList.get(0).getCompanyId()+"");
				countryList = commonService.getCompanyCountries(workerGroupList.get(0).getCompanyId());
				
		    	//wageRateList  = 
		    	shiftPatternList = shiftPatternService.getShiftPatternDropdown(workerGroupList.get(0).getCustomerId(), workerGroupList.get(0).getCompanyId());
		    	holidayCalendarList = holidayCalendarService.getHolidayCalendarDropdown(workerGroupList.get(0).getCustomerId(), workerGroupList.get(0).getCompanyId());
		    	timeRuleList = timeRulesService.getRuleCodesList(workerGroupList.get(0).getCustomerId(), workerGroupList.get(0).getCompanyId()) ;
		    	overTimeRuleList =  overTimeService.getOverTimeNamesByCompanyId(workerGroupList.get(0).getCustomerId(), workerGroupList.get(0).getCompanyId());
		    	
		    }
			returnRules.put("workerGroupVo",workerGroupList);
			returnRules.put("shiftPatternList", shiftPatternList);
			//returnRules.put("wageRateList", wageRateList);
			returnRules.put("customerList", customerList);
			returnRules.put("companyList", companyList);
			returnRules.put("countryList",countryList);
			returnRules.put("holidayCalendarList",holidayCalendarList);
			returnRules.put("timeRuleList",timeRuleList);
			returnRules.put("overTimeRuleList",overTimeRuleList);
		
		}catch(Exception e){
			log.error("Error Occured ",e);
			log.info("Exiting from StatutorySetupsController getWorkerGroupById() ::  "+returnRules);
		}
		log.info("Exiting from StatutorySetupsController getWorkerGroupById() ::  "+returnRules);
		return new ResponseEntity<Map<String,List>>(returnRules, HttpStatus.OK);
	}
	
	/*
	 * This method will be used to save or update the worker group
	 */
	@RequestMapping(value = "/saveWorkerGroup.json", method = RequestMethod.POST)
	public ResponseEntity<SimpleObject> saveWorkerGroup(@RequestBody DefineWorkerGroupVo paramWorkerGroup) {
		log.info("Entered in StatutorySetupsController saveWorkerGroup() ::  "+paramWorkerGroup);
		
		SimpleObject object = new SimpleObject();
		Integer budgetId = null;
		try{
			if (paramWorkerGroup != null){
				budgetId = statutorySetupsService.saveWorkerGroup(paramWorkerGroup);
				log.debug("Professional Tax Id: "+budgetId);
			}
		
			if (budgetId != null && budgetId > 0) {
				object.setId(budgetId);
				object.setName("Success");
			} else {
				object.setId(0);
				object.setName("Fail");
			}
			
		}catch(Exception e){
			object.setId(0);
			object.setName("Exception");
			log.error("Error Occured ",e);
			log.info("Exiting from StatutorySetupsController saveWorkerGroup() with exception ::  "+object+"  : "+e.getMessage());
		}
		log.info("Exiting from StatutorySetupsController saveWorkerGroup() ::  "+object);
		return new ResponseEntity<SimpleObject>(object, HttpStatus.OK);
	}
	
	/*
	 * This method will be used to get transaction dates list for worker group based on given customerId, companyId and uniqueId
	 */
	@RequestMapping(value = "/getTransactionDatesListForWorkerGroup.json", method = RequestMethod.POST)
	public ResponseEntity<List<SimpleObject>> getTransactionDatesListForWorkerGroup(@RequestBody DefineWorkerGroupVo paramWorkerGroup) throws JSONException {
		log.info("Entered in StatutorySetupsController getTransactionDatesListForBudget() ::  "+paramWorkerGroup);
		
		List<SimpleObject> simpleObjects =  new ArrayList<SimpleObject>();
		try{
			simpleObjects = statutorySetupsService.getTransactionDatesListForWorkerGroup(paramWorkerGroup.getCustomerId(), paramWorkerGroup.getCompanyId(), paramWorkerGroup.getUniqueId());	
			
		}catch(Exception e){
			log.error("Error Occured ",e);
			log.info("Exiting from StatutorySetupsController getTransactionDatesListForWorkerGroup() ::  "+simpleObjects);
		}
		log.info("Exiting from StatutorySetupsController getTransactionDatesListForWorkerGroup() ::  "+simpleObjects);
		return new ResponseEntity<List<SimpleObject>>(simpleObjects, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/validateWorkerGroupCode.json", method = RequestMethod.POST)
	public Integer validateWorkerGroupCode(@RequestBody DefineWorkerGroupVo paramWorkerGroup) throws JSONException{
		log.info("Entered in  StatutorySetupsController validateWorkerGroupCode() ::  "+paramWorkerGroup);
		Integer isValid = null;
		
		try{
			isValid = statutorySetupsService.validateWorkerGroupCode(paramWorkerGroup.getCustomerId(), paramWorkerGroup.getCompanyId(), paramWorkerGroup.getGroupCode());
			
		}catch(Exception e){
			log.error("Error Occured ",e);
			isValid = -1;
		}
		System.out.println(isValid);
			log.info("Exiting from  StatutorySetupsController validateWorkerGroupCode() ::  "+isValid);
			return isValid;
	}
	
	
	/*
	 * This method will be used to get the countries list,wage rates list, holiday calendar list, shift pattern list, time rules list and overtime rules list
	 */	
	@RequestMapping(value = "/getDropdownsForWorkerGroup.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getDropdownsForWorkerGroup(@RequestBody String paramString) throws JSONException {
		log.info("Entered in StatutorySetupsController  getDropdownsForWorkerGroup()  :: paramString =   "+paramString);
		Map<String,List> returnRules = new HashMap<String,List>();
		
		try{
			
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(paramString);
			log.debug("JSON Object:  "+jo);
			
			String companyId = null, customerId = null;
			if(jo.get("companyId") != null && jo.get("companyId").getAsString() != null){
				companyId = jo.get("companyId").getAsString();  
				log.debug("Company Id : "+companyId);
			}
			
			if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null){
				customerId = jo.get("customerId").getAsString();  
				log.debug("customer Id : "+customerId);
			}
		
			//List<SimpleObject> wageRateList = new ArrayList<SimpleObject>();
		    List<SimpleObject> shiftPatternList = shiftPatternService.getShiftPatternDropdown(Integer.parseInt(customerId), Integer.parseInt(companyId));
		    List<SimpleObject> holidayCalendarList = holidayCalendarService.getHolidayCalendarDropdown(Integer.parseInt(customerId), Integer.parseInt(companyId));
		    List<SimpleObject> timeRuleList = timeRulesService.getRuleCodesList(Integer.parseInt(customerId), Integer.parseInt(companyId));
		    List<SimpleObject> overTimeRuleList =  overTimeService.getOverTimeNamesByCompanyId(Integer.parseInt(customerId), Integer.parseInt(companyId));		    
		    List<SimpleObject> countryList = commonService.getCompanyCountries(Integer.parseInt(companyId));
			    	
			   
			returnRules.put("shiftPatternList", shiftPatternList);
			//returnRules.put("wageRateList", wageRateList);
			returnRules.put("countryList",countryList);
			returnRules.put("holidayCalendarList",holidayCalendarList);
			returnRules.put("timeRuleList",timeRuleList);
			returnRules.put("overTimeRuleList",overTimeRuleList);
		   
		
		}catch(Exception e){
			log.error("Error Occured ",e);
			log.info("Exiting from StatutorySetupsController getDropdownsForWorkerGroup() ::  "+returnRules);
		}
		log.info("Exiting from StatutorySetupsController getDropdownsForWorkerGroup() ::  "+returnRules);
		return new ResponseEntity<Map<String,List>>(returnRules, HttpStatus.OK);
	}

	
}


