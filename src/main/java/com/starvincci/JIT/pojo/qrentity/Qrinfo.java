package com.starvincci.JIT.pojo.qrentity;


public class Qrinfo {

  private Integer id;
  private Integer workno;//工号
  private String prdno;//工令单号
  private String facno;//款号
  private Integer qty;//数量
  private String item;//工序
  private String recdate;//录入时间


  public String getItem() {
    return item;
  }

  public void setItem(String item) {
    this.item = item;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public Integer getWorkno() {
    return workno;
  }

  public void setWorkno(Integer workno) {
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


  public Integer getQty() {
    return qty;
  }

  public void setQty(Integer qty) {
    this.qty = qty;
  }


  public String getRecdate() {
    return recdate;
  }

  public void setRecdate(String recdate) {
    this.recdate = recdate;
  }

}
