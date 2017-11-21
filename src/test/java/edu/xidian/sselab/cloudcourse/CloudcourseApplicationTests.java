package edu.xidian.sselab.cloudcourse;

import edu.xidian.sselab.cloudcourse.domain.CountRecord;
import edu.xidian.sselab.cloudcourse.hbase.HbaseClient;
import edu.xidian.sselab.cloudcourse.redis.RedisProperties;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CloudcourseApplicationTests {

	@Test
	public void contextLoads() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Long time = Long.parseLong("1496246559")*1000;
		String d = format.format(time);
		try {
			Date date = format.parse(d);
			System.out.println(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}


	}

}
