package com.Ntranga.CLMS.Dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Ntranga.CLMS.vo.AssociateWorkOrderVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.core.CLMS.entities.AssociateWorkOrder;
import com.Ntranga.core.CLMS.entities.CompanyDetails;
import com.Ntranga.core.CLMS.entities.CustomerDetails;
import com.Ntranga.core.CLMS.entities.VendorDetails;
import com.Ntranga.core.CLMS.entities.WorkrorderDetail;

import common.Logger;

@Transactional
@Repository("associateWorkOrderDao")
public class AssociateWorkOrderDaoImpl implements AssociateWorkOrderDao  {

	private static Logger log = Logger.getLogger(AssociateWorkOrderDaoImpl.class);
	
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<SimpleObject> getVendorAssociatedWorkOrder(AssociateWorkOrderVo associateWorkOrderVo) {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> associateWorkOrderVoList = new ArrayList<SimpleObject>();		
		try {
			
			 String q = "SELECT awo.`WorkOrder_id`,`WorkOrder_Code`,CONCAT(`Work_Order_Name`,' (',`WorkOrder_Code`,')') FROM `associate_work_order` awo "
					+ " INNER JOIN `workrorder_detail` wo ON (awo.`WorkOrder_id` = wo.`WorkOrder_id`) "
					+ " INNER JOIN `workorder_detail_info` wodi ON (awo.`WorkOrder_id` = wodi.`WorkOrder_id`) "
					+ " WHERE "   
					+ " CONCAT(DATE_FORMAT(wodi.transaction_date, '%Y%m%d'), LPAD(wodi.Sequence_Id, 2, '0')) = "  
					+ " ( " 
					+ " SELECT MAX(CONCAT(DATE_FORMAT(wodi1.transaction_date, '%Y%m%d'), LPAD(wodi1.`Sequence_Id`, 2, '0'))) " 
					+ " FROM workorder_detail_info wodi1 " 
					+ " WHERE wodi.`WorkOrder_id` = wodi1.`WorkOrder_id` " 
					+ " AND wodi1.transaction_date <= CURRENT_DATE() "   
					+ " ) "					 
					+ " and awo.Customer_Id = '"+associateWorkOrderVo.getCustomerDetailsId()+"'"
					+ " and awo.Company_Id = '"+associateWorkOrderVo.getCompanyDetailsId()+"' and awo.Vendor_Id = '"+associateWorkOrderVo.getVendorDetailsId()+"'"	
					+ " ORDER BY Work_Order_Name ";						
													 
			
			List tempList = session.createSQLQuery(q).list();			
			for(Object o : tempList){
				Object[] obj = (Object[]) o;
				SimpleObject associateWorkOrderVo1 = new SimpleObject();
				associateWorkOrderVo1.setId((Integer)obj[0]);				
				associateWorkOrderVo1.setName(obj[1]+"");
				associateWorkOrderVo1.setDesc(obj[2]+"");				
				associateWorkOrderVoList.add(associateWorkOrderVo1);
			}						
		} catch (Exception e) {
			log.error("Error Occured ",e);

		}
		return associateWorkOrderVoList;
	}

	
	
