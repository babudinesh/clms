package com.Ntranga.CLMS.Dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Ntranga.CLMS.vo.ShiftsDefineVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.core.DateHelper;
import com.Ntranga.core.CLMS.entities.CompanyDetails;
import com.Ntranga.core.CLMS.entities.CustomerDetails;
import com.Ntranga.core.CLMS.entities.LocationDetails;
import com.Ntranga.core.CLMS.entities.PlantDetails;
import com.Ntranga.core.CLMS.entities.ShiftsDefine;
import common.Logger;


@SuppressWarnings("rawtypes")
@Transactional
@Service("shiftsDefineDao")
public class ShiftsDefineDaoImpl implements ShiftsDefineDao {

	private static Logger log = Logger.getLogger(ShiftsDefineDaoImpl.class);
	
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Integer saveShiftDefineData(ShiftsDefineVo shiftsDefineVo) {
		Session session = null;
		Integer shiftId = 0;
		try{
			
			session = sessionFactory.getCurrentSession();			
			ShiftsDefine shifts = new ShiftsDefine();
			if(shiftsDefineVo.getShiftDefineId() != null && shiftsDefineVo.getShiftDefineId() > 0){
				shifts = (ShiftsDefine) session.get(ShiftsDefine.class, shiftsDefineVo.getShiftDefineId());
			}
			shifts.setCompanyDetails(new CompanyDetails(shiftsDefineVo.getCompanyDetailsId()));
			shifts.setCustomerDetails(new CustomerDetails(shiftsDefineVo.getCustomerDetailsId()));
			if(shiftsDefineVo.getLocationDetailsId() != null && shiftsDefineVo.getLocationDetailsId() > 0)
				shifts.setLocationDetails(new LocationDetails(shiftsDefineVo.getLocationDetailsId()));
			else
				shifts.setLocationDetails(null);
			if(shiftsDefineVo.getPlantDetailsId() != null && shiftsDefineVo.getPlantDetailsId() > 0)
				shifts.setPlantDetails(new PlantDetails(shiftsDefineVo.getPlantDetailsId()));
			else
				shifts.setPlantDetails(null);
			shifts.setCountryId(shiftsDefineVo.getCountryId());
			shifts.setTransactionDate(shiftsDefineVo.getTransactionDate());
			shifts.setIsActive(shiftsDefineVo.getIsActive());
			shifts.setShiftCode(shiftsDefineVo.getShiftCode());
			shifts.setShiftName(shiftsDefineVo.getShiftName());
			shifts.setShiftColor(shiftsDefineVo.getShiftColor());
			shifts.setStartTime(shiftsDefineVo.getStartTime());
			shifts.setEndTime(shiftsDefineVo.getEndTime());
			shifts.setUnpaidBreak(shiftsDefineVo.getUnpaidBreak());
			shifts.setTotalHours(shiftsDefineVo.getTotalHours());
			shifts.setOffShift(shiftsDefineVo.getOffShift());
			shifts.setHalfDayShift(shiftsDefineVo.getHalfDayShift());
			shifts.setShiftAllowance(shiftsDefineVo.getShiftAllowance());
			shifts.setFlexibleShift(shiftsDefineVo.getFlexibleShift());
			shifts.setIsNextDay(shiftsDefineVo.getIsNextDay());
			shifts.setModifiedBy(shiftsDefineVo.getModifiedBy());
			shifts.setModifiedDate(new Date());
			
			if(shiftsDefineVo.getShiftDefineId() != null && shiftsDefineVo.getShiftDefineId() > 0){
				session.update(shifts);
				shiftId = shiftsDefineVo.getShiftDefineId();
			}else{
				BigInteger sequenceId = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Sequence_Id),0) FROM  `shifts_define` WHERE `Customer_Id`="+shiftsDefineVo.getCustomerDetailsId()+" and `Company_id`="+shiftsDefineVo.getCompanyDetailsId()+" and  `Transaction_Date`='"+DateHelper.convertDateToSQLString(shiftsDefineVo.getTransactionDate())+"' and shift_code = '"+shiftsDefineVo.getShiftCode()+"'").list().get(0);
				shifts.setSequenceId(sequenceId.intValue()+1);	
				shifts.setCreatedBy(shiftsDefineVo.getCreatedBy());
				shifts.setCreatedDate(new Date());			
				shiftId = (Integer) session.save(shifts);
			}
					
