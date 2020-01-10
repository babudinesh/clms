package com.Ntranga.CLMS.Dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Ntranga.CLMS.vo.MedicalTestVo;
import com.Ntranga.CLMS.vo.ScreenButtonsVo;
import com.Ntranga.CLMS.vo.SectorVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.core.CLMS.entities.MAddressType;
import com.Ntranga.core.CLMS.entities.MCompanyType;
import com.Ntranga.core.CLMS.entities.MContactType;
import com.Ntranga.core.CLMS.entities.MCountry;
import com.Ntranga.core.CLMS.entities.MCurrency;
import com.Ntranga.core.CLMS.entities.MDepartment;
import com.Ntranga.core.CLMS.entities.MEquipmentTypes;
import com.Ntranga.core.CLMS.entities.MHolidayType;
import com.Ntranga.core.CLMS.entities.MIndustry;
import com.Ntranga.core.CLMS.entities.MLanguage;
import com.Ntranga.core.CLMS.entities.MLocation;
import com.Ntranga.core.CLMS.entities.MMedicalTests;
import com.Ntranga.core.CLMS.entities.MProvidentFundTypes;
import com.Ntranga.core.CLMS.entities.MRegistrationActs;
import com.Ntranga.core.CLMS.entities.MSector;
import com.Ntranga.core.CLMS.entities.MSkillTests;
import com.Ntranga.core.CLMS.entities.MSkills;
import com.Ntranga.core.CLMS.entities.MTrainingTypes;

import common.Logger;

@SuppressWarnings({"unchecked", "rawtypes"})
@Transactional
@Repository("commonDao")
public class CommonDaoImpl implements CommonDao  {

