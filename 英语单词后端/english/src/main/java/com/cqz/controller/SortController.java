package com.cqz.controller;

import com.cqz.pojo.Sort;
import com.cqz.service.SortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@ResponseBody
@Controller
public class SortController {
    @Autowired
    private SortService sortService;

    @RequestMapping("/sort")
    public List<Sort> findSort() {
        List<Sort> sort = sortService.findSort();
        return sort;
    }
}
