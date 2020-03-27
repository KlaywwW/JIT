package com.starvincci.JIT.pojo;

import java.io.Serializable;

/**
 * 存货编码子表
 * @author admin
 *
 */
public class ErpspPlus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer SP_ID;//外键id
	private String erpspPlusmyField03;//UPC号码
	private String erpspPlusmyField12;//客户编码
	private String erpspPlusmyField05;//品牌名称
	private String erpspPlusmyField06;//价格
	private String erpspPlusmyField09;//款式类别
	private String erpspPlusmyField11;//使用对象
	
	public String getErpspPlusmyField03() {
		return erpspPlusmyField03;
	}
	public void setErpspPlusmyField03(String erpspPlusmyField03) {
		this.erpspPlusmyField03 = erpspPlusmyField03;
	}
	public String getErpspPlusmyField12() {
		return erpspPlusmyField12;
	}
	public void setErpspPlusmyField12(String erpspPlusmyField12) {
		this.erpspPlusmyField12 = erpspPlusmyField12;
	}
	public String getErpspPlusmyField05() {
		return erpspPlusmyField05;
	}
	public void setErpspPlusmyField05(String erpspPlusmyField05) {
		this.erpspPlusmyField05 = erpspPlusmyField05;
	}
	public String getErpspPlusmyField06() {
		return erpspPlusmyField06;
	}
	public void setErpspPlusmyField06(String erpspPlusmyField06) {
		this.erpspPlusmyField06 = erpspPlusmyField06;
	}
	public String getErpspPlusmyField09() {
		return erpspPlusmyField09;
	}
	public void setErpspPlusmyField09(String erpspPlusmyField09) {
		this.erpspPlusmyField09 = erpspPlusmyField09;
	}
	public String getErpspPlusmyField11() {
		return erpspPlusmyField11;
	}
	public void setErpspPlusmyField11(String erpspPlusmyField11) {
		this.erpspPlusmyField11 = erpspPlusmyField11;
	}
	public Integer getSP_ID() {
		return SP_ID;
	}
	public void setSP_ID(Integer sP_ID) {
		SP_ID = sP_ID;
	}

}
