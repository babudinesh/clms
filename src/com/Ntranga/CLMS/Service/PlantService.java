package com.Ntranga.CLMS.Service;

import java.util.List;

import com.Ntranga.CLMS.vo.PlantVo;
import com.Ntranga.CLMS.vo.SimpleObject;

public interface PlantService {

	public Integer savePlant(PlantVo paramPlant);	
	
	public List<PlantVo>  getPlantById(Integer plantDetailsId);
	
	public List<PlantVo> getPlantsListBySearch(PlantVo paramPlant);
	
	public List<SimpleObject> getTransactionListForPlants(Integer customerId, Integer companyId, Integer plantId);
}
