package com.starvincci.JIT;

import com.starvincci.JIT.util.DateUtils;
import com.starvincci.JIT.util.qrutil.QRCodeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JitApplicationTests {

	@Test
	public void contextLoads() {

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
		String time ="2020-01-04 12:21:00";
		String time2 = "2020-01-05 13:25:10";
		Timestamp t1=Timestamp.valueOf(time);
		Timestamp t2=Timestamp.valueOf(time2);
		System.out.println(DateUtils.getHourDiffer(t1,t2));





	}

}
