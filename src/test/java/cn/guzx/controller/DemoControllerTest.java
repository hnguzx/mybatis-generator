package cn.guzx.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DemoControllerTest {

    @Autowired
    private DemoController demoController;

    @Test
    void testConnect() throws Exception {
        String connect = demoController.connect();
        Assertions.assertEquals("success", connect);
    }
}
