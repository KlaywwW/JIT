package com.starvincci.JIT.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.starvincci.JIT.Mysql.service.WipService;
import com.starvincci.JIT.Sqlserver.service.MmlReqOutServiceImpl;
import com.starvincci.JIT.Sqlserver.service.MpsPlanServiceImpl;
import com.starvincci.JIT.pojo.WipDetails;
import com.starvincci.JIT.util.DateUtils;

@Controller
@CrossOrigin
public class WipController {

    private static final Logger log = LoggerFactory.getLogger(WipController.class);

    @Autowired
    private MpsPlanServiceImpl mpsService;
    @Autowired
    private WipService wipService;
    @Autowired
    private MmlReqOutServiceImpl mmlReqOutService;


    @RequestMapping("/download")
    public String downloadapk(){
        return "download";
    }

    /**
     * @param request
     * @param Brief   生产单位
     * @return 进入到制二线各段生产
     */
    @RequestMapping("/wipdetails")
    public String collerterct(HttpServletRequest request, String Brief) {
        HttpSession session = request.getSession();
        session.removeAttribute("isZ");


        List<Integer> ids3 = mpsService.findwip("制三");
        List<Integer> ids6 = mpsService.findwip("制六");
        List<Integer> ids9 = mpsService.findwip("制九");

        List<Integer> ids = mpsService.findwip(Brief);
        ids.removeAll(ids3);
        ids.removeAll(ids6);
        ids.removeAll(ids9);

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -6);//自动处理2月平润年的问提
        Date d = c.getTime();
        Timestamp startDate = new Timestamp(d.getTime());

        c.setTime(new Date());
        c.add(Calendar.MONTH, 1);
        d = c.getTime();
        Timestamp endDate = new Timestamp(d.getTime());

        //查询	出台湾的在制品数量
        //1查询出出货台湾工单id集合
        List<Integer> taiWanIds = mpsService.findPlanIdsOfTaiWan(startDate, endDate);
        //2查询制二线完品检工序 完工量>0的
        List<Integer> pingJianIds = mpsService.selectWanGongPLan_id(startDate, endDate);
        //3两者取交集 得出皮夹线的发往台湾的数量
        pingJianIds.retainAll(taiWanIds);
        //4再和领料单中有制二线的工单集合取交集
        pingJianIds.retainAll(ids);

        //4去重
        List<Integer> taiwanids1 = pingJianIds.stream().distinct().collect(Collectors.toList());
        ids.removeAll(taiwanids1);



