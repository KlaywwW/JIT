package com.starvincci.JIT.Sqlserver.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.starvincci.JIT.pojo.MmlReqOutA;


@Mapper
public interface MmlReqOutMapper { //生产领料单 相关接口

	//查询生产领料备注中发往制二线的单号集合
	@Select("select Bt_id from mmlReqOutA where mmlReqOutA.Brief like '%'+#{Brief}+'%' and Bt_Date between  #{startDate} and #{endDate}  ")
	public List<Integer> selectMmlAIdsByBrief(MmlReqOutA mmlReqOutA);
	
	
	//联表查找制二线所有开了领料单的工令单id集合  包含已结案的  只是第一步筛选
	@Select("<script>"
			+ "select OrdA_ID from mmlReqOutB "
			+ "<where>"
			+ " mmlReqOutB.mk_ID='A247' "
			+ "and  Bt_id  in"
			+"<foreach  item='item' collection='list' open='(' close=')' separator=','>"
			+"#{item}"
			+"</foreach>"
			+ "</where>"
			+ "</script>")
	public List<Integer> selectPlanIDInMmlReqOutB(List<Integer> ids);
	
	@Select("select Count(1) from mmlReqOutB where mk_ID='A247' and Bt_Date&gt;= #{startDate}  ")
	public Integer selectPlanIDInMmlReqOutB1(@Param("startDate")Timestamp startDate,@Param("endDate")Timestamp endDate);

	//查询生产领料单中某工令单的皮料领料时间
	@Select("select top 1 Bt_Date from mmlReqOutB,erpSp where OrdA_ID=#{id} and mmlReqOutB.sp_ID=erpSp.Sp_id  and erpSP.Unit_id='A067' and Sp_Name like '%'+'皮'+'%' ")
	public Timestamp getLingLiaoTimeByPlanId(Integer id);

	
	
}
