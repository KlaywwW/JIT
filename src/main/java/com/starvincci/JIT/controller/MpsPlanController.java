package com.starvincci.JIT.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import org.hibernate.validator.constraints.pl.REGON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.starvincci.JIT.Sqlserver.service.MpsPlanServiceImpl;
import com.starvincci.JIT.pojo.Erpsp;
import com.starvincci.JIT.pojo.MmlReqOutA;
import com.starvincci.JIT.pojo.MpsJobmat;
import com.starvincci.JIT.pojo.MpsPlan;

import com.starvincci.JIT.util.IOUtils;
import com.starvincci.JIT.util.SwitchLanguageUtils;

@Controller
@CrossOrigin
public class MpsPlanController {
	
	private static final Logger log=LoggerFactory.getLogger(MpsPlanController.class);
	
    @Autowired
    private MpsPlanServiceImpl mpsService;
	
	/**
	 * 功能:进入首页，显示在制数量
	 * 
	 * 逻辑：1 mpsService.findwip-----得到最近6个月皮夹生产单位的已领料的工令单id集合   -----从中取出掉其他单位的工单（一个工单由多个单位生产领料）
	 *     2  selectWanGongPLan_id查询制二线最近6个月未结案且完品检工序 完工量>0的工单id---与1中数据取交集 即为台湾在制
	 *     3  
	 * 
	 * @param request
	 * @param Brief    生产单位 以领料单为准
	 * @return
	 * @throws IOException
	 * 
	 * 
	 */
	@RequestMapping("/wip")
	public String findZaiZhi(HttpServletRequest request,String Brief) throws IOException{
       
         HttpSession session=request.getSession();
          //获取在制工单的id集合
         
         
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
			 c.add(Calendar.MONTH, -12);//自动处理2月平润年的问提
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
		   Integer wip=SumOut.intValue()-SumIn.intValue();//获取在制
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
//		 log.info("...开始查询");
//		 List<MpsJobmat> jobIDList=mpsService.selectAllBomId(ids);
//		 Integer lenth=jobIDList.size();
//		 log.info("...查询完毕关联表"+lenth);
//		 BigDecimal money = new BigDecimal("1");
//		 BigDecimal SumMoney = new BigDecimal("1");
//		 Timestamp time=new Timestamp(new Date().getTime());
//		 //找出所有的价格  和用量
//		
//		
//		 log.info("查询完毕...开始循环");
//		 for(int i=0;i<lenth;i++) {
//			 
//			 money=mpsService.selectAllPrice(time, jobIDList.get(i).getMat_id());
//			 if(money!=null) {
//				 money=money.multiply(jobIDList.get(i).getPUse_Qty());
//				 SumMoney=SumMoney.add(money);
//			 }
//			
//		 }
//	    session.setAttribute("wipMoney", SumMoney.intValue());
//	    log.info("是:"+SumMoney);
		 return "WIP";//work in-process
	}
   

