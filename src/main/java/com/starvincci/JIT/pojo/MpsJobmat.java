package com.starvincci.JIT.pojo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * 工令单子表 材料用量表
 * @author admin
 *
 */
public class MpsJobmat {
	
	private String Jobmat_ID;//主键id
	private Integer Plan_ID;//工令单id
	private Integer Mat_id;//存货编码价格
	private BigDecimal PUse_Qty;//物料应用量
	private BigDecimal LP_Price;//价格
	private List<Integer> ids;
	private Timestamp time;
	
	private BigDecimal Out_Qty;//已领料数量
	private String Sp_No;//存货编码
	private BigDecimal Bommoney;//单个物料总价
	private BigDecimal PlanMoney;//订单价格
	private String Sp_Name;//存货编码
	
	
	
	public MpsJobmat() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getJobmat_ID() {
		return Jobmat_ID;
	}
	public void setJobmat_ID(String jobmat_ID) {
		Jobmat_ID = jobmat_ID;
	}
	public Integer getPlan_ID() {
		return Plan_ID;
	}
	public void setPlan_ID(Integer plan_ID) {
		Plan_ID = plan_ID;
	}
	public Integer getMat_id() {
		return Mat_id;
	}
	public void setMat_id(Integer mat_id) {
		Mat_id = mat_id;
	}
	public BigDecimal getPUse_Qty() {
		return PUse_Qty;
	}
	public void setPUse_Qty(BigDecimal pUse_Qty) {
		PUse_Qty = pUse_Qty;
	}
	public BigDecimal getLP_Price() {
		return LP_Price;
	}
	public void setLP_Price(BigDecimal lP_Price) {
		LP_Price = lP_Price;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public List<Integer> getIds() {
		return ids;
	}
	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
	public BigDecimal getOut_Qty() {
		return Out_Qty;
	}
	public void setOut_Qty(BigDecimal out_Qty) {
		Out_Qty = out_Qty;
	}
	public String getSp_No() {
		return Sp_No;
	}
	public void setSp_No(String sp_No) {
		Sp_No = sp_No;
	}
	public Integer getBommoney() {
		return Bommoney.intValue();
	}
	public void setBommoney(BigDecimal bommoney) {
		Bommoney = bommoney;
	}
	public Integer getPlanMoney() {
		return PlanMoney.intValue();
	}
	public void setPlanMoney(BigDecimal planMoney) {
		PlanMoney = planMoney;
	}
	public String getSp_Name() {
		return Sp_Name;
	}
	public void setSp_Name(String sp_Name) {
		Sp_Name = sp_Name;
	}
	
	

}
