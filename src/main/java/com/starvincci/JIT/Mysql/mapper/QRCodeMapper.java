package com.starvincci.JIT.Mysql.mapper;

import com.starvincci.JIT.pojo.WipDetails;
import com.starvincci.JIT.pojo.qrentity.Qrinfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface QRCodeMapper {

    /**
     * 检查这个码是否被扫过
     * @param qrinfo
     * @return
     */
    @Select("SELECT * FROM qrinfo WHERE prdno=#{prdno} AND facno=#{facno} AND qty=#{qty} and item=#{item}")
    public Qrinfo checkqrOnly(Qrinfo qrinfo);


    /**
     * 添加扫码数据
     * @param qrinfo
     * @return
     */
    @Insert("INSERT INTO qrinfo VALUES (null,#{workno},#{prdno},#{facno},#{qty},#{recdate},#{item})")
    public int addqr(Qrinfo qrinfo);

    /**
     * 查询wipdeatils表中某段工序是否开始
     * @param prdno 工令单号
     * @param facno 款号
     * @return
     */
    @Select("SELECT * from wipdetails where planNo=#{planNo} and erpspNo=#{erpspNo}")
    public WipDetails checkwipdetails(@Param("planNo") String prdno,@Param("erpspNo") String facno);

    /**
     * 修改结束时间
     * @param wipDetails
     * @return
     */
    @Update("update wipdetails set endtime1=#{endtime1} where planNo=#{planNo}")
    public int updateendtime1 (WipDetails wipDetails);

    @Update("update wipdetails set endtime2=#{endtime2} where planNo=#{planNo}")
    public int updateendtime2 (WipDetails wipDetails);

    @Update("update wipdetails set endtime3=#{endtime3} where planNo=#{planNo}")
    public int updateendtime3 (WipDetails wipDetails);

    @Update("update wipdetails set endtime4=#{endtime4} where planNo=#{planNo}")
    public int updateendtime4 (WipDetails wipDetails);



}
