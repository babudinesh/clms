package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.LocationDao;
import com.Ntranga.CLMS.vo.LocationCharacteristicsVo;
import com.Ntranga.CLMS.vo.LocationVo;
import com.Ntranga.CLMS.vo.SimpleObject;

@Service("locationService")
public class LocationServiceImpl implements LocationService {

	@Autowired
	private LocationDao locationDao;

	@Override
	public String saveLocation(LocationVo paramLocation) {
		return locationDao.saveLocation(paramLocation);
	}

	@Override
	public List<LocationVo> getLocationsList() {
		return locationDao.getLocationsList();
	}

	@Override
	public List<LocationVo> getLocationById(Integer locationId) {
		return locationDao.getLocationById(locationId);
	}

	@Override
	public List<LocationVo> getLocationsListBySearch(LocationVo paramLocation) {
		return locationDao.getLocationsListBySearch(paramLocation);
	}


	@Override
	public List<SimpleObject> getTransactionListForLocation(Integer customerId, Integer companyId, Integer locationId) {
		return locationDao.getTransactionListForLocation(customerId, companyId, locationId);
	}

	@Override
	public Integer saveLocationCharacteristics(LocationCharacteristicsVo paramLocation) {
		return locationDao.saveLocationCharacteristics(paramLocation);
	}
	
	@Override
	public List<LocationCharacteristicsVo> getLocationCharacteristicsByLocationId(Integer locationId){
		return locationDao.getLocationCharacteristicByLocationId(locationId);
	}

	@Override
	public List<LocationVo> getDepartmentList(Integer locationId) {
		// TODO Auto-generated method stub
		return locationDao.getDepartmentList(locationId);
	}
	
	public Integer validateLocationCode(LocationVo paramLocation){
		return locationDao.validateLocationCode(paramLocation);
	}
}
