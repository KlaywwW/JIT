package com.starvincci.JIT.Sqlserver.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.starvincci.JIT.Sqlserver.mapper.*;
import com.starvincci.JIT.pojo.MmlReqOutA;


@Service
public class MmlReqOutServiceImpl {
	
   @Autowired
   private MmlReqOutMapper mmlreq;

	public List<Integer> selectPlanIDInMmlReqOutB(List<Integer> ids) {
		// TODO Auto-generated method stub
		return mmlreq.selectPlanIDInMmlReqOutB(ids);
	}
	
	public List<Integer> selectMmlAIdsByBrief(MmlReqOutA mmlReqOutA){
		return mmlreq.selectMmlAIdsByBrief(mmlReqOutA);
	}

	public Timestamp getLingLiaoTimeByPlanId(Integer id) {
		return mmlreq.getLingLiaoTimeByPlanId(id);
	}
}