        List<WipDetails> wipde = mpsService.selectMpsPlansByIds(ids);
        //将id集合一一遍历循环 查找mysql数据库中
        WipDetails wipdetail = null;
        String planNo = "";
        for (int i = 0; i < wipde.size(); i++) {
            wipde.get(i).setLingPiLiaoTime(mmlReqOutService.getLingLiaoTimeByPlanId(wipde.get(i).getPlanId()));
            planNo = wipde.get(i).getPlanNo();
            wipdetail = wipService.selectWipDetails(planNo);
            if (wipdetail != null) {
                wipde.get(i).setTime1(wipdetail.getTime1());
                wipde.get(i).setTime2(wipdetail.getTime2());
                wipde.get(i).setTime3(wipdetail.getTime3());
                wipde.get(i).setTime4(wipdetail.getTime4());

                //jdp修改二次开发
                //获取结束时间
                wipde.get(i).setEndtime1(wipdetail.getEndtime1());
                wipde.get(i).setEndtime2(wipdetail.getEndtime2());
                wipde.get(i).setEndtime3(wipdetail.getEndtime3());
                wipde.get(i).setEndtime4(wipdetail.getEndtime4());
                wipde.get(i).setOuttime(wipdetail.getOuttime());

//                获取时间差
                if (wipdetail.getEndtime1() != null) {
                    int hourdiffer1 = DateUtils.getHourDiffer(wipdetail.getTime1(), wipdetail.getEndtime1());
                    wipde.get(i).setTimediffer1(hourdiffer1);
                    System.out.println("diff1---"+wipde.get(i).getTimediffer1());
                }
                if (wipdetail.getEndtime2() != null) {
                    int hourdiffer2 = DateUtils.getHourDiffer(wipdetail.getTime2(), wipdetail.getEndtime2());
                    wipde.get(i).setTimediffer2(hourdiffer2);
                    System.out.println("diff2---"+wipde.get(i).getTimediffer2());
                }
                if (wipdetail.getEndtime3() != null) {
                    int hourdiffer3 = DateUtils.getHourDiffer(wipdetail.getTime3(), wipdetail.getEndtime3());
                    wipde.get(i).setTimediffer3(hourdiffer3);
                    System.out.println("diff3---"+wipde.get(i).getTimediffer3());
                }
                if (wipdetail.getEndtime4() != null) {
                    int hourdiffer4 = DateUtils.getHourDiffer(wipdetail.getTime4(), wipdetail.getEndtime4());
                    wipde.get(i).setTimediffer4(hourdiffer4);
                    System.out.println("diff4---"+wipde.get(i).getTimediffer4());
                }

                wipde.get(i).setInfo(wipdetail.getInfo());
                wipde.get(i).setDay1(wipdetail.getDay1());
                wipde.get(i).setDay2(wipdetail.getDay2());
                wipde.get(i).setDay3(wipdetail.getDay3());
            }
        }


        session.removeAttribute("Allwipdetails");
//        session.setAttribute("Allwipdetails", wipde);
        session.setAttribute("isZ", 1);



        //查询到在制工单集合
//        List<WipDetails> wipde = mpsService.selectMpsPlansByIds(ids);
//        //将id集合一一遍历循环 查找mysql数据库中
//        WipDetails wipdetail = null;
//        String planNo = "";
//        //循环遍历集合获取NO，查询到数据
//        //进入首页不涉及到新增数据
//        for (int i = 0; i < wipde.size(); i++) {
//            System.out.println("开始循环..");
//            // wipde.get(i).setLingPiLiaoTime(mmlReqOutService.getLingLiaoTimeByPlanId(wipde.get(i).getPlanId()));
//            planNo = wipde.get(i).getPlanNo();
//            wipdetail = wipService.selectWipDetails(planNo);
//            if (wipdetail != null) {
//                wipde.get(i).setTime1(wipdetail.getTime1());
//                wipde.get(i).setTime2(wipdetail.getTime2());
//                wipde.get(i).setTime3(wipdetail.getTime3());
//                wipde.get(i).setTime4(wipdetail.getTime4());
//                wipde.get(i).setInfo(wipdetail.getInfo());
//                wipde.get(i).setDay1(wipdetail.getDay1());
//                wipde.get(i).setDay2(wipdetail.getDay2());
//                wipde.get(i).setDay3(wipdetail.getDay3());
//            }
//        }
        session.setAttribute("Allwipdetails", wipde);

        //根据在制id集合获取到在制No集合

        //查询每个段的工单进度
        List<String> Nos = mpsService.selectNosByIDS(ids);
        List<String> listA = wipService.selectWipInDuan(Nos);
        //获取A段的在制数量
        Integer SumA = 0;
        if (listA != null && listA.size() > 0) {
            SumA = mpsService.selectPlanQueeInPlanNo(listA).intValue() - mpsService.selectInQueeInPlanNo(listA).intValue();
        } else {
            SumA = 0;
        }
        System.out.println("SumA-------"+SumA);


