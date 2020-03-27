package com.starvincci.JIT.pojo;

public class MpsBook {//申请领料单子表

	private Integer Bl_ID;//主键id
	private String  Bt_ID;//外键id
	private String  Plan_No;//工令单单号
	private Integer isIn;//是否开了生产领料单 1为开了 0为未开申领单
	
	public Integer getBl_ID() {
		return Bl_ID;
	}
	public void setBl_ID(Integer bl_ID) {
		Bl_ID = bl_ID;
	}
	public String getBt_ID() {
		return Bt_ID;
	}
	public void setBt_ID(String bt_ID) {
		Bt_ID = bt_ID;
	}
	public String getPlan_No() {
		return Plan_No;
	}
	public void setPlan_No(String plan_No) {
		Plan_No = plan_No;
	}
	public Integer getIsIn() {
		return isIn;
	}
	public void setIsIn(Integer isIn) {
		this.isIn = isIn;
	}
	public MpsBook() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
