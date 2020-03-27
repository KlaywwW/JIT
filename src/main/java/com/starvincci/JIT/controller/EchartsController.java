package com.starvincci.JIT.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.starvincci.JIT.Mysql.service.WipService;
import com.starvincci.JIT.Sqlserver.service.MpsPlanServiceImpl;
import com.starvincci.JIT.Sqlserver.service.erpService;
import com.starvincci.JIT.pojo.EchartVo;
import com.starvincci.JIT.pojo.MpsJobmat;
import com.starvincci.JIT.pojo.Scandata;
import com.starvincci.JIT.pojo.Wip;
import com.starvincci.JIT.util.IOUtils;


@Controller
@CrossOrigin
public class EchartsController {//生产实时数据刷新
	
	private static final Logger log=LoggerFactory.getLogger(EchartsController.class);
	
	@Autowired
	private WipService wips;
	
    @Autowired
    private MpsPlanServiceImpl mpsService;
    
    
    @Autowired
    private  erpService er;
    
    
	/**
	 * 功能:首页折线图初始化
	 * 
	 * 逻辑:首页会将目标值写入一个txt中，IO流读取文件，读取前7天mysql数据，处理日期，后返回封装的实体
	 * 
	 * 
	 * @param time 设定多久时间刷新一次
	 * @return
	 * @throws IOException 
	 * 
	 */
	@RequestMapping("/charts")
	@ResponseBody
	public EchartVo getCharts(Integer time,HttpServletResponse response,HttpSession session,String linkQuee) throws IOException {
	  
		//将linkQuee写入io中    1初始页面,已经写入 2改变上限值 传入页面
		
		
		  EchartVo ev=new EchartVo();
		  int[] linkCounts=new int[12];
		  if(linkQuee!=null &&linkQuee!="") {
			IOUtils.WriteLink(linkQuee);
		  }
		  linkQuee=IOUtils.ReadLink();
		if(linkQuee!="---" && linkQuee!=null&&linkQuee!="") {
			IOUtils.WriteLink(linkQuee.toString());
			linkCounts[0]=Integer.parseInt(linkQuee);
			linkCounts[1]=Integer.parseInt(linkQuee);
			linkCounts[2]=Integer.parseInt(linkQuee);
			linkCounts[3]=Integer.parseInt(linkQuee);
			linkCounts[4]=Integer.parseInt(linkQuee);
			linkCounts[5]=Integer.parseInt(linkQuee);
			linkCounts[6]=Integer.parseInt(linkQuee);
			ev.setLinkQuee(linkQuee.toString());
		}else {
			linkCounts[0]=32000;
			linkCounts[1]=32000;
			linkCounts[2]=32000;
			linkCounts[3]=32000;
			linkCounts[4]=32000;
			linkCounts[5]=32000;
			linkCounts[6]=32000;
			ev.setLinkQuee("32000");
		}
	    session.setAttribute("linkQuee", linkQuee);
	    
		//获取当前的日期的前7天时间
	  
		Date date=new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -7);
		date = calendar.getTime();
		Timestamp time7=new Timestamp(date.getTime());
	     //获取这7天的在制品数量集合
		System.out.println(time7);
		List<Wip> list=wips.selectWip(time7);
		
		String[] Xtime=new String[7];
		int[] wip=new int[7];
		
