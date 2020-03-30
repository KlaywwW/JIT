package com.starvincci.JIT.Sqlserver.mapper;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.starvincci.JIT.pojo.Erpsp;
import com.starvincci.JIT.pojo.MpsJobmat;
import com.starvincci.JIT.pojo.MpsPlan;
import com.starvincci.JIT.pojo.WipDetails;

@Mapper
public interface MpsPlanMapper {//工单相关接口

	

	/**
	 * 
	 * @param ids 在制品工单号集合
	 * @return 在制品总计划数量
	 */
	@Select("<script>"
			+ "select Sum(Plan_Quan) from mpsPlan"
			+ "<where>"
			+ " In_Quan!=Plan_Quan "//已完全入库的不统计
			+ "and lkState=0"            //未结案
			+ " and State=1 "             //已审核
			+ "and  Plan_id  in"
			+"<foreach  item='item' collection='list' open='(' close=')' separator=','>"
			+"#{item}"
			+"</foreach>"
			+ "</where>"
			+ "</script>")

	public BigDecimal selectPlanQuee(List<Integer> ids);
	
	@Select("<script>"
			+ "select plan_No from mpsPlan"
			+ "<where>"
			+ " In_Quan!=Plan_Quan "//已完全入库的不统计
			+ "and lkState=0"            //未结案
			+ " and State=1 "             //已审核
			+ "and  Plan_id  in"
			+"<foreach  item='item' collection='list' open='(' close=')' separator=','>"
			+"#{item}"
			+"</foreach>"
			+ "</where>"
			+ "</script>")
	public List<String> selectNosByIDS(List<Integer> ids);
	
	
	
	
	

	@Select("<script>"
			+ "select Sum(Plan_Quan) from mpsPlan"
			+ "<where>"
			+ "  Plan_No  in"
			+"<foreach  item='item' collection='list' open='(' close=')' separator=','>"
			+"#{item}"
			+"</foreach>"
			+ "</where>"
			+ "</script>")

	public BigDecimal selectPlanQueeInPlanNo(List<String> nos);

	
	@Select("<script>"
			+ "select Sum(In_Quan) from mpsPlan"
			+ "<where>"
			+ "  Plan_No  in"
			+"<foreach  item='item' collection='list' open='(' close=')' separator=','>"
			+"#{item}"
			+"</foreach>"
			+ "</where>"
			+ "</script>")

	public BigDecimal selectInQueeInPlanNo(List<String> nos);
	
	
	
	
	/**
	 * 
	 * @param ids 在制品工单号集合
	 * @return     在制品总入库数量
	 */
	@Select("<script>"
			+ "select Sum(In_Quan) from mpsPlan"
			+ "<where>"
			+ " In_Quan!=Plan_Quan "//已完全入库的不统计
			+ "and lkState=0"            //未结案
			+ "and State=1 "             //已审核
			+ "and  Plan_id  in"
			+"<foreach  item='item' collection='list' open='(' close=')' separator=','>"
			+"#{item}"
			+"</foreach>"
			+ "</where>"
			+ "</script>")
	
	public BigDecimal  selectInQuee(List<Integer> ids);
	
	
	/**
	 * 查找成品品检工序已完工的工令单id
	 * @param startDate 起始时间
	 * @param endDate   结束时间
	 * @return
	 */
	@Select("select Plan_id from MpsJob where mk_ID='A247'and Plan_Date between #{startDate} and #{endDate} and ps_Id='A131' and  MoveIn_Quan>0")
	public List<Integer>selectWanGongPLan_id(@Param("startDate")Timestamp startDate,@Param("endDate")Timestamp endDate);
	
