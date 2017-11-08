package edu.xidian.sselab.cloudcourse.controller;

import edu.xidian.sselab.cloudcourse.domain.Record;
import edu.xidian.sselab.cloudcourse.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/track")
public class TrackController {

    private final RecordRepository repository;

    @Autowired
    public TrackController(RecordRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public String get(Model model) {
        model.addAttribute("title", "轨迹重现");
        model.addAttribute("condition", new Record());
        return "track";
    }

    @PostMapping("")
    public String post(Model model, Record record) {
        List<Record> recordList = repository.findAllByRecord(record);
        Collections.sort(recordList);
        model.addAttribute("recordList", recordList);
        model.addAttribute("title", "Track");
        model.addAttribute("condition", record);
        return "track";
    }
    
}
