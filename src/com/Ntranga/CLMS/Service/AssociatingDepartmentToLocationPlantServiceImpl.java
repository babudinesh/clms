package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.AssociatingDepartmentToLocationPlantDao;
import com.Ntranga.CLMS.vo.AssociatingDepartmentToLocationPlantVo;
import com.Ntranga.CLMS.vo.DepartmentVo;
import com.Ntranga.CLMS.vo.SimpleObject;




@Service("associatingDepartmentToLocationPlantService")
public class AssociatingDepartmentToLocationPlantServiceImpl implements AssociatingDepartmentToLocationPlantService {

	@Autowired
	private  AssociatingDepartmentToLocationPlantDao associatingDepartmentToLocationPlantDao;

	@Override
	public boolean associatingDepartmentToLocationAndPlantSaving(
			AssociatingDepartmentToLocationPlantVo associatingDepartmentToLocationPlantVo) {
		// TODO Auto-generated method stub
		return associatingDepartmentToLocationPlantDao.associatingDepartmentToLocationAndPlantSaving(associatingDepartmentToLocationPlantVo);
	}

	@Override
	public List<DepartmentVo> getDeparmentNamesAsDropDown(String customerId, String companyId, String LocationId,
			String PlantId, String departmentId) {
		// TODO Auto-generated method stub
		return associatingDepartmentToLocationPlantDao.getDeparmentNamesAsDropDown(customerId, companyId, LocationId, PlantId);
		//return null;
	}

	@Override
	public List<SimpleObject> getSectionNamesAsDropDown(String customerId, String companyId, String locationId, String plantId, String departmentId) {
		return associatingDepartmentToLocationPlantDao.getSectionNamesAsDropDown(customerId, companyId, locationId, plantId, departmentId);
	}

	
	

}