	/***
	 * 查找工令单中已审核未结案 出货地点为台湾的工令单id集合
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Select("Select Plan_id from mpsPlan where lkState=0 and  State=1 and Plan_Date between #{startDate} and #{endDate} and In_Quan!=Plan_Quan ")
	public List<Integer> findPlanIdsOfTaiWan(@Param("startDate")Timestamp startDate,@Param("endDate")Timestamp endDate);
	
	
	
	//统计有未完工 工序 的工单ids
	@Select("select  Plan_id from MpsJob where mk_ID='A247'  and Plan_Date between #{startDate} and #{endDate} and FS_Quan < Plan_Quan")
	public List<Integer> selectIDFromMpsJob(@Param("startDate")Timestamp startDate,@Param("endDate")Timestamp endDate);
	
	//统计有完工工序的  工单ids
	@Select("select  Plan_id from MpsJob where mk_ID='A247'  and  Plan_Date between #{startDate} and #{endDate} and FS_Quan = Plan_Quan")
	public List<Integer> selectIDByFS_Quan(@Param("startDate")Timestamp startDate,@Param("endDate")Timestamp endDate);
	
	/**
	 * 
	 * @param ids
	 * @return  包含已结案的工令单信息
	 */
	@Select("<script>"
			+ "select Plan_id,mpsPlan.Brief,Plan_NO,Plan_Quan,In_Quan,Plan_Date,sp_Name,sp_No,sCP_NOs from mpsPlan,erpSp"
			+ "<where>"
			+ "mpsPlan.Sp_id=erpSp.Sp_id "
			+ "and In_Quan!=Plan_Quan "
			+ "and State=1 "            //已审核
			+ "and  Plan_id  in "
			+"<foreach  item='item' collection='list' open='(' close=')' separator=','>"
			+"#{item}"
			+"</foreach>"
			+ "</where>"
			+ "</script>")
	@Results({
		@Result(column="Plan_id",property="Plan_id"),
		@Result(column="Plan_No",property="Plan_No"),
		@Result(column="Plan_Quan",property="Plan_Quan"),
		@Result(column="In_Quan",property="In_Quan"),
		@Result(column="Plan_Date",property="Plan_Date"),
		@Result(column="lkState",property="lkState"),
		@Result(column="sCP_NOs",property="sCP_NOs"),
		@Result(column="sp_No",property="sp_No")
	})
	public List<MpsPlan> selectMpsPlanInfo(List<Integer> ids);
	
	
	/**
	 * 统计在制工单详情   联表查询
	 * @param ids
	 * @return
	 */
	@Select("<script>"
			+ "select Plan_id,mpsPlan.Brief,Plan_NO,Plan_Quan,In_Quan,Plan_Date,sp_Name,sp_No,sCP_NOs from mpsPlan,erpSp"
			+ "<where>"
			+ "mpsPlan.Sp_id=erpSp.Sp_id "
			+ "and In_Quan!=Plan_Quan "
			+ "and State=1 "
			+ "and lkState=0 "             //已审核
			+ "and  Plan_id  in "
			+"<foreach  item='item' collection='list' open='(' close=')' separator=','>"
			+"#{item}"
			+"</foreach>"
			+ "</where>"
			+ "</script>")
	@Results({
		@Result(column="Plan_id",property="Plan_id"),
		@Result(column="Plan_No",property="Plan_No"),
		@Result(column="Plan_Quan",property="Plan_Quan"),
		@Result(column="In_Quan",property="In_Quan"),
		@Result(column="Plan_Date",property="Plan_Date"),
		@Result(column="sp_Name",property="sp_Name"),
		@Result(column="sp_No",property="sp_No"),
		@Result(column="sCP_NOs",property="sCP_NOs"),
		@Result(column="Brief",property="Brief")
	})
	public List<MpsPlan> selectWipInfo(List<Integer> ids);
	
	/**
	 *在制工单id集合
	 * @param ids
	 * @return
	 */
	@Select("<script>"
			+ "select Plan_id from mpsPlan"
			+ "<where>"
			+ " In_Quan!=Plan_Quan "//已完全入库的不统计
			+ "and lkState=0"            //未结案
			+ "and State=1 "             //已审核
			+ "and  Plan_id  in"
			+"<foreach  item='item' collection='list' open='(' close=')' separator=','>"
			+"#{item}"
			+"</foreach>"
			+ "</where>"
			+ "</script>")
	@Results({
		@Result(column="Plan_id",property="Plan_id"),
		@Result(column="Plan_No",property="Plan_No"),
		@Result(column="Plan_Quan",property="Plan_Quan"),
		@Result(column="In_Quan",property="In_Quan"),
		@Result(column="Plan_Date",property="Plan_Date")
	})
	public List<Integer> selectMpsPlanids(List<Integer> ids);
	
	
	
