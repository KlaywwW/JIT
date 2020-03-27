package com.starvincci.JIT.pojo;
/**
 * 采购价格表
 * @author admin
 *
 */

import java.math.BigDecimal;
import java.sql.Timestamp;

public class PmPrice {
	
	private String Price_Id;//价格表id
	private BigDecimal LP_Price;//rmb价格
    private Timestamp FromDate;//生效日期 取最晚
    private BigDecimal SP_ID;//bomid
    private BigDecimal Out_Qty;//已领料数量
    
    
    
	public String getPrice_Id() {
		return Price_Id;
	}
	public void setPrice_Id(String price_Id) {
		Price_Id = price_Id;
	}
	public BigDecimal getLP_Price() {
		return LP_Price;
	}
	public void setLP_Price(BigDecimal lP_Price) {
		LP_Price = lP_Price;
	}
	public Timestamp getFromDate() {
		return FromDate;
	}
	public void setFromDate(Timestamp fromDate) {
		FromDate = fromDate;
	}
	public BigDecimal getSP_ID() {
		return SP_ID;
	}
	public void setSP_ID(BigDecimal sP_ID) {
		SP_ID = sP_ID;
	}
	public BigDecimal getOut_Qty() {
		return Out_Qty;
	}
	public void setOut_Qty(BigDecimal out_Qty) {
		Out_Qty = out_Qty;
	}
    
    
    

}
