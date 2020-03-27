package com.starvincci.JIT.Sqlserver.mapper;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.starvincci.JIT.pojo.Erpsp;
import com.starvincci.JIT.pojo.Scandata;

import javax.swing.plaf.PanelUI;


/**
 * 存货档案
 * @author admin
 *
 */
@Mapper
public interface erpMapper {
	
	@Insert("insert  into scan_data (workno,prdno,facno) values(#{workno},#{prdno},#{facno})")
	public int insert(Scandata scan);
	
	
	
	
	
	/**
	 * 动态sql查询所有的存货编码信息
	 * @param erpsp
	 * @return
	 */
	@Select("<script>"
			+ "select Sp_No, Sp_Name,erpspPlusmyField03,erpspPlusmyField12,erpspPlusmyField05,erpspPlusmyField06,erpspPlusmyField09,erpspPlusmyField11 from erpsp,erpspPlus"
			+ "<where>"
			+ "erpsp.Sp_id=erpspPlus.SP_ID "
			+ "<if test='Sp_id !=null'>"
			+ "and erpsp.Sp_id=#{Sp_id}"
			+ "</if>"
			+ "<if test='Sp_No!=null'>"
			+ "and Sp_No=#{Sp_No} "    //存货编码名称
			+ "</if>"                 
			+ "<if test='erpspPlusmyField12!=null'>"
			+ " and erpspPlusmyField12=#{erpspPlusmyField12}" //客户编码
			+ "</if>"
			+ "<if test='erpspPlusmyField05!=null'>"
			+ " and erpspPlusmyField05=#{erpspPlusmyField05}" //品牌名称
			+ "</if>" 
			+ "</where>"
			+ "</script>")
	@Results({
		@Result(column="Sp_No",property="Sp_No"),
		@Result(column="Sp_Name",property="Sp_Name"),
		@Result(column="erpspPlusmyField03",property="erpspPlusmyField03"),
		@Result(column="erpspPlusmyField12",property="erpspPlusmyField12"),
		@Result(column="erpspPlusmyField05",property="erpspPlusmyField05"),
		@Result(column="erpspPlusmyField06",property="erpspPlusmyField06"),
		@Result(column="erpspPlusmyField09",property="erpspPlusmyField09"),
		@Result(column="erpspPlusmyField11",property="erpspPlusmyField11")
	})
	public List<Erpsp> selectAllerpSP(Erpsp erpsp);
	
	
	/**
	 * 根据工单编号找到工单id
	 * 
	 * @param Plan_No
	 * @return
	 */
	@Select("select Sp_id from mpsPlan where Plan_No=#{Plan_No}")
	public Integer selectMpsPlan(String Plan_No);


	
   
}