	@Override
	public List<AssociateWorkOrderVo> getAssociateWorkOrderSearch(AssociateWorkOrderVo associateWorkOrderVo) {
		Session session = sessionFactory.getCurrentSession();
		List<AssociateWorkOrderVo> associateWorkOrderVoList = new ArrayList<AssociateWorkOrderVo>();		
		try {
			
			 String q = "select vd.Vendor_Id,vd.Vendor_Code,vdi.Vendor_Name, case vdi.is_active WHEN 'Y' THEN 'Active' ELSE 'In-Active' END AS STATUS,vdi.Transaction_Date "+
						" from vendor_details vd, "+
					 "  vendor_details_info vdi  "+
					 " left join vendor_industry_subindustry_details isd on (isd.vendor_id = vdi.Vendor_Id) "+
						  
					 "  where vd.Vendor_Id = vdi.Vendor_Id 	"+					
					 "  and vdi.Transaction_Date = (select max(vdf.Transaction_Date) from vendor_details_info vdf where vdf.Vendor_Id = vdi.Vendor_Id  "+
					 "  and vdf.is_active = 'Y' and vdf.Transaction_Date <= current_date())  "+
					 " 	 and vdi.Sequence_Id in (select max(Sequence_Id) from vendor_details_info vdi1  "+
					 "  where vdi1.Transaction_Date = vdi.Transaction_Date and vdi1.Customer_Id = vdi.Customer_Id  "+
					 " 	 and vdi1.Company_Id = vdi.Company_Id and vdi1.Vendor_Id = vdi.Vendor_Id "+
					 "  group by vdi1.Customer_Id,vdi1.Company_Id,vdi1.Vendor_Id) ";					
			 
					 if(associateWorkOrderVo != null && associateWorkOrderVo.getCustomerDetailsId() != null && associateWorkOrderVo.getCustomerDetailsId() > 0){
						 q +=" and vdi.Customer_Id = '"+associateWorkOrderVo.getCustomerDetailsId()+"'";				 
					 }
					 if(associateWorkOrderVo != null && associateWorkOrderVo.getCompanyDetailsId() != null && associateWorkOrderVo.getCompanyDetailsId() > 0){						 
						 q +=" and vdi.Company_Id = '"+associateWorkOrderVo.getCompanyDetailsId()+"'";	
					 } 
					 
					 if(associateWorkOrderVo != null && associateWorkOrderVo.getVendorCode() != null  ){						 
						 q +=" and vd.Vendor_Code LIKE '"+associateWorkOrderVo.getVendorCode()+"%'";	
					 }	
					 
					 if(associateWorkOrderVo != null && associateWorkOrderVo.getVendorName() != null){						 
						 q +=" and vdi.Vendor_Name LIKE '"+associateWorkOrderVo.getVendorName()+"%'";	
					 }
					 
					 if(associateWorkOrderVo != null && associateWorkOrderVo.getVendorDetailsId() != null && associateWorkOrderVo.getVendorDetailsId() > 0){						 
						 q +=" and vdi.Vendor_Id = '"+associateWorkOrderVo.getVendorDetailsId()+"'";	
					 }
					 
					q = q 	+" group by vdi.Customer_Id,vdi.Company_Id,vdi.Vendor_Id  order by vdi.Vendor_Name ";						
													 
			
			List tempList = session.createSQLQuery(q).list();			
			for(Object o : tempList){
				Object[] obj = (Object[]) o;
				AssociateWorkOrderVo associateWorkOrderVo1 = new AssociateWorkOrderVo();
				associateWorkOrderVo1.setVendorDetailsId((Integer)obj[0]);				
				associateWorkOrderVo1.setVendorCode(obj[1]+"");
				associateWorkOrderVo1.setVendorName(obj[2]+"");
				associateWorkOrderVo1.setIsActive(obj[3]+"");		
				associateWorkOrderVoList.add(associateWorkOrderVo1);
			}						
		} catch (Exception e) {
			log.error("Error Occured ",e);

		}
		return associateWorkOrderVoList;
	}


