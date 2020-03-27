package com.starvincci.JIT.pojo;

import java.sql.Timestamp;
import java.util.Date;

public class MmlReqA {//申请领料表
	
	private Integer Bt_ID;//主键id
	private String Bt_No;//申领单单号
	private Integer mk_ID;//生产单位
	private Date   CRT;//制单时间
	private Timestamp startDate;
	private Timestamp endDate;
	private String mmls[];//申领单号数组
    
	
	
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
	public Integer getMk_ID() {
		return mk_ID;
	}
	public void setMk_ID(Integer mk_ID) {
		this.mk_ID = mk_ID;
	}
	public Date getCRT() {
		return CRT;
	}
	public void setCRT(Date cRT) {
		CRT = cRT;
	}
	
	public MmlReqA() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	public String[] getMmls() {
		return mmls;
	}
	public void setMmls(String mmls[]) {
		this.mmls = mmls;
	}
	
	
}
