package cn.guzx.web;

import cn.guzx.MybatisGeneratorApplication;
import cn.guzx.controller.SysUserController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = MybatisGeneratorApplication.class)
@AutoConfigureMockMvc
public class UserWebTest {

    @Autowired
    private SysUserController userController;

    @Test
    void testNotNull() {
        Assertions.assertNotNull(userController);
    }
}
