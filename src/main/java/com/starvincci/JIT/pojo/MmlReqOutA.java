package com.starvincci.JIT.pojo;

import java.sql.Timestamp;
import java.util.Date;

public class MmlReqOutA { //生产领料单
	private Integer Bt_ID; //领料单id
	private String  Bt_No;//领料单编号
	private String  mk_ID;//领料单位 A247
	private Date    Bt_Date;//时间
	private String  Brief;//备注
	private String  CN_Brief;//简体 生产单位备注
	private String  TW_Brief;//繁体生产单位备注
	private Timestamp   startDate;
	private Timestamp   endDate;
	
	
	
	public Integer getBt_ID() {
		return Bt_ID;
	}
	public void setBt_ID(Integer bt_ID) {
		Bt_ID = bt_ID;
	}
	public String getBt_No() {
		return Bt_No;
	}
	public void setBt_No(String bt_No) {
		Bt_No = bt_No;
	}
	public MmlReqOutA() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getMk_ID() {
		return mk_ID;
	}
	public void setMk_ID(String mk_ID) {
		this.mk_ID = mk_ID;
	}
	public Date getBt_Date() {
		return Bt_Date;
	}
	public void setBt_Date(Date bt_Date) {
		Bt_Date = bt_Date;
	}

	public String getBrief() {
		return Brief;
	}
	public void setBrief(String brief) {
		Brief = brief;
	}
	public String getCN_Brief() {
		return CN_Brief;
	}
	public void setCN_Brief(String cN_Brief) {
		CN_Brief = cN_Brief;
	}
	public String getTW_Brief() {
		return TW_Brief;
	}
	public void setTW_Brief(String tW_Brief) {
		TW_Brief = tW_Brief;
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
	
	

}