       /***
        * 
        * @param ids
        * @return  用于分段统计的数据
        */
	@Select("<script>"
			+ "select Plan_id,Plan_NO,Plan_Quan,sCP_NOs,sp_No,PEndDate from mpsPlan,erpSp"
			+ "<where>"
			+ "mpsPlan.Sp_id=erpSp.Sp_id "
			+ "and In_Quan!=Plan_Quan "
			+ "and State=1 "
			+ "and lkState=0 "             //已审核
			+ "and  Plan_id  in "
			+"<foreach  item='item' collection='list' open='(' close=')' separator=','>"
			+"#{item}"
			+"</foreach>"
			+ "</where>"
			+ "</script>")
	@Results({
		@Result(column="Plan_id",property="planId"),
		@Result(column="Plan_No",property="planNo"),
		@Result(column="Plan_Quan",property="num"),
		@Result(column="sCP_NOs",property="keHu"),
		@Result(column="sp_No",property="bomNo"),
		@Result(column="PEndDate",property="PEndDate"),
	})
	public List<WipDetails> selectMpsPlansByIds(List<Integer> ids);
	
	
	/**
	 * 模糊查询
	 * @param sp_No
	 * @return
	 */
	@Select("select top 5 Sp_id,Sp_No,Sp_Name  from erpSP where Sp_No like '%'+#{sp_No}+'%' ")
	@Results({
	   @Result(column="Sp_id",property="Sp_id"),
	   @Result(column="Sp_No",property="Sp_No"),
	   @Result(column="Sp_Name",property="Sp_Name")
	})
	public List<Erpsp> selectSpIDByNo(String sp_No);
	
	@Select("select top 10 Plan_id from mpsPlan where Sp_id=#{Sp_id} order by Plan_Date desc")
	public List<Integer> selectmpsPlanBySpid(Integer Sp_id);
	
	
	
	@Select("select Plan_id,Plan_NO,Plan_Quan,sCP_NOs,sp_No from mpsPlan,erpSp where mpsPlan.Sp_id=erpSp.Sp_id and Plan_No=#{No}")
	@Results({
		@Result(column="Plan_id",property="planId"),
		@Result(column="Plan_No",property="planNo"),
		@Result(column="Plan_Quan",property="num"),
		@Result(column="sCP_NOs",property="keHu"),
		@Result(column="sp_No",property="bomNo"),
		
	})
	public WipDetails selectWipDetails(String No); 
	

	/**
	 * 查询出所有的在制工单 的关联表信息
	 * @param id
	 * @return
	 */
    @Select("<script>"
    		+ "select Out_Qty ,Mat_id, Sp_No,sp_Name from MpsJobmat,erpSp "
    		+ "<where>"
    		+ "Mat_id=Sp_id "
    		+ "and Plan_ID in "
    		+"<foreach  item='item' collection='list' open='(' close=')' separator=','>"
			+"#{item}"
			+"</foreach>"
    		+ "</where>"
    		+ "</script>")
    @Results({
		@Result(column="Out_Qty",property="Out_Qty"),
		@Result(column="Plan_ID",property="Plan_ID"),
		@Result(column="Mat_id",property="Mat_id"),
		@Result(column="Sp_No",property="Sp_No"),
		@Result(column="Sp_Name",property="Sp_Name")
	})
    public  List<MpsJobmat> selectAllBomId(List<Integer> ids);
	

	/**
	 * 你以为我不想一步到位吗 每个bom表对应多个价格采购 无法唯一标识所以没法联表查
	 * @param time
	 * @return
	 */
	@Select("<script>"
			+ "select Top 1  LP_Price from  PmPrice"
			+ "<where>"
			+ " SP_ID =#{id} "
			+ "and ToDate>#{time} "
			+ "</where>"
			+ "order by LET desc"
			+ "</script>")

	public BigDecimal selectAllPrice(@Param("time")Timestamp time,@Param("id") Integer id);

	/**
	 * 在源码上二次开发
	 * 根据工令单号查询出数据放入二位码中
	 * @param planno 工令单单号
	 * @return
	 */
	@Select("select * from mpsPlan where Plan_No=#{planno}")
	List<MpsPlan> selectInfoByplanno(@Param("planno") String planno);
	/**
	 * 先在工令单表中查出产品编码id
	 * 再根据产品编码id查询产品信息
	 * @param spid 产品编码id
	 * @return
	 */
	@Select("select Sp_id,Sp_No,Sp_Name from erpsp where Sp_id=#{spid}")
	public Erpsp selectinfoByspid(@Param("spid") Integer spid);
	

}
