package com.Ntranga.CLMS.Dao;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.Ntranga.CLMS.vo.WorkerDetailsVo;
import com.Ntranga.CLMS.vo.WorkerIdentificationProofPoliceVerificationVo;
import com.Ntranga.CLMS.vo.WorkerIdentificationProofVo;
import com.Ntranga.CLMS.vo.WorkerMedicalExaminationVo;
import com.Ntranga.CLMS.vo.WorkerPoliceVerificationVo;
import com.Ntranga.core.DateHelper;
import com.Ntranga.core.CLMS.entities.CompanyDetails;
import com.Ntranga.core.CLMS.entities.CustomerDetails;
import com.Ntranga.core.CLMS.entities.VendorDetails;
import com.Ntranga.core.CLMS.entities.WorkerDetails;
import com.Ntranga.core.CLMS.entities.WorkerIdentificationProof;
import com.Ntranga.core.CLMS.entities.WorkerIdentificationProofPoliceVerification;
import com.Ntranga.core.CLMS.entities.WorkerMedicalExamination;
import com.Ntranga.core.CLMS.entities.WorkerPoliceVerification;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import common.Logger;

@SuppressWarnings({"rawtypes"})
@Transactional
@Repository("workerPoliceVerificationDao")
public class WorkerPoliceVerificationDaoImpl implements WorkerPoliceVerificationDao {

