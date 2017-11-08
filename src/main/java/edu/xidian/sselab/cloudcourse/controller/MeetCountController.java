package edu.xidian.sselab.cloudcourse.controller;

import edu.xidian.sselab.cloudcourse.domain.Record;
import edu.xidian.sselab.cloudcourse.hbase.HbaseClient;
import groovy.lang.Tuple2;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/meetCount")
public class MeetCountController {

    private static final String TABLE_NAME = "MeetCount";

    private final HbaseClient hbaseClient;

    @Autowired
    public MeetCountController(HbaseClient hbaseClient) {
        this.hbaseClient = hbaseClient;
    }

    @GetMapping("")
    public String get(Model model) {
        model.addAttribute("title", "MeetCount");
        model.addAttribute("condition", new Record());
        return "meetCount";
    }

    @PostMapping("")
    public String post(Model model, Record record) {
        Map<String, String> map = new HashMap<String, String>();
        hbaseClient.getConnection();
        Table table = hbaseClient.getTableByName(TABLE_NAME);
        Get get = new Get(Bytes.toBytes(record.getEid()));
        try {
            Result result = table.get(get);
            List<Cell> cellList =  result.listCells();
            for (Cell cell : cellList) {
                String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
                String value = Bytes.toString(CellUtil.cloneValue(cell));
                map.put(qualifier, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("query failed!");
        }
        model.addAttribute("title", "MeetCount");
        model.addAttribute("condition", record);
        model.addAttribute("recordList", map);
        return "meetCount";
    }
}
