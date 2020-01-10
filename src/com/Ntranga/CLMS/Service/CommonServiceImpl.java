package com.Ntranga.CLMS.Service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.CommonDao;
import com.Ntranga.CLMS.vo.MedicalTestVo;
import com.Ntranga.CLMS.vo.ScreenButtonsVo;
import com.Ntranga.CLMS.vo.SectorVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.UsersVo;



@Service("commonService")
public class CommonServiceImpl implements CommonService {

	@Autowired
	private  CommonDao commonDao;
	
	
	@Override
	public List<SimpleObject> getCompanySectors() {
		return commonDao.getCompanySectors();
	}

	@Override
	public List<SimpleObject> getPFTypes() {
		return commonDao.getPFTypes();
	}

	@Override
	public List<SimpleObject> getRegistrationActs() {
		return commonDao.getRegistrationActs();
	}

	@Override
	public List<SimpleObject> getIndustriesList() {
		return commonDao.getIndustriesList();
	}

	@Override
	public List<SectorVo> getSectorList(int industryId) {
		return commonDao.getSectorList(industryId);
	}

	@Override
	public List<SimpleObject> getCurrencyList(){
		return commonDao.getCurrencyList();
	}
	
	@Override
	public List<SimpleObject> getLanguageList(){		
		return commonDao.getLanguageList();
	}
	
	@Override
	public List<SimpleObject> getCountriesList() {
		return commonDao.getCountriesList();
	}

	@Override
	public List<SimpleObject> getStatesList(Integer CountryId) {
		return commonDao.getStatesList(CountryId);
	}

	@Override
	public List<SimpleObject> getCitiesList(Integer cityId) {
		return commonDao.getCitiesList(cityId);
	}

	@Override
	public List<SimpleObject> getAddressTypeList() {
		return commonDao.getAddressTypeList();
	}

	@Override
	public List<SimpleObject> getContactTypeList() {
		return commonDao.getContactTypeList();
	}

	@Override
	public List<SimpleObject> getSkills() {
		return commonDao.getSkills();
	}
	
	@Override
	public List<SimpleObject> getDepartmentTypes() {
		return commonDao.getDepartmentTypes();
	}
	
	@Override
	public List<SimpleObject> getLocationTypes() {
		return commonDao.getLocationTypes();
	}


	@Override
	public List<SimpleObject> getTrainingTypes(int jobCodeId) {
		return commonDao.getTrainingTypes(jobCodeId);
	}

	@Override
	public List<SimpleObject> getEquipmentTypes(int jobCodeId) {
		return commonDao.getEquipmentTypes(jobCodeId);
	}

	@Override
	public List<MedicalTestVo> getMedicalTests(int jobCodeId) {
		return commonDao.getMedicalTests(jobCodeId);
	}

	@Override
	public List<SimpleObject> getSkillTests(int jobCodeId) {
		return commonDao.getSkillTests(jobCodeId);
	}


	@Override
	public List<SimpleObject> getCompanyCountries(Integer companyId) {
		return commonDao.getCompanyCountries(companyId);
	}

	@Override
	public List<SimpleObject> getActiveJobCodes(Integer customerId, Integer companyId) {
		return commonDao.getActiveJobCodes( customerId,  companyId);
	}

	@Override
	public List<SimpleObject> getHolidayTypes() {
		return commonDao.getHolidayTypes();
	}

	@Override
	public List<SimpleObject> getLocationListByDept(Integer customerId, Integer companyId, Integer departmentId) {
		// TODO Auto-generated method stub
		return commonDao.getLocationListByDept(customerId, companyId, departmentId);
	}

	@Override
	public Map<String, List<SimpleObject>> getSectionAndWorkOrderListByLocation(Integer customerId, Integer companyId,
			Integer departmentId, Integer locationId) {
		// TODO Auto-generated method stub
		return commonDao.getSectionAndWorkOrderListByLocation(customerId, companyId, departmentId, locationId);
	}

	@Override
	public ScreenButtonsVo getUserScreenButtons(ScreenButtonsVo screenButtonsVo) {
		// TODO Auto-generated method stub
		return commonDao.getUserScreenButtons(screenButtonsVo);
	}

	@Override
	public List<SimpleObject> getRoles(Integer customerId, Integer companyId) {
		// TODO Auto-generated method stub
		return commonDao.getRoles( customerId, companyId);
	}

	@Override
	public List<SimpleObject> getUsersByRoleId(Integer roleId) {
		// TODO Auto-generated method stub
		return commonDao.getUsersByRoleId(roleId);
	}

	
	
	@Override
	public List<SimpleObject> getLocationsByCompanyId(UsersVo usrVo) {
		// TODO Auto-generated method stub
		//return commonDao.getLocationsByCompanyId(usrVo);
		return null;
	}

	@Override
	public List<SimpleObject> getPlantsByLocationId(UsersVo usrVo) {
		// TODO Auto-generated method stub
		//return commonDao.getPlantsByLocationId(usrVo);
		return null;
	}

	@Override
	public List<SimpleObject> getDepartmentsByLocationAndPlant(UsersVo usrVo) {
		// TODO Auto-generated method stub
		//return commonDao.getDepartmentsByLocationAndPlant(usrVo);
		return null;
	}

	@Override
	public List<SimpleObject> getSectionDetailsByLocationAndPlantAndDeptId(UsersVo usrVo) {
		// TODO Auto-generated method stub
		//return commonDao.getSectionDetailsByLocationAndPlantAndDeptId(usrVo);
		return null;
	}

	@Override
	public List<SimpleObject> getWorkAreasBySectionesAndLocationAndPlantAndDept(UsersVo usrVo) {
		// TODO Auto-generated method stub
		//return commonDao.getWorkAreasBySectionesAndLocationAndPlantAndDept(usrVo);
		return null;
	}

	@Override
	public List<SimpleObject> getLocationsByVendorId(UsersVo usrVo) {
		// TODO Auto-generated method stub
		//return commonDao.getLocationsByVendorId(usrVo);
		return null;
	}

	

}