	private static Logger log = Logger.getLogger(WorkerPoliceVerificationDaoImpl.class);
	
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private @Value("#{system['WORKER_POLICE_VERIFICATION']}")
	String workerPoliceVerification;
	
	
	
	
	public List<WorkerPoliceVerificationVo> getWorkerPoliceVerificationDetailsByWorkerId(int workerId) {
		List<WorkerPoliceVerificationVo> workerPoliceVerificationList = new ArrayList<WorkerPoliceVerificationVo>();	
		Session session = sessionFactory.getCurrentSession();
		Integer policeVerificationId = 0;
		WorkerPoliceVerification policeVo = null;
		WorkerPoliceVerificationVo details = new WorkerPoliceVerificationVo();
		try{
			 String q = " SELECT Police_Verification_Id  FROM Worker_Police_Verification where Worker_Id = "+workerId;	
			 List tempList = session.createSQLQuery(q).list();	
			for(Object o : tempList){
				policeVerificationId = (Integer) o;				
			}				
			
			if(policeVerificationId > 0){
				policeVo = (WorkerPoliceVerification) session.load(WorkerPoliceVerification.class,policeVerificationId) ;
				details.setPoliceVerificationId(policeVerificationId);
				details.setCustomerId(policeVo.getCustomerDetails().getCustomerId());
				details.setCompanyId(policeVo.getCompanyDetails().getCompanyId());
				details.setVendorId(policeVo.getVendorDetails().getVendorId());
				details.setWorkerId(policeVo.getWorkerDetails().getWorkerId());	
				details.setCountryId(policeVo.getCountryId());
				details.setFatherName(policeVo.getFatherName());
				details.setMotherName(policeVo.getMotherName());
				details.setMobileNumber(policeVo.getMobileNumber());
				details.setLanguage(policeVo.getLanguage());
				details.setNationality(policeVo.getNationality());
				details.setEducation(policeVo.getEducation());
				details.setPreviousEmployer(policeVo.getPreviousEmployer());
				details.setTotalExperience(policeVo.getTotalExperience());
				details.setVisibleIdentificationMarksOne(policeVo.getVisibleIdentificationMarksOne());
				details.setVisibleIdentificationMarksTwo(policeVo.getVisibleIdentificationMarksTwo());
				
				details.setRelativesOrFriendsName(policeVo.getRelativesOrFriendsName());
				details.setPhoneNumber(policeVo.getPhoneNumber());
				details.setPropertyDetails(policeVo.getPropertyDetails());
				details.setHobbies(policeVo.getHobbies());
				details.setDresses(policeVo.getDresses());
				details.setHabit(policeVo.getHabit());
				details.setSocialActivity(policeVo.getSocialActivity());
				details.setProhibitedActivity(policeVo.getProhibitedActivity());
				details.setHiddenActivity(policeVo.getHiddenActivity());
				details.setSpecial(policeVo.getSpecial());
				details.setLeftHandThumbPrint(policeVo.getLeftHandThumbPrint());
				details.setSignature(policeVo.getSignature());
				details.setReviewDate(policeVo.getReviewDate());
				details.setPlace(policeVo.getPlace());
				details.setDateOne(policeVo.getDateOne());
				details.setDateTwo(policeVo.getDateTwo());
				details.setThePoliceStationDetailsOne(policeVo.getThePoliceStationDetailsOne());
				details.setThePoliceStationDetailsTwo(policeVo.getThePoliceStationDetailsTwo());
				details.setLetterNumber(policeVo.getLetterNumber());
				details.setDatedOn(policeVo.getDatedOn());
				details.setNameOfContract(policeVo.getNameOfContract());
				details.setContractEngagedWith(policeVo.getContractEngagedWith());
				details.setSignatureOfProprietor(policeVo.getSignatureOfProprietor());
				details.setFileName(policeVo.getFileName());
				details.setFilePath(policeVo.getFilePath());
				details.setLetterPlace(policeVo.getLetterPlace());
				details.setCreatedBy(policeVo.getCreatedBy());
				details.setCreatedDate(policeVo.getCreatedDate());
				
				String idQuery = "SELECT Identity_Type,Identification_Number,Identity_Place_Of_Issue,Identity_Date_Of_Issue FROM  worker_identification_proof_police_verification where police_Verification_Id =  "+policeVerificationId;
						 
			 					
			    System.out.println("query: "+idQuery);	
			    
			    List identityList = session.createSQLQuery(idQuery).list();
			    
			    WorkerIdentificationProofPoliceVerificationVo identity = new WorkerIdentificationProofPoliceVerificationVo();
			    List<WorkerIdentificationProofPoliceVerificationVo> idList = new ArrayList<WorkerIdentificationProofPoliceVerificationVo>();
			    log.info("Size of identity List  "+identityList.size());
			    
			    if(identityList != null && identityList.size() > 0){
					for (Object o1 : identityList) {
						Object[] obj1 = (Object[]) o1;
						identity = new WorkerIdentificationProofPoliceVerificationVo();
						identity.setIdentityType(obj1[0]+"");						
						identity.setIdentificationNumber(obj1[1]+"");
						identity.setIdentityPlaceOfIssue(obj1[2]+"");
						identity.setIdentityDateOfIssue(DateHelper.convertDateToString((Date)obj1[3], "dd/MM/yyyy"));						
						idList.add(identity);
					 }
			    }
			    details.setIdentityList(idList);		
				
				workerPoliceVerificationList.add(details);
			}
			
		}catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return workerPoliceVerificationList;
	}
	
	
	public List<WorkerPoliceVerificationVo> getWorkerAddressByWorkerInfoId(int workerInfoId) {
		List<WorkerPoliceVerificationVo> workerAddressList = new ArrayList<WorkerPoliceVerificationVo>();	
		Session session = sessionFactory.getCurrentSession();
		
		
		try{
			String q1 = " SELECT CONCAT(CASE WHEN Address_line1 IS NULL THEN '' ELSE CONCAT(Address_line1,', ') END, "+
						" CASE WHEN Address_line2 IS NULL THEN '' ELSE CONCAT(Address_line2,', ') END ,  "+
						"  CASE WHEN Address_line3 IS NULL THEN '' ELSE CONCAT(Address_line3,', ') END , "+
						"   CASE WHEN mc.Country_name IS NULL THEN '' ELSE CONCAT(mc.Country_name,', ') END , "+
						"  CASE WHEN  ms.State_name IS NULL THEN '' ELSE CONCAT(ms.State_name,', ') END , "+
						" CASE WHEN  City IS NULL THEN '' ELSE CONCAT(City,', ') END , "+
						"  CASE WHEN  pin_code IS NULL THEN '' ELSE CONCAT(pin_code,'. ') END ) AS address,address_type "+
						"   FROM worker_details_info wdi   "+
						"  LEFT JOIN worker_address wa ON(wdi.worker_id = wa.worker_id AND wdi.sequence_id = wa.sequence_id  "+
						"  AND wdi.transaction_date = wa.transaction_date) "+
						"  LEFT JOIN m_country mc ON(mc.Country_Id = wa.country_id) "+
						"  LEFT JOIN m_state ms ON (ms.state_id = wa.state_id) "
						+" WHERE  wdi.`Worker_info_id` = "+workerInfoId;
		 					
				System.out.println("query"+q1);					 

				List addressList = session.createSQLQuery(q1).list();
				for (Object o1 : addressList) {
					Object[] obj1 = (Object[]) o1;
					WorkerPoliceVerificationVo details = new WorkerPoliceVerificationVo();
					if (obj1[1] != null && ((String) obj1[1]).equalsIgnoreCase("Permanent")) {
						details.setPermanentAddress((String)obj1[0]);
					} else if (obj1[1] != null) {						
						details.setPresentAddress((String)obj1[0]);					
					}
					workerAddressList.add(details)	;
				}
			
			
		}catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return workerAddressList;
	}

	


