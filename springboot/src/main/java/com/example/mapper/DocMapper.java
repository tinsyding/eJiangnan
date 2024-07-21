package com.example.mapper;

import com.example.entity.Doc;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface DocMapper {

    @Select("SELECT * FROM doc WHERE number = #{number}")
    Doc selectByNumber(String number);

    @Insert("INSERT INTO doc (title, time, authority, number, pdf) " +
            "VALUES (#{title}, #{time}, #{authority}, #{number}, #{pdf})")
    void insert(Doc doc);

    @Delete("DELETE FROM doc WHERE id = #{id}")
    void deleteById(Integer id);

    @Update("UPDATE doc SET title = #{title}, time = #{time}, authority = #{authority}, " +
            "number = #{number}, pdf = #{pdf} WHERE id = #{id}")
    void updateById(Doc doc);

    @Select("SELECT * FROM doc WHERE title LIKE CONCAT('%', #{title}, '%') AND time LIKE CONCAT('%', #{time}, '%') " +
            "AND authority LIKE CONCAT('%', #{authority}, '%') " +
            "AND number LIKE CONCAT('%', #{number}, '%') ORDER BY id DESC")
    List<Doc> selectList(Doc doc);

}