	/**
	 *在制品明细---
	 *
	 * @param session  
	 * @return 
	 */
	@RequestMapping("/pick")
	public String checkPlans(HttpSession session,String Brief) {
		 List<Integer> ids=new ArrayList<>();
				 
		   //通过条件查询进入页面,有参数
		   if(Brief!=null&&Brief!="") {
			    if(Brief=="制二"){
			    	      List<Integer> ids3=mpsService.findwip("制三");
				          List<Integer> ids6=mpsService.findwip("制六");
				          List<Integer> ids9=mpsService.findwip("制九");
						  ids=mpsService.findwip(Brief);
						  ids.removeAll(ids3);
						  ids.removeAll(ids6);
						  ids.removeAll(ids9);
						  session.removeAttribute("sc");
						  session.setAttribute("sc", "制二");
			    }else {
			    	  ids=mpsService.findwip(Brief);
			    	  session.removeAttribute("sc");
					  session.setAttribute("sc", Brief);
			    }
			   
		   }else { 
			   
			   Brief=(String)session.getAttribute("sc");
			   //第一次进入页面
			   if(Brief==null) {
				     Brief="制二";
				     session.setAttribute("sc", "制二");
				     List<Integer> ids3=mpsService.findwip("制三");
			         List<Integer> ids6=mpsService.findwip("制六");
			         List<Integer> ids9=mpsService.findwip("制九");
			         
					 ids=mpsService.findwip(Brief);
					 ids.removeAll(ids3);
					 ids.removeAll(ids6);
					 ids.removeAll(ids9);
					 session.removeAttribute("sc");
					  session.setAttribute("sc", "制二");
				   
			   }else {
				   if(Brief=="制二") {
					   List<Integer> ids3=mpsService.findwip("制三");
				         List<Integer> ids6=mpsService.findwip("制六");
				         List<Integer> ids9=mpsService.findwip("制九");
						 ids=mpsService.findwip(Brief);
						 ids.removeAll(ids3);
						 ids.removeAll(ids6);
						 ids.removeAll(ids9);
						 session.removeAttribute("sc");
						  session.setAttribute("sc", "制二");
				   }else {
					   ids=mpsService.findwip(Brief);
					   session.removeAttribute("sc");
						  session.setAttribute("sc", Brief);
				   }
				  
			   }
		   }
		
		   List<MpsPlan> wipPlans=mpsService.selectWipInfo(ids);
		   
		
		   Integer SumIn=0;
		   Integer SumOut=0;
		
		  for(int i=0;i<wipPlans.size();i++) {
				  SumIn=wipPlans.get(i).getIn_Quan()+SumIn;  
			      SumOut=wipPlans.get(i).getPlan_Quan()+SumOut; 
			      //一一遍历xunhu
		  }
		 
		  session.setAttribute("SumIn", SumIn);
		  session.setAttribute("SumOut", SumOut);
		  session.setAttribute("Sum", SumOut-SumIn);
		  session.setAttribute("wipPlans",wipPlans);
		  
		  
		  
		  
		  //查询在制品中  已完成的工序 并显示
		  //1查询工序单中有完成数量=计划数量 的工单id集合(包含已结案的)
		   Calendar c=Calendar.getInstance();
			 c.setTime(new Date());
			 c.add(Calendar.MONTH,-12);//自动处理2月平润年的问提
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
		    
		  
		  
		  List<MpsPlan> taiwan=mpsService.selectWipInfo(taiwanids1);
		  Integer taiwanIn=0;
		   Integer taiwanOut=0;
		  for(int i=0;i<taiwan.size();i++) {
			  taiwanIn=taiwan.get(i).getIn_Quan()+taiwanIn;  
		      taiwanOut=taiwan.get(i).getPlan_Quan()+taiwanOut; 
	      }
		  Integer taiwanSum=taiwanOut-taiwanIn;
		  session.setAttribute("taiwan", taiwan);
		  session.setAttribute("taiwanIn", taiwanIn);
		  session.setAttribute("taiwanOut", taiwanOut);
		  session.setAttribute("taiwanSum", taiwanSum);
		  
		  
		  
		  
		 //三 取差集 即为制二线的在制
		 //工序中没有完工量的id集合
	
		  
		  
		  
		  
		  ids.removeAll(taiwanids1);
		  List<MpsPlan> sc=mpsService.selectWipInfo(ids);
		  Integer scIn=0;
		   Integer scOut=0;
		  for(int i=0;i<sc.size();i++) {
			  scIn=sc.get(i).getIn_Quan()+scIn;  
		      scOut=sc.get(i).getPlan_Quan()+scOut; 
	      }
		  Integer scSum=scOut-scIn;
		  session.setAttribute("scList", sc);
		  session.setAttribute("scIn", scIn);
		  session.setAttribute("scOut", scOut);
		  session.setAttribute("scSum", scSum);
		  return "Pick";
	}
	
