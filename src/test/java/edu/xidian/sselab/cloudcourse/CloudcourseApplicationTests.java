package edu.xidian.sselab.cloudcourse;

import edu.xidian.sselab.cloudcourse.domain.CountRecord;
import edu.xidian.sselab.cloudcourse.hbase.HbaseClient;
import edu.xidian.sselab.cloudcourse.redis.RedisProperties;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CloudcourseApplicationTests {

	private static Connection connection;
	private static String zookeeper = "192.168.1.104, 192.168.1.105";

	@Test
	public void contextLoads() {
		Configuration configuration = HBaseConfiguration.create();
		configuration.set("hbase.zookeeper.quorum", zookeeper);
		try {
			connection = ConnectionFactory.createConnection(configuration);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<String, String> map = new HashMap<String, String>();
		Table table = null;
		try {
			table = connection.getTable(TableName.valueOf("Trace"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Get get = new Get(Bytes.toBytes("33041100046805"));
		try {
			Result result = table.get(get);
			List<Cell> cellList =  result.listCells();
			for (Cell cell : cellList) {
				String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
				String[] value = Bytes.toString(CellUtil.cloneValue(cell)).split("#");
				map.put(value[0], value[1]);
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("query failed!");
		}

		for (String value : map.values()) {
			System.out.println("Value: " + value);
		}
	}

}
