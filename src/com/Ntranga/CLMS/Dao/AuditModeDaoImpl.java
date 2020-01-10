package com.Ntranga.CLMS.Dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Ntranga.CLMS.vo.AuditModeControlVo;
import com.Ntranga.core.DateHelper;
import com.Ntranga.core.CLMS.entities.AuditModeControl;

import common.Logger;

@Transactional
@Repository("auditModeDao")
public class AuditModeDaoImpl implements AuditModeDao  {

	private static Logger log = Logger.getLogger(AuditModeDaoImpl.class);
	
	
	@Autowired
	private SessionFactory sessionFactory;

	private @Value("#{db['db.schemaName']}")
	String dbSchemaName;

	public Integer saveAudit(AuditModeControlVo audit) {
		Session session = sessionFactory.getCurrentSession();
		Integer auditId = 0;
		String dbSchema = null;
		try{
			if(audit.getSchemaName() != null && !audit.getSchemaName().equalsIgnoreCase("null") && !audit.getSchemaName().trim().isEmpty() ){
				dbSchema =audit.getSchemaName();
			}else{
				dbSchema = dbSchemaName;
			}
			
			AuditModeControl auditMode = new AuditModeControl();
			
			Query q = null;
			List<AuditModeControlVo>  auditList = new ArrayList();
			
				if(audit.getCustomerId()+"" != null && audit.getCustomerId() >0 && audit.getCompanyId()+"" != null && audit.getCompanyId() >0 && audit.getLocationId()+"" != null && audit.getLocationId() >0 && audit.getCountryId()+"" != null && audit.getCountryId() >0 && audit.getStartDate() != null){
				q =   session.createSQLQuery("SELECT Audit_Id FROM "+dbSchema+".`audit_mode_control` WHERE `Customer_Id` = '"+audit.getCustomerId()+"' AND `Company_Id` ='"+audit.getCompanyId()+"'  AND `Location_Id` ='"+ audit.getLocationId()+"'  AND `Country_Id`='"+audit.getCountryId()+"' AND start_date='"+DateHelper.convertDateToSQLString(audit.getStartDate())+"'");
				
				}
				List temp =q.list();
				if( temp.size()>0){
					Integer obj = (Integer) temp.get(0);
					auditId = (Integer)obj;
					audit.setAuditId(auditId);
				}
			
			  
			
			if(audit.getAuditStatus()!=null && audit.getAuditStatus().equalsIgnoreCase("Y")){
				auditMode.setAuditStatus("Y");
			}else{				
				auditMode.setAuditStatus("N");
			}
			auditMode.setCustomerId(audit.getCustomerId());
			auditMode.setCompanyId(audit.getCompanyId());
			auditMode.setCountryId(audit.getCountryId());
			auditMode.setLocationId(audit.getLocationId());
			auditMode.setStartDate(audit.getStartDate());
			auditMode.setEndDate(audit.getEndDate());
			if(audit.getComments() != null){
				auditMode.setComments(audit.getComments());
			}			
			auditMode.setCreatedBy(audit.getCreatedBy());
			auditMode.setCreatedDate(new Date());
			auditMode.setModifiedBy(audit.getModifiedBy());
			auditMode.setModifiedDate(new Date());
			
			
			if(audit.getAuditId() != null && audit.getAuditId() >0){
				
				auditMode.setAuditId(audit.getAuditId());
				// session.update(auditMode);
				session.createSQLQuery("UPDATE "+dbSchema+".`audit_mode_control` SET `Audit_Status`='"+auditMode.getAuditStatus()+"', `Comments`='"+auditMode.getComments()+"', `Company_Id`='"+auditMode.getCompanyId()+"', `Country_Id`='"+auditMode.getCountryId()+"', `Customer_Id`='"+auditMode.getCustomerId()+"', `End_Date`='"+DateHelper.convertDateToSQLString(auditMode.getEndDate())+"', `Location_Id`='"+auditMode.getLocationId()+"', `Modified_By`='"+auditMode.getModifiedBy()+"',Modified_Date='"+DateHelper.convertDateTimeToSQLString(new Date())+"',  `Start_Date`='"+DateHelper.convertDateToSQLString(auditMode.getStartDate())+"' WHERE `Audit_Id` = "+audit.getAuditId() ).executeUpdate();
				
				auditId = auditMode.getAuditId();
			}else{			
				session.createSQLQuery("INSERT INTO "+dbSchema+".`audit_mode_control` ( `Audit_Status`, `Comments`, `Company_Id`, `Country_Id`, `Created_By`, `Created_date`, `Customer_Id`, `End_Date`, `Location_Id`, `Modified_By`, `Modified_Date`, `Start_Date`) "
						+ " VALUES ('"+auditMode.getAuditStatus()+"','"+auditMode.getComments()+"','"+auditMode.getCompanyId()+"','"+auditMode.getCountryId()+"','"+auditMode.getCreatedBy()+"','"+DateHelper.convertDateTimeToSQLString(new Date())+"','"+auditMode.getCustomerId()+"','"+DateHelper.convertDateToSQLString(auditMode.getEndDate())+"','"+auditMode.getLocationId()+"','"+auditMode.getModifiedBy()+"','"+DateHelper.convertDateTimeToSQLString(new Date())+"','"+DateHelper.convertDateToSQLString(auditMode.getStartDate())+"' ) ").executeUpdate();
				auditId=  1;// (Integer) session.save(auditMode);	
			}
			
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return auditId;
	}
	

	
	public List<AuditModeControlVo>  getauditList(Integer customerId,Integer companyId,Integer locationId,Integer countryId,String schemaName){
		Session session = sessionFactory.getCurrentSession();
		Query q = null;
		List<AuditModeControlVo>  auditList = new ArrayList();
		Integer  auditId=0;
		String dbSchema = null;
		try{

			
			if(schemaName != null && !schemaName.equalsIgnoreCase("null") && !schemaName.trim().isEmpty() ){
				dbSchema =schemaName;
			}else{
				dbSchema = dbSchemaName;
			}
			
			if(customerId != null && customerId >0 && companyId != null && companyId >0 && locationId != null && locationId >0 && countryId != null && countryId >0){
				q =   session.createSQLQuery("SELECT Audit_Id,Customer_Id,Company_Id,Location_Id,Country_Id,Audit_Status,Start_Date, "+
						" End_Date,Comments,Created_By,Created_date,Modified_By,Modified_Date FROM  "+dbSchema+".`audit_mode_control` a "+
						" WHERE `Customer_Id` = '"+customerId+"' AND `Company_Id` ='"+companyId+"'  "+
						" AND `Location_Id` ='"+locationId+"'  AND `Country_Id`='"+countryId+"'    "+
						" AND start_date  = (SELECT MAX(start_date) FROM audit_mode_control b  "+
						" WHERE  a.Customer_Id =b.Customer_Id AND a.Company_Id = b.Company_Id AND CURRENT_DATE BETWEEN start_date AND end_date "+
						" AND a.Location_Id = b.Location_Id AND a.Country_Id = b.Country_Id  ORDER BY start_date DESC )");
			}
			
			
			for(Object o :q.list()){
				Object[] obj = (Object[]) o;
				AuditModeControlVo auditMode = new AuditModeControlVo();
				auditMode.setAuditId((Integer)obj[0]);
				auditMode.setCustomerId((Integer)obj[1]);
				auditMode.setCompanyId((Integer)obj[2]);
				auditMode.setLocationId((Integer)obj[3]);
				auditMode.setCountryId((Integer)obj[4]);
				auditMode.setAuditStatus(obj[5]+"");
				auditMode.setStartDate((Date)obj[6]);
				auditMode.setEndDate((Date)obj[7]);
				if(obj[8] != null)
				auditMode.setComments(obj[8]+"");
				auditMode.setCreatedBy((Integer)obj[9]);
				auditMode.setCreatedDate((Date)obj[10]);
				auditMode.setModifiedBy((Integer)obj[11]);
				auditMode.setModifiedDate((Date)obj[12]);
				auditList.add(auditMode);
			}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
			
			//simpleObjectList.add(object);
		return auditList;
	}
	
	
	public List<AuditModeControlVo>  getCompleteAuditList(Integer customerId,Integer companyId,Integer locationId,Integer countryId,String schemaName){
		Session session = sessionFactory.getCurrentSession();
		Query q = null;
		List<AuditModeControlVo>  auditList = new ArrayList();
		Integer  auditId=0;
		String dbSchema = null;
		try{
			if(schemaName != null && !schemaName.equalsIgnoreCase("null") && !schemaName.trim().isEmpty() ){
				dbSchema =schemaName;
			}else{
				dbSchema = dbSchemaName;
			}
			if(customerId != null && customerId >0 && companyId != null && companyId >0 && locationId != null && locationId >0 && countryId != null && countryId >0){
			q =   session.createSQLQuery("SELECT Audit_Id,Customer_Id,Company_Id,Location_Id,Country_Id,Audit_Status,Start_Date,End_Date,Comments,Created_By,Created_date,Modified_By,Modified_Date FROM "+dbSchema+".`audit_mode_control` WHERE `Customer_Id` = '"+customerId+"' AND `Company_Id` ='"+companyId+"'  AND `Location_Id` ='"+locationId+"'  AND `Country_Id`='"+countryId+"'");
			}
			
			
			for(Object o :q.list()){
				Object[] obj = (Object[]) o;
				AuditModeControlVo auditMode = new AuditModeControlVo();
				auditMode.setAuditId((Integer)obj[0]);
				auditMode.setCustomerId((Integer)obj[1]);
				auditMode.setCompanyId((Integer)obj[2]);
				auditMode.setLocationId((Integer)obj[3]);
				auditMode.setCountryId((Integer)obj[4]);
				auditMode.setAuditStatus(obj[5]+"");
				auditMode.setStartDate((Date)obj[6]);
				auditMode.setEndDate((Date)obj[7]);
				if(obj[8] != null)
				auditMode.setComments(obj[8]+"");
				auditMode.setCreatedBy((Integer)obj[9]);
				auditMode.setCreatedDate((Date)obj[10]);
				auditMode.setModifiedBy((Integer)obj[11]);
				auditMode.setModifiedDate((Date)obj[12]);
				auditList.add(auditMode);
			}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
			
			//simpleObjectList.add(object);
		return auditList;
	}
	
	

	
}
