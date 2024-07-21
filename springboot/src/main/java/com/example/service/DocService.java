package com.example.service;

import com.example.entity.Doc;
import com.example.exception.CustomException;
import com.example.mapper.DocMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class DocService {

    @Resource
    private DocMapper docMapper;

    public void add(Doc doc) {
        Doc dbDoc = docMapper.selectByNumber(doc.getNumber());
        if (dbDoc != null) { // 该发文字号已存在 不允许重复
            throw new CustomException("该发文字号已存在");
        }
        docMapper.insert(doc);
    }

    public void deleteById(Integer id) {
        docMapper.deleteById(id);
    }

    public void updateById(Doc doc) {
        docMapper.updateById(doc);
    }

    public PageInfo<Doc> selectPage(Integer pageNum, Integer pageSize, Doc doc) {
        PageHelper.startPage(pageNum, pageSize);
        List<Doc> docList = docMapper.selectList(doc);
        return PageInfo.of(docList);
    }
}
