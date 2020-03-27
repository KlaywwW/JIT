package com.starvincci.JIT.pojo;

public class EchartVo {//封装echarts信息的实体
   
	private String [] xtime;//X轴坐标为 0.5h 1h 2h 4h 24h
	private int[] wip;//半成品数量
	private int[] finishProduct;//成品入库数量
	private int[] linkCounts;//成品入库数量\
	private String linkQuee;
	
	public String[] getXTime() {
		return xtime;
	}
	public void setXTime(String[] xtime) {
		this.xtime = xtime;
	}
	public int[] getWip() {
		return wip;
	}
	public void setWip(int[] wip) {
		this.wip = wip;
	}
	public int[] getFinishProduct() {
		return finishProduct;
	}
	public void setFinishProduct(int[] finishProduct) {
		this.finishProduct = finishProduct;
	}
	
	public EchartVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int[] getLinkCounts() {
		return linkCounts;
	}
	public void setLinkCounts(int[] linkCounts) {
		this.linkCounts = linkCounts;
	}
	public String getLinkQuee() {
		return linkQuee;
	}
	public void setLinkQuee(String linkQuee) {
		this.linkQuee = linkQuee;
	}
	
	
	
	
}
