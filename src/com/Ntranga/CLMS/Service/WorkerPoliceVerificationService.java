package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.Ntranga.CLMS.vo.WorkerDetailsVo;
import com.Ntranga.CLMS.vo.WorkerPoliceVerificationVo;

public interface WorkerPoliceVerificationService {

	public List<WorkerPoliceVerificationVo> getWorkerPoliceVerificationDetailsByWorkerId(int workerId);

	public Integer saveOrUpdateWorkerPoliceVerificationDetails(String name, MultipartFile[] files);

	public List<WorkerPoliceVerificationVo> getWorkerAddressByWorkerInfoId(int workerInfoId) ;

}
