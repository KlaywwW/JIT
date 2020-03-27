package com.starvincci.JIT.pojo;

import java.math.BigDecimal;

public class MpsJob {//加工单表
	private Integer    Plan_id;//外键id(mpsPlan)
	private Integer    Job_ID;//主键id(MPSJOB)
	private String    Job_No;//加工单号(mpsPlan)
	private BigDecimal In_Quan;//完工数量
	private Integer    Mk_Id;//生产单位表外键(bmMk）制二线为A247
	private BigDecimal  FS_Quan;//完工数量
	private BigDecimal  Plan_Quan;//完工数量
	
	public Integer getPlan_id() {
		return Plan_id;
	}
	public void setPlan_id(Integer plan_id) {
		Plan_id = plan_id;
	}
	public Integer getJob_ID() {
		return Job_ID;
	}
	public void setJob_ID(Integer job_ID) {
		Job_ID = job_ID;
	}
	public String getJob_No() {
		return Job_No;
	}
	public void setJob_No(String job_No) {
		Job_No = job_No;
	}
	public BigDecimal getIn_Quan() {
		return In_Quan;
	}
	public void setIn_Quan(BigDecimal in_Quan) {
		In_Quan = in_Quan;
	}
	public Integer getMk_Id() {
		return Mk_Id;
	}
	public void setMk_Id(Integer mk_Id) {
		Mk_Id = mk_Id;
	}
	public BigDecimal getFS_Quan() {
		return FS_Quan;
	}
	public void setFS_Quan(BigDecimal fS_Quan) {
		FS_Quan = fS_Quan;
	}
	public BigDecimal getPlan_Quan() {
		return Plan_Quan;
	}
	public void setPlan_Quan(BigDecimal plan_Quan) {
		Plan_Quan = plan_Quan;
	}

}
