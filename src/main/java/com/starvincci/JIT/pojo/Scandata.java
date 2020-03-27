package com.starvincci.JIT.pojo;

/**
 * 测试类(与业务无关)
 * @author admin
 *
 */
public class Scandata {
	
	private Integer id;
	private String workno;
	private String prdno;
	private String facno;
	private String bedno;
	private Integer seq;
	public Scandata() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public Scandata(String workno, String prdno, String facno) {
		super();
		this.workno = workno;
		this.prdno = prdno;
		this.facno = facno;
	}



	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getWorkno() {
		return workno;
	}
	public void setWorkno(String workno) {
		this.workno = workno;
	}
	public String getPrdno() {
		return prdno;
	}
	public void setPrdno(String prdno) {
		this.prdno = prdno;
	}
	public String getFacno() {
		return facno;
	}
	public void setFacno(String facno) {
		this.facno = facno;
	}
	public String getBedno() {
		return bedno;
	}
	public void setBedno(String bedno) {
		this.bedno = bedno;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public Scandata(Integer id, String workno, String prdno, String facno, String bedno, Integer seq) {
		super();
		this.id = id;
		this.workno = workno;
		this.prdno = prdno;
		this.facno = facno;
		this.bedno = bedno;
		this.seq = seq;
	}

	

}
