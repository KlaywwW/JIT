package com.starvincci.JIT.Sqlserver.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.starvincci.JIT.Sqlserver.mapper.MmlReqOutMapper;
import com.starvincci.JIT.Sqlserver.mapper.MpsPlanMapper;
import com.starvincci.JIT.pojo.Erpsp;
import com.starvincci.JIT.pojo.MmlReqOutA;
import com.starvincci.JIT.pojo.MpsJobmat;
import com.starvincci.JIT.pojo.MpsPlan;
import com.starvincci.JIT.pojo.WipDetails;
import com.starvincci.JIT.util.SwitchLanguageUtils;

@Service
public class MpsPlanServiceImpl {

	@Autowired
	private MpsPlanMapper mpsPlanMapper;
	@Autowired
	private MmlReqOutMapper mmlmapper;
	
	
	public BigDecimal selectInQuee(List<Integer> ids) {
		// TODO Auto-generated method stub
		return mpsPlanMapper.selectInQuee(ids);
	}
	
	public BigDecimal selectPlanQueeInPlanNo(List<String> nos) {
		return mpsPlanMapper.selectPlanQueeInPlanNo(nos);
	}
	
	public BigDecimal selectInQueeInPlanNo(List<String> nos) {
		return mpsPlanMapper.selectInQueeInPlanNo(nos);
	}
	
	
	
	public BigDecimal selectPlanQuee(List<Integer> ids) {
		// TODO Auto-generated method stub
		return mpsPlanMapper.selectPlanQuee(ids);
	}
	
	public List<Integer> selectIDFromMpsJob(Timestamp startDate,Timestamp endDate) {
		// TODO Auto-generated method stub
		return mpsPlanMapper.selectIDFromMpsJob( startDate,endDate);
	}

	public List<MpsPlan> selectMpsPlanInfo(List<Integer> ids) {
		// TODO Auto-generated method stub
		return mpsPlanMapper.selectMpsPlanInfo(ids);
	}
	
	public List<Integer> selectMpsPlanids(List<Integer> ids) {
		// TODO Auto-generated method stub
		return mpsPlanMapper.selectMpsPlanids(ids);
	}

	public List<MpsPlan> selectWipInfo(List<Integer> ids) {
		// TODO Auto-generated method stub
		return mpsPlanMapper.selectWipInfo(ids);
	}
	
	public List<Integer> selectIDByFS_Quan(Timestamp startDate, Timestamp endDate) {
		// TODO Auto-generated method stub
		return mpsPlanMapper.selectIDByFS_Quan(startDate, endDate);
	}
	
	
	public List<Erpsp> selectSpIDByNo(String sp_No){
		return mpsPlanMapper.selectSpIDByNo(sp_No);
		
	}
	
	  public  List<MpsJobmat> selectAllBomId(List<Integer> ids){
		  return mpsPlanMapper.selectAllBomId(ids);
	  }
	
	
	public List<Integer> selectmpsPlanBySpid(Integer Sp_id){
		return mpsPlanMapper.selectmpsPlanBySpid(Sp_id);
	}
	
	public List<Integer>selectWanGongPLan_id(Timestamp startDate,Timestamp endDate){
		return mpsPlanMapper.selectWanGongPLan_id(startDate, endDate);
	}
	public List<Integer> findPlanIdsOfTaiWan(Timestamp startDate,Timestamp endDate){
		return mpsPlanMapper.findPlanIdsOfTaiWan(startDate, endDate);
	}
	
	public List<WipDetails> selectMpsPlansByIds(List<Integer> ids){
		return mpsPlanMapper.selectMpsPlansByIds(ids);
	}
	
	
	
	public BigDecimal selectAllPrice(Timestamp time,Integer id){
		return mpsPlanMapper.selectAllPrice(time,id);
	}

