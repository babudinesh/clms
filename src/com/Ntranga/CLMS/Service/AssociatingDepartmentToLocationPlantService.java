package com.Ntranga.CLMS.Service;

import java.util.List;

import com.Ntranga.CLMS.vo.AssociatingDepartmentToLocationPlantVo;
import com.Ntranga.CLMS.vo.DepartmentVo;
import com.Ntranga.CLMS.vo.SimpleObject;

public interface AssociatingDepartmentToLocationPlantService {

	public boolean associatingDepartmentToLocationAndPlantSaving(AssociatingDepartmentToLocationPlantVo associatingDepartmentToLocationPlantVo);

	public List<DepartmentVo> getDeparmentNamesAsDropDown(String customerId,String companyId,String LocationId, String PlantId, String departmentId);

	public List<SimpleObject> getSectionNamesAsDropDown(String customerId,String companyId,String locationId, String plantId, String departmentId);

}