        if(list==null||list.size()==0) {
        	wip[0]=25000;
        	wip[1]=26000;
        	wip[2]=27000;
        	wip[3]=28000;
        	wip[4]=25500;
        	wip[5]=26500;
        	wip[6]=23520;
   
        	Xtime[0]="8/26";
        	Xtime[1]="8/27";
        	Xtime[2]="8/28";
        	Xtime[3]="8/29";
        	Xtime[4]="8/30";
        	Xtime[5]="8/31";
        	Xtime[6]="9/1";
        
        }else {
    		for(int i=0;i<7;i++) {
    			int j=-(7-i);
    			Date date1=new Date();
    			Calendar calenda1 = Calendar.getInstance();
    			calenda1.setTime(date1);
    			calenda1.add(Calendar.DAY_OF_MONTH, j);
    		    String Str=(calenda1.get(calenda1.MONTH)+1)+"月"+calenda1.get(calenda1.DATE)+"日";
    		    System.out.println(Str);
    			Xtime[i]=Str;
    		    int k=list.size();
    		    if(i>=k) {
    		    	wip[i]=list.get(k-1).getNum();
    		    }else {
    		    	if(list.get(i).getNum()==null) {
    		    		wip[i]=list.get(i-1).getNum();
    		    	}else {
    		    		wip[i]=list.get(i).getNum();
    		    	}
    		    	
    		    }
    			
    		}
    
        }

		
		//循环,
	

	 
	    ev.setLinkCounts(linkCounts);
	    ev.setXTime(Xtime);
	    ev.setWip(wip);
	
		
		return ev;
	
			
		}
	
	/**
	 * 1//获取制二线在制工单的id集合
	 * 2获取工单数据和id、id找到bom价格表，
	
	 * 3循环查询获取数据
	 * @param request
	 * @param Brief
	 * @return
	 */
	@RequestMapping("findAllMoney")
	@ResponseBody
	public String getMoney(HttpServletRequest request,String Brief) {
		HttpSession session=request.getSession();
        
       
       
        //获得制二线的在制品集合(会有和其他生产线重复的)
       List<Integer> ids3=mpsService.findwip("制三");
       List<Integer> ids6=mpsService.findwip("制六");
       List<Integer> ids9=mpsService.findwip("制九");
       
		 List<Integer> ids=mpsService.findwip(Brief);
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
			 
		  //查询	出台湾的在制品数量
			 //1查询出出货台湾工单id集合
			 List<Integer> taiWanIds=mpsService.findPlanIdsOfTaiWan(startDate, endDate);
			 //2查询制二线完品检工序 完工量>0的
			 List<Integer> pingJianIds=mpsService.selectWanGongPLan_id(startDate, endDate);
			 //3两者取交集 得出皮夹线的发往台湾的数量
			 pingJianIds.retainAll(taiWanIds);
			 //4再和领料单中有制二线的工单集合取交集
			 pingJianIds.retainAll(ids);
		  
		    //4去重
		    List<Integer> taiwanids1 = pingJianIds.stream().distinct().collect(Collectors.toList()); 
		    ids.removeAll(taiwanids1);
		    
		   //找到在制
		   BigDecimal SumIn=mpsService.selectInQuee(ids);
		   BigDecimal SumOut=mpsService.selectPlanQuee(ids);
		   Integer wip=SumOut.intValue()-SumIn.intValue();
		   request.getSession().setAttribute("wip", wip);
		    //查询半成品上限 写入io中 默认为40000或者---
		     String link= IOUtils.ReadLink();
			  if(link!=null &&link!="") {
				  request.getSession().setAttribute("linkQuee", link);
			  }else {
				  String link2="---";
				  request.getSession().setAttribute("linkQuee", link2);
			  }

      
		 request.getSession().setAttribute("wip", wip);
		 
		  //找出所有的在制品的关联表集合
		 log.info("...开始查询");
		 List<MpsJobmat> jobIDList=mpsService.selectAllBomId(ids);
		 Integer lenth=jobIDList.size();
		 log.info("...查询完毕关联表"+lenth);
		 BigDecimal money = new BigDecimal("0");
		 BigDecimal SumMoney = new BigDecimal("0");
		 BigDecimal use = new BigDecimal("0");
		 Timestamp time=new Timestamp(new Date().getTime());
		 //找出所有的价格  和用量
		
		
		 log.info("查询完毕...开始循环");
		 for(int i=0;i<lenth;i++) {
			 
			 money=mpsService.selectAllPrice(time, jobIDList.get(i).getMat_id());
			 use=jobIDList.get(i).getOut_Qty();
			 if(money!=null&&use!=null) {
				 money=money.multiply(use);
				 SumMoney=SumMoney.add(money);
			 }
			
		 }
	  
	    log.info("是:"+SumMoney);
	    String mes=SumMoney.intValue()+"";
	    return mes;
	}
	
  
	@RequestMapping("/insert")
	@ResponseBody
	public String getInsert() {
		Scandata scan1=new Scandata("111","222","333");
	   for(int i=0;i<50;i++) {
		   er.insert(scan1);
	   }
		return "OK";
	}
		
	}


