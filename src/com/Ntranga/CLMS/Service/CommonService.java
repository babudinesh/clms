package com.Ntranga.CLMS.Service;


import java.util.List;
import java.util.Map;

import com.Ntranga.CLMS.vo.MedicalTestVo;
import com.Ntranga.CLMS.vo.ScreenButtonsVo;
import com.Ntranga.CLMS.vo.SectorVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.UsersVo;




public interface CommonService {
	
	public List<SimpleObject> getCompanySectors();
	
	public List<SimpleObject> getPFTypes();
	
	public List<SimpleObject> getSkills();
	
	public List<SimpleObject> getRegistrationActs();
	
	public List<SimpleObject> getIndustriesList();

	public List<SectorVo> getSectorList(int industryId);
	
	public List<SimpleObject> getCountriesList();
	
	public List<SimpleObject> getStatesList(Integer CountryId);
	
	public List<SimpleObject> getCitiesList(Integer cityId);
	
	public List<SimpleObject> getAddressTypeList();
	
	public List<SimpleObject> getCurrencyList();
	
	public List<SimpleObject> getLanguageList();
	
	public List<SimpleObject> getContactTypeList();
	
	public List<SimpleObject> getDepartmentTypes();
	
	public List<SimpleObject> getLocationTypes();
	

	public List<SimpleObject> getTrainingTypes(int jobCodeId);
	
	public List<SimpleObject> getEquipmentTypes(int jobCodeId);
	
	public List<MedicalTestVo> getMedicalTests(int jobCodeId);
	
	public List<SimpleObject> getSkillTests(int jobCodeId);
	

	public List<SimpleObject> getCompanyCountries(Integer companyId);
	
	public List<SimpleObject> getActiveJobCodes(Integer customerId, Integer companyId);
	
	public List<SimpleObject> getHolidayTypes();

	public List<SimpleObject> getLocationListByDept(Integer customerId, Integer companyId, Integer departmentId);

	public Map<String,List<SimpleObject>> getSectionAndWorkOrderListByLocation(Integer customerId, Integer companyId, Integer departmentId, Integer locationId);

	
	public ScreenButtonsVo getUserScreenButtons(ScreenButtonsVo screenButtonsVo);

	public List<SimpleObject> getRoles(Integer customerId, Integer companyId);

	public List<SimpleObject> getUsersByRoleId(Integer roleId);
	
	
	// For Locations 
	
	public List<SimpleObject> getLocationsByCompanyId(UsersVo usrVo);
	
	public List<SimpleObject> getLocationsByVendorId(UsersVo usrVo);
	
	public List<SimpleObject> getPlantsByLocationId(UsersVo usrVo);
	
	public List<SimpleObject> getDepartmentsByLocationAndPlant(UsersVo usrVo);
	
	public List<SimpleObject> getSectionDetailsByLocationAndPlantAndDeptId(UsersVo usrVo);
	
	public List<SimpleObject> getWorkAreasBySectionesAndLocationAndPlantAndDept(UsersVo usrVo); 
	

}
