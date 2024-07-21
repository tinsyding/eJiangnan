package com.example.controller;

import com.example.common.Result;
import com.example.entity.Doc;
import com.example.service.DocService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/doc")
public class DocController {

    @Resource
    DocService docService;

    // 新增文档
    @PostMapping("/add")
    public Result add(@RequestBody Doc doc) {
        docService.add(doc);
        return Result.success();
    }

    // 删除文档
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        docService.deleteById(id);
        return Result.success();
    }

    // 修改文档
    @PutMapping("/update")
    public Result update(@RequestBody Doc doc) {
        docService.updateById(doc);
        return Result.success();
    }

    // 查询文档
    @GetMapping("/selectPage")
    public Result selectPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            Doc doc) {
        PageInfo<Doc> pageInfo = docService.selectPage(pageNum, pageSize, doc);
        return Result.success(pageInfo);
    }

}