	private static Logger log =
			Logger.getLogger(CommonDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	
	
	@Override
	public List<SimpleObject> getCompanySectors() {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> simpleObjects = new ArrayList<SimpleObject>(); 
		try {
			List<MCompanyType> tempList =  session.createQuery(" from MCompanyType ORDER BY companyType asc").list();					
			for(MCompanyType sector : tempList){				
				SimpleObject object = new SimpleObject();
				object.setId(sector.getCompanyTypeId());
				object.setName(sector.getCompanyType());
				simpleObjects.add(object);
			}
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return simpleObjects;
	}

	@Override
	public List<SimpleObject> getPFTypes() {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> simpleObjects = new ArrayList<SimpleObject>(); 
		try {
			List<MProvidentFundTypes> tempList =  session.createQuery(" from MProvidentFundTypes ORDER BY pfName asc").list();			
			for(MProvidentFundTypes fundTypes : tempList){				
				SimpleObject object = new SimpleObject();
				object.setId(fundTypes.getPfId());
				object.setName(fundTypes.getPfName());
				simpleObjects.add(object);
			}
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return simpleObjects;
	}

	@Override
	public List<SimpleObject> getRegistrationActs() {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> simpleObjects = new ArrayList<SimpleObject>();
		try {
			List<MRegistrationActs> tempList =  session.createQuery(" from MRegistrationActs ORDER BY registrationAct asc ").list();					

			for(MRegistrationActs acts : tempList){
				SimpleObject object = new SimpleObject();
				object.setId(acts.getRegistrationActId());
				object.setName(acts.getRegistrationAct());
				simpleObjects.add(object);
			}
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return simpleObjects;
	}
	
	@Override
	public List<SimpleObject> getIndustriesList() {

		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> industryList = new ArrayList<>();
		try {
			List<MIndustry> industryListTemp = (List<MIndustry>) session.createQuery("from MIndustry ORDER BY industryName asc").list();
			for (MIndustry industry : industryListTemp) {
				SimpleObject industryVo = new SimpleObject();
				industryVo.setId(industry.getIndustryId());
				industryVo.setName(industry.getIndustryName());
				industryList.add(industryVo);
			}

		} catch (Exception e) {
			log.error("Error Occured ",e);

		}

		return industryList;

	}

	@Override
	public List<SectorVo> getSectorList(int industryId) {
		Session session = sessionFactory.getCurrentSession();
		System.out.println("::session::" + session);
		List sectorList = new ArrayList<SectorVo>();
		try {
			Query query = session.createQuery("from MSector where MIndustry = '" + industryId + "' ORDER BY sectorName asc");
			List tempList = query.list();
			for(int i=0;i<tempList.size();i++){
				MSector mSector = (MSector) tempList.get(i);
				SectorVo sectorVo = new SectorVo();
				sectorVo.setSectorId(mSector.getSectorId());
				sectorVo.setSectorName(mSector.getSectorName());
				sectorList.add(sectorVo);
			}
			
			
		} catch (Exception e) {
			log.error("Error Occured ",e);

		}

		return sectorList;
	}
	
	@Override

	public List<SimpleObject> getCountriesList() {

		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> countriesList = new ArrayList<SimpleObject>();

		try {
			List<MCountry> countryListTemp = (List<MCountry>) session.createQuery("from MCountry ORDER BY countryName asc ").list();
			for (MCountry mcountry : countryListTemp) {
				SimpleObject countryVo = new SimpleObject();
				countryVo.setId(mcountry.getCountryId());
				countryVo.setName(mcountry.getCountryName());
				countriesList.add(countryVo);			

			}
		

		} catch (Exception e) {
			log.error("Error Occured ",e);
		}

		return countriesList;
	}


	public List<SimpleObject> getStatesList(Integer CountryId) {

		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> stateList = new ArrayList<SimpleObject>();
		try {
			List statesListTemp = session.createSQLQuery(
					"select State_Id,State_Name from m_state ms, m_country mc where ms.Country_Id = mc.Country_Id and mc.Country_Id ="
							+ CountryId+ " ORDER BY State_Name asc ")
					.list();

			for (Object o : statesListTemp) {
				Object[] obj = (Object[]) o;
				SimpleObject stateVo = new SimpleObject();
				stateVo.setId((Integer) obj[0]);
				stateVo.setName(obj[1] + "");
				stateList.add(stateVo);
			}
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}

		return stateList;
	}


	@Override
	public List<SimpleObject> getCitiesList(Integer stateId) {

		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> cityList = new ArrayList<SimpleObject>();

		try {
			List citiesLIstTemp = session.createSQLQuery(
					"select mc.`City_Id`,mc.`City_Name` from m_city mc ,m_state ms where ms.State_Id = mc.State_Id and ms.State_Id =" + stateId+" ORDER BY City_Name asc ")
					.list();
			for (Object o : citiesLIstTemp) {
				Object[] obj = (Object[]) o;
				SimpleObject cityVo = new SimpleObject();
				cityVo.setId((Integer) obj[0]);
				cityVo.setName(obj[1] + "");
				cityList.add(cityVo);
			}
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}

		return cityList;
	}

	@Override
	public List<SimpleObject> getAddressTypeList() {		
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> addressTypes = new ArrayList<SimpleObject>(); 
		try {
			List<MAddressType> tempList =  session.createQuery(" from MAddressType ORDER BY addressTypeName asc ").list();			
			for(MAddressType address : tempList){				
				SimpleObject object = new SimpleObject();
				object.setId(address.getAddressTypeId());
				object.setName(address.getAddressTypeName());
				addressTypes.add(object);
			}
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return addressTypes;
	}
	

	@Override
	public List<SimpleObject> getCurrencyList() {
		Session session = sessionFactory.getCurrentSession();				
		List<MCurrency> currency = new ArrayList<MCurrency>();
		List<SimpleObject> currencyList = new ArrayList<SimpleObject>(); 
		try {				
			currency  = (List<MCurrency>) session.createQuery("FROM MCurrency where isActive = 'Y'  ORDER BY currency asc").list();
			for(MCurrency mcurrency : currency){				
				SimpleObject object = new SimpleObject();
				object.setId(mcurrency.getCurrencyId());
				object.setName(mcurrency.getCurrency());
				currencyList.add(object);
			}
		} catch (Exception e) {
			log.error("Error Occured ",e);
			
		}
		
		return currencyList;
	}
	
	@Override
	public List<SimpleObject> getLanguageList() {
		Session session = sessionFactory.getCurrentSession();				
		List<MLanguage> language = new ArrayList<MLanguage>();
		List<SimpleObject> currencyList = new ArrayList<SimpleObject>(); 
		try {				
			language  = (List<MLanguage>) session.createQuery("FROM MLanguage where isActive = 'Y' ORDER BY language asc").list();
			for(MLanguage mlanguage : language){				
				SimpleObject object = new SimpleObject();
				object.setId(mlanguage.getLanguageId());
				object.setName(mlanguage.getLanguage());
				currencyList.add(object);
			}
		} catch (Exception e) {
			log.error("Error Occured ",e);
			
		}
		
		return currencyList;
	}

	/*
	 * This method will be used to get different contact tpes types
	 */
	@Override
	public List<SimpleObject> getContactTypeList() {
		Session session = sessionFactory.getCurrentSession();				

		List<SimpleObject> returnContacts = new ArrayList<SimpleObject>();
		try {
			List<MContactType> returnList =  session.createQuery(" from MContactType ORDER BY contactTypeName asc").list();			
			for(MContactType contact : returnList){				
				SimpleObject object = new SimpleObject();
				object.setId(contact.getContactTypeId());
				object.setName(contact.getContactTypeName());
				returnContacts.add(object);
			}

		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return returnContacts;
	}

	@Override
	public List<SimpleObject> getSkills() {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> simpleObjects = new ArrayList<SimpleObject>(); 
		try {
			List<MSkills> tempList =  session.createQuery(" from MSkills ORDER BY skillName asc").list();			
			for(MSkills skills : tempList){				
				SimpleObject object = new SimpleObject();
				object.setId(skills.getSkillId());
				object.setName(skills.getSkillName());
				simpleObjects.add(object);
			}
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return simpleObjects;
	}
	
	/*
	 * This method will be used to get different department types
	 */
	@Override
	public List<SimpleObject> getDepartmentTypes() {
		log.info("Entered in getDepartmentTypes() ...");
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> simpleObjects = new ArrayList<SimpleObject>(); 
		try{
		List<MDepartment> returnList = session.createQuery(" FROM MDepartment ORDER BY departmentTypeName asc").list();
		for(MDepartment dept : returnList){				
			SimpleObject object = new SimpleObject();
			object.setId(dept.getDepartmentTypeId());
			object.setName(dept.getDepartmentTypeName());
			simpleObjects.add(object);
		}
		}catch(Exception e){
			log.error("Error Occurred ... ", e);
			log.info("Exiting from getDepartmentTypes()  ::  "+simpleObjects);
		}
		log.info("Exiting from getDepartmentTypes()  ::  "+simpleObjects);
		return simpleObjects;
	}
	
	/*
	 * This method will be used to get different Location types
	 */
	@Override
	public List<SimpleObject> getLocationTypes() {
		log.info("Entered in getLocationTypes() ...");
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> simpleObjects = new ArrayList<SimpleObject>(); 
		try{
			List<MLocation>	returnList = session.createQuery("FROM MLocation ORDER BY locationTypeName asc").list();
			
			for(MLocation location : returnList){				
				SimpleObject object = new SimpleObject();
				object.setId(location.getLocationTypeId());
				object.setName(location.getLocationTypeName());
				simpleObjects.add(object);
			}
		}catch(Exception e){
			log.error("Error Occurred ... ", e);
			log.info("Exiting from getLocationTypes()  ::  "+simpleObjects);
		}
		log.info("Exiting from getLocationTypes()  ::  "+simpleObjects);
		return simpleObjects;
	}

	@Override
	public List<SimpleObject> getCompanyCountries(Integer companyId) {
				Session session = sessionFactory.getCurrentSession();
				List<SimpleObject> countryList = new ArrayList<SimpleObject>();
				try {
					List statesListTemp = session.createSQLQuery("SELECT DISTINCT country.country_id,country.country_name FROM `company_details_info` info INNER JOIN m_country country ON (info.country_id = country.country_id)  WHERE company_id ="+ companyId+ " ORDER BY country.country_name asc").list();
					for (Object o : statesListTemp) {
						Object[] obj = (Object[]) o;
						SimpleObject object = new SimpleObject();
						object.setId((Integer) obj[0]);
						object.setName(obj[1] + "");
						countryList.add(object);
					}
				} catch (Exception e) {
					log.error("Error Occured ",e);
				}

				return countryList;
			}

	
	/*
	 * This method will be used to get list of training types based on given job code 
	 */
	@Override
	public List<SimpleObject> getTrainingTypes(int jobCodeId) {
		log.info("Entered in getTrainingTypes()   ::  jobCodeId = "+jobCodeId);
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> simpleObjects = new ArrayList<SimpleObject>(); 
		try{
			List<MTrainingTypes>	returnList = session.createQuery("FROM MTrainingTypes WHERE jobCodeDetails = "+jobCodeId+" ORDER BY  trainingName asc").list();
			
			for(MTrainingTypes training : returnList){				
				SimpleObject object = new SimpleObject();
				object.setId(training.getTrainingId());
				object.setName(training.getTrainingName());
				simpleObjects.add(object);
			}
		}catch(Exception e){
			log.error("Error Occurred ... ", e);
			log.info("Exiting from getTrainingTypes()  ::  "+simpleObjects);
		}
		log.info("Exiting from getTrainingTypes()  ::  "+simpleObjects);
		return simpleObjects;
	}
	
	/*
	 * This method will be used to get list of equipment types based on given job code 
	 */
	@Override
	public List<SimpleObject> getEquipmentTypes(int jobCodeId) {
		log.info("Entered in getEquipmentTypes()   ::  jobCodeId = "+jobCodeId);
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> simpleObjects = new ArrayList<SimpleObject>(); 
		try{
			List<MEquipmentTypes>	returnList = session.createQuery("FROM MEquipmentTypes WHERE jobCodeDetails = "+jobCodeId+" ORDER BY  equipmentTypeName asc").list();
			
			for(MEquipmentTypes equipment : returnList){				
				SimpleObject object = new SimpleObject();
				object.setId(equipment.getEquipmentTypeId());
				object.setName(equipment.getEquipmentTypeName());
				simpleObjects.add(object);
			}
		}catch(Exception e){
			log.error("Error Occurred ... ", e);
			log.info("Exiting from getEquipmentTypes()  ::  "+simpleObjects);
		}
		log.info("Exiting from getEquipmentTypes()  ::  "+simpleObjects);
		return simpleObjects;
	}
	
	/*
	 * This method will be used to get list of medical tests based on given job code 
	 */
	@Override
	public List<MedicalTestVo> getMedicalTests(int jobCodeId) {
		log.info("Entered in getMedicalTests()   ::  jobCodeId = "+jobCodeId);
		Session session = sessionFactory.getCurrentSession();
		List<MedicalTestVo>	returnList = new ArrayList<MedicalTestVo>(); 
		try{
			List<MMedicalTests>	queryList = session.createQuery("FROM MMedicalTests WHERE jobCodeDetails = "+jobCodeId+" ORDER BY  medicalTestName asc").list();
			for(MMedicalTests test : queryList){				
				MedicalTestVo object = new MedicalTestVo();
				object.setId(test.getMedicalTestId());
				object.setName(test.getMedicalTestName());
				object.setIsPeriodic((test.getIsPeriodic() != null && test.getIsPeriodic().equalsIgnoreCase("Y")) ? true : false);
				object.setIsOnBoard((test.getIsOnBoard() != null && test.getIsOnBoard().equalsIgnoreCase("Y")) ? true : false);
				returnList.add(object);
			}
		}catch(Exception e){
			log.error("Error Occurred ... ", e);
			log.info("Exiting from getMedicalTests()  ::  "+returnList);
		}
		log.info("Exiting from getMedicalTests()  ::  "+returnList);
		return returnList;
	}
	
	/*
	 * This method will be used to get list of skill tests based on given job code 
	 */
	@Override
	public List<SimpleObject> getSkillTests(int jobCodeId) {
		log.info("Entered in getSkillTests()   ::  jobCodeId = "+jobCodeId);
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> simpleObjects = new ArrayList<SimpleObject>(); 
		try{
			List<MSkillTests>	returnList = session.createQuery("FROM MSkillTests WHERE jobCodeDetails = "+jobCodeId+" ORDER BY  skillTestName asc").list();
			
			for(MSkillTests skillTest : returnList){				
				SimpleObject object = new SimpleObject();
				object.setId(skillTest.getSkillTestId());
				object.setName(skillTest.getSkillTestName());
				simpleObjects.add(object);
			}
		}catch(Exception e){
			log.error("Error Occurred ... ", e);
			log.info("Exiting from getSkillTests()  ::  "+simpleObjects);
		}
		log.info("Exiting from getSkillTests()  ::  "+simpleObjects);
		return simpleObjects;
	}

	@Override
	public List<SimpleObject> getActiveJobCodes(Integer customerId, Integer companyId) {	
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> simpleObjects = new ArrayList<SimpleObject>(); 
		try{
			List statesListTemp = session.createSQLQuery(" SELECT parent.`Job_Code_Id`,CONCAT(`Job_Code`,' - ', `Job_Name`) FROM `job_code_details` parent  INNER JOIN `job_code_details_info` child ON (parent.`Job_Code_Id` = parent.`Job_Code_Id`) WHERE child.`Status`='Y'  AND CONCAT(DATE_FORMAT(child.`Transaction_Date`, '%Y%m%d'), child.`Job_Code_Sequence_Id`) = (SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), vdi1.`Job_Code_Sequence_Id`)) FROM job_code_details_info  vdi1 WHERE parent.Job_Code_Id = vdi1.Job_Code_Id AND vdi1.transaction_date <= CURRENT_DATE()) AND child.`Customer_Id`  ="+customerId+" AND child.`Company_Id`=  "+companyId+" ORDER BY  Job_Name asc").list();

			for (Object o : statesListTemp) {
				Object[] obj = (Object[]) o;				
				simpleObjects.add(new SimpleObject((Integer)obj[0],obj[1]+""));
			}
		}catch(Exception e){
			log.error("Error Occurred ... ", e);
			log.info("Exiting from getSkillTests()  ::  "+simpleObjects);
		}
		log.info("Exiting from getSkillTests()  ::  "+simpleObjects);
		return simpleObjects;
	}

	/*
	 * This method will be used to get holiday types
	 */
	@Override
	public List<SimpleObject> getHolidayTypes() {
		log.info("Entered in getHolidayTypes() ...");
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> simpleObjects = new ArrayList<SimpleObject>(); 
		try{
			List<MHolidayType>	returnList = session.createQuery("FROM MHolidayType ORDER BY holidayTypeName asc").list();
			
			for(MHolidayType holiday : returnList){				
				SimpleObject object = new SimpleObject();
				object.setId(holiday.getHolidayTypeId());
				object.setName(holiday.getHolidayTypeName());
				simpleObjects.add(object);
			}
		}catch(Exception e){
			log.error("Error Occurred ... ", e);
			log.info("Exiting from getHolidayTypes()  ::  "+simpleObjects);
		}
		log.info("Exiting from getHolidayTypes()  ::  "+simpleObjects);
		return simpleObjects;
	}

	@Override
	public List<SimpleObject> getLocationListByDept(Integer customerId, Integer companyId, Integer departmentId) {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> locationList = new ArrayList<SimpleObject>(); 
		try{
			List statesListTemp = session.createSQLQuery("SELECT loc.location_Id, CONCAT(loc.`Location_Code`,' - ', info.`Location_Name`) AS location FROM `location_department` dept  INNER JOIN location_details loc ON (dept.location_Id = loc.location_Id) INNER JOIN `location_details_info` info ON (info.location_Id = loc.location_Id) WHERE CONCAT(DATE_FORMAT(info.transaction_date, '%Y%m%d'), info.`Location_Sequence_Id`) =  ( SELECT  MAX(CONCAT(DATE_FORMAT(location1.transaction_date,'%Y%m%d'), location1.`Location_Sequence_Id`)) FROM location_details_info location1 WHERE info.`Location_Id` = location1.`Location_Id`  AND location1.transaction_date <= CURRENT_DATE()) AND dept.Department_Id ="+departmentId+"  AND info.`Customer_Id`  ="+customerId+" AND info.`Company_Id`=  "+companyId+" ORDER BY loc.Location_Code asc ").list();

			for (Object o : statesListTemp) {
				Object[] obj = (Object[]) o;				
				locationList.add(new SimpleObject((Integer)obj[0],obj[1]+""));
			}
		}catch(Exception e){
			log.error("Error Occurred ... ", e);
			log.info("Exiting from getSkillTests()  ::  "+locationList);
		}
		log.info("Exiting from getSkillTests()  ::  "+locationList);
		return locationList;
	}

	@Override
	public Map<String, List<SimpleObject>> getSectionAndWorkOrderListByLocation(Integer customerId, Integer companyId,
			Integer departmentId, Integer locationId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Map<String, List<SimpleObject>> list = new HashMap<String, List<SimpleObject>>();
		 
		try{
			List<SimpleObject> sectionList = new ArrayList<SimpleObject>();
			List<SimpleObject> workOrderList = new ArrayList<SimpleObject>();
			List sectionTempList = session.createSQLQuery("SELECT child.`Section_Id`, CONCAT(parent.`Section_Code`,' - ', child.`Section_Name`) AS sname FROM `section_details_info`  child LEFT JOIN `section_details`  parent  ON parent.`Section_Id` = child.`Section_Id`  WHERE CONCAT(DATE_FORMAT(child.transaction_date, '%Y%m%d'), child.`Sequence_Id`) =  (SELECT MAX(CONCAT(DATE_FORMAT(child1.transaction_date, '%Y%m%d'), child1.`Sequence_Id`))  FROM section_details_info child1 WHERE child.`Section_Id` = child1.`Section_Id`  AND child1.transaction_date <= CURRENT_DATE() )  AND child.`Customer_Id`="+customerId+" AND child.`Company_Id`="+companyId+"   AND child.`Location_Id`= "+locationId+  " ORDER BY Section_Code asc").list();

			for (Object o : sectionTempList) {
				Object[] obj = (Object[]) o;				
				sectionList.add(new SimpleObject((Integer)obj[0],obj[1]+""));
			}
			list.put("sectionList", sectionList);
			
			List workOrderTempList = session.createSQLQuery("SELECT  wd.`WorkOrder_id`,CONCAT(`WorkOrder_Code`,' - ',`Work_Order_Name`) AS wName FROM  `workrorder_detail` wd  INNER JOIN workorder_detail_info wdi ON (wd.`WorkOrder_id` = wdi.`WorkOrder_id`)  WHERE CONCAT(DATE_FORMAT(wdi.transaction_date, '%Y%m%d'), wdi.`Sequence_Id`) =  ( SELECT MAX(CONCAT(DATE_FORMAT(wdi1.transaction_date, '%Y%m%d'), wdi1.`Sequence_Id`))   FROM workorder_detail_info wdi1  WHERE wdi.`WorkOrder_id` = wdi1.`WorkOrder_id`  AND wdi1.transaction_date <= CURRENT_DATE() ) AND wdi.is_active = 'Y' AND wdi.`Customer_Id`="+customerId+"  AND wdi.`Company_Id`="+companyId+"  AND wdi.`Location`="+locationId+"   AND wdi.`Department`= "+departmentId +" ORDER BY WorkOrder_Code asc").list();
			
			for (Object o : workOrderTempList) {
				Object[] obj = (Object[]) o;				
				workOrderList.add(new SimpleObject((Integer)obj[0],obj[1]+""));
			}
			list.put("workOrderList", workOrderList);
			
		}catch(Exception e){
			log.error("Error Occurred ... ", e);
			log.info("Exiting from getSectionAndWorkOrderListByLocation()  ::  "+list);
		}
		log.info("Exiting from getSectionAndWorkOrderListByLocation()  ::  "+list);
		return list;
	}

	@Override
	public ScreenButtonsVo getUserScreenButtons(ScreenButtonsVo screenButtonsVo) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		ScreenButtonsVo buttonsVo = new ScreenButtonsVo();
		try{
			
			String query = " SELECT GROUP_CONCAT( DISTINCT msa.action_name) AS actionNames, GROUP_CONCAT( DISTINCT msa.`master_screen_action_id`) AS actionIds "
			 +"  FROM `user_role_mapping` urm INNER JOIN users u ON (urm.user_id = u.user_id AND urm.is_active = 'Y')  "
			 +" INNER JOIN `roles` r ON (r.role_id = urm.role_id)  "
			 +" INNER JOIN `role_permissiongroup_mapping` rpm ON(urm.`role_id` = rpm.`role_id` AND urm.`is_active` = rpm.`is_active` AND rpm.`is_active` = 'Y') " 
			 +" INNER JOIN `screen_permissions_group_mapping` spgm ON(spgm.`permissions_group_id` = rpm.`permissions_group_id` AND spgm.`is_active` = rpm.`is_active` AND spgm.`is_active` = 'Y') " 
			 +" INNER JOIN `screen_actions_mapping`  sam ON(sam.`screen_action_mapping_id` = spgm.`screen_action_mapping_id` AND sam.`is_active` = spgm.`is_active` AND sam.`is_active` = 'Y') " 
			 +" INNER JOIN `screens_list` sl ON (sl.`screen_id` = sam.`screen_id`) " 
			 +" INNER JOIN `m_screen_actions` msa ON (msa.`master_screen_action_id` = sam.`master_screen_action_id`) " 
			 +" WHERE u.user_name='"+screenButtonsVo.getUserName()+"' AND sl.screen_name='"+screenButtonsVo.getScreenName()+"' ";
			 System.out.println(query);
			List statesListTemp = session.createSQLQuery(query).list();

			for (Object o : statesListTemp) {
				Object[] obj = (Object[]) o;
				if(obj[0] != null &&  (obj[0]+"").contains(","))
					buttonsVo.setScreenButtonNamesArray((obj[0]+"").split(",") );
				else if(obj[0] != null)
					buttonsVo.setScreenButtonNamesArray( new String[]{obj[0]+""});
				
				if(obj[1] != null &&  (obj[1]+"").contains(","))
					buttonsVo.setScreenButtonIdsArray((obj[1]+"").split(",") );
				else if(obj[1] != null)
					buttonsVo.setScreenButtonIdsArray( new String[]{obj[1]+""});
				
			}
		}catch(Exception e){
			log.error("Error Occurred ... ", e);
		}
		return buttonsVo;
	}

	@Override
	public List<SimpleObject> getRoles(Integer customerId, Integer companyId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> rolesList = new ArrayList<SimpleObject>(); 
		try{
			String query = "SELECT role_Id, role_name FROM roles where 1>0  ";
			if(customerId != null && customerId >0){
				query+= " and customer_id ="+customerId;
			}
			if(companyId != null && companyId >0){
				query+= " and company_id ="+companyId;
			}
			query+= " order by role_name ";
			List statesListTemp = session.createSQLQuery(query).list();

			for (Object o : statesListTemp) {
				Object[] obj = (Object[]) o;				
				rolesList.add(new SimpleObject((Integer)obj[0],obj[1]+""));
			}
		}catch(Exception e){
			log.error("Error Occurred ... ", e);
			log.info("Exiting from getRoles()  ::  "+rolesList);
		}
		log.info("Exiting from getRoles()  ::  "+rolesList);
		return rolesList;
	}

	@Override
	public List<SimpleObject> getUsersByRoleId(Integer roleId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> locationList = new ArrayList<SimpleObject>(); 
		try{
			List statesListTemp = session.createSQLQuery("SELECT DISTINCT u.user_id, u.user_name FROM users u INNER JOIN `user_role_mapping` urm ON (u.user_id = urm.user_id) WHERE role_id ="+roleId+" ORDER BY user_name ").list();

			for (Object o : statesListTemp) {
				Object[] obj = (Object[]) o;				
				locationList.add(new SimpleObject((Integer)obj[0],obj[1]+""));
			}
		}catch(Exception e){
			log.error("Error Occurred ... ", e);
			log.info("Exiting from getSkillTests()  ::  "+locationList);
		}
		log.info("Exiting from getSkillTests()  ::  "+locationList);
		return locationList;
	}

	
}
