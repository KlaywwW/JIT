package com.starvincci.JIT.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 在制工令单
 * @author admin
 *
 */
public class WipDetails implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String mkName;//生产单位 线
	private Timestamp time1;//上线到开料作色的时间 A段
	private Timestamp time2;//上线到成品                    B段
	private Timestamp time3;//上线到包装                    C段
	private Timestamp time4;//出库
	private Timestamp endtime1;//A段结束时间
	private Timestamp endtime2;//B段结束时间
	private Timestamp endtime3;//C段结束时间
	private Timestamp endtime4;//D段结束时间

	private Timestamp outtime;//出库时间

	private String  day1;//A段滞留时间
	private String  day2;//b段滞留时间
	private String  day3;//c段滞留时间
	private Timestamp PEndDate;//计划交期
	private Timestamp LingPiLiaoTime;//皮料的发料时间

	private int timediffer1;//结束时间-开始时间 得到的小时数
	private int timediffer2;//结束时间-开始时间 得到的小时数
	private int timediffer3;//结束时间-开始时间 得到的小时数
	private int timediffer4;//结束时间-开始时间 得到的小时数



	private String info;//当前进度
	private Integer zt;//结案状态
	
	
	private Integer planId;//工令单id
	private String planNo;//工令单No
	private String keHu;//客户订单号
	private String bomNo;//产品编码
	private BigDecimal num;//生产数量

	public Timestamp getOuttime() {
		return outtime;
	}

	public void setOuttime(Timestamp outtime) {
		this.outtime = outtime;
	}

	public int getTimediffer1() {
		return timediffer1;
	}

	public void setTimediffer1(int timediffer1) {
		this.timediffer1 = timediffer1;
	}

	public int getTimediffer2() {
		return timediffer2;
	}

	public void setTimediffer2(int timediffer2) {
		this.timediffer2 = timediffer2;
	}

	public int getTimediffer3() {
		return timediffer3;
	}

	public void setTimediffer3(int timediffer3) {
		this.timediffer3 = timediffer3;
	}

	public int getTimediffer4() {
		return timediffer4;
	}

	public void setTimediffer4(int timediffer4) {
		this.timediffer4 = timediffer4;
	}

	public Timestamp getEndtime1() {
		return endtime1;
	}

	public void setEndtime1(Timestamp endtime1) {
		this.endtime1 = endtime1;
	}

	public Timestamp getEndtime2() {
		return endtime2;
	}

	public void setEndtime2(Timestamp endtime2) {
		this.endtime2 = endtime2;
	}

	public Timestamp getEndtime3() {
		return endtime3;
	}

	public void setEndtime3(Timestamp endtime3) {
		this.endtime3 = endtime3;
	}

	public Timestamp getEndtime4() {
		return endtime4;
	}

	public void setEndtime4(Timestamp endtime4) {
		this.endtime4 = endtime4;
	}

	public String getMkName() {
		return mkName;
	}
	public void setMkName(String mkName) {
		this.mkName = mkName;
	}

	public Integer getPlanId() {
		return planId;
	}
	public void setPlanId(Integer planId) {
		this.planId = planId;
	}
	public String getPlanNo() {
		return planNo;
	}
	public void setPlanNo(String planNo) {
		this.planNo = planNo;
	}
	public String getKeHu() {
		return keHu;
	}
	public void setKeHu(String keHu) {
		this.keHu = keHu;
	}
	public String getBomNo() {
		return bomNo;
	}
	public void setBomNo(String bomNo) {
		this.bomNo = bomNo;
	}
	
	
	public Integer getNum() {
		if(num!=null) {
			return num.intValue();
		}else {
			return null;
		}
		
	}
	
	public void setNum(BigDecimal num) {
		this.num = num;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Timestamp getTime1() {
		return time1;
	}
	public void setTime1(Timestamp time1) {
		this.time1 = time1;
	}
	public Timestamp getTime2() {
		return time2;
	}
	public void setTime2(Timestamp time2) {
		this.time2 = time2;
	}
	public Timestamp getTime3() {
		return time3;
	}
	public void setTime3(Timestamp time3) {
		this.time3 = time3;
	}
	public Timestamp getTime4() {
		return time4;
	}
	public void setTime4(Timestamp time4) {
		this.time4 = time4;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Integer getZt() {
		return zt;
	}
	public void setZt(Integer zt) {
		this.zt = zt;
	}
	public String getDay1() {
		return day1;
	}
	public void setDay1(String day1) {
		this.day1 = day1;
	}
	public String getDay2() {
		return day2;
	}
	public void setDay2(String day2) {
		this.day2 = day2;
	}
	public String getDay3() {
		return day3;
	}
	public void setDay3(String day3) {
		this.day3 = day3;
	}
	public Timestamp getPEndDate() {
		return PEndDate;
	}
	public void setPEndDate(Timestamp pEndDate) {
		PEndDate = pEndDate;
	}
	public Timestamp getLingPiLiaoTime() {
		return LingPiLiaoTime;
	}
	public void setLingPiLiaoTime(Timestamp lingPiLiaoTime) {
		LingPiLiaoTime = lingPiLiaoTime;
	}


	
}
