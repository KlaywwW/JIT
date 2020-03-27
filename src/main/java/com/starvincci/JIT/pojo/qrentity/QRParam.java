package com.starvincci.JIT.pojo.qrentity;

public class QRParam {
    private String strs;//扫描的结果
    private Integer jobNum;//扫描时输入的工号

    @Override
    public String toString() {
        return "QRParam{" +
                "strs='" + strs + '\'' +
                ", jobNum='" + jobNum + '\'' +
                '}';
    }

    public String getStrs() {
        return strs;
    }

    public void setStrs(String strs) {
        this.strs = strs;
    }

    public Integer getJobNum() {
        return jobNum;
    }

    public void setJobNum(Integer jobNum) {
        this.jobNum = jobNum;
    }
}