	@Override
	public AssociateWorkOrderVo getWorkOrderAssociatedList(AssociateWorkOrderVo associateWorkOrderVo) {
		AssociateWorkOrderVo associateWorkVo = new AssociateWorkOrderVo();		
		Session session = sessionFactory.getCurrentSession();
		try{

			String listQuery = "SELECT  vdi.vendor_id,cdi.customer_id,ci.company_id ,vdi.vendor_name,ci.company_name, cdi.customer_name "
							+" FROM  vendor_details_info vdi  "
							+" 	INNER JOIN customer_details_info cdi ON cdi.customer_id=vdi.customer_id "
							+" 	INNER JOIN company_details_info ci ON ci.company_id = vdi.company_id "
							+" WHERE  CONCAT(DATE_FORMAT(vdi.transaction_date, '%Y%m%d'), vdi.Sequence_Id) = "
							+" ( " 
							+" SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), vdi1.Sequence_Id)) "
							+" FROM vendor_details_info vdi1 "
							+" WHERE vdi.vendor_id = vdi1.vendor_id AND vdi1.transaction_date <= CURRENT_DATE() "
							+" ) "
							+" AND  CONCAT(DATE_FORMAT(cdi.transaction_date, '%Y%m%d'), cdi.Customer_Sequence_Id) = " 
							+" ( "
							+" SELECT MAX(CONCAT(DATE_FORMAT(cdi1.transaction_date, '%Y%m%d'), cdi1.Customer_Sequence_Id)) "
							+" FROM customer_details_info cdi1 "
							+" WHERE cdi.customer_id = cdi1.customer_id AND cdi1.transaction_date <= CURRENT_DATE() "
							+" ) "
							+" AND  CONCAT(DATE_FORMAT(ci.transaction_date, '%Y%m%d'), ci.Company_Sequence_Id) = " 
							+" ( "
							+" SELECT MAX(CONCAT(DATE_FORMAT(cdi1.transaction_date, '%Y%m%d'), cdi1.Company_Sequence_Id)) "
							+" FROM company_details_info cdi1 "
							+" WHERE ci.company_id = cdi1.company_id AND cdi1.transaction_date <= CURRENT_DATE() ) " 
							+" AND vdi.Vendor_Id ="+associateWorkOrderVo.getVendorDetailsId();
			
			Object listObj = session.createSQLQuery(listQuery).list().get(0);
			Object[] listObjVal = (Object[]) listObj;
		
			String query = "SELECT  DISTINCT awo.`WorkOrder_id`,date_format(`Work_Period_From`,'%d/%m/%Y'),date_format(`Work_Period_To`,'%d/%m/%Y'),CONCAT( `WorkOrder_Code`,' - ',`Work_Order_Name`)  FROM associate_work_order awo INNER JOIN `workrorder_detail` parent  ON (awo.`WorkOrder_id` = parent.WorkOrder_id)  INNER JOIN `workorder_detail_info` child ON (parent.`WorkOrder_id` = child.`WorkOrder_id`)  WHERE CONCAT(DATE_FORMAT(child.transaction_date, '%Y%m%d'), LPAD(child.Sequence_Id,2,'0')) = (  SELECT MAX(CONCAT(DATE_FORMAT(child1.transaction_date, '%Y%m%d'), LPAD(child1.Sequence_Id,2,'0')))  FROM workorder_detail_info child1 WHERE child.`WorkOrder_id` = child1.`WorkOrder_id`   AND child1.transaction_date <= CURRENT_DATE() ) AND Work_Period_From IS NOT NULL  AND  awo.vendor_id ="+associateWorkOrderVo.getVendorDetailsId()+" GROUP BY  awo.`WorkOrder_id`  ORDER BY WorkOrder_Code ";
			List tempList = session.createSQLQuery(query).list();			
			for(Object o : tempList){
				Object[] obj = (Object[]) o;
				AssociateWorkOrderVo associateWorkOrderVo1 = new AssociateWorkOrderVo();
				associateWorkOrderVo1.setVendorDetailsId((Integer)listObjVal[0]);
				associateWorkOrderVo1.setCustomerDetailsId((Integer)listObjVal[1]);
				associateWorkOrderVo1.setCompanyDetailsId((Integer)listObjVal[2]);
				associateWorkOrderVo1.setVendorName(listObjVal[3]+"");
				associateWorkOrderVo1.setCustomerName( listObjVal[5]+"");
				associateWorkOrderVo1.setCompanyName( listObjVal[4]+"");
				associateWorkOrderVo1.setWorkrorderDetailId((Integer)obj[0]);
				associateWorkOrderVo1.setWorkOrderCode(obj[3]+"");
				associateWorkOrderVo1.setOrderPeriodFrom(obj[1]+"");
				associateWorkOrderVo1.setOrderPeriodTo(obj[2]+"");		
				associateWorkVo.getAssociateWorkOrderVo().add(associateWorkOrderVo1);
			}	
			if(tempList == null || tempList.size() == 0){
				AssociateWorkOrderVo associateWorkOrderVo1 = new AssociateWorkOrderVo();
				associateWorkOrderVo1.setVendorDetailsId((Integer)listObjVal[0]);
				associateWorkOrderVo1.setCustomerDetailsId((Integer)listObjVal[1]);
				associateWorkOrderVo1.setCompanyDetailsId((Integer)listObjVal[2]);
				associateWorkOrderVo1.setVendorName(listObjVal[3]+"");
				associateWorkOrderVo1.setCustomerName( listObjVal[5]+"");
				associateWorkOrderVo1.setCompanyName( listObjVal[4]+"");				
				associateWorkVo.getAssociateWorkOrderVo().add(associateWorkOrderVo1);
			}
			
		}catch(Exception e){
			log.error("Error Occured ",e);
		}		
		return associateWorkVo;
	}
	
