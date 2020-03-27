package com.starvincci.JIT.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class MpsPlan {//工令单类对应工令单
	
	private Integer    Plan_id;//主键id(mpsPlan)
	private String     Plan_No;//工令单单号(mpsPlan)
	private Date   	   Plan_Date;//开单时间(mpsPlan)
	private BigDecimal Plan_Quan;//开单生产数量(mpsPlan)
	private BigDecimal In_Quan;//实际累计入库数量(mpsPlan)
	private String     Brief;//工令单备注(mpsPlan)
	private Integer    Sp_id;//产品编码表id(erpSp )
	private String     sp_Name;//产品编码表产品名称(erpSp )
	private String     Kh_No;//客户编码（erpKh）
	private String     Kh_Name;//客户名称（erpKh）                   
	private Integer    lkState;//结案状态 0为未结案1为结案
	private Integer    State;//审核状态 0为未审核 1为已审核
	private Integer    Kh_ID;//客户id
	private String    sCP_NOs;//客户订单号
	private String      sp_No;
	private String[]   Plan_Nos;//工单号数组
	private Date[]     Plan_Dates;//制单时间数组
	private Integer    money;//在制金额
	
	

	
	
	public Integer getPlan_id() {
		return Plan_id;
	}
	public void setPlan_id(Integer plan_id) {
		Plan_id = plan_id;
	}
	public String getPlan_No() {
		return Plan_No;
	}
	public void setPlan_No(String plan_No) {
		Plan_No = plan_No;
	}
	public Date getPlan_Date() {
		return Plan_Date;
	}
	public void setPlan_Date(Date plan_Date) {
		Plan_Date = plan_Date;
	}

	public String getBrief() {
		return Brief;
	}
	public void setBrief(String brief) {
		Brief = brief;
	}
	public Integer getSp_id() {
		return Sp_id;
	}
	public void setSp_id(Integer sp_id) {
		Sp_id = sp_id;
	}

	public String getKh_No() {
		return Kh_No;
	}
	public void setKh_No(String kh_No) {
		Kh_No = kh_No;
	}
	public String getKh_Name() {
		return Kh_Name;
	}
	public void setKh_Name(String kh_Name) {
		Kh_Name = kh_Name;
	}
	
	public MpsPlan() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getLkState() {
		return lkState;
	}
	public void setLkState(Integer lkState) {
		this.lkState = lkState;
	}
	public Integer getState() {
		return State;
	}
	public void setState(Integer state) {
		State = state;
	}
	
	
	public Integer getIn_Quan() {
		if(In_Quan!=null) {
			return In_Quan.intValue();
		}else {
			return null;
		}
		
	}
	public void setIn_Quan(BigDecimal in_Quan) {
		In_Quan = in_Quan;
	}
	public Integer getKh_ID() {
		return Kh_ID;
	}
	public void setKh_ID(Integer kh_ID) {
		Kh_ID = kh_ID;
	}
	public Integer getPlan_Quan() {
		if(Plan_Quan!=null) {
			return Plan_Quan.intValue();
		}else {
			return null;
		}
		
	}
	
	public void setPlan_Quan(BigDecimal plan_Quan) {
		Plan_Quan = plan_Quan;
	}
	public String[] getPlan_Nos() {
		return Plan_Nos;
	}
	public void setPlan_Nos(String[] plan_Nos) {
		Plan_Nos = plan_Nos;
	}
	public Date[] getPlan_Dates() {
		return Plan_Dates;
	}
	public void setPlan_Dates(Date[] plan_Dates) {
		Plan_Dates = plan_Dates;
	}
	public String getSp_Name() {
		return sp_Name;
	}
	public void setSp_Name(String sp_Name) {
		this.sp_Name = sp_Name.replaceAll("[^\\u4e00-\\u9fa5]", "");
	}

	public String getSp_No() {
		return sp_No;
	}
	
	public void setSp_No(String sp_No) {
		
		this.sp_No = sp_No;
	}
	public String getsCP_NOs() {
		return sCP_NOs;
	}
	public void setsCP_NOs(String sCP_NOs) {
		this.sCP_NOs = sCP_NOs;
	}
	public Integer getMoney() {
		return money;
	}
	public void setMoney(Integer money) {
		this.money = money;
	}
	
	

}
