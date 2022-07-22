package cn.guzx.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 25446
 */
@RestController
public class DemoController {

    @GetMapping("/demo/connect")
    public String connect() {
        return "success";
    }
}
