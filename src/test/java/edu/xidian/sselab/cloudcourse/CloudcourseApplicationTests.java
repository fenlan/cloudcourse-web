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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CloudcourseApplicationTests {

	private static final String TABLE_NAME = "VehicleCount";

	private final HbaseClient hbaseClient;

	@Autowired
	public CloudcourseApplicationTests(HbaseClient hbaseClient) {
		this.hbaseClient = hbaseClient;
	}

	@Test
	public void contextLoads() {

		List<CountRecord> resultRecords = new ArrayList<>();
		hbaseClient.getConnection();
		Table table = hbaseClient.getTableByName(TABLE_NAME);
		Scan scan = new Scan();
		try {
			ResultScanner resultScanner = table.getScanner(scan);
			for (Result result : resultScanner) {
				CountRecord tempRecord = new CountRecord().mapFrom(result);
				System.out.println(tempRecord.getCount());
				resultRecords.add(tempRecord);
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("query failed!");
		}
	}

}
