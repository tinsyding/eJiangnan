package com.example.controller;

import com.example.common.Result;
import com.example.entity.StudentCourse;
import com.example.service.StudentCourseService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/studentCourse")
public class StudentCourseController {

    @Resource
    private StudentCourseService studentCourseService;

    @PostMapping("/add")
    public Result add(@RequestBody StudentCourse studentCourse) {
        // @RequestBody: 接收json数据
        studentCourseService.add(studentCourse);
        return Result.success();
    }

    // 查询所有课程
    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "2") Integer pageSize,
                             StudentCourse studentCourse) {  // ?name=java&no=1&teacher=1
        PageInfo<StudentCourse> pageInfo = studentCourseService.selectPage(pageNum, pageSize, studentCourse);
        return Result.success(pageInfo);
    }

    // 删除所选课程
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        studentCourseService.deleteById(id);
        return Result.success();
    }
}
