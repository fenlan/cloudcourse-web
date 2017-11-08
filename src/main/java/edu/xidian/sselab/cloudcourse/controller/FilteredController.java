package edu.xidian.sselab.cloudcourse.controller;

import edu.xidian.sselab.cloudcourse.redis.RedisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.Jedis;
import java.util.Set;


@Controller
@RequestMapping("/filter")
public class FilteredController {

    @Autowired
    private RedisProperties redisProperties;

    @GetMapping("")
    public String filter(Model model) {
        String redisNode = redisProperties.getRedisNode();
        String redisKey = redisProperties.getRedisKey();
        Jedis jedis = new Jedis(redisNode);
        Set<String> set = jedis.smembers(redisKey);
        model.addAttribute("dataSet", set);
        model.addAttribute("title", "Filtered data");
        return "filter";
    }
}
