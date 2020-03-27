package com.starvincci.JIT.pojo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class MmlReqOutB {//领料单子表 对应的工单 料数
	
	private Integer     BL_ID;//领料单子表id
	private Integer     Bt_ID; //领料主表id
	private Date        Bt_Date;//生产领料单日期
    private BigDecimal  SpP_Qty;//出库数量(需要数量)
    private BigDecimal  UpP_Qty;//申领数量
    private Integer     OA_ID;//工令单对应外键id(mpsPlan)
	private Integer     OrderB_ID;//工令单子表外键id(mpsJob)
	private Integer     sBL_ID;//对应申请领料单外键id(mpsbook:申领数量)
	private String      mk_ID;
	private Timestamp   startDate;
	private Timestamp   endDate;
	private Integer     sp_ID;//bomdi
	
	
	

	public BigDecimal getSpP_Qty() {
		return SpP_Qty;
	}
	public void setSpP_Qty(BigDecimal spP_Qty) {
		SpP_Qty = spP_Qty;
	}
	public BigDecimal getUpP_Qty() {
		return UpP_Qty;
	}
	public void setUpP_Qty(BigDecimal upP_Qty) {
		UpP_Qty = upP_Qty;
	}
	public MmlReqOutB() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getOrderB_ID() {
		return OrderB_ID;
	}
	public void setOrderB_ID(Integer orderB_ID) {
		OrderB_ID = orderB_ID;
	}
	public Integer getBL_ID() {
		return BL_ID;
	}
	public void setBL_ID(Integer bL_ID) {
		BL_ID = bL_ID;
	}
	public Integer getBt_ID() {
		return Bt_ID;
	}
	public void setBt_ID(Integer bt_ID) {
		Bt_ID = bt_ID;
	}
	public Integer getsBL_ID() {
		return sBL_ID;
	}
	public void setsBL_ID(Integer sBL_ID) {
		this.sBL_ID = sBL_ID;
	}
	public Date getBt_Date() {
		return Bt_Date;
	}
	public void setBt_Date(Date bt_Date) {
		Bt_Date = bt_Date;
	}
	public String getMk_ID() {
		return mk_ID;
	}
	public void setMk_ID(String mk_ID) {
		this.mk_ID = mk_ID;
	}
	public Integer getOA_ID() {
		return OA_ID;
	}
	public void setOA_ID(Integer oA_ID) {
		OA_ID = oA_ID;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public Integer getSp_ID() {
		return sp_ID;
	}
	public void setSp_ID(Integer sp_ID) {
		this.sp_ID = sp_ID;
	}
    
    
}
