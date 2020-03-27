package com.starvincci.JIT.util;

import java.util.Calendar;
import java.util.Date;

import com.starvincci.JIT.pojo.Erpsp;

public class ErpSpUtils {//根据下拉框的选择的筛选条件选择
	
	  public static Erpsp getErpspInfo(Integer No,String mes) {
		  Erpsp erpsp=new Erpsp();
		  if(No==1) {  //存货编码
			  erpsp.setSp_No(mes);
			  return erpsp;
		  }else if(No==2) {//客户编码
			  erpsp.setErpspPlusmyField12(mes);
			  return erpsp;
		  }else if(No==3){//品牌名称
			  erpsp.setErpspPlusmyField05(mes);
			  return erpsp;
		  }else {//工单号
			  return null;  
		  }
		
		 
	  }

	  
	   public static void text() {
			//找到此款的 订单号 和日期
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			Integer month=(calendar.get(calendar.MONTH)+1);
			String mes1="";
			if(month<10) {
				mes1=0+""+month+"/";
			}else {
				mes1=month+"/";
			}
			String year=calendar.get(calendar.YEAR)+"";
			mes1=mes1+year;
			String po="63123";
			String mes2="M&F Western Products,Inc"+"\n"+"Made In China"+"\n"+"PO#"+po+"\n"+"ItemNo.A1019401"+"\n"+"Meets CPSC&CPSIA Safety Requirements"+"\n"+mes1;
	        System.out.println(mes2);
	   }
	   
	
}
