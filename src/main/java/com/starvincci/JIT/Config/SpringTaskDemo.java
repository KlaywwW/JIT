package com.starvincci.JIT.Config;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.starvincci.JIT.Mysql.mapper.WipMapper;
import com.starvincci.JIT.Mysql.service.WipService;
import com.starvincci.JIT.Sqlserver.service.MpsPlanServiceImpl;
import com.starvincci.JIT.pojo.Wip;

/**
 * 定时任务:每天22点将在制数量写入数据库
 * @author admin
 *
 */
@Component
public class SpringTaskDemo {
	 private static final Logger log = LoggerFactory.getLogger(SpringTaskDemo.class);

	 @Autowired
	 private WipService wipmapper;
	 @Autowired
	 private  MpsPlanServiceImpl mpsPlanService;
	 
	    
	 
	    @Async
	    @Scheduled(cron = "0 0 22 * * ? ")
	    public void scheduled2() throws InterruptedException {
	        Thread.sleep(3000);
	        log.info("scheduled2 每12H执行一次：{}", LocalDateTime.now());
	        Wip wip=new Wip();
	        Timestamp time=new Timestamp(new Date().getTime());
	        wip.setTime(time);
	        //读取在制数量
	        Integer Num=mpsPlanService.getWipNum();
	       
	        wip.setNum(Num);
	        try {
	        	  wipmapper.insertOneInfo(wip);
			} catch (Exception e) {
				// TODO: handle exception
				  log.info("sql语句执行失败....");
			}
	      
	      
	    }

	 
	
}