			session.flush();
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return shiftId;
	}

	
	@Override
	public Map<String,List> getTransactionDatesForShiftsHistory(Integer customerId, Integer companyId,String shiftCode) {
		Map<String,List> map = new HashMap<String,List>();
		List<SimpleObject> objects = new ArrayList<SimpleObject>();
		List<ShiftsDefineVo> defineVos = new ArrayList<ShiftsDefineVo>();
		try{
			
			List tempList = sessionFactory.getCurrentSession().createSQLQuery("SELECT `shift_Define_Id` , CONCAT(DATE_FORMAT(Transaction_Date,'%d/%m/%Y'),' - ', Sequence_Id) AS tname FROM `shifts_define`  WHERE `Customer_Id`="+customerId+" and `Company_id`="+companyId+" and shift_code = '"+shiftCode+"'").list();
			for(Object object : tempList){
				Object[] obj = (Object[]) object;
				objects.add(new SimpleObject((Integer)obj[0],obj[1]+""));
			}
			map.put("transactionList", objects);
			List tempLists = sessionFactory.getCurrentSession()
					.createSQLQuery(
							"SELECT `shift_Define_Id`, `Customer_Id`, `Company_Id`, `Country_Id`, `Shift_Code`, `Transaction_Date`, "
									+ "	`Is_Active`, `Shift_Name`, `Shift_Color`, `Start_Time`, `End_Time`, `Unpaid_Break`, `Total_Hours`, `OFF_Shift`,"
									+ "	 `Half_Day_Shift`, `Shift_Allowance`, `Flexible_Shift`,sequence_id, Is_Next_Day FROM `shifts_define` define"
									+ "	  WHERE   "
									+ "	CONCAT(DATE_FORMAT(define.transaction_date, '%Y%m%d'), LPAD(define.Sequence_Id, 2, '0')) =  "
									+ "	( "
									+ "	SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.Sequence_Id, 2, '0'))) "
									+ "	FROM shifts_define vdi1 " + "	WHERE  define.Shift_Code = vdi1.Shift_Code     "
									+ "	AND vdi1.transaction_date <= CURRENT_DATE()   "
									+ "	) AND `Customer_Id`='"+customerId+"'  AND `Company_id`='"+companyId+"' and shift_code = '"+shiftCode+"'").list();
			for(Object object : tempLists){
				Object[] obj = (Object[]) object;
				ShiftsDefineVo defineVo = new ShiftsDefineVo();
				defineVo.setShiftDefineId((Integer)obj[0]);
				defineVo.setCustomerDetailsId((Integer)obj[1]);
				defineVo.setCompanyDetailsId((Integer)obj[2]);	        	
				defineVo.setCountryId((Integer)obj[3]);
				defineVo.setShiftCode(obj[4]+"");
				defineVo.setTransactionDate((Date)obj[5]);
				defineVo.setIsActive(obj[6]+"");				
				defineVo.setShiftName(obj[7]+"");
				defineVo.setShiftColor(obj[8]+"");
				defineVo.setStartTime(obj[9]+"");			
				defineVo.setEndTime(obj[10]+"");
				defineVo.setUnpaidBreak(obj[11]+"");
				defineVo.setTotalHours(obj[12]+"");
				defineVo.setOffShift(obj[13]+"");
				defineVo.setHalfDayShift(obj[14]+"");
				defineVo.setShiftAllowance(obj[15]+"");
				defineVo.setFlexibleShift(obj[16]+""); 
				defineVo.setIsNextDay(obj[17]+""); 
				defineVos.add(defineVo);
				break;
			}
			map.put("shiftDefineVo", defineVos)	;				
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return map;
	}

	@Override
	public ShiftsDefineVo getShiftRecordByShiftId(Integer shiftDefineId) {
		ShiftsDefineVo defineVo = new ShiftsDefineVo();
		try{			
			List tempList = sessionFactory.getCurrentSession().createSQLQuery("SELECT `shift_Define_Id`, `Customer_Id`, `Company_Id`, `Country_Id`, `Shift_Code`, `Transaction_Date`, `Is_Active`, `Shift_Name`, `Shift_Color`, `Start_Time`, `End_Time`, `Unpaid_Break`, `Total_Hours`, `OFF_Shift`, `Half_Day_Shift`, `Shift_Allowance`, `Flexible_Shift`,location_id,plant_id , Is_Next_Day FROM `shifts_define` WHERE shift_Define_Id ="+shiftDefineId).list();
			for(Object object : tempList){
				Object[] obj = (Object[]) object;
				defineVo.setShiftDefineId((Integer)obj[0]);
				defineVo.setCustomerDetailsId((Integer)obj[1]);
				defineVo.setCompanyDetailsId((Integer)obj[2]);	        	
				defineVo.setCountryId((Integer)obj[3]);
				defineVo.setShiftCode(obj[4]+"");
				defineVo.setTransactionDate((Date)obj[5]);
				defineVo.setIsActive(obj[6]+"");				
				defineVo.setShiftName(obj[7]+"");
				defineVo.setShiftColor(obj[8]+"");
				defineVo.setStartTime(obj[9]+"");			
				defineVo.setEndTime(obj[10]+"");
				defineVo.setUnpaidBreak(obj[11]+"");
				defineVo.setTotalHours(obj[12]+"");
				defineVo.setOffShift(obj[13]+"");
				defineVo.setHalfDayShift(obj[14]+"");
				defineVo.setShiftAllowance(obj[15]+"");
				defineVo.setFlexibleShift(obj[16]+""); 
				if(obj[17] != null )
					defineVo.setLocationDetailsId((Integer)obj[17]);
				else
					defineVo.setLocationDetailsId(0);
				if(obj[18] != null )
					defineVo.setPlantDetailsId((Integer)obj[18]);
				else
					defineVo.setPlantDetailsId(0);
				
				defineVo.setIsNextDay((String)obj[19]);
			}
			
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return defineVo;
	}

	@Override
	public List<ShiftsDefineVo> getShiftDefineGridData(Integer customerId, Integer companyId, String status, String shiftCode, String shiftName) {
		List<ShiftsDefineVo> defineVos = new ArrayList<ShiftsDefineVo>();
		try{			
			// String query = "SELECT 	`shift_Define_Id`, `Customer_Id`, `Company_Id`, `Country_Id`, `Shift_Code`, `Transaction_Date`, `Sequence_Id`, `Is_Active`, `Shift_Name`, `Shift_Color`, `Start_Time`, `End_Time`, `Unpaid_Break`, `Total_Hours`, `OFF_Shift`, `Half_Day_Shift`, `Shift_Allowance`, `Flexible_Shift` FROM `shifts_define` WHERE shift_Define_Id > 0 ";
			
			
			String query = " SELECT `shift_Define_Id`,`Shift_Name`, `Shift_Code`, date_format(define.`Transaction_Date`,'%d/%m/%Y'),  companydetails.company_name, customerDetails.customer_name, "+
							" CASE WHEN define.is_Active = 'Y' THEN 'Active' ELSE 'In Active' END AS is_Active,CASE WHEN define.Location_Id IS NULL THEN 'All' ELSE loc.location_name END AS locname,CASE WHEN define.Plant_Id IS NULL THEN 'All' ELSE plant.plant_name END AS plantname,define.`Transaction_Date` "+
							" FROM `shifts_define`  define "+
							" INNER JOIN `company_details_info` companydetails ON (companydetails.`Company_Id`= define.`Company_Id`) "+
							" INNER JOIN `customer_details_info` customerDetails ON (customerDetails.`Customer_Id` = define.`Customer_Id`)"+							
							" LEFT JOIN location_details_info loc ON  (loc.Location_Id = define.Location_Id AND "+ 
							" CONCAT(DATE_FORMAT(loc.transaction_date, '%Y%m%d'), LPAD(loc.location_Sequence_Id, 2, '0')) = "+  
							" ( "+  
							" SELECT MAX(CONCAT(DATE_FORMAT(loc1.transaction_date, '%Y%m%d'), LPAD(loc1.location_Sequence_Id, 2, '0'))) "+ 
							" FROM location_details_info loc1 "+ 
							" WHERE  loc.Location_Id = loc1.Location_Id   AND loc1.status='Y' "+  
							" AND loc1.transaction_date <= CURRENT_DATE()   )) "+
							" LEFT JOIN  plant_details_info  plant ON (plant.Plant_Id = define.Plant_Id AND "+ 
							" CONCAT(DATE_FORMAT(plant.transaction_date, '%Y%m%d'), LPAD(plant.plant_Sequence_Id, 2, '0')) = "+  
							" ( "+ 
							" SELECT MAX(CONCAT(DATE_FORMAT(plant1.transaction_date, '%Y%m%d'), LPAD(plant1.plant_Sequence_Id, 2, '0'))) "+ 
							" FROM plant_details_info plant1 "+ 
							" WHERE  plant.Plant_Id = plant1.Plant_Id   AND plant1.status='Y' "+  
							" AND plant1.transaction_date <= CURRENT_DATE()   )) "+ 
							" WHERE "+
							" CONCAT(DATE_FORMAT(define.transaction_date, '%Y%m%d'), LPAD(define.Sequence_Id, 2, '0')) =  "+
							" ( "+
							" SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.Sequence_Id, 2, '0'))) "+
							" FROM shifts_define vdi1 "+
							" WHERE  define.Shift_Code = vdi1.Shift_Code     "+
							" AND vdi1.transaction_date <= CURRENT_DATE()"+
							" )  AND "+
							
							" CONCAT(DATE_FORMAT(companydetails.transaction_date, '%Y%m%d'), LPAD(companydetails.company_Sequence_Id, 2, '0')) =  "+
							" ( "+
							" SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.company_Sequence_Id, 2, '0'))) "+
							" FROM company_details_info vdi1 "+
							" WHERE  companydetails.company_id = vdi1.company_id   AND vdi1.is_active='Y'  "+
							" AND vdi1.transaction_date <= CURRENT_DATE()   "+
							" ) AND "+							
							" CONCAT(DATE_FORMAT(customerDetails.transaction_date, '%Y%m%d'), LPAD(customerDetails.customer_Sequence_Id, 2, '0')) =  "+
							" ( "+
							" SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.customer_Sequence_Id, 2, '0'))) "+
							" FROM customer_details_info vdi1 "+
							" WHERE  customerDetails.customer_id = vdi1.customer_id   AND vdi1.is_active='Y'"+
							" AND vdi1.transaction_date <= CURRENT_DATE() )" ;
			if(customerId != null && customerId >0 ){
				query += " AND define.`Customer_Id`= "+customerId;
			}
			if(companyId != null && companyId >0 ){
				query += " AND define.`Company_Id`= "+companyId;		
			}
			if(status != null & !status.trim().isEmpty()){
				query += " AND define.`Is_Active`= '"+status+"'";
			}
			if(shiftCode != null & !shiftCode.trim().isEmpty()){
				query += "AND `Shift_Code` LIKE '%"+shiftCode+"%'";
			}
			if(shiftName != null & !shiftName.trim().isEmpty()){
				query += "AND `Shift_Name` LIKE '%"+shiftName+"%'";
			}
			
			query += " ORDER BY Shift_Name asc";
			List tempList = sessionFactory.getCurrentSession().createSQLQuery(query).list();
			for(Object object : tempList){
				Object[] obj = (Object[]) object;
				ShiftsDefineVo defineVo = new ShiftsDefineVo();
				defineVo.setShiftDefineId((Integer)obj[0]);
				defineVo.setShiftCode(obj[2]+"");
				defineVo.setShiftName(obj[1]+"");
				defineVo.setTransactionDate((Date)obj[9]);
				defineVo.setStringTransactionDate(obj[3]+"");
				defineVo.setCompanyName(obj[4]+"");
				defineVo.setCustomerName(obj[5]+"");				
				defineVo.setIsActive(obj[6]+"");
				defineVo.setLocationName(obj[7]+"");
				defineVo.setPlantName(obj[8]+"");
	        	defineVos.add(defineVo);
			}
			
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		
		return defineVos;
	}
	
}
