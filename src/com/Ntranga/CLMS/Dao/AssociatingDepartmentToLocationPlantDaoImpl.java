package com.Ntranga.CLMS.Dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Ntranga.CLMS.vo.AssociatingDepartmentToLocationPlantVo;
import com.Ntranga.CLMS.vo.DepartmentVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.core.CLMS.entities.AssociatingDepartmentToLocationPlant;
import com.Ntranga.core.CLMS.entities.CompanyDetails;
import com.Ntranga.core.CLMS.entities.CustomerDetails;
import com.Ntranga.core.CLMS.entities.DepartmentDetails;
import com.Ntranga.core.CLMS.entities.LocationDetails;
import com.Ntranga.core.CLMS.entities.PlantDetails;
import common.Logger;

@SuppressWarnings("rawtypes")
@Transactional
@Repository("associatingDepartmentToLocationPlantDao")
public class AssociatingDepartmentToLocationPlantDaoImpl implements  AssociatingDepartmentToLocationPlantDao {

private static Logger log = Logger.getLogger(AssociatingDepartmentToLocationPlantDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	/*
	 * This method will be used to save associatingDepartmentToLocationAndPlantSaving
	 */
	
	public boolean associatingDepartmentToLocationAndPlantSaving(AssociatingDepartmentToLocationPlantVo associatingDepartmentToLocationPlantVo) {
		log.info("Entered in saveassociatingDepartmentToLocationPlant()  ::   "+associatingDepartmentToLocationPlantVo);
		boolean flag = false;
		try{
			Session session = sessionFactory.getCurrentSession();
		
			AssociatingDepartmentToLocationPlant associatingDepartmentToLocationPlant = new AssociatingDepartmentToLocationPlant();
			
			if(associatingDepartmentToLocationPlantVo.getDepartmentlist() != null && associatingDepartmentToLocationPlantVo.getDepartmentlist().size() > 0 ){						
				Query q = session.createSQLQuery("delete from associating_department_to_location_plant where Location_Id = '"+associatingDepartmentToLocationPlantVo.getLocationId()+"' and Plant_Id = '"+associatingDepartmentToLocationPlantVo.getPlantId()+"' and customer_Id = '"+associatingDepartmentToLocationPlantVo.getCustomerId()+"' and company_Id = '"+associatingDepartmentToLocationPlantVo.getCompanyId()+"'");
				q.executeUpdate();	 
			
				for(DepartmentVo  dlp : associatingDepartmentToLocationPlantVo.getDepartmentlist()){					
					log.info("********  "+associatingDepartmentToLocationPlantVo.getDepartmentlist());
					associatingDepartmentToLocationPlant = new AssociatingDepartmentToLocationPlant();						
						associatingDepartmentToLocationPlant.setCustomerDetails(new CustomerDetails(associatingDepartmentToLocationPlantVo.getCustomerId()));
						associatingDepartmentToLocationPlant.setCompanyDetails(new CompanyDetails(associatingDepartmentToLocationPlantVo.getCompanyId()));
						associatingDepartmentToLocationPlant.setLocationDetails(new LocationDetails(associatingDepartmentToLocationPlantVo.getLocationId()));
						associatingDepartmentToLocationPlant.setPlantDetails(new PlantDetails(associatingDepartmentToLocationPlantVo.getPlantId()));
						associatingDepartmentToLocationPlant.setDepartmentDetails(new DepartmentDetails(dlp.getDepartmentId()));						
						associatingDepartmentToLocationPlant.setCreatedBy(associatingDepartmentToLocationPlantVo.getCreatedBy());
						associatingDepartmentToLocationPlant.setCreatedDate(new Date());
						associatingDepartmentToLocationPlant.setModifiedBy(associatingDepartmentToLocationPlantVo.getModifiedBy());
						associatingDepartmentToLocationPlant.setModifiedDate(new Date());
						session.save(associatingDepartmentToLocationPlant);
					
				}
				
			}
			session.flush();
			flag = true;
		}catch (Exception e) {
			flag = false;
			 log.error("Error Occurred...   ", e);
			 log.info("Exiting from saveassociatingDepartmentToLocationPlant()  ");
		}				
		log.info("Exiting from saveassociatingDepartmentToLocationPlant() ");
		return flag;
	}

	/*
	 * This method will be used to getDeparmentNamesAsDropDown Based on Location And Plant
	 */

	@Override
	public List<DepartmentVo> getDeparmentNamesAsDropDown(String customerId,String companyId,String locationId, String PlantId) { 
		Session session = sessionFactory.getCurrentSession();
		List<DepartmentVo> simpleObjects = new ArrayList<DepartmentVo>(); 		
		try {
			String query = 	" SELECT DISTINCT addi.department_id,addi.department_name,addi.Department_Info_Id,dd.department_code FROM "+
							" associating_department_to_location_plant adlp  "+
							" INNER JOIN department_details_info addi ON (adlp.department_id = addi.department_id) "+
							"  INNER JOIN department_details dd ON(adlp.department_Id = dd.department_Id)       "+
							" WHERE  "+
							" CONCAT(DATE_FORMAT(addi.Transaction_Date,'%Y%m%d'),LPAD(addi.Department_Sequence_id,2,'0')) = (SELECT "+
							" MAX(CONCAT(DATE_FORMAT(vdi1.Transaction_Date,'%Y%m%d'),LPAD(vdi1.Department_Sequence_id,2,'0'))) "+
							" FROM department_details_info vdi1 "+
							" WHERE ((addi.department_id = vdi1.department_id) "+
							" AND (addi.Transaction_Date <= CURDATE()))) and adlp.Customer_Id = '"+customerId+"' and adlp.Company_Id = '"+companyId+"' and adlp.Location_Id IN( "+locationId+" ) and adlp.plant_Id IN ( "+PlantId+" ) ORDER BY addi.department_name ASC";	
			
			System.out.println("================="+query);
			
			List tempList =  session.createSQLQuery(query).list();																	
			for(Object  o: tempList){
				Object[] obj = (Object[]) o;
				DepartmentVo object = new DepartmentVo();
				object.setDepartmentId((Integer)obj[0]);
				object.setDepartmentName(obj[1]+"");	
				object.setDepartmentInfoId((Integer)obj[2]);	
				object.setDepartmentCode(obj[3]+"");	
				simpleObjects.add(object);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error Occured ",e);
		}
		return simpleObjects;
	}
	
	@Override
	public List<SimpleObject> getSectionNamesAsDropDown(String customerId,String companyId,String locationId, String plantId, String departmentId){
		log.info("Entered in  getSectionNamesAsDropDown()  ::  ");
		
		Session session = sessionFactory.getCurrentSession();
		List queryList = null;
		List<SimpleObject> returnList = new ArrayList<SimpleObject>();
		SimpleObject object = new SimpleObject();
		
		String hqlQuery = "SELECT  "
							+" info.`Section_Id`, workd.`Section_Code`, info.Section_Name "
							+" FROM `section_details_info`  info "
							+" LEFT JOIN `section_details`  workd ON workd.`Section_Id` = info.`Section_Id` "  
							+" inner JOIN associating_department_to_location_plant  dlp  ON dlp.department_Id = info.department_Id " 
							+" LEFT JOIN  plant_details_info  plantDetails ON (plantDetails.Plant_Id = info.Plant_Id AND CONCAT(DATE_FORMAT(plantDetails.transaction_date, '%Y%m%d'), LPAD(plantDetails.Plant_Sequence_Id, 2, '0')) = (SELECT MAX(CONCAT(DATE_FORMAT(plantDetails1.transaction_date, '%Y%m%d'), LPAD(plantDetails1.Plant_Sequence_Id, 2, '0'))) FROM plant_details_info plantDetails1 WHERE plantDetails.Plant_Id = plantDetails1.Plant_Id AND plantDetails1.transaction_date <= CURRENT_DATE())) "
							+" LEFT JOIN company_details_info  company  ON (company.Company_Id = info.Company_Id AND CONCAT(DATE_FORMAT(company.transaction_date, '%Y%m%d'), LPAD(company.company_Sequence_Id, 2, '0')) = (SELECT MAX(CONCAT(DATE_FORMAT(company1.transaction_date, '%Y%m%d'), LPAD(company1.company_Sequence_Id, 2, '0'))) FROM company_details_info company1 WHERE company.Company_Id = company1.Company_Id AND company1.transaction_date <= CURRENT_DATE())) " 
							+" LEFT JOIN customer_details_info  customer  ON (customer.Customer_Id = info.Customer_Id AND CONCAT(DATE_FORMAT(customer.transaction_date, '%Y%m%d'), LPAD(customer.customer_Sequence_Id, 2, '0')) = (SELECT MAX(CONCAT(DATE_FORMAT(customer1.transaction_date, '%Y%m%d'), LPAD(customer1.customer_Sequence_Id, 2, '0'))) FROM customer_details_info customer1 WHERE customer.customer_id = customer1.customer_id AND customer1.transaction_date <= CURRENT_DATE())) " 
							+" LEFT JOIN location_details_info location ON  (location.Location_Id = info.Location_Id AND CONCAT(DATE_FORMAT(location.transaction_date, '%Y%m%d'), LPAD(location.location_Sequence_Id, 2, '0')) = (SELECT MAX(CONCAT(DATE_FORMAT(location1.transaction_date, '%Y%m%d'), LPAD(location1.location_Sequence_Id, 2, '0'))) FROM location_details_info location1 WHERE location.Location_Id = location1.Location_Id AND location1.transaction_date <= CURRENT_DATE())) " 
							+" WHERE CONCAT(DATE_FORMAT(info.transaction_date, '%Y%m%d'), LPAD(info.Sequence_Id, 2, '0')) =  "    
							+" (SELECT MAX(CONCAT(DATE_FORMAT(workd1.transaction_date, '%Y%m%d'), LPAD(workd1.Sequence_Id, 2, '0')))  "
							+" FROM section_details_info workd1 WHERE info.Section_Id = workd1.Section_Id AND workd1.transaction_date <= CURRENT_DATE())  "
							 + "  AND  info.Customer_Id = "+customerId
							 + "  AND  info.Company_Id = "+companyId
							 + "  AND  info.Location_Id in ( "+locationId
							 + ")  AND  info.Plant_Id in ("+plantId
							 + ") AND info.Status = 'Y'" 
							 + "  AND  info.Department_Id in( "+departmentId
							 + ") Group By info.Section_Id "
							 + " Order By workd.Section_Code asc";
		
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			queryList = query.list();
			if(queryList.size() > 0){
				for (Object section : queryList) {
					Object[] obj = (Object[]) section;
					object = new SimpleObject();					
					object.setId((Integer)obj[0]);
					object.setName((String)obj[2]+" ("+(String)obj[1]+")");
					
					returnList.add(object);
				}	
			}
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from getWorkAreaListBySearch()  ::   "+returnList);
		}
		session.flush();	
		return returnList;
	}
	

}
