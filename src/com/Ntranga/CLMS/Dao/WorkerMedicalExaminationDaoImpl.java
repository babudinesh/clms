package com.Ntranga.CLMS.Dao;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.Ntranga.CLMS.vo.WorkerMedicalExaminationVo;
import com.Ntranga.core.DateHelper;
import com.Ntranga.core.CLMS.entities.CompanyDetails;
import com.Ntranga.core.CLMS.entities.CustomerDetails;
import com.Ntranga.core.CLMS.entities.VendorDetails;
import com.Ntranga.core.CLMS.entities.WorkerDetails;
import com.Ntranga.core.CLMS.entities.WorkerMedicalExamination;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import common.Logger;

@SuppressWarnings({"rawtypes"})
@Transactional
@Repository("workerMedicalExaminationDao")
public class WorkerMedicalExaminationDaoImpl implements WorkerMedicalExaminationDao {

	private static Logger log = Logger.getLogger(WorkerMedicalExaminationDaoImpl.class);
	
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private @Value("#{system['WORKER_MEDICAL_EXAMINATION']}")
	String workermedicalexamination;
	
	public List<WorkerMedicalExaminationVo> getWorkerMedicalExaminationDetailsByWorkerId(int workerId) {
		List<WorkerMedicalExaminationVo> masterInfoMap = new ArrayList<WorkerMedicalExaminationVo>();	
		Session session = sessionFactory.getCurrentSession();
		try{
			 String q = " SELECT Medical_Examination_Id,Customer_Id,Company_Id,Vendor_Id,Worker_Id,Height,Weight,Pulse, "+
						" Blood_Pressure,Hearing,Color_Blindness,Eye_Vision,R,Blood_Group,Respiratory_System, "+
						" Ans_Autonomous_Nerves_System,Liver_Or_Spleen,PastHistory,Any_Operation_Done_In_Past, "+
						" oeema,Cunosis,Jaundice,Medical_Officer_Comment,Any_Other_Physical_Disability,Medically_Fit, "+
						" Review_Date,Signature_Of_Doctor,File_Path,Created_By,Created_Date,Modified_By,Modified_Date,File_Name,country_id,height_type,weight_type "+
						" FROM worker_medical_examination where Worker_Id = "+workerId;	 		
						 
	
	 List tempList = session.createSQLQuery(q).list();
	// System.out.println(q);
	for(Object o : tempList){
		Object[] obj = (Object[]) o;
		WorkerMedicalExaminationVo workerMedicalExaminationVo = new WorkerMedicalExaminationVo();
		workerMedicalExaminationVo.setMedicalExaminationId((Integer)obj[0]);
		workerMedicalExaminationVo.setCustomerId((Integer)obj[1]);
		workerMedicalExaminationVo.setCompanyId((Integer)obj[2]);
		workerMedicalExaminationVo.setVendorId((Integer)obj[3]);
		workerMedicalExaminationVo.setWorkerId((Integer)obj[4]);		
		workerMedicalExaminationVo.setHeight(obj[5] != null ? obj[5]+"" : "");
		workerMedicalExaminationVo.setWeight(obj[6] != null ? obj[6]+"" : "");
		workerMedicalExaminationVo.setPulse(obj[7] != null ? obj[7]+"" : "");
		
		workerMedicalExaminationVo.setBloodPressure(obj[8] != null ? obj[8]+"" : "");
		workerMedicalExaminationVo.setHearing(obj[9] != null ? obj[9]+"" : "");
		
		workerMedicalExaminationVo.setColorBlindness(obj[10] != null ? obj[10]+"" : "");
		workerMedicalExaminationVo.setEyeVision(obj[11] != null ? obj[11]+"" : "");
		
		workerMedicalExaminationVo.setR(obj[12] != null ? obj[12]+"" : "");
		workerMedicalExaminationVo.setBloodGroup(obj[13] != null ? obj[13]+"" : "");
		workerMedicalExaminationVo.setRespiratorySystem(obj[14] != null ? obj[14]+"" : "");		
		workerMedicalExaminationVo.setAnsAutonomousNervesSystem(obj[15] != null ? obj[15]+"" : "");
		
		workerMedicalExaminationVo.setLiverOrSpleen(obj[16] != null ? obj[16]+"" : "");
		workerMedicalExaminationVo.setPastHistory(obj[17] != null ? obj[17]+"" : "");
		
		workerMedicalExaminationVo.setAnyOperationDoneInPast(obj[18] != null ? obj[18]+"" : "");
		workerMedicalExaminationVo.setOeema(obj[19] != null ? obj[19]+"" : "");
		
		workerMedicalExaminationVo.setCunosis(obj[20] != null ? obj[20]+"" : "");
		workerMedicalExaminationVo.setJaundice(obj[21] != null ? obj[21]+"" : "");
		workerMedicalExaminationVo.setMedicalOfficerComment(obj[22] != null ? obj[22]+"" : "");
		workerMedicalExaminationVo.setAnyOtherPhysicalDisability(obj[23] != null ? obj[23]+"" : "");
		workerMedicalExaminationVo.setMedicallyFit(obj[24] != null ? obj[24]+"" : "");
		workerMedicalExaminationVo.setReviewDate(obj[25] != null ? (Date)obj[25]:null);
		workerMedicalExaminationVo.setSignatureOfDoctor(obj[26] != null ? obj[26]+"" : "");
		workerMedicalExaminationVo.setFilePath(obj[27] != null ? obj[27]+"" : "");		
		workerMedicalExaminationVo.setCreatedBy(obj[28] != null ? (Integer)obj[28] : null);
		workerMedicalExaminationVo.setCreatedDate((Date)obj[29]);
		workerMedicalExaminationVo.setModifiedBy((Integer)obj[30]);
		workerMedicalExaminationVo.setModifiedDate((Date)obj[31]);
		workerMedicalExaminationVo.setFileName(obj[32] != null ? obj[32]+"" : "");
		workerMedicalExaminationVo.setCountryId(obj[33] != null ? (Integer) obj[33] : null);
		workerMedicalExaminationVo.setHeightType(obj[34] != null ? obj[34]+"" : "");
		workerMedicalExaminationVo.setWeightType(obj[35] != null ? obj[35]+"" : "");
		masterInfoMap.add(workerMedicalExaminationVo);
	}	
			
			
		}catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return masterInfoMap;
	}

	


