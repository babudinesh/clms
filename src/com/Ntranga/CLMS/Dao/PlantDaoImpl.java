package com.Ntranga.CLMS.Dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Ntranga.CLMS.vo.PlantVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.core.DateHelper;
import com.Ntranga.core.CLMS.entities.CompanyDetails;
import com.Ntranga.core.CLMS.entities.CustomerDetails;
import com.Ntranga.core.CLMS.entities.LocationDetails;
import com.Ntranga.core.CLMS.entities.MCountry;
import com.Ntranga.core.CLMS.entities.MState;
import com.Ntranga.core.CLMS.entities.PlantDetails;
import com.Ntranga.core.CLMS.entities.PlantDetailsInfo;
import common.Logger;

@Transactional
@Repository("plantDao")
@SuppressWarnings("rawtypes")
public class PlantDaoImpl implements PlantDao {

	private static Logger log = Logger.getLogger(PlantDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Integer savePlant(PlantVo paramPlant) {
		log.info("Entered in savePlant()  ::   "+paramPlant);

		Session session = sessionFactory.getCurrentSession();
		Integer plantDetailsId = 0;
		Integer plantId = 0;
		BigInteger plantSequenceId = new BigInteger("0");	
		
		
		PlantDetails plantDetails = new PlantDetails();
		PlantDetailsInfo plantDetailsInfo = new PlantDetailsInfo();
		
		try{									
			if(paramPlant != null && paramPlant.getPlantId() > 0){
				
				plantDetails = (PlantDetails) session.load(PlantDetails.class, paramPlant.getPlantId());
				plantDetails.setCustomerDetails(new CustomerDetails(paramPlant.getCustomerId()));
				plantDetails.setCompanyDetails(new CompanyDetails(paramPlant.getCompanyId()));
				plantDetails.setPlantCode(paramPlant.getPlantCode().toUpperCase());
				plantDetails.setModifiedBy(paramPlant.getModifiedBy());
				plantDetails.setModifiedDate(new Date());
				session.update(plantDetails);
				plantId  = paramPlant.getPlantId();
			}else{
				
				plantDetails = new PlantDetails();
				plantDetails.setCustomerDetails(new CustomerDetails(paramPlant.getCustomerId()));
				plantDetails.setCompanyDetails(new CompanyDetails(paramPlant.getCompanyId()));
				plantDetails.setPlantCode(paramPlant.getPlantCode().toUpperCase());
				plantDetails.setCreatedBy(paramPlant.getCreatedBy());
				plantDetails.setModifiedBy(paramPlant.getModifiedBy());
				plantDetails.setCreatedDate(new Date());
				plantDetails.setModifiedDate(new Date());
				plantId = (Integer) session.save(plantDetails);
			}
			
			session.clear();
			//plant details info table
			if(paramPlant != null &&  paramPlant.getPlantDetailsId() > 0){
				plantDetailsInfo = (PlantDetailsInfo) sessionFactory.getCurrentSession().load(PlantDetailsInfo.class, paramPlant.getPlantDetailsId());
				plantDetailsInfo.setPlantDetails(new PlantDetails(plantId));
				plantDetailsInfo.setCustomerDetails(new CustomerDetails(paramPlant.getCustomerId()));
				plantDetailsInfo.setCompanyDetails(new CompanyDetails(paramPlant.getCompanyId()));
				plantDetailsInfo.setPlantName(paramPlant.getPlantName());
				plantDetailsInfo.setShortName(paramPlant.getShortName());
				plantDetailsInfo.setContactName(paramPlant.getContactName());
				plantDetailsInfo.setEmailId(paramPlant.getEmailId());
				plantDetailsInfo.setPhoneNumber(paramPlant.getPhoneNumber());
				plantDetailsInfo.setFaxNumber(paramPlant.getFaxNumber());
				plantDetailsInfo.setAddress1(paramPlant.getAddress1());
				plantDetailsInfo.setAddress2(paramPlant.getAddress2());
				plantDetailsInfo.setAddress3(paramPlant.getAddress3());
				plantDetailsInfo.setMCountry(new MCountry(paramPlant.getCountryId()));
				plantDetailsInfo.setMState(new MState((paramPlant.getStateId()!= null && paramPlant.getStateId() > 0) ? paramPlant.getStateId() : null));
				plantDetailsInfo.setCityName(paramPlant.getCityName());
				plantDetailsInfo.setPincode(paramPlant.getPincode());
				plantDetailsInfo.setLocationDetails(new LocationDetails(paramPlant.getLocationId()));
				plantDetailsInfo.setStatus(paramPlant.getStatus());
				plantDetailsInfo.setTransactionDate(paramPlant.getTransactionDate());
				plantDetailsInfo.setModifiedBy(paramPlant.getModifiedBy());
				plantDetailsInfo.setModifiedDate(new Date());
				session.update(plantDetailsInfo);
				plantDetailsId = plantDetailsInfo.getPlantDetailsId();
			}else{	
				plantDetailsInfo = new PlantDetailsInfo();
				plantDetailsInfo.setPlantDetails(new PlantDetails(plantDetails.getPlantId()));
				plantDetailsInfo.setCustomerDetails(new CustomerDetails(paramPlant.getCustomerId()));
				plantDetailsInfo.setCompanyDetails(new CompanyDetails(paramPlant.getCompanyId()));
				plantDetailsInfo.setPlantName(paramPlant.getPlantName());
				plantDetailsInfo.setShortName(paramPlant.getShortName());
				plantDetailsInfo.setContactName(paramPlant.getContactName());
				plantDetailsInfo.setEmailId(paramPlant.getEmailId());
				plantDetailsInfo.setPhoneNumber(paramPlant.getPhoneNumber());
				plantDetailsInfo.setFaxNumber(paramPlant.getFaxNumber());
				plantDetailsInfo.setAddress1(paramPlant.getAddress1());
				plantDetailsInfo.setAddress2(paramPlant.getAddress2());
				plantDetailsInfo.setAddress3(paramPlant.getAddress3());
				plantDetailsInfo.setMCountry(new MCountry(paramPlant.getCountryId()));
				plantDetailsInfo.setMState(new MState((paramPlant.getStateId()!= null && paramPlant.getStateId() > 0) ? paramPlant.getStateId() : null));
				plantDetailsInfo.setCityName(paramPlant.getCityName());
				plantDetailsInfo.setPincode(paramPlant.getPincode());
				plantDetailsInfo.setLocationDetails(new LocationDetails(paramPlant.getLocationId()));
				plantDetailsInfo.setStatus(paramPlant.getStatus());
				plantDetailsInfo.setTransactionDate(paramPlant.getTransactionDate());
				plantDetailsInfo.setCreatedBy(paramPlant.getModifiedBy());
				plantDetailsInfo.setModifiedBy(paramPlant.getModifiedBy());
				plantDetailsInfo.setCreatedDate(new Date());
				plantDetailsInfo.setModifiedDate(new Date());
				
				if(plantId != null && plantId > 0){
					plantSequenceId  = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Plant_Sequence_Id),0) FROM `plant_details_info` plant"
							+ "  WHERE  plant.Plant_Id = "+paramPlant.getPlantId() +" AND plant.`Transaction_Date` = '"+DateHelper.convertDateToSQLString(paramPlant.getTransactionDate())+"' and plant.Customer_Id='"+paramPlant.getCustomerId()+"' And plant.Company_Id = '"+paramPlant.getCompanyId()+"'").list().get(0);
					log.info("Plant sequence Id : "+plantSequenceId);
				}	
				plantDetailsInfo.setPlantSequenceId(plantSequenceId.intValue()+1);			
				plantDetailsId = (Integer) session.save(plantDetailsInfo);
			}
			session.clear();
			session.flush();
		}catch (Exception e) {
			 if(!(session.getTransaction().wasRolledBack())){
				 session.getTransaction().rollback();
			 }
			 log.error("Error Occurred...   ", e);
			 log.info("Exiting from savePlant()  ::  plantDetailsId =  "+plantDetailsId);
		}				
		log.info("Exiting from savePlant()  ::  plantDetailsId =  "+plantDetailsId);
		return plantDetailsId;
	}

	@Override
	public List<PlantVo> getPlantById(Integer plantDetailsId) {
		log.info("Entered in  getPlantById()  ::   plantDetailsId = "+plantDetailsId);
		Session session = sessionFactory.getCurrentSession();
		PlantVo plant = new PlantVo();
		List<PlantVo> returnList = new ArrayList<PlantVo>();
		String  hqlQuery = " SELECT info.Customer_Id, info.Company_Id, info.Plant_Details_Id, info.Plant_Id, plant.Plant_Code, "
							+ "	info.Plant_Name, info.Short_Name, info.Contact_Name, info.Email_Id, info.Phone_Number, info.Fax_Number, "
							+ " info.Address_1, info.Address_2, info.Address_3, info.Country_Id, info.State_Id, info.City_Name, info.Pincode, "
							+ " info.Location_Id, info.Transaction_Date, info.Status"							
							+ " FROM plant_details_info AS info "
							+ " LEFT JOIN plant_details plant ON plant.Plant_Id = info.Plant_Id"
							+ " LEFT JOIN company_details AS cmp  ON info.Customer_Id = cmp.Customer_Id  "
																			+ "	AND info.Company_Id = cmp.Company_Id "
							+ " LEFT JOIN company_details_info AS company  ON company.Customer_Id = info.Customer_Id  "
																			+ "	AND company.Company_Id = info.Company_Id "
							+ " LEFT JOIN customer_details AS cus  ON info.Customer_Id = cus.Customer_Id "
							+ " LEFT JOIN customer_details_info AS customer  ON customer.Customer_Id = info.Customer_Id "
							+ " WHERE info.Plant_Details_Id = "+plantDetailsId;
		
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			List plantList = query.list();

			for (Object object : plantList) {
				Object[] obj = (Object[]) object;
				plant = new PlantVo();
				plant.setCustomerId((Integer)obj[0]);
				plant.setCompanyId((Integer)obj[1]);
				plant.setPlantDetailsId((Integer)obj[2]);
				plant.setPlantId((Integer)obj[3]);
				plant.setPlantCode((String)obj[4]);
				plant.setPlantName((String)obj[5]);
				plant.setShortName((String)obj[6]);
				plant.setContactName((String)obj[7]);
				plant.setEmailId((String)obj[8]);
				plant.setPhoneNumber((String)obj[9]);
				plant.setFaxNumber((String)obj[10]);
				plant.setAddress1((String)obj[11]);
				plant.setAddress2((String)obj[12]);
				plant.setAddress3((String)obj[13]);
				plant.setCountryId((Integer)obj[14]);
				plant.setStateId((Integer)obj[15]);
				plant.setCityName((String)obj[16]);
				plant.setPincode((Integer)obj[17]);
				plant.setLocationId((Integer)obj[18]);
				plant.setTransactionDate(DateHelper.convertSQLDateToDate((java.sql.Date)obj[19]));
				plant.setStatus(obj[20]+"");
				
				returnList.add(plant);
			}	
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from  getPlantById()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from  getPlantById()  ::   "+returnList);
		return returnList;
	}

	@Override
	public List<PlantVo> getPlantsListBySearch(PlantVo paramPlant) {
		log.info("Entered in  getPlantsListBySearch()  ::   plantVo  = "+paramPlant);
		
		Session session = sessionFactory.getCurrentSession();
		List queryList = null;
		List<PlantVo> returnList = new ArrayList<PlantVo>();
		PlantVo plant = new PlantVo();
		
		String hqlQuery = "SELECT info.Customer_Id, info.Company_Id, company.Company_Name, customer.Customer_Name, "
							+ "	info.Plant_Id,  info.Plant_Name, location.Location_Name, "
							+ "	info.Transaction_Date, info.Status, info.Plant_Details_Id , plant.Plant_Code"
							+ " FROM plant_details_info AS info "
							+ " LEFT JOIN plant_details plant ON plant.Plant_Id = info.Plant_Id "

							+ " LEFT JOIN company_details_info AS company  ON company.Customer_Id = info.Customer_Id  "
																	+ "	AND company.Company_Id = info.Company_Id "
																	+ "	AND  CONCAT(DATE_FORMAT(company.Transaction_Date, '%Y%m%d'), company.Company_Sequence_Id) = ( "
																	+"	SELECT MAX(CONCAT(DATE_FORMAT(company1.Transaction_Date, '%Y%m%d'), company1.Company_Sequence_Id)) "
																	+"	FROM company_details_info company1 "
																	+"	WHERE company.Company_Id = company1.Company_Id AND company1.Transaction_Date <= CURRENT_DATE() "
																	+ " ) "	
						
							+ " LEFT JOIN customer_details_info AS customer  ON customer.Customer_Id = info.Customer_Id "
																	+ " AND  CONCAT(DATE_FORMAT(customer.Transaction_Date, '%Y%m%d'), customer.Customer_Sequence_Id) =  ( "
																	+"	SELECT MAX(CONCAT(DATE_FORMAT(customer1.Transaction_Date, '%Y%m%d'), customer1.Customer_Sequence_Id)) "
																	+"	FROM customer_details_info customer1 "
																	+"	WHERE customer.Customer_Id = customer1.Customer_Id AND customer1.Transaction_Date <= CURRENT_DATE() "
																	+"	) "
							
							+ " LEFT JOIN location_details_info location ON  location.Location_Id = info.Location_Id "
																	+ " AND  CONCAT(DATE_FORMAT(location.Transaction_Date, '%Y%m%d'), location.Location_Sequence_Id) =  ( "
																	+"	SELECT MAX(CONCAT(DATE_FORMAT(location1.Transaction_Date, '%Y%m%d'), location1.Location_Sequence_Id)) "
																	+"	FROM location_details_info location1 "
																	+"	WHERE location.Location_Id = location1.Location_Id AND location1.Transaction_Date <= CURRENT_DATE() "
																	+"	) "

								+ " WHERE  customer.Is_Active = 'Y' "
								+ " AND company.Is_Active = 'Y' "
								+" AND location.Status = 'Y' "
								+ " AND  CONCAT(DATE_FORMAT(info.Transaction_Date, '%Y%m%d'), info.Plant_Sequence_Id) =  ( "
								+"	SELECT MAX(CONCAT(DATE_FORMAT(info1.Transaction_Date, '%Y%m%d'), info1.Plant_Sequence_Id)) "
								+"	FROM plant_details_info info1 "
								+"	WHERE info.Plant_Id = info1.Plant_Id AND info1.Transaction_Date <= CURRENT_DATE() "
								+"	) ";
			
		if(paramPlant.getCustomerId() > 0){
			hqlQuery += "  AND  info.Customer_Id = "+paramPlant.getCustomerId();
		}
		
		if(paramPlant.getCompanyId() > 0){
			hqlQuery += "  AND  info.Company_Id = "+paramPlant.getCompanyId();
		}
		if(paramPlant.getLocationId() > 0){
			hqlQuery += "  AND  info.Location_Id = "+paramPlant.getLocationId();
		}
		
		if(paramPlant.getPlantName() != null && !paramPlant.getPlantName().isEmpty()){
			hqlQuery += " AND info.Plant_Name LIKE '"+paramPlant.getPlantName()+"%' ";
		}
		
		if(paramPlant.getPlantCode() != null && !paramPlant.getPlantCode().isEmpty()){
			hqlQuery += " AND plant.Plant_Code = '"+paramPlant.getPlantCode()+"' ";
		}
		
		if(paramPlant.getStatus() != null && !paramPlant.getStatus().isEmpty()){
			hqlQuery += " AND info.Status LIKE '"+paramPlant.getStatus()+"%' ";
		}
		
		hqlQuery += " GROUP BY info.Plant_Id Order By info.Plant_Name asc";
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			queryList = query.list();
			if(queryList.size() > 0){
				for (Object customer : queryList) {
					Object[] obj = (Object[]) customer;
					plant = new PlantVo();
					
					plant.setCustomerId((Integer)obj[0]);
					plant.setCompanyId((Integer)obj[1]);
					plant.setCompanyName((String)obj[2]);
					plant.setCustomerName((String)obj[3]);
					plant.setPlantId((Integer)obj[4]);
					plant.setPlantName((String)obj[5]);
					plant.setLocationName((String)obj[6]);
					//plant.setTransactionDate((Date)obj[7]);
					plant.setTransDate(DateHelper.convertSQLDateToString((java.sql.Date)obj[7],"dd/MM/yyyy"));
					plant.setStatus((obj[8]+"").equalsIgnoreCase("Y") ? "Active":"Inactive");
					plant.setPlantDetailsId((Integer)obj[9]);
					plant.setPlantCode(obj[10]+"");
					
					returnList.add(plant);
				}	
			}
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from getPlantsListBySearch()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from getPlantsListBySearch()  ::   "+returnList);
		return returnList;
	}

	@Override
	public List<SimpleObject> getTransactionListForPlants(Integer customerId,Integer companyId, Integer plantId) {
		log.info("Entered in getTransactionListForPlants()  ::   customerId = "+customerId+" , companyId = "+companyId+" , plantId = "+plantId);
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> transactionList = new ArrayList<>();
		try {
			List plantList = session.createSQLQuery("SELECT Plant_Details_Id AS id ,CONCAT(DATE_FORMAT(Transaction_Date,'%d/%m/%Y'),' - ',Plant_Sequence_Id) AS name FROM  plant_details_info plant  WHERE plant.Customer_Id = "+customerId+" AND plant.Company_Id = "+companyId+" AND plant.Plant_Id = "+plantId+" ORDER BY plant.Transaction_Date, plant.Plant_Details_Id asc").list();
			for (Object transDates  : plantList) {
				Object[] transaction = (Object[]) transDates;
				SimpleObject object = new SimpleObject();
				object.setId((Integer)transaction[0]);
				object.setName(transaction[1]+"");
				transactionList.add(object);
			}			
		} catch (Exception e) {
			log.error("Error Occurred...   ", e);
			log.info("Exiting from getTransactionListForPlants()  ::   "+transactionList);
		}
		
		log.info("Exiting from getTransactionListForPlants()  ::   "+transactionList);
		return transactionList;
	}

}