	@RequestMapping("/findErpSP")
	@ResponseBody
	public String findeErpSPIdByName(String spNo,HttpServletRequest request) {
		
		if(spNo!=null&&spNo!="") {
			//转换为大写
			spNo=spNo.toUpperCase();
			List<Erpsp> spIds=mpsService.selectSpIDByNo(spNo);
			if(spIds!=null&&spIds.size()>0) {
				request.getSession().setAttribute("search", spIds);
				return "OK";
			}else {
				return "NO";
			}
		}else {
			return "NO";
		}
		
		
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping("/findErpSPJindu")
	public String findErpSpJinDu(HttpSession Session,Integer id,String Brief,HttpSession session,String spNo) {
		
		
		
		
		Session.removeAttribute("mis");
		Session.removeAttribute("zaizhiList2");
		Session.removeAttribute("zaizhiList3");
		Session.removeAttribute("zaizhiList6");
		Session.removeAttribute("zaizhiList9");
		//找到款号对应的工单号 前五条数据工单id数据 一个款号可能对应多个工单
		List<Integer> mpsids=mpsService.selectmpsPlanBySpid(id);
		//多个工单可能在不同的生产单位生产
		List<Integer> ids2=mpsService.findwip("制二");
		List<Integer> ids3=mpsService.findwip("制三");
		List<Integer> ids6=mpsService.findwip("制六");
		List<Integer> ids9=mpsService.findwip("制九");
		//取交集
		ids2.retainAll(mpsids);
		ids3.retainAll(mpsids);
		ids6.retainAll(mpsids);
		ids9.retainAll(mpsids);
		
		if(ids2!=null&&ids2.size()>0) {
			System.out.println("是制二线的单");
			List<MpsPlan> zaizhiList=mpsService.selectWipInfo(ids2);
			Session.setAttribute("zaizhiList2", zaizhiList);
		}else if(ids3!=null&&ids3.size()>0) {
			System.out.println("是制3线的单");
			List<MpsPlan> zaizhiList=mpsService.selectWipInfo(ids3);
			Session.setAttribute("zaizhiList3", zaizhiList);
		}else if(ids6!=null&&ids6.size()>0) {
			System.out.println("是制6线的单");
			List<MpsPlan> zaizhiList=mpsService.selectWipInfo(ids6);
			Session.setAttribute("zaizhiList6", zaizhiList);
		}else if(ids9!=null&&ids9.size()>0) {
			System.out.println("是制9线的单");
			List<MpsPlan> zaizhiList=mpsService.selectWipInfo(ids9);
			Session.setAttribute("zaizhiList9", zaizhiList);
		}else {
			//取差集
			mpsids.removeAll(ids2);
			mpsids.removeAll(ids3);
			mpsids.removeAll(ids6);
			mpsids.removeAll(ids9);
			System.out.println("非在制+"+mpsids.size());
			List<MpsPlan> feiZaizhi=mpsService.selectMpsPlanInfo(mpsids);
			Session.setAttribute("feizaizhi",feiZaizhi);
		    String mes="皮夹生产没有关于"+spNo+"款号的在制品数据，可能是此款的工令单未领料生产或已结案或未投产";
		    Session.setAttribute("mis", mes);
		}
		
		
		
		
		Session.setAttribute("spNo", spNo);
		return "Link";
	}
	
	
	
	
	
	
	//使用poi导出成excel表
     @RequestMapping(value="/excel")
     @ResponseBody
     public String excelExport(HttpSession session,HttpServletResponse respone) throws IOException {
    	 //取得数据
    	 List<MpsPlan> scList=(List<MpsPlan>)session.getAttribute("scList");
    	 List<MpsPlan> taiwanList=(List<MpsPlan>)session.getAttribute("taiwan");
    	 String sc=(String)session.getAttribute("sc");
    	//创建工作薄
    	 HSSFWorkbook book=new HSSFWorkbook();
    	 HSSFSheet sheet=(HSSFSheet)book.createSheet("斯达文星在制品统计");
    	 //单位格式对象
    	 HSSFCellStyle style=book.createCellStyle();
    	 //设置边框
    	
 		
        //设置字体
 		HSSFFont font=book.createFont();
 		font.setFontName("宋体");
 		font.setFontHeightInPoints((short)12);
 		style.setFont(font);
         style.setFillBackgroundColor(IndexedColors.PINK.getIndex());
 		style.setWrapText(false);
 		//设置列宽
 		 sheet.setDefaultColumnWidth((short) 20);
 		 style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        
 		//写表头标题
 		
 		Cell cell;//单元格
 		int rowNum=0;//行序号
 		int colNum=0;//列序号
 		String[] titles= {"工令单单号","客户订单号","产品编码","产品名称","计划数量","入库数量","开单时间","生产单位","状态"};
 		
 		Row rowhead=sheet.createRow(0);
 		for(int j=0;j<titles.length;j++) {
 			cell=rowhead.createCell(colNum);
 			cell.setCellStyle(style);
 			cell.setCellValue(titles[j]);
 			colNum++;
 		}
 		//从第二行开始遍历循环 scList
 		rowNum=1;
 		for(int i=0;i<scList.size();i++) {
 			Row rowhead2=sheet.createRow(rowNum);
 			colNum=0;//列
 			for(int j=0;j<titles.length;j++) {
 				cell=rowhead2.createCell(colNum);
 				cell.setCellStyle(style);
 				if(j==0) {
 				   cell.setCellValue(scList.get(i).getPlan_No());	
 				}else if(j==1) {
 					cell.setCellValue(scList.get(i).getsCP_NOs());
 				}else if(j==2) {
 					cell.setCellValue(scList.get(i).getSp_No());
 				}else if(j==3) {
 					cell.setCellValue(scList.get(i).getSp_Name());
 				}else if(j==4) {
 					cell.setCellValue(scList.get(i).getPlan_Quan());
 				}else if(j==5) {
 					cell.setCellValue(scList.get(i).getIn_Quan());
 				}else if(j==6) {
 					cell.setCellValue(scList.get(i).getPlan_Date());
 				}else if(j==7) {
 					cell.setCellValue(sc);
 				}else {
 					cell.setCellValue("制二线在制");
 				}
 					
 				
 				colNum++;
 			}
 			rowNum++;//行数加一
 		}
 		
 		//获得当前的行数
 		Row rowhead3=sheet.createRow(rowNum);
 		cell=rowhead3.createCell(0);
			cell.setCellStyle(style);
			cell.setCellValue("以下为制二线的工单");
			
			
			for(int i=0;i<taiwanList.size();i++) {
	 			Row rowhead2=sheet.createRow(rowNum);
	 			colNum=0;//列
	 			for(int j=0;j<titles.length;j++) {
	 				cell=rowhead2.createCell(colNum);
	 				cell.setCellStyle(style);
	 				if(j==0) {
	 				   cell.setCellValue(taiwanList.get(i).getPlan_No());	
	 				}else if(j==1) {
	 					cell.setCellValue(taiwanList.get(i).getsCP_NOs());
	 				}else if(j==2) {
	 					cell.setCellValue(taiwanList.get(i).getSp_No());
	 				}else if(j==3) {
	 					cell.setCellValue(taiwanList.get(i).getSp_Name());
	 				}else if(j==4) {
	 					cell.setCellValue(taiwanList.get(i).getPlan_Quan());
	 				}else if(j==5) {
	 					cell.setCellValue(taiwanList.get(i).getIn_Quan());
	 				}else if(j==6) {
	 					cell.setCellValue(taiwanList.get(i).getPlan_Date());
	 				}else if(j==7) {
	 					cell.setCellValue(sc);
	 				}else {
	 					cell.setCellValue("已移转未入库");
	 				}
	 					
	 				
	 				colNum++;
	 			}
	 			rowNum++;//行数加一
	 		}
 		
 		
		//写成文件
			//重复文件 会导致写不进去
			Date date=new Date();
			Timestamp timeStramp=new Timestamp(date.getTime());
			 DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			 
			String name=sdf.format(timeStramp);
			String url="E:\\JIT\\"+name+".xls";
	    try {
			OutputStream out=new FileOutputStream(url);
			book.write(out);
			out.close();
			
		
	
		} catch (FileNotFoundException e) {
			System.out.println(e);
			e.printStackTrace();
		}
	    
	  //进行下载
	    FileInputStream is = null;
        BufferedInputStream bs = null;
        OutputStream os = null;
        String filename="在制品清单";
        try {
            File file = new File(url);
            if (file.exists()) {
            	System.out.println("正在下载");
                //设置Headers
                respone.setHeader("Content-Type","application/octet-stream");
                //设置下载的文件的名称-该方式已解决中文乱码问题
                respone.setHeader("Content-Disposition","attachment;filename=" +  new String( filename.getBytes("gb2312"), "ISO8859-1" ));
                is = new FileInputStream(file);
                bs =new BufferedInputStream(is);
                os = respone.getOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while((len = bs.read(buffer)) != -1){
                    os.write(buffer,0,len);
                }
            }else{
              System.out.println("文件不存在");
                
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }finally {
            try{
                if(is != null){
                    is.close();
                }
                if( bs != null ){
                    bs.close();
                }
                if( os != null){
                    os.flush();
                    os.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }


 		return "oK";
     }
     
  
     
     /**
      * 
      * @author zhangzhe
      * @param session
      * @return
      */
     @RequestMapping("/findwipMoney")
     public String findWipMoney(HttpSession session,Integer id,String No) {
		 System.out.println(id);
    	 //根据id找到对应的bom材料表
    	 List<Integer> ids=new ArrayList<>();
    	 ids.add(id);
    	 session.setAttribute("spNo", No);
    	 List<MpsJobmat> jobIDList=mpsService.selectAllBomId(ids);
    	 Integer  lenth=jobIDList.size();
    	 
		 BigDecimal money = new BigDecimal("0");
		 BigDecimal money2 = new BigDecimal("0");
		 BigDecimal use = new BigDecimal("0");
		 BigDecimal SumMoney = new BigDecimal("0");
		 BigDecimal index = new BigDecimal("0");
		 Timestamp time=new Timestamp(new Date().getTime());
		 
		 List<MpsJobmat>  mjm=new ArrayList<>();
	
		 for(int i=0;i<lenth;i++) {
			 Integer mat_id=jobIDList.get(i).getMat_id();
			 money=mpsService.selectAllPrice(time, mat_id);
			 use=jobIDList.get(i).getOut_Qty();
			 if(money!=null) {
				 MpsJobmat mps=new MpsJobmat();
				 mps.setSp_No(jobIDList.get(i).getSp_No());
				 mps.setBommoney(money);
				
				 mps.setOut_Qty(use);
				 mps.setSp_Name(jobIDList.get(i).getSp_Name());
                   if(use.intValue()>0) {
                	   money2=money.multiply(use);
				   }else {
					 money2=index;
				  }
				
				 mps.setPlanMoney(money2);;
				 mjm.add(mps);
				 SumMoney=SumMoney.add(money2);
			    
			 }
		
		 }
		 session.setAttribute("sumMoney", mjm);
		 session.setAttribute("money",SumMoney.intValue());
		 System.out.println(SumMoney.intValue());
		 
		 
		 
		 
		 
		    List<Integer> ids2=mpsService.findwip("制二");
			List<Integer> ids3=mpsService.findwip("制三");
			List<Integer> ids6=mpsService.findwip("制六");
			List<Integer> ids9=mpsService.findwip("制九");
			//取交集
			ids2.retainAll(ids);
			ids3.retainAll(ids);
			ids6.retainAll(ids);
			ids9.retainAll(ids);
			
			if(ids2!=null&&ids2.size()>0) {
				System.out.println("是制二线的单");
				List<MpsPlan> zaizhiList=mpsService.selectWipInfo(ids2);
				session.setAttribute("zaizhiList2", zaizhiList);
			}else if(ids3!=null&&ids3.size()>0) {
				System.out.println("是制3线的单");
				List<MpsPlan> zaizhiList=mpsService.selectWipInfo(ids3);
				session.setAttribute("zaizhiList3", zaizhiList);
			}else if(ids6!=null&&ids6.size()>0) {
				System.out.println("是制6线的单");
				List<MpsPlan> zaizhiList=mpsService.selectWipInfo(ids6);
				session.setAttribute("zaizhiList6", zaizhiList);
			}else if(ids9!=null&&ids9.size()>0) {
				System.out.println("是制9线的单");
				List<MpsPlan> zaizhiList=mpsService.selectWipInfo(ids9);
				session.setAttribute("zaizhiList9", zaizhiList);
			}else {
				//取差集
				ids.removeAll(ids2);
				ids.removeAll(ids3);
				ids.removeAll(ids6);
				ids.removeAll(ids9);
				System.out.println("非在制+"+ids.size());
				List<MpsPlan> feiZaizhi=mpsService.selectMpsPlanInfo(ids);
				session.setAttribute("feizaizhi",feiZaizhi);
			    String mes="皮夹生产没有关于"+No+"款号的在制品数据，可能是此款的工令单未领料生产或已结案或未投产";
			    session.setAttribute("mis", mes);
			}
			
    	 return "Link";
     }
     
  
     
     
	
}
