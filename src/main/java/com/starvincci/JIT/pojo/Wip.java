package com.starvincci.JIT.pojo;

import java.sql.Timestamp;

public class Wip {//从数据库 记录每天在制数量的变化用于绘制曲线图
	private Timestamp time;
	private Integer Num;
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public Integer getNum() {
		return Num;
	}
	public void setNum(Integer num) {
		Num = num;
	}



}
