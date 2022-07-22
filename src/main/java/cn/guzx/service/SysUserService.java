package cn.guzx.service;

import cn.guzx.mapper.SysUserMapper;
import cn.guzx.model.SysUser;
import cn.guzx.model.example.SysUserExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author 25446
 */
@Slf4j
@Service
public class SysUserService {

    @Autowired
    private SysUserMapper userMapper;

    public boolean addUser(SysUser user) {
        int insert = userMapper.insert(user);
        return insert > 0;
    }

    public Optional<SysUser> queryUserById(Integer id) {
        SysUser sysUser = userMapper.selectByPrimaryKey(id);
        Optional<SysUser> optionalSysUser = Optional.ofNullable(sysUser);
        return optionalSysUser;
    }

    public List<SysUser> queryUser(SysUser user) {
        SysUserExample example = new SysUserExample();
        SysUserExample.Criteria criteria = example.createCriteria();
        if (user.getId() != null) {
            criteria.andIdEqualTo(user.getId());
        }
        if (user.getUsername() != null) {
            criteria.andUsernameEqualTo(user.getUsername());
        }

        List<SysUser> sysUsers = userMapper.selectByExample(example);
        return Optional.ofNullable(sysUsers).orElse(new ArrayList<>());
    }

    public boolean updateUser(SysUser user) {
        int i = userMapper.updateByPrimaryKeySelective(user);
        return i > 0;
    }

    public boolean deleteUser(Integer id) {
        try {
            SysUser sysUser = userMapper.selectByPrimaryKey(id);
            if (sysUser != null) {
                int i = userMapper.deleteByPrimaryKey(id);
                return i > 0;
            }
        } catch (Exception e) {
            log.error("delete sysUser exception:{}", e);
        }
        return false;
    }
}
