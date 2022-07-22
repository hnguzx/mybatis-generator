package cn.guzx.controller;

import cn.guzx.mapper.SysUserMapper;
import cn.guzx.model.SysUser;
import cn.guzx.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author 25446
 */
@RestController
public class SysUserController {

    @Autowired
    private SysUserService userService;

    @PostMapping("/addUser")
    public String addUser(@RequestBody SysUser user) {
        boolean b = userService.addUser(user);
        return b ? "success" : "fail";
    }

    @GetMapping("/queryUser/{id}")
    public SysUser queryUser(@PathVariable Integer id) {
        Optional<SysUser> optionalSysUser = userService.queryUserById(id);
        if (optionalSysUser.isPresent()) {
            return optionalSysUser.get();
        }
        return null;
    }

}
