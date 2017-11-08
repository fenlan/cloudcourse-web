package edu.xidian.sselab.cloudcourse.controller;

import edu.xidian.sselab.cloudcourse.domain.CountRecord;
import edu.xidian.sselab.cloudcourse.hbase.HbaseClient;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/count")
public class CountController {

    private static final String TABLE_NAME = "VehicleCount";

    private final HbaseClient hbaseClient;

    @Autowired
    public CountController(HbaseClient hbaseClient) {
        this.hbaseClient = hbaseClient;
    }

    @GetMapping("")
    public String get(Model model) {
        List<CountRecord> resultRecords = new ArrayList<>();
        hbaseClient.getConnection();
        Table table = hbaseClient.getTableByName(TABLE_NAME);
        Scan scan = new Scan();
        try {
            ResultScanner resultScanner = table.getScanner(scan);
            for (Result result : resultScanner) {
                CountRecord tempRecord = new CountRecord().mapFrom(result);
                resultRecords.add(tempRecord);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("query failed!");
        }
        model.addAttribute("title", "VehicleCount");
        model.addAttribute("recordList", resultRecords);
        return "vehicleCount";
    }

}
