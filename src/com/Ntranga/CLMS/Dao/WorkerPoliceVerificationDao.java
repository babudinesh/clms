package com.Ntranga.CLMS.Dao;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.Ntranga.CLMS.vo.WorkerDetailsVo;
import com.Ntranga.CLMS.vo.WorkerPoliceVerificationVo;

public interface WorkerPoliceVerificationDao {

	
	public List<WorkerPoliceVerificationVo> getWorkerPoliceVerificationDetailsByWorkerId(int workerId);

	public Integer saveOrUpdateWorkerPoliceVerificationDetails(String name, MultipartFile[] files);
	
	public List<WorkerPoliceVerificationVo> getWorkerAddressByWorkerInfoId(int workerInfoId) ;
	
}

