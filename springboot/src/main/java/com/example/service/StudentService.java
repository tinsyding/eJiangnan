package com.example.service;

import com.example.common.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Student;
import com.example.exception.CustomException;
import com.example.mapper.StudentMapper;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StudentService {

    @Resource
    private StudentMapper studentMapper;

    // 学生登录
    public Account login(Account account) {
        Account dbStudent = studentMapper.selectByUsername(account.getUsername());
        if (dbStudent == null) {
            throw new CustomException("学号/工号不存在");
        }
        if (!account.getPassword().equals(dbStudent.getPassword())) {
            throw new CustomException("密码错误");
        }
        return dbStudent;
    }

    // 学生注册
    public void register(Account account) {
        Student student = new Student();
        student.setUsername(account.getUsername()); // 学号/工号
        student.setPassword(account.getPassword()); // 密码
        this.add(student);
    }

    // 学生重置密码
    public void reset(Account account) {
        Student student = new Student();
        student.setUsername(account.getUsername()); // 学号/工号
        student.setPassword(account.getPassword()); // 密码
        this.update(student);
    }


    // 增
    public void add(Student student) {
        Student dbStudent = studentMapper.selectByUsername(student.getUsername());
        if (dbStudent != null) { // 已有同名学号/工号 不允许重复
            throw new CustomException("此学号/工号已存在");
        }
        if (ObjectUtil.isEmpty(student.getName())) { // hutool工具包判断字符串是否为空
            student.setName(student.getUsername()); // 默认姓名为学号/工号
        }
        student.setRole(RoleEnum.STUDENT.name()); // 默认角色为学生
        studentMapper.insert(student);
    }

    private void update(Student student) {
        Student dbStudent = studentMapper.selectByUsername(student.getUsername());
        if (dbStudent == null) { // 无此学号/工号
            throw new CustomException("学号/工号不存在, 请先注册");
        }
        if (ObjectUtil.isEmpty(student.getName())) { // hutool工具包判断字符串是否为空
            student.setName(student.getUsername()); // 默认姓名为学号/工号
        }
        student.setRole(RoleEnum.STUDENT.name()); // 默认角色为学生
        studentMapper.update(student);
    }

    public void deleteById(Integer id) {
        studentMapper.deleteById(id);
    }

    public void updateById(Student student) {
        studentMapper.updateById(student);
    }

    public PageInfo<Student> selectPage(Integer pageNum, Integer pageSize, Student student) {
        PageHelper.startPage(pageNum, pageSize);
        List<Student> studentList = studentMapper.selectAll(student);
        return PageInfo.of(studentList);
    }
}
