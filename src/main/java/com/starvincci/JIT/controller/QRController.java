package com.starvincci.JIT.controller;

import com.alibaba.fastjson.JSON;
import com.starvincci.JIT.Mysql.mapper.QRCodeMapper;
import com.starvincci.JIT.Mysql.service.QRCodeService;
import com.starvincci.JIT.Sqlserver.service.MpsPlanServiceImpl;
import com.starvincci.JIT.pojo.Erpsp;
import com.starvincci.JIT.pojo.MpsPlan;
import com.starvincci.JIT.pojo.WipDetails;
import com.starvincci.JIT.pojo.qrentity.QRParam;
import com.starvincci.JIT.pojo.qrentity.Qrinfo;
import com.starvincci.JIT.util.FilesUtils;
import com.starvincci.JIT.util.qrutil.QRCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class QRController {

    @Resource
    private MpsPlanServiceImpl mpsPlanService;

    @Resource
    private QRCodeService qrCodeService;

    @RequestMapping("/qrinfo")
    private String getInfo(String plan_No, Model model) {
        System.out.println(plan_No);
        List<MpsPlan> list = mpsPlanService.selectInfoByplanno(plan_No);
        System.out.println(list.size());
        Erpsp erpsp = null;
        MpsPlan mpsPlan = null;
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        for (MpsPlan mps : list) {
            mpsPlan = new MpsPlan();
            System.out.println(mps.getSp_id());
            erpsp = mpsPlanService.selectinfoByspid(mps.getSp_id());
            mpsPlan.setPlan_No(mps.getPlan_No());
            mpsPlan.setsCP_NOs(mps.getsCP_NOs());
            mpsPlan.setSp_id(erpsp.getSp_id());
            mpsPlan.setSp_No(erpsp.getSp_No());
            mpsPlan.setSp_Name(erpsp.getSp_Name());
            BigDecimal ins = new BigDecimal(mps.getPlan_Quan());
            mpsPlan.setPlan_Quan(ins);
            mpsPlan.setPlan_Date(mps.getPlan_Date());
        }
        String qrA = mpsPlan.getPlan_No() + "@" + mpsPlan.getSp_No() + "@" + mpsPlan.getPlan_Quan() + "@A@";
        String qrB = mpsPlan.getPlan_No() + "@" + mpsPlan.getSp_No() + "@" + mpsPlan.getPlan_Quan() + "@B@";
        String qrC = mpsPlan.getPlan_No() + "@" + mpsPlan.getSp_No() + "@" + mpsPlan.getPlan_Quan() + "@C@";
        String qrD = mpsPlan.getPlan_No() + "@" + mpsPlan.getSp_No() + "@" + mpsPlan.getPlan_Quan() + "@D@";

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        String time = df.format(new Date());
        String fileDirPath = new String("D:/jitimg/" + time + "_" + mpsPlan.getPlan_No() + ".jpg");
//        String text = "我是小铭";
//        // 嵌入二维码的图片路径
        String imgPath = "";
//        // 生成的二维码的路径及名称
//        String destPath = "E:/JDP/dog.jpg";
//        //生成二维码
        try {
            QRCodeUtil.encode(qrA, imgPath, fileDirPath, true);
            String qrbase64A = FilesUtils.ImageToBase64(fileDirPath);
            model.addAttribute("qrimga", qrbase64A);
            QRCodeUtil.encode(qrB, imgPath, fileDirPath, true);
            String qrbase64B = FilesUtils.ImageToBase64(fileDirPath);
            model.addAttribute("qrimgb", qrbase64B);
            QRCodeUtil.encode(qrC, imgPath, fileDirPath, true);
            String qrbase64C = FilesUtils.ImageToBase64(fileDirPath);
            model.addAttribute("qrimgc", qrbase64C);
            QRCodeUtil.encode(qrD, imgPath, fileDirPath, true);
            String qrbase64D = FilesUtils.ImageToBase64(fileDirPath);
            model.addAttribute("qrimgd", qrbase64D);
        } catch (Exception e) {
            e.printStackTrace();
        }


        model.addAttribute("mpsplans", mpsPlan);


        return "QRInfo";
    }


    @RequestMapping("/jit")
    @ResponseBody
    public String getQr(@RequestBody String jsons) {
        System.out.println(jsons);

        QRParam qrParam = JSON.parseObject(jsons, QRParam.class);
        String str = qrParam.getStrs();
        String[] splitstr = str.split("@");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String time = df.format(new Date());// new Date()为获取当前系统时间
        Qrinfo qrinfo = new Qrinfo();
        qrinfo.setWorkno(qrParam.getJobNum());
        qrinfo.setPrdno(splitstr[0]);
        qrinfo.setFacno(splitstr[1]);
        qrinfo.setQty(Integer.parseInt(splitstr[2]));
        qrinfo.setItem(splitstr[3]);
        qrinfo.setRecdate(time);

        Qrinfo checkqr = qrCodeService.checkqrOnly(qrinfo);
        System.out.println(checkqr);
        if (checkqr == null) {

                /*
                判断工序
                如果是 A 就只需要判断 A 的开始时间
                如果是 B 需要判断 A 的结束时间是不是空的 和 B的开始时间是不是空的
                如果是 C 需要判断 A和B 的结束时间是不是空的 和 C的开始时间是不是空的
                如果是 D 需要判断 A、B和C  和 D的开始时间是不是空的
                 */

            String gongxu = splitstr[3];
            System.out.println("工序----》" + gongxu);
            WipDetails wipDetails = qrCodeService.checkwipdetails(qrinfo.getPrdno(), qrinfo.getFacno());
            int result = 0;
            if (wipDetails != null) {
                if ("A".equals(gongxu)) {
                    if (wipDetails.getTime1() != null && !"".equals(wipDetails.getTime1())) {
                        //修改time1
                        System.out.println(wipDetails.getPlanNo());
                        wipDetails.setEndtime1(Timestamp.valueOf(qrinfo.getRecdate()));
                        System.out.println("end1--->"+wipDetails.getEndtime1());
                        result = qrCodeService.updateendtime1(wipDetails);
                    } else {
                        return "未设置A段开始时间，扫码失败";
                    }
                } else if ("B".equals(gongxu)) {
                    if (wipDetails.getEndtime1() != null && wipDetails.getTime2() != null) {
                        wipDetails.setEndtime2(Timestamp.valueOf(qrinfo.getRecdate()));
                        System.out.println("end2--->"+wipDetails.getEndtime2());
                        result = qrCodeService.updateendtime2(wipDetails);
                    } else {
                        return "未设置B段开始时间或A段并未结束，扫码失败";
                    }

                } else if ("C".equals(gongxu)) {
                    if (wipDetails.getEndtime1() != null && wipDetails.getEndtime2() != null && wipDetails.getTime3() != null) {
                        wipDetails.setEndtime3(Timestamp.valueOf(qrinfo.getRecdate()));
                        System.out.println("end3--->"+wipDetails.getEndtime3());
                        result = qrCodeService.updateendtime3(wipDetails);
                    } else {
                        return "未设置C段开始时间或 A段或B段并未结束，扫码失败";
                    }

                } else if ("D".equals(gongxu)) {
                    if (wipDetails.getEndtime1() != null && wipDetails.getEndtime2() != null && wipDetails.getEndtime3() != null && wipDetails.getTime4() != null) {
                        wipDetails.setEndtime4(Timestamp.valueOf(qrinfo.getRecdate()));
                        System.out.println("end4--->"+wipDetails.getEndtime3());
                        result = qrCodeService.updateendtime4(wipDetails);
                    } else {
                        return "未设置C段开始时间或A段或B段并未结束，扫码失败";
                    }
                }
            }else{
                return "该工序未开始";
            }
//            int res = 1;
            //添加扫码数据
            int res=qrCodeService.addqr(qrinfo);
            if (res == 1 && result == 1) {
                return "扫码成功";
            } else {
                return "扫码失败";
            }
        } else {
            return "请勿重复扫码";
        }


    }

}
