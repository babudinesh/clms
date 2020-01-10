package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.PlantDao;
import com.Ntranga.CLMS.vo.PlantVo;
import com.Ntranga.CLMS.vo.SimpleObject;

@Service("plantService")
public class PlantserviceImpl implements PlantService {

	@Autowired
	PlantDao plantDao;
	
	@Override
	public Integer savePlant(PlantVo paramPlant) {
		return plantDao.savePlant(paramPlant);
	}

	@Override
	public List<PlantVo> getPlantById(Integer plantDetailsId) {
		return plantDao.getPlantById(plantDetailsId);
	}

	@Override
	public List<PlantVo> getPlantsListBySearch(PlantVo paramPlant) {
		return plantDao.getPlantsListBySearch(paramPlant);
	}

	@Override
	public List<SimpleObject> getTransactionListForPlants(Integer customerId,Integer companyId, Integer plantId) {
		return plantDao.getTransactionListForPlants(customerId, companyId, plantId);
	}

}
