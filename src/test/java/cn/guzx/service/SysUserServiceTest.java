package cn.guzx.service;

import cn.guzx.MybatisGeneratorApplication;
import cn.guzx.model.SysUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = MybatisGeneratorApplication.class)
class SysUserServiceTest {

    @Autowired
    private SysUserService userService;

    @Test
    void addUser() {
    }

    @DisplayName("根据id查询SysUser")
    @Test
    void queryUserById() {
        Optional<SysUser> optionalSysUser = userService.queryUserById(2);
        Assertions.assertEquals(2, optionalSysUser.get().getId());
    }

    @Test
    void queryUser() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }
}