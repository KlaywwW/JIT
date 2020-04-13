package com.starvincci.JIT.Mysql.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.starvincci.JIT.pojo.Wip;
import com.starvincci.JIT.pojo.WipDetails;


@Mapper
public interface WipMapper {
	
	@Select("Select * from wip where time > #{beginTime} order by time  ")
	public List<Wip> selectWip(@Param("beginTime")Timestamp beginTime);

	
	@Insert("insert into wip value(#{time},#{Num})")
	public int insertOneInfo(Wip wip);

	/**
	 * 新增一条工单记录
	 */
	@Insert("insert into wipdetails value(null,#{planNo},#{bomNo},#{mkName},#{info},#{zt},#{time1},#{endtime1},#{time2},#{endtime2},#{time3},#{endtime3},#{time4},#{endtime4},null,null,null,null)")
    public int insertOneMpsPlan(WipDetails wipdetails);
  
	/**
	 * 查询在制品工令单中的
	 * @param info
	 * @return
	 */
	@Select("<script>"
			+ "select planNo from wipdetails "
			+ "<where>"
			+ "planNo in"
			+"<foreach  item='item' collection='list' open='(' close=')' separator=','>"
			+"#{item}"
			+"</foreach>"
			+ "and info='A段' "
			+ "</where>"
			+ "</script>")
	public List<String> selectWipInDuan(List<String> Nos);
	
	
	/**
	 * 查询在制品工令单中的
	 * @param info
	 * @return
	 */
	@Select("<script>"
			+ "select planNo from wipdetails "
			+ "<where>"
			+ "planNo in"
			+"<foreach  item='item' collection='list' open='(' close=')' separator=','>"
			+"#{item}"
			+"</foreach>"
			+ "and info='B段' "
			+ "</where>"
			+ "</script>")
	public List<String> selectWipInDuan2(List<String> Nos);
	
	/**
	 * 查询在制品工令单中的
	 * @param info
	 * @return
	 */
	@Select("<script>"
			+ "select planNo from wipdetails "
			+ "<where>"
			+ "planNo in"
			+"<foreach  item='item' collection='list' open='(' close=')' separator=','>"
			+"#{item}"
			+"</foreach>"
			+ "and info='C段' "
			+ "</where>"
			+ "</script>")
	public List<String> selectWipInDuan3(List<String> Nos);

	/**
	 * 查询在制品工令单中的
	 * @param info
	 * @return
	 */
	@Select("<script>"
			+ "select planNo from wipdetails "
			+ "<where>"
			+ "planNo in"
			+"<foreach  item='item' collection='list' open='(' close=')' separator=','>"
			+"#{item}"
			+"</foreach>"
			+ "and info='D段' "
			+ "</where>"
			+ "</script>")
	public List<String> selectWipInDuanD(List<String> Nos);
	
	/**
	 * 查询在制品工令单中的
	 * @param info
	 * @return
	 */
	@Select("<script>"
			+ "select planNo from wipdetails "
			+ "<where>"
			+ "planNo in"
			+"<foreach  item='item' collection='list' open='(' close=')' separator=','>"
			+"#{item}"
			+"</foreach>"
			+ "and info='入库' "
			+ "</where>"
			+ "</script>")
	public List<String> selectWipInDuan4(List<String> Nos);
	 /**
	  * 
	  * @param planNo  工令单NO
	  * @param erpspNo BOM
	  * 
	  * @return
	  */
    @Select("select * from wipdetails where planNo=#{planNo} ")
	@Results({
		@Result(column="id",property="planId"),
		@Result(column="planNo",property="planNo"),
		@Result(column="time1",property="time1"),
		@Result(column="time2",property="time2"),
		@Result(column="time3",property="time3"),
		@Result(column="time4",property="time4"),
	})
    public WipDetails selectWipDetails(@Param("planNo")String planNo);
    
    
    @Update("<script>"
    		+ "update wipdetails"
    		+ "<set>"
    		+ "<if test='time1!=null'>"
    		+ "time1=#{time1},"
    		+ "</if>"
    		+ "<if test='time2!=null'>"
    		+ "time2=#{time2},"
    		+ "</if>"
    		+ "<if test='time3!=null'>"
    		+ "time3=#{time3},"
    		+ "</if>"
    		+ "<if test='time4!=null'>"
    		+ "time4=#{time4},"
    		+ "</if>"
			+" <if test='outtime!=null'>"
			+ "outtime=#{outtime},"
			+"</if>"
    		+ "<if test='zt!=null'>"
    		+ "zt=#{zt},"
    		+ "</if>"
    		+ "<if test='day1!=null'>"
    		+ "day1=#{day1},"
    		+ "</if>"
    		+ "<if test='day2!=null'>"
    		+ "day2=#{day2},"
    		+ "</if>"
    		+ "<if test='day3!=null'>"
    		+ "day3=#{day3},"
    		+ "</if>"
    		+ "<if test='info!=null'>"
    		+ "info=#{info}"
    		+ "</if>"
    		+ "</set>"
    		+ "<where>"
    		+ "planNo=#{planNo}"
    		+ "</where>"
    		+ "</script>")
    public int updateWipDtetails(WipDetails wipdetail);
    
    
    /**
     * 清空数据
     * 
     */
    @Update("update wipdetails set  time1=null,endtime1=null, info=''  where planNo=#{No}")
    public int clearTime1(String No);
    
    @Delete("Delete from wipdetails where where planNo=#{No} ")
    public int delete(String No);
    
    @Update("update wipdetails set  time2=null,endtime2=null, info='A段' ,day1=null where planNo=#{No} ")
    public int clearTime2(String No);
    
    @Update("update wipdetails set  time3=null,endtime3=null, info='B段' ,day2=null  where planNo=#{No} ")
    public int clearTime3(String No);
    
    @Update("update wipdetails set  time4=null, endtime4=null,zt=1, info='C段', day3=null where planNo=#{No} ")
	public int clearTime4(String No);
	@Update("update wipdetails set  outtime=null, info='D段' where planNo=#{No} ")
	public int clearChuKu(String No);
    
}
