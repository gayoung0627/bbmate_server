package com.bb.bb_server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/sample")
public class TestController {

    @GetMapping("/doA")
    public List<String> doA(){
        return Arrays.asList("AAA", "BBB");
    }
}