        //查询B
        List<String> listB = wipService.selectWipInDuan2(Nos);
        Integer SumB = 0;
        if (listB != null && listB.size() > 0) {
            SumB = mpsService.selectPlanQueeInPlanNo(listB).intValue() - mpsService.selectInQueeInPlanNo(listB).intValue();
        } else {
            SumB = 0;
        }
        System.out.println("SumB-------"+SumB);
        //查询C
        List<String> listC = wipService.selectWipInDuan3(Nos);
        Integer SumC = 0;
        if (listC != null && listC.size() > 0) {
            SumC = mpsService.selectPlanQueeInPlanNo(listC).intValue() - mpsService.selectInQueeInPlanNo(listC).intValue();
        } else {
            SumC = 0;
        }
        System.out.println("SumC-------"+SumC);

        //查询D
        List<String> listD = wipService.selectWipInDuanD(Nos);
        Integer SumD = 0;
        if (listD != null && listD.size() > 0) {
            SumD = mpsService.selectPlanQueeInPlanNo(listD).intValue() - mpsService.selectInQueeInPlanNo(listD).intValue();
        } else {
            SumD = 0;
        }
        System.out.println("SumD-------"+SumD);

//        List<String> listD = wipService.selectWipInDuanD(Nos);
//        List<String> listD2 = wipService.selectWipInDuan4(Nos);
//        boolean a = (listD != null && listD.size() > 0 == true);
//        boolean a2 = (listD2 != null && listD2.size() > 0 == true);
//        Integer SumD = 0;
//        if (a || a2) {
//            listD.addAll(listD2);
//            SumD = mpsService.selectPlanQueeInPlanNo(listD).intValue() - mpsService.selectInQueeInPlanNo(listD).intValue();
//        } else {
//            SumD = 0;
//        }

//        List<String> listC = wipService.selectWipInDuan3(Nos);
//        List<String> listC2 = wipService.selectWipInDuan4(Nos);
//        boolean a = (listC != null && listC.size() > 0 == true);
//        boolean a2 = (listC2 != null && listC2.size() > 0 == true);
//        Integer SumC = 0;
//        if (a || a2) {
//            listC.addAll(listC2);
//            SumC = mpsService.selectPlanQueeInPlanNo(listC).intValue() - mpsService.selectInQueeInPlanNo(listC).intValue();
//        } else {
//            SumC = 0;
//        }


        BigDecimal SumIn = mpsService.selectInQuee(ids);
        BigDecimal SumOut = mpsService.selectPlanQuee(ids);
        Integer wip = SumOut.intValue() - SumIn.intValue();
        log.info("wiP:" + wip);


        BigDecimal f1 = new BigDecimal((float) SumA * 100 / wip);
        BigDecimal decimalA = f1.setScale(2, BigDecimal.ROUND_HALF_UP);
        log.info(decimalA + "");
        session.setAttribute("A", decimalA);
        session.setAttribute("An", SumA);


        BigDecimal f2 = new BigDecimal((float) SumB * 100 / wip);
        BigDecimal decimalB = f2.setScale(2, BigDecimal.ROUND_HALF_UP);
        log.info(f2 + "");
        session.setAttribute("B", decimalB);
        session.setAttribute("Bn", SumB);


        BigDecimal f3 = new BigDecimal((float) SumC * 100 / wip);
        BigDecimal decimalC = f3.setScale(2, BigDecimal.ROUND_HALF_UP);
        log.info(f3 + "");

        session.setAttribute("C", decimalC);
        session.setAttribute("Cn", SumC);

        BigDecimal f4 = new BigDecimal((float) SumD * 100 / wip);
        BigDecimal decimalD = f4.setScale(2, BigDecimal.ROUND_HALF_UP);
        log.info(f4 + "");

        session.setAttribute("D", decimalD);
        session.setAttribute("Dn", SumD);