	@Override
	public Integer saveAssociateWorkOrder(AssociateWorkOrderVo associateWorkOrderVo) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Integer id = 0;
		try{
			/*session.createSQLQuery("DELETE FROM  `associate_work_order` WHERE Vendor_Id="+associateWorkOrderVo.getVendorDetailsId()).executeUpdate();		
			for(AssociateWorkOrderVo orderVo : associateWorkOrderVo.getAssociateWorkOrderVo()){				
				AssociateWorkOrder workOrder = new AssociateWorkOrder();
				workOrder.setCustomerDetails(new CustomerDetails(associateWorkOrderVo.getCustomerDetailsId()));
				workOrder.setCompanyDetails( new CompanyDetails(associateWorkOrderVo.getCompanyDetailsId()));
				workOrder.setVendorDetails(new VendorDetails(associateWorkOrderVo.getVendorDetailsId()));
				workOrder.setWorkrorderDetail(new WorkrorderDetail(orderVo.getWorkrorderDetailId()));
				workOrder.setIsActive("Y");
				workOrder.setCreatedBy(associateWorkOrderVo.getCreatedBy());
				workOrder.setModifiedBy(associateWorkOrderVo.getModifiedBy());
				workOrder.setCreatedDate(new Date());
				workOrder.setModifiedDate(new Date());				
				session.save(workOrder);
			}*/
			if(associateWorkOrderVo.getAction() != null && (associateWorkOrderVo.getAction().equalsIgnoreCase("save") || associateWorkOrderVo.getAction().equalsIgnoreCase("delete")) ){
				session.createSQLQuery("DELETE FROM  `associate_work_order` WHERE Vendor_Id="+associateWorkOrderVo.getVendorDetailsId()+" AND WorkOrder_id="+associateWorkOrderVo.getWorkrorderDetailId()).executeUpdate();
			}else if(associateWorkOrderVo.getAction() != null && associateWorkOrderVo.getAction().equalsIgnoreCase("update")){			
				session.createSQLQuery("DELETE FROM  `associate_work_order` WHERE Vendor_Id="+associateWorkOrderVo.getVendorDetailsId()+" AND WorkOrder_id="+associateWorkOrderVo.getOldWorkrorderDetailId()).executeUpdate();
			}
			if(associateWorkOrderVo.getAction() != null && (associateWorkOrderVo.getAction().equalsIgnoreCase("save") || associateWorkOrderVo.getAction().equalsIgnoreCase("update")) ){
				AssociateWorkOrder workOrder = new AssociateWorkOrder();
				workOrder.setCustomerDetails(new CustomerDetails(associateWorkOrderVo.getCustomerDetailsId()));
				workOrder.setCompanyDetails( new CompanyDetails(associateWorkOrderVo.getCompanyDetailsId()));
				workOrder.setVendorDetails(new VendorDetails(associateWorkOrderVo.getVendorDetailsId()));
				workOrder.setWorkrorderDetail(new WorkrorderDetail(associateWorkOrderVo.getWorkrorderDetailId()));
				workOrder.setIsActive("Y");
				workOrder.setCreatedBy(associateWorkOrderVo.getCreatedBy());
				workOrder.setModifiedBy(associateWorkOrderVo.getModifiedBy());
				workOrder.setCreatedDate(new Date());
				workOrder.setModifiedDate(new Date());				
				session.save(workOrder);
			}
			session.flush();
		}catch(Exception e){
			log.error("Error Occured ",e);
		}		
		return id;
	}


	@SuppressWarnings("rawtypes")
	@Override
	public List<AssociateWorkOrderVo> getWorkOrderList(AssociateWorkOrderVo associateWorkOrderVo) {
		// TODO Auto-generated method stub		
		List<AssociateWorkOrderVo> associateWorkOrderVoList = new ArrayList<AssociateWorkOrderVo>();	
		Session session = sessionFactory.getCurrentSession();
		try{
			String query = "SELECT  parent.`WorkOrder_id`, CONCAT(`WorkOrder_Code`,' - ',`Work_Order_Name`),`Work_Order_Name` FROM `workrorder_detail` parent INNER JOIN `workorder_detail_info` child ON (parent.`WorkOrder_id` = child.`WorkOrder_id`) WHERE CONCAT(DATE_FORMAT(child.transaction_date, '%Y%m%d'), LPAD(child.Sequence_Id,2,'0')) = ( SELECT MAX(CONCAT(DATE_FORMAT(child1.transaction_date, '%Y%m%d'), LPAD(child1.Sequence_Id,2,'0'))) FROM workorder_detail_info child1 WHERE child.`WorkOrder_id` = child1.`WorkOrder_id`  AND child1.transaction_date <= CURRENT_DATE() ) AND parent.`WorkOrder_id` NOT IN (SELECT `WorkOrder_id` FROM `associate_work_order`) AND child.is_active = 'Y' GROUP BY  parent.`WorkOrder_id` ORDER BY WorkOrder_Code";
			List tempList = session.createSQLQuery(query).list();			
			for(Object o : tempList){
				Object[] obj = (Object[]) o;
				AssociateWorkOrderVo associateWorkOrderVo1 = new AssociateWorkOrderVo();
				associateWorkOrderVo1.setWorkrorderDetailId((Integer)obj[0]);				
				associateWorkOrderVo1.setWorkOrderName(obj[1]+"");
				associateWorkOrderVo1.setWorkOrderCode(obj[2]+"");
				associateWorkOrderVoList.add(associateWorkOrderVo1);
			}	
			
		}catch(Exception e){
			log.error("Error Occured ",e);
		}		
		return associateWorkOrderVoList;
	}


	@Override
	public Map<String, String> workorderChangeListener(Integer workrorderDetailId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Map<String, String> map = new HashMap<String, String>();
		try{
			String query = "SELECT DATE_FORMAT(Work_Period_From,'%d/%m/%Y'),DATE_FORMAT(Work_Period_To,'%d/%m/%Y') FROM `workorder_detail_info` parent WHERE CONCAT(DATE_FORMAT(parent.transaction_date, '%Y%m%d'), LPAD(parent.Sequence_Id,2,'0')) = ( SELECT MAX(CONCAT(DATE_FORMAT(child1.transaction_date, '%Y%m%d'), LPAD(child1.Sequence_Id,2,'0'))) FROM workorder_detail_info child1 WHERE parent.`WorkOrder_id` = child1.`WorkOrder_id`  AND child1.transaction_date <= CURRENT_DATE() ) AND WorkOrder_id = "+workrorderDetailId;
			List tempList = session.createSQLQuery(query).list();			
			for(Object o : tempList){
				Object[] obj = (Object[]) o;
				map.put("orderPeriodFrom", obj[0]+"");
				map.put("orderPeriodTo", obj[1]+"");
				break;
			}	
			
		}catch(Exception e){
			log.error("Error Occured ",e);
		}	
		return map;
	}
	
	
}
