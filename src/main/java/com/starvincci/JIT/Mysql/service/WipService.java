package com.starvincci.JIT.Mysql.service;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.starvincci.JIT.Mysql.mapper.WipMapper;
import com.starvincci.JIT.pojo.Wip;
import com.starvincci.JIT.pojo.WipDetails;

@Service
public class WipService {
	
	@Autowired
	private WipMapper wipmapper;
	
	public List<Wip> selectWip(Timestamp beginTime){
		return wipmapper.selectWip(beginTime);
	}

	public int insertOneInfo(Wip wip){
		return wipmapper.insertOneInfo(wip);
	}
	
	public int index1() {
		System.out.println("进入到业务");
		return 1;
	}
	 public WipDetails selectWipDetails(String planNo){
		 return wipmapper.selectWipDetails(planNo);
	 }
	 
	 public int insertOneMpsPlan(WipDetails wipdetails) {
		 return wipmapper.insertOneMpsPlan(wipdetails);
	 }
	 
	 public int updateWipDtetails(WipDetails wipdetail) {
		 return wipmapper.updateWipDtetails(wipdetail);
	 }
	 
	 public List<String> selectWipInDuan(List<String> Nos){
		 return wipmapper.selectWipInDuan(Nos);
	 }
	 
	 public List<String> selectWipInDuan3(List<String> Nos){
		 return wipmapper.selectWipInDuan3(Nos);
	 }
	 public List<String> selectWipInDuan2(List<String> Nos){
		 return wipmapper.selectWipInDuan2(Nos);
	 }
	 public List<String> selectWipInDuan4(List<String> Nos){
		 return wipmapper.selectWipInDuan4(Nos);
	 }
	  //@Update("update wipdetails set  time1=null and info='暂无数据' ")
	    public int clearTime1(String No) {
	    	return wipmapper.clearTime1(No);
	    }
	    
	   // @Update("update wipdetails set  time2=null and info='A段' ")
	    public int clearTime2(String No) {
	    	return wipmapper.clearTime2(No);
	    }
	    
	   // @Update("update wipdetails set  time3=null and info='B段' ")
	    public int clearTime3(String No) {
	    	return wipmapper.clearTime3(No);
	    }
	public int clearTime4(String No){
		return wipmapper.clearTime4(No);
	}
	    //@Update("update wipdetails set  zt=null and info='C段' ")
	    public int clearChuKu(String No) {
	    	return wipmapper.clearChuKu(No);
	    }
	 
	 
}