        return "WIPDetails";
    }

    /**
     * 新增数据节点可以是任意单位
     * 但是更改节点 只能更改之后的节点 禁止往前更改数据
     *
     * 如果页面表格列有变化，对应的index也需要变化
     *
     * @param session
     * @param No      工令单单号
     * @param index   工序下标 1为开料作色  2为成品  3为包装
     * @return 点击界面新增一条数据，
     */
    @RequestMapping("update")
    @ResponseBody
    public String updateInfo(HttpSession session, String No, Integer index) {
        //1查询mysql数据库有无此工单数据
        WipDetails wipdetail = wipService.selectWipDetails(No);
        Timestamp timestamp = new Timestamp(new Date().getTime());

        if (wipdetail == null) {
            //2 SQLserver 查询
            WipDetails wip = mpsService.selectWipDetails(No);
            if (index == 1) {
                wip.setInfo("A段");
                wip.setMkName("制二");
                wip.setTime1(timestamp);
                wip.setZt(1);//未结案状态
                //3新增数据
                wipService.insertOneMpsPlan(wip);
                return "OK";
            } else if (index == 3) {
                wip.setInfo("B段");
                wip.setMkName("制二");
                wip.setTime2(timestamp);
                wip.setZt(1);//未结案状态
                //3新增数据
                wipService.insertOneMpsPlan(wip);
                return "OK";
            } else if (index == 5) {
                wip.setInfo("C段");
                wip.setMkName("制二");
                wip.setTime3(timestamp);
                wip.setZt(1);//未结案状态
                //3新增数据
                wipService.insertOneMpsPlan(wip);
                return "OK";
            } else if (index == 7) {
                wip.setInfo("D段");
                wip.setMkName("制二");
                wip.setTime4(timestamp);
                wip.setZt(1);//结案状态
                //3新增数据
                wipService.insertOneMpsPlan(wipdetail);
                return "OK";
            }
            //二次开发新增
            else if(index == 9){
                wip.setInfo("入库");
                wip.setMkName("制二");
                wip.setOuttime(timestamp);
                wip.setZt(0);
            }


        } else {
            //当前款式前工序已有记录   需要修改当前工序
            boolean isAdd = wipdetail.getTime1() == null && wipdetail.getTime2() == null && wipdetail.getTime3() == null && wipdetail.getTime4() == null;

            if (index == 1) {
                if (isAdd) {
                    wipdetail.setInfo("A段");
                    wipdetail.setTime1(timestamp);
                    wipService.updateWipDtetails(wipdetail);
                    return "OK";
                } else {
                    return "error";
                }

            } else if (index == 3) {
                if (wipdetail.getTime1() != null && wipdetail.getEndtime1()!=null && !"".equals(wipdetail.getEndtime1())) {
                    wipdetail.setInfo("B段");
                    wipdetail.setTime2(timestamp);
                    String mes = DateUtils.getDayDiffer(wipdetail.getTime1(), timestamp);
                    wipdetail.setDay1(mes);
                    wipService.updateWipDtetails(wipdetail);
                    return "OK";
                } else if (isAdd) {
                    wipdetail.setInfo("B段");
                    wipdetail.setTime2(timestamp);
                    wipService.updateWipDtetails(wipdetail);
                    return "OK";
                } else {
                    //向前工序修改数据
                    return "error";
                }

            } else if (index == 5) {
                if (wipdetail.getTime2() != null && wipdetail.getEndtime2()!=null && !"".equals(wipdetail.getEndtime2())) {
                    wipdetail.setInfo("C段");
                    wipdetail.setTime3(timestamp);
                    String mes = DateUtils.getDayDiffer(wipdetail.getTime2(), timestamp);
                    wipdetail.setDay2(mes);
                    wipService.updateWipDtetails(wipdetail);
                    return "OK";
                } else if (isAdd) {
                    wipdetail.setInfo("C段");
                    wipdetail.setTime3(timestamp);
                    wipService.updateWipDtetails(wipdetail);
                    return "OK";
                } else {
                    //禁止修改前工序修改数据
                    return "error";
                }

            } else if(index==7){
                if (wipdetail.getTime3() != null  && wipdetail.getEndtime3()!=null && !"".equals(wipdetail.getEndtime3())) {
                    wipdetail.setInfo("D段");
                    wipdetail.setTime4(timestamp);
//                    wipdetail.setZt(0);
                    String mes = DateUtils.getDayDiffer(wipdetail.getTime3(), timestamp);
                    wipdetail.setDay3(mes);
                    wipService.updateWipDtetails(wipdetail);
                    return "OK";
                } else if (isAdd) {
                    wipdetail.setInfo("D段");
                    wipdetail.setTime4(timestamp);
//                    wipdetail.setZt(0);
                    wipService.updateWipDtetails(wipdetail);
                    return "OK";
                } else {
                    //禁止修改前工序修改数据
                    return "error";
                }
            }else{
                if(wipdetail.getTime4()!=null  && wipdetail.getEndtime4()!=null && !"".equals(wipdetail.getEndtime4())){
                    wipdetail.setInfo("入库");
                    wipdetail.setOuttime(timestamp);
                    wipdetail.setZt(0);
                    wipService.updateWipDtetails(wipdetail);
                }else if (isAdd) {
                    wipdetail.setInfo("入库");
                    wipdetail.setOuttime(timestamp);
                    wipdetail.setZt(0);
                    wipService.updateWipDtetails(wipdetail);
                    return "OK";
                } else {
                    //禁止修改前工序修改数据
                    return "error";
                }

            }

        }

        return "OK";


    }


    /**
     * 将已有的数据清除掉
     *
     * @param session
     * @return
     */
    @RequestMapping("update2")
    @ResponseBody
    public String update2(HttpSession session, String No, Integer index) {

        WipDetails wipdetail = wipService.selectWipDetails(No);
        if (index == 1) {
            //清空A段数据 需要后续数据没有
            if (wipdetail.getTime2() == null&&wipdetail.getEndtime1()==null) {
                wipService.clearTime1(No);
            } else {
                return "error";
            }
        } else if (index == 3) {
            //清除B段数据 若有A段数据则一定要保证将生产天数清除
            if (wipdetail.getTime3() == null&&wipdetail.getEndtime2()==null) {
                wipService.clearTime2(No);
            } else {
                return "error";
            }
        } else if (index == 5) {
            if (wipdetail.getTime4() == null&&wipdetail.getEndtime3()==null) {
                wipService.clearTime3(No);
            } else {
                return "error";
            }
        } else if (index == 7) {
            if (wipdetail.getOuttime() == null&&wipdetail.getEndtime4()==null) {
                wipService.clearTime4(No);
            } else {
                return "error";
            }
        }else{
            wipService.clearChuKu(No);
        }

        return "OK";
    }


    //查询所有的单领料时间
    @RequestMapping("update3")
    public String update3(String Brief, HttpServletRequest request) {

        if (Brief == null || Brief == "") {
            Brief = "制二";
            request.getSession().setAttribute("brief2", Brief);
        }
        //找到在制品工令单集合
        HttpSession session = request.getSession();
        //获取在制工单的id集合


        //获得制二线的在制品集合(会有和其他生产线重复的)
        List<Integer> ids3 = mpsService.findwip("制三");
        List<Integer> ids6 = mpsService.findwip("制六");
        List<Integer> ids9 = mpsService.findwip("制九");
        //
        List<Integer> ids = mpsService.findwip(Brief);
        ids.removeAll(ids3);
        ids.removeAll(ids6);
        ids.removeAll(ids9);

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -6);//自动处理2月平润年的问提
        Date d = c.getTime();
        Timestamp startDate = new Timestamp(d.getTime());

        c.setTime(new Date());
        c.add(Calendar.MONTH, 1);
        d = c.getTime();
        Timestamp endDate = new Timestamp(d.getTime());

        //查询	出台湾的在制品数量
        //1查询出出货台湾工单id集合
        List<Integer> taiWanIds = mpsService.findPlanIdsOfTaiWan(startDate, endDate);
        //2查询制二线完品检工序 完工量>0的
        List<Integer> pingJianIds = mpsService.selectWanGongPLan_id(startDate, endDate);
        //3两者取交集 得出皮夹线的发往台湾的数量
        pingJianIds.retainAll(taiWanIds);
        //4再和领料单中有制二线的工单集合取交集
        pingJianIds.retainAll(ids);

        //4去重
        List<Integer> taiwanids1 = pingJianIds.stream().distinct().collect(Collectors.toList());
        ids.removeAll(taiwanids1);