	public Integer saveOrUpdateWorkerPoliceVerificationDetails(String name, MultipartFile[] files) {
		Integer policeVerificationId = 0;
		Session session = null;
		WorkerPoliceVerification details = null;
		try{
			
			
			session = sessionFactory.getCurrentSession();			
		
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(name);
			
			
			if(jo.has("policeVerificationId") && !jo.get("policeVerificationId").isJsonNull() && jo.get("policeVerificationId") != null  && jo.get("policeVerificationId").getAsInt() > 0){
				details = (WorkerPoliceVerification) session.load(WorkerPoliceVerification.class,jo.get("policeVerificationId").getAsInt()) ;
				policeVerificationId = jo.get("policeVerificationId").getAsInt();
			}else{
				details = new WorkerPoliceVerification(); 
			}
			
			details.setCustomerDetails(new CustomerDetails(jo.get("customerId").getAsInt()));
			details.setCompanyDetails(new CompanyDetails(jo.get("companyId").getAsInt()));
			//System.out.println(policeVo.getVendorId()+":::====");
			details.setVendorDetails(new VendorDetails(jo.get("vendorId").getAsInt()));
			details.setWorkerDetails(new WorkerDetails(jo.get("workerId").getAsInt()));	
			details.setCountryId(jo.get("countryId").getAsInt());
			details.setFatherName(!jo.has("fatherName") ? null : (jo.get("fatherName").isJsonNull() ? null : jo.get("fatherName").getAsString()));
			details.setMotherName(!jo.has("motherName") ? null : (jo.get("motherName").isJsonNull() ? null : jo.get("motherName").getAsString()));
			details.setMobileNumber(!jo.has("mobileNumber") ? null : (jo.get("mobileNumber").isJsonNull() ? null : jo.get("mobileNumber").getAsString()));
			details.setLanguage(!jo.has("language") ? null : (jo.get("language").isJsonNull() ? null : jo.get("language").getAsString()));
			details.setNationality(!jo.has("nationality") ? null : (jo.get("nationality").isJsonNull() ? null : jo.get("nationality").getAsString()));
			details.setEducation(!jo.has("education") ? null : (jo.get("education").isJsonNull() ? null : jo.get("education").getAsString()));
			details.setPreviousEmployer(!jo.has("previousDeveloper") ? null : (jo.get("previousDeveloper").isJsonNull() ? null : jo.get("previousDeveloper").getAsString()));
			details.setTotalExperience(!jo.has("totalExperience") ? null : (jo.get("totalExperience").isJsonNull() ? null : jo.get("totalExperience").getAsString()));
			details.setVisibleIdentificationMarksOne(!jo.has("visibleIdentificationMarksOne") ? null : (jo.get("visibleIdentificationMarksOne").isJsonNull() ? null : jo.get("visibleIdentificationMarksOne").getAsString()));
			details.setVisibleIdentificationMarksTwo(!jo.has("visibleIdentificationMarksTwo") ? null : (jo.get("visibleIdentificationMarksTwo").isJsonNull() ? null : jo.get("visibleIdentificationMarksTwo").getAsString()));			
			details.setRelativesOrFriendsName(!jo.has("relativesOrFriendsName") ? null : (jo.get("relativesOrFriendsName").isJsonNull() ? null : jo.get("relativesOrFriendsName").getAsString()));
			details.setPhoneNumber(!jo.has("phoneNumber") ? null : (jo.get("phoneNumber").isJsonNull() ? null : jo.get("phoneNumber").getAsString()));
			details.setPropertyDetails(!jo.has("propertyDetails") ? null : (jo.get("propertyDetails").isJsonNull() ? null : jo.get("propertyDetails").getAsString()));
			details.setHobbies(!jo.has("hobbies") ? null : (jo.get("hobbies").isJsonNull() ? null : jo.get("hobbies").getAsString()));
			details.setDresses(!jo.has("dresses") ? null : (jo.get("dresses").isJsonNull() ? null : jo.get("dresses").getAsString()));
			details.setHabit(!jo.has("habit") ? null : (jo.get("habit").isJsonNull() ? null : jo.get("habit").getAsString()));
			details.setSocialActivity(!jo.has("socialActivity") ? null : (jo.get("socialActivity").isJsonNull() ? null : jo.get("socialActivity").getAsString()));
			details.setProhibitedActivity(!jo.has("prohibitedActivity") ? null : (jo.get("prohibitedActivity").isJsonNull() ? null : jo.get("prohibitedActivity").getAsString()));
			details.setHiddenActivity(!jo.has("hiddenActivity") ? null : (jo.get("hiddenActivity").isJsonNull() ? null : jo.get("hiddenActivity").getAsString()));
			details.setSpecial(!jo.has("special") ? null : (jo.get("special").isJsonNull() ? null : jo.get("special").getAsString()));
			details.setLeftHandThumbPrint(!jo.has("leftHandThumbPrint") ? null : (jo.get("leftHandThumbPrint").isJsonNull() ? null : jo.get("leftHandThumbPrint").getAsString()));
			details.setSignature(!jo.has("signature") ? null : (jo.get("signature").isJsonNull() ? null : jo.get("signature").getAsString()));
		//	details.setReviewDate(policeVo.getReviewDate());
		//	details.setPlace(policeVo.getPlace());
			
		//	System.out.println(jo.has("dateOne")+"=====1");
			//System.out.println(jo.get("dateOne").isJsonNull()+"=====2");
			//System.out.println(jo.get("dateOne").getAsString()+"=====2");
			details.setDateOne(!jo.has("dateOne") ? null : (jo.get("dateOne").isJsonNull() || jo.get("dateOne") == null ? null : DateHelper.convertStringToSQLDate(jo.get("dateOne").getAsString())));
			details.setDateTwo(!jo.has("dateTwo") ? null : (jo.get("dateTwo").isJsonNull() || jo.get("dateTwo") == null? null : DateHelper.convertStringToSQLDate(jo.get("dateTwo").getAsString())));
			details.setThePoliceStationDetailsOne(!jo.has("thePoliceStationDetailsOne") ? null : (jo.get("thePoliceStationDetailsOne").isJsonNull() ? null : jo.get("thePoliceStationDetailsOne").getAsString()));
			details.setThePoliceStationDetailsTwo(!jo.has("thePoliceStationDetailsTwo") ? null : (jo.get("thePoliceStationDetailsTwo").isJsonNull() ? null : jo.get("thePoliceStationDetailsTwo").getAsString()));
			details.setLetterNumber(!jo.has("letterNumber") ? null : (jo.get("letterNumber").isJsonNull() ? null : jo.get("letterNumber").getAsString()));
			details.setDatedOn(!jo.has("datedOn") ? null : (jo.get("datedOn").isJsonNull() ? null : DateHelper.convertStringToSQLDate(jo.get("datedOn").getAsString())));
			details.setNameOfContract(!jo.has("nameOfContract") ? null : (jo.get("nameOfContract").isJsonNull() ? null : jo.get("nameOfContract").getAsString()));
			details.setContractEngagedWith(!jo.has("contractEngagedWith") ? null : (jo.get("contractEngagedWith").isJsonNull() ? null : jo.get("contractEngagedWith").getAsString()));
			details.setSignatureOfProprietor(!jo.has("signatureOfProprietor") ? null : (jo.get("signatureOfProprietor").isJsonNull() ? null : jo.get("signatureOfProprietor").getAsString()));
			details.setLetterPlace(!jo.has("letterPlace") ? null : (jo.get("letterPlace").isJsonNull() ? null : jo.get("letterPlace").getAsString()));			
			details.setModifiedBy(!jo.has("modifiedBy") ? null : (jo.get("modifiedBy").isJsonNull() ? null : jo.get("modifiedBy").getAsInt()));
			details.setModifiedDate(new Date());
			
			
			if(jo.has("policeVerificationId") && !jo.get("policeVerificationId").isJsonNull() && jo.get("policeVerificationId") != null  && jo.get("policeVerificationId").getAsInt() > 0){
				session.update(details);
			}else{				
				details.setCreatedBy(!jo.has("createdBy") ? null : (jo.get("createdBy").isJsonNull() ? null : jo.get("createdBy").getAsInt()));
				details.setCreatedDate(new Date());
				policeVerificationId = (Integer) session.save(details);
			}
			
			
			if(files.length > 0  ){
				 for(MultipartFile multipartFile :files){						 
					String path = workerPoliceVerification + policeVerificationId+"."+multipartFile.getOriginalFilename().split("\\.")[1];;
					 if(! new File(path).exists()){
				            new File(path).mkdirs();
				     }
					File destinationPath = new File(path);
					multipartFile.transferTo(destinationPath);
					details.setFilePath(path);
					details.setFileName(multipartFile.getOriginalFilename());	 
					 }
			}
			
			WorkerIdentificationProofPoliceVerification  identificationProof = new WorkerIdentificationProofPoliceVerification();
			if(jo.get("identityList") != null && jo.get("identityList").getAsJsonArray().size() > 0 ){						
				Query q = session.createSQLQuery("delete from Worker_Identification_Proof_Police_Verification where police_Verification_Id = '"+policeVerificationId+"'");
				q.executeUpdate();
			 
				
				JsonArray array = jo.get("identityList").getAsJsonArray();
				//System.out.println(!jo.get("customerId").getAsString().toString().equalsIgnoreCase("null"));
				for (Object o : array) {				
					JsonObject jobj = (JsonObject) o;
					identificationProof.setCustomerDetails(new CustomerDetails(jo.get("customerId").getAsInt()));
					identificationProof.setCompanyDetails(new CompanyDetails(jo.get("companyId").getAsInt()));	
					identificationProof.setWorkerPoliceVerification(new WorkerPoliceVerification(policeVerificationId));					
					identificationProof.setIdentityType(!jobj.has("identityType") ? null : (jobj.get("identityType").isJsonNull() ? null : jobj.get("identityType").getAsString()));						
					identificationProof.setIdentificationNumber(!jobj.has("identificationNumber") ? null : (jobj.get("identificationNumber").isJsonNull() ? null : jobj.get("identificationNumber").getAsString()));
					identificationProof.setIdentityPlaceOfIssue(!jobj.has("identityPlaceOfIssue") ? null : (jobj.get("identityPlaceOfIssue").isJsonNull() ? null : jobj.get("identityPlaceOfIssue").getAsString()));
					System.out.println(jobj.get("identityDateOfIssue").getAsString());
					identificationProof.setIdentityDateOfIssue(DateHelper.convertStringToDate(jobj.get("identityDateOfIssue").getAsString(), "dd/MM/yyyy"));						
					identificationProof.setCreatedBy(!jo.has("createdBy") ? null : (jo.get("createdBy").isJsonNull() ? null : jo.get("createdBy").getAsInt()));
					identificationProof.setCreatedDate(new Date());
					identificationProof.setModifiedBy(!jo.has("modifiedBy") ? null : (jo.get("modifiedBy").isJsonNull() ? null : jo.get("modifiedBy").getAsInt()));
					identificationProof.setModifiedDate(new Date());
					session.save(identificationProof);		
					
				}
				
			
			}
			
			session.flush();
			
		}catch(Exception e){
			
			log.error("Error Occured ",e);
		}
		return policeVerificationId;
	}

	
	
	
	

}
