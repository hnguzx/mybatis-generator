package cn.guzx.mapper;

import cn.guzx.MybatisGeneratorApplication;
import cn.guzx.model.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Rollback
@SpringBootTest(classes = MybatisGeneratorApplication.class)
public class SysUserMapperTest {

    @Autowired
    private SysUserMapper userMapper;

    @Test
    void testQueryUser() {
        SysUser user = userMapper.selectByPrimaryKey(1);
        Assertions.assertTrue(user != null);
    }

    @Test
    @Transactional
    void testDeleteUser() {
        int i = userMapper.deleteByPrimaryKey(2);
        Assertions.assertEquals(1, i);
    }
}