//        List<WipDetails> wipde = mpsService.selectMpsPlansByIds(ids);
//        //将id集合一一遍历循环 查找mysql数据库中
//        WipDetails wipdetail = null;
//        String planNo = "";
//        for (int i = 0; i < wipde.size(); i++) {
//            wipde.get(i).setLingPiLiaoTime(mmlReqOutService.getLingLiaoTimeByPlanId(wipde.get(i).getPlanId()));
//            planNo = wipde.get(i).getPlanNo();
//            wipdetail = wipService.selectWipDetails(planNo);
//            if (wipdetail != null) {
//                wipde.get(i).setTime1(wipdetail.getTime1());
//                wipde.get(i).setTime2(wipdetail.getTime2());
//                wipde.get(i).setTime3(wipdetail.getTime3());
//                wipde.get(i).setTime4(wipdetail.getTime4());
//
//                //jdp修改二次开发
//                //获取结束时间
//                wipde.get(i).setEndtime1(wipdetail.getEndtime1());
//                wipde.get(i).setEndtime2(wipdetail.getEndtime2());
//                wipde.get(i).setEndtime3(wipdetail.getEndtime3());
//                wipde.get(i).setEndtime4(wipdetail.getEndtime4());
//                wipde.get(i).setOuttime(wipdetail.getOuttime());
//
////                获取时间差
//                if (wipdetail.getEndtime1() != null) {
//                    int hourdiffer1 = DateUtils.getHourDiffer(wipdetail.getTime1(), wipdetail.getEndtime1());
//                    wipde.get(i).setTimediffer1(hourdiffer1);
//                    System.out.println("diff1---"+wipde.get(i).getTimediffer1());
//                }
//                if (wipdetail.getEndtime2() != null) {
//                    int hourdiffer2 = DateUtils.getHourDiffer(wipdetail.getTime2(), wipdetail.getEndtime2());
//                    wipde.get(i).setTimediffer2(hourdiffer2);
//                    System.out.println("diff2---"+wipde.get(i).getTimediffer2());
//                }
//                if (wipdetail.getEndtime3() != null) {
//                    int hourdiffer3 = DateUtils.getHourDiffer(wipdetail.getTime3(), wipdetail.getEndtime3());
//                    wipde.get(i).setTimediffer3(hourdiffer3);
//                    System.out.println("diff3---"+wipde.get(i).getTimediffer3());
//                }
//                if (wipdetail.getEndtime4() != null) {
//                    int hourdiffer4 = DateUtils.getHourDiffer(wipdetail.getTime4(), wipdetail.getEndtime4());
//                    wipde.get(i).setTimediffer4(hourdiffer4);
//                    System.out.println("diff4---"+wipde.get(i).getTimediffer4());
//                }
//
//                wipde.get(i).setInfo(wipdetail.getInfo());
//                wipde.get(i).setDay1(wipdetail.getDay1());
//                wipde.get(i).setDay2(wipdetail.getDay2());
//                wipde.get(i).setDay3(wipdetail.getDay3());
//            }
//        }
//
//
//        session.removeAttribute("Allwipdetails");
//        session.setAttribute("Allwipdetails", wipde);
//        session.setAttribute("isZ", 1);

//        return "redirect:http://192.168.123.198:778/update4";
        return "WIPDetails";
    }


    @RequestMapping("/update4")
    public String getIndex() {
        return "WIPDetails";
    }

}