	public Integer saveOrUpdateWorkerMedicalExaminationDetails(String name, MultipartFile[] files) {
		Integer workerMedicalExaminationId = 0;
		Session session = null;
		WorkerMedicalExamination details = null;
		try{
			WorkerMedicalExaminationVo workerMedicalExaminationVo = new WorkerMedicalExaminationVo();
			
			session = sessionFactory.getCurrentSession();
			
			System.out.println(files.length+":::=======");
			
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(name);
			//System.out.println(jo.get("medicalExaminationId").getAsInt()+":::=====");
			
			if(jo.has("medicalExaminationId") && !jo.get("medicalExaminationId").isJsonNull() && jo.get("medicalExaminationId") != null  && jo.get("medicalExaminationId").getAsInt() > 0){
				details = (WorkerMedicalExamination) session.load(WorkerMedicalExamination.class,jo.get("medicalExaminationId").getAsInt()) ;
				workerMedicalExaminationId = jo.get("medicalExaminationId").getAsInt() ;
			}else{
				details = new WorkerMedicalExamination(); 
			}
			
			details.setCustomerDetails(new CustomerDetails(jo.get("customerId").getAsInt()));
			details.setCompanyDetails(new CompanyDetails(jo.get("companyId").getAsInt()));
			details.setVendorDetails(new VendorDetails(jo.get("vendorId").getAsInt()));
			details.setWorkerDetails(new WorkerDetails(jo.get("workerId").getAsInt()));	
			details.setCountryId(jo.get("countryId").getAsInt());
			details.setHeight(!jo.has("height") ? null : (jo.get("height").isJsonNull() ? null : jo.get("height").getAsString()));
			details.setWeight(!jo.has("weight") ? null : (jo.get("weight").isJsonNull() ? null : jo.get("weight").getAsString()));
			details.setPulse(!jo.has("pulse") ? null : (jo.get("pulse").isJsonNull() ? null : jo.get("pulse").getAsString()));
			details.setHeightType(!jo.has("heightType") ? null : (jo.get("heightType").isJsonNull() ? null : jo.get("heightType").getAsString()));
			details.setWeightType(!jo.has("weightType") ? null : (jo.get("weightType").isJsonNull() ? null : jo.get("weightType").getAsString()));
			details.setBloodPressure(!jo.has("bloodPressure") ? null : (jo.get("bloodPressure").isJsonNull() ? null : jo.get("bloodPressure").getAsString()));
			details.setHearing(!jo.has("hearing") ? null : (jo.get("hearing").isJsonNull() ? null : jo.get("hearing").getAsString()));
			
			details.setColorBlindness(!jo.has("colorBlindness") ? null : (jo.get("colorBlindness").isJsonNull() ? null : jo.get("colorBlindness").getAsString()));
			details.setEyeVision(!jo.has("eyeVision") ? null : (jo.get("eyeVision").isJsonNull() ? null : jo.get("eyeVision").getAsString()));
			
			details.setR(!jo.has("r") ? null : (jo.get("r").isJsonNull() ? null : jo.get("r").getAsString()));
			details.setBloodGroup(!jo.has("bloodGroup") ? null : (jo.get("bloodGroup").isJsonNull() ? null : jo.get("bloodGroup").getAsString()));
			details.setRespiratorySystem(!jo.has("respiratorySystem") ? null : (jo.get("respiratorySystem").isJsonNull() ? null : jo.get("respiratorySystem").getAsString()));		
			details.setAnsAutonomousNervesSystem(!jo.has("ansAutonomousNervesSystem") ? null : (jo.get("ansAutonomousNervesSystem").isJsonNull() ? null : jo.get("ansAutonomousNervesSystem").getAsString()));
			
			details.setLiverOrSpleen(!jo.has("liverOrSpleen") ? null : (jo.get("liverOrSpleen").isJsonNull() ? null : jo.get("liverOrSpleen").getAsString()));
			details.setPastHistory(!jo.has("pastHistory") ? null : (jo.get("pastHistory").isJsonNull() ? null : jo.get("pastHistory").getAsString()));
			
			details.setAnyOperationDoneInPast(!jo.has("anyOperationDoneInPast") ? null : (jo.get("anyOperationDoneInPast").isJsonNull() ? null : jo.get("anyOperationDoneInPast").getAsString()));
			details.setOeema(!jo.has("oeema") ? null : (jo.get("oeema").isJsonNull() ? null : jo.get("oeema").getAsString()));
			
			details.setCunosis(!jo.has("cunosis") ? null : (jo.get("cunosis").isJsonNull() ? null : jo.get("cunosis").getAsString()));
			details.setJaundice(!jo.has("jaundice") ? null : (jo.get("jaundice").isJsonNull() ? null : jo.get("jaundice").getAsString()));
			details.setMedicalOfficerComment(!jo.has("medicalOfficerComment") ? null : (jo.get("medicalOfficerComment").isJsonNull() ? null : jo.get("medicalOfficerComment").getAsString()));
			details.setAnyOtherPhysicalDisability(!jo.has("anyOtherPhysicalDisability") ? null : (jo.get("anyOtherPhysicalDisability").isJsonNull() ? null : jo.get("anyOtherPhysicalDisability").getAsString()));
			details.setMedicallyFit(!jo.has("medicallyFit") ? null : (jo.get("medicallyFit").isJsonNull() ? null : jo.get("medicallyFit").getAsString()));
			details.setReviewDate(DateHelper.convertStringToSQLDate(jo.get("reviewDate").getAsString()));
			details.setSignatureOfDoctor(!jo.has("signatureOfDoctor") ? null : (jo.get("signatureOfDoctor").isJsonNull() ? null : jo.get("signatureOfDoctor").getAsString()));
			//details.setFilePath(jo.get("filePath").getAsString());
			details.setModifiedBy(jo.get("modifiedBy").getAsInt());
			details.setModifiedDate(new Date());
			
			
			
			if(jo.has("medicalExaminationId") && !jo.get("medicalExaminationId").isJsonNull() && jo.get("medicalExaminationId") != null  && jo.get("medicalExaminationId").getAsInt() > 0){
				session.update(details);
			}else{				
				details.setCreatedBy(jo.get("createdBy").getAsInt());
				details.setCreatedDate(new Date());
				workerMedicalExaminationId = (Integer) session.save(details);
			}
			if(files.length > 0  ){
				 for(MultipartFile multipartFile :files ){						 
					String path = workermedicalexamination + workerMedicalExaminationId+"."+multipartFile.getOriginalFilename().split("\\.")[1];;
					 if(! new File(path).exists()){
				            new File(path).mkdirs();
				     }
					File destinationPath = new File(path);
					multipartFile.transferTo(destinationPath);
					details.setFilePath(path);
					details.setFileName(multipartFile.getOriginalFilename());	 
					 }
			}
			session.flush();
			
		}catch(Exception e){
			
			log.error("Error Occured ",e);
		}
		return workerMedicalExaminationId;
	}

	
	
	
	

}