	public List<String> selectNosByIDS(List<Integer> ids){
		return mpsPlanMapper.selectNosByIDS(ids);
	}
	
	
	
	
	/**
	 * 
	 * @param Brief 生产单位
	 * @return  该生产单位最近6个月的领料单备注有 该生产单位的工令单集合
	 */
	public List<Integer> findwip(String Brief){
		
		
		//初始化查询日期 默认只查询六个月以内的东西
		Calendar c=Calendar.getInstance();
		 c.setTime(new Date());
		 c.add(Calendar.MONTH, -12);
		 Date d=c.getTime();
		 Timestamp startDate=new Timestamp(d.getTime());
		 c.setTime(new Date());
		 c.add(Calendar.MONTH,1);
		 d=c.getTime();
		 Timestamp endDate=new Timestamp(d.getTime());
		 
		 MmlReqOutA mml=new MmlReqOutA();
		 mml.setStartDate(startDate);
		 mml.setEndDate(endDate);
		 //判断领料单的生产单位
		 if(Brief==null||Brief=="") {
			 Brief="制二";
		
		 }
		
		 mml.setBrief(Brief);
		 
		 //查询生产领料A表中的备注包含 制二 的数据的B表id集合六个月以内的
		 //@Select("select Bt_id from mmlReqOutA where mmlReqOutA.Brief like '%'+#{Brief}+'%' and Bt_Date between  #{startDate} and #{endDate}  ")
		 
		 List<Integer> mmlReqOutAIds=mmlmapper.selectMmlAIdsByBrief(mml);
		 //转化为繁体再搜索一次(工具类)
		 String fantiBrief=SwitchLanguageUtils.JianToFan(Brief);
		
		 mml.setBrief(fantiBrief);
		 List<Integer> fantimmlReqOutAIds=mmlmapper.selectMmlAIdsByBrief(mml);
		 
		 
		
		 //并集得到繁体和简体制二的领料单id
		 mmlReqOutAIds.addAll(fantimmlReqOutAIds);
		 
		 //去重 ---得到领料单B表中外键工令单id集合
		 List<Integer> mmlAids =mmlReqOutAIds.stream().distinct().collect(Collectors.toList());


		 //2-2 查询    在B表中查询他的外键 工令单ID集合
		 //数据超过2100会报错 超过2100就分批量查询 
		 List<Integer> mplPlanIds=new ArrayList<>();
		 List<Integer> Plans=new ArrayList<>();
		 if(mmlAids.size()<2100){
			 //select OrdA_ID from mmlReqOutB WHERE  Bt_id  in ...
			 //在子表中查询父id在集合中的数据 并取得她关联工令单的外键---即为在制工令单集合(包含已结案的)
			 mplPlanIds=mmlmapper.selectPlanIDInMmlReqOutB(mmlAids);
		 }else {
			 int  limitCount=500;//设置每次子查询的传参的个数
			 int  limitIndex=limitCount-1;//每批查询的下标
		     for(int i=0;i<mmlAids.size()-1;) {
		  		 if(limitIndex>mmlAids.size()-1) {//结果集中的数据小于分批查询的数据
				        limitIndex=mmlAids.size()-1;
				        //筛选未结案的工令单集合
				        Plans=mmlmapper.selectPlanIDInMmlReqOutB(mmlAids.subList(i, limitIndex+1));
				        if(Plans!=null) {
				        	 mplPlanIds.addAll(Plans);
		  				   
				        }else {
				        	 System.out.println("无数据");
				         }
				           break;
		  		 }else {
		  			   Plans=mmlmapper.selectPlanIDInMmlReqOutB(mmlAids.subList(i, limitIndex+1));
		  			    if(Plans!=null) {
		  			    	 mplPlanIds.addAll(Plans);
		  			 	   
		  			 	}else {
		  			 	 System.out.println("无数据");
		  			 	}
		  			 	i=limitIndex+1;
		  			 	limitIndex=i+(limitCount-1);
		  		 }
		 } 	
	
		 }
        //得到最近6个月皮夹生产单位 已领料的工令单集合 
		 List<Integer>ids=mplPlanIds.stream().distinct().collect(Collectors.toList());
		 List<Integer> planids=mpsPlanMapper.selectMpsPlanids(ids);
		return  planids;
	}

	
	public Integer getWipNum() {
		List<Integer> ids=findwip("制二");
		List<Integer> ids3=findwip("制三");
        List<Integer> ids6=findwip("制六");
        List<Integer> ids9=findwip("制九");
		   ids.removeAll(ids3);
		   ids.removeAll(ids6);
		   ids.removeAll(ids9);
		     Calendar c=Calendar.getInstance();
			 c.setTime(new Date());
			 c.add(Calendar.MONTH, -6);//自动处理2月平润年的问提
			 Date d=c.getTime();
			 Timestamp startDate=new Timestamp(d.getTime());
			 
			 c.setTime(new Date());
			 c.add(Calendar.MONTH,1);
			 d=c.getTime();
			 Timestamp endDate=new Timestamp(d.getTime());
			 
		     //查询	  出台湾的在制品数量
			 //1查询出出货台湾工单id集合
			 List<Integer> taiWanIds=findPlanIdsOfTaiWan(startDate, endDate);
			 //2查询制二线完品检工序 完工量>0的
			 List<Integer> pingJianIds=selectWanGongPLan_id(startDate, endDate);
			 //3两者取交集 得出皮夹线的发往台湾的数量
			 pingJianIds.retainAll(taiWanIds);
			 //4再和领料单中有制二线的工单集合取交集
			 pingJianIds.retainAll(ids);
		  
		    //4去重
		    List<Integer> taiwanids1 = pingJianIds.stream().distinct().collect(Collectors.toList()); 
		    ids.removeAll(taiwanids1);
		 
		   BigDecimal SumIn=selectInQuee(ids);
		   BigDecimal SumOut=selectPlanQuee(ids);
		   Integer wip=SumOut.intValue()-SumIn.intValue();
		   return wip;
	}
	
	/**
	 * 
	 * @param No  根据工令单NO找到相关的数据
	 * @return
	 */
	public WipDetails selectWipDetails(String No) {
		return mpsPlanMapper.selectWipDetails(No);
	}


	/**
	 * 在源码上二次开发
	 * 根据工令单号查询出数据放入二位码中
	 * @param planno 工令单单号
	 * @return
	 */
	public List<MpsPlan> selectInfoByplanno(String planno){
		return  mpsPlanMapper.selectInfoByplanno(planno);
	}
	/**
	 * 先在工令单表中查出产品编码id
	 * 再根据产品编码id查询产品信息
	 * @param spid 产品编码id
	 * @return
	 */
	public Erpsp selectinfoByspid(Integer spid){
		return mpsPlanMapper.selectinfoByspid(spid);
	}
	
}
