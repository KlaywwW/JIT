package com.starvincci.JIT.Sqlserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.starvincci.JIT.Sqlserver.mapper.erpMapper;
import com.starvincci.JIT.pojo.Erpsp;
import com.starvincci.JIT.pojo.Scandata;




@Service
public class erpService {
	
	
	@Autowired
	private erpMapper erpmapper;
	
	public int insert(Scandata scan) {
		return erpmapper.insert(scan);
	}
	public List<Erpsp> selectAllerpSP(Erpsp erpsp){
		return erpmapper.selectAllerpSP(erpsp);
	}
	
	public Integer selectMpsPlan(String Plan_No) {
		return erpmapper.selectMpsPlan(Plan_No);
	}
	
	//判断款式对应的条码规格
	public List<Erpsp> selectBarCodeStyleByErpSpInfo(List<Erpsp> erpSpList ){
		System.out.println("已进入");
	    String  pidai="皮带";
        String fanTipidai="皮帶";
        String pijia="夹";
        String fanTipijia="夾";
        String tongdai="孩";
        String tongdai2="童";
        for(int i=0;i<erpSpList.size();i++) {
			   //判断是不是皮带
			   if(erpSpList.get(i).getErpspPlusmyField09().contains(pidai)||erpSpList.get(i).getErpspPlusmyField09().contains(fanTipidai)) {
				    //判断是否是童带
				   if(erpSpList.get(i).getErpspPlusmyField11().contains(tongdai)||erpSpList.get(i).getErpspPlusmyField11().contains(tongdai2)) {
				    	erpSpList.get(i).setPhotoStyle("胶片:12X2.5cm 贴标:9.5X2cm");
				    	erpSpList.get(i).setStyle("童带:3.8x2cm");
				    	System.out.println("已进入童带");
				    }else {
				    	 erpSpList.get(i).setStyle("皮带:3.8x2cm");
				    	 erpSpList.get(i).setPhotoStyle("胶片:9.7X2cm 贴标:5.5X1.5cm");
						 System.out.println(i+"皮帶");
				    }
				  
			   }else if(erpSpList.get(i).getErpspPlusmyField09().contains(pijia)||erpSpList.get(i).getErpspPlusmyField09().contains(fanTipijia)) {
				   erpSpList.get(i).setPhotoStyle("皮夹:暂无参数");
				   erpSpList.get(i).setStyle("皮夹:暂无参数");
				   System.out.println("已进入皮夹");
			   }else {
				   erpSpList.get(i).setPhotoStyle("系统暂无此款参数");
				   erpSpList.get(i).setPhotoStyle("系统暂无此款参数");
				   System.out.println("已进入其他");
			   }
		   }
		return erpSpList;
	}

}


