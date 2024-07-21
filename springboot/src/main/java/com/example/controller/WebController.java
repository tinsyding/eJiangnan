package com.example.controller;

import com.example.common.Result;
import com.example.common.RoleEnum;
import com.example.entity.Account;
import com.example.service.AdminService;
import com.example.service.StudentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import cn.hutool.core.util.ObjectUtil;

@RestController
public class WebController {

    @Resource
    private AdminService adminService;

    @Resource
    private StudentService studentService;

    // 默认请求接口
    @GetMapping("/")
    public Result hello() {
        return Result.success();
    }

    // 登录接口
    @PostMapping("/login")
    public Result login(@RequestBody Account account) {
        Account dbAccount;
        if (RoleEnum.ADMIN.name().equals(account.getRole())) {  // 管理员登录
            dbAccount = adminService.login(account);
        } else if (RoleEnum.STUDENT.name().equals(account.getRole())) {  // 学生登录
            dbAccount = studentService.login(account);
        } else {
            return Result.error("角色错误");
        }
        return Result.success(dbAccount);
    }

    // 注册接口
    @PostMapping("/register")
    public Result register(@RequestBody Account account) {
        if (ObjectUtil.isEmpty(account.getUsername()) || ObjectUtil.isEmpty(account.getPassword())) {
            return Result.error("学号/工号或密码不能为空");
        }
        studentService.register(account);
        return Result.success();
    }

    // 重置密码接口
    @PostMapping("/reset")
    public Result reset(@RequestBody Account account) {
        if (ObjectUtil.isEmpty(account.getUsername()) || ObjectUtil.isEmpty(account.getPassword())) {
            return Result.error("学号/工号或密码不能为空");
        }
        studentService.reset(account);
        return Result.success();
    }

}
