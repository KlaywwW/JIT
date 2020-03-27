package com.starvincci.JIT.Sqlserver.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.starvincci.JIT.pojo.MmlReqA;

@Mapper
public interface MmlReqAMapper {//申请领料单    相关操作
	
	
	/**
	 * 模糊查询相关编号的申请领料单单号
	 * @param mmlReqA
	 * @return
	 */
	@Select("select top 10 Bt_ID from mmlReqA where  Bt_No like '%'+#{Bt_No}+'%'  and mk_id='A247' and  CRT between #{startDate} and #{endDate} order by CRT DESC")
	@Results(id="mA",value={
		@Result(column="Bt_ID",property="Bt_ID"),
		@Result(column="CRT",property="CRT"),
		@Result(column="Bt_No",property="Bt_No")
	})
	public List<Integer> selectIdsByBtNo(MmlReqA mmlReqA);
	
	//查询bt_id
	
	//外键关联所有子表工令单id集合
	@Select("<script>"
			+ "select OrdA_ID from mpsBook"
			+ "<where>"
			+ "Bt_ID =#{id}"
			+ "</where>"
			+ "</script>")
    public List<Integer> selectmpsBookByBtID(Integer id);
	
	
	/**
	 * 模糊查询 BY申领单号
	 * @param mmlReqA
	 * @return
	 * 
	 * 
	 */
	@Select("select  Bt_No,Bt_ID,CRT from mmlReqA  where  Bt_No like '%'+#{Bt_No}+'%' and mk_id='A247' and  CRT between #{startDate} and #{endDate} order by CRT DESC")
	@Results({
			@Result(column="Bt_ID",property="Bt_ID"),
			@Result(column="CRT",property="CRT"),
			@Result(column="Bt_No",property="Bt_No")
		})
	public List<MmlReqA> selectBtNO(MmlReqA mmlReqA);
}
